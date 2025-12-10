package com.spiritatlas.core.ui.animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.ChakraCrown
import com.spiritatlas.core.ui.theme.CosmicBlue
import com.spiritatlas.core.ui.theme.DarkBackground
import com.spiritatlas.core.ui.theme.MysticPurple
import com.spiritatlas.core.ui.theme.SpiritualPurple
import com.spiritatlas.core.ui.theme.TantricRose

/**
 * Animation Showcase Screen
 * Demonstrates all the enhanced spiritual animations in the app
 *
 * This screen is for development and demonstration purposes.
 * Each animation can be integrated into the actual app screens as needed.
 */
@Composable
fun AnimationShowcaseScreen() {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = DarkBackground
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      Text(
        text = "Spiritual Animations Showcase",
        style = MaterialTheme.typography.headlineMedium,
        color = AuraGold
      )

      // ========================================================================
      // CHAKRA ANIMATIONS
      // ========================================================================
      AnimationSection(title = "Chakra Animations") {
        // Spinning Chakra with Particles
        Box(
          modifier = Modifier
            .size(200.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          SpinningChakraWithParticles(
            modifier = Modifier.fillMaxSize(),
            chakraIndex = 6, // Crown chakra
            particleCount = 24,
            rotationDurationMs = 8000
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Chakra Spinner (smaller)
        Row(
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          for (i in 0 until 7) {
            Box(
              modifier = Modifier
                .size(60.dp)
                .background(Color.Black.copy(alpha = 0.3f))
            ) {
              ChakraSpinner(
                modifier = Modifier.fillMaxSize(),
                chakraIndex = i,
                size = 25f
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Chakra Alignment
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          ChakraAlignment(
            modifier = Modifier.fillMaxSize(),
            spacing = 80f
          )
        }
      }

      // ========================================================================
      // ZODIAC ANIMATIONS
      // ========================================================================
      var zodiacRevealed by remember { mutableStateOf(false) }
      AnimationSection(title = "Zodiac Constellation Animations") {
        Button(onClick = { zodiacRevealed = !zodiacRevealed }) {
          Text(if (zodiacRevealed) "Reset" else "Reveal Constellation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.Black)
        ) {
          // Star field background
          StarFieldBackground(
            modifier = Modifier.fillMaxSize(),
            starCount = 150
          )

          // Constellation reveal
          ConstellationReveal(
            modifier = Modifier.fillMaxSize(),
            zodiacSign = "Leo",
            isRevealed = zodiacRevealed,
            starColor = AuraGold,
            lineColor = CosmicBlue
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Zodiac wheel rotation
        Box(
          modifier = Modifier
            .size(300.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          ZodiacWheelRotation(
            modifier = Modifier.fillMaxSize(),
            rotationDurationMs = 30000,
            highlightedSign = "Leo"
          )
        }
      }

      // ========================================================================
      // ENERGY FLOW ANIMATIONS
      // ========================================================================
      AnimationSection(title = "Energy Flow Animations") {
        // Horizontal energy flow
        Text(
          text = "Compatibility: 85%",
          style = MaterialTheme.typography.bodyMedium,
          color = Color.White
        )

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          EnergyFlowBetweenProfiles(
            modifier = Modifier.fillMaxSize(),
            compatibilityPercentage = 0.85f,
            isHorizontal = true,
            particleCount = 30,
            bidirectional = true
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Circular energy flow
        Box(
          modifier = Modifier
            .size(200.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          CircularEnergyFlow(
            modifier = Modifier.fillMaxSize(),
            color = SpiritualPurple,
            particleCount = 12,
            radius = 0.4f
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Heart connection
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          HeartConnection(
            modifier = Modifier.fillMaxSize(),
            connectionStrength = 0.8f,
            color = TantricRose
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Compatibility energy meter
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(16.dp)
        ) {
          CompatibilityEnergyMeter(
            modifier = Modifier.fillMaxSize(),
            percentage = 0.75f,
            showPercentage = true
          )
        }
      }

      // ========================================================================
      // PLANET ANIMATIONS
      // ========================================================================
      AnimationSection(title = "Planet Orbit Animations") {
        // Full solar system
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black)
        ) {
          SolarSystemAnimation(
            modifier = Modifier.fillMaxSize(),
            showOrbits = true,
            showInnerPlanets = true,
            showOuterPlanets = false, // Only show inner planets for clarity
            speedMultiplier = 2.0f
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Single planet orbit
        Box(
          modifier = Modifier
            .size(250.dp)
            .background(Color.Black)
        ) {
          SinglePlanetOrbit(
            modifier = Modifier.fillMaxSize(),
            planetName = "Earth",
            orbitDurationMs = 6000,
            showOrbit = true
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Moon phases
        Box(
          modifier = Modifier
            .size(200.dp)
            .background(Color.Black)
        ) {
          MoonPhasesAnimation(
            modifier = Modifier.fillMaxSize(),
            phaseDurationMs = 8000
          )
        }
      }

      // ========================================================================
      // SACRED GEOMETRY ANIMATIONS
      // ========================================================================
      AnimationSection(title = "Sacred Geometry Animations") {
        // Flower of Life
        Box(
          modifier = Modifier
            .size(250.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          FlowerOfLife(
            modifier = Modifier.fillMaxSize(),
            circleCount = 7,
            color = AuraGold,
            strokeWidth = 2f,
            animate = false // Set to true to see expansion animation
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Metatron's Cube
        Box(
          modifier = Modifier
            .size(300.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          MetatronsCube(
            modifier = Modifier.fillMaxSize(),
            color = SpiritualPurple,
            rotationDurationMs = 30000
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sri Yantra
        Box(
          modifier = Modifier
            .size(300.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          SriYantra(
            modifier = Modifier.fillMaxSize(),
            morphProgress = 0.5f,
            color = AuraGold
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Golden Ratio Spiral
        Box(
          modifier = Modifier
            .size(300.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          GoldenRatioSpiral(
            modifier = Modifier.fillMaxSize(),
            spiralProgress = 1f,
            color = AuraGold
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Vesica Piscis
        Box(
          modifier = Modifier
            .size(250.dp)
            .background(Color.Black.copy(alpha = 0.3f))
        ) {
          VesicaPiscis(
            modifier = Modifier.fillMaxSize(),
            color = MysticPurple,
            animate = false
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Platonic Solids
        Row(
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          listOf("tetrahedron", "cube", "octahedron").forEach { solid ->
            Box(
              modifier = Modifier
                .size(120.dp)
                .background(Color.Black.copy(alpha = 0.3f))
            ) {
              PlatonicSolid(
                modifier = Modifier.fillMaxSize(),
                solidType = solid,
                color = ChakraCrown
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}

/**
 * Helper composable for organizing animation sections
 */
@Composable
private fun AnimationSection(
  title: String,
  content: @Composable () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White.copy(alpha = 0.05f))
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.titleLarge,
      color = CosmicBlue
    )
    content()
  }
}

/**
 * Usage Examples:
 *
 * 1. Chakra Meditation Screen:
 * ```
 * SpinningChakraWithParticles(
 *   modifier = Modifier.size(300.dp),
 *   chakraIndex = selectedChakraIndex,
 *   particleCount = 30
 * )
 * ```
 *
 * 2. Compatibility Analysis Screen:
 * ```
 * EnergyFlowBetweenProfiles(
 *   modifier = Modifier.fillMaxWidth().height(120.dp),
 *   compatibilityPercentage = compatibilityScore / 100f,
 *   bidirectional = true
 * )
 * ```
 *
 * 3. Astrology Chart Screen:
 * ```
 * ConstellationReveal(
 *   modifier = Modifier.fillMaxSize(),
 *   zodiacSign = userProfile.sunSign,
 *   isRevealed = true
 * )
 * ```
 *
 * 4. Sacred Space/Meditation:
 * ```
 * FlowerOfLife(
 *   modifier = Modifier.size(400.dp),
 *   circleCount = 19,
 *   animate = true
 * )
 * ```
 *
 * 5. Planetary Positions:
 * ```
 * SolarSystemAnimation(
 *   modifier = Modifier.fillMaxSize(),
 *   showOrbits = true,
 *   speedMultiplier = 3.0f
 * )
 * ```
 */
