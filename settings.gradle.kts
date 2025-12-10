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

// Build cache configuration for maximum performance
buildCache {
    local {
        // Enable local build cache
        isEnabled = true

        // Store cache in project directory for better control
        directory = File(rootDir, ".gradle/build-cache")

        // Keep cache for 30 days to maximize reuse during development
        removeUnusedEntriesAfterDays = 30
    }

    // Remote build cache can be configured here for team collaboration
    // Recommended for CI/CD pipelines to share build artifacts across machines
    // remote<HttpBuildCache> {
    //     url = uri("https://your-cache-server.com/cache/")
    //     isPush = System.getenv("CI") == "true"
    //     credentials {
    //         username = System.getenv("BUILD_CACHE_USER")
    //         password = System.getenv("BUILD_CACHE_PASSWORD")
    //     }
    // }
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
include(":feature:onboarding")
include(":feature:settings")
include(":core:ayurveda")
