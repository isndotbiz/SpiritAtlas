# SpiritAtlas Spiritual Icons Guide

## Overview

The SpiritualIcons component system provides beautiful, hand-crafted vector icons for spiritual themes. All icons are drawn using Canvas API with smooth paths, gradients, and subtle glow effects.

## Quick Start

```kotlin
import com.spiritatlas.core.ui.components.*

// Basic usage
SunIcon()

// Custom size
MoonIcon(size = 32.dp)

// Custom color
HeartChakraIcon(color = TantricRose)

// Disable glow effect
FlowerOfLifeIcon(glowEffect = false)
```

## Icon Categories

### Profile Types (3 icons)

Energy archetypes for profile identification:

| Icon | Function | Default Color | Description |
|------|----------|---------------|-------------|
| Masculine | `MasculineIcon()` | `FireOrange` | Upward-pointing triangle (fire/yang energy) |
| Feminine | `FeminineIcon()` | `WaterTeal` | Downward-pointing triangle (water/yin energy) |
| Non-Binary | `NonBinaryIcon()` | `FireOrange` + `WaterTeal` | Hexagram (balanced union) |

**Usage Example:**
```kotlin
Row {
    MasculineIcon(size = 24.dp)
    FeminineIcon(size = 24.dp)
    NonBinaryIcon(size = 24.dp)
}
```

### Zodiac Signs (12 icons)

All astrological signs with proper elemental colors:

| Sign | Function | Default Color | Element |
|------|----------|---------------|---------|
| Aries | `AriesIcon()` | `FireOrange` | Fire |
| Taurus | `TaurusIcon()` | `EarthGreen` | Earth |
| Gemini | `GeminiIcon()` | `AirCyan` | Air |
| Cancer | `CancerIcon()` | `WaterTeal` | Water |
| Leo | `LeoIcon()` | `FireOrange` | Fire |
| Virgo | `VirgoIcon()` | `EarthGreen` | Earth |
| Libra | `LibraIcon()` | `AirCyan` | Air |
| Scorpio | `ScorpioIcon()` | `WaterTeal` | Water |
| Sagittarius | `SagittariusIcon()` | `FireOrange` | Fire |
| Capricorn | `CapricornIcon()` | `EarthGreen` | Earth |
| Aquarius | `AquariusIcon()` | `AirCyan` | Air |
| Pisces | `PiscesIcon()` | `WaterTeal` | Water |

**Usage Example:**
```kotlin
// Zodiac indicator
Row(verticalAlignment = Alignment.CenterVertically) {
    AriesIcon(size = 20.dp)
    Spacer(Modifier.width(8.dp))
    Text("Aries - March 21 to April 19")
}
```

### Chakras (7 icons)

Seven energy centers with traditional colors:

| Chakra | Function | Default Color | Sanskrit Name |
|--------|----------|---------------|---------------|
| Root | `RootChakraIcon()` | `ChakraRed` | Muladhara |
| Sacral | `SacralChakraIcon()` | `ChakraOrange` | Svadhisthana |
| Solar Plexus | `SolarPlexusChakraIcon()` | `ChakraYellow` | Manipura |
| Heart | `HeartChakraIcon()` | `ChakraGreen` | Anahata |
| Throat | `ThroatChakraIcon()` | `ChakraBlue` | Vishuddha |
| Third Eye | `ThirdEyeChakraIcon()` | `ChakraIndigo` | Ajna |
| Crown | `CrownChakraIcon()` | `ChakraCrown` | Sahasrara |

**Usage Example:**
```kotlin
// Chakra balance display
Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    ChakraRow("Root", ChakraRed) { RootChakraIcon() }
    ChakraRow("Sacral", ChakraOrange) { SacralChakraIcon() }
    ChakraRow("Solar Plexus", ChakraYellow) { SolarPlexusChakraIcon() }
    ChakraRow("Heart", ChakraGreen) { HeartChakraIcon() }
    ChakraRow("Throat", ChakraBlue) { ThroatChakraIcon() }
    ChakraRow("Third Eye", ChakraIndigo) { ThirdEyeChakraIcon() }
    ChakraRow("Crown", ChakraCrown) { CrownChakraIcon() }
}

@Composable
fun ChakraRow(name: String, color: Color, icon: @Composable () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon()
        Spacer(Modifier.width(12.dp))
        Text(name, color = color)
    }
}
```

### Elements (5 icons)

Classical and spiritual elements with gradients:

| Element | Function | Gradient | Description |
|---------|----------|----------|-------------|
| Fire | `FireElementIcon()` | Yellow→Orange→Red | Upward triangle |
| Water | `WaterElementIcon()` | Blue→Teal→Deep Teal | Downward triangle |
| Earth | `EarthElementIcon()` | Lime→Green→Brown | Downward triangle with line |
| Air | `AirElementIcon()` | Light Cyan→Cyan→Blue | Upward triangle with line |
| Spirit | `SpiritElementIcon()` | Purple gradient | Circle with 8-pointed star |

**Usage Example:**
```kotlin
// Element selector
Row(horizontalArrangement = Arrangement.SpaceEvenly) {
    ElementButton("Fire") { FireElementIcon(size = 32.dp) }
    ElementButton("Water") { WaterElementIcon(size = 32.dp) }
    ElementButton("Earth") { EarthElementIcon(size = 32.dp) }
    ElementButton("Air") { AirElementIcon(size = 32.dp) }
    ElementButton("Spirit") { SpiritElementIcon(size = 32.dp) }
}
```

### Moon Phases (4 icons)

Lunar cycle representations:

| Phase | Function | Default Color | Description |
|-------|----------|---------------|-------------|
| New Moon | `NewMoonIcon()` | `NightSky` | Dark circle with outline |
| Waxing | `WaxingMoonIcon()` | `MoonlightSilver` | Crescent moon (right side) |
| Full Moon | `FullMoonIcon()` | `MoonlightSilver` | Bright circle with glow |
| Waning | `WaningMoonIcon()` | `MoonlightSilver` | Crescent moon (left side) |

**Usage Example:**
```kotlin
// Moon phase indicator
fun getMoonPhaseIcon(phase: MoonPhase): @Composable () -> Unit = {
    when (phase) {
        MoonPhase.NEW -> NewMoonIcon()
        MoonPhase.WAXING_CRESCENT, MoonPhase.FIRST_QUARTER, MoonPhase.WAXING_GIBBOUS ->
            WaxingMoonIcon()
        MoonPhase.FULL -> FullMoonIcon()
        MoonPhase.WANING_GIBBOUS, MoonPhase.LAST_QUARTER, MoonPhase.WANING_CRESCENT ->
            WaningMoonIcon()
    }
}
```

### Sacred Geometry (4 icons)

Mathematical spiritual patterns:

| Pattern | Function | Default Color | Description |
|---------|----------|---------------|-------------|
| Flower of Life | `FlowerOfLifeIcon()` | `SacredGold` | 7 overlapping circles |
| Metatron's Cube | `MetatronsCubeIcon()` | `SpiritualPurple` | 13 circles with connections |
| Sri Yantra | `SriYantraIcon()` | `SacredGold` | Interlocking triangles |
| Vesica Piscis | `VesicaPiscisIcon()` | `MysticPurple` | Two overlapping circles |

**Usage Example:**
```kotlin
// Decorative background
Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    FlowerOfLifeIcon(
        size = 256.dp,
        color = SacredGold.copy(alpha = 0.1f),
        glowEffect = false
    )
}
```

### Planetary Symbols (6 icons)

Astrological planetary symbols:

| Planet | Function | Default Color | Symbol |
|--------|----------|---------------|--------|
| Sun | `SunIcon()` | `SacredGold` | Circle with rays |
| Venus | `VenusIcon()` | `TantricRose` | Female symbol (♀) |
| Mars | `MarsIcon()` | `ChakraRed` | Male symbol (♂) |
| Mercury | `MercuryIcon()` | `AirCyan` | Caduceus style (☿) |
| Jupiter | `JupiterIcon()` | `SpiritualPurple` | Stylized "4" (♃) |
| Saturn | `SaturnIcon()` | `NightSky` | Cross with tail (♄) |

**Usage Example:**
```kotlin
// Planetary ruler indicator
Row(verticalAlignment = Alignment.CenterVertically) {
    Text("Ruled by:", color = MoonlightSilver)
    Spacer(Modifier.width(8.dp))
    VenusIcon(size = 20.dp)
    Spacer(Modifier.width(4.dp))
    Text("Venus", color = TantricRose)
}
```

## Common Parameters

All icons support these parameters:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `modifier` | `Modifier` | `Modifier` | Standard Compose modifier |
| `size` | `Dp` | `24.dp` | Icon size (width and height) |
| `color` | `Color` | Varies | Primary icon color |
| `glowEffect` | `Boolean` | `true` | Whether to show glow effect |

**Note:** `NonBinaryIcon` has additional parameters:
- `colorMasculine`: Color for upward triangle (default `FireOrange`)
- `colorFeminine`: Color for downward triangle (default `WaterTeal`)

## Size Guidelines

Recommended icon sizes for different use cases:

| Use Case | Recommended Size | Example |
|----------|------------------|---------|
| Small inline icons | 16.dp - 20.dp | List items, inline text |
| Standard icons | 24.dp - 32.dp | Buttons, navigation |
| Feature icons | 40.dp - 56.dp | Cards, prominent features |
| Decorative/Hero | 64.dp - 128.dp | Headers, splash screens |
| Background pattern | 128.dp+ | Watermarks, backgrounds |

## Color Customization

### Using Theme Colors

```kotlin
// Chakra colors
RootChakraIcon(color = ChakraRed)
SacralChakraIcon(color = ChakraOrange)
HeartChakraIcon(color = ChakraGreen)

// Element colors
FireElementIcon() // Uses built-in gradient
WaterElementIcon() // Uses built-in gradient

// Zodiac with element colors
AriesIcon(color = FireOrange)
TaurusIcon(color = EarthGreen)
GeminiIcon(color = AirCyan)
CancerIcon(color = WaterTeal)
```

### Custom Colors

```kotlin
// Monochrome theme
SunIcon(color = Color.White)
MoonIcon(color = Color.White)

// Brand colors
FlowerOfLifeIcon(color = Color(0xFFFF6B6B))
SriYantraIcon(color = Color(0xFF4ECDC4))

// With transparency
HeartChakraIcon(color = ChakraGreen.copy(alpha = 0.5f))
```

## Glow Effects

All icons support optional glow effects:

```kotlin
// With glow (default)
SunIcon(glowEffect = true)

// Without glow
SunIcon(glowEffect = false)

// Subtle glow for light backgrounds
FlowerOfLifeIcon(
    color = SacredGold.copy(alpha = 0.8f),
    glowEffect = true
)
```

## Integration Examples

### Profile Header

```kotlin
@Composable
fun ProfileHeader(profile: Profile) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Energy type
        when (profile.energyType) {
            EnergyType.MASCULINE -> MasculineIcon(size = 32.dp)
            EnergyType.FEMININE -> FeminineIcon(size = 32.dp)
            EnergyType.NON_BINARY -> NonBinaryIcon(size = 32.dp)
        }

        // Zodiac sign
        getZodiacIcon(profile.sunSign, size = 32.dp)

        // Moon phase
        getMoonPhaseIcon(profile.moonPhase, size = 32.dp)
    }
}

@Composable
fun getZodiacIcon(sign: ZodiacSign, size: Dp = 24.dp) {
    when (sign) {
        ZodiacSign.ARIES -> AriesIcon(size = size)
        ZodiacSign.TAURUS -> TaurusIcon(size = size)
        ZodiacSign.GEMINI -> GeminiIcon(size = size)
        ZodiacSign.CANCER -> CancerIcon(size = size)
        ZodiacSign.LEO -> LeoIcon(size = size)
        ZodiacSign.VIRGO -> VirgoIcon(size = size)
        ZodiacSign.LIBRA -> LibraIcon(size = size)
        ZodiacSign.SCORPIO -> ScorpioIcon(size = size)
        ZodiacSign.SAGITTARIUS -> SagittariusIcon(size = size)
        ZodiacSign.CAPRICORN -> CapricornIcon(size = size)
        ZodiacSign.AQUARIUS -> AquariusIcon(size = size)
        ZodiacSign.PISCES -> PiscesIcon(size = size)
    }
}
```

### Chakra Balance Card

```kotlin
@Composable
fun ChakraBalanceCard(chakraLevels: Map<Chakra, Float>) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Chakra Balance", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(16.dp))

            chakraLevels.forEach { (chakra, level) ->
                ChakraBalanceRow(chakra, level)
            }
        }
    }
}

@Composable
fun ChakraBalanceRow(chakra: Chakra, level: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        when (chakra) {
            Chakra.ROOT -> RootChakraIcon(size = 24.dp)
            Chakra.SACRAL -> SacralChakraIcon(size = 24.dp)
            Chakra.SOLAR_PLEXUS -> SolarPlexusChakraIcon(size = 24.dp)
            Chakra.HEART -> HeartChakraIcon(size = 24.dp)
            Chakra.THROAT -> ThroatChakraIcon(size = 24.dp)
            Chakra.THIRD_EYE -> ThirdEyeChakraIcon(size = 24.dp)
            Chakra.CROWN -> CrownChakraIcon(size = 24.dp)
        }

        Spacer(Modifier.width(12.dp))

        // Name
        Text(
            text = chakra.name,
            modifier = Modifier.weight(1f)
        )

        // Progress bar
        LinearProgressIndicator(
            progress = level,
            modifier = Modifier.width(100.dp)
        )
    }
}
```

### Elemental Compatibility

```kotlin
@Composable
fun ElementalCompatibility(element1: Element, element2: Element) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // First element
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            getElementIcon(element1, size = 48.dp)
            Text(element1.name, color = getElementColor(element1))
        }

        // Compatibility indicator
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Compatibility",
            tint = MysticPurple
        )

        // Second element
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            getElementIcon(element2, size = 48.dp)
            Text(element2.name, color = getElementColor(element2))
        }
    }
}

@Composable
fun getElementIcon(element: Element, size: Dp = 24.dp) {
    when (element) {
        Element.FIRE -> FireElementIcon(size = size)
        Element.WATER -> WaterElementIcon(size = size)
        Element.EARTH -> EarthElementIcon(size = size)
        Element.AIR -> AirElementIcon(size = size)
        Element.SPIRIT -> SpiritElementIcon(size = size)
    }
}
```

### Sacred Geometry Background

```kotlin
@Composable
fun SacredBackground(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background pattern
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            FlowerOfLifeIcon(
                size = 300.dp,
                color = SacredGold.copy(alpha = 0.05f),
                glowEffect = false
            )
        }

        // Content
        content()
    }
}
```

## Performance Considerations

1. **Icon Recomposition**: Icons are lightweight and optimized for performance
2. **Glow Effects**: Disable for better performance on low-end devices
3. **Large Sizes**: Be cautious with sizes above 128.dp in lists
4. **Gradients**: Element icons use gradients which are slightly more expensive

## Best Practices

1. **Consistency**: Use the same size for similar elements
2. **Spacing**: Add appropriate spacing around icons (8.dp - 16.dp)
3. **Accessibility**: Always provide content descriptions when used as semantic icons
4. **Color Contrast**: Ensure sufficient contrast with background
5. **Theme Support**: Icons work well on both light and dark backgrounds

## Future Enhancements

Potential additions to the icon system:

- Tarot card symbols
- I Ching hexagrams
- Additional planetary bodies (Uranus, Neptune, Pluto)
- Runic symbols
- Celtic knots
- Animated icon variants
- Additional sacred geometry patterns

---

**Version**: 1.0
**Last Updated**: 2025-12-08
**Component File**: `SpiritualIcons.kt`
**Example File**: `SpiritualIconsExample.kt`
