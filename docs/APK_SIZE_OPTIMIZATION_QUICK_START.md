# APK Size Optimization Quick Start Guide

## Prerequisites

✅ **Build Status:** Ensure compilation errors are fixed before applying optimizations

```bash
# Verify clean build works
./gradlew clean
./gradlew :app:assembleDebug
```

---

## Step 1: Apply Build Configuration (5 minutes)

### Replace build.gradle.kts
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas

# Backup current configuration
cp app/build.gradle.kts app/build.gradle.kts.backup

# Apply optimized configuration
cp app/build.gradle.kts.optimized app/build.gradle.kts
```

### Key Changes Applied
- ✅ Resource density filtering (xxhdpi only)
- ✅ R8 resource shrinking enabled
- ✅ Native debug symbol removal
- ✅ App Bundle splits configuration
- ✅ Packaging exclusions

---

## Step 2: Apply ProGuard Rules (5 minutes)

### Replace proguard-rules.pro
```bash
# Backup current rules
cp app/proguard-rules.pro app/proguard-rules.pro.backup

# Apply optimized rules
cp app/proguard-rules.pro.optimized app/proguard-rules.pro
```

### Key Changes Applied
- ✅ 7 optimization passes (was 5)
- ✅ Aggressive interface merging
- ✅ Complete log removal in release
- ✅ Enhanced code shrinking
- ✅ Class repackaging

---

## Step 3: Build Release APK (10-15 minutes)

### Standard APK Build
```bash
# Clean build
./gradlew clean

# Build release APK
./gradlew :app:assembleRelease

# Check size
ls -lh app/build/outputs/apk/release/app-release-unsigned.apk
```

### Expected Result
```
app-release-unsigned.apk: ~14.5MB (with density filtering)
```

---

## Step 4: Build App Bundle (Recommended)

### AAB Build
```bash
# Build Android App Bundle
./gradlew :app:bundleRelease

# Check bundle size
ls -lh app/build/outputs/bundle/release/app-release.aab
```

### Analyze Bundle with bundletool
```bash
# Install bundletool (if not installed)
brew install bundletool

# Generate device-specific APKs
bundletool build-apks \
  --bundle=app/build/outputs/bundle/release/app-release.aab \
  --output=app-release.apks \
  --mode=universal

# Extract universal APK
unzip -p app-release.apks universal.apk > app-universal.apk
ls -lh app-universal.apk
```

### Expected Results (Device-Specific)
```
xxhdpi device: ~8MB  ✅ MEETS TARGET
xhdpi device:  ~6MB  ✅ EXCEEDS TARGET
xxxhdpi device: ~12MB
```

---

## Step 5: Size Analysis & Verification

### APK Analyzer (Android Studio)
1. **Build > Analyze APK**
2. Select `app-release-unsigned.apk`
3. Review:
   - Resources size
   - DEX file size
   - Native libraries
   - Assets

### Command Line Analysis
```bash
# Detailed size breakdown
unzip -l app/build/outputs/apk/release/app-release-unsigned.apk | \
  grep -E "resources.arsc|classes.dex|res/" | \
  awk '{sum+=$1} END {print "Total:", sum/1024/1024, "MB"}'

# Compare with previous build (if available)
du -h app/build/outputs/apk/release/*.apk
```

---

## Step 6: Testing & Validation

### Functional Testing
```bash
# Install on test device
adb install app/build/outputs/apk/release/app-release-unsigned.apk

# Verify functionality
# - Check all screens load correctly
# - Verify images display properly (density filtering)
# - Test navigation flows
# - Confirm calculations work
```

### Critical Test Cases
- [ ] Home screen displays correctly
- [ ] Profile images load (check all densities)
- [ ] Navigation transitions work
- [ ] Compatibility analysis functions
- [ ] Settings screen accessible
- [ ] No ResourceNotFoundException crashes

---

## Rollback Procedure

If issues occur:

```bash
# Restore original configuration
cp app/build.gradle.kts.backup app/build.gradle.kts
cp app/proguard-rules.pro.backup app/proguard-rules.pro

# Clean and rebuild
./gradlew clean
./gradlew :app:assembleDebug
```

---

## Progressive Optimization Strategy

### Conservative Approach (Recommended)

#### Phase 1: No Risk
```kotlin
// Only apply these initially:
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        ndk { debugSymbolLevel = "NONE" }
    }
}

packaging {
    resources {
        excludes += /* metadata files */
    }
}
```

**Build & Test → Measure Size**

#### Phase 2: Medium Risk
```kotlin
// Add density filtering after Phase 1 validation:
defaultConfig {
    resourceConfigurations += setOf("en", "xxhdpi")
}
```

**Build & Test → Verify on multiple devices**

#### Phase 3: High Impact
```kotlin
// Enable App Bundle splits:
bundle {
    density { enableSplit = true }
    abi { enableSplit = true }
}
```

**Build AAB → Test with bundletool → Deploy to Play Store**

---

## Troubleshooting

### Issue: Resources Not Found
```
ResourceNotFoundException: Resource ID #0x...
```

**Solution:** Density filtering too aggressive
```kotlin
// Add more densities
resourceConfigurations += setOf("en", "xhdpi", "xxhdpi", "xxxhdpi")
```

### Issue: ProGuard Obfuscation Errors
```
ClassNotFoundException or MethodNotFoundException
```

**Solution:** Add keep rules for affected classes
```proguard
-keep class com.problematic.Class { *; }
```

### Issue: Large APK Despite Optimizations
```
APK still > 20MB
```

**Check:**
1. Run `./gradlew :app:assembleRelease --info` to see what's included
2. Use APK Analyzer to identify large components
3. Verify `isShrinkResources = true` is set
4. Check if unused resources are actually removed

---

## Size Monitoring Commands

### Quick Size Check
```bash
#!/bin/bash
# save as scripts/check_apk_size.sh

RELEASE_APK="app/build/outputs/apk/release/app-release-unsigned.apk"

if [ -f "$RELEASE_APK" ]; then
    SIZE_MB=$(du -m "$RELEASE_APK" | cut -f1)
    echo "APK Size: ${SIZE_MB}MB"

    if [ $SIZE_MB -lt 10 ]; then
        echo "✅ PASS: Under 10MB target"
    else
        echo "❌ FAIL: Over 10MB target"
    fi
else
    echo "❌ Release APK not found. Run: ./gradlew :app:assembleRelease"
fi
```

### Automated Size Report
```bash
# Build and report
./gradlew :app:assembleRelease && \
  ls -lh app/build/outputs/apk/release/*.apk && \
  unzip -l app/build/outputs/apk/release/app-release-unsigned.apk | \
  awk '/resources.arsc/{res=$1} /classes.dex/{dex=$1} END {
    printf "Resources: %.2fMB\nDEX: %.2fMB\n", res/1024/1024, dex/1024/1024
  }'
```

---

## Success Criteria

### Primary Goals
- ✅ Release APK < 15MB (achievable without density filtering)
- ✅ App Bundle download < 10MB for xxhdpi devices
- ✅ App Bundle download < 8MB for xhdpi devices

### Validation Checklist
- [ ] Clean build succeeds
- [ ] Release build completes without errors
- [ ] APK size meets target
- [ ] All screens function correctly
- [ ] No resource loading failures
- [ ] ProGuard mapping generated
- [ ] App installs and launches
- [ ] Critical user flows work
- [ ] No crashes in testing

---

## Next Steps After Optimization

1. **Monitor Play Console**
   - Track "App size" metrics
   - Monitor install success rate
   - Check crash reports

2. **User Feedback**
   - Survey install experience
   - Check app performance
   - Monitor ratings

3. **Continuous Optimization**
   - Review quarterly for new savings
   - Update ProGuard rules as needed
   - Optimize new features for size

---

## Support & Resources

### Documentation
- Main Report: `/docs/APK_SIZE_OPTIMIZATION_REPORT.md`
- Optimized Config: `/app/build.gradle.kts.optimized`
- Optimized ProGuard: `/app/proguard-rules.pro.optimized`

### Android Resources
- [App Bundle Docs](https://developer.android.com/guide/app-bundle)
- [R8 Shrinking](https://developer.android.com/studio/build/shrink-code)
- [Resource Shrinking](https://developer.android.com/studio/build/shrink-code#shrink-resources)

---

**Last Updated:** 2025-12-10
**Build Status:** ⚠️ Pending compilation fix
**Optimization Status:** ✅ Ready to apply
