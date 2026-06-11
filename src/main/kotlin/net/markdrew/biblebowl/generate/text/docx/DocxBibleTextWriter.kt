package net.markdrew.biblebowl.generate.text.docx

import mu.KLogger
import mu.KotlinLogging
import net.markdrew.biblebowl.docx.docxToPdf
import net.markdrew.biblebowl.generate.text.AnnotatedDoc
import net.markdrew.biblebowl.generate.text.BibleTextHandler
import net.markdrew.biblebowl.generate.text.BibleTextWalker
import net.markdrew.biblebowl.generate.text.BibleTextWriter
import net.markdrew.biblebowl.generate.text.Docx
import net.markdrew.biblebowl.generate.text.FeatureOptions
import net.markdrew.biblebowl.generate.text.HighlightContext
import net.markdrew.biblebowl.generate.text.LayoutOptions
import net.markdrew.biblebowl.generate.text.OutputFormat
import net.markdrew.biblebowl.model.AnalysisUnit
import net.markdrew.biblebowl.model.Book
import net.markdrew.biblebowl.model.ChapterRef
import net.markdrew.biblebowl.model.FULL_BOOK_FORMAT
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.model.VerseRef
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
import java.nio.file.Files
import java.nio.file.Path
import java.time.format.DateTimeFormatter

private val logger: KLogger = KotlinLogging.logger {}

/**
 * DOCX/Word output writer. Owns file lifecycle and PDF compilation; the per-transition emit logic
 * lives in [DocxHandler], driven by [BibleTextWalker]. The handler holds the docx4j package and
 * the AST stack across the walk; the writer calls `save()` on it after the walk completes.
 */
class DocxBibleTextWriter(private val style: DocxStyle) : BibleTextWriter {

    override val format: OutputFormat = Docx

    override fun write(
        outputFile: Path,
        doc: AnnotatedDoc<AnalysisUnit>,
        studyData: StudyData,
        layout: LayoutOptions,
        features: FeatureOptions,
    ) {
        Files.createDirectories(outputFile.parent)
        val handler = DocxHandler(style, layout, features)
        BibleTextWalker.walk(doc, studyData, layout, features, handler)
        handler.save(outputFile.toFile())
        outputFile.docxToPdf()
    }
}

private class DocxHandler(
    private val style: DocxStyle,
    private val layout: LayoutOptions,
    private val features: FeatureOptions,
) : BibleTextHandler {

    private val factory = ObjectFactory()
    private val wordPackage: WordprocessingMLPackage = WordprocessingMLPackage.createPackage()
    private val mainPart: MainDocumentPart = wordPackage.mainDocumentPart

    private val contentStack = ArrayDeque<ContentAccessor>().apply { addFirst(mainPart) }
    private val footnotes = mutableListOf<CTFtnEdn>()
    private var nextFootnote = 2L

    // The handler tracks "we're annotation-inside a paragraph" so that events between paragraphs
    // (which the historic DOCX guard skipped via `transition.present(PARAGRAPH) ?: continue`) stay
    // skipped here. Set true at paragraphBegin, false at paragraphEnd.
    private var inParagraph: Boolean = false

    // Active highlights for the upcoming text() run. Set by *Begin events, cleared by *End.
    private var activeUniqueWord: Boolean = false
    private var activeName: Boolean = false
    private var activeNumber: Boolean = false
    private var activeRegexCategory: String? = null
    private var activeSmallCaps: Boolean = false

    /**
     * Resolves a template fragment (e.g. `styles.xml`) under the style's resource directory to its
     * classpath URL. We go through [Class.getResource] rather than resolving a child off a base
     * directory URI: the latter requires NIO path resolution, which throws
     * [java.nio.file.FileSystemNotFoundException] when the app runs from a jar (a `jar:…!/…` URI whose
     * zip filesystem isn't open). The returned URI is only ever opened as a stream, which works for
     * both `file:` and `jar:` URLs.
     */
    private fun template(child: String): URI =
        javaClass.getResource("/${style.resourcePath}/$child")?.toURI()
            ?: throw IllegalStateException("Couldn't find template resource in classpath: /${style.resourcePath}/$child")

    private var footerRel: Relationship? = null

    init {
        mainPart.addTargetPart(fontTableFromTemplate(template("fontTable.xml")))
    }

    override fun documentBegin(studyData: StudyData, layout: LayoutOptions, features: FeatureOptions) {
        mainPart.addTargetPart(stylesPartFromTemplate(
            template("styles.xml"),
            style.typography.toTemplateBindings(layout.fontSize),
        ))
        footerRel = mainPart.addTargetPart(
            footerFromTemplate(
                template("footer.xml"), mapOf(
                    "title" to studyData.studySet.name,
                    "date" to layout.testDate.format(dateFormatter),
                )
            )
        )
    }

    override fun documentEnd() {
        val rel = checkNotNull(footerRel) { "documentEnd called before documentBegin" }
        if (layout.twoColumns) endTwoCols(rel)
        addEndMatter()
        finalSection(rel)
        addFootnotes()
    }

    fun save(outputFile: File) {
        wordPackage.save(outputFile)
        println("Wrote $outputFile")
    }

    override fun chapterBegin(chapter: ChapterRef, multiBook: Boolean, asHeading: Boolean, inParagraph: Boolean) {
        if (!inParagraph) return
        if (asHeading) {
            add(makeParagraph("Heading1")).addRun(chapterNum(chapter, multiBook))
        }
    }

    override fun chapterEnd(pageBreak: Boolean) {
        // DOCX has no chapterBreaksPage path today; the option is silently ignored to preserve
        // historical behavior. (Capability gating could surface this as an error later.)
    }

    override fun headingBegin(heading: String, inParagraph: Boolean) {
        if (!inParagraph) return
        add(makeParagraph("Heading1")).addRun().addText(heading)
    }

    override fun paragraphBegin(poetryIndentLevel: Int, inPoetry: Boolean, isFirstParagraphOfPoetry: Boolean) {
        val style = when (poetryIndentLevel) {
            1 -> if (isFirstParagraphOfPoetry) "Poetry0" else "Poetry1"
            2 -> "Poetry2"
            else -> "TextBody"
        }
        pushP(style)
        inParagraph = true
    }

    override fun paragraphEnd(inPoetry: Boolean) {
        finishP()
        inParagraph = false
    }

    override fun poetryBegin() {
        // No emit — poetry styling is encoded in paragraphBegin's selected style.
    }

    override fun poetryEnd() {
        // No emit — symmetric with poetryBegin.
    }

    override fun verseBegin(
        verse: VerseRef,
        chapter: ChapterRef,
        multiBook: Boolean,
        isFirstVerseOfChapter: Boolean,
        useHeadingsForChapters: Boolean,
        inPoetry: Boolean,
    ) {
        if (!inParagraph) return
        if (isFirstVerseOfChapter && !useHeadingsForChapters) {
            add(chapterNum(chapter, multiBook))
            // Multi-book corner case: in poetry the book-and-chapter label exceeds the indent, so
            // pop the current P and start a new "Poetry1" P (e.g. Deut 32 in the Moses set).
            if (inPoetry && multiBook) {
                add(pop<P>())
                pushP("Poetry1")
            }
        } else {
            add(verseNumRun(verse))
        }
    }

    override fun verseSeparator(inPoetry: Boolean) {
        if (!inPoetry) add(R()).addText(" ")
    }

    override fun bookBegin(book: Book) {
        // No emit — book title is currently unused.
    }

    override fun poetryIndent(numIndents: Int) {
        if (!inParagraph) return
        add(R().apply { repeat(numIndents) { content.add(Tab()) } })
    }

    override fun leadingFootnote(verseRef: VerseRef, content: String) {
        println(
            "DBG_DocxHandler.leadingFootnote: verse=$verseRef inParagraph=$inParagraph " +
                "stackTop=${contentStack.first()::class.simpleName}"
        )
        if (!inParagraph) return
        val fnRef = nextFootnote++
        footnotes.add(footnoteContent(verseRef, content, fnRef))
        add(footnoteRef2(fnRef))
        add(R()).addText(" ")
    }

    override fun trailingFootnote(verseRef: VerseRef, content: String, continuing: HighlightContext) {
        if (!inParagraph) return
        // DOCX doesn't need the close-emit-reopen dance the LaTeX writer uses — `continuing` is ignored.
        val fnRef = nextFootnote++
        footnotes.add(footnoteContent(verseRef, content, fnRef))
        add(footnoteRef2(fnRef))
    }

    override fun uniqueWordBegin() { activeUniqueWord = true }
    override fun uniqueWordEnd()   { activeUniqueWord = false }
    override fun nameBegin()       { activeName = true }
    override fun nameEnd()         { activeName = false }
    override fun numberBegin()     { activeNumber = true }
    override fun numberEnd()       { activeNumber = false }
    override fun regexBegin(category: String) { activeRegexCategory = category }
    override fun regexEnd()        { activeRegexCategory = null }
    override fun smallCapsBegin()  { activeSmallCaps = true }
    override fun smallCapsEnd()    { activeSmallCaps = false }

    override fun text(text: String, inPoetry: Boolean, inParagraph: Boolean) {
        if (!inParagraph) return
        val r = pushR()
        if (activeUniqueWord) {
            r.rPr = (r.rPr ?: RPr()).apply { u = U().apply { `val` = UnderlineEnumeration.SINGLE } }
        }
        if (activeNumber) {
            r.rPr = (r.rPr ?: RPr()).apply {
                shd = CTShd().apply { `val` = STShd.CLEAR; fill = NUMBER_FILL }
            }
        }
        activeRegexCategory?.let { category ->
            r.rPr = (r.rPr ?: RPr()).apply {
                shd = CTShd().apply { `val` = STShd.CLEAR; fill = features.customHighlights.color(category).toHex() }
            }
        }
        if (activeSmallCaps) {
            r.rPr = (r.rPr ?: RPr()).apply { smallCaps = wmlBoolean(true) }
        }
        if (activeName) {
            r.rPr = (r.rPr ?: RPr()).apply {
                shd = CTShd().apply { `val` = STShd.CLEAR; fill = NAME_FILL }
            }
        }
        finishR(text)
    }

    // ---- contentStack + AST helpers (lifted verbatim from the old Builder) -------------------

    private fun <T : ContentAccessor> push(ca: T): T = ca.also { contentStack.addFirst(ca) }

    @Suppress("UNCHECKED_CAST")
    private fun <T> pop(): T = contentStack.removeFirst() as T

    private fun pushR(): R {
        require(contentStack.first() is P) { "Can't push an R into a ${contentStack.first()::class.simpleName}!" }
        return push(R())
    }

    private fun finishR(text: String) {
        val r = pop<R>()
        val textToAdd = features.smallCaps[text] ?: text
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

    private fun endTwoCols(footerRelationship: Relationship) {
        add(P().apply {
            pPr = PPr().apply {
                sectPr = sectionProperties(footerRelationship, twoColumns = true)
            }
        })
    }

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

    private fun addContentsFromDoc(docUri: URI) {
        try {
            val doc = docUri.toURL().openStream().use {
                XmlUtils.unmarshal(it) as Document
            }
            mainPart.content.addAll(doc.body.content)
        } catch (_: FileNotFoundException) {
            // skip if front/endMatter doesn't exist
        }
    }

    private fun addEndMatter() {
        addContentsFromDoc(template("endMatter.xml"))
    }

    private fun addFootnotes() {
        mainPart.addTargetPart(FootnotesPart())
        mainPart.footnotesPart.contents = CTFootnotes().apply {
            footnote.add(0, CTFtnEdn().apply {
                type = STFtnEdn.SEPARATOR
                content.add(P().apply {
                    pPr = PPr().apply {
                        rPr = ParaRPr().apply { sz = HpsMeasure().apply { `val` = BigInteger.valueOf(13L) } }
                    }
                    content.add(R().apply { content.add(Separator()) })
                })
            })
            footnote.add(1, CTFtnEdn().apply {
                type = STFtnEdn.CONTINUATION_SEPARATOR
                content.add(P().apply {
                    pPr = PPr().apply {
                        rPr = ParaRPr().apply { sz = HpsMeasure().apply { `val` = BigInteger.valueOf(13L) } }
                    }
                    content.add(R().apply { content.add(ContinuationSeparator()) })
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

    private fun pStyle(style: String): PStyle = PStyle().apply { `val` = style }
    private fun rStyle(style: String): RStyle = RStyle().apply { `val` = style }

    private fun verseNumRun(verseRef: VerseRef): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("VerseNum") }
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
    ): CTFtnEdn = CTFtnEdn().apply {
        id = BigInteger.valueOf(footnoteId)
        content.add(makeParagraph("Footnote").apply {
            val runsSeq = " $fnContent".splitToSequence('*')
            addRun(R().apply {
                rPr = RPr().apply { rStyle = rStyle("FootnoteCharacters") }
                content.add(FootnoteRef())
            })
            addRun(R().apply {
                rPr = RPr().apply { u = U().apply { `val` = UnderlineEnumeration.SINGLE } }
                content.add(Tab())
                content.add(makeText(verseRef.format(FULL_BOOK_FORMAT), preserveSpace = false))
            })
            runsSeq.forEachIndexed { index, s ->
                if (s.isNotEmpty()) {
                    val italic = index % 2 == 1
                    val smallCaps = s in features.smallCaps || (s == "Lord" && italic)
                    content.add(makeRun(s, italic, smallCaps))
                }
            }
        })
    }

    private fun makeRun(textContent: String, italic: Boolean = false, smallCaps: Boolean = false): R = R().apply {
        if (italic || smallCaps) {
            rPr = RPr()
            if (italic) rPr.i = BooleanDefaultTrue()
            if (smallCaps) rPr.smallCaps = BooleanDefaultTrue()
        }
        content.add(makeText(textContent))
    }

    private fun footnoteRef2(id: Long): R = R().apply {
        rPr = RPr().apply { rStyle = rStyle("FootnoteAnchor") }
        content.add(factory.createRFootnoteReference(CTFtnEdnRef().apply { setId(BigInteger.valueOf(id)) }))
    }

    companion object {
        private const val NUMBER_FILL: String = "ffb66c"
        private const val NAME_FILL: String = "cc99ff" // light purple

        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL d, uuuu")

        private fun stylesPartFromTemplate(templateUri: URI, mappings: Map<String, String>): StyleDefinitionsPart =
            StyleDefinitionsPart().unmarshallFromTemplate(templateUri, mappings)

        private fun fontTableFromTemplate(templateUri: URI, mappings: Map<String, String> = emptyMap()): FontTablePart =
            FontTablePart().unmarshallFromTemplate(templateUri, mappings)

        private fun footerFromTemplate(templateUri: URI, mappings: Map<String, String> = emptyMap()): FooterPart =
            FooterPart().unmarshallFromTemplate(templateUri, mappings)

        private fun <E, P : JaxbXmlPart<E>> P.unmarshallFromTemplate(
            templateUri: URI,
            mappings: Map<String, String> = emptyMap(),
        ): P = apply {
            templateUri.toURL().openStream().reader().use { unmarshallFromTemplate(it, mappings) }
        }

        private fun <E, P : JaxbXmlPart<E>> P.unmarshallFromTemplate(
            template: Reader,
            mappings: Map<String, String> = emptyMap(),
        ): P = apply {
            @Suppress("UNCHECKED_CAST")
            jaxbElement = XmlUtils.unmarshallFromTemplate(template.readText(), mappings) as E
        }
    }
}
