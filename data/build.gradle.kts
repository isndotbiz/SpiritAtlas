import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    jacoco
}

android {
    namespace = "com.spiritatlas.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        
        // BuildConfig fields for API configuration
        val localProps = Properties()
        val localPropsFile = rootProject.file("local.properties")
        if (localPropsFile.exists()) {
            localProps.load(localPropsFile.inputStream())
        }
        fun sanitize(raw: String?): String {
            val v = (raw ?: "").trim().trim('"')
            return v
        }
        // App-level API keys (built-in, optional)
        val openRouterKey = sanitize(localProps.getProperty("openrouter.api.key"))
        val geminiKey = sanitize(localProps.getProperty("gemini.api.key"))
        val groqKey = sanitize(localProps.getProperty("groq.api.key"))
        val ollamaRaw = sanitize(localProps.getProperty("ollama.base.url"))
        val ollamaUrl = if (ollamaRaw.isBlank()) "http://localhost:11434" else ollamaRaw

        // Build config fields
        buildConfigField("String", "OPENROUTER_API_KEY", "\"$openRouterKey\"")
        buildConfigField("String", "OPENROUTER_BASE_URL", "\"https://openrouter.ai/\"")
        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiKey\"")
        buildConfigField("String", "GROQ_API_KEY", "\"$groqKey\"")
        buildConfigField("String", "OLLAMA_BASE_URL", "\"$ollamaUrl\"")

        // User API keys stored in encrypted preferences (set via UI)
        // OpenAI, Claude, and other providers use user-provided keys
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }

    // Room schema export configuration
    sourceSets {
        getByName("main") {
            assets.srcDirs("src/schemas")
        }
    }
}

// Configure KSP for Room schema export
ksp {
    arg("room.schemaLocation", "$projectDir/src/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:common"))
    implementation(project(":core:numerology"))
    implementation(project(":core:astro"))
    implementation(project(":core:humandesign"))

    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.work.runtime)
    implementation(libs.hilt.work)
    
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    // Google Gemini AI
    implementation(libs.generativeai)

    // Image loading (for MemoryManager integration)
    implementation(libs.coil.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("com.google.truth:truth:1.1.5")

    // AndroidTest dependencies for integration tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("app.cash.turbine:turbine:1.0.0")
    androidTestImplementation("com.google.truth:truth:1.1.5")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    androidTestImplementation(project(":core:ayurveda"))
}

// JaCoCo configuration for Android module
jacoco {
    toolVersion = "0.8.12"
}

android {
    buildTypes {
        debug {
            enableUnitTestCoverage = true
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    group = "verification"
    description = "Generate JaCoCo coverage report"

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val fileFilter = listOf(
        "**/R.class", "**/R\$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*", "**/databinding/**", "**/generated/**",
        "**/*_Factory.*", "**/*_MembersInjector.*", "**/Hilt_*.*",
        "**/*Module.*", "**/*Component.*", "**/BR.class", "**/*_Impl.*"
    )

    val debugTree = fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    sourceDirectories.setFrom(files("${projectDir}/src/main/java"))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get().asFile) {
        include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        include("jacoco/testDebugUnitTest.exec")
    })
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn("jacocoTestReport")

    val fileFilter = listOf(
        "**/R.class", "**/R\$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*", "**/databinding/**", "**/generated/**",
        "**/*_Factory.*", "**/*_MembersInjector.*", "**/Hilt_*.*",
        "**/*Module.*", "**/*Component.*", "**/BR.class", "**/*_Impl.*"
    )

    val debugTree = fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get().asFile) {
        include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        include("jacoco/testDebugUnitTest.exec")
    })

    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

