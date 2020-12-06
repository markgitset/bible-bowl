plugins {
    application
    kotlin("jvm") version "1.4.20"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "12"
    }
}

application {
    mainClassName = "net.markdrew.biblebowl.MainKt"
}

dependencies {
    compile(kotlin("stdlib"))
    compile("com.squareup.retrofit2:retrofit:2.3.0")
    compile("com.squareup.retrofit2:converter-gson:2.3.0")
    compile("org.crosswire:jsword:2.1-SNAPSHOT")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.github.microutils:kotlin-logging:1.5.4")
}

repositories {
    jcenter()
    mavenLocal()
}
