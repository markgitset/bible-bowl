# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

BibleBowl is a Kotlin/JVM CLI + library that prepares study materials for the Texas Bible Bowl. It downloads ESV passages, indexes them into a single annotated text model, then generates indices, flashcards, Kahoot sheets, practice tests, and typeset Bibles (Typst is the primary format; LaTeX / DOCX are legacy and maintained but no longer extended).

## Build, run, test

JDK 23 toolchain is configured in `gradle.properties` (`jvmVersion = 23`, `kotlinVersion = 2.3.10`). Use the wrapper.

- Build: `./gradlew build`
- Build distribution: `./gradlew installDist`
- The CLI is subcommand-based (`text`, `annotate`, `indices`, `flashcards`, `practice`, `all`); the study set is the `-s`/`--study-set` option (default from config) and each subcommand exposes only the options relevant to it. `all` generates every resource with defaults.
- Run everything for the default study set: `./gradlew run --args="all"`
- Run with args: `./gradlew run --args="all -s luke --force-download"` (subcommand plus that subcommand's flags)
- Run built distribution binary: `./build/install/bible-bowl/bin/bible-bowl text -s acts`
- Generate specific indices: `./build/install/bible-bowl/bin/bible-bowl indices names-index numbers-index` (positional resource slugs; no slugs = all in that category; `--list` to discover slugs)
- Run a subcommand with verbose logs: `./build/install/bible-bowl/bin/bible-bowl all -s acts -v`
- All tests: `./gradlew test`
- Regenerate text snapshot baselines: `./gradlew test -Dregenerate-baseline-texts=true`
- Single test class: `./gradlew test --tests 'net.markdrew.biblebowl.analysis.FindNamesKtTest'`
- Single test method: `./gradlew test --tests 'net.markdrew.biblebowl.model.VerseRefKtTest.<methodName>'`

Tests use JUnit 5 (`useJUnitPlatform()`) and mockito-kotlin. Text writers support bypassing expensive PDF generation steps (pdflatex, typst, libreoffice) in tests if the system property `skip-pdf-generation` is set to `true`.

The `chupacabra` dependency is resolved from JitPack (configured in `settings.gradle.kts`).

## Required configuration

- `ESV_API_TOKEN` env var must be set to download new study sets or the dynamic copyright notice via `EsvClient` / `EsvService` (see `ws/EsvClient.kt`). Without it, requests to api.esv.org go unauthenticated and will fail. Already-indexed data under `dataDir` can be regenerated, and the cached copyright details at `<rawDataDir>/copyright.txt` will be used if available.
- Default I/O layout lives under `~/.tbb/`: `~/.tbb/raw-data` (ESV JSON), `~/.tbb/data` (indexed `StudyData` files), `~/.tbb/products` (generated artifacts). Each can be overridden per-subcommand with `--raw-data-dir`, `--data-dir`, `--products-dir`/`-p`. The repo also has its own `data/`, `raw-data/`, `products/` trees that some legacy code paths default to — prefer the `~/.tbb` layout via the CLI.
- **Properties Configuration**: Default CLI values for the study set (`default-study-set`) and footer date (`test-date`) can be configured in a `bible-bowl.properties` file. Resolution order is: `./bible-bowl.properties` (CWD) -> `~/.tbb/bible-bowl.properties` (User global) -> hardcoded code defaults. Option `--test-date` or arguments on command line will override these values at runtime.

## Entry points

- **Active CLI**: `src/main/kotlin/net/markdrew/biblebowl/cli/BibleBowlCli.kt` — `mainClass` in `build.gradle.kts` points here (`BibleBowlCliKt`). Built on clikt as a subcommand tree: a no-op root `BibleBowlCli` with six subcommands. `BaseCommand` carries the shared `-s`/`--study-set` option + I/O dirs + global flags and the data-loading helpers; `GeneratingCommand` adds `--products-dir` and `runResources(...)`; `SelectingCommand` adds the positional resource-slug selection + `--list` for the category subcommands (`indices`/`flashcards`/`practice`). `text` and `all` extend `GeneratingCommand` directly; `annotate` extends `BaseCommand` (no products). Resource definitions live in `cli/StudyResources.kt` (one `StudyResource` per generatable artifact, tagged with a `ResourceCategory`); CLI defaults resolve through `cli/CliConfig.kt`.
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

- `generate/text/` — annotated Bible text in multiple formats. `AnnotatedDoc` + `Annotation` are the abstraction layer over `StudyData` ranges (built by `BibleAnnotationPipeline`, walked by `BibleTextWalker` which dispatches format-agnostic events to a `BibleTextHandler`); format-specific writers live in `typst/`, `latex/`, `docx/`. **Typst is the actively-developed format; LaTeX and DOCX are effectively deprecated** — kept working for now, but new text features land in Typst only (other writers should reject an unsupported option via `BibleTextWriter.supports(...)` or the feature guard in `BibleTextPipeline.render`, which skips-with-message rather than aborting).
  - **Configuration model** (see `TextPreset.kt`): three orthogonal option groups — `FeatureOptions` (content emphasis: `underlineUniqueWords`, `customHighlights`, `verseOnNewLine`, `smallCaps`), `LayoutOptions` (page structure: `fontSize`, `twoColumns`, `chapterBreaksPage`, `useHeadingsForChapters`, `testDate`), and `StyleId` (typography family `TBB`/`MARKS`, resolved per-format to a concrete `TypstStyle`/`DocxStyle`). A `TextPreset` (`tbb`, `marks`, `plain`) bundles a coherent default of all three; `TextOverrides` carries tri-state per-field overrides (`null` = inherit) applied via `TextPreset.resolve(...)`.
  - **`generateBibleTexts` has two modes**: with no `--preset`/override flags it emits the curated *pack* (plain/unique/full × tbb/marks × formats); naming a preset or setting any override switches to *single* mode (one resolved `ResolvedTextConfig` → one document per format). CLI exposes `--preset` plus tri-state flags (`--style`, `--font-size`, `--[no-]two-columns`, `--[no-]inline-chapter-labels`, `--[no-]page-break-chapters`, `--[no-]underline-unique`, `--[no-]highlight`, `--[no-]verse-per-line`). Note `--inline-chapter-labels` is the inverse of the internal `LayoutOptions.useHeadingsForChapters`.
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
- **Logging**: Console log output is restricted to `WARN`/`ERROR` level by default. Detailed logs (like cache loading events) are written to `~/.tbb/bible-bowl.log` at `DEBUG` level. To show detailed logs on the console, run with the `--verbose` / `-v` flag.
- **Caching**: `AnnotationStore` manages sidecar caches (`~/.tbb/data/<studySet>`). The cache fingerprint (`codeVersion` header) is calculated using a deterministic bytecode-level classes walk (using normalized relative package paths) in both raw classes directory and packaged JARs, ensuring that switching execution environments doesn't invalidate cache.

## TODO.md

`TODO.md` tracks open feature work (e.g. Round 2/3 question banks, footnote indexing, TF-IDF flashcards, docx style fixes). It's the closest thing to a roadmap; check it before proposing new generators to avoid duplicating planned work.
