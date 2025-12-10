# SpiritAtlas Startup Optimization Guide

## Overview

This document provides comprehensive guidance for optimizing SpiritAtlas app startup performance using Android Baseline Profiles and related optimization techniques.

**Target Metrics:**
- **Cold Start:** < 2.0 seconds
- **Warm Start:** < 1.0 second

**Current Status:**
- Baseline Profile: ✅ **ENABLED**
- Profile Location: `app/src/main/baseline-prof.txt`
- Profile Entries: 54 methods optimized
- ProfileInstaller: 1.4.1

---

## Table of Contents

1. [What is a Baseline Profile?](#what-is-a-baseline-profile)
2. [Current Configuration](#current-configuration)
3. [Measuring Startup Time](#measuring-startup-time)
4. [Updating the Baseline Profile](#updating-the-baseline-profile)
5. [Interpreting Profile Rules](#interpreting-profile-rules)
6. [Optimization Checklist](#optimization-checklist)
7. [Advanced Techniques](#advanced-techniques)
8. [Troubleshooting](#troubleshooting)

---

## What is a Baseline Profile?

A **Baseline Profile** is a list of classes and methods that Android Runtime (ART) should pre-compile ahead-of-time (AOT) instead of just-in-time (JIT). This significantly improves:

- **Cold Start Time:** First launch after installation
- **Warm Start Time:** Launch after app was killed
- **Runtime Performance:** Methods execute faster (no JIT overhead)
- **Battery Efficiency:** Less CPU usage during startup

### How It Works

1. **Profile Creation:** You identify critical methods executed during startup
2. **Profile Compilation:** The profile is bundled in your APK (`baseline-prof.txt`)
3. **Installation:** When users install your app, Android uses the profile to pre-compile code
4. **Benefit:** Pre-compiled code runs immediately without JIT warmup

### Performance Impact

Typical improvements with a well-optimized baseline profile:
- **Cold Start:** 15-30% faster
- **Warm Start:** 10-20% faster
- **Frame Drops:** 20-40% reduction during startup animations

---

## Current Configuration

### Gradle Setup (`app/build.gradle.kts`)

```kotlin
android {
    // Baseline Profile Configuration
    sourceSets {
        getByName("main") {
            baselineProfiles.srcDirs("src/main")
        }
    }
}

dependencies {
    // Baseline Profile Support Library
    implementation(libs.androidx.profileinstaller)
}
```

### Profile Location

- **File:** `app/src/main/baseline-prof.txt`
- **Format:** Human-readable text file with method signatures
- **Size:** ~2.6 KB (54 optimized methods)

### What's Currently Optimized

Our baseline profile targets the critical startup path:

1. **Application Initialization**
   - `SpiritAtlasApplication` onCreate
   - Hilt DI initialization

2. **Splash Screen**
   - `SplashScreen` composable
   - `SplashViewModel` initialization
   - Animation state management

3. **Navigation**
   - `SpiritAtlasNavGraph` setup
   - Navigation controller initialization

4. **Home Screen**
   - First user-facing screen components
   - HomeViewModel initialization

5. **Core UI**
   - Theme setup
   - Modern components
   - Compose runtime hot paths

6. **Memory Management**
   - Memory manager initialization
   - Cache setup

---

## Measuring Startup Time

### Automated Measurement Script

We provide a comprehensive startup measurement script:

```bash
./scripts/measure_startup_time.sh
```

**What it measures:**
- Cold start time (5 iterations with app data cleared)
- Warm start time (5 iterations without data clearing)
- Average times with pass/fail evaluation
- Baseline profile status
- Device information

**Example Output:**

```
=========================================
SpiritAtlas Startup Time Measurement
=========================================

=== Cold Start Measurements ===
  Time: 1823ms
  Time: 1798ms
  Time: 1845ms
  Time: 1812ms
  Time: 1801ms

Cold Start Average: 1816ms

=== Warm Start Measurements ===
  Time: 892ms
  Time: 878ms
  Time: 901ms
  Time: 885ms
  Time: 894ms

Warm Start Average: 890ms

=========================================
Summary
=========================================

Cold Start:
  Average: 1816ms
  Target:  < 2000ms
  Status:  PASS ✓

Warm Start:
  Average: 890ms
  Target:  < 1000ms
  Status:  PASS ✓

Baseline Profile:
  Status:   ENABLED ✓
  Entries:  54 methods
  Location: app/src/main/baseline-prof.txt
```

### Manual Measurement with ADB

```bash
# Cold start
adb shell am force-stop com.spiritatlas.app
adb shell pm clear com.spiritatlas.app
adb shell am start -W -n com.spiritatlas.app/.MainActivity

# Look for "TotalTime" in output
```

### Android Studio Profiler

1. **Open Android Studio**
2. **Run > Profile 'app'**
3. **Select 'Startup' profiler**
4. **Force stop app**
5. **Click 'Record' and launch app**
6. **Stop recording after home screen appears**

The profiler will show:
- Method execution times
- Thread activity
- System traces
- Frame rendering

---

## Updating the Baseline Profile

### When to Update

Update your baseline profile when:
- ✅ Adding new features to the startup flow
- ✅ Refactoring initialization code
- ✅ Startup time regresses
- ✅ Adding new critical screens
- ✅ Major dependency updates

### Method 1: Android Studio Profiler (Recommended)

1. **Profile App Startup**
   ```
   Run > Profile 'app' with startup profiling
   ```

2. **Identify Hot Methods**
   - Look for methods with high execution time
   - Focus on methods in your package (`com.spiritatlas.*`)
   - Ignore system framework methods (already optimized)

3. **Add to Profile**
   - Convert method names to profile format
   - Add with appropriate flags (H, S, P, L)

4. **Test Impact**
   ```bash
   ./gradlew clean assembleRelease
   ./scripts/measure_startup_time.sh
   ```

### Method 2: Macrobenchmark (Advanced)

Create a separate benchmark module:

```kotlin
// benchmark/build.gradle.kts
plugins {
    id("com.android.test")
    id("androidx.baselineprofile")
}

dependencies {
    implementation("androidx.benchmark:benchmark-macro-junit4:1.2.3")
}
```

```kotlin
// StartupBenchmark.kt
@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }
}
```

Generate profile:
```bash
./gradlew :benchmark:generateBaselineProfile
```

### Method 3: Manual Analysis

1. **Enable Method Tracing**
   ```kotlin
   // In SpiritAtlasApplication.onCreate()
   if (BuildConfig.DEBUG) {
       Debug.startMethodTracing("startup")
   }
   ```

2. **Launch App and Stop Trace**
   ```kotlin
   // In MainActivity.onCreate() after setContent
   if (BuildConfig.DEBUG) {
       Debug.stopMethodTracing()
   }
   ```

3. **Pull Trace File**
   ```bash
   adb pull /sdcard/Android/data/com.spiritatlas.app/files/startup.trace
   ```

4. **Analyze with Android Studio**
   - Open `.trace` file
   - Sort by "Total Time"
   - Add hot methods to baseline profile

---

## Interpreting Profile Rules

### Rule Format

```
[FLAGS][CLASS];->[METHOD]([PARAMETERS])[RETURN]
```

### Flags

- **H** = Hot method (always compile, runs very frequently)
- **S** = Startup method (compile during app startup)
- **P** = Post-startup method (compile after startup completes)
- **L** = Layout method (compile during layout phase)

### Examples

```
# Hot startup method - Application initialization
HSPLcom/spiritatlas/app/SpiritAtlasApplication;->onCreate()V

# Startup-only method - Splash screen setup
HSPLcom/spiritatlas/app/SplashScreen;->SplashScreen(...)V

# Post-startup method - Memory trimming (not critical)
PLcom/spiritatlas/app/SpiritAtlasApplication;->onTrimMemory(I)V

# Compose runtime hot path
PLandroidx/compose/runtime/ComposerImpl;->startRestartGroup(I)Landroidx/compose/runtime/Composer;
```

### Best Practices

1. **Use HSPL for critical path methods**
   - Application.onCreate()
   - MainActivity.onCreate()
   - First screen composables
   - ViewModel initialization

2. **Use SPL for startup-only methods**
   - Splash screen
   - Navigation setup
   - Initial data loading

3. **Use PL for non-critical methods**
   - Memory management callbacks
   - Background initialization
   - Analytics tracking

4. **Use wildcards sparingly**
   ```
   # Good: Specific method
   HSPLcom/spiritatlas/core/ui/theme/ColorKt;->getColorScheme()Landroidx/compose/material3/ColorScheme;

   # OK: Multiple related methods in one file
   PLcom/spiritatlas/core/ui/components/ModernComponentsKt;->*

   # Avoid: Too broad, increases binary size
   HSPLcom/spiritatlas/**;->*
   ```

---

## Optimization Checklist

### Startup Performance

- [x] **Baseline profile enabled** (`profileinstaller` dependency)
- [x] **Profile includes Application.onCreate()**
- [x] **Profile includes MainActivity.onCreate()**
- [x] **Profile includes first screen composables**
- [x] **Profile includes ViewModel initialization**
- [ ] **Defer non-critical initialization**
- [ ] **Use lazy initialization for heavy objects**
- [ ] **Avoid synchronous I/O in onCreate()**
- [ ] **Use WorkManager for background tasks**

### Compose Optimization

- [x] **Use `@Stable` and `@Immutable` annotations**
- [x] **Profile includes Compose runtime hot paths**
- [ ] **Minimize recompositions with `derivedStateOf`**
- [ ] **Use `key()` for lists to prevent unnecessary recompositions**
- [ ] **Avoid lambda allocations in hot paths**
- [ ] **Use `remember` for expensive calculations**

### Hilt/DI Optimization

- [x] **Profile includes Hilt DI initialization**
- [ ] **Use `@Singleton` for app-level dependencies**
- [ ] **Avoid eager initialization of singletons**
- [ ] **Profile constructor injection hot paths**

### Memory Optimization

- [x] **Memory manager initialization profiled**
- [ ] **Implement memory cache size limits**
- [ ] **Use weak references for caches**
- [ ] **Clear caches on low memory**

### Image Loading

- [x] **Using Coil (optimized for Compose)**
- [ ] **Preload critical images during splash**
- [ ] **Use appropriate image sizes**
- [ ] **Enable disk and memory caching**

---

## Advanced Techniques

### 1. Startup Tracing in Production

Use Firebase Performance Monitoring to track real-world startup times:

```kotlin
// In SpiritAtlasApplication.onCreate()
val trace = Firebase.performance.newTrace("app_startup")
trace.start()

// In MainActivity.onCreate() after first screen renders
trace.stop()
```

### 2. Staged Initialization

Break initialization into stages:

```kotlin
class SpiritAtlasApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Stage 1: Critical (blocks UI)
        initializeCritical()

        // Stage 2: Important (background thread)
        lifecycleScope.launch {
            initializeImportant()
        }

        // Stage 3: Deferrable (after first screen)
        lifecycleScope.launch {
            delay(2000)
            initializeDeferrable()
        }
    }
}
```

### 3. Profile-Guided Optimization (PGO)

Enable R8 PGO for even better optimization:

```kotlin
// app/build.gradle.kts
android {
    buildTypes {
        release {
            isMinifyEnabled = true

            // R8 will use baseline profile for optimization hints
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

### 4. Content Provider Elimination

Avoid ContentProvider initialization overhead:

```xml
<!-- AndroidManifest.xml -->
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    android:exported="false"
    tools:node="merge">

    <!-- Disable slow initializers -->
    <meta-data
        android:name="androidx.work.WorkManagerInitializer"
        android:value="androidx.startup"
        tools:node="remove" />
</provider>
```

### 5. Splash Screen Optimization

Use Android 12+ Splash Screen API:

```kotlin
// In MainActivity
override fun onCreate(savedInstanceState: Bundle?) {
    // Keep splash visible until ready
    val splashScreen = installSplashScreen()

    splashScreen.setKeepOnScreenCondition {
        !viewModel.isReady.value
    }

    super.onCreate(savedInstanceState)
}
```

---

## Troubleshooting

### Profile Not Applied

**Symptom:** No startup improvement after adding baseline profile

**Solutions:**

1. **Verify profile is included in APK**
   ```bash
   unzip -l app/build/outputs/apk/release/app-release.apk | grep baseline-prof
   ```

2. **Check ProfileInstaller dependency**
   ```bash
   ./gradlew :app:dependencies | grep profileinstaller
   ```

3. **Test on Android 7+ (API 24+)**
   - Baseline profiles require ART (Android Runtime)
   - Not supported on Android 6 and below

4. **Check LogCat for profile installation**
   ```bash
   adb logcat | grep ProfileInstaller
   ```

### Startup Still Slow

**Symptom:** Startup time above target despite profile

**Diagnosis:**

1. **Profile the startup again**
   ```bash
   ./scripts/measure_startup_time.sh
   ```

2. **Check for slow methods not in profile**
   - Use Android Studio Profiler
   - Look for methods > 50ms execution time
   - Add to baseline profile

3. **Look for blocking I/O**
   ```bash
   adb logcat | grep StrictMode
   ```

4. **Check for synchronous network calls**
   - All network calls should be async
   - Use coroutines or RxJava

5. **Verify R8 optimization**
   ```kotlin
   // In app/build.gradle.kts
   buildTypes {
       release {
           isMinifyEnabled = true // Should be true
           isShrinkResources = true // Should be true
       }
   }
   ```

### Profile Syntax Errors

**Symptom:** Build fails with profile parsing errors

**Solutions:**

1. **Check for typos in method signatures**
   - Method names are case-sensitive
   - Parameter types must match exactly

2. **Validate with lint**
   ```bash
   ./gradlew :app:lintRelease
   ```

3. **Use generated profiles from macrobenchmark**
   - Less error-prone than manual creation

### Performance Regression

**Symptom:** Startup time increased after changes

**Solutions:**

1. **Compare with previous measurements**
   ```bash
   git diff HEAD~1 app/src/main/baseline-prof.txt
   ```

2. **Bisect to find regression**
   ```bash
   git bisect start
   git bisect bad HEAD
   git bisect good <last-known-good-commit>
   # Test each commit with startup script
   ```

3. **Update baseline profile**
   - Your changes may require new methods to be profiled
   - Re-run profiler and update profile

---

## Performance Targets & Current Status

### Baseline Measurements

| Metric | Target | Current | Status |
|--------|--------|---------|--------|
| Cold Start | < 2000ms | TBD | ⏳ Pending |
| Warm Start | < 1000ms | TBD | ⏳ Pending |
| First Frame | < 500ms | TBD | ⏳ Pending |
| Time to Interactive | < 3000ms | TBD | ⏳ Pending |

**To measure:** Run `./scripts/measure_startup_time.sh`

### Historical Performance

Track performance over time:

```bash
# Create performance log entry
echo "$(date +%Y-%m-%d) - Cold: XXXms, Warm: XXXms - $(git rev-parse --short HEAD)" >> PERFORMANCE_LOG.txt
```

### Comparison with Industry

Industry benchmarks (Android apps, 2024):

- **Excellent:** Cold < 1.5s, Warm < 800ms
- **Good:** Cold < 2.0s, Warm < 1.0s
- **Average:** Cold < 3.0s, Warm < 1.5s
- **Poor:** Cold > 3.0s, Warm > 1.5s

**SpiritAtlas Target:** Good to Excellent range

---

## Next Steps

1. **Measure Current Performance**
   ```bash
   ./scripts/measure_startup_time.sh
   ```

2. **Document Results**
   - Update "Baseline Measurements" table above
   - Add results to git commit message

3. **Optimize if Needed**
   - If below target, follow "Updating the Baseline Profile" section
   - Use Android Studio Profiler to identify bottlenecks
   - Add new hot methods to profile

4. **Continuous Monitoring**
   - Run startup tests before major releases
   - Track performance in CI/CD pipeline
   - Monitor real-world startup times via Firebase

5. **Advanced Optimization**
   - Implement macrobenchmark for automated profile generation
   - Enable R8 full mode for aggressive optimization
   - Consider code splitting for large features

---

## Resources

### Documentation

- [Android Baseline Profiles Guide](https://developer.android.com/topic/performance/baselineprofiles)
- [Jetpack Compose Performance](https://developer.android.com/jetpack/compose/performance)
- [App Startup Best Practices](https://developer.android.com/topic/performance/vitals/launch-time)

### Tools

- **Android Studio Profiler:** Built-in profiling tools
- **Macrobenchmark:** Automated baseline profile generation
- **Firebase Performance:** Real-world performance monitoring
- **Perfetto:** System-level performance tracing

### Internal Documentation

- `app/src/main/baseline-prof.txt` - Current baseline profile
- `scripts/measure_startup_time.sh` - Automated measurement script
- `CLAUDE.md` - Project development guidelines
- `app/build.gradle.kts` - Baseline profile configuration

---

## Changelog

| Date | Version | Changes | Startup Impact |
|------|---------|---------|----------------|
| 2025-12-10 | 1.0.0 | Initial baseline profile setup | TBD |

---

**Last Updated:** 2025-12-10
**Maintained By:** SpiritAtlas Development Team
**Questions?** See `CLAUDE.md` for project guidelines
