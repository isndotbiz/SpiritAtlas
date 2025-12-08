# SpiritAtlas Test Commands Reference

## For Codex Max (runs in CLI agent)
Run ONLY critical calculation modules:

```bash
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test
```

**Why these:** Foundation calculation engines - must pass before anything else.

---

## For You (run on local machine after Codex completes)

### High-Priority (run immediately after Codex finishes)
ViewModel tests that drive the UI features:

```bash
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest
```

### Optional (run when you have time)
Repository/data layer tests:

```bash
./gradlew :data:testDebugUnitTest
```

### Comprehensive (run before release)
All tests in the project:

```bash
./gradlew test
```

---

## Test Breakdown by Priority

| Priority | Tests | Command | When to Run |
|----------|-------|---------|------------|
| 🔴 CRITICAL | Numerology, Astrology, Ayurveda, Human Design | `:core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test` | In Codex (Agent 0) |
| 🟠 HIGH | Home, Compatibility ViewModels | `:feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest` | After Codex finishes |
| 🟡 MEDIUM | Repositories, Data layer | `:data:testDebugUnitTest` | Before integration testing |
| 🟢 LOW | AI providers, integration tests | `test` (comprehensive) | Before release |

---

## Known Issues Codex Will Fix

1. **Chaldean calculator** - Empty string recursion
   - File: `core/numerology/src/main/java/com/spiritatlas/core/numerology/ChaldeanCalculator.kt`
   - Fix: Add `if (name.isBlank()) return 0` guard clause

2. **Master number 33** - Missing support
   - File: `core/numerology/src/main/java/com/spiritatlas/core/numerology/NumerologyVisualization.kt`
   - Fix: Add 33 to master numbers list (currently only 11, 22)

3. **Certificate pins** - Placeholders
   - File: `app/src/main/res/xml/network_security_config.xml`
   - Fix: Replace AAAA...= and BBBB...= with real openrouter.ai SHA-256 pins

---

## Timeline

1. **Codex runs** - Executes critical tests, fixes failures → ~30-45 min
2. **You run high-priority** - Home/Compatibility tests → ~10-15 min
3. **You run optional** - Data layer tests → ~5-10 min
4. **You run comprehensive** - All tests before release → ~2-3 min

---

## Quick Copy-Paste Commands

```bash
# Critical (Codex)
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

# High-priority (Local)
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest

# Optional (Local)
./gradlew :data:testDebugUnitTest

# All (Local - before release)
./gradlew test
```
