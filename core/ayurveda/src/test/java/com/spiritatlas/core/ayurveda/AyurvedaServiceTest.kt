package com.spiritatlas.core.ayurveda

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class AyurvedaServiceTest {

    @Test
    fun `profile analysis returns complete results`() {
        val profile = AyurvedaCalculator.calculate(
            mapOf(
                "sleep" to 0,
                "appetite" to 1,
                "temperature" to 2,
                "energy" to 2
            )
        )

        assertNotNull(profile)
        assertNotNull(profile.dominant)
    }

    @Test
    fun `wellness recommendations align with dominant dosha`() {
        val kaphaProfile = AyurvedaCalculator.calculate(
            mapOf(
                "sleep" to 1,
                "appetite" to 1,
                "temperature" to 1
            )
        )

        val recommendation = when (kaphaProfile.dominant) {
            Dosha.VATA -> "Prioritize warm meals and steady routines"
            Dosha.PITTA -> "Favor cooling foods and calming practices"
            Dosha.KAPHA -> "Emphasize light movement and spices"
        }

        assertEquals("Emphasize light movement and spices", recommendation)
    }
}

