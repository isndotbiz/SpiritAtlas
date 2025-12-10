package com.spiritatlas.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * SPIRITUAL COLOR PALETTE ORGANIZATION
 *
 * This file provides organized access to the expanded spiritual color system.
 * All colors are WCAG AAA compliant when used with appropriate backgrounds.
 *
 * Total Colors: 100+ individual colors
 * Total Gradients: 30+ unique gradients (light + dark mode variants)
 */

// ============================================================================
// CHAKRA PALETTE - Complete chakra system with variations
// ============================================================================
object ChakraPalette {
  // Primary chakra colors (7 main energy centers)
  val primary = listOf(
    ChakraRed,      // 1. Root (Muladhara)
    ChakraOrange,   // 2. Sacral (Svadhisthana)
    ChakraYellow,   // 3. Solar Plexus (Manipura)
    ChakraGreen,    // 4. Heart (Anahata)
    ChakraBlue,     // 5. Throat (Vishuddha)
    ChakraIndigo,   // 6. Third Eye (Ajna)
    ChakraCrown     // 7. Crown (Sahasrara)
  )

  // Light variations (20% lighter for highlights)
  val light = listOf(
    ChakraRedLight,
    ChakraOrangeLight,
    ChakraYellowLight,
    ChakraGreenLight,
    ChakraBlueLight,
    ChakraIndigoLight,
    ChakraCrownLight
  )

  // Dark variations (20% darker for shadows)
  val dark = listOf(
    ChakraRedDark,
    ChakraOrangeDark,
    ChakraYellowDark,
    ChakraGreenDark,
    ChakraBlueDark,
    ChakraIndigoDark,
    ChakraCrownDark
  )

  // Pale variations (for backgrounds and subtle accents)
  val pale = listOf(
    ChakraRedPale,
    ChakraOrangePale,
    ChakraYellowPale,
    ChakraGreenPale,
    ChakraBluePale,
    ChakraIndigoPale,
    ChakraCrownPale
  )

  // Get chakra color by index (0-6)
  fun getByIndex(index: Int): Color = primary.getOrElse(index) { ChakraCrown }

  // Get chakra light variation by index
  fun getLightByIndex(index: Int): Color = light.getOrElse(index) { ChakraCrownLight }

  // Get chakra dark variation by index
  fun getDarkByIndex(index: Int): Color = dark.getOrElse(index) { ChakraCrownDark }

  // Get chakra pale variation by index
  fun getPaleByIndex(index: Int): Color = pale.getOrElse(index) { ChakraCrownPale }
}

// ============================================================================
// ELEMENTAL PALETTE - Fire, Water, Air, Earth
// ============================================================================
object ElementalPalette {
  // Fire Element Colors (8 variations)
  object Fire {
    val primary = FireOrange
    val all = listOf(
      FireCoal,       // Darkest
      FireEmber,
      FireCrimson,
      FireOrange,     // Primary
      FireFlame,
      FireSunset,
      FireAmber,      // Lightest
      FireInferno
    )
    val gradient = SpiritualGradients.fireJourney
    val gradientDark = SpiritualGradients.fireJourneyDark
  }

  // Water Element Colors (8 variations)
  object Water {
    val primary = WaterTealElement
    val all = listOf(
      WaterAbyss,     // Darkest
      WaterDeepOcean,
      WaterCoral,
      WaterTealElement, // Primary
      WaterTurquoise,
      WaterAqua,
      WaterSeafoam,
      WaterMist       // Lightest
    )
    val gradient = SpiritualGradients.waterFlow
    val gradientDark = SpiritualGradients.waterFlowDark
  }

  // Air Element Colors (8 variations)
  object Air {
    val primary = AirCyanElement
    val all = listOf(
      AirThunder,     // Darkest
      AirStorm,
      AirCyanElement, // Primary
      AirSky,
      AirBreeze,
      AirMist,
      AirCloud,
      AirFrost        // Lightest
    )
    val gradient = SpiritualGradients.airAscension
  }

  // Earth Element Colors (8 variations)
  object Earth {
    val primary = EarthGreen
    val all = listOf(
      EarthRoot,      // Darkest
      EarthStone,
      EarthClay,
      EarthTerracotta,
      EarthForest,
      EarthGreen,     // Primary
      EarthMoss,
      EarthSand       // Lightest
    )
    val gradient = SpiritualGradients.earthGrounding
  }

  // Get element colors by name
  fun getByName(element: String): List<Color> = when (element.lowercase()) {
    "fire" -> Fire.all
    "water" -> Water.all
    "air" -> Air.all
    "earth" -> Earth.all
    else -> Fire.all
  }

  // All elemental colors combined
  val allColors = Fire.all + Water.all + Air.all + Earth.all
}

// ============================================================================
// CELESTIAL PALETTE - Stars, Cosmos, Solar
// ============================================================================
object CelestialPalette {
  // Star Colors (5 metallic tones)
  object Stars {
    val all = listOf(
      StarWhiteGold,
      StarSilver,
      StarPlatinum,
      StarDiamond,
      StarCrystal
    )
    val brightest = StarWhiteGold
    val softest = StarCrystal
  }

  // Cosmic Colors (7 deep space themes)
  object Cosmos {
    val all = listOf(
      CosmicDeepSpace,
      CosmicVoid,
      CosmicNebulaPurple,
      CosmicNebulaPink,
      CosmicGalaxyBlue,
      CosmicStardust,
      CosmicAurora
    )
    val darkest = CosmicDeepSpace
    val brightest = CosmicAurora
    val gradient = SpiritualGradients.cosmicStarfield
    val gradientDark = SpiritualGradients.cosmicStarfieldDark
  }

  // Solar Colors (7 sun and light themes)
  object Solar {
    val all = listOf(
      SolarDawn,
      SolarGoldenHour,
      SolarSunrise,
      SolarNoon,
      SolarFlare,
      SolarSunset,
      SolarDusk
    )
    val brightest = SolarNoon
    val warmest = SolarSunset
    val gradient = SpiritualGradients.solarEclipse
  }

  // All celestial colors combined
  val allColors = Stars.all + Cosmos.all + Solar.all
}

// ============================================================================
// SPIRITUAL GRADIENTS CATALOG - Organized gradient collection
// ============================================================================
object GradientCatalog {
  // Chakra Gradients
  object Chakra {
    val rainbow = SpiritualGradients.chakraRainbow
    val flow = SpiritualGradients.chakraFlow
    val flowDark = SpiritualGradients.chakraFlowDark
  }

  // Elemental Gradients
  object Elements {
    val fire = SpiritualGradients.fireJourney
    val fireDark = SpiritualGradients.fireJourneyDark
    val water = SpiritualGradients.waterFlow
    val waterDark = SpiritualGradients.waterFlowDark
    val air = SpiritualGradients.airAscension
    val earth = SpiritualGradients.earthGrounding
    val balance = SpiritualGradients.elementalBalance
  }

  // Celestial Gradients
  object Celestial {
    val cosmicNight = SpiritualGradients.cosmicNight
    val cosmicOcean = SpiritualGradients.cosmicOcean
    val cosmicOceanDark = SpiritualGradients.cosmicOceanDark
    val starfield = SpiritualGradients.cosmicStarfield
    val starfieldDark = SpiritualGradients.cosmicStarfieldDark
    val solarEclipse = SpiritualGradients.solarEclipse
    val moonlight = SpiritualGradients.moonlight
    val lunarPhases = SpiritualGradients.lunarPhases
  }

  // Nature Gradients
  object Nature {
    val aurora = SpiritualGradients.aurora
    val auroraDark = SpiritualGradients.auroraDark
    val auroraBorealis = SpiritualGradients.auroraBorealis
    val desertSunrise = SpiritualGradients.desertSunrise
    val desertSunriseDark = SpiritualGradients.desertSunriseDark
    val forestMystical = SpiritualGradients.forestMystical
    val forestMysticalDark = SpiritualGradients.forestMysticalDark
    val arcticIce = SpiritualGradients.arcticIce
    val arcticIceDark = SpiritualGradients.arcticIceDark
    val volcanicFire = SpiritualGradients.volcanicFire
    val volcanicFireDark = SpiritualGradients.volcanicFireDark
    val crystalCave = SpiritualGradients.crystalCave
    val crystalCaveDark = SpiritualGradients.crystalCaveDark
  }

  // Seasonal Gradients
  object Seasons {
    val springBloom = SpiritualGradients.springBloom
    val springBloomDark = SpiritualGradients.springBloomDark
    val autumnEquinox = SpiritualGradients.autumnEquinox
    val autumnEquinoxDark = SpiritualGradients.autumnEquinoxDark
    val tropicalParadise = SpiritualGradients.tropicalParadise
    val tropicalParadiseDark = SpiritualGradients.tropicalParadiseDark
  }

  // Spiritual Practice Gradients
  object Practice {
    val healing = SpiritualGradients.healingEnergy
    val healingDark = SpiritualGradients.healingEnergyDark
    val meditation = SpiritualGradients.meditationSerenity
    val tantric = SpiritualGradients.tantricPassion
    val tantricAwakening = SpiritualGradients.tantricAwakening
    val ascension = SpiritualGradients.ascensionPath
    val energyFlow = SpiritualGradients.energyFlow
    val sacredUnion = SpiritualGradients.sacredUnion
  }

  // Divine Energy Gradients
  object Divine {
    val masculine = SpiritualGradients.divineMasculine
    val feminine = SpiritualGradients.divineFeminine
    val union = SpiritualGradients.sacredUnion
  }

  // Miscellaneous Atmospheric Gradients
  object Atmosphere {
    val goldenHour = SpiritualGradients.goldenHour
    val mysticFog = SpiritualGradients.mysticFog
    val earthConnection = SpiritualGradients.earthConnection
  }

  // Get all light mode gradients
  val allLightMode = listOf(
    Chakra.rainbow, Chakra.flow,
    Elements.fire, Elements.water, Elements.air, Elements.earth, Elements.balance,
    Celestial.cosmicNight, Celestial.cosmicOcean, Celestial.starfield, Celestial.solarEclipse,
    Nature.aurora, Nature.desertSunrise, Nature.forestMystical, Nature.arcticIce,
    Practice.healing, Practice.meditation, Practice.tantric, Practice.ascension
  )

  // Get all dark mode gradients
  val allDarkMode = listOf(
    Chakra.flowDark,
    Elements.fireDark, Elements.waterDark,
    Celestial.cosmicOceanDark, Celestial.starfieldDark,
    Nature.auroraDark, Nature.desertSunriseDark, Nature.forestMysticalDark, Nature.arcticIceDark,
    Practice.healingDark
  )
}

// ============================================================================
// THEMED PALETTES - Pre-configured color combinations
// ============================================================================
object ThemedPalettes {
  // Numerology themed colors (1-9)
  val numerology = mapOf(
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

  // Tantric relationship colors
  val tantric = mapOf(
    "Passionate" to TantricRose,
    "Sensual" to SensualCoral,
    "Sacred" to IntimacyPurple,
    "Divine" to SpiritualPurple,
    "Union" to UnionGold,
    "Mystical" to MysticViolet,
    "Cosmic" to CosmicBlue
  )

  // Planetary colors
  val planetary = mapOf(
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

  // Ayurvedic doshas
  val ayurvedic = mapOf(
    "Vata" to AirCyan,
    "Pitta" to FireOrange,
    "Kapha" to EarthGreen
  )

  // Zodiac elements
  val zodiacElements = mapOf(
    "Fire" to ZodiacElementColors.FireSignColors,
    "Earth" to ZodiacElementColors.EarthSignColors,
    "Air" to ZodiacElementColors.AirSignColors,
    "Water" to ZodiacElementColors.WaterSignColors
  )
}

// ============================================================================
// COLOR UTILITY FUNCTIONS
// ============================================================================

/**
 * Get a gradient appropriate for the current theme mode
 */
fun Brush.Companion.spiritual(
  type: String,
  isDarkMode: Boolean = false
): Brush = when (type.lowercase()) {
  "chakra" -> if (isDarkMode) GradientCatalog.Chakra.flowDark else GradientCatalog.Chakra.flow
  "fire" -> if (isDarkMode) GradientCatalog.Elements.fireDark else GradientCatalog.Elements.fire
  "water" -> if (isDarkMode) GradientCatalog.Elements.waterDark else GradientCatalog.Elements.water
  "healing" -> if (isDarkMode) GradientCatalog.Practice.healingDark else GradientCatalog.Practice.healing
  "cosmic" -> if (isDarkMode) GradientCatalog.Celestial.starfieldDark else GradientCatalog.Celestial.starfield
  "aurora" -> if (isDarkMode) GradientCatalog.Nature.auroraDark else GradientCatalog.Nature.aurora
  else -> GradientCatalog.Chakra.flow
}

/**
 * Get an elemental color by name and intensity (0.0 = darkest, 1.0 = lightest)
 */
fun Color.Companion.element(element: String, intensity: Float = 0.5f): Color {
  val colors = ElementalPalette.getByName(element)
  val index = (intensity * (colors.size - 1)).toInt().coerceIn(0, colors.size - 1)
  return colors[index]
}

/**
 * Get a chakra color with optional variation
 */
fun Color.Companion.chakra(
  index: Int,
  variation: ChakraVariation = ChakraVariation.PRIMARY
): Color = when (variation) {
  ChakraVariation.PRIMARY -> ChakraPalette.getByIndex(index)
  ChakraVariation.LIGHT -> ChakraPalette.getLightByIndex(index)
  ChakraVariation.DARK -> ChakraPalette.getDarkByIndex(index)
  ChakraVariation.PALE -> ChakraPalette.getPaleByIndex(index)
}

enum class ChakraVariation {
  PRIMARY,
  LIGHT,
  DARK,
  PALE
}
