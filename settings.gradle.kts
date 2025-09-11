pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "SpiritAtlas"
include(":app")
include(":core:ui")
include(":core:common")
include(":core:numerology")
include(":core:astro")
include(":core:humandesign")
include(":domain")
include(":data")
include(":feature:home")
include(":feature:profile")
include(":feature:consent")
include(":feature:tantric")
include(":feature:compatibility")
include(":core:ayurveda")
