package com.spiritatlas.core.numerology

object ChaldeanCalculator {
    private val chaldeanMap = mapOf(
        'A' to 1, 'I' to 1, 'J' to 1, 'Q' to 1, 'Y' to 1,
        'B' to 2, 'K' to 2, 'R' to 2,
        'C' to 3, 'G' to 3, 'L' to 3, 'S' to 3,
        'D' to 4, 'M' to 4, 'T' to 4,
        'E' to 5, 'H' to 5, 'N' to 5,
        'U' to 6, 'V' to 6, 'W' to 6, 'X' to 6,
        'O' to 7, 'Z' to 7,
        'F' to 8, 'P' to 8
    )

    fun calculateNameNumber(name: String): Int {
        val sum = name.uppercase()
            .filter { it.isLetter() }
            .sumOf { chaldeanMap[it] ?: 0 }
        return reduceToSingleDigit(sum)
    }

    private fun reduceToSingleDigit(num: Int): Int {
        if (num in 1..9 || num == 11 || num == 22) return num
        return reduceToSingleDigit(num.toString().sumOf { it.digitToInt() })
    }
}


