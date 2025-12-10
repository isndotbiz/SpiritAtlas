# Navigation Enhancement Quick Reference

## Deep Links

### Test Deep Links
```bash
# Profile
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://profile/test123"

# Compatibility
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://compatibility"

# Home
adb shell am start -W -a android.intent.action.VIEW -d "https://spiritualatlas.app/home"
```

### Generate Deep Links
```kotlin
val uri = DeepLinkHandler.createAppLinkUri(Screen.Profile, "profileId" to "123")
```

## Transitions

```kotlin
// Use predefined transitions
SpiritTransitions.homeToFeature          // FADE_THROUGH
SpiritTransitions.profileListToDetail    // SHARED_ELEMENT
SpiritTransitions.toCompatibilityResults // CIRCULAR_REVEAL
SpiritTransitions.toModal                // SLIDE_UP

// Custom transition
navController.navigateWithTransition(
    route = Screen.Profile.createRoute("123"),
    transitionType = TransitionType.ZOOM_IN
)
```

## Haptics

```kotlin
val haptic = rememberHapticFeedback()

haptic.performHaptic(HapticFeedbackType.LIGHT)         // 10ms tap
haptic.performHaptic(HapticFeedbackType.SELECTION)     // Tab change
haptic.performHaptic(HapticFeedbackType.SUCCESS)       // Double tap
haptic.performHaptic(HapticFeedbackType.NAVIGATION)    // Screen transition
haptic.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE) // Mystical wave
```

## Analytics

```kotlin
val analytics = NavigationAnalytics(context)

// Auto-track navigation
navController.setupAnalytics(analytics)

// Get summary
val session = analytics.getSessionSummary()
val allTime = analytics.getAllTimeAnalytics()

// Export report
val report = analytics.exportAnalytics()
Log.d("Analytics", report)
```

## Gestures

```kotlin
Modifier.swipeBackGesture(
    navController = navController,
    hapticController = haptic,
    enabled = true
)
```

## Bottom Navigation

```kotlin
EnhancedBottomNavigation(
    items = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            label = "Home",
            icon = Icons.Default.Home,
            selectedIcon = Icons.Filled.Home,
            badgeCount = 0
        )
    ),
    currentRoute = currentRoute,
    navController = navController,
    hapticController = haptic
)
```

## Files Created

```
app/navigation/
├── DeepLinkHandler.kt          (9.6KB) - Deep link routing
├── NavigationAnalytics.kt      (11KB)  - Analytics tracking
├── BottomNavigationEnhanced.kt (12KB)  - Enhanced bottom nav
└── GestureNavigation.kt        (5.3KB) - Gesture support

docs/
├── NAVIGATION_GUIDE.md                 - Full implementation guide
└── NAVIGATION_TESTING_GUIDE.md         - Testing procedures

NAVIGATION_ENHANCEMENT_SUMMARY.md       - Complete summary
```

## Configuration

```kotlin
// Gesture sensitivity
GestureNavigationConfig.MIN_SWIPE_DISTANCE_DP = 100
GestureNavigationConfig.EDGE_MARGIN_DP = 20

// Haptic preferences
HapticPreferences(context).setHapticEnabled(true)
```

## Testing Commands

```bash
# Build app
./gradlew :app:assembleDebug

# Check deep links
adb shell pm get-app-links com.spiritatlas.app

# Monitor analytics
adb logcat | grep NavigationAnalytics

# GPU profiling
adb shell setprop debug.hwui.profile true
adb shell dumpsys gfxinfo com.spiritatlas.app
```

## Key Features

- ✅ 8 deep link routes
- ✅ 8 transition types
- ✅ 9 haptic patterns
- ✅ Swipe-back gesture
- ✅ Badge support
- ✅ Full analytics
- ✅ 60fps animations

## Transition Types

| Type | Use Case | Duration |
|------|----------|----------|
| FADE_THROUGH | Peer navigation | 450ms |
| SHARED_ELEMENT | List → Detail | 450ms |
| SLIDE_HORIZONTAL | Sequential | 450ms |
| SLIDE_UP | Modals | 450ms |
| ZOOM_IN | Focus detail | 600ms |
| CROSS_FADE | Tab switch | 300ms |
| CIRCULAR_REVEAL | Dramatic | 800ms |
| SCALE_FADE | Mystical | 600ms |

## Haptic Patterns

| Type | Duration | Feel |
|------|----------|------|
| LIGHT | 10ms | Subtle tick |
| MEDIUM | 20ms | Standard tap |
| HEAVY | 30ms | Strong tap |
| SELECTION | 15ms | Quick tick |
| SUCCESS | 80ms | Tap-pause-tap |
| WARNING | 25ms | Medium pulse |
| ERROR | 90ms | 3 rapid taps |
| NAVIGATION | 8ms | Very subtle |
| SPIRITUAL_PULSE | 600ms | Flowing waves |

---

**Full Documentation:** `/docs/NAVIGATION_GUIDE.md`
**Testing Guide:** `/docs/NAVIGATION_TESTING_GUIDE.md`
