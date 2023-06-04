import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
    application
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "19"
    }
}

tasks.compileJava {
    options.release.set(19)
}

application {
    mainClass.set("net.markdrew.biblebowl.MainKt")
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.crosswire:jsword:2.1")
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("io.github.microutils:kotlin-logging:2.1.23")
    implementation("org.apache.lucene:lucene-analyzers-common:8.11.2")
    implementation("net.markdrew:chupacabra-core:1.0-beta")
    implementation("org.apache.opennlp:opennlp-tools:1.9.4")
    implementation("org.apache.lucene:lucene-core:9.3.0")
    implementation("com.robrua.nlp:easy-bert:1.0.3")
//    implementation("org.tensorflow:tensorflow:1.13.1")
    implementation("com.robrua.nlp.models:easy-bert-uncased-L-12-H-768-A-12:1.0.0") // com/robrua/nlp/easy-bert/bert-uncased-L-12-H-768-A-12
    implementation("com.robrua.nlp.models:easy-bert-cased-L-12-H-768-A-12:1.0.0") // com/robrua/nlp/easy-bert/bert-cased-L-12-H-768-A-12
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    implementation("org.apache.poi:poi-ooxml:5.2.2")
    implementation("org.apache.commons:commons-csv:1.10.0") // https://mvnrepository.com/artifact/org.apache.commons/commons-csv
//    implementation("org.docx4j:docx4j-bundle:11.4.9")
    implementation("org.docx4j:docx4j-JAXB-ReferenceImpl:11.4.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
