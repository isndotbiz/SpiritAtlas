# Navigation Enhancement Testing Guide

## Overview

This guide provides comprehensive testing procedures for all navigation enhancements implemented in Agent 18.

## Feature Checklist

### 1. Deep Linking

#### Manual Testing

**Test Custom URI Scheme:**
```bash
# Home screen
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://home"

# Profile detail
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://profile/test123"

# Profile list
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://profiles"

# Compatibility
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://compatibility"

# Profile comparison
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://comparison/profile1/profile2"

# Settings/Consent
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://consent"

# Enrichment result
adb shell am start -W -a android.intent.action.VIEW -d "spiritualatlas://enrichment/test123"
```

**Test App Links:**
```bash
# Home screen
adb shell am start -W -a android.intent.action.VIEW -d "https://spiritualatlas.app/home"

# Profile detail
adb shell am start -W -a android.intent.action.VIEW -d "https://spiritualatlas.app/profile/test123"

# Profile list
adb shell am start -W -a android.intent.action.VIEW -d "https://spiritualatlas.app/profiles"

# Compatibility
adb shell am start -W -a android.intent.action.VIEW -d "https://spiritualatlas.app/compatibility"
```

**Expected Behavior:**
- ✅ App opens to correct screen
- ✅ Back stack is properly built
- ✅ Back navigation works naturally
- ✅ Deep link logged in analytics
- ✅ Haptic feedback on navigation

**Verify App Links:**
```bash
# Check app link verification status
adb shell pm get-app-links com.spiritatlas.app

# Should show "verified" for spiritualatlas.app domain
```

#### Automated Testing

Add to your test suite:

```kotlin
@Test
fun testDeepLinkNavigation() {
    // Launch app with deep link
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("spiritualatlas://profile/test123")
    }

    launchActivity<MainActivity>(intent)

    // Verify correct screen displayed
    onView(withId(R.id.profile_screen))
        .check(matches(isDisplayed()))

    // Verify back stack
    pressBack()
    onView(withId(R.id.profile_list_screen))
        .check(matches(isDisplayed()))

    pressBack()
    onView(withId(R.id.home_screen))
        .check(matches(isDisplayed()))
}
```

### 2. Navigation Transitions

#### Visual Testing

Run each transition and verify:

**Fade Through (Home ↔ Features):**
- ✅ Old screen fades out completely
- ✅ New screen fades in with slight scale
- ✅ Duration: ~450ms
- ✅ Smooth 60fps animation

**Shared Element (List → Detail):**
- ✅ Card appears to expand into detail
- ✅ Scale from 0.85 to 1.0
- ✅ Simultaneous fade
- ✅ No jarring jumps

**Slide Horizontal (Profile → Comparison):**
- ✅ New screen slides in from right
- ✅ Old screen slides out partially left
- ✅ Both fade during transition
- ✅ Back reverses direction

**Slide Up (Modals):**
- ✅ Modal slides up from bottom
- ✅ Fade in simultaneously
- ✅ Back slides down smoothly
- ✅ No overscroll effects

**Zoom In (Enrichment):**
- ✅ Content zooms from 0.5 to 1.0
- ✅ Fade in during zoom
- ✅ Feels like focusing in
- ✅ Back zooms out

**Cross Fade (Tabs):**
- ✅ Simple opacity crossfade
- ✅ No spatial movement
- ✅ Quick and peaceful
- ✅ 300ms duration

**Circular Reveal (Compatibility):**
- ✅ Dramatic expand from center
- ✅ Scale from 0.0 to 1.0
- ✅ Fade in simultaneously
- ✅ 800ms mystical duration

#### Performance Testing

```bash
# Enable GPU profiling
adb shell setprop debug.hwui.profile true

# Monitor frame times during transitions
adb shell dumpsys gfxinfo com.spiritatlas.app

# Should show:
# - 90% frames < 16ms (60fps)
# - No janks during transitions
# - Smooth animation curves
```

### 3. Navigation Analytics

#### Testing Analytics Tracking

```kotlin
// In MainActivity or test
val analytics = NavigationAnalytics(context)

// Navigate through app
navController.navigate(Screen.Home.route)
navController.navigate(Screen.ProfileList.route)
navController.navigate(Screen.Profile.createRoute("test"))

// Check analytics
val session = analytics.getSessionSummary()
assertEquals(3, session.screenViews.size)
assertTrue(session.navigationPath.contains("home"))
assertTrue(session.navigationPath.contains("profile_list"))

// Check all-time analytics
val allTime = analytics.getAllTimeAnalytics()
assertTrue(allTime.totalSessions > 0)
```

**Manual Verification:**

1. Navigate through app
2. Check logcat for analytics logs:
```bash
adb logcat | grep -E "(NavigationAnalytics|Screen view|Navigation transition)"
```

Expected output:
```
NavigationAnalytics: Screen view: home (view #1)
NavigationAnalytics: Navigation transition: home -> profile_list (count: 1)
NavigationAnalytics: Screen view: profile_list (view #1)
```

3. Background app and check session summary:
```bash
adb logcat | grep "Session summary"
```

**Analytics Export Testing:**

```kotlin
// Export analytics report
val report = analytics.exportAnalytics()
Log.d("Analytics", report)

// Verify report contains:
// - Session duration
// - Screen view counts
// - Navigation paths
// - Transition counts
// - Drop-off points
// - Deep link usage
```

### 4. Haptic Feedback

#### Testing Each Haptic Type

**Manual Test Script:**

1. **LIGHT** - Tap a small button
   - Should feel like: Quick, subtle tick
   - Duration: ~10ms
   - Amplitude: Light

2. **MEDIUM** - Select a card
   - Should feel like: Standard tap
   - Duration: ~20ms
   - Amplitude: Medium

3. **HEAVY** - Confirm an action
   - Should feel like: Strong, definitive
   - Duration: ~30ms
   - Amplitude: Heavy

4. **SELECTION** - Change tab
   - Should feel like: Smooth selection tick
   - Duration: ~15ms
   - Amplitude: Medium

5. **SUCCESS** - Complete profile
   - Should feel like: Double tap (tap-pause-tap)
   - Pattern: [0, 15ms, 50ms pause, 15ms]
   - Satisfying confirmation

6. **WARNING** - Data consent alert
   - Should feel like: Single medium pulse
   - Duration: ~25ms
   - Gets attention

7. **ERROR** - Invalid input
   - Should feel like: Three rapid taps
   - Pattern: [10ms, pause, 10ms, pause, 10ms]
   - Clearly different from success

8. **NAVIGATION** - Screen transition
   - Should feel like: Very subtle pulse
   - Duration: ~8ms
   - Barely noticeable but present

9. **SPIRITUAL_PULSE** - Mystical moment
   - Should feel like: Flowing waves
   - Pattern: [100ms, fade, 100ms, fade, 100ms]
   - Meditative, calming

**Device Compatibility Testing:**

Test on:
- ✅ Android 8.0 (API 26) - Legacy vibration API
- ✅ Android 10 (API 29) - VibrationEffect API
- ✅ Android 12 (API 31) - VibratorManager API
- ✅ Device with no vibrator - Graceful degradation

**User Preference Testing:**

```kotlin
val prefs = HapticPreferences(context)

// Test disable
prefs.setHapticEnabled(false)
haptic.performHaptic(HapticFeedbackType.MEDIUM)
// Verify: No vibration

// Test re-enable
prefs.setHapticEnabled(true)
haptic.performHaptic(HapticFeedbackType.MEDIUM)
// Verify: Vibration occurs
```

### 5. Gesture Navigation

#### Swipe-Back Testing

**Manual Test:**

1. Navigate to Profile Detail
2. Place finger on left edge (< 20dp from edge)
3. Swipe right slowly
4. At 50% progress:
   - ✅ Feel haptic feedback (SELECTION)
   - ✅ Visual feedback (if implemented)
5. At 100% progress:
   - ✅ Feel navigation haptic
   - ✅ Screen pops back
   - ✅ Previous screen visible

**Edge Cases:**

- ✅ Swipe from middle of screen → No effect
- ✅ Swipe left → No effect
- ✅ Swipe right from right edge → No effect
- ✅ Fast swipe (< 50% distance) → No navigation
- ✅ At root screen → No navigation possible

**Performance:**

```bash
# Monitor touch latency
adb shell dumpsys input

# Should show:
# - Touch latency < 50ms
# - Smooth gesture tracking
# - No dropped frames during swipe
```

#### Predictive Back Testing (Android 13+)

On Android 13+, test system predictive back:

1. Enable predictive back:
```bash
adb shell setprop persist.wm.debug.predictive_back_anim 1
```

2. Navigate to detail screen
3. Swipe from left edge slowly
4. Should see preview of previous screen
5. Complete swipe to navigate back

### 6. Bottom Navigation

#### Enhanced Bottom Nav Testing

**Visual Tests:**

1. **Icon Animation:**
   - ✅ Selected icon scales to 1.15x
   - ✅ Spring animation (bouncy)
   - ✅ Smooth crossfade between icons
   - ✅ Color changes to primary

2. **Label Animation:**
   - ✅ Selected label becomes bold (700)
   - ✅ Color changes to primary
   - ✅ Smooth weight transition

3. **Badge Display:**
   - ✅ Badge appears for count > 0
   - ✅ Badge shows "99+" for count > 99
   - ✅ Badge animates on count change
   - ✅ Badge dot shows when showBadgeDot = true

4. **Haptic Feedback:**
   - ✅ SELECTION haptic on tab tap
   - ✅ No haptic if already selected
   - ✅ Respects haptic preference

**Floating Bottom Nav Testing:**

If using FloatingBottomNavigation:

1. **Visual Tests:**
   - ✅ Rounded corners (24dp)
   - ✅ Elevated above content (8dp)
   - ✅ Horizontal padding (16dp)
   - ✅ Gradient background

2. **Selection Indicator:**
   - ✅ Pill-shaped background
   - ✅ Animates background color
   - ✅ Shows label only when selected
   - ✅ Expand/collapse animation

**Badge Update Testing:**

```kotlin
var profileBadgeCount by remember { mutableStateOf(0) }

val items = listOf(
    BottomNavItem(
        route = Screen.ProfileList.route,
        label = "Profiles",
        icon = Icons.Default.Person,
        badgeCount = profileBadgeCount
    ),
    // ... other items
)

// Update badge
Button(onClick = { profileBadgeCount++ }) {
    Text("Add Badge")
}

// Verify:
// - Badge appears when count > 0
// - Badge animates on increment
// - Badge shows correct count
```

## Integration Testing

### Complete User Flow

Test a complete user journey with all features:

```
1. Open app via deep link: spiritualatlas://profile/test123
   ✅ App opens to profile detail
   ✅ Back stack: Home → Profile List → Profile Detail
   ✅ Navigation haptic on load

2. Swipe back from left edge
   ✅ Haptic at 50% progress
   ✅ Navigation haptic at completion
   ✅ Return to Profile List
   ✅ Shared element transition

3. Tap Compatibility tab
   ✅ Selection haptic
   ✅ Cross-fade transition
   ✅ Analytics tracks tab change

4. Complete compatibility analysis
   ✅ SUCCESS haptic on completion
   ✅ Circular reveal transition
   ✅ Analytics tracks screen view

5. Share result via deep link
   ✅ Generate app link URI
   ✅ Share intent created
   ✅ Recipient can open link

6. Background app
   ✅ Analytics logged
   ✅ Drop-off tracked
   ✅ Session summary exported
```

### Performance Benchmarks

Target metrics:

| Metric | Target | Measurement |
|--------|--------|-------------|
| Screen transition | < 500ms | Manual timing |
| Deep link handling | < 200ms | Logcat timestamps |
| Haptic latency | < 50ms | Touch to vibration |
| Analytics write | < 5ms | Method profiling |
| Gesture detection | < 30ms | Touch to feedback |
| Frame rate | > 90% @ 60fps | GPU profiling |

## Regression Testing

Before release, verify:

### Existing Features
- ✅ Splash screen still works
- ✅ Onboarding flow unaffected
- ✅ Profile creation works
- ✅ Compatibility analysis works
- ✅ Settings/consent accessible

### Edge Cases
- ✅ Deep link with invalid profileId
- ✅ Deep link when offline
- ✅ Rapid tab switching
- ✅ Back button spam
- ✅ Deep link during transition
- ✅ Analytics with full storage
- ✅ Haptics with battery saver

### Device Coverage
- ✅ Small screen (4.7")
- ✅ Normal screen (5.5"-6.0")
- ✅ Large screen (6.5"+)
- ✅ Tablet (7"+)
- ✅ Foldable devices

## Accessibility Testing

### TalkBack Testing

1. Enable TalkBack
2. Test navigation:
   - ✅ Screen announcements
   - ✅ Button descriptions
   - ✅ Navigation gestures work
   - ✅ Deep links accessible

### High Contrast Testing

1. Enable high contrast mode
2. Verify:
   - ✅ Transitions visible
   - ✅ Selection indicators clear
   - ✅ Badges readable

### Reduced Motion

1. Enable reduce motion
2. Verify:
   - ✅ Transitions still work
   - ✅ Animations simplified
   - ✅ No motion sickness triggers

## Known Issues and Limitations

### Deep Links

- App Links require domain verification (upload assetlinks.json)
- Custom URI scheme works without verification
- Back stack may not match exact user path

### Haptics

- Not available on emulators
- Varies by device manufacturer
- Some devices ignore amplitude settings
- Battery saver may disable haptics

### Gestures

- May conflict with system gestures
- Not available on button navigation mode
- Edge detection varies by screen protector

### Analytics

- Stored locally only
- Limited by SharedPreferences size
- No automatic sync/backup

## Success Criteria

All features should meet:

- ✅ **Deep Links:** 100% of routes accessible
- ✅ **Transitions:** Smooth 60fps, < 500ms
- ✅ **Analytics:** All events tracked accurately
- ✅ **Haptics:** 9 distinct patterns working
- ✅ **Gestures:** < 30ms latency
- ✅ **Bottom Nav:** Animations smooth, badges work

Target health score improvement: **+0.5 points**

---

For implementation details, see [Navigation Guide](NAVIGATION_GUIDE.md)
