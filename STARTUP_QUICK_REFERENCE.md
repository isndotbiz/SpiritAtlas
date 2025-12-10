# Startup Optimization Quick Reference

## TL;DR

Baseline profile optimization is **ENABLED** for SpiritAtlas. Run this to measure startup performance:

```bash
./scripts/measure_startup_time.sh
```

**Targets:** Cold < 2.0s, Warm < 1.0s

---

## Quick Commands

### Measure Startup Time
```bash
./scripts/measure_startup_time.sh
```

### Build and Install
```bash
./gradlew clean :app:assembleDebug
./gradlew installDebug
adb shell am start -n com.spiritatlas.app/.MainActivity
```

### Check Profile Status
```bash
# Verify profile in APK
unzip -l app/build/outputs/apk/debug/app-debug.apk | grep baseline

# Check profile installation log
adb logcat | grep ProfileInstaller
```

### Manual Timing
```bash
# Cold start
adb shell am force-stop com.spiritatlas.app
adb shell pm clear com.spiritatlas.app
adb shell am start -W -n com.spiritatlas.app/.MainActivity
# Look for "TotalTime: XXXXms"
```

---

## File Locations

| File | Path |
|------|------|
| **Baseline Profile** | `app/src/main/baseline-prof.txt` |
| **Build Config** | `app/build.gradle.kts` |
| **Dependencies** | `gradle/libs.versions.toml` |
| **Measurement Script** | `scripts/measure_startup_time.sh` |
| **Full Documentation** | `STARTUP_OPTIMIZATION.md` |
| **Status Report** | `BASELINE_PROFILE_STATUS.md` |

---

## Profile Format

```
[FLAGS][CLASS];->[METHOD]([PARAMETERS])[RETURN]
```

**Flags:**
- **H** = Hot (always compile)
- **S** = Startup (compile during startup)
- **P** = Post-startup (compile after startup)
- **L** = Layout (compile during layout)

**Example:**
```
HSPLcom/spiritatlas/app/SplashScreen;->SplashScreen(...)V
```

---

## Current Status

✅ **Baseline Profile:** Enabled
✅ **Methods Optimized:** 25
✅ **Profile Size:** 2.6 KB
✅ **Configuration:** Complete
⏳ **Performance:** Pending measurement

---

## Quick Fixes

### Startup Too Slow?

1. **Update profile:**
   - Use Android Studio: Run > Profile 'app' with Startup
   - Add slow methods to `app/src/main/baseline-prof.txt`
   - Rebuild: `./gradlew clean assembleDebug`

2. **Defer initialization:**
   ```kotlin
   // Move to background thread
   lifecycleScope.launch {
       initializeNonCritical()
   }
   ```

3. **Check for blocking calls:**
   ```bash
   adb logcat | grep StrictMode
   ```

### Profile Not Working?

1. **Check device API:** Must be Android 7+ (API 24+)
2. **Verify installation:** `adb logcat | grep ProfileInstaller`
3. **Check APK:** `unzip -l app.apk | grep baseline`

---

## Performance Checklist

Quick checklist for startup optimization:

- [x] Baseline profile enabled
- [x] ProfileInstaller dependency added
- [x] Profile includes Application.onCreate()
- [x] Profile includes first screen
- [ ] Startup time measured
- [ ] Targets met (< 2.0s cold, < 1.0s warm)
- [ ] Monitoring enabled

---

## When to Update Profile

Update `app/src/main/baseline-prof.txt` when:

- ✅ Adding new features to startup flow
- ✅ Refactoring initialization code
- ✅ Startup time regresses
- ✅ Adding critical screens
- ✅ Major dependency updates

---

## Documentation Hierarchy

1. **This file** - Quick reference (you are here)
2. **`BASELINE_PROFILE_STATUS.md`** - Implementation status
3. **`STARTUP_OPTIMIZATION.md`** - Comprehensive guide (400+ lines)
4. **`CLAUDE.md`** - Project guidelines

---

## Common Methods to Optimize

High-impact methods for baseline profile:

```
# Application lifecycle
HSPLcom/spiritatlas/app/SpiritAtlasApplication;->onCreate()V

# First screen
HSPLcom/spiritatlas/app/MainActivity;->onCreate(Landroid/os/Bundle;)V
HSPLcom/spiritatlas/app/SplashScreen;->SplashScreen(...)V

# ViewModels
HSPLcom/spiritatlas/app/SplashViewModel;-><init>()V
HSPLcom/spiritatlas/feature/home/HomeViewModel;-><init>()V

# Navigation
HSPLcom/spiritatlas/app/navigation/SpiritAtlasNavGraphKt;->SpiritAtlasNavGraph(...)V

# Theme and UI
PLcom/spiritatlas/core/ui/theme/ThemeKt;->SpiritAtlasTheme(...)V
PLcom/spiritatlas/core/ui/components/ModernComponentsKt;->*

# Compose runtime (hot paths)
PLandroidx/compose/runtime/ComposerImpl;->startRestartGroup(I)...
PLandroidx/compose/ui/platform/AndroidComposeView;->onMeasure(II)V
```

---

## Measurement Script Output

The measurement script reports:

```
Cold Start Average: XXXXms
  Target:  < 2000ms
  Status:  PASS ✓ / NEEDS IMPROVEMENT

Warm Start Average: XXXms
  Target:  < 1000ms
  Status:  PASS ✓ / NEEDS IMPROVEMENT

Baseline Profile:
  Status:   ENABLED ✓
  Entries:  25 methods
```

---

## Next Action

**Run this now to establish baseline:**

```bash
./scripts/measure_startup_time.sh
```

Then update the measurement results in `STARTUP_OPTIMIZATION.md`.

---

**Last Updated:** 2025-12-10
**Quick Help:** See `STARTUP_OPTIMIZATION.md` for details
