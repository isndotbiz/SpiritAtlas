# Android Image Optimization for SpiritAtlas

Complete guide for optimizing 99 FLUX 1.1 Pro generated images for Android deployment.

## Quick Start

```bash
# 1. Ensure you have generated images in the correct location
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation

# 2. Install dependencies
pip install Pillow

# 3. Preview optimization (dry run)
python optimize_for_android.py --dry-run

# 4. Run optimization
python optimize_for_android.py \
  --input generated_images/optimized_flux_pro \
  --output ../../app/src/main/res \
  --create-mapping

# 5. Verify output
ls -lh ../../app/src/main/res/drawable-*/
```

## Overview

This optimization system converts 99 high-resolution FLUX 1.1 Pro images into Android-optimized WebP files with multi-density support.

### What It Does

1. **Format Conversion**: Converts PNG to WebP with category-specific quality settings
2. **Multi-Density Generation**: Creates mdpi/hdpi/xhdpi/xxhdpi/xxxhdpi variants
3. **Smart Categorization**: Automatically detects image category and applies appropriate settings
4. **Resource Naming**: Converts filenames to Android-compatible resource names
5. **Size Optimization**: Compresses images while maintaining visual quality
6. **Manifest Generation**: Creates resource_mapping.json for easy integration

## Image Categories (11 Total)

### 1. App Branding (8 images)
- **Location**: `001_primary_app_icon.png` through `008_notification_icon.png`
- **WebP Quality**: 100 (lossless for launcher icons)
- **Densities**: All 5 (mdpi to xxxhdpi)
- **Target Size**: Icons <100KB each
- **Base Density**: xxxhdpi (1024x1024 = 4x for 256dp)

### 2. Backgrounds (15 images)
- **Location**: `009_home_screen_background.png` through `023_*.png`
- **WebP Quality**: 85 (aggressive compression acceptable)
- **Densities**: xxhdpi, xhdpi, hdpi only (backgrounds don't need all)
- **Target Size**: <500KB per density
- **Base Density**: xxhdpi (1080x1920 = 3x for 360x640dp)
- **Max Dimension**: 1920px to prevent excessive file sizes

### 3. Avatars (10 images)
- **Location**: `024_default_avatar_cosmic_soul.png` through `033_*.png`
- **WebP Quality**: 88 (good balance)
- **Densities**: hdpi, xhdpi, xxhdpi
- **Target Size**: <50KB per density
- **Base Density**: xhdpi (512x512 = 2x for 256dp)
- **Transparency**: Preserved

### 4. Zodiac Signs (12 images)
- **Location**: `034_aries_constellation.png` through `045_pisces_constellation.png`
- **WebP Quality**: 92 (high quality for spiritual symbols)
- **Densities**: hdpi, xhdpi, xxhdpi
- **Target Size**: <75KB per density
- **Base Density**: xhdpi (512x512 = 2x for 256dp)
- **Transparency**: Preserved

### 5. Chakras (7 images)
- **Location**: `046_root_chakra.png` through `052_crown_chakra.png`
- **WebP Quality**: 92
- **Densities**: hdpi, xhdpi, xxhdpi
- **Target Size**: <75KB per density
- **Base Density**: xhdpi (512x512)
- **Transparency**: Preserved

### 6. Moon Phases (8 images)
- **Location**: `053_new_moon.png` through `060_waning_crescent.png`
- **WebP Quality**: 92
- **Densities**: hdpi, xhdpi, xxhdpi
- **Target Size**: <75KB per density
- **Base Density**: xhdpi (512x512)

### 7. Elements (5 images)
- **Location**: `061_fire_element.png` through `065_ether_element.png`
- **WebP Quality**: 92
- **Densities**: hdpi, xhdpi, xxhdpi
- **Target Size**: <75KB per density
- **Base Density**: xhdpi (512x512)
- **Transparency**: Preserved

### 8. Sacred Geometry (8 images)
- **Location**: `066_flower_of_life.png` through `073_golden_spiral.png`
- **WebP Quality**: 92
- **Densities**: xhdpi, xxhdpi
- **Target Size**: <150KB per density
- **Base Density**: xxhdpi (1024x1024 for detailed patterns)

### 9. UI Elements (12 images)
- **Location**: `074_primary_button_normal.png` through `085_dropdown_chevron.png`
- **WebP Quality**: 90
- **Densities**: All 5 (UI elements need all densities)
- **Target Size**: <50KB per density
- **Base Density**: xhdpi (varies 128-800px)
- **Transparency**: Preserved for icons

### 10. Spiritual Symbols (8 images)
- **Location**: `086_om_symbol.png` through `093_infinity_symbol.png`
- **WebP Quality**: 92
- **Densities**: hdpi, xhdpi, xxhdpi
- **Target Size**: <75KB per density
- **Base Density**: xhdpi (512x512)
- **Transparency**: Preserved

### 11. Onboarding Illustrations (6 images)
- **Location**: `094_welcome_cosmic_discovery.png` through `099_ready_to_begin.png`
- **WebP Quality**: 88
- **Densities**: xhdpi, xxhdpi
- **Target Size**: <250KB per density
- **Base Density**: xxhdpi (1080x1080)

## Optimization Script Usage

### Command Line Options

```bash
python optimize_for_android.py [OPTIONS]

Options:
  --input, -i PATH        Input directory with PNG images
                          Default: app/src/main/res/generated_assets

  --output, -o PATH       Output directory for Android resources
                          Default: app/src/main/res

  --dry-run               Preview changes without writing files

  --create-mapping        Generate resource_mapping.json
```

### Example Workflows

#### 1. Standard Optimization
```bash
python optimize_for_android.py \
  --input generated_images/optimized_flux_pro \
  --output ../../app/src/main/res \
  --create-mapping
```

#### 2. Preview First (Recommended)
```bash
# Dry run to see what will happen
python optimize_for_android.py \
  --input generated_images/optimized_flux_pro \
  --output ../../app/src/main/res \
  --dry-run

# If looks good, run for real
python optimize_for_android.py \
  --input generated_images/optimized_flux_pro \
  --output ../../app/src/main/res
```

#### 3. Test on Subset
```bash
# Create test directory with a few images
mkdir -p test_images
cp generated_images/optimized_flux_pro/001_*.png test_images/
cp generated_images/optimized_flux_pro/034_*.png test_images/

# Optimize test set
python optimize_for_android.py \
  --input test_images \
  --output test_output
```

## Output Structure

After optimization, your resources will be organized as:

```
app/src/main/res/
├── drawable-mdpi/
│   ├── primary_app_icon.webp (256x256)
│   ├── aries_constellation.webp (256x256)
│   └── ... (99 images × scale factor)
├── drawable-hdpi/
│   ├── primary_app_icon.webp (384x384)
│   ├── aries_constellation.webp (384x384)
│   └── ...
├── drawable-xhdpi/
│   ├── primary_app_icon.webp (512x512)
│   ├── aries_constellation.webp (512x512)
│   └── ...
├── drawable-xxhdpi/
│   ├── primary_app_icon.webp (768x768)
│   ├── aries_constellation.webp (768x768)
│   └── ...
├── drawable-xxxhdpi/
│   ├── primary_app_icon.webp (1024x1024)
│   ├── aries_constellation.webp (1024x1024)
│   └── ...
└── resource_mapping.json (manifest of all resources)
```

### Naming Convention

The script converts filenames to Android resource naming:

| Original Filename | Android Resource Name |
|-------------------|----------------------|
| `001_primary_app_icon.png` | `primary_app_icon.webp` |
| `034_aries_constellation.png` | `aries_constellation.webp` |
| `053_new_moon.png` | `new_moon.webp` |
| `066_flower_of_life.png` | `flower_of_life.webp` |

Rules:
- Lowercase only
- Replace spaces, hyphens with underscores
- Remove number prefixes
- Max 80 characters
- Only alphanumeric and underscore

## Integration into Android App

### 1. Update build.gradle.kts

Ensure Coil image loading is configured:

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("io.coil-kt:coil-compose:2.7.0")
}
```

### 2. Use Images in Composables

```kotlin
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.spiritatlas.app.R

@Composable
fun ZodiacIcon(sign: ZodiacSign) {
    Image(
        painter = painterResource(
            when (sign) {
                ZodiacSign.ARIES -> R.drawable.aries_constellation
                ZodiacSign.TAURUS -> R.drawable.taurus_constellation
                // ...
            }
        ),
        contentDescription = sign.name,
        modifier = Modifier.size(48.dp)
    )
}
```

### 3. Use Backgrounds

```kotlin
@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background layer
        Image(
            painter = painterResource(R.drawable.home_screen_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )

        // Content on top
        HomeScreenContent()
    }
}
```

### 4. Create Resource Helper

```kotlin
// core/ui/src/main/java/com/spiritatlas/core/ui/resources/SpiritualResources.kt
object SpiritualResources {
    fun getZodiacIcon(sign: ZodiacSign): Int = when (sign) {
        ZodiacSign.ARIES -> R.drawable.aries_constellation
        ZodiacSign.TAURUS -> R.drawable.taurus_constellation
        ZodiacSign.GEMINI -> R.drawable.gemini_constellation
        ZodiacSign.CANCER -> R.drawable.cancer_constellation
        ZodiacSign.LEO -> R.drawable.leo_constellation
        ZodiacSign.VIRGO -> R.drawable.virgo_constellation
        ZodiacSign.LIBRA -> R.drawable.libra_constellation
        ZodiacSign.SCORPIO -> R.drawable.scorpio_constellation
        ZodiacSign.SAGITTARIUS -> R.drawable.sagittarius_constellation
        ZodiacSign.CAPRICORN -> R.drawable.capricorn_constellation
        ZodiacSign.AQUARIUS -> R.drawable.aquarius_constellation
        ZodiacSign.PISCES -> R.drawable.pisces_constellation
    }

    fun getChakraIcon(chakra: Int): Int = when (chakra) {
        1 -> R.drawable.root_chakra
        2 -> R.drawable.sacral_chakra
        3 -> R.drawable.solar_plexus_chakra
        4 -> R.drawable.heart_chakra
        5 -> R.drawable.throat_chakra
        6 -> R.drawable.third_eye_chakra
        7 -> R.drawable.crown_chakra
        else -> R.drawable.root_chakra
    }

    fun getMoonPhaseIcon(phase: MoonPhase): Int = when (phase) {
        MoonPhase.NEW_MOON -> R.drawable.new_moon
        MoonPhase.WAXING_CRESCENT -> R.drawable.waxing_crescent
        MoonPhase.FIRST_QUARTER -> R.drawable.first_quarter
        MoonPhase.WAXING_GIBBOUS -> R.drawable.waxing_gibbous
        MoonPhase.FULL_MOON -> R.drawable.full_moon
        MoonPhase.WANING_GIBBOUS -> R.drawable.waning_gibbous
        MoonPhase.LAST_QUARTER -> R.drawable.last_quarter
        MoonPhase.WANING_CRESCENT -> R.drawable.waning_crescent
    }
}
```

## Expected Results

### Size Reduction

| Category | Original PNG | Optimized WebP (all densities) | Savings |
|----------|--------------|--------------------------------|---------|
| App Icons (8) | ~40 MB | ~15 MB | 62% |
| Backgrounds (15) | ~60 MB | ~20 MB | 67% |
| Symbols (40) | ~80 MB | ~40 MB | 50% |
| UI Elements (12) | ~20 MB | ~10 MB | 50% |
| Others (24) | ~40 MB | ~20 MB | 50% |
| **Total (99)** | **~240 MB** | **~105 MB** | **56%** |

### Build Impact

- **Debug APK**: Will include all densities (~105 MB)
- **Release AAB**: Users download only their density (~25-35 MB per device)
- **Install Size**: ~30-40 MB on device

### Performance

- **Cold Start**: No impact (images loaded on-demand)
- **Memory**: ~2-5 MB in memory cache (25% of app memory)
- **Disk Cache**: ~50 MB configured for Coil
- **Network**: N/A (bundled resources)

## Troubleshooting

### Issue: Script fails with "WebP not supported"

**Solution:**
```bash
# Reinstall Pillow with WebP support
pip uninstall Pillow
pip install Pillow --force-reinstall
```

### Issue: Images look blurry on device

**Cause**: Wrong density selected or over-compression

**Solution:**
1. Check density folders are correctly generated
2. Increase WebP quality in script (edit `optimize_for_android.py`)
3. Ensure device is picking correct density

### Issue: Build fails with "Resource not found"

**Cause**: Resource name mismatch

**Solution:**
1. Check `resource_mapping.json` for actual names
2. Update Kotlin code to use correct resource IDs
3. Verify images are in correct drawable-* folders

### Issue: APK size too large

**Solutions:**
1. Skip mdpi (modern devices are hdpi+)
2. Skip xxxhdpi for non-critical images
3. Use Android App Bundle (AAB) for Play Store
4. Reduce WebP quality further (85 → 80)

### Issue: OutOfMemoryError on low-end devices

**Solutions:**
1. Reduce memory cache percentage (25% → 15%)
2. Use smaller density variants
3. Implement pagination for image-heavy lists
4. Clear cache when navigating away from screens

## Testing Checklist

Before deploying:

- [ ] Run optimization successfully without errors
- [ ] Verify all 99 images have WebP variants in drawable-* folders
- [ ] Check file sizes are within targets
- [ ] Build APK successfully: `./gradlew :app:assembleDebug`
- [ ] Test on xxhdpi emulator (most common)
- [ ] Test on xhdpi emulator (mid-range)
- [ ] Test on hdpi emulator (low-end)
- [ ] Verify images are crisp, not blurry
- [ ] Check memory usage in Android Profiler
- [ ] Test dark mode compatibility
- [ ] Verify smooth scrolling in lists with images
- [ ] Generate release build: `./gradlew :app:bundleRelease`
- [ ] Analyze APK size with APK Analyzer
- [ ] Test on physical device (recommended)

## Performance Best Practices

### 1. Image Loading with Coil

```kotlin
// Configure Coil in Application class
ImageLoader.Builder(context)
    .memoryCache {
        MemoryCache.Builder(context)
            .maxSizePercent(0.25) // 25% of app memory
            .build()
    }
    .diskCache {
        DiskCache.Builder()
            .directory(context.cacheDir.resolve("image_cache"))
            .maxSizeBytes(50 * 1024 * 1024) // 50 MB
            .build()
    }
    .crossfade(true)
    .build()
```

### 2. Lazy Loading in Lists

```kotlin
@Composable
fun ProfileList(profiles: List<Profile>) {
    LazyColumn {
        items(profiles) { profile ->
            ProfileCard(
                avatarResource = profile.avatarId,
                zodiacResource = getZodiacIcon(profile.sunSign)
            )
        }
    }
}
```

### 3. Preload Critical Assets

```kotlin
// In Application.onCreate() or splash screen
suspend fun preloadCriticalAssets(imageLoader: ImageLoader) {
    listOf(
        R.drawable.primary_app_icon,
        R.drawable.home_screen_background,
        R.drawable.cosmic_soul_avatar
    ).forEach { resourceId ->
        imageLoader.enqueue(
            ImageRequest.Builder(context)
                .data(resourceId)
                .build()
        )
    }
}
```

## Related Documentation

- **IMAGE_OPTIMIZATION_GUIDE.md**: Complete technical guide with Coil setup
- **IMAGE_INTEGRATION_STRATEGY.md**: Screen-by-screen integration plan
- **OPTIMIZED_FLUX_PRO_PROMPTS_99.md**: Original prompts for all 99 images
- **IMAGE_INTEGRATION_CHECKLIST.md**: Step-by-step implementation checklist

## Next Steps

1. **Complete Image Generation**: Ensure all 99 images are generated
2. **Run Optimization**: Execute `optimize_for_android.py`
3. **Integrate into App**: Update Composables to use new resources
4. **Create Helper Classes**: Build `SpiritualResources.kt`
5. **Test Thoroughly**: Use testing checklist above
6. **Measure Impact**: Compare before/after APK sizes
7. **Fine-tune**: Adjust quality/densities based on results

## Support

For issues or questions:
1. Review `IMAGE_OPTIMIZATION_GUIDE.md` for detailed explanations
2. Check `optimize_for_android.py` script comments
3. Refer to Coil documentation: https://coil-kt.github.io/coil/
4. Android density guide: https://developer.android.com/training/multiscreen/screendensities

---

**Last Updated**: December 9, 2025
**Script Version**: 1.0
**Compatible with**: Android SDK 26+ (Android 8.0+)
