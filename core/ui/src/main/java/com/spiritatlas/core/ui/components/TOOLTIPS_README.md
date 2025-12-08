# Tooltips and Coach Marks for SpiritAtlas

Beautiful, non-intrusive UI guidance components with glassmorphism styling and smooth animations.

## Components Overview

### 1. SpiritualTooltip
Elegant tooltip with glassmorphism background and customizable arrow position.

**Features:**
- Glassmorphism background with gradient
- Customizable arrow position (top, bottom, left, right, none)
- Animated entrance (scale + fade)
- Auto-dismiss after delay option
- Rich content support (icon + text)
- Smooth spring animations

**Usage:**
```kotlin
var showTooltip by remember { mutableStateOf(false) }

SpiritualTooltip(
    visible = showTooltip,
    text = "Your life path number shows your soul's purpose",
    icon = Icons.Default.Star,
    arrowPosition = TooltipArrowPosition.BOTTOM,
    autoDismissAfter = 5000,
    onDismiss = { showTooltip = false }
)
```

**Parameters:**
- `visible: Boolean` - Controls tooltip visibility
- `text: String` - Tooltip message
- `icon: ImageVector?` - Optional icon
- `arrowPosition: TooltipArrowPosition` - Arrow direction (TOP, BOTTOM, LEFT, RIGHT, NONE)
- `autoDismissAfter: Long?` - Auto-dismiss delay in milliseconds
- `onDismiss: () -> Unit` - Callback when dismissed

---

### 2. HintBubble
Small, dismissible contextual hints.

**Features:**
- Compact design
- Dismissible with close button
- Customizable background color
- Fade and scale animations

**Usage:**
```kotlin
var showHint by remember { mutableStateOf(true) }

HintBubble(
    text = "Tap for details",
    visible = showHint,
    backgroundColor = SpiritualPurple,
    onDismiss = { showHint = false }
)
```

**Best Practices:**
- Use for small, contextual tips
- Keep text short (5-10 words)
- Allow users to dismiss
- Don't show too many at once

---

### 3. FeatureSpotlight
Highlights new features with an animated "NEW" badge.

**Features:**
- Shimmer animation effect
- Eye-catching gradient
- Dismissible
- Compact design

**Usage:**
```kotlin
var showSpotlight by remember { mutableStateOf(true) }

Row {
    Text("New Feature Name")
    FeatureSpotlight(
        visible = showSpotlight,
        onDismiss = { showSpotlight = false }
    )
}
```

**Best Practices:**
- Use sparingly for truly new features
- Track dismissal in preferences
- Remove after user has seen it
- Don't overuse - only for important features

---

### 4. CoachMark
Spotlight circle with explanation card for focused guidance.

**Features:**
- Darkened overlay with spotlight
- Pulsing animation on target
- Intelligent card positioning
- Step indicators
- "Got it" / "Next" / "Skip" buttons
- Multi-step tour support

**Usage:**
```kotlin
var showCoachMark by remember { mutableStateOf(true) }
var targetBounds by remember { mutableStateOf<Rect?>(null) }

// Track element bounds
Box(
    modifier = Modifier.trackBounds { bounds ->
        targetBounds = bounds
    }
) {
    // Your target element
}

// Show coach mark
CoachMark(
    visible = showCoachMark,
    step = CoachMarkStep(
        targetBounds = targetBounds,
        title = "Your Profile",
        description = "Tap to view detailed spiritual insights",
        icon = Icons.Default.Person
    ),
    currentStep = 1,
    totalSteps = 3,
    onNext = { /* Move to next step */ },
    onSkip = { /* Skip tour */ }
)
```

**Positioning:**
- Card appears at top if target is in lower half
- Card appears at bottom if target is in upper half
- Automatically avoids overlap with target

---

### 5. CoachMarkTour
Complete multi-step tour system.

**Features:**
- Manages multiple coach mark steps
- Automatic progression
- Skip functionality
- Completion callback
- Smooth transitions between steps

**Usage:**
```kotlin
var showTour by remember { mutableStateOf(true) }

// Track bounds for all targets
var profileBounds by remember { mutableStateOf<Rect?>(null) }
var settingsBounds by remember { mutableStateOf<Rect?>(null) }

CoachMarkTour(
    steps = listOf(
        CoachMarkStep(
            targetBounds = profileBounds,
            title = "Your Profile",
            description = "View and edit your spiritual profile here",
            icon = Icons.Default.Person
        ),
        CoachMarkStep(
            targetBounds = settingsBounds,
            title = "Settings",
            description = "Customize your experience",
            icon = Icons.Default.Settings
        )
    ),
    visible = showTour,
    onComplete = {
        showTour = false
        // Save completion to preferences
        PreferencesManager.setTourCompleted("main_tour", true)
    }
)
```

**Best Practices:**
- Keep tours to 3-5 steps maximum
- Make each step clear and actionable
- Always provide a skip option
- Track completion in preferences
- Don't show the same tour repeatedly

---

### 6. OnboardingOverlay
Full-screen tutorial overlay for first-time users.

**Features:**
- Full-screen gradient background
- Multi-page walkthrough
- Page indicators
- Breathing animation on icons
- Skip option
- Beautiful transitions

**Usage:**
```kotlin
var showOnboarding by remember { mutableStateOf(true) }

OnboardingOverlay(
    visible = showOnboarding,
    title = "Welcome to SpiritAtlas",
    description = "Your spiritual journey begins here",
    steps = listOf(
        OnboardingStep(
            title = "Discover Your Path",
            description = "Explore numerology and astrology",
            icon = Icons.Default.Star
        ),
        OnboardingStep(
            title = "Connect Deeply",
            description = "Find soul-level compatibility",
            icon = Icons.Default.Favorite
        ),
        OnboardingStep(
            title = "Grow Together",
            description = "Track your spiritual growth",
            icon = Icons.Default.AccountCircle
        )
    ),
    onComplete = {
        showOnboarding = false
        PreferencesManager.setOnboardingCompleted(true)
    }
)
```

**Best Practices:**
- Show only once per user
- Keep to 3-4 screens
- Make it skippable
- Focus on key value propositions
- Use compelling visuals

---

## Utility Functions

### rememberBoundsInRoot()
Tracks element bounds for coach marks.

```kotlin
val (modifier, getBounds) = rememberBoundsInRoot()

Box(modifier = modifier) {
    // Your element
}

// Use bounds in coach mark
val bounds = getBounds()
```

### trackBounds()
Modifier to track bounds changes.

```kotlin
Box(
    modifier = Modifier.trackBounds { bounds ->
        targetBounds = bounds
    }
) {
    // Your element
}
```

---

## Preferences Management

### Example Implementation

```kotlin
object CoachMarkPreferences {
    private const val PREF_NAME = "coach_marks"

    fun isTourCompleted(context: Context, tourId: String): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("tour_$tourId", false)
    }

    fun setTourCompleted(context: Context, tourId: String, completed: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("tour_$tourId", completed).apply()
    }

    fun shouldShowTour(context: Context, tourId: String): Boolean {
        return !isTourCompleted(context, tourId)
    }
}
```

### Using with DataStore (Recommended)

```kotlin
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "coach_marks")

class CoachMarkPreferences(private val context: Context) {
    private val tourCompletedKey = stringSetPreferencesKey("completed_tours")

    suspend fun isTourCompleted(tourId: String): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[tourCompletedKey]?.contains(tourId) ?: false
        }.first()
    }

    suspend fun setTourCompleted(tourId: String) {
        context.dataStore.edit { preferences ->
            val completed = preferences[tourCompletedKey]?.toMutableSet() ?: mutableSetOf()
            completed.add(tourId)
            preferences[tourCompletedKey] = completed
        }
    }
}
```

---

## Complete Example: Profile Screen

```kotlin
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showTour by remember {
        mutableStateOf(
            CoachMarkPreferences.shouldShowTour(context, "profile_tour")
        )
    }

    // Track element bounds
    var profileCardBounds by remember { mutableStateOf<Rect?>(null) }
    var editButtonBounds by remember { mutableStateOf<Rect?>(null) }
    var shareButtonBounds by remember { mutableStateOf<Rect?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile card with bounds tracking
            ModernCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .trackBounds { bounds -> profileCardBounds = bounds }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Spiritual Profile",
                        style = MaterialTheme.typography.headlineSmall,
                        color = SpiritualPurple
                    )
                    // ... profile content
                }
            }

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Edit */ },
                    modifier = Modifier
                        .weight(1f)
                        .trackBounds { bounds -> editButtonBounds = bounds }
                ) {
                    Icon(Icons.Default.Edit, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Edit")
                }

                Button(
                    onClick = { /* Share */ },
                    modifier = Modifier
                        .weight(1f)
                        .trackBounds { bounds -> shareButtonBounds = bounds }
                ) {
                    Icon(Icons.Default.Share, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Share")
                }
            }
        }

        // Coach mark tour
        if (showTour) {
            CoachMarkTour(
                steps = listOf(
                    CoachMarkStep(
                        targetBounds = profileCardBounds,
                        title = "Your Spiritual Profile",
                        description = "This shows your life path number, astrological details, and sacred archetypes.",
                        icon = Icons.Default.Person
                    ),
                    CoachMarkStep(
                        targetBounds = editButtonBounds,
                        title = "Edit Profile",
                        description = "Update your birth details to refine your spiritual insights.",
                        icon = Icons.Default.Edit
                    ),
                    CoachMarkStep(
                        targetBounds = shareButtonBounds,
                        title = "Share Your Profile",
                        description = "Share your spiritual profile with friends and potential soul connections.",
                        icon = Icons.Default.Share
                    )
                ),
                onComplete = {
                    showTour = false
                    CoachMarkPreferences.setTourCompleted(context, "profile_tour", true)
                }
            )
        }
    }
}
```

---

## Best Practices

### General Guidelines

1. **Don't Overuse**: Too many tooltips/coach marks create a poor UX
2. **Be Concise**: Keep text short and actionable
3. **Make It Skippable**: Always allow users to dismiss or skip
4. **Track Completion**: Use preferences to avoid showing tours repeatedly
5. **Test on Real Devices**: Ensure positioning works on different screen sizes

### When to Use Each Component

| Component | Use Case | Frequency |
|-----------|----------|-----------|
| SpiritualTooltip | Context-sensitive help, feature explanation | On-demand or first-time |
| HintBubble | Quick tips, dismissible hints | First-time, then never |
| FeatureSpotlight | New feature announcement | Until dismissed |
| CoachMark | Single element guidance | First-time only |
| CoachMarkTour | Multi-step onboarding | First-time only |
| OnboardingOverlay | App introduction | Once per user |

### Animation Performance

- All animations use Compose's built-in animation APIs
- Spring animations provide natural, bouncy feel
- Tween animations for smooth fades
- Infinite animations use `rememberInfiniteTransition`
- All animations are optimized for 60fps

### Accessibility

- All interactive elements have proper `contentDescription`
- Text is readable (14sp minimum)
- High contrast colors (white on purple gradient)
- Touch targets are 48dp minimum
- Skip/dismiss options always available

---

## Theming and Customization

All components use SpiritAtlas theme colors:
- `SpiritualPurple` - Primary spiritual color
- `MysticViolet` - Secondary mystical color
- `AuraGold` - Accent color for hints
- `TantricRose` - Feature spotlight gradient

To customize colors, modify the theme in:
`core/ui/src/main/java/com/spiritatlas/core/ui/theme/Color.kt`

---

## Troubleshooting

### Tooltip Not Showing
- Verify `visible` parameter is `true`
- Check that parent has enough space
- Ensure tooltip is not clipped by parent bounds

### Coach Mark Positioning Issues
- Ensure bounds are tracked correctly with `trackBounds`
- Wait for composition before showing tour
- Use `LaunchedEffect` to delay tour start if needed

### Tour Showing Repeatedly
- Verify preferences are being saved correctly
- Check tour ID is consistent
- Clear app data to reset during development

### Performance Issues
- Limit simultaneous animations
- Use `remember` for animation states
- Avoid showing multiple coach marks at once

---

## Migration Guide

### From Plain Tooltips
```kotlin
// Before
Tooltip(text = "Help text") {
    Icon(Icons.Default.Info, null)
}

// After
var showTooltip by remember { mutableStateOf(false) }

Box {
    IconButton(onClick = { showTooltip = true }) {
        Icon(Icons.Default.Info, null)
    }

    SpiritualTooltip(
        visible = showTooltip,
        text = "Help text",
        onDismiss = { showTooltip = false }
    )
}
```

### Adding Coach Marks to Existing Screens
1. Add bounds tracking to target elements
2. Create coach mark steps
3. Check preferences for tour completion
4. Add `CoachMarkTour` in overlay
5. Save completion on finish

---

## Future Enhancements

Potential additions:
- [ ] Voice-over support
- [ ] Tooltip animations (bounce, shake)
- [ ] More arrow styles
- [ ] Tour analytics
- [ ] A/B testing support
- [ ] Video tutorials in overlays
- [ ] Interactive tutorial mode

---

## Support

For questions or issues:
1. Check this README
2. Review example implementations in `TooltipsAndCoachMarksExample.kt`
3. Consult SpiritAtlas UI documentation
4. Reach out to the team

---

**Version**: 1.0.0
**Last Updated**: December 2024
**Author**: SpiritAtlas UI Team
