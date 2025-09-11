# WARP.md

This file provides guidance to Warp (warp.dev) when working with the SpiritAtlas repository.

## Overview
SpiritAtlas is a privacy‑first spiritual insights Android app built with multi‑module Clean Architecture. It integrates dual AI providers (cloud and local) with strict consent guardrails.

## Project Map
Modules (include all current features):
- :app
- :core:ui, :core:common, :core:numerology, :core:astro, :core:humandesign
- :domain
- :data
- :feature:home, :feature:profile, :feature:consent, :feature:compatibility, :feature:tantric

Dependency flow:
```
app → features → domain
     ↘ core ↗
 data → domain
```

Key principles:
- Domain has zero Android dependencies
- Data implements domain interfaces (DI via Hilt)
- Features depend on domain and core; no circular deps

## Common Development Commands

### Setup
```bash
# One‑time setup (creates local.properties if missing)
./scripts/bootstrap.sh || true

# Verify toolchain
./gradlew --version
java -version
```

### Build
```bash
# Clean and assemble
./gradlew clean :app:assembleDebug
```

### Install & Launch
```bash
./gradlew installDebug
adb shell am start -n "com.spiritatlas.app/.MainActivity"
```

### Testing
```bash
# Core calculation unit tests
./gradlew :core:numerology:test :core:astro:test

# Unified coverage report
./gradlew testCoverageReport
```

### Quality & Security
```bash
# Linting and static analysis
./gradlew detekt ktlintCheck lint

# Security and dependency audit
./gradlew dependencyCheckAggregate
```

## Privacy‑First Design
- All user data encrypted at rest via EncryptedSharedPreferences (AES‑256)
- No PII sent to AI without explicit user consent
- ConsentManager centrally gates all network operations
- Default to local processing when possible

```kotlin
// Encrypted storage pattern example
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
Providers:
- OpenRouterProvider (Cloud)
- OllamaProvider (Local)
- CombinedAiProvider (AUTO)

Selection logic:
```kotlin
when (settings.aiProviderMode) {
    AUTO -> if (consent.cloudAllowed && openRouterAvailable) cloud else local
    CLOUD -> if (consent.cloudAllowed) cloud else null
    LOCAL -> local
}
```

Emulator ↔ Host (Ollama) connectivity:
```bash
adb reverse tcp:11434 tcp:11434
```

## Background Work
WorkManager (Hilt‑injected):
- EnrichmentWorker — AI‑generated insights processing
- DataSyncWorker — Synchronization

## Hilt
```kotlin
@HiltAndroidApp
class SpiritAtlasApplication : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity()
```

## Development Guidelines
- Maintain strict module boundaries; domain stays Android‑free
- Use StateFlow in ViewModels; sealed classes for UI state
- Keep secrets out of VCS; via local.properties → BuildConfig
- Target 80%+ coverage on core calculation modules

## Troubleshooting
```bash
# Missing API keys
printf "openrouter.api.key=sk-or-...\nollama.base.url=http://localhost:11434\n" > local.properties

# Long builds? Try daemon/config cache debug
./gradlew --stop; ./gradlew --status
./gradlew clean --no-build-cache
```

## Maintenance
Update this file when:
- Module boundaries or feature modules change
- AI provider behavior or consent flows change
- Build commands or quality/security tasks change
