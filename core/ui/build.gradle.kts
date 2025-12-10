plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.spiritatlas.core.ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

}

dependencies {
    api(platform(libs.compose.bom))
    api(libs.compose.ui)
    api(libs.compose.material3)
    implementation(libs.compose.material)
    api(libs.compose.ui.tooling.preview)
    debugApi(libs.compose.ui.tooling)

    // Icons
    implementation(libs.compose.material.icons.extended)

    // Image loading
    implementation(libs.coil.compose)

    // Core dependencies for WindowCompat
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Project dependencies - using api because UI components expose these types
    api(project(":core:astro"))
    api(project(":core:numerology"))
    api(project(":core:humandesign"))
    api(project(":domain"))

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("org.robolectric:robolectric:4.11.1")
}
