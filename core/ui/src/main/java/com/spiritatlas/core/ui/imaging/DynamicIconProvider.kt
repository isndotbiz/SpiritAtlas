package com.spiritatlas.core.ui.imaging

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spiritatlas.core.ui.components.*

/**
 * Context-aware icon provider that selects appropriate icons
 * based on content type, user preferences, and visual hierarchy.
 *
 * Provides consistent icon usage across the app with automatic selection
 * between vector icons (for UI elements) and generated artwork (for backgrounds).
 *
 * Features:
 * - Zodiac icons (vector + constellation art)
 * - Chakra icons (vector + full visualizations)
 * - Element symbols
 * - Moon phases
 * - Sacred geometry
 * - Action icons
 *
 * Usage:
 * ```
 * DynamicIconProvider.ZodiacIcon(
 *     sign = ZodiacSign.LEO,
 *     displayMode = IconDisplayMode.VECTOR_SMALL
 * )
 * ```
 */
object DynamicIconProvider {

    /**
     * Get zodiac icon (vector or image) based on display context
     */
    @Composable
    fun ZodiacIcon(
        sign: ZodiacSign,
        displayMode: IconDisplayMode = IconDisplayMode.VECTOR_SMALL,
        modifier: Modifier = Modifier,
        color: Color? = null
    ) {
        when (displayMode) {
            IconDisplayMode.VECTOR_SMALL -> {
                when (sign) {
                    ZodiacSign.ARIES -> AriesIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.FireOrange)
                    ZodiacSign.TAURUS -> TaurusIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.EarthGreen)
                    ZodiacSign.GEMINI -> GeminiIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.AirCyan)
                    ZodiacSign.CANCER -> CancerIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.WaterTeal)
                    ZodiacSign.LEO -> LeoIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.FireOrange)
                    ZodiacSign.VIRGO -> VirgoIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.EarthGreen)
                    ZodiacSign.LIBRA -> LibraIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.AirCyan)
                    ZodiacSign.SCORPIO -> ScorpioIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.WaterTeal)
                    ZodiacSign.SAGITTARIUS -> SagittariusIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.FireOrange)
                    ZodiacSign.CAPRICORN -> CapricornIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.EarthGreen)
                    ZodiacSign.AQUARIUS -> AquariusIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.AirCyan)
                    ZodiacSign.PISCES -> PiscesIcon(modifier = modifier, size = 24.dp, color = color ?: com.spiritatlas.core.ui.theme.WaterTeal)
                }
            }
            IconDisplayMode.VECTOR_LARGE -> {
                when (sign) {
                    ZodiacSign.ARIES -> AriesIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.FireOrange)
                    ZodiacSign.TAURUS -> TaurusIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.EarthGreen)
                    ZodiacSign.GEMINI -> GeminiIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.AirCyan)
                    ZodiacSign.CANCER -> CancerIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.WaterTeal)
                    ZodiacSign.LEO -> LeoIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.FireOrange)
                    ZodiacSign.VIRGO -> VirgoIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.EarthGreen)
                    ZodiacSign.LIBRA -> LibraIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.AirCyan)
                    ZodiacSign.SCORPIO -> ScorpioIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.WaterTeal)
                    ZodiacSign.SAGITTARIUS -> SagittariusIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.FireOrange)
                    ZodiacSign.CAPRICORN -> CapricornIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.EarthGreen)
                    ZodiacSign.AQUARIUS -> AquariusIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.AirCyan)
                    ZodiacSign.PISCES -> PiscesIcon(modifier = modifier, size = 64.dp, color = color ?: com.spiritatlas.core.ui.theme.WaterTeal)
                }
            }
            IconDisplayMode.ART_BACKGROUND,
            IconDisplayMode.ART_HERO -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        "zodiac/constellation_${sign.name.lowercase()}.png"
                    ),
                    contentDescription = sign.name,
                    modifier = modifier,
                    contentScale = if (displayMode == IconDisplayMode.ART_BACKGROUND)
                        ContentScale.Crop else ContentScale.Fit,
                    alpha = if (displayMode == IconDisplayMode.ART_BACKGROUND) 0.3f else 1f
                )
            }
        }
    }

    /**
     * Get chakra icon with appropriate detail level
     */
    @Composable
    fun ChakraIcon(
        chakra: Chakra,
        displayMode: IconDisplayMode = IconDisplayMode.VECTOR_SMALL,
        showGlow: Boolean = true,
        modifier: Modifier = Modifier
    ) {
        when (displayMode) {
            IconDisplayMode.VECTOR_SMALL -> {
                val size = 24.dp
                when (chakra) {
                    Chakra.ROOT -> RootChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.SACRAL -> SacralChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.SOLAR_PLEXUS -> SolarPlexusChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.HEART -> HeartChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.THROAT -> ThroatChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.THIRD_EYE -> ThirdEyeChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.CROWN -> CrownChakraIcon(modifier, size, glowEffect = showGlow)
                }
            }
            IconDisplayMode.VECTOR_LARGE -> {
                val size = 64.dp
                when (chakra) {
                    Chakra.ROOT -> RootChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.SACRAL -> SacralChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.SOLAR_PLEXUS -> SolarPlexusChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.HEART -> HeartChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.THROAT -> ThroatChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.THIRD_EYE -> ThirdEyeChakraIcon(modifier, size, glowEffect = showGlow)
                    Chakra.CROWN -> CrownChakraIcon(modifier, size, glowEffect = showGlow)
                }
            }
            IconDisplayMode.ART_BACKGROUND,
            IconDisplayMode.ART_HERO -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        "chakras/chakra_${chakra.sanskritName.lowercase()}.png"
                    ),
                    contentDescription = chakra.sanskritName,
                    modifier = modifier,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    /**
     * Get element icon
     */
    @Composable
    fun ElementIcon(
        element: Element,
        size: Dp = 24.dp,
        showGlow: Boolean = true,
        modifier: Modifier = Modifier
    ) {
        when (element) {
            Element.FIRE -> FireElementIcon(modifier, size, showGlow)
            Element.WATER -> WaterElementIcon(modifier, size, showGlow)
            Element.EARTH -> EarthElementIcon(modifier, size, showGlow)
            Element.AIR -> AirElementIcon(modifier, size, showGlow)
            Element.SPIRIT -> SpiritElementIcon(modifier, size, showGlow)
        }
    }

    /**
     * Get moon phase icon
     */
    @Composable
    fun MoonPhaseIcon(
        phase: MoonPhase,
        displayMode: IconDisplayMode = IconDisplayMode.VECTOR_SMALL,
        modifier: Modifier = Modifier
    ) {
        when (displayMode) {
            IconDisplayMode.VECTOR_SMALL,
            IconDisplayMode.VECTOR_LARGE -> {
                val size = if (displayMode == IconDisplayMode.VECTOR_SMALL) 24.dp else 64.dp
                when (phase) {
                    MoonPhase.NEW_MOON -> NewMoonIcon(modifier, size)
                    MoonPhase.WAXING_CRESCENT -> WaxingMoonIcon(modifier, size)
                    MoonPhase.FIRST_QUARTER -> FullMoonIcon(modifier, size)
                    MoonPhase.WAXING_GIBBOUS -> FullMoonIcon(modifier, size)
                    MoonPhase.FULL_MOON -> FullMoonIcon(modifier, size)
                    MoonPhase.WANING_GIBBOUS -> WaningMoonIcon(modifier, size)
                    MoonPhase.LAST_QUARTER -> WaningMoonIcon(modifier, size)
                    MoonPhase.WANING_CRESCENT -> WaningMoonIcon(modifier, size)
                }
            }
            IconDisplayMode.ART_BACKGROUND,
            IconDisplayMode.ART_HERO -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        "moon_phases/moon_${phase.name.lowercase().replace('_', '_')}.png"
                    ),
                    contentDescription = phase.phaseName,
                    modifier = modifier,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    /**
     * Get sacred geometry icon
     */
    @Composable
    fun SacredGeometryIcon(
        geometry: SacredGeometry,
        size: Dp = 24.dp,
        showGlow: Boolean = true,
        modifier: Modifier = Modifier
    ) {
        when (geometry) {
            SacredGeometry.FLOWER_OF_LIFE -> FlowerOfLifeIcon(modifier, size, glowEffect = showGlow)
            SacredGeometry.METATRONS_CUBE -> MetatronsCubeIcon(modifier, size, glowEffect = showGlow)
            SacredGeometry.SRI_YANTRA -> SriYantraIcon(modifier, size, glowEffect = showGlow)
            SacredGeometry.VESICA_PISCIS -> VesicaPiscisIcon(modifier, size, glowEffect = showGlow)
        }
    }

    /**
     * Get action icon for common actions
     */
    @Composable
    fun ActionIcon(
        action: SpiritualAction,
        size: Dp = 24.dp,
        color: Color? = null,
        modifier: Modifier = Modifier
    ) {
        when (action) {
            SpiritualAction.ADD -> SpiritElementIcon(modifier, size)
            SpiritualAction.EDIT -> MercuryIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.AirCyan)
            SpiritualAction.DELETE -> SaturnIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.NightSky)
            SpiritualAction.SHARE -> VenusIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.TantricRose)
            SpiritualAction.SEARCH -> ThirdEyeChakraIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.ChakraIndigo)
            SpiritualAction.FAVORITE -> HeartChakraIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.ChakraGreen)
            SpiritualAction.SETTINGS -> FlowerOfLifeIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.SacredGold)
            SpiritualAction.COMPATIBILITY -> VesicaPiscisIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.MysticPurple)
            SpiritualAction.PROFILE -> SunIcon(modifier, size, color ?: com.spiritatlas.core.ui.theme.SacredGold)
        }
    }

    /**
     * Get planet icon
     */
    @Composable
    fun PlanetIcon(
        planet: Planet,
        size: Dp = 24.dp,
        modifier: Modifier = Modifier
    ) {
        when (planet) {
            Planet.SUN -> SunIcon(modifier, size)
            Planet.MOON -> FullMoonIcon(modifier, size)
            Planet.MERCURY -> MercuryIcon(modifier, size)
            Planet.VENUS -> VenusIcon(modifier, size)
            Planet.MARS -> MarsIcon(modifier, size)
            Planet.JUPITER -> JupiterIcon(modifier, size)
            Planet.SATURN -> SaturnIcon(modifier, size)
        }
    }
}

/**
 * Icon display mode determines how icons are rendered
 */
enum class IconDisplayMode {
    VECTOR_SMALL,      // 24dp vector icon (buttons, chips)
    VECTOR_LARGE,      // 64dp+ vector icon (cards, headers)
    ART_BACKGROUND,    // Full image as blurred background
    ART_HERO           // Full image as featured element
}

/**
 * Spiritual actions for consistent icon mapping
 */
enum class SpiritualAction {
    ADD, EDIT, DELETE, SHARE, SEARCH,
    FAVORITE, SETTINGS, COMPATIBILITY, PROFILE
}

/**
 * Zodiac signs enum
 */
enum class ZodiacSign {
    ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO,
    LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES
}

/**
 * Chakras enum
 */
enum class Chakra(val number: Int, val sanskritName: String) {
    ROOT(1, "Muladhara"),
    SACRAL(2, "Svadhisthana"),
    SOLAR_PLEXUS(3, "Manipura"),
    HEART(4, "Anahata"),
    THROAT(5, "Vishuddha"),
    THIRD_EYE(6, "Ajna"),
    CROWN(7, "Sahasrara")
}

/**
 * Elements enum
 */
enum class Element {
    FIRE, WATER, EARTH, AIR, SPIRIT
}

/**
 * Moon phases enum
 */
enum class MoonPhase(val phaseName: String) {
    NEW_MOON("New Moon"),
    WAXING_CRESCENT("Waxing Crescent"),
    FIRST_QUARTER("First Quarter"),
    WAXING_GIBBOUS("Waxing Gibbous"),
    FULL_MOON("Full Moon"),
    WANING_GIBBOUS("Waning Gibbous"),
    LAST_QUARTER("Last Quarter"),
    WANING_CRESCENT("Waning Crescent")
}

/**
 * Sacred geometry patterns
 */
enum class SacredGeometry {
    FLOWER_OF_LIFE,
    METATRONS_CUBE,
    SRI_YANTRA,
    VESICA_PISCIS
}

/**
 * Planets enum
 */
enum class Planet {
    SUN, MOON, MERCURY, VENUS, MARS, JUPITER, SATURN
}
