# SpiritAtlas Haptic Integration Guide

This guide shows exactly where and how to add haptic feedback to existing SpiritAtlas screens for a premium user experience.

## Navigation & Top Bars

### MainActivity Navigation
**File:** `app/src/main/java/com/spiritatlas/app/MainActivity.kt`

```kotlin
// Add to NavigationBar items
NavigationBarItem(
    selected = currentRoute == screen.route,
    onClick = {
        if (currentRoute != screen.route) {
            haptic.performHaptic(HapticFeedbackType.SELECTION)
            navController.navigate(screen.route)
        }
    },
    icon = { Icon(screen.icon, contentDescription = screen.label) },
    label = { Text(screen.label) }
)
```

### ProfileScreen TopBar
**File:** `feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt`

```kotlin
@Composable
fun ProfileTopBar(...) {
    val haptic = rememberHapticFeedback()

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                haptic.performHaptic(HapticFeedbackType.MEDIUM)
                onNavigateBack()
            }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
        },
        actions = {
            // Save button
            IconButton(
                onClick = {
                    haptic.performHaptic(HapticFeedbackType.SUCCESS)
                    onSave()
                },
                enabled = !isSaving
            ) {
                Icon(Icons.Default.Save, "Save")
            }

            // Clear button
            IconButton(onClick = {
                haptic.performHaptic(HapticFeedbackType.WARNING)
                onClear()
            }) {
                Icon(Icons.Default.Clear, "Clear")
            }
        }
    )
}
```

## Profile Screens

### Tab Selection
**File:** `feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt`

```kotlin
@Composable
fun ProfileSectionTabs(...) {
    val haptic = rememberHapticFeedback()

    ScrollableTabRow(
        selectedTabIndex = activeSection.ordinal
    ) {
        ProfileSection.values().forEach { section ->
            Tab(
                selected = activeSection == section,
                onClick = {
                    if (activeSection != section) {
                        haptic.performHaptic(HapticFeedbackType.SELECTION)
                        onSectionSelected(section)
                    }
                },
                text = { Text(section.displayName) }
            )
        }
    }
}
```

### Form Fields
**File:** `feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt`

```kotlin
@Composable
fun CoreIdentitySection(...) {
    val haptic = rememberHapticFeedback()

    // Dropdowns with haptic feedback
    ModernDropdown(
        selectedValue = profile.gender,
        onValueChange = { newValue ->
            haptic.performHaptic(HapticFeedbackType.SELECTION)
            onProfileUpdate(profile.copy(gender = newValue))
        },
        options = Gender.values().toList(),
        label = "Gender"
    )

    // Date picker with haptic
    DateTimePicker(
        value = profile.birthDateTime,
        onValueChange = { newDateTime ->
            haptic.performHaptic(HapticFeedbackType.LIGHT)
            onProfileUpdate(profile.copy(birthDateTime = newDateTime))
        },
        label = "Birth Date & Time"
    )
}
```

### Ayurveda Section
**File:** Add to Ayurveda profile section

```kotlin
@Composable
fun AyurvedaSection(...) {
    val haptic = rememberHapticFeedback()

    // Dosha selection chips
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Dosha.values().forEach { dosha ->
            FilterChip(
                selected = profile.dominantDosha == dosha,
                onClick = {
                    haptic.performHaptic(HapticFeedbackType.SELECTION)
                    onProfileUpdate(profile.copy(dominantDosha = dosha))
                },
                label = { Text(dosha.name) }
            )
        }
    }
}
```

### Chakra Selection
**File:** Add to Chakra section

```kotlin
@Composable
fun ChakraSection(...) {
    val haptic = rememberHapticFeedback()

    // Chakra indicators
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        (0..6).forEach { chakraIndex ->
            ChakraIndicator(
                chakraIndex = chakraIndex,
                isActive = profile.activeChakras.contains(chakraIndex),
                modifier = Modifier
                    .clickable {
                        haptic.performHaptic(HapticFeedbackType.LIGHT)
                        toggleChakra(chakraIndex)
                    }
            )
        }
    }
}
```

## Compatibility Screen

### Calculate Button
**File:** `feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityScreen.kt`

```kotlin
@Composable
fun CompatibilityScreen(...) {
    val haptic = rememberHapticFeedback()

    ModernButton(
        text = "Calculate Compatibility",
        onClick = {
            haptic.performHaptic(HapticFeedbackType.MEDIUM)
            onCalculate()
        },
        enabled = canCalculate,
        variant = ButtonVariant.Spiritual
    )

    // Show success haptic when results appear
    LaunchedEffect(compatibilityScore) {
        compatibilityScore?.let { score ->
            when {
                score >= 80 -> haptic.performHaptic(HapticFeedbackType.SUCCESS)
                score < 40 -> haptic.performHaptic(HapticFeedbackType.WARNING)
                else -> haptic.performHaptic(HapticFeedbackType.MEDIUM)
            }
        }
    }
}
```

### Profile Selection
**File:** `feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityScreen.kt`

```kotlin
@Composable
fun ProfileSelector(...) {
    val haptic = rememberHapticFeedback()

    LazyColumn {
        items(profiles) { profile ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHaptic(HapticFeedbackType.SELECTION)
                        onProfileSelected(profile)
                    }
            ) {
                // Profile content
            }
        }
    }
}
```

## Tantric Features

### Tantric Type Selection
**File:** `feature/tantric/...`

```kotlin
@Composable
fun TantricTypeSelector(...) {
    val haptic = rememberHapticFeedback()

    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TantricType.values().forEach { type ->
            TantricTypeChip(
                tantricType = type.name,
                isSelected = selectedType == type,
                onClick = {
                    haptic.performHaptic(HapticFeedbackType.SELECTION)
                    onTypeSelected(type)
                }
            )
        }
    }
}
```

## Home Screen

### Quick Actions
**File:** `feature/home/...`

```kotlin
@Composable
fun HomeScreen(...) {
    val haptic = rememberHapticFeedback()

    // FAB for new profile
    FloatingActionButton(
        onClick = {
            haptic.performHaptic(HapticFeedbackType.MEDIUM)
            navigateToNewProfile()
        }
    ) {
        Icon(Icons.Default.Add, "New Profile")
    }

    // Quick action cards
    Card(
        onClick = {
            haptic.performHaptic(HapticFeedbackType.MEDIUM)
            navigateToCompatibility()
        }
    ) {
        // Card content
    }
}
```

## Consent Screen

### Acceptance
**File:** `feature/consent/...`

```kotlin
@Composable
fun ConsentScreen(...) {
    val haptic = rememberHapticFeedback()

    // Checkbox for terms
    Row(
        modifier = Modifier.clickable {
            haptic.performHaptic(HapticFeedbackType.SELECTION)
            onTermsToggle(!termsAccepted)
        }
    ) {
        Checkbox(
            checked = termsAccepted,
            onCheckedChange = {
                haptic.performHaptic(HapticFeedbackType.SELECTION)
                onTermsToggle(it)
            }
        )
        Text("I accept the terms and conditions")
    }

    // Accept button
    Button(
        onClick = {
            haptic.performHaptic(HapticFeedbackType.SUCCESS)
            onAccept()
        },
        enabled = termsAccepted
    ) {
        Text("Accept & Continue")
    }
}
```

## Settings Screen (To Be Created)

### Haptic Settings Section

```kotlin
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val preferences = remember { HapticPreferences(context) }
    val haptic = rememberHapticFeedback()

    var hapticsEnabled by remember { mutableStateOf(preferences.isHapticEnabled()) }
    var hapticIntensity by remember { mutableStateOf(preferences.getHapticIntensity()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Haptic Feedback",
            style = MaterialTheme.typography.titleLarge
        )

        // Enable/Disable
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Enable Haptic Feedback")
            Switch(
                checked = hapticsEnabled,
                onCheckedChange = {
                    hapticsEnabled = it
                    preferences.setHapticEnabled(it)
                    if (it) haptic.performHaptic(HapticFeedbackType.SELECTION)
                }
            )
        }

        AnimatedVisibility(visible = hapticsEnabled) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Intensity: ${(hapticIntensity * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium
                )

                Slider(
                    value = hapticIntensity,
                    onValueChange = { hapticIntensity = it },
                    onValueChangeFinished = {
                        preferences.setHapticIntensity(hapticIntensity)
                        haptic.performHaptic(HapticFeedbackType.MEDIUM)
                    }
                )

                Text(
                    text = "Adjust the strength of haptic feedback throughout the app",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Test button
                Button(
                    onClick = {
                        haptic.performHaptic(HapticFeedbackType.MEDIUM)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Test Haptic Feedback")
                }
            }
        }
    }
}
```

## Common Patterns

### 1. List Item Selection

```kotlin
LazyColumn {
    items(items) { item ->
        ListItem(
            modifier = Modifier.clickable {
                haptic.performHaptic(HapticFeedbackType.SELECTION)
                onItemSelected(item)
            },
            headlineContent = { Text(item.title) }
        )
    }
}
```

### 2. Dialog Actions

```kotlin
AlertDialog(
    onDismissRequest = {
        haptic.performHaptic(HapticFeedbackType.MEDIUM)
        onDismiss()
    },
    confirmButton = {
        TextButton(onClick = {
            haptic.performHaptic(HapticFeedbackType.SUCCESS)
            onConfirm()
        }) {
            Text("Confirm")
        }
    },
    dismissButton = {
        TextButton(onClick = {
            haptic.performHaptic(HapticFeedbackType.MEDIUM)
            onDismiss()
        }) {
            Text("Cancel")
        }
    }
)
```

### 3. Error States

```kotlin
// Show error with haptic
LaunchedEffect(errorMessage) {
    errorMessage?.let {
        haptic.performHaptic(HapticFeedbackType.ERROR)
        snackbarHostState.showSnackbar(it)
    }
}

// Success with haptic
LaunchedEffect(successMessage) {
    successMessage?.let {
        haptic.performHaptic(HapticFeedbackType.SUCCESS)
        snackbarHostState.showSnackbar(it)
    }
}
```

### 4. Long Press Actions

```kotlin
Box(
    modifier = Modifier
        .hapticLongPress(
            hapticType = HapticFeedbackType.HEAVY,
            onClick = { /* Regular tap */ },
            onLongPress = {
                // Show context menu
                showContextMenu = true
            }
        )
) {
    // Content
}
```

## Priority Order for Integration

### Phase 1: High-Traffic Screens (Start Here)
1. Navigation bar items
2. Profile save button
3. Tab selections
4. Back navigation

### Phase 2: Form Interactions
1. Dropdown selections
2. Toggle switches
3. Checkboxes
4. Radio buttons

### Phase 3: Advanced Interactions
1. Long press menus
2. Swipe actions
3. Drag & drop
4. Multi-select

### Phase 4: Feedback States
1. Success messages
2. Error states
3. Warning dialogs
4. Loading completions

## Testing Checklist

After adding haptics to each screen:

- [ ] Navigation feels responsive
- [ ] Form interactions have appropriate feedback
- [ ] Success/error states are distinct
- [ ] Haptics can be disabled in settings
- [ ] Intensity slider affects all haptics
- [ ] No haptic feedback spam on rapid taps
- [ ] Battery impact is minimal
- [ ] Works on different Android versions (8.0+)

## Performance Tips

1. **Reuse HapticController**: Use `rememberHapticFeedback()` once per screen
2. **Avoid Rapid Haptics**: Don't trigger on every scroll/drag event
3. **Check State Changes**: Only haptic when state actually changes
4. **Use Appropriate Types**: LIGHT for frequent, HEAVY for rare actions

## Common Mistakes to Avoid

❌ **Don't**: Add haptics to every interaction
```kotlin
LazyColumn {
    items(1000) { item ->
        // This will be too much!
        Box(modifier = Modifier.hapticClickable { ... })
    }
}
```

✅ **Do**: Add haptics to intentional selections
```kotlin
LazyColumn {
    items(profiles) { profile ->
        ProfileCard(
            onClick = {
                haptic.performHaptic(HapticFeedbackType.SELECTION)
                selectProfile(profile)
            }
        )
    }
}
```

❌ **Don't**: Use wrong semantic types
```kotlin
Button(onClick = {
    haptic.performHaptic(HapticFeedbackType.ERROR) // Wrong!
    saveData()
})
```

✅ **Do**: Use semantic types correctly
```kotlin
Button(onClick = {
    haptic.performHaptic(HapticFeedbackType.SUCCESS) // Correct!
    saveData()
})
```

## Quick Reference Card

```
Action Type          → Haptic Type
────────────────────────────────────
Back/Cancel          → MEDIUM
Save/Submit          → SUCCESS
Delete/Clear         → WARNING
Error Occurred       → ERROR
Tab Switch           → SELECTION
Toggle Switch        → SELECTION
Dropdown Select      → SELECTION
Form Input           → LIGHT
Button Click         → MEDIUM
Long Press           → HEAVY
Dialog Open          → LIGHT
Context Menu         → CONTEXT_CLICK
```

## Next Steps

1. Start with Phase 1 (high-traffic screens)
2. Test on a physical device
3. Gather feedback from users
4. Iterate on haptic types/intensities
5. Expand to remaining screens

---

**Ready to enhance SpiritAtlas with premium haptic feedback!**
