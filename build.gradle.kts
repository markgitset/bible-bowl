import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
    application
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "12"
        useIR = true
    }
}

tasks.compileJava {
    options.release.set(12)
}

application {
    mainClass.set("net.markdrew.biblebowl.MainKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.crosswire:jsword:2.1-SNAPSHOT")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("io.github.microutils:kotlin-logging:2.0.11")
    implementation("org.apache.lucene:lucene-analyzers-common:8.9.0")
    implementation("net.markdrew:chupacabra-core:1.0-beta")
    implementation("org.apache.opennlp:opennlp-tools:1.9.3")
    implementation("org.apache.lucene:lucene-core:8.9.0")
    implementation("com.robrua.nlp:easy-bert:1.0.3")
//    implementation("org.tensorflow:tensorflow:1.13.1")
    implementation("com.robrua.nlp.models:easy-bert-uncased-L-12-H-768-A-12:1.0.0") // com/robrua/nlp/easy-bert/bert-uncased-L-12-H-768-A-12
    implementation("com.robrua.nlp.models:easy-bert-cased-L-12-H-768-A-12:1.0.0") // com/robrua/nlp/easy-bert/bert-cased-L-12-H-768-A-12
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    implementation("org.apache.poi:poi-ooxml:5.0.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}
