# Dependency Change Checklist

Use this checklist when adding new dependencies or creating new modules in the SpiritAtlas project.

## Before Adding a New Dependency

- [ ] Check if the dependency already exists in the project (avoid duplicates)
- [ ] Verify the dependency is actively maintained (check GitHub stars, last commit)
- [ ] Review security advisories for the dependency
- [ ] Check license compatibility (prefer Apache 2.0, MIT)
- [ ] Verify Android compatibility (minSdk 26)
- [ ] Check version conflicts with existing dependencies

## Adding a Dependency

### 1. Add to Version Catalog First
**File:** `gradle/libs.versions.toml`

```toml
[versions]
new-library = "x.y.z"

[libraries]
new-library = { module = "com.example:library", version.ref = "new-library" }
```

### 2. Use in Module Build File
**File:** `[module]/build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.new.library)  // NOT: implementation("com.example:library:x.y.z")
}
```

### 3. Choose Correct Scope

- [ ] `implementation` - Most common, hides dependency from consumers
- [ ] `api` - Only if module exposes types from this dependency (rare)
- [ ] `compileOnly` - Compile-time only (e.g., annotations)
- [ ] `runtimeOnly` - Runtime only (e.g., JDBC drivers)
- [ ] `testImplementation` - Unit tests only
- [ ] `androidTestImplementation` - Instrumentation tests only
- [ ] `debugImplementation` - Debug builds only

**Default Choice:** `implementation` (unless you have a specific reason for `api`)

## Creating a New Module

### 1. Choose Module Type

- [ ] **Pure Kotlin JVM** - For business logic, calculations (prefer this)
- [ ] **Android Library** - For UI, Android-specific features
- [ ] **Android Application** - Only for the app module (already exists)

### 2. Determine Layer

```
Layer 1: Pure Kotlin (core:*, domain)
Layer 2: Data (data)
Layer 3: UI Core (core:ui)
Layer 4: Features (feature:*)
Layer 5: Application (app)
```

- [ ] Module fits into one of these layers
- [ ] Module only depends on lower layers
- [ ] Module doesn't create circular dependencies

### 3. Create Module Structure

```bash
mkdir -p [module-name]/src/main/java
mkdir -p [module-name]/src/test/java
```

### 4. Create `build.gradle.kts`

**For Pure Kotlin Module:**
```kotlin
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
    // Add dependencies here
    testImplementation(libs.junit.jupiter)
}
```

**For Android Library Module:**
```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.spiritatlas.[module-path]"
    compileSdk = 35

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
        compose = true  // Only if using Compose
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"  // Only if using Compose
    }
}

dependencies {
    // Add dependencies here
}
```

### 5. Add to `settings.gradle.kts`

```kotlin
include(":[module-name]")
// or for nested:
include(":category:[module-name]")
```

### 6. Add Dependencies Based on Layer

**Layer 1 (Pure Kotlin):**
```kotlin
// Minimal dependencies, prefer pure Java/Kotlin
implementation(project(":core:common"))  // If needed
```

**Layer 2 (Data):**
```kotlin
implementation(project(":domain"))
implementation(project(":core:common"))
// Android/data dependencies (Room, Retrofit, etc.)
```

**Layer 3 (UI Core):**
```kotlin
api(platform(libs.compose.bom))
api(libs.compose.ui)
api(libs.compose.material3)
implementation(project(":domain"))
```

**Layer 4 (Feature):**
```kotlin
implementation(project(":core:ui"))
implementation(project(":core:common"))
implementation(project(":domain"))
implementation(project(":data"))  // Only if needed
```

## Dependency Scoping Decision Tree

```
Does the module expose types from this dependency in its public API?
├─ YES → Use 'api'
│   Examples:
│   • core:ui exposes Compose types to features
│   • Module has public functions returning dependency types
│
└─ NO → Use 'implementation'
    Examples:
    • Internal utilities
    • Implementation details
    • Most dependencies (90%+ of cases)
```

## After Adding Dependencies

- [ ] Run `./gradlew build` to verify compilation
- [ ] Run critical tests: `./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test`
- [ ] Check for dependency conflicts: `./gradlew :app:dependencies`
- [ ] Verify no circular dependencies: `./gradlew buildEnvironment | grep -i circular`
- [ ] Update `DEPENDENCY_GRAPH.md` if adding a new module
- [ ] Document any special configuration in module README

## Common Pitfalls to Avoid

### ❌ DON'T: Hardcode Versions
```kotlin
implementation("com.example:library:1.2.3")  // WRONG
```

### ✅ DO: Use Version Catalog
```kotlin
implementation(libs.library)  // CORRECT
```

---

### ❌ DON'T: Use `api` Everywhere
```kotlin
api(project(":domain"))  // Only if you expose domain types
```

### ✅ DO: Default to `implementation`
```kotlin
implementation(project(":domain"))  // Most cases
```

---

### ❌ DON'T: Create Circular Dependencies
```kotlin
// In feature:profile
implementation(project(":feature:home"))  // WRONG - features shouldn't depend on features
```

### ✅ DO: Extract Shared Code to Core
```kotlin
// In feature:profile
implementation(project(":core:ui"))  // CORRECT - use shared UI components
```

---

### ❌ DON'T: Add Android Dependencies to Pure Kotlin Modules
```kotlin
// In core:numerology
implementation("androidx.core:core-ktx:1.13.1")  // WRONG - breaks pure Kotlin
```

### ✅ DO: Keep Pure Kotlin Modules Android-Free
```kotlin
// In core:numerology
// Only Kotlin stdlib, javax.inject, test libraries
```

---

### ❌ DON'T: Duplicate Compose Dependencies in Feature Modules
```kotlin
// In feature:home
implementation(platform(libs.compose.bom))  // WRONG - core:ui already provides this
implementation(libs.compose.ui)
implementation(libs.compose.material3)
```

### ✅ DO: Let core:ui Provide Compose Dependencies
```kotlin
// In feature:home
implementation(project(":core:ui"))  // CORRECT - gets Compose transitively
```

## Dependency Update Process

### Regular Updates (Quarterly)
1. Check for updates: `./gradlew dependencyUpdates`
2. Review release notes for breaking changes
3. Update one dependency at a time
4. Run full test suite after each update
5. Test app on device/emulator

### Security Updates (Immediate)
1. Monitor security advisories
2. Update affected dependency immediately
3. Verify SSL pinning configuration if network dependencies change
4. Run security scan: `./gradlew dependencyCheckAnalyze` (if configured)

### Major Version Updates
1. Read migration guide
2. Create feature branch
3. Update version in `libs.versions.toml`
4. Fix breaking changes
5. Run full test suite
6. Test all features manually
7. Create PR with detailed testing notes

## Troubleshooting

### "Circular dependency detected"
1. Draw dependency graph on paper
2. Identify the cycle
3. Extract shared code to lower layer
4. Never have features depend on each other

### "Duplicate class" errors
1. Check for version conflicts: `./gradlew :app:dependencies`
2. Use `dependencyInsight` to find source
3. Exclude transitive dependencies if needed:
   ```kotlin
   implementation(libs.library) {
       exclude(group = "com.conflict", module = "module")
   }
   ```

### "Unresolved reference" after adding dependency
1. Sync Gradle files in Android Studio
2. Invalidate caches and restart
3. Check dependency scope (implementation vs testImplementation)
4. Verify version catalog entry is correct

### Build is slow after adding dependencies
1. Check dependency size (avoid large libraries)
2. Use `--build-cache` and `--parallel`
3. Consider using `api` for frequently changing shared libraries (rare)
4. Profile build: `./gradlew build --profile`

## Reference

- **Version Catalog:** `gradle/libs.versions.toml`
- **Dependency Graph:** `DEPENDENCY_GRAPH.md`
- **Project Guidelines:** `CLAUDE.md`
- **Gradle Docs:** https://docs.gradle.org/current/userguide/dependency_management.html

---

**Last Updated:** 2025-12-10
**Maintained By:** BUILD FIX AGENT 10
