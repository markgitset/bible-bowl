# BibleBowl

BibleBowl is a Kotlin-based Command-Line Interface (CLI) application and library for preparing resources and indices for the **Texas Bible Bowl**.

It provides tools to download Bible passages (using the ESV API), process the text, and generate a variety of study materials such as:
- Comprehensive indices (Names, Numbers, Unique Words, Phrases).
- Cram materials (Headings, Verses, Fill-in-the-blanks).
- Flashcards and Kahoot-compatible output.
- Practice test questions for different Bible Bowl rounds (e.g., Quotes, Events, Find the Verse).

## Prerequisites

- **Java Development Kit (JDK):** Version 17 or higher is recommended.
- **Gradle:** The project uses the Gradle wrapper (`gradlew`), so a local Gradle installation is not strictly necessary.

## Building and Running

This project uses Gradle. You can build and run it via the Gradle Wrapper:

### Build the project

```bash
./gradlew build
```

### Run the CLI

To run the application and see the available options:

```bash
./gradlew run --args="--help"
```

## Command-Line Interface Usage

The CLI is the main entry point for the application.

```bash
Usage: biblebowl [<options>] [STUDY_SET]
```

### Arguments:
- `[STUDY_SET]`: Optional name of the study set to use (e.g., specific books or portions of the Bible being studied). Defaults to the standard current study set.

### Options:
- `--force-download, -f`: Force download and re-index the study set, even if local data exists. (default: false)
- `--data-dir, -d <path>`: Directory where processed Bible Bowl data is stored.
- `--raw-data-dir, -r <path>`: Directory where raw downloaded data is stored.
- `--products-dir, -p <path>`: Directory where output products (indices, cram materials, practice tests) are saved.
- `-h, --help`: Show the help message and exit.

## Example

To generate resources for the default study set, simply run:

```bash
./gradlew run
```

The CLI will download (if necessary) and index the text, then generate study resources in the `products` directory located within your `~/.tbb` home directory setup.

## Project Structure

- `src/main/kotlin/net/markdrew/biblebowl/cli`: Contains the main CLI entry point using `clikt`.
- `src/main/kotlin/net/markdrew/biblebowl/ws`: Web services for downloading passages via the ESV API.
- `src/main/kotlin/net/markdrew/biblebowl/generate`: Logic for creating the different outputs (indices, practice questions, Kahoots, text files).
- `src/main/kotlin/net/markdrew/biblebowl/analysis`: Tools for linguistic and textual analysis of passages (tokenizing, extracting names/numbers, unique words).
