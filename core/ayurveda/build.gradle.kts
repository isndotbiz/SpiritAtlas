plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jacoco)
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
    
    // Testing
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}

tasks.test {
    useJUnitPlatform()
}

// JaCoCo test coverage
jacoco {
    toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal() // 80% minimum coverage
            }
        }
    }
}
