package com.spiritatlas.core.astro

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AstroCalculatorTest {

    @Test
    fun `negative longitude currently throws`() {
        assertThrows(NoSuchElementException::class.java) {
            AstroCalculator.getZodiacSign(-30.0)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "1980, 23.85 - (20 * 0.01397)",
        "2000, 23.85",
        "2025, 23.85 + (25 * 0.01397)"
    )
    fun `ayanamsa matches linear model`(year: Int, expectedExpression: String) {
        val expected = when (year) {
            1980 -> 23.85 - (20 * 0.01397)
            2000 -> 23.85
            else -> 23.85 + (25 * 0.01397)
        }
        assertEquals(expected, AstroCalculator.calculateAyanamsa(year), 0.001)
    }

    @Test
    fun `tropical to sidereal shifts by ayanamsa`() {
        val tropical = 120.0
        val year = 2025
        val ayanamsa = AstroCalculator.calculateAyanamsa(year)
        val sidereal = AstroCalculator.tropicalToSidereal(tropical, year)
        val expected = (tropical - ayanamsa + 360) % 360
        assertEquals(expected, sidereal, 0.001)
    }

    @ParameterizedTest
    @CsvSource(
        "0.0, ARIES",
        "29.999, ARIES",
        "30.0, TAURUS",
        "59.999, TAURUS",
        "60.0, GEMINI",
        "89.999, GEMINI",
        "90.0, CANCER",
        "119.999, CANCER",
        "120.0, LEO",
        "149.999, LEO",
        "150.0, VIRGO",
        "179.999, VIRGO",
        "180.0, LIBRA",
        "209.999, LIBRA",
        "210.0, SCORPIO",
        "239.999, SCORPIO",
        "240.0, SAGITTARIUS",
        "269.999, SAGITTARIUS",
        "270.0, CAPRICORN",
        "299.999, CAPRICORN",
        "300.0, AQUARIUS",
        "329.999, AQUARIUS",
        "330.0, PISCES",
        "359.999, PISCES"
    )
    fun `zodiac boundaries for all signs`(longitude: Double, expectedSign: String) {
        val position = AstroCalculator.getZodiacSign(longitude)
        assertEquals(AstroCalculator.ZodiacSign.valueOf(expectedSign), position.sign)
        assertTrue(position.degree >= 0.0)
    }

    @ParameterizedTest
    @CsvSource(
        "0.0, 0.0, CONJUNCTION",
        "0.0, 60.0, SEXTILE",
        "0.0, 90.0, SQUARE",
        "0.0, 120.0, TRINE",
        "0.0, 180.0, OPPOSITION"
    )
    fun `aspect calculations detect primary aspects`(
        p1: Double,
        p2: Double,
        aspect: String
    ) {
        val detected = AstroCalculator.calculateAspect(p1, p2)
        assertEquals(AstroCalculator.Aspect.valueOf(aspect), detected)
    }
}

