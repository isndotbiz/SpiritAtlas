# Spiritual Pull-to-Refresh Component

A beautiful, custom pull-to-refresh component for SpiritAtlas with celestial and spiritual themed animations.

## Features

- **5 Custom Indicators**: Lotus, Moon Phases, Zodiac Wheel, Constellation, and Chakra
- **Haptic Feedback**: Tactile response at trigger point
- **Smooth Animations**: Spiritual-themed state transitions
- **Material 3 Integration**: Built on Material 3 pull-to-refresh foundation
- **Composable Architecture**: Easy to integrate and customize

## Indicator Types

### 1. Lotus Indicator (Default)
The lotus flower blooms as you pull down, representing spiritual awakening and enlightenment.

**Animation States:**
- **Idle**: Nothing visible
- **Pulling**: Petals spread outward based on pull distance
  - Outer petals (light purple) appear first
  - Inner petals (darker violet) appear at 30% progress
- **Armed**: Full bloom at trigger point
- **Refreshing**: Spinning flower with pulsing glow
- **Complete**: Petals close and fade away

**Visual Elements:**
- 8 outer petals (Spiritual Purple)
- 8 inner petals (Mystic Violet)
- Golden center with white highlight
- Glowing aura when armed/refreshing

### 2. Moon Phase Indicator
Cycles through lunar phases as you pull, representing cycles and transformation.

**Animation States:**
- **Pulling**: Moon grows and cycles through phases based on pull distance
- **Refreshing**: Continuous phase cycling
- **Armed**: Golden glow around moon

**Visual Elements:**
- White moon circle
- Dynamic shadow overlay for phase effect
- Golden aura when armed

### 3. Zodiac Wheel Indicator
A celestial wheel with zodiac markers that spins.

**Animation States:**
- **Pulling**: Wheel appears and rotates based on pull distance
- **Refreshing**: Continuous rotation
- **Armed**: Purple glow around wheel

**Visual Elements:**
- 12 zodiac markers (every 30 degrees)
- Emphasized markers at cardinal points (gold)
- Golden center point
- Purple outer ring

### 4. Constellation Indicator
Stars appear and connect to form a constellation pattern.

**Animation States:**
- **Pulling**: Stars appear one by one as you pull
- **Connecting**: Lines draw between stars
- **Refreshing**: Stars twinkle continuously
- **Armed**: Full constellation with glow

**Visual Elements:**
- 5 stars in diamond formation
- Connecting lines between stars
- Golden star glow
- White star cores
- Purple ambient glow when armed

### 5. Chakra Indicator
Energy centers light up sequentially from bottom to top.

**Animation States:**
- **Pulling**: Chakras activate one by one (1-7 based on progress)
- **Refreshing**: Energy flows through chakras in sequence
- **Armed**: All 7 chakras glowing

**Visual Elements:**
- 7 chakra circles in authentic colors
- Root (red) → Crown (violet)
- White energy flow line when refreshing
- Pulsing glow on active chakras

## Usage

### Basic Implementation

```kotlin
import com.spiritatlas.core.ui.components.SpiritualPullRefresh
import com.spiritatlas.core.ui.components.RefreshIndicatorType

@Composable
fun MyScreen() {
    var isRefreshing by remember { mutableStateOf(false) }

    SpiritualPullRefresh(
        isRefreshing = isRefreshing,
        onRefresh = {
            // Trigger your refresh logic
            viewModel.refresh()
        },
        indicatorType = RefreshIndicatorType.Lotus
    ) {
        // Your scrollable content
        LazyColumn {
            // ... items
        }
    }
}
```

### With ViewModel Integration

```kotlin
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    SpiritualPullRefresh(
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.refreshProfile() },
        indicatorType = RefreshIndicatorType.Chakra
    ) {
        LazyColumn {
            items(uiState.items) { item ->
                ProfileItem(item)
            }
        }
    }
}
```

### Changing Indicator Types

```kotlin
@Composable
fun CustomizableRefresh() {
    var selectedIndicator by remember {
        mutableStateOf(RefreshIndicatorType.Lotus)
    }
    var isRefreshing by remember { mutableStateOf(false) }

    Column {
        // Indicator selector
        Row {
            RefreshIndicatorType.values().forEach { type ->
                Button(onClick = { selectedIndicator = type }) {
                    Text(type.name)
                }
            }
        }

        // Content with selected indicator
        SpiritualPullRefresh(
            isRefreshing = isRefreshing,
            onRefresh = { isRefreshing = true },
            indicatorType = selectedIndicator
        ) {
            // Content
        }
    }
}
```

## API Reference

### SpiritualPullRefresh

```kotlin
@Composable
fun SpiritualPullRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    indicatorType: RefreshIndicatorType = RefreshIndicatorType.Lotus,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
```

**Parameters:**
- `isRefreshing`: Boolean state indicating if refresh is active
- `onRefresh`: Callback invoked when refresh is triggered
- `indicatorType`: Type of spiritual indicator to display
- `modifier`: Modifier for the container
- `content`: Scrollable content (LazyColumn, LazyVerticalGrid, etc.)

### RefreshIndicatorType

```kotlin
enum class RefreshIndicatorType {
    MoonPhase,
    ZodiacWheel,
    Lotus,
    Constellation,
    Chakra
}
```

## Haptic Feedback

The component automatically provides haptic feedback when:
1. Pull distance reaches 100% (trigger point)
2. User releases and refresh begins

**Haptic Type:** `HapticFeedbackType.MEDIUM`

Users can control haptic intensity through the app's haptic settings.

## Customization

### Color Themes

The indicators use colors from the spiritual theme palette:
- `SpiritualPurple` - Main spiritual purple
- `MysticViolet` - Deep mystic violet
- `AuraGold` - Golden aura
- `ChakraColors` - 7 authentic chakra colors
- `CosmicBlue` - Cosmic blue accent

### Animation Timing

Animations use predefined durations from `AnimationDurations`:
- Pull response: Immediate (spring animation)
- Refresh cycle: 2-4 seconds depending on indicator
- Completion: 400ms fade out

### Performance

All indicators are implemented using Compose Canvas for optimal performance:
- No bitmap allocations
- Efficient path drawing
- Hardware-accelerated rendering
- Smooth 60 FPS animations

## Best Practices

### 1. Choose Appropriate Indicator

Match the indicator to your screen's purpose:
- **Lotus**: General spiritual content, meditation, enlightenment features
- **Moon Phases**: Calendar, cycles, time-based features
- **Zodiac Wheel**: Astrology, birth chart, zodiac features
- **Constellation**: Star maps, cosmic features, destiny readings
- **Chakra**: Energy work, healing, spiritual profile features

### 2. Handle Refresh State Properly

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            // Show loading UI if needed
        }
    }

    SpiritualPullRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() }
    ) {
        // Content
    }
}

class MyViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // Fetch data
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
```

### 3. Ensure Content is Scrollable

The pull-to-refresh only works with scrollable content:

```kotlin
// ✅ Works
SpiritualPullRefresh(...) {
    LazyColumn { ... }
}

// ✅ Works
SpiritualPullRefresh(...) {
    LazyVerticalGrid { ... }
}

// ❌ Won't work (not scrollable)
SpiritualPullRefresh(...) {
    Column { ... }
}
```

### 4. Combine with Error States

```kotlin
@Composable
fun RobustScreen(viewModel: MyViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Error -> ErrorScreen(
            onRetry = { viewModel.refresh() }
        )
        else -> SpiritualPullRefresh(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.refresh() }
        ) {
            ContentList(uiState.data)
        }
    }
}
```

## Examples

See `SpiritualPullRefreshExample.kt` for:
- Complete demo screen with all 5 indicators
- Interactive indicator selector
- Simulated refresh with delay
- Best practices implementation

## Technical Details

### Dependencies

- `androidx.compose.material3:material3` (pull-to-refresh foundation)
- `com.spiritatlas.core.ui.haptics` (haptic feedback)
- `com.spiritatlas.core.ui.animation` (animation utilities)
- `com.spiritatlas.core.ui.theme` (color palette)

### Implementation Notes

1. **Built on Material 3**: Uses `PullToRefreshState` and `nestedScrollConnection` from Material 3
2. **Custom Indicators**: All indicators are custom Canvas drawings, not default Material components
3. **Haptic Integration**: Automatically detects trigger point and provides feedback
4. **State Management**: Handles all animation states internally
5. **Performance**: Canvas-based rendering for smooth 60 FPS animations

### State Flow

```
Idle
  ↓ (user pulls down)
Pulling (progress 0.0 → 1.0)
  ↓ (progress >= 1.0)
Armed (visual feedback, haptic triggers)
  ↓ (user releases)
Refreshing (continuous animation)
  ↓ (isRefreshing = false)
Complete (fade out)
  ↓
Idle
```

## Accessibility

The component supports:
- **Haptic Feedback**: Respects user haptic settings
- **Semantic Scrolling**: Works with screen readers
- **Visual Indicators**: Clear visual progress feedback
- **Alternative Refresh**: Can be triggered programmatically

## Migration from Material Pull-to-Refresh

If you're currently using standard Material pull-to-refresh:

```kotlin
// Before
PullRefreshIndicator(
    refreshing = isRefreshing,
    state = pullRefreshState
)

// After
SpiritualPullRefresh(
    isRefreshing = isRefreshing,
    onRefresh = { /* refresh */ },
    indicatorType = RefreshIndicatorType.Lotus
) {
    // content
}
```

## Troubleshooting

### Indicator Not Showing
- Ensure content is scrollable
- Check that `isRefreshing` state is properly managed
- Verify content is not blocking the indicator area

### Haptic Not Working
- Check device haptic settings
- Ensure app has vibration permissions
- Test on physical device (emulator may not support haptics)

### Animation Stuttering
- Reduce number of list items rendered
- Use `key` parameter in LazyColumn items
- Check for heavy computation in composition

## Contributing

When adding new indicators:
1. Create a new `@Composable` function following the naming pattern
2. Accept `progress`, `isRefreshing`, and `isArmed` parameters
3. Use Canvas for drawing
4. Follow spiritual theme color palette
5. Add entry to `RefreshIndicatorType` enum
6. Update documentation with visual description
7. Add example to demo screen

## License

Part of SpiritAtlas - Spiritual companion app
