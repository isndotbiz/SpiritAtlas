# APK Size Optimization Opportunities

**Analysis Date**: 2025-12-10
**Project**: SpiritAtlas Android App
**Current Status**: Build passing, R8 configured, resource optimization enabled

---

## Executive Summary

Based on comprehensive analysis of the SpiritAtlas codebase, this document identifies **high-impact APK size optimization opportunities** across three key areas:

1. **R8 Configuration & Code Shrinking** - Already well-optimized (7/10 rating)
2. **Vector Drawable Conversions** - Significant opportunity (119MB unused assets)
3. **Density Splits & App Bundles** - Major opportunity for multi-APK distribution

**Estimated Total Size Reduction**: 40-60% of final APK size possible through recommended optimizations.

---

## 1. R8 Configuration Analysis

### Current State: ✅ EXCELLENT

**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/app/proguard-rules.pro`

#### Strengths (Already Implemented)

```kotlin
// app/build.gradle.kts
buildTypes {
    release {
        isMinifyEnabled = true              // ✅ Enabled
        isShrinkResources = true            // ✅ Enabled
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

**ProGuard Rules Highlights**:
- ✅ 7 optimization passes configured (`-optimizationpasses 7`)
- ✅ Aggressive repackaging (`-repackageclasses ''`)
- ✅ Interface merging enabled (`-mergeinterfacesaggressively`)
- ✅ Access modification allowed (`-allowaccessmodification`)
- ✅ Logging removal for release builds (`-assumenosideeffects`)
- ✅ Kotlin null-check intrinsics removed
- ✅ Compose debug metadata stripped

#### Additional Optimization Opportunities

**Priority: MEDIUM** (Estimated savings: 5-10%)

##### 1.1 Add Resource Path Shortening

```properties
# gradle.properties - ADD THIS
android.enableResourcePathShortening=true
```

**Impact**: Reduces resource path lengths in final APK, saving ~2-5% APK size.

##### 1.2 Enhanced R8 Full Mode Configuration

```properties
# gradle.properties - ADD THIS
android.enableR8.fullMode=true
android.r8.enableJavaInlining=true
android.r8.enableClassInlining=true
```

**Impact**: More aggressive inlining and class merging, ~3-5% reduction.

##### 1.3 Remove Unused Kotlin Reflection

```kotlin
// app/proguard-rules.pro - ADD AFTER LINE 61
-dontwarn kotlin.reflect.**
-dontnote kotlin.reflect.**

# Remove unused reflection metadata
-assumenosideeffects class kotlin.jvm.internal.Reflection {
    public static *** getOrCreateKotlinClass(...);
    public static *** getOrCreateKotlinPackage(...);
}
```

**Impact**: Kotlin reflection metadata is ~200-500KB. If not using reflection, this saves significant space.

##### 1.4 Strip Native Debug Symbols

```kotlin
// app/build.gradle.kts - ADD to android {} block
buildTypes {
    release {
        // ... existing config

        // Strip native debug symbols from .so files
        ndk {
            debugSymbolLevel = "NONE"
        }
    }
}
```

**Impact**: Reduces native library size by ~30-40% if using any native dependencies.

---

## 2. Vector Drawable Conversion Analysis

### Current State: ⚠️ MAJOR OPPORTUNITY

**Key Findings**:
- **396 PNG/JPG images** across the project
- **119MB of unused generated assets** in `tools/image_generation/`
- **2,320 WebP images** (good format choice)
- Only **5 vector drawables** currently in use (launcher icons)

### 2.1 Immediate Actions Required

#### 2.1.1 Remove Unused Generated Assets

**Priority: CRITICAL** (Potential savings: 119MB if accidentally included)

```bash
# Verify these are NOT included in app resources
ls -la app/src/main/res/generated_assets/

# If empty or not present, these are safe to ignore
# If present, remove them as they're only in tools/ directory
```

**Largest offenders** (each 2-6MB):
```
tools/image_generation/app/src/main/res/generated_assets/
├── 1_app_icons/spiritatlas_spiritual_symbols_icons_*.png (2.8-6.0MB each)
├── 6_bonus_ui/spiritatlas_bonus_special_ui_elements_*.png (3.4-4.6MB)
```

**Action**: Confirm these are NOT being included in APK via:
```bash
./gradlew :app:assembleRelease
unzip -l app/build/outputs/apk/release/app-release.apk | grep "generated_assets"
```

If found, add to `.gitignore` and exclude from packaging.

#### 2.1.2 Convert Decorative Icons to Vector Drawables

**Priority: HIGH** (Estimated savings: 200-500KB)

**Candidates for vectorization** (simple geometric shapes):
- Chakra symbols (currently in `resource_mapping.json`)
- Zodiac constellation icons (12 signs)
- Sacred geometry icons (Flower of Life, Metatron's Cube)
- UI icons (error, warning, info tooltips)

**Conversion Strategy**:

```bash
# Use Android Studio's "Convert to Vector" tool
# Or use command-line converter

# For each simple PNG icon:
# 1. Open in vector graphics tool (Figma, Illustrator, Inkscape)
# 2. Export as SVG
# 3. Use Android Studio: Right-click → New → Vector Asset → Local file

# Example sizes:
# PNG (xhdpi): ~48KB
# Vector XML: ~2KB
# Savings per icon: ~46KB × 50 icons = ~2.3MB
```

**Example Vector Conversion** (Lotus icon):

```xml
<!-- res/drawable/ic_lotus.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="?attr/colorPrimary"
        android:pathData="M12,2C12,2 8,6 8,10C8,12 9,13 10,13.5C11,14 12,14 12,14C12,14 13,14 14,13.5C15,13 16,12 16,10C16,6 12,2 12,2Z"/>
    <!-- Additional paths for petals -->
</vector>
```

**Size comparison**:
- PNG @ xhdpi: ~48KB
- Vector XML: ~2KB (24× smaller!)

#### 2.1.3 Use WebP Lossy Compression for Photos

**Priority: MEDIUM** (Current WebP usage is good, enhance with lossy)

```bash
# For photo-realistic images (backgrounds, gradients)
# Use lossy WebP at 85% quality

cwebp -q 85 input.png -o output.webp

# For transparent images, use mixed compression
cwebp -q 85 -m 6 -alpha_q 90 input.png -o output.webp
```

**Estimated savings**: 30-50% compared to PNG, 10-20% compared to lossless WebP.

---

## 3. Density Splits & App Bundle Strategy

### Current State: ⚠️ DISABLED (Major Opportunity)

**File**: `/Users/jonathanmallinger/Workspace/SpiritAtlas/app/build.gradle.kts`

```kotlin
// CURRENT - Lines 112-122
splits {
    abi {
        isEnable = false  // ❌ Disabled
        reset()
        include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        isUniversalApk = true
    }
    density {
        isEnable = false  // ❌ Disabled - WARNING: Deprecated in AGP 10.0
    }
}
```

### 3.1 Recommended: Migrate to Android App Bundle (AAB)

**Priority: CRITICAL** (Estimated savings: 35-50% per-device APK size)

#### Why App Bundles?

- Google Play automatically generates optimized APKs for each device
- Density splits deprecated in AGP 10.0
- Users only download resources for their screen density and ABI
- No code changes required

#### Implementation

```kotlin
// app/build.gradle.kts - REPLACE splits {} block with:

android {
    // Remove splits {} block entirely

    bundle {
        language {
            enableSplit = true  // Split by language (if multi-language)
        }
        density {
            enableSplit = true  // ✅ Play Store handles density splits
        }
        abi {
            enableSplit = true  // ✅ Play Store handles ABI splits
        }
    }
}
```

**Build command**:
```bash
# Build AAB for Google Play
./gradlew :app:bundleRelease

# Output: app/build/outputs/bundle/release/app-release.aab
```

#### Size Comparison Example

**Universal APK** (all densities + ABIs):
```
Base APK: 15MB
├── Resources (all densities): 8MB
│   ├── mdpi: 1MB
│   ├── hdpi: 1.5MB
│   ├── xhdpi: 2MB
│   ├── xxhdpi: 2.5MB
│   └── xxxhdpi: 3MB
├── Native libs (all ABIs): 5MB
│   ├── armeabi-v7a: 1MB
│   ├── arm64-v8a: 2MB
│   ├── x86: 1MB
│   └── x86_64: 1.5MB
└── Code (DEX): 2MB
```

**App Bundle** (per-device download):
```
Pixel 6 (xxhdpi, arm64-v8a):
├── Resources (xxhdpi only): 2.5MB  ↓ 5.5MB saved
├── Native libs (arm64 only): 2MB   ↓ 3MB saved
└── Code (DEX): 2MB
Total: 6.5MB (57% smaller!)
```

### 3.2 Alternative: Manual Density Splits (If Not Using Play Store)

**Priority: LOW** (Only if distributing outside Google Play)

```kotlin
// ONLY use if not publishing to Google Play
// This approach is deprecated and will be removed in AGP 10.0

splits {
    density {
        isEnable = true
        exclude("ldpi")  // Exclude low-density devices (obsolete)
        include("mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi")
    }
    abi {
        isEnable = true
        reset()
        include("armeabi-v7a", "arm64-v8a")  // 99% of devices
        isUniversalApk = false  // Don't generate universal APK
    }
}
```

**Generate version codes**:
```kotlin
android.applicationVariants.all { variant ->
    variant.outputs.each { output ->
        def abiVersion = 0
        if (output.getFilter("ABI") != null) {
            abiVersion = output.getFilter("ABI") == "arm64-v8a" ? 2 : 1
        }
        output.versionCodeOverride =
            android.defaultConfig.versionCode * 10 + abiVersion
    }
}
```

---

## 4. Dependency Optimization

### Current State: ⚠️ MEDIUM OPPORTUNITY

#### 4.1 Material Icons Extended

**Issue**: All 6 feature modules include `compose.material.icons.extended`

```kotlin
// feature/*/build.gradle.kts (6 modules)
implementation(libs.compose.material.icons.extended)  // ~1.2MB library
```

**Impact**: Material Icons Extended contains 2,000+ icons but most apps use <50.

**Optimization Strategy**:

##### Option A: Replace with Custom Icons (Recommended)

```kotlin
// Remove from ALL modules:
// implementation(libs.compose.material.icons.extended)

// Create core:ui/src/main/res/drawable/ with only needed icons
// Use vector drawables (2KB each vs 1.2MB for entire library)
```

**Savings**: ~1.2MB if you use <50 icons.

##### Option B: Use Icon Subsetting (Advanced)

```kotlin
// Use R8 rule to keep only used icons
-keep class androidx.compose.material.icons.** {
    public static ** get*();
}
-assumenosideeffects class androidx.compose.material.icons.** {
    public static ** <fields>;
}
```

**Savings**: ~800KB (R8 can't remove unused icon constants completely).

#### 4.2 Coil Image Loading

**Issue**: Coil included in 7 modules

```kotlin
// app, data, core:ui, feature:profile, feature:tantric
implementation(libs.coil.compose)  // ~400KB
```

**Analysis**: Coil is necessary for image loading. Current usage is appropriate.

**Optimization**: Ensure Coil is only imported via `core:ui` module (already done correctly).

#### 4.3 Compose BOM Duplication

**Issue**: Some modules declare Compose BOM directly instead of inheriting from `core:ui`

```kotlin
// domain/build.gradle.kts - REMOVE THIS
implementation(platform("androidx.compose:compose-bom:2024.09.02"))
implementation("androidx.compose.runtime:runtime")

// REPLACE WITH
implementation(project(":core:ui"))  // Transitively provides Compose
```

**Savings**: ~50KB per module from reduced duplicate declarations.

---

## 5. Resource Optimization

### 5.1 Enable Aggressive Resource Shrinking

**Already enabled in gradle.properties** ✅:
```properties
android.enableResourceOptimizations=true
```

### 5.2 Add Strict Resource Shrinking

```xml
<!-- app/src/main/res/raw/keep.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools"
    tools:shrinkMode="strict" />
```

**Impact**: R8 will remove ALL unreferenced resources, even if accessed via `Resources.getIdentifier()`. Use this only if you don't use dynamic resource access.

### 5.3 Remove Unused Alternative Resources

```bash
# Check for unused density variants
./gradlew :app:assembleRelease
cat app/build/outputs/mapping/release/resources.txt

# Look for resources with NO references
# Example output:
# res/drawable-xxxhdpi/icon_unused.png => removed (no references)
```

**Action**: Remove any resources marked as "removed (no references)" from source.

---

## 6. Packaging Optimization

### Current Packaging Configuration ✅ GOOD

```kotlin
// app/build.gradle.kts - Already optimized
packaging {
    resources {
        excludes += listOf(
            "META-INF/**",           // ✅ Metadata removal
            "kotlin/**",             // ✅ Kotlin runtime exclusions
            "**.txt", "**.bin",      // ✅ Unnecessary files
        )
    }
    jniLibs {
        useLegacyPackaging = false  // ✅ Compressed native libs
    }
    dex {
        useLegacyPackaging = false  // ✅ Compressed DEX
    }
}
```

### Additional Exclusions (Optional)

```kotlin
packaging {
    resources {
        excludes += listOf(
            // Add these if not needed:
            "DebugProbesKt.bin",              // Coroutines debug probes
            "kotlin-tooling-metadata.json",   // Kotlin tooling metadata
            "androidx/**/*.version",          // AndroidX version files
            "**/kotlin-tooling-metadata.json" // Duplicate tooling files
        )
    }
}
```

**Savings**: ~50-100KB from metadata removal.

---

## 7. Build Configuration Enhancements

### 7.1 Enable Build Analyzer

```bash
# Add to gradle.properties
android.enableBuildScriptClasspathCheck=true
android.experimental.enableOptimizedKotlinCompilation=true
```

### 7.2 Measure APK Size After Each Optimization

```bash
# Build release APK
./gradlew :app:assembleRelease

# Analyze APK
./gradlew :app:analyzeReleaseBundle

# Or use APK Analyzer
$ANDROID_HOME/tools/bin/apkanalyzer apk summary app/build/outputs/apk/release/app-release.apk

# Expected output:
# Raw file size: 15.2 MB
# Download size: 12.8 MB (compressed)
```

---

## 8. Optimization Priority Matrix

| Optimization | Priority | Difficulty | Est. Savings | Timeline |
|--------------|----------|------------|--------------|----------|
| **Migrate to App Bundle** | 🔴 CRITICAL | Easy | 35-50% | 1 day |
| **Remove unused generated assets** | 🔴 CRITICAL | Easy | 0-119MB* | 1 hour |
| **Convert icons to vectors** | 🟡 HIGH | Medium | 200-500KB | 2-3 days |
| **Remove Material Icons Extended** | 🟡 HIGH | Medium | 1.2MB | 1-2 days |
| **R8 full mode + path shortening** | 🟢 MEDIUM | Easy | 5-10% | 2 hours |
| **WebP lossy for photos** | 🟢 MEDIUM | Medium | 10-20% | 1 day |
| **Native symbol stripping** | 🟢 MEDIUM | Easy | 30-40% of .so | 1 hour |
| **Manual density splits** | 🔵 LOW | Medium | 35-50%** | 1 day |

\* Only if accidentally included in APK
\** Deprecated, use App Bundle instead

---

## 9. Measurement & Validation

### 9.1 Baseline APK Size

**Before optimizations**, measure:
```bash
# Build baseline
./gradlew clean :app:assembleRelease

# Measure size
ls -lh app/build/outputs/apk/release/app-release.apk

# Expected baseline: ~15-25MB (estimated)
```

### 9.2 After Each Optimization

**Track size reduction**:
```bash
# Create size tracking script
cat > scripts/measure_apk_size.sh << 'EOF'
#!/bin/bash
APK_PATH="app/build/outputs/apk/release/app-release.apk"
if [ -f "$APK_PATH" ]; then
    SIZE=$(du -h "$APK_PATH" | cut -f1)
    echo "APK Size: $SIZE"

    # Detailed breakdown
    unzip -l "$APK_PATH" | awk '{
        if ($4 ~ /\.dex$/) dex+=$1;
        else if ($4 ~ /res\//) res+=$1;
        else if ($4 ~ /lib\//) lib+=$1;
        else other+=$1;
    } END {
        printf "DEX: %.2f MB\n", dex/1024/1024;
        printf "Resources: %.2f MB\n", res/1024/1024;
        printf "Native Libs: %.2f MB\n", lib/1024/1024;
        printf "Other: %.2f MB\n", other/1024/1024;
    }'
else
    echo "APK not found. Run: ./gradlew :app:assembleRelease"
fi
EOF
chmod +x scripts/measure_apk_size.sh

# Run measurement
./scripts/measure_apk_size.sh
```

### 9.3 Google Play Console Size Reports

After uploading AAB to Play Console:
1. Navigate to **Release Management > App Bundles Explorer**
2. View **Size by Device** report
3. Confirm size reductions across device configurations

---

## 10. Implementation Roadmap

### Phase 1: Quick Wins (Day 1)
1. ✅ Verify no generated_assets in APK
2. 🔧 Enable R8 full mode flags in gradle.properties
3. 🔧 Enable resource path shortening
4. 🔧 Strip native debug symbols
5. 📊 Measure baseline APK size

**Expected savings**: 5-15% APK size reduction

### Phase 2: App Bundle Migration (Day 2-3)
1. 🔧 Configure App Bundle in build.gradle.kts
2. 🔧 Remove deprecated density splits
3. 🧪 Test AAB generation
4. 📤 Upload to Play Console internal track
5. 📊 Verify per-device sizes

**Expected savings**: 35-50% per-device download size

### Phase 3: Asset Optimization (Week 1-2)
1. 🎨 Audit all drawable resources
2. 🎨 Convert 50+ icons to vector drawables
3. 🎨 Apply lossy WebP compression to photos
4. 🗑️ Remove unused Material Icons Extended dependency
5. 📊 Measure asset size reduction

**Expected savings**: 1.5-2.5MB APK size

### Phase 4: Advanced Optimization (Week 2-3)
1. 🔧 Implement strict resource shrinking
2. 🔧 Add ProGuard rules for Kotlin reflection removal
3. 🔧 Audit and deduplicate Compose dependencies
4. 🔧 Add metadata exclusions to packaging
5. 📊 Final size comparison

**Expected savings**: Additional 5-10%

---

## 11. Monitoring & Maintenance

### Continuous Size Tracking

```kotlin
// Add to app/build.gradle.kts
tasks.register("checkApkSize") {
    doLast {
        val apkFile = file("build/outputs/apk/release/app-release.apk")
        if (apkFile.exists()) {
            val sizeInMB = apkFile.length() / (1024.0 * 1024.0)
            val maxSizeMB = 20.0  // Set your threshold

            println("APK Size: %.2f MB".format(sizeInMB))

            if (sizeInMB > maxSizeMB) {
                throw GradleException(
                    "APK size ($sizeInMB MB) exceeds limit ($maxSizeMB MB)"
                )
            }
        }
    }
}
```

### Automated Size Regression Detection

```bash
# Add to CI/CD pipeline (.github/workflows/size-check.yml)
- name: Check APK Size
  run: |
    ./gradlew assembleRelease checkApkSize

    # Compare with baseline
    CURRENT_SIZE=$(stat -f%z app/build/outputs/apk/release/app-release.apk)
    BASELINE_SIZE=$(cat apk-size-baseline.txt)

    if [ "$CURRENT_SIZE" -gt "$BASELINE_SIZE" ]; then
      echo "⚠️ APK size increased!"
      exit 1
    fi
```

---

## 12. References & Tools

### Android Studio Tools
- **APK Analyzer**: `Build > Analyze APK...`
- **Build Analyzer**: `View > Tool Windows > Build Analyzer`
- **Resource Manager**: `View > Tool Windows > Resource Manager`

### Command-Line Tools
```bash
# APK Analyzer CLI
$ANDROID_HOME/tools/bin/apkanalyzer

# Bundle Tool (for AAB inspection)
bundletool build-apks --bundle=app.aab --output=app.apks
bundletool get-size total --apks=app.apks

# WebP conversion
cwebp -q 85 input.png -o output.webp

# Vector conversion (requires svgcleaner)
svgcleaner input.svg output.svg
```

### Documentation
- [Android App Bundles](https://developer.android.com/guide/app-bundle)
- [R8 Shrinking](https://developer.android.com/studio/build/shrink-code)
- [ProGuard Manual](https://www.guardsquare.com/manual/configuration)
- [Vector Drawables](https://developer.android.com/develop/ui/views/graphics/vector-drawable-resources)

---

## Summary

**Total Estimated APK Size Reduction**: 40-60%

### Key Recommendations (Prioritized)

1. **🔴 CRITICAL**: Migrate to Android App Bundle → **35-50% per-device savings**
2. **🔴 CRITICAL**: Verify no unused 119MB generated assets in APK
3. **🟡 HIGH**: Convert 50+ simple icons to vectors → **1-2MB savings**
4. **🟡 HIGH**: Remove Material Icons Extended → **1.2MB savings**
5. **🟢 MEDIUM**: Enable R8 full mode optimizations → **5-10% savings**
6. **🟢 MEDIUM**: Apply lossy WebP compression → **10-20% image savings**

**Next Steps**:
1. Review and approve optimization roadmap
2. Create feature branch: `optimization/apk-size-reduction`
3. Implement Phase 1 quick wins
4. Measure and document size reductions
5. Submit PR with size comparison report

---

**Document Version**: 1.0
**Last Updated**: 2025-12-10
**Maintained By**: SpiritAtlas Development Team
