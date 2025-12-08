package com.spiritatlas.core.humandesign

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HumanDesignCalculatorTest {

    @ParameterizedTest
    @CsvSource(
        "1,1,2000, GUIDE",       // (1+1+2000)%5 = 2 -> GUIDE
        "2,2,2000, OBSERVER",    // (2+2+2000)%5 = 4 -> OBSERVER
        "3,3,2000, BUILDER",     // (3+3+2000)%5 = 1 -> BUILDER
        "4,4,2000, REFINER",     // (4+4+2000)%5 = 3 -> REFINER
        "5,5,2000, INITIATOR",   // (5+5+2000)%5 = 0 -> INITIATOR
        "6,6,2001, REFINER"      // (6+6+2001)%5 = 3 -> REFINER
    )
    fun `calculateType cycles through energy types`(day: Int, month: Int, year: Int, expected: String) {
        val type = EnergyProfileCalculator.calculateEnergyType(day, month, year)
        assertEquals(EnergyProfileCalculator.EnergyType.valueOf(expected), type)
    }

    @Test
    fun `profile calculation produces notation`() {
        val profile = EnergyProfileCalculator.calculateProfile(day = 7, month = 2)
        // day=7: (7-1)%6 = 0 -> INVESTIGATOR (1)
        // month=2: (2-1)%6 = 1 -> HERMIT (2)
        assertEquals("1/2", profile.notation)
    }

    @Test
    fun `profile lines stay within defined range`() {
        val profile = EnergyProfileCalculator.calculateProfile(day = 31, month = 12)
        assertTrue(profile.conscious in EnergyProfileCalculator.ProfileLine.values())
        assertTrue(profile.unconscious in EnergyProfileCalculator.ProfileLine.values())
    }
}
