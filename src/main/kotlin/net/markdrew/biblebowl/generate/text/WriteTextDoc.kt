package net.markdrew.biblebowl.generate.text

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.PRODUCTS_DIR
import net.markdrew.biblebowl.latex.docxToPdf
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.AnalysisUnit.CHAPTER
import net.markdrew.biblebowl.model.AnalysisUnit.FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.HEADING
import net.markdrew.biblebowl.model.AnalysisUnit.LEADING_FOOTNOTE
import net.markdrew.biblebowl.model.AnalysisUnit.NAME
import net.markdrew.biblebowl.model.AnalysisUnit.NUMBER
import net.markdrew.biblebowl.model.AnalysisUnit.PARAGRAPH
import net.markdrew.biblebowl.model.AnalysisUnit.POETRY
import net.markdrew.biblebowl.model.AnalysisUnit.REGEX
import net.markdrew.biblebowl.model.AnalysisUnit.SMALL_CAPS
import net.markdrew.biblebowl.model.AnalysisUnit.STUDY_SET
import net.markdrew.biblebowl.model.AnalysisUnit.UNIQUE_WORD
import net.markdrew.biblebowl.model.AnalysisUnit.VERSE
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
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
import org.docx4j.wml.CTColumns
import org.docx4j.wml.CTDocGrid
import org.docx4j.wml.CTFootnotes
import org.docx4j.wml.CTFtnEdn
import org.docx4j.wml.CTFtnEdnRef
import org.docx4j.wml.CTFtnProps
import org.docx4j.wml.CTPageNumber
import org.docx4j.wml.CTShd
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.Document
import org.docx4j.wml.FooterReference
import org.docx4j.wml.HdrFtrRef
import org.docx4j.wml.HpsMeasure
import org.docx4j.wml.NumFmt
import org.docx4j.wml.NumberFormat
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import org.docx4j.wml.PPrBase.PStyle
import org.docx4j.wml.ParaRPr
import org.docx4j.wml.R
import org.docx4j.wml.R.ContinuationSeparator
import org.docx4j.wml.R.FootnoteRef
import org.docx4j.wml.R.Separator
import org.docx4j.wml.R.Tab
import org.docx4j.wml.RPr
import org.docx4j.wml.RStyle
import org.docx4j.wml.STDocGrid
import org.docx4j.wml.STFtnEdn
import org.docx4j.wml.STShd
import org.docx4j.wml.SectPr
import org.docx4j.wml.Text
import org.docx4j.wml.TextDirection
import org.docx4j.wml.U
import org.docx4j.wml.UnderlineEnumeration
import java.io.File
import java.io.FileNotFoundException
import java.io.Reader
import java.math.BigInteger
import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val logger: KLogger = KotlinLogging.logger {}

enum class WmlFont(val value: String) {
    TIMES_NEW_ROMAN("Times New Roman:liga"),
    QUATTROCENTO_SANS("Quattrocento Sans"),
    MONOSPACE("Liberation Mono"),
    SANS_SERIF("Liberation Sans"),
}

private typealias RStyler = RPr.() -> Unit

fun main(args: Array<String>) {
    // parse the argument (if present)
    val studySet = StandardStudySet.parse(args.firstOrNull())

    // load the Bible data for the requested study set
    val studyData = StudyData.readData(studySet)

    // write a bunch of variations of the Bible text
    writeBibleDoc(studyData, LocalDate.of(2025, 4, 5))
}

fun writeBibleDoc(studyData: StudyData, testDate: LocalDate) {

    val customHighlights: Map<String, Set<Regex>> = mapOf(
        "ffff00" to divineNames.map { Regex(it) }.toSet(), // bright yellow
//        "namesColor" to setOf("John the Baptist".toRegex()),
//        rStyler to setOf(Regex.fromLiteral("LORD")),
    )

    // plain
    val tbbPlainOpts = TextOptions<String>(testDate, fontSize = 12)
    val marksPlainOpts = tbbPlainOpts.copy(fontSize = 10, twoColumns = true, useHeadingsForChapters = true)
    // unique words underlined
    val tbbUniqueWordsOpts = tbbPlainOpts.copy(underlineUniqueWords = true)
    val marksUniqueWordsOpts = marksPlainOpts.copy(underlineUniqueWords = true)
    // all highlighting
    val tbbFullOpts = tbbUniqueWordsOpts.copy(
        customHighlights = customHighlights,
        highlightNames = true,
        highlightNumbers = true,
    )
    val marksFullOpts = marksUniqueWordsOpts.copy(
        customHighlights = customHighlights,
        highlightNames = true,
        highlightNumbers = true,
    )

    // plain
    writeOneText("tbb-doc-format", defaultStyle, studyData, tbbPlainOpts)
    writeOneText("marks-doc-format", marksStyle, studyData, marksPlainOpts)

    // unique words underlined
    writeOneText("tbb-doc-format", defaultStyle, studyData, tbbUniqueWordsOpts)
    writeOneText("marks-doc-format", marksStyle, studyData, marksUniqueWordsOpts)

    // all highlighting
    writeOneText("tbb-doc-format", defaultStyle, studyData, tbbFullOpts)
    writeOneText("marks-doc-format", marksStyle, studyData, marksFullOpts)
}

private fun writeOneText(
    resourcePath: String,
    styleParams: Map<String, String>,
    studyData: StudyData,
    opts: TextOptions<String>,
): File {
    val name = studyData.studySet.simpleName
    // FIXME this is an ugly hack
    val modifiedOpts: TextOptions<String> =
        styleParams["mainFontSize"]?.let {
            opts.copy(fontSize = it.toInt() / 2)
        } ?: opts
    val outputFile = File("$PRODUCTS_DIR/$name/text/docx/$name-bible-text-${opts.fileNameSuffix}.docx")
    outputFile.parentFile.mkdirs()
    DocMaker(resourcePath, styleParams, modifiedOpts).renderText(outputFile, studyData)

    return outputFile.docxToPdf()
}

val defaultStyle: Map<String, String> = mapOf(
    "mainFont" to WmlFont.QUATTROCENTO_SANS.value,
    "verseNumFont" to WmlFont.QUATTROCENTO_SANS.value,
    "headingFont" to WmlFont.QUATTROCENTO_SANS.value,
    "headingFontSize" to "32",
    "chapterFontSize" to "27",
//    "mainFontSize" to "24", // populate from options
    "footnoteFontSize" to "20",
    "justified" to "left",
)

val marksStyle: Map<String, String> = mapOf(
    "mainFont" to WmlFont.TIMES_NEW_ROMAN.value,
    "verseNumFont" to WmlFont.SANS_SERIF.value,
    "headingFont" to WmlFont.SANS_SERIF.value,
    "chapterFontSize" to "28",
    "headingFontSize" to "24",
//    "mainFontSize" to "20", // populate from options
    "footnoteFontSize" to "18",
    "justified" to "both",
)

class DocMaker(
    resourcePath: String = "tbb-doc-format",
    private val styleParams: Map<String, String> = defaultStyle,
    private val opts: TextOptions<String>,
) {

    val factory = ObjectFactory()
    private val wordPackage: WordprocessingMLPackage = WordprocessingMLPackage.createPackage()
    private val mainPart: MainDocumentPart = wordPackage.mainDocumentPart

    private val contentStack = ArrayDeque<ContentAccessor>().apply { addFirst(mainPart) }
    private val footnotes = mutableListOf<CTFtnEdn>()
    private var nextFootnote = 2L

    private val baseUri: URI = javaClass.getResource("/$resourcePath")?.toURI()
        ?: throw IllegalStateException("Couldn't find resource in classpath: /$resourcePath")

    init {
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

    private fun finishR(text: String, opts: TextOptions<String>) {
        val r = pop<R>()
        val textToAdd = opts.smallCaps[text] ?: text
        if (textToAdd.isNotEmpty()) r.addText(textToAdd)
        if (r.content.isNotEmpty()) add(r)
    }

    private fun finishP() {
        if (contentStack.first() is P) add(pop<P>())
    }

    private fun pushP(style: String = "TextBody"): P {
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

    fun renderText(outputFile: File, studyData: StudyData) {
        mainPart.addTargetPart(stylesPartFromTemplate(baseUri.resolveChild("styles.xml"), styleParams, opts))

        val footerRel: Relationship = mainPart.addTargetPart(
            footerFromTemplate(
                baseUri.resolveChild("footer.xml"), mapOf(
                    "title" to studyData.studySet.name,
                    "date" to opts.testDate.format(dateFormatter)
                )
            )
        )

//        addFrontMatter()
//        if (opts.twoColumns) endOneCol(footerRel)
        renderText(studyData)
        if (opts.twoColumns) endTwoCols(footerRel)
        addEndMatter()
        finalSection(footerRel)
        addFootnotes()

        wordPackage.save(outputFile)
        println("Wrote $outputFile")
    }

    private fun endOneCol(footerRelationship: Relationship?) {
        add(P().apply {
            pPr = PPr().apply {
                sectPr = sectionProperties(footerRelationship)//sectionType = "nextPage")
            }
        })
    }

    private fun endTwoCols(footerRelationship: Relationship) {
        add(P().apply {
            pPr = PPr().apply {
                sectPr = sectionProperties(footerRelationship, twoColumns = true)
            }
        })
    }

    /**
     * A section's properties are stored in a sectPr element. For all sections except the last section, the sectPr
     * element is stored as a child element of the last paragraph in the section. For the last section, the sectPr is
     * stored as a child element of the body element.
     */
    private fun sectionProperties(
        footerRelationship: Relationship? = null,
        sectionType: String = "continuous",
        twoColumns: Boolean = false,
    ): SectPr = SectPr().update(footerRelationship, sectionType, twoColumns)

    private fun SectPr.update(
        footerRelationship: Relationship? = null,
        sectionType: String = "continuous",
        twoColumns: Boolean = false,
    ): SectPr {
        if (footerRelationship != null) egHdrFtrReferences.add(FooterReference().apply {
            id = footerRelationship.id
            type = HdrFtrRef.DEFAULT
        })
        footnotePr = CTFtnProps().apply {
            numFmt = NumFmt().apply { `val` = NumberFormat.DECIMAL }
        }
        type = SectPr.Type().apply { `val` = sectionType }
        pgSz = SectPr.PgSz().apply {
            w = BigInteger.valueOf(12240)
            h = BigInteger.valueOf(15840)
        }
        if (twoColumns) cols = CTColumns().apply {
            num = BigInteger.TWO
            space = BigInteger.valueOf(288)
            isEqualWidth = true
            isSep = false
        }
        pgMar = SectPr.PgMar().apply {
            left = BigInteger.valueOf(1440)
            right = BigInteger.valueOf(1440)
            gutter = BigInteger.ZERO
            header = BigInteger.ZERO
            top = BigInteger.valueOf(1440)
            footer = BigInteger.valueOf(720)
            bottom = BigInteger.valueOf(1440)
        }
        pgNumType = CTPageNumber().apply { fmt = NumberFormat.DECIMAL }
        formProt = wmlBoolean(false)
        textDirection = TextDirection().apply { `val` = "lrTb" }
        docGrid = CTDocGrid().apply {
            type = STDocGrid.DEFAULT
            linePitch = BigInteger.valueOf(100)
            charSpace = BigInteger.ZERO
        }
        return this
    }

    private fun renderText(studyData: StudyData) {
        val annotatedDoc: AnnotatedDoc<AnalysisUnit> = BibleTextRenderer.annotatedDoc(studyData, opts)
        for ((excerpt, transition) in annotatedDoc.stateTransitions()) {

            /*
             * Endings
             */

            if ((transition.isEnded(PARAGRAPH))) {
                finishP()
                logger.trace { "Ended $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }

            if (transition.isEnded(STUDY_SET)) {
                logger.trace { "Ended $STUDY_SET ${transition.present(VERSE)?.value}" }
            }

            /*
             * Beginnings
             */

            if (transition.isBeginning(STUDY_SET)) {
                logger.trace { "Began $STUDY_SET ${transition.present(VERSE)?.value}" }
            }

            // Get the current paragraph annotation, and if we're not in a paragraph, skip the remainder of this loop
            val paragraph: Annotation<AnalysisUnit> = transition.present(PARAGRAPH) ?: continue

            transition.beginning(CHAPTER)?.apply {
                if (opts.useHeadingsForChapters) {
                    add(makeParagraph("Heading1")).addRun(chapterNum(value as ChapterRef, studyData.isMultiBook))
                    logger.trace { "Added $CHAPTER: $value (${transition.present(VERSE)?.value})" }
                }
            }

            transition.beginning(HEADING)?.apply {
                add(makeParagraph("Heading1")).addRun().addText(value as String)
                logger.trace { "Added $HEADING: $value (${transition.present(VERSE)?.value})" }
            }

            val newParagraph: Boolean = transition.isBeginning(PARAGRAPH)
            val newPoetry: Boolean = transition.isBeginning(POETRY)
            val inPoetry: Boolean = transition.isPresent(POETRY)
            val numIndents: Int = if (!inPoetry) 0 else paragraph.value as Int
            if (newParagraph) {
                val style = when (numIndents) {
                    1 -> if (newPoetry) "Poetry0" else "Poetry1"
                    2 -> "Poetry2"
                    else -> "TextBody"
                }
                pushP(style)
                logger.trace { "Began $PARAGRAPH ${transition.present(VERSE)?.value}" }
            }

            // text
            transition.beginning(VERSE)?.apply {
                val verseRef: VerseRef = value as VerseRef
                startVerse(verseRef, transition, studyData.isMultiBook)

                // when we're in poetry in a multi-book set and using chapter labels (that include the book name), the
                // book-and-chapter label will exceed the indent, so rather than adding a tab after the label on the
                // same line, just drop to the next line (this handles a rare, corner case such as in Deut 32)
                if (!opts.useHeadingsForChapters && inPoetry && studyData.isMultiBook && verseRef.verse == 1) {
                    add(pop<P>())
                    pushP("Poetry1")
                }
            }

            if (newParagraph && inPoetry) {
                add(R().apply {
                    repeat(numIndents) { content.add(Tab()) }
                })
                logger.trace { "Added $numIndents <tab/>s ${transition.present(VERSE)?.value}" }
            }

            // since LEADING footnotes are zero-width and precede the run text to which they are attached,
            // we need to handle them before any text
            transition.prePoint(LEADING_FOOTNOTE)?.apply {
                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verses: DisjointRangeMap<VerseRef> = studyData.verses
                val excerptRange: IntRange = excerpt.excerptRange
                val verseRef: VerseRef? = verses.valueContaining(excerptRange.first) // footnote in verse
                val fnRef = nextFootnote++
                footnotes.add(footnoteContent(verseRef!!, value as String, fnRef, opts))
                add(footnoteRef2(fnRef))
                add(R()).addText(" ")
                logger.trace { "Added $LEADING_FOOTNOTE ref ${transition.present(VERSE)?.value}" }
            }

            val r = pushR()

            if (transition.isPresent(UNIQUE_WORD)) {
                r.rPr = (r.rPr ?: RPr()).apply {
                    u = U().apply { `val` = UnderlineEnumeration.SINGLE }
                }
            }

            if (transition.isPresent(NUMBER)) {
                r.rPr = (r.rPr ?: RPr()).apply {
                    shd = CTShd().apply {
                        `val` = STShd.CLEAR
                        fill = "ffb66c" // light orange
                    }
                }
            }

            transition.present(REGEX)?.apply {
                r.rPr = (r.rPr ?: RPr()).apply {
                    shd = CTShd().apply {
                        `val` = STShd.CLEAR
                        fill = value as String
                    }
                }
            }

            transition.present(SMALL_CAPS)?.apply {
                r.rPr = (r.rPr ?: RPr()).apply {
                    smallCaps = wmlBoolean(true)
                }
            }

            if (transition.isPresent(NAME)) {
                r.rPr = (r.rPr ?: RPr()).apply {
                    shd = CTShd().apply {
                        `val` = STShd.CLEAR
                        fill = "b4c7dc" // light blue
                    }
                }
            }

            finishR(excerpt.excerptText, opts)

            // since footnotes are zero-width and follow the run text to which they are attached,
            // we need to handle them before any endings
            transition.postPoint(FOOTNOTE)?.apply {
                // subtract/add one from footnote offset to find verse in case
                // the footnote occurs at the end/beginning of the verse
                val verses: DisjointRangeMap<VerseRef> = studyData.verses
                val excerptRange: IntRange = excerpt.excerptRange
                val verseRef = verses.valueContaining(excerptRange.last) // footnote in verse
                val fnRef: Long = nextFootnote++
                footnotes.add(footnoteContent(verseRef!!, value as String, fnRef, opts))
                add(footnoteRef2(fnRef))
                logger.trace { "Added $FOOTNOTE ref ${transition.present(VERSE)?.value}" }
            }
        }
    }

    private fun addContentsFromDoc(docUri: URI) {
        try {
            val doc = docUri.toURL().openStream().use {
                XmlUtils.unmarshal(it) as Document
            }
            mainPart.content.addAll(doc.body.content)
        } catch (_: FileNotFoundException) {
            // if front/endMatter doesn't exist in the right directory, skip it
        }
    }

    private fun addFrontMatter() {
        addContentsFromDoc(baseUri.resolveChild("frontMatter.xml"))
    }

    private fun addEndMatter() {
        addContentsFromDoc(baseUri.resolveChild("endMatter.xml"))
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

    private fun finalSection(footerRel: Relationship) {
        wordPackage.documentModel.sections.last().sectPr.update(footerRel)
    }

    private fun R.addText(text: CharSequence): Text = makeText(text).also { content.add(it) }
    private fun P.addRun(r: R = R()): R = r.also { content.add(it) }

    private fun makeParagraph(style: String = "TextBody"): P = P().apply {
        pPr = PPr().apply {
            pStyle = pStyle(style)
            rPr = ParaRPr()
        }
    }

    private fun startVerse(
        verseRef: VerseRef,
        transition: StateTransition<AnalysisUnit>,
        multiBook: Boolean,
    ) {
        val chapterRef: ChapterRef = transition.present(CHAPTER)?.value as? ChapterRef
            ?: throw Exception("Not in any chapter?!")
        if (verseRef.verse == 1 && !opts.useHeadingsForChapters) {
            add(chapterNum(chapterRef, multiBook))
            logger.trace { "Skipping verse number for first verse of chapter." }
            logger.trace { "Added $CHAPTER: ${chapterRef.chapter} ($verseRef)" }
        } else {
            add(verseNumRun(verseRef))
            if (!transition.isPresent(POETRY)) add(R()).addText(" ")
            logger.trace { "Added $VERSE $verseRef" }
        }
    }

    private fun pStyle(style: String): PStyle = PStyle().apply { `val` = style }
    private fun rStyle(style: String): RStyle = RStyle().apply { `val` = style }

    private fun verseNumRun(verseRef: VerseRef): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("VerseNum") }
//        content.add(makeText("${verseRef.bookName.first()}${verseRef.chapter}:${verseRef.verse}"))
        content.add(makeText(verseRef.verse.toString()))
    }

    private fun chapterNum(chapterRef: ChapterRef, multiBook: Boolean): R = R().apply {
        val bookLabel: String =
            if (multiBook) chapterRef.format(FULL_BOOK_FORMAT)
            else "Chapter ${chapterRef.chapter}"
        rPr = RPr().apply { rStyle = rStyle("ChapterNum") }
        content.add(makeText("$bookLabel "))
    }

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
        footnoteId: Long,
        opts: TextOptions<String>,
    ): CTFtnEdn = CTFtnEdn().apply {
        id = BigInteger.valueOf(footnoteId)
        content.add(makeParagraph("Footnote").apply {
            // footnotes come in with asterisks around what should be italicized--compute the distinct runs
            val runsSeq = " $fnContent".splitToSequence('*')
            addRun(R().apply {
                rPr = RPr().apply { rStyle = rStyle("FootnoteCharacters") }
                content.add(FootnoteRef())
            })
            addRun(R().apply {
                rPr = RPr().apply {
                    u = U().apply { `val` = UnderlineEnumeration.SINGLE }
                }
                content.add(Tab())
                content.add(makeText(verseRef.format(FULL_BOOK_FORMAT), preserveSpace = false))
            })
            runsSeq.forEachIndexed { index, s ->
                if (s.isNotEmpty()) {
                    val italic = index % 2 == 1
                    // this is kind of fragile because it requires that the small caps portion already
                    // be its own run, but at least in Exodus, that seems to always be the case
                    val smallCaps = s in opts.smallCaps || (s == "Lord" && italic)
                    content.add(makeRun(s, italic, smallCaps))
                }
            }
        })
    }

    private fun makeRun(textContent: String, italic: Boolean = false, smallCaps: Boolean = false): R = R().apply {
        if (italic || smallCaps) {
            rPr = RPr()
            if (italic) {
                rPr.i = BooleanDefaultTrue()
            }
            if (smallCaps) {
                rPr.smallCaps = BooleanDefaultTrue()
            }
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

        private fun stylesPartFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap(),
            opts: TextOptions<String>
        ): StyleDefinitionsPart =
            StyleDefinitionsPart().unmarshallFromTemplate(
                templateUri,
                mappings + ("mainFontSize" to (2 * opts.fontSize).toString()),
            )

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
            templateUri.toURL().openStream().reader().use {
                unmarshallFromTemplate(it, mappings)
            }
        }

        private fun <E, P : JaxbXmlPart<E>> P.unmarshallFromTemplate(
            template: Reader,
            mappings: Map<String, String> = emptyMap()
        ): P = apply {
            @Suppress("UNCHECKED_CAST")
            jaxbElement = XmlUtils.unmarshallFromTemplate(template.readText(), mappings) as E
        }
    }
}
