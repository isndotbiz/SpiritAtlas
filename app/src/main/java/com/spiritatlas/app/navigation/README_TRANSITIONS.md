# SpiritAtlas Navigation Transitions

Beautiful, spiritual navigation transitions for SpiritAtlas using Jetpack Compose Navigation.

## Overview

The SpiritAtlas navigation system provides smooth, flowing transitions that create a mystical, contemplative user experience. All transitions are designed with longer durations (400-800ms) and emphasis on scale + fade combinations to evoke a sense of consciousness expanding and contracting.

## Quick Start

### Basic Usage

```kotlin
NavHost(navController = navController, startDestination = "home") {
    // Simple transition using predefined type
    spiritualComposable(
        route = "home",
        transitionType = TransitionType.FADE_THROUGH
    ) {
        HomeScreen()
    }
}
```

### Navigation

```kotlin
// Standard navigation with transition
navController.navigateWithTransition(
    route = "profile/123",
    transitionType = TransitionType.ZOOM_IN
)

// Semantic helpers
navController.navigateToDetail("details/456")      // Zoom in effect
navController.navigateToModal("settings")          // Slide up effect
navController.navigateToRevelation("compatibility") // Circular reveal
navController.navigateToTopLevel("home")           // Cross fade for tabs
```

## Available Transitions

### 1. SHARED_ELEMENT
**Purpose:** Profile cards expanding to detail screens
**Effect:** Seamless morphing with scale + fade
**Duration:** 450ms
**Best for:** List items to detail views

```kotlin
spiritualComposable(
    route = "profile/{id}",
    transitionType = TransitionType.SHARED_ELEMENT
)
```

### 2. FADE_THROUGH
**Purpose:** Material motion pattern for peer-level navigation
**Effect:** Fade out completely, then fade in new screen
**Duration:** 450ms (150ms fade out, 300ms fade in)
**Best for:** Navigating between main sections

```kotlin
spiritualComposable(
    route = "explore",
    transitionType = TransitionType.FADE_THROUGH
)
```

### 3. SLIDE_UP
**Purpose:** Bottom sheets and modal presentations
**Effect:** Slides in from bottom with fade
**Duration:** 450ms
**Best for:** Modals, settings, overlays

```kotlin
navController.navigateToModal("settings")
```

### 4. SLIDE_HORIZONTAL
**Purpose:** Forward/back navigation
**Effect:** Horizontal slide with parallax (exits slower)
**Duration:** 450ms
**Best for:** Wizards, comparisons, sequential flows

```kotlin
spiritualComposable(
    route = "comparison",
    transitionType = TransitionType.SLIDE_HORIZONTAL
)
```

### 5. ZOOM_IN
**Purpose:** Detail views that expand from center
**Effect:** Scales from 50% to 100% with fade
**Duration:** 600ms
**Best for:** Focusing on specific content, enrichment results

```kotlin
navController.navigateToDetail("enrichment/123")
```

### 6. CROSS_FADE
**Purpose:** Tab switches and peaceful transitions
**Effect:** Smooth blending without spatial movement
**Duration:** 450ms
**Best for:** Bottom navigation, tabs, non-hierarchical navigation

```kotlin
navController.navigateToTopLevel("explore")
```

### 7. CIRCULAR_REVEAL
**Purpose:** Dramatic transitions for significant moments
**Effect:** Expands from center point (simulated with scale)
**Duration:** 800ms (mystical)
**Best for:** Compatibility results, major revelations, achievements

```kotlin
navController.navigateToRevelation("compatibility_result")
```

### 8. SCALE_FADE
**Purpose:** Mystical floating effect
**Effect:** Gentle scale (75% to 100%) with fade
**Duration:** 600ms
**Best for:** General spiritual transitions, meditation content

```kotlin
spiritualComposable(
    route = "meditation",
    transitionType = TransitionType.SCALE_FADE
)
```

### 9. NONE
**Purpose:** Instant transition
**Effect:** No animation
**Best for:** Accessibility (reduce motion), performance

```kotlin
spiritualComposable(
    route = "heavy_screen",
    transitionType = TransitionType.NONE
)
```

## Predefined App Transitions

Use `SpiritTransitions` object for consistent app-wide transitions:

```kotlin
object SpiritTransitions {
    val homeToFeature = TransitionType.FADE_THROUGH
    val profileListToDetail = TransitionType.SHARED_ELEMENT
    val profileToComparison = TransitionType.SLIDE_HORIZONTAL
    val toCompatibilityResults = TransitionType.CIRCULAR_REVEAL
    val toEnrichmentResults = TransitionType.ZOOM_IN
    val toModal = TransitionType.SLIDE_UP
    val tabSwitch = TransitionType.CROSS_FADE
    val default = TransitionType.FADE_THROUGH
}
```

## Advanced Usage

### Custom Transitions

```kotlin
spiritualComposable(
    route = "custom",
    enterTransition = scaleIn(
        initialScale = 0.8f,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    ) + fadeIn(tween(600)),
    exitTransition = scaleOut(
        targetScale = 1.2f,
        animationSpec = tween(600)
    ) + fadeOut(tween(400))
) {
    CustomScreen()
}
```

### Context-Aware Transitions

```kotlin
val transition = if (isFirstLaunch) {
    TransitionType.CIRCULAR_REVEAL // Dramatic first impression
} else {
    TransitionType.FADE_THROUGH // Standard return
}

spiritualComposable(route = "onboarding", transitionType = transition)
```

### Accessibility Support

```kotlin
val transition = if (reduceMotion) {
    TransitionType.NONE
} else {
    TransitionType.FADE_THROUGH
}
```

### Navigation with Options

```kotlin
navController.navigateWithTransition(
    route = "profile/123",
    transitionType = TransitionType.ZOOM_IN
) {
    launchSingleTop = true
    popUpTo("home") { saveState = true }
}
```

## Animation Durations

```kotlin
object SpiritDurations {
    const val FAST = 300      // Quick transitions
    const val MEDIUM = 450    // Standard transitions
    const val SLOW = 600      // Complex/important screens
    const val MYSTICAL = 800  // Dramatic revelations
}
```

## Easing Curves

```kotlin
object SpiritEasing {
    val Emphasized = FastOutSlowInEasing  // Default - natural flow
    val Gentle = LinearEasing             // Peaceful, constant
    fun mysticalSpring()                  // Bouncy, alive
}
```

## Extension Functions

### NavGraphBuilder Extensions

```kotlin
// With transition type
fun NavGraphBuilder.spiritualComposable(
    route: String,
    transitionType: TransitionType = TransitionType.FADE_THROUGH,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
)

// With explicit transitions
fun NavGraphBuilder.spiritualComposable(
    route: String,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    popEnterTransition: EnterTransition = enterTransition,
    popExitTransition: ExitTransition = exitTransition,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
)
```

### NavController Extensions

```kotlin
// Generic navigation
fun NavController.navigateWithTransition(
    route: String,
    transitionType: TransitionType,
    builder: (NavOptionsBuilder.() -> Unit)? = null
)

// Semantic navigation helpers
fun NavController.navigateToTopLevel(route: String)    // For tabs
fun NavController.navigateToDetail(route: String)      // Zoom in
fun NavController.navigateToModal(route: String)       // Slide up
fun NavController.navigateToRevelation(route: String)  // Circular reveal
```

## Design Principles

### Spiritual Feel
- **Longer durations (400-600ms)**: Creates contemplative, mindful experience
- **Scale + fade combinations**: Evokes consciousness expanding/contracting
- **Smooth easing curves**: Mirrors natural energy flow
- **Purposeful animations**: Each transition has semantic meaning

### Best Practices

1. **Consistency**: Use `SpiritTransitions` for common patterns
2. **Semantics**: Match transition to navigation meaning
3. **Performance**: Use simpler transitions for heavy screens
4. **Accessibility**: Respect reduce motion settings
5. **Context**: Adjust transitions based on user state
6. **Hierarchy**: Different transitions for different levels
7. **Restraint**: Don't overuse dramatic transitions

### Transition Selection Guide

| Navigation Type | Recommended Transition | Why |
|----------------|------------------------|-----|
| List → Detail | SHARED_ELEMENT | Continuous element transformation |
| Main sections | FADE_THROUGH | Peer-level, non-hierarchical |
| Settings/Modals | SLIDE_UP | Clear modal context |
| Wizard steps | SLIDE_HORIZONTAL | Sequential progression |
| Focus content | ZOOM_IN | Draw attention inward |
| Tabs | CROSS_FADE | No spatial hierarchy |
| Big reveals | CIRCULAR_REVEAL | Dramatic, ceremonial |
| Meditation | SCALE_FADE | Mystical, floating |
| Back navigation | Use pop transitions | Reverses enter animation |

## Performance Considerations

### When to Use Lighter Transitions

- Heavy screens with lots of content
- Lists with many items
- Screens with animations already running
- Low-end devices (detect via system info)

```kotlin
val transition = if (isLowEndDevice) {
    TransitionType.CROSS_FADE  // Simpler
} else {
    TransitionType.CIRCULAR_REVEAL  // More dramatic
}
```

### Measuring Performance

Monitor frame rates during transitions:
```kotlin
// In development build
androidx.compose.runtime.LaunchedEffect(Unit) {
    // Monitor composition performance
}
```

## Testing

### Visual Testing
Run app and verify:
- Smooth 60fps animations
- No jarring transitions
- Appropriate timing
- Correct direction (forward/back)

### Accessibility Testing
- Enable "Reduce Motion" in system settings
- Verify TransitionType.NONE works correctly
- Test with TalkBack enabled

### Integration Testing
```kotlin
@Test
fun testNavigationTransition() {
    composeTestRule.setContent {
        val navController = rememberNavController()
        SpiritAtlasNavGraph(navController)
    }

    // Navigate and verify destination
    composeTestRule.runOnUiThread {
        navController.navigate("profile/123")
    }

    composeTestRule.waitForIdle()
    // Assert correct screen is showing
}
```

## Migration Guide

### From Standard NavHost

Before:
```kotlin
composable("profile") {
    ProfileScreen()
}
```

After:
```kotlin
spiritualComposable(
    route = "profile",
    transitionType = SpiritTransitions.default
) {
    ProfileScreen()
}
```

### From navigate() to navigateWithTransition()

Before:
```kotlin
navController.navigate("profile/123")
```

After:
```kotlin
navController.navigateWithTransition(
    route = "profile/123",
    transitionType = TransitionType.ZOOM_IN
)
```

## Troubleshooting

### Transitions feel too slow
Reduce duration: Use `SpiritDurations.FAST` or custom duration

### Transitions feel jarring
Try combinations:
- SCALE_FADE for gentler feel
- CROSS_FADE for minimal motion
- Increase duration for smoother feel

### Performance issues
- Use simpler transitions (CROSS_FADE, FADE_THROUGH)
- Reduce duration
- Disable on low-end devices
- Profile with Layout Inspector

### Back navigation wrong direction
Ensure `popEnterTransition` and `popExitTransition` reverse the forward animation

## Files

- `NavTransitions.kt` - Core transitions and extensions
- `SpiritAtlasNavGraph.kt` - Main app navigation graph with transitions
- `NavTransitionsExamples.kt` - Example usage patterns
- `Screen.kt` - Route definitions
- `MainActivity.kt` - Entry point

## Dependencies

Requires:
- Jetpack Compose Navigation 2.7+
- Compose Animation library (included in BOM)

No additional dependencies needed - uses built-in Compose animation APIs.

## Examples

See `NavTransitionsExamples.kt` for comprehensive examples including:
1. Basic usage
2. Custom transitions
3. NavController extensions
4. Context-aware transitions
5. Transition chains
6. Performance optimization
7. App-specific configs
8. Nested navigation
9. Deep link handling
10. Duration customization
