# Gradient System Guide

**SpiritAtlas Advanced Gradient System - Complete Reference**

Version: 1.0.0
Last Updated: 2025-12-10

---

## Table of Contents

1. [Overview](#overview)
2. [Gradient Library](#gradient-library)
3. [Dynamic Gradients](#dynamic-gradients)
4. [Animated Gradients](#animated-gradients)
5. [Usage Examples](#usage-examples)
6. [Best Practices](#best-practices)
7. [Performance Optimization](#performance-optimization)

---

## Overview

The SpiritAtlas gradient system provides **50+ carefully designed gradients** organized by spiritual categories, plus dynamic generation and animation capabilities.

### System Architecture

```
Gradient System
├── GradientLibrary.kt      - 50+ static gradients (7 categories)
├── DynamicGradients.kt     - Profile-based generation
├── AnimatedGradients.kt    - 3 animation types
└── Color.kt                - 20 existing spiritual gradients
```

### Total Gradient Count

- **Chakra Gradients**: 7
- **Elemental Gradients**: 8
- **Zodiac Gradients**: 12
- **Time of Day Gradients**: 6
- **Seasonal Gradients**: 4
- **Cosmic Gradients**: 10
- **Healing Gradients**: 5
- **Existing Spiritual Gradients**: 20
- **Total Static Gradients**: 72
- **Dynamic Gradients**: Infinite (generated on-demand)
- **Animated Presets**: 10

---

## Gradient Library

### 1. Chakra Gradients (7)

Individual energy center gradients for spiritual visualization.

#### Root Chakra (Muladhara)
```kotlin
GradientLibrary.rootChakra
```
**Colors**: Red → Crimson → Deep Red → Brown
**Energy**: Grounding, survival, security
**Use Cases**: Root chakra cards, stability themes

#### Sacral Chakra (Svadhisthana)
```kotlin
GradientLibrary.sacralChakra
```
**Colors**: Orange → Bright Orange → Deep Orange → Soft Orange
**Energy**: Creativity, sexuality, emotions
**Use Cases**: Creative content, passion themes

#### Solar Plexus Chakra (Manipura)
```kotlin
GradientLibrary.solarPlexusChakra
```
**Colors**: Yellow → Bright Yellow → Gold → Aura Gold
**Energy**: Power, confidence, transformation
**Use Cases**: Empowerment features, confidence themes

#### Heart Chakra (Anahata)
```kotlin
GradientLibrary.heartChakra
```
**Colors**: Light Green → Green → Vibrant Green → Pink (love accent)
**Energy**: Love, compassion, healing
**Use Cases**: Love/relationship features, healing sections

#### Throat Chakra (Vishuddha)
```kotlin
GradientLibrary.throatChakra
```
**Colors**: Light Blue → Blue → Deep Blue → Cyan
**Energy**: Communication, truth, expression
**Use Cases**: Communication features, profile expression

#### Third Eye Chakra (Ajna)
```kotlin
GradientLibrary.thirdEyeChakra
```
**Colors**: Light Indigo → Indigo → Deep Indigo → Mystic Purple
**Energy**: Intuition, wisdom, psychic abilities
**Use Cases**: Insight screens, divination features

#### Crown Chakra (Sahasrara)
```kotlin
GradientLibrary.crownChakra
```
**Colors**: White → Very Light Purple → Violet → Cosmic Violet
**Energy**: Divine connection, enlightenment
**Use Cases**: Spiritual awakening themes, highest wisdom

---

### 2. Elemental Gradients (8)

Fundamental energy forces plus elemental combinations.

#### Fire Element
```kotlin
GradientLibrary.fireElement
```
**Energy**: Passion, transformation, willpower
**Perfect For**: Aries/Leo/Sagittarius profiles, action buttons

#### Water Element
```kotlin
GradientLibrary.waterElement
```
**Energy**: Emotions, intuition, flow
**Perfect For**: Cancer/Scorpio/Pisces profiles, emotional content

#### Earth Element
```kotlin
GradientLibrary.earthElement
```
**Energy**: Grounding, stability, abundance
**Perfect For**: Taurus/Virgo/Capricorn profiles, material themes

#### Air Element
```kotlin
GradientLibrary.airElement
```
**Energy**: Intellect, communication, freedom
**Perfect For**: Gemini/Libra/Aquarius profiles, mental content

#### Ether/Spirit Element
```kotlin
GradientLibrary.etherElement
```
**Energy**: Divine connection, transcendence
**Perfect For**: Spiritual awakening, meditation features

#### Fire + Air (Lightning)
```kotlin
GradientLibrary.fireAirElement
```
**Energy**: Inspiration, sudden insight
**Perfect For**: Breakthrough moments, revelations

#### Water + Earth (Growth)
```kotlin
GradientLibrary.waterEarthElement
```
**Energy**: Nourishment, healing
**Perfect For**: Healing sections, growth tracking

#### Air + Water (Mist)
```kotlin
GradientLibrary.airWaterElement
```
**Energy**: Dreams, psychic flow
**Perfect For**: Dream journals, psychic features

---

### 3. Zodiac Gradients (12)

One gradient per astrological sign.

```kotlin
// Fire Signs
GradientLibrary.aries        // Bold red-orange warrior
GradientLibrary.leo          // Golden sun radiance
GradientLibrary.sagittarius  // Purple-orange adventure

// Earth Signs
GradientLibrary.taurus       // Grounded green-brown
GradientLibrary.virgo        // Pure green-white
GradientLibrary.capricorn    // Dark brown-gray mountain

// Air Signs
GradientLibrary.gemini       // Yellow-cyan communication
GradientLibrary.libra        // Pink-blue harmony
GradientLibrary.aquarius     // Electric blue-purple vision

// Water Signs
GradientLibrary.cancer       // Silvery moonlight emotion
GradientLibrary.scorpio      // Deep red-purple transformation
GradientLibrary.pisces       // Dreamy purple-teal mystical
```

**Usage Example**:
```kotlin
// On profile screen, show user's sun sign gradient
val userGradient = when (profile.sunSign) {
    "Aries" -> GradientLibrary.aries
    "Taurus" -> GradientLibrary.taurus
    // ... etc
}
```

---

### 4. Time of Day Gradients (6)

Circadian energy cycle representation.

```kotlin
GradientLibrary.dawn      // 5-6am: Deep blue → Pink sunrise
GradientLibrary.morning   // 7-11am: Orange-yellow sun energy
GradientLibrary.midday    // 12-1pm: Bright white-gold peak
GradientLibrary.afternoon // 2-4pm: Golden amber light
GradientLibrary.dusk      // 5-7pm: Purple-orange-pink sunset
GradientLibrary.night     // 8pm-4am: Deep indigo → Starlit black
```

**Auto-Select by Time**:
```kotlin
val currentGradient = DynamicGradients.fromTimeOfDay()
// Automatically returns gradient matching current hour
```

---

### 5. Seasonal Gradients (4)

Natural cycle representation.

#### Spring
```kotlin
GradientLibrary.spring
```
**Theme**: Rebirth, growth, new life
**Colors**: Pink blossoms → Fresh green
**Best For**: March-May themes, new beginnings

#### Summer
```kotlin
GradientLibrary.summer
```
**Theme**: Abundance, vitality, joy
**Colors**: Bright yellow → Lush green
**Best For**: June-August themes, peak energy

#### Autumn
```kotlin
GradientLibrary.autumn
```
**Theme**: Harvest, gratitude, transformation
**Colors**: Red-orange-gold-brown fall
**Best For**: September-November themes, release

#### Winter
```kotlin
GradientLibrary.winter
```
**Theme**: Rest, introspection, purification
**Colors**: Icy blue → Silver white
**Best For**: December-February themes, inner work

---

### 6. Cosmic Gradients (10)

Universal energy representations.

```kotlin
GradientLibrary.galaxy          // Spiral star systems
GradientLibrary.nebula          // Star birth, cosmic creation
GradientLibrary.supernova       // Explosive transformation
GradientLibrary.blackHole       // Mystery, infinite potential
GradientLibrary.starfield       // Infinite possibilities
GradientLibrary.comet           // Swift change, destiny
GradientLibrary.auroraCosmic    // Celestial light show
GradientLibrary.void            // Emptiness, quantum field
GradientLibrary.cosmicOcean     // Space and water unity
GradientLibrary.celestialHarmony // Divine balance
```

**Perfect For**: Splash screens, cosmic themes, mystical features

---

### 7. Healing Gradients (5)

Therapeutic energy visualizations.

#### Reiki Healing
```kotlin
GradientLibrary.reikiHealing
```
**Energy**: Universal life force
**Colors**: Soft white → Gentle lavender
**Use**: Energy healing features

#### Heart Healing
```kotlin
GradientLibrary.heartHealing
```
**Energy**: Emotional restoration, self-love
**Colors**: Pink → Green (heart chakra)
**Use**: Relationship healing, self-love content

#### Crystal Healing
```kotlin
GradientLibrary.crystalHealing
```
**Energy**: Clarity, amplification
**Colors**: Clear → Purple crystal shimmer
**Use**: Crystal features, clarity themes

#### Sound Healing
```kotlin
GradientLibrary.soundHealing
```
**Energy**: Vibrational therapy
**Colors**: Blue-teal sound waves
**Use**: Sound/music therapy features

#### Divine Light Healing
```kotlin
GradientLibrary.divineLightHealing
```
**Energy**: Highest vibration, pure source
**Colors**: Golden-white divine light
**Use**: Highest healing modality, enlightenment

---

## Dynamic Gradients

Generate gradients on-the-fly based on user data.

### Profile-Based Generation

```kotlin
// Generate unique gradient from user's spiritual profile
val profileGradient = DynamicGradients.fromProfile(userProfile)

// Use on profile header
Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .background(profileGradient)
)
```

**What it considers**:
- Zodiac element colors (sun sign)
- Life path number colors (numerology)
- Sexual energy colors (Tantric polarity)
- Ayurvedic dosha (if available)

### Compatibility Score Gradients

```kotlin
// Visualize compatibility with color
val compatGradient = DynamicGradients.fromCompatibilityScore(score = 87.5)

// Score mapping:
// 0-30:   Red (incompatible)
// 30-45:  Orange (challenging)
// 45-60:  Yellow (moderate)
// 60-75:  Green (good)
// 75-90:  Blue-Teal (excellent)
// 90-100: Purple-Gold (soulmate)
```

### Chakra-Specific Gradients

```kotlin
// Generate gradient for specific chakra with intensity
val throatChakraGradient = DynamicGradients.fromChakra(
    chakraIndex = 4,  // Throat chakra (0-6)
    intensity = 0.8f  // 80% active
)
```

### Zodiac Sign Gradients

```kotlin
// By sign name
val signGradient = DynamicGradients.fromZodiacSign("Pisces")
```

### Transit-Based Gradients

```kotlin
// Current astrological transit
val transitGradient = DynamicGradients.fromTransit(
    planetName = "Venus",
    transitType = "conjunction"  // or "opposition", "trine", "square"
)
```

### Time-Based Gradients

```kotlin
// Auto-select by current time
val timeGradient = DynamicGradients.fromTimeOfDay()

// Auto-select by current season
val seasonGradient = DynamicGradients.fromSeason()
```

### Mood-Based Gradients

```kotlin
// Generate gradient matching mood/emotion
val moodGradient = DynamicGradients.fromMood("calm")

// Available moods:
// "calm", "energized", "romantic", "focused",
// "spiritual", "sad", "anxious", "confident"
```

### Intention-Based Gradients

```kotlin
// Generate gradient supporting manifestation goal
val intentionGradient = DynamicGradients.fromIntention("abundance")

// Available intentions:
// "abundance", "love", "healing", "creativity",
// "clarity", "protection", "transformation", "peace"
```

---

## Animated Gradients

Three animation types: Flowing, Breathing, Rotating.

### 1. Flowing Gradients (Scrolling Effect)

#### Horizontal Flow
```kotlin
@Composable
fun MyComponent() {
    val gradient = AnimatedGradients.flowingHorizontal(
        colors = listOf(FireOrange, SacredGold, ChakraYellow),
        durationMillis = 3000,
        easing = LinearEasing
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(gradient)
    )
}
```

#### Vertical Flow
```kotlin
val gradient = AnimatedGradients.flowingVertical(
    colors = ChakraGradient,  // All 7 chakras
    durationMillis = 5000
)
```

#### Diagonal Flow
```kotlin
val gradient = AnimatedGradients.flowingDiagonal(
    colors = listOf(MysticPurple, StardustBlue, WaterTeal),
    durationMillis = 4000
)
```

#### Radial Flow (Pulse)
```kotlin
val gradient = AnimatedGradients.flowingRadial(
    colors = listOf(SpiritualPurple, MysticViolet, CosmicViolet),
    durationMillis = 3000,
    easing = FastOutSlowInEasing
)
```

---

### 2. Breathing Gradients (Pulsing Effect)

Creates meditative, calming effect.

```kotlin
@Composable
fun MeditationScreen() {
    val breathingGradient = AnimatedGradients.breathing(
        colors = listOf(NightSky, CosmicViolet, MysticPurple),
        durationMillis = 4000,  // 4 second breath cycle
        minAlpha = 0.5f,
        maxAlpha = 1.0f,
        direction = GradientDirection.Radial
    )

    // Use for meditation timer, breathing exercises
}
```

#### Color Morphing
```kotlin
val morphingGradient = AnimatedGradients.colorMorphing(
    colors = listOf(
        EarthGreen,  // Earth
        WaterTeal,   // Water
        AirCyan,     // Air
        FireOrange   // Fire
    ),
    durationMillis = 5000,
    direction = GradientDirection.Vertical
)
```

#### Shimmer Breathing
```kotlin
val shimmerGradient = AnimatedGradients.shimmerBreathing(
    baseColors = listOf(NightSky, MoonlightSilver),
    highlightColor = Color.White,
    durationMillis = 3000
)
```

---

### 3. Rotating Gradients (Spinning Effect)

#### Basic Rotation
```kotlin
val rotatingGradient = AnimatedGradients.rotating(
    colors = listOf(SacredGold, AuraGold, SpiritualPurple),
    durationMillis = 5000,
    clockwise = true,
    centerX = 500f,
    centerY = 500f
)
```

#### Spiral Animation
```kotlin
val spiralGradient = AnimatedGradients.spiral(
    colors = listOf(CosmicViolet, MysticPurple, SpiritualPurple),
    rotationDurationMillis = 5000,
    pulseDurationMillis = 3000
)
```

#### Wave Animation
```kotlin
val waveGradient = AnimatedGradients.wave(
    colors = listOf(WaterTeal, StardustBlue, AirCyan),
    durationMillis = 4000,
    amplitude = 500f
)
```

---

### Preset Animated Gradients

Ready-to-use spiritual animations:

```kotlin
// Northern lights flowing
AnimatedGradients.aurora()

// Energy centers flowing up spine
AnimatedGradients.chakraFlow()

// Breathing universe
AnimatedGradients.cosmicPulse()

// Rotating mandala
AnimatedGradients.sacredGeometry()

// Passionate diagonal flow
AnimatedGradients.tantricEnergy()

// Earth → Water → Air → Fire cycle
AnimatedGradients.elementalCycle()

// Gentle lunar glow
AnimatedGradients.moonlightShimmer()

// Sunrise/sunset flow
AnimatedGradients.goldenHour()

// Pulsing intuition
AnimatedGradients.thirdEyeActivation()

// Expanding love
AnimatedGradients.heartOpening()
```

---

## Usage Examples

### Example 1: Profile Header with Dynamic Gradient

```kotlin
@Composable
fun ProfileHeader(profile: UserProfile) {
    val gradient = DynamicGradients.fromProfile(profile)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = profile.profileName,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
    }
}
```

### Example 2: Compatibility Card with Score Gradient

```kotlin
@Composable
fun CompatibilityCard(score: Double) {
    val gradient = DynamicGradients.fromCompatibilityScore(score)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            Column {
                Text("Compatibility Score", style = MaterialTheme.typography.titleMedium)
                Text("${score.toInt()}%", style = MaterialTheme.typography.displayLarge)
            }
        }
    }
}
```

### Example 3: Animated Chakra Meditation Screen

```kotlin
@Composable
fun ChakraMeditationScreen() {
    val chakraFlowGradient = AnimatedGradients.chakraFlow()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(chakraFlowGradient),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Focus on your breath",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}
```

### Example 4: Time-Aware Splash Screen

```kotlin
@Composable
fun SplashScreen() {
    val timeBasedGradient = DynamicGradients.fromTimeOfDay()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(timeBasedGradient),
        contentAlignment = Alignment.Center
    ) {
        Text("SpiritAtlas", style = MaterialTheme.typography.displayLarge)
    }
}
```

### Example 5: Seasonal Background

```kotlin
@Composable
fun HomeScreen() {
    val seasonalGradient = DynamicGradients.fromSeason()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(seasonalGradient)
    ) { padding ->
        // Content
    }
}
```

---

## Best Practices

### 1. Performance Considerations

**DO**:
- Use static gradients from `GradientLibrary` when profile data doesn't change
- Cache dynamic gradients if profile data is stable
- Use `remember` for gradients that don't need to update

```kotlin
val cachedGradient = remember(profile.id) {
    DynamicGradients.fromProfile(profile)
}
```

**DON'T**:
- Regenerate dynamic gradients on every recomposition
- Use multiple animated gradients on the same screen (max 2-3)
- Apply animated gradients to frequently recomposing elements

### 2. Animation Duration Guidelines

- **Fast animations** (1-2s): Energetic, exciting content
- **Medium animations** (3-4s): Balanced, standard UI elements
- **Slow animations** (5-8s): Meditative, calming experiences

### 3. Color Harmony

When creating custom gradients, maintain spiritual harmony:

```kotlin
// GOOD: Related colors on spectrum
val harmonicGradient = listOf(
    ChakraBlue,
    ChakraIndigo,
    ChakraCrown
)

// AVOID: Jarring color jumps
val disharmonicGradient = listOf(
    ChakraRed,
    ChakraGreen  // Complementary colors can clash
)
```

### 4. Accessibility

- Ensure text on gradients has sufficient contrast
- Test gradients in both light and dark mode
- Provide solid color fallbacks for accessibility settings

```kotlin
val gradient = if (isAccessibilityEnabled) {
    Brush.verticalGradient(listOf(SolidColor, SolidColor))
} else {
    DynamicGradients.fromProfile(profile)
}
```

### 5. Spiritual Intention

Match gradient energy to screen purpose:

| Screen Type | Recommended Gradient |
|-------------|---------------------|
| Login/Auth | `GradientLibrary.crownChakra` (connection) |
| Profile | `DynamicGradients.fromProfile()` (personalized) |
| Compatibility | `DynamicGradients.fromCompatibilityScore()` (relationship) |
| Meditation | `AnimatedGradients.cosmicPulse()` (calm) |
| Settings | `GradientLibrary.throatChakra` (expression) |
| Home | `DynamicGradients.fromTimeOfDay()` (current energy) |

---

## Performance Optimization

### Memory Management

```kotlin
// Cache gradients at ViewModel level
class ProfileViewModel : ViewModel() {
    private var cachedGradient: Brush? = null

    fun getProfileGradient(profile: UserProfile): Brush {
        return cachedGradient ?: DynamicGradients.fromProfile(profile).also {
            cachedGradient = it
        }
    }
}
```

### Reducing Recomposition

```kotlin
@Composable
fun OptimizedGradientBox(profile: UserProfile) {
    // Only regenerate when profile ID changes
    val gradient = remember(profile.id) {
        DynamicGradients.fromProfile(profile)
    }

    Box(modifier = Modifier.background(gradient))
}
```

### Animation Performance

```kotlin
// Limit animation FPS for better battery life
val lowPowerGradient = AnimatedGradients.breathing(
    colors = chakraColors,
    durationMillis = 6000  // Slower = fewer frames
)
```

---

## Summary

### Gradient Counts

- **Total Static Gradients**: 72
- **Dynamic Generation**: Infinite combinations
- **Animated Presets**: 10
- **Animation Types**: 3 (Flowing, Breathing, Rotating)

### Key Files

- `GradientLibrary.kt` - 50+ static gradients
- `DynamicGradients.kt` - Profile-based generation
- `AnimatedGradients.kt` - Animation system
- `Color.kt` - Base colors and 20 existing gradients

### Quick Start

```kotlin
// Static gradient
GradientLibrary.heartChakra

// Dynamic gradient
DynamicGradients.fromProfile(profile)

// Animated gradient
AnimatedGradients.aurora()
```

---

**For questions or gradient requests, see the project maintainers.**

*May your gradients flow with divine energy.* ✨
