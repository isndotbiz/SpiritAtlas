# Moon Phase Visualization - Quick Start Guide

## Installation

The Moon Phase Visualization system is already included in the SpiritAtlas core UI module. No additional dependencies are required.

**Location:** `/core/ui/src/main/java/com/spiritatlas/core/ui/visualization/`

## Basic Usage

### 1. Show Current Moon Phase

```kotlin
import com.spiritatlas.core.ui.visualization.*
import java.time.LocalDate

@Composable
fun MyScreen() {
    // Get today's moon phase
    val today = LocalDate.now()
    val phaseData = MoonPhaseData.forDate(today)

    // Display the phase card
    MoonPhaseCard(
        phaseData = phaseData,
        showFullMeaning = true
    )
}
```

### 2. Display Moon Phase Icon

```kotlin
@Composable
fun MoonIcon() {
    val currentPhase = MoonPhase.calculatePhase(LocalDate.now())

    MoonPhaseIcon(
        phase = currentPhase,
        size = 120f,
        showGlow = true,
        animated = true
    )
}
```

### 3. Show Lunar Cycle Wheel

```kotlin
@Composable
fun LunarWheel() {
    val currentPhase = MoonPhase.calculatePhase(LocalDate.now())

    LunarCycle(
        currentPhase = currentPhase,
        onPhaseSelected = { phase ->
            // Handle phase selection
            println("User selected: ${phase.name}")
        }
    )
}
```

### 4. Display Monthly Calendar

```kotlin
@Composable
fun MonthlyMoonCalendar() {
    val today = LocalDate.now()

    MoonCalendar(
        year = today.year,
        month = today.monthValue,
        onDaySelected = { date, phaseData ->
            // Show details for selected day
            println("${date}: ${phaseData.phase.name}")
        }
    )
}
```

### 5. Animate Phase Transition

```kotlin
@Composable
fun PhaseTransition() {
    var currentPhase by remember { mutableStateOf(MoonPhase.NEW_MOON) }

    Column {
        MoonPhaseAnimation(
            fromPhase = MoonPhase.NEW_MOON,
            toPhase = MoonPhase.FULL_MOON,
            durationMillis = 3000,
            onAnimationComplete = {
                currentPhase = MoonPhase.FULL_MOON
            }
        )

        Button(onClick = {
            // Trigger animation to next phase
        }) {
            Text("Next Phase")
        }
    }
}
```

## Complete Examples

### Spiritual Guidance Screen

```kotlin
@Composable
fun MoonGuidanceScreen() {
    val today = LocalDate.now()
    val phaseData = MoonPhaseData.forDate(today)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Today's Lunar Guidance",
                style = MaterialTheme.typography.headlineMedium,
                color = SpiritualPurple
            )
        }

        item {
            MoonPhaseCard(
                phaseData = phaseData,
                showFullMeaning = true
            )
        }

        item {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recommended Activities",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Show phase-specific activities
                    Text(phaseData.phase.meaning)
                }
            }
        }

        item {
            Text(
                text = "Lunar Cycle",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            LunarCycle(currentPhase = phaseData.phase)
        }
    }
}
```

### Moon Tracker Widget

```kotlin
@Composable
fun MoonTrackerWidget(modifier: Modifier = Modifier) {
    val today = LocalDate.now()
    val phaseData = MoonPhaseData.forDate(today)

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = phaseData.phase.color.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoonPhaseIcon(
                phase = phaseData.phase,
                size = 60f,
                illumination = phaseData.illumination
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = phaseData.phase.name
                        .replace("_", " ")
                        .lowercase()
                        .replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = phaseData.phase.color
                )

                Text(
                    text = "${(phaseData.illumination * 100).toInt()}% illuminated",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Text(
                    text = "Day ${phaseData.dayInCycle.toInt()} of 29",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
```

### Full Moon Dashboard

```kotlin
@Composable
fun MoonDashboard() {
    val today = LocalDate.now()
    val phaseData = MoonPhaseData.forDate(today)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Moon Phases",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple
                )

                MoonPhaseIcon(
                    phase = phaseData.phase,
                    size = 50f,
                    animated = true
                )
            }
        }

        // Current Phase
        item {
            MoonPhaseCard(
                phaseData = phaseData,
                showFullMeaning = true
            )
        }

        // Lunar Cycle
        item {
            Text(
                text = "Lunar Cycle",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            LunarCycle(currentPhase = phaseData.phase)
        }

        // Monthly Calendar
        item {
            Text(
                text = "This Month",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            MoonCalendar(
                year = today.year,
                month = today.monthValue
            )
        }

        // Upcoming Changes
        item {
            Text(
                text = "Next 7 Days",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                (0..6).forEach { dayOffset ->
                    val futureDate = today.plusDays(dayOffset.toLong())
                    val futurePhaseData = MoonPhaseData.forDate(futureDate)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (dayOffset == 0)
                                SpiritualPurple.copy(alpha = 0.1f)
                            else
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MoonPhaseIcon(
                                phase = futurePhaseData.phase,
                                size = 40f,
                                showGlow = false,
                                illumination = futurePhaseData.illumination,
                                animated = false
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = futureDate.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (dayOffset == 0) FontWeight.Bold else FontWeight.Normal
                                )
                                Text(
                                    text = futurePhaseData.phase.spiritualTheme,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = futurePhaseData.phase.color
                                )
                            }

                            Text(
                                text = "${(futurePhaseData.illumination * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
```

## Calculations API

### Get Current Phase

```kotlin
val today = LocalDate.now()
val phase = MoonPhase.calculatePhase(today)
```

### Get Phase for Any Date

```kotlin
val date = LocalDate.of(2024, 12, 25)
val phase = MoonPhase.calculatePhase(date)
```

### Get Illumination Percentage

```kotlin
val date = LocalDate.now()
val illumination = MoonPhase.calculateIllumination(date)
println("Moon is ${(illumination * 100).toInt()}% illuminated")
```

### Get Complete Phase Data

```kotlin
val date = LocalDate.now()
val phaseData = MoonPhaseData.forDate(date)

// Access properties
println("Phase: ${phaseData.phase.name}")
println("Illumination: ${phaseData.illumination}")
println("Day in cycle: ${phaseData.dayInCycle}")
println("Is waxing: ${phaseData.isWaxing}")
```

### Find Next Full Moon

```kotlin
fun findNextFullMoon(startDate: LocalDate): LocalDate {
    var date = startDate
    while (date.isBefore(startDate.plusMonths(2))) {
        val phase = MoonPhase.calculatePhase(date)
        if (phase == MoonPhase.FULL_MOON) {
            return date
        }
        date = date.plusDays(1)
    }
    return startDate // Fallback
}
```

### Get All Phases in Month

```kotlin
fun getPhasesInMonth(year: Int, month: Int): List<Pair<LocalDate, MoonPhase>> {
    val startDate = LocalDate.of(year, month, 1)
    val endDate = startDate.plusMonths(1)

    val phases = mutableListOf<Pair<LocalDate, MoonPhase>>()
    var currentDate = startDate

    while (currentDate.isBefore(endDate)) {
        val phase = MoonPhase.calculatePhase(currentDate)
        phases.add(currentDate to phase)
        currentDate = currentDate.plusDays(1)
    }

    return phases
}
```

## Customization

### Custom Icon Size

```kotlin
MoonPhaseIcon(
    phase = MoonPhase.FULL_MOON,
    size = 150f, // Custom size in dp
    showGlow = true
)
```

### Custom Colors

```kotlin
// Access phase color
val phaseColor = phaseData.phase.color

// Use in custom UI
Box(
    modifier = Modifier.background(phaseColor.copy(alpha = 0.2f))
) {
    // Your content
}
```

### Disable Animations

```kotlin
MoonPhaseIcon(
    phase = phase,
    animated = false // Disable pulsing animation
)
```

### Hide Glow Effect

```kotlin
MoonPhaseIcon(
    phase = phase,
    showGlow = false // Remove glow for minimal look
)
```

## Testing

Run the included unit tests:

```bash
./gradlew :core:ui:test --tests "*MoonPhaseVisualizationTest"
```

Tests cover:
- Phase calculation accuracy
- Illumination percentage ranges
- Waxing vs waning determination
- Date consistency
- All 8 phases validation

## Performance Tips

1. **Cache phase calculations** for the same date
2. **Use remember** in Composables to avoid recalculation
3. **Disable animations** when showing many icons (e.g., in calendar)
4. **Use LazyColumn** for scrollable lists of phases

Example with caching:

```kotlin
@Composable
fun CachedMoonPhase() {
    val today = LocalDate.now()
    val phaseData = remember(today) {
        MoonPhaseData.forDate(today)
    }

    MoonPhaseCard(phaseData = phaseData)
}
```

## Common Patterns

### Notification for Full Moon

```kotlin
fun checkForFullMoon(context: Context) {
    val today = LocalDate.now()
    val phase = MoonPhase.calculatePhase(today)

    if (phase == MoonPhase.FULL_MOON) {
        // Show notification
        showFullMoonNotification(context)
    }
}
```

### Track Phase Changes

```kotlin
@Composable
fun PhaseChangeTracker() {
    var lastPhase by remember { mutableStateOf<MoonPhase?>(null) }
    val currentPhase = MoonPhase.calculatePhase(LocalDate.now())

    LaunchedEffect(currentPhase) {
        if (lastPhase != null && lastPhase != currentPhase) {
            // Phase changed!
            println("Phase changed from $lastPhase to $currentPhase")
        }
        lastPhase = currentPhase
    }

    MoonPhaseIcon(phase = currentPhase)
}
```

### Compare Two Dates

```kotlin
fun compareMoonPhases(date1: LocalDate, date2: LocalDate) {
    val phase1 = MoonPhase.calculatePhase(date1)
    val phase2 = MoonPhase.calculatePhase(date2)

    println("$date1: $phase1")
    println("$date2: $phase2")

    if (phase1 == phase2) {
        println("Same phase!")
    }
}
```

## Resources

- **Main Component File:** `/core/ui/src/main/java/com/spiritatlas/core/ui/visualization/MoonPhaseVisualization.kt`
- **Examples File:** `/core/ui/src/main/java/com/spiritatlas/core/ui/visualization/MoonPhaseVisualizationExamples.kt`
- **Tests:** `/core/ui/src/test/java/com/spiritatlas/core/ui/visualization/MoonPhaseVisualizationTest.kt`
- **Full Documentation:** `/docs/MoonPhaseVisualization.md`

## Support

For issues or questions:
1. Check the examples file for usage patterns
2. Review the comprehensive documentation
3. Run the unit tests to verify calculations
4. Refer to other visualization components (Chakra, Zodiac) for similar patterns

---

**Quick Reference:**
- 8 moon phases with spiritual meanings
- 29.53 day lunar cycle (astronomically accurate)
- 5 main UI components
- 8 ready-to-use examples
- Full test coverage
- Complete documentation

Happy moon tracking!
