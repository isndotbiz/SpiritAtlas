# UX Feel Quick Reference Card

## One-Page Cheat Sheet for Amazing Interactions

---

## 🎯 Gestures

```kotlin
// Swipe to dismiss
Modifier.swipeToDismiss(onDismiss = { })

// Long press (500ms)
Modifier.longPressAction(onLongPress = { })

// Pinch to zoom
Modifier.pinchToZoom(minScale = 0.5f, maxScale = 3f)
```

---

## ⚡ Micro-Interactions

```kotlin
// Button press - Scale + haptic
Modifier.buttonPressEffect()

// Card hover - Lift + glow
Modifier.cardHoverEffect()

// Selection - Scale + color
Modifier.selectionHighlight(isSelected)

// List entrance - Stagger
Modifier.staggeredEntrance(index)
```

---

## 🔄 Loading States

```kotlin
// Sacred geometry
SpiritualLoader(size = 64.dp, message = "Loading...")

// Chakra colors
ChakraLoader(size = 80.dp)

// Zodiac wheel
ZodiacWheelLoader(size = 100.dp)

// Meditation breath
MeditationBreathLoader(size = 80.dp)

// Skeleton screen
SkeletonBox(modifier = Modifier.height(200.dp))
```

---

## 🎉 Celebrations

```kotlin
// Confetti explosion
ConfettiCelebration(particleCount = 100)

// Sparkle burst
SparkleBurst(sparkleCount = 20)

// Success ripple
SuccessRipple(color = CosmicBlue)

// Starburst
Starburst(rayCount = 12)

// Achievement
AchievementUnlock(title = "Milestone", icon = "🏆")
```

---

## ✨ Delight Moments

```kotlin
// Personalized greeting
PersonalizedGreeting(userName = "Sarah", zodiacSign = "Sagittarius")

// Birthday party
BirthdayCelebration(name = "Sarah", age = 28)

// Easter egg (7 taps)
Modifier.easterEggTrigger(tapCount = 7, onEasterEggFound = { })

// Cosmic event
CosmicEventNotification(event = fullMoonEvent)

// Encouraging message
EncouragingMessage(onDismiss = { })
```

---

## 📳 Haptic Feedback

```kotlin
val haptic = rememberHapticFeedback()

haptic.performHaptic(HapticFeedbackType.LIGHT)      // Touch
haptic.performHaptic(HapticFeedbackType.MEDIUM)     // Button
haptic.performHaptic(HapticFeedbackType.HEAVY)      // Important
haptic.performHaptic(HapticFeedbackType.SUCCESS)    // Complete
haptic.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE) // Special
```

---

## 🎨 Common Patterns

### Enhanced Button
```kotlin
Button(
    onClick = { viewModel.save() },
    modifier = Modifier.buttonPressEffect()
) { Text("Save") }
```

### Enhanced Card
```kotlin
ProfileCard(
    modifier = Modifier
        .cardHoverEffect()
        .longPressAction { showActions() }
        .swipeToDismiss { delete() }
)
```

### Loading Screen
```kotlin
if (isLoading) {
    SpiritualLoader(message = "Loading...")
} else {
    Content()
}
```

### Success Flow
```kotlin
if (success) {
    SuccessRipple(onComplete = { success = false })
}
```

---

## 📱 Settings Toggles

```kotlin
SettingsSwitch(title = "Haptic Feedback", checked = hapticEnabled)
SettingsSwitch(title = "Celebrations", checked = celebrationsEnabled)
SettingsSwitch(title = "Surprise Moments", checked = delightEnabled)
SettingsSwitch(title = "Reduce Motion", checked = reduceMotion)
```

---

## ⚙️ Performance Targets

- **Frame Time**: <16.67ms (60fps)
- **Memory**: <100KB per effect
- **Battery**: Negligible impact
- **Response**: <50ms perceived

---

## ✅ Integration Checklist

- [ ] Add `buttonPressEffect()` to all buttons
- [ ] Add `cardHoverEffect()` to all cards
- [ ] Replace spinners with spiritual loaders
- [ ] Add celebrations to completions
- [ ] Implement personalized greetings
- [ ] Test haptics on physical device

---

## 🔗 Full Documentation

- **Implementation Guide**: `UX_FEEL_ENHANCEMENTS.md`
- **Delight Catalog**: `DELIGHT_MOMENTS_CATALOG.md`
- **Summary**: `UX_FEEL_IMPLEMENTATION_SUMMARY.md`

---

## 💡 Pro Tips

1. **Always test haptics on real device** (emulator doesn't support)
2. **Layer effects**: Combine haptic + visual + animation
3. **Keep it subtle**: Best interactions feel natural, not flashy
4. **Performance first**: Monitor frame rate during development
5. **Give users control**: Settings toggles for preferences

---

**Remember**: The feel is what users remember!

Make every interaction AMAZING! ✨
