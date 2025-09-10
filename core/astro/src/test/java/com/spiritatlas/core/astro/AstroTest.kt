package com.spiritatlas.core.astro

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class AstroTest {

    @Test
    fun `ayanamsa increases over years`() {
        val a2000 = AstroCalculator.calculateAyanamsa(2000)
        val a2020 = AstroCalculator.calculateAyanamsa(2020)
        assert(a2020 > a2000)
    }

    @Test
    fun `sidereal conversion wraps properly`() {
        val sid = AstroCalculator.tropicalToSidereal(5.0, 2000)
        assert(sid in 0.0..360.0)
    }

    @Test
    fun `aspect detection finds trine`() {
        val aspect = AstroCalculator.calculateAspect(10.0, 130.0)
        assertNotNull(aspect)
        assertEquals(AstroCalculator.Aspect.TRINE, aspect)
    }
}


