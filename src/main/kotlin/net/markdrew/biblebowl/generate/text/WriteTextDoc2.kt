package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.STUDY_SET
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.BookFormat
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import org.docx4j.XmlUtils
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.JaxbXmlPart
import org.docx4j.openpackaging.parts.WordprocessingML.FontTablePart
import org.docx4j.openpackaging.parts.WordprocessingML.FootnotesPart
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart
import org.docx4j.wml.BooleanDefaultTrue
import org.docx4j.wml.CTFootnotes
import org.docx4j.wml.CTFtnEdn
import org.docx4j.wml.CTFtnEdnRef
import org.docx4j.wml.CTTabStop
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import org.docx4j.wml.PPrBase
import org.docx4j.wml.PPrBase.PStyle
import org.docx4j.wml.ParaRPr
import org.docx4j.wml.R
import org.docx4j.wml.RPr
import org.docx4j.wml.RStyle
import org.docx4j.wml.STLineSpacingRule
import org.docx4j.wml.STTabJc
import org.docx4j.wml.Tabs
import org.docx4j.wml.Text
import org.docx4j.wml.U
import org.docx4j.wml.UnderlineEnumeration
import java.io.File
import java.io.InputStream
import java.math.BigInteger
import java.net.URI
import java.time.format.DateTimeFormatter
import kotlin.io.path.toPath

private val logger: KLogger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    val studySet = StandardStudySet.parse(args.firstOrNull())
    val studyData = StudyData.readData(studySet)
    val customHighlights = mapOf(
//        "divineColor" to divineNames.map { it.toRegex() }.toSet(),
        "namesColor" to setOf("John the Baptist".toRegex()),
    )
//    writeBibleText(book, TextOptions(fontSize = 12, names = false, numbers = false, uniqueWords = true))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = false))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = true))
    writeBibleDoc2(
        studyData,
//        TextOptions(names = true, numbers = true, uniqueWords = true, customHighlights = customHighlights)
    )
}

fun writeBibleDoc2(studyData: StudyData, opts: TextOptions = TextOptions()) {
    val name = studyData.studySet.simpleName
    val outputFile = File("$name-bible-text-${opts.fileNameSuffix}2.docx")
//    val outputFile = File("$PRODUCTS_DIR/$name/text/$name-bible-text-${opts.fileNameSuffix}.docx")
    val wordPackage: WordprocessingMLPackage = DocMaker2().renderText(studyData, opts)

    wordPackage.save(outputFile)
    println("Wrote $outputFile")
}

private fun URI.resolveChild(childString: String): URI =
    toPath().resolve(childString).toUri()
private fun URI.open(childString: String): InputStream =
    resolveChild(childString).toURL().openStream()
private fun StringBuilder.trimInPlace() {
    replace(0, Int.MAX_VALUE, trim().toString())
}

class DocMaker2 {

    val factory = ObjectFactory()
    val wordPackage: WordprocessingMLPackage = WordprocessingMLPackage.createPackage()
    val out: MainDocumentPart = wordPackage.mainDocumentPart

    val contentStack = ArrayDeque<ContentAccessor>().apply { addFirst(out) }
    val footnotes = mutableMapOf<Long, CTFtnEdn>()
    var nextFootnote = 2L
    val currentRunText = StringBuilder()
    var footnoteListId = 2L // this is the ID of the default/first numbered list

    init {
        val baseUri: URI = javaClass.getResource("/tbb-doc-format2")?.toURI()
            ?: throw IllegalStateException("Couldn't find resource in classpath: /tbb-doc-format2")
        out.addTargetPart(
            stylesPartFromTemplate(baseUri.resolveChild("styles.xml")),
            RelationshipsPart.AddPartBehaviour.OVERWRITE_IF_NAME_EXISTS,
            "rId1"
        )
        out.addTargetPart(
            fontTableFromTemplate(baseUri.resolveChild("fontTable.xml")),
            RelationshipsPart.AddPartBehaviour.OVERWRITE_IF_NAME_EXISTS,
            "rId3"
        )
    }

    private fun <T : ContentAccessor> push(ca: T): T = ca.also { contentStack.addFirst(ca) }
    @Suppress("UNCHECKED_CAST")
    private fun <T> pop(): T = contentStack.removeFirst() as T

    private fun pushR(): R {
        require(contentStack.first() is P)
        return push(R())
    }
    private fun popR(): R = pop()

    private fun pushP(style: String = "TextBody"): P {
        require(contentStack.first() !is R)
        return push(makeParagraph(style))
    }
    private fun popP(): P = pop()

    private fun <T> add(item: T): T = item.also { contentStack.first().content.add(it) }

    fun renderText(studyData: StudyData, opts: TextOptions): WordprocessingMLPackage {
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleTextRenderer.annotatedDoc(studyData, opts)
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            // endings

            transition.ended(CHAPTER)?.apply {
//                if (footnotes.isNotEmpty()) {
//                    contentStack.last().add(footnotesHeader())
//                    logger.debug { "Added ${FOOTNOTE}s for chapter $value (${transition.present(VERSE)?.value})" }
//                    contentStack.last().addAll(footnotes.values)
//                    contentStack.last().add(paragraph())
//                    footnotes.clear()
//                    nextFootnote = 0
////                    footnoteListId = out.numberingDefinitionsPart.restart(2L, 0L, 1L)
//                }
            }

            // beginnings

            if (transition.isBeginning(STUDY_SET)) {
                logger.debug { "Began $STUDY_SET ${transition.present(VERSE)?.value}" }
            }

            transition.beginning(AnalysisUnit.HEADING)?.apply {
                add(makeParagraph("Heading1")).addRun().addText(value as String)
                logger.debug { "Added ${AnalysisUnit.HEADING}: $value (${transition.present(VERSE)?.value})" }
            }
            if (transition.isBeginning(PARAGRAPH)) {
                if (contentStack.first() !is P) pushP()
                val p: P = contentStack.first() as P
                if (transition.isPresent(POETRY)) p.poetryPPr(1)
                logger.debug { "Began $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }


            // text
//            if (currentRunText.isNotBlank()) {
                transition.beginning(VERSE)?.apply {
                    startVerse(value as VerseRef, transition, studyData.isMultiBook)
                }
            if (excerpt.excerptText.isNotBlank()) currentRunText.append(excerpt.excerptText.trim())//.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
                if (transition.isPresent(POETRY)) {
                    val numIndents = countIndents(currentRunText)
                    repeat(numIndents) {
                        add(R.Tab())
                        logger.debug { "Added <tab/> ${transition.present(VERSE)?.value}" }
                        currentRunText.trimInPlace()
                    }
                }
//            } else {
//                if (!transition.isPresent(POETRY)) currentRunText.clear()
////                if (transition.isEnded(POETRY) && transition.isBeginning(POETRY)) {
////                    contentStack.last().add(paragraph().poetryPPr(0))
////                }
//            }
            if ((transition.isEnded(PARAGRAPH))) {
                if (contentStack.first() is R)
                    add(popR()).addText()
                add(popP())
                logger.debug { "Ended $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }

            // since footnotes are zero-width (1-width?) and follow the text to which they refer,
            // we need to handle them before any endings
            transition.present(FOOTNOTE)?.apply {
                if (contentStack.first() is R)
                    add(popR()).addText()

                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verseRef = studyData.verses.valueContaining(excerpt.excerptRange.first)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first - 1)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first + 1)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first + 2)
//                val fnRef = ('a' + nextFootnote++).toString()
                val fnRef = nextFootnote++
                footnotes[fnRef] = footnoteContent(verseRef!!, value as String, footnoteListId)
                if (contentStack.first() !is P) pushP()
                add(footnoteRef2(fnRef))
                logger.debug { "Added $FOOTNOTE ref ${transition.present(VERSE)?.value}" }
//                pushR()
            }

            if (transition.isEnded(STUDY_SET)) {
                logger.debug { "Ended $STUDY_SET ${transition.present(VERSE)?.value}" }
            }
        }
//        out.content.add(SectPr().apply {
//            footnotePr = CTFtnProps().apply {
//                numFmt = NumFmt().apply { `val` = NumberFormat.LOWER_LETTER }
//                numRestart = CTNumRestart().apply { `val` = STRestartNumber.EACH_SECT }
//            }
//        })
        out.addTargetPart(FootnotesPart(), RelationshipsPart.AddPartBehaviour.OVERWRITE_IF_NAME_EXISTS,
            "rId4")
        out.footnotesPart.contents = CTFootnotes().apply {
            for (fn in footnotes.values) {
                footnote.add(fn)
            }
        }
        return wordPackage
    }

    private fun R.addText(): Text = makeText().also { content.add(it) }
    private fun R.addText(text: CharSequence): Text = makeText(text).also { content.add(it) }
    private fun P.addRun(r: R = R()): R = r.also { content.add(it) }

//    private fun makeText(text: CharSequence): Text = Text().apply { value = text.toString() }
    private fun makeParagraph(formatFun: (PPr.() -> Unit)? = null): P = P().apply {
        if (formatFun != null) pPr = PPr().apply(formatFun)
    }
    private fun makeParagraph(style: String = "TextBody"): P = P().apply {
        pPr = PPr().apply { pStyle = pStyle(style) }
    }
//    private fun makeP(init: P.() -> Unit): P = P().apply { init() }

    private fun countIndents(text: CharSequence): Int = text.takeWhile { it.isWhitespace() }.length / 4

    private fun startVerse(
        verseRef: VerseRef,
        transition: StateTransition<AnalysisUnit>,
        multiBook: Boolean,
    ) {
        val verseNum: Int = verseRef.verse
        val chapterRef = transition.present(CHAPTER)?.value as? ChapterRef ?: throw Exception("Not in any chapter?!")
        if (verseNum == 1) {
            add(chapterNum(chapterRef, if (multiBook) FULL_BOOK_FORMAT else NO_BOOK_FORMAT))
            logger.debug { "Skipping verse number for first verse of chapter." }
            logger.debug { "Added $CHAPTER: ${chapterRef.chapter} ($verseRef)" }
            if (transition.isPresent(POETRY)) {
                add(popP())
                logger.debug { "Ended $PARAGRAPH ${transition.present(VERSE)?.value}" }
                pushP().also { if (transition.isPresent(POETRY)) it.poetryPPr(1) }
                logger.debug { "Began $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }
        } else {
            if (contentStack.first() is R) {
                currentRunText.append(' ')
                add(popR()).addText()
            }
            add(verseNumRun(verseNum))
            currentRunText.append(' ')
            logger.debug { "Added $VERSE $verseRef" }
        }
        pushR()
    }

    private fun P.poetryPPr(numIndents: Int): P {
        pPr = pPr ?: PPr()
        pPr.tabs = Tabs().apply {
            tab.add(CTTabStop().apply { `val` = STTabJc.CLEAR; pos = BigInteger("720") })
            tab.add(CTTabStop().apply { `val` = STTabJc.LEFT; pos = BigInteger("630") })
            tab.add(CTTabStop().apply { `val` = STTabJc.LEFT; pos = BigInteger("990") })
        }
        val indent = when(numIndents) {
            1 -> 630L
            2 -> 990L
            else -> 2000L
        }
        pPr.ind = PPrBase.Ind().apply {
            left = BigInteger.valueOf(indent)
            hanging = BigInteger.valueOf(indent)
        }
        return this
    }

    private fun heading(heading: String): P = P().apply {
        pPr = PPr().apply { pStyle = pStyle("Heading1") }
        content.add(R().apply { content.add(makeText(heading)) })
    }

    private fun pStyle(style: String): PStyle = PStyle().apply { `val` = style }
    private fun rStyle(style: String): RStyle = RStyle().apply { `val` = style }

    private fun verseNumRun(verseNum: Int): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("VerseNum") }
        content.add(makeText(verseNum.toString()))
    }

    private fun chapterNum(chapterRef: ChapterRef, bookFormat: BookFormat): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("ChapterNum") }
        content.add(makeText("${chapterRef.format(bookFormat)} "))
    }

    private fun makeText(preserveSpace: Boolean = true): Text = makeText(currentRunText, preserveSpace).also {
        currentRunText.clear()
    }

    private fun makeText(text: CharSequence, preserveSpace: Boolean = true): Text = Text().apply {
        value = text.toString()
        if (preserveSpace) space = "preserve"
    }

    private fun footnotesHeader(): P = P().apply {
        pPr = PPr().apply {
            keepNext = wmlBoolean(true)
            spacing = PPrBase.Spacing().apply {
                before = BigInteger.valueOf(300)
                after = BigInteger.valueOf(150)
                lineRule = STLineSpacingRule.AUTO
            }
            rPr = ParaRPr().apply {
//                rFonts = qsFonts
                b = wmlBoolean(true)
//                color = black
            }
        }
        content.add(R().apply {
            rPr = RPr().apply {
//                rFonts = qsFonts
                b = wmlBoolean(true)
//                color = black
                rtl = wmlBoolean(false)
            }
            content.add(makeText("Footnotes"))
        })
    }

    private fun wmlBoolean(value: Boolean): BooleanDefaultTrue = BooleanDefaultTrue().apply { isVal = value }

    private fun footnoteContent(
        verseRef: VerseRef,
        fnContent: String,
        footnoteListId: Long
    ): CTFtnEdn = CTFtnEdn().apply {
        id = BigInteger.valueOf(footnoteListId)
        content.add(makeParagraph("Footnote").apply {
            // footnotes come in with asterisks around what should be italicized--compute the distinct runs
            val runsSeq = " $fnContent".splitToSequence('*')
            addRun(R().apply {
                rPr = RPr().apply {
                    u = U().apply { `val` = UnderlineEnumeration.SINGLE }
                }
                content.add(makeText(verseRef.format(FULL_BOOK_FORMAT)))
            })
            runsSeq.forEachIndexed { index, s ->
                content.add(makeRun(s, italic = index % 2 == 1))
            }
        })
    }

    private fun makeRun(textContent: String, italic: Boolean = false): R = R().apply {
        rPr = RPr().apply {
//            rFonts = qsFonts
//            color = black
            rtl = wmlBoolean(false)
            if (italic) {
                i = wmlBoolean(true)
                iCs = wmlBoolean(true)
            }
        }
        content.add(makeText(textContent))
    }

    // for real footnotes
    fun footnoteRef2(id: Long): R = R().apply {
        rPr = RPr().apply {
            rStyle = RStyle().apply { `val` = "FootnoteAnchor" }
        }

        content.add(factory.createRFootnoteReference(CTFtnEdnRef().apply { setId(BigInteger.valueOf(id)) }))
    }
//    private fun footnoteRef(ref: String): R = R().apply {
//        rPr = RPr().apply {
////            rFonts = qsFonts
//            color = black
//            vertAlign = CTVerticalAlignRun().apply { `val` = STVerticalAlignRun.SUPERSCRIPT }
//        }
//        content.add(makeText(ref))
//    }

    companion object {
        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL d, uuuu") // e.g. June 12, 2023

        private fun stylesPartFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap()
        ): StyleDefinitionsPart =
            StyleDefinitionsPart().unmarshallFromTemplate(templateUri, mappings)

        private fun fontTableFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap()
        ): FontTablePart =
            FontTablePart().unmarshallFromTemplate(templateUri, mappings)

        private fun <E, P : JaxbXmlPart<E>> P.unmarshallFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap()
        ): P = apply {
            templateUri.toURL().openStream().use {
                @Suppress("UNCHECKED_CAST")
                jaxbElement = XmlUtils.unmarshallFromTemplate(
                    it.reader().readText(), mappings
                ) as E
            }
        }

    }
}
