# Gradient Text Quick Reference

Quick copy-paste examples for SpiritAtlas gradient text components.

## Import Statement

```kotlin
import com.spiritatlas.core.ui.components.*
```

---

## Basic Usage

### Simple Gradient Header
```kotlin
GradientText(
    text = "Your Title Here",
    gradient = SpiritualGradients.cosmicPurple,
    style = MaterialTheme.typography.headlineLarge
)
```

### Animated Flowing Text
```kotlin
AnimatedGradientText(
    text = "Flowing Text",
    gradient = SpiritualGradients.chakraRainbow,
    animationDuration = 3000
)
```

### Shimmer Effect
```kotlin
ShimmerText(
    text = "Call to Action",
    baseColors = SpiritualGradients.fireEnergy
)
```

### Glowing Text
```kotlin
GlowingText(
    text = "Sacred Words",
    glowColor = SpiritualPurple
)
```

### Typewriter Animation
```kotlin
TypewriterText(
    text = "Loading...",
    gradient = SpiritualGradients.aurora,
    characterDelayMs = 50
)
```

### Fade In Words
```kotlin
FadeInText(
    text = "Welcome message here",
    gradient = SpiritualGradients.divineLight
)
```

### Cosmic Starfield
```kotlin
CosmicText(
    text = "SpiritAtlas",
    starCount = 80
)
```

---

## All Gradient Presets

```kotlin
SpiritualGradients.cosmicPurple     // Purple → Blue cosmic
SpiritualGradients.mysticGold       // Gold → Orange mystical
SpiritualGradients.chakraRainbow    // 7 chakra colors
SpiritualGradients.twilight         // Deep purple → Magenta
SpiritualGradients.aurora           // Cyan → Green → Yellow
SpiritualGradients.fireEnergy       // Orange → Red → Yellow
SpiritualGradients.waterFlow        // Deep blue → Light blue
SpiritualGradients.celestialSilver  // Silver metallic
SpiritualGradients.tantricPassion   // Rose → Coral → Purple
SpiritualGradients.divineLight      // White → Gold → Purple
```

---

## Common Patterns

### Hero Title (Large, Bold, Animated)
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

### Section Header (Medium, Bold, Static)
```kotlin
GradientText(
    text = "Meditation Practices",
    gradient = SpiritualGradients.twilight,
    style = MaterialTheme.typography.headlineLarge.copy(
        fontWeight = FontWeight.Bold
    )
)
```

### Loading Message (Typewriter)
```kotlin
TypewriterText(
    text = "Analyzing your profile...",
    gradient = SpiritualGradients.aurora,
    characterDelayMs = 50,
    showCursor = true
)
```

### Welcome Screen (Fade In)
```kotlin
FadeInText(
    text = "Welcome back, ${userName}",
    gradient = SpiritualGradients.divineLight,
    style = MaterialTheme.typography.headlineMedium,
    wordDelayMs = 150
)
```

### CTA Button Text (Shimmer)
```kotlin
ShimmerText(
    text = "Start Your Journey",
    baseColors = SpiritualGradients.fireEnergy,
    style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.SemiBold
    ),
    shimmerDuration = 2000
)
```

### Mystical Quote (Glowing)
```kotlin
GlowingText(
    text = "The universe within you",
    textColor = Color.White,
    glowColor = SpiritualGradients.cosmicPurple[0],
    style = MaterialTheme.typography.headlineMedium,
    animateGlow = true,
    glowRadius = 20f
)
```

### App Branding (Cosmic)
```kotlin
CosmicText(
    text = "SpiritAtlas",
    style = MaterialTheme.typography.displayLarge.copy(
        fontWeight = FontWeight.ExtraBold
    ),
    starCount = 100,
    animationSpeed = 0.5f
)
```

---

## Customization Examples

### Custom Gradient
```kotlin
val myGradient = listOf(
    Color(0xFFFF6B6B),
    Color(0xFF4ECDC4),
    Color(0xFF45B7D1)
)

GradientText(
    text = "Custom Colors",
    gradient = myGradient
)
```

### Different Gradient Directions
```kotlin
// Horizontal (default)
GradientText(
    text = "Horizontal",
    gradient = SpiritualGradients.cosmicPurple,
    gradientDirection = GradientDirection.Horizontal
)

// Vertical
GradientText(
    text = "Vertical",
    gradient = SpiritualGradients.waterFlow,
    gradientDirection = GradientDirection.Vertical
)

// Diagonal
GradientText(
    text = "Diagonal",
    gradient = SpiritualGradients.aurora,
    gradientDirection = GradientDirection.Diagonal
)
```

### Animation Directions
```kotlin
// Left to Right
AnimatedGradientText(
    text = "Left to Right",
    gradient = SpiritualGradients.fireEnergy,
    animationDirection = AnimationDirection.LeftToRight
)

// Right to Left
AnimatedGradientText(
    text = "Right to Left",
    gradient = SpiritualGradients.waterFlow,
    animationDirection = AnimationDirection.RightToLeft
)

// Top to Bottom
AnimatedGradientText(
    text = "Top to Bottom",
    gradient = SpiritualGradients.aurora,
    animationDirection = AnimationDirection.TopToBottom
)

// Bottom to Top
AnimatedGradientText(
    text = "Bottom to Top",
    gradient = SpiritualGradients.divineLight,
    animationDirection = AnimationDirection.BottomToTop
)
```

### With Callbacks
```kotlin
TypewriterText(
    text = "Your destiny unfolds...",
    gradient = SpiritualGradients.mysticGold,
    characterDelayMs = 80,
    onComplete = {
        // Navigate to next screen
        navController.navigate("nextScreen")
    }
)

FadeInText(
    text = "Welcome to your journey",
    gradient = SpiritualGradients.divineLight,
    wordDelayMs = 150,
    onComplete = {
        // Show next content
        showNextContent = true
    }
)
```

### With Modifiers
```kotlin
ShimmerText(
    text = "Enhanced Text",
    baseColors = SpiritualGradients.mysticGold,
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { handleClick() },
    style = MaterialTheme.typography.headlineMedium
)
```

---

## Typography Scale

Use Material 3 typography for consistent sizing:

```kotlin
// Extra large (display)
style = MaterialTheme.typography.displayLarge   // 57sp
style = MaterialTheme.typography.displayMedium  // 45sp
style = MaterialTheme.typography.displaySmall   // 36sp

// Large (headline)
style = MaterialTheme.typography.headlineLarge  // 32sp
style = MaterialTheme.typography.headlineMedium // 28sp
style = MaterialTheme.typography.headlineSmall  // 24sp

// Medium (title)
style = MaterialTheme.typography.titleLarge     // 22sp
style = MaterialTheme.typography.titleMedium    // 16sp
style = MaterialTheme.typography.titleSmall     // 14sp

// Small (body/label)
style = MaterialTheme.typography.bodyLarge      // 16sp
style = MaterialTheme.typography.bodyMedium     // 14sp
style = MaterialTheme.typography.bodySmall      // 12sp
```

---

## Performance Tips

```kotlin
// Good: Single animated text on screen
AnimatedGradientText(
    text = "Main Title",
    gradient = SpiritualGradients.cosmicPurple
)

// Avoid: Too many animated components
// Don't have 5+ animated text components visible at once

// Optimize: Reduce star count for cosmic text
CosmicText(
    text = "Optimized",
    starCount = 30  // Instead of 100+
)

// Optimize: Slower animations use less CPU
AnimatedGradientText(
    text = "Smooth",
    gradient = SpiritualGradients.chakraRainbow,
    animationDuration = 5000  // Slower is smoother
)
```

---

## Testing

View all components:
```kotlin
// Full showcase
GradientTextShowcase()

// Demo screen
GradientTextDemoScreen()

// Individual examples
GradientTextUsageExamples.HeroTitle()
GradientTextUsageExamples.FeatureHeader()
GradientTextUsageExamples.LoadingMessage()
GradientTextUsageExamples.WelcomeMessage("John")
GradientTextUsageExamples.CallToAction()
GradientTextUsageExamples.MysticalQuote()
GradientTextUsageExamples.AppLogo()
```

---

## Common Issues & Solutions

### Issue: Text is cut off
**Solution:** Add padding
```kotlin
GradientText(
    text = "Text",
    gradient = SpiritualGradients.cosmicPurple,
    modifier = Modifier.padding(8.dp)
)
```

### Issue: Animation is choppy
**Solution:** Increase duration
```kotlin
AnimatedGradientText(
    text = "Smooth",
    gradient = SpiritualGradients.chakraRainbow,
    animationDuration = 5000  // Slower = smoother
)
```

### Issue: Glow effect not visible
**Solution:** Use on dark background
```kotlin
Box(
    modifier = Modifier
        .background(Color.Black)
        .padding(16.dp)
) {
    GlowingText(
        text = "Glowing",
        glowColor = SpiritualPurple
    )
}
```

### Issue: Gradient too subtle
**Solution:** Use more contrasting colors
```kotlin
val highContrastGradient = listOf(
    Color(0xFFFF0000),  // Bright red
    Color(0xFF0000FF)   // Bright blue
)
```

---

## Copy-Paste Templates

### Template: Feature Card Title
```kotlin
@Composable
fun FeatureCardTitle(title: String) {
    GradientText(
        text = title,
        gradient = SpiritualGradients.twilight,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
```

### Template: Loading Screen
```kotlin
@Composable
fun LoadingScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TypewriterText(
            text = message,
            gradient = SpiritualGradients.aurora,
            style = MaterialTheme.typography.headlineMedium,
            characterDelayMs = 60,
            showCursor = true
        )
    }
}
```

### Template: Hero Section
```kotlin
@Composable
fun HeroSection(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedGradientText(
            text = title,
            gradient = SpiritualGradients.cosmicPurple,
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            animationDuration = 4000
        )

        FadeInText(
            text = subtitle,
            gradient = SpiritualGradients.divineLight,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
```

---

**Quick Tip:** Start with `GradientText` for static content, upgrade to animated versions for emphasis.
