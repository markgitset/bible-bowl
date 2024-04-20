plugins {
    id("com.palantir.git-version") version "3.0.0"
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    application
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

// set the project version from tags and commits in Git repository
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

kotlin {
    val jvmVersion: String by project.properties
    jvmToolchain(jvmVersion.toInt())
}

application {
    mainClass.set("net.markdrew.biblebowl.MainKt")
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.github.crosswire:jsword:2.1")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.apache.lucene:lucene-analyzers-common:8.11.3")
    implementation("net.markdrew:chupacabra-core:1.0-beta")
    implementation("org.apache.opennlp:opennlp-tools:1.9.4")
    implementation("org.apache.lucene:lucene-core:9.10.0")
    implementation("com.robrua.nlp:easy-bert:1.0.3")
    implementation("com.robrua.nlp.models:easy-bert-uncased-L-12-H-768-A-12:1.0.0") // com/robrua/nlp/easy-bert/bert-uncased-L-12-H-768-A-12
    implementation("com.robrua.nlp.models:easy-bert-cased-L-12-H-768-A-12:1.0.0") // com/robrua/nlp/easy-bert/bert-cased-L-12-H-768-A-12
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.commons:commons-csv:1.10.0") // https://mvnrepository.com/artifact/org.apache.commons/commons-csv
//    implementation("org.docx4j:docx4j-bundle:11.4.9")
    implementation("org.docx4j:docx4j-JAXB-ReferenceImpl:11.4.11")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
