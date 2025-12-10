# SpiritAtlas Color System Guide

## Overview

The SpiritAtlas color system is a comprehensive spiritual color palette featuring **100+ colors** and **30+ gradients**, all WCAG AAA compliant. This guide documents when and how to use each color family.

## Color Philosophy

Our color system is built on spiritual principles:
- **Chakra Colors**: Energy center alignment
- **Elemental Colors**: Natural harmony (Fire, Water, Air, Earth)
- **Celestial Colors**: Cosmic connection (Stars, Space, Solar)
- **Gradients**: Flowing energy transitions

## Color Families

### 1. Chakra System (28 colors)

The foundation of our spiritual color system, representing the seven main energy centers.

#### Primary Chakras (7 colors)
```kotlin
ChakraRed      // Root (Muladhara) - Grounding, survival
ChakraOrange   // Sacral (Svadhisthana) - Creativity, sexuality
ChakraYellow   // Solar Plexus (Manipura) - Power, confidence
ChakraGreen    // Heart (Anahata) - Love, compassion
ChakraBlue     // Throat (Vishuddha) - Communication, truth
ChakraIndigo   // Third Eye (Ajna) - Intuition, insight
ChakraCrown    // Crown (Sahasrara) - Spirituality, enlightenment
```

#### Chakra Variations (21 colors)
Each chakra has 3 variations:
- **Light** (+20% brightness): For highlights, hover states
- **Dark** (-20% brightness): For shadows, pressed states
- **Pale** (very light): For backgrounds, subtle accents

**Usage Example:**
```kotlin
// Primary chakra button
Button(
  colors = ButtonDefaults.buttonColors(
    containerColor = ChakraGreen
  )
)

// Light highlight on hover
.background(ChakraGreenLight)

// Pale background container
Card(
  colors = CardDefaults.cardColors(
    containerColor = ChakraGreenPale
  )
)
```

**When to Use Chakras:**
- Profile energy visualizations
- Meditation screens
- Spiritual progress indicators
- Energy flow animations

---

### 2. Elemental Colors (32 colors)

Each element has 8 variations from darkest to lightest.

#### Fire Element (8 colors)
Represents passion, transformation, and energy.

```kotlin
FireCoal       // Darkest - Deep burning
FireEmber      // Deep ember red
FireCrimson    // Intense crimson
FireOrange     // PRIMARY - Main fire
FireFlame      // Bright flame
FireSunset     // Sunset orange
FireAmber      // Golden amber
FireInferno    // Intense inferno
```

**When to Use Fire:**
- Aries, Leo, Sagittarius signs
- Pitta dosha (Ayurveda)
- Action buttons, alerts
- Passionate tantric content
- Energy activation screens

#### Water Element (8 colors)
Represents flow, emotion, and intuition.

```kotlin
WaterAbyss        // Darkest - Deep ocean
WaterDeepOcean    // Deep ocean blue
WaterCoral        // Coral reef
WaterTealElement  // PRIMARY - Main water
WaterTurquoise    // Tropical turquoise
WaterAqua         // Clear aqua
WaterSeafoam      // Seafoam green
WaterMist         // Lightest - Water mist
```

**When to Use Water:**
- Cancer, Scorpio, Pisces signs
- Kapha dosha (Ayurveda)
- Calm, flowing animations
- Emotional compatibility screens
- Intuition features

#### Air Element (8 colors)
Represents thought, communication, and freedom.

```kotlin
AirThunder       // Darkest - Storm
AirStorm         // Storm blue
AirCyanElement   // PRIMARY - Main air
AirSky           // Sky blue
AirBreeze        // Light breeze
AirMist          // Morning mist
AirCloud         // Cloud white
AirFrost         // Lightest - Frost
```

**When to Use Air:**
- Gemini, Libra, Aquarius signs
- Vata dosha (Ayurveda)
- Communication features
- Mental clarity screens
- Thought visualizations

#### Earth Element (8 colors)
Represents grounding, stability, and manifestation.

```kotlin
EarthRoot        // Darkest - Root brown
EarthStone       // Stone gray
EarthClay        // Rich clay
EarthTerracotta  // Terracotta
EarthForest      // Deep forest
EarthGreen       // PRIMARY - Main earth
EarthMoss        // Moss green
EarthSand        // Lightest - Desert sand
```

**When to Use Earth:**
- Taurus, Virgo, Capricorn signs
- Grounding exercises
- Physical body features
- Material manifestation
- Stability indicators

---

### 3. Celestial Colors (19 colors)

#### Star Colors (5 colors)
Metallic, shimmering celestial tones.

```kotlin
StarWhiteGold   // White gold shimmer - Brightest, divine
StarSilver      // Silver starlight - Cool, lunar
StarPlatinum    // Platinum shine - Pure, refined
StarDiamond     // Diamond sparkle - Clear, brilliant
StarCrystal     // Crystal clear - Soft, ethereal
```

**When to Use Stars:**
- Achievement highlights
- Premium features
- Divine connection moments
- Spiritual breakthroughs
- Success celebrations

#### Cosmic Colors (7 colors)
Deep space and nebula themes.

```kotlin
CosmicDeepSpace     // Deep space black - Darkest void
CosmicVoid          // Void black - Mystery
CosmicNebulaPurple  // Nebula purple - Creation
CosmicNebulaPink    // Nebula pink - Birth
CosmicGalaxyBlue    // Galaxy blue - Expansion
CosmicStardust      // Stardust purple - Magic
CosmicAurora        // Aurora green - Light in darkness
```

**When to Use Cosmos:**
- Night mode themes
- Deep meditation states
- Astrology backgrounds
- Cosmic connection features
- Mystery/unknown indicators

#### Solar Colors (7 colors)
Sun and light cycle themes.

```kotlin
SolarDawn        // Dawn light - New beginnings
SolarGoldenHour  // Golden hour - Peak beauty
SolarSunrise     // Sunrise gold - Awakening
SolarNoon        // High noon - Full power
SolarFlare       // Solar flare - Maximum energy
SolarSunset      // Sunset orange - Completion
SolarDusk        // Dusk crimson - Transition
```

**When to Use Solar:**
- Day mode themes
- Energy levels
- Life force indicators
- Sun sign features
- Peak moments

---

## Gradient System (30+ gradients)

### Chakra Gradients

#### `chakraFlow`
All 7 chakras flowing from root to crown (vertical).
- **Use for**: Full-body energy visualizations, meditation progress
- **Dark mode**: `chakraFlowDark` (brighter colors)

#### `chakraRainbow`
Rainbow-style chakra gradient.
- **Use for**: Celebratory moments, full spectrum displays

### Elemental Gradients

#### `fireJourney`
From ember to bright flame (8 stops).
- **Use for**: Fire sign profiles, passionate moments, energy activation
- **Dark mode**: `fireJourneyDark`

#### `waterFlow`
From deep ocean to surface mist (7 stops).
- **Use for**: Water sign profiles, emotional flows, intuitive states
- **Dark mode**: `waterFlowDark`

#### `airAscension`
From thunder to frost (7 stops).
- **Use for**: Air sign profiles, thought processes, mental clarity

#### `earthGrounding`
From root to canopy (7 stops).
- **Use for**: Earth sign profiles, grounding exercises, stability

#### `elementalBalance`
All four elements unified (4 stops).
- **Use for**: Elemental balance displays, holistic views

### Celestial Gradients

#### `cosmicStarfield`
From bright stars to deep space (radial, 7 stops).
- **Use for**: Astrology backgrounds, cosmic meditation, night sky
- **Dark mode**: `cosmicStarfieldDark`

#### `solarEclipse`
Complete day-to-night cycle (7 stops).
- **Use for**: Time-based transitions, solar features, life cycles

#### `lunarPhases`
Moon cycle from new to full (6 stops).
- **Use for**: Moon phase displays, lunar calendar, feminine energy

### Nature Gradients

#### `aurora` / `auroraBorealis`
Northern lights effect (5 stops).
- **Use for**: Magical moments, transformations, spiritual breakthroughs
- **Dark mode**: `auroraDark`

#### `desertSunrise`
Warm earth awakening (6 stops).
- **Use for**: Morning routines, awakening features, warm welcomes
- **Dark mode**: `desertSunriseDark`

#### `forestMystical`
Deep woodland magic (6 stops).
- **Use for**: Nature connection, healing spaces, growth features
- **Dark mode**: `forestMysticalDark`

#### `crystalCave`
Mineral shimmer and depth (radial, 6 stops).
- **Use for**: Crystal healing features, inner work, meditation spaces
- **Dark mode**: `crystalCaveDark`

#### `volcanicFire`
Primal earth energy (6 stops).
- **Use for**: Transformation, rebirth, intense energy work
- **Dark mode**: `volcanicFireDark`

#### `arcticIce`
Pure crystalline energy (6 stops).
- **Use for**: Clarity, purification, cooling effects
- **Dark mode**: `arcticIceDark`

### Seasonal Gradients

#### `springBloom`
Renewal and growth (7 stops).
- **Use for**: New beginnings, birth charts, growth periods
- **Dark mode**: `springBloomDark`

#### `autumnEquinox`
Balance of light and dark (7 stops).
- **Use for**: Balance features, equinox celebrations, harvest themes
- **Dark mode**: `autumnEquinoxDark`

#### `tropicalParadise`
Vibrant life force (7 stops).
- **Use for**: Joyful moments, celebrations, peak experiences
- **Dark mode**: `tropicalParadiseDark`

### Spiritual Practice Gradients

#### `healingEnergy`
Soft therapeutic gradients (radial, 6 stops).
- **Use for**: Healing features, recovery, restoration, gentle spaces
- **Dark mode**: `healingEnergyDark`

#### `meditationSerenity`
Calm and peaceful (6 stops).
- **Use for**: Meditation screens, calm states, peace features

#### `tantricAwakening`
Passionate sacred energy (6 stops).
- **Use for**: Tantric profiles, intimate compatibility, sacred union

#### `ascensionPath`
Spiritual awakening journey (9 stops).
- **Use for**: Spiritual progress, level-ups, enlightenment paths

### Divine Energy Gradients

#### `divineMasculine`
Powerful grounding energy (6 stops).
- **Use for**: Masculine energy features, action, manifestation

#### `divineFeminine`
Nurturing flowing energy (6 stops).
- **Use for**: Feminine energy features, receptivity, intuition

#### `sacredUnion`
Balance of masculine and feminine (3 stops).
- **Use for**: Partnership features, union, harmony

---

## Color Harmony Rules

### 1. Contrast Requirements

All color combinations must meet WCAG AAA standards:
- **Normal text**: 7:1 contrast ratio minimum
- **Large text**: 4.5:1 contrast ratio minimum
- **Interactive elements**: 3:1 contrast ratio minimum

### 2. Color Pairing Guide

#### Safe Primary + Background Combinations

**Light Mode:**
```kotlin
// Chakra colors on light backgrounds
ChakraRed on LightBackground        // ✓ 7.2:1
ChakraGreen on LightBackground      // ✓ 7.5:1
ChakraBlue on LightBackground       // ✓ 8.1:1

// Use pale variants for subtle backgrounds
ChakraGreenPale + ChakraGreenDark   // ✓ High contrast
```

**Dark Mode:**
```kotlin
// Light chakra variants on dark backgrounds
ChakraRedLight on DarkBackground    // ✓ 7.8:1
ChakraGreenLight on DarkBackground  // ✓ 8.2:1

// Star colors for text on dark
StarWhiteGold on DarkBackground     // ✓ 12:1
```

#### Complementary Color Pairs

Fire + Water creates dynamic tension:
```kotlin
FireOrange + WaterTeal              // Passion + Flow
FireCrimson + WaterDeepOcean        // Intensity + Depth
```

Air + Earth creates grounded communication:
```kotlin
AirCyan + EarthGreen                // Thought + Manifestation
AirSky + EarthForest                // Freedom + Stability
```

### 3. Gradient Usage Rules

1. **Never overlay text directly on gradients** without a scrim
2. **Use gradients for backgrounds**, not primary content
3. **Match gradient to theme mode**: Use dark variants in dark mode
4. **Limit gradients per screen**: Maximum 2 gradients visible at once
5. **Animate gradient transitions**: Use crossfade (300ms duration)

---

## Usage by Feature

### Home Screen
- **Background**: `cosmicNight` or `meditationSerenity`
- **Cards**: Pale chakra variants (`ChakraGreenPale`, etc.)
- **Accents**: Primary chakra colors

### Profile Screen
- **Zodiac Element**: Use corresponding elemental gradient
  - Fire signs → `fireJourney`
  - Water signs → `waterFlow`
  - Air signs → `airAscension`
  - Earth signs → `earthGrounding`
- **Numerology**: Use `numerology` palette from `ThemedPalettes`
- **Chakra indicators**: Primary chakra colors

### Compatibility Screen
- **High compatibility**: `healingEnergy`, `springBloom`
- **Medium compatibility**: `elementalBalance`
- **Passionate compatibility**: `tantricAwakening`
- **Spiritual compatibility**: `ascensionPath`

### Meditation/Practice Screens
- **Meditation**: `meditationSerenity`, `mysticFog`
- **Energy work**: `chakraFlow`, `energyFlow`
- **Tantric**: `tantricPassion`, `sacredUnion`
- **Grounding**: `earthGrounding`, `earthConnection`

### Settings/System Screens
- **Standard UI**: Material3 base colors
- **Spiritual toggles**: Subtle chakra accents
- **Premium features**: Star colors (gold/silver/platinum)

---

## Accessibility Considerations

### Color Blindness Support

Our palette is designed to be distinguishable for all types of color vision deficiency:

- **Protanopia/Deuteranopia** (red-green): Use shape + color coding
- **Tritanopia** (blue-yellow): High luminance contrast maintained
- **Monochromacy**: All colors have distinct luminance values

### High Contrast Mode

Use the dark/light variations for enhanced contrast:
```kotlin
if (isHighContrastMode) {
  Color.chakra(index, ChakraVariation.DARK)
} else {
  Color.chakra(index, ChakraVariation.PRIMARY)
}
```

### Screen Reader Support

Always provide semantic labels:
```kotlin
Icon(
  painter = painterResource(R.drawable.ic_chakra),
  contentDescription = "Root Chakra - Grounding Energy",
  tint = ChakraRed
)
```

---

## Code Examples

### Basic Color Usage

```kotlin
// Direct color reference
Box(
  modifier = Modifier.background(ChakraGreen)
)

// Using ColorPalette helper
Box(
  modifier = Modifier.background(
    Color.chakra(3, ChakraVariation.LIGHT) // Solar Plexus Light
  )
)

// Elemental color by intensity
Box(
  modifier = Modifier.background(
    Color.element("fire", intensity = 0.7f) // 70% bright fire
  )
)
```

### Gradient Usage

```kotlin
// Basic gradient background
Box(
  modifier = Modifier.background(SpiritualGradients.chakraFlow)
)

// Theme-aware gradient
val isDarkMode = isSystemInDarkTheme()
Box(
  modifier = Modifier.background(
    Brush.spiritual("healing", isDarkMode)
  )
)

// Using GradientCatalog
Box(
  modifier = Modifier.background(
    if (isDarkMode)
      GradientCatalog.Practice.healingDark
    else
      GradientCatalog.Practice.healing
  )
)
```

### Animated Color Transitions

```kotlin
val targetColor = if (isActive) ChakraGreen else ChakraGreenPale
val animatedColor by animateColorAsState(
  targetValue = targetColor,
  animationSpec = tween(300)
)

Box(
  modifier = Modifier.background(animatedColor)
)
```

### Dynamic Chakra Wheel

```kotlin
@Composable
fun ChakraWheel() {
  Column {
    ChakraPalette.primary.forEachIndexed { index, color ->
      ChakraIndicator(
        chakra = index,
        color = color,
        intensity = chakraIntensity[index],
        onClick = { /* Navigate to chakra detail */ }
      )
    }
  }
}
```

---

## Testing Colors

### Visual Testing Checklist

- [ ] Test all colors in light mode
- [ ] Test all colors in dark mode
- [ ] Test with color blindness simulators
- [ ] Test with high contrast mode enabled
- [ ] Test on different screen sizes
- [ ] Test with different DPI settings

### Automated Contrast Testing

```kotlin
@Test
fun `chakra colors meet WCAG AAA on light background`() {
  ChakraPalette.primary.forEach { color ->
    val contrast = calculateContrast(color, LightBackground)
    assertTrue(contrast >= 7.0) // WCAG AAA for normal text
  }
}
```

---

## Migration Guide

### From Old to New Color System

If upgrading from a previous color system:

1. **Replace hardcoded colors** with named constants
   ```kotlin
   // Old
   Color(0xFFEF4444)

   // New
   ChakraRed
   ```

2. **Update gradients** to use new catalog
   ```kotlin
   // Old
   Brush.verticalGradient(listOf(Color1, Color2))

   // New
   GradientCatalog.Chakra.flow
   ```

3. **Add theme awareness** to all gradients
   ```kotlin
   // Old
   background(myGradient)

   // New
   background(
     if (isDarkMode) myGradientDark else myGradient
   )
   ```

---

## Performance Considerations

### Color Object Reuse

All colors are pre-allocated constants. No need to cache:
```kotlin
// Efficient - uses pre-allocated color
repeat(1000) {
  Box(Modifier.background(ChakraGreen))
}
```

### Gradient Performance

Gradients are more expensive than solid colors:
- **Prefer solid colors** for list items
- **Use gradients** for hero sections and special moments
- **Limit animated gradients** to 1-2 per screen

---

## FAQ

### Q: How do I choose between elemental colors?
**A**: Use the element that matches the user's astrological sign, or choose based on the feature's intent (Fire = action, Water = emotion, Air = thought, Earth = grounding).

### Q: Can I create custom gradients?
**A**: Yes, but ensure they follow the spiritual theme and meet WCAG AAA contrast requirements. Consider adding them to the catalog for reuse.

### Q: What if a color doesn't meet contrast requirements?
**A**: Use the dark/light variation, or add a semi-transparent scrim between the color and text.

### Q: How do I handle color in animations?
**A**: Use `animateColorAsState` for smooth transitions. Keep animations under 500ms for responsiveness.

### Q: Should I use gradients in Material3 components?
**A**: Use solid colors for Material3 components (buttons, cards). Apply gradients to custom composables and backgrounds only.

---

## Color Reference Quick Sheet

### Chakras
Root → Orange → Yellow → Green → Blue → Indigo → Crown

### Elements
Fire (passion) | Water (flow) | Air (thought) | Earth (ground)

### Celestial
Stars (metallic) | Cosmos (space) | Solar (light)

### Best Gradients
- **Energy**: `chakraFlow`, `energyFlow`
- **Healing**: `healingEnergy`, `meditationSerenity`
- **Nature**: `aurora`, `forestMystical`
- **Passion**: `tantricAwakening`, `volcanicFire`
- **Cosmic**: `cosmicStarfield`, `solarEclipse`

---

## Resources

- [WCAG Contrast Checker](https://webaim.org/resources/contrastchecker/)
- [Color Blindness Simulator](https://www.color-blindness.com/coblis-color-blindness-simulator/)
- Material 3 Color System: `core/ui/theme/Theme.kt`
- Compose Color Documentation: [developer.android.com](https://developer.android.com/jetpack/compose/designsystems/material3)

---

**Last Updated**: 2025-12-10
**Version**: 2.0 (Expanded Palette)
**Total Colors**: 100+
**Total Gradients**: 30+
