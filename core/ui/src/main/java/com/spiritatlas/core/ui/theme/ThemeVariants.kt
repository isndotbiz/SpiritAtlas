package com.spiritatlas.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Theme variants for different spiritual moods and user preferences
 * Each variant includes complete color schemes, gradients, and accessibility support
 */
enum class ThemeVariant {
    MYSTICAL,    // Purples, violets - current enhanced theme
    CELESTIAL,   // Blues, silvers, cosmic
    EARTHY,      // Greens, browns, nature
    SOLAR,       // Golds, oranges, warm
    LUNAR        // Silvers, blues, cool
}

/**
 * Complete theme variant configuration with colors, gradients, and metadata
 */
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

/**
 * Gradient collection for a theme variant
 */
@Immutable
data class ThemeGradients(
    val primary: Brush,           // Main spiritual gradient
    val secondary: Brush,         // Alternative accent gradient
    val background: Brush,        // Background gradient
    val energy: Brush,            // Energy flow gradient
    val cosmic: Brush,            // Cosmic/celestial gradient
    val chakra: Brush            // Chakra rainbow (universal)
)

/**
 * Icon tint colors for a theme variant
 */
@Immutable
data class ThemeIconTints(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val info: Color
)

/**
 * Semantic colors for a theme variant
 */
@Immutable
data class ThemeSemanticColors(
    val success: Color,
    val successContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val info: Color,
    val infoContainer: Color
)

/**
 * Get the configuration for a specific theme variant
 */
fun getThemeVariantConfig(variant: ThemeVariant): ThemeVariantConfig {
    return when (variant) {
        ThemeVariant.MYSTICAL -> getMysticalThemeConfig()
        ThemeVariant.CELESTIAL -> getCelestialThemeConfig()
        ThemeVariant.EARTHY -> getEarthyThemeConfig()
        ThemeVariant.SOLAR -> getSolarThemeConfig()
        ThemeVariant.LUNAR -> getLunarThemeConfig()
    }
}

/**
 * Get all available theme variants for selection
 */
fun getAllThemeVariants(): List<ThemeVariantConfig> {
    return ThemeVariant.values().map { getThemeVariantConfig(it) }
}

/**
 * Theme variant metadata for display in settings
 */
data class ThemeVariantInfo(
    val variant: ThemeVariant,
    val name: String,
    val description: String,
    val emoji: String,
    val psychology: String
)

/**
 * Get theme variant info for UI display
 */
fun getThemeVariantInfo(variant: ThemeVariant): ThemeVariantInfo {
    val config = getThemeVariantConfig(variant)
    return ThemeVariantInfo(
        variant = config.variant,
        name = config.displayName,
        description = config.description,
        emoji = config.emoji,
        psychology = config.colorPsychology
    )
}
