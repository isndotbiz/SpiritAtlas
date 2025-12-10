# Build Performance Validation Checklist

Use this checklist to verify that build optimizations are working correctly.

## Pre-Validation Setup

- [ ] Stop all Gradle daemons: `./gradlew --stop`
- [ ] Clear build cache: `rm -rf .gradle/build-cache`
- [ ] Clear build directory: `./gradlew clean`
- [ ] Restart IDE (Android Studio)

## Validation Tests

### Test 1: Clean Build Performance
```bash
time ./gradlew clean assembleDebug --profile --build-cache
```

**Expected Results:**
- Build completes successfully
- Total time: <70 seconds ✅
- Profile report generated in `build/reports/profile/`
- No configuration cache warnings

**Actual Results:**
- [ ] Build succeeded
- [ ] Time: _______ seconds
- [ ] Profile report exists
- [ ] Notes: _______________

### Test 2: Incremental Build (No Changes)
```bash
# Run twice to test cache
time ./gradlew assembleDebug --build-cache
time ./gradlew assembleDebug --build-cache
```

**Expected Results:**
- First run: Cache builds from Test 1
- Second run: All tasks UP-TO-DATE or FROM-CACHE
- Second run time: <10 seconds ✅

**Actual Results:**
- [ ] First run time: _______ seconds
- [ ] Second run time: _______ seconds
- [ ] Cache working (UP-TO-DATE shown)
- [ ] Notes: _______________

### Test 3: Single File Change
```bash
# Make a small change in a feature module
echo "// comment" >> feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt
time ./gradlew assembleDebug --build-cache
```

**Expected Results:**
- Only changed module and app rebuild
- Build time: <25 seconds ✅
- Most tasks show FROM-CACHE

**Actual Results:**
- [ ] Time: _______ seconds
- [ ] Only necessary modules rebuilt
- [ ] Notes: _______________

### Test 4: Configuration Cache
```bash
# First build populates cache
./gradlew clean
time ./gradlew assembleDebug

# Second build reuses configuration
time ./gradlew assembleDebug
```

**Expected Results:**
- Second build shows "Reusing configuration cache"
- Configuration phase: <5 seconds ✅

**Actual Results:**
- [ ] Configuration cache message shown
- [ ] Configuration time: _______ seconds
- [ ] Notes: _______________

### Test 5: Parallel Execution
```bash
# Check Gradle daemon info
./gradlew assembleDebug --profile

# Open profile report and check:
# - Task execution → Parallel tasks
# - Should see multiple tasks running simultaneously
```

**Expected Results:**
- Profile shows parallel task execution
- Max workers: 8
- Multiple tasks in same time window

**Actual Results:**
- [ ] Parallel execution confirmed
- [ ] Worker count: _______
- [ ] Notes: _______________

### Test 6: Core Test Suite
```bash
time ./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test
```

**Expected Results:**
- All 113 tests pass
- Time: <30 seconds ✅
- Tests run in parallel

**Actual Results:**
- [ ] Tests passed: _______ / 113
- [ ] Time: _______ seconds
- [ ] Notes: _______________

## Configuration Verification

### gradle.properties Settings
```bash
grep -E "(jvmargs|parallel|workers|kapt|kotlin)" gradle.properties
```

**Verify:**
- [ ] Xmx8g (8GB heap)
- [ ] org.gradle.parallel=true
- [ ] org.gradle.workers.max=8
- [ ] kapt.use.worker.api=true
- [ ] kotlin.incremental=true

### settings.gradle.kts Settings
```bash
grep -A 5 "buildCache" settings.gradle.kts
```

**Verify:**
- [ ] isEnabled = true
- [ ] removeUnusedEntriesAfterDays = 30
- [ ] directory configured

## Performance Metrics

### Baseline Measurements

Record these for future comparison:

| Build Type | Time (seconds) | Date |
|------------|---------------|------|
| Clean build (no cache) | _______ | _____ |
| Clean build (with cache) | _______ | _____ |
| Incremental (1 file) | _______ | _____ |
| Incremental (module) | _______ | _____ |
| Core tests | _______ | _____ |

### Build Scan Analysis
```bash
./gradlew assembleDebug --scan
```

After build, open the scan URL and verify:
- [ ] Configuration time <5s
- [ ] Task execution time reasonable
- [ ] Cache hit rate >80% (incremental)
- [ ] Parallel execution visible

**Scan URL:** _______________________________

## Troubleshooting

If any tests fail:

### Build fails with OOM
```bash
# Increase heap
echo "org.gradle.jvmargs=-Xmx10g" >> gradle.properties
```

### Configuration cache issues
```bash
# Disable temporarily
./gradlew assembleDebug --no-configuration-cache
```

### Kapt errors
```bash
# Clear kapt cache
rm -rf build/generated/kaptKotlin
./gradlew clean assembleDebug
```

### Cache corruption
```bash
# Clear and rebuild
rm -rf .gradle/build-cache
./gradlew clean assembleDebug
```

## Sign-Off

**Validated by:** _______________________
**Date:** _______________________
**Build environment:**
- OS: _______________________
- CPU cores: _______________________
- RAM: _______________________
- Gradle version: _______________________
- AGP version: _______________________

**Overall Status:** [ ] PASS  [ ] FAIL  [ ] NEEDS REVIEW

**Notes:**
_____________________________________________
_____________________________________________
_____________________________________________

## Next Steps

After validation:
1. [ ] Commit changes to version control
2. [ ] Share BUILD_PERFORMANCE_REPORT.md with team
3. [ ] Schedule monthly performance review
4. [ ] Set up build time monitoring in CI/CD
5. [ ] Document any custom optimizations for your environment
