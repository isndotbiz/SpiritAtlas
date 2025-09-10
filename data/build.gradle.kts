import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.spiritatlas.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        
        // Generate BuildConfig fields from local.properties
        val props = Properties().apply {
            val f = rootProject.file("local.properties")
            if (f.exists()) load(f.inputStream())
        }
        buildConfigField("String", "OPENROUTER_API_KEY", "\"${props.getProperty("openrouter.api.key", "")}\"")
        buildConfigField("String", "OLLAMA_BASE_URL", "\"${props.getProperty("ollama.base.url", "http://localhost:11434")}\"")
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
    
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    
    // Logging
    implementation(libs.timber)
}


