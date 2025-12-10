# Visual Polish - Quick Reference

## Import Statement

```kotlin
import com.spiritatlas.core.ui.components.*
```

## Common Patterns

### 1. Screen Entry Animation

```kotlin
@Composable
fun MyScreen() {
    val visible = rememberScreenEntranceAnimation()

    Column(
        modifier = Modifier.screenEntrance(visible = visible)
    ) {
        // Screen content
    }
}
```

### 2. Staggered List Items

```kotlin
LazyColumn {
    itemsIndexed(items) { index, item ->
        ItemCard(
            modifier = Modifier.staggeredEntrance(index, baseDelay = 50)
        )
    }
}
```

### 3. Enhanced Card

```kotlin
EnhancedGlassmorphCard(
    elevation = 2,
    glowColor = SpiritualPurple,
    enableGlow = true
) {
    // Card content
}
```

### 4. Gradient Header

```kotlin
GradientText(
    text = "My Title",
    gradient = listOf(SpiritualPurple, MysticViolet, CosmicBlue),
    style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold
    )
)
```

### 5. Section with Decoration

```kotlin
Box(modifier = Modifier.fillMaxWidth()) {
    SacredGeometryCorner(
        modifier = Modifier.align(Alignment.TopStart),
        color = SpiritualPurple.copy(alpha = 0.4f)
    )

    Column {
        // Section content
    }
}
```

### 6. Spiritual Divider

```kotlin
SpiritualDivider(
    modifier = Modifier.fillMaxWidth(),
    thickness = 0.5.dp
)
```

### 7. Interactive Button

```kotlin
var pressed by remember { mutableStateOf(false) }

Button(
    onClick = { /* action */ },
    modifier = Modifier.buttonPressEffect(pressed = pressed)
) {
    Text("Click Me")
}
```

### 8. Hover Effect Card

```kotlin
var hovered by remember { mutableStateOf(false) }

Card(
    modifier = Modifier
        .cardHoverEffect(hovered = hovered)
        .pointerInput(Unit) {
            detectHover { hovered = it }
        }
) {
    // Content
}
```

### 9. Loading Indicator

```kotlin
if (isLoading) {
    SpiritualLoadingIndicator(
        message = "Loading..."
    )
}
```

### 10. Success/Error States

```kotlin
when (state) {
    is Success -> SuccessIndicator(message = "Saved!")
    is Error -> ErrorIndicator(message = "Failed")
}
```

## Modifier Cheat Sheet

| Modifier | Purpose | Example |
|----------|---------|---------|
| `.screenEntrance()` | Screen fade-in | `Modifier.screenEntrance(visible = true)` |
| `.staggeredEntrance()` | List item animation | `Modifier.staggeredEntrance(index = 0)` |
| `.cardHoverEffect()` | Card scale on hover | `Modifier.cardHoverEffect(hovered = true)` |
| `.buttonPressEffect()` | Button press feedback | `Modifier.buttonPressEffect(pressed = true)` |
| `.softGlow()` | Static glow | `Modifier.softGlow(glowColor = Purple)` |
| `.pulsingGlow()` | Animated glow | `Modifier.pulsingGlow(glowColor = Gold)` |
| `.borderGlow()` | Border highlight | `Modifier.borderGlow(glowColor = Blue)` |
| `.spiritualElevation()` | Shadow depth | `Modifier.spiritualElevation(level = 2)` |
| `.selectionHighlight()` | Selection state | `Modifier.selectionHighlight(selected = true)` |
| `.focusRing()` | Focus indicator | `Modifier.focusRing(focused = true)` |

## Component Cheat Sheet

| Component | Purpose | Usage |
|-----------|---------|-------|
| `EnhancedGlassmorphCard` | Premium card | `EnhancedGlassmorphCard(elevation = 2) { }` |
| `GradientText` | Colorful headers | `GradientText(text = "Title", gradient = colors)` |
| `SacredGeometryCorner` | Corner decoration | `SacredGeometryCorner(color = Purple)` |
| `SpiritualDivider` | Section separator | `SpiritualDivider(thickness = 0.5.dp)` |
| `GoldenRatioAccent` | Accent line | `GoldenRatioAccent(color = Gold)` |
| `SuccessIndicator` | Success state | `SuccessIndicator(message = "Done!")` |
| `ErrorIndicator` | Error state | `ErrorIndicator(message = "Error")` |
| `SpiritualLoadingIndicator` | Loading state | `SpiritualLoadingIndicator(message = "Loading")` |
| `DotPatternBackground` | Subtle texture | `DotPatternBackground(spacing = 32.dp)` |
| `GeometricGridPattern` | Grid background | `GeometricGridPattern(spacing = 48.dp)` |

## Spacing Guide (4dp Grid)

```kotlin
4.dp   // Extra tight
8.dp   // Tight
12.dp  // Cozy
16.dp  // Standard
20.dp  // Comfortable
24.dp  // Spacious
32.dp  // Large
48.dp  // Extra large
```

## Color Palette

```kotlin
// Primary
SpiritualPurple
MysticViolet
CosmicBlue

// Accent
AuraGold
TantricRose

// Semantic
ChakraGreen   // Success
ChakraRed     // Error
ChakraOrange  // Warning
CosmicBlue    // Info
```

## Animation Durations

```kotlin
200 // Quick (micro-interactions)
400 // Medium (standard transitions)
600 // Slow (dramatic reveals)
2000 // Breathing (calm pulsing)
3000 // Floating (gentle movement)
```

## Elevation Levels

```kotlin
level = 1  // 4dp - Subtle
level = 2  // 8dp - Standard
level = 3  // 16dp - High
```

## Copy-Paste Templates

### Full Screen Template

```kotlin
@Composable
fun MyScreen(onBack: () -> Unit) {
    val visible = rememberScreenEntranceAnimation()

    SimpleSpiritualBackground(
        backgroundResourceId = R.drawable.background,
        alpha = 0.15f
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Screen") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .screenEntrance(visible = visible),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Content items
            }
        }
    }
}
```

### Enhanced Section Template

```kotlin
item {
    Box(modifier = Modifier.fillMaxWidth()) {
        SacredGeometryCorner(
            modifier = Modifier.align(Alignment.TopStart),
            color = SpiritualPurple.copy(alpha = 0.4f)
        )

        EnhancedGlassmorphCard(
            elevation = 2,
            glowColor = SpiritualPurple,
            modifier = Modifier.spiritualElevation(level = 2)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Header
                GradientText(
                    text = "Section Title",
                    gradient = listOf(SpiritualPurple, MysticViolet),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                SpiritualDivider(thickness = 0.5.dp)

                // Content
                Text(
                    text = "Section content here",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
```

### Interactive Card Template

```kotlin
var isHovered by remember { mutableStateOf(false) }
var isPressed by remember { mutableStateOf(false) }

Card(
    onClick = { /* action */ },
    modifier = Modifier
        .fillMaxWidth()
        .cardHoverEffect(hovered = isHovered)
        .buttonPressEffect(pressed = isPressed)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    tryAwaitRelease()
                    isPressed = false
                },
                onTap = { /* click handler */ }
            )
        }
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon with glow
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SpiritualPurple.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Star, "Icon", tint = AuraGold)
        }

        // Text content
        Column {
            Text(
                text = "Card Title",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Card description",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

## Pro Tips

1. **Combine Effects**: Layer multiple modifiers for rich interactions
2. **Respect 4dp Grid**: Always use multiples of 4 for spacing
3. **Use Gradient Text**: For headers and important titles
4. **Add Corner Decorations**: To hero sections and key cards
5. **Stagger Animations**: For lists with 50ms delays
6. **Test on Slow Devices**: Ensure 60fps performance
7. **Provide Fallbacks**: Disable effects on low-end devices
8. **Consider Accessibility**: Always add focus rings and reduce motion support

---

**Quick Tip**: Start with the screen template, add enhanced cards, sprinkle decorations, and animate!
