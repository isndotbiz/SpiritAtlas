# SpiritAtlas App Icon - Quick Start Guide

This guide walks you through generating and implementing the new SpiritAtlas app icon in 15 minutes.

## Prerequisites

```bash
# Install required Python packages
pip install fal-client requests Pillow

# Ensure you have FAL_KEY environment variable set
export FAL_KEY="your-fal-api-key-here"
```

## Step 1: Generate Icon Concepts (5 min)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation

# Generate all 7 icon concepts using FLUX.1 [dev]
python3 generate_app_icons.py

# This will create:
# - generated_icons/lotus_master_1024.png (RECOMMENDED)
# - generated_icons/lotus_foreground_1024.png
# - generated_icons/lotus_background_1024.png
# - generated_icons/sacred_geometry_master_1024.png
# - generated_icons/cosmic_eye_master_1024.png
# - generated_icons/zodiac_constellation_master_1024.png
# - generated_icons/minimalist_om_master_1024.png
#
# Cost: ~$0.50 from your $4.20 fal.ai budget
```

## Step 2: Review Generated Icons

```bash
# Open generated icons directory
open generated_icons/

# Review all concepts and choose your favorite
# RECOMMENDED: lotus_master_1024.png (Gradient Lotus concept)
```

## Step 3: Optimize Icon for Android (3 min)

```bash
# Process your chosen icon into all required Android sizes
python3 optimize_app_icons.py generated_icons/lotus_master_1024.png

# This creates:
# - All mipmap densities (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
# - Round variants
# - Play Store asset (512x512)
# - Preview composites
#
# Output: generated_icons/lotus_master_1024_optimized/
```

## Step 4: Copy Assets to App (2 min)

```bash
# Copy raster assets to app resources
cd generated_icons/lotus_master_1024_optimized/mipmap

# Copy all density folders
cp -r mipmap-mdpi /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/
cp -r mipmap-hdpi /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/
cp -r mipmap-xhdpi /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/
cp -r mipmap-xxhdpi /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/
cp -r mipmap-xxxhdpi /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/

# OR use this one-liner:
for density in mdpi hdpi xhdpi xxhdpi xxxhdpi; do
  cp mipmap-$density/ic_launcher*.png /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/mipmap-$density/
done
```

## Step 5: Update Vector XML Layers (3 min)

```bash
# Copy vector XML templates to app
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/icon_xml_templates

# Copy background, foreground, and monochrome layers
cp ic_launcher_background_lotus.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/ic_launcher_background.xml
cp ic_launcher_foreground_lotus.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/ic_launcher_foreground.xml
cp ic_launcher_monochrome_lotus.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/ic_launcher_monochrome.xml

# Copy adaptive icon configurations
cp ic_launcher.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml
cp ic_launcher_round.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml
```

## Step 6: Build and Test (2 min)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas

# Clean build with new icon
./gradlew clean assembleDebug

# Install on device/emulator
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.spiritatlas.app/.MainActivity
```

## Step 7: Verify Icon Appearance

### Test Different Launcher Shapes

1. Go to Settings > Display > Icon Shape (on supported devices)
2. Try different shapes:
   - Circle
   - Squircle
   - Rounded Square
   - Teardrop

3. Verify icon looks good in all shapes

### Test Themed Icons (Android 13+)

1. Go to Settings > Wallpaper & Style
2. Enable "Themed icons"
3. Change wallpaper to different colors
4. Verify monochrome icon adapts to theme colors

### Test on Different Wallpapers

1. Set a light wallpaper - verify icon stands out
2. Set a dark wallpaper - verify icon stands out
3. Set a colorful wallpaper - verify icon doesn't clash

## Quick Commands Cheatsheet

```bash
# Generate icons
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
python3 generate_app_icons.py

# Optimize chosen icon
python3 optimize_app_icons.py generated_icons/lotus_master_1024.png

# Copy to app (run from optimized output directory)
cd generated_icons/lotus_master_1024_optimized/mipmap
for density in mdpi hdpi xhdpi xxhdpi xxxhdpi; do
  cp mipmap-$density/ic_launcher*.png /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/mipmap-$density/
done

# Copy XML templates
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/icon_xml_templates
cp ic_launcher_background_lotus.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/ic_launcher_background.xml
cp ic_launcher_foreground_lotus.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/ic_launcher_foreground.xml
cp ic_launcher_monochrome_lotus.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/ic_launcher_monochrome.xml
cp ic_launcher.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml
cp ic_launcher_round.xml /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml

# Build and install
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
./gradlew clean assembleDebug && adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch
adb shell am start -n com.spiritatlas.app/.MainActivity

# View logs
adb logcat | grep -E "SpiritAtlas|AndroidRuntime"
```

## Troubleshooting

### Icon not updating after install

```bash
# Clear launcher cache
adb shell pm clear com.android.launcher3

# Or reboot device
adb reboot

# Then reinstall
./gradlew assembleDebug && adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Icon looks pixelated

- Make sure you copied ALL density folders (mdpi through xxxhdpi)
- Check that XML vector files are in drawable/ directory
- Verify adaptive-icon configs are in mipmap-anydpi-v26/

### Monochrome/themed icon not working

- Only works on Android 13+ (API 33+)
- Check that ic_launcher_monochrome.xml exists in drawable/
- Verify it's referenced in mipmap-anydpi-v26/ic_launcher.xml
- Enable themed icons in Settings > Wallpaper & Style

### Build errors

```bash
# Clean and rebuild
./gradlew clean
./gradlew assembleDebug

# Check for XML syntax errors
./gradlew lintDebug

# Verify all resource files exist
ls -la app/src/main/res/drawable/ic_launcher*
ls -la app/src/main/res/mipmap-*/ic_launcher*
ls -la app/src/main/res/mipmap-anydpi-v26/ic_launcher*
```

## Next Steps

### For A/B Testing

See full documentation: `/SPIRIT_ATLAS_ICON_CONCEPTS.md`

1. Set up Firebase Remote Config
2. Generate multiple icon variants
3. Implement icon switching logic
4. Track metrics (install rate, engagement, user preference)

### For Play Store

```bash
# Copy 512x512 Play Store asset
cp generated_icons/lotus_master_1024_optimized/playstore/ic_launcher_playstore.png ~/Desktop/

# Upload to Google Play Console:
# Store presence > Main store listing > App icon
```

### For Seasonal Variants

Generate special event icons:
- Winter Solstice (blue-white lotus)
- Spring Equinox (green-pink lotus)
- Summer Solstice (gold-orange lotus)
- Autumn Equinox (red-brown lotus)

Implement using Firebase Remote Config to switch icons based on date.

## File Structure Reference

```
app/src/main/res/
├── drawable/
│   ├── ic_launcher_background.xml          ← Background layer (vector)
│   ├── ic_launcher_foreground.xml          ← Foreground layer (vector)
│   └── ic_launcher_monochrome.xml          ← Monochrome layer (vector, Android 13+)
├── mipmap-anydpi-v26/
│   ├── ic_launcher.xml                     ← Adaptive icon config
│   └── ic_launcher_round.xml               ← Adaptive icon config (round)
├── mipmap-mdpi/
│   ├── ic_launcher.png                     ← 48x48px
│   └── ic_launcher_round.png               ← 48x48px (circular mask)
├── mipmap-hdpi/
│   ├── ic_launcher.png                     ← 72x72px
│   └── ic_launcher_round.png               ← 72x72px
├── mipmap-xhdpi/
│   ├── ic_launcher.png                     ← 96x96px
│   └── ic_launcher_round.png               ← 96x96px
├── mipmap-xxhdpi/
│   ├── ic_launcher.png                     ← 144x144px
│   └── ic_launcher_round.png               ← 144x144px
└── mipmap-xxxhdpi/
    ├── ic_launcher.png                     ← 192x192px
    └── ic_launcher_round.png               ← 192x192px
```

## Resources

- Full documentation: `/SPIRIT_ATLAS_ICON_CONCEPTS.md`
- Material Design guidelines: https://developer.android.com/develop/ui/views/launch/icon_design_adaptive
- FLUX prompt templates: In main documentation
- Budget tracking: $0.50 used of $4.20 fal.ai credits

---

**Time to Complete:** 15 minutes
**Cost:** ~$0.50
**Result:** Professional, stunning app icon that stands out on any home screen
