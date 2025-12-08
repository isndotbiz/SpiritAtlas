package com.spiritatlas.core.numerology

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PythagoreanCalculatorTest {

    @Test
    fun `life path calculation for standard date`() {
        val lifePath = PythagoreanCalculator.calculateLifePath(day = 15, month = 5, year = 1990)
        assertEquals(3, lifePath) // 6 + 5 + 1 = 12 -> 3
    }

    @Test
    fun `life path preserves master numbers`() {
        val lifePath = PythagoreanCalculator.calculateLifePath(day = 11, month = 11, year = 2000)
        assertEquals(6, lifePath) // 11 + 11 + 2 = 24 -> 6
    }

    @Test
    fun `master numbers in names are preserved`() {
        assertEquals(11, PythagoreanCalculator.calculateNameNumber("QBA"))   // 8+2+1
        assertEquals(22, PythagoreanCalculator.calculateNameNumber("QQLL")) // 8+8+3+3
        assertEquals(33, PythagoreanCalculator.calculateNameNumber("QQQR")) // 8+8+8+9
    }

    @Test
    fun `leap year date does not error`() {
        val lifePath = PythagoreanCalculator.calculateLifePath(day = 29, month = 2, year = 2000)
        assertEquals(6, lifePath)
    }

    @Test
    fun `boundary date handles low values`() {
        val lifePath = PythagoreanCalculator.calculateLifePath(day = 1, month = 1, year = 1900)
        assertEquals(3, lifePath)
    }
}
