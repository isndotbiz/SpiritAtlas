# SpiritAtlas Image Integration - Quick Reference Checklist

**Quick access companion to IMAGE_INTEGRATION_STRATEGY.md**

---

## Image Inventory (99 Total)

### By Category
- [x] App Icons: 10
- [x] Buttons: 12
- [x] Backgrounds: 12
- [x] Avatars: 10
- [x] Chakras: 7
- [x] Sacred Geometry: 8
- [x] Zodiac: 12
- [x] Elements: 5
- [x] Moon Phases: 8
- [x] Spiritual Symbols: 6
- [x] UI States: 9

**Total: 99 images** ✓

---

## Critical Path Implementation

### Week 1: First Impressions
```
□ Launcher Icon (App Icon #1)
  └─ Generate all densities (mdpi → xxxhdpi)

□ Splash Screen (Background #1 + App Icon #2)
  └─ File: app/src/main/java/com/spiritatlas/app/SplashScreen.kt

□ Primary Button (Button #1)
  └─ Create SpiritualImageButton component

□ Home Background (Background #2)
  └─ File: feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt
```

### Week 2: Core Features
```
□ Avatar System (Avatars #1-10)
  └─ New file: core/ui/components/AvatarComponents.kt

□ Zodiac Icons (All 12)
  └─ Update: core/ui/components/SpiritualIcons.kt

□ Chakras (All 7)
  └─ New file: core/ui/components/ChakraComponents.kt

□ Elements (All 5)
  └─ New file: core/ui/components/ElementComponents.kt
```

### Week 3: Polish
```
□ Sacred Geometry (All 8)
  └─ Update: core/ui/components/CosmicBackgrounds.kt

□ All Backgrounds (#1-12)

□ All Buttons (#1-12)

□ Spiritual Symbols (#1-6)
```

### Week 4: UX
```
□ Loading States (#1-3)

□ Success States (#4-5)

□ Error States (#6-7)

□ Empty States (#8-9)

□ Optimization & Testing
```

---

## File Creation Checklist

### New Files to Create
```
□ core/ui/components/AvatarComponents.kt
□ core/ui/components/ChakraComponents.kt
□ core/ui/components/ElementComponents.kt
□ core/ui/resources/SpiritualResources.kt
```

### Files to Update
```
□ app/src/main/java/com/spiritatlas/app/SplashScreen.kt
□ feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt
□ feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt
□ feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileLibraryScreen.kt
□ feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt
□ core/ui/components/SpiritualIcons.kt
□ core/ui/components/CosmicBackgrounds.kt
□ core/ui/components/ErrorComponents.kt
□ core/ui/components/ModernComponents.kt
□ app/src/main/AndroidManifest.xml (launcher icon)
```

---

## Screen Priority Order

### Priority 1: User's First Experience
1. ✓ Splash Screen
2. ✓ Home Screen
3. ✓ Onboarding Screen

### Priority 2: Core Workflows
4. ✓ Profile Library
5. ✓ Profile Creation
6. ✓ Profile Detail
7. ✓ Compatibility Screen
8. ✓ Compatibility Detail

### Priority 3: Secondary Features
9. ✓ Settings
10. ✓ Consent
11. ✓ Enrichment Results
12. ✓ Profile Comparison

---

## Resource Folder Structure

```
app/src/main/res/
├── drawable/
│   ├── Buttons (12)
│   │   ├── button_primary.webp
│   │   ├── button_secondary.webp
│   │   ├── button_add.webp
│   │   ├── button_compare.webp
│   │   ├── button_analyze.webp
│   │   ├── button_share.webp
│   │   ├── button_save.webp
│   │   ├── button_edit.webp
│   │   ├── button_delete.webp
│   │   ├── button_enrich.webp
│   │   ├── button_settings.webp
│   │   └── button_info.webp
│   │
│   ├── Backgrounds (12)
│   │   ├── background_splash.webp
│   │   ├── background_home.webp
│   │   ├── background_profile_library.webp
│   │   ├── background_profile_creation.webp
│   │   ├── background_profile_detail.webp
│   │   ├── background_compatibility.webp
│   │   ├── background_compatibility_detail.webp
│   │   ├── background_onboarding_1.webp
│   │   ├── background_onboarding_2.webp
│   │   ├── background_onboarding_3.webp
│   │   ├── background_onboarding_4.webp
│   │   └── background_settings.webp
│   │
│   ├── Zodiac (12)
│   │   ├── zodiac_aries.webp
│   │   ├── zodiac_taurus.webp
│   │   ├── zodiac_gemini.webp
│   │   ├── zodiac_cancer.webp
│   │   ├── zodiac_leo.webp
│   │   ├── zodiac_virgo.webp
│   │   ├── zodiac_libra.webp
│   │   ├── zodiac_scorpio.webp
│   │   ├── zodiac_sagittarius.webp
│   │   ├── zodiac_capricorn.webp
│   │   ├── zodiac_aquarius.webp
│   │   └── zodiac_pisces.webp
│   │
│   ├── Chakras (7)
│   │   ├── chakra_root.webp
│   │   ├── chakra_sacral.webp
│   │   ├── chakra_solar_plexus.webp
│   │   ├── chakra_heart.webp
│   │   ├── chakra_throat.webp
│   │   ├── chakra_third_eye.webp
│   │   └── chakra_crown.webp
│   │
│   ├── Elements (5)
│   │   ├── element_fire.webp
│   │   ├── element_water.webp
│   │   ├── element_earth.webp
│   │   ├── element_air.webp
│   │   └── element_ether.webp
│   │
│   ├── Moon Phases (8)
│   │   ├── moon_new.webp
│   │   ├── moon_waxing_crescent.webp
│   │   ├── moon_first_quarter.webp
│   │   ├── moon_waxing_gibbous.webp
│   │   ├── moon_full.webp
│   │   ├── moon_waning_gibbous.webp
│   │   ├── moon_last_quarter.webp
│   │   └── moon_waning_crescent.webp
│   │
│   ├── Sacred Geometry (8)
│   │   ├── geometry_flower_of_life.webp
│   │   ├── geometry_metatrons_cube.webp
│   │   ├── geometry_sri_yantra.webp
│   │   ├── geometry_seed_of_life.webp
│   │   ├── geometry_vesica_piscis.webp
│   │   ├── geometry_golden_spiral.webp
│   │   ├── geometry_platonic_solids.webp
│   │   └── geometry_torus_field.webp
│   │
│   ├── Spiritual Symbols (6)
│   │   ├── symbol_om.webp
│   │   ├── symbol_lotus.webp
│   │   ├── symbol_hamsa.webp
│   │   ├── symbol_ankh.webp
│   │   ├── symbol_tree_of_life.webp
│   │   └── symbol_mandala.webp
│   │
│   ├── Avatars (5)
│   │   ├── avatar_1.webp
│   │   ├── avatar_2.webp
│   │   ├── avatar_3.webp
│   │   ├── avatar_4.webp
│   │   └── avatar_5.webp
│   │
│   ├── Frames (5)
│   │   ├── frame_1.webp
│   │   ├── frame_2.webp
│   │   ├── frame_3.webp
│   │   ├── frame_4.webp
│   │   └── frame_5.webp
│   │
│   └── UI States (9)
│       ├── state_loading_cosmic.webp
│       ├── state_loading_analyzing.webp
│       ├── state_loading_enriching.webp
│       ├── state_success_sparkle.webp
│       ├── state_success_celebration.webp
│       ├── state_error_gentle.webp
│       ├── state_error_alert.webp
│       ├── state_empty_profiles.webp
│       └── state_empty_results.webp
│
└── mipmap-xxxhdpi/ (+ xxhdpi, xhdpi, hdpi, mdpi)
    ├── ic_launcher.webp
    └── ic_launcher_round.webp
```

---

## Image Generation Specs for FLUX 1.1 Pro

### App Icons (10)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Minimalist spiritual symbol, works at small sizes
- **Colors:** Purple/gold gradient, mystical

### Buttons (12)
- **Size:** 320x100 (or maintain aspect ratio)
- **Format:** PNG with transparency or 9-patch
- **Style:** Elegant, clickable appearance, subtle glow
- **States:** Design for normal, pressed, disabled

### Backgrounds (12)
- **Size:** 1920x1080 (landscape), optimize for portrait crop
- **Format:** WebP
- **Style:** Ethereal, cosmic, not too busy (readability)
- **Colors:** Deep purples, blacks, blues with accents
- **Opacity:** Consider 20-30% opacity overlay use

### Avatars (10)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Diverse, mystical beings/characters
- **Variation:** Different genders, energies, archetypes

### Chakras (7)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Traditional chakra symbols with modern spiritual art
- **Colors:** Match traditional chakra colors
  - Root: Red
  - Sacral: Orange
  - Solar Plexus: Yellow
  - Heart: Green
  - Throat: Blue
  - Third Eye: Indigo
  - Crown: Violet/White

### Sacred Geometry (8)
- **Size:** 1024x1024
- **Format:** PNG with transparency
- **Style:** Precise geometric patterns, glowing lines
- **Use:** Background layers, overlays

### Zodiac (12)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Elegant constellation + symbol + creature
- **Colors:** Gold/white with color accent per sign

### Elements (5)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Abstract elemental representation
- **Colors:**
  - Fire: Red/orange flames
  - Water: Blue flowing water
  - Earth: Green/brown solid
  - Air: Light blue/white swirls
  - Ether: Purple/cosmic energy

### Moon Phases (8)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Realistic moon with ethereal glow
- **Accuracy:** Astronomically correct phase shapes

### Spiritual Symbols (6)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Culturally accurate, respectful, beautiful
- **Research:** Ensure proper representation

### UI States (9)
- **Size:** 512x512
- **Format:** PNG with transparency
- **Style:** Clear, communicative, fits app aesthetic
- **Animation:** Consider if static or need frame sequence

---

## Testing Checklist

### Visual Quality
```
□ No pixelation at any screen size
□ Proper transparency rendering
□ Colors match theme (SpiritualPurple, AuraGold, etc.)
□ Works in both light and dark themes
□ Consistent style across all images
```

### Performance
```
□ All backgrounds < 500KB each
□ Icons < 100KB each
□ No lag when loading images
□ Smooth transitions
□ Memory usage acceptable on low-end devices
```

### Functionality
```
□ All images load correctly
□ Proper fallbacks if image missing
□ Content descriptions for accessibility
□ Images respond to theme changes
□ Touch targets appropriate for buttons
```

### Device Testing
```
□ Test on small screen (5.5" phone)
□ Test on large screen (6.7" phone)
□ Test on tablet (10")
□ Test on fold device
□ Test on various Android versions (API 26+)
```

---

## Quick Commands

### Generate Launcher Icon Densities
```bash
# Use Android Asset Studio or run script to generate:
# xxxhdpi: 192x192
# xxhdpi: 144x144
# xhdpi: 96x96
# hdpi: 72x72
# mdpi: 48x48
```

### Optimize Images
```bash
# WebP conversion (lossless)
cwebp input.png -lossless -o output.webp

# WebP conversion (lossy, quality 90)
cwebp -q 90 input.png -o output.webp

# Batch convert all PNGs in folder
for file in *.png; do cwebp "$file" -o "${file%.png}.webp"; done
```

### Check Image Sizes
```bash
# Check size of all drawables
du -sh app/src/main/res/drawable/*

# Find large images
find app/src/main/res -type f -size +500k
```

---

## Component Code Templates

### Using an Image Background
```kotlin
@Composable
fun MyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_my_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )
        // Your content here
    }
}
```

### Using a Zodiac Icon
```kotlin
Image(
    painter = painterResource(SpiritualResources.getZodiacResource(ZodiacSign.ARIES)),
    contentDescription = "Aries",
    modifier = Modifier.size(48.dp)
)
```

### Using a Button Image
```kotlin
SpiritualImageButton(
    imageType = ButtonImageType.PRIMARY,
    text = "Create Profile",
    onClick = { /* action */ },
    modifier = Modifier.fillMaxWidth()
)
```

### Using a Chakra Visualization
```kotlin
ChakraVisualization(
    chakras = listOf(
        ChakraState(activation = 0.8f),
        ChakraState(activation = 0.6f),
        // ... 7 total
    ),
    modifier = Modifier.fillMaxWidth()
)
```

---

## Progress Tracking

### Images Generated: ___ / 99

- [ ] App Icons: ___ / 10
- [ ] Buttons: ___ / 12
- [ ] Backgrounds: ___ / 12
- [ ] Avatars: ___ / 10
- [ ] Chakras: ___ / 7
- [ ] Sacred Geometry: ___ / 8
- [ ] Zodiac: ___ / 12
- [ ] Elements: ___ / 5
- [ ] Moon Phases: ___ / 8
- [ ] Spiritual Symbols: ___ / 6
- [ ] UI States: ___ / 9

### Screens Updated: ___ / 17

- [ ] Splash Screen
- [ ] Home Screen
- [ ] Onboarding Screen
- [ ] Profile Library
- [ ] Profile Creation
- [ ] Profile Detail
- [ ] Profile Comparison
- [ ] Compatibility Screen
- [ ] Compatibility Detail
- [ ] Enrichment Results
- [ ] Settings
- [ ] Consent
- [ ] Tantric Content
- [ ] Profile List Screen
- [ ] Comprehensive Profile Form
- [ ] Comprehensive Results
- [ ] Compatibility Analysis

### Components Created/Updated: ___ / 15

- [ ] SpiritualResources.kt (new)
- [ ] AvatarComponents.kt (new)
- [ ] ChakraComponents.kt (new)
- [ ] ElementComponents.kt (new)
- [ ] SpiritualIcons.kt (update)
- [ ] CosmicBackgrounds.kt (update)
- [ ] ModernComponents.kt (update)
- [ ] ErrorComponents.kt (update)
- [ ] InteractiveButton.kt (update)
- [ ] SplashScreen.kt (update)
- [ ] HomeScreen.kt (update)
- [ ] ProfileScreen.kt (update)
- [ ] ProfileLibraryScreen.kt (update)
- [ ] CompatibilityDetailScreen.kt (update)
- [ ] AndroidManifest.xml (update)

---

**Last Updated:** December 9, 2025
**Companion to:** IMAGE_INTEGRATION_STRATEGY.md
