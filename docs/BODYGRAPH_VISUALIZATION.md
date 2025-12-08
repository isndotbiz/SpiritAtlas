# Human Design Bodygraph Visualization

## Overview

The Bodygraph Visualization component provides a beautiful, interactive rendering of the Human Design bodygraph chart. This component visualizes the nine centers, 64 gates, and 36 channels that make up a person's unique energetic blueprint.

## File Location

```
core/ui/src/main/java/com/spiritatlas/core/ui/visualization/
├── BodygraphVisualization.kt  # Main visualization component
└── BodygraphExample.kt         # Usage examples and previews
```

## Features

### Visual Elements

1. **Nine Centers** - Geometric shapes representing energy centers:
   - Head (triangle pointing up) - Pressure center
   - Ajna (triangle pointing down) - Awareness center
   - Throat (square) - Expression center
   - G/Identity (diamond) - Identity center
   - Heart/Will (small triangle) - Motor center
   - Sacral (square) - Motor center
   - Solar Plexus (triangle up) - Awareness center
   - Spleen (triangle down) - Awareness center
   - Root (square) - Pressure center

2. **64 Gates** - Small circles positioned along channels

3. **36 Channels** - Lines connecting centers with animated energy flow

### Interactive Features

- **Touch to Explore**: Tap any center to see detailed information
- **Animated Energy Flow**: Flowing energy visualization through defined channels
- **Glow Effects**: Defined centers have ethereal glow effects
- **Smooth Transitions**: Animated state changes when showing different profiles

### Color Scheme

The visualization uses a sophisticated color system based on center categories:

- **Motor Centers** (Sacral, Heart, Root, Solar Plexus): Red (#E74C3C)
- **Awareness Centers** (Ajna, Spleen, Solar Plexus): Green (#2ECC71)
- **Pressure Centers** (Head, Root): Brown (#8B4513)
- **Identity Center** (G): Gold (#F39C12)
- **Expression Center** (Throat): Purple (#9B59B6)
- **Undefined Centers**: White with subtle border

## Usage

### Basic Usage

```kotlin
import com.spiritatlas.core.ui.visualization.*

@Composable
fun MyScreen() {
    val bodygraphState = BodygraphState(
        definedCenters = setOf(
            Center.SACRAL,
            Center.THROAT,
            Center.G
        ),
        definedGates = setOf(1, 2, 3, 14, 20, 34),
        definedChannels = setOf(
            Channel(2, 14, Center.G to Center.SACRAL),
            Channel(34, 20, Center.SACRAL to Center.THROAT)
        ),
        type = HumanDesignType.GENERATOR,
        authority = Authority.SACRAL,
        profile = "3/5"
    )

    BodygraphChart(
        state = bodygraphState,
        onCenterTap = { center ->
            // Handle center tap
            println("Tapped: ${center.displayName}")
        }
    )
}
```

### With Controls

```kotlin
@Composable
fun BodygraphScreen() {
    var showAnimation by remember { mutableStateOf(true) }

    Column {
        // Control panel
        Switch(
            checked = showAnimation,
            onCheckedChange = { showAnimation = it }
        )

        // Bodygraph
        BodygraphChart(
            state = bodygraphState,
            animated = showAnimation,
            showLabels = true,
            onCenterTap = { center ->
                // Show dialog or navigate to detail screen
            }
        )
    }
}
```

### Creating Sample Data

Use the provided helper function for testing:

```kotlin
val sampleState = getSampleBodygraphState()

BodygraphChart(state = sampleState)
```

## Data Models

### BodygraphState

Main state container for bodygraph data:

```kotlin
data class BodygraphState(
    val definedCenters: Set<Center>,
    val definedGates: Set<Int>,
    val definedChannels: Set<Channel>,
    val type: HumanDesignType,
    val authority: Authority,
    val profile: String,
    val incarnationCross: String? = null
)
```

### Center Enum

Nine centers with positions and categories:

```kotlin
enum class Center {
    HEAD,           // Inspiration & Mental Pressure
    AJNA,           // Mental Awareness & Conceptualization
    THROAT,         // Communication & Manifestation
    G,              // Identity & Direction
    HEART,          // Willpower & Self-Worth
    SACRAL,         // Life Force & Response
    SOLAR_PLEXUS,   // Emotional Awareness
    SPLEEN,         // Instinctive Awareness
    ROOT            // Adrenaline & Pressure to Act
}
```

### HumanDesignType Enum

Five Human Design types:

```kotlin
enum class HumanDesignType {
    MANIFESTOR,              // Initiator
    GENERATOR,               // Builder
    MANIFESTING_GENERATOR,   // Multi-passionate Builder
    PROJECTOR,               // Guide
    REFLECTOR                // Mirror
}
```

### Authority Enum

Seven decision-making authorities:

```kotlin
enum class Authority {
    EMOTIONAL,         // Wait for emotional clarity
    SACRAL,           // Trust gut response
    SPLENIC,          // Trust intuition
    EGO,              // Follow heart's desires
    SELF_PROJECTED,   // Listen to what you say
    MENTAL,           // Talk it through
    LUNAR             // Wait through lunar cycle
}
```

### Channel Data Class

Represents connections between centers:

```kotlin
data class Channel(
    val gate1: Int,
    val gate2: Int,
    val connectingCenters: Pair<Center, Center>,
    val name: String = ""
)
```

## Composables Reference

### BodygraphChart

Main visualization component.

**Parameters:**
- `state: BodygraphState` - The bodygraph data to visualize
- `modifier: Modifier` - Standard Compose modifier
- `showLabels: Boolean` - Whether to show the legend (default: true)
- `onCenterTap: ((Center) -> Unit)?` - Callback when a center is tapped
- `animated: Boolean` - Enable/disable energy flow animation (default: true)

**Example:**
```kotlin
BodygraphChart(
    state = bodygraphState,
    showLabels = true,
    onCenterTap = { center ->
        // Handle tap
    },
    animated = true
)
```

### BodygraphLegend

Display type, authority, and profile information.

**Parameters:**
- `type: HumanDesignType` - The person's type
- `authority: Authority` - Decision-making authority
- `profile: String` - Profile notation (e.g., "3/5")
- `incarnationCross: String?` - Optional incarnation cross
- `modifier: Modifier` - Standard modifier

### CenterDetailCard

Detail card shown when a center is tapped.

**Parameters:**
- `center: Center` - The selected center
- `isDefined: Boolean` - Whether the center is defined
- `onDismiss: () -> Unit` - Callback to close the card
- `modifier: Modifier` - Standard modifier

## Customization

### Color Customization

To customize colors, modify the `getCenterColor` function:

```kotlin
private fun getCenterColor(center: Center): Color {
    return when (center.category) {
        CenterCategory.MOTOR -> Color(0xFFE74C3C)      // Red
        CenterCategory.AWARENESS -> Color(0xFF2ECC71)   // Green
        CenterCategory.PRESSURE -> Color(0xFF8B4513)    // Brown
        CenterCategory.IDENTITY -> Color(0xFFF39C12)    // Gold
        CenterCategory.EXPRESSION -> Color(0xFF9B59B6)  // Purple
    }
}
```

### Animation Customization

Adjust animation speed in the `BodygraphChart` composable:

```kotlin
val energyFlow by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
        animation = tween(2000, easing = LinearEasing), // Change duration here
        repeatMode = RepeatMode.Restart
    )
)
```

## Integration with Human Design Calculator

To integrate with the existing Human Design calculator:

```kotlin
import com.spiritatlas.core.humandesign.EnergyProfileCalculator

@Composable
fun UserBodygraphScreen(birthDate: LocalDate) {
    val energyType = EnergyProfileCalculator.calculateEnergyType(
        birthDate.dayOfMonth,
        birthDate.monthValue,
        birthDate.year
    )

    val profile = EnergyProfileCalculator.calculateProfile(
        birthDate.dayOfMonth,
        birthDate.monthValue
    )

    // Map to HumanDesignType
    val hdType = when (energyType) {
        EnergyProfileCalculator.EnergyType.BUILDER -> HumanDesignType.GENERATOR
        EnergyProfileCalculator.EnergyType.INITIATOR -> HumanDesignType.MANIFESTOR
        EnergyProfileCalculator.EnergyType.GUIDE -> HumanDesignType.PROJECTOR
        else -> HumanDesignType.GENERATOR
    }

    val bodygraphState = BodygraphState(
        // Calculate or fetch user's specific data
        type = hdType,
        profile = profile.notation
    )

    BodygraphChart(state = bodygraphState)
}
```

## Performance Considerations

1. **Canvas Drawing**: All shapes are drawn directly on Canvas for optimal performance
2. **Animation**: Uses Compose animation APIs for smooth 60fps rendering
3. **State Management**: Remember to use `remember` and `mutableStateOf` appropriately
4. **Lazy Loading**: Consider lazy loading gate and channel data for large datasets

## Best Practices

1. **Responsive Layout**: The bodygraph uses `aspectRatio(0.5f)` to maintain proper proportions
2. **Touch Targets**: Center tap detection uses 80dp threshold for comfortable tapping
3. **Accessibility**: Consider adding content descriptions for screen readers
4. **Dark Theme**: The visualization is designed for dark backgrounds
5. **Error Handling**: Validate `BodygraphState` data before rendering

## Future Enhancements

Potential areas for expansion:

1. **Complete Gate Mapping**: Add precise positions for all 64 gates
2. **Channel Details**: Tap channels to see detailed information
3. **Transit Overlay**: Show current planetary transits
4. **Comparison Mode**: Display two bodygraphs side-by-side
5. **Export**: Generate shareable images of the bodygraph
6. **Zoom & Pan**: Allow users to zoom into specific areas
7. **Variable Definitions**: Show variables (tone, color, base, etc.)
8. **Profile Lines**: Visual indicators for profile lines

## Troubleshooting

### Centers not displaying correctly
- Verify `definedCenters` set contains valid Center enum values
- Check that colors are properly defined in the theme

### Channels not connecting properly
- Ensure Channel objects have correct gate numbers
- Verify connecting centers match actual channel topology

### Animation stuttering
- Reduce animation complexity if running on lower-end devices
- Set `animated = false` to disable animations

### Touch detection not working
- Ensure parent composable isn't consuming touch events
- Verify the Canvas has sufficient size (check constraints)

## References

- [Human Design System](https://www.jovianarchive.com/)
- [Jetpack Compose Canvas](https://developer.android.com/jetpack/compose/graphics/draw/overview)
- [Compose Animation](https://developer.android.com/jetpack/compose/animation)

## License

This component is part of the SpiritAtlas project and follows the project's license.
