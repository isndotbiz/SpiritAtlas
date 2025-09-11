package com.spiritatlas.core.ui.theme

import androidx.compose.ui.graphics.Color

// Material 3 Base Colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Spiritual Color Palette
val SpiritualPurple = Color(0xFF8B5CF6) // Main spiritual purple
val MysticViolet = Color(0xFF7C3AED)     // Deep mystic violet
val CosmicBlue = Color(0xFF3B82F6)      // Cosmic blue
val AuraGold = Color(0xFFFBBF24)        // Golden aura
val ChakraRed = Color(0xFFEF4444)       // Root chakra
val ChakraOrange = Color(0xFFF97316)    // Sacral chakra
val ChakraYellow = Color(0xFFFDE047)    // Solar plexus
val ChakraGreen = Color(0xFF22C55E)     // Heart chakra
val ChakraBlue = Color(0xFF3B82F6)      // Throat chakra
val ChakraIndigo = Color(0xFF6366F1)    // Third eye
val ChakraCrown = Color(0xFF8B5CF6)     // Crown chakra

// Tantric & Relationship Colors
val TantricRose = Color(0xFFEC4899)     // Passionate rose
val SensualCoral = Color(0xFFF87171)    // Sensual coral
val IntimacyPurple = Color(0xFFA855F7)  // Intimacy purple
val UnionGold = Color(0xFFF59E0B)       // Union gold
val CosmicOrange = Color(0xFFFF7F50)    // Cosmic orange
val DeepTeal = Color(0xFF008B8B)        // Deep teal

// Gradient Collections
val SpiritualGradient = listOf(SpiritualPurple, MysticViolet, CosmicBlue)
val ChakraGradient = listOf(ChakraRed, ChakraOrange, ChakraYellow, ChakraGreen, ChakraBlue, ChakraIndigo, ChakraCrown)
val TantricGradient = listOf(TantricRose, SensualCoral, IntimacyPurple)

object SpiritualColors {
    val ChakraColors = listOf(
        ChakraRed,    // Root
        ChakraOrange, // Sacral
        ChakraYellow, // Solar Plexus
        ChakraGreen,  // Heart
        ChakraBlue,   // Throat
        ChakraIndigo, // Third Eye
        ChakraCrown   // Crown
    )
    
    val TantricColors = mapOf(
        "Passionate" to TantricRose,
        "Sensual" to SensualCoral,
        "Sacred" to IntimacyPurple,
        "Divine" to SpiritualPurple,
        "Union" to UnionGold,
        "Mystical" to MysticViolet,
        "Cosmic" to CosmicBlue
    )
    
    val NumerologyColors = mapOf(
        1 to ChakraRed,
        2 to ChakraOrange, 
        3 to ChakraYellow,
        4 to ChakraGreen,
        5 to ChakraBlue,
        6 to ChakraIndigo,
        7 to ChakraCrown,
        8 to AuraGold,
        9 to MysticViolet
    )
}


