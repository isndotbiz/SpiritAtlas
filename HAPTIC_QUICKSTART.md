# Haptic Feedback Quick Start Guide

## What Was Added

A comprehensive haptic feedback system has been added to SpiritAtlas to provide premium, tactile interactions throughout the application.

### Files Created

1. **Core System** (`core/ui/src/main/java/com/spiritatlas/core/ui/haptics/`)
   - `HapticFeedback.kt` - Main haptic system implementation
   - `HapticExamples.kt` - Interactive demo of all haptic types
   - `HapticIntegrationExample.kt` - Real-world integration examples
   - `README.md` - Comprehensive documentation

2. **Tests** (`core/ui/src/test/java/com/spiritatlas/core/ui/haptics/`)
   - `HapticFeedbackTest.kt` - Unit tests for the haptic system

3. **Configuration**
   - Updated `app/src/main/AndroidManifest.xml` - Added VIBRATE permission
   - Updated `core/ui/build.gradle.kts` - Added test dependencies

## Quick Integration

### 1. Basic Button Click

```kotlin
@Composable
fun MyScreen() {
    val haptic = rememberHapticFeedback()

    Button(
        onClick = {
            haptic.performHaptic(HapticFeedbackType.MEDIUM)
            // Handle click
        }
    ) {
        Text("Click Me")
    }
}
```

### 2. Using Modifiers

```kotlin
Button(
    onClick = { },
    modifier = Modifier.hapticClickable(
        hapticType = HapticFeedbackType.MEDIUM
    ) {
        // Click handler with automatic haptic
    }
) {
    Text("Click Me")
}
```

### 3. Save Actions

```kotlin
IconButton(
    onClick = {
        haptic.performHaptic(HapticFeedbackType.SUCCESS)
        saveProfile()
    }
) {
    Icon(Icons.Default.Save, "Save")
}
```

### 4. Tab Navigation

```kotlin
Tab(
    selected = selectedTab == index,
    onClick = {
        if (selectedTab != index) {
            haptic.performHaptic(HapticFeedbackType.SELECTION)
            onTabSelected(index)
        }
    },
    text = { Text(tabTitle) }
)
```

### 5. Toggles and Switches

```kotlin
Switch(
    checked = isEnabled,
    onCheckedChange = {
        haptic.performHaptic(HapticFeedbackType.SELECTION)
        onToggle(it)
    }
)
```

### 6. Error States

```kotlin
LaunchedEffect(isError) {
    if (isError) {
        haptic.performHaptic(HapticFeedbackType.ERROR)
    }
}
```

## Haptic Types Reference

| Type | Use Case | Feel |
|------|----------|------|
| `LIGHT` | Hover, scroll, subtle feedback | Very gentle |
| `MEDIUM` | Standard clicks, selections | Standard tactile |
| `HEAVY` | Important actions, confirmations | Strong tactile |
| `SUCCESS` | Completed actions, saves | Pleasant double-tap |
| `WARNING` | Caution, destructive actions | Attention triple-tap |
| `ERROR` | Failures, validation errors | Distinct error pattern |
| `SELECTION` | Picker selections, radio buttons | Crisp single tap |

## Semantic Mapping Guide

### For SpiritAtlas Screens

**Profile Screens:**
```kotlin
// Navigation
haptic.performHaptic(HapticFeedbackType.MEDIUM)

// Save profile
haptic.performHaptic(HapticFeedbackType.SUCCESS)

// Tab switching
haptic.performHaptic(HapticFeedbackType.SELECTION)

// Delete action
haptic.performHaptic(HapticFeedbackType.WARNING)
```

**Compatibility Screens:**
```kotlin
// Calculate compatibility
haptic.performHaptic(HapticFeedbackType.MEDIUM)

// High compatibility result (80+%)
haptic.performHaptic(HapticFeedbackType.SUCCESS)

// Low compatibility result (<40%)
haptic.performHaptic(HapticFeedbackType.WARNING)
```

**Spiritual Features:**
```kotlin
// Chakra selection
haptic.performHaptic(HapticFeedbackType.LIGHT)

// Meditation timer complete
haptic.performHaptic(HapticFeedbackType.SUCCESS)

// Sacred geometry interaction
haptic.performHaptic(HapticFeedbackType.LIGHT)
```

## User Settings

Add haptic preferences to your settings screen:

```kotlin
@Composable
fun HapticSettingsSection() {
    val context = LocalContext.current
    val preferences = remember { HapticPreferences(context) }
    val haptic = rememberHapticFeedback()

    var enabled by remember { mutableStateOf(preferences.isHapticEnabled()) }
    var intensity by remember { mutableStateOf(preferences.getHapticIntensity()) }

    Column {
        // Enable/Disable
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Haptic Feedback")
            Switch(
                checked = enabled,
                onCheckedChange = {
                    enabled = it
                    preferences.setHapticEnabled(it)
                    if (it) haptic.performHaptic(HapticFeedbackType.SELECTION)
                }
            )
        }

        // Intensity
        if (enabled) {
            Text("Intensity: ${(intensity * 100).toInt()}%")
            Slider(
                value = intensity,
                onValueChange = { intensity = it },
                onValueChangeFinished = {
                    preferences.setHapticIntensity(intensity)
                    haptic.performHaptic(HapticFeedbackType.MEDIUM)
                }
            )
        }
    }
}
```

## Testing on Device

### Prerequisites
- Physical Android device (emulators don't provide real haptic feedback)
- Android 8.0 (API 26) or higher
- Device with vibrator hardware

### Testing Steps

1. Install the app on a physical device
2. Navigate to any screen with haptic feedback
3. Interact with buttons, toggles, and other elements
4. Verify haptic feedback feels appropriate for each action

### Testing Different Intensities

1. Go to Settings
2. Adjust haptic intensity slider
3. Test various interactions at different intensities
4. Ensure patterns scale appropriately

### Testing Disable State

1. Disable haptics in Settings
2. Verify no haptic feedback occurs
3. Re-enable to confirm restoration

## Best Practices

### DO
- Use semantic haptic types (SUCCESS for saves, ERROR for failures)
- Respect user preferences (system automatically does this)
- Combine haptics with visual feedback
- Use LIGHT haptics for frequent interactions
- Use HEAVY haptics sparingly for important actions

### DON'T
- Overuse haptics (avoid on every scroll event)
- Use haptics without visual feedback
- Ignore user's disable preference
- Use random haptic types
- Block UI thread with haptic calls (system handles this)

## Advanced Features

### Custom Patterns

```kotlin
// Heartbeat pattern
haptic.vibratePattern(
    pattern = longArrayOf(0, 100, 100, 100, 100, 200),
    amplitudes = intArrayOf(0, 150, 0, 150, 0, 255)
)
```

### Long Press Detection

```kotlin
Box(
    modifier = Modifier.hapticLongPress(
        hapticType = HapticFeedbackType.HEAVY,
        onClick = { /* Regular click */ },
        onLongPress = { /* Long press action */ }
    )
) {
    Text("Long Press Me")
}
```

### Multiple Gesture Types

```kotlin
Box(
    modifier = Modifier.hapticTapGesture(
        onTap = { /* Single tap */ },
        onDoubleTap = { /* Double tap */ },
        onLongPress = { /* Long press */ },
        tapHapticType = HapticFeedbackType.MEDIUM,
        doubleTapHapticType = HapticFeedbackType.SUCCESS,
        longPressHapticType = HapticFeedbackType.HEAVY
    )
)
```

## Performance Notes

- Haptic feedback runs on background thread (non-blocking)
- Minimal battery impact for standard usage
- Gracefully handles devices without vibrators
- Works on Android 8.0+ with appropriate fallbacks

## Troubleshooting

**No haptic feedback?**
1. Check device has vibrator: `haptic.hasVibrator()`
2. Verify haptics enabled: `haptic.isEnabled()`
3. Check intensity not zero: `haptic.getIntensity()`
4. Ensure device settings allow vibration

**Haptics feel weak?**
1. Increase intensity in app settings
2. Check device vibration settings
3. Some devices have naturally weaker vibrators

**Haptics too strong?**
1. Decrease intensity in app settings
2. Consider using LIGHT type instead of MEDIUM/HEAVY

## Next Steps

1. **Add to existing screens**: Review each screen and add appropriate haptics
2. **Test on multiple devices**: Different devices have different haptic capabilities
3. **Gather user feedback**: See how users respond to haptic feedback
4. **Iterate on patterns**: Adjust haptic types and intensities based on feedback

## Resources

- Full documentation: `core/ui/src/main/java/com/spiritatlas/core/ui/haptics/README.md`
- Examples: `HapticExamples.kt` and `HapticIntegrationExample.kt`
- Tests: `HapticFeedbackTest.kt`

## Support

For issues or questions:
1. Check inline documentation in `HapticFeedback.kt`
2. Review examples in `HapticExamples.kt`
3. See integration patterns in `HapticIntegrationExample.kt`
4. Run tests: `./gradlew :core:ui:test`

---

**Ready to make SpiritAtlas feel premium!** Start by adding haptics to your most-used screens first, then expand to other areas of the app.
