package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.LEADING_FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.NAME
import net.markdrew.biblebowl.model.AnalysisUnit.NUMBER
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.REGEX
import net.markdrew.biblebowl.model.AnalysisUnit.STUDY_SET
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.BookFormat
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import net.markdrew.chupacabra.core.DisjointRangeMap
import org.docx4j.XmlUtils
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.JaxbXmlPart
import org.docx4j.openpackaging.parts.WordprocessingML.FontTablePart
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart
import org.docx4j.openpackaging.parts.WordprocessingML.FootnotesPart
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart
import org.docx4j.relationships.Relationship
import org.docx4j.wml.BooleanDefaultTrue
import org.docx4j.wml.CTFootnotes
import org.docx4j.wml.CTFtnEdn
import org.docx4j.wml.CTFtnEdnRef
import org.docx4j.wml.CTShd
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.FooterReference
import org.docx4j.wml.HdrFtrRef
import org.docx4j.wml.HpsMeasure
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import org.docx4j.wml.PPrBase.PStyle
import org.docx4j.wml.ParaRPr
import org.docx4j.wml.R
import org.docx4j.wml.R.ContinuationSeparator
import org.docx4j.wml.R.Separator
import org.docx4j.wml.R.Tab
import org.docx4j.wml.RPr
import org.docx4j.wml.RStyle
import org.docx4j.wml.STFtnEdn
import org.docx4j.wml.STShd
import org.docx4j.wml.SectPr
import org.docx4j.wml.Text
import org.docx4j.wml.U
import org.docx4j.wml.UnderlineEnumeration
import java.io.File
import java.io.InputStream
import java.math.BigInteger
import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.toPath

private val logger: KLogger = KotlinLogging.logger {}

private typealias RStyler = RPr.() -> Unit

fun main(args: Array<String>) {
    val studySet = StandardStudySet.parse(args.firstOrNull())
    val studyData = StudyData.readData(studySet)

    val rStyler: RStyler = { smallCaps = BooleanDefaultTrue().apply { isVal = true } }
    val customHighlights: Map<String, Set<Regex>> = mapOf(
        "ffff00" to divineNames.map { it.toRegex() }.toSet(), // bright yellow
//        "namesColor" to setOf("John the Baptist".toRegex()),
//        rStyler to setOf(Regex.fromLiteral("LORD")),
    )

//    writeBibleText(book, TextOptions(fontSize = 12, names = false, numbers = false, uniqueWords = true))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = false))
//    writeBibleText(book, TextOptions(names = false, numbers = false, uniqueWords = true))
    writeBibleDoc2(
        studyData,
        TextOptions(names = true, numbers = true, uniqueWords = true, customHighlights = customHighlights)
    )
}

fun writeBibleDoc2(studyData: StudyData, opts: TextOptions<String> = TextOptions()) {
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

class DocMaker2 {

    val factory = ObjectFactory()
    private val wordPackage: WordprocessingMLPackage = WordprocessingMLPackage.createPackage()
    private val mainPart: MainDocumentPart = wordPackage.mainDocumentPart

    private val contentStack = ArrayDeque<ContentAccessor>().apply { addFirst(mainPart) }
    val footnotes = mutableListOf<CTFtnEdn>()
    private var nextFootnote = 2L
    private val currentRunText = StringBuilder()

    private val baseUri: URI = javaClass.getResource("/tbb-doc-format2")?.toURI()
        ?: throw IllegalStateException("Couldn't find resource in classpath: /tbb-doc-format2")
    init {
        mainPart.addTargetPart(stylesPartFromTemplate(baseUri.resolveChild("styles.xml")))
        mainPart.addTargetPart(fontTableFromTemplate(baseUri.resolveChild("fontTable.xml")))
    }

    private fun <T : ContentAccessor> push(ca: T): T = ca.also { contentStack.addFirst(ca) }

    @Suppress("UNCHECKED_CAST")
    private fun <T> pop(): T = contentStack.removeFirst() as T

    private fun pushR(styler: RStyler? = null): R {
        require(contentStack.first() is P) { "Can't push an R into a ${contentStack.first()::class.simpleName}!" }
        return push(R().apply {
            if (styler != null) {
                if (rPr == null) rPr = RPr()
                rPr.styler()
            }
        })
    }

    private fun finishR() {
        if (contentStack.first() is R) {
            if ("LORD" !in currentRunText) {
                val r = pop<R>()
                if (currentRunText.isNotEmpty()) r.addText()
                if (r.content.isNotEmpty()) add(r)
            } else {
                // all this is just to use LORD in small caps!
                val findAll: Sequence<MatchResult> = Regex.fromLiteral("LORD").findAll(currentRunText)
                var r = add(pop<R>())
                var nextIndex = 0
                findAll.forEach { matchResult ->
                    if (nextIndex < matchResult.range.first) {
                        r.apply { addText(currentRunText.substring(nextIndex until matchResult.range.first)) }
                        r = add(R())
                    }
                    r.apply {
                        rPr = RPr().apply { smallCaps = wmlBoolean(true) }
                        addText("Lord")
                    }
                    nextIndex = matchResult.range.last + 1
                    if (nextIndex < currentRunText.length) r = add(R())
                }
                var removeLastR = true
                if (nextIndex < currentRunText.length) {
                    val text = currentRunText.substring(nextIndex)
                    if (text.isNotEmpty()) {
                        r.apply { addText(text) }
                        removeLastR = false
                    }
                }
                if (removeLastR) contentStack.first().content.removeLast()
                currentRunText.clear()
            }
        }
    }

    private fun finishP() {
        finishR()
        if (contentStack.first() is P) add(pop<P>())
    }

    private fun pushP(style: String = "TextBody"): P {
        finishR()
        require(contentStack.first() is MainDocumentPart) { "Can't push a P into a ${contentStack.first()::class.simpleName}!" }
        return push(makeParagraph(style))
    }

    private fun <T : Any> add(item: T): T = item.also {
        val container = contentStack.first()
        require(container is MainDocumentPart && item is P || container is P && item is R || container is R && item is Tab) {
            "Can't add a ${item::class.simpleName} to a ${container::class.simpleName}!"
        }
        container.content.add(it)
    }

    fun renderText(studyData: StudyData, opts: TextOptions<String>): WordprocessingMLPackage {
        addFooter(studyData)

        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleTextRenderer.annotatedDoc(studyData, opts)
        var newPoetry = false // used to track when we've entered a poetry section to use Poetry0 style for 1st line
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            // endings

            // beginnings

            if (transition.isBeginning(STUDY_SET)) {
                logger.debug { "Began $STUDY_SET ${transition.present(VERSE)?.value}" }
            }

            transition.beginning(AnalysisUnit.HEADING)?.apply {
                finishP()
                add(makeParagraph("Heading1")).addRun().addText(value as String)
                logger.debug { "Added ${AnalysisUnit.HEADING}: $value (${transition.present(VERSE)?.value})" }
            }

            if (transition.isBeginning(POETRY)) newPoetry = true

            val newParagraph: Boolean = transition.isBeginning(PARAGRAPH)
            val numIndents: Int =
                if (!transition.isPresent(POETRY)) 0
                else transition.present(PARAGRAPH)?.value as? Int? ?: 0
            if (newParagraph) {
                if (contentStack.first() is MainDocumentPart) {
                    val style = when(numIndents) {
                        1 -> if (newPoetry) "Poetry0" else "Poetry1"
                        2 -> "Poetry2"
                        else -> "TextBody"
                    }
                    pushP(style)
                    newPoetry = false
                }
                if (contentStack.first() is P) pushR()
                logger.debug { "Began $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }

            // text
            transition.beginning(VERSE)?.apply {
                startVerse(value as VerseRef, transition, studyData.isMultiBook, newParagraph)
            }

            if (newParagraph) {
                repeat(numIndents) {
                    add(Tab())
                    logger.debug { "Added <tab/> ${transition.present(VERSE)?.value}" }
                }
            }

            // since LEADING footnotes are zero-width and precede the run text to which they are attached,
            // we need to handle them before any text
            transition.prePoint(LEADING_FOOTNOTE)?.apply {
                if (contentStack.first() !is R) pushP() else finishR()

                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verses: DisjointRangeMap<VerseRef> = studyData.verses
                val excerptRange: IntRange = excerpt.excerptRange
                val verseRef = verses.valueContaining(excerptRange.first) // footnote in verse
                currentRunText.append(' ')
                val fnRef = nextFootnote++
                footnotes.add(footnoteContent(verseRef!!, value as String, fnRef))
                add(footnoteRef2(fnRef))
                pushR()
                logger.debug { "Added $LEADING_FOOTNOTE ref ${transition.present(VERSE)?.value}" }
            }

//            if (transition.isPresent(UNIQUE_WORD) && (transition.isPresent(NUMBER) || transition.isPresent(NAME) || transition.isPresent(REGEX)))
//                throw Exception(transition.present(VERSE)?.value.toString())

            if (opts.uniqueWords) {
                if (transition.isBeginning(UNIQUE_WORD)) {
                    finishR()
                    pushR { this.u = U().apply { `val` = UnderlineEnumeration.SINGLE }}
                }
                if (transition.isEnded(UNIQUE_WORD)) {
                    finishR()
                    pushR()
                }
            }

            if (opts.numbers) {
                if (transition.isBeginning(NUMBER)) {
                    finishR()
                    pushR {
                        shd = CTShd().apply {
                            `val` = STShd.CLEAR
                            fill = "ffb66c" // light orange
                        }
                    }
                }
                if (transition.isEnded(NUMBER)) {
                    finishR()
                    pushR()
                }
            }

            transition.present(REGEX)?.apply {
                finishR()
                pushR {
                    shd = CTShd().apply {
                        `val` = STShd.CLEAR
                        fill = value as String
                    }
                }
            }
            if (transition.isEnded(REGEX)) {
                finishR()
                pushR()
            }

            if (opts.names) {
                if (transition.isBeginning(NAME)) {
                    finishR()
                    pushR {
                        shd = CTShd().apply {
                            `val` = STShd.CLEAR
                            fill = "b4c7dc" // light blue
                        }
                    }
                }
                if (transition.isEnded(NAME)) {
                    finishR()
                    pushR()
                }
            }

//            if (excerpt.excerptText.isNotBlank()) {
            if (transition.isPresent(PARAGRAPH))
                currentRunText.append(excerpt.excerptText) //.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
//            }

            // since footnotes are zero-width and follow the run text to which they are attached,
            // we need to handle them before any endings
            transition.postPoint(FOOTNOTE)?.apply {
                if (contentStack.first() !is R) pushP() else finishR()

                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verses: DisjointRangeMap<VerseRef> = studyData.verses
                val excerptRange: IntRange = excerpt.excerptRange
                val verseRef = verses.valueContaining(excerptRange.last) // footnote in verse
//                currentRunText.append(' ')
                val fnRef: Long = nextFootnote++
                footnotes.add(footnoteContent(verseRef!!, value as String, fnRef))
                add(footnoteRef2(fnRef))
                pushR()
                logger.debug { "Added $FOOTNOTE ref ${transition.present(VERSE)?.value}" }
            }

            if ((transition.isEnded(PARAGRAPH))) {
                finishP()
                logger.debug { "Ended $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }

            if (transition.isEnded(STUDY_SET)) {
                logger.debug { "Ended $STUDY_SET ${transition.present(VERSE)?.value}" }
            }
        }
        addFootnotes()
        return wordPackage
    }

    private fun addFootnotes() {
        mainPart.addTargetPart(FootnotesPart())
        mainPart.footnotesPart.contents = CTFootnotes().apply {
            footnote.add(0, CTFtnEdn().apply {
                type = STFtnEdn.SEPARATOR
                content.add(P().apply {
                    pPr = PPr().apply {
                        rPr = ParaRPr().apply {
                            sz = HpsMeasure().apply { `val` = BigInteger.valueOf(13L) }
                        }
                    }
                    content.add(R().apply {
                        content.add(Separator())
                    })
                })
            })
            footnote.add(1, CTFtnEdn().apply {
                type = STFtnEdn.CONTINUATION_SEPARATOR
                content.add(P().apply {
                    pPr = PPr().apply {
                        rPr = ParaRPr().apply {
                            sz = HpsMeasure().apply { `val` = BigInteger.valueOf(13L) }
                        }
                    }
                    content.add(R().apply {
                        content.add(ContinuationSeparator())
                    })
                })
            })
            footnote.addAll(footnotes)
        }
    }

    private fun addFooter(studyData: StudyData) {
        val footerRel: Relationship = mainPart.addTargetPart(
            footerFromTemplate(
                baseUri.resolveChild("footer1.xml"), mapOf(
                    "title" to studyData.studySet.name,
                    "date" to LocalDate.now().format(dateFormatter)
                )
            )
        )
        val sectPr: SectPr = wordPackage.documentModel.sections.last().sectPr
        sectPr.egHdrFtrReferences.add(FooterReference().apply {
            id = footerRel.id
            type = HdrFtrRef.DEFAULT
        })
    }

    private fun R.addText(): Text = makeText().also { content.add(it) }
    private fun R.addText(text: CharSequence): Text = makeText(text).also { content.add(it) }
    private fun P.addRun(r: R = R()): R = r.also { content.add(it) }

    private fun makeParagraph(style: String = "TextBody"): P = P().apply {
        pPr = PPr().apply { pStyle = pStyle(style) }
    }

    private fun startVerse(
        verseRef: VerseRef,
        transition: StateTransition<AnalysisUnit>,
        multiBook: Boolean,
        newParagraph: Boolean,
    ) {
        val chapterRef = transition.present(CHAPTER)?.value as? ChapterRef ?: throw Exception("Not in any chapter?!")
        if (verseRef.verse == 1) {
            finishR()
            add(chapterNum(chapterRef, if (multiBook) FULL_BOOK_FORMAT else NO_BOOK_FORMAT))
            logger.debug { "Skipping verse number for first verse of chapter." }
            logger.debug { "Added $CHAPTER: ${chapterRef.chapter} ($verseRef)" }
        } else {
            if (!newParagraph && !currentRunText.endsWith(' '))
                currentRunText.append(' ')
            finishR()
            add(verseNumRun(verseRef))
            if (!transition.isPresent(POETRY)) currentRunText.append(' ')
            logger.debug { "Added $VERSE $verseRef" }
        }
        pushR()
    }

    private fun pStyle(style: String): PStyle = PStyle().apply { `val` = style }
    private fun rStyle(style: String): RStyle = RStyle().apply { `val` = style }

    private fun verseNumRun(verseRef: VerseRef): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("VerseNum") }
//        content.add(makeText("${verseRef.bookName.first()}${verseRef.chapter}:${verseRef.verse}"))
        content.add(makeText(verseRef.verse.toString()))
    }

    private fun chapterNum(chapterRef: ChapterRef, bookFormat: BookFormat): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("ChapterNum") }
        content.add(makeText("${chapterRef.format(bookFormat)} "))
    }

    private fun makeText(preserveSpace: Boolean = true): Text =
        makeText(currentRunText, preserveSpace).also { currentRunText.clear() }

    private fun makeText(text: CharSequence, preserveSpace: Boolean = true): Text =
        Text().apply {
            require(text.isNotEmpty()) { "Don't create text with no text in it!" }
            value = text.toString()
            if (preserveSpace) space = "preserve"
        }

    private fun wmlBoolean(value: Boolean): BooleanDefaultTrue = BooleanDefaultTrue().apply { isVal = value }

    private fun footnoteContent(
        verseRef: VerseRef,
        fnContent: String,
        footnoteId: Long
    ): CTFtnEdn = CTFtnEdn().apply {
        id = BigInteger.valueOf(footnoteId)
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
                if (s.isNotEmpty()) content.add(makeRun(s, italic = index % 2 == 1))
            }
        })
    }

    private fun makeRun(textContent: String, italic: Boolean = false): R = R().apply {
        if (italic) {
            rPr = RPr().apply { i = wmlBoolean(true) }
        }
        content.add(makeText(textContent))
    }

    // for real footnotes
    private fun footnoteRef2(id: Long): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("FootnoteAnchor") }
        content.add(factory.createRFootnoteReference(CTFtnEdnRef().apply { setId(BigInteger.valueOf(id)) }))
    }

    companion object {
        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL d, uuuu") // e.g. June 12, 2023

        private fun notBlank(s: CharSequence): Boolean = s.isNotBlank() || ' ' in s
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

        private fun footerFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap()
        ): FooterPart =
            FooterPart().unmarshallFromTemplate(templateUri, mappings)

        private fun <E, P : JaxbXmlPart<E>> P.unmarshallFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap()
        ): P = apply {
            templateUri.toURL().openStream().use {
                @Suppress("UNCHECKED_CAST")
                jaxbElement = XmlUtils.unmarshallFromTemplate(it.reader().readText(), mappings) as E
            }
        }
    }
}
