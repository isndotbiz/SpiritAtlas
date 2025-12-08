# Distributed Testing Commands for SpiritAtlas

## Machine Assignment

| Machine | Specs | Test Assignment |
|---------|-------|-----------------|
| **Xeon Gold** | 64 cores, 64GB | UI/Integration tests (heavy parallelization) |
| **E5 2678 v3** | 96GB RAM | Full test suite + coverage report |
| **Ryzen 3900** | 64GB | Domain/Core module tests |
| **i7-14xxx (1)** | 32GB | Feature module tests |
| **i7-14xxx (2)** | 32GB | Data layer tests |
| **Mac (1)** | - | ViewModel tests |
| **Mac (2)** | - | Repository tests |
| **M4 MacBook** | 24GB | Development machine (fixes only) |

---

## Prerequisites (Run on ALL machines)

```bash
# Clone repo
git clone <your-repo-url> SpiritAtlas
cd SpiritAtlas

# Ensure Java 17+
java -version

# Setup Android SDK (set ANDROID_HOME)
export ANDROID_HOME=$HOME/Android/Sdk  # Linux
export ANDROID_HOME=$HOME/Library/Android/sdk  # macOS

# Grant execute permission
chmod +x gradlew
```

---

## XEON GOLD (64 cores) - UI & Integration Tests

```bash
# Run all instrumented tests with max parallelization
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.numShards=8 \
  -Pandroid.testInstrumentationRunnerArguments.shardIndex=0 \
  --parallel \
  --max-workers=32 \
  -Dorg.gradle.jvmargs="-Xmx48g -XX:+UseParallelGC -XX:ParallelGCThreads=32"

# Run Compose UI tests specifically
./gradlew :app:connectedDebugAndroidTest \
  --tests "*Screen*" \
  --parallel \
  --max-workers=32

# Run all integration tests
./gradlew :tests:integration:test \
  --parallel \
  --max-workers=32
```

---

## E5 2678 v3 (96GB) - Full Suite + Coverage

```bash
# Full test suite with coverage
./gradlew test jacocoTestReport \
  --parallel \
  --max-workers=24 \
  -Dorg.gradle.jvmargs="-Xmx64g -XX:+UseG1GC"

# Generate unified coverage report
./gradlew jacocoFullReport

# Coverage report will be at:
# build/reports/jacoco/jacocoFullReport/html/index.html
```

---

## RYZEN 3900 (64GB) - Core/Domain Modules

```bash
# Core Numerology module
./gradlew :core:numerology:test \
  --parallel \
  --max-workers=12 \
  -Dorg.gradle.jvmargs="-Xmx32g"

# Core Astrology module
./gradlew :core:astro:test \
  --parallel \
  --max-workers=12

# Core Human Design module
./gradlew :core:humandesign:test \
  --parallel \
  --max-workers=12

# Core Ayurveda module
./gradlew :core:ayurveda:test \
  --parallel \
  --max-workers=12

# Domain module (all business logic)
./gradlew :domain:test \
  --parallel \
  --max-workers=12

# Run all core modules at once
./gradlew :core:numerology:test :core:astro:test :core:humandesign:test :core:ayurveda:test :core:common:test :core:ui:test :domain:test \
  --parallel \
  --max-workers=12 \
  -Dorg.gradle.jvmargs="-Xmx48g"
```

---

## i7-14xxx #1 (32GB) - Feature Modules

```bash
# Profile feature
./gradlew :feature:profile:test \
  --parallel \
  --max-workers=8 \
  -Dorg.gradle.jvmargs="-Xmx24g"

# Home feature
./gradlew :feature:home:test \
  --parallel \
  --max-workers=8

# Compatibility feature
./gradlew :feature:compatibility:test \
  --parallel \
  --max-workers=8

# Consent feature
./gradlew :feature:consent:test \
  --parallel \
  --max-workers=8

# Tantric feature
./gradlew :feature:tantric:test \
  --parallel \
  --max-workers=8

# All feature modules
./gradlew :feature:profile:test :feature:home:test :feature:compatibility:test :feature:consent:test :feature:tantric:test \
  --parallel \
  --max-workers=8 \
  -Dorg.gradle.jvmargs="-Xmx24g"
```

---

## i7-14xxx #2 (32GB) - Data Layer

```bash
# Data module (repositories, workers, AI providers)
./gradlew :data:test \
  --parallel \
  --max-workers=8 \
  -Dorg.gradle.jvmargs="-Xmx24g"

# Run with specific test groups
./gradlew :data:test --tests "*Repository*" --parallel
./gradlew :data:test --tests "*Worker*" --parallel
./gradlew :data:test --tests "*Provider*" --parallel
./gradlew :data:test --tests "*Mapper*" --parallel
```

---

## MAC #1 - ViewModel Tests

```bash
# All ViewModel tests across modules
./gradlew test --tests "*ViewModel*" \
  --parallel \
  --max-workers=4 \
  -Dorg.gradle.jvmargs="-Xmx8g"

# Specific ViewModels
./gradlew :feature:profile:test --tests "*ProfileViewModel*"
./gradlew :feature:profile:test --tests "*ProfileLibraryViewModel*"
./gradlew :feature:home:test --tests "*HomeViewModel*"
./gradlew :feature:consent:test --tests "*ConsentViewModel*"
./gradlew :feature:compatibility:test --tests "*CompatibilityViewModel*"
```

---

## MAC #2 - Repository Tests

```bash
# All Repository tests
./gradlew test --tests "*Repository*" \
  --parallel \
  --max-workers=4 \
  -Dorg.gradle.jvmargs="-Xmx8g"

# Specific repositories
./gradlew :data:test --tests "*UserRepositoryImpl*"
./gradlew :data:test --tests "*ConsentRepository*"
./gradlew :data:test --tests "*AiSettingsRepository*"
```

---

## Quick Smoke Test (Any Machine)

```bash
# Fast smoke test to verify build
./gradlew assembleDebug testDebugUnitTest \
  --parallel \
  -x lint \
  -Dorg.gradle.jvmargs="-Xmx8g"
```

---

## Continuous Test Watcher (Development)

```bash
# Watch for changes and re-run affected tests
./gradlew test --continuous --parallel
```

---

## Test Results Collection

After tests complete on all machines, collect reports:

```bash
# On each machine, zip the test reports
cd SpiritAtlas
zip -r test-reports-$(hostname).zip */build/reports/tests/

# Copy to central location or use:
# scp test-reports-*.zip user@central-server:/reports/
```

---

## Priority Test Files to Create

These tests don't exist yet and need to be written:

### HIGH PRIORITY (Core Business Logic)
1. `core/ayurveda/src/test/java/.../AyurvedaCalculatorTest.kt`
2. `core/humandesign/src/test/java/.../HumanDesignCalculatorTest.kt`
3. `domain/src/test/java/.../CompatibilityAnalysisEngineTest.kt`
4. `domain/src/test/java/.../EnhancedCouplesAnalysisEngineTest.kt`

### MEDIUM PRIORITY (ViewModels)
5. `feature/profile/src/test/java/.../ProfileViewModelTest.kt`
6. `feature/profile/src/test/java/.../ProfileLibraryViewModelTest.kt`
7. `feature/home/src/test/java/.../HomeViewModelTest.kt`
8. `feature/consent/src/test/java/.../ConsentViewModelTest.kt`
9. `feature/compatibility/src/test/java/.../CompatibilityViewModelTest.kt`

### DATA LAYER
10. `data/src/test/java/.../UserRepositoryImplTest.kt`
11. `data/src/test/java/.../OpenRouterProviderTest.kt`
12. `data/src/test/java/.../OllamaProviderTest.kt`
13. `data/src/test/java/.../EnrichmentWorkerTest.kt`

---

## Estimated Time per Machine

| Machine | Test Scope | Est. Time |
|---------|-----------|-----------|
| Xeon Gold | UI/Integration | 15-25 min |
| E5 2678 | Full + Coverage | 20-30 min |
| Ryzen 3900 | Core/Domain | 5-10 min |
| i7 #1 | Features | 5-10 min |
| i7 #2 | Data | 5-10 min |
| Mac #1 | ViewModels | 3-5 min |
| Mac #2 | Repositories | 3-5 min |

**Total parallel time: ~30 minutes** (vs ~2 hours sequential)
