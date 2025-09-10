# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Overview

SpiritAtlas is a privacy-first spiritual insights Android app built with multi-module Clean Architecture. It integrates AI providers (cloud and local) for generating personalized content while maintaining strict privacy guardrails and user consent.

## Common Development Commands

### Setup
```bash
# One-time project setup (creates local.properties if missing)
scripts/bootstrap.sh
```

### Build
```bash
# Clean build artifacts
./gradlew clean

# Build debug APK
./gradlew :app:assembleDebug
```

### Testing
```bash
# Run unit tests for calculation modules
./gradlew :core:numerology:test :core:astro:test
```

## Architecture

### Module Structure
The app follows Clean Architecture with strict dependency boundaries:

- **`:domain`** - Pure business logic, interfaces, models (no Android dependencies, JVM-only)
- **`:data`** - Repository implementations, network, storage, WorkManager workers
- **`:core`** modules - Reusable components:
  - `:core:ui` - Material 3 theming, shared UI components
  - `:core:common` - Common utilities and Result types
  - `:core:numerology` - Chaldean/Pythagorean calculations
  - `:core:astro` - Sidereal/tropical zodiac calculations
  - `:core:humandesign` - Generic energy profiling (non-proprietary)
- **`:feature`** modules - Feature-specific UI and ViewModels:
  - `:feature:home` - Main dashboard and insights
  - `:feature:profile` - User profile and calculations
  - `:feature:consent` - Privacy controls and AI provider selection
- **`:app`** - Composition root, navigation, DI orchestration

### Dependency Flow
```
app → features → domain
     ↘ core ↗
data → domain
```

Key principles:
- Domain module has **zero** Android dependencies
- Data implements domain interfaces (dependency inversion)
- Features depend on domain and core, receive data via DI
- No circular dependencies between modules

## Privacy-First Design

### Core Principles
- All user data encrypted at rest via `EncryptedSharedPreferences`
- No PII sent to AI without explicit user consent
- `ConsentManager` centrally gates all network operations
- Default to local processing when possible

### Implementation
```kotlin
// Encrypted storage pattern
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()
val prefs = EncryptedSharedPreferences.create(
    context, "spiritatlas_prefs", masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)
```

## AI Integration

### Provider Architecture
Dual AI provider system with consent-gated selection:

- **`OpenRouterProvider`** - Cloud-based AI via OpenRouter API
- **`OllamaProvider`** - Local AI via Ollama
- **`CombinedAiProvider`** - Intelligent provider selection

### Selection Logic
```kotlin
when (settings.aiProviderMode) {
    AUTO -> if (consent.cloudAllowed && openRouterAvailable) cloud else local
    CLOUD -> if (consent.cloudAllowed) cloud else null
    LOCAL -> local
}
```

Users can switch providers via the Consent screen. AUTO mode prefers cloud when available and consented, falls back to local.

## Configuration

### Required Setup
The app requires `local.properties` in the project root:
```properties
# API keys (keep secure, never commit)
openrouter.api.key=your_openrouter_key_here
ollama.base.url=http://localhost:11434
```

`scripts/bootstrap.sh` creates this file with empty values if missing.

### BuildConfig Integration
Configuration values are injected into `BuildConfig` via Gradle and accessed throughout the data layer for API endpoints and keys.

## Testing

### Scope
- **Unit tests** for calculation logic in `:core:numerology` and `:core:astro`
- **80% minimum coverage** requirement for calculation modules
- Uses **JUnit 5** and **MockK** for mocking

### Commands
```bash
# Run calculation tests
./gradlew :core:numerology:test :core:astro:test

# Test coverage reports (if configured)
./gradlew testCoverage
```

Test files are located in `src/test/` within each core module.

## Background Work

### WorkManager Integration
- **`EnrichmentWorker`** - Processes AI-generated insights
- **`DataSyncWorker`** - Handles data synchronization
- Workers are Hilt-injected and respect consent boundaries

```kotlin
@HiltWorker
class EnrichmentWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val aiProvider: AiTextProvider,
    private val consentManager: ConsentManager
) : CoroutineWorker(context, params)
```

## Dependency Injection

### Hilt Setup
```kotlin
@HiltAndroidApp
class SpiritAtlasApplication : Application()

@AndroidEntryPoint  
class MainActivity : ComponentActivity()
```

DI modules in `:data:di` bind repository interfaces to implementations, enabling the Clean Architecture dependency inversion.

## Material 3 & UI

- **Material 3** components throughout
- **Dynamic color** support on Android 12+
- **Light/dark theme** support
- Compose-first UI with `androidx.compose.material3`

## Development Guidelines

### Architecture Rules
- Maintain strict module boundaries - no circular dependencies
- Domain module must remain Android-free (JVM plugin only)
- Use dependency inversion via Hilt modules

### Privacy Guardrails  
- No hardcoded API keys or secrets
- All user data must use `EncryptedSharedPreferences`
- Network calls require explicit consent checks
- Minimize data collection and maximize local processing

### Code Quality
- 80% test coverage minimum for calculation modules
- Use Kotlin official style guide
- Prefer `StateFlow` over `LiveData` in ViewModels
- Use sealed classes for state representation

## Quick Start (Optional)

For convenience, a complete build and install flow:
```bash
# Clean and build
./gradlew clean :app:assembleDebug

# Install to device/emulator  
./gradlew installDebug

# Launch app (update package name if changed)
adb shell am start -n "com.spiritatlas.app/.MainActivity"
```

---

**Maintenance Note**: Update this file when module boundaries, AI provider behavior, consent flows, or build commands change.
