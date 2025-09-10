package com.spiritatlas.core.numerology

object PythagoreanCalculator {
    private val pythagoreanMap = mapOf(
        'A' to 1, 'J' to 1, 'S' to 1,
        'B' to 2, 'K' to 2, 'T' to 2,
        'C' to 3, 'L' to 3, 'U' to 3,
        'D' to 4, 'M' to 4, 'V' to 4,
        'E' to 5, 'N' to 5, 'W' to 5,
        'F' to 6, 'O' to 6, 'X' to 6,
        'G' to 7, 'P' to 7, 'Y' to 7,
        'H' to 8, 'Q' to 8, 'Z' to 8,
        'I' to 9, 'R' to 9
    )

    fun calculateNameNumber(name: String): Int {
        val sum = name.uppercase()
            .filter { it.isLetter() }
            .sumOf { pythagoreanMap[it] ?: 0 }
        return reduceToSingleDigit(sum)
    }

    fun calculateLifePath(day: Int, month: Int, year: Int): Int {
        val daySum = reduceToSingleDigit(day)
        val monthSum = reduceToSingleDigit(month)
        val yearSum = reduceToSingleDigit(year)
        return reduceToSingleDigit(daySum + monthSum + yearSum)
    }

    private fun reduceToSingleDigit(num: Int): Int {
        if (num in 1..9 || num == 11 || num == 22 || num == 33) return num
        return reduceToSingleDigit(num.toString().sumOf { it.digitToInt() })
    }
}


