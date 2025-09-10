# SpiritAtlas

Privacy-first spiritual insights app with multi-module Clean Architecture, Material 3, Hilt, and WorkManager. Providers support OpenRouter (cloud) and Ollama (local).

## Modules
- app: Compose UI host and navigation
- core:common, core:ui, core:numerology, core:astro, core:humandesign
- domain: pure interfaces and models
- data: repositories, DI, network, secure storage, workers
- feature:home, feature:profile, feature:consent

## Requirements
- JDK 17
- Android SDK 34
- Android Studio (for emulator/AVD)

## Setup
1) Create `local.properties` in the project root with:
```
openrouter.api.key=sk-or-...
ollama.base.url=
```
2) Build
```
./gradlew :app:assembleDebug
```
3) Run on device/emulator.

## Provider selection
- Default AUTO prefers OpenRouter when key is set; falls back to Ollama if configured.
- Change provider in Consent screen (Auto/Cloud/Local).

## Privacy
- All user data stored via EncryptedSharedPreferences
- No PII sent without explicit consent

## Tests
```
./gradlew :core:numerology:test :core:astro:test
```


