package com.spiritatlas.core.numerology

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NumerologyTest {

    @Test
    fun `chaldean name number basic`() {
        val value = ChaldeanCalculator.calculateNameNumber("John Doe")
        // J(1)+O(7)+H(5)+N(5) + D(4)+O(7)+E(5) = 34 -> 3+4 = 7
        assertEquals(7, value)
    }

    @Test
    fun `pythagorean name number basic`() {
        val value = PythagoreanCalculator.calculateNameNumber("Anna")
        // A(1)+N(5)+N(5)+A(1) = 12 -> 1+2 = 3
        assertEquals(3, value)
    }

    @Test
    fun `pythagorean life path preserves master numbers`() {
        // 11-11-2000 -> 11 + 11 + 2 = 24 -> 2+4 = 6 (11 preserved at step)
        val value = PythagoreanCalculator.calculateLifePath(11, 11, 2000)
        assertEquals(6, value)
    }
}


