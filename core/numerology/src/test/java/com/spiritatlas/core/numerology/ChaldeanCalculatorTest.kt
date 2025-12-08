package com.spiritatlas.core.numerology

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChaldeanCalculatorTest {

    @Test
    fun `empty name returns zero`() {
        assertEquals(0, ChaldeanCalculator.calculateNameNumber(""))
    }

    @Test
    fun `single letter maps correctly`() {
        assertEquals(1, ChaldeanCalculator.calculateNameNumber("A"))
    }

    @Test
    fun `name calculation uses chaldean mapping`() {
        // J(1) + O(7) + H(5) + N(5) = 18 -> 1+8 = 9
        assertEquals(9, ChaldeanCalculator.calculateNameNumber("JOHN"))
    }

    @Test
    fun `master numbers are preserved`() {
        // K+K+K+K+S = 2+2+2+2+3 = 11
        assertEquals(11, ChaldeanCalculator.calculateNameNumber("KKKKS"))
        // U+U+E+E = 6+6+5+5 = 22
        assertEquals(22, ChaldeanCalculator.calculateNameNumber("UUEE"))
        // P+P+P+F+A = 8+8+8+8+1 = 33
        assertEquals(33, ChaldeanCalculator.calculateNameNumber("PPPFA"))
    }

    @Test
    fun `ignores spaces and punctuation`() {
        // A=1, N=5, A=1 -> sum=7
        assertEquals(7, ChaldeanCalculator.calculateNameNumber("A! n-a"))
    }

    @Test
    fun `case insensitivity holds`() {
        assertEquals(
            ChaldeanCalculator.calculateNameNumber("mystic"),
            ChaldeanCalculator.calculateNameNumber("MYSTIC")
        )
    }
}
