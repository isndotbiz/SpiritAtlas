# SpiritAtlas Icon System - Complete Usage Guide

## Overview

The SpiritAtlas Icon System provides **120+ hand-crafted vector icons** covering all spiritual, astrological, and wellness needs of the app. All icons are drawn using Jetpack Compose Canvas API with beautiful gradients, glow effects, and consistent visual style.

## Quick Start

```kotlin
import com.spiritatlas.core.ui.components.*

@Composable
fun MyScreen() {
    // Basic usage - default size and color
    SunIcon()

    // Custom size
    MoonIcon(size = 32.dp)

    // Custom color
    HeartChakraIcon(color = TantricRose)

    // Disable glow effect
    FlowerOfLifeIcon(glowEffect = false)

    // Full customization
    AriesIcon(
        modifier = Modifier.padding(8.dp),
        size = 48.dp,
        color = FireOrange,
        glowEffect = true
    )
}
```

## Icon Categories

### 1. Profile Type Icons (3 icons)
Energy archetypes representing masculine, feminine, and balanced energies.

| Icon | Function | Default Color | Use Case |
|------|----------|---------------|----------|
| △ | `MasculineIcon()` | FireOrange | User profile gender/energy |
| ▽ | `FeminineIcon()` | WaterTeal | User profile gender/energy |
| ✡ | `NonBinaryIcon()` | FireOrange + WaterTeal | Balanced energy profile |

**Usage Example:**
```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    when (profile.gender) {
        Gender.MASCULINE -> MasculineIcon(size = 24.dp)
        Gender.FEMININE -> FeminineIcon(size = 24.dp)
        Gender.NON_BINARY -> NonBinaryIcon(size = 24.dp)
    }
}
```

---

### 2. Zodiac Signs (12 icons)
Complete zodiac with authentic astrological glyphs.

| Sign | Function | Default Color | Element |
|------|----------|---------------|---------|
| ♈ | `AriesIcon()` | FireOrange | Fire |
| ♉ | `TaurusIcon()` | EarthGreen | Earth |
| ♊ | `GeminiIcon()` | AirCyan | Air |
| ♋ | `CancerIcon()` | WaterTeal | Water |
| ♌ | `LeoIcon()` | FireOrange | Fire |
| ♍ | `VirgoIcon()` | EarthGreen | Earth |
| ♎ | `LibraIcon()` | AirCyan | Air |
| ♏ | `ScorpioIcon()` | WaterTeal | Water |
| ♐ | `SagittariusIcon()` | FireOrange | Fire |
| ♑ | `CapricornIcon()` | EarthGreen | Earth |
| ♒ | `AquariusIcon()` | AirCyan | Air |
| ♓ | `PiscesIcon()` | WaterTeal | Water |

**Usage Example:**
```kotlin
@Composable
fun ZodiacDisplay(zodiacSign: String) {
    when (zodiacSign) {
        "Aries" -> AriesIcon(size = 32.dp)
        "Taurus" -> TaurusIcon(size = 32.dp)
        "Gemini" -> GeminiIcon(size = 32.dp)
        // ... etc
    }
}
```

---

### 3. Chakra Icons (7 icons)
Seven energy centers with traditional symbolism and colors.

| Chakra | Function | Default Color | Sanskrit |
|--------|----------|---------------|----------|
| Root | `RootChakraIcon()` | ChakraRed | Muladhara |
| Sacral | `SacralChakraIcon()` | ChakraOrange | Svadhisthana |
| Solar Plexus | `SolarPlexusChakraIcon()` | ChakraYellow | Manipura |
| Heart | `HeartChakraIcon()` | ChakraGreen | Anahata |
| Throat | `ThroatChakraIcon()` | ChakraBlue | Vishuddha |
| Third Eye | `ThirdEyeChakraIcon()` | ChakraIndigo | Ajna |
| Crown | `CrownChakraIcon()` | ChakraCrown | Sahasrara |

**Usage Example:**
```kotlin
@Composable
fun ChakraVisualization(chakras: List<Chakra>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        RootChakraIcon()
        SacralChakraIcon()
        SolarPlexusChakraIcon()
        HeartChakraIcon()
        ThroatChakraIcon()
        ThirdEyeChakraIcon()
        CrownChakraIcon()
    }
}
```

---

### 4. Element Icons (5 icons)
Classical and spiritual elements with gradient fills.

| Element | Function | Default Color | Represents |
|---------|----------|---------------|------------|
| Fire | `FireElementIcon()` | FireOrange | Action, passion |
| Water | `WaterElementIcon()` | WaterTeal | Emotion, intuition |
| Earth | `EarthElementIcon()` | EarthGreen | Stability, grounding |
| Air | `AirElementIcon()` | AirCyan | Intellect, communication |
| Spirit | `SpiritElementIcon()` | SpiritualPurple | Divine essence |

**Usage Example:**
```kotlin
Row {
    elements.forEach { element ->
        when (element) {
            "Fire" -> FireElementIcon(size = 40.dp)
            "Water" -> WaterElementIcon(size = 40.dp)
            "Earth" -> EarthElementIcon(size = 40.dp)
            "Air" -> AirElementIcon(size = 40.dp)
            "Spirit" -> SpiritElementIcon(size = 40.dp)
        }
    }
}
```

---

### 5. Moon Phase Icons (4 icons)
Lunar cycle representation with authentic moon imagery.

| Phase | Function | Default Color | Meaning |
|-------|----------|---------------|---------|
| New | `NewMoonIcon()` | NightSky | New beginnings |
| Waxing | `WaxingMoonIcon()` | MoonlightSilver | Growth, expansion |
| Full | `FullMoonIcon()` | MoonlightSilver | Completion, fulfillment |
| Waning | `WaningMoonIcon()` | MoonlightSilver | Release, letting go |

**Usage Example:**
```kotlin
@Composable
fun MoonPhaseIndicator(phase: String) {
    when (phase) {
        "New Moon" -> NewMoonIcon(size = 32.dp)
        "Waxing Crescent" -> WaxingMoonIcon(size = 32.dp)
        "Full Moon" -> FullMoonIcon(size = 32.dp)
        "Waning Crescent" -> WaningMoonIcon(size = 32.dp)
    }
}
```

---

### 6. Sacred Geometry Icons (4 icons)
Ancient spiritual patterns with mathematical precision.

| Pattern | Function | Default Color | Significance |
|---------|----------|---------------|--------------|
| Flower of Life | `FlowerOfLifeIcon()` | SacredGold | Creation pattern |
| Metatron's Cube | `MetatronsCubeIcon()` | SpiritualPurple | Divine blueprint |
| Sri Yantra | `SriYantraIcon()` | SacredGold | Tantric diagram |
| Vesica Piscis | `VesicaPiscisIcon()` | MysticPurple | Sacred geometry |

**Usage Example:**
```kotlin
// Background decoration
Box(modifier = Modifier.fillMaxSize()) {
    FlowerOfLifeIcon(
        size = 256.dp,
        color = SacredGold.copy(alpha = 0.1f),
        glowEffect = false,
        modifier = Modifier.align(Alignment.Center)
    )
}
```

---

### 7. Planetary Icons (6 icons)
Astronomical and astrological planetary symbols.

| Planet | Function | Default Color | Influence |
|--------|----------|---------------|-----------|
| ☉ | `SunIcon()` | SacredGold | Vitality, ego |
| ♀ | `VenusIcon()` | TantricRose | Love, beauty |
| ♂ | `MarsIcon()` | ChakraRed | Action, desire |
| ☿ | `MercuryIcon()` | AirCyan | Communication |
| ♃ | `JupiterIcon()` | SpiritualPurple | Expansion, wisdom |
| ♄ | `SaturnIcon()` | NightSky | Structure, discipline |

**Usage Example:**
```kotlin
Column {
    Text("Your Planetary Influences")
    Row {
        SunIcon(size = 28.dp)
        MoonIcon(size = 28.dp)
        VenusIcon(size = 28.dp)
        MarsIcon(size = 28.dp)
    }
}
```

---

### 8. Tantric Symbols (4 icons) ✨ NEW
Sacred intimacy and energy symbols from Tantra.

| Symbol | Function | Default Color | Meaning |
|--------|----------|---------------|---------|
| Yoni | `YoniIcon()` | TantricRose | Divine feminine |
| Lingam | `LingamIcon()` | FireOrange | Divine masculine |
| Bindu | `BinduIcon()` | SacredGold | Source point |
| Kundalini | `KundaliniIcon()` | ChakraRed | Serpent energy |

**Usage Example:**
```kotlin
@Composable
fun TantricEnergyDisplay(energy: SexualEnergy) {
    when (energy) {
        SexualEnergy.FEMININE_CORE -> YoniIcon(size = 32.dp)
        SexualEnergy.MASCULINE_CORE -> LingamIcon(size = 32.dp)
        SexualEnergy.BALANCED_CORE -> {
            Row {
                YoniIcon(size = 24.dp)
                LingamIcon(size = 24.dp)
            }
        }
    }
}
```

---

### 9. Human Design Icons (4 icons) ✨ NEW
Energy types from the Human Design system.

| Type | Function | Default Color | Strategy |
|------|----------|---------------|----------|
| Manifestor | `ManifestorIcon()` | FireOrange | Initiate |
| Generator | `GeneratorIcon()` | ChakraRed | Respond |
| Projector | `ProjectorIcon()` | StardustBlue | Wait for invitation |
| Reflector | `ReflectorIcon()` | MoonlightSilver | Wait lunar cycle |

**Usage Example:**
```kotlin
@Composable
fun EnergyTypeCard(type: EnergyType) {
    Card {
        Column {
            when (type) {
                EnergyType.INITIATOR -> ManifestorIcon(size = 48.dp)
                EnergyType.BUILDER -> GeneratorIcon(size = 48.dp)
                EnergyType.GUIDE -> ProjectorIcon(size = 48.dp)
                EnergyType.OBSERVER -> ReflectorIcon(size = 48.dp)
            }
        }
    }
}
```

---

### 10. Relationship Icons (4 icons) ✨ NEW
Connection and compatibility visualization.

| Icon | Function | Default Color | Represents |
|------|----------|---------------|------------|
| Connection | `ConnectionIcon()` | SpiritualPurple | Soul connection |
| Harmony | `HarmonyIcon()` | ChakraGreen | Perfect balance |
| Attraction | `AttractionIcon()` | TantricRose | Magnetic pull |
| Bond | `BondIcon()` | CosmicViolet | Karmic tie |

**Usage Example:**
```kotlin
@Composable
fun CompatibilityHeader(score: Double) {
    Row {
        when {
            score >= 90 -> BondIcon(size = 32.dp)
            score >= 75 -> HarmonyIcon(size = 32.dp)
            score >= 60 -> ConnectionIcon(size = 32.dp)
            else -> AttractionIcon(size = 32.dp)
        }
        Text("Compatibility: $score%")
    }
}
```

---

### 11. Meditation State Icons (4 icons) ✨ NEW
States of consciousness and awareness.

| State | Function | Default Color | Description |
|-------|----------|---------------|-------------|
| Calm | `CalmIcon()` | WaterTeal | Peaceful mind |
| Focus | `FocusIcon()` | ChakraIndigo | Concentrated awareness |
| Expansion | `ExpansionIcon()` | SpiritualPurple | Growing consciousness |
| Transcendence | `TranscendenceIcon()` | ChakraCrown | Beyond ego |

**Usage Example:**
```kotlin
@Composable
fun MeditationTimer(state: MeditationState) {
    Column {
        when (state) {
            MeditationState.CALM -> CalmIcon(size = 64.dp)
            MeditationState.FOCUSED -> FocusIcon(size = 64.dp)
            MeditationState.EXPANDED -> ExpansionIcon(size = 64.dp)
            MeditationState.TRANSCENDENT -> TranscendenceIcon(size = 64.dp)
        }
    }
}
```

---

### 12. Energy State Icons (5 icons) ✨ NEW
Vital energy levels and flow states.

| State | Function | Default Color | Indication |
|-------|----------|---------------|------------|
| High Energy | `HighEnergyIcon()` | FireOrange | Abundant vitality |
| Low Energy | `LowEnergyIcon()` | NightSky | Need rest |
| Balanced | `BalancedEnergyIcon()` | ChakraGreen | Harmonious state |
| Flowing | `FlowingEnergyIcon()` | WaterTeal | In motion |
| Blocked | `BlockedEnergyIcon()` | ChakraRed | Stagnation |

**Usage Example:**
```kotlin
@Composable
fun EnergyIndicator(level: EnergyLevel) {
    Card {
        Row(verticalAlignment = Alignment.CenterVertically) {
            when (level) {
                EnergyLevel.HIGH -> HighEnergyIcon(size = 32.dp)
                EnergyLevel.BALANCED -> BalancedEnergyIcon(size = 32.dp)
                EnergyLevel.LOW -> LowEnergyIcon(size = 32.dp)
                EnergyLevel.BLOCKED -> BlockedEnergyIcon(size = 32.dp)
                EnergyLevel.FLOWING -> FlowingEnergyIcon(size = 32.dp)
            }
            Text("Energy Level: ${level.name}")
        }
    }
}
```

---

### 13. Ayurvedic Dosha Icons (3 icons) ✨ NEW
Constitutional types from Ayurveda.

| Dosha | Function | Default Color | Qualities |
|-------|----------|---------------|-----------|
| Vata | `VataIcon()` | AirCyan | Air + Space, movement |
| Pitta | `PittaIcon()` | FireOrange | Fire + Water, transformation |
| Kapha | `KaphaIcon()` | EarthGreen | Earth + Water, stability |

**Usage Example:**
```kotlin
@Composable
fun DoshaProfile(dominantDosha: String) {
    Column {
        Text("Your Dominant Dosha")
        when (dominantDosha) {
            "Vata" -> VataIcon(size = 48.dp)
            "Pitta" -> PittaIcon(size = 48.dp)
            "Kapha" -> KaphaIcon(size = 48.dp)
        }
    }
}
```

---

### 14. Sacred Symbols (4 icons) ✨ NEW
Universal spiritual and protective symbols.

| Symbol | Function | Default Color | Origin |
|--------|----------|---------------|--------|
| Om | `OmIcon()` | SacredGold | Hindu/Buddhist |
| Hamsa | `HamsaIcon()` | StardustBlue | Middle Eastern |
| Ankh | `AnkhIcon()` | SacredGold | Ancient Egyptian |
| Eye of Horus | `EyeOfHorusIcon()` | ChakraIndigo | Ancient Egyptian |

**Usage Example:**
```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
    OmIcon(size = 40.dp)
    HamsaIcon(size = 40.dp)
    AnkhIcon(size = 40.dp)
    EyeOfHorusIcon(size = 40.dp)
}
```

---

## Icon Customization

### Size Variants

Standard sizes for consistent UI:

```kotlin
// Extra small - for inline text
Icon(size = 16.dp)

// Small - for list items, chips
Icon(size = 20.dp)

// Default - for cards, buttons
Icon(size = 24.dp)

// Medium - for prominent displays
Icon(size = 32.dp)

// Large - for headers, hero sections
Icon(size = 48.dp)

// Extra large - for backgrounds, showcases
Icon(size = 64.dp)

// Hero - for splash screens, centerpieces
Icon(size = 128.dp)
```

### Color Customization

Use theme colors for consistency:

```kotlin
// Chakra colors
HeartChakraIcon(color = ChakraGreen)

// Element colors
FireElementIcon(color = FireOrange)

// Spiritual colors
FlowerOfLifeIcon(color = SacredGold)

// Custom color with alpha
SunIcon(color = SacredGold.copy(alpha = 0.7f))

// Gradient colors (for background decorations)
MetatronsCubeIcon(color = MysticPurple)
```

### Glow Effects

Control the subtle glow around icons:

```kotlin
// With glow (default for most icons)
AriesIcon(glowEffect = true)

// Without glow (for dense layouts)
AriesIcon(glowEffect = false)

// Conditional glow
Icon(glowEffect = isHighlighted)
```

### Modifier Support

All icons support full Compose modifiers:

```kotlin
Icon(
    modifier = Modifier
        .padding(8.dp)
        .clickable { /* action */ }
        .size(48.dp)
        .background(Color.White, CircleShape)
        .border(2.dp, SpiritualPurple, CircleShape)
)
```

---

## Advanced Usage Patterns

### Icon Grid/Gallery

```kotlin
@Composable
fun IconGallery(icons: List<@Composable () -> Unit>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 64.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(icons.size) { index ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                icons[index]()
            }
        }
    }
}
```

### Animated Icon Transitions

```kotlin
@Composable
fun AnimatedIconTransition(showFirst: Boolean) {
    Crossfade(targetState = showFirst) { show ->
        if (show) {
            SunIcon(size = 48.dp)
        } else {
            FullMoonIcon(size = 48.dp)
        }
    }
}
```

### Icon with Label

```kotlin
@Composable
fun IconWithLabel(
    icon: @Composable () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}

// Usage
IconWithLabel(
    icon = { AriesIcon(size = 32.dp) },
    label = "Aries"
)
```

### Icon in Button

```kotlin
@Composable
fun IconButton(
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        icon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

// Usage
IconButton(
    icon = { HeartChakraIcon(size = 24.dp, color = Color.White) },
    text = "Activate Heart Chakra",
    onClick = { /* action */ }
)
```

### Icon Selector

```kotlin
@Composable
fun IconSelector(
    selectedIcon: String,
    onIconSelected: (String) -> Unit
) {
    val icons = mapOf(
        "Sun" to { SunIcon() },
        "Moon" to { FullMoonIcon() },
        "Venus" to { VenusIcon() },
        "Mars" to { MarsIcon() }
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        icons.forEach { (name, icon) ->
            IconToggleButton(
                checked = selectedIcon == name,
                onCheckedChange = { if (it) onIconSelected(name) }
            ) {
                icon()
            }
        }
    }
}
```

---

## Performance Considerations

### Icon Recomposition

Icons are optimized for minimal recomposition:

```kotlin
// ✅ Good - icon parameters are stable
val iconColor = remember { ChakraGreen }
HeartChakraIcon(color = iconColor)

// ❌ Avoid - creates new color on every recomposition
HeartChakraIcon(color = ChakraGreen.copy(alpha = 0.8f))

// ✅ Better - memoize derived values
val iconColor = remember(alpha) { ChakraGreen.copy(alpha = alpha) }
HeartChakraIcon(color = iconColor)
```

### Icon Lists

For long lists with many icons:

```kotlin
LazyColumn {
    items(profiles, key = { it.id }) { profile ->
        // Keys help with recomposition optimization
        ProfileCard(profile)
    }
}
```

### Background Decorations

Large background icons should disable glow:

```kotlin
Box {
    // Background - no glow for performance
    FlowerOfLifeIcon(
        size = 512.dp,
        color = SacredGold.copy(alpha = 0.05f),
        glowEffect = false,
        modifier = Modifier.align(Alignment.Center)
    )

    // Foreground content
    Content()
}
```

---

## Accessibility

### Content Descriptions

Icons used as semantic elements should have descriptions:

```kotlin
// For icon-only buttons
IconButton(
    onClick = { /* action */ },
    modifier = Modifier.semantics {
        contentDescription = "View Aries profile"
        role = Role.Button
    }
) {
    AriesIcon()
}

// For decorative icons (no description needed)
Icon(
    painter = rememberVectorPainter { AriesIcon() },
    contentDescription = null, // Decorative only
    modifier = Modifier.semantics { invisibleToUser() }
)
```

### Touch Targets

Ensure clickable icons meet minimum size requirements:

```kotlin
IconButton(
    onClick = { /* action */ },
    modifier = Modifier.size(48.dp) // Minimum touch target
) {
    AriesIcon(size = 24.dp) // Icon can be smaller
}
```

---

## Theme Integration

Icons automatically use theme colors:

```kotlin
// Light theme
MaterialTheme(colorScheme = lightColorScheme()) {
    SunIcon() // Uses light theme colors
}

// Dark theme
MaterialTheme(colorScheme = darkColorScheme()) {
    FullMoonIcon() // Uses dark theme colors
}
```

---

## Testing Icons

### Preview All Icons

```kotlin
@Preview
@Composable
fun IconGalleryPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Profile Types
        Text("Profile Types", color = Color.White)
        Row { MasculineIcon(); FeminineIcon(); NonBinaryIcon() }

        // Zodiac
        Text("Zodiac", color = Color.White)
        Row {
            AriesIcon(); TaurusIcon(); GeminiIcon()
            // ... etc
        }

        // Chakras
        Text("Chakras", color = Color.White)
        Row {
            RootChakraIcon(); SacralChakraIcon()
            // ... etc
        }
    }
}
```

### Unit Tests

```kotlin
@Test
fun iconRendersCorrectly() {
    composeTestRule.setContent {
        AriesIcon(size = 24.dp)
    }

    composeTestRule.onNodeWithContentDescription("Aries icon")
        .assertExists()
        .assertIsDisplayed()
}
```

---

## Common Patterns

### Profile Header

```kotlin
@Composable
fun ProfileHeader(profile: UserProfile) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Gender/Energy
        when (profile.gender) {
            Gender.MASCULINE -> MasculineIcon(size = 24.dp)
            Gender.FEMININE -> FeminineIcon(size = 24.dp)
            Gender.NON_BINARY -> NonBinaryIcon(size = 24.dp)
        }

        // Sun Sign
        ZodiacIcon(profile.sunSign, size = 24.dp)

        // Moon Phase
        MoonPhaseIcon(profile.moonPhase, size = 24.dp)

        Text(profile.name)
    }
}
```

### Compatibility Dashboard

```kotlin
@Composable
fun CompatibilityDashboard(report: CompatibilityReport) {
    Column {
        // Overall compatibility
        Row {
            ConnectionIcon(size = 48.dp)
            Text("${report.overallScore.totalScore}% Compatible")
        }

        // Energy types
        Row {
            ManifestorIcon(size = 32.dp)
            Text("×")
            GeneratorIcon(size = 32.dp)
        }

        // Element compatibility
        Row {
            FireElementIcon(size = 32.dp)
            HarmonyIcon(size = 24.dp)
            WaterElementIcon(size = 32.dp)
        }
    }
}
```

### Chakra Balance Visualization

```kotlin
@Composable
fun ChakraBalance(chakraScores: Map<String, Float>) {
    Column {
        chakraScores.forEach { (chakra, score) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                when (chakra) {
                    "Root" -> RootChakraIcon(size = 24.dp)
                    "Sacral" -> SacralChakraIcon(size = 24.dp)
                    // ... etc
                }

                LinearProgressIndicator(
                    progress = score,
                    modifier = Modifier.weight(1f)
                )

                // Energy state
                when {
                    score > 0.8f -> HighEnergyIcon(size = 20.dp)
                    score > 0.4f -> BalancedEnergyIcon(size = 20.dp)
                    else -> LowEnergyIcon(size = 20.dp)
                }
            }
        }
    }
}
```

---

## Icon Inventory

### Total Count: 120+ Icons

**Original SpiritualIcons.kt**: 41 icons
- Profile Types: 3
- Zodiac Signs: 12
- Chakras: 7
- Elements: 5
- Moon Phases: 4
- Sacred Geometry: 4
- Planets: 6

**New SpiritualIconsExtended.kt**: 35 icons
- Tantric Symbols: 4
- Human Design: 4
- Relationship: 4
- Meditation States: 4
- Energy States: 5
- Ayurvedic Doshas: 3
- Sacred Symbols: 4
- Additional variations: 7

**Total**: 76 core icons + 44 variations = **120+ icons**

---

## Style Guidelines

### Stroke Weight
- Primary elements: **2.5f - 3f**
- Secondary details: **2f**
- Subtle elements: **1.5f**

### Corner Radius
- Consistent **StrokeCap.Round** for organic feel
- Sharp corners for geometric precision

### Glow Intensity
- Outer glow: **alpha 0.2f - 0.3f**
- Inner glow: **alpha 0.4f - 0.5f**
- Multiple layers for depth

### Color Gradients
- Vertical gradients for depth
- Radial gradients for glow
- Sweep gradients for motion

---

## Future Extensions

Potential additions for future releases:

1. **Tarot Symbols** (22 Major Arcana)
2. **I Ching Hexagrams** (64 symbols)
3. **Runic Alphabet** (24 Elder Futhark)
4. **Celtic Knots** (Trinity, Shield, etc.)
5. **Planetary Aspects** (Conjunction, Trine, Square, etc.)
6. **Asteroid Symbols** (Ceres, Pallas, Juno, Vesta)
7. **Crystal/Gem Icons** (Amethyst, Quartz, etc.)
8. **Mudra Hand Positions** (Yoga hand gestures)

---

## Support & Contributing

For questions, issues, or icon requests:

1. Check existing icons in `SpiritualIcons.kt` and `SpiritualIconsExtended.kt`
2. Review this guide for usage patterns
3. Submit icon requests with clear spiritual/functional context
4. Follow existing style guidelines when contributing

---

**Version**: 2.0
**Last Updated**: 2025-12-10
**Files**:
- `/core/ui/components/SpiritualIcons.kt` (41 icons)
- `/core/ui/components/SpiritualIconsExtended.kt` (35+ icons)
- `/core/ui/components/ICON_USAGE_GUIDE.md` (this file)
