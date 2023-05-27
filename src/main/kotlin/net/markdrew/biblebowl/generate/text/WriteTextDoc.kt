package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.generate.text.DocMaker.createMatthew
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StandardStudySet
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
import org.docx4j.Docx4J
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.PartName
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart
import org.docx4j.wml.BooleanDefaultTrue
import org.docx4j.wml.CTFtnProps
import org.docx4j.wml.CTNumRestart
import org.docx4j.wml.CTVerticalAlignRun
import org.docx4j.wml.Color
import org.docx4j.wml.NumFmt
import org.docx4j.wml.NumberFormat
import org.docx4j.wml.Numbering
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
import org.docx4j.wml.STVerticalAlignRun
import org.docx4j.wml.SectPr
import org.docx4j.wml.Text
import org.docx4j.wml.U
import org.docx4j.wml.UnderlineEnumeration
import java.io.File
import java.math.BigInteger

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

fun writeBibleDoc(studyData: StudyData, opts: TextOptions = TextOptions()) {
    val name = studyData.studySet.simpleName
    val outputFile = File("$name-bible-text-${opts.fileNameSuffix}.docx")
//    val outputFile = File("$PRODUCTS_DIR/$name/text/$name-bible-text-${opts.fileNameSuffix}.docx")
    val wordPackage: WordprocessingMLPackage = createMatthew(studyData, opts)

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
    
    fun createMatthew(studyData: StudyData, opts: TextOptions = TextOptions()): WordprocessingMLPackage =
        Docx4J.load(this.javaClass.getResourceAsStream("/text-template.docx")).apply {
            renderText(mainDocumentPart, studyData, opts)
//            with(mainDocumentPart.content) {
//                for (heading in studyData.headings) {
//                    val headingP = heading(heading.title)
////                    println(XmlUtils.marshaltoString(heading))
//                    add(headingP)
//                }
//            }
        }


    private fun renderText(out: MainDocumentPart, studyData: StudyData, opts: TextOptions) {
        // add a numbering part to the document
        out.addTargetPart(NumberingDefinitionsPart(PartName("/numbering")).apply {
            unmarshalDefaultNumbering()
        })

        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleTextRenderer.annotatedDoc(studyData, opts)
        val contentStack = ArrayDeque<MutableList<Any>>()
        val footnotes = mutableMapOf<String, Any>()
        var nextFootnote = 0
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            // since footnotes are zero-width and follow the text to which they refer,
            // we need to handle them before any endings
            transition.beginning(AnalysisUnit.FOOTNOTE)?.apply {
//                val outerAnns: List<Annotation<AnalysisUnit>> =
//                    transition.continuing.filter { it.key in setOf(
//                        AnalysisUnit.REGEX,
//                        AnalysisUnit.NAME,
//                        AnalysisUnit.NUMBER
//                    ) }
//                // assume/hope that only one of these ever matches
//                val outerAnn: Annotation<AnalysisUnit>? = if (outerAnns.isEmpty()) null else outerAnns.single()
//
//                // before inserting a footnote, need to end highlighting
//                if (outerAnn?.key == AnalysisUnit.REGEX) out.append('}')
//                if (opts.names && outerAnn?.key == AnalysisUnit.NAME) out.append('}')
//                if (opts.numbers && outerAnn?.key == AnalysisUnit.NUMBER) out.append('}')

                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verseRef = studyData.verses.valueContaining(excerpt.excerptRange.first)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first - 1)
                    ?: studyData.verses.valueContaining(excerpt.excerptRange.first + 1)
                val fnRef = ('a' + nextFootnote++).toString()
                footnotes[fnRef] = footnoteContent(verseRef!!, value as String, out.numberingDefinitionsPart.jaxbElement)
                contentStack.last().add(footnoteRef(fnRef))
                // after inserting a footnote, need to resume highlighting
//                if (opts.names && outerAnn?.key == AnalysisUnit.NAME) out.append("""\myname{""")
//                if (opts.numbers && outerAnn?.key == AnalysisUnit.NUMBER) out.append("""\mynumber{""")
//                if (outerAnn?.key == AnalysisUnit.REGEX) {
//                    val color = outerAnn.value
//                    out.append("""\myhl[$color]{""")
//                }
            }

            // endings

            if (transition.isEnded(AnalysisUnit.PARAGRAPH) /*&& !transition.isPresent(AnalysisUnit.POETRY)*/) {
                val p = paragraph().also { it.content.addAll(contentStack.removeLast()) }
                contentStack.last().add(p)
                logger.debug { "Ended ${AnalysisUnit.PARAGRAPH} ${contentStack.size}" }
            }
//            if (transition.isEnded(AnalysisUnit.POETRY)) out.appendLine("\\end{verse}\n")
//            if (opts.chapterBreaksPage && transition.isEnded(AnalysisUnit.CHAPTER)) out.appendLine("\\clearpage")
            transition.ended(AnalysisUnit.CHAPTER)?.apply {
                contentStack.last().add(footnotesHeader())
                logger.debug { "Added ${AnalysisUnit.FOOTNOTE}s for chapter $value (${contentStack.size})" }
                contentStack.last().addAll(footnotes.values)
                contentStack.last().add(paragraph())
                footnotes.clear()
                nextFootnote = 0
            }

            // beginnings

            if (transition.isBeginning(AnalysisUnit.STUDY_SET)) {
                contentStack.addLast(mutableListOf())
                logger.debug { "Began ${AnalysisUnit.STUDY_SET} ${contentStack.size}" }
            }

//            transition.beginning(AnalysisUnit.BOOK)?.apply { out.appendBookTitle(value as Book) }
//
            transition.beginning(AnalysisUnit.HEADING)?.apply {
                contentStack.last().add(heading(value as String))
                logger.debug { "Added ${AnalysisUnit.HEADING}: $value (${contentStack.size})" }
            }
            if (transition.isBeginning(AnalysisUnit.PARAGRAPH)) {
                contentStack.addLast(mutableListOf())
                logger.debug { "Began ${AnalysisUnit.PARAGRAPH} ${contentStack.size}" }
            }

//            if (transition.isBeginning(AnalysisUnit.POETRY)) out.appendLine("""\begin{verse}""")

            transition.beginning(AnalysisUnit.VERSE)?.apply {
                val verseNum: Int = (value as VerseRef).verse
                if (verseNum == 1) {
                    logger.debug { "Skipping verse number for first verse of chapter." }
                    val chapterRef = transition.present(AnalysisUnit.CHAPTER)?.value as? ChapterRef
                        ?: throw Exception("Not in any chapter?!")
                    contentStack.last().add(chapterNum(chapterRef.chapter))
                    logger.debug { "Added ${AnalysisUnit.CHAPTER}: ${chapterRef.chapter} (${contentStack.size})" }
                } else {
                    contentStack.last().add(verseNum(verseNum).also {
    //                    println(XmlUtils.marshaltoString(it))
                    })
                    logger.debug { "Added ${AnalysisUnit.VERSE} ${contentStack.size}" }
                }
            }

//            if (opts.uniqueWords && transition.isBeginning(AnalysisUnit.UNIQUE_WORD)) out.append("""{\uline{""")
//            if (opts.names && transition.isBeginning(AnalysisUnit.NAME)) out.append("""\myname{""")
//            if (opts.numbers && transition.isBeginning(AnalysisUnit.NUMBER)) out.append("""\mynumber{""")
//            if (transition.isBeginning(AnalysisUnit.REGEX)) {
//                val color = transition.beginning.first { it.key == AnalysisUnit.REGEX }.value
//                out.append("""\myhl[$color]{""")
//            }

            // text

            var textToOutput = excerpt.excerptText//.replace("""LORD""".toRegex(), """\\textsc{Lord}""")
//            if (transition.isPresent(AnalysisUnit.POETRY)) {
//                textToOutput = textToOutput
//                    .replace("""\n""".toRegex(), "\\\\\\\\\n")
//                    .replace("""(?<= {$INDENT_POETRY_LINES}) {$INDENT_POETRY_LINES}""".toRegex(), """\\vin """)
//            }
            if (textToOutput.isNotBlank()) {
                contentStack.last().add(verseText(textToOutput))
                logger.debug { "Added '$textToOutput' ${contentStack.size}" }
            }

            if (transition.isEnded(AnalysisUnit.STUDY_SET)) {
                out.content.addAll(contentStack.removeLast())
                logger.debug { "Ended ${AnalysisUnit.STUDY_SET} ${contentStack.size}" }
            }
        }
        out.content.add(SectPr().apply {
            footnotePr = CTFtnProps().apply {
                numFmt = NumFmt().apply { `val` = NumberFormat.LOWER_LETTER }
                numRestart = CTNumRestart().apply { `val` = STRestartNumber.EACH_SECT }
            }
        })
    }

    private fun heading(heading: String): P = P().apply {
        pPr = PPr().apply {
            keepNext = wmlBoolean(true)
//            pStyle = factory.createPPrBasePStyle().apply { `val` = "Normal1" }
//            shd = factory.createCTShd().apply {
//                fill = "ffffff"
//                `val` = STShd.CLEAR
//            }
            spacing = factory.createPPrBaseSpacing().apply {
                after = BigInteger.valueOf(150)
                line = BigInteger.valueOf(240)
                before = BigInteger.valueOf(300)
                lineRule = STLineSpacingRule.AUTO
            }
            rPr = factory.createParaRPr().apply {
                rFonts = qsFonts
                b = BooleanDefaultTrue()
                color = black
                sz = factory.createHpsMeasure().apply { `val` = BigInteger("27") }
                szCs = factory.createHpsMeasure().apply { `val` = BigInteger("27") }
            }
        }
        content.add(factory.createR().apply {
            rPr = factory.createRPr().apply {
                rFonts = qsFonts
                b = BooleanDefaultTrue()
                color = black
                sz = factory.createHpsMeasure().apply { `val` = BigInteger("27") }
                szCs = factory.createHpsMeasure().apply { `val` = BigInteger("27") }
                rtl = BooleanDefaultTrue()
            }
            content.add(mkText(heading))
        })
    }

    fun verseNum(verseNum: Int): R = factory.createR().apply {
        rPr = factory.createRPr().apply {
            rFonts = qsFonts
            b = BooleanDefaultTrue()
            color = black
            vertAlign = factory.createCTVerticalAlignRun().apply { `val` = STVerticalAlignRun.SUPERSCRIPT }
        }
//        content.add(mkText(" $verseNum "))
        content.add(mkText("$verseNum "))
    }

    fun chapterNum(chapterNum: Int): R = factory.createR().apply {
//        rsidRPr = "00000000"
//        rsidDel = "00000000"
//        rsidR = "00000000"
        rPr = factory.createRPr().apply {
            rFonts = qsFonts
            b = wmlBoolean(true)
            color = black
            sz = factory.createHpsMeasure().apply { `val` = BigInteger("32") }
            szCs = factory.createHpsMeasure().apply { `val` = BigInteger("32") }
            rtl = wmlBoolean(false)
        }
        content.add(mkText("$chapterNum "))
    }

    private fun mkText(text: String, preserveSpace: Boolean = true): Text = Text().apply {
        value = text
        if (preserveSpace) space = "preserve"
    }

    fun verseText(text: String): R = R().apply {
        rPr = factory.createRPr().apply {
            rFonts = qsFonts
            color = black
        }
        content.add(mkText("$text"))
    }

    fun paragraph(): P = factory.createP().apply {
//        rsidRPr="00000000"
//        rsidR="00000000"
//        rsidDel="00000000"
//        rsidP="00000000"
//        rsidRDefault="00000000"
        pPr = factory.createPPr().apply {
//            pStyle = factory.createPPrBasePStyle().apply { `val` = "Normal1" }
            shd = factory.createCTShd().apply {
                fill = "ffffff"
                `val` = STShd.CLEAR
            }
            spacing = factory.createPPrBaseSpacing().apply {
                before = BigInteger.valueOf(280)
                after = BigInteger.valueOf(280)
//                line = BigInteger.valueOf(240)
                lineRule = STLineSpacingRule.AUTO
            }
            rPr = factory.createParaRPr().apply {
                rFonts = qsFonts
                color = black
            }
        }
    }

    fun footnotesHeader(): P = factory.createP().apply {
        pPr = factory.createPPr().apply {
//            pStyle = factory.createPPrBasePStyle().apply { `val` = "Normal1" }
            shd = factory.createCTShd().apply {
                fill = "ffffff"
                `val` = STShd.CLEAR
            }
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
            content.add(mkText("Footnotes"))
        })
    }

    private fun wmlBoolean(value: Boolean): BooleanDefaultTrue = BooleanDefaultTrue().apply { isVal = value }

    fun footnoteContent(verseRef: VerseRef, fnContent: String, jaxbElement: Numbering): P = factory.createP().apply {
        // footnotes come in with asterisks around what should be italicized--compute the distinct runs
        val runsSeq = " $fnContent".splitToSequence('*')

        val bigNumId = BigInteger((10 * verseRef.chapter).toString())
        val nums: MutableList<Numbering.Num> = jaxbElement.num
        if (nums.none { it.numId == bigNumId }) nums.add(Numbering.Num().apply {
            numId = bigNumId
            abstractNumId = Numbering.Num.AbstractNumId().apply { `val` = BigInteger.ONE }
            // restart the footnote letters for each chapter
            lvlOverride.add(Numbering.Num.LvlOverride().apply {
                ilvl = BigInteger.ZERO
                startOverride = Numbering.Num.LvlOverride.StartOverride().apply { `val` = BigInteger.ONE }
            })
        })
        pPr = factory.createPPr().apply {
            numPr = PPrBase.NumPr().apply {
                ilvl = PPrBase.NumPr.Ilvl().apply { `val` = BigInteger.ZERO }
                numId = PPrBase.NumPr.NumId().apply { `val` = bigNumId }
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
            content.add(mkText(verseRef.format(FULL_BOOK_FORMAT)))
        })
//        content.add(makeRun(" $fnContent"))//, italic = index % 2 == 1))
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
        content.add(mkText(textContent))
    }

    // for real footnotes
    fun footnoteRef2(id: Int): R = R().apply {
        rPr = RPr().apply {
            rStyle = RStyle().apply { `val` = "FootnoteCharacters" }
            rFonts = qsFonts
            color = black
        }
        content.add(FootnoteRef()/*.apply { id = 2 }*/)
    }
    fun footnoteRef(ref: String): R = R().apply {
        rPr = RPr().apply {
            rFonts = qsFonts
            color = black
            vertAlign = CTVerticalAlignRun().apply { `val` = STVerticalAlignRun.SUPERSCRIPT }
        }
        content.add(mkText("$ref "))
    }
}
