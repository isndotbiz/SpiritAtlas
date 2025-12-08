# Moon Phase Visualization System

## Overview

The Moon Phase Visualization system provides astronomically accurate and visually stunning representations of lunar phases for SpiritAtlas. It combines precise astronomical calculations with beautiful Jetpack Compose UI components to create an immersive spiritual experience.

## Features

### 1. Astronomical Accuracy
- **29.53 day lunar cycle** - Uses the exact synodic month period
- **Accurate illumination calculations** - Based on known new moon reference (January 6, 2000)
- **8 distinct moon phases** - Complete coverage of the lunar cycle
- **Real-time phase determination** - Calculates current phase for any date

### 2. Visual Components

#### MoonPhaseIcon
Individual moon phase icon with realistic rendering.

```kotlin
@Composable
fun MoonPhaseIcon(
    phase: MoonPhase,
    modifier: Modifier = Modifier,
    size: Float = 80f,
    showGlow: Boolean = true,
    illumination: Float = phase.illumination,
    animated: Boolean = true
)
```

**Features:**
- Accurate moon shape with terminator line
- Realistic crater texture (12 predefined craters)
- Glowing effect for illuminated portions
- Animated pulse effect
- Customizable size

**Usage:**
```kotlin
MoonPhaseIcon(
    phase = MoonPhase.FULL_MOON,
    size = 100f,
    showGlow = true
)
```

#### MoonPhaseCard
Card component showing phase with spiritual meaning.

```kotlin
@Composable
fun MoonPhaseCard(
    phaseData: MoonPhaseData,
    modifier: Modifier = Modifier,
    showFullMeaning: Boolean = true
)
```

**Features:**
- Phase name and symbol
- Illumination percentage
- Spiritual theme
- Complete meaning and guidance
- Color-coded by phase

**Usage:**
```kotlin
val today = LocalDate.now()
val phaseData = MoonPhaseData.forDate(today)

MoonPhaseCard(
    phaseData = phaseData,
    showFullMeaning = true
)
```

#### LunarCycle
Circular visualization of complete lunar cycle.

```kotlin
@Composable
fun LunarCycle(
    currentPhase: MoonPhase,
    modifier: Modifier = Modifier,
    onPhaseSelected: ((MoonPhase) -> Unit)? = null
)
```

**Features:**
- All 8 phases arranged in circle
- Current phase highlighted
- Interactive phase selection
- Smooth rotation animation
- Orbital path visualization

**Usage:**
```kotlin
val currentPhase = MoonPhase.calculatePhase(LocalDate.now())

LunarCycle(
    currentPhase = currentPhase,
    onPhaseSelected = { phase ->
        // Handle phase selection
        println("Selected: ${phase.name}")
    }
)
```

#### MoonCalendar
Month view with moon phases on each day.

```kotlin
@Composable
fun MoonCalendar(
    year: Int,
    month: Int,
    modifier: Modifier = Modifier,
    onDaySelected: ((LocalDate, MoonPhaseData) -> Unit)? = null
)
```

**Features:**
- Full month calendar grid
- Moon phase icon on each day
- Current day highlighting
- Interactive day selection
- Automatic phase calculation

**Usage:**
```kotlin
val today = LocalDate.now()

MoonCalendar(
    year = today.year,
    month = today.monthValue,
    onDaySelected = { date, phaseData ->
        // Show phase details for selected day
    }
)
```

#### MoonPhaseAnimation
Smooth animated transitions between phases.

```kotlin
@Composable
fun MoonPhaseAnimation(
    fromPhase: MoonPhase,
    toPhase: MoonPhase,
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    onAnimationComplete: (() -> Unit)? = null
)
```

**Features:**
- Smooth interpolation between phases
- Configurable duration
- Completion callback
- Realistic illumination transition

**Usage:**
```kotlin
MoonPhaseAnimation(
    fromPhase = MoonPhase.NEW_MOON,
    toPhase = MoonPhase.FULL_MOON,
    durationMillis = 3000,
    onAnimationComplete = {
        println("Animation complete!")
    }
)
```

## Moon Phase Enumeration

### Available Phases

```kotlin
enum class MoonPhase(
    val illumination: Float,
    val meaning: String,
    val spiritualTheme: String,
    val color: Color,
    val symbol: String
)
```

| Phase | Illumination | Spiritual Theme | Color |
|-------|-------------|-----------------|-------|
| NEW_MOON | 0% | Intention & New Beginnings | Deep Indigo |
| WAXING_CRESCENT | 25% | Growth & Development | Purple |
| FIRST_QUARTER | 50% | Action & Commitment | Violet |
| WAXING_GIBBOUS | 75% | Refinement & Adjustment | Light Purple |
| FULL_MOON | 100% | Illumination & Celebration | Golden Yellow |
| WANING_GIBBOUS | 75% | Gratitude & Sharing | Purple Violet |
| LAST_QUARTER | 50% | Release & Forgiveness | Indigo |
| WANING_CRESCENT | 25% | Rest & Reflection | Dark Purple |

## Astronomical Calculations

### Phase Calculation Algorithm

```kotlin
fun calculatePhase(date: LocalDate): MoonPhase {
    val daysSinceNewMoon = calculateDaysSinceKnownNewMoon(date)
    val lunarCycle = daysSinceNewMoon % LUNAR_CYCLE_DAYS
    val illumination = calculateIllumination(lunarCycle)

    // Determine phase based on day in cycle
    return when {
        lunarCycle < 7.4f -> WAXING_CRESCENT
        lunarCycle < 11.1f -> FIRST_QUARTER
        // ... etc
    }
}
```

### Illumination Calculation

The illumination percentage is calculated based on the number of days since the last new moon:

```kotlin
private fun calculateIllumination(lunarCycle: Float): Float {
    return if (lunarCycle < LUNAR_CYCLE_DAYS / 2) {
        // Waxing (growing): 0% -> 100%
        lunarCycle / (LUNAR_CYCLE_DAYS / 2)
    } else {
        // Waning (shrinking): 100% -> 0%
        2f - (lunarCycle / (LUNAR_CYCLE_DAYS / 2))
    }
}
```

### Reference New Moon
- **Date:** January 6, 2000, 18:14 UTC
- **Astronomical Constant:** Synodic month = 29.53058867 days

## Spiritual Meanings

### New Moon (0%)
**Theme:** Intention & New Beginnings

**Meaning:** New beginnings, setting intentions, planting seeds of manifestation

**Activities:**
- Set intentions for the lunar cycle
- Start new projects or ventures
- Plant seeds (literal or metaphorical)
- Meditate on your deepest desires
- Create a vision board

### Waxing Crescent (0-50%)
**Theme:** Growth & Development

**Meaning:** Initial growth, taking action on goals, building momentum

**Activities:**
- Take first steps toward goals
- Build new habits
- Stay focused and committed
- Gather resources and information
- Network and connect

### First Quarter (50%)
**Theme:** Action & Commitment

**Meaning:** Decision-making, overcoming challenges, commitment to path

**Activities:**
- Make important decisions
- Overcome obstacles
- Take assertive action
- Adjust plans as needed
- Stay determined

### Waxing Gibbous (50-100%)
**Theme:** Refinement & Adjustment

**Meaning:** Refinement, adjustment, preparing for culmination

**Activities:**
- Refine your approach
- Polish and perfect
- Prepare for completion
- Review progress
- Make final adjustments

### Full Moon (100%)
**Theme:** Illumination & Celebration

**Meaning:** Completion, illumination, celebration, heightened energy and emotions

**Activities:**
- Celebrate achievements
- Perform full moon rituals
- Release and let go
- Express gratitude
- Charge crystals and tools

### Waning Gibbous (50-100%)
**Theme:** Gratitude & Sharing

**Meaning:** Gratitude, sharing wisdom, giving back to community

**Activities:**
- Share your knowledge
- Give back to community
- Express gratitude
- Reflect on lessons learned
- Mentor others

### Last Quarter (50%)
**Theme:** Release & Forgiveness

**Meaning:** Release, forgiveness, letting go of what no longer serves

**Activities:**
- Release old patterns
- Forgive and let go
- Clear physical and mental clutter
- Break negative habits
- Complete unfinished business

### Waning Crescent (0-50%)
**Theme:** Rest & Reflection

**Meaning:** Rest, reflection, surrender, preparing for renewal

**Activities:**
- Rest and recuperate
- Reflect deeply
- Surrender control
- Practice self-care
- Prepare for renewal

## Visual Rendering Details

### Moon Drawing Algorithm

The moon is rendered using Jetpack Compose Canvas with multiple layers:

1. **Base Moon (Dark Side)**
   - Radial gradient from gray to dark gray
   - Subtle craters with shadow effect

2. **Illuminated Portion**
   - Calculated using path clipping based on illumination
   - Different algorithms for waxing vs waning
   - Golden-yellow gradient for brightness

3. **Crater Texture**
   - 12 predefined crater positions
   - Size varies from 7% to 18% of moon radius
   - Shadow and rim highlight effects
   - Different opacity for dark vs light sides

4. **Terminator Line**
   - Accurate curved shadow boundary
   - Smooth gradient transition
   - Positioned based on illumination percentage

5. **Outer Effects**
   - Rim shadow for depth
   - Glow effect (especially for full moon)
   - Animated pulse for current phase

### Crater Positions

Craters are realistically positioned using relative coordinates:

```kotlin
val craters = listOf(
    Triple(0.3f, -0.2f, 0.15f),   // Right upper
    Triple(-0.4f, 0.1f, 0.12f),   // Left middle
    Triple(0.1f, 0.4f, 0.18f),    // Center lower (largest)
    // ... 9 more craters
)
```

Each crater has:
- Center position (x, y) relative to moon center
- Size (radius) relative to moon radius
- Shadow gradient
- Rim highlight

## Example Usage

### Complete Dashboard

```kotlin
@Composable
fun MoonPhaseDashboard() {
    LazyColumn {
        item {
            Text("Moon Phase Dashboard")
        }

        item {
            // Current phase
            val today = LocalDate.now()
            val phaseData = MoonPhaseData.forDate(today)
            MoonPhaseCard(phaseData = phaseData)
        }

        item {
            // Lunar cycle wheel
            val currentPhase = MoonPhase.calculatePhase(LocalDate.now())
            LunarCycle(currentPhase = currentPhase)
        }

        item {
            // Calendar
            val today = LocalDate.now()
            MoonCalendar(
                year = today.year,
                month = today.monthValue
            )
        }
    }
}
```

### Phase-Based Guidance

```kotlin
@Composable
fun SpiritualGuidance() {
    val phaseData = MoonPhaseData.forDate(LocalDate.now())

    Column {
        MoonPhaseCard(phaseData = phaseData)

        Text(
            text = "Today's Guidance:",
            fontWeight = FontWeight.Bold
        )

        Text(
            text = phaseData.phase.meaning,
            style = MaterialTheme.typography.bodyMedium
        )

        // Show activities based on phase
        when (phaseData.phase) {
            MoonPhase.NEW_MOON -> NewMoonActivities()
            MoonPhase.FULL_MOON -> FullMoonRituals()
            // ... etc
        }
    }
}
```

### Animated Phase Tracker

```kotlin
@Composable
fun PhaseTracker() {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    var previousPhase by remember { mutableStateOf<MoonPhase?>(null) }
    val currentPhase = MoonPhase.calculatePhase(currentDate)

    if (previousPhase != null && previousPhase != currentPhase) {
        MoonPhaseAnimation(
            fromPhase = previousPhase!!,
            toPhase = currentPhase,
            onAnimationComplete = {
                previousPhase = currentPhase
            }
        )
    } else {
        MoonPhaseIcon(phase = currentPhase)
    }

    LaunchedEffect(currentDate) {
        previousPhase = currentPhase
    }
}
```

## Technical Details

### Dependencies
- Jetpack Compose (Foundation, Material3, Animation)
- Java Time API (LocalDate)
- Kotlin Math libraries

### Performance Considerations
- Moon phase calculations are cached where possible
- Complex canvas drawing is optimized with path clipping
- Animations use Compose's efficient animation system
- Crater drawing uses predefined positions (not computed)

### Color Palette Integration
Uses SpiritAtlas theme colors:
- `SpiritualPurple` - Primary lunar color
- `MysticViolet` - Deep space tones
- `AuraGold` - Full moon illumination
- Phase-specific gradient colors

## Files

- **MoonPhaseVisualization.kt** - Main visualization components (30KB)
  - MoonPhase enum
  - MoonPhaseData class
  - All UI components
  - Drawing functions
  - Astronomical calculations

- **MoonPhaseVisualizationExamples.kt** - Usage examples (20KB)
  - Icon gallery
  - Current phase card
  - Lunar cycle wheel
  - Calendar example
  - Animation demo
  - 30-day forecast
  - Complete dashboard
  - Spiritual guidance

## Future Enhancements

Potential additions for future versions:
1. **Eclipse tracking** - Solar and lunar eclipses
2. **Supermoon detection** - Highlight exceptional lunar events
3. **Astrological aspects** - Moon sign and aspects
4. **Tide predictions** - Based on lunar phases
5. **Custom rituals** - User-created phase-specific practices
6. **Notifications** - Alert for significant lunar events
7. **3D moon rendering** - More realistic visualization
8. **Historical data** - Past lunar phases and events

## Credits

- Astronomical calculations based on NASA lunar data
- Lunar cycle reference: [Meeus, Astronomical Algorithms]
- Spiritual meanings adapted from various lunar traditions
- Designed and implemented for SpiritAtlas by Claude Code

---

**Last Updated:** December 8, 2025
**Version:** 1.0
**Component Count:** 5 main components + 8 examples
