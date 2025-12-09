# CLAUDE.md - SpiritAtlas Project Guidelines

This file provides guidance to Claude Code when working with the SpiritAtlas Android codebase.

## Project Overview

SpiritAtlas is a comprehensive spiritual profile and compatibility analysis Android application. It combines multiple spiritual calculation systems (Numerology, Astrology, Ayurveda, Human Design, Tantric) to provide personalized insights and relationship compatibility analysis.

## Technology Stack

- **Language**: Kotlin 1.9.25
- **UI Framework**: Jetpack Compose with Material3
- **Compose Compiler**: 1.5.15
- **Architecture**: Clean Architecture with MVVM
- **DI**: Hilt
- **Build System**: Gradle 8.13 with Kotlin DSL
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Project Structure

```
SpiritAtlas/
├── app/                          # Main application module
│   └── navigation/               # Navigation graph and transitions
├── core/
│   ├── ui/                       # Shared UI components, theme, animations
│   │   ├── components/           # Reusable Compose components
│   │   ├── theme/               # Colors, typography, gradients
│   │   ├── animation/           # Spiritual animations
│   │   └── haptics/             # Haptic feedback utilities
│   ├── common/                  # Shared utilities
│   ├── numerology/              # Numerology calculations (Chaldean, Pythagorean)
│   ├── astro/                   # Astrology calculations
│   ├── ayurveda/                # Ayurveda/Dosha calculations
│   └── humandesign/             # Human Design calculations
├── data/                        # Data layer (repositories, database, network)
│   ├── repository/              # Repository implementations
│   └── ai/                      # AI provider integrations
├── domain/                      # Domain layer (models, use cases)
│   ├── model/                   # Domain models
│   └── service/                 # Business logic services
└── feature/
    ├── home/                    # Home screen
    ├── profile/                 # Profile creation and viewing
    ├── compatibility/           # Compatibility analysis
    ├── consent/                 # Data consent management
    └── settings/                # App settings
```

## Development Commands

### Build
```bash
./gradlew :app:assembleDebug     # Build debug APK
./gradlew :app:assembleRelease   # Build release APK
```

### Testing
```bash
# Critical tests (run before commits)
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

# Feature tests
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest

# All tests
./gradlew test
```

### Linting
```bash
./gradlew lint                   # Run Android lint
./gradlew ktlintCheck           # Check Kotlin style (if configured)
```

## Key Architectural Patterns

1. **Clean Architecture**: Strict separation between data, domain, and presentation layers
2. **MVVM**: ViewModels expose StateFlow to Compose UI
3. **Repository Pattern**: All data access through repository interfaces
4. **Hilt DI**: Constructor injection throughout

## Compose Best Practices

- Use `@Stable` and `@Immutable` for state classes to prevent unnecessary recompositions
- Prefer `remember` and `derivedStateOf` for computed values
- Use `LaunchedEffect` for side effects, `rememberCoroutineScope` for event handlers
- Never use `GlobalScope` in Composables

## Important Conventions

- **Imports**: Always use explicit imports for Compose functions
- **Coroutines**: Use `rememberCoroutineScope()` in Composables, not `GlobalScope`
- **Experimental APIs**: Add `@OptIn` annotations for experimental Material3 APIs
- **Type Conversions**: When drawing in Canvas, convert `IntSize` to `Size` explicitly

## Common Issues & Fixes

1. **Unresolved reference in Compose**
   - Check if import is missing (e.g., `import kotlin.random.Random`)
   - Check if experimental API needs `@OptIn` annotation

2. **Type mismatch IntSize vs Size**
   - Use `Size(size.width.toFloat(), size.height.toFloat())`

3. **Coroutine scope in Composable**
   - Replace `GlobalScope.launch` with `scope.launch` where `val scope = rememberCoroutineScope()`

4. **Missing module dependency**
   - Add to build.gradle.kts: `implementation(project(":core:modulename"))`

## Files to Avoid Modifying

- Documentation files in `docs/` (reference only)
- Test data fixtures in `*/src/test/resources/`

## Commit Guidelines

- Use conventional commits: `feat:`, `fix:`, `refactor:`, `test:`, `docs:`
- Run critical tests before committing
- Include module name in scope: `fix(core:ui): description`

---

## Current Project Status (Updated 2025-12-08)

### Build Status: ✅ PASSING
- All modules compile cleanly
- Debug build: ✅ SUCCESS
- Release build: ✅ SUCCESS
- Lint checks: ✅ PASSING
- ProGuard/R8: ✅ Configured

### Test Coverage: ✅ 113/113 PASSING

| Module | Tests | Status |
|--------|-------|--------|
| **core:numerology** | 14 | ✅ 100% |
| **core:astro** | 83 | ✅ 100% |
| **core:ayurveda** | 6 | ✅ 100% |
| **core:humandesign** | 10 | ✅ 100% |

**Run critical tests:**
```bash
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test
```

### Security: ✅ SSL PINNING CONFIGURED

**Network Security Config:** `app/src/main/res/xml/network_security_config.xml`
- Real SHA-256 certificate pins for openrouter.ai
- Leaf certificate + Intermediate CA (Google Trust Services WE1)
- Pin expiration: 2026-12-31
- Localhost cleartext allowed for Ollama (local AI development)

#### 🔄 SSL Pin Rotation Process

**When to rotate pins:**
- Certificate approaching expiration date
- Certificate authority change
- Security incident or compromise

**How to update pins:**

1. **Fetch new certificate pins:**
```bash
# Get the new certificate chain
python3 << 'EOF'
import ssl
import socket
import hashlib
import base64

hostname = 'openrouter.ai'
port = 443

context = ssl.create_default_context()
with socket.create_connection((hostname, port)) as sock:
    with context.wrap_socket(sock, server_hostname=hostname) as ssock:
        cert_bin = ssock.getpeercert(binary_form=True)

        # Extract and hash public key
        import subprocess
        cert_pem = ssl.DER_cert_to_PEM_cert(cert_bin)

        proc1 = subprocess.run(['openssl', 'x509', '-pubkey', '-noout'],
                              input=cert_pem.encode(), capture_output=True)
        proc2 = subprocess.run(['openssl', 'pkey', '-pubin', '-outform', 'der'],
                              input=proc1.stdout, capture_output=True)

        pin = base64.b64encode(hashlib.sha256(proc2.stdout).digest()).decode()
        print(f"New pin: {pin}")
EOF
```

2. **Update `network_security_config.xml`:**
   - Add new pin alongside existing pin (overlap period)
   - Update expiration date
   - Test with new configuration

3. **Gradual rollout:**
   - Keep both old and new pins for 30 days
   - Monitor crash reports for SSL errors
   - Remove old pin after transition period

4. **Emergency backup plan:**
   - Always maintain 2+ pins (leaf + intermediate CA)
   - Document pin update procedure in runbook
   - Test pin validation in integration tests

**Testing pin rotation:**
```bash
# Verify pins are correct
./gradlew :data:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.spiritatlas.data.network.SslPinningTest
```

### Compose/Kotlin Compatibility: ✅ VERIFIED
- Kotlin: 1.9.25
- Compose Compiler: 1.5.15 (all modules)
- Compose BOM: 2024.09.02
- KSP: 1.9.25-1.0.20

### Code Quality
- ✅ Explicit imports (no wildcards)
- ✅ Proper `@OptIn` annotations
- ✅ StateFlow properly collected with `collectAsState()`
- ✅ No deprecated API usage in core modules

---

## Quick Commands Reference

**Install & Launch on Emulator:**
```bash
./gradlew installDebug && adb shell am start -n com.spiritatlas.app/.MainActivity
```

**View Logs:**
```bash
adb logcat | grep -E "SpiritAtlas|AndroidRuntime"
```

**Clean Build:**
```bash
./gradlew clean assembleDebug
```

**Full Test Suite:**
```bash
./gradlew test
```

---

## AI Configuration

The app supports two AI provider modes:
1. **Local (Ollama)** - Privacy-first, runs on device
2. **Cloud (OpenRouter)** - Advanced models, requires SSL pinning

Users can switch providers in Settings (ConsentScreen.kt)
