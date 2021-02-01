plugins {
    application
    kotlin("jvm") version "1.4.21"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "12"
    }
}

application {
    mainClass.set("net.markdrew.biblebowl.MainKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("org.crosswire:jsword:2.1-SNAPSHOT")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.5.4")
    implementation("org.apache.lucene:lucene-analyzers-common:8.7.0")
    implementation("net.markdrew:chupacabra-core:1.0-beta")
}

repositories {
    jcenter()
    mavenLocal()
}
