package com.spiritatlas.core.ayurveda

object AyurvedaCalculator {
    /**
     * Very simple questionnaire scoring placeholder.
     * Accepts map of questionId -> answer (0..2) where higher favors PITTA, mid favors KAPHA, low favors VATA.
     */
    fun calculate(answers: Map<String, Int>): DoshaProfile {
        var v = 0; var p = 0; var k = 0
        answers.values.forEach { a ->
            when (a.coerceIn(0, 2)) {
                0 -> v += 1
                1 -> k += 1
                2 -> p += 1
            }
        }
        return DoshaProfile(v, p, k)
    }
}
