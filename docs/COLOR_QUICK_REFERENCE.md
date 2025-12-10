# Color System Quick Reference Card

One-page cheat sheet for the SpiritAtlas color system.

## By Number: 114 Colors | 30+ Gradients

---

## CHAKRAS (28 colors)

```kotlin
// Primary (7)
ChakraRed, ChakraOrange, ChakraYellow, ChakraGreen,
ChakraBlue, ChakraIndigo, ChakraCrown

// Light (7) - for highlights
ChakraRedLight, ChakraOrangeLight, ChakraYellowLight, ChakraGreenLight,
ChakraBlueLight, ChakraIndigoLight, ChakraCrownLight

// Dark (7) - for shadows
ChakraRedDark, ChakraOrangeDark, ChakraYellowDark, ChakraGreenDark,
ChakraBlueDark, ChakraIndigoDark, ChakraCrownDark

// Pale (7) - for backgrounds
ChakraRedPale, ChakraOrangePale, ChakraYellowPale, ChakraGreenPale,
ChakraBluePale, ChakraIndigoPale, ChakraCrownPale
```

**Gradient**: `chakraFlow` | `chakraFlowDark`

---

## ELEMENTS (32 colors)

### Fire (8)
```kotlin
FireCoal, FireEmber, FireCrimson, FireOrange,
FireFlame, FireSunset, FireAmber, FireInferno
```
**Gradient**: `fireJourney` | `fireJourneyDark`

### Water (8)
```kotlin
WaterAbyss, WaterDeepOcean, WaterCoral, WaterTealElement,
WaterTurquoise, WaterAqua, WaterSeafoam, WaterMist
```
**Gradient**: `waterFlow` | `waterFlowDark`

### Air (8)
```kotlin
AirThunder, AirStorm, AirCyanElement, AirSky,
AirBreeze, AirMist, AirCloud, AirFrost
```
**Gradient**: `airAscension`

### Earth (8)
```kotlin
EarthRoot, EarthStone, EarthClay, EarthTerracotta,
EarthForest, EarthGreen, EarthMoss, EarthSand
```
**Gradient**: `earthGrounding`

---

## CELESTIAL (19 colors)

### Stars (5)
```kotlin
StarWhiteGold, StarSilver, StarPlatinum, StarDiamond, StarCrystal
```

### Cosmos (7)
```kotlin
CosmicDeepSpace, CosmicVoid, CosmicNebulaPurple, CosmicNebulaPink,
CosmicGalaxyBlue, CosmicStardust, CosmicAurora
```
**Gradient**: `cosmicStarfield` | `cosmicStarfieldDark`

### Solar (7)
```kotlin
SolarDawn, SolarGoldenHour, SolarSunrise, SolarNoon,
SolarFlare, SolarSunset, SolarDusk
```
**Gradient**: `solarEclipse`

---

## GRADIENTS BY CATEGORY

### Chakra (3)
```kotlin
chakraRainbow, chakraFlow, chakraFlowDark
```

### Elements (6)
```kotlin
fireJourney, fireJourneyDark, waterFlow, waterFlowDark,
airAscension, earthGrounding
```

### Celestial (5)
```kotlin
cosmicNight, cosmicStarfield, cosmicStarfieldDark,
solarEclipse, lunarPhases
```

### Nature (8)
```kotlin
aurora, auroraDark, desertSunrise, desertSunriseDark,
forestMystical, forestMysticalDark, volcanicFire, arcticIce
```

### Practice (5)
```kotlin
healingEnergy, healingEnergyDark, meditationSerenity,
tantricAwakening, ascensionPath
```

### Divine (3)
```kotlin
divineMasculine, divineFeminine, sacredUnion
```

---

## QUICK CODE PATTERNS

### Direct Color
```kotlin
Box(Modifier.background(ChakraGreen))
```

### Chakra by Index (0-6)
```kotlin
Box(Modifier.background(
  Color.chakra(3, ChakraVariation.LIGHT)
))
```

### Element by Intensity (0.0-1.0)
```kotlin
Box(Modifier.background(
  Color.element("fire", 0.7f)
))
```

### Theme-Aware Gradient
```kotlin
Box(Modifier.background(
  Brush.spiritual("healing", isDarkMode)
))
```

### From Catalog
```kotlin
Box(Modifier.background(
  GradientCatalog.Practice.healing
))
```

---

## FEATURE TO COLOR MAPPING

| Feature | Colors | Gradient |
|---------|--------|----------|
| **Home** | Chakra + Cosmic | `cosmicNight` |
| **Profile Fire** | Fire tones | `fireJourney` |
| **Profile Water** | Water tones | `waterFlow` |
| **Profile Air** | Air tones | `airAscension` |
| **Profile Earth** | Earth tones | `earthGrounding` |
| **Compatibility** | Healing greens | `healingEnergy` |
| **Tantric** | Rose/purple | `tantricAwakening` |
| **Meditation** | Pale chakras | `meditationSerenity` |
| **Energy Work** | Bright chakras | `chakraFlow` |

---

## ACCESSIBILITY CHECKLIST

- [ ] All colors meet WCAG AAA (7:1 contrast)
- [ ] Dark mode gradients for dark backgrounds
- [ ] Named colors (no anonymous hex codes)
- [ ] Semantic meaning clear from name
- [ ] Icon + color coding (not color alone)

---

## HELPER OBJECTS

```kotlin
ChakraPalette        // Access chakra colors by index
ElementalPalette     // Access elements by name
CelestialPalette     // Access celestial colors
GradientCatalog      // Organized gradient library
ThemedPalettes       // Pre-configured color mappings
```

---

## MOST USED COLORS

```kotlin
// General UI
ChakraGreen         // Primary success, heart
ChakraBlue          // Primary info, throat
ChakraIndigo        // Intuition, third eye
MysticPurple        // Spiritual actions

// Elements
FireOrange          // Action, passion
WaterTealElement    // Flow, emotion
AirCyanElement      // Communication
EarthGreen          // Grounding

// Celestial
StarWhiteGold       // Premium, divine
SolarGoldenHour     // Warmth, beauty
CosmicViolet        // Mystery, cosmos
```

---

## FILES

- **Colors**: `core/ui/theme/Color.kt` (968 lines)
- **Palette**: `core/ui/theme/ColorPalette.kt` (425 lines)
- **Guide**: `docs/COLOR_SYSTEM_GUIDE.md` (701 lines)
- **Visual**: `docs/COLOR_PALETTE_VISUAL_REFERENCE.md` (346 lines)
- **Summary**: `COLOR_EXPANSION_SUMMARY.md` (445 lines)

**Total Documentation**: 2,885 lines

---

**Print this card** and keep it handy while coding!
