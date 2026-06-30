plugins {
    id("com.palantir.git-version") version "5.0.0"
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    application
    id("org.jetbrains.dokka") version "2.2.0"
}

// set the project version from tags and commits in Git repository
@Suppress("UNCHECKED_CAST")
val gitVersion = extra["gitVersion"] as groovy.lang.Closure<String>
version = gitVersion()

kotlin {
    val jvmVersion = project.findProperty("jvmVersion") as String
    jvmToolchain(jvmVersion.toInt())
}

application {
//    mainClass.set("net.markdrew.biblebowl.MainKt")
    mainClass.set("net.markdrew.biblebowl.cli.BibleBowlCliKt")
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.github.crosswire:jsword:2.1")
    implementation("ch.qos.logback:logback-classic:1.5.36")
    implementation("io.github.oshai:kotlin-logging:8.0.4")
    // chupacabra transitively drags in the old com.github.ajalt:clikt:2.6.0 (different groupId, so
    // Gradle won't dedupe it against our clikt 5.x). Its packages shadow clikt 5's and break option
    // resolution, so exclude it. (v0.2.0 would let us drop this, but it isn't buildable on JitPack
    // yet — its Gradle needs JVM 17+ while JitPack's default builder runs JVM 8; needs a jitpack.yml.)
    implementation("com.github.markgitset:chupacabra:v0.1.0") {
        exclude(group = "com.github.ajalt", module = "clikt")
    }
    implementation("org.apache.poi:poi-ooxml:5.5.1")
    implementation("com.cognitect:transit-java:1.1.403")
    implementation("org.apache.commons:commons-csv:1.14.1") // https://mvnrepository.com/artifact/org.apache.commons/commons-csv
    implementation("org.docx4j:docx4j-JAXB-ReferenceImpl:17.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
    implementation("com.github.ajalt.clikt:clikt:5.1.0")
    implementation("com.googlecode.lanterna:lanterna:3.1.2") // full-screen TUI for the annotation validator
    testImplementation("io.kotest:kotest-runner-junit5:6.2.1")
    testImplementation("io.kotest:kotest-assertions-core:6.2.1")
    // Kotest 6 folded the standalone datatest module into kotest-framework-engine (pulled in
    // transitively by the runner), so `io.kotest.datatest.withData` no longer needs its own dep.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    // Mirror the application's native-access grant so JNA (pulled in transitively) doesn't emit the
    // restricted-method warning under JDK 25 — and won't hard-fail when that becomes an error.
    jvmArgs("--enable-native-access=ALL-UNNAMED")
    // Forward the snapshot-regeneration flag so TextWriterFidelityTest can overwrite committed
    // baselines when invoked as `./gradlew test -Dregenerate-baseline-texts=true`.
    System.getProperty("regenerate-baseline-texts")?.let { systemProperty("regenerate-baseline-texts", it) }
    // Kotest runs its specs regardless of Gradle's `--tests` filter, so the filter can match zero
    // descriptors at the JUnit-Platform level (e.g. `--tests '*TextWriterFidelityTest'` while
    // regenerating baselines) and fail the build even though the spec ran. Don't treat that as an error.
    filter { isFailOnNoMatchingTests = false }
    // The fidelity test compares multi-megabyte source files; assertion diffs can blow the
    // default heap. 2GB is comfortable headroom.
    maxHeapSize = "2g"
}
