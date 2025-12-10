package com.spiritatlas.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*

/**
 * SpiritualIcons Example and Preview Screen
 *
 * Demonstrates all available spiritual icons with proper categorization
 * and usage examples.
 */

@Composable
fun SpiritualIconsExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "SpiritAtlas Icon System",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )

        Text(
            text = "Comprehensive collection of spiritual vector icons",
            style = MaterialTheme.typography.bodyMedium,
            color = MoonlightSilver
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Profile Type Icons
        IconCategory(title = "Profile Types") {
            IconItem(name = "Masculine") { MasculineIcon() }
            IconItem(name = "Feminine") { FeminineIcon() }
            IconItem(name = "Non-Binary") { NonBinaryIcon() }
        }

        // Zodiac Signs
        IconCategory(title = "Zodiac Signs") {
            IconItem(name = "Aries") { AriesIcon() }
            IconItem(name = "Taurus") { TaurusIcon() }
            IconItem(name = "Gemini") { GeminiIcon() }
            IconItem(name = "Cancer") { CancerIcon() }
            IconItem(name = "Leo") { LeoIcon() }
            IconItem(name = "Virgo") { VirgoIcon() }
            IconItem(name = "Libra") { LibraIcon() }
            IconItem(name = "Scorpio") { ScorpioIcon() }
            IconItem(name = "Sagittarius") { SagittariusIcon() }
            IconItem(name = "Capricorn") { CapricornIcon() }
            IconItem(name = "Aquarius") { AquariusIcon() }
            IconItem(name = "Pisces") { PiscesIcon() }
        }

        // Chakras
        IconCategory(title = "Chakras") {
            IconItem(name = "Root") { RootChakraIcon() }
            IconItem(name = "Sacral") { SacralChakraIcon() }
            IconItem(name = "Solar Plexus") { SolarPlexusChakraIcon() }
            IconItem(name = "Heart") { HeartChakraIcon() }
            IconItem(name = "Throat") { ThroatChakraIcon() }
            IconItem(name = "Third Eye") { ThirdEyeChakraIcon() }
            IconItem(name = "Crown") { CrownChakraIcon() }
        }

        // Elements
        IconCategory(title = "Elements") {
            IconItem(name = "Fire") { FireElementIcon() }
            IconItem(name = "Water") { WaterElementIcon() }
            IconItem(name = "Earth") { EarthElementIcon() }
            IconItem(name = "Air") { AirElementIcon() }
            IconItem(name = "Spirit") { SpiritElementIcon() }
        }

        // Moon Phases
        IconCategory(title = "Moon Phases") {
            IconItem(name = "New Moon") { NewMoonIcon() }
            IconItem(name = "Waxing") { WaxingMoonIcon() }
            IconItem(name = "Full Moon") { FullMoonIcon() }
            IconItem(name = "Waning") { WaningMoonIcon() }
        }

        // Sacred Geometry
        IconCategory(title = "Sacred Geometry") {
            IconItem(name = "Flower of Life") { FlowerOfLifeIcon() }
            IconItem(name = "Metatron's Cube") { MetatronsCubeIcon() }
            IconItem(name = "Sri Yantra") { SriYantraIcon() }
            IconItem(name = "Vesica Piscis") { VesicaPiscisIcon() }
        }

        // Planets
        IconCategory(title = "Planetary Symbols") {
            IconItem(name = "Sun") { SunIcon() }
            IconItem(name = "Venus") { VenusIcon() }
            IconItem(name = "Mars") { MarsIcon() }
            IconItem(name = "Mercury") { MercuryIcon() }
            IconItem(name = "Jupiter") { JupiterIcon() }
            IconItem(name = "Saturn") { SaturnIcon() }
        }

        // Size Variations
        IconCategory(title = "Size Variations") {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SunIcon(size = 16.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("16dp", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SunIcon(size = 24.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("24dp", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SunIcon(size = 32.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("32dp", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SunIcon(size = 48.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("48dp", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SunIcon(size = 64.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("64dp", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
            }
        }

        // Glow Effect Toggle
        IconCategory(title = "Glow Effects") {
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FlowerOfLifeIcon(size = 48.dp, glowEffect = false)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("No Glow", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FlowerOfLifeIcon(size = 48.dp, glowEffect = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("With Glow", style = MaterialTheme.typography.bodySmall, color = MoonlightSilver)
                }
            }
        }

        // Custom Colors
        IconCategory(title = "Custom Colors") {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                HeartChakraIcon(size = 32.dp, color = TantricRose)
                HeartChakraIcon(size = 32.dp, color = StardustBlue)
                HeartChakraIcon(size = 32.dp, color = SacredGold)
                HeartChakraIcon(size = 32.dp, color = MysticPurple)
                HeartChakraIcon(size = 32.dp, color = EarthGreen)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun IconCategory(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = AuroraPink
        )

        content()
    }
}

@Composable
private fun IconItem(
    name: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(DarkSurface, MaterialTheme.shapes.small)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MoonlightSilver
        )
    }
}

/**
 * Usage Examples for Documentation
 */
object SpiritualIconsUsageExamples {

    /**
     * Example 1: Basic icon usage
     */
    @Composable
    fun BasicUsage() {
        // Default size (24.dp) and color
        SunIcon()

        // Custom size
        MoonIcon(size = 32.dp)

        // Custom color
        HeartChakraIcon(color = TantricRose)

        // No glow effect
        FlowerOfLifeIcon(glowEffect = false)
    }

    /**
     * Example 2: Icons in a row
     */
    @Composable
    fun IconRow() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AriesIcon(size = 20.dp)
            Text("Aries", color = Color.White)
        }
    }

    /**
     * Example 3: Chakra selection
     */
    @Composable
    fun ChakraSelector() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RootChakraIcon(size = 32.dp)
            SacralChakraIcon(size = 32.dp)
            SolarPlexusChakraIcon(size = 32.dp)
            HeartChakraIcon(size = 32.dp)
            ThroatChakraIcon(size = 32.dp)
            ThirdEyeChakraIcon(size = 32.dp)
            CrownChakraIcon(size = 32.dp)
        }
    }

    /**
     * Example 4: Element indicators
     */
    @Composable
    fun ElementIndicator(element: String) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (element) {
                "Fire" -> FireElementIcon()
                "Water" -> WaterElementIcon()
                "Earth" -> EarthElementIcon()
                "Air" -> AirElementIcon()
                "Spirit" -> SpiritElementIcon()
            }
            Text(element, color = Color.White)
        }
    }

    /**
     * Example 5: Large decorative icon
     */
    @Composable
    fun DecorativeIcon() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            FlowerOfLifeIcon(
                size = 128.dp,
                color = SacredGold,
                glowEffect = true
            )
        }
    }
}

/**
 * Helper function for MoonIcon (Full Moon as default)
 */
@Composable
fun MoonIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MoonlightSilver,
    glowEffect: Boolean = true
) {
    FullMoonIcon(modifier, size, color, glowEffect)
}
