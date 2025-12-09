# Gradient Text Components Guide

Beautiful gradient text components for SpiritAtlas headings and titles with spiritual aesthetics and smooth animations.

## Overview

The `GradientText.kt` file provides 7 stunning text components with gradient and animation effects:

1. **GradientText** - Basic text with horizontal/vertical/diagonal gradients
2. **AnimatedGradientText** - Gradient that flows/shifts continuously
3. **ShimmerText** - Text with moving shimmer highlight
4. **GlowingText** - Text with outer glow effect
5. **TypewriterText** - Text that types in character by character
6. **FadeInText** - Text that fades in word by word
7. **CosmicText** - Text with animated starfield background

## File Location

```
core/ui/src/main/java/com/spiritatlas/core/ui/components/GradientText.kt
```

## Spiritual Gradient Presets

Pre-defined gradient color palettes for spiritual aesthetics:

```kotlin
object SpiritualGradients {
    val cosmicPurple: List<Color>      // Purple to blue cosmic gradient
    val mysticGold: List<Color>        // Golden mystical gradient
    val chakraRainbow: List<Color>     // 7 chakra colors
    val twilight: List<Color>          // Deep purple to magenta
    val aurora: List<Color>            // Cyan to green to yellow
    val fireEnergy: List<Color>        // Orange to red to yellow
    val waterFlow: List<Color>         // Deep blue to light blue
    val celestialSilver: List<Color>   // Silver metallic gradient
    val tantricPassion: List<Color>    // Rose to coral to purple
    val divineLight: List<Color>       // White to gold to purple
}
```

---

## Components Reference

### 1. GradientText

Basic text with customizable gradient direction.

**Usage:**
```kotlin
GradientText(
    text = "Discover Your Path",
    gradient = SpiritualGradients.cosmicPurple,
    style = MaterialTheme.typography.headlineLarge,
    gradientDirection = GradientDirection.Horizontal
)
```

**Parameters:**
- `text: String` - Text to display
- `gradient: List<Color>` - List of colors for gradient
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text style (size, weight, etc.)
- `gradientDirection: GradientDirection` - Direction: `Horizontal`, `Vertical`, or `Diagonal`

**Best for:**
- Section headers
- Page titles
- Static decorative text

---

### 2. AnimatedGradientText

Gradient that animates continuously, creating a flowing color effect.

**Usage:**
```kotlin
AnimatedGradientText(
    text = "Spiritual Atlas",
    gradient = SpiritualGradients.chakraRainbow,
    style = MaterialTheme.typography.displayMedium,
    animationDuration = 3000,
    animationDirection = AnimationDirection.LeftToRight
)
```

**Parameters:**
- `text: String` - Text to display
- `gradient: List<Color>` - Gradient colors
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text styling
- `animationDuration: Int` - Duration in milliseconds (default: 3000)
- `animationDirection: AnimationDirection` - `LeftToRight`, `RightToLeft`, `TopToBottom`, or `BottomToTop`

**Best for:**
- Hero titles
- App logo/branding
- Attention-grabbing headers
- Feature highlights

---

### 3. ShimmerText

Text with a moving shimmer highlight that sweeps across.

**Usage:**
```kotlin
ShimmerText(
    text = "Shimmering Light",
    baseColors = listOf(SpiritualPurple, MysticViolet),
    shimmerColor = Color.White,
    style = MaterialTheme.typography.headlineLarge,
    shimmerDuration = 2000
)
```

**Parameters:**
- `text: String` - Text to display
- `baseColors: List<Color>` - Base gradient colors (default: purple shades)
- `shimmerColor: Color` - Color of shimmer highlight (default: White)
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text styling
- `shimmerDuration: Int` - Shimmer sweep duration in ms (default: 2000)

**Best for:**
- Call-to-action text
- Premium features
- Special announcements
- Loading indicators

---

### 4. GlowingText

Text with animated or static outer glow effect.

**Usage:**
```kotlin
GlowingText(
    text = "Illuminated Wisdom",
    textColor = Color.White,
    glowColor = SpiritualPurple,
    style = MaterialTheme.typography.displaySmall,
    animateGlow = true,
    glowRadius = 20f
)
```

**Parameters:**
- `text: String` - Text to display
- `textColor: Color` - Main text color (default: White)
- `glowColor: Color` - Glow effect color (default: SpiritualPurple)
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text styling
- `animateGlow: Boolean` - Whether to animate glow intensity (default: true)
- `glowRadius: Float` - Size of glow effect (default: 20f)

**Best for:**
- Mystical quotes
- Sacred text
- Emphasis text
- Dark backgrounds
- Meditation/spiritual content

---

### 5. TypewriterText

Text that types in character by character with optional cursor.

**Usage:**
```kotlin
TypewriterText(
    text = "Your destiny unfolds...",
    gradient = SpiritualGradients.cosmicPurple, // Optional
    style = MaterialTheme.typography.headlineMedium,
    characterDelayMs = 80,
    showCursor = true,
    onComplete = {
        println("Animation complete!")
    }
)
```

**Parameters:**
- `text: String` - Text to type out
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text styling
- `textColor: Color` - Text color (if not using gradient)
- `gradient: List<Color>?` - Optional gradient (overrides textColor)
- `characterDelayMs: Long` - Delay between characters (default: 50)
- `showCursor: Boolean` - Show blinking cursor (default: true)
- `onComplete: (() -> Unit)?` - Callback when typing completes

**Best for:**
- Loading messages
- Storytelling
- Guided experiences
- Tutorial text
- Dynamic quotes

---

### 6. FadeInText

Text that fades in word by word with smooth transitions.

**Usage:**
```kotlin
FadeInText(
    text = "Welcome to your spiritual journey",
    gradient = SpiritualGradients.divineLight, // Optional
    style = MaterialTheme.typography.headlineMedium,
    wordDelayMs = 150,
    onComplete = {
        println("Fade in complete!")
    }
)
```

**Parameters:**
- `text: String` - Text to fade in
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text styling
- `textColor: Color` - Text color (if not using gradient)
- `gradient: List<Color>?` - Optional gradient colors
- `wordDelayMs: Long` - Delay between words (default: 150)
- `onComplete: (() -> Unit)?` - Callback when complete

**Best for:**
- Welcome messages
- Introduction screens
- Gentle reveals
- Poetic content
- Meditation instructions

---

### 7. CosmicText

Text with animated starfield background clipped to letters.

**Usage:**
```kotlin
CosmicText(
    text = "Starfield Magic",
    style = MaterialTheme.typography.displaySmall,
    starCount = 80,
    animationSpeed = 0.5f
)
```

**Parameters:**
- `text: String` - Text to display
- `modifier: Modifier` - Optional modifier
- `style: TextStyle` - Text styling
- `starCount: Int` - Number of stars (default: 50)
- `animationSpeed: Float` - Star twinkle speed (default: 0.5f)

**Best for:**
- App branding
- Cosmic/space themes
- Premium features
- Hero sections
- Special events

---

## Quick Start Examples

### Hero Title
```kotlin
AnimatedGradientText(
    text = "Transform Your Spirit",
    gradient = SpiritualGradients.cosmicPurple,
    style = MaterialTheme.typography.displayLarge.copy(
        fontWeight = FontWeight.ExtraBold
    ),
    animationDuration = 4000
)
```

### Section Header
```kotlin
GradientText(
    text = "Meditation Practices",
    gradient = SpiritualGradients.twilight,
    style = MaterialTheme.typography.headlineLarge.copy(
        fontWeight = FontWeight.Bold
    )
)
```

### Loading State
```kotlin
TypewriterText(
    text = "Analyzing your spiritual profile...",
    gradient = SpiritualGradients.aurora,
    style = MaterialTheme.typography.titleLarge,
    characterDelayMs = 50
)
```

### Welcome Message
```kotlin
FadeInText(
    text = "Welcome back, seeker of truth",
    gradient = SpiritualGradients.divineLight,
    style = MaterialTheme.typography.headlineMedium
)
```

### Call to Action
```kotlin
ShimmerText(
    text = "Begin Your Journey Today",
    baseColors = SpiritualGradients.fireEnergy,
    style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.SemiBold
    )
)
```

---

## Design Guidelines

### When to Use Each Component

| Component | Use Case | Best For |
|-----------|----------|----------|
| **GradientText** | Static headers | Section titles, labels |
| **AnimatedGradientText** | Hero sections | Main headings, branding |
| **ShimmerText** | CTAs | Buttons, special features |
| **GlowingText** | Emphasis | Quotes, sacred text |
| **TypewriterText** | Dynamic content | Loading, storytelling |
| **FadeInText** | Introductions | Welcome messages, reveals |
| **CosmicText** | Premium branding | App logo, special sections |

### Performance Considerations

- **Animations consume resources** - Use sparingly on one screen
- **Limit simultaneous animations** - Max 2-3 animated text components visible at once
- **Consider disabling on lower-end devices** - Provide static fallback option
- **Star count affects performance** - CosmicText with 100+ stars may lag on some devices

### Accessibility

- **Text must remain readable** - Ensure sufficient contrast
- **Provide non-animated alternatives** - Respect reduced motion preferences
- **Don't rely solely on color** - Text should be understandable without color
- **Test with screen readers** - Ensure text content is properly announced

---

## Advanced Usage

### Custom Gradients

Create your own gradient presets:

```kotlin
val customGradient = listOf(
    Color(0xFFFF6B6B),
    Color(0xFF4ECDC4),
    Color(0xFF45B7D1)
)

GradientText(
    text = "Custom Colors",
    gradient = customGradient,
    style = MaterialTheme.typography.headlineLarge
)
```

### Combining with Modifiers

```kotlin
ShimmerText(
    text = "Enhanced Text",
    baseColors = SpiritualGradients.mysticGold,
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { /* Handle click */ },
    style = MaterialTheme.typography.headlineMedium
)
```

### Responsive Typography

```kotlin
@Composable
fun ResponsiveHeroTitle(text: String) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    AnimatedGradientText(
        text = text,
        gradient = SpiritualGradients.cosmicPurple,
        style = if (isTablet) {
            MaterialTheme.typography.displayLarge
        } else {
            MaterialTheme.typography.displayMedium
        }
    )
}
```

---

## Testing

To preview all components, use the provided showcase:

```kotlin
GradientTextShowcase()
```

Or use the demo screen:

```kotlin
GradientTextDemoScreen()
```

Individual examples available in `GradientTextUsageExamples` object.

---

## Troubleshooting

### Text appears cut off
- Ensure adequate padding around component
- Check parent container size constraints

### Animation stuttering
- Reduce `starCount` for CosmicText
- Increase `animationDuration` for smoother motion
- Check for other simultaneous animations

### Gradient not visible
- Verify colors have sufficient contrast
- Check if colors are too similar
- Ensure background allows gradient visibility

### Performance issues
- Limit animated components per screen
- Consider static alternatives for low-end devices
- Reduce `shimmerDuration` and `animationDuration`

---

## Related Components

- **ModernComponents.kt** - Additional UI components
- **ShimmerEffects.kt** - Shimmer loading effects
- **Color.kt** - Spiritual color palette

---

## Future Enhancements

Potential additions:
- Path animation along text outline
- Particle effects around letters
- 3D depth/shadow effects
- Text morphing animations
- Wave distortion effects
- Rainbow cycle animation
- Gradient noise textures

---

## License

Part of SpiritAtlas core UI components.

---

**Created:** December 8, 2024
**Last Updated:** December 8, 2024
**Version:** 1.0.0
