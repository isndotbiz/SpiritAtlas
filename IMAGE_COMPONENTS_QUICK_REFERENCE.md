# Image Components Quick Reference

**Fast lookup guide for SpiritAtlas image-related components**

---

## Backgrounds - Which One to Use?

### For Animated Screens (Home, Compatibility)
```kotlin
import com.spiritatlas.core.ui.components.StarfieldBackground

StarfieldBackground {
    // Your content here
}
```
✨ **Best for:** Home screens, dynamic dashboards
🎨 **Features:** Twinkling stars, nebula effects, smooth 60fps animation

---

### For Spiritual/Profile Screens
```kotlin
import com.spiritatlas.core.ui.components.SacredGeometryBackground

SacredGeometryBackground {
    // Your content here
}
```
✨ **Best for:** Profile library, meditation screens
🎨 **Features:** Flower of Life patterns, sacred geometry, subtle rotation

---

### For Relationship/Compatibility Screens
```kotlin
import com.spiritatlas.core.ui.components.CosmicConnectionBackground

CosmicConnectionBackground {
    // Your content here
}
```
✨ **Best for:** Compatibility analysis, relationship screens
🎨 **Features:** Dual energies flowing, particle effects, cosmic convergence

---

### For Static Screens (Splash, Onboarding)
```kotlin
import com.spiritatlas.core.ui.components.SimpleSpiritualBackground

SimpleSpiritualBackground(
    backgroundResourceId = R.drawable.spiritual_bg,
    alpha = 0.3f
) {
    // Your content here
}
```
✨ **Best for:** Splash screens, static onboarding pages
🎨 **Features:** Fast image loading, no caching overhead

---

### For Image-Heavy Screens (Gallery, Media)
```kotlin
import com.spiritatlas.core.ui.components.SpiritualBackgroundImage

SpiritualBackgroundImage(
    backgroundResourceId = R.drawable.large_bg,
    alpha = 0.3f
) {
    // Your content here
}
```
✨ **Best for:** Screens with large background images
🎨 **Features:** Coil caching, crossfade transitions, memory efficient

---

## Decorative Elements

### Sacred Geometry Corner
```kotlin
import com.spiritatlas.core.ui.components.SacredGeometryCorner

Box(modifier = Modifier.fillMaxWidth()) {
    SacredGeometryCorner(
        modifier = Modifier.align(Alignment.TopStart),
        color = SpiritualPurple.copy(alpha = 0.4f),
        size = 40.dp
    )
    // Your content
}
```
📍 **Use case:** Card decorations, section headers

---

### Spiritual Divider
```kotlin
import com.spiritatlas.core.ui.components.SpiritualDivider

Column {
    Text("Section 1")
    SpiritualDivider(thickness = 0.5.dp)
    Text("Section 2")
}
```
📍 **Use case:** Section separators in cards

---

## Moon & Zodiac

### Moon Phase Image
```kotlin
import com.spiritatlas.core.ui.components.MoonPhaseImage

MoonPhaseImage(
    phase = "Full Moon",  // or "New Moon", "Waxing Crescent", etc.
    size = 64.dp
)
```
🌙 **Supported phases:** New Moon, Waxing Crescent, First Quarter, Waxing Gibbous, Full Moon, Waning Gibbous, Last Quarter, Waning Crescent

---

## Interactive Components

### Pull to Refresh
```kotlin
import com.spiritatlas.core.ui.components.SpiritualPullRefresh

var isRefreshing by remember { mutableStateOf(false) }

SpiritualPullRefresh(
    isRefreshing = isRefreshing,
    onRefresh = {
        // Refresh logic
    }
) {
    LazyColumn {
        // Your content
    }
}
```
🔄 **Features:** Spiritual-themed indicator, haptic feedback

---

## Modifier Extensions

### Button Press Effect
```kotlin
import com.spiritatlas.core.ui.components.buttonPressEffect

Button(
    onClick = { },
    modifier = Modifier.buttonPressEffect()
) {
    Text("Press Me")
}
```
✨ **Effect:** Subtle scale animation on press

---

### Card Hover Effect
```kotlin
import com.spiritatlas.core.ui.components.cardHoverEffect

Card(
    modifier = Modifier.cardHoverEffect()
) {
    // Card content
}
```
✨ **Effect:** Elevation change on hover/press

---

### Spiritual Elevation
```kotlin
import com.spiritatlas.core.ui.components.spiritualElevation

Card(
    modifier = Modifier.spiritualElevation(level = 2)
) {
    // Card content
}
```
✨ **Effect:** Spiritual-colored shadow (levels 1-5)

---

### Staggered Entrance
```kotlin
import com.spiritatlas.core.ui.components.staggeredEntrance

items.forEachIndexed { index, item ->
    Card(
        modifier = Modifier.staggeredEntrance(index, delayMs = 100)
    ) {
        // Card content
    }
}
```
✨ **Effect:** Sequential fade-in animation

---

### Selection Highlight
```kotlin
import com.spiritatlas.core.ui.components.selectionHighlight

Card(
    modifier = Modifier.selectionHighlight(isSelected = selected)
) {
    // Card content
}
```
✨ **Effect:** Pulsing border when selected

---

## File Locations

| Component Type | File Path |
|----------------|-----------|
| Cosmic Backgrounds | `core/ui/components/CosmicBackgrounds.kt` |
| Image Backgrounds | `core/ui/components/ImageBackgrounds.kt` |
| Zodiac/Moon | `core/ui/components/ZodiacImageComponents.kt` |
| Visual Polish | `core/ui/components/VisualPolish.kt` |
| Pull Refresh | `core/ui/components/SpiritualPullRefresh.kt` |
| UX Modifiers | `core/ui/components/UXFeelEnhancements.kt` |

---

## Decision Tree

```
Need a background?
│
├─ Animated? → YES
│  ├─ Home/Dashboard → StarfieldBackground
│  ├─ Profile/Library → SacredGeometryBackground
│  └─ Compatibility → CosmicConnectionBackground
│
└─ Static Image? → YES
   ├─ Frequent recomposition → SimpleSpiritualBackground
   └─ Large images → SpiritualBackgroundImage (Coil cached)
```

---

## Common Patterns

### Pattern 1: Header with Corner Decoration
```kotlin
Box(modifier = Modifier.fillMaxWidth()) {
    SacredGeometryCorner(
        modifier = Modifier.align(Alignment.TopStart),
        color = SpiritualPurple.copy(alpha = 0.4f),
        size = 40.dp
    )

    GlassmorphCard {
        // Header content
    }
}
```

---

### Pattern 2: Animated Background + Content
```kotlin
StarfieldBackground {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = { /* Top bar */ }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            // Content
        }
    }
}
```

---

### Pattern 3: Card List with Staggered Entrance
```kotlin
LazyColumn {
    itemsIndexed(items) { index, item ->
        Card(
            modifier = Modifier
                .staggeredEntrance(index)
                .cardHoverEffect()
        ) {
            // Card content
        }
    }
}
```

---

## Performance Tips

1. **Use Canvas backgrounds** for animated screens (60fps optimized)
2. **Use SimpleSpiritualBackground** for static screens (no caching overhead)
3. **Use `remember` for stable data** in animated components
4. **Avoid nesting multiple backgrounds** (pick one per screen)
5. **Test on real devices** for animation smoothness

---

## See Also

- Full inventory: `IMAGE_COMPONENTS_INVENTORY.md`
- Cosmic backgrounds guide: `docs/archive/visual-design/COSMIC_BACKGROUNDS_GUIDE.md`
- Pull refresh readme: `core/ui/components/SpiritualPullRefreshReadme.md`
