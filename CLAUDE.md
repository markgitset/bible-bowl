# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

BibleBowl is a Kotlin/JVM CLI + library that prepares study materials for the Texas Bible Bowl. It downloads ESV passages, indexes them into a single annotated text model, then generates indices, flashcards, Kahoot sheets, practice tests, and typeset Bibles (LaTeX / Typst / DOCX / ODT).

## Build, run, test

JDK 23 toolchain is configured in `gradle.properties` (`jvmVersion = 23`, `kotlinVersion = 2.3.10`). Use the wrapper.

- Build: `./gradlew build`
- Run full pipeline against the default study set (Joshua/Judges/Ruth): `./gradlew run`
- Run with args: `./gradlew run --args="luke --force-download"` (study set name, plus any flags from `BibleBowlCli`)
- All tests: `./gradlew test`
- Single test class: `./gradlew test --tests 'net.markdrew.biblebowl.analysis.FindNamesKtTest'`
- Single test method: `./gradlew test --tests 'net.markdrew.biblebowl.model.VerseRefKtTest.<methodName>'`

Tests use JUnit 5 (`useJUnitPlatform()`) and mockito-kotlin.

The `chupacabra` dependency is resolved from JitPack (configured in `settings.gradle.kts`).

## Required configuration

- `ESV_API_TOKEN` env var must be set to download new study sets via `EsvClient` / `EsvService` (see `ws/EsvClient.kt`). Without it, requests to api.esv.org go unauthenticated and will fail. Already-indexed data under `dataDir` can be regenerated without a token.
- Default I/O layout lives under `~/.tbb/`: `~/.tbb/raw-data` (ESV JSON), `~/.tbb/data` (indexed `StudyData` files), `~/.tbb/products` (generated artifacts). Each can be overridden with `-r`, `-d`, `-p`. The repo also has its own `data/`, `raw-data/`, `products/` trees that some legacy code paths default to — prefer the `~/.tbb` layout via the CLI.

## Entry points

- **Active CLI**: `src/main/kotlin/net/markdrew/biblebowl/cli/BibleBowlCli.kt` — `mainClass` in `build.gradle.kts` points here (`BibleBowlCliKt`). Built on clikt.
- **Legacy `main`**: `src/main/kotlin/net/markdrew/biblebowl/biblebowl.kt` has an older non-clikt `main` that hard-codes the repo-relative `data/`/`raw-data/` dirs and a slightly different generator set. Don't add new behavior there — extend `BibleBowlCli` instead.

## Core architecture

The whole pipeline pivots on **`StudyData`** (`model/StudyData.kt`):

```
StudySet → EsvClient (ESV API) → Passage stream
                                       ↓
                                 EsvIndexer
                                       ↓
                              StudyData (in-memory)
                                       ↓
                           writeData() / readData()  ←→  files in dataDir
                                       ↓
              generate/* + flashcards/* + typst/*  →  productsDir/<simpleName>/...
```

`StudyData` is one big `text: String` plus a set of `DisjointRangeMap` / `DisjointRangeSet` annotations over character offsets — verses, headings, chapters, paragraphs (with poetry indent counts), poetry blocks, and footnotes. Most generators consume these range maps rather than re-parsing text. The `chupacabra` library provides the disjoint-range collections; understand it before touching anything that joins or slices ranges.

`StudySet` (`model/StudySet.kt`) describes which Bible chapters are in scope and carries a `simpleName` that becomes the per-set output directory (e.g. `josh-judg-ruth`, `luke`). `StandardStudySet` enums the known sets and provides lenient name parsing (default: `JOSHUA_JUDGES_RUTH`). `simpleName` is validated to lowercase letters/digits/hyphens — preserve that when adding new sets.

### Indexing path

`ws/EsvClient.kt` fetches one chapter at a time from `api.esv.org/v3/passage/text/`. `ws/EsvIndexer.kt` parses ESV's text-format markup (headings, paragraph indents, poetry, footnotes, chapter/verse markers) and builds the offset annotations. The `BibleBowlCli` flow is: try `StudyData.readData(...)`; on miss, download + index + `writeData(...)`. `--force-download` / `-f` re-downloads and re-indexes.

### Generator families

All generators take `(StudyData, productsDir)` and write to `productsDir/<simpleName>/<category>/...`. Use `fileForProduct()` in `biblebowl.kt` for the standard naming.

- `generate/text/` — annotated Bible text in multiple formats. `AnnotatedDoc` + `Annotation` are the abstraction layer over `StudyData` ranges; format-specific writers live in `latex/`, `typst/`, `docx/`. `TextOptions` toggles highlighting (names, numbers, unique words, red-letter, etc.).
- `generate/indices/` — `FullIndex`, `IndexOfNames`, `IndexOfNumbers`, `IndexOfHeadings`, `IndexOfUniqueWords`, `PhrasesIndex`, `IndexOfWordList` (driven by `analysis/WordList.kt` enum: animals, foods, body parts, men, women, places, …).
- `generate/practice/` — practice round generators driven by `Round` enum + `PracticeTest`. **Implemented**: Round 1 (Find the Verse), Round 4 (Quotes), Round 5 (Events). Round 2/3/Power are listed in `TODO.md` and not generated.
- `generate/kahoot/` — Kahoot-format xlsx (Apache POI).
- `flashcards/cram/` — Cram.com-style flashcard sets (verses, headings, reverse headings, one-time words, name blanks, etc.).
- `typst/writeTypstFlashCards` — Typst flashcards.

### Analysis layer

`analysis/` is the linguistic toolkit consumed by indices/practice generators:

- `FindNames.kt` — OpenNLP-based name extraction; allows hyphens (e.g. "Tubal-cain"); supports multi-word names.
- `FindNumbers.kt` — number extraction (cardinal/ordinal/written-out, including fractions and "three and a half").
- `OneTimeWords.kt` — Lucene-backed one-time-word detection.
- `PhraseAnalysis.kt` — recurring/non-local phrase mining.
- `WordList.kt` — curated word categories used by `IndexOfWordList`.
- `FullTokenizer.kt`, `Bert.kt`, `OneTimeWordsBert.kt` — tokenizing utilities; the BERT bits are currently dormant (deps commented out in `build.gradle.kts`).

## Conventions worth knowing

- All output paths derive from `studySet.simpleName`. If you add a study set, that name becomes a directory name everywhere — keep it short and stable.
- `StudyData` is the single source of truth at runtime. Don't re-tokenize or re-parse text inside generators; ask `StudyData` for verses/chapters/headings/paragraphs/poetry by character range.
- Character offsets are over the joined `StudyData.text` string, not per-chapter. `DisjointRangeMap` operations (`enclose`, `intersect`, `valuesIntersectedBy`, etc.) come from `chupacabra`.
- Many generators shell out for typesetting (e.g. `evince` to preview PDFs, LaTeX/Typst compilers must be installed for those output formats to actually produce PDFs).
- `editorconfig` enforces 4-space indent, 120-col lines, LF, no trailing newline.

## TODO.md

`TODO.md` tracks open feature work (e.g. Round 2/3 question banks, footnote indexing, TF-IDF flashcards, docx style fixes). It's the closest thing to a roadmap; check it before proposing new generators to avoid duplicating planned work.
