# Tooltips & Coach Marks - Quick Reference

## Quick Start Guide

### 1. Simple Tooltip
```kotlin
var showTooltip by remember { mutableStateOf(false) }

SpiritualTooltip(
    visible = showTooltip,
    text = "Your life path number",
    icon = Icons.Default.Star,
    onDismiss = { showTooltip = false }
)
```

### 2. Hint Bubble
```kotlin
HintBubble(
    text = "Tap for details",
    visible = showHint,
    onDismiss = { showHint = false }
)
```

### 3. Feature Spotlight (NEW badge)
```kotlin
val (shouldShow, markSeen) = rememberFeatureSpotlightState("feature_id")

FeatureSpotlight(
    visible = shouldShow,
    onDismiss = markSeen
)
```

### 4. Simple Coach Mark Tour
```kotlin
val (shouldShow, setCompleted) = rememberShouldShowTour("tour_id")
var targetBounds by remember { mutableStateOf<Rect?>(null) }

// Track element
Box(modifier = Modifier.trackBounds { targetBounds = it }) {
    // Your element
}

// Show tour
if (shouldShow) {
    CoachMarkTour(
        steps = listOf(
            CoachMarkStep(
                targetBounds = targetBounds,
                title = "Title",
                description = "Description"
            )
        ),
        onComplete = { setCompleted(true) }
    )
}
```

### 5. Onboarding Overlay
```kotlin
val (isCompleted, setCompleted) = rememberOnboardingState()

OnboardingOverlay(
    visible = !isCompleted,
    title = "Welcome",
    description = "Get started",
    steps = listOf(
        OnboardingStep(
            title = "Step 1",
            description = "Info",
            icon = Icons.Default.Star
        )
    ),
    onComplete = { setCompleted(true) }
)
```

## Component Decision Tree

```
Need UI guidance?
│
├─ Single element help?
│  ├─ On-demand → SpiritualTooltip
│  └─ Persistent → HintBubble
│
├─ New feature?
│  └─ FeatureSpotlight
│
├─ Multi-step walkthrough?
│  ├─ 1-3 elements → CoachMarkTour
│  └─ App introduction → OnboardingOverlay
│
└─ Contextual tip?
    └─ HintBubble
```

## Tour IDs Reference

```kotlin
// Predefined tour IDs
TourIds.MAIN_ONBOARDING
TourIds.PROFILE_TOUR
TourIds.COMPATIBILITY_TOUR
TourIds.SETTINGS_TOUR
TourIds.TANTRIC_TOUR
TourIds.NUMEROLOGY_TOUR
TourIds.ASTROLOGY_TOUR

// Predefined feature IDs
FeatureIds.TANTRIC_COMPATIBILITY
FeatureIds.COUPLES_ANALYSIS
FeatureIds.DAILY_INSIGHTS
FeatureIds.PROFILE_SHARING
FeatureIds.ADVANCED_CHARTS
```

## Common Patterns

### Show Tour Once Per User
```kotlin
val (shouldShow, setCompleted) = rememberShouldShowTour(TourIds.PROFILE_TOUR)

if (shouldShow) {
    CoachMarkTour(
        steps = mySteps,
        onComplete = { setCompleted(true) }
    )
}
```

### Track Multiple Elements
```kotlin
var element1Bounds by remember { mutableStateOf<Rect?>(null) }
var element2Bounds by remember { mutableStateOf<Rect?>(null) }

Element1(modifier = Modifier.trackBounds { element1Bounds = it })
Element2(modifier = Modifier.trackBounds { element2Bounds = it })
```

### Reset Tours (Debug)
```kotlin
// In debug menu
Button(onClick = {
    CoachMarkPreferences.resetAll(context)
}) {
    Text("Reset All Tours")
}
```

## Styling Reference

### Colors
- Primary: `SpiritualPurple` (#8B5CF6)
- Secondary: `MysticViolet` (#7C3AED)
- Accent: `AuraGold` (#FBBF24)
- Spotlight: `TantricRose` (#EC4899)

### Sizes
- Tooltip max width: 280.dp
- Tooltip padding: 16.dp
- Arrow size: 12.dp
- Coach mark card: 90% screen width
- Onboarding icon: 120.dp

### Animations
- Tooltip entrance: 300ms scale + fade
- Tooltip exit: 200ms scale + fade
- Spotlight shimmer: Continuous
- Coach mark pulse: 2000ms cycle
- Onboarding breathing: 4000ms cycle

## Troubleshooting Checklist

- [ ] Is `visible` parameter true?
- [ ] Are bounds being tracked correctly?
- [ ] Is parent large enough?
- [ ] Is tour ID unique?
- [ ] Are preferences being saved?
- [ ] Did you add necessary imports?
- [ ] Is element rendered before showing tour?

## File Locations

```
core/ui/src/main/java/com/spiritatlas/core/ui/components/
├── TooltipsAndCoachMarks.kt           # Main components
├── TooltipsAndCoachMarksExample.kt    # Usage examples
├── CoachMarkPreferences.kt            # State management
├── TOOLTIPS_README.md                 # Full documentation
└── TOOLTIPS_QUICK_REFERENCE.md        # This file
```

## Performance Tips

1. Use `remember` for animation states
2. Track only necessary bounds
3. Don't show multiple tours simultaneously
4. Dismiss tooltips after delay
5. Limit onboarding to 3-4 steps

## Need Help?

1. Check `TooltipsAndCoachMarksExample.kt` for examples
2. Read `TOOLTIPS_README.md` for detailed docs
3. Review existing implementations in the app
4. Test on real devices for positioning

---

**Quick Reference Version**: 1.0.0
