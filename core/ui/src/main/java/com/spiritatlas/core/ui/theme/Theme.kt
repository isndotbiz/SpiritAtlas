package com.spiritatlas.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

// ============================================================================
// SPIRITUAL COLOR SCHEMES - Custom Material 3 color schemes
// ============================================================================

/**
 * Dark color scheme with spiritual purple and cosmic themes
 */
private val SpiritualDarkColorScheme = darkColorScheme(
  primary = MysticPurple,
  onPrimary = Color.White,
  primaryContainer = CosmicViolet,
  onPrimaryContainer = MoonlightSilver,

  secondary = AuroraPink,
  onSecondary = Color.White,
  secondaryContainer = TantricRose,
  onSecondaryContainer = MoonlightSilver,

  tertiary = SacredGold,
  onTertiary = NightSky,
  tertiaryContainer = TempleBronze,
  onTertiaryContainer = MoonlightSilver,

  error = ChakraRed,
  onError = Color.White,
  errorContainer = Color(0xFF93000A),
  onErrorContainer = Color(0xFFFFDAD6),

  background = DarkBackground,
  onBackground = DarkOnBackground,
  surface = DarkSurface,
  onSurface = DarkOnSurface,

  surfaceVariant = Color(0xFF2D2D2D),
  onSurfaceVariant = Color(0xFFCAC4D0),
  surfaceTint = MysticPurple,

  outline = Color(0xFF938F99),
  outlineVariant = Color(0xFF49454F),

  scrim = OverlayColors.Scrim,
  inverseSurface = Color(0xFFE6E1E5),
  inverseOnSurface = Color(0xFF1C1B1F),
  inversePrimary = CosmicViolet,

  surfaceDim = Color(0xFF141218),
  surfaceBright = Color(0xFF3B383E),
  surfaceContainerLowest = Color(0xFF0F0D13),
  surfaceContainerLow = Color(0xFF1D1B20),
  surfaceContainer = Color(0xFF211F26),
  surfaceContainerHigh = Color(0xFF2B2930),
  surfaceContainerHighest = Color(0xFF36343B)
)

/**
 * Light color scheme with spiritual purple and cosmic themes
 */
private val SpiritualLightColorScheme = lightColorScheme(
  primary = CosmicViolet,
  onPrimary = Color.White,
  primaryContainer = MysticPurple.copy(alpha = 0.15f),
  onPrimaryContainer = CosmicViolet,

  secondary = AuroraPink,
  onSecondary = Color.White,
  secondaryContainer = TantricRose.copy(alpha = 0.15f),
  onSecondaryContainer = Color(0xFF31111D),

  tertiary = TempleBronze,
  onTertiary = Color.White,
  tertiaryContainer = SacredGold.copy(alpha = 0.15f),
  onTertiaryContainer = GroundingBrown,

  error = ChakraRed,
  onError = Color.White,
  errorContainer = Color(0xFFFFDAD6),
  onErrorContainer = Color(0xFF410002),

  background = LightBackground,
  onBackground = LightOnBackground,
  surface = LightSurface,
  onSurface = LightOnSurface,

  surfaceVariant = Color(0xFFF3EDF7),
  onSurfaceVariant = Color(0xFF49454F),
  surfaceTint = CosmicViolet,

  outline = Color(0xFF79747E),
  outlineVariant = Color(0xFFCAC4D0),

  scrim = OverlayColors.Scrim,
  inverseSurface = Color(0xFF313033),
  inverseOnSurface = Color(0xFFF4EFF4),
  inversePrimary = MysticPurple,

  surfaceDim = Color(0xFFDED8E1),
  surfaceBright = Color(0xFFFEF7FF),
  surfaceContainerLowest = Color(0xFFFFFFFF),
  surfaceContainerLow = Color(0xFFF7F2FA),
  surfaceContainer = Color(0xFFF3EDF7),
  surfaceContainerHigh = Color(0xFFECE6F0),
  surfaceContainerHighest = Color(0xFFE6E0E9)
)

/**
 * High contrast dark color scheme for accessibility
 */
private val HighContrastDarkColorScheme = darkColorScheme(
  primary = Color(0xFFE0B0FF),
  onPrimary = Color.Black,
  primaryContainer = Color(0xFFD0A0FF),
  onPrimaryContainer = Color.Black,

  secondary = Color(0xFFFF99CC),
  onSecondary = Color.Black,
  secondaryContainer = Color(0xFFFF88BB),
  onSecondaryContainer = Color.Black,

  tertiary = Color(0xFFFFD700),
  onTertiary = Color.Black,
  tertiaryContainer = Color(0xFFFFCC00),
  onTertiaryContainer = Color.Black,

  background = Color.Black,
  onBackground = Color.White,
  surface = Color(0xFF000000),
  onSurface = Color.White
)

/**
 * High contrast light color scheme for accessibility
 */
private val HighContrastLightColorScheme = lightColorScheme(
  primary = Color(0xFF4A0080),
  onPrimary = Color.White,
  primaryContainer = Color(0xFF6A00A8),
  onPrimaryContainer = Color.White,

  secondary = Color(0xFFC0004A),
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFE0006A),
  onSecondaryContainer = Color.White,

  tertiary = Color(0xFF804000),
  onTertiary = Color.White,
  tertiaryContainer = Color(0xFFA05000),
  onTertiaryContainer = Color.White,

  background = Color.White,
  onBackground = Color.Black,
  surface = Color(0xFFFFFFFF),
  onSurface = Color.Black
)

// ============================================================================
// SACRED SHAPES - Inspired by sacred geometry
// ============================================================================

/**
 * Sacred geometry inspired shapes with specific rounded corners
 */
val SpiritualShapes = Shapes(
  extraSmall = RoundedCornerShape(4.dp),   // Small elements like chips
  small = RoundedCornerShape(8.dp),        // Buttons, small cards
  medium = RoundedCornerShape(12.dp),      // Cards, dialogs
  large = RoundedCornerShape(16.dp),       // Large cards, sheets
  extraLarge = RoundedCornerShape(28.dp)   // Hero elements, full screen
)

/**
 * Alternative organic shapes for specific use cases
 */
val OrganicShapes = Shapes(
  extraSmall = RoundedCornerShape(6.dp),
  small = RoundedCornerShape(12.dp),
  medium = RoundedCornerShape(18.dp),
  large = RoundedCornerShape(24.dp),
  extraLarge = RoundedCornerShape(32.dp)
)

// ============================================================================
// ELEVATION SYSTEM - Custom depth and shadows
// ============================================================================

/**
 * Elevation levels for spiritual UI depth
 */
@Immutable
data class SpiritualElevation(
  val level0: androidx.compose.ui.unit.Dp = 0.dp,   // Surface
  val level1: androidx.compose.ui.unit.Dp = 1.dp,   // Raised
  val level2: androidx.compose.ui.unit.Dp = 3.dp,   // Cards
  val level3: androidx.compose.ui.unit.Dp = 6.dp,   // Floating
  val level4: androidx.compose.ui.unit.Dp = 8.dp,   // Nav drawer
  val level5: androidx.compose.ui.unit.Dp = 12.dp   // Modal
)

// ============================================================================
// THEME CONFIGURATION - Settings and preferences
// ============================================================================

/**
 * Theme configuration for SpiritAtlas app
 */
@Immutable
data class SpiritualThemeConfig(
  val useDynamicColor: Boolean = true,
  val useHighContrast: Boolean = false,
  val reduceMotion: Boolean = false,
  val useOrganicShapes: Boolean = false,
  val animationDurationMs: Int = 300
)

// ============================================================================
// EXTENDED THEME - Additional theme values
// ============================================================================

/**
 * Extended theme values for spiritual features
 */
@Immutable
data class ExtendedColors(
  val chakraColors: List<Color> = SpiritualColors.ChakraColors,
  val elementColors: Map<String, Color> = SpiritualColors.ElementColors,
  val planetaryColors: Map<String, Color> = SpiritualColors.PlanetaryColors,
  val glowPurple: Color = ShadowColors.PurpleGlow,
  val glowGold: Color = ShadowColors.GoldGlow,
  val glowBlue: Color = ShadowColors.BlueGlow,
  val glowPink: Color = ShadowColors.PinkGlow,
  val shadowLight: Color = ShadowColors.LightShadow,
  val shadowMedium: Color = ShadowColors.MediumShadow,
  val shadowDark: Color = ShadowColors.DarkShadow,
  val success: Color = SemanticColors.Success,
  val error: Color = SemanticColors.Error,
  val warning: Color = SemanticColors.Warning,
  val info: Color = SemanticColors.Info
)

// ============================================================================
// COMPOSITION LOCALS - For accessing extended theme values
// ============================================================================

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }
val LocalSpiritualElevation = staticCompositionLocalOf { SpiritualElevation() }
val LocalThemeConfig = staticCompositionLocalOf { SpiritualThemeConfig() }

// ============================================================================
// MAIN THEME COMPOSABLE
// ============================================================================

/**
 * Main theme composable for SpiritAtlas with dynamic theming support
 *
 * @param darkTheme Whether to use dark theme
 * @param config Theme configuration settings
 * @param content The composable content to theme
 */
@Composable
fun SpiritAtlasTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  config: SpiritualThemeConfig = SpiritualThemeConfig(),
  content: @Composable () -> Unit
) {
  val context = LocalContext.current

  // Determine the color scheme based on configuration
  val colorScheme = when {
    // Dynamic color (Android 12+)
    config.useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      if (darkTheme) {
        dynamicDarkColorScheme(context)
      } else {
        dynamicLightColorScheme(context)
      }
    }
    // High contrast mode
    config.useHighContrast -> {
      if (darkTheme) HighContrastDarkColorScheme else HighContrastLightColorScheme
    }
    // Standard spiritual theme
    else -> {
      if (darkTheme) SpiritualDarkColorScheme else SpiritualLightColorScheme
    }
  }

  // Animate color transitions if motion is not reduced
  val animatedPrimary by animateColorAsState(
    targetValue = colorScheme.primary,
    animationSpec = if (config.reduceMotion) {
      spring(stiffness = Spring.StiffnessHigh)
    } else {
      spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    },
    label = "primary_color"
  )

  val animatedBackground by animateColorAsState(
    targetValue = colorScheme.background,
    animationSpec = if (config.reduceMotion) {
      spring(stiffness = Spring.StiffnessHigh)
    } else {
      spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    },
    label = "background_color"
  )

  // Update system bars
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      window?.let {
        it.statusBarColor = animatedPrimary.toArgb()
        it.navigationBarColor = animatedBackground.toArgb()
        WindowCompat.getInsetsController(it, view).apply {
          isAppearanceLightStatusBars = !darkTheme
          isAppearanceLightNavigationBars = !darkTheme
        }
      }
    }
  }

  // Choose shapes based on configuration
  val shapes = if (config.useOrganicShapes) OrganicShapes else SpiritualShapes

  // Provide all theme values
  CompositionLocalProvider(
    LocalExtendedColors provides ExtendedColors(),
    LocalSpiritualElevation provides SpiritualElevation(),
    LocalThemeConfig provides config
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      shapes = shapes,
      content = content
    )
  }
}

// ============================================================================
// THEME ACCESSORS - Easy access to extended theme values
// ============================================================================

/**
 * Access extended colors from the theme
 */
object SpiritualTheme {
  val extendedColors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current

  val elevation: SpiritualElevation
    @Composable
    get() = LocalSpiritualElevation.current

  val config: SpiritualThemeConfig
    @Composable
    get() = LocalThemeConfig.current

  val colors: androidx.compose.material3.ColorScheme
    @Composable
    get() = MaterialTheme.colorScheme

  val typography: androidx.compose.material3.Typography
    @Composable
    get() = MaterialTheme.typography

  val shapes: Shapes
    @Composable
    get() = MaterialTheme.shapes
}

// ============================================================================
// PREVIEW HELPERS - For Compose previews
// ============================================================================

/**
 * Preview wrapper for light theme
 */
@Composable
fun SpiritAtlasLightThemePreview(content: @Composable () -> Unit) {
  SpiritAtlasTheme(
    darkTheme = false,
    config = SpiritualThemeConfig(useDynamicColor = false),
    content = content
  )
}

/**
 * Preview wrapper for dark theme
 */
@Composable
fun SpiritAtlasDarkThemePreview(content: @Composable () -> Unit) {
  SpiritAtlasTheme(
    darkTheme = true,
    config = SpiritualThemeConfig(useDynamicColor = false),
    content = content
  )
}

/**
 * Preview wrapper for high contrast theme
 */
@Composable
fun SpiritAtlasHighContrastPreview(content: @Composable () -> Unit) {
  SpiritAtlasTheme(
    darkTheme = true,
    config = SpiritualThemeConfig(
      useDynamicColor = false,
      useHighContrast = true
    ),
    content = content
  )
}
