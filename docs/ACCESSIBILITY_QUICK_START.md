# Accessibility Quick Start Guide

> Get started with WCAG 2.1 AAA accessibility in 5 minutes

## For Developers: Add Accessibility in 3 Steps

### Step 1: Import Utilities

```kotlin
import com.spiritatlas.core.ui.accessibility.*
import com.spiritatlas.core.ui.components.AccessibleButton
import com.spiritatlas.core.ui.components.AccessibleTextField
```

### Step 2: Use Accessible Components

```kotlin
// Replace regular Button
AccessibleButton(
    onClick = { /* action */ },
    text = "Create Profile",
    description = "Create a new spiritual profile"
)

// Replace regular TextField
AccessibleTextField(
    value = name,
    onValueChange = { name = it },
    label = "Full Name",
    required = true,
    helperText = "Your birth name for numerology"
)

// Replace regular IconButton
AccessibleIconButton(
    onClick = { /* action */ },
    contentDescription = "Settings"
) {
    Icon(Icons.Filled.Settings, contentDescription = null)
}
```

### Step 3: Add Semantic Information

```kotlin
// For custom components
Box(
    modifier = Modifier
        .size(48.dp)  // Minimum touch target
        .semantics {
            contentDescription = "Heart chakra, active"
            role = Role.Button
            stateDescription = "active"
        }
)
```

## Common Patterns

### Button with State
```kotlin
AccessibleButton(
    onClick = { enabled = !enabled },
    text = if (enabled) "Enabled" else "Disabled",
    description = "Toggle feature, currently ${if (enabled) "on" else "off"}",
    enabled = true
)
```

### Progress Indicator
```kotlin
Column(
    modifier = Modifier.semantics {
        contentDescription = "Profile completion: ${percentage}%"
        stateDescription = when {
            percentage >= 80 -> "almost complete"
            percentage >= 50 -> "halfway done"
            else -> "just started"
        }
    }
) {
    LinearProgressIndicator(progress = { percentage / 100f })
    Text("$percentage% Complete")
}
```

### List Item with Position
```kotlin
AccessibleListItem(
    title = profile.name,
    subtitle = profile.zodiacSign,
    onClick = { /* navigate */ },
    position = index + 1,
    total = profiles.size
)
// Announces: "Sarah, Sagittarius, item 1 of 3"
```

### Chakra Indicator
```kotlin
AccessibleChakraIndicator(
    chakraName = "Root",
    chakraIndex = 0,
    isActive = true,
    onClick = { /* show details */ }
)
// Announces: "Root chakra, number 1, active"
```

## Testing Your Changes

### 1. Automated Tests
```kotlin
@Test
fun testAccessibility() {
    composeTestRule.setContent {
        YourScreen()
    }

    // Run accessibility checks
    composeTestRule.onRoot().performAccessibilityChecks()

    // Verify touch targets
    composeTestRule
        .onNodeWithTag("button")
        .assertHeightIsAtLeast(48.dp)
        .assertWidthIsAtLeast(48.dp)
}
```

### 2. Manual TalkBack Testing
1. Enable TalkBack on device
2. Navigate through your screen
3. Verify all announcements are clear
4. Check logical reading order

### 3. Keyboard Navigation
1. Connect external keyboard
2. Press Tab to navigate
3. Press Enter to activate
4. Verify focus indicators visible

## Checklist

Before committing code, verify:
- [ ] All buttons are minimum 48dp
- [ ] All interactive elements have content descriptions
- [ ] Forms indicate required fields
- [ ] Error messages are clear and announced
- [ ] Progress/state changes are announced
- [ ] Focus indicators are visible
- [ ] Color contrast meets 7:1 ratio
- [ ] No information by color alone

## Common Mistakes to Avoid

### ❌ DON'T
```kotlin
// Too small
IconButton(
    onClick = { },
    modifier = Modifier.size(24.dp)  // Too small!
)

// No description
Icon(Icons.Filled.Star)

// Includes control type
contentDescription = "Settings button"  // TalkBack adds "button"

// Too verbose
contentDescription = "This button when pressed will open settings"
```

### ✅ DO
```kotlin
// Minimum size
IconButton(
    onClick = { },
    modifier = Modifier.size(48.dp)  // Perfect!
)

// Clear description
Icon(
    Icons.Filled.Star,
    contentDescription = "Favorite"
)

// Concise
contentDescription = "Settings"

// Action-oriented
contentDescription = "Open settings"
```

## Quick Reference

### Touch Targets
- Minimum: `48.dp x 48.dp`
- Use: `Modifier.minimumTouchTarget()`

### Content Descriptions
- Buttons: Action-oriented ("Create", "Save")
- Icons: Function ("Favorite", "Share")
- Images: What's shown ("Full moon")
- State: Include current state ("Dark mode, on")

### Semantic Roles
- `Role.Button` - Clickable actions
- `Role.Checkbox` - Toggle selections
- `Role.Switch` - On/off states
- `Role.Tab` - Navigation tabs
- `Role.Image` - Informational graphics

## Need Help?

### Documentation
- Full Guide: `/docs/ACCESSIBILITY_GUIDE.md`
- Testing: `/docs/ACCESSIBILITY_TESTING_CHECKLIST.md`
- TalkBack: `/docs/TALKBACK_USER_GUIDE.md`

### Code Examples
- Utilities: `/core/ui/accessibility/AccessibilityUtils.kt`
- Components: `/core/ui/components/AccessibilityComponents.kt`
- Focus: `/core/ui/accessibility/FocusManagement.kt`

### Questions?
- Email: accessibility@spiritatlas.com
- Slack: #accessibility channel

---

**Remember**: Accessibility is not optional. Every feature must be accessible from day one.

*"Build it right the first time. Build it accessible."*
