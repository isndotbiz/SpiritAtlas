# Human Design Bodygraph - Quick Reference

## File Locations

```
core/ui/src/main/java/com/spiritatlas/core/ui/visualization/
├── BodygraphVisualization.kt      # Main component (819 lines)
├── BodygraphExample.kt             # Usage examples
└── README.md                       # Complete documentation

docs/
└── BODYGRAPH_VISUALIZATION.md      # Detailed guide

tests/
└── core/ui/src/test/.../BodygraphVisualizationTest.kt
```

## 60-Second Integration

```kotlin
// 1. Import
import com.spiritatlas.core.ui.visualization.*

// 2. Create state
val state = BodygraphState(
    definedCenters = setOf(Center.SACRAL, Center.THROAT),
    definedGates = setOf(2, 14, 20, 34),
    definedChannels = setOf(
        Channel(2, 14, Center.G to Center.SACRAL)
    ),
    type = HumanDesignType.GENERATOR,
    authority = Authority.SACRAL,
    profile = "3/5"
)

// 3. Display
BodygraphChart(state = state)
```

## Key Components

| Component | Purpose | Parameters |
|-----------|---------|------------|
| `BodygraphChart` | Main visualization | `state`, `onCenterTap`, `animated` |
| `BodygraphLegend` | Type/Authority display | `type`, `authority`, `profile` |
| `CenterDetailCard` | Center information | `center`, `isDefined`, `onDismiss` |

## Nine Centers

| Center | Shape | Category | Color | Position |
|--------|-------|----------|-------|----------|
| Head | △ | Pressure | Brown | Top |
| Ajna | ▽ | Awareness | Green | Upper |
| Throat | ▢ | Expression | Purple | Upper-mid |
| G | ◇ | Identity | Gold | Center |
| Heart | △ small | Motor | Red | Left-mid |
| Sacral | ▢ | Motor | Red | Lower-mid |
| Solar Plexus | △ | Awareness | Green | Right-mid |
| Spleen | ▽ | Awareness | Green | Left-mid |
| Root | ▢ | Pressure | Brown | Bottom |

## Five Types

1. **Manifestor** - Initiator (8% of population)
2. **Generator** - Builder (37%)
3. **Manifesting Generator** - Multi-passionate Builder (33%)
4. **Projector** - Guide (21%)
5. **Reflector** - Mirror (1%)

## Seven Authorities

1. **Emotional** - Wait for clarity
2. **Sacral** - Gut response
3. **Splenic** - Intuition
4. **Ego** - Heart's desires
5. **Self-Projected** - What you say
6. **Mental** - Talk through
7. **Lunar** - 28-day cycle

## Common Patterns

### Basic Display
```kotlin
BodygraphChart(state = bodygraphState)
```

### With Interaction
```kotlin
var selectedCenter by remember { mutableStateOf<Center?>(null) }

BodygraphChart(
    state = bodygraphState,
    onCenterTap = { selectedCenter = it }
)
```

### Without Animation
```kotlin
BodygraphChart(
    state = bodygraphState,
    animated = false
)
```

### Without Legend
```kotlin
BodygraphChart(
    state = bodygraphState,
    showLabels = false
)
```

## Sample States

### Generator Example
```kotlin
getSampleBodygraphState() // Pre-built sample
```

### Custom State
```kotlin
BodygraphState(
    definedCenters = setOf(
        Center.SACRAL,
        Center.THROAT,
        Center.G,
        Center.SOLAR_PLEXUS
    ),
    definedGates = setOf(2, 14, 20, 34, 36, 37, 40, 49),
    definedChannels = setOf(
        Channel(2, 14, Center.G to Center.SACRAL, "Beat"),
        Channel(34, 20, Center.SACRAL to Center.THROAT, "Charisma"),
        Channel(40, 37, Center.HEART to Center.SOLAR_PLEXUS, "Community")
    ),
    type = HumanDesignType.GENERATOR,
    authority = Authority.EMOTIONAL,
    profile = "3/5",
    incarnationCross = "Right Angle Cross of Planning"
)
```

### Reflector (All Undefined)
```kotlin
BodygraphState(
    definedCenters = emptySet(),
    definedGates = emptySet(),
    definedChannels = emptySet(),
    type = HumanDesignType.REFLECTOR,
    authority = Authority.LUNAR,
    profile = "6/2"
)
```

## Channel Reference (36 Total)

### Major Channels

| Gates | Centers | Name | Keynote |
|-------|---------|------|---------|
| 2-14 | G → Sacral | Beat | Direction |
| 34-20 | Sacral → Throat | Charisma | Power |
| 31-7 | Throat → G | Leadership | Alpha |
| 10-34 | G → Sacral | Exploration | Self-empowerment |
| 59-6 | Sacral → Solar Plexus | Intimacy | Reproduction |
| 27-50 | Sacral → Spleen | Preservation | Caring |

See `Channel.ALL_CHANNELS` for complete list.

## Customization

### Colors
```kotlin
// In getCenterColor() function
CenterCategory.MOTOR -> Color(0xFFE74C3C)      // Red
CenterCategory.AWARENESS -> Color(0xFF2ECC71)   // Green
CenterCategory.PRESSURE -> Color(0xFF8B4513)    // Brown
CenterCategory.IDENTITY -> Color(0xFFF39C12)    // Gold
CenterCategory.EXPRESSION -> Color(0xFF9B59B6)  // Purple
```

### Animation Speed
```kotlin
// In infiniteTransition animation spec
tween(2000, easing = LinearEasing) // 2000ms = 2 seconds
```

## Testing

### Run Unit Tests
```bash
./gradlew :core:ui:test --tests BodygraphVisualizationTest
```

### Test Coverage
```bash
./gradlew :core:ui:jacocoTestReport
```

### Preview in Android Studio
```kotlin
@Preview
@Composable
fun BodygraphPreview() {
    BodygraphChart(state = getSampleBodygraphState())
}
```

## Performance Tips

1. **Disable animations on low-end devices**
   ```kotlin
   BodygraphChart(state = state, animated = false)
   ```

2. **Use remember for state**
   ```kotlin
   val state = remember { calculateBodygraphState() }
   ```

3. **Lazy load channel data**
   ```kotlin
   val channels = remember { Channel.ALL_CHANNELS.filter { /* ... */ } }
   ```

## Common Issues

### Centers not visible
- Check `definedCenters` contains valid Center enum values
- Verify Canvas has sufficient size (use `Modifier.aspectRatio(0.5f)`)

### Channels not displaying
- Ensure channels reference defined gates
- Verify both connecting centers exist

### Touch not working
- Check parent doesn't consume touch events
- Verify `onCenterTap` callback is provided

### Colors wrong
- Check theme colors in `core/ui/theme/Color.kt`
- Verify using dark background

## Resources

- **Full Documentation**: `docs/BODYGRAPH_VISUALIZATION.md`
- **Examples**: `BodygraphExample.kt`
- **Tests**: `BodygraphVisualizationTest.kt`
- **Package README**: `visualization/README.md`

## Support

For questions or issues:
1. Check examples in `BodygraphExample.kt`
2. Review tests for usage patterns
3. See full documentation in `docs/`
4. Refer to Human Design system resources

---

**Last Updated**: December 2024
**Component Version**: 1.0.0
**Minimum API Level**: 21
**Compose Version**: Material 3
