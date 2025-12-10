# AGENT FIX COORDINATION
Build Coordinator: BUILD FIX AGENT 7
Last Update: 2025-12-10 04:58 PST

## AGENT STATUS BOARD

| Agent ID | Task | Status | File | ETA |
|----------|------|--------|------|-----|
| **AGENT 1** | Clean kapt cache | UNASSIGNED | - | 2 min |
| **AGENT 2** | Fix FakeUserRepository | UNASSIGNED | CompatibilityViewModelTest.kt | 5 min |
| **AGENT 3** | Fix UserRepositoryImpl nulls | UNASSIGNED | UserRepositoryImpl.kt | 15 min |
| **AGENT 4** | Fix ConsentDebugViewModelTest | UNASSIGNED | ConsentDebugViewModelTest.kt | 10 min |
| **AGENT 5** | Fix HomeViewModelTest | UNASSIGNED | HomeViewModelTest.kt | 10 min |
| **AGENT 6** | Fix Room schema config | UNASSIGNED | data/build.gradle.kts | 5 min |
| **AGENT 7** | Code quality cleanup | UNASSIGNED | Multiple files | 30 min |

---

## CRITICAL PATH (DO THESE FIRST)

### PHASE 1: Unblock Compilation (URGENT)
**Timeline**: 0-10 minutes
**Blocking**: ALL downstream work

#### Task 1.1: Clean Build Cache
**Agent**: Any available
**Priority**: P0 - CRITICAL
**Commands**:
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
./gradlew clean
rm -rf .gradle/
rm -rf app/build/
./gradlew :app:compileDebugKotlin
```
**Success Criteria**: App module compiles
**Status**: WAITING

#### Task 1.2: Fix FakeUserRepository
**Agent**: Any agent with file editing capability
**Priority**: P1 - HIGH
**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/feature/compatibility/src/test/java/com/spiritatlas/feature/compatibility/CompatibilityViewModelTest.kt`
**Line**: Insert after line 171

**Code to Add**:
```kotlin
    override suspend fun exportAllProfilesToJson(): Result<String> =
        Result.success("{}")

    override suspend fun importProfilesFromJson(json: String): Result<Int> =
        Result.success(0)
```

**Verification**:
```bash
./gradlew :feature:compatibility:testDebugUnitTest
```
**Status**: WAITING

---

### PHASE 2: Fix Data Layer (HIGH PRIORITY)
**Timeline**: 10-30 minutes
**Blocking**: Data integrity, null safety

#### Task 2.1: Fix UserRepositoryImpl Type Mismatches
**Agent**: NULL SAFETY AGENT or experienced Kotlin agent
**Priority**: P1 - HIGH
**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/data/src/main/java/com/spiritatlas/data/repository/UserRepositoryImpl.kt`
**Lines**: 433-538 (43 errors)

**Error Pattern**:
```
Type mismatch: inferred type is Nothing? but String was expected
```

**Investigation Required**:
1. Read lines 430-540
2. Identify null handling issues
3. Add null coalescing operators (`?:`) or safe calls (`?.`)
4. Ensure all String fields have default values

**Verification**:
```bash
./gradlew :data:compileDebugKotlin
```
**Status**: WAITING

#### Task 2.2: Configure Room Schema Export
**Agent**: DATABASE AGENT
**Priority**: P2 - MEDIUM
**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/data/build.gradle.kts`

**Code to Add**:
```kotlin
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
```

**Verification**:
```bash
./gradlew :data:kspDebugKotlin 2>&1 | grep -i "schema"
# Should: No warnings
```
**Status**: WAITING

---

### PHASE 3: Fix Test Failures (MEDIUM PRIORITY)
**Timeline**: 30-60 minutes
**Blocking**: Test suite health

#### Task 3.1: Fix ConsentDebugViewModelTest
**Agent**: TEST FIX AGENT
**Priority**: P2 - MEDIUM
**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/feature/consent/src/test/java/com/spiritatlas/feature/consent/ConsentDebugViewModelTest.kt`

**Failures**:
- debugText updates when consent changes
- debugText shows all consents granted
- debugText shows all consent statuses
- debugText shows all consents denied

**Investigation**:
```bash
./gradlew :feature:consent:testDebugUnitTest --tests "*ConsentDebugViewModelTest*"
```

**Root Cause**: Unknown - requires reading test and ViewModel
**Status**: WAITING

#### Task 3.2: Fix HomeViewModelTest
**Agent**: TEST FIX AGENT
**Priority**: P2 - MEDIUM
**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/feature/home/src/test/java/com/spiritatlas/feature/home/HomeViewModelTest.kt`

**Failures** (all provider-related):
- provider label updates when mode changes to CLAUDE
- provider label updates when mode changes to OPENAI
- provider label updates when mode changes to OPENROUTER
- provider label updates when mode changes to GROQ

**Root Cause**: Likely enum or label mapping changed
**Investigation**: Check HomeViewModel.kt for provider label logic
**Status**: WAITING

---

### PHASE 4: Code Quality (LOW PRIORITY)
**Timeline**: 60-120 minutes
**Blocking**: Nothing (quality only)

#### Task 4.1: Fix Lint Errors
**Agent**: CODE QUALITY AGENT
**Priority**: P3 - LOW
**Modules**:
- :data:lintDebug
- :core:ui:lintDebug
- :feature:settings:lintAnalyzeDebug
- :feature:home:lintAnalyzeDebug
- :feature:compatibility:lintAnalyzeDebug
- :feature:onboarding:lintReportDebug
- :feature:consent:lintReportDebug
- :feature:tantric:lintReportDebug
- :feature:profile:lintAnalyzeDebug

**Common Issues**:
- Unused parameters (prefix with `_`)
- Deprecated APIs (update to modern equivalents)
- Missing @OptIn annotations

**Status**: DEFERRED

#### Task 4.2: Clean Up Warnings
**Agent**: CODE CLEANUP AGENT
**Priority**: P3 - LOW
**Scope**: 200+ warnings across codebase

**Categories**:
- Unused variables (remove or use)
- Deprecated API calls (modernize)
- Missing @OptIn annotations (add)
- Unnecessary safe calls (simplify)

**Status**: DEFERRED

---

## DEPENDENCY GRAPH

```
Task 1.1 (Clean Cache)
    ↓
Task 1.2 (Fix FakeUserRepository)
    ↓
[Phase 1 Complete - Compilation Works]
    ↓
Task 2.1 (Fix UserRepositoryImpl) ──→ Task 2.2 (Room Schema)
    ↓
[Phase 2 Complete - Data Layer Works]
    ↓
Task 3.1 (Consent Tests) ──→ Task 3.2 (Home Tests)
    ↓
[Phase 3 Complete - Tests Pass]
    ↓
Task 4.1 (Lint) ──→ Task 4.2 (Warnings)
    ↓
[Phase 4 Complete - Code Quality High]
```

---

## COMMUNICATION PROTOCOL

### When Starting a Task
Update this file:
```markdown
| Agent ID | Task | Status | File | ETA |
| AGENT_X | Task Name | IN_PROGRESS | file.kt | 5 min |
```

### When Completing a Task
Update this file:
```markdown
| Agent ID | Task | Status | File | ETA |
| AGENT_X | Task Name | DONE ✓ | file.kt | - |
```

### When Blocked
Create a note in this file:
```markdown
## BLOCKERS
- Agent X blocked on Task Y: Reason
- Waiting for: Agent Z to complete Task A
```

---

## VERIFICATION CHECKPOINTS

### Checkpoint 1: After Phase 1
```bash
./gradlew :app:compileDebugKotlin :feature:compatibility:compileDebugKotlin
```
**Expected**: Both compile successfully

### Checkpoint 2: After Phase 2
```bash
./gradlew :data:compileDebugKotlin
```
**Expected**: Data module compiles with no errors

### Checkpoint 3: After Phase 3
```bash
./gradlew test
```
**Expected**: All tests pass (or at least run without compilation errors)

### Checkpoint 4: After Phase 4
```bash
./gradlew build lint
```
**Expected**: Full build passes with < 50 warnings

---

## FINAL VERIFICATION

Run this to verify all fixes:
```bash
# Full clean build
./gradlew clean build

# Critical tests
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

# Feature tests
./gradlew :feature:home:test :feature:compatibility:test :feature:consent:test

# Lint check
./gradlew lintDebug
```

**Success Criteria**:
- [ ] 0 compilation errors
- [ ] All core module tests pass
- [ ] All feature module tests pass (or investigated)
- [ ] < 50 lint warnings
- [ ] Build time < 3 minutes

---

## NOTES FOR AGENTS

1. **Always update this file** when starting/completing a task
2. **Test after each fix** - Don't batch fixes without testing
3. **Use verification commands** provided in each task
4. **Report blockers immediately** - Don't work in silence
5. **Phase 1 must complete first** - Everything else depends on it
6. **UserRepositoryImpl needs investigation** - Don't guess, read the code
7. **Tests may reveal more issues** - Be prepared for follow-up work

---

## QUICK REFERENCE

**Full build**: `./gradlew build`
**Clean build**: `./gradlew clean build`
**Test module**: `./gradlew :module:test`
**Compile module**: `./gradlew :module:compileDebugKotlin`
**Lint module**: `./gradlew :module:lintDebug`

**Error logs**: `build_output.log` (7.3MB)
**Full report**: `COMPILATION_ERRORS_COMPLETE.md`
**Quick fixes**: `QUICK_FIX_GUIDE.md`

---

**Build Coordinator**: BUILD FIX AGENT 7
**Status**: Coordination document ready
**Next**: Waiting for agents to claim tasks
