package net.markdrew.biblebowl

import org.crosswire.common.util.NetUtil
import org.crosswire.common.util.ResourceUtil
import org.crosswire.common.xml.SAXEventProvider
import org.crosswire.common.xml.TransformingSAXEventProvider
import org.crosswire.common.xml.XMLUtil
import org.crosswire.jsword.book.Book
import org.crosswire.jsword.book.BookCategory
import org.crosswire.jsword.book.BookData
import org.crosswire.jsword.book.BookException
import org.crosswire.jsword.book.BookFilter
import org.crosswire.jsword.book.BookFilters
import org.crosswire.jsword.book.Books
import org.crosswire.jsword.book.BooksEvent
import org.crosswire.jsword.book.BooksListener
import org.crosswire.jsword.book.OSISUtil
import org.crosswire.jsword.book.install.InstallException
import org.crosswire.jsword.book.install.InstallManager
import org.crosswire.jsword.book.install.Installer
import org.crosswire.jsword.index.search.DefaultSearchModifier
import org.crosswire.jsword.index.search.DefaultSearchRequest
import org.crosswire.jsword.passage.Key
import org.crosswire.jsword.passage.NoSuchKeyException
import org.crosswire.jsword.passage.Passage
import org.crosswire.jsword.passage.PassageTally
import org.crosswire.jsword.passage.RestrictionType
import org.crosswire.jsword.util.ConverterFactory
import org.xml.sax.SAXException
import javax.xml.transform.TransformerException

/**
 * All the methods in this class highlight some are of the API and how to use
 * it.
 *
 * @see gnu.lgpl.License The GNU Lesser General Public License for details.
 *
 * @author Joe Walker
 */
class ApiExamples {

    /**
     * Get a particular installed book by initials.
     *
     * @param bookInitials
     * The book name to search for
     * @return The found book. Null otherwise.
     */
    fun getBook(bookInitials: String): Book? {
        return Books.installed().getBook(bookInitials)
    }

    /**
     * Get just the canonical text of one or more book entries without any
     * markup.
     *
     * @param bookInitials
     * the book to use
     * @param reference
     * a reference, appropriate for the book, of one or more entries
     * @return the plain text for the reference
     * @throws BookException
     * @throws NoSuchKeyException
     */
    @Throws(BookException::class, NoSuchKeyException::class)
    fun getPlainText(bookInitials: String, reference: String): String {
        val book = getBook(bookInitials) ?: return ""

        val key = book.getKey(reference)
        val data = BookData(book, key)
        return OSISUtil.getCanonicalText(data.osisFragment)
    }

    /**
     * Obtain a SAX event provider for the OSIS document representation of one
     * or more book entries.
     *
     * @param bookInitials
     * the book to use
     * @param reference
     * a reference, appropriate for the book, of one or more entries
     * @param maxKeyCount
     * @return a SAX Event Provider to retrieve the reference
     * @throws BookException
     * @throws NoSuchKeyException
     */
    @Throws(BookException::class, NoSuchKeyException::class)
    fun getOSIS(bookInitials: String?, reference: String?, maxKeyCount: Int): SAXEventProvider? {
        if (bookInitials == null || reference == null) {
            return null
        }

        val book = getBook(bookInitials)

        val key: Key?
        if (BookCategory.BIBLE == book?.bookCategory) {
            key = book.getKey(reference)
            (key as Passage).trimVerses(maxKeyCount)
        } else {
            key = book?.createEmptyKeyList()

            for ((count, aKey) in book!!.getKey(reference).withIndex()) {
                if (count + 1 >= maxKeyCount) break
                key!!.addAll(aKey)
            }
        }

        val data = BookData(book, key)

        return data.saxEventProvider
    }

    /**
     * Obtain styled text (in this case HTML) for a book reference.
     *
     * @param bookInitials
     * the book to use
     * @param reference
     * a reference, appropriate for the book, of one or more entries
     * @param maxKeyCount
     * @return the styled text
     * @throws NoSuchKeyException
     * @throws BookException
     * @throws TransformerException
     * @throws SAXException
     * @see Book
     *
     * @see SAXEventProvider
     */
    @Throws(NoSuchKeyException::class, BookException::class, TransformerException::class, SAXException::class)
    fun readStyledText(bookInitials: String, reference: String, maxKeyCount: Int): String {
        val book = getBook(bookInitials)
        val osissep = getOSIS(bookInitials, reference, maxKeyCount) ?: return ""

        val styler = ConverterFactory.getConverter()

        val htmlsep = styler.convert(osissep) as TransformingSAXEventProvider

        // You can also pass parameters to the XSLT. What you pass depends upon what the XSLT can use.
        val direction = book?.bookMetaData?.isLeftToRight ?: true
        htmlsep.setParameter("direction", if (direction) "ltr" else "rtl")

        // Finally you can get the styled text.
        return XMLUtil.writeToString(htmlsep)
    }

    /**
     * While Bible and Commentary are very similar, a Dictionary is read in a
     * slightly different way. It is also worth looking at the JavaDoc for Book
     * that has a way of treating Bible, Commentary and Dictionary the same.
     *
     * @throws BookException
     * @see Book
     */
    @Throws(BookException::class)
    fun readDictionary() {
        // This just gets a list of all the known dictionaries and picks the
        // first. In a real world app you will probably have a better way
        // of doing this.
        val dicts = Books.installed().getBooks(BookFilters.getDictionaries())
        val dict = dicts[0]

        // If I want every key in the Dictionary then I do this (or something
        // like it - in the real world you want to call hasNext() on an iterator
        // before next() but the point is the same:
        val keys = dict.globalKeyList
        val first = keys.iterator().next()

        println("The first Key in the default dictionary is $first")

        val data = BookData(dict, first)
        println("And the text against that key is " + OSISUtil.getPlainText(data.osisFragment))
    }

    /**
     * An example of how to search for various bits of data.
     *
     * @throws BookException
     */
    @Throws(BookException::class)
    fun search() {
        val bible = Books.installed().getBook(BIBLE_NAME)

        // This does a standard operator search. See the search documentation
        // for more examples of how to search
        val key = bible.find("+moses +aaron")

        println("The following verses contain both moses and aaron: " + key.name)

        // You can also trim the result to a more manageable quantity. 
        // The test here is not necessary since we are working with a bible. 
        // It is necessary if we don't know what it is.
        if (key is Passage) {
            val remaining = key.trimVerses(5)
            println("The first 5 verses containing both moses and aaron: " + key.getName())
            if (remaining != null) {
                println("The rest of the verses are: " + remaining.name)
            } else {
                println("There are only 5 verses containing both moses and aaron")
            }
        }
    }

    /**
     * An example of how to perform a ranked search.
     *
     * @throws BookException
     */
    @Throws(BookException::class)
    internal fun rankedSearch() {
        val bible = Books.installed().getBook(BIBLE_NAME)

        // For a more complex example:
        // Rank the verses and show the first 20
        val rank = true
        val max = 20

        val modifier = DefaultSearchModifier()
        modifier.isRanked = rank
        modifier.maxResults = max

        val results = bible.find(DefaultSearchRequest("for god so loved the world", modifier))
        val total = results.cardinality
        var partial = total

        // we get PassageTallys for rank searches
        if (results is PassageTally || rank) {
            val tally = results as PassageTally
            tally.ordering = PassageTally.Order.TALLY
            if (max in 1 until total) {
                // Here we are trimming by ranges, where a range is a set of continuous verses.
                tally.trimRanges(max, RestrictionType.NONE)
                partial = max
            }
        }
        println("Showing the first $partial of $total verses.")
        println(results)
    }

    /**
     * An example of how to do a search and then get text for each range of
     * verses.
     *
     * @throws BookException
     * @throws SAXException
     */
    @Throws(BookException::class, SAXException::class)
    internal fun searchAndShow() {
        val bible = Books.installed().getBook(BIBLE_NAME)

        // Search for words like Melchezedik
        val key = bible.find("melchesidec~")

        // Here is an example of how to iterate over the ranges and get the text
        // for each.
        // The key's iterator would have iterated over verses.

        // The following shows how to use a stylesheet of your own choosing
        val path = "org/crosswire/jsword/xml/html5.xsl"
        val xslUrl = ResourceUtil.getResource(path)
        // Make ranges  break  on  chapter
        val rangeIter = (key as Passage).rangeIterator(RestrictionType.CHAPTER)
        // boundaries.
        while (rangeIter.hasNext()) {
            val range = rangeIter.next()
            val data = BookData(bible, range)
            val osisSep = data.saxEventProvider
            val htmlSep = TransformingSAXEventProvider(NetUtil.toURI(xslUrl), osisSep)
            val text = XMLUtil.writeToString(htmlSep)
            println("The html text of " + range.name + " is " + text)
        }
    }

    /**
     * This is an example of the different ways to select a Book from the
     * selection available.
     *
     * @see org.crosswire.common.config.Config
     *
     * @see Books
     */
    fun pickBible() {
        // The Default Bible - JSword does everything it can to make this work
        var book = Books.installed().getBook(BIBLE_NAME)

        // And you can find out more too:
        println(book!!.language)

        // If you want a greater selection of Books:
        var books = Books.installed().books
        book = books[0]
        if (book != null) println(book.initials)

        // Or you can narrow the range a bit
        books = Books.installed().getBooks(BookFilters.getOnlyBibles())
        book = books[0]
        if (book != null) println(book.initials)

        // There are implementations of BookFilter for all sorts of things in
        // the BookFilters class

        // If you are wanting to get really fancy you can implement your own
        // BookFilter easily
        val test = Books.installed().getBooks(MyBookFilter("ESV"))
        book = test[0]

        if (book != null) println(book.initials)

        // If you want to know about new books as they arrive:
        Books.installed().addBooksListener(MyBooksListener())
    }

    fun installBook() {
        // An installer knows how to install books

        val installManager = InstallManager()

        // Ask the Install Manager for a map of all known module sites
        val installers: Map<String, Installer> = installManager.installers

        // Get all the installers one after the other
        for ((name, installer) in installers.entries) {
            println(name + ": " + installer.installerDefinition)
        }

        // If we know the name of the installer we can get it directly
        val installer: Installer = installManager.getInstaller("CrossWire")

        // Now we can get the list of books
        try {
            installer.reloadBookList()
        } catch (e: InstallException) {
            e.printStackTrace()
        }

        // Get a list of all the available books
//        for (book in installer.books) {
//            println("Book " + book.initials + " is available")
//        }
        println("${installer.books.size} books are available")

        val initials = "ESV2011" // An example book that's been at CrossWire for years.
        // get some available books. In this case, just one book.
        val book: Book? = installer.getBooks(MyBookFilter(initials)).firstOrNull()

        if (book != null) {
            println("Book " + book.initials + " is available")

            // Delete the book, if present
            // At the moment, JSword will not re-install. Later it will, if the remote version is greater.
//            try {
//                if (Books.installed().getBook(initials) != null) {
//                    // Make the book unavailable.
//                    // This is normally done via listeners.
//                    Books.installed().removeBook(book)
//
//                    // Actually do the delete
//                    // This should be a call on installer.
//                    book.driver.delete(book)
//                }
//            } catch (ex: BookException) {
//                ex.printStackTrace()
//            }

            try {
                // Now install it. Note this is a background task.
                installer.install(book)
            } catch (ex: InstallException) {
                ex.printStackTrace()
            }

        }
    }

    /**
     * A simple BookFilter that looks for a Bible by name.
     */
    internal class MyBookFilter(private val name: String) : BookFilter {

        override fun test(bk: Book): Boolean {
            return bk.initials.equals(name)
        }
    }

    /**
     * A simple BooksListener that actually does nothing.
     */
    internal class MyBooksListener : BooksListener {
        /*
         * (non-Javadoc)
         *
         * @see
         * org.crosswire.jsword.book.BooksListener#bookAdded(org.crosswire.jsword
         * .book.BooksEvent)
         */
        override fun bookAdded(ev: BooksEvent) {
            println("Added book: $ev")
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * org.crosswire.jsword.book.BooksListener#bookRemoved(org.crosswire
         * .jsword.book.BooksEvent)
         */
        override fun bookRemoved(ev: BooksEvent) {
            println("Removed book: $ev")
        }
    }

    companion object {
        /**
         * The name of a Bible to find
         */
        private const val BIBLE_NAME = "ESV2011"

        /**
         * Quick Demo
         * @param args
         *
         * @throws NoSuchKeyException
         * @throws BookException
         * @throws SAXException
         * @throws TransformerException
         */
        @JvmStatic
        @Throws(BookException::class, NoSuchKeyException::class, TransformerException::class, SAXException::class)
        fun main(args: Array<String>) {
            val examples = ApiExamples()

            examples.installBook()
            println("The plain text of Gen 1:1 is " + examples.getPlainText(BIBLE_NAME, "Gen 1:1"))
//            println("The html text of Gen 1:1 is " + examples.readStyledText(BIBLE_NAME, "Gen 1:1", 100))
            examples.readDictionary()
            examples.search()
//            examples.rankedSearch()
//            examples.searchAndShow()
        }
    }
}
