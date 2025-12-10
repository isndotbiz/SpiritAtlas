# Resource Cleanup Verification Checklist

Use this checklist to verify the resource cleanup was successful and didn't break the app.

## Pre-Build Verification

- [x] Archive directory created: `/archived_resources/`
- [x] 495 drawable files moved to archive
- [x] 99 unique images archived (across 5 density folders)
- [x] 20 active drawables retained in `/app/src/main/res/drawable-*/`
- [x] Unused colors removed from `colors.xml`
- [x] Original `colors.xml` backed up to archive
- [x] Resource keep configuration created: `/app/src/main/res/raw/keep.xml`
- [x] Deprecated Gradle flag removed from `gradle.properties`

## Build Verification

Run these commands to verify the build:

```bash
# Clean build
./gradlew clean

# Assemble debug APK
./gradlew :app:assembleDebug

# Check for resource errors
./gradlew :app:lintDebug | grep -i "resource\|missing"
```

### Expected Results
- [ ] Build completes without resource-related errors
- [ ] No "Unresolved reference" errors for drawables
- [ ] No "Resource not found" warnings in lint output

## Runtime Verification

### Manual Testing
Test these screens to ensure all images load correctly:

- [ ] **Splash Screen** - Verify splash background loads
- [ ] **Home Screen** - Check all UI elements render
- [ ] **Profile Creation** - Verify buttons and icons appear
- [ ] **Profile Library** - Check empty state illustrations
- [ ] **Compatibility Screen** - Verify all UI components
- [ ] **Settings Screen** - Check icons and buttons

### Logcat Verification
```bash
# Monitor for resource errors
adb logcat | grep -E "ResourceNotFoundException|drawable|Resource"
```

- [ ] No ResourceNotFoundException errors
- [ ] No missing drawable warnings
- [ ] All screens load without visual errors

## Active Drawable Verification

Verify these 20 active drawables are present and load correctly:

### App Icons & Branding
- [ ] `img_001_primary_app_icon`
- [ ] `img_002_alternative_app_icon_dark_mode`
- [ ] `img_003_splash_screen_background`
- [ ] `img_004_wordmark_logo`
- [ ] `img_005_monogram_icon`
- [ ] `img_006_loading_spinner_icon`
- [ ] `img_007_app_store_feature_graphic`
- [ ] `img_008_notification_icon`

### UI Components
- [ ] `img_074_primary_action_button_normal_state`
- [ ] `img_075_primary_action_button_pressed_state`
- [ ] `img_076_secondary_button_outline_style`
- [ ] `img_077_card_background_glassmorphic`
- [ ] `img_078_loading_state_circular_progress`
- [ ] `img_079_loading_state_linear_progress_bar`

### Empty States & Feedback
- [ ] `img_080_empty_state_illustration_no_profiles`
- [ ] `img_081_empty_state_illustration_no_compatibility_result`
- [ ] `img_082_success_checkmark_icon`
- [ ] `img_083_error_warning_icon`
- [ ] `img_084_info_tooltip_icon`
- [ ] `img_085_dropdown_chevron_icon`

## Automated Test Verification

Run the test suite to ensure no regressions:

```bash
# Run core module tests
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

# Run feature tests
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest

# Run instrumented tests (if available)
./gradlew connectedDebugAndroidTest
```

- [ ] All tests pass
- [ ] No new test failures related to resources
- [ ] No missing resource errors in test logs

## APK Size Verification

Generate APKs and measure size reduction:

```bash
# Build debug APK
./gradlew :app:assembleDebug

# Build release APK
./gradlew :app:assembleRelease

# Check APK sizes
ls -lh app/build/outputs/apk/debug/
ls -lh app/build/outputs/apk/release/
```

- [ ] Debug APK is significantly smaller (~43 MB reduction expected)
- [ ] Release APK shows size reduction (~15-20 MB expected)
- [ ] APK can be installed successfully
- [ ] App launches without errors

## Multi-Density Testing

Test on devices/emulators with different screen densities:

- [ ] **mdpi** (160dpi) - e.g., older phones
- [ ] **hdpi** (240dpi) - e.g., Nexus 5
- [ ] **xhdpi** (320dpi) - e.g., Pixel 2
- [ ] **xxhdpi** (480dpi) - e.g., Pixel 5
- [ ] **xxxhdpi** (640dpi) - e.g., Pixel 6 Pro

### Expected Results
- [ ] All 20 active drawables load correctly on each density
- [ ] No "Resource not found" errors for any density
- [ ] Images scale appropriately for screen size

## Resource Shrinking Verification

Verify R8 resource shrinking configuration:

```bash
# Check build configuration
cat app/build.gradle.kts | grep -A 5 "buildTypes"

# Check resource keep rules
cat app/src/main/res/raw/keep.xml
```

- [ ] `isMinifyEnabled = true` for release builds
- [ ] `isShrinkResources = true` for release builds
- [ ] `keep.xml` exists and contains proper rules
- [ ] No conflicting resource shrinking configurations

## Archive Integrity Verification

Verify the archive is complete and restorable:

```bash
# Count archived files
find archived_resources/drawables -name "*.webp" | wc -l
# Should output: 495

# Check archive size
du -sh archived_resources/
# Should output: ~43M

# Verify inventory file exists
cat archived_resources/ARCHIVED_INVENTORY.txt | wc -l
# Should output: 101 (99 drawables + 2 header lines)
```

- [ ] 495 drawable files in archive
- [ ] Archive size is approximately 43 MB
- [ ] ARCHIVED_INVENTORY.txt exists and is complete
- [ ] RESTORE_GUIDE.md exists with restoration scripts

## Rollback Test (Optional)

Test that resources can be restored if needed:

```bash
# Restore a single image as a test
cp archived_resources/drawables/hdpi/img_009_home_screen_background.webp \
   app/src/main/res/drawable-hdpi/

# Verify it appears in the resource directory
ls app/src/main/res/drawable-hdpi/img_009_home_screen_background.webp

# Remove it to revert the test
rm app/src/main/res/drawable-hdpi/img_009_home_screen_background.webp
```

- [ ] Files can be copied from archive
- [ ] Restored files are valid and not corrupted
- [ ] Restoration doesn't cause build errors

## Performance Metrics

### Before Cleanup
- Total drawable files: 610
- Total drawable size: ~45.5 MB
- Color resources: 7 definitions

### After Cleanup
- Total drawable files: 115
- Total drawable size: ~2.3 MB
- Color resources: 0 definitions

### Improvements
- [ ] 81% reduction in drawable file count (610 → 115)
- [ ] 95% reduction in drawable size (45.5 MB → 2.3 MB)
- [ ] 100% reduction in unused color definitions

## Sign-Off

Once all checkboxes are marked, the cleanup is verified and safe to commit.

**Verified by:** ___________________
**Date:** ___________________
**Commit hash:** ___________________

---

## Troubleshooting

### If resources are missing after cleanup:

1. Check logcat for ResourceNotFoundException errors
2. Identify the missing resource name
3. Restore from archive using RESTORE_GUIDE.md
4. Update the resource reference in code if needed

### If build fails after cleanup:

1. Check the error message for resource-related issues
2. Verify all active resources are still in `/app/src/main/res/drawable-*/`
3. Check `keep.xml` for correct resource patterns
4. If needed, restore all resources: `cp -r archived_resources/drawables/* app/src/main/res/`

### If tests fail after cleanup:

1. Check test logs for ResourceNotFoundException
2. Verify test resources (if any) weren't moved to archive
3. Update test code to use mock resources instead of real drawables
4. If needed, restore specific test resources from archive

---

**Cleanup Date:** 2025-12-10
**Agent:** OPTIMIZATION AGENT 1 - Unused Resource Cleaner
**Status:** Ready for verification
