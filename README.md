# 🌌 SpiritAtlas — Privacy‑First Spiritual Insights

Powerful, privacy‑first spiritual insights for self and relationships — built with modern Android (Jetpack Compose + Material 3), Clean Architecture, Hilt DI, and WorkManager. Dual AI providers support cloud (OpenRouter) and local (Ollama) with strict consent gates. 🔐✨

<p align="center">
  <img alt="SpiritAtlas" src="https://user-images.githubusercontent.com/placeholder/spiritatlas-hero.png" width="720"/>
</p>

---

## 🧭 Table of Contents
- Highlights
- Architecture & Modules
- Setup & Configuration
- Build • Run • Test
- Quality • Security • Coverage
- AI Providers & Consent
- Background Work
- Performance Notes
- Troubleshooting & FAQ
- Changelog (inline)
- Contributing & Best Practices
- License & Disclaimers

---

## ✨ Highlights
- Privacy‑first design: no PII leaves device without explicit consent
- Multi‑module Clean Architecture with strict boundaries
- Insights powered by numerology, astrology (sidereal/tropical), and generic energy profiling
- Couples compatibility and tantric content modules
- Cloud + Local AI with intelligent AUTO selection logic
- Encrypted storage via EncryptedSharedPreferences (AES‑256)

---

## 🧱 Architecture & Modules

Dependency flow:
```
app → features → domain
     ↘ core ↗
 data → domain
```

Modules:
- app — Compose host, navigation, Hilt entry
- core
  - core:ui — Material 3 theme and reusable components
  - core:common — Result types and utilities
  - core:numerology — Pythagorean/Chaldean
  - core:astro — Sidereal/tropical calculations
  - core:humandesign — Generic energy profiling (non‑proprietary)
- domain — Interfaces, models, business rules (Android‑free)
- data — Repositories, network, secure storage, WorkManager, DI
- feature
  - feature:home — Dashboard & insights
  - feature:profile — Profiles & calculations
  - feature:consent — Privacy controls & AI provider selection
  - feature:compatibility — Couples compatibility analysis 💞
  - feature:tantric — Curated tantric content 🕉️

---

## ⚙️ Setup & Configuration

Requirements:
- JDK 17
- Android SDK 34
- Android Studio Koala or later

1) Create local.properties
```properties
openrouter.api.key=sk-or-...
ollama.base.url=http://localhost:11434
```

2) Bootstrap (optional, creates local.properties if missing)
```bash
./scripts/bootstrap.sh
```

3) Build debug APK
```bash
./gradlew clean :app:assembleDebug
```

4) Install & launch (device/emulator)
```bash
./gradlew installDebug
adb shell am start -n "com.spiritatlas.app/.MainActivity"
```

Secrets policy:
- Never commit keys. Keys are read from local.properties and injected into BuildConfig.

---

## ▶️ Build • Run • Test

Build app:
```bash
./gradlew :app:assembleDebug
```

Run unit tests (core calculations):
```bash
./gradlew :core:numerology:test :core:astro:test
```

Unified coverage report:
```bash
./gradlew testCoverageReport
```

Static analysis:
```bash
./gradlew detekt ktlintCheck lint
```

Security & dependency audit:
```bash
./gradlew dependencyCheckAggregate
```

---

## 🧠 AI Providers & Consent

Providers:
- OpenRouterProvider — Cloud
- OllamaProvider — Local
- CombinedAiProvider — AUTO selection

Selection logic:
```kotlin
when (settings.aiProviderMode) {
    AUTO -> if (consent.cloudAllowed && openRouterAvailable) cloud else local
    CLOUD -> if (consent.cloudAllowed) cloud else null
    LOCAL -> local
}
```

Tips (emulator <-> Ollama on host):
```bash
# If using Android Emulator, forward port to host Ollama
adb reverse tcp:11434 tcp:11434
```

Consent guardrails:
- No network calls without ConsentManager approval
- Default to local processing when possible

---

## 🔄 Background Work

WorkManager tasks:
- EnrichmentWorker — Processes AI‑generated insights
- DataSyncWorker — Sync data if/when enabled

Both respect consent boundaries and are Hilt‑injected.

---

## 🚀 Performance Notes
- Gradle optimizations: parallel, config cache, build cache
- Kotlin/JVM 17 target, Compose compiler extension 1.5.x
- Optimized compatibility analysis engine with caching

---

## 🛟 Troubleshooting & FAQ

Build fails due to missing keys?
```bash
# Ensure local.properties exists and is populated
printf "openrouter.api.key=sk-or-...\nollama.base.url=http://localhost:11434\n" > local.properties
```

Ollama not reachable from the emulator?
```bash
# Forward host port to emulator
adb reverse tcp:11434 tcp:11434
```

Where are quality and security checks?
```bash
./gradlew detekt ktlintCheck lint testCoverageReport dependencyCheckAggregate
```

Python virtual environment?
- No Python tooling is required for this Android project. If you add Python scripts later:
```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -U pip pre-commit black isort flake8
pre-commit --version
deactivate
```

---

## 🧾 Changelog (Inline)
- 2025‑09‑10: Infrastructure hardening — Detekt, ktlint, JaCoCo, OWASP DC; dependency updates; performance optimizations; comprehensive tests; compatibility and tantric features
- 2025‑09‑11: Build artifact cleanup and repository hygiene; added feature modules and docs baseline

See RELEASE_NOTES.md for detailed notes. Consider adopting Keep a Changelog with semantic versioning.

---

## 🤝 Contributing & Best Practices
- Keep domain Android‑free
- Enforce module boundaries — no circular deps
- Use StateFlow in ViewModels and sealed classes for UI state
- Minimum 80% coverage for core calculation modules
- No hardcoded secrets — use EncryptedSharedPreferences for user data
- Prefer small, focused PRs with tests

Pre‑flight checks:
```bash
./gradlew detekt ktlintCheck lint :core:numerology:test :core:astro:test testCoverageReport dependencyCheckAggregate
```

---

## 📜 License & Disclaimers
- See DISCLAIMER.md for usage boundaries (entertainment/education only)
- SECURITY.md covers data protection and network security guidelines
- Copyright © SpiritAtlas Contributors
