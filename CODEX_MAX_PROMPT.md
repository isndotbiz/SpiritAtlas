# Codex Max Coordination Prompt for SpiritAtlas

## STATUS UPDATE: CLAUDE HAS COMPLETED ALL WORK

**Claude is NO LONGER working on this codebase. You have FULL ACCESS to ALL files.**

See `CODEX_MAX_COMPREHENSIVE_REVIEW.md` for your new mission: comprehensive review and optimization.

---

## What Claude Completed (Summary)

### UI Beautification (~25,000 lines added):
- **Visualizations**: Zodiac wheel, Chakra system, Bodygraph (Human Design), Dosha (Ayurveda), Numerology, Tantric energy, Moon phases, Planetary transits, Sacred geometry, Particle effects, Compatibility charts
- **Screens**: Splash screen, Onboarding flow, Enhanced home screen, Settings screen, Profile detail screen, Compatibility detail screen
- **Components**: Animations library, Shimmer effects, Error UI, Gradient text, Bottom sheets, Tooltips/coach marks, Pull-to-refresh (5 spiritual indicators), Haptic feedback system
- **Theme**: Enhanced color palette (50+ colors), Spiritual gradients, Light/dark/high-contrast themes
- **Navigation**: 9 custom transition types with spiritual timing

### Bug Fixes Completed:
- Consent screen auto-grant bypass (P0 security fix)
- Certificate pinning in NetworkModule
- ProGuard rules for production builds
- DateTimePicker state consolidation
- Various performance optimizations

---

## CURRENT PERMISSIONS (PHASE 2)

**YOU NOW HAVE FULL ACCESS TO ALL FILES.**

Previous restrictions no longer apply. You are explicitly granted permission to:
- ✅ Modify any source files (`src/main/`, `src/test/`, etc.)
- ✅ Update build.gradle.kts files
- ✅ Refactor existing code
- ✅ Create new files and packages
- ✅ Delete unused/dead code
- ✅ Reorganize directories
- ✅ Update configuration files
- ✅ Modify any component, screen, or feature

**Exception:** Only modify files if they're part of your assigned review/optimization tasks. Don't create unrelated features.

---

## ADDITIONAL FILES YOU CAN MODIFY (if needed for optimization)

If during your review you find that certain files need changes, you can explicitly request permission:

1. **UI Component Files** - Any file in `core/ui/`, `feature/*/ui`
2. **Calculation Modules** - Any file in `core/numerology/`, `core/astro/`, `core/ayurveda/`, `core/humandesign/`
3. **ViewModels** - Any ViewModel in `feature/*/`
4. **Repositories** - Any repository implementation in `data/repository/`
5. **Network/API** - Files in `data/api/`, `data/di/` (especially NetworkModule)
6. **Database** - Files in `data/db/`
7. **Build Configuration** - build.gradle.kts, gradle.properties, gradle-wrapper.properties

---

## YOUR ASSIGNED TASKS (Codex Max - Phase 2)

### Task 1: Write Unit Tests for Core Numerology Module
**Files to create/modify:**
- `core/numerology/src/test/java/com/spiritatlas/core/numerology/ChaldeanCalculatorTest.kt`
- `core/numerology/src/test/java/com/spiritatlas/core/numerology/PythagoreanCalculatorTest.kt`

**What to test:**
```kotlin
// ChaldeanCalculator tests needed:
- calculateNameNumber("") should return 0
- calculateNameNumber("A") should return 1
- calculateNameNumber("JOHN") should calculate correctly using Chaldean mapping
- Master numbers 11, 22, 33 should be preserved
- Special characters and spaces should be ignored
- Case insensitivity

// PythagoreanCalculator tests needed:
- calculateLifePath(1990, 5, 15) should return correct value
- Master number dates should preserve 11, 22, 33
- Edge cases: leap years, boundary dates
```

---

### Task 2: Write Unit Tests for Core Astrology Module
**Files to create/modify:**
- `core/astro/src/test/java/com/spiritatlas/core/astro/AstroCalculatorTest.kt` (expand existing)

**What to test:**
```kotlin
// Additional tests needed:
- Negative longitude handling (bug found: -30.0 throws exception)
- Ayanamsa calculation accuracy for different years
- Tropical to Sidereal conversion
- All 12 zodiac signs boundary tests
- Aspect calculations (conjunction, opposition, trine, square, sextile)
```

---

### Task 3: Write Unit Tests for Ayurveda Module (Currently 0% coverage)
**Files to create:**
- `core/ayurveda/src/test/java/com/spiritatlas/core/ayurveda/DoshaCalculatorTest.kt`
- `core/ayurveda/src/test/java/com/spiritatlas/core/ayurveda/AyurvedaServiceTest.kt`

**What to test:**
```kotlin
// DoshaCalculator tests:
- calculateDosha() returns valid Dosha type (VATA, PITTA, KAPHA)
- Dosha percentages sum to 100
- Input validation for physical characteristics
- Diet recommendations based on dosha

// AyurvedaService tests:
- Profile analysis returns complete results
- Wellness recommendations are appropriate for dosha
```

---

### Task 4: Write Unit Tests for Human Design Module (Currently 0% coverage)
**Files to create:**
- `core/humandesign/src/test/java/com/spiritatlas/core/humandesign/HumanDesignCalculatorTest.kt`
- `core/humandesign/src/test/java/com/spiritatlas/core/humandesign/BodygraphTest.kt`

**What to test:**
```kotlin
// HumanDesignCalculator tests:
- calculateType() returns valid types (Generator, Manifesting Generator, Projector, Manifestor, Reflector)
- calculateStrategy() matches type
- calculateAuthority() is valid for type
- Profile calculation (1/3, 2/4, etc.)

// Bodygraph tests:
- Centers are correctly defined/undefined
- Gates and channels
```

---

### Task 5: Write ViewModel Tests (Currently 0% coverage)
**Files to create:**
- `feature/home/src/test/java/com/spiritatlas/feature/home/HomeViewModelTest.kt`
- `feature/compatibility/src/test/java/com/spiritatlas/feature/compatibility/CompatibilityViewModelTest.kt`

**What to test:**
```kotlin
// HomeViewModel tests:
- Initial state is correct
- Navigation events trigger correctly
- Profile loading works

// CompatibilityViewModel tests:
- Two profiles comparison works
- 12-dimension analysis returns results
- Error handling for missing profiles
```

---

### Task 6: Add Network Security Config
**File to create:**
- `app/src/main/res/xml/network_security_config.xml`

**Content needed:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">openrouter.ai</domain>
        <pin-set expiration="2025-12-31">
            <!-- Add actual SHA-256 pins -->
            <pin digest="SHA-256">AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=</pin>
        </pin-set>
    </domain-config>
    <!-- Allow localhost for Ollama (local AI) -->
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="false">localhost</domain>
        <domain includeSubdomains="false">127.0.0.1</domain>
    </domain-config>
</network-security-config>
```

**Also update AndroidManifest.xml:**
```xml
<application
    android:networkSecurityConfig="@xml/network_security_config"
    ...>
```

---

### Task 7: Create Missing Repository Tests
**Files to create:**
- `data/src/test/java/com/spiritatlas/data/repository/UserRepositoryImplTest.kt`
- `data/src/test/java/com/spiritatlas/data/repository/ConsentRepositoryImplTest.kt`

**What to test:**
```kotlin
// UserRepositoryImpl tests:
- CRUD operations (create, read, update, delete profiles)
- Profile completion calculation
- Entity to domain mapping
- Error handling

// ConsentRepositoryImpl tests:
- Consent status persistence
- Encrypted storage works
- StateFlow updates correctly
```

---

### Task 8: Create AI Provider Tests
**Files to create:**
- `data/src/test/java/com/spiritatlas/data/ai/OpenRouterProviderTest.kt`
- `data/src/test/java/com/spiritatlas/data/ai/OllamaProviderTest.kt`

**What to test:**
```kotlin
// OpenRouterProvider tests:
- API key validation
- Request building
- Response parsing
- Error handling (network errors, rate limits)
- Consent check before API call

// OllamaProvider tests:
- Local server connection
- Model selection
- Fallback behavior when server unavailable
```

---

## Test Dependencies to Add (if not present)

```kotlin
// In each module's build.gradle.kts testImplementation block:
testImplementation("junit:junit:4.13.2")
testImplementation("io.mockk:mockk:1.13.8")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
testImplementation("app.cash.turbine:turbine:1.0.0") // For Flow testing
testImplementation("com.google.truth:truth:1.1.5")
```

---

## Coordination Rules

1. **ONLY work on files listed in YOUR ASSIGNED TASKS**
2. **Create new test files, don't modify existing source files**
3. **Use Vitest/JUnit assertion style as per CLAUDE.md**
4. **Target 80%+ coverage for modules you test**
5. **Run tests locally before committing**

---

## Communication

When done, create a summary file:
`CODEX_MAX_COMPLETED.md` with:
- List of files created/modified
- Test coverage achieved per module
- Any issues encountered

This allows Claude to see what was completed when it syncs.

---

## Git Branch

Work on branch: `codex-max/testing-coverage`
```bash
git checkout -b codex-max/testing-coverage
# Do your work
git add .
git commit -m "test: Add comprehensive unit tests for core modules

- Numerology calculator tests
- Astrology calculator tests
- Ayurveda module tests
- Human Design module tests
- ViewModel tests
- Repository tests
- AI provider tests

🤖 Generated with Codex Max"
```

Claude will merge after review.
