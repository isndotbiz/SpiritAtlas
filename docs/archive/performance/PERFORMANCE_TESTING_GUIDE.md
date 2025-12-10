# Performance Testing Guide - SpiritAtlas

## Table of Contents

1. [Overview](#overview)
2. [Setup & Prerequisites](#setup--prerequisites)
3. [Android Studio Profiler](#android-studio-profiler)
4. [Macrobenchmark Testing](#macrobenchmark-testing)
5. [Baseline Profiles](#baseline-profiles)
6. [Memory Profiling](#memory-profiling)
7. [UI Performance Testing](#ui-performance-testing)
8. [Network Performance Testing](#network-performance-testing)
9. [Battery Impact Testing](#battery-impact-testing)
10. [Continuous Performance Monitoring](#continuous-performance-monitoring)
11. [Performance Testing Checklist](#performance-testing-checklist)

---

## Overview

This guide provides step-by-step instructions for measuring, profiling, and testing the performance of the SpiritAtlas Android application. Follow these procedures before each release and when investigating performance issues.

**Testing Philosophy**: Measure, don't guess. Always profile before and after optimizations.

---

## Setup & Prerequisites

### Required Tools

1. **Android Studio Hedgehog or later** (2023.1.1+)
2. **Physical Android device** (Pixel 4+ or equivalent)
   - Minimum: Android 8.0 (API 26)
   - Recommended: Android 12+ (API 31+)
3. **adb** (Android Debug Bridge) in PATH
4. **Gradle 8.13+**

### Device Preparation

```bash
# 1. Enable Developer Options
# Settings > About Phone > Tap "Build Number" 7 times

# 2. Enable USB Debugging
# Settings > Developer Options > USB Debugging

# 3. Disable animations (for consistent benchmarking)
adb shell settings put global window_animation_scale 0
adb shell settings put global transition_animation_scale 0
adb shell settings put global animator_duration_scale 0

# 4. Set CPU governor to performance mode (requires root)
adb shell "echo performance > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"

# 5. Verify device connection
adb devices
```

### Build Configuration

```bash
# Always test with release builds for accurate performance
./gradlew :app:assembleRelease

# Install release build
adb install -r app/build/outputs/apk/release/app-release.apk
```

---

## Android Studio Profiler

### CPU Profiler

#### Measuring Startup Time

**Objective**: Measure cold start time and identify initialization bottlenecks

**Steps**:

1. Open Android Studio
2. Navigate to `View > Tool Windows > Profiler`
3. Click `+` > Select device and app
4. Click the CPU dropdown > Select "Callstack Sample Recording"
5. Force close the app:
   ```bash
   adb shell am force-stop com.spiritatlas.app
   ```
6. Click "Record" button
7. Launch the app:
   ```bash
   adb shell am start -n com.spiritatlas.app/.MainActivity
   ```
8. Wait for splash screen to complete and home screen to render
9. Click "Stop" recording

**Analysis**:

Look for these key events in the timeline:
- `Application.onCreate()` - Should be <200ms
- `SplashViewModel` initialization - Should be <100ms
- `MainActivity.onCreate()` - Should be <150ms
- First `SplashScreen` composition - Should be <100ms
- Navigation to `HomeScreen` - Should be <300ms

**Red Flags**:
- Any single method >50ms (except intentional delays)
- Excessive time in `Hilt` dependency injection
- Long database queries (should be none)
- Synchronous network calls (should be none)

#### Measuring Screen Navigation Performance

**Objective**: Measure transition performance between screens

**Steps**:

1. Start CPU recording in Profiler
2. Navigate from HomeScreen to ProfileListScreen
3. Navigate from ProfileListScreen to ProfileScreen
4. Navigate back to HomeScreen
5. Stop recording

**Target Metrics**:
- `NavHostController.navigate()` - <50ms
- Transition animation - 300-400ms (as designed)
- Destination screen first composition - <100ms

#### Profiling Hot Paths

**Objective**: Identify methods consuming most CPU time

**Steps**:

1. Use "Sample Java Methods" recording mode
2. Record for 30 seconds during normal app usage
3. Navigate through multiple screens
4. Scroll through lists
5. Stop recording

**Analysis**:
- Sort by "Self Time" to find expensive methods
- Look for:
  - Repeated allocations in loops
  - Unnecessary string operations
  - Complex calculations in UI thread
  - Inefficient collection operations

**Key Methods to Monitor**:
- `ChaldeanCalculator.calculateNameNumber()`
- `OptimizedCompatibilityAnalysisEngine.analyzeCompatibility()`
- `HomeScreen` composable functions
- Canvas drawing operations

### Memory Profiler

#### Detecting Memory Leaks

**Objective**: Identify activities/fragments that aren't garbage collected

**Steps**:

1. Open Memory Profiler
2. Launch the app
3. Navigate through all screens (Home > ProfileList > Profile > Back > Home)
4. Force garbage collection (click garbage truck icon)
5. Take heap dump (click camera icon)
6. In heap dump viewer:
   - Filter by "Activity" class
   - Check "Show only leaked instances"
   - Look for retained Activity instances

**Expected Results**:
- 0 leaked Activity instances
- 1 MainActivity instance (current)
- 0-1 ViewModel instances per screen

**Common Leak Sources**:
- `rememberCoroutineScope()` not cancelled
- Static references to Context
- Listeners not unregistered
- Infinite animations not cleaned up

#### Measuring Memory Allocation Rate

**Objective**: Identify excessive allocations causing GC pressure

**Steps**:

1. Open Memory Profiler
2. Enable "Record allocations" mode
3. Perform a specific action (e.g., scroll HomeScreen)
4. Record for 10-15 seconds
5. Stop recording

**Analysis**:
- Sort allocations by count
- Look for:
  - Repeated String allocations (use StringBuilder)
  - Boxing/unboxing of primitives
  - Unnecessary List/Array copies
  - Lambda allocations in loops

**Red Flags**:
- >1000 allocations/second during steady state
- Large allocations (>1MB) during scrolling
- Allocations of the same object repeatedly

#### Memory Consumption by Component

**Objective**: Understand memory distribution

**Steps**:

1. Take heap dump after app is fully loaded
2. Group by package:
   - `com.spiritatlas.app` - App code
   - `com.spiritatlas.core` - Core libraries
   - `com.spiritatlas.feature` - Feature modules
   - `androidx.compose` - Compose framework
3. Verify against budgets from PERFORMANCE_BENCHMARKS.md

**Target Distribution**:
```
App Base Memory: ~50MB
Compose UI: ~30-40MB
ViewModels + State: ~15-20MB
Cached Data: ~20MB
Images (when implemented): ~50MB
```

### Energy Profiler

#### Measuring Battery Impact

**Objective**: Ensure app doesn't drain battery excessively

**Steps**:

1. Open Energy Profiler
2. Launch app and use normally for 5 minutes
3. Monitor energy consumption by component:
   - CPU
   - Network
   - Location (should be 0)
   - Screen

**Target Metrics**:
- CPU usage: <5% during idle
- Network usage: Sporadic, only during AI enrichment
- Location: 0 (app doesn't use location)
- Total energy: <2% battery per hour of active use

**Red Flags**:
- Sustained CPU usage >10%
- Wake locks preventing device sleep
- Background network activity
- Unexpected location access

---

## Macrobenchmark Testing

### Setup Macrobenchmark Module

Add to `settings.gradle.kts`:

```kotlin
include(":benchmark")
```

Create `benchmark/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.spiritatlas.benchmark"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
        }
    }
}

dependencies {
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.uiautomator)
}
```

### Startup Benchmark Test

Create `benchmark/src/main/java/com/spiritatlas/benchmark/StartupBenchmark.kt`:

```kotlin
package com.spiritatlas.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupNoCompilation() {
        benchmark(CompilationMode.None())
    }

    @Test
    fun startupBaselineProfile() {
        benchmark(CompilationMode.Partial())
    }

    @Test
    fun startupFullCompilation() {
        benchmark(CompilationMode.Full())
    }

    private fun benchmark(compilationMode: CompilationMode) {
        benchmarkRule.measureRepeated(
            packageName = "com.spiritatlas.app",
            metrics = listOf(StartupTimingMetric()),
            iterations = 10,
            startupMode = StartupMode.COLD,
            compilationMode = compilationMode
        ) {
            pressHome()
            startActivityAndWait()
        }
    }
}
```

### Running Benchmarks

```bash
# Run all benchmark tests
./gradlew :benchmark:connectedBenchmarkAndroidTest

# View results in Android Studio
# Build > Analyze > Show Benchmark Results
```

**Expected Results**:

| Compilation Mode | Cold Start Time | Target |
|-----------------|-----------------|--------|
| None (first install) | ~3500ms | <4000ms |
| Partial (baseline profile) | ~2500ms | <2000ms |
| Full (after multiple runs) | ~1800ms | <1500ms |

### Frame Timing Benchmark

Create `benchmark/src/main/java/com/spiritatlas/benchmark/ScrollBenchmark.kt`:

```kotlin
package com.spiritatlas.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollHomeScreen() {
        benchmarkRule.measureRepeated(
            packageName = "com.spiritatlas.app",
            metrics = listOf(FrameTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.WARM,
            compilationMode = CompilationMode.Partial()
        ) {
            startActivityAndWait()

            val lazyColumn = device.findObject(By.scrollable(true))
            lazyColumn?.setGestureMargin(device.displayWidth / 5)
            lazyColumn?.fling(Direction.DOWN)
            device.waitForIdle()
            lazyColumn?.fling(Direction.UP)
            device.waitForIdle()
        }
    }
}
```

**Target Metrics**:
- P50 frame time: <16ms (60 FPS)
- P95 frame time: <32ms (30 FPS acceptable)
- P99 frame time: <48ms (20 FPS rare)
- Janky frames: <1%

---

## Baseline Profiles

### Generating Baseline Profiles

Baseline profiles improve startup time by pre-compiling frequently used code paths.

**Step 1**: Create baseline profile generator

Create `benchmark/src/main/java/com/spiritatlas/benchmark/BaselineProfileGenerator.kt`:

```kotlin
package com.spiritatlas.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generateBaselineProfile() = baselineProfileRule.collect(
        packageName = "com.spiritatlas.app"
    ) {
        // Cold start
        pressHome()
        startActivityAndWait()

        // Navigate to ProfileList
        device.findObject(By.desc("Create Profile")).click()
        device.waitForIdle()

        // Navigate to Profile detail
        device.findObject(By.text("Add New")).click()
        device.waitForIdle()

        // Scroll through profile form
        val scrollable = device.findObject(By.scrollable(true))
        scrollable?.fling(Direction.DOWN)
        device.waitForIdle()

        // Navigate back
        device.pressBack()
        device.waitForIdle()
        device.pressBack()
        device.waitForIdle()

        // Scroll home screen
        val homeList = device.findObject(By.scrollable(true))
        homeList?.fling(Direction.DOWN)
        device.waitForIdle()
    }
}
```

**Step 2**: Run generator

```bash
./gradlew :benchmark:generateBaselineProfile
```

**Step 3**: Copy generated profile

The baseline profile will be generated at:
```
app/src/main/baseline-prof.txt
```

**Step 4**: Verify improvement

Run startup benchmark before and after to measure improvement (expect 20-30% reduction in cold start time).

---

## Memory Profiling

### Memory Leak Detection with LeakCanary

**Step 1**: Add LeakCanary dependency

In `app/build.gradle.kts`:

```kotlin
dependencies {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}
```

**Step 2**: Run app in debug mode

LeakCanary automatically detects leaks and shows notifications.

**Step 3**: Perform leak-prone actions

- Navigate between screens 10 times
- Rotate device multiple times
- Background and foreground app repeatedly
- Start and cancel long-running operations

**Expected Result**: 0 leaks detected

### Manual Memory Testing Procedure

**Objective**: Verify memory doesn't grow unbounded

**Steps**:

1. Launch app and note initial memory (baseline)
2. Perform 50 navigation cycles:
   ```
   Home > ProfileList > Profile > Back > ProfileList > Back > Home
   ```
3. Force garbage collection
4. Check current memory usage

**Expected Results**:
- Memory growth <20MB after 50 cycles
- Memory returns to baseline ±10MB after GC
- No OutOfMemoryError

**Red Flags**:
- Linear memory growth with each cycle
- Memory not reclaimed after GC
- Total memory >200MB

---

## UI Performance Testing

### Compose Recomposition Profiling

**Step 1**: Enable layout inspector

In Android Studio:
1. Run app in debug mode
2. `Tools > Layout Inspector`
3. Click "Show Recomposition Counts"

**Step 2**: Identify recomposition hotspots

Navigate to HomeScreen and observe:
- Recomposition count for each composable
- Skipped compositions (good)
- Recompositions per second

**Target Metrics**:
- Total recompositions: <100/second during animations
- Individual composable recompositions: <10/second
- Skip rate: >50%

**Red Flags**:
- Composables recomposing on every frame (60/second)
- Zero skipped compositions
- Parent recomposition causing child recompositions

### Frame Rate Measurement

**Using adb**:

```bash
# Monitor frame stats in real-time
adb shell dumpsys gfxinfo com.spiritatlas.app framestats

# Analyze specific screen
adb shell dumpsys gfxinfo com.spiritatlas.app reset
# Perform action (e.g., scroll)
adb shell dumpsys gfxinfo com.spiritatlas.app framestats > frame_stats.txt
```

**Analyze output**:
- Look for frame times >16ms (60 FPS target)
- Calculate P50, P95, P99 percentiles
- Identify janky frames (>32ms)

**Python script to analyze** (create `analyze_frames.py`):

```python
import sys

def analyze_frames(file_path):
    with open(file_path, 'r') as f:
        lines = f.readlines()

    frame_times = []
    for line in lines:
        if line.startswith('0,'):
            parts = line.split(',')
            if len(parts) >= 13:
                # Total frame time in ms
                frame_time = (int(parts[13]) - int(parts[1])) / 1_000_000
                frame_times.append(frame_time)

    frame_times.sort()
    count = len(frame_times)

    print(f"Total frames: {count}")
    print(f"P50 (median): {frame_times[int(count * 0.5)]:.2f}ms")
    print(f"P95: {frame_times[int(count * 0.95)]:.2f}ms")
    print(f"P99: {frame_times[int(count * 0.99)]:.2f}ms")

    janky = sum(1 for t in frame_times if t > 16)
    print(f"Janky frames (>16ms): {janky} ({janky/count*100:.1f}%)")

if __name__ == "__main__":
    analyze_frames(sys.argv[1])
```

**Run analysis**:
```bash
python analyze_frames.py frame_stats.txt
```

---

## Network Performance Testing

### Measuring AI Request Latency

**Using Charles Proxy or Android Studio Network Inspector**:

1. Open `View > Tool Windows > App Inspection > Network Inspector`
2. Perform AI enrichment request
3. Observe:
   - Connection time
   - Time to first byte
   - Total response time
   - Response size

**Target Metrics**:

| Provider | Connection | TTFB | Total | Size |
|----------|-----------|------|-------|------|
| OpenRouter | <200ms | <500ms | <3s | <50KB |
| Ollama (local) | <50ms | <1s | <8s | <100KB |
| Gemini | <200ms | <400ms | <2s | <30KB |

### Simulating Poor Network Conditions

```bash
# Simulate 3G network
adb shell settings put global airplane_mode_on 0
adb shell svc data enable
adb shell settings put global mobile_data 1

# Use Network Profiler to throttle to 3G speeds
# Android Studio > Profiler > Network > Speed dropdown > 3G
```

**Test scenarios**:
1. App startup on slow network
2. AI request timeout behavior
3. Image loading on slow network (when implemented)

**Expected Behavior**:
- App remains responsive during network requests
- Proper timeout handling (10s for AI requests)
- Loading states visible to user
- No ANR (Application Not Responding)

---

## Battery Impact Testing

### Battery Historian Analysis

**Step 1**: Capture bug report

```bash
# Reset battery stats
adb shell dumpsys batterystats --reset

# Use app for 30 minutes (normal usage)

# Capture bug report
adb bugreport bugreport.zip
```

**Step 2**: Upload to Battery Historian

Visit: https://bathist.ef.lc/
Upload `bugreport.zip`

**Step 3**: Analyze results

Look for:
- App's CPU usage percentage
- Wake lock duration (should be 0)
- Network activity (should be minimal)
- GPS usage (should be 0)

**Target Metrics**:
- <2% battery drain per hour of active use
- 0 wake locks
- <1 minute of total CPU time per hour of active use

---

## Continuous Performance Monitoring

### Firebase Performance Monitoring Setup

**Step 1**: Add dependency

In `app/build.gradle.kts`:

```kotlin
plugins {
    id("com.google.gms.google-services")
    id("com.google.firebase.firebase-perf")
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-perf")
}
```

**Step 2**: Add custom traces

```kotlin
// In critical paths
val trace = Firebase.performance.newTrace("compatibility_analysis")
trace.start()
try {
    // Perform compatibility analysis
    val result = engine.analyzeCompatibility(profileA, profileB)
} finally {
    trace.stop()
}
```

**Step 3**: Monitor metrics in Firebase Console

Track:
- App startup time (P50, P95, P99)
- Screen rendering time
- Network request duration
- Custom traces

### Custom Performance Metrics

Create `PerformanceMonitor.kt`:

```kotlin
package com.spiritatlas.core.common

import android.os.SystemClock
import timber.log.Timber

object PerformanceMonitor {
    private val measurements = mutableMapOf<String, Long>()

    fun startMeasure(tag: String) {
        measurements[tag] = SystemClock.elapsedRealtimeNanos()
    }

    fun endMeasure(tag: String) {
        val startTime = measurements.remove(tag) ?: return
        val duration = (SystemClock.elapsedRealtimeNanos() - startTime) / 1_000_000
        Timber.d("⏱️ $tag: ${duration}ms")

        // Send to analytics in production
        // Firebase.performance.newTrace(tag).apply {
        //     putMetric("duration_ms", duration)
        // }
    }

    inline fun <T> measure(tag: String, block: () -> T): T {
        startMeasure(tag)
        return try {
            block()
        } finally {
            endMeasure(tag)
        }
    }
}
```

**Usage**:

```kotlin
val result = PerformanceMonitor.measure("profile_calculation") {
    calculator.calculateProfile(userData)
}
```

---

## Performance Testing Checklist

### Pre-Release Testing (Required)

- [ ] Run startup benchmark (cold, warm, hot)
- [ ] Run scroll/frame timing benchmarks
- [ ] Check for memory leaks with LeakCanary
- [ ] Verify memory usage <150MB (P95)
- [ ] Profile CPU usage for hot paths
- [ ] Test on low-end device (Pixel 4 or equivalent)
- [ ] Test on high-end device (Pixel 7+ or equivalent)
- [ ] Measure APK size (must be <60MB with images)
- [ ] Run battery impact test (30 min session)
- [ ] Test on slow network (3G simulation)

### Performance Regression Testing

Before each PR merge:

- [ ] Run Macrobenchmark suite
- [ ] Compare startup time vs baseline
- [ ] Check for new allocations in hot paths
- [ ] Verify frame rate hasn't degraded
- [ ] Review ProGuard mapping for size impact

### Monthly Performance Review

- [ ] Analyze Firebase Performance metrics
- [ ] Review crash and ANR rates
- [ ] Identify performance regressions
- [ ] Update baseline profiles
- [ ] Profile new features
- [ ] Compare with competitor apps

---

## Troubleshooting Common Issues

### Issue: Slow Startup Time

**Diagnosis**:
1. Profile with CPU profiler
2. Look for methods >50ms in Application.onCreate()
3. Check for synchronous I/O or network calls

**Common Causes**:
- Excessive Hilt module initialization
- Database schema migration
- Synchronous SharedPreferences reads
- Heavy computation in Application.onCreate()

**Solutions**:
- Defer non-critical initialization
- Use lazy injection for heavy dependencies
- Move I/O to background threads
- Implement baseline profiles

### Issue: Janky Scrolling

**Diagnosis**:
1. Use Frame Timing profiler
2. Look for frames >16ms
3. Check for main thread blocking

**Common Causes**:
- Complex calculations in composables
- Excessive recompositions
- Large image loading without optimization
- Nested scrolling containers

**Solutions**:
- Move calculations to ViewModel
- Add @Stable/@Immutable annotations
- Implement image placeholders and progressive loading
- Simplify layout hierarchy

### Issue: High Memory Usage

**Diagnosis**:
1. Take heap dump
2. Look for large retained objects
3. Check for bitmap/image memory

**Common Causes**:
- Memory leaks (Activity/ViewModel not released)
- Unbounded caches
- Large bitmaps in memory
- Retained listeners

**Solutions**:
- Fix memory leaks identified by LeakCanary
- Implement bounded LRU caches
- Configure Coil memory limits
- Unregister listeners properly

---

## Conclusion

Regular performance testing ensures SpiritAtlas maintains world-class responsiveness and efficiency. Follow this guide for each release cycle and when investigating performance issues.

**Key Principles**:
1. Measure before optimizing
2. Test on real devices, not emulators
3. Use release builds for accurate measurements
4. Compare against baseline metrics
5. Profile regularly, not just when problems occur

**Performance is a feature** - treat it with the same rigor as functional requirements.
