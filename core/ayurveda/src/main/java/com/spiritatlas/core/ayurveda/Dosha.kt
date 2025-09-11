package com.spiritatlas.core.ayurveda

enum class Dosha { VATA, PITTA, KAPHA }

data class DoshaProfile(
    val vata: Int,
    val pitta: Int,
    val kapha: Int
) {
    val dominant: Dosha = when {
        vata >= pitta && vata >= kapha -> Dosha.VATA
        pitta >= vata && pitta >= kapha -> Dosha.PITTA
        else -> Dosha.KAPHA
    }
}
