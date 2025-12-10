# Animation Enhancement Guide

## Overview

This guide documents the enhanced spiritual animation system for SpiritAtlas. All animations are built with Jetpack Compose and optimized for 60 FPS performance.

## Animation Files

### 1. ChakraAnimations.kt

**Purpose**: Advanced chakra visualizations with particle effects and energy flows

**Components**:

- `SpinningChakraWithParticles`: Full-featured chakra animation with:
  - Smooth 360° rotation
  - Particle emission from center (24 configurable particles)
  - Pulsing glow effect
  - Breathing scale animation
  - Sacred geometry petals (accurate petal counts per chakra)
  - Color transitions matching chakra energy

- `ChakraSpinner`: Compact chakra indicator for UI elements
  - Lightweight performance
  - Ideal for list items or small indicators

- `ChakraActivation`: Burst animation when chakra is activated
  - Expanding wave effect
  - Triggered by user interaction

- `ChakraAlignment`: Shows all 7 chakras spinning in vertical alignment
  - Perfect for meditation screens
  - Individual chakra rotation directions alternate

**Usage Example**:
```kotlin
SpinningChakraWithParticles(
  modifier = Modifier.size(300.dp),
  chakraIndex = 6, // Crown chakra
  particleCount = 30,
  rotationDurationMs = 8000
)
```

**Performance**: Optimized for continuous animation at 60 FPS. Each particle is calculated once and reused.

---

### 2. ZodiacAnimations.kt

**Purpose**: Constellation patterns and zodiac wheel animations

**Components**:

- `ConstellationReveal`: Sequential star and line drawing animation
  - Stars fade in sequentially with proper astronomical positions
  - Lines connect stars to form constellation
  - Constellation name appears with shimmer effect
  - Twinkling star animation
  - Real constellation patterns for Aries, Taurus, Leo (more can be added)

- `ZodiacWheelRotation`: All 12 zodiac signs rotating in a circle
  - Slow rotation (30s default)
  - Highlight specific signs
  - Cosmic aesthetic

- `StarFieldBackground`: Ambient twinkling stars
  - Configurable star count
  - Perfect background layer for zodiac screens

- `ZodiacTransition`: Morphs between two constellations
  - Smooth interpolation of star positions

**Usage Example**:
```kotlin
var isRevealed by remember { mutableStateOf(false) }

Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
  StarFieldBackground(
    modifier = Modifier.fillMaxSize(),
    starCount = 100
  )

  ConstellationReveal(
    modifier = Modifier.fillMaxSize(),
    zodiacSign = "Leo",
    isRevealed = isRevealed,
    starColor = AuraGold,
    lineColor = CosmicBlue
  )
}
```

**Performance**: Star field uses pre-calculated positions. Constellation reveal is a one-time animation.

---

### 3. EnergyFlowAnimations.kt

**Purpose**: Visualize energy connections and compatibility between profiles

**Components**:

- `EnergyFlowBetweenProfiles`: Flowing particles between two points
  - Color based on compatibility strength (red to gold gradient)
  - Bidirectional flow option
  - Configurable particle count and density
  - Smooth sine wave paths
  - Horizontal or vertical orientation

- `CircularEnergyFlow`: Rotating energy aura around a profile
  - Creates depth effect with particle sizing
  - Smooth orbit animation
  - Configurable radius and particle count

- `HeartConnection`: Romantic/soul connection visualization
  - Two hearts connected by flowing particles
  - Bezier curve energy flow
  - Heartbeat pulse animation
  - Perfect for tantric/relationship compatibility

- `CompatibilityEnergyMeter`: Animated progress bar with particles
  - Color changes based on compatibility level
  - Flowing particles inside the bar
  - Energy glow at the end

**Usage Example**:
```kotlin
// Between two profile cards
EnergyFlowBetweenProfiles(
  modifier = Modifier.fillMaxWidth().height(100.dp),
  compatibilityPercentage = 0.85f, // 85% compatibility
  isHorizontal = true,
  bidirectional = true
)

// Compatibility score
CompatibilityEnergyMeter(
  modifier = Modifier.fillMaxWidth().height(60.dp),
  percentage = compatibilityScore / 100f
)
```

**Performance**: Particle system uses pre-calculated offsets. Optimized for 30+ particles at 60 FPS.

---

### 4. PlanetAnimations.kt

**Purpose**: Astronomical and planetary orbit animations

**Components**:

- `SolarSystemAnimation`: Complete solar system with 8 planets
  - Accurate relative orbital speeds
  - Inner planets (Mercury, Venus, Earth, Mars)
  - Outer planets (Jupiter, Saturn, Uranus, Neptune)
  - Saturn's rings
  - Sun with corona effect
  - Planet shadows based on sun position
  - Configurable speed multiplier

- `SinglePlanetOrbit`: Focus on one planet's orbit
  - Detailed planet rendering with glow
  - Orbital trail showing recent path
  - Perfect for birth chart visualizations

- `PlanetaryAlignment`: Rare alignment visualization
  - Shows planets moving into alignment
  - Energy burst effect when fully aligned
  - Progress parameter (0.0 = orbiting, 1.0 = aligned)

- `MoonPhasesAnimation`: Lunar cycle visualization
  - Smooth moon phase transitions
  - Realistic shadow movement
  - Glow effect

**Usage Example**:
```kotlin
// Full solar system
SolarSystemAnimation(
  modifier = Modifier.fillMaxSize(),
  showOrbits = true,
  showInnerPlanets = true,
  showOuterPlanets = true,
  speedMultiplier = 2.0f // 2x speed
)

// Single planet (for natal chart)
SinglePlanetOrbit(
  modifier = Modifier.size(300.dp),
  planetName = "Venus",
  orbitDurationMs = 8000
)
```

**Performance**: Optimized with DrawScope transforms. Can render full solar system at 60 FPS.

---

### 5. SacredGeometryAnimations.kt

**Purpose**: Sacred geometry patterns with spiritual significance

**Components**:

- `FlowerOfLife`: Expanding circular pattern
  - 7-circle or 19-circle patterns
  - Expansion animation
  - Slow rotation
  - Pulsing glow

- `MetatronsCube`: Complex geometric pattern
  - 13 sacred circles
  - All circles interconnected
  - 3D rotation effect
  - Inner hexagonal structure

- `SriYantra`: Hindu sacred diagram
  - 9 interlocking triangles (5 down, 4 up)
  - Represents Shiva-Shakti union
  - Morphing animation parameter
  - Central bindu (point)

- `GoldenRatioSpiral`: Fibonacci spiral
  - Based on phi (1.618)
  - Growth animation
  - Mathematical beauty

- `VesicaPiscis`: Two overlapping circles
  - Sacred intersection
  - Expansion animation
  - Represents divine union

- `PlatonicSolid`: 3D geometric shapes
  - Tetrahedron, Cube, Octahedron
  - Rotation on multiple axes
  - 2D projection of 3D rotation

**Usage Example**:
```kotlin
// Meditation screen background
FlowerOfLife(
  modifier = Modifier.size(400.dp),
  circleCount = 19,
  color = AuraGold,
  animate = true
)

// Sacred space loading
MetatronsCube(
  modifier = Modifier.size(300.dp),
  color = SpiritualPurple,
  rotationDurationMs = 30000
)

// Tantric union visualization
SriYantra(
  modifier = Modifier.size(350.dp),
  morphProgress = animatedProgress,
  color = AuraGold
)
```

**Performance**: Complex paths are rendered once per frame. All animations maintain 60 FPS.

---

## Integration Guide

### Step 1: Choose Your Animation

Refer to the component descriptions above to select the appropriate animation for your use case.

### Step 2: Add to Composable

```kotlin
@Composable
fun YourScreen() {
  Box(modifier = Modifier.fillMaxSize()) {
    // Background
    StarFieldBackground(modifier = Modifier.fillMaxSize())

    // Main content
    Column {
      // Your animation
      SpinningChakraWithParticles(
        modifier = Modifier.size(200.dp),
        chakraIndex = 6
      )
    }
  }
}
```

### Step 3: Control Animation State

```kotlin
// For triggered animations
var isRevealed by remember { mutableStateOf(false) }

Button(onClick = { isRevealed = true }) {
  Text("Reveal Constellation")
}

ConstellationReveal(
  modifier = Modifier.fillMaxSize(),
  zodiacSign = "Leo",
  isRevealed = isRevealed
)
```

---

## Performance Guidelines

### Optimization Tips

1. **Use appropriate sizes**: Don't make canvases larger than needed
2. **Limit particle counts**: 20-30 particles is optimal for most devices
3. **Cache expensive calculations**: Use `remember` for particle positions
4. **Avoid nested animations**: Combine effects in single Canvas when possible
5. **Use composition locals**: Share animation state across components

### Performance Targets

- All animations target **60 FPS** on mid-range devices
- Particle effects are optimized for **30+ particles**
- Complex geometry renders in **< 16ms per frame**

### Testing Performance

```kotlin
// Enable GPU overdraw visualization in Android Developer Options
// Enable "Profile GPU Rendering" to see frame times
```

---

## Accessibility Considerations

### Animation Settings

Add user preference for reduced motion:

```kotlin
@Composable
fun AnimationWrapper(
  enableAnimations: Boolean,
  staticContent: @Composable () -> Unit,
  animatedContent: @Composable () -> Unit
) {
  if (enableAnimations) {
    animatedContent()
  } else {
    staticContent()
  }
}
```

### Usage

```kotlin
AnimationWrapper(
  enableAnimations = userPreferences.enableAnimations,
  staticContent = {
    // Static chakra symbol
  },
  animatedContent = {
    SpinningChakraWithParticles(...)
  }
)
```

---

## Color Scheme Integration

All animations use colors from `core/ui/theme/Color.kt`:

- **Chakras**: `ChakraRed`, `ChakraOrange`, `ChakraYellow`, `ChakraGreen`, `ChakraBlue`, `ChakraIndigo`, `ChakraCrown`
- **Zodiac**: `AuraGold`, `CosmicBlue`, `StardustBlue`, `MoonlightSilver`
- **Energy**: `SpiritualPurple`, `TantricRose`, `ChakraGreen`, `AuraGold`
- **Planets**: Specific colors per planet (Sun = `AuraGold`, Mars = `ChakraRed`, etc.)
- **Sacred Geometry**: `AuraGold`, `SpiritualPurple`, `MysticPurple`, `CosmicViolet`

---

## Animation Durations

Standard durations (from `AnimationDurations`):

- **Quick**: 200ms (micro-interactions)
- **Medium**: 400ms (standard transitions)
- **Slow**: 600ms (reveal animations)
- **Breath**: 4000ms (breathing/meditation)
- **Pulse**: 2000ms (energy pulsing)
- **Float**: 3000ms (levitation effects)

---

## Advanced Techniques

### Combining Animations

```kotlin
Box(modifier = Modifier.size(400.dp)) {
  // Background
  StarFieldBackground(modifier = Modifier.fillMaxSize())

  // Middle layer
  FlowerOfLife(
    modifier = Modifier.fillMaxSize(),
    animate = false
  )

  // Foreground
  SpinningChakraWithParticles(
    modifier = Modifier.align(Alignment.Center).size(200.dp),
    chakraIndex = 6
  )
}
```

### Custom Easing

All animations use spiritual easing curves from `SpiritualEasing`:
- `Gentle`: FastOutSlowInEasing
- `Breath`: Custom bezier for breathing
- `Float`: Smooth levitation
- `Pulse`: Energy pulsing
- `Wave`: Wave motion

---

## Testing Animations

### Visual Testing

Use `AnimationShowcase.kt` to preview all animations:

```kotlin
@Preview
@Composable
fun PreviewAnimations() {
  AnimationShowcaseScreen()
}
```

### Unit Testing

Animations are stateless and pure - test business logic separately:

```kotlin
@Test
fun testChakraColorMapping() {
  assertEquals(ChakraRed, getChakraColor(0))
  assertEquals(ChakraCrown, getChakraColor(6))
}
```

---

## Future Enhancements

Potential additions:
- [ ] Aura field animations
- [ ] Mandala rotating patterns
- [ ] Energy meridian flow (acupuncture)
- [ ] Kundalini rising visualization
- [ ] DNA helix sacred geometry
- [ ] Torus field animation
- [ ] Merkaba rotation

---

## Support

For questions or issues with animations:
1. Check this guide first
2. Review `AnimationShowcase.kt` for examples
3. Refer to inline documentation in animation files
4. Test on physical device for accurate performance

---

## Contributing

When adding new animations:
1. Follow existing file structure
2. Use colors from theme system
3. Target 60 FPS performance
4. Add comprehensive documentation
5. Include usage examples
6. Update `AnimationShowcase.kt`
7. Add entry to this guide

---

**Version**: 1.0
**Last Updated**: 2025-12-10
**Author**: Agent 3: Animation Enhancement Specialist
