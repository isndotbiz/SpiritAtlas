package com.spiritatlas.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Material 3 Base Colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// ============================================================================
// SPIRITUAL PURPLES - Core mystical and spiritual colors
// ============================================================================
val CosmicViolet = Color(0xFF6B21A8)      // Deep cosmic purple
val MysticPurple = Color(0xFF7C3AED)      // Vibrant mystic purple
val AuroraPink = Color(0xFFEC4899)        // Aurora borealis pink
val SpiritualPurple = Color(0xFF8B5CF6)   // Main spiritual purple
val MysticViolet = Color(0xFF7C3AED)      // Deep mystic violet
val IntimacyPurple = Color(0xFFA855F7)    // Intimacy purple
val ChakraIndigo = Color(0xFF6366F1)      // Third eye chakra
val ChakraCrown = Color(0xFF8B5CF6)       // Crown chakra

// ============================================================================
// CELESTIAL BLUES - Night sky and cosmic themes
// ============================================================================
val NightSky = Color(0xFF1E1B4B)          // Deep night sky
val StardustBlue = Color(0xFF3B82F6)      // Bright stardust blue
val MoonlightSilver = Color(0xFFE2E8F0)   // Soft moonlight
val CosmicBlue = Color(0xFF3B82F6)        // Cosmic blue
val ChakraBlue = Color(0xFF3B82F6)        // Throat chakra
val AirCyan = Color(0xFF06B6D4)           // Air element cyan
val WaterTeal = Color(0xFF14B8A6)         // Water element teal
val DeepTeal = Color(0xFF008B8B)          // Deep teal

// ============================================================================
// EARTH TONES - Grounding and sacred metals
// ============================================================================
val SacredGold = Color(0xFFD97706)        // Sacred temple gold
val TempleBronze = Color(0xFFB45309)      // Ancient bronze
val GroundingBrown = Color(0xFF78350F)    // Deep earth brown
val AuraGold = Color(0xFFFBBF24)          // Golden aura
val UnionGold = Color(0xFFF59E0B)         // Union gold

// ============================================================================
// ELEMENTAL COLORS - Fire, Water, Air, Earth
// ============================================================================
val FireOrange = Color(0xFFF97316)        // Fire element
val WaterTealElement = Color(0xFF14B8A6)  // Water element
val AirCyanElement = Color(0xFF06B6D4)    // Air element
val EarthGreen = Color(0xFF22C55E)        // Earth element

// ============================================================================
// CHAKRA SYSTEM - Seven primary energy centers
// ============================================================================
val ChakraRed = Color(0xFFEF4444)         // Root chakra (Muladhara)
val ChakraOrange = Color(0xFFF97316)      // Sacral chakra (Svadhisthana)
val ChakraYellow = Color(0xFFFDE047)      // Solar plexus (Manipura)
val ChakraGreen = Color(0xFF22C55E)       // Heart chakra (Anahata)
// ChakraBlue defined above               // Throat chakra (Vishuddha)
// ChakraIndigo defined above             // Third eye (Ajna)
// ChakraCrown defined above              // Crown chakra (Sahasrara)

// ============================================================================
// TANTRIC & RELATIONSHIP COLORS - Sacred intimacy
// ============================================================================
val TantricRose = Color(0xFFEC4899)       // Passionate rose
val SensualCoral = Color(0xFFF87171)      // Sensual coral
val CosmicOrange = Color(0xFFFF7F50)      // Cosmic orange

// ============================================================================
// LIGHT & DARK THEME FOUNDATION
// ============================================================================
val LightBackground = Color(0xFFFAFAFA)
val LightSurface = Color(0xFFFFFFFF)
val LightOnBackground = Color(0xFF1C1B1F)
val LightOnSurface = Color(0xFF1C1B1F)

val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkOnBackground = Color(0xFFE6E1E5)
val DarkOnSurface = Color(0xFFE6E1E5)

// ============================================================================
// ZODIAC ELEMENT PALETTES - Colors for each astrological element
// ============================================================================
object ZodiacElementColors {
  // Fire Signs: Aries, Leo, Sagittarius
  val FireSignColors = listOf(
    FireOrange,
    Color(0xFFDC2626),     // Crimson red
    Color(0xFFF59E0B),     // Amber
    Color(0xFFEA580C),     // Orange-red
    SacredGold
  )

  // Earth Signs: Taurus, Virgo, Capricorn
  val EarthSignColors = listOf(
    EarthGreen,
    GroundingBrown,
    TempleBronze,
    Color(0xFF059669),     // Emerald
    Color(0xFF84CC16)      // Lime
  )

  // Air Signs: Gemini, Libra, Aquarius
  val AirSignColors = listOf(
    AirCyan,
    StardustBlue,
    Color(0xFF0EA5E9),     // Sky blue
    Color(0xFF8B5CF6),     // Violet
    MoonlightSilver
  )

  // Water Signs: Cancer, Scorpio, Pisces
  val WaterSignColors = listOf(
    WaterTeal,
    DeepTeal,
    Color(0xFF3B82F6),     // Ocean blue
    CosmicViolet,
    Color(0xFF6366F1)      // Indigo
  )
}

// ============================================================================
// SPIRITUAL GRADIENTS - Beautiful gradient definitions
// ============================================================================
object SpiritualGradients {
  // Cosmic night sky gradient
  val cosmicNight: Brush = Brush.verticalGradient(
    colors = listOf(
      NightSky,
      CosmicViolet,
      MysticPurple
    )
  )

  // Aurora borealis gradient
  val auroraBorealis: Brush = Brush.linearGradient(
    colors = listOf(
      AuroraPink,
      MysticPurple,
      StardustBlue,
      WaterTeal
    )
  )

  // Golden hour gradient
  val goldenHour: Brush = Brush.horizontalGradient(
    colors = listOf(
      FireOrange,
      SacredGold,
      AuraGold,
      ChakraYellow
    )
  )

  // Chakra rainbow gradient (root to crown)
  val chakraRainbow: Brush = Brush.verticalGradient(
    colors = listOf(
      ChakraRed,
      ChakraOrange,
      ChakraYellow,
      ChakraGreen,
      ChakraBlue,
      ChakraIndigo,
      ChakraCrown
    )
  )

  // Mystic fog gradient
  val mysticFog: Brush = Brush.radialGradient(
    colors = listOf(
      MysticPurple.copy(alpha = 0.4f),
      CosmicViolet.copy(alpha = 0.2f),
      NightSky.copy(alpha = 0.1f)
    )
  )

  // Energy flow gradient
  val energyFlow: Brush = Brush.linearGradient(
    colors = listOf(
      SpiritualPurple,
      AuroraPink,
      SacredGold,
      WaterTeal
    )
  )

  // Tantric passion gradient
  val tantricPassion: Brush = Brush.linearGradient(
    colors = listOf(
      TantricRose,
      SensualCoral,
      IntimacyPurple
    )
  )

  // Sacred union gradient
  val sacredUnion: Brush = Brush.verticalGradient(
    colors = listOf(
      CosmicViolet,
      MysticPurple,
      UnionGold
    )
  )

  // Moonlight gradient
  val moonlight: Brush = Brush.verticalGradient(
    colors = listOf(
      NightSky,
      Color(0xFF334155),
      MoonlightSilver
    )
  )

  // Earth connection gradient
  val earthConnection: Brush = Brush.verticalGradient(
    colors = listOf(
      GroundingBrown,
      TempleBronze,
      SacredGold
    )
  )
}

// ============================================================================
// GRADIENT COLLECTIONS - Organized gradient lists
// ============================================================================
val SpiritualGradient = listOf(SpiritualPurple, MysticViolet, CosmicBlue)
val ChakraGradient = listOf(
  ChakraRed,
  ChakraOrange,
  ChakraYellow,
  ChakraGreen,
  ChakraBlue,
  ChakraIndigo,
  ChakraCrown
)
val TantricGradient = listOf(TantricRose, SensualCoral, IntimacyPurple)
val CosmicGradient = listOf(NightSky, CosmicViolet, StardustBlue, MoonlightSilver)
val ElementalGradient = listOf(FireOrange, EarthGreen, AirCyan, WaterTeal)

// ============================================================================
// SPIRITUAL COLORS COLLECTION - Organized color mappings
// ============================================================================
object SpiritualColors {
  val ChakraColors = listOf(
    ChakraRed,    // Root (Muladhara)
    ChakraOrange, // Sacral (Svadhisthana)
    ChakraYellow, // Solar Plexus (Manipura)
    ChakraGreen,  // Heart (Anahata)
    ChakraBlue,   // Throat (Vishuddha)
    ChakraIndigo, // Third Eye (Ajna)
    ChakraCrown   // Crown (Sahasrara)
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

  val ElementColors = mapOf(
    "Fire" to FireOrange,
    "Earth" to EarthGreen,
    "Air" to AirCyan,
    "Water" to WaterTeal
  )

  val PlanetaryColors = mapOf(
    "Sun" to SacredGold,
    "Moon" to MoonlightSilver,
    "Mercury" to AirCyan,
    "Venus" to TantricRose,
    "Mars" to ChakraRed,
    "Jupiter" to SpiritualPurple,
    "Saturn" to NightSky,
    "Uranus" to StardustBlue,
    "Neptune" to WaterTeal,
    "Pluto" to CosmicViolet
  )

  val AyurvedicDoshaColors = mapOf(
    "Vata" to AirCyan,
    "Pitta" to FireOrange,
    "Kapha" to EarthGreen
  )
}

// ============================================================================
// SHADOW & GLOW COLORS - For elevation and effects
// ============================================================================
object ShadowColors {
  // Purple-tinted shadows for spiritual feel
  val LightShadow = CosmicViolet.copy(alpha = 0.08f)
  val MediumShadow = CosmicViolet.copy(alpha = 0.12f)
  val DarkShadow = CosmicViolet.copy(alpha = 0.16f)

  // Glow effects
  val PurpleGlow = MysticPurple.copy(alpha = 0.3f)
  val GoldGlow = SacredGold.copy(alpha = 0.3f)
  val BlueGlow = StardustBlue.copy(alpha = 0.3f)
  val PinkGlow = AuroraPink.copy(alpha = 0.3f)
}

// ============================================================================
// SEMANTIC COLORS - Success, Error, Warning, Info
// ============================================================================
object SemanticColors {
  val Success = ChakraGreen
  val Error = ChakraRed
  val Warning = SacredGold
  val Info = StardustBlue

  val SuccessContainer = ChakraGreen.copy(alpha = 0.12f)
  val ErrorContainer = ChakraRed.copy(alpha = 0.12f)
  val WarningContainer = SacredGold.copy(alpha = 0.12f)
  val InfoContainer = StardustBlue.copy(alpha = 0.12f)
}

// ============================================================================
// OVERLAY COLORS - For scrim, dim, and overlay effects
// ============================================================================
object OverlayColors {
  val Scrim = Color(0x80000000)  // 50% black
  val DimLight = Color(0x26000000)  // 15% black
  val DimMedium = Color(0x4D000000)  // 30% black
  val DimHeavy = Color(0x99000000)  // 60% black

  val MysticOverlay = CosmicViolet.copy(alpha = 0.15f)
  val GoldenOverlay = SacredGold.copy(alpha = 0.15f)
  val CosmicOverlay = NightSky.copy(alpha = 0.7f)
}
