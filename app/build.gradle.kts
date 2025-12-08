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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}
