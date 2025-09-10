package com.spiritatlas.core.astro

import kotlin.math.*

object AstroCalculator {
    private const val LAHIRI_AYANAMSA_2000 = 23.85 // degrees at J2000
    private const val AYANAMSA_RATE = 0.01397 // degrees per year
    
    enum class ZodiacSign(val startDegree: Int) {
        ARIES(0), TAURUS(30), GEMINI(60), CANCER(90),
        LEO(120), VIRGO(150), LIBRA(180), SCORPIO(210),
        SAGITTARIUS(240), CAPRICORN(270), AQUARIUS(300), PISCES(330)
    }
    
    enum class Aspect(val degrees: Double, val orb: Double) {
        CONJUNCTION(0.0, 8.0),
        SEXTILE(60.0, 6.0),
        SQUARE(90.0, 7.0),
        TRINE(120.0, 8.0),
        OPPOSITION(180.0, 8.0)
    }
    
    data class PlanetPosition(
        val longitude: Double,
        val sign: ZodiacSign,
        val degree: Double
    )
    
    fun calculateAyanamsa(year: Int): Double {
        val yearsSince2000 = year - 2000
        return LAHIRI_AYANAMSA_2000 + (yearsSince2000 * AYANAMSA_RATE)
    }
    
    fun tropicalToSidereal(tropicalLongitude: Double, year: Int): Double {
        val ayanamsa = calculateAyanamsa(year)
        var siderealLongitude = tropicalLongitude - ayanamsa
        if (siderealLongitude < 0) siderealLongitude += 360
        return siderealLongitude % 360
    }
    
    fun getZodiacSign(longitude: Double): PlanetPosition {
        val normalizedLongitude = longitude % 360
        val sign = ZodiacSign.values().last { normalizedLongitude >= it.startDegree }
        val degreeInSign = normalizedLongitude - sign.startDegree
        return PlanetPosition(normalizedLongitude, sign, degreeInSign)
    }
    
    fun calculateAspect(planet1: Double, planet2: Double): Aspect? {
        val diff = abs(planet1 - planet2)
        val normalizedDiff = min(diff, 360 - diff)
        
        return Aspect.values().find { aspect ->
            abs(normalizedDiff - aspect.degrees) <= aspect.orb
        }
    }
}


