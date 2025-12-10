# Gradient System - Quick Reference

**Fast lookup for developers**

---

## Import Statement

```kotlin
import com.spiritatlas.core.ui.theme.GradientLibrary
import com.spiritatlas.core.ui.theme.DynamicGradients
import com.spiritatlas.core.ui.theme.AnimatedGradients
```

---

## Static Gradients (52)

### Chakras (7)
```kotlin
GradientLibrary.rootChakra          // Red-Brown
GradientLibrary.sacralChakra        // Orange
GradientLibrary.solarPlexusChakra   // Yellow-Gold
GradientLibrary.heartChakra         // Green-Pink
GradientLibrary.throatChakra        // Blue-Cyan
GradientLibrary.thirdEyeChakra      // Indigo-Purple
GradientLibrary.crownChakra         // White-Violet
```

### Elements (8)
```kotlin
GradientLibrary.fireElement         // Red-Orange-Yellow
GradientLibrary.waterElement        // Blue-Teal
GradientLibrary.earthElement        // Brown-Green
GradientLibrary.airElement          // Light Blue-White
GradientLibrary.etherElement        // Purple Cosmic
GradientLibrary.fireAirElement      // Orange-Blue (Lightning)
GradientLibrary.waterEarthElement   // Teal-Green (Growth)
GradientLibrary.airWaterElement     // Blue-Teal (Mist)
```

### Zodiac (12)
```kotlin
GradientLibrary.aries               // Red-Orange
GradientLibrary.taurus              // Green-Brown
GradientLibrary.gemini              // Yellow-Cyan
GradientLibrary.cancer              // Silver-Teal
GradientLibrary.leo                 // Golden Sun
GradientLibrary.virgo               // Green-White
GradientLibrary.libra               // Pink-Blue
GradientLibrary.scorpio             // Red-Purple
GradientLibrary.sagittarius         // Purple-Orange
GradientLibrary.capricorn           // Brown-Gray
GradientLibrary.aquarius            // Blue-Purple
GradientLibrary.pisces              // Purple-Teal
```

### Time of Day (6)
```kotlin
GradientLibrary.dawn                // Deep Blue-Pink
GradientLibrary.morning             // Orange-Yellow
GradientLibrary.midday              // Bright Gold
GradientLibrary.afternoon           // Golden Amber
GradientLibrary.dusk                // Purple-Pink
GradientLibrary.night               // Indigo-Black
```

### Seasons (4)
```kotlin
GradientLibrary.spring              // Pink-Green
GradientLibrary.summer              // Yellow-Green
GradientLibrary.autumn              // Red-Orange-Brown
GradientLibrary.winter              // Ice Blue-Silver
```

### Cosmic (10)
```kotlin
GradientLibrary.galaxy              // Purple-Black Spiral
GradientLibrary.nebula              // Pink-Blue Cloud
GradientLibrary.supernova           // White-Red Explosion
GradientLibrary.blackHole           // Black Center-Purple
GradientLibrary.starfield           // Deep Space Stars
GradientLibrary.comet               // White-Transparent Tail
GradientLibrary.auroraCosmic        // Bright Aurora
GradientLibrary.void                // Dark Emptiness
GradientLibrary.cosmicOcean         // Night Sky-Teal
GradientLibrary.celestialHarmony    // Gold-Silver-Purple
```

### Healing (5)
```kotlin
GradientLibrary.reikiHealing        // White-Lavender
GradientLibrary.heartHealing        // Pink-Green
GradientLibrary.crystalHealing      // Clear-Purple
GradientLibrary.soundHealing        // Blue-Teal Waves
GradientLibrary.divineLightHealing  // Golden-White
```

---

## Dynamic Gradients

### From User Data
```kotlin
// Personalized from profile
DynamicGradients.fromProfile(userProfile)

// Specific chakra with intensity
DynamicGradients.fromChakra(chakraIndex = 3, intensity = 0.8f)

// Zodiac sign
DynamicGradients.fromZodiacSign("Pisces")

// Relationship strength (0-100)
DynamicGradients.fromCompatibilityScore(87.5)

// Couple blend
DynamicGradients.fromProfilePair(profileA, profileB)
```

### From Environment
```kotlin
// Current time (auto-selects dawn/morning/etc)
DynamicGradients.fromTimeOfDay()

// Current season (auto-selects spring/summer/etc)
DynamicGradients.fromSeason()

// Planetary transit
DynamicGradients.fromTransit("Venus", "conjunction")
```

### From State
```kotlin
// Emotional mood
DynamicGradients.fromMood("calm")
// Options: calm, energized, romantic, focused, spiritual, sad, anxious, confident

// Manifestation intention
DynamicGradients.fromIntention("abundance")
// Options: abundance, love, healing, creativity, clarity, protection, transformation, peace
```

---

## Animated Gradients

### Flowing (Scrolling)
```kotlin
AnimatedGradients.flowingHorizontal(colors, durationMillis = 3000)
AnimatedGradients.flowingVertical(colors, durationMillis = 3000)
AnimatedGradients.flowingDiagonal(colors, durationMillis = 3000)
AnimatedGradients.flowingRadial(colors, durationMillis = 3000)
```

### Breathing (Pulsing)
```kotlin
AnimatedGradients.breathing(
    colors = listOf(...),
    durationMillis = 4000,
    minAlpha = 0.6f,
    maxAlpha = 1.0f,
    direction = GradientDirection.Radial
)

AnimatedGradients.colorMorphing(colors, durationMillis = 5000)
AnimatedGradients.shimmerBreathing(baseColors, highlightColor)
```

### Rotating (Spinning)
```kotlin
AnimatedGradients.rotating(colors, durationMillis = 5000, clockwise = true)
AnimatedGradients.spiral(colors, rotationDurationMillis = 5000)
AnimatedGradients.wave(colors, durationMillis = 4000)
```

### Presets (Ready-to-Use)
```kotlin
AnimatedGradients.aurora()              // Northern lights
AnimatedGradients.chakraFlow()          // Energy up spine
AnimatedGradients.cosmicPulse()         // Breathing universe
AnimatedGradients.sacredGeometry()      // Rotating mandala
AnimatedGradients.tantricEnergy()       // Passionate flow
AnimatedGradients.elementalCycle()      // Earth→Water→Air→Fire
AnimatedGradients.moonlightShimmer()    // Lunar glow
AnimatedGradients.goldenHour()          // Sunrise/sunset
AnimatedGradients.thirdEyeActivation()  // Pulsing intuition
AnimatedGradients.heartOpening()        // Expanding love
```

---

## Usage Patterns

### Basic Background
```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(GradientLibrary.heartChakra)
)
```

### Dynamic Profile Header
```kotlin
@Composable
fun ProfileHeader(profile: UserProfile) {
    val gradient = remember(profile.id) {
        DynamicGradients.fromProfile(profile)
    }
    Box(modifier = Modifier.background(gradient))
}
```

### Animated Screen
```kotlin
@Composable
fun MeditationScreen() {
    val gradient = AnimatedGradients.cosmicPulse()
    Box(modifier = Modifier.background(gradient))
}
```

### Compatibility Visualization
```kotlin
val gradient = DynamicGradients.fromCompatibilityScore(score)
Card(modifier = Modifier.background(gradient))
```

---

## Color Utilities

```kotlin
// Blend two colors
val blended = color1.blend(color2, ratio = 0.5f)

// Lighten/darken
val lighter = color.lighten(0.3f)
val darker = color.darken(0.3f)

// Saturate
val vibrant = color.saturate(1.5f)

// Complement
val opposite = color.complement()
```

---

## Common Use Cases

| Screen | Recommended Gradient |
|--------|---------------------|
| **Login** | `GradientLibrary.crownChakra` |
| **Profile** | `DynamicGradients.fromProfile(profile)` |
| **Compatibility** | `DynamicGradients.fromCompatibilityScore(score)` |
| **Meditation** | `AnimatedGradients.cosmicPulse()` |
| **Home** | `DynamicGradients.fromTimeOfDay()` |
| **Settings** | `GradientLibrary.throatChakra` |
| **Journal** | `DynamicGradients.fromMood(mood)` |
| **Manifesting** | `DynamicGradients.fromIntention(intention)` |
| **Splash** | `DynamicGradients.fromSeason()` |
| **Chakra View** | `GradientLibrary.[chakraName]` |

---

## Performance Tips

```kotlin
// ✅ Cache dynamic gradients
val gradient = remember(profile.id) {
    DynamicGradients.fromProfile(profile)
}

// ✅ Limit animated gradients per screen
// Use 2-3 max

// ✅ Use static when possible
GradientLibrary.heartChakra // Faster than dynamic

// ❌ Don't regenerate every recomposition
// This will be slow:
val gradient = DynamicGradients.fromProfile(profile) // No remember!
```

---

## Complete File List

1. **GradientLibrary.kt** - 52 static gradients (670 lines)
2. **DynamicGradients.kt** - Dynamic generation (654 lines)
3. **AnimatedGradients.kt** - Animation system (617 lines)
4. **GradientExamples.kt** - Usage examples (496 lines)
5. **GRADIENT_SYSTEM_GUIDE.md** - Full documentation (20 KB)

**Total Code**: 2,437 lines
**Total Gradients**: 72+ static, infinite dynamic

---

## Quick Examples

### 1. Zodiac Profile Card
```kotlin
val gradient = DynamicGradients.fromZodiacSign(profile.sunSign)
Card(modifier = Modifier.background(gradient))
```

### 2. Compatibility Score Bar
```kotlin
val gradient = DynamicGradients.fromCompatibilityScore(score)
LinearProgressIndicator(
    progress = score / 100f,
    modifier = Modifier.background(gradient)
)
```

### 3. Time-Aware Splash
```kotlin
val gradient = DynamicGradients.fromTimeOfDay()
Box(modifier = Modifier.fillMaxSize().background(gradient))
```

### 4. Chakra Meditation
```kotlin
val gradient = AnimatedGradients.chakraFlow()
Box(modifier = Modifier.fillMaxSize().background(gradient))
```

### 5. Seasonal Home Screen
```kotlin
val gradient = DynamicGradients.fromSeason()
Scaffold(modifier = Modifier.background(gradient))
```

---

**For complete documentation, see `/docs/GRADIENT_SYSTEM_GUIDE.md`**
