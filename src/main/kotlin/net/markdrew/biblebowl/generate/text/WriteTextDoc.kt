package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.generate.text.DocMaker.createWmlPackage
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.BookFormat
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.NO_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import org.docx4j.Docx4J
import org.docx4j.XmlUtils
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.PartName
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart
import org.docx4j.wml.BooleanDefaultTrue
import org.docx4j.wml.CTFtnProps
import org.docx4j.wml.CTNumRestart
import org.docx4j.wml.CTTabStop
import org.docx4j.wml.CTVerticalAlignRun
import org.docx4j.wml.Color
import org.docx4j.wml.Ftr
import org.docx4j.wml.Lvl
import org.docx4j.wml.NumFmt
import org.docx4j.wml.NumberFormat
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import org.docx4j.wml.PPrBase
import org.docx4j.wml.R
import org.docx4j.wml.R.FootnoteRef
import org.docx4j.wml.RFonts
import org.docx4j.wml.RPr
import org.docx4j.wml.RStyle
import org.docx4j.wml.STLineSpacingRule
import org.docx4j.wml.STRestartNumber
import org.docx4j.wml.STShd
import org.docx4j.wml.STTabJc
import org.docx4j.wml.STVerticalAlignRun
import org.docx4j.wml.SectPr
import org.docx4j.wml.Tabs
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
    writeBibleDoc(
        studyData,
//        TextOptions(names = true, numbers = true, uniqueWords = true, customHighlights = customHighlights)
    )
}

fun writeBibleDoc(studyData: StudyData, opts: TextOptions<String> = TextOptions()) {
    val name = studyData.studySet.simpleName
    val outputFile = File("$name-bible-text-${opts.fileNameSuffix}.docx")
//    val outputFile = File("$PRODUCTS_DIR/$name/text/$name-bible-text-${opts.fileNameSuffix}.docx")
    val wordPackage: WordprocessingMLPackage = createWmlPackage(studyData, opts)

    wordPackage.save(outputFile)
    println("Wrote $outputFile")
}

object DocMaker {

    private val factory = ObjectFactory()
    
    private val qsFonts: RFonts = RFonts().apply {
        ascii="Quattrocento Sans"
        cs="Quattrocento Sans"
        eastAsia="Quattrocento Sans"
        hAnsi="Quattrocento Sans"
    }

    private val black: Color = factory.createColor().apply { `val` = "000000" }

    private fun URI.resolveChild(childString: String): URI =
        toPath().resolve(childString).toUri()
    private fun URI.open(childString: String): InputStream =
        resolveChild(childString).toURL().openStream()

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL d, uuuu") // e.g. June 12, 2023

    fun createWmlPackage(studyData: StudyData, opts: TextOptions<String> = TextOptions()): WordprocessingMLPackage {
        val baseUri: URI = javaClass.getResource("/tbb-doc-format")?.toURI()
            ?: throw IllegalStateException("Couldn't find resource in classpath: /tbb-doc-format")
        return Docx4J.load(baseUri.open("text-template.docx")).apply {
            val mappings: Map<String, String> = mapOf(
                "title" to studyData.studySet.name,
                "date" to LocalDate.now().format(dateFormatter)
            )
            mainDocumentPart.addTargetPart(
                footerPartFromTemplate("/word/footer2.xml", baseUri.resolveChild("footer2.xml"), mappings),
                RelationshipsPart.AddPartBehaviour.OVERWRITE_IF_NAME_EXISTS,
                "rId3"
            )
            renderText(mainDocumentPart, studyData, opts)
        }
    }

    private fun footerPartFromTemplate(
        footerPartName: String,
        templateUri: URI,
        mappings: Map<String, String>
    ): FooterPart = FooterPart(PartName(footerPartName)).apply {
        templateUri.toURL().openStream().use {
            jaxbElement = XmlUtils.unmarshallFromTemplate(
                it.reader().readText(), mappings
            ) as Ftr?
        }
    }


    private fun renderText(out: MainDocumentPart, studyData: StudyData, opts: TextOptions<String>) {
        // add a numbering part to the document
        out.addTargetPart(NumberingDefinitionsPart(PartName("/numbering")).apply {
            unmarshalDefaultNumbering()
            abstractListDefinitions["0"]?.abstractNumNode!!.lvl[0].apply {
                numFmt = NumFmt().apply { `val` = NumberFormat.LOWER_LETTER }
                lvlText = Lvl.LvlText().apply { `val` = "%1." }
                rPr = RPr().apply {
                    rFonts = qsFonts
                }
            }
        })

        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleTextRenderer.annotatedDoc(studyData, opts)
        val contentStack = ArrayDeque<MutableList<Any>>()
        val footnotes = mutableMapOf<String, Any>()
        var nextFootnote = 0
        val textToOutput = StringBuilder()
        var footnoteListId = 2L // this is the ID of the default/first numbered list
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            // endings

            if ((transition.isEnded(PARAGRAPH))) {
                endParagraph(contentStack, transition)
            }
            transition.ended(CHAPTER)?.apply {
                if (footnotes.isNotEmpty()) {
                    contentStack.last().add(footnotesHeader())
                    logger.debug { "Added ${FOOTNOTE}s for chapter $value (${transition.present(VERSE)?.value})" }
                    contentStack.last().addAll(footnotes.values)
                    contentStack.last().add(paragraph())
                    footnotes.clear()
                    nextFootnote = 0
                    footnoteListId = out.numberingDefinitionsPart.restart(2L, 0L, 1L)
                }
            }

            // beginnings

            if (transition.isBeginning(AnalysisUnit.STUDY_SET)) {
                contentStack.addLast(mutableListOf())
                logger.debug { "Began ${AnalysisUnit.STUDY_SET} ${transition.present(VERSE)?.value}" }
            }

            transition.beginning(AnalysisUnit.HEADING)?.apply {
                contentStack.last().add(heading(value as String))
                logger.debug { "Added ${AnalysisUnit.HEADING}: $value (${transition.present(VERSE)?.value})" }
            }
            if (transition.isBeginning(PARAGRAPH)) startParagraph(contentStack, transition)


            // text
            textToOutput.append(excerpt.excerptText)//.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
            if (textToOutput.isNotBlank()) {
                transition.beginning(VERSE)?.apply {
                    startVerse(value as VerseRef, transition, contentStack, studyData.isMultiBook)
                }
                contentStack.last().add(makeRun().apply {
                    if (transition.isPresent(POETRY)) {
                        val numIndents = countIndents(textToOutput)
                        repeat(numIndents) {
                            content.add(R.Tab())
                            textToOutput.trimInPlace()
                            logger.debug { "Added <tab/> ${transition.present(VERSE)?.value}" }
                        }
                    }
                    content.add(makeText(textToOutput))
                })
                logger.debug { "Added '$textToOutput' ${transition.present(VERSE)?.value}" }
                textToOutput.clear()
            } else {
                if (!transition.isPresent(POETRY)) textToOutput.clear()
                if (transition.isEnded(POETRY) && transition.isBeginning(POETRY)) {
                    contentStack.last().add(paragraph().poetryPPr(0))
                }
            }

            // since footnotes are zero-width (1-width?) and follow the text to which they refer,
            // we need to handle them before any endings
            transition.present(FOOTNOTE)?.apply {

                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verseRef = studyData.verses.valueContaining(excerpt.excerptRange.first)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first - 1)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first + 1)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first + 2)
                val fnRef = ('a' + nextFootnote++).toString()
                footnotes[fnRef] = footnoteContent(verseRef!!, value as String, footnoteListId)
                contentStack.last().add(footnoteRef(fnRef))
                logger.debug { "Added $FOOTNOTE ref ${transition.present(VERSE)?.value}" }
            }

            if (transition.isEnded(AnalysisUnit.STUDY_SET)) {
                out.content.addAll(contentStack.removeLast())
                logger.debug { "Ended ${AnalysisUnit.STUDY_SET} ${transition.present(VERSE)?.value}" }
            }
        }
        out.content.add(SectPr().apply {
            footnotePr = CTFtnProps().apply {
                numFmt = NumFmt().apply { `val` = NumberFormat.LOWER_LETTER }
                numRestart = CTNumRestart().apply { `val` = STRestartNumber.EACH_SECT }
            }
        })
    }

    private fun startParagraph(
        contentStack: ArrayDeque<MutableList<Any>>,
        transition: StateTransition<AnalysisUnit>
    ) {
        contentStack.addLast(mutableListOf())
        logger.debug { "Began $PARAGRAPH ${transition.present(VERSE)?.value}" }
    }

    private fun countIndents(text: CharSequence): Int = text.takeWhile { it.isWhitespace() }.length / 4

    private fun endParagraph(
        contentStack: ArrayDeque<MutableList<Any>>,
        transition: StateTransition<AnalysisUnit>,
    ) {
        val p = paragraph().also {
            it.content.addAll(contentStack.removeLast())
            if (transition.isPresent(POETRY)) it.poetryPPr(1)
        }
        contentStack.last().add(p)
        logger.debug { "Ended $PARAGRAPH ${transition.present(VERSE)?.value}" }
    }

    private fun startVerse(
        verseRef: VerseRef,
        transition: StateTransition<AnalysisUnit>,
        contentStack: ArrayDeque<MutableList<Any>>,
        multiBook: Boolean,
    ) {
        val verseNum: Int = verseRef.verse
        val chapterRef = transition.present(CHAPTER)?.value as? ChapterRef ?: throw Exception("Not in any chapter?!")
        if (verseNum == 1) {
            contentStack.last().add(chapterNum(chapterRef, if (multiBook) FULL_BOOK_FORMAT else NO_BOOK_FORMAT))
            logger.debug { "Skipping verse number for first verse of chapter." }
            logger.debug { "Added $CHAPTER: ${chapterRef.chapter} ($verseRef)" }
            if (transition.isPresent(POETRY)) {
                endParagraph(contentStack, transition)
                startParagraph(contentStack, transition)
            }
        } else {
            contentStack.last().add(verseNum(verseNum, addSpace = !transition.isBeginning(PARAGRAPH)))
            logger.debug { "Added $VERSE $verseRef" }
        }
    }

    private fun P.poetryPPr(numIndents: Int): P {
        pPr.tabs = Tabs().apply {
            tab.add(CTTabStop().apply { `val` = STTabJc.CLEAR; pos = BigInteger("720") })
            tab.add(CTTabStop().apply { `val` = STTabJc.LEFT; pos = BigInteger("630") })
            tab.add(CTTabStop().apply { `val` = STTabJc.LEFT; pos = BigInteger("990") })
        }
        pPr.spacing.apply {
            before = BigInteger.ZERO
            after = BigInteger.ZERO
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

    private fun heading(heading: String, size: Long = 32): P = P().apply {
        pPr = PPr().apply {
            keepNext = wmlBoolean(true)
            spacing = factory.createPPrBaseSpacing().apply {
                after = BigInteger.valueOf(150)
                line = BigInteger.valueOf(240)
                before = BigInteger.valueOf(300)
                lineRule = STLineSpacingRule.AUTO
            }
        }
        content.add(factory.createR().apply {
            rPr = factory.createRPr().apply {
                rFonts = qsFonts
                b = BooleanDefaultTrue()
                color = black
                sz = factory.createHpsMeasure().apply { `val` = BigInteger.valueOf(size) }
                szCs = factory.createHpsMeasure().apply { `val` = BigInteger.valueOf(size) }
                rtl = BooleanDefaultTrue()
            }
            content.add(makeText(heading))
        })
    }

    private fun verseNum(verseNum: Int, addSpace: Boolean): R = factory.createR().apply {
        rPr = factory.createRPr().apply {
            rFonts = qsFonts
            b = BooleanDefaultTrue()
            color = black
            vertAlign = factory.createCTVerticalAlignRun().apply { `val` = STVerticalAlignRun.SUPERSCRIPT }
        }
        var text = "$verseNum "
        if (addSpace) text = " $text"
        content.add(makeText(text))
    }

    private fun chapterNum(chapterRef: ChapterRef, bookFormat: BookFormat, size: Long = 27): R = factory.createR().apply {
        rPr = factory.createRPr().apply {
            rFonts = qsFonts
            b = wmlBoolean(true)
            color = black
            sz = factory.createHpsMeasure().apply { `val` = BigInteger.valueOf(size) }
            szCs = factory.createHpsMeasure().apply { `val` = BigInteger.valueOf(size) }
            rtl = wmlBoolean(false)
        }
        content.add(makeText("${chapterRef.format(bookFormat)} "))
    }

    private fun makeText(text: CharSequence, preserveSpace: Boolean = true): Text = Text().apply {
        value = text.toString()
        if (preserveSpace) space = "preserve"
    }

    private fun makeRun(): R = R().apply {
        rPr = factory.createRPr().apply {
            rFonts = qsFonts
            color = black
        }
    }

    fun paragraph(): P = factory.createP().apply {
        pPr = factory.createPPr().apply {
            shd = factory.createCTShd().apply {
                fill = "ffffff"
                `val` = STShd.CLEAR
            }
            spacing = factory.createPPrBaseSpacing().apply {
                before = BigInteger.valueOf(280)
                after = BigInteger.valueOf(280)
                lineRule = STLineSpacingRule.AUTO
            }
            rPr = factory.createParaRPr().apply {
                rFonts = qsFonts
                color = black
            }
        }
    }

    private fun footnotesHeader(): P = factory.createP().apply {
        pPr = factory.createPPr().apply {
            keepNext = wmlBoolean(true)
            spacing = factory.createPPrBaseSpacing().apply {
                before = BigInteger.valueOf(300)
                after = BigInteger.valueOf(150)
                lineRule = STLineSpacingRule.AUTO
            }
            rPr = factory.createParaRPr().apply {
                rFonts = qsFonts
                b = wmlBoolean(true)
                color = black
            }
        }
        content.add(R().apply {
            rPr = RPr().apply {
                rFonts = qsFonts
                b = wmlBoolean(true)
                color = black
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
    ): P = factory.createP().apply {
        // footnotes come in with asterisks around what should be italicized--compute the distinct runs
        val runsSeq = " $fnContent".splitToSequence('*')

        pPr = factory.createPPr().apply {
            numPr = PPrBase.NumPr().apply {
                ilvl = PPrBase.NumPr.Ilvl().apply { `val` = BigInteger.ZERO }
                numId = PPrBase.NumPr.NumId().apply { `val` = BigInteger.valueOf(footnoteListId) }
            }
            shd = factory.createCTShd().apply {
                fill = "ffffff"
                `val` = STShd.CLEAR
            }
            ind = PPrBase.Ind().apply {
                left = BigInteger("720")
                hanging = BigInteger("360")
            }
            spacing = factory.createPPrBaseSpacing().apply {
                before = BigInteger.valueOf(0)
                after = BigInteger.valueOf(0)
                lineRule = STLineSpacingRule.AUTO
            }
            rPr = factory.createParaRPr().apply {
                rFonts = qsFonts
                color = black
            }
        }
        content.add(R().apply {
            rPr = RPr().apply {
                rFonts = qsFonts
                color = black
                u = U().apply { `val` = UnderlineEnumeration.SINGLE }
                rtl = wmlBoolean(false)
            }
            content.add(makeText(verseRef.format(FULL_BOOK_FORMAT)))
        })
        runsSeq.forEachIndexed { index, s ->
            content.add(makeRun(s, italic = index % 2 == 1))
        }
    }

    private fun makeRun(textContent: String, italic: Boolean = false): R = R().apply {
        rPr = RPr().apply {
            rFonts = qsFonts
            color = black
            rtl = wmlBoolean(false)
            if (italic) {
                i = wmlBoolean(true)
                iCs = wmlBoolean(true)
            }
        }
        content.add(makeText(textContent))
    }

    // for real footnotes
    fun footnoteRef2(id: Int): R = R().apply {
        rPr = RPr().apply {
            rStyle = RStyle().apply { `val` = "FootnoteCharacters" }
            rFonts = qsFonts
            color = black
        }
        content.add(FootnoteRef())
    }
    private fun footnoteRef(ref: String): R = R().apply {
        rPr = RPr().apply {
            rFonts = qsFonts
            color = black
            vertAlign = CTVerticalAlignRun().apply { `val` = STVerticalAlignRun.SUPERSCRIPT }
        }
        content.add(makeText(ref))
    }

    private fun StringBuilder.trimInPlace() {
        replace(0, Int.MAX_VALUE, trim().toString())
    }
}
