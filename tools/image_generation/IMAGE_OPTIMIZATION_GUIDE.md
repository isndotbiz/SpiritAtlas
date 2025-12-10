# Android Image Optimization Guide for SpiritAtlas

Complete guide for optimizing FLUX 1.1 Pro generated images for Android deployment.

## Table of Contents
- [Overview](#overview)
- [Quick Start](#quick-start)
- [Android Image Best Practices](#android-image-best-practices)
- [Recommended Sizes](#recommended-sizes)
- [Density Mapping](#density-mapping)
- [Loading Strategies](#loading-strategies)
- [Caching Configuration](#caching-configuration)
- [App Size Impact](#app-size-impact)
- [Performance Optimization](#performance-optimization)
- [Troubleshooting](#troubleshooting)

---

## Overview

SpiritAtlas includes 99 high-resolution images generated with FLUX 1.1 Pro. This guide helps you optimize them for Android while maintaining visual quality and minimizing app size.

### Key Optimization Strategies

1. **WebP Format**: 25-35% smaller than PNG with comparable quality
2. **Multiple Densities**: Serve appropriately sized images per device
3. **Lazy Loading**: Load images only when needed
4. **Memory Caching**: Reuse decoded bitmaps
5. **Disk Caching**: Persist transformed images

### Current Asset Inventory

| Category | Count | Original Sizes | Total Size |
|----------|-------|----------------|------------|
| App Icons & Branding | 24 | 96×96 to 1200×300 | ~35 MB |
| Spiritual Symbols | 28 | 200×200 to 512×512 | ~55 MB |
| Avatars & Placeholders | 6 | 128×128 to 600×600 | ~3 MB |
| Buttons & Controls | 12 | 128×128 to 512×512 | ~15 MB |
| Backgrounds | 8 | 1024×1024 to 1080×1920 | ~18 MB |
| Bonus UI | 21 | Various | ~20 MB |
| **Total** | **99** | | **~146 MB** |

---

## Quick Start

### 1. Install Dependencies

```bash
cd tools/image_generation
pip install Pillow
```

### 2. Run Optimization

```bash
# Preview what will happen (dry run)
python optimize_for_android.py --dry-run

# Optimize all images
python optimize_for_android.py

# With resource mapping
python optimize_for_android.py --create-mapping
```

### 3. Verify Output

Check the generated resources:
```bash
ls -lh app/src/main/res/drawable-*/
```

### 4. Update Build Configuration

Already configured in `core/ui/build.gradle.kts`:
```kotlin
implementation("io.coil-kt:coil-compose:2.7.0")
```

---

## Android Image Best Practices

### Format Selection

| Format | Use Case | Transparency | Compression | Android Support |
|--------|----------|--------------|-------------|-----------------|
| **WebP** | All assets (recommended) | Yes | Lossy/Lossless | Android 4.0+ (API 14+) |
| PNG | Legacy compatibility | Yes | Lossless | All versions |
| JPEG | Photos (no transparency) | No | Lossy | All versions |
| Vector (XML) | Simple icons, logos | Yes | N/A | All versions |

**Recommendation**: Use WebP for all raster images. It provides:
- 25-35% size reduction vs PNG
- Transparency support
- Better quality than JPEG at same file size
- Excellent Android support (min SDK 26 = Android 8.0)

### WebP Quality Settings

Our optimization script uses different quality levels per category:

```python
APP_ICONS = 100 (lossless)      # Maximum quality for launcher
SYMBOLS = 92                     # High quality for spiritual icons
AVATARS = 88                     # Good quality, smaller size
BUTTONS = 90                     # High quality for UI
BACKGROUNDS = 85                 # More compression acceptable
```

---

## Recommended Sizes

### Size Categories by Asset Type

#### 1. App Icons & Launcher Resources

```
Primary App Icon (launcher):
- xxxhdpi: 512×512  (Store listing)
- xxhdpi:  384×384  (Most devices)
- xhdpi:   256×256
- hdpi:    192×192
- mdpi:    128×128

Adaptive Icon Layers (foreground + background):
- xxxhdpi: 432×432  (with safe zone)
- xxhdpi:  324×324
- xhdpi:   216×216
- hdpi:    162×162
- mdpi:    108×108

Notification Icons:
- xxxhdpi: 96×96
- xxhdpi:  72×72
- xhdpi:   48×48
- hdpi:    36×36
- mdpi:    24×24
```

**Note**: Our 1024×1024 generated icons map to **xxxhdpi** (4×) density.

#### 2. Spiritual Symbols (Chakras, Zodiac, Moon Phases)

Original: 512×512, 400×400, 200×200

```
For 512×512 images (base: xhdpi = 2×):
- xxxhdpi: 1024×1024  (skip - too large)
- xxhdpi:  768×768
- xhdpi:   512×512    ← Original
- hdpi:    384×384
- mdpi:    256×256

For 400×400 images (base: xhdpi = 2×):
- xxhdpi:  600×600
- xhdpi:   400×400    ← Original
- hdpi:    300×300
- mdpi:    200×200

For 200×200 images (base: hdpi = 1.5×):
- xxhdpi:  400×400
- xhdpi:   267×267
- hdpi:    200×200    ← Original
- mdpi:    133×133
```

**Recommendation**: For most spiritual symbols displayed at ~48-72dp:
- Target xxhdpi: 144-216px
- Skip xxxhdpi variants to save space

#### 3. Avatar Placeholders

Original: 512×512 (avatars), 600×600 (frames)

```
Avatar Display Size: 64-128dp typically

For 512×512 avatars (base: xhdpi = 2×):
- xxhdpi:  768×768    (for 256dp display)
- xhdpi:   512×512    ← Original
- hdpi:    384×384
- mdpi:    256×256

Profile detail view: up to 256dp
- xxhdpi:  768×768
- xhdpi:   512×512
```

#### 4. Buttons & Interactive Elements

Original: 128×128 to 512×512

```
Small Buttons (128×128):
- Base: hdpi (1.5×)
- xxhdpi:  256×256
- xhdpi:   171×171
- hdpi:    128×128    ← Original
- mdpi:    85×85

Medium Buttons (256×256):
- Base: xhdpi (2×)
- xxhdpi:  384×384
- xhdpi:   256×256    ← Original
- hdpi:    192×192
- mdpi:    128×128

Large Buttons (512×512):
- Same as spiritual symbols above
```

#### 5. Backgrounds

Original: 1024×1024, 1024×1536, 1080×1920

```
Full Screen Background (1080×1920):
- Base: xxhdpi (3×) for 360×640dp screen
- xxxhdpi: 1440×2560  (skip - apply max dimension)
- xxhdpi:  1080×1920  ← Original
- xhdpi:   720×1280
- hdpi:    540×960
- mdpi:    360×640

Square Backgrounds (1024×1024):
- Apply max_dimension = 1920 to prevent excessive size
- Generate xxhdpi and xhdpi only
```

**Important**: Backgrounds benefit most from lossy compression (quality=85).

---

## Density Mapping

### Understanding Android Densities

Android uses **density-independent pixels (dp)** to ensure consistent sizing across devices.

```
1 dp = physical pixels × (dpi / 160)

Examples:
- mdpi (160dpi):  1dp = 1px   (baseline)
- hdpi (240dpi):  1dp = 1.5px
- xhdpi (320dpi): 1dp = 2px
- xxhdpi (480dpi): 1dp = 3px
- xxxhdpi (640dpi): 1dp = 4px
```

### Determining Base Density

**The base density is the density your original image was designed for.**

```python
# Formula
base_density_scale = original_pixels / intended_dp_size

# Example 1: App icon 1024×1024 for 256dp display
base_scale = 1024 / 256 = 4.0  → xxxhdpi (4×)

# Example 2: Spiritual symbol 512×512 for 256dp display
base_scale = 512 / 256 = 2.0  → xhdpi (2×)

# Example 3: Moon phase 200×200 for 133dp display
base_scale = 200 / 133 = 1.5  → hdpi (1.5×)
```

### Scaling Strategy

Our script automatically scales from base density:

```python
def calculate_target_size(original_size, base_density, target_density):
    scale_factor = target_density.scale / base_density.scale
    new_width = int(original_size[0] * scale_factor)
    new_height = int(original_size[1] * scale_factor)
    return (new_width, new_height)

# Example: 512×512 xhdpi (2×) → xxhdpi (3×)
scale_factor = 3.0 / 2.0 = 1.5
new_size = (512 × 1.5, 512 × 1.5) = (768, 768)
```

### Which Densities to Generate?

**Recommended approach**:

1. **Essential**: mdpi, hdpi, xhdpi, xxhdpi (covers 95% of devices)
2. **Optional**: xxxhdpi (only for icons and key assets)
3. **Skip**: ldpi (obsolete)

**Device distribution (2024)**:
- xxhdpi: ~45% (most common)
- xhdpi: ~30%
- xxxhdpi: ~15%
- hdpi: ~8%
- mdpi: ~2%

**Space-saving strategy**:
- Generate all densities for icons, avatars, buttons (<100KB each)
- Generate xxhdpi + xhdpi only for backgrounds (>500KB each)
- Let Android scale up/down for missing densities (minor quality loss)

---

## Loading Strategies

### Coil Configuration

Coil is already integrated. Optimize it for SpiritAtlas:

#### 1. Create Custom ImageLoader (Singleton)

```kotlin
// core/ui/src/main/java/com/spiritatlas/core/ui/image/SpiritImageLoader.kt

package com.spiritatlas.core.ui.image

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25) // 25% of available memory
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50 * 1024 * 1024) // 50 MB
                    .build()
            }
            .respectCacheHeaders(false)
            .crossfade(true)
            .crossfade(300) // 300ms fade
            // Debug only - remove in production
            .logger(DebugLogger())
            .build()
    }
}
```

#### 2. Composable Extensions

```kotlin
// core/ui/src/main/java/com/spiritatlas/core/ui/image/SpiritImage.kt

package com.spiritatlas.core.ui.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

/**
 * Optimized image loader for SpiritAtlas assets
 *
 * Features:
 * - Automatic density selection
 * - Memory and disk caching
 * - Crossfade animations
 * - Placeholder support
 */
@Composable
fun SpiritImage(
    @DrawableRes resourceId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null,
    crossfade: Boolean = true,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(resourceId)
            .crossfade(crossfade)
            .crossfade(if (crossfade) 300 else 0)
            .size(Size.ORIGINAL) // Let Coil pick best density
            .scale(Scale.FIT)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        placeholder = placeholder?.let { painterResource(it) },
        error = error?.let { painterResource(it) },
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError
    )
}

/**
 * Optimized spiritual symbol loader
 * Pre-configured for chakras, zodiac, moon phases
 */
@Composable
fun SpiritualSymbol(
    @DrawableRes resourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
) {
    SpiritImage(
        resourceId = resourceId,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha,
        crossfade = true,
        filterQuality = FilterQuality.High
    )
}

/**
 * Optimized avatar loader with circular crop
 */
@Composable
fun AvatarImage(
    @DrawableRes resourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int? = null,
) {
    SpiritImage(
        resourceId = resourceId,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        crossfade = true
    )
}
```

#### 3. Preloading Strategy

For critical images (app icon, splash, default avatar):

```kotlin
// app/src/main/java/com/spiritatlas/app/preload/ImagePreloader.kt

package com.spiritatlas.app.preload

import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePreloader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) {
    /**
     * Preload critical images during app startup
     * Call from Application.onCreate() or splash screen
     */
    suspend fun preloadCriticalAssets() {
        val criticalImages = listOf(
            R.drawable.app_icon_primary,
            R.drawable.default_avatar_cosmic_silhouette_feminine,
            R.drawable.default_avatar_cosmic_silhouette_masculine,
            R.drawable.default_avatar_cosmic_silhouette_non_binary,
            // Add other frequently used images
        )

        criticalImages.forEach { resourceId ->
            val request = ImageRequest.Builder(context)
                .data(resourceId)
                .build()
            imageLoader.enqueue(request)
        }
    }
}
```

### Lazy Loading with Paging

For lists of profiles or compatibility results:

```kotlin
@Composable
fun ProfileList(profiles: LazyPagingItems<Profile>) {
    LazyColumn {
        items(profiles) { profile ->
            ProfileCard(
                profile = profile,
                onProfileClick = { /* Navigate */ }
            )
        }
    }
}

@Composable
fun ProfileCard(profile: Profile, onProfileClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onProfileClick() }
    ) {
        Row {
            // Avatar loads only when visible in viewport
            AvatarImage(
                resourceId = profile.avatarResourceId,
                contentDescription = "Avatar for ${profile.name}",
                modifier = Modifier.size(64.dp),
                placeholder = R.drawable.default_avatar_cosmic_silhouette_feminine
            )

            Text(profile.name)
        }
    }
}
```

---

## Caching Configuration

### Memory Cache

**Purpose**: Avoid re-decoding images from disk

```kotlin
MemoryCache.Builder(context)
    .maxSizePercent(0.25)  // 25% of app memory
    // For 256MB device: 64MB cache
    // For 512MB device: 128MB cache
    .build()
```

**Size estimation**:
- ARGB_8888 (default): width × height × 4 bytes
- Example: 512×512 image = 1 MB in memory
- 64MB cache = ~64 images at 512×512

**Tuning**:
- Increase to 0.30-0.35 if app is image-heavy
- Decrease to 0.15-0.20 if memory constrained

### Disk Cache

**Purpose**: Persist processed images (resized, cropped, etc.)

```kotlin
DiskCache.Builder()
    .directory(context.cacheDir.resolve("image_cache"))
    .maxSizeBytes(50 * 1024 * 1024)  // 50 MB
    .build()
```

**What's cached**:
- Transformed images (scaled, cropped)
- NOT raw resources (already on disk)

**Tuning**:
- 50 MB = recommended for SpiritAtlas
- Increase to 100 MB if using remote images (future feature)
- Reduce to 25 MB if storage constrained

### Cache Policies

```kotlin
ImageRequest.Builder(context)
    .data(resourceId)
    .memoryCachePolicy(CachePolicy.ENABLED)  // Default
    .diskCachePolicy(CachePolicy.ENABLED)    // Default
    .networkCachePolicy(CachePolicy.DISABLED) // No network for bundled assets
    .build()
```

### Clearing Caches

```kotlin
// Clear memory cache
imageLoader.memoryCache?.clear()

// Clear disk cache
imageLoader.diskCache?.clear()

// In Kotlin coroutines
scope.launch {
    imageLoader.memoryCache?.clear()
    imageLoader.diskCache?.clear()
}
```

---

## App Size Impact

### Before Optimization (Current)

```
PNG images: 146 MB
APK impact: ~146 MB (uncompressed in APK)
Download size: ~100 MB (compressed)
```

### After Optimization (Projected)

#### Strategy 1: All Densities, WebP Quality 90

```
Generated densities: mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi
Total images: 99 × 5 = 495 files

Size breakdown:
- App icons (24 × 5): ~15 MB
- Symbols (28 × 5): ~35 MB
- Avatars (6 × 5): ~2 MB
- Buttons (12 × 5): ~8 MB
- Backgrounds (8 × 3): ~12 MB  (xxhdpi, xhdpi, hdpi only)
- Bonus UI (21 × 5): ~18 MB

Total: ~90 MB (38% reduction)
APK size: ~90 MB
Download size: ~65 MB
```

#### Strategy 2: Essential Densities Only (Recommended)

```
Generated densities: hdpi, xhdpi, xxhdpi (+ xxxhdpi for icons only)
Total images:
- Icons: 24 × 4 = 96
- Others: 75 × 3 = 225
- Total: 321 files

Size breakdown:
- App icons (24 × 4): ~12 MB
- Symbols (28 × 3): ~25 MB
- Avatars (6 × 3): ~1.5 MB
- Buttons (12 × 3): ~6 MB
- Backgrounds (8 × 2): ~8 MB  (xxhdpi, xhdpi only)
- Bonus UI (21 × 3): ~12 MB

Total: ~65 MB (55% reduction)
APK size: ~65 MB
Download size: ~45 MB
```

#### Strategy 3: Aggressive Optimization

```
- Use WebP quality 85 (vs 90)
- Skip mdpi entirely (let Android scale from hdpi)
- Backgrounds xxhdpi only (let Android scale)
- Symbols and buttons xhdpi + xxhdpi only

Total: ~40 MB (73% reduction)
APK size: ~40 MB
Download size: ~30 MB
```

**Recommendation**: Start with Strategy 2, measure performance on target devices, then optimize further if needed.

### Android App Bundle (AAB) Benefits

When publishing to Play Store, use Android App Bundle:

```gradle
// app/build.gradle.kts
android {
    bundle {
        density {
            enableSplit = true  // Deliver only required density
        }
        language {
            enableSplit = true
        }
    }
}
```

**Impact**:
- User downloads only their device's density
- xxhdpi device: Downloads xxhdpi images only (~20 MB)
- Total savings: 60-70% vs including all densities

---

## Performance Optimization

### 1. Measure Image Load Performance

```kotlin
// Enable Coil logging
ImageLoader.Builder(context)
    .logger(DebugLogger())
    .build()

// Monitor in Logcat
adb logcat | grep "Coil"
```

### 2. Avoid Overdraw

```kotlin
// Bad: Multiple background layers
Box {
    Image(background1)  // Hidden by background2
    Image(background2)
    Content()
}

// Good: Single background
Box {
    Image(background2)
    Content()
}
```

### 3. Use Correct Image Size

```kotlin
// Bad: Large image scaled down
SpiritImage(
    resourceId = R.drawable.background_1920x1920,  // 1920×1920
    modifier = Modifier.size(100.dp),  // Display at 100dp = wasted memory
    contentScale = ContentScale.Crop
)

// Good: Use appropriate size or let Coil handle it
SpiritImage(
    resourceId = R.drawable.symbol_chakra_heart_512,  // 512×512
    modifier = Modifier.size(100.dp),
    contentScale = ContentScale.Fit
)
```

Coil will automatically select the best density:
- 100dp on xxhdpi = 300px → loads xhdpi (512px) and scales down
- Efficient and good quality

### 4. Composable Recomposition

```kotlin
// Bad: Image request recreated on every recomposition
@Composable
fun ProfileImage(profile: Profile) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(profile.avatarId)
        .build()  // ← Recreated on every recomposition!

    AsyncImage(model = request, ...)
}

// Good: Remember image request
@Composable
fun ProfileImage(profile: Profile) {
    val request = remember(profile.avatarId) {
        ImageRequest.Builder(LocalContext.current)
            .data(profile.avatarId)
            .build()
    }

    AsyncImage(model = request, ...)
}

// Better: Use SpiritImage which handles this
@Composable
fun ProfileImage(profile: Profile) {
    SpiritImage(
        resourceId = profile.avatarId,
        contentDescription = "Avatar"
    )
}
```

### 5. List Performance

```kotlin
// Use LazyColumn/LazyGrid, not Column/Row with many images
LazyVerticalGrid(
    columns = GridCells.Fixed(3)
) {
    items(zodiacSigns) { sign ->
        SpiritualSymbol(
            resourceId = sign.iconResourceId,
            contentDescription = sign.name,
            modifier = Modifier.size(72.dp)
        )
    }
}
```

### 6. Monitor Memory Usage

```kotlin
// In debug builds, track image memory
if (BuildConfig.DEBUG) {
    imageLoader.memoryCache?.size  // Current size
    imageLoader.memoryCache?.maxSize  // Max size
}
```

Use Android Profiler to identify:
- Memory leaks from retained bitmaps
- Excessive allocations during scrolling
- Peak memory usage

---

## Troubleshooting

### Issue: Images Look Blurry

**Causes**:
1. Wrong density folder (Android scaling up low-res image)
2. ContentScale.Crop cutting important details
3. Over-compression (WebP quality too low)

**Solutions**:
```kotlin
// 1. Verify density is being selected
// Check Logcat: "Loading drawable-xxhdpi/chakra_heart.webp"

// 2. Use appropriate ContentScale
SpiritImage(
    contentScale = ContentScale.Fit,  // vs Crop
    filterQuality = FilterQuality.High  // vs Medium/Low
)

// 3. Increase WebP quality in optimizer
config = OptimizationConfig(
    webp_quality = 95  # Was 90
)
```

### Issue: High Memory Usage

**Causes**:
1. Loading full-resolution images unnecessarily
2. Memory cache too large
3. Not clearing old images from cache

**Solutions**:
```kotlin
// 1. Reduce memory cache
MemoryCache.Builder(context)
    .maxSizePercent(0.15)  // Reduce from 0.25

// 2. Manually clear cache when navigating away
viewModelScope.launch {
    imageLoader.memoryCache?.clear()
}

// 3. Use smaller image variants
// Instead of loading 1920×1920 background for 360×640dp screen,
// use xxhdpi variant at 1080×1920
```

### Issue: Slow Image Loading

**Causes**:
1. Disk I/O blocking main thread
2. Too many concurrent loads
3. Re-decoding same images

**Solutions**:
```kotlin
// 1. Increase disk cache
DiskCache.Builder()
    .maxSizeBytes(100 * 1024 * 1024)  // 100 MB

// 2. Preload critical images
imagePreloader.preloadCriticalAssets()

// 3. Use crossfade to hide loading delay
SpiritImage(
    crossfade = true  // 300ms fade-in
)
```

### Issue: APK Size Too Large

**Solutions**:
1. **Skip mdpi**: Modern devices are hdpi+ (saves 20%)
2. **Skip xxxhdpi for non-icons**: Only xxhdpi needed (saves 25%)
3. **Use AAB**: Deliver only required density (saves 60-70%)
4. **Aggressive WebP compression**: quality=85 vs 90 (saves 15-20%)
5. **Consider vector alternatives**: For simple icons, use XML vectors

```bash
# Analyze APK size
./gradlew :app:assembleRelease
cd app/build/outputs/apk/release

# Check size breakdown
unzip -l app-release.apk | grep -E "res/drawable|webp"

# Use bundletool to estimate AAB savings
bundletool build-apks \
  --bundle=app-release.aab \
  --output=app.apks \
  --mode=universal
```

### Issue: Build Time Too Long

**Causes**:
- Gradle processing 500+ images on every build

**Solutions**:
```gradle
// app/build.gradle.kts
android {
    // Disable PNG crunching (not needed for WebP)
    aaptOptions {
        cruncherEnabled = false
    }

    // Only include required densities in debug builds
    buildTypes {
        debug {
            resConfigs("en", "xxhdpi")  // English + xxhdpi only
        }
    }
}
```

### Issue: Wrong Image Displayed

**Causes**:
- Resource naming conflicts (Android picks alphabetically)
- Missing density fallback

**Solutions**:
```bash
# Check for duplicate names
cd app/src/main/res
find . -name "*.webp" | sort | uniq -d

# Verify density folders have consistent names
ls -R drawable-*/
```

---

## Best Practices Summary

### Do's ✓

1. ✓ Use WebP for all raster images
2. ✓ Generate hdpi, xhdpi, xxhdpi at minimum
3. ✓ Configure Coil with memory and disk caching
4. ✓ Use `SpiritImage` composable for consistent loading
5. ✓ Preload critical assets during splash screen
6. ✓ Use Android App Bundle (AAB) for distribution
7. ✓ Monitor memory usage with Android Profiler
8. ✓ Test on multiple device densities
9. ✓ Use lazy loading for lists (LazyColumn)
10. ✓ Remember image requests in Composables

### Don'ts ✗

1. ✗ Don't use PNG when WebP is supported (min SDK 26)
2. ✗ Don't generate mdpi or xxxhdpi for all assets
3. ✗ Don't load full-res images and scale them down
4. ✗ Don't recreate ImageRequest on every recomposition
5. ✗ Don't ignore memory cache configuration
6. ✗ Don't use GlobalScope for image loading
7. ✗ Don't forget to test on low-end devices
8. ✗ Don't skip density testing in emulator
9. ✗ Don't bundle all densities in APK (use AAB)
10. ✗ Don't over-compress spiritual symbols (keep quality high)

---

## Testing Checklist

Before deploying optimized images:

- [ ] Run optimization script successfully
- [ ] Verify generated WebP files in drawable-* folders
- [ ] Check file sizes are reduced vs PNG
- [ ] Build APK successfully: `./gradlew :app:assembleDebug`
- [ ] Test on xxhdpi device/emulator (most common)
- [ ] Test on xhdpi device/emulator
- [ ] Test on hdpi device/emulator (low-end)
- [ ] Verify images look crisp (not blurry)
- [ ] Check memory usage in Android Profiler
- [ ] Measure cold start time with preloading
- [ ] Verify smooth scrolling in profile list
- [ ] Test dark mode (if applicable)
- [ ] Generate release build: `./gradlew :app:bundleRelease`
- [ ] Analyze APK size: `bundletool dump sizes`
- [ ] Test on physical device (recommended)

---

## Additional Resources

### Android Documentation
- [Support Different Pixel Densities](https://developer.android.com/training/multiscreen/screendensities)
- [Reduce Image Download Sizes](https://developer.android.com/topic/performance/network-xfer)
- [Reduce APK Size](https://developer.android.com/topic/performance/reduce-apk-size)

### Coil Documentation
- [Coil Image Loading Library](https://coil-kt.github.io/coil/)
- [Coil Compose Integration](https://coil-kt.github.io/coil/compose/)
- [Coil Caching](https://coil-kt.github.io/coil/getting_started/#caching)

### Tools
- [Android Studio APK Analyzer](https://developer.android.com/studio/build/apk-analyzer)
- [bundletool](https://developer.android.com/studio/command-line/bundletool)
- [WebP Converter (cwebp)](https://developers.google.com/speed/webp/docs/cwebp)

---

## Next Steps

1. **Run optimization**: `python optimize_for_android.py`
2. **Create ImageLoader module**: Copy code from Loading Strategies section
3. **Update Compose code**: Use `SpiritImage` instead of raw `Image`
4. **Test thoroughly**: Use testing checklist above
5. **Measure impact**: Compare APK sizes before/after
6. **Fine-tune**: Adjust quality/densities based on results

For questions or issues, refer to this guide or check the project documentation.

---

*Last Updated: 2025-12-09*
*For SpiritAtlas v1.0 - Android Image Optimization*
