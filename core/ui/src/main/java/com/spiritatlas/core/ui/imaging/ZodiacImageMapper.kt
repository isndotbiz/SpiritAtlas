package com.spiritatlas.core.ui.imaging

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spiritatlas.core.ui.theme.ZodiacElementColors
import java.time.LocalDate

/**
 * Maps zodiac signs to their visual representations and provides
 * date-based zodiac detection for seasonal imagery.
 *
 * Features:
 * - Automatic zodiac sign detection from birth date
 * - Current zodiac season detection
 * - Constellation background rendering
 * - Element-colored zodiac cards
 *
 * Usage:
 * ```
 * val sign = ZodiacImageMapper.getZodiacSign(month = 7, day = 23) // Leo
 * ZodiacImageMapper.ZodiacConstellationBackground(sign = sign)
 * ```
 */
object ZodiacImageMapper {

    /**
     * Get zodiac sign from birth date
     */
    fun getZodiacSign(month: Int, day: Int): ZodiacSign {
        return when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> ZodiacSign.ARIES
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> ZodiacSign.TAURUS
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> ZodiacSign.GEMINI
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> ZodiacSign.CANCER
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> ZodiacSign.LEO
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> ZodiacSign.VIRGO
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> ZodiacSign.LIBRA
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> ZodiacSign.SCORPIO
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> ZodiacSign.SAGITTARIUS
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> ZodiacSign.CAPRICORN
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> ZodiacSign.AQUARIUS
            else -> ZodiacSign.PISCES
        }
    }

    /**
     * Get current zodiac season based on today's date
     */
    fun getCurrentZodiacSeason(): ZodiacSign {
        val now = LocalDate.now()
        return getZodiacSign(now.monthValue, now.dayOfMonth)
    }

    /**
     * Get zodiac element
     */
    fun getZodiacElement(sign: ZodiacSign): Element {
        return when (sign) {
            ZodiacSign.ARIES, ZodiacSign.LEO, ZodiacSign.SAGITTARIUS -> Element.FIRE
            ZodiacSign.TAURUS, ZodiacSign.VIRGO, ZodiacSign.CAPRICORN -> Element.EARTH
            ZodiacSign.GEMINI, ZodiacSign.LIBRA, ZodiacSign.AQUARIUS -> Element.AIR
            ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.PISCES -> Element.WATER
        }
    }

    /**
     * Get element colors for a zodiac sign
     */
    fun getZodiacColors(sign: ZodiacSign): List<androidx.compose.ui.graphics.Color> {
        return when (getZodiacElement(sign)) {
            Element.FIRE -> ZodiacElementColors.FireSignColors
            Element.EARTH -> ZodiacElementColors.EarthSignColors
            Element.AIR -> ZodiacElementColors.AirSignColors
            Element.WATER -> ZodiacElementColors.WaterSignColors
            else -> ZodiacElementColors.AirSignColors
        }
    }

    /**
     * Render zodiac constellation as background image
     */
    @Composable
    fun ZodiacConstellationBackground(
        sign: ZodiacSign,
        modifier: Modifier = Modifier,
        alpha: Float = 0.3f
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                "zodiac/constellation_${sign.name.lowercase()}.png"
            ),
            contentDescription = "${sign.name} constellation",
            modifier = modifier.fillMaxSize().alpha(alpha),
            contentScale = ContentScale.Crop
        )
    }

    /**
     * Render zodiac card with element colors and constellation
     */
    @Composable
    fun ZodiacElementCard(
        sign: ZodiacSign,
        modifier: Modifier = Modifier,
        showConstellation: Boolean = true,
        showIcon: Boolean = true
    ) {
        Box(modifier = modifier) {
            val colors = getZodiacColors(sign)

            // Background gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = colors.take(2)
                        )
                    )
            )

            // Constellation background
            if (showConstellation) {
                ZodiacConstellationBackground(
                    sign = sign,
                    alpha = 0.4f
                )
            }

            // Vector icon overlay
            if (showIcon) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DynamicIconProvider.ZodiacIcon(
                        sign = sign,
                        displayMode = IconDisplayMode.VECTOR_LARGE,
                        modifier = Modifier.size(80.dp),
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }

    /**
     * Get zodiac sign name
     */
    fun getZodiacSignName(sign: ZodiacSign): String {
        return when (sign) {
            ZodiacSign.ARIES -> "Aries the Ram"
            ZodiacSign.TAURUS -> "Taurus the Bull"
            ZodiacSign.GEMINI -> "Gemini the Twins"
            ZodiacSign.CANCER -> "Cancer the Crab"
            ZodiacSign.LEO -> "Leo the Lion"
            ZodiacSign.VIRGO -> "Virgo the Maiden"
            ZodiacSign.LIBRA -> "Libra the Scales"
            ZodiacSign.SCORPIO -> "Scorpio the Scorpion"
            ZodiacSign.SAGITTARIUS -> "Sagittarius the Archer"
            ZodiacSign.CAPRICORN -> "Capricorn the Goat"
            ZodiacSign.AQUARIUS -> "Aquarius the Water Bearer"
            ZodiacSign.PISCES -> "Pisces the Fish"
        }
    }

    /**
     * Get zodiac date range
     */
    fun getZodiacDateRange(sign: ZodiacSign): String {
        return when (sign) {
            ZodiacSign.ARIES -> "March 21 - April 19"
            ZodiacSign.TAURUS -> "April 20 - May 20"
            ZodiacSign.GEMINI -> "May 21 - June 20"
            ZodiacSign.CANCER -> "June 21 - July 22"
            ZodiacSign.LEO -> "July 23 - August 22"
            ZodiacSign.VIRGO -> "August 23 - September 22"
            ZodiacSign.LIBRA -> "September 23 - October 22"
            ZodiacSign.SCORPIO -> "October 23 - November 21"
            ZodiacSign.SAGITTARIUS -> "November 22 - December 21"
            ZodiacSign.CAPRICORN -> "December 22 - January 19"
            ZodiacSign.AQUARIUS -> "January 20 - February 18"
            ZodiacSign.PISCES -> "February 19 - March 20"
        }
    }

    /**
     * Get ruling planet for zodiac sign
     */
    fun getRulingPlanet(sign: ZodiacSign): Planet {
        return when (sign) {
            ZodiacSign.ARIES -> Planet.MARS
            ZodiacSign.TAURUS -> Planet.VENUS
            ZodiacSign.GEMINI -> Planet.MERCURY
            ZodiacSign.CANCER -> Planet.MOON
            ZodiacSign.LEO -> Planet.SUN
            ZodiacSign.VIRGO -> Planet.MERCURY
            ZodiacSign.LIBRA -> Planet.VENUS
            ZodiacSign.SCORPIO -> Planet.MARS
            ZodiacSign.SAGITTARIUS -> Planet.JUPITER
            ZodiacSign.CAPRICORN -> Planet.SATURN
            ZodiacSign.AQUARIUS -> Planet.SATURN
            ZodiacSign.PISCES -> Planet.JUPITER
        }
    }

    /**
     * Check if two zodiac signs are compatible
     */
    fun areSignsCompatible(sign1: ZodiacSign, sign2: ZodiacSign): Boolean {
        val element1 = getZodiacElement(sign1)
        val element2 = getZodiacElement(sign2)

        // Same element = compatible
        if (element1 == element2) return true

        // Fire + Air = compatible
        if ((element1 == Element.FIRE && element2 == Element.AIR) ||
            (element1 == Element.AIR && element2 == Element.FIRE)) {
            return true
        }

        // Earth + Water = compatible
        if ((element1 == Element.EARTH && element2 == Element.WATER) ||
            (element1 == Element.WATER && element2 == Element.EARTH)) {
            return true
        }

        return false
    }

    /**
     * Get compatibility score between two zodiac signs (0.0 to 1.0)
     */
    fun getZodiacCompatibilityScore(sign1: ZodiacSign, sign2: ZodiacSign): Float {
        val element1 = getZodiacElement(sign1)
        val element2 = getZodiacElement(sign2)

        return when {
            // Same sign
            sign1 == sign2 -> 0.85f

            // Same element
            element1 == element2 -> 0.75f

            // Compatible elements
            (element1 == Element.FIRE && element2 == Element.AIR) ||
            (element1 == Element.AIR && element2 == Element.FIRE) -> 0.7f

            (element1 == Element.EARTH && element2 == Element.WATER) ||
            (element1 == Element.WATER && element2 == Element.EARTH) -> 0.7f

            // Opposite elements (challenging but growth-oriented)
            (element1 == Element.FIRE && element2 == Element.WATER) ||
            (element1 == Element.WATER && element2 == Element.FIRE) -> 0.45f

            (element1 == Element.EARTH && element2 == Element.AIR) ||
            (element1 == Element.AIR && element2 == Element.EARTH) -> 0.45f

            else -> 0.5f
        }
    }
}
