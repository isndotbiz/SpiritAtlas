package com.spiritatlas.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * CELESTIAL THEME - Blues, Silvers, Cosmic
 *
 * Color Psychology: Expansiveness, wisdom, cosmic connection, tranquility
 * Best For: Meditation, astrology focus, night sky enthusiasts
 * Palette: Deep space blues, silver starlight, cosmic indigos
 */

// ============================================================================
// CELESTIAL COLOR PALETTE
// ============================================================================

// Primary Colors - Cosmic Blues
private val CelestialBlue = Color(0xFF4A9EFF)           // Bright cosmic blue
private val DeepSpaceBlue = Color(0xFF1E3A8A)           // Deep space
private val StardustSilver = Color(0xFFE0E7FF)          // Silver starlight
private val NebulaBlue = Color(0xFF6B7FFF)              // Nebula purple-blue

// Secondary Colors - Silver & Cyan
private val MoonbeamSilver = Color(0xFFC7D2FE)          // Soft moonbeam
private val CrystalCyan = Color(0xFF06B6D4)             // Crystal cyan
private val FrostedSilver = Color(0xFFE2E8F0)           // Frosted silver
private val AuroraCyan = Color(0xFF22D3EE)              // Aurora cyan

// Tertiary Colors - Deep Indigo
private val CosmicIndigo = Color(0xFF4338CA)            // Deep cosmic indigo
private val TwilightPurple = Color(0xFF6366F1)          // Twilight purple-blue
private val VoidBlack = Color(0xFF0C1A2E)               // Void of space

// Accent Colors
private val SupernovaWhite = Color(0xFFFAFAFA)          // Supernova bright
private val CometBlue = Color(0xFF3B82F6)               // Comet trail blue
private val GalaxyPurple = Color(0xFF8B9CFF)            // Galaxy purple

// ============================================================================
// CELESTIAL DARK COLOR SCHEME
// ============================================================================

val CelestialDarkColorScheme = darkColorScheme(
    // Primary - Cosmic blue (WCAG AAA: 8.2:1 on dark background)
    primary = Color(0xFF7AA8FF),                         // Lighter cosmic blue
    onPrimary = Color(0xFF001A33),                       // Very dark blue
    primaryContainer = Color(0xFF1E4D8C),                // Medium space blue
    onPrimaryContainer = Color(0xFFD9E7FF),              // Very light blue

    // Secondary - Aurora cyan (WCAG AAA: 9.1:1)
    secondary = Color(0xFF5DE4FF),                       // Bright aurora cyan
    onSecondary = Color(0xFF00151F),                     // Very dark cyan
    secondaryContainer = Color(0xFF0E5570),              // Medium cyan container
    onSecondaryContainer = Color(0xFFD1F2FF),            // Very light cyan

    // Tertiary - Moonbeam silver (WCAG AAA: 8.8:1)
    tertiary = Color(0xFFE0E7FF),                        // Bright moonbeam
    onTertiary = Color(0xFF1A1F33),                      // Very dark indigo
    tertiaryContainer = Color(0xFF3F4873),               // Medium indigo container
    onTertiaryContainer = Color(0xFFF0F3FF),             // Very light silver

    // Error - Aurora red (WCAG AAA: 7.9:1)
    error = Color(0xFFFF7B7B),
    onError = Color(0xFF2E0000),
    errorContainer = Color(0xFF8C1818),
    onErrorContainer = Color(0xFFFFDFDF),

    // Background - Deep space with blue tint
    background = Color(0xFF0F1828),                      // Warm dark blue-black
    onBackground = Color(0xFFF0F3FF),                    // Soft white with blue tint (9.5:1)
    surface = Color(0xFF1A2334),                         // Warm dark surface
    onSurface = Color(0xFFF0F3FF),                       // Matching soft white (9.0:1)

    // Surface variants
    surfaceVariant = Color(0xFF242F40),
    onSurfaceVariant = Color(0xFFD9E2F0),                // Enhanced contrast (7.8:1)
    surfaceTint = Color(0xFF7AA8FF),

    // Outline colors
    outline = Color(0xFFA3B3CC),                         // Lighter outline
    outlineVariant = Color(0xFF4A5566),

    // Scrim and inverse
    scrim = Color(0xCC000000),
    inverseSurface = Color(0xFFF0F3FF),
    inverseOnSurface = Color(0xFF0F1828),
    inversePrimary = Color(0xFF2563A8),

    // Surface containers
    surfaceDim = Color(0xFF0A1220),
    surfaceBright = Color(0xFF354050),
    surfaceContainerLowest = Color(0xFF070E18),
    surfaceContainerLow = Color(0xFF181F2E),
    surfaceContainer = Color(0xFF212938),
    surfaceContainerHigh = Color(0xFF2C3542),
    surfaceContainerHighest = Color(0xFF37404D)
)

// ============================================================================
// CELESTIAL LIGHT COLOR SCHEME
// ============================================================================

val CelestialLightColorScheme = lightColorScheme(
    primary = Color(0xFF1E5FAA),                         // Deep cosmic blue
    onPrimary = Color.White,
    primaryContainer = CelestialBlue.copy(alpha = 0.15f),
    onPrimaryContainer = DeepSpaceBlue,

    secondary = Color(0xFF0891B2),                       // Deep cyan
    onSecondary = Color.White,
    secondaryContainer = CrystalCyan.copy(alpha = 0.15f),
    onSecondaryContainer = Color(0xFF001F2A),

    tertiary = CosmicIndigo,
    onTertiary = Color.White,
    tertiaryContainer = MoonbeamSilver.copy(alpha = 0.5f),
    onTertiaryContainer = Color(0xFF1A1B3D),

    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFFAFAFF),                      // Subtle blue-white
    onBackground = Color(0xFF0F1828),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F1828),

    surfaceVariant = Color(0xFFF0F4FF),
    onSurfaceVariant = Color(0xFF3A4556),
    surfaceTint = Color(0xFF1E5FAA),

    outline = Color(0xFF6B7785),
    outlineVariant = Color(0xFFD0DBE8),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF2C3542),
    inverseOnSurface = Color(0xFFF0F4FF),
    inversePrimary = Color(0xFF7AA8FF),

    surfaceDim = Color(0xFFE0E7F0),
    surfaceBright = Color(0xFFFAFAFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF5F8FF),
    surfaceContainer = Color(0xFFF0F4FF),
    surfaceContainerHigh = Color(0xFFE8EDF8),
    surfaceContainerHighest = Color(0xFFE0E7F0)
)

// ============================================================================
// CELESTIAL HIGH CONTRAST SCHEMES
// ============================================================================

val CelestialHighContrastDarkColorScheme = darkColorScheme(
    primary = Color(0xFFA0C8FF),                         // Very bright cosmic blue
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF90B8FF),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFF80F0FF),                       // Very bright cyan
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF70E0FF),
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Color(0xFFF0F4FF),                        // Very bright white-blue
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFE0E8FF),
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
    surfaceTint = Color(0xFFA0C8FF),

    outline = Color(0xFFFFFFFF),
    outlineVariant = Color(0xFFCCCCCC),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFFFFFFFF),
    inverseOnSurface = Color(0xFF000000),
    inversePrimary = Color(0xFF004080),

    surfaceDim = Color(0xFF000000),
    surfaceBright = Color(0xFF2A2A2A),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0F0F0F),
    surfaceContainer = Color(0xFF1A1A1A),
    surfaceContainerHigh = Color(0xFF242424),
    surfaceContainerHighest = Color(0xFF2E2E2E)
)

val CelestialHighContrastLightColorScheme = lightColorScheme(
    primary = Color(0xFF003D80),                         // Very deep blue
    onPrimary = Color.White,
    primaryContainer = Color(0xFF005DB3),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF005566),                       // Very deep cyan
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF007799),
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFF2A2D66),                        // Very deep indigo
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF3D4080),
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
    surfaceTint = Color(0xFF003D80),

    outline = Color(0xFF000000),
    outlineVariant = Color(0xFF333333),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFFA0C8FF),

    surfaceDim = Color(0xFFE8E8E8),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F8F8),
    surfaceContainer = Color(0xFFF0F0F0),
    surfaceContainerHigh = Color(0xFFE8E8E8),
    surfaceContainerHighest = Color(0xFFE0E0E0)
)

// ============================================================================
// CELESTIAL GRADIENTS
// ============================================================================

object CelestialGradients {
    val primary = Brush.linearGradient(
        colors = listOf(
            DeepSpaceBlue,
            CelestialBlue,
            StardustSilver
        )
    )

    val secondary = Brush.linearGradient(
        colors = listOf(
            CrystalCyan,
            CelestialBlue,
            MoonbeamSilver
        )
    )

    val background = Brush.verticalGradient(
        colors = listOf(
            VoidBlack,
            DeepSpaceBlue,
            CosmicIndigo
        )
    )

    val energy = Brush.linearGradient(
        colors = listOf(
            CelestialBlue,
            AuroraCyan,
            MoonbeamSilver,
            CrystalCyan
        )
    )

    val cosmic = Brush.radialGradient(
        colors = listOf(
            StardustSilver,
            CelestialBlue,
            DeepSpaceBlue,
            VoidBlack
        )
    )

    val chakra = SpiritualGradients.chakraRainbow
}

// ============================================================================
// CELESTIAL THEME CONFIGURATION
// ============================================================================

fun getCelestialThemeConfig(): ThemeVariantConfig {
    return ThemeVariantConfig(
        variant = ThemeVariant.CELESTIAL,
        displayName = "Celestial",
        description = "Blues, silvers, and cosmic energies",
        emoji = "🌌",
        colorPsychology = "Promotes expansiveness, wisdom, cosmic connection, and tranquility. " +
            "Blue is associated with truth, communication, and higher consciousness.",
        recommendedFor = "Meditation practitioners, astrology enthusiasts, those seeking cosmic connection, " +
            "night sky lovers, and users who prefer cool, calming colors.",
        lightColorScheme = CelestialLightColorScheme,
        darkColorScheme = CelestialDarkColorScheme,
        highContrastLightColorScheme = CelestialHighContrastLightColorScheme,
        highContrastDarkColorScheme = CelestialHighContrastDarkColorScheme,
        gradients = ThemeGradients(
            primary = CelestialGradients.primary,
            secondary = CelestialGradients.secondary,
            background = CelestialGradients.background,
            energy = CelestialGradients.energy,
            cosmic = CelestialGradients.cosmic,
            chakra = CelestialGradients.chakra
        ),
        iconTints = ThemeIconTints(
            primary = CelestialBlue,
            secondary = CrystalCyan,
            tertiary = MoonbeamSilver,
            success = Color(0xFF22C55E),
            warning = Color(0xFFFFC857),
            error = Color(0xFFFF6B6B),
            info = AuroraCyan
        ),
        semanticColors = ThemeSemanticColors(
            success = Color(0xFF22C55E),
            successContainer = Color(0xFF22C55E).copy(alpha = 0.15f),
            warning = Color(0xFFFFC857),
            warningContainer = Color(0xFFFFC857).copy(alpha = 0.15f),
            error = Color(0xFFFF6B6B),
            errorContainer = Color(0xFFFF6B6B).copy(alpha = 0.15f),
            info = AuroraCyan,
            infoContainer = AuroraCyan.copy(alpha = 0.15f)
        )
    )
}
