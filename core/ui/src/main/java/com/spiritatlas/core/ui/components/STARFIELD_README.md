# StarField Component

## Overview

`StarField` is a high-performance animated background component that creates a beautiful twinkling star field with parallax layers. It's optimized for 60 FPS performance and perfect for creating cosmic/spiritual atmospheric backgrounds.

## Features

- **Three Parallax Layers**: Far, mid, and near layers with different speeds, sizes, and opacity
- **Twinkling Animation**: Smooth, continuous twinkling effect using sine wave animation
- **Parallax Movement**: Optional subtle horizontal parallax movement for depth
- **Performance Optimized**:
  - Pre-generated star positions (computed once)
  - Single Canvas composable (no overdraw)
  - Efficient math operations
  - Minimal allocations per frame
  - Targets 60 FPS on most devices

## Usage

### Basic Usage

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
) {
    StarField(modifier = Modifier.fillMaxSize())

    // Your content here
}
```

### Customized Configuration

```kotlin
StarField(
    modifier = Modifier.fillMaxSize(),
    starCount = 150,              // Total stars across all layers
    twinkleSpeed = 2.5f,          // Speed multiplier for twinkling
    color = SpiritualPurple,      // Custom star color
    enableParallax = true         // Enable parallax movement
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `modifier` | Modifier | Modifier | Modifier for the canvas |
| `starCount` | Int | 100 | Total number of stars across all layers |
| `twinkleSpeed` | Float | 2f | Speed multiplier for twinkle animation (higher = faster) |
| `color` | Color | MoonlightSilver | Base color for the stars |
| `enableParallax` | Boolean | true | Enable/disable parallax movement effect |

## Parallax Layers

Stars are automatically distributed across three layers:

### Far Layer (50% of stars)
- Speed: 0.3x (slow)
- Size: 0.8x (small)
- Opacity: 0.4 (dim)

### Mid Layer (30% of stars)
- Speed: 0.6x (medium)
- Size: 1.0x (medium)
- Opacity: 0.6 (medium)

### Near Layer (20% of stars)
- Speed: 1.0x (fast)
- Size: 1.3x (large)
- Opacity: 0.8 (bright)

## Performance Tips

### Recommended Star Counts
- **High-end devices**: 150-200 stars
- **Mid-range devices**: 80-120 stars (default: 100)
- **Low-end devices**: 30-80 stars

### Performance Optimization
- Use lower star counts for text-heavy screens
- Disable parallax (`enableParallax = false`) if experiencing performance issues
- Combine with static backgrounds instead of gradients when needed

## Examples

See `StarFieldExample.kt` for comprehensive examples including:

1. **Basic StarField** - Default configuration
2. **Dense StarField** - High star count with fast twinkle
3. **Colored StarField** - Custom color variations
4. **Static StarField** - No parallax movement
5. **StarField with Gradient** - Combined with gradient backgrounds
6. **Minimal StarField** - Sparse, slow animation
7. **Production Example** - Full screen spiritual background

## Best Practices

### Background Color
Always use a dark background behind StarField for best visual effect:

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.Black) // Dark background required
) {
    StarField(modifier = Modifier.fillMaxSize())
}
```

### Gradient Combinations
For rich visual depth, combine with cosmic gradients:

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(SpiritualGradients.cosmicNight)
) {
    StarField(
        modifier = Modifier.fillMaxSize(),
        color = MoonlightSilver
    )
}
```

### Static vs Dynamic
Use static mode for screens with heavy content:

```kotlin
// Static mode for text-heavy screens
StarField(
    modifier = Modifier.fillMaxSize(),
    enableParallax = false,  // Disable movement
    starCount = 60           // Lower star count
)
```

## Integration with Existing Codebase

### Related Components

The codebase already has two similar components:

1. **`StarFieldBackground`** in `ZodiacAnimations.kt`
   - Simpler implementation without parallax
   - Used for zodiac constellation backgrounds

2. **`StarField`** in `SplashScreen.kt`
   - Inline implementation in splash screen
   - No parallax layers

The new `StarField` component supersedes these with:
- Better performance optimization
- Parallax layers for depth
- More configuration options
- Reusable across the app

### Migration Guide

To migrate from old implementations:

**From `StarFieldBackground` (ZodiacAnimations.kt):**
```kotlin
// Old
StarFieldBackground(
    modifier = Modifier.fillMaxSize(),
    starCount = 100,
    color = MoonlightSilver
)

// New
StarField(
    modifier = Modifier.fillMaxSize(),
    starCount = 100,
    color = MoonlightSilver
)
```

**From inline SplashScreen StarField:**
```kotlin
// Old (inline in SplashScreen.kt)
StarField(
    modifier = Modifier.fillMaxSize(),
    visible = animationProgress >= 0f
)

// New
import com.spiritatlas.core.ui.components.StarField

if (animationProgress >= 0f) {
    StarField(modifier = Modifier.fillMaxSize())
}
```

## File Location

- **Component**: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/StarField.kt`
- **Examples**: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/StarFieldExample.kt`
- **Documentation**: This file

## Testing

Build verification:
```bash
./gradlew :core:ui:compileDebugKotlin
```

## Version History

- **v1.0** (2025-12-10): Initial implementation
  - Three parallax layers
  - Twinkling animation
  - Performance optimizations
  - Comprehensive documentation and examples

## License

Part of the SpiritAtlas project.
