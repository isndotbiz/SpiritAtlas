# Navigation Transitions - Quick Start Guide

Beautiful, spiritual navigation transitions for SpiritAtlas using Jetpack Compose Navigation.

## Files Created

### Core Implementation
- **`app/src/main/java/com/spiritatlas/app/navigation/NavTransitions.kt`**
  - Core transitions library with 9 predefined transition types
  - NavGraphBuilder and NavController extension functions
  - Spiritual-themed durations and easing curves
  - ~500 lines of comprehensive transition logic

### Navigation Graph
- **`app/src/main/java/com/spiritatlas/app/navigation/SpiritAtlasNavGraph.kt`**
  - Main navigation graph using spiritual transitions
  - All screens configured with appropriate transitions
  - Integration with existing feature modules

### Documentation & Examples
- **`app/src/main/java/com/spiritatlas/app/navigation/NavTransitionsExamples.kt`**
  - 10 comprehensive usage examples
  - Best practices and design patterns
  - Advanced patterns for nested navigation, deep links, etc.

- **`app/src/main/java/com/spiritatlas/app/navigation/README_TRANSITIONS.md`**
  - Complete API documentation
  - Transition selection guide
  - Performance considerations
  - Migration guide

### Preview Tool
- **`app/src/main/java/com/spiritatlas/app/navigation/TransitionPreview.kt`**
  - Interactive preview in Android Studio
  - Visualize all transitions in Design view
  - Multiple preview variants for specific transitions

### Updated Files
- **`app/src/main/java/com/spiritatlas/app/MainActivity.kt`**
  - Updated to use new SpiritAtlasNavGraph
  - Simplified navigation setup

## 9 Transition Types

### 1. SHARED_ELEMENT
Scale + fade morphing effect (450ms) - Perfect for card-to-detail transitions

### 2. FADE_THROUGH
Material Design fade pattern (450ms) - Main section navigation

### 3. SLIDE_UP
Vertical slide from bottom (450ms) - Modals and bottom sheets

### 4. SLIDE_HORIZONTAL
Horizontal slide with parallax (450ms) - Forward/back navigation

### 5. ZOOM_IN
Center expansion focus (600ms) - Detail views and enrichment

### 6. CROSS_FADE
Peaceful blending (450ms) - Tab switches

### 7. CIRCULAR_REVEAL
Dramatic center expansion (800ms) - Compatibility results, revelations

### 8. SCALE_FADE
Mystical floating effect (600ms) - Spiritual content

### 9. NONE
Instant transition - Accessibility, performance

## Quick Start

### 1. Add transition to screen

```kotlin
spiritualComposable(
    route = "profile",
    transitionType = TransitionType.ZOOM_IN
) {
    ProfileScreen()
}
```

### 2. Navigate with transition

```kotlin
navController.navigateToDetail("profile/123")  // Zoom in
navController.navigateToModal("settings")      // Slide up
navController.navigateToRevelation("results")  // Circular reveal
```

### 3. Use predefined app transitions

```kotlin
spiritualComposable(
    route = "profile/{id}",
    transitionType = SpiritTransitions.profileListToDetail
)
```

## Testing

### Visual Testing in Android Studio
1. Open `TransitionPreview.kt`
2. Click "Split" or "Design" view
3. Interact with preview to see all transitions

### Preview Individual Transitions
The file includes `@Preview` annotations for:
- `PreviewFadeThrough` - See fade through in action
- `PreviewZoomIn` - See zoom effect
- `PreviewCircularReveal` - See dramatic reveal

### Run on Device
```bash
./gradlew installDebug
```
Navigate through the app to experience transitions in context.

## Spiritual Design Philosophy

### Longer Durations (400-800ms)
Creates a contemplative, mindful experience rather than rushed feel

### Scale + Fade Combinations
Evokes consciousness expanding and contracting, spiritual transformation

### Smooth Easing (FastOutSlowIn)
Mirrors natural energy flow, not mechanical motion

### Purposeful Animation
Each transition has semantic meaning - not just decoration

### Dramatic Moments
Circular reveal and mystical timing (800ms) for compatibility results and revelations

## Customization

### Adjust Durations
```kotlin
object SpiritDurations {
    const val FAST = 300      // Quick transitions
    const val MEDIUM = 450    // Standard
    const val SLOW = 600      // Important screens
    const val MYSTICAL = 800  // Dramatic revelations
}
```

### Create Custom Transition
```kotlin
spiritualComposable(
    route = "custom",
    enterTransition = scaleIn(initialScale = 0.9f) + fadeIn(),
    exitTransition = scaleOut(targetScale = 1.1f) + fadeOut()
) {
    CustomScreen()
}
```

### Context-Aware Transitions
```kotlin
val transition = if (isFirstLaunch) {
    TransitionType.CIRCULAR_REVEAL  // Dramatic first time
} else {
    TransitionType.FADE_THROUGH      // Standard return
}
```

## Accessibility

### Reduce Motion Support
```kotlin
val transition = if (reduceMotionEnabled) {
    TransitionType.NONE  // Instant
} else {
    TransitionType.FADE_THROUGH  // Animated
}
```

### Implementation
```kotlin
val accessibilityManager = context.getSystemService(AccessibilityManager::class.java)
val reduceMotion = accessibilityManager.isEnabled &&
    Settings.Global.getFloat(
        context.contentResolver,
        Settings.Global.TRANSITION_ANIMATION_SCALE,
        1f
    ) == 0f
```

## Performance

### Monitor Transitions
Use Layout Inspector in Android Studio:
1. Tools → Layout Inspector
2. Navigate during transition
3. Monitor frame rate (should be 60fps)

### Optimize Heavy Screens
```kotlin
val transition = if (isComplexContent) {
    TransitionType.CROSS_FADE  // Simpler
} else {
    TransitionType.CIRCULAR_REVEAL  // More dramatic
}
```

## Predefined App Transitions

```kotlin
object SpiritTransitions {
    val homeToFeature = FADE_THROUGH
    val profileListToDetail = SHARED_ELEMENT
    val profileToComparison = SLIDE_HORIZONTAL
    val toCompatibilityResults = CIRCULAR_REVEAL
    val toEnrichmentResults = ZOOM_IN
    val toModal = SLIDE_UP
    val tabSwitch = CROSS_FADE
    val default = FADE_THROUGH
}
```

## Best Practices

### 1. Consistency
Use `SpiritTransitions` predefined values for common patterns

### 2. Semantics
Match transition to the meaning of navigation:
- List → Detail: SHARED_ELEMENT or ZOOM_IN
- Sibling screens: FADE_THROUGH
- Modals: SLIDE_UP
- Tabs: CROSS_FADE
- Revelations: CIRCULAR_REVEAL

### 3. Performance
- Use simpler transitions (CROSS_FADE) for heavy content
- Monitor frame rates in Layout Inspector
- Provide reduce motion option

### 4. User Experience
- Longer durations for contemplative feel
- Consistent patterns throughout app
- Dramatic transitions used sparingly

### 5. Testing
- Test on actual devices, not just emulator
- Test with reduce motion enabled
- Test all navigation paths

## Troubleshooting

### Transitions Too Slow
Reduce duration or use `SpiritDurations.FAST`

### Transitions Feel Jarring
Try scale + fade combinations (SCALE_FADE) for gentler feel

### Performance Issues
Use simpler transitions (CROSS_FADE) or reduce duration

### Wrong Direction on Back
Ensure popEnterTransition/popExitTransition reverse the forward animation

## Migration from Standard Navigation

### Before
```kotlin
composable("profile") {
    ProfileScreen()
}

navController.navigate("profile/123")
```

### After
```kotlin
spiritualComposable(
    route = "profile",
    transitionType = SpiritTransitions.profileListToDetail
) {
    ProfileScreen()
}

navController.navigateToDetail("profile/123")
```

## Next Steps

1. **Review Examples**: See `NavTransitionsExamples.kt` for 10 comprehensive examples
2. **Read Documentation**: Full API docs in `README_TRANSITIONS.md`
3. **Test Transitions**: Open `TransitionPreview.kt` in Android Studio design view
4. **Customize**: Adjust durations and create custom transitions as needed
5. **Accessibility**: Implement reduce motion support for your app

## Dependencies

Already included in project:
- ✅ Jetpack Compose Navigation 2.8.1
- ✅ Compose Animation (via BOM)

No additional dependencies required!

## Support

Questions or issues? Check:
1. `README_TRANSITIONS.md` - Complete API documentation
2. `NavTransitionsExamples.kt` - 10 usage examples
3. `TransitionPreview.kt` - Visual previews

---

**Created for SpiritAtlas** - Navigation transitions designed for spiritual, contemplative experiences.
