package com.spiritatlas.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Comprehensive Gradient Library for SpiritAtlas
 *
 * 50+ spiritual gradients organized by category:
 * - Chakra Gradients (7)
 * - Elemental Gradients (8)
 * - Zodiac Gradients (12)
 * - Time of Day Gradients (6)
 * - Seasonal Gradients (4)
 * - Cosmic Gradients (10)
 * - Healing Gradients (5)
 * - Plus existing spiritual gradients from Color.kt
 *
 * Each gradient is carefully designed to evoke specific spiritual energies
 * and can be used throughout the app for backgrounds, cards, and UI elements.
 */
object GradientLibrary {

    // ============================================================================
    // CHAKRA GRADIENTS - Individual energy centers (7 gradients)
    // ============================================================================

    /**
     * Root Chakra (Muladhara) - Grounding, survival, security
     * Red to deep crimson with earth tones
     */
    val rootChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            ChakraRed,
            Color(0xFFDC2626), // Crimson
            Color(0xFF991B1B), // Deep red
            GroundingBrown
        )
    )

    /**
     * Sacral Chakra (Svadhisthana) - Creativity, sexuality, emotions
     * Orange with warm passionate tones
     */
    val sacralChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            ChakraOrange,
            Color(0xFFF97316), // Bright orange
            Color(0xFFEA580C), // Deep orange
            Color(0xFFFB923C)  // Soft orange
        )
    )

    /**
     * Solar Plexus Chakra (Manipura) - Power, confidence, transformation
     * Yellow to gold with sun energy
     */
    val solarPlexusChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            ChakraYellow,
            Color(0xFFFDE047), // Bright yellow
            SacredGold,
            AuraGold
        )
    )

    /**
     * Heart Chakra (Anahata) - Love, compassion, healing
     * Green with pink rose accents
     */
    val heartChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF86EFAC), // Light green
            ChakraGreen,
            Color(0xFF22C55E), // Vibrant green
            TantricRose.copy(alpha = 0.6f) // Pink love energy
        )
    )

    /**
     * Throat Chakra (Vishuddha) - Communication, truth, expression
     * Sky blue to aqua with clear tones
     */
    val throatChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF93C5FD), // Light blue
            ChakraBlue,
            Color(0xFF2563EB), // Deep blue
            AirCyan.copy(alpha = 0.8f)
        )
    )

    /**
     * Third Eye Chakra (Ajna) - Intuition, wisdom, psychic abilities
     * Indigo to deep purple with mystical tones
     */
    val thirdEyeChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFA5B4FC), // Light indigo
            ChakraIndigo,
            Color(0xFF4F46E5), // Deep indigo
            MysticPurple
        )
    )

    /**
     * Crown Chakra (Sahasrara) - Divine connection, enlightenment, consciousness
     * Violet to white light with cosmic energy
     */
    val crownChakra: Brush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.9f),
            Color(0xFFF3E8FF), // Very light purple
            ChakraCrown,
            CosmicViolet
        )
    )

    // ============================================================================
    // ELEMENTAL GRADIENTS - Fundamental energies (8 gradients)
    // ============================================================================

    /**
     * Fire - Passion, transformation, energy, willpower
     * Red-orange-yellow flames
     */
    val fireElement: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF7F1D1D), // Deep crimson
            ChakraRed,
            FireOrange,
            Color(0xFFFB923C), // Bright orange
            ChakraYellow
        )
    )

    /**
     * Water - Emotions, intuition, flow, cleansing
     * Deep blue to teal waves
     */
    val waterElement: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0C4A6E), // Deep ocean
            Color(0xFF0369A1), // Ocean blue
            ChakraBlue,
            WaterTeal,
            Color(0xFF5EEAD4)  // Light teal
        )
    )

    /**
     * Earth - Grounding, stability, abundance, fertility
     * Brown to green forest tones
     */
    val earthElement: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF422006), // Deep earth brown
            GroundingBrown,
            TempleBronze,
            Color(0xFF166534), // Forest green
            EarthGreen
        )
    )

    /**
     * Air - Intellect, communication, freedom, clarity
     * Light blue to white sky gradients
     */
    val airElement: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE0F2FE), // Very light sky
            Color(0xFFBAE6FD), // Light sky
            StardustBlue,
            AirCyan,
            Color(0xFF06B6D4)  // Bright cyan
        )
    )

    /**
     * Ether/Spirit - Divine connection, consciousness, transcendence
     * Purple to cosmic shimmer
     */
    val etherElement: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFFAF5FF), // Very light purple center
            Color(0xFFE9D5FF), // Light purple
            SpiritualPurple,
            MysticViolet,
            CosmicViolet,
            NightSky
        )
    )

    /**
     * Fire + Air - Lightning, inspiration, sudden insight
     */
    val fireAirElement: Brush = Brush.linearGradient(
        colors = listOf(
            FireOrange,
            ChakraYellow,
            Color(0xFFFEF3C7), // Pale yellow
            StardustBlue,
            AirCyan
        )
    )

    /**
     * Water + Earth - Growth, nourishment, healing
     */
    val waterEarthElement: Brush = Brush.verticalGradient(
        colors = listOf(
            WaterTeal,
            Color(0xFF14B8A6), // Teal-green
            Color(0xFF10B981), // Emerald
            EarthGreen,
            Color(0xFF166534)  // Forest
        )
    )

    /**
     * Air + Water - Mist, dreams, emotions, psychic flow
     */
    val airWaterElement: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFDEE9FC), // Misty sky
            StardustBlue,
            AirCyan,
            WaterTeal,
            Color(0xFF0891B2)  // Deep cyan
        )
    )

    // ============================================================================
    // ZODIAC GRADIENTS - Astrological signs (12 gradients)
    // ============================================================================

    /** Aries (Fire) - Bold red-orange warrior energy */
    val aries: Brush = Brush.linearGradient(
        colors = listOf(ChakraRed, FireOrange, Color(0xFFDC2626))
    )

    /** Taurus (Earth) - Grounded green-brown earth energy */
    val taurus: Brush = Brush.verticalGradient(
        colors = listOf(EarthGreen, Color(0xFF059669), GroundingBrown)
    )

    /** Gemini (Air) - Bright yellow-cyan communication energy */
    val gemini: Brush = Brush.linearGradient(
        colors = listOf(ChakraYellow, Color(0xFF38BDF8), AirCyan)
    )

    /** Cancer (Water) - Silvery moonlight emotional energy */
    val cancer: Brush = Brush.verticalGradient(
        colors = listOf(MoonlightSilver, Color(0xFFE2E8F0), WaterTeal, Color(0xFF0891B2))
    )

    /** Leo (Fire) - Golden sun radiant energy */
    val leo: Brush = Brush.radialGradient(
        colors = listOf(Color(0xFFFFFBEB), AuraGold, SacredGold, FireOrange)
    )

    /** Virgo (Earth) - Pure green-white analytical energy */
    val virgo: Brush = Brush.verticalGradient(
        colors = listOf(Color(0xFFF0FDF4), Color(0xFFBBF7D0), EarthGreen, Color(0xFF16A34A))
    )

    /** Libra (Air) - Balanced pink-blue harmony energy */
    val libra: Brush = Brush.linearGradient(
        colors = listOf(TantricRose, Color(0xFFF9A8D4), StardustBlue, AirCyan)
    )

    /** Scorpio (Water) - Deep red-purple transformative energy */
    val scorpio: Brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF450A0A), ChakraRed, CosmicViolet, MysticPurple)
    )

    /** Sagittarius (Fire) - Purple-orange adventurous energy */
    val sagittarius: Brush = Brush.linearGradient(
        colors = listOf(SpiritualPurple, ChakraIndigo, FireOrange, ChakraOrange)
    )

    /** Capricorn (Earth) - Dark brown-gray mountain energy */
    val capricorn: Brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF1C1917), GroundingBrown, TempleBronze, Color(0xFF78716C))
    )

    /** Aquarius (Air) - Electric blue-purple visionary energy */
    val aquarius: Brush = Brush.linearGradient(
        colors = listOf(StardustBlue, AirCyan, Color(0xFF06B6D4), ChakraIndigo, SpiritualPurple)
    )

    /** Pisces (Water) - Dreamy purple-teal mystical energy */
    val pisces: Brush = Brush.verticalGradient(
        colors = listOf(MysticPurple, SpiritualPurple, Color(0xFF8B5CF6), WaterTeal, Color(0xFF2DD4BF))
    )

    // ============================================================================
    // TIME OF DAY GRADIENTS - Circadian energy cycles (6 gradients)
    // ============================================================================

    /**
     * Dawn - New beginnings, hope, fresh starts
     * Deep blue to soft pink sunrise
     */
    val dawn: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1E3A8A), // Deep night blue
            Color(0xFF3730A3), // Indigo
            Color(0xFF7C3AED), // Purple
            Color(0xFFF472B6), // Soft pink
            Color(0xFFFED7AA)  // Peach
        )
    )

    /**
     * Morning - Vitality, energy, action
     * Warm orange-yellow sun energy
     */
    val morning: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFED7AA), // Soft peach
            Color(0xFFFDE68A), // Light yellow
            ChakraYellow,
            Color(0xFFFBBF24), // Gold
            Color(0xFFF59E0B)  // Amber
        )
    )

    /**
     * Midday - Peak power, clarity, illumination
     * Bright white-gold sun peak
     */
    val midday: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFFFFFF0), // Bright white
            Color(0xFFFFFBEB), // Ivory
            AuraGold,
            SacredGold,
            ChakraYellow
        )
    )

    /**
     * Afternoon - Warm productivity, gentle focus
     * Golden amber afternoon light
     */
    val afternoon: Brush = Brush.horizontalGradient(
        colors = listOf(
            SacredGold,
            Color(0xFFFBBF24), // Gold
            ChakraOrange,
            Color(0xFFF97316)  // Orange
        )
    )

    /**
     * Dusk - Transformation, reflection, magic hour
     * Purple-orange-pink sunset
     */
    val dusk: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF9E6D), // Bright orange
            Color(0xFFFF6B9D), // Coral pink
            Color(0xFFDB2777), // Hot pink
            Color(0xFF9333EA), // Purple
            Color(0xFF6366F1), // Indigo
            Color(0xFF1E3A8A)  // Deep blue
        )
    )

    /**
     * Night - Rest, dreams, cosmic connection
     * Deep indigo to starlit black
     */
    val night: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F172A), // Almost black
            NightSky,
            Color(0xFF1E1B4B), // Deep indigo
            CosmicViolet,
            Color(0xFF4C1D95)  // Purple-black
        )
    )

    // ============================================================================
    // SEASONAL GRADIENTS - Natural cycles (4 gradients)
    // ============================================================================

    /**
     * Spring - Rebirth, growth, new life, fresh energy
     * Pink blossoms to fresh green
     */
    val spring: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFCE7F3), // Very soft pink
            Color(0xFFFBCFE8), // Light pink
            TantricRose,
            Color(0xFFF472B6), // Pink
            Color(0xFFBBF7D0), // Light green
            EarthGreen,
            Color(0xFF22C55E)  // Vibrant green
        )
    )

    /**
     * Summer - Abundance, vitality, peak energy, joy
     * Bright yellow to lush green
     */
    val summer: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFEF3C7), // Pale yellow
            ChakraYellow,
            Color(0xFFFDE047), // Bright yellow
            AuraGold,
            Color(0xFF84CC16), // Lime
            EarthGreen,
            Color(0xFF059669)  // Emerald
        )
    )

    /**
     * Autumn - Harvest, gratitude, transformation, release
     * Red-orange-gold-brown fall colors
     */
    val autumn: Brush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF7C2D12), // Deep brown
            GroundingBrown,
            TempleBronze,
            Color(0xFFEA580C), // Orange
            ChakraOrange,
            SacredGold,
            Color(0xFFFCD34D), // Amber
            Color(0xFFDC2626)  // Red accent
        )
    )

    /**
     * Winter - Rest, introspection, clarity, purification
     * Icy blue to silver white
     */
    val winter: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF0F9FF), // Very pale ice
            Color(0xFFE0F2FE), // Light sky
            Color(0xFFBAE6FD), // Sky blue
            StardustBlue,
            AirCyan,
            Color(0xFF0891B2), // Deep cyan
            Color(0xFF164E63)  // Deep ice
        )
    )

    // ============================================================================
    // COSMIC GRADIENTS - Universal energies (10 gradients)
    // ============================================================================

    /**
     * Galaxy - Spiral star systems, cosmic vastness
     */
    val galaxy: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFFFFFFF).copy(alpha = 0.8f), // Bright center
            Color(0xFFA5B4FC), // Light purple
            SpiritualPurple,
            CosmicViolet,
            NightSky,
            Color(0xFF0F172A)  // Deep space
        )
    )

    /**
     * Nebula - Star birth, cosmic creation
     */
    val nebula: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF6BB5), // Pink
            TantricRose,
            MysticPurple,
            StardustBlue,
            WaterTeal,
            Color(0xFF14B8A6)
        )
    )

    /**
     * Supernova - Explosive transformation, rebirth
     */
    val supernova: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFFFFFFF), // Bright white core
            Color(0xFFFEF3C7), // Pale yellow
            AuraGold,
            ChakraOrange,
            ChakraRed,
            Color(0xFF991B1B), // Deep red
            Color(0xFF450A0A)  // Dark red
        )
    )

    /**
     * Black Hole - Mystery, void, infinite potential
     */
    val blackHole: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFF000000), // Absolute black center
            Color(0xFF0F172A), // Near black
            NightSky,
            CosmicViolet,
            SpiritualPurple.copy(alpha = 0.6f)
        )
    )

    /**
     * Starfield - Infinite possibilities, wonder
     */
    val starfield: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F172A), // Deep space
            NightSky,
            Color(0xFF1E1B4B),
            CosmicViolet.copy(alpha = 0.8f),
            StardustBlue.copy(alpha = 0.4f)
        )
    )

    /**
     * Comet - Swift change, messenger, destiny
     */
    val comet: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF), // Bright head
            Color(0xFFE0F2FE), // Ice blue
            StardustBlue,
            AirCyan,
            WaterTeal.copy(alpha = 0.6f),
            Color.Transparent
        )
    )

    /**
     * Aurora Cosmic - Celestial light show, divine beauty
     */
    val auroraCosmic: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF33FF99), // Bright aurora green
            Color(0xFF00FF87), // Electric green
            Color(0xFF60EFFF), // Electric cyan
            AuroraPink,
            MysticPurple,
            ChakraIndigo
        )
    )

    /**
     * Void - Emptiness, potential, quantum field
     */
    val void: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFF1A1A2E), // Dark center
            Color(0xFF16213E), // Deep blue-black
            NightSky.copy(alpha = 0.9f),
            CosmicViolet.copy(alpha = 0.5f),
            Color.Transparent
        )
    )

    /**
     * Cosmic Ocean - Space and water unity
     */
    val cosmicOcean: Brush = Brush.verticalGradient(
        colors = listOf(
            NightSky,
            Color(0xFF1E3A8A), // Deep ocean blue
            Color(0xFF0891B2), // Cyan
            WaterTeal,
            Color(0xFF2DD4BF)  // Light teal
        )
    )

    /**
     * Celestial Harmony - Divine balance, universal order
     */
    val celestialHarmony: Brush = Brush.linearGradient(
        colors = listOf(
            SacredGold,
            AuraGold,
            MoonlightSilver,
            StardustBlue,
            SpiritualPurple,
            MysticViolet
        )
    )

    // ============================================================================
    // HEALING GRADIENTS - Therapeutic energies (5 gradients)
    // ============================================================================

    /**
     * Reiki Healing - Universal life force energy
     * Soft white to gentle lavender
     */
    val reikiHealing: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFAFAFA), // Soft white
            Color(0xFFF3E8FF), // Very light purple
            Color(0xFFE9D5FF), // Light purple
            SpiritualPurple.copy(alpha = 0.6f),
            MysticViolet.copy(alpha = 0.4f)
        )
    )

    /**
     * Heart Healing - Emotional restoration, self-love
     * Pink-green heart chakra colors
     */
    val heartHealing: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFCE7F3), // Soft pink
            TantricRose.copy(alpha = 0.7f),
            Color(0xFFF9A8D4), // Light pink
            Color(0xFFBBF7D0), // Light green
            EarthGreen.copy(alpha = 0.8f),
            ChakraGreen
        )
    )

    /**
     * Crystal Healing - Mineral energy, clarity, amplification
     * Clear to purple crystal shimmer
     */
    val crystalHealing: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFF5F5F5), // Clear crystal
            Color(0xFFE0E7FF), // Light indigo
            Color(0xFFC7D2FE), // Indigo
            ChakraIndigo.copy(alpha = 0.7f),
            MysticPurple.copy(alpha = 0.5f)
        )
    )

    /**
     * Sound Healing - Vibrational therapy, harmony
     * Blue-teal sound wave energy
     */
    val soundHealing: Brush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFDEEEFC), // Very light blue
            StardustBlue.copy(alpha = 0.7f),
            ChakraBlue,
            AirCyan,
            WaterTeal.copy(alpha = 0.8f)
        )
    )

    /**
     * Divine Light Healing - Highest vibration, pure source energy
     * Golden-white divine light
     */
    val divineLightHealing: Brush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFFFFFFF), // Pure white center
            Color(0xFFFFFBEB), // Ivory
            Color(0xFFFEF3C7), // Pale cream
            AuraGold.copy(alpha = 0.8f),
            SacredGold.copy(alpha = 0.6f),
            SpiritualPurple.copy(alpha = 0.3f)
        )
    )
}
