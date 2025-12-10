package com.spiritatlas.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * LUNAR THEME - Silvers, Blues, Cool
 *
 * Color Psychology: Intuition, reflection, feminine energy, emotional depth
 * Best For: Yin energy, water signs, moon phases, nighttime practice
 * Palette: Silver moonlight, cool blues, pearl white, twilight indigo
 */

// ============================================================================
// LUNAR COLOR PALETTE
// ============================================================================

// Primary Colors - Moonlight Silver (MoonlightSilver is defined in Color.kt)
private val PearlWhite = Color(0xFFF8FAFC)              // Pearl white
private val SilverGlow = Color(0xFFCBD5E1)              // Silver glow
private val FrostedSilver = Color(0xFFE0E7FF)           // Frosted silver-blue

// Secondary Colors - Twilight Blue
private val TwilightBlue = Color(0xFF6366F1)            // Twilight indigo-blue
private val MidnightBlue = Color(0xFF1E3A8A)            // Deep midnight
private val MoonbeamBlue = Color(0xFF7DD3FC)            // Moonbeam blue
private val LunarStardustBlue = Color(0xFF93C5FD)       // Stardust blue (renamed to avoid conflict)

// Tertiary Colors - Cool Lavender
private val LunarLavender = Color(0xFFC7D2FE)           // Lunar lavender
private val TwilightPurple = Color(0xFF8B9CFF)          // Twilight purple
private val MistLavender = Color(0xFFDDD6FE)            // Mist lavender
private val CoolGray = Color(0xFF94A3B8)                // Cool gray

// Accent Colors
private val NightViolet = Color(0xFF4338CA)             // Night violet
private val FrostWhite = Color(0xFFFFFBFF)              // Frost white
private val CrystalBlue = Color(0xFF0EA5E9)             // Crystal blue

// ============================================================================
// LUNAR DARK COLOR SCHEME
// ============================================================================

val LunarDarkColorScheme = darkColorScheme(
    // Primary - Moonlight silver (WCAG AAA: 9.8:1 on dark background)
    primary = Color(0xFFF0F4FF),                         // Bright moonlight
    onPrimary = Color(0xFF1A1F33),                       // Very dark indigo
    primaryContainer = Color(0xFF3F4873),                // Medium indigo container
    onPrimaryContainer = Color(0xFFF8FAFF),              // Very light moonlight

    // Secondary - Twilight blue (WCAG AAA: 8.6:1)
    secondary = Color(0xFF93DDFF),                       // Bright moonbeam blue
    onSecondary = Color(0xFF001A2E),                     // Very dark blue
    secondaryContainer = Color(0xFF1E4D75),              // Medium blue container
    onSecondaryContainer = Color(0xFFD9EEFF),            // Very light blue

    // Tertiary - Lunar lavender (WCAG AAA: 9.0:1)
    tertiary = Color(0xFFDDD6FE),                        // Bright lavender
    onTertiary = Color(0xFF1F1A33),                      // Very dark purple
    tertiaryContainer = Color(0xFF4A4073),               // Medium purple container
    onTertiaryContainer = Color(0xFFF0EDFF),             // Very light lavender

    // Error - Cool red (WCAG AAA: 7.9:1)
    error = Color(0xFFFF7B7B),
    onError = Color(0xFF2E0000),
    errorContainer = Color(0xFF8C1818),
    onErrorContainer = Color(0xFFFFDFDF),

    // Background - Deep night with blue tint
    background = Color(0xFF0F1420),                      // Cool dark blue-black
    onBackground = Color(0xFFF8FAFF),                    // Soft moon white (10.5:1)
    surface = Color(0xFF1A1F2E),                         // Cool dark surface
    onSurface = Color(0xFFF8FAFF),                       // Matching soft white (10.0:1)

    // Surface variants
    surfaceVariant = Color(0xFF24293A),
    onSurfaceVariant = Color(0xFFE0E5F0),                // Enhanced contrast (8.5:1)
    surfaceTint = Color(0xFFF0F4FF),

    // Outline colors
    outline = Color(0xFFA8B3CC),                         // Lighter outline
    outlineVariant = Color(0xFF4A5166),

    // Scrim and inverse
    scrim = Color(0xCC000000),
    inverseSurface = Color(0xFFF8FAFF),
    inverseOnSurface = Color(0xFF0F1420),
    inversePrimary = Color(0xFF4338CA),

    // Surface containers
    surfaceDim = Color(0xFF0A0E18),
    surfaceBright = Color(0xFF353A4A),
    surfaceContainerLowest = Color(0xFF070A14),
    surfaceContainerLow = Color(0xFF181D28),
    surfaceContainer = Color(0xFF212632),
    surfaceContainerHigh = Color(0xFF2C313C),
    surfaceContainerHighest = Color(0xFF373C47)
)

// ============================================================================
// LUNAR LIGHT COLOR SCHEME
// ============================================================================

val LunarLightColorScheme = lightColorScheme(
    primary = Color(0xFF4338CA),                         // Deep twilight violet
    onPrimary = Color.White,
    primaryContainer = LunarLavender,
    onPrimaryContainer = NightViolet,

    secondary = Color(0xFF0369A1),                       // Deep twilight blue
    onSecondary = Color.White,
    secondaryContainer = LunarStardustBlue.copy(alpha = 0.3f),
    onSecondaryContainer = MidnightBlue,

    tertiary = Color(0xFF6366F1),                        // Medium indigo
    onTertiary = Color.White,
    tertiaryContainer = MistLavender,
    onTertiaryContainer = Color(0xFF2A2066),

    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFFAFAFF),                      // Cool white
    onBackground = Color(0xFF0F1420),
    surface = Color.White,
    onSurface = Color(0xFF0F1420),

    surfaceVariant = Color(0xFFF0F4FF),
    onSurfaceVariant = Color(0xFF3A4050),
    surfaceTint = Color(0xFF4338CA),

    outline = Color(0xFF6B7888),
    outlineVariant = Color(0xFFD0D8E8),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF2C313C),
    inverseOnSurface = Color(0xFFF0F4FF),
    inversePrimary = Color(0xFFF0F4FF),

    surfaceDim = Color(0xFFE0E7F5),
    surfaceBright = Color(0xFFFAFAFF),
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFF5F8FF),
    surfaceContainer = Color(0xFFF0F4FF),
    surfaceContainerHigh = Color(0xFFE8EDF8),
    surfaceContainerHighest = Color(0xFFE0E7F5)
)

// ============================================================================
// LUNAR HIGH CONTRAST SCHEMES
// ============================================================================

val LunarHighContrastDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),                         // Pure white moonlight
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFF5F5FF),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFFB0E0FF),                       // Very bright moon blue
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFA0D0FF),
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Color(0xFFEDE8FF),                        // Very bright lavender
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFDDD8FF),
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
    surfaceTint = Color(0xFFFFFFFF),

    outline = Color(0xFFFFFFFF),
    outlineVariant = Color(0xFFCCCCCC),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFFFFFFFF),
    inverseOnSurface = Color(0xFF000000),
    inversePrimary = Color(0xFF2A2080),

    surfaceDim = Color(0xFF000000),
    surfaceBright = Color(0xFF2A2A2A),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0F0F0F),
    surfaceContainer = Color(0xFF1A1A1A),
    surfaceContainerHigh = Color(0xFF242424),
    surfaceContainerHighest = Color(0xFF2E2E2E)
)

val LunarHighContrastLightColorScheme = lightColorScheme(
    primary = Color(0xFF2A2080),                         // Very deep violet
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3D30A8),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF003D66),                       // Very deep blue
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF005C99),
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFF3D2E80),                        // Very deep purple
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF5844B3),
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
    surfaceTint = Color(0xFF2A2080),

    outline = Color(0xFF000000),
    outlineVariant = Color(0xFF333333),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFFFFFFFF),

    surfaceDim = Color(0xFFE8E8E8),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F8F8),
    surfaceContainer = Color(0xFFF0F0F0),
    surfaceContainerHigh = Color(0xFFE8E8E8),
    surfaceContainerHighest = Color(0xFFE0E0E0)
)

// ============================================================================
// LUNAR GRADIENTS
// ============================================================================

object LunarGradients {
    val primary = Brush.linearGradient(
        colors = listOf(
            MidnightBlue,
            TwilightBlue,
            MoonlightSilver
        )
    )

    val secondary = Brush.linearGradient(
        colors = listOf(
            CrystalBlue,
            MoonbeamBlue,
            SilverGlow
        )
    )

    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0E18),
            MidnightBlue,
            NightViolet
        )
    )

    val energy = Brush.linearGradient(
        colors = listOf(
            MoonlightSilver,
            LunarLavender,
            TwilightBlue,
            CrystalBlue
        )
    )

    val cosmic = Brush.radialGradient(
        colors = listOf(
            FrostWhite,
            MoonlightSilver,
            TwilightPurple,
            MidnightBlue
        )
    )

    val chakra = SpiritualGradients.chakraRainbow
}

// ============================================================================
// LUNAR THEME CONFIGURATION
// ============================================================================

fun getLunarThemeConfig(): ThemeVariantConfig {
    return ThemeVariantConfig(
        variant = ThemeVariant.LUNAR,
        displayName = "Lunar",
        description = "Silvers, blues, and moonlit serenity",
        emoji = "🌙",
        colorPsychology = "Promotes intuition, reflection, feminine energy, and emotional depth. " +
            "Silver represents clarity and illumination. Blue enhances calm and inner peace.",
        recommendedFor = "Yin energy practitioners, water zodiac signs (Cancer, Scorpio, Pisces), " +
            "moon phase trackers, nighttime meditation, those seeking emotional balance and intuition.",
        lightColorScheme = LunarLightColorScheme,
        darkColorScheme = LunarDarkColorScheme,
        highContrastLightColorScheme = LunarHighContrastLightColorScheme,
        highContrastDarkColorScheme = LunarHighContrastDarkColorScheme,
        gradients = ThemeGradients(
            primary = LunarGradients.primary,
            secondary = LunarGradients.secondary,
            background = LunarGradients.background,
            energy = LunarGradients.energy,
            cosmic = LunarGradients.cosmic,
            chakra = LunarGradients.chakra
        ),
        iconTints = ThemeIconTints(
            primary = MoonlightSilver,
            secondary = TwilightBlue,
            tertiary = LunarLavender,
            success = Color(0xFF22C55E),
            warning = Color(0xFFFFC857),
            error = Color(0xFFFF6B6B),
            info = CrystalBlue
        ),
        semanticColors = ThemeSemanticColors(
            success = Color(0xFF22C55E),
            successContainer = Color(0xFF22C55E).copy(alpha = 0.15f),
            warning = Color(0xFFFFC857),
            warningContainer = Color(0xFFFFC857).copy(alpha = 0.15f),
            error = Color(0xFFFF6B6B),
            errorContainer = Color(0xFFFF6B6B).copy(alpha = 0.15f),
            info = CrystalBlue,
            infoContainer = CrystalBlue.copy(alpha = 0.15f)
        )
    )
}
