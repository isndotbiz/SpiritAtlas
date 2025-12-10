package com.spiritatlas.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.spiritatlas.domain.model.*
import java.time.LocalDateTime
import kotlin.math.abs

/**
 * Dynamic Gradient Generation System
 *
 * Generates personalized gradients based on:
 * - User profile data (numerology, astrology, chakras)
 * - Compatibility scores (relationship strength visualization)
 * - Current astrological transits (planetary energy)
 * - Mood and intention (emotional state)
 *
 * Each function analyzes spiritual data and creates unique gradient
 * representations that reflect the user's energetic signature.
 */
object DynamicGradients {

    // ============================================================================
    // PROFILE-BASED GRADIENT GENERATION
    // ============================================================================

    /**
     * Generate a personalized gradient from user profile
     *
     * Combines:
     * - Sun sign colors (astrological element)
     * - Life path number colors (numerological essence)
     * - Dosha colors (Ayurvedic constitution)
     * - Sexual energy type (Tantric polarity)
     *
     * @param profile The user's spiritual profile
     * @return A unique gradient representing their energetic signature
     */
    fun fromProfile(profile: UserProfile): Brush {
        val colors = mutableListOf<Color>()

        // Add zodiac element color if birth date available
        profile.birthDateTime?.let { birthDate ->
            val zodiacColor = getZodiacColor(birthDate)
            colors.add(zodiacColor)
        }

        // Add life path number color (if name available for numerology)
        profile.name?.let { name ->
            val lifePathColor = getNumerologyColor(name)
            colors.add(lifePathColor)
        }

        // Add sexual energy color
        profile.sexualEnergy?.let { energy ->
            colors.add(getSexualEnergyColor(energy))
        }

        // Add dosha color (if available from profile)
        // Default to balanced purple if no data
        if (colors.isEmpty()) {
            colors.addAll(listOf(SpiritualPurple, MysticViolet, CosmicBlue))
        }

        // Ensure at least 3 colors for smooth gradient
        while (colors.size < 3) {
            colors.add(colors.last().blend(SpiritualPurple, 0.5f))
        }

        return Brush.linearGradient(colors = colors)
    }

    /**
     * Generate gradient from specific chakra energy
     *
     * @param chakraIndex 0-6 (Root to Crown)
     * @param intensity 0.0-1.0 (how active/strong the chakra is)
     * @return Gradient representing chakra state
     */
    fun fromChakra(chakraIndex: Int, intensity: Float = 1.0f): Brush {
        val baseGradient = when (chakraIndex) {
            0 -> GradientLibrary.rootChakra
            1 -> GradientLibrary.sacralChakra
            2 -> GradientLibrary.solarPlexusChakra
            3 -> GradientLibrary.heartChakra
            4 -> GradientLibrary.throatChakra
            5 -> GradientLibrary.thirdEyeChakra
            6 -> GradientLibrary.crownChakra
            else -> GradientLibrary.heartChakra // Default to heart
        }

        // Could modify brightness/saturation based on intensity
        // For now, return base gradient
        return baseGradient
    }

    /**
     * Generate gradient for zodiac sign
     *
     * @param sunSign Zodiac sign name (e.g., "Aries", "Taurus")
     * @return Gradient matching zodiac energy
     */
    fun fromZodiacSign(sunSign: String): Brush {
        return when (sunSign.lowercase()) {
            "aries" -> GradientLibrary.aries
            "taurus" -> GradientLibrary.taurus
            "gemini" -> GradientLibrary.gemini
            "cancer" -> GradientLibrary.cancer
            "leo" -> GradientLibrary.leo
            "virgo" -> GradientLibrary.virgo
            "libra" -> GradientLibrary.libra
            "scorpio" -> GradientLibrary.scorpio
            "sagittarius" -> GradientLibrary.sagittarius
            "capricorn" -> GradientLibrary.capricorn
            "aquarius" -> GradientLibrary.aquarius
            "pisces" -> GradientLibrary.pisces
            else -> Brush.linearGradient(listOf(SpiritualPurple, MysticViolet))
        }
    }

    // ============================================================================
    // COMPATIBILITY-BASED GRADIENT GENERATION
    // ============================================================================

    /**
     * Generate gradient from compatibility score
     *
     * Color mapping:
     * - 0-30: Red (challenging/incompatible)
     * - 30-45: Orange (needs work)
     * - 45-60: Yellow (moderate)
     * - 60-75: Green (good)
     * - 75-90: Blue-Teal (excellent)
     * - 90-100: Purple-Gold (soulmate)
     *
     * @param score Compatibility score 0-100
     * @return Gradient representing relationship strength
     */
    fun fromCompatibilityScore(score: Double): Brush {
        return when {
            score < 30.0 -> {
                // Incompatible - Red to dark red
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFDC2626), // Red
                        ChakraRed,
                        Color(0xFF991B1B)  // Dark red
                    )
                )
            }
            score < 45.0 -> {
                // Challenging - Orange tones
                Brush.linearGradient(
                    colors = listOf(
                        ChakraRed,
                        FireOrange,
                        ChakraOrange
                    )
                )
            }
            score < 60.0 -> {
                // Moderate - Yellow-amber
                Brush.linearGradient(
                    colors = listOf(
                        ChakraOrange,
                        ChakraYellow,
                        SacredGold
                    )
                )
            }
            score < 75.0 -> {
                // Good - Green tones
                Brush.linearGradient(
                    colors = listOf(
                        ChakraYellow,
                        Color(0xFF84CC16), // Lime
                        ChakraGreen,
                        EarthGreen
                    )
                )
            }
            score < 90.0 -> {
                // Excellent - Blue-teal harmony
                Brush.linearGradient(
                    colors = listOf(
                        EarthGreen,
                        WaterTeal,
                        StardustBlue,
                        ChakraBlue
                    )
                )
            }
            else -> {
                // Soulmate - Purple-gold divine union
                Brush.linearGradient(
                    colors = listOf(
                        SacredGold,
                        AuraGold,
                        MysticPurple,
                        SpiritualPurple,
                        CosmicViolet
                    )
                )
            }
        }
    }

    /**
     * Generate gradient combining two profiles for relationship view
     *
     * @param profileA First partner's profile
     * @param profileB Second partner's profile
     * @return Gradient blending both energies
     */
    fun fromProfilePair(profileA: UserProfile, profileB: UserProfile): Brush {
        val colorsA = extractProfileColors(profileA)
        val colorsB = extractProfileColors(profileB)

        // Interleave colors from both profiles
        val blendedColors = mutableListOf<Color>()
        val maxColors = maxOf(colorsA.size, colorsB.size)

        for (i in 0 until maxColors) {
            if (i < colorsA.size) blendedColors.add(colorsA[i])
            if (i < colorsB.size) blendedColors.add(colorsB[i])
        }

        return Brush.horizontalGradient(colors = blendedColors)
    }

    // ============================================================================
    // TRANSIT-BASED GRADIENT GENERATION
    // ============================================================================

    /**
     * Generate gradient from current astrological transit
     *
     * @param planetName The transiting planet (e.g., "Mars", "Venus")
     * @param transitType Type of transit (conjunction, opposition, trine, etc.)
     * @return Gradient representing planetary energy
     */
    fun fromTransit(planetName: String, transitType: String = "conjunction"): Brush {
        val planetColor = SpiritualColors.PlanetaryColors[planetName] ?: SpiritualPurple

        return when (transitType.lowercase()) {
            "conjunction" -> {
                // Intense, focused energy
                Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f),
                        planetColor,
                        planetColor.darken(0.3f)
                    )
                )
            }
            "opposition" -> {
                // Tension, polarization
                Brush.horizontalGradient(
                    colors = listOf(
                        planetColor,
                        planetColor.blend(Color.White, 0.5f),
                        planetColor.complement()
                    )
                )
            }
            "trine", "sextile" -> {
                // Harmonious, flowing energy
                Brush.linearGradient(
                    colors = listOf(
                        planetColor.lighten(0.3f),
                        planetColor,
                        planetColor.blend(SpiritualPurple, 0.3f)
                    )
                )
            }
            "square" -> {
                // Challenge, friction
                Brush.linearGradient(
                    colors = listOf(
                        planetColor,
                        planetColor.saturate(1.5f),
                        planetColor.darken(0.2f),
                        ChakraRed.copy(alpha = 0.6f)
                    )
                )
            }
            else -> {
                // Default planetary gradient
                Brush.linearGradient(
                    colors = listOf(
                        planetColor.lighten(0.2f),
                        planetColor,
                        planetColor.darken(0.2f)
                    )
                )
            }
        }
    }

    /**
     * Generate gradient based on current time of day
     *
     * @param dateTime Current date/time
     * @return Gradient matching current solar energy
     */
    fun fromTimeOfDay(dateTime: LocalDateTime = LocalDateTime.now()): Brush {
        val hour = dateTime.hour
        return when (hour) {
            in 5..6 -> GradientLibrary.dawn
            in 7..11 -> GradientLibrary.morning
            in 12..13 -> GradientLibrary.midday
            in 14..16 -> GradientLibrary.afternoon
            in 17..19 -> GradientLibrary.dusk
            else -> GradientLibrary.night
        }
    }

    /**
     * Generate gradient based on current season
     *
     * @param dateTime Current date/time
     * @return Gradient matching current seasonal energy
     */
    fun fromSeason(dateTime: LocalDateTime = LocalDateTime.now()): Brush {
        val month = dateTime.monthValue
        return when (month) {
            3, 4, 5 -> GradientLibrary.spring   // Mar, Apr, May
            6, 7, 8 -> GradientLibrary.summer   // Jun, Jul, Aug
            9, 10, 11 -> GradientLibrary.autumn // Sep, Oct, Nov
            else -> GradientLibrary.winter      // Dec, Jan, Feb
        }
    }

    // ============================================================================
    // MOOD & INTENTION-BASED GRADIENT GENERATION
    // ============================================================================

    /**
     * Generate gradient from emotional mood
     *
     * @param mood Mood descriptor (e.g., "calm", "energized", "romantic", "focused")
     * @return Gradient matching emotional state
     */
    fun fromMood(mood: String): Brush {
        return when (mood.lowercase()) {
            "calm", "peaceful", "relaxed" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE0F2FE), // Soft blue
                        WaterTeal.copy(alpha = 0.7f),
                        Color(0xFF5EEAD4)  // Light teal
                    )
                )
            }
            "energized", "excited", "active" -> {
                Brush.linearGradient(
                    colors = listOf(
                        ChakraYellow,
                        FireOrange,
                        ChakraRed
                    )
                )
            }
            "romantic", "loving", "passionate" -> {
                Brush.linearGradient(
                    colors = listOf(
                        TantricRose,
                        SensualCoral,
                        IntimacyPurple
                    )
                )
            }
            "focused", "determined", "productive" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        ChakraYellow,
                        SacredGold,
                        TempleBronze
                    )
                )
            }
            "spiritual", "meditative", "transcendent" -> {
                Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        SpiritualPurple,
                        MysticViolet,
                        CosmicViolet
                    )
                )
            }
            "sad", "melancholy", "reflective" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF475569), // Slate
                        Color(0xFF334155), // Dark slate
                        NightSky
                    )
                )
            }
            "anxious", "stressed", "tense" -> {
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFCA5A5), // Light red
                        ChakraOrange.copy(alpha = 0.8f),
                        ChakraYellow.copy(alpha = 0.7f)
                    )
                )
            }
            "confident", "powerful", "strong" -> {
                Brush.linearGradient(
                    colors = listOf(
                        SacredGold,
                        AuraGold,
                        FireOrange,
                        ChakraRed
                    )
                )
            }
            else -> {
                // Default balanced gradient
                Brush.linearGradient(
                    colors = listOf(
                        SpiritualPurple,
                        MysticViolet,
                        CosmicBlue
                    )
                )
            }
        }
    }

    /**
     * Generate gradient from intention/manifestation goal
     *
     * @param intention What the user wants to manifest
     * @return Gradient supporting that intention
     */
    fun fromIntention(intention: String): Brush {
        return when (intention.lowercase()) {
            "abundance", "prosperity", "wealth" -> {
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFFBEB), // Bright gold center
                        AuraGold,
                        SacredGold,
                        TempleBronze
                    )
                )
            }
            "love", "romance", "relationship" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        TantricRose,
                        IntimacyPurple,
                        ChakraGreen,
                        EarthGreen
                    )
                )
            }
            "healing", "health", "wellness" -> {
                GradientLibrary.heartHealing
            }
            "creativity", "inspiration", "art" -> {
                Brush.linearGradient(
                    colors = listOf(
                        ChakraOrange,
                        ChakraYellow,
                        TantricRose,
                        SpiritualPurple
                    )
                )
            }
            "clarity", "wisdom", "insight" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        ChakraIndigo,
                        MysticPurple,
                        CosmicViolet
                    )
                )
            }
            "protection", "safety", "grounding" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        GroundingBrown,
                        TempleBronze,
                        ChakraRed,
                        Color(0xFF450A0A) // Deep protective red
                    )
                )
            }
            "transformation", "change", "growth" -> {
                Brush.linearGradient(
                    colors = listOf(
                        CosmicViolet,
                        MysticPurple,
                        SpiritualPurple,
                        SacredGold,
                        AuraGold
                    )
                )
            }
            "peace", "calm", "serenity" -> {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF0F9FF), // Very light
                        StardustBlue.copy(alpha = 0.6f),
                        WaterTeal.copy(alpha = 0.5f),
                        ChakraGreen.copy(alpha = 0.4f)
                    )
                )
            }
            else -> {
                // Default spiritual gradient
                Brush.linearGradient(
                    colors = listOf(
                        SpiritualPurple,
                        MysticViolet,
                        SacredGold
                    )
                )
            }
        }
    }

    // ============================================================================
    // HELPER FUNCTIONS
    // ============================================================================

    private fun extractProfileColors(profile: UserProfile): List<Color> {
        val colors = mutableListOf<Color>()

        // Zodiac color
        profile.birthDateTime?.let { birthDate ->
            colors.add(getZodiacColor(birthDate))
        }

        // Numerology color
        profile.name?.let { name ->
            colors.add(getNumerologyColor(name))
        }

        // Sexual energy color
        profile.sexualEnergy?.let { energy ->
            colors.add(getSexualEnergyColor(energy))
        }

        // Default if no colors found
        if (colors.isEmpty()) {
            colors.add(SpiritualPurple)
        }

        return colors
    }

    private fun getZodiacColor(birthDate: LocalDateTime): Color {
        val month = birthDate.monthValue
        val day = birthDate.dayOfMonth

        return when {
            // Aries: Mar 21 - Apr 19
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> ChakraRed
            // Taurus: Apr 20 - May 20
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> EarthGreen
            // Gemini: May 21 - Jun 20
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> ChakraYellow
            // Cancer: Jun 21 - Jul 22
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> MoonlightSilver
            // Leo: Jul 23 - Aug 22
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> SacredGold
            // Virgo: Aug 23 - Sep 22
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> EarthGreen
            // Libra: Sep 23 - Oct 22
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> TantricRose
            // Scorpio: Oct 23 - Nov 21
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> CosmicViolet
            // Sagittarius: Nov 22 - Dec 21
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> SpiritualPurple
            // Capricorn: Dec 22 - Jan 19
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> GroundingBrown
            // Aquarius: Jan 20 - Feb 18
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> StardustBlue
            // Pisces: Feb 19 - Mar 20
            else -> WaterTeal
        }
    }

    private fun getNumerologyColor(name: String): Color {
        // Simple life path calculation (sum of name values)
        val nameValue = name.sumOf { it.code } % 9 + 1
        return SpiritualColors.NumerologyColors[nameValue] ?: SpiritualPurple
    }

    private fun getSexualEnergyColor(energy: SexualEnergy): Color {
        return when (energy) {
            SexualEnergy.MASCULINE_CORE -> ChakraRed
            SexualEnergy.FEMININE_CORE -> TantricRose
            SexualEnergy.BALANCED_CORE -> SpiritualPurple
            SexualEnergy.FIRE_ENERGY -> FireOrange
            SexualEnergy.WATER_ENERGY -> WaterTeal
            SexualEnergy.EARTH_ENERGY -> EarthGreen
            SexualEnergy.AIR_ENERGY -> AirCyan
        }
    }
}

// ============================================================================
// COLOR MANIPULATION EXTENSIONS
// ============================================================================

/**
 * Blend two colors together
 */
fun Color.blend(other: Color, ratio: Float): Color {
    val r = this.red * (1 - ratio) + other.red * ratio
    val g = this.green * (1 - ratio) + other.green * ratio
    val b = this.blue * (1 - ratio) + other.blue * ratio
    val a = this.alpha * (1 - ratio) + other.alpha * ratio
    return Color(r, g, b, a)
}

/**
 * Lighten a color
 */
fun Color.lighten(amount: Float): Color {
    return this.blend(Color.White, amount.coerceIn(0f, 1f))
}

/**
 * Darken a color
 */
fun Color.darken(amount: Float): Color {
    return this.blend(Color.Black, amount.coerceIn(0f, 1f))
}

/**
 * Increase color saturation
 */
fun Color.saturate(factor: Float): Color {
    val gray = (red + green + blue) / 3f
    val r = gray + (red - gray) * factor
    val g = gray + (green - gray) * factor
    val b = gray + (blue - gray) * factor
    return Color(r.coerceIn(0f, 1f), g.coerceIn(0f, 1f), b.coerceIn(0f, 1f), alpha)
}

/**
 * Get complementary color (opposite on color wheel)
 */
fun Color.complement(): Color {
    return Color(1f - red, 1f - green, 1f - blue, alpha)
}
