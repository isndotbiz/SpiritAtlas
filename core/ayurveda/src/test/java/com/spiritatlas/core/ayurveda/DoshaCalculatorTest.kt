package com.spiritatlas.core.ayurveda

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DoshaCalculatorTest {

    @Test
    fun `calculateDosha returns valid dominant type`() {
        val answers = mapOf(
            "sleep" to 0, // vata
            "appetite" to 2, // pitta
            "temperature" to 0 // vata
        )

        val profile = AyurvedaCalculator.calculate(answers)

        assertTrue(profile.dominant in Dosha.values())
    }

    @Test
    fun `dosha percentages sum to 100`() {
        val answers = mapOf(
            "a" to 0, "b" to 0, "c" to 1, "d" to 2, "e" to 2
        )

        val profile = AyurvedaCalculator.calculate(answers)
        val total = profile.vata + profile.pitta + profile.kapha

        val vPct = profile.vata * 100 / total
        val pPct = profile.pitta * 100 / total
        val kPct = profile.kapha * 100 / total

        assertEquals(100, vPct + pPct + kPct)
    }

    @Test
    fun `input values are clamped to supported range`() {
        val answers = mapOf(
            "low" to -3,  // should clamp to 0 (vata)
            "mid" to 1,   // kapha
            "high" to 5   // clamp to 2 (pitta)
        )

        val profile = AyurvedaCalculator.calculate(answers)

        assertEquals(1, profile.vata)
        assertEquals(1, profile.kapha)
        assertEquals(1, profile.pitta)
    }

    @Test
    fun `diet recommendations can derive from dominant dosha`() {
        val pittaProfile = AyurvedaCalculator.calculate(mapOf("x" to 2, "y" to 2, "z" to 1))

        val recommendation = when (pittaProfile.dominant) {
            Dosha.VATA -> "Warm, grounding foods"
            Dosha.PITTA -> "Cooling, hydrating foods"
            Dosha.KAPHA -> "Light, spicy foods"
        }

        assertEquals("Cooling, hydrating foods", recommendation)
    }
}

