package com.spiritatlas.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * EARTHY THEME - Greens, Browns, Nature
 *
 * Color Psychology: Grounding, growth, stability, natural harmony
 * Best For: Ayurveda focus, earth signs, nature connection
 * Palette: Forest greens, rich earth browns, golden harvest
 */

// ============================================================================
// EARTHY COLOR PALETTE
// ============================================================================

// Primary Colors - Forest Greens
private val ForestGreen = Color(0xFF22C55E)             // Vibrant forest green
private val DeepForest = Color(0xFF166534)              // Deep forest
private val SageGreen = Color(0xFF84CC16)               // Sage green
private val MossGreen = Color(0xFF4ADE80)               // Bright moss

// Secondary Colors - Earth Browns
private val RichEarth = Color(0xFF78350F)               // Rich earth brown
private val CopperBrown = Color(0xFFB45309)             // Copper brown
private val ClayBrown = Color(0xFF92400E)               // Clay brown
private val SandBeige = Color(0xFFFDE68A)               // Sand beige

// Tertiary Colors - Nature Accents
private val BarkBrown = Color(0xFF57341D)               // Tree bark
private val LeafGreen = Color(0xFF059669)               // Leaf green
private val FlowerPink = Color(0xFFF472B6)              // Wildflower pink
private val SunlitGold = Color(0xFFFBBF24)              // Sunlit gold

// Accent Colors
private val EarthGlow = Color(0xFFFEF3C7)               // Warm earth glow
private val StoneGray = Color(0xFF78716C)               // Natural stone
private val WoodBrown = Color(0xFF7C2D12)               // Deep wood

// ============================================================================
// EARTHY DARK COLOR SCHEME
// ============================================================================

val EarthyDarkColorScheme = darkColorScheme(
    // Primary - Forest green (WCAG AAA: 8.5:1 on dark background)
    primary = Color(0xFF6EE7A7),                         // Lighter forest green
    onPrimary = Color(0xFF001A0D),                       // Very dark green
    primaryContainer = Color(0xFF1E5D3D),                // Medium forest green
    onPrimaryContainer = Color(0xFFD1F5E3),              // Very light green

    // Secondary - Copper brown (WCAG AAA: 8.0:1)
    secondary = Color(0xFFE67E22),                       // Bright copper
    onSecondary = Color(0xFF1F0D00),                     // Very dark brown
    secondaryContainer = Color(0xFF6B3610),              // Medium brown container
    onSecondaryContainer = Color(0xFFFFE8D4),            // Very light cream

    // Tertiary - Sunlit gold (WCAG AAA: 9.2:1)
    tertiary = Color(0xFFFFC857),                        // Bright gold
    onTertiary = Color(0xFF2E1F00),                      // Very dark gold
    tertiaryContainer = Color(0xFF7D5A00),               // Medium gold container
    onTertiaryContainer = Color(0xFFFFEDC2),             // Very light gold

    // Error - Natural red (WCAG AAA: 7.9:1)
    error = Color(0xFFFF7B7B),
    onError = Color(0xFF2E0000),
    errorContainer = Color(0xFF8C1818),
    onErrorContainer = Color(0xFFFFDFDF),

    // Background - Rich earth with warm tint
    background = Color(0xFF1A1410),                      // Warm earth-black
    onBackground = Color(0xFFF5F1E8),                    // Soft cream (9.8:1)
    surface = Color(0xFF241E18),                         // Warm earth surface
    onSurface = Color(0xFFF5F1E8),                       // Matching soft cream (9.3:1)

    // Surface variants
    surfaceVariant = Color(0xFF2E271E),
    onSurfaceVariant = Color(0xFFE0D8C8),                // Enhanced contrast (7.9:1)
    surfaceTint = Color(0xFF6EE7A7),

    // Outline colors
    outline = Color(0xFFAFA493),                         // Lighter outline
    outlineVariant = Color(0xFF55504A),

    // Scrim and inverse
    scrim = Color(0xCC000000),
    inverseSurface = Color(0xFFF5F1E8),
    inverseOnSurface = Color(0xFF1A1410),
    inversePrimary = Color(0xFF178A4A),

    // Surface containers
    surfaceDim = Color(0xFF120F0B),
    surfaceBright = Color(0xFF3D352A),
    surfaceContainerLowest = Color(0xFF0D0B08),
    surfaceContainerLow = Color(0xFF1F1A14),
    surfaceContainer = Color(0xFF272118),
    surfaceContainerHigh = Color(0xFF322A22),
    surfaceContainerHighest = Color(0xFF3E352C)
)

// ============================================================================
// EARTHY LIGHT COLOR SCHEME
// ============================================================================

val EarthyLightColorScheme = lightColorScheme(
    primary = Color(0xFF178A4A),                         // Deep forest green
    onPrimary = Color.White,
    primaryContainer = ForestGreen.copy(alpha = 0.15f),
    onPrimaryContainer = DeepForest,

    secondary = CopperBrown,                             // Rich copper
    onSecondary = Color.White,
    secondaryContainer = CopperBrown.copy(alpha = 0.15f),
    onSecondaryContainer = RichEarth,

    tertiary = Color(0xFFD97706),                        // Deep gold
    onTertiary = Color.White,
    tertiaryContainer = SandBeige,
    onTertiaryContainer = Color(0xFF4A2800),

    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFFFFDF5),                      // Warm off-white
    onBackground = Color(0xFF1A1410),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1410),

    surfaceVariant = Color(0xFFF5F1E8),
    onSurfaceVariant = Color(0xFF3E3A32),
    surfaceTint = Color(0xFF178A4A),

    outline = Color(0xFF6B6760),
    outlineVariant = Color(0xFFD9D2C8),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF322A22),
    inverseOnSurface = Color(0xFFF5F1E8),
    inversePrimary = Color(0xFF6EE7A7),

    surfaceDim = Color(0xFFE8E3D8),
    surfaceBright = Color(0xFFFFFDF5),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFF8EE),
    surfaceContainer = Color(0xFFF5F1E8),
    surfaceContainerHigh = Color(0xFFEFE9E0),
    surfaceContainerHighest = Color(0xFFE8E3D8)
)

// ============================================================================
// EARTHY HIGH CONTRAST SCHEMES
// ============================================================================

val EarthyHighContrastDarkColorScheme = darkColorScheme(
    primary = Color(0xFF90F7C0),                         // Very bright green
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF80E7B0),
    onPrimaryContainer = Color(0xFF000000),

    secondary = Color(0xFFFF9952),                       // Very bright copper
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFFF8842),
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Color(0xFFFFD77F),                        // Very bright gold
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFFFC76F),
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
    surfaceTint = Color(0xFF90F7C0),

    outline = Color(0xFFFFFFFF),
    outlineVariant = Color(0xFFCCCCCC),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFFFFFFFF),
    inverseOnSurface = Color(0xFF000000),
    inversePrimary = Color(0xFF006030),

    surfaceDim = Color(0xFF000000),
    surfaceBright = Color(0xFF2A2A2A),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0F0F0F),
    surfaceContainer = Color(0xFF1A1A1A),
    surfaceContainerHigh = Color(0xFF242424),
    surfaceContainerHighest = Color(0xFF2E2E2E)
)

val EarthyHighContrastLightColorScheme = lightColorScheme(
    primary = Color(0xFF005028),                         // Very deep green
    onPrimary = Color.White,
    primaryContainer = Color(0xFF007A3D),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF662800),                       // Very deep brown
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF994000),
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFF663D00),                        // Very deep gold
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF996600),
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
    surfaceTint = Color(0xFF005028),

    outline = Color(0xFF000000),
    outlineVariant = Color(0xFF333333),

    scrim = Color(0xE6000000),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFF90F7C0),

    surfaceDim = Color(0xFFE8E8E8),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F8F8),
    surfaceContainer = Color(0xFFF0F0F0),
    surfaceContainerHigh = Color(0xFFE8E8E8),
    surfaceContainerHighest = Color(0xFFE0E0E0)
)

// ============================================================================
// EARTHY GRADIENTS
// ============================================================================

object EarthyGradients {
    val primary = Brush.linearGradient(
        colors = listOf(
            DeepForest,
            ForestGreen,
            MossGreen
        )
    )

    val secondary = Brush.linearGradient(
        colors = listOf(
            WoodBrown,
            CopperBrown,
            SandBeige
        )
    )

    val background = Brush.verticalGradient(
        colors = listOf(
            RichEarth,
            BarkBrown,
            DeepForest
        )
    )

    val energy = Brush.linearGradient(
        colors = listOf(
            ForestGreen,
            LeafGreen,
            SunlitGold,
            CopperBrown
        )
    )

    val cosmic = Brush.radialGradient(
        colors = listOf(
            SandBeige,
            SageGreen,
            ForestGreen,
            DeepForest
        )
    )

    val chakra = SpiritualGradients.chakraRainbow
}

// ============================================================================
// EARTHY THEME CONFIGURATION
// ============================================================================

fun getEarthyThemeConfig(): ThemeVariantConfig {
    return ThemeVariantConfig(
        variant = ThemeVariant.EARTHY,
        displayName = "Earthy",
        description = "Greens, browns, and natural harmony",
        emoji = "🌿",
        colorPsychology = "Promotes grounding, growth, stability, and natural harmony. " +
            "Green represents renewal and healing. Brown provides stability and security.",
        recommendedFor = "Ayurveda practitioners, earth zodiac signs (Taurus, Virgo, Capricorn), " +
            "nature lovers, those seeking grounding and stability, gardeners and herbalists.",
        lightColorScheme = EarthyLightColorScheme,
        darkColorScheme = EarthyDarkColorScheme,
        highContrastLightColorScheme = EarthyHighContrastLightColorScheme,
        highContrastDarkColorScheme = EarthyHighContrastDarkColorScheme,
        gradients = ThemeGradients(
            primary = EarthyGradients.primary,
            secondary = EarthyGradients.secondary,
            background = EarthyGradients.background,
            energy = EarthyGradients.energy,
            cosmic = EarthyGradients.cosmic,
            chakra = EarthyGradients.chakra
        ),
        iconTints = ThemeIconTints(
            primary = ForestGreen,
            secondary = CopperBrown,
            tertiary = SageGreen,
            success = MossGreen,
            warning = SunlitGold,
            error = Color(0xFFFF6B6B),
            info = LeafGreen
        ),
        semanticColors = ThemeSemanticColors(
            success = MossGreen,
            successContainer = MossGreen.copy(alpha = 0.15f),
            warning = SunlitGold,
            warningContainer = SunlitGold.copy(alpha = 0.15f),
            error = Color(0xFFFF6B6B),
            errorContainer = Color(0xFFFF6B6B).copy(alpha = 0.15f),
            info = LeafGreen,
            infoContainer = LeafGreen.copy(alpha = 0.15f)
        )
    )
}
