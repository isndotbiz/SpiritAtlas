plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("javax.inject:javax.inject:1")
    implementation("org.json:json:20231013")

    // Compose runtime for @Stable and @Immutable annotations
    implementation(platform("androidx.compose:compose-bom:2024.09.02"))
    implementation("androidx.compose.runtime:runtime")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("com.google.truth:truth:1.1.3")
}

tasks.test {
    useJUnit()
}


