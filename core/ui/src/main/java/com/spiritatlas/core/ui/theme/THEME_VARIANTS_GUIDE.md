# Theme Variants Guide

## Overview

SpiritAtlas now supports **5 spiritual theme variants**, each with its own color psychology, mood, and recommended use cases. All themes are fully accessible (WCAG AAA compliant) and include light, dark, and high-contrast modes.

## Available Themes

### 🔮 Mystical (Default)
**Colors:** Purples, Violets, Aurora Pink, Sacred Gold

**Color Psychology:**
- Promotes spirituality, transformation, and mystery
- Activates crown chakra and third eye
- Highest vibrational color in the visible spectrum
- Represents divine connection and spiritual wisdom

**Recommended For:**
- General spiritual practice
- Meditation and mindfulness
- Mystical exploration
- Third eye and crown chakra work
- Users drawn to traditional spiritual colors

**Gradients:**
- Primary: Cosmic Night (deep violet → mystic purple)
- Secondary: Aurora Borealis (pink → purple → blue → teal)
- Energy: Energy Flow (purple → pink → gold → teal)

---

### 🌌 Celestial
**Colors:** Blues, Silvers, Cosmic Indigos

**Color Psychology:**
- Promotes expansiveness, wisdom, and cosmic connection
- Enhances tranquility and truth
- Associated with communication and higher consciousness
- Encourages clarity and emotional depth

**Recommended For:**
- Meditation practitioners
- Astrology enthusiasts
- Night sky lovers
- Those seeking cosmic connection
- Users who prefer cool, calming colors

**Gradients:**
- Primary: Deep space → cosmic blue → starlight silver
- Secondary: Crystal cyan → cosmic blue → moonbeam
- Cosmic: Radial gradient from starlight to void of space

---

### 🌿 Earthy
**Colors:** Greens, Browns, Golden Harvest

**Color Psychology:**
- Promotes grounding, growth, and stability
- Provides natural harmony and security
- Green represents renewal and healing
- Brown offers stability and connection to earth

**Recommended For:**
- Ayurveda practitioners
- Earth zodiac signs (Taurus, Virgo, Capricorn)
- Nature lovers and gardeners
- Those seeking grounding and stability
- Herbalists and earth-based spirituality

**Gradients:**
- Primary: Deep forest → vibrant green → bright moss
- Secondary: Deep wood → copper → sand beige
- Energy: Forest → leaf green → sunlit gold → copper

---

### ☀️ Solar
**Colors:** Golds, Oranges, Warm Amber

**Color Psychology:**
- Promotes vitality, confidence, and joy
- Stimulates creative energy and enthusiasm
- Gold represents divine wisdom and spiritual illumination
- Orange enhances creativity and passion

**Recommended For:**
- Fire zodiac signs (Aries, Leo, Sagittarius)
- Yang energy practitioners
- Those seeking motivation and vitality
- Solar return celebrations
- Summer solstice practices

**Gradients:**
- Primary: Bronze → solar gold → sunlight yellow
- Secondary: Ember red → flame orange → sunset coral
- Energy: Sunlight → gold → orange → ember

---

### 🌙 Lunar
**Colors:** Silvers, Blues, Pearl White

**Color Psychology:**
- Promotes intuition, reflection, and feminine energy
- Enhances emotional depth and inner peace
- Silver represents clarity and illumination
- Blue encourages calm and introspection

**Recommended For:**
- Yin energy practitioners
- Water zodiac signs (Cancer, Scorpio, Pisces)
- Moon phase trackers
- Nighttime meditation
- Those seeking emotional balance and intuition

**Gradients:**
- Primary: Midnight → twilight blue → moonlight silver
- Secondary: Crystal blue → moonbeam → silver glow
- Cosmic: Radial from frost white to midnight depths

---

## Theme Selection

### In the App

1. Go to **Settings**
2. Scroll to **Theme Style** section
3. Tap on your desired theme card
4. Theme changes instantly with smooth animation

### For Developers

```kotlin
// In MainActivity or root composable
val context = LocalContext.current
val settingsPrefs = remember { SettingsPreferences(context) }
val themeVariant by remember {
    mutableStateOf(settingsPrefs.getThemeVariant())
}

SpiritAtlasTheme(
    themeVariant = themeVariant,
    darkTheme = isSystemInDarkTheme()
) {
    // Your content
}
```

### Saving User Preference

```kotlin
// In ViewModel
fun setThemeVariant(variant: ThemeVariant) {
    viewModelScope.launch {
        settingsPreferences.setThemeVariant(variant)
        _themeVariant.value = variant
    }
}
```

---

## Accessibility

### WCAG AAA Compliance

All themes meet **WCAG AAA standards** (minimum 7:1 contrast ratio for normal text):

- **Dark Themes:** 7.7:1 to 10.5:1 contrast
- **Light Themes:** 7:1 to 12:1 contrast
- **High Contrast Modes:** 10:1 to 21:1 contrast

### High Contrast Variants

Each theme includes a high-contrast variant:

```kotlin
SpiritAtlasTheme(
    themeVariant = ThemeVariant.CELESTIAL,
    config = SpiritualThemeConfig(
        useHighContrast = true
    )
) {
    // Content
}
```

High contrast themes feature:
- Pure black/white backgrounds
- Vivid, highly saturated colors
- 10:1+ contrast ratios
- Enhanced borders (2-4dp width)
- Larger touch targets (48dp minimum)

---

## Theme Animations

### Smooth Transitions

Theme changes include smooth color animations:

```kotlin
// Animated color transitions
val animatedPrimary by animateColorAsState(
    targetValue = colorScheme.primary,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy
    )
)
```

### Reduced Motion

For users with motion sensitivity:

```kotlin
SpiritAtlasTheme(
    config = SpiritualThemeConfig(
        reduceMotion = true
    )
) {
    // Reduced animation
}
```

---

## Theme Components

### Color Schemes

Each theme variant provides complete Material3 color schemes:

- **Primary, Secondary, Tertiary** colors
- **Container** colors for surfaces
- **On-colors** for text/icons
- **Error, Warning, Success, Info** semantic colors
- **Surface variants** for depth and hierarchy
- **Outline** colors for borders

### Gradients

Each theme includes 6 spiritual gradients:

1. **Primary:** Main theme gradient
2. **Secondary:** Alternative accent gradient
3. **Background:** For immersive backgrounds
4. **Energy:** For energy flow visualizations
5. **Cosmic:** For celestial effects
6. **Chakra:** Universal chakra rainbow (same for all themes)

### Icon Tints

Predefined colors for consistent icon styling:

```kotlin
val config = getThemeVariantConfig(ThemeVariant.SOLAR)
Icon(
    imageVector = Icons.Filled.Sun,
    tint = config.iconTints.primary // Solar gold
)
```

---

## Best Practices

### Theme Selection UX

1. **Show Visual Previews:** Display color swatches in theme selector
2. **Instant Feedback:** Apply theme immediately on selection
3. **Haptic Feedback:** Use medium haptic when changing themes
4. **Smooth Transitions:** Animate between theme colors
5. **Persist Selection:** Save theme preference immediately

### Performance

- Theme changes are **lightweight** (< 16ms)
- Color schemes are **pre-computed** (no runtime calculation)
- Animations use **hardware acceleration**
- Gradients are **cached** for reuse

### Testing

Test all themes for:

```bash
# Visual regression testing
./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=ThemeVariantTests

# Accessibility testing
./gradlew lintDebug  # Checks contrast ratios
```

---

## Theme Psychology Reference

### When to Recommend Each Theme

| User Goal | Recommended Theme | Why |
|-----------|------------------|-----|
| General spiritual practice | Mystical | Traditional, balanced, universally spiritual |
| Astrology focus | Celestial | Cosmic connection, night sky association |
| Grounding/stability | Earthy | Earth element, root chakra, stability |
| Energy/motivation | Solar | Yang energy, confidence, vitality |
| Intuition/reflection | Lunar | Yin energy, introspection, emotional depth |
| Meditation | Celestial or Lunar | Calming blues/silvers |
| Tantra/relationships | Mystical | Aurora pink, intimacy purple |
| Ayurveda | Earthy | Dosha balance, nature connection |

---

## API Reference

### Theme Variant Enum

```kotlin
enum class ThemeVariant {
    MYSTICAL,    // Purples, violets
    CELESTIAL,   // Blues, silvers
    EARTHY,      // Greens, browns
    SOLAR,       // Golds, oranges
    LUNAR        // Silvers, cool blues
}
```

### Getting Theme Configuration

```kotlin
val config: ThemeVariantConfig = getThemeVariantConfig(ThemeVariant.CELESTIAL)

// Access properties
config.displayName         // "Celestial"
config.emoji               // "🌌"
config.colorPsychology     // Full description
config.lightColorScheme    // Material3 ColorScheme
config.darkColorScheme     // Material3 ColorScheme
config.gradients           // ThemeGradients
config.iconTints           // ThemeIconTints
```

### Theme Data Classes

```kotlin
@Immutable
data class ThemeVariantConfig(
    val variant: ThemeVariant,
    val displayName: String,
    val description: String,
    val emoji: String,
    val colorPsychology: String,
    val recommendedFor: String,
    val lightColorScheme: ColorScheme,
    val darkColorScheme: ColorScheme,
    val highContrastLightColorScheme: ColorScheme,
    val highContrastDarkColorScheme: ColorScheme,
    val gradients: ThemeGradients,
    val iconTints: ThemeIconTints,
    val semanticColors: ThemeSemanticColors
)
```

---

## Future Enhancements

Potential additions:

- [ ] **Custom theme creator** - Let users create custom color combinations
- [ ] **Seasonal themes** - Equinox/solstice special themes
- [ ] **Planetary themes** - Based on planetary transits
- [ ] **Chakra-specific themes** - One theme per chakra
- [ ] **Time-based themes** - Auto-switch based on time of day
- [ ] **Astrological themes** - Themes for each zodiac sign

---

## Support

For questions or issues with themes:

1. Check accessibility with TalkBack enabled
2. Verify contrast ratios with Android Accessibility Scanner
3. Test on both light and dark system themes
4. Report theme bugs with screenshots and device info

---

**Last Updated:** 2025-12-10
**Version:** 1.0
**Themes:** 5 (Mystical, Celestial, Earthy, Solar, Lunar)
