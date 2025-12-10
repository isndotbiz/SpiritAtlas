package com.spiritatlas.core.ui.imaging

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import java.time.LocalDateTime

/**
 * Smart background image selector that chooses appropriate spiritual backgrounds
 * based on context, time, season, and user preferences.
 *
 * Features:
 * - Context-aware selection (splash, home, profile, compatibility, etc.)
 * - Time-of-day adaptation (dawn, day, evening, night)
 * - Seasonal themes (spring, summer, autumn, winter)
 * - Day-of-week rotation for variety
 * - Graceful fallback to procedural backgrounds
 *
 * Usage:
 * ```
 * SpiritualBackgroundImage(
 *     context = BackgroundContext.HOME,
 *     alpha = 0.8f
 * ) {
 *     // Your content here
 * }
 * ```
 */
@Composable
fun SpiritualBackgroundImage(
    context: BackgroundContext = BackgroundContext.DEFAULT,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null,
    content: @Composable () -> Unit = {}
) {
    val backgroundAsset = remember(context) {
        selectBackgroundForContext(context)
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (backgroundAsset.startsWith("procedural:")) {
            // Use procedural background composable
            when (backgroundAsset.removePrefix("procedural:")) {
                "starfield" -> {
                    com.spiritatlas.core.ui.components.StarfieldBackground(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        content()
                    }
                }
                "cosmic_connection" -> {
                    com.spiritatlas.core.ui.components.CosmicConnectionBackground(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        content()
                    }
                }
                "sacred_geometry" -> {
                    com.spiritatlas.core.ui.components.SacredGeometryBackground(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        content()
                    }
                }
                else -> content()
            }
        } else {
            // Use static image
            Image(
                painter = rememberAsyncImagePainter(backgroundAsset),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha),
                contentScale = ContentScale.Crop,
                colorFilter = colorFilter
            )
            content()
        }
    }
}

/**
 * Background context determines which image/background to use
 */
enum class BackgroundContext {
    DEFAULT,
    SPLASH,
    HOME,
    PROFILE_LIST,
    PROFILE_DETAIL,
    COMPATIBILITY,
    ANALYSIS,
    MEDITATION,
    SETTINGS
}

/**
 * Core background selection logic
 */
fun selectBackgroundForContext(context: BackgroundContext): String {
    val now = LocalDateTime.now()
    val hour = now.hour
    val dayOfWeek = now.dayOfWeek.value

    return when (context) {
        BackgroundContext.SPLASH -> {
            // Alternate between deep space and aurora variants
            if (dayOfWeek % 2 == 0)
                "backgrounds/splash_screen_deep_space.png"
            else
                "backgrounds/splash_screen_aurora.png"
        }

        BackgroundContext.HOME -> {
            // Use procedural starfield for animation
            "procedural:starfield"
        }

        BackgroundContext.PROFILE_LIST -> {
            // Sacred geometry with subtle animation
            "procedural:sacred_geometry"
        }

        BackgroundContext.PROFILE_DETAIL -> {
            // Time-based cosmic gradient
            getTimeBasedBackground()
        }

        BackgroundContext.COMPATIBILITY -> {
            // Dual-energy cosmic connection
            "procedural:cosmic_connection"
        }

        BackgroundContext.ANALYSIS -> {
            "backgrounds/cosmic_gradient_deep.png"
        }

        BackgroundContext.MEDITATION -> {
            // Time-sensitive meditation backgrounds
            when {
                hour < 6 -> "backgrounds/deep_night_meditation.png"
                hour < 12 -> "backgrounds/morning_energy.png"
                hour < 18 -> "backgrounds/afternoon_balance.png"
                else -> "backgrounds/evening_calm.png"
            }
        }

        BackgroundContext.SETTINGS -> {
            "backgrounds/minimal_cosmic.png"
        }

        BackgroundContext.DEFAULT -> {
            "backgrounds/cosmic_gradient.png"
        }
    }
}

/**
 * Seasonal background variant selector
 */
fun getSeasonalBackground(): String {
    val month = LocalDateTime.now().monthValue
    return when {
        month in 3..5 -> "backgrounds/spring_renewal.png"     // Spring
        month in 6..8 -> "backgrounds/summer_radiance.png"    // Summer
        month in 9..11 -> "backgrounds/autumn_grounding.png"  // Autumn
        else -> "backgrounds/winter_cosmos.png"               // Winter
    }
}

/**
 * Time-of-day background selector
 */
fun getTimeBasedBackground(): String {
    val hour = LocalDateTime.now().hour
    return when {
        hour in 5..7 -> "backgrounds/dawn_awakening.png"      // Dawn
        hour in 8..11 -> "backgrounds/morning_energy.png"     // Morning
        hour in 12..16 -> "backgrounds/afternoon_balance.png" // Afternoon
        hour in 17..19 -> "backgrounds/golden_hour.png"       // Golden hour
        else -> "backgrounds/night_cosmos.png"                // Night
    }
}

/**
 * Day-of-week background rotation for home screen variety
 */
fun getDayOfWeekBackground(): String {
    return when (LocalDateTime.now().dayOfWeek.value) {
        1 -> "backgrounds/monday_introspection.png"   // Monday: Deep purple
        2 -> "backgrounds/tuesday_energy.png"         // Tuesday: Mars red
        3 -> "backgrounds/wednesday_communication.png" // Wednesday: Mercury
        4 -> "backgrounds/thursday_expansion.png"     // Thursday: Jupiter
        5 -> "backgrounds/friday_love.png"            // Friday: Venus
        6 -> "backgrounds/saturday_grounding.png"     // Saturday: Saturn
        7 -> "backgrounds/sunday_radiance.png"        // Sunday: Sun
        else -> "backgrounds/cosmic_gradient.png"
    }
}

/**
 * Moon phase background selector
 */
fun getMoonPhaseBackground(illumination: Float): String {
    return when {
        illumination < 0.1f -> "backgrounds/new_moon_dark.png"
        illumination < 0.4f -> "backgrounds/crescent_moon.png"
        illumination < 0.6f -> "backgrounds/half_moon.png"
        illumination < 0.9f -> "backgrounds/gibbous_moon.png"
        else -> "backgrounds/full_moon_bright.png"
    }
}
