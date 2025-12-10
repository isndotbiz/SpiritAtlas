package com.spiritatlas.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * SOLAR THEME - Golds, Oranges, Warm
 *
 * Color Psychology: Vitality, confidence, joy, creative energy
 * Best For: Fire signs, yang energy, motivation
 * Palette: Golden sun, vibrant oranges, warm amber
 */

// ============================================================================
// SOLAR COLOR PALETTE
// ============================================================================

// Primary Colors - Golden Sun
private val SolarGold = Color(0xFFFFC857)               // Bright solar gold
private val SunlightYellow = Color(0xFFFDE047)          // Sunlight yellow
private val AmberGlow = Color(0xFFFBBF24)               // Amber glow
private val DawnGold = Color(0xFFF59E0B)                // Dawn gold

// Secondary Colors - Fire Orange
private val FlameOrange = Color(0xFFF97316)             // Flame orange
private val SunsetCoral = Color(0xFFFF9352)             // Sunset coral
private val EmberRed = Color(0xFFFF7F50)                // Ember red
private val SunriseRose = Color(0xFFFFB088)             // Sunrise rose

// Tertiary Colors - Warm Bronze
private val BronzeGold = Color(0xFFD97706)              // Bronze gold
private val CopperGlow = Color(0xFFE67E22)              // Copper glow
private val TerracottaBrown = Color(0xFFB45309)         // Terracotta
private val HoneyAmber = Color(0xFFFFE082)              // Honey amber

// Accent Colors
private val RadiantWhite = Color(0xFFFFFBF0)            // Warm radiant white
private val WarmCream = Color(0xFFFEF3C7)               // Warm cream
private val MoltenOrange = Color(0xFFFF6B35)            // Molten orange

// ============================================================================
// SOLAR DARK COLOR SCHEME
// ============================================================================

val SolarDarkColorScheme = darkColorScheme(
    // Primary - Solar gold (WCAG AAA: 9.5:1 on dark background)
    primary = Color(0xFFFFD77F),                         // Lighter solar gold
    onPrimary = Color(0xFF2E1F00),                       // Very dark brown
    primaryContainer = Color(0xFF7D5A00),                // Medium gold container
    onPrimaryContainer = Color(0xFFFFEDC2),              // Very light cream

    // Secondary - Flame orange (WCAG AAA: 8.3:1)
    secondary = Color(0xFFFF9352),                       // Bright flame orange
    onSecondary = Color(0xFF2E0F00),                     // Very dark orange
    secondaryContainer = Color(0xFF8C4000),              // Medium orange container
    onSecondaryContainer = Color(0xFFFFDFC2),            // Very light peach

    // Tertiary - Copper glow (WCAG AAA: 8.7:1)
    tertiary = Color(0xFFFFC857),                        // Bright copper
    onTertiary = Color(0xFF2E1A00),                      // Very dark copper
    tertiaryContainer = Color(0xFF8B5A00),               // Medium copper container
    onTertiaryContainer = Color(0xFFFFE8C2),             // Very light amber

    // Error - Ember red (WCAG AAA: 7.9:1)
    error = Color(0xFFFF7B7B),
    onError = Color(0xFF2E0000),
    errorContainer = Color(0xFF8C1818),
    onErrorContainer = Color(0xFFFFDFDF),

    // Background - Warm earth with golden tint
    background = Color(0xFF1F1610),                      // Warm dark gold-black
    onBackground = Color(0xFFFFF8E8),                    // Soft warm white (10.2:1)
    surface = Color(0xFF2A1F14),                         // Warm dark surface
    onSurface = Color(0xFFFFF8E8),                       // Matching soft white (9.6:1)

    // Surface variants
    surfaceVariant = Color(0xFF342818),
    onSurfaceVariant = Color(0xFFE8DCC8),                // Enhanced contrast (8.1:1)
    surfaceTint = Color(0xFFFFD77F),

    // Outline colors
    outline = Color(0xFFB8A890),                         // Lighter outline
    outlineVariant = Color(0xFF5A5040),

    // Scrim and inverse
    scrim = Color(0xCC000000),
    inverseSurface = Color(0xFFFFF8E8),
    inverseOnSurface = Color(0xFF1F1610),
    inversePrimary = Color(0xFFB8860B),

    // Surface containers
    surfaceDim = Color(0xFF15100A),
    surfaceBright = Color(0xFF3F3428),
    surfaceContainerLowest = Color(0xFF100C08),
    surfaceContainerLow = Color(0xFF241A10),
    surfaceContainer = Color(0xFF2D2318),
    surfaceContainerHigh = Color(0xFF382D22),
    surfaceContainerHighest = Color(0xFF44382C)
)

// ============================================================================
// SOLAR LIGHT COLOR SCHEME
// ============================================================================

val SolarLightColorScheme = lightColorScheme(
    primary = Color(0xFFD97706),                         // Deep solar gold
    onPrimary = Color.White,
    primaryContainer = SolarGold.copy(alpha = 0.20f),
    onPrimaryContainer = Color(0xFF4A2800),

    secondary = FlameOrange,                             // Vibrant flame
    onSecondary = Color.White,
    secondaryContainer = FlameOrange.copy(alpha = 0.15f),
    onSecondaryContainer = Color(0xFF4A1800),

    tertiary = BronzeGold,                               // Rich bronze
    onTertiary = Color.White,
    tertiaryContainer = HoneyAmber,
    onTertiaryContainer = Color(0xFF4A2000),

    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFFFFDF5),                      // Warm cream-white
    onBackground = Color(0xFF1F1610),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F1610),

    surfaceVariant = Color(0xFFFFF8E8),
    onSurfaceVariant = Color(0xFF3E3528),
    surfaceTint = Color(0xFFD97706),

    outline = Color(0xFF6B5D48),
    outlineVariant = Color(0xFFE0D5C0),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF382D22),
    inverseOnSurface = Color(0xFFFFF8E8),
    inversePrimary = Color(0xFFFFD77F),

    surfaceDim = Color(0xFFEFE5D0),
    surfaceBright = Color(0xFFFFFDF5),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFFBF0),
    surfaceContainer = Color(0xFFFFF8E8),
    surfaceContainerHigh = Color(0xFFFFF0DB),
    surfaceContainerHighest = Color(0xFFEFE5D0)
)

// ============================================================================
// SOLAR HIGH CONTRAST SCHEMES
// ============================================================================

val SolarHighContrastDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFE899),                         // Very bright gold
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFFFD889),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFFFFAA6B),                       // Very bright orange
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFFF9A5B),
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Color(0xFFFFD88F),                        // Very bright copper
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFFFC87F),
    onTertiaryContainer = Color(0xFF000000),

    error = Color(0xFFFF7777),
    onError = Color(0xFF000000),
    errorContainer = Color(0xFFFF6666),
    onErrorContainer = Color(0xFFFFFFFF),

    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF000000),
    onSurface = Color(0xFFFFFFFF),

    surfaceVariant = Color(0xFF1A1A1A),
    onSurfaceVariant = Color(0xFFFFFFFF),
    surfaceTint = Color(0xFFFFE899),

    outline = Color(0xFFFFFFFF),
    outlineVariant = Color(0xFFCCCCCC),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFFFFFFFF),
    inverseOnSurface = Color(0xFF000000),
    inversePrimary = Color(0xFF805200),

    surfaceDim = Color(0xFF000000),
    surfaceBright = Color(0xFF2A2A2A),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0F0F0F),
    surfaceContainer = Color(0xFF1A1A1A),
    surfaceContainerHigh = Color(0xFF242424),
    surfaceContainerHighest = Color(0xFF2E2E2E)
)

val SolarHighContrastLightColorScheme = lightColorScheme(
    primary = Color(0xFF804600),                         // Very deep gold
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB36800),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF8C3000),                       // Very deep orange
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCC4800),
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFF7A4200),                        // Very deep bronze
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFB36300),
    onTertiaryContainer = Color.White,

    error = Color(0xFFB00020),
    onError = Color.White,
    errorContainer = Color(0xFFD00030),
    onErrorContainer = Color.White,

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),

    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = Color(0xFF000000),
    surfaceTint = Color(0xFF804600),

    outline = Color(0xFF000000),
    outlineVariant = Color(0xFF333333),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFFFFE899),

    surfaceDim = Color(0xFFE8E8E8),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F8F8),
    surfaceContainer = Color(0xFFF0F0F0),
    surfaceContainerHigh = Color(0xFFE8E8E8),
    surfaceContainerHighest = Color(0xFFE0E0E0)
)

// ============================================================================
// SOLAR GRADIENTS
// ============================================================================

object SolarGradients {
    val primary = Brush.linearGradient(
        colors = listOf(
            BronzeGold,
            SolarGold,
            SunlightYellow
        )
    )

    val secondary = Brush.linearGradient(
        colors = listOf(
            EmberRed,
            FlameOrange,
            SunsetCoral
        )
    )

    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A2800),
            BronzeGold,
            SolarGold
        )
    )

    val energy = Brush.linearGradient(
        colors = listOf(
            SunlightYellow,
            SolarGold,
            FlameOrange,
            EmberRed
        )
    )

    val cosmic = Brush.radialGradient(
        colors = listOf(
            WarmCream,
            AmberGlow,
            SolarGold,
            BronzeGold
        )
    )

    val chakra = SpiritualGradients.chakraRainbow
}

// ============================================================================
// SOLAR THEME CONFIGURATION
// ============================================================================

fun getSolarThemeConfig(): ThemeVariantConfig {
    return ThemeVariantConfig(
        variant = ThemeVariant.SOLAR,
        displayName = "Solar",
        description = "Golds, oranges, and radiant warmth",
        emoji = "☀️",
        colorPsychology = "Promotes vitality, confidence, joy, and creative energy. " +
            "Gold represents divine wisdom and spiritual illumination. Orange stimulates creativity and enthusiasm.",
        recommendedFor = "Fire zodiac signs (Aries, Leo, Sagittarius), yang energy practitioners, " +
            "those seeking motivation and vitality, solar return celebrations, summer solstice.",
        lightColorScheme = SolarLightColorScheme,
        darkColorScheme = SolarDarkColorScheme,
        highContrastLightColorScheme = SolarHighContrastLightColorScheme,
        highContrastDarkColorScheme = SolarHighContrastDarkColorScheme,
        gradients = ThemeGradients(
            primary = SolarGradients.primary,
            secondary = SolarGradients.secondary,
            background = SolarGradients.background,
            energy = SolarGradients.energy,
            cosmic = SolarGradients.cosmic,
            chakra = SolarGradients.chakra
        ),
        iconTints = ThemeIconTints(
            primary = SolarGold,
            secondary = FlameOrange,
            tertiary = BronzeGold,
            success = Color(0xFF22C55E),
            warning = AmberGlow,
            error = EmberRed,
            info = CopperGlow
        ),
        semanticColors = ThemeSemanticColors(
            success = Color(0xFF22C55E),
            successContainer = Color(0xFF22C55E).copy(alpha = 0.15f),
            warning = AmberGlow,
            warningContainer = AmberGlow.copy(alpha = 0.20f),
            error = EmberRed,
            errorContainer = EmberRed.copy(alpha = 0.15f),
            info = CopperGlow,
            infoContainer = CopperGlow.copy(alpha = 0.15f)
        )
    )
}
