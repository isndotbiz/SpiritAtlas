# SpiritAtlas Navigation Guide

## Overview

SpiritAtlas features a comprehensive navigation system with:
- Deep linking support
- Smooth spiritual transitions
- Navigation analytics
- Haptic feedback
- Gesture navigation
- Bottom navigation enhancements

## Deep Linking

### Supported Deep Link Formats

#### Custom URI Scheme (`spiritualatlas://`)

```
spiritualatlas://home
spiritualatlas://profile/{profileId}
spiritualatlas://profile/new
spiritualatlas://profiles
spiritualatlas://compatibility
spiritualatlas://comparison/{profileId1}/{profileId2}
spiritualatlas://consent
spiritualatlas://enrichment/{profileId}
```

#### App Links (`https://spiritualatlas.app/`)

```
https://spiritualatlas.app/home
https://spiritualatlas.app/profile/{profileId}
https://spiritualatlas.app/profile/new
https://spiritualatlas.app/profiles
https://spiritualatlas.app/compatibility
https://spiritualatlas.app/comparison/{profileId1}/{profileId2}
https://spiritualatlas.app/consent
https://spiritualatlas.app/enrichment/{profileId}
```

### Testing Deep Links

#### Via ADB

```bash
# Test custom URI scheme
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://profile/123"

# Test app links
adb shell am start -W -a android.intent.action.VIEW -d "https://spiritualatlas.app/compatibility"

# Test profile creation
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://profile/new"

# Test comparison
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://comparison/profile1/profile2"
```

#### Programmatic Usage

```kotlin
// Generate deep link URI
val deepLinkUri = DeepLinkHandler.createDeepLinkUri(
    screen = Screen.Profile,
    "profileId" to "user123"
)

// Generate app link URI
val appLinkUri = DeepLinkHandler.createAppLinkUri(
    screen = Screen.Compatibility
)

// Share deep link
val shareIntent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, appLinkUri.toString())
}
startActivity(Intent.createChooser(shareIntent, "Share Profile"))
```

### Back Stack Behavior

Deep links automatically build an appropriate back stack:

- `spiritualatlas://home` → Home (clear stack)
- `spiritualatlas://profile/123` → Home → Profile List → Profile Detail
- `spiritualatlas://comparison/p1/p2` → Home → Profile List → Comparison
- `spiritualatlas://enrichment/123` → Home → Profile List → Enrichment

This ensures users can navigate back naturally after deep link entry.

## Navigation Transitions

### Available Transition Types

| Transition | Use Case | Animation |
|------------|----------|-----------|
| `FADE_THROUGH` | Peer navigation (Home ↔ Features) | Fade out, then fade in |
| `SHARED_ELEMENT` | Profile list → Detail | Morph with scale |
| `SLIDE_HORIZONTAL` | Sequential flow | Left/right slide |
| `SLIDE_UP` | Modals & bottom sheets | Slide from bottom |
| `ZOOM_IN` | Focus on detail | Expand from center |
| `CROSS_FADE` | Tab switches | Peaceful blend |
| `CIRCULAR_REVEAL` | Dramatic reveals | Expand in circle |
| `SCALE_FADE` | Mystical moments | Float and fade |

### Predefined Flows

```kotlin
// Home to feature screens
SpiritTransitions.homeToFeature // FADE_THROUGH

// Profile list to detail
SpiritTransitions.profileListToDetail // SHARED_ELEMENT

// Profile to comparison
SpiritTransitions.profileToComparison // SLIDE_HORIZONTAL

// Compatibility results
SpiritTransitions.toCompatibilityResults // CIRCULAR_REVEAL

// Enrichment results
SpiritTransitions.toEnrichmentResults // ZOOM_IN

// Modals (Consent, Settings)
SpiritTransitions.toModal // SLIDE_UP

// Tab switches
SpiritTransitions.tabSwitch // CROSS_FADE
```

### Custom Transitions

```kotlin
// In NavGraph
spiritualComposable(
    route = Screen.MyScreen.route,
    transitionType = TransitionType.ZOOM_IN
) {
    MyScreen()
}

// Programmatic navigation with transition
navController.navigateWithTransition(
    route = Screen.Profile.createRoute("123"),
    transitionType = TransitionType.SHARED_ELEMENT
)
```

## Navigation Analytics

### Tracked Metrics

- **Screen Views**: Count and duration per screen
- **Navigation Paths**: User journey through app
- **Transitions**: Which screens lead to which
- **Drop-offs**: Where users exit the app
- **Deep Links**: Which deep links are used

### Accessing Analytics

```kotlin
// In MainActivity
val analytics = NavigationAnalytics(context)

// Get current session summary
val session = analytics.getSessionSummary()
Log.d("Analytics", "Session duration: ${session.sessionDuration}ms")
Log.d("Analytics", "Navigation path: ${session.navigationPath}")

// Get all-time analytics
val allTime = analytics.getAllTimeAnalytics()
Log.d("Analytics", "Total sessions: ${allTime.totalSessions}")

// Export as readable string
val report = analytics.exportAnalytics()
Log.d("Analytics", report)

// Reset analytics (privacy)
analytics.resetAnalytics()
```

### Analytics Privacy

- All analytics stored **locally only**
- No data transmitted to servers
- Users can clear analytics via `resetAnalytics()`
- No personal information collected

## Haptic Feedback

### Feedback Types

```kotlin
// Light tap - subtle UI interactions
hapticController.performHaptic(HapticFeedbackType.LIGHT)

// Medium tap - standard interactions
hapticController.performHaptic(HapticFeedbackType.MEDIUM)

// Heavy tap - important actions
hapticController.performHaptic(HapticFeedbackType.HEAVY)

// Selection - picker/tab changes
hapticController.performHaptic(HapticFeedbackType.SELECTION)

// Success - positive confirmation
hapticController.performHaptic(HapticFeedbackType.SUCCESS)

// Warning - attention needed
hapticController.performHaptic(HapticFeedbackType.WARNING)

// Error - something went wrong
hapticController.performHaptic(HapticFeedbackType.ERROR)

// Navigation - screen transitions
hapticController.performHaptic(HapticFeedbackType.NAVIGATION)

// Spiritual pulse - mystical moments
hapticController.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE)
```

### Haptic Patterns

| Type | Duration | Pattern | Use Case |
|------|----------|---------|----------|
| LIGHT | 10ms | Single pulse | Button taps |
| MEDIUM | 20ms | Single pulse | Card selections |
| HEAVY | 30ms | Strong pulse | Confirmations |
| SELECTION | 15ms | Quick tick | Tab/picker changes |
| SUCCESS | 80ms | Tap-pause-tap | Successful actions |
| WARNING | 25ms | Medium pulse | Alerts |
| ERROR | 90ms | Three rapid taps | Errors |
| NAVIGATION | 8ms | Very subtle | Screen transitions |
| SPIRITUAL_PULSE | 600ms | Flowing waves | Mystical reveals |

### Composable Integration

```kotlin
@Composable
fun MyScreen() {
    val haptic = rememberHapticFeedback()

    Button(
        onClick = {
            haptic.performHaptic(HapticFeedbackType.SELECTION)
            // Handle click
        }
    ) {
        Text("Tap Me")
    }
}
```

### User Preferences

```kotlin
val preferences = HapticPreferences(context)

// Check if haptics enabled
if (preferences.isHapticEnabled()) {
    // Perform haptic
}

// Toggle haptics
preferences.setHapticEnabled(false)
```

## Gesture Navigation

### Swipe-Back Gesture

Users can swipe from the left edge to navigate back:

```kotlin
@Composable
fun MyScreen(navController: NavController) {
    val haptic = rememberHapticFeedback()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipeBackGesture(
                navController = navController,
                hapticController = haptic,
                enabled = true,
                onSwipeProgress = { progress ->
                    // Optional: Show visual feedback
                    Log.d("Gesture", "Swipe progress: $progress")
                }
            )
    ) {
        // Screen content
    }
}
```

### Gesture Configuration

```kotlin
// Minimum swipe distance (default: 100dp)
GestureNavigationConfig.MIN_SWIPE_DISTANCE_DP

// Velocity threshold (default: 300dp/sec)
GestureNavigationConfig.VELOCITY_THRESHOLD

// Edge margin where gesture works (default: 20dp)
GestureNavigationConfig.EDGE_MARGIN_DP
```

### Gesture Behavior

1. User swipes right from left edge
2. At 50% progress: haptic feedback
3. At 100%: navigation back + haptic
4. Back stack updates automatically

## Bottom Navigation

### Enhanced Bottom Navigation

```kotlin
@Composable
fun AppContent(navController: NavController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val haptic = rememberHapticFeedback()

    Scaffold(
        bottomBar = {
            EnhancedBottomNavigation(
                items = SpiritAtlasBottomNav.getDefaultItems(),
                currentRoute = currentRoute?.destination?.route,
                navController = navController,
                hapticController = haptic
            )
        }
    ) { padding ->
        // Content
    }
}
```

### Custom Nav Items with Badges

```kotlin
val items = listOf(
    BottomNavItem(
        route = Screen.Home.route,
        label = "Home",
        icon = Icons.Default.Home,
        selectedIcon = Icons.Filled.Home,
        badgeCount = 0 // No badge
    ),
    BottomNavItem(
        route = Screen.ProfileList.route,
        label = "Profiles",
        icon = Icons.Default.Person,
        selectedIcon = Icons.Filled.Person,
        badgeCount = 3 // Shows "3"
    ),
    BottomNavItem(
        route = Screen.Compatibility.route,
        label = "Compatibility",
        icon = Icons.Default.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        showBadgeDot = true // Shows dot indicator
    )
)
```

### Floating Bottom Navigation

For a more modern look:

```kotlin
FloatingBottomNavigation(
    items = items,
    currentRoute = currentRoute,
    navController = navController,
    hapticController = haptic
)
```

## Best Practices

### Navigation

1. **Use appropriate transitions** for each navigation type
2. **Build back stacks** logically for deep links
3. **Track analytics** to understand user flows
4. **Provide haptic feedback** for all interactions
5. **Support gesture navigation** on all screens

### Performance

1. **Lazy load** screens with heavy content
2. **Dispose resources** when leaving screens
3. **Optimize animations** for 60fps
4. **Minimize analytics writes** (batching)
5. **Test on low-end devices**

### Accessibility

1. **Provide content descriptions** for icons
2. **Support TalkBack** for navigation
3. **Allow haptic disable** for sensory sensitivity
4. **Use semantic routes** for screen reader
5. **Test with accessibility scanner**

## Troubleshooting

### Deep Links Not Working

```bash
# Verify intent filters
adb shell dumpsys package com.spiritatlas.app

# Check link verification
adb shell pm get-app-links com.spiritatlas.app

# Reset app links
adb shell pm set-app-links --package com.spiritatlas.app 0

# Re-verify
adb shell pm verify-app-links --re-verify com.spiritatlas.app
```

### Analytics Not Recording

```kotlin
// Check if analytics initialized
if (::analytics.isInitialized) {
    Log.d("Analytics", "Analytics active")
} else {
    Log.e("Analytics", "Analytics not initialized")
}

// Verify tracking
navController.addOnDestinationChangedListener { _, destination, _ ->
    Log.d("Analytics", "Destination changed: ${destination.route}")
}
```

### Haptics Not Working

```kotlin
// Check device capability
val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
if (vibrator?.hasVibrator() == true) {
    Log.d("Haptics", "Vibrator available")
} else {
    Log.w("Haptics", "No vibrator on device")
}

// Check permissions
if (checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
    Log.d("Haptics", "Vibrate permission granted")
}
```

## Examples

### Complete Screen with All Features

```kotlin
@Composable
fun EnhancedScreen(
    navController: NavController,
    analytics: NavigationAnalytics
) {
    val haptic = rememberHapticFeedback()

    // Track screen view
    LaunchedEffect(Unit) {
        analytics.trackScreenView("enhanced_screen")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipeBackGesture(
                navController = navController,
                hapticController = haptic
            )
    ) {
        Column {
            Button(
                onClick = {
                    haptic.performHaptic(HapticFeedbackType.SELECTION)
                    navController.navigateWithTransition(
                        route = Screen.Profile.createRoute("123"),
                        transitionType = TransitionType.ZOOM_IN
                    )
                }
            ) {
                Text("Go to Profile")
            }

            Button(
                onClick = {
                    haptic.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE)
                    // Create shareable deep link
                    val uri = DeepLinkHandler.createAppLinkUri(
                        Screen.Profile,
                        "profileId" to "123"
                    )
                    shareLink(uri)
                }
            ) {
                Text("Share Profile")
            }
        }
    }
}
```

---

For more information, see:
- [Deep Link Handler Implementation](../app/src/main/java/com/spiritatlas/app/navigation/DeepLinkHandler.kt)
- [Navigation Analytics](../app/src/main/java/com/spiritatlas/app/navigation/NavigationAnalytics.kt)
- [Haptic Feedback](../core/ui/src/main/java/com/spiritatlas/core/ui/haptics/HapticFeedback.kt)
- [Gesture Navigation](../app/src/main/java/com/spiritatlas/app/navigation/GestureNavigation.kt)
