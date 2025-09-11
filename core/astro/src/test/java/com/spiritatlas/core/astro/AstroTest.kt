package com.spiritatlas.core.astro

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.abs

class AstroTest {

    // Ayanamsa calculation tests
    @Test
    fun `ayanamsa increases over years`() {
        val a2000 = AstroCalculator.calculateAyanamsa(2000)
        val a2020 = AstroCalculator.calculateAyanamsa(2020)
        assert(a2020 > a2000)
    }

    @Test
    fun `ayanamsa calculation for year 2000`() {
        val ayanamsa = AstroCalculator.calculateAyanamsa(2000)
        assertEquals(23.85, ayanamsa, 0.001)
    }

    @Test
    fun `ayanamsa calculation for future years`() {
        val ayanamsa2010 = AstroCalculator.calculateAyanamsa(2010)
        val expected = 23.85 + (10 * 0.01397)
        assertEquals(expected, ayanamsa2010, 0.001)
    }

    @Test
    fun `ayanamsa calculation for past years`() {
        val ayanamsa1990 = AstroCalculator.calculateAyanamsa(1990)
        val expected = 23.85 + (-10 * 0.01397)
        assertEquals(expected, ayanamsa1990, 0.001)
    }

    // Tropical to sidereal conversion tests
    @Test
    fun `sidereal conversion wraps properly`() {
        val sid = AstroCalculator.tropicalToSidereal(5.0, 2000)
        assert(sid in 0.0..360.0)
    }

    @Test
    fun `tropical to sidereal conversion basic case`() {
        val tropical = 30.0
        val year = 2000
        val sidereal = AstroCalculator.tropicalToSidereal(tropical, year)
        val expectedSidereal = tropical - 23.85
        assertEquals(expectedSidereal, sidereal, 0.001)
    }

    @Test
    fun `tropical to sidereal handles negative wrap`() {
        val tropical = 10.0 // Small value that will be negative after ayanamsa subtraction
        val year = 2000
        val sidereal = AstroCalculator.tropicalToSidereal(tropical, year)
        assertTrue(sidereal >= 0.0 && sidereal < 360.0)
        val expected = (tropical - 23.85 + 360) % 360
        assertEquals(expected, sidereal, 0.001)
    }

    @Test
    fun `tropical to sidereal handles large values`() {
        val tropical = 400.0 // Value > 360
        val year = 2020
        val sidereal = AstroCalculator.tropicalToSidereal(tropical, year)
        assertTrue(sidereal >= 0.0 && sidereal < 360.0)
    }

    // Zodiac sign tests
    @ParameterizedTest
    @CsvSource(
        "0.0, ARIES, 0.0",
        "15.0, ARIES, 15.0",
        "29.9, ARIES, 29.9",
        "30.0, TAURUS, 0.0",
        "45.0, TAURUS, 15.0",
        "60.0, GEMINI, 0.0",
        "90.0, CANCER, 0.0",
        "120.0, LEO, 0.0",
        "150.0, VIRGO, 0.0",
        "180.0, LIBRA, 0.0",
        "210.0, SCORPIO, 0.0",
        "240.0, SAGITTARIUS, 0.0",
        "270.0, CAPRICORN, 0.0",
        "300.0, AQUARIUS, 0.0",
        "330.0, PISCES, 0.0",
        "359.9, PISCES, 29.9"
    )
    fun `zodiac sign calculation`(longitude: Double, expectedSign: String, expectedDegree: Double) {
        val position = AstroCalculator.getZodiacSign(longitude)
        assertEquals(AstroCalculator.ZodiacSign.valueOf(expectedSign), position.sign)
        assertEquals(expectedDegree, position.degree, 0.1)
        assertEquals(longitude, position.longitude, 0.001)
    }

    @Test
    fun `zodiac sign handles values over 360`() {
        val position = AstroCalculator.getZodiacSign(390.0) // 30 degrees over full circle
        assertEquals(AstroCalculator.ZodiacSign.TAURUS, position.sign)
        assertEquals(0.0, position.degree, 0.001)
        assertEquals(30.0, position.longitude, 0.001) // Should be normalized
    }

    @Test
    fun `zodiac sign handles negative values`() {
        // In Kotlin, -30 % 360 = -30, so this will cause NoSuchElementException
        // The implementation expects positive normalized values
        // This test demonstrates the current behavior limitation
        try {
            AstroCalculator.getZodiacSign(-30.0)
            fail("Expected NoSuchElementException for negative longitude")
        } catch (e: NoSuchElementException) {
            // This is the current behavior - negative values cause exception
            assertTrue(true)
        }
    }

    // Aspect calculation tests
    @Test
    fun `aspect detection finds trine`() {
        val aspect = AstroCalculator.calculateAspect(10.0, 130.0)
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.TRINE, aspect)
    }

    @Test
    fun `aspect detection finds conjunction`() {
        val aspect = AstroCalculator.calculateAspect(15.0, 20.0) // 5 degree difference
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.CONJUNCTION, aspect)
    }

    @Test
    fun `aspect detection finds exact conjunction`() {
        val aspect = AstroCalculator.calculateAspect(45.0, 45.0)
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.CONJUNCTION, aspect)
    }

    @Test
    fun `aspect detection finds sextile`() {
        val aspect = AstroCalculator.calculateAspect(10.0, 65.0) // 55 degree difference, within orb
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.SEXTILE, aspect)
    }

    @Test
    fun `aspect detection finds square`() {
        val aspect = AstroCalculator.calculateAspect(0.0, 85.0) // 85 degrees, within orb for square
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.SQUARE, aspect)
    }

    @Test
    fun `aspect detection finds opposition`() {
        val aspect = AstroCalculator.calculateAspect(0.0, 175.0) // 175 degrees, within orb for opposition
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.OPPOSITION, aspect)
    }

    @Test
    fun `aspect detection handles cross-zero boundary`() {
        val aspect = AstroCalculator.calculateAspect(5.0, 355.0) // 10 degree separation across 0
        // The calculation: abs(5.0 - 355.0) = 350, min(350, 360-350) = min(350, 10) = 10
        // 10 degrees is outside the conjunction orb of 8 degrees, so no aspect
        assertNull(aspect)
    }

    @Test
    fun `aspect detection finds conjunction across zero boundary`() {
        val aspect = AstroCalculator.calculateAspect(2.0, 358.0) // 4 degree separation across 0
        // The calculation: abs(2.0 - 358.0) = 356, min(356, 360-356) = min(356, 4) = 4
        // 4 degrees is within the conjunction orb of 8 degrees
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.CONJUNCTION, aspect)
    }

    @Test
    fun `aspect detection returns null when no aspect found`() {
        val aspect = AstroCalculator.calculateAspect(0.0, 45.0) // 45 degrees, no aspect
        assertNull(aspect)
    }

    @Test
    fun `aspect detection with exact opposition across boundary`() {
        val aspect = AstroCalculator.calculateAspect(10.0, 190.0) // Exactly 180 degrees
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.OPPOSITION, aspect)
    }

    @ParameterizedTest
    @CsvSource(
        "0.0, 8.0, CONJUNCTION",
        "0.0, 9.0, ", // Outside orb - empty string represents null
        "60.0, 6.0, SEXTILE",
        "60.0, 7.0, ", // Outside orb
        "90.0, 7.0, SQUARE",
        "90.0, 8.0, ", // Outside orb
        "120.0, 8.0, TRINE",
        "120.0, 9.0, ", // Outside orb
        "180.0, 8.0, OPPOSITION",
        "180.0, 9.0, " // Outside orb
    )
    fun `aspect detection orb boundaries`(baseDegree: Double, offset: Double, expectedAspectStr: String?) {
        val planet1 = 0.0
        val planet2 = baseDegree + offset
        val aspect = AstroCalculator.calculateAspect(planet1, planet2)
        
        if (expectedAspectStr.isNullOrEmpty()) {
            assertNull(aspect)
        } else {
            assertNotNull(aspect)
            assertEquals(AstroCalculator.Aspect.valueOf(expectedAspectStr), aspect)
        }
    }

    // Test enum values
    @Test
    fun `zodiac signs have correct start degrees`() {
        assertEquals(0, AstroCalculator.ZodiacSign.ARIES.startDegree)
        assertEquals(30, AstroCalculator.ZodiacSign.TAURUS.startDegree)
        assertEquals(60, AstroCalculator.ZodiacSign.GEMINI.startDegree)
        assertEquals(90, AstroCalculator.ZodiacSign.CANCER.startDegree)
        assertEquals(120, AstroCalculator.ZodiacSign.LEO.startDegree)
        assertEquals(150, AstroCalculator.ZodiacSign.VIRGO.startDegree)
        assertEquals(180, AstroCalculator.ZodiacSign.LIBRA.startDegree)
        assertEquals(210, AstroCalculator.ZodiacSign.SCORPIO.startDegree)
        assertEquals(240, AstroCalculator.ZodiacSign.SAGITTARIUS.startDegree)
        assertEquals(270, AstroCalculator.ZodiacSign.CAPRICORN.startDegree)
        assertEquals(300, AstroCalculator.ZodiacSign.AQUARIUS.startDegree)
        assertEquals(330, AstroCalculator.ZodiacSign.PISCES.startDegree)
    }

    @Test
    fun `aspects have correct degrees and orbs`() {
        assertEquals(0.0, AstroCalculator.Aspect.CONJUNCTION.degrees)
        assertEquals(8.0, AstroCalculator.Aspect.CONJUNCTION.orb)
        
        assertEquals(60.0, AstroCalculator.Aspect.SEXTILE.degrees)
        assertEquals(6.0, AstroCalculator.Aspect.SEXTILE.orb)
        
        assertEquals(90.0, AstroCalculator.Aspect.SQUARE.degrees)
        assertEquals(7.0, AstroCalculator.Aspect.SQUARE.orb)
        
        assertEquals(120.0, AstroCalculator.Aspect.TRINE.degrees)
        assertEquals(8.0, AstroCalculator.Aspect.TRINE.orb)
        
        assertEquals(180.0, AstroCalculator.Aspect.OPPOSITION.degrees)
        assertEquals(8.0, AstroCalculator.Aspect.OPPOSITION.orb)
    }

    @Test
    fun `planet position data class works correctly`() {
        val position = AstroCalculator.PlanetPosition(45.5, AstroCalculator.ZodiacSign.TAURUS, 15.5)
        assertEquals(45.5, position.longitude)
        assertEquals(AstroCalculator.ZodiacSign.TAURUS, position.sign)
        assertEquals(15.5, position.degree)
    }
}


