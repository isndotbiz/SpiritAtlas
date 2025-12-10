package com.spiritatlas.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * MYSTICAL THEME - Purples, Violets (Current Enhanced)
 *
 * Color Psychology: Spirituality, transformation, mystery, crown chakra
 * Best For: General spiritual practice, meditation, mystical exploration
 * Palette: Deep cosmic violet, mystic purple, aurora pink, sacred indigo
 */

// ============================================================================
// MYSTICAL COLOR PALETTE (Uses existing Color.kt palette)
// ============================================================================

// Primary Colors - Mystic Purples (from Color.kt)
// CosmicViolet, MysticPurple, SpiritualPurple, etc.

// Secondary Colors - Aurora & Pink
// AuroraPink, IntimacyPurple, TantricRose

// Tertiary Colors - Sacred Metals
// SacredGold, TempleBronze, UnionGold

/**
 * Mystical Dark Color Scheme - Enhanced from existing SpiritualDarkColorScheme
 * This is the current theme, refined for consistency
 */
val MysticalDarkColorScheme = SpiritualDarkColorScheme

/**
 * Mystical Light Color Scheme - Enhanced from existing SpiritualLightColorScheme
 */
val MysticalLightColorScheme = SpiritualLightColorScheme

/**
 * Mystical High Contrast Schemes - Using existing high contrast themes
 */
val MysticalHighContrastDarkColorScheme = HighContrastDarkColorScheme
val MysticalHighContrastLightColorScheme = HighContrastLightColorScheme

// ============================================================================
// MYSTICAL GRADIENTS (Using existing gradients from Color.kt)
// ============================================================================

object MysticalGradients {
    val primary = SpiritualGradients.cosmicNight
    val secondary = SpiritualGradients.auroraBorealis
    val background = SpiritualGradients.mysticFog
    val energy = SpiritualGradients.energyFlow
    val cosmic = SpiritualGradients.sacredUnion
    val chakra = SpiritualGradients.chakraRainbow
}

// ============================================================================
// MYSTICAL THEME CONFIGURATION
// ============================================================================

fun getMysticalThemeConfig(): ThemeVariantConfig {
    return ThemeVariantConfig(
        variant = ThemeVariant.MYSTICAL,
        displayName = "Mystical",
        description = "Purples, violets, and spiritual mystery",
        emoji = "🔮",
        colorPsychology = "Promotes spirituality, transformation, mystery, and crown chakra activation. " +
            "Purple represents divine connection, spiritual wisdom, and mystical experiences. " +
            "The highest vibrational color in the visible spectrum.",
        recommendedFor = "General spiritual practice, meditation and mindfulness, mystical exploration, " +
            "third eye and crown chakra work, those drawn to the traditional spiritual color palette.",
        lightColorScheme = MysticalLightColorScheme,
        darkColorScheme = MysticalDarkColorScheme,
        highContrastLightColorScheme = MysticalHighContrastLightColorScheme,
        highContrastDarkColorScheme = MysticalHighContrastDarkColorScheme,
        gradients = ThemeGradients(
            primary = MysticalGradients.primary,
            secondary = MysticalGradients.secondary,
            background = MysticalGradients.background,
            energy = MysticalGradients.energy,
            cosmic = MysticalGradients.cosmic,
            chakra = MysticalGradients.chakra
        ),
        iconTints = ThemeIconTints(
            primary = MysticPurple,
            secondary = AuroraPink,
            tertiary = SacredGold,
            success = ChakraGreen,
            warning = AuraGold,
            error = ChakraRed,
            info = StardustBlue
        ),
        semanticColors = ThemeSemanticColors(
            success = SemanticColors.Success,
            successContainer = SemanticColors.SuccessContainer,
            warning = SemanticColors.Warning,
            warningContainer = SemanticColors.WarningContainer,
            error = SemanticColors.Error,
            errorContainer = SemanticColors.ErrorContainer,
            info = SemanticColors.Info,
            infoContainer = SemanticColors.InfoContainer
        )
    )
}
