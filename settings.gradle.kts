import org.gradle.api.initialization.resolve.RepositoriesMode

rootProject.name = "bible-bowl"


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
    }
}