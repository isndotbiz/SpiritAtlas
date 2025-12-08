# Transit Visualization Components

Comprehensive planetary transit visualization system for SpiritAtlas featuring beautiful Canvas-drawn planet symbols, animated aspect diagrams, and real-time planetary hour tracking.

## Overview

The Transit Visualization system provides a complete set of composable components for displaying astrological transits, planetary movements, retrograde periods, and planetary hours with premium quality animations and authentic astrological symbols.

**Location:** `/core/ui/src/main/java/com/spiritatlas/core/ui/visualization/TransitVisualization.kt`

## Components

### 1. TransitTimeline

Horizontal scrolling timeline displaying upcoming transits across multiple days.

**Features:**
- Horizontal scrolling through 7+ days of transits
- Date markers with highlighted current day
- Transit markers showing planets and aspects
- Clickable transit events
- Connection lines between related transits
- Animated glow effects for exact aspects

**Usage:**
```kotlin
TransitTimeline(
    transits = listOf(
        Transit(
            planet1 = Planet.SUN,
            planet2 = Planet.MOON,
            aspect = Aspect.CONJUNCTION,
            exactTime = LocalDateTime.now().plusHours(2),
            orb = 0.5f
        )
    ),
    daysToShow = 7,
    onTransitClick = { transit ->
        // Handle transit click
    }
)
```

**Parameters:**
- `transits: List<Transit>` - List of transits to display
- `startDate: LocalDateTime` - Start date for timeline (default: now)
- `daysToShow: Int` - Number of days to show (default: 7)
- `onTransitClick: ((Transit) -> Unit)?` - Click handler

---

### 2. TransitCard

Beautiful card displaying detailed information about a single transit.

**Features:**
- Planet icons with authentic symbols
- Aspect symbol and type
- Exact timing information
- Orb accuracy display
- "EXACT" badge for precise aspects
- Animated aspect line connection
- Transit strength meter
- Applying/Separating status

**Usage:**
```kotlin
TransitCard(
    transit = Transit(
        planet1 = Planet.VENUS,
        planet2 = Planet.MARS,
        aspect = Aspect.TRINE,
        exactTime = LocalDateTime.now().plusDays(1),
        orb = 2.3f,
        isApplying = true
    ),
    showImpact = true
)
```

**Parameters:**
- `transit: Transit` - Transit data to display
- `showImpact: Boolean` - Show strength meter (default: true)

---

### 3. TransitAspectDiagram

Visual diagram showing planetary aspect relationship with rotating animation.

**Features:**
- Two planets positioned by aspect angle
- Zodiac circle background
- Animated rotation of planetary positions
- Aspect line with appropriate styling
- Pulsing animation for exact aspects
- Orb accuracy progress ring
- Central aspect symbol
- Real-time orb display

**Usage:**
```kotlin
TransitAspectDiagram(
    transit = Transit(
        planet1 = Planet.JUPITER,
        planet2 = Planet.SATURN,
        aspect = Aspect.SQUARE,
        exactTime = LocalDateTime.now().plusDays(3),
        orb = 5.2f
    ),
    animate = true
)
```

**Parameters:**
- `transit: Transit` - Transit to visualize
- `animate: Boolean` - Enable rotation animation (default: true)

---

### 4. RetrogradeBadge

Special indicator for retrograde planets with intensity-based glow.

**Features:**
- ℞ (retrograde symbol) with planet glyph
- Rotating outer glow rings
- Pulsing animation (faster when stationing)
- Color-coded by planet
- Rotation direction indicates direct/retrograde motion
- Variable glow intensity for station points

**Usage:**
```kotlin
RetrogradeBadge(
    retrogradeInfo = RetrogradeInfo(
        planet = Planet.MERCURY,
        startDate = LocalDateTime.now().minusDays(5),
        endDate = LocalDateTime.now().plusDays(16),
        isStationing = false,
        isDirect = false
    ),
    size = 80.dp
)
```

**Parameters:**
- `retrogradeInfo: RetrogradeInfo` - Retrograde period data
- `size: Dp` - Badge size (default: 48.dp)

---

### 5. TransitImpactMeter

Visual meter showing how a transit affects the user's natal chart.

**Features:**
- Animated horizontal progress bar
- Gradient fill based on aspect type
- Percentage display
- Aspect nature indicator (Harmonious/Challenging/Neutral)
- Glowing endpoint on filled portion
- Color coding by impact level
- Marker lines for reference

**Usage:**
```kotlin
TransitImpactMeter(
    transit = Transit(
        planet1 = Planet.SUN,
        planet2 = Planet.MOON,
        aspect = Aspect.CONJUNCTION,
        exactTime = LocalDateTime.now(),
        orb = 0.5f
    ),
    personalImpact = 0.85f  // 85% impact
)
```

**Parameters:**
- `transit: Transit` - Transit data
- `personalImpact: Float` - Impact level 0.0-1.0

---

### 6. PlanetaryHoursDisplay

Current planetary hour with live countdown timer.

**Features:**
- Current ruling planet icon and name
- Live countdown timer (updates every second)
- Progress circle visualization
- Next hour preview
- Time range display
- Color-coded by ruling planet
- Smooth progress animations

**Usage:**
```kotlin
PlanetaryHoursDisplay(
    currentHour = PlanetaryHour(
        planet = Planet.VENUS,
        startTime = LocalDateTime.now().minusMinutes(23),
        endTime = LocalDateTime.now().plusMinutes(37),
        hourNumber = 3
    ),
    nextHour = PlanetaryHour(
        planet = Planet.MERCURY,
        startTime = LocalDateTime.now().plusMinutes(37),
        endTime = LocalDateTime.now().plusMinutes(97),
        hourNumber = 4
    )
)
```

**Parameters:**
- `currentHour: PlanetaryHour` - Current planetary hour
- `nextHour: PlanetaryHour?` - Next hour preview (optional)

---

### 7. PlanetIcon

Individual planet icon with authentic hand-drawn symbol.

**Features:**
- Canvas-drawn planetary symbols
- Radial glow effect
- Pulsing animation
- Optional label display
- Color-coded by planet

**Usage:**
```kotlin
PlanetIcon(
    planet = Planet.JUPITER,
    size = 64.dp,
    showLabel = true
)
```

**Parameters:**
- `planet: Planet` - Planet to display
- `size: Dp` - Icon size (default: 48.dp)
- `showLabel: Boolean` - Show planet name (default: true)

---

## Data Models

### Planet Enum

All celestial bodies with authentic symbols and colors:

```kotlin
enum class Planet {
    SUN,        // ☉ - Golden
    MOON,       // ☽ - Silver
    MERCURY,    // ☿ - Sky Blue
    VENUS,      // ♀ - Pink
    MARS,       // ♂ - Red-Orange
    JUPITER,    // ♃ - Gold
    SATURN,     // ♄ - Royal Blue
    URANUS,     // ♅ - Cyan
    NEPTUNE,    // ♆ - Purple
    PLUTO,      // ♇ - Brown
    NORTH_NODE, // ☊ - Green
    SOUTH_NODE  // ☋ - Red-Orange
}
```

### Aspect Enum

Traditional astrological aspects:

```kotlin
enum class Aspect {
    CONJUNCTION,  // ☌ - 0° - Neutral - Gold
    SEXTILE,      // ⚹ - 60° - Harmonious - Cyan
    SQUARE,       // □ - 90° - Challenging - Red
    TRINE,        // △ - 120° - Harmonious - Green
    OPPOSITION,   // ☍ - 180° - Challenging - Crimson
    QUINCUNX,     // ⚻ - 150° - Neutral - Purple
    SEMI_SEXTILE  // ⚺ - 30° - Harmonious - Sky Blue
}
```

**Properties:**
- `angle: Int` - Exact angle in degrees
- `symbol: String` - Unicode aspect symbol
- `nature: AspectNature` - Harmonious/Challenging/Neutral
- `color: Color` - Visual color coding
- `orb: Float` - Allowed deviation (degrees)

### Transit Data Class

Complete transit information:

```kotlin
data class Transit(
    val planet1: Planet,
    val planet2: Planet,
    val aspect: Aspect,
    val exactTime: LocalDateTime,
    val orb: Float,
    val isApplying: Boolean = true,
    val intensity: Float = 1.0f
)
```

**Computed Properties:**
- `accuracy: Float` - How close to exact (0.0-1.0)
- `isExact: Boolean` - Within 1° of exact
- `timeUntilExact: Long` - Minutes until exact time

### RetrogradeInfo Data Class

Retrograde period data:

```kotlin
data class RetrogradeInfo(
    val planet: Planet,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isStationing: Boolean = false,
    val isDirect: Boolean = false
)
```

**Computed Properties:**
- `isActive: Boolean` - Currently retrograde
- `daysRemaining: Long` - Days until direct

### PlanetaryHour Data Class

Planetary hour information:

```kotlin
data class PlanetaryHour(
    val planet: Planet,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val hourNumber: Int
)
```

**Computed Properties:**
- `minutesRemaining: Long` - Minutes until next hour
- `progress: Float` - Progress through hour (0.0-1.0)
- `isActive: Boolean` - Currently active

---

## Planet Symbol Drawing

All planetary symbols are hand-drawn using Canvas primitives for authentic appearance:

### Symbol Designs

- **Sun (☉)**: Circle with center dot
- **Moon (☽)**: Crescent shape
- **Mercury (☿)**: Circle with cross below and semicircle above
- **Venus (♀)**: Circle with cross below
- **Mars (♂)**: Circle with arrow pointing up-right
- **Jupiter (♃)**: Stylized "4" with cross
- **Saturn (♄)**: Stylized "h" with cross
- **Uranus (♅)**: "H" with circle and center dot
- **Neptune (♆)**: Trident
- **Pluto (♇)**: "P" with circle
- **North Node (☊)**: Horseshoe facing up with dots
- **South Node (☋)**: Horseshoe facing down with dots

Each symbol features:
- Precise geometric construction
- Consistent line weights
- Radial glow effects
- Color coding by planet

---

## Animation Features

### Glow Effects
- Radial gradient glows on planets
- Pulsing alpha animations
- Intensity-based brightness
- Color-matched to planetary associations

### Motion
- Smooth spring animations for transitions
- Rotating planetary positions
- Pulsing scale for exact aspects
- Flowing particle effects

### Timing
- 60fps smooth rendering
- Configurable animation speeds
- Infinite repeating transitions
- Responsive to user interaction

---

## Color System

All components use the SpiritAtlas spiritual color palette:

**Planetary Colors:**
- Sun: Gold (#FDB813)
- Moon: Silver (#C0C0C0)
- Mercury: Sky Blue (#87CEEB)
- Venus: Pink (#FF69B4)
- Mars: Red-Orange (#FF4500)
- Jupiter: Gold (#FFD700)
- Saturn: Royal Blue (#4169E1)
- Uranus: Cyan (#00CED1)
- Neptune: Purple (#9370DB)
- Pluto: Brown (#8B4513)

**Aspect Colors:**
- Conjunction: Gold
- Sextile: Cyan
- Square: Red
- Trine: Green
- Opposition: Crimson

---

## Example Usage

### Complete Transit Screen

```kotlin
@Composable
fun TransitScreen() {
    val transits = remember { loadUpcomingTransits() }
    val currentHour = remember { getCurrentPlanetaryHour() }
    val retrogrades = remember { getActiveRetrogrades() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current planetary hour
        PlanetaryHoursDisplay(
            currentHour = currentHour,
            nextHour = getNextPlanetaryHour()
        )

        // Transit timeline
        TransitTimeline(
            transits = transits,
            daysToShow = 7
        )

        // Active retrogrades
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            retrogrades.forEach { retrograde ->
                RetrogradeBadge(
                    retrogradeInfo = retrograde,
                    size = 64.dp
                )
            }
        }

        // Detailed transit cards
        transits.take(5).forEach { transit ->
            TransitCard(transit = transit)
            TransitAspectDiagram(transit = transit)
            TransitImpactMeter(
                transit = transit,
                personalImpact = calculatePersonalImpact(transit)
            )
        }
    }
}
```

### Quick Transit Alert

```kotlin
@Composable
fun TransitAlert(transit: Transit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = transit.aspect.color.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PlanetIcon(transit.planet1, size = 32.dp, showLabel = false)
            Text(transit.aspect.symbol, fontSize = 20.sp)
            PlanetIcon(transit.planet2, size = 32.dp, showLabel = false)

            Spacer(Modifier.weight(1f))

            if (transit.isExact) {
                Text(
                    "EXACT NOW",
                    fontWeight = FontWeight.Bold,
                    color = AuraGold
                )
            }
        }
    }
}
```

---

## Sample Data

The `TransitVisualizationExamples.kt` file includes comprehensive sample data:

- 6 sample transits covering all aspect types
- Mercury and Venus retrograde periods
- Current and next planetary hours
- Complete showcase composable
- Individual component examples

**Load Examples:**
```kotlin
import com.spiritatlas.core.ui.visualization.TransitSampleData

// Use sample data for testing
val sampleTransits = TransitSampleData.sampleTransits
val mercuryRx = TransitSampleData.mercuryRetrograde
```

---

## Integration

### Add to Feature Module

```kotlin
// In your feature's build.gradle.kts
dependencies {
    implementation(project(":core:ui"))
    // ... other dependencies
}
```

### Import Components

```kotlin
import com.spiritatlas.core.ui.visualization.*
```

### Fetch Real Transit Data

```kotlin
// Connect to your astrology calculation engine
val transits = astroCalculator.calculateTransits(
    startDate = LocalDateTime.now(),
    endDate = LocalDateTime.now().plusDays(7),
    natalChart = userChart
)

// Display with components
TransitTimeline(transits = transits)
```

---

## Performance

### Optimization Features
- Efficient Canvas drawing with minimal recomposition
- Memoized calculations for expensive operations
- LaunchedEffect for timer updates only
- Remember for static computations
- Smart invalidation on data changes

### Best Practices
- Limit animated components on screen
- Use `remember` for sample data
- Avoid excessive `daysToShow` values
- Consider pagination for long transit lists

---

## Accessibility

All components include:
- Semantic text descriptions
- High contrast color choices
- Readable font sizes (minimum 10.sp)
- Touch target sizes (minimum 48.dp)
- Support for screen readers via MaterialTheme

---

## Testing

Example test files can be found in:
`/core/ui/src/test/java/com/spiritatlas/core/ui/visualization/`

### Basic Component Test

```kotlin
@Test
fun transitCard_displaysCorrectly() {
    composeTestRule.setContent {
        TransitCard(
            transit = Transit(
                planet1 = Planet.SUN,
                planet2 = Planet.MOON,
                aspect = Aspect.CONJUNCTION,
                exactTime = LocalDateTime.now(),
                orb = 0.5f
            )
        )
    }

    composeTestRule.onNodeWithText("Conjunction").assertExists()
    composeTestRule.onNodeWithText("EXACT").assertExists()
}
```

---

## Future Enhancements

Planned features:
- Touch gestures for timeline navigation
- Export transit calendar to ICS
- Notification system for exact aspects
- Historical transit playback
- 3D zodiac wheel option
- Voice narration of transits
- Integration with transit interpretations
- Multi-chart synastry transits

---

## Resources

### Astrological References
- Traditional aspect orbs and meanings
- Planetary hour calculations
- Retrograde period effects
- Transit interpretation guidelines

### Design Inspiration
- Premium meditation apps
- Astronomical visualization tools
- Traditional astrological charts
- Sacred geometry patterns

---

## License

Part of the SpiritAtlas project. See main LICENSE file for details.

---

## Support

For issues or questions about transit visualizations:
- Open an issue in the main repository
- Reference the `TransitVisualization.kt` file
- Include screenshots if possible
- Provide sample data that reproduces the issue

---

**Created:** December 2025
**Version:** 1.0.0
**Components:** 7 main + 12 helper functions
**Lines of Code:** 1,416 (main) + 540 (examples)
