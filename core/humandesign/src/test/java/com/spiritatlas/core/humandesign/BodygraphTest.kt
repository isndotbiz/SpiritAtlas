package com.spiritatlas.core.humandesign

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BodygraphTest {

    @Test
    fun `profile notation reflects conscious and unconscious lines`() {
        val profile = EnergyProfileCalculator.Profile(
            conscious = EnergyProfileCalculator.ProfileLine.ROLE_MODEL,
            unconscious = EnergyProfileCalculator.ProfileLine.INVESTIGATOR
        )

        assertEquals("6/1", profile.notation)
    }

    @Test
    fun `all centers remain defined via enum set`() {
        val lines = EnergyProfileCalculator.ProfileLine.values().toSet()
        assertEquals(6, lines.size)
        assertTrue(lines.map { it.number }.containsAll(listOf(1, 2, 3, 4, 5, 6)))
    }
}

