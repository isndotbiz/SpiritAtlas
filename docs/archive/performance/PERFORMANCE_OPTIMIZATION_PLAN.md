# SpiritAtlas Performance Optimization Plan

## Executive Summary

This document outlines a comprehensive performance optimization strategy for the SpiritAtlas Android application. The goal is to achieve blazing-fast performance across all metrics.

## Current State Analysis

### Build Configuration
- **Status**: ✅ Good foundation
- ProGuard/R8 enabled for release builds
- Comprehensive ProGuard rules already in place
- Build cache enabled in Gradle

### Memory Management
- **Status**: ✅ Excellent foundation
- Custom `MemoryManager` singleton with LRU cache
- Coil image loading with adaptive memory caching (15-25% based on device)
- Proper `onTrimMemory()` handling in Application class
- Weak references for preventing memory leaks

### Startup Performance
- **Status**: ⚠️ Needs optimization
- Custom splash screen with heavy animations (2.5s duration)
- Hilt DI initialization at app start
- WorkManager initialization disabled (good)
- No baseline profile configured

### Rendering Performance
- **Status**: ⚠️ Moderate concern
- 19 @Composable functions in HomeScreen alone
- Complex Canvas drawing in SplashScreen (stars, sacred geometry, rotating animations)
- Multiple infinite animations running simultaneously
- No explicit recomposition optimization

### APK Size
- **Status**: ✅ Good
- Resources: 45MB (includes generated spiritual images)
- Using WebP format for images (modern compression)
- ProGuard enabled for code shrinking
- Density-specific drawables configured

## Optimization Targets

| Metric | Current | Target | Priority |
|--------|---------|--------|----------|
| Cold Startup | ~2.5s+ | <1.0s | HIGH |
| Warm Startup | Unknown | <500ms | HIGH |
| Memory Usage | <100MB | <80MB | MEDIUM |
| Jank/Frame Drops | Unknown | 0 drops | HIGH |
| APK Size (Debug) | Unknown | <15MB | LOW |
| APK Size (Release) | Unknown | <8MB | MEDIUM |
| Battery Impact | Unknown | Minimal | MEDIUM |

## Optimization Strategy

### 1. Startup Optimization (Target: <1.0s cold start)

#### A. Splash Screen Optimization
**Problem**: 2.5s animation sequence with heavy Canvas drawing
**Solutions**:
- Replace complex Canvas animations with simpler transitions
- Use Android 12+ SplashScreen API for instant display
- Defer animation to after app initialization
- Lazy-load heavy dependencies during splash

#### B. Dependency Injection Optimization
**Current**: Hilt initializes all modules at app start
**Solutions**:
- Use `@EntryPoint` for lazy module access
- Defer heavy repository/service initialization
- Profile Hilt initialization time with Perfetto

#### C. Baseline Profile
**Status**: Not configured
**Impact**: 15-20% startup improvement, 5-10% runtime improvement
**Implementation**:
- Add androidx.profileinstaller dependency
- Generate baseline profile with Macrobenchmark
- Include in release build

### 2. Memory Optimization (Target: <80MB average)

#### Current State: Excellent Foundation
The app already has:
- Custom `MemoryManager` with LRU caching
- Adaptive Coil image caching (15-25% of memory)
- Proper `onTrimMemory()` callbacks
- Weak references for leak prevention

#### Additional Optimizations:
- Add LeakCanary for debug builds to detect leaks
- Implement `@Stable` and `@Immutable` on all state classes
- Use `derivedStateOf` for computed Compose values
- Profile with Android Studio Profiler to identify allocations

### 3. Rendering Optimization (Target: 60 FPS constant)

#### A. Compose Recomposition Optimization
**Problems**:
- 19 @Composable functions in HomeScreen (high recomposition risk)
- Infinite animations in SplashScreen consuming CPU
- No explicit recomposition scoping

**Solutions**:
```kotlin
// Use @Stable for state classes
@Stable
data class HomeUiState(...)

// Use derivedStateOf for computed values
val filteredProfiles by remember(profiles, searchQuery) {
    derivedStateOf {
        profiles.filter { it.name.contains(searchQuery) }
    }
}

// Scope recompositions with keys
key(profile.id) {
    ProfileCard(profile)
}

// Use remember for expensive calculations
val sortedProfiles = remember(profiles) {
    profiles.sortedBy { it.name }
}
```

#### B. Animation Optimization
**SplashScreen optimizations**:
- Reduce animation complexity
- Use hardware acceleration
- Limit concurrent animations
- Use `rememberInfiniteTransition` wisely (already done)

#### C. Canvas Drawing Optimization
**Current**: Heavy path drawing, multiple layers, continuous rotation
**Solutions**:
- Cache Path objects with `remember`
- Use `drawWithCache` for static content
- Reduce layer count (currently 3 aura layers)
- Consider static image for logo instead of Canvas

### 4. APK Size Optimization (Target: <8MB release)

#### Current: 45MB resources, WebP images
**Optimizations**:
```gradle
android {
    buildTypes {
        release {
            // Enable aggressive shrinking
            shrinkResources true
            minifyEnabled true

            // Full R8 optimization
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Resource optimization
    bundle {
        density {
            enableSplit true
        }
        language {
            enableSplit true
        }
    }

    // Remove unused resources
    packagingOptions {
        exclude 'META-INF/**'
        exclude '**/kotlin/**'
        exclude '**/*.txt'
    }
}
```

**App Bundle Strategy**:
- Use Android App Bundle for density splits
- Users only download xhdpi/xxhdpi resources they need
- ~60-70% size reduction for individual downloads

### 5. Battery Optimization

#### Current Concerns:
- Infinite animations on splash screen
- Continuous rotation animations
- Navigation analytics tracking

#### Solutions:
```kotlin
// Pause animations when app is backgrounded
val lifecycleOwner = LocalLifecycleOwner.current
val isActive by lifecycleOwner.lifecycle.currentStateFlow
    .map { it.isAtLeast(Lifecycle.State.RESUMED) }
    .collectAsState(initial = false)

if (isActive) {
    // Only animate when visible
    AnimatedContent(...)
}

// Batch analytics events
analytics.batchEvents(intervalMs = 30_000)

// Reduce wake locks
// Already good: WorkManager initialization disabled
```

### 6. Network Optimization

#### Current State:
- SSL pinning configured (good for security)
- OpenRouter/Ollama AI providers
- Retrofit with OkHttp

#### Optimizations:
```kotlin
// Add response caching
val cacheSize = 10L * 1024 * 1024 // 10MB
val cache = Cache(context.cacheDir, cacheSize)

val okHttpClient = OkHttpClient.Builder()
    .cache(cache)
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Cache-Control", "public, max-age=3600")
            .build()
        chain.proceed(request)
    }
    .build()

// Request batching for AI calls
class BatchedAiRepository {
    private val pendingRequests = mutableListOf<AiRequest>()

    suspend fun batchAnalyze(profiles: List<Profile>) {
        // Batch multiple profiles into single API call
    }
}

// Compression
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(GzipRequestInterceptor())
    .build()
```

## Implementation Priority

### Phase 1: Critical Path (Week 1)
1. ✅ Baseline Profile generation
2. ✅ Splash screen simplification
3. ✅ Add @Stable/@Immutable annotations
4. ✅ Implement derivedStateOf optimizations

### Phase 2: High Impact (Week 2)
5. ✅ LeakCanary integration
6. ✅ Canvas drawing optimization
7. ✅ APK size reduction (App Bundle + shrinking)
8. ✅ Response caching for network calls

### Phase 3: Polish (Week 3)
9. ✅ Battery optimization (pause animations)
10. ✅ Request batching
11. ✅ Comprehensive profiling and benchmarking
12. ✅ Performance testing automation

## Measurement & Validation

### Startup Metrics
```bash
# Cold start time
adb shell am start -W -n com.spiritatlas.app/.MainActivity

# Output: TotalTime: XXXms (target: <1000ms)
```

### Memory Metrics
```bash
# Monitor memory during app usage
adb shell dumpsys meminfo com.spiritatlas.app

# Target: PSS Total < 80MB
```

### Frame Rendering
```bash
# Enable GPU rendering profile
adb shell setprop debug.hwui.profile visual_bars

# View in Android Studio profiler
# Target: 0 dropped frames
```

### APK Size
```bash
# Build release AAB
./gradlew bundleRelease

# Analyze with bundletool
java -jar bundletool-all.jar build-apks \
  --bundle=app/build/outputs/bundle/release/app-release.aab \
  --output=app.apks

# Target: <8MB for xhdpi devices
```

## Benchmarking Infrastructure

### Macrobenchmark for Startup
```kotlin
@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(StartupTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }
}
```

### Microbenchmark for Calculations
```kotlin
@RunWith(AndroidJUnit4::class)
class NumerologyCalculationBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun calculateLifePath() = benchmarkRule.measureRepeated {
        NumerologyCalculator.calculateLifePath("1990-01-01")
    }
}
```

## Success Criteria

### Must Have (Blocking Release)
- ✅ Cold startup < 1.0s
- ✅ No memory leaks detected by LeakCanary
- ✅ 60 FPS on mid-range devices (no jank)
- ✅ APK size < 12MB for release

### Should Have (Quality)
- ✅ Warm startup < 500ms
- ✅ Memory usage < 80MB average
- ✅ APK size < 8MB with App Bundle
- ✅ Battery usage rated "Low" by Android vitals

### Nice to Have (Polish)
- ✅ Startup animations < 500ms total
- ✅ All screens render in < 16ms (60 FPS)
- ✅ Network caching 80%+ hit rate
- ✅ ProGuard obfuscation 100% effective

## Monitoring & Continuous Optimization

### Android Vitals Integration
- Track startup time percentiles (P50, P90, P99)
- Monitor crash-free rate
- Watch battery drain metrics
- Analyze ANR rate

### CI/CD Performance Gates
```yaml
# .github/workflows/performance.yml
- name: Performance Tests
  run: |
    ./gradlew :benchmark:connectedCheck
    ./performance_gate.sh --max-startup-ms 1000 \
                         --max-memory-mb 80 \
                         --min-fps 60
```

### Automated Regression Detection
- Baseline profile generation on every release
- Startup time regression tests
- Memory leak tests with LeakCanary
- Frame drop detection in UI tests

## Resources & Tools

### Profiling Tools
- Android Studio Profiler (CPU, Memory, Network)
- Perfetto (system-wide tracing)
- Systrace (frame rendering analysis)
- Layout Inspector (UI hierarchy)

### Benchmarking Libraries
- Jetpack Macrobenchmark (startup, jank)
- Jetpack Microbenchmark (calculation performance)
- Firebase Performance Monitoring

### Analysis Tools
- APK Analyzer (size breakdown)
- R8 optimization reports
- Method trace analysis
- Battery Historian

## Conclusion

The SpiritAtlas app has an excellent foundation for performance with proper memory management and ProGuard configuration. The key optimization areas are:

1. **Startup Performance**: Simplify splash screen and add baseline profile (biggest impact)
2. **Rendering**: Add Compose optimization annotations and reduce recompositions
3. **APK Size**: Enable App Bundle splits for density-based optimization
4. **Monitoring**: Add benchmarking infrastructure for continuous validation

Expected improvements:
- 60-70% faster cold startup (2.5s → <1.0s)
- 20% memory reduction (with monitoring)
- 50-60% smaller download size (with App Bundle)
- Zero frame drops on all screens

The implementation is prioritized for maximum impact with minimum risk.
