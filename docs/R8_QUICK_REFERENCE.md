# R8 Optimization Quick Reference

## Quick Status

| Setting | Value | Location |
|---------|-------|----------|
| **R8 Mode** | ✅ Full Mode | `gradle.properties` |
| **Code Shrinking** | ✅ Enabled | `app/build.gradle.kts` |
| **Resource Shrinking** | ✅ Enabled | `app/build.gradle.kts` |
| **Optimization Passes** | 7 | `proguard-rules.pro` |
| **Obfuscation** | ✅ Aggressive | `proguard-rules.pro` |
| **Expected APK Reduction** | 30-40% | N/A |

---

## Common Commands

### Build Release APK
```bash
./gradlew :app:assembleRelease
```

### Check APK Size
```bash
ls -lh app/build/outputs/apk/release/app-release.apk
```

### Analyze APK
```bash
./gradlew :app:analyzeReleaseBundle
```

### Install Release Build
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

### View ProGuard Mapping
```bash
cat app/build/outputs/mapping/release/mapping.txt | less
```

---

## Key Configuration Locations

### app/build.gradle.kts
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true           // ✅ Code shrinking
        isShrinkResources = true         // ✅ Resource shrinking
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

### gradle.properties
```properties
# R8 Full Mode (already enabled)
# Note: R8 is default in AGP 7.0+, explicit flag not needed
```

### proguard-rules.pro
```proguard
# Key optimization flags
-optimizationpasses 7
-allowaccessmodification
-repackageclasses ''
-overloadaggressively
-mergeinterfacesaggressively
```

---

## Common ProGuard Rules

### Keep a Class
```proguard
-keep class com.example.MyClass { *; }
```

### Keep Only Public Methods
```proguard
-keep class com.example.MyClass {
    public <methods>;
}
```

### Keep with Obfuscation
```proguard
-keep,allowobfuscation class com.example.MyClass { *; }
```

### Keep Classes with Annotation
```proguard
-keep @com.example.MyAnnotation class * { *; }
```

### Remove Logging
```proguard
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}
```

---

## Troubleshooting

### App Crashes After R8

1. **Check logcat for ClassNotFoundException:**
   ```bash
   adb logcat | grep "ClassNotFoundException"
   ```

2. **Add keep rule for missing class:**
   ```proguard
   -keep class com.missing.ClassName { *; }
   ```

3. **Rebuild:**
   ```bash
   ./gradlew clean :app:assembleRelease
   ```

### Hilt Injection Fails

**Add to proguard-rules.pro:**
```proguard
-keep class **_HiltModules { *; }
-keep class **_Factory { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }
```

### Retrofit API Fails

**Add to proguard-rules.pro:**
```proguard
-keep class com.spiritatlas.data.api.** { *; }
-keep class **JsonAdapter { *; }
```

### Compose UI Broken

**Verify these rules exist:**
```proguard
-keep @androidx.compose.runtime.Stable class * { *; }
-keep @androidx.compose.runtime.Immutable class * { *; }
```

---

## APK Size Breakdown

### Before R8 (Debug)
- **DEX Files:** 6-8 MB
- **Resources:** 4-6 MB
- **Native Libs:** 2-3 MB
- **Metadata:** 2-3 MB
- **Total:** ~15-20 MB

### After R8 (Release)
- **DEX Files:** 3-4 MB (↓ 50%)
- **Resources:** 2-3 MB (↓ 40%)
- **Native Libs:** 2-3 MB (same)
- **Metadata:** 0.5-1 MB (↓ 70%)
- **Total:** ~8-12 MB (↓ 35%)

---

## Testing Checklist

- [ ] Build succeeds: `./gradlew :app:assembleRelease`
- [ ] APK size < 12 MB
- [ ] App launches without crash
- [ ] All screens render correctly
- [ ] Navigation works
- [ ] API calls succeed
- [ ] Database operations work
- [ ] Hilt injection functions
- [ ] ProGuard mapping file generated

---

## Advanced Options

### Enable APK Splits (Per-ABI)

**In app/build.gradle.kts:**
```kotlin
splits {
    abi {
        isEnable = true  // Enable splits
        reset()
        include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        isUniversalApk = false  // Disable universal APK
    }
}
```

**Result:** 4 separate APKs, each 5-8 MB (↓ 50-60%)

### Build App Bundle (Recommended)

```bash
./gradlew :app:bundleRelease
```

**Output:** `app/build/outputs/bundle/release/app-release.aab`
**Upload to:** Google Play Store
**Benefit:** Automatic optimization per-device (50-70% smaller downloads)

---

## Resources

- **Full Documentation:** `docs/R8_OPTIMIZATION_CONFIGURATION.md`
- **Android R8 Guide:** https://developer.android.com/studio/build/shrink-code
- **ProGuard Manual:** https://www.guardsquare.com/manual/home
- **APK Analyzer:** Android Studio > Build > Analyze APK

---

**Last Updated:** 2025-12-10
**Status:** ✅ Configuration Complete | ⏳ Build Validation Pending
