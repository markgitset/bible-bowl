package net.markdrew.biblebowl.cli

import net.markdrew.biblebowl.defaultTbbPath
import java.time.LocalDate
import kotlin.io.path.exists
import kotlin.io.path.inputStream

/**
 * Resolved CLI defaults, sourced from `bible-bowl.properties` with code fallbacks.
 *
 * Resolution order (later wins): `~/.tbb/bible-bowl.properties` (user global), then
 * `./bible-bowl.properties` (CWD). Command-line options override these at runtime.
 */
object CliConfig {

    private const val FALLBACK_STUDY_SET = "acts"
    private val FALLBACK_TEST_DATE = LocalDate.of(2026, 3, 28)

    private val props: java.util.Properties by lazy {
        val props = java.util.Properties()
        // 1. User config: ~/.tbb/bible-bowl.properties
        val userConfig = defaultTbbPath.resolve("bible-bowl.properties")
        if (userConfig.exists()) {
            try {
                userConfig.inputStream().use { props.load(it) }
            } catch (e: Exception) {
                System.err.println("Warning: failed to load config from $userConfig: ${e.message}")
            }
        }
        // 2. CWD config: ./bible-bowl.properties
        val localConfig = java.nio.file.Path.of("bible-bowl.properties")
        if (localConfig.exists()) {
            try {
                localConfig.inputStream().use { props.load(it) }
            } catch (e: Exception) {
                System.err.println("Warning: failed to load config from $localConfig: ${e.message}")
            }
        }
        props
    }

    /** Default study set name when none is given on the command line. */
    val defaultStudySetName: String get() = props.getProperty("default-study-set", FALLBACK_STUDY_SET)

    /** Default cover/footer date stamped on generated text when `--test-date` is omitted. */
    val defaultTestDate: LocalDate by lazy {
        val dateStr = props.getProperty("test-date") ?: return@lazy FALLBACK_TEST_DATE
        try {
            LocalDate.parse(dateStr)
        } catch (e: Exception) {
            System.err.println("Warning: invalid test-date format in config: $dateStr. Expected YYYY-MM-DD.")
            FALLBACK_TEST_DATE
        }
    }
}
