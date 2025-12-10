# ABI Optimization Quick Start Guide

**Goal**: Reduce APK size by 40-50% per device through ABI splits

## Prerequisites

- [ ] Read full plan: `ABI_OPTIMIZATION_PLAN.md`
- [ ] Backup current build file: `cp app/build.gradle.kts app/build.gradle.kts.backup`
- [ ] Ensure clean working directory: `git status`

## 5-Minute Implementation

### Step 1: Apply Changes

**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/app/build.gradle.kts`

**Location**: Lines 111-122 (search for `splits {`)

**Replace with**:
```kotlin
// Configure splits for optimized APK delivery
splits {
    abi {
        // Enable ABI splits for smaller per-device APKs
        isEnable = true
        reset()

        // Include all ABIs (recommended for initial rollout)
        include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")

        // Create universal APK as fallback
        isUniversalApk = true
    }

    density {
        // Disable density splits (keep all densities in each APK)
        isEnable = false
    }
}
```

**Add after `splits` block** (around line 123):
```kotlin
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
        output.versionCodeOverride =
            (variant.versionCode.toInt() * 10) + abiMultiplier
    }
}
```

### Step 2: Build

```bash
# Clean build
./gradlew clean

# Build all APK variants
./gradlew :app:assembleRelease
```

**Expected build time**: 3-5 minutes (first build with splits)

### Step 3: Verify Outputs

```bash
# List APKs with sizes
ls -lh app/build/outputs/apk/release/

# Expected files:
# app-armeabi-v7a-release.apk
# app-arm64-v8a-release.apk
# app-x86-release.apk
# app-x86_64-release.apk
# app-universal-release.apk
```

### Step 4: Measure Savings

```bash
# Compare sizes
du -h app/build/outputs/apk/release/*.apk | sort -h

# Calculate percentage reduction
# (universal_size - arm64_size) / universal_size × 100
```

**Expected Results**:
- ARM64 APK: ~12-15 MB (40-50% smaller)
- Universal APK: ~25-35 MB (baseline)
- **Savings**: ~40-50% per device

## Quick Test

```bash
# Install ARM64 variant on device
adb install app/build/outputs/apk/release/app-arm64-v8a-release.apk

# Launch app
adb shell am start -n com.spiritatlas.app/.MainActivity

# Check for errors
adb logcat | grep -E "SpiritAtlas|FATAL"
```

## Common Issues

### Issue: Build fails with compilation errors

**Solution**:
```bash
./gradlew clean
./gradlew --stop
./gradlew :app:assembleRelease
```

### Issue: APK not found

**Solution**:
Check build output:
```bash
./gradlew :app:assembleRelease --info | grep "APK"
```

### Issue: Version code conflicts

**Solution**:
Adjust multiplier in version code strategy (change `* 10` to `* 100`)

## Next Steps

### For Development
- Use debug builds (no splits needed): `./gradlew :app:assembleDebug`
- Build only ARM64 for faster iteration: `./gradlew :app:assembleRelease -Pandroid.injected.abi=arm64-v8a`

### For Testing
1. Upload to Internal Testing track on Google Play Console
2. Test on 3-5 different device types
3. Monitor crash reports for 24-48 hours
4. Move to Closed Beta when stable

### For Production
**Option 1**: Upload all APK variants to Google Play Console

**Option 2 (Recommended)**: Build App Bundle
```bash
./gradlew :app:bundleRelease

# Upload app/build/outputs/bundle/release/app-release.aab
```

## Size Projections

Based on typical Compose app with network + crypto dependencies:

| Configuration | APK Size | Reduction |
|--------------|----------|-----------|
| **Current (Universal)** | ~30 MB | Baseline |
| **ARM64 Split** | ~15 MB | **50%** |
| **armeabi-v7a Split** | ~14 MB | **53%** |
| **x86_64 Split** | ~17 MB | **43%** |
| **App Bundle (AAB)** | ~12 MB | **60%** |

## Rollout Checklist

- [ ] ABI splits enabled in build.gradle.kts
- [ ] Version code strategy implemented
- [ ] Test builds complete successfully
- [ ] APK sizes verified (40-50% reduction)
- [ ] Functionality tested on ARM64 device
- [ ] No crashes or errors in logcat
- [ ] Native libraries present in APK (unzip -l)
- [ ] Upload to Internal Testing track
- [ ] Monitor for 24-48 hours
- [ ] Promote to production with staged rollout (5% → 25% → 100%)

## Monitoring

Track these metrics after deployment:

- **APK Download Size** (Play Console → Statistics)
- **Install Success Rate** (Play Console → Statistics)
- **Crash Rate by ABI** (Firebase Crashlytics)
- **User Retention** (Firebase Analytics)

**Success criteria**:
- APK size reduced by >40%
- Install success rate >98%
- Crash rate <1%
- No increase in ANR rate

## Rollback Plan

If issues arise:

1. **Immediate**: Halt staged rollout in Play Console
2. **Revert**: Upload previous universal APK as emergency release
3. **Fix**: Address ABI-specific issues
4. **Retry**: Resume rollout after fixes

**Rollback command**:
```bash
# Restore backup
cp app/build.gradle.kts.backup app/build.gradle.kts

# Build universal APK
./gradlew clean :app:assembleRelease
```

## Advanced Options

### ARM64-Only (Aggressive)

Change line in build.gradle.kts:
```kotlin
include("arm64-v8a") // Only ARM64
```

**Pros**: 60-70% size reduction, simplest configuration
**Cons**: Excludes ~5% of devices, no emulator support

### App Bundle (Recommended)

```bash
# Build AAB instead of APK
./gradlew :app:bundleRelease

# Output: app/build/outputs/bundle/release/app-release.aab
```

**Pros**: Automatic optimization, future-proof, Google recommended
**Cons**: Requires Google Play signing

## Resources

- **Full Plan**: `ABI_OPTIMIZATION_PLAN.md`
- **Implementation Patch**: `abi_splits_implementation.patch`
- **Android Docs**: https://developer.android.com/studio/build/configure-apk-splits
- **App Bundles**: https://developer.android.com/guide/app-bundle

## Questions?

1. Review `ABI_OPTIMIZATION_PLAN.md` for detailed explanations
2. Check "Troubleshooting" section for common issues
3. Test in Internal Testing before production rollout

---

**Estimated Time**: 30 minutes (implementation + testing)
**Expected Savings**: 40-50% APK size reduction per device
**Risk Level**: Low (with staged rollout and universal APK fallback)
