import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.spiritatlas.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.spiritatlas.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        
        // Load API keys from local.properties
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }
        fun sanitize(raw: String?): String = (raw ?: "").trim().trim('"')
        val openRouterKey = sanitize(localProperties.getProperty("openrouter.api.key"))
        val ollamaRaw = sanitize(localProperties.getProperty("ollama.base.url"))
        val ollamaUrl = if (ollamaRaw.isBlank()) "http://localhost:11434" else ollamaRaw
        
        buildConfigField("String", "OPENROUTER_API_KEY", "\"$openRouterKey\"")
        buildConfigField("String", "OLLAMA_BASE_URL", "\"$ollamaUrl\"")
    }

    buildTypes {
        release {
            // Enable full R8 optimization
            isMinifyEnabled = true
            isShrinkResources = true

            // Use aggressive optimization profile
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Signing config (add your keystore details in gradle.properties)
            // signingConfig = signingConfigs.getByName("release")

            // Additional R8 optimization flags
            // These are passed to R8 via gradle.properties or build config
        }

        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    // Packaging options for APK size optimization
    packaging {
        resources {
            excludes += listOf(
                "META-INF/**",
                "kotlin/**",
                "**.txt",
                "**.bin",
                "META-INF/LICENSE*",
                "META-INF/NOTICE*",
                "META-INF/*.kotlin_module",
                "META-INF/versions/**",
                "okhttp3/internal/publicsuffix/*",
                "org/bouncycastle/x509/*",
                "org/apache/commons/logging/**"
            )
        }

        // Enable JNI library stripping
        jniLibs {
            useLegacyPackaging = false
        }

        // Enable resource optimization
        dex {
            useLegacyPackaging = false
        }
    }

    // Configure splits for smaller APKs (optional - for advanced optimization)
    splits {
        abi {
            isEnable = false // Set to true for ABI-specific APKs
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
        density {
            isEnable = false // Set to true for density-specific APKs
        }
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":feature:home"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:consent"))
    implementation(project(":feature:compatibility"))
    implementation(project(":feature:onboarding"))
    implementation(project(":core:ayurveda"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.androidx.navigation.compose)
    
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    
    // WorkManager for reliable background tasks
    implementation(libs.androidx.work.runtime)
    
    // EncryptedSharedPreferences for secure PII storage
    implementation(libs.androidx.security.crypto)
    
    // Logging
    implementation(libs.timber)

    // Image Loading
    implementation(libs.coil.compose)

    // Baseline Profile Support
    implementation(libs.androidx.profileinstaller)

    // Android UI Testing dependencies
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.5")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
