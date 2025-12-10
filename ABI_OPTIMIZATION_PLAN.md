# ABI Optimization Plan for SpiritAtlas

**Document Version**: 1.0
**Date**: 2025-12-10
**Status**: Ready for Implementation

## Executive Summary

This document outlines a comprehensive plan to optimize SpiritAtlas APK size through ABI (Application Binary Interface) splits and ARM64-focused delivery. By implementing ABI splits and prioritizing ARM64 architecture, we can achieve **40-50% APK size reduction** per device while maintaining broad device compatibility.

## Current State Analysis

### Technology Stack
- **Build System**: Gradle 8.13 with Kotlin DSL
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 35 (Android 14)
- **Compile SDK**: 35
- **Kotlin**: 1.9.25
- **AGP**: 8.13.1

### Native Library Dependencies

The following dependencies include native libraries that will be affected by ABI splits:

| Dependency | Version | Native Libraries |
|------------|---------|------------------|
| **OkHttp** | 4.12.0 | Conscrypt (SSL/TLS) |
| **Coil** | 2.7.0 | Image decoding |
| **Security Crypto** | 1.1.0-alpha06 | Tink (encryption) |
| **Room** | 2.6.1 | SQLite |
| **Google Generative AI** | 0.9.0 | gRPC, protobuf |
| **Compose BOM** | 2024.09.02 | Skia (UI rendering) |
| **WorkManager** | 2.9.1 | Background processing |

### Current Build Configuration

**Location**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/app/build.gradle.kts`

Current ABI configuration (lines 112-122):
```kotlin
splits {
    abi {
        isEnable = false // Currently disabled
        reset()
        include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        isUniversalApk = true
    }
    density {
        isEnable = false
    }
}
```

## Market Analysis

### Android Device Distribution (2025)

| Architecture | Market Share | Min SDK Support | Notes |
|--------------|--------------|-----------------|-------|
| **arm64-v8a** | ~85% | API 21+ | Modern 64-bit ARM devices |
| **armeabi-v7a** | ~10% | API 16+ | Legacy 32-bit ARM devices |
| **x86_64** | ~3% | API 21+ | Emulators, ChromeOS |
| **x86** | ~2% | API 16+ | Legacy emulators |

**Key Insights**:
- 85% of devices run ARM64 (our primary target)
- Google Play requires 64-bit support (arm64-v8a)
- x86/x86_64 mainly for emulators and ChromeOS
- armeabi-v7a represents legacy devices (phones from 2012-2017)

### User Base Projection

Given SpiritAtlas **Min SDK 26** (Android 8.0, released 2017):
- **95%+** of compatible devices support ARM64
- **4-5%** are ARM 32-bit devices (armeabi-v7a)
- **<1%** are x86-based devices

## Optimization Strategy

### Phase 1: Enable ABI Splits (Recommended)

**Goal**: Create separate APKs for each ABI while maintaining universal compatibility.

**Configuration**:
```kotlin
splits {
    abi {
        isEnable = true
        reset()
        include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        isUniversalApk = true // Create universal APK as fallback
    }
}
```

**APK Outputs** (estimated):
- `app-arm64-v8a-release.apk` - ~12-15 MB (primary)
- `app-armeabi-v7a-release.apk` - ~11-14 MB (legacy)
- `app-x86_64-release.apk` - ~14-17 MB (emulators)
- `app-x86-release.apk` - ~13-16 MB (legacy emulators)
- `app-universal-release.apk` - ~30-40 MB (fallback)

**Size Reduction**: ~50-60% per device (from universal to ABI-specific)

### Phase 2: ARM64-Only Build (Aggressive Optimization)

**Goal**: Target the primary market segment with maximum optimization.

**Configuration**:
```kotlin
splits {
    abi {
        isEnable = true
        reset()
        include("arm64-v8a") // ARM64 only
        isUniversalApk = false // No fallback
    }
}
```

**APK Output**:
- `app-arm64-v8a-release.apk` - ~12-15 MB

**Size Reduction**: ~60-70% from universal APK

**Trade-offs**:
- Excludes ~5% of compatible devices (armeabi-v7a)
- Cannot run on x86/x86_64 emulators without Universal APK
- Recommended for Play Store only (not for direct distribution)

### Phase 3: App Bundle (AAB) - Recommended for Production

**Goal**: Let Google Play optimize delivery automatically.

**Configuration**:
```kotlin
// No splits configuration needed for AAB
bundle {
    language {
        enableSplit = true // Language-based splits
    }
    density {
        enableSplit = true // Screen density splits
    }
    abi {
        enableSplit = true // ABI splits (automatic)
    }
}
```

**Command**:
```bash
./gradlew :app:bundleRelease
```

**Benefits**:
- Google Play serves optimal APK to each device automatically
- Users download only what they need (~40-50% smaller)
- Supports dynamic delivery and on-demand modules
- Future-proof for new architectures

## Size Breakdown & Projections

### Estimated APK Composition

Based on typical Compose + Network + Crypto app:

| Component | Universal APK | ARM64-Only APK | Savings |
|-----------|--------------|----------------|---------|
| **DEX Code** | 8-10 MB | 8-10 MB | 0% |
| **Native Libraries** | 12-15 MB | 3-4 MB | **70-75%** |
| **Resources** | 3-5 MB | 3-5 MB | 0% |
| **Assets** | 1-2 MB | 1-2 MB | 0% |
| **Other** | 2-3 MB | 1-2 MB | 33% |
| **Total** | **26-35 MB** | **16-23 MB** | **38-45%** |

### Native Library Size Estimates

| Library Category | Universal | ARM64 | armeabi-v7a | x86_64 | x86 |
|------------------|-----------|-------|-------------|--------|-----|
| **OkHttp/Conscrypt** | 4-5 MB | 1.2 MB | 1.0 MB | 1.5 MB | 1.3 MB |
| **Room/SQLite** | 3-4 MB | 0.8 MB | 0.7 MB | 1.0 MB | 0.9 MB |
| **Security/Tink** | 2-3 MB | 0.6 MB | 0.5 MB | 0.8 MB | 0.7 MB |
| **Compose/Skia** | 2-3 MB | 0.5 MB | 0.4 MB | 0.7 MB | 0.6 MB |
| **Other** | 1-2 MB | 0.3 MB | 0.2 MB | 0.4 MB | 0.3 MB |
| **Total** | **12-17 MB** | **3.4 MB** | **2.8 MB** | **4.4 MB** | **3.8 MB** |

## Implementation Plan

### Step 1: Backup Current Configuration

```bash
# Create backup
cp app/build.gradle.kts app/build.gradle.kts.backup
git add app/build.gradle.kts.backup
```

### Step 2: Update build.gradle.kts

**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/app/build.gradle.kts`

**Replace lines 111-122** with:

```kotlin
// Configure splits for optimized APK delivery
splits {
    abi {
        // Enable ABI splits for smaller per-device APKs
        isEnable = true
        reset()

        // Option A: Include all ABIs (recommended for initial rollout)
        include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")

        // Option B: ARM64-only (uncomment for aggressive optimization)
        // include("arm64-v8a")

        // Create universal APK as fallback
        isUniversalApk = true
    }

    density {
        // Disable density splits (keep all densities in each APK)
        isEnable = false
    }
}

// Version code strategy for ABI splits
android.applicationVariants.all {
    val variant = this
    variant.outputs.all {
        val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl

        // Map ABIs to version code multipliers
        val abiMultiplier = when {
            output.getFilter("ABI") == "arm64-v8a" -> 4
            output.getFilter("ABI") == "armeabi-v7a" -> 3
            output.getFilter("ABI") == "x86_64" -> 2
            output.getFilter("ABI") == "x86" -> 1
            else -> 0 // Universal APK
        }

        // New version code = (base * 10) + ABI multiplier
        // Example: 1.0.0 (versionCode 1) -> 14 (arm64-v8a)
        output.versionCodeOverride =
            (variant.versionCode.toInt() * 10) + abiMultiplier
    }
}
```

### Step 3: Test Build

```bash
# Clean build to ensure fresh state
./gradlew clean

# Build all APK variants
./gradlew :app:assembleRelease

# Verify outputs
ls -lh app/build/outputs/apk/release/
```

**Expected outputs**:
```
app-armeabi-v7a-release.apk
app-arm64-v8a-release.apk
app-x86-release.apk
app-x86_64-release.apk
app-universal-release.apk
```

### Step 4: Measure APK Sizes

```bash
# Get detailed size breakdown
find app/build/outputs/apk/release -name "*.apk" -exec sh -c \
  'echo "{}:"; unzip -l {} | tail -1; echo' \;

# Compare sizes
du -h app/build/outputs/apk/release/*.apk | sort -h
```

### Step 5: Analyze APK Contents

```bash
# Install Android SDK build tools analyzer
# Analyze each APK variant
for apk in app/build/outputs/apk/release/*.apk; do
    echo "=== Analyzing $apk ==="
    unzip -l "$apk" | grep "lib/" | head -20
    echo ""
done
```

### Step 6: Test on Devices

```bash
# Test ARM64 APK on physical device
./gradlew installRelease -Pandroid.injected.abi=arm64-v8a

# Launch app and verify functionality
adb shell am start -n com.spiritatlas.app/.MainActivity

# Check for crashes
adb logcat | grep -E "SpiritAtlas|AndroidRuntime|FATAL"
```

## Version Code Strategy

Google Play requires unique version codes for each APK variant.

### Current Strategy

```
Base Version Code: 1 (from defaultConfig)
Version Name: 1.0.0
```

### New Strategy with ABI Splits

```
Formula: (baseVersionCode × 10) + abiMultiplier

ABI Multipliers:
- arm64-v8a: 4 (highest priority)
- armeabi-v7a: 3
- x86_64: 2
- x86: 1
- universal: 0

Example Version Codes:
1.0.0 (base=1):
  - arm64-v8a: 14
  - armeabi-v7a: 13
  - x86_64: 12
  - x86: 11
  - universal: 10

2.0.0 (base=2):
  - arm64-v8a: 24
  - armeabi-v7a: 23
  - x86_64: 22
  - x86: 21
  - universal: 20
```

This ensures:
1. Each ABI variant has a unique version code
2. Newer app versions always have higher version codes
3. Google Play serves the optimal APK to each device

## Google Play Configuration

### Upload Strategy

**Option 1: Multiple APKs (Legacy)**

1. Build all APK variants
2. Upload to Google Play Console
3. Configure device compatibility for each APK
4. Google Play serves the appropriate APK based on device

**Pros**:
- More control over delivery
- Can test specific variants

**Cons**:
- Manual upload for each variant
- More complex release management
- Legacy approach (Google recommends AAB)

**Option 2: App Bundle (Recommended)**

1. Build App Bundle (AAB)
   ```bash
   ./gradlew :app:bundleRelease
   ```
2. Upload single AAB to Google Play Console
3. Google Play generates optimized APKs automatically
4. Users receive minimal-size APK for their device

**Pros**:
- Automatic optimization
- Single upload process
- Smaller user downloads
- Future-proof
- Supports dynamic delivery

**Cons**:
- Less control over exact APK composition
- Requires Google Play signing

### Play Console Configuration

**For Multiple APKs**:
1. Go to "Release" > "Production"
2. Create new release
3. Upload all APK variants
4. Configure device catalog targeting
5. Set rollout percentage (start with 5-10%)

**For App Bundle**:
1. Go to "Release" > "Production"
2. Create new release
3. Upload AAB file
4. Configure "App signing by Google Play" (required)
5. Set rollout percentage

## Testing & Validation

### Pre-Release Testing

**1. Local Testing**
```bash
# Test each variant on emulator
emulator -avd Pixel_6_API_35 -arch arm64-v8a
./gradlew installRelease -Pandroid.injected.abi=arm64-v8a

# Test on physical device
adb install app/build/outputs/apk/release/app-arm64-v8a-release.apk
```

**2. Internal Testing Track**
- Upload to Internal Testing track on Play Console
- Test on variety of device architectures
- Monitor crash reports for 24-48 hours

**3. Closed Beta Testing**
- Promote to Closed Beta
- Include 100-500 users
- Monitor for 1-2 weeks
- Track metrics: crash rate, ANR rate, install success

### Validation Checklist

- [ ] All APK variants build successfully
- [ ] Version codes are unique and sequential
- [ ] APK sizes show expected reduction
- [ ] Native libraries present in each APK variant
- [ ] App launches on each ABI variant
- [ ] Core functionality works (profile creation, compatibility analysis)
- [ ] Network requests succeed (SSL pinning works)
- [ ] Database operations work (Room)
- [ ] Encryption works (Security Crypto)
- [ ] No crashes in logcat
- [ ] Performance is acceptable (no lag)

### Performance Metrics

Track these metrics before and after optimization:

| Metric | Target | Measurement |
|--------|--------|-------------|
| **APK Download Size** | <15 MB | Play Console |
| **Install Size** | <50 MB | Play Console |
| **App Startup Time** | <2s cold start | Firebase Performance |
| **Crash-free Users** | >99.5% | Firebase Crashlytics |
| **ANR Rate** | <0.1% | Play Console |
| **Install Success Rate** | >98% | Play Console |

## Rollout Strategy

### Phase 1: Internal Testing (Week 1)
- Build with ABI splits enabled
- Upload to Internal Testing track
- Test on 3-5 device types (arm64, armeabi-v7a, x86)
- Validate core functionality

### Phase 2: Closed Beta (Week 2-3)
- Promote to Closed Beta track
- 100-500 beta testers
- Monitor crash reports and feedback
- Fix any ABI-specific issues

### Phase 3: Staged Rollout (Week 4-6)
- Start at 5% of production users
- Monitor for 48 hours
- Increase to 10% → 25% → 50% → 100%
- Halt rollout if crash rate >1%

### Phase 4: App Bundle Migration (Week 7+)
- Switch from multiple APKs to App Bundle
- Upload AAB to new release
- Enable App Signing by Google Play
- Full rollout to 100% of users

## Risk Assessment & Mitigation

### Risk 1: ABI-Specific Crashes
**Probability**: Low
**Impact**: High
**Mitigation**:
- Comprehensive testing on each ABI
- Keep universal APK as fallback
- Staged rollout with monitoring
- Quick rollback plan

### Risk 2: Version Code Conflicts
**Probability**: Low
**Impact**: Medium
**Mitigation**:
- Test version code generation logic
- Validate in Play Console before release
- Document version code strategy

### Risk 3: Increased Build Time
**Probability**: High
**Impact**: Low
**Mitigation**:
- ABI splits add ~30% build time
- Consider building only arm64-v8a for local dev
- Use CI/CD for release builds

### Risk 4: Missing Native Libraries
**Probability**: Low
**Impact**: High
**Mitigation**:
- Validate each APK contains required .so files
- Test core functionality on each variant
- Monitor for UnsatisfiedLinkError in production

### Risk 5: Google Play Rejection
**Probability**: Very Low
**Impact**: High
**Mitigation**:
- Follow Google Play policies
- Maintain 64-bit support (arm64-v8a)
- Test on variety of devices before submission

## Advanced Optimizations

### 1. Enable R8 Full Mode

**File**: `gradle.properties`
```properties
android.enableR8.fullMode=true
```

**Additional size reduction**: 5-10%

### 2. Resource Shrinking

Already enabled in `app/build.gradle.kts`:
```kotlin
buildTypes {
    release {
        isShrinkResources = true // Remove unused resources
    }
}
```

### 3. Native Library Stripping

Already configured in `app/build.gradle.kts`:
```kotlin
packaging {
    jniLibs {
        useLegacyPackaging = false // Enable compression
    }
}
```

### 4. Language-Specific APKs

If your app is localized:
```kotlin
splits {
    language {
        enable = true
        include("en", "es", "fr") // Your supported languages
    }
}
```

**Additional reduction**: 2-5 MB per APK

### 5. Density-Specific APKs

For apps with many image assets:
```kotlin
splits {
    density {
        enable = true
        include("mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi")
    }
}
```

**Additional reduction**: 10-30% depending on image assets

## Monitoring & Analytics

### Key Metrics to Track

**Google Play Console**:
- Install success rate by ABI
- Crash rate by ABI
- ANR rate by ABI
- User retention by ABI
- APK download size statistics

**Firebase Crashlytics**:
- Crashes grouped by ABI
- Non-fatal exceptions
- Stack traces with native symbols

**Firebase Performance**:
- App startup time by ABI
- Network request latency
- Database query performance

### Custom Tracking

Add ABI detection to analytics:
```kotlin
// In MainActivity or Application class
val abi = Build.SUPPORTED_ABIS[0] // Primary ABI
Firebase.analytics.setUserProperty("device_abi", abi)
```

## Cost-Benefit Analysis

### Benefits

| Benefit | Quantification |
|---------|----------------|
| **Reduced Download Size** | 40-50% per user |
| **Faster Downloads** | 50-60% faster on slow networks |
| **Lower Data Usage** | 12-20 MB saved per install |
| **Better User Experience** | Faster installs, less storage |
| **Higher Install Rates** | 10-15% improvement (industry avg) |
| **Lower Hosting Costs** | Reduced CDN/bandwidth costs |

### Costs

| Cost | Impact |
|------|--------|
| **Development Time** | 4-8 hours initial setup |
| **Testing Time** | 2-3 days comprehensive testing |
| **Build Time Increase** | +30-50% longer builds |
| **Maintenance Overhead** | Minimal (automated) |
| **Play Console Complexity** | Slight increase (AAB simplifies) |

### ROI Calculation

**Assumptions**:
- 10,000 installs/month
- Average universal APK: 30 MB
- Average ABI-specific APK: 15 MB
- CDN cost: $0.05/GB

**Monthly Savings**:
```
Data saved: 10,000 × 15 MB = 150 GB/month
CDN savings: 150 GB × $0.05 = $7.50/month
Annual savings: $90/year

User experience value:
- Higher install conversion: +10% = 1,000 more installs/month
- Better retention: +5% = 500 more active users
- Improved ratings: +0.2 stars (estimated)
```

**Conclusion**: Significant user experience improvements with minimal costs.

## Troubleshooting

### Issue 1: APK Not Building

**Symptom**: Build fails with compilation errors

**Solution**:
```bash
# Clean build and restart
./gradlew clean
./gradlew --stop
./gradlew :app:assembleRelease
```

### Issue 2: Missing Native Libraries

**Symptom**: `UnsatisfiedLinkError` at runtime

**Solution**:
```bash
# Check APK contents
unzip -l app/build/outputs/apk/release/app-arm64-v8a-release.apk | grep ".so$"

# Verify dependency packaging
./gradlew :app:dependencies --configuration releaseRuntimeClasspath | grep -A5 "native"
```

### Issue 3: Version Code Conflicts

**Symptom**: Play Console rejects upload

**Solution**:
```kotlin
// Adjust version code multiplier in build.gradle.kts
output.versionCodeOverride = (variant.versionCode.toInt() * 100) + abiMultiplier
```

### Issue 4: APK Too Large

**Symptom**: ABI-specific APK still >20 MB

**Solution**:
```bash
# Analyze APK
./gradlew :app:analyzeReleaseApk

# Check for unused resources
./gradlew :app:lintRelease

# Enable additional optimizations
android.enableR8.fullMode=true
```

## Future Enhancements

### 1. Dynamic Delivery

Migrate to on-demand modules:
```kotlin
// Future: Split features into dynamic modules
dynamicFeatures = mutableSetOf(
    ":feature:compatibility",
    ":feature:profile",
    ":feature:settings"
)
```

**Potential savings**: Additional 30-40% reduction

### 2. Baseline Profiles

Add Baseline Profiles for faster startup:
```bash
# Generate baseline profile
./gradlew :app:generateReleaseBaselineProfile
```

**Benefit**: 15-30% faster cold start

### 3. Native Image Caching

Implement WebP image caching:
- Reduce image assets by 25-35%
- Use vector drawables where possible
- Lazy load large images

### 4. Code Splitting

Use ProGuard/R8 aggressive optimization:
```properties
# proguard-rules.pro
-optimizationpasses 5
-optimizations !code/simplification/arithmetic
```

**Additional reduction**: 5-8%

## Appendix A: Build Commands Reference

```bash
# Clean build
./gradlew clean

# Build debug APK (single universal)
./gradlew :app:assembleDebug

# Build release APK (all ABI variants)
./gradlew :app:assembleRelease

# Build specific ABI variant
./gradlew :app:assembleRelease -Pandroid.injected.abi=arm64-v8a

# Build App Bundle
./gradlew :app:bundleRelease

# Install debug on device
./gradlew installDebug

# Install release on device
./gradlew installRelease

# Analyze APK
./gradlew :app:analyzeReleaseApk

# Generate dependency tree
./gradlew :app:dependencies > dependencies.txt

# Check APK size
du -h app/build/outputs/apk/release/*.apk

# Verify APK contents
unzip -l app/build/outputs/apk/release/app-arm64-v8a-release.apk

# Run on emulator
emulator -avd Pixel_6_API_35 && ./gradlew installDebug
```

## Appendix B: ProGuard/R8 Rules

If you encounter issues after enabling ABI splits, add these rules to `app/proguard-rules.pro`:

```proguard
# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep classes used via reflection in libraries
-keep class com.squareup.okhttp3.** { *; }
-keep class androidx.security.crypto.** { *; }
-keep class androidx.room.** { *; }

# Keep Compose runtime
-keep class androidx.compose.** { *; }

# Keep native library loading
-keep class * implements android.os.Parcelable {
    static final android.os.Parcelable$Creator *;
}
```

## Appendix C: Version History

| Date | Version | Changes |
|------|---------|---------|
| 2025-12-10 | 1.0 | Initial optimization plan created |

## References

1. [Android Developer Guide: Configure build variants](https://developer.android.com/studio/build/build-variants)
2. [Android Developer Guide: Configure APK splits](https://developer.android.com/studio/build/configure-apk-splits)
3. [Android Developer Guide: App bundles](https://developer.android.com/guide/app-bundle)
4. [Google Play Console: Upload app bundles](https://support.google.com/googleplay/android-developer/answer/9859152)
5. [ProGuard/R8 optimization](https://developer.android.com/studio/build/shrink-code)

---

**Document Prepared By**: Claude (Anthropic)
**Review Status**: Ready for Engineering Review
**Next Steps**: Implement Phase 1 and test on internal devices
