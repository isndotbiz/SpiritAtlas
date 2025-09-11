plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.versions)
    alias(libs.plugins.dependencycheck)
}

// Detekt configuration
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt/detekt.yml")
    baseline = file("$projectDir/config/detekt/baseline.xml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

// ktlint configuration
ktlint {
    version.set("1.0.1")
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

// JaCoCo configuration for test coverage
jacoco {
    toolVersion = "0.8.8"
}

// Task to generate unified test coverage report
tasks.register("testCoverageReport", JacocoReport::class) {
    group = "verification"
    description = "Generate unified test coverage report for all modules"
    
    val testTasksToInclude = subprojects.mapNotNull { subproject ->
        subproject.tasks.findByName("testDebugUnitTest") ?: subproject.tasks.findByName("test")
    }
    
    dependsOn(testTasksToInclude)
    
    executionData.setFrom(
        fileTree(rootDir) {
            include("**/jacoco/*.exec", "**/outputs/unit_test_code_coverage/**/testDebugUnitTest.exec")
        }
    )
    
    classDirectories.setFrom(
        files(subprojects.map { project ->
            fileTree("${project.layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
                exclude(
                    "**/R.class",
                    "**/R\$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/*Test*.*",
                    "android/**/*.*"
                )
            }
        })
    )
    
    sourceDirectories.setFrom(
        files(subprojects.map { "${it.projectDir}/src/main/java" })
    )
    
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }
}

// Security and dependency monitoring configuration
dependencyCheck {
    failBuildOnCVSS = 7.0f  // Fail on high/critical vulnerabilities
    suppressionFile = "owasp-suppressions.xml"
    format = "ALL"
}

// Dependency version updates configuration
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return !isStable
}

// Custom task to check for security vulnerabilities
tasks.register("securityAudit") {
    group = "verification"
    description = "Run comprehensive security audit including dependency check and detekt"
    dependsOn("dependencyCheckAnalyze", "detekt")
    doLast {
        println("✅ Security audit completed successfully")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
