# Build Errors Final Report

**Date:** 2025-12-10
**Build Command:** `./gradlew :app:assembleDebug`
**Status:** ❌ FAILED

## Executive Summary

The app build fails due to architectural violations where feature modules are attempting to reference drawable resources that only exist in the app module. Feature modules in a clean architecture setup should be self-contained and cannot access app module resources.

---

## Critical Errors

### Error 1: Unresolved Drawable References in feature:home

**Location:** `/Users/jonathanmallinger/Workspace/SpiritAtlas/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt:614-615`

**Error Message:**
```
e: file:///Users/jonathanmallinger/Workspace/SpiritAtlas/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt:614:30 Unresolved reference: drawable
e: file:///Users/jonathanmallinger/Workspace/SpiritAtlas/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt:615:29 Unresolved reference: drawable
```

**Problematic Code:**
```kotlin
val avatarImageResId = R.drawable.img_001_primary_app_icon
val avatarLqipResId = R.drawable.img_006_loading_spinner_icon
```

**Root Cause:**
- The `img_001_primary_app_icon` and `img_006_loading_spinner_icon` drawables exist in `/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable-*/` directories
- The `feature:home` module cannot access `app` module resources due to Android module dependency rules
- Feature modules can only access resources from modules they explicitly depend on (core:ui, core:common, domain)

**Fix Options:**

#### Option 1: Move Resources to core:ui (Recommended)
Move the shared image resources from `app/src/main/res/` to `core:ui/src/main/res/`:

```bash
# Create drawable directories in core:ui
mkdir -p core/ui/src/main/res/drawable-{hdpi,mdpi,xhdpi,xxhdpi,xxxhdpi}

# Move all img_* resources from app to core:ui
for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
  mv app/src/main/res/drawable-$density/img_*.webp core/ui/src/main/res/drawable-$density/ 2>/dev/null || true
done
```

Then update the references in HomeScreen.kt:
```kotlin
import com.spiritatlas.core.ui.R

val avatarImageResId = R.drawable.img_001_primary_app_icon
val avatarLqipResId = R.drawable.img_006_loading_spinner_icon
```

#### Option 2: Remove Progressive Image Loading from Profile Avatars
Replace the hardcoded drawable references with placeholder composables:

```kotlin
// Remove lines 614-615 and replace with:
// For now, use a simple text avatar until profiles have their own images
Box(
    modifier = Modifier
        .size(64.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primaryContainer),
    contentAlignment = Alignment.Center
) {
    Text(
        text = name.take(2).uppercase(),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}
```

---

### Error 2: Similar Issues in Other Feature Modules

**Locations:**
1. `/Users/jonathanmallinger/Workspace/SpiritAtlas/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt:130-131`
2. `/Users/jonathanmallinger/Workspace/SpiritAtlas/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt:322-323`

**Code:**
```kotlin
// ProfileScreen.kt
imageResourceId = com.spiritatlas.feature.profile.R.drawable.img_003_splash_screen_background,
lqipResourceId = com.spiritatlas.feature.profile.R.drawable.img_006_loading_spinner_icon,

// CompatibilityDetailScreen.kt
backgroundResourceId = com.spiritatlas.feature.compatibility.R.drawable.img_003_splash_screen_background,
lqipResourceId = com.spiritatlas.feature.compatibility.R.drawable.img_006_loading_spinner_icon,
```

**Issue:** These modules are referencing resources using their own namespace (`com.spiritatlas.feature.profile.R`) but the resources don't exist in those modules.

**Fix:** Apply Option 1 above, then update imports:
```kotlin
import com.spiritatlas.core.ui.R

imageResourceId = R.drawable.img_003_splash_screen_background,
lqipResourceId = R.drawable.img_006_loading_spinner_icon,
```

---

## Secondary Issues

### Warning: KAPT Incremental Build Issues

**Error:**
```
Cannot access output property 'destinationDir' of task ':app:kaptDebugKotlin'.
Accessing unreadable inputs or outputs is not supported.
```

**Cause:** Gradle configuration cache conflicts with KAPT's incremental annotation processing, especially after clean builds.

**Workaround:**
```bash
# If this error persists, disable configuration cache for KAPT tasks
./gradlew --stop  # Stop Gradle daemon
rm -rf .gradle/configuration-cache
./gradlew :app:assembleDebug --no-configuration-cache
```

### Warning: Deprecated API Usage

**Warning:**
```
API 'splits.density' is obsolete and will be removed in AGP 10.0
```

**Location:** `app/build.gradle.kts`

**Fix:** Remove density splits configuration and migrate to Android App Bundles:
```kotlin
// Remove or comment out:
splits {
    density {
        // ... density split configuration
    }
}
```

---

## Recommended Solution Path

### Step 1: Reorganize Image Resources (30 minutes)

1. Create resource directories in core:ui:
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
mkdir -p core/ui/src/main/res/drawable-{hdpi,mdpi,xhdpi,xxhdpi,xxxhdpi}
```

2. Move all `img_*.webp` files from app to core:ui:
```bash
for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
  if [ -d "app/src/main/res/drawable-$density" ]; then
    mv app/src/main/res/drawable-$density/img_*.webp core/ui/src/main/res/drawable-$density/ 2>/dev/null || true
  fi
done
```

3. Update resource_mapping.json location if it exists:
```bash
mv app/src/main/res/resource_mapping.json core/ui/src/main/res/ 2>/dev/null || true
```

### Step 2: Update Import Statements (10 minutes)

Update files to import from core:ui:

**File: feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt**
```kotlin
// Add at top of file
import com.spiritatlas.core.ui.R

// Lines 614-615 remain the same - they'll now resolve correctly
val avatarImageResId = R.drawable.img_001_primary_app_icon
val avatarLqipResId = R.drawable.img_006_loading_spinner_icon
```

**File: feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt**
```kotlin
// Update lines 130-131
imageResourceId = com.spiritatlas.core.ui.R.drawable.img_003_splash_screen_background,
lqipResourceId = com.spiritatlas.core.ui.R.drawable.img_006_loading_spinner_icon,
```

**File: feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt**
```kotlin
// Update lines 322-323
backgroundResourceId = com.spiritatlas.core.ui.R.drawable.img_003_splash_screen_background,
lqipResourceId = com.spiritatlas.core.ui.R.drawable.img_006_loading_spinner_icon,
```

### Step 3: Clean Build (5 minutes)

```bash
./gradlew clean
./gradlew :app:assembleDebug
```

---

## Build Environment Details

- **Gradle Version:** 8.13
- **Android Gradle Plugin:** 8.8.0
- **Kotlin Version:** 1.9.25
- **Compose Compiler:** 1.5.15
- **Java Version:** 17
- **Build Host:** macOS Darwin 25.1.0

---

## Test Commands

After applying fixes, verify the build:

```bash
# Full clean build
./gradlew clean assembleDebug

# Verify APK was created
ls -lh app/build/outputs/apk/debug/app-debug.apk

# Check APK size
du -h app/build/outputs/apk/debug/app-debug.apk

# Run critical tests
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test
```

---

## Additional Notes

### Why This Happened

The image generation work added drawable resources to the `app/src/main/res/` directory, but the code that references these resources is in feature modules (feature:home, feature:profile, feature:compatibility). In Android's multi-module architecture:

- App modules can see everything (they depend on all feature modules)
- Feature modules can only see core modules and domain modules
- Feature modules CANNOT see the app module (circular dependency would occur)

### Prevention

1. **Always place shared resources in core modules** (core:ui is the correct place for UI resources)
2. **Feature modules should be self-contained** - if a feature needs a resource, it should either:
   - Be in the feature's own res/ directory (feature-specific resources)
   - Be in core:ui (shared resources)
   - Never be in the app module
3. **Use import aliases** to make resource references explicit and catch these errors early

### Architecture Reminder

```
app/                      ← Application resources, can depend on everything
├── feature:home          ← Can depend on: core:ui, core:common, domain
├── feature:profile       ← Can depend on: core:ui, core:common, domain
├── feature:compatibility ← Can depend on: core:ui, core:common, domain
├── core:ui              ← Shared UI components and resources
├── core:common          ← Shared utilities
└── domain               ← Domain models and business logic
```

---

## Status After Fixes

Once the recommended solution is applied:
- ✅ All drawable resources accessible from feature modules
- ✅ Clean architecture principles maintained
- ✅ Build should complete successfully
- ✅ APK size: ~15-25 MB (estimated with image assets)
- ✅ Build time: ~45-60 seconds (clean build)

---

## Contact / Next Steps

After applying these fixes:
1. Verify build succeeds
2. Test progressive image loading works correctly in all screens
3. Consider adding lint rules to prevent future app module resource references in feature modules
4. Update architecture documentation to clarify resource placement guidelines
