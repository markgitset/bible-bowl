package net.markdrew.biblebowl.cli

import net.markdrew.biblebowl.defaultRawDataPath
import net.markdrew.biblebowl.analysis.AnnotationStore
import net.markdrew.biblebowl.analysis.OneTimeWordsSource
import net.markdrew.biblebowl.analysis.WordList
import net.markdrew.biblebowl.cli.ResourceCategory.FLASHCARDS
import net.markdrew.biblebowl.cli.ResourceCategory.INDICES
import net.markdrew.biblebowl.cli.ResourceCategory.PRACTICE
import net.markdrew.biblebowl.cli.ResourceCategory.TEXT
import net.markdrew.biblebowl.flashcards.cram.writeCramHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramOneTimeWords
import net.markdrew.biblebowl.flashcards.cram.writeCramReverseHeadings
import net.markdrew.biblebowl.flashcards.cram.writeCramVerses
import net.markdrew.biblebowl.generate.indices.writeFullIndex
import net.markdrew.biblebowl.generate.indices.writeHeadingsPdf
import net.markdrew.biblebowl.generate.indices.writeHeadingsText
import net.markdrew.biblebowl.generate.indices.writeNamesIndex
import net.markdrew.biblebowl.generate.indices.writeNonLocalPhrasesIndex
import net.markdrew.biblebowl.generate.indices.writeNumbersIndex
import net.markdrew.biblebowl.generate.indices.writeOneTimeWordsHomework
import net.markdrew.biblebowl.generate.indices.writeOneTimeWordsIndex
import net.markdrew.biblebowl.generate.indices.writeWordListIndex
import net.markdrew.biblebowl.generate.practice.Round
import net.markdrew.biblebowl.generate.practice.practiceTest
import net.markdrew.biblebowl.generate.practice.writeFullSet
import net.markdrew.biblebowl.generate.practice.writeRound1VerseFind
import net.markdrew.biblebowl.generate.practice.writeRound4Quotes
import net.markdrew.biblebowl.generate.practice.writeRound5Events
import net.markdrew.biblebowl.generate.text.OutputFormat
import net.markdrew.biblebowl.generate.text.TextOverrides
import net.markdrew.biblebowl.generate.text.Typst
import net.markdrew.biblebowl.generate.text.generateBibleTexts
import net.markdrew.biblebowl.model.StudyData
import net.markdrew.biblebowl.typst.writeTypstFlashCards
import java.nio.file.Path
import java.time.LocalDate

/** Top-level grouping of study resources. The lowercased [name] doubles as the `-r` category token. */
enum class ResourceCategory { TEXT, INDICES, FLASHCARDS, PRACTICE }

/**
 * One generatable study resource: a stable [slug] (selectable via `-r`), its [category], a
 * human-readable [label] (shown by `--list`/the picker), and the [generate] action that writes it.
 */
class StudyResource(
    val slug: String,
    val category: ResourceCategory,
    val label: String,
    val generate: (StudyData, AnnotationStore, Path) -> Unit,
)

/**
 * Ranges assigned to [wordList] in the one unified word-list resolution for [data] (overrides + precedence
 * applied), in ascending offset order. Names and numbers are now ordinary categories, so their indices read
 * from here exactly like the other word-list indices.
 */
private fun categoryRanges(data: StudyData, store: AnnotationStore, wordList: WordList): List<IntRange> =
    store.categoryResolution(data.studySet)
        .filterValues { it == wordList.token }.keys.toList()

/** A category-specific word-list index: its [wordList] plus the singular/plural labels the index uses. */
private class WordListSpec(val wordList: WordList, val slug: String, val singular: String, val plural: String)

private val WORD_LISTS: List<WordListSpec> = listOf(
    WordListSpec(WordList.ANGELS_DEMONS, "wordlist-angels-demons", "Angel or Demon", "Angels or Demons"),
    WordListSpec(WordList.ANIMALS, "wordlist-animals", "Animal", "Animals"),
    WordListSpec(WordList.BODY_PARTS, "wordlist-body-parts", "Body Part", "Body Parts"),
    WordListSpec(WordList.COLORS, "wordlist-colors", "Color", "Colors"),
    WordListSpec(WordList.FOODS, "wordlist-foods", "Food", "Foods"),
    WordListSpec(WordList.MEN, "wordlist-men", "Man", "Men"),
    WordListSpec(WordList.WOMEN, "wordlist-women", "Woman", "Women"),
    WordListSpec(WordList.PLACES, "wordlist-places", "Place", "Places"),
)

/**
 * The full registry of generatable resources, in generation order. Built per-run so the `text`
 * resource can close over the CLI-resolved [formats] and [testDate].
 *
 * Kahoot, monikers, event-card, and Anki generators exist in the codebase but are intentionally not
 * registered here (they aren't part of the standard year set today); adding one is a single `+=`.
 */
fun studyResources(
    formats: Set<OutputFormat> = setOf(Typst),
    testDate: LocalDate = CliConfig.defaultTestDate,
    rawDataDir: Path = defaultRawDataPath,
    forceDownload: Boolean = false,
    preset: String? = null,
    overrides: TextOverrides = TextOverrides(),
): List<StudyResource> = buildList {
    // TEXT
    add(StudyResource("text", TEXT, "Bible text variants") { data, store, dir ->
        generateBibleTexts(data, testDate, dir, formats, store, rawDataDir, forceDownload, preset, overrides)
    })

    // INDICES
    add(StudyResource("unique-words-index", INDICES, "One-time words index") { d, store, p ->
        writeOneTimeWordsIndex(d, p, store.rangeList(OneTimeWordsSource))
    })
    add(StudyResource("unique-words-homework", INDICES, "One-time words homework") { d, store, p ->
        writeOneTimeWordsHomework(d, p, store.rangeList(OneTimeWordsSource))
    })
    add(StudyResource("full-index", INDICES, "Full word index") { d, _, p -> writeFullIndex(d, productsDir = p) })
    add(StudyResource("numbers-index", INDICES, "Numbers index") { d, store, p ->
        writeNumbersIndex(d, productsDir = p, numberRanges = categoryRanges(d, store, WordList.NUMBERS))
    })
    add(StudyResource("names-index", INDICES, "Names index") { d, store, p ->
        writeNamesIndex(d, p, categoryRanges(d, store, WordList.OTHER))
    })
    add(StudyResource("phrases-index", INDICES, "Non-local phrases index") { d, _, p -> writeNonLocalPhrasesIndex(d, p) })
    add(StudyResource("headings-index", INDICES, "Headings index (PDF)") { d, _, p -> writeHeadingsPdf(d, p) })
    add(StudyResource("headings-text", INDICES, "Headings list (text)") { d, _, p -> writeHeadingsText(d, p) })
    for (spec in WORD_LISTS) {
        add(StudyResource(spec.slug, INDICES, "${spec.plural} word-list index") { d, store, p ->
            writeWordListIndex(p, d, spec.wordList, spec.singular, spec.plural, store)
        })
    }

    // FLASHCARDS
    add(StudyResource("cram-verses", FLASHCARDS, "Cram verses deck") { d, _, p -> writeCramVerses(d, p) })
    add(StudyResource("cram-headings", FLASHCARDS, "Cram headings deck") { d, _, p -> writeCramHeadings(d, p) })
    add(StudyResource("cram-reverse-headings", FLASHCARDS, "Cram reverse-headings deck") { d, _, p -> writeCramReverseHeadings(d, p) })
    add(StudyResource("cram-one-time-words", FLASHCARDS, "Cram one-time-words deck") { d, store, p ->
        writeCramOneTimeWords(d, store.rangeList(OneTimeWordsSource), p)
    })
    add(StudyResource("typst-cards", FLASHCARDS, "Typst heading flashcards (PDF)") { d, _, p -> writeTypstFlashCards(d, p) })

    // PRACTICE
    add(StudyResource("practice", PRACTICE, "Practice test set (rounds 1, 4, 5)") { d, _, p ->
        writeFullSet(d, p) { content, seed, dir ->
            writeRound1VerseFind(practiceTest(Round.FIND_THE_VERSE, content, seed, numQuestions = 20), dir)
            writeRound4Quotes(practiceTest(Round.QUOTES, content, seed), dir)
            writeRound5Events(practiceTest(Round.EVENTS, content, seed), dir)
        }
    })
}

/**
 * Resolves user `-R` [tokens] against the [all] registry. Each token matches a [ResourceCategory]
 * name (case-insensitive — expands to every resource in it) or a resource [StudyResource.slug].
 * Returns the selected resources in registry order, de-duplicated. An empty [tokens] selects all.
 *
 * @throws IllegalArgumentException naming the bad token if it matches neither a category nor a slug
 */
/** Renders [resources] as aligned `slug — label` lines for a subcommand's `--list` output. */
fun formatResourceList(resources: List<StudyResource>): String =
    resources.joinToString("\n") { "  ${it.slug.padEnd(28)} ${it.label}" }

fun resolveSelection(tokens: List<String>, all: List<StudyResource>): List<StudyResource> {
    if (tokens.isEmpty()) return all
    val bySlug: Map<String, StudyResource> = all.associateBy { it.slug }
    val categoriesByName: Map<String, ResourceCategory> = ResourceCategory.entries.associateBy { it.name.lowercase() }
    val selected = HashSet<StudyResource>()
    for (raw in tokens) {
        val token = raw.lowercase()
        val category = categoriesByName[token]
        when {
            category != null -> selected.addAll(all.filter { it.category == category })
            token in bySlug -> selected.add(bySlug.getValue(token))
            else -> {
                val known = (categoriesByName.keys + bySlug.keys).sorted().joinToString(", ")
                throw IllegalArgumentException("Unknown resource or category '$raw'. Choose from: $known")
            }
        }
    }
    return all.filter { it in selected }
}
