# Resource Restoration Guide

This guide explains how to restore archived resources if needed.

## Quick Restore Commands

### Restore Everything
```bash
# From project root
cp -r archived_resources/drawables/* app/src/main/res/
cp archived_resources/values/colors.xml.backup app/src/main/res/values/colors.xml
```

### Restore Specific Categories

#### Restore All Zodiac Constellations (12 images)
```bash
for i in {34..45}; do
  for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
    img=$(printf "img_%03d_*" $i)
    cp archived_resources/drawables/$density/$img \
       app/src/main/res/drawable-$density/ 2>/dev/null || true
  done
done
```

#### Restore All Chakra Images (7 images)
```bash
for i in {46..52}; do
  for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
    img=$(printf "img_%03d_*" $i)
    cp archived_resources/drawables/$density/$img \
       app/src/main/res/drawable-$density/ 2>/dev/null || true
  done
done
```

#### Restore All Moon Phases (8 images)
```bash
for i in {53..60}; do
  for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
    img=$(printf "img_%03d_*" $i)
    cp archived_resources/drawables/$density/$img \
       app/src/main/res/drawable-$density/ 2>/dev/null || true
  done
done
```

#### Restore All Sacred Geometry (8 images)
```bash
for i in {66..73}; do
  for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
    img=$(printf "img_%03d_*" $i)
    cp archived_resources/drawables/$density/$img \
       app/src/main/res/drawable-$density/ 2>/dev/null || true
  done
done
```

#### Restore All Screen Backgrounds (15 images)
```bash
for i in {9..23}; do
  for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
    img=$(printf "img_%03d_*" $i)
    cp archived_resources/drawables/$density/$img \
       app/src/main/res/drawable-$density/ 2>/dev/null || true
  done
done
```

### Restore a Single Image

```bash
# Example: Restore img_009_home_screen_background
IMAGE_NAME="img_009_home_screen_background"

for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
  cp "archived_resources/drawables/$density/${IMAGE_NAME}.webp" \
     "app/src/main/res/drawable-$density/"
done

echo "Restored $IMAGE_NAME to all density folders"
```

### Restore by Pattern

```bash
# Restore all avatar images (img_024 through img_033)
for i in {24..33}; do
  for density in hdpi mdpi xhdpi xxhdpi xxxhdpi; do
    img=$(printf "img_%03d_*" $i)
    cp archived_resources/drawables/$density/$img \
       app/src/main/res/drawable-$density/ 2>/dev/null || true
  done
done
```

## Verification After Restore

After restoring resources, verify the build:

```bash
# Clean and rebuild
./gradlew clean
./gradlew :app:assembleDebug

# Check for missing resources
./gradlew :app:lintDebug | grep "missing resources"
```

## Archived Resource Categories

- **img_009 - img_023:** Screen backgrounds (15)
- **img_024 - img_033:** Avatar assets and frames (10)
- **img_034 - img_045:** Zodiac constellations (12)
- **img_046 - img_052:** Chakra system (7)
- **img_053 - img_060:** Moon phases (8)
- **img_061 - img_065:** Element symbols (5)
- **img_066 - img_073:** Sacred geometry (8)
- **img_086 - img_093:** Spiritual symbols (8)
- **img_094 - img_099:** Onboarding screens (6)
- **img_100 - img_112:** Tantric & energy visualizations (13)
- **img_113 - img_119:** Spiritual practice assets (7)

## Important Notes

1. Always test the app after restoring resources
2. Check that all density variants are restored (hdpi, mdpi, xhdpi, xxhdpi, xxxhdpi)
3. If a resource is restored, update any code that references it
4. Run lint checks to ensure no broken references
5. Commit restored resources to version control

## Rollback Script

If you need to undo the entire cleanup operation:

```bash
#!/bin/bash
# rollback_cleanup.sh

echo "Rolling back resource cleanup..."

# Restore all drawables
cp -r archived_resources/drawables/* app/src/main/res/

# Restore colors
cp archived_resources/values/colors.xml.backup app/src/main/res/values/colors.xml

# Clean build
./gradlew clean

echo "Rollback complete. Run './gradlew assembleDebug' to rebuild."
```

Save as `rollback_cleanup.sh`, make executable with `chmod +x`, then run `./rollback_cleanup.sh`.

---

**Archive Date:** 2025-12-10
**Total Archived:** 495 drawable files, 7 color definitions
**Archive Size:** 43 MB
