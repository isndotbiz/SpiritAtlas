# QUICK FIX GUIDE - BUILD ERRORS
Generated: 2025-12-10 04:58 PST

## IMMEDIATE FIX (5 minutes)

### Problem
Build is failing due to kapt cache corruption in `:app` module.

Error:
```
java.nio.file.NoSuchFileException: .../app/build/tmp/kapt3/incrementalData/debug/com
```

### Solution
Run these commands in order:

```bash
# 1. Clean all build artifacts
./gradlew clean

# 2. Remove Gradle cache (nuclear option)
rm -rf .gradle/
rm -rf app/build/

# 3. Rebuild app module
./gradlew :app:compileDebugKotlin

# 4. If that works, run full build
./gradlew build
```

**Expected Result**: App module should compile successfully.

---

## FIX #2 - Test Compilation Error (5 minutes)

### Problem
FakeUserRepository in tests missing new interface methods.

File: `feature/compatibility/src/test/java/com/spiritatlas/feature/compatibility/CompatibilityViewModelTest.kt:153`

### Solution
Add these two methods to the `FakeUserRepository` class (around line 172):

```kotlin
override suspend fun exportAllProfilesToJson(): Result<String> =
    Result.success("{}")

override suspend fun importProfilesFromJson(json: String): Result<Int> =
    Result.success(0)
```

**Test Fix**:
```bash
./gradlew :feature:compatibility:testDebugUnitTest
```

---

## FIX #3 - UserRepositoryImpl Type Errors (15 minutes)

### Problem
43 type mismatch errors in UserRepositoryImpl.kt (lines 433-538)

Error:
```
Type mismatch: inferred type is Nothing? but String was expected
```

### Investigation Needed
Someone needs to read the file and fix the null handling:

```bash
# Read the problematic section
cat data/src/main/java/com/spiritatlas/data/repository/UserRepositoryImpl.kt | sed -n '430,540p'
```

**Likely Fix**: Add null coalescing or safe calls:
```kotlin
// Before (broken)
someField = nullableValue  // Error: Nothing? vs String

// After (fixed)
someField = nullableValue ?: ""  // OK
// or
someField = nullableValue.toString()  // OK
```

---

## VERIFICATION

After each fix, run:

```bash
# Test app compilation
./gradlew :app:compileDebugKotlin

# Test compatibility tests
./gradlew :feature:compatibility:testDebugUnitTest

# Full build
./gradlew build --continue
```

---

## SUCCESS CRITERIA

- [ ] App module compiles (Fix #1)
- [ ] Compatibility tests compile (Fix #2)
- [ ] Data module compiles (Fix #3)
- [ ] Full build passes with 0 compilation errors
- [ ] Tests run (may still fail, but should run)

---

## IF STILL FAILING

Run this to get fresh error output:

```bash
./gradlew build --continue 2>&1 | grep "^e: file" | head -20
```

Then check COMPILATION_ERRORS_COMPLETE.md for detailed analysis.
