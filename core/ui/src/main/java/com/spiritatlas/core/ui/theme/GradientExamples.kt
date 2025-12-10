package com.spiritatlas.core.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spiritatlas.domain.model.*
import java.time.LocalDateTime

/**
 * Gradient System Usage Examples
 *
 * This file demonstrates how to use the SpiritAtlas gradient system
 * with practical examples for common UI patterns.
 *
 * See docs/GRADIENT_SYSTEM_GUIDE.md for complete documentation.
 */

// ============================================================================
// EXAMPLE 1: Static Gradient from Library
// ============================================================================

@Composable
fun ChakraCard(chakraName: String, chakraIndex: Int) {
    val gradient = when (chakraIndex) {
        0 -> GradientLibrary.rootChakra
        1 -> GradientLibrary.sacralChakra
        2 -> GradientLibrary.solarPlexusChakra
        3 -> GradientLibrary.heartChakra
        4 -> GradientLibrary.throatChakra
        5 -> GradientLibrary.thirdEyeChakra
        6 -> GradientLibrary.crownChakra
        else -> GradientLibrary.heartChakra
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chakraName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ============================================================================
// EXAMPLE 2: Dynamic Gradient from User Profile
// ============================================================================

@Composable
fun ProfileHeaderWithDynamicGradient(profile: UserProfile) {
    // Generate personalized gradient from profile data
    val profileGradient = DynamicGradients.fromProfile(profile)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(profileGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = profile.profileName,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            profile.birthDateTime?.let { birthDate ->
                Text(
                    text = "Born: ${birthDate.toLocalDate()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

// ============================================================================
// EXAMPLE 3: Compatibility Score Visualization
// ============================================================================

@Composable
fun CompatibilityScoreCard(score: Double, partnerAName: String, partnerBName: String) {
    val compatibilityGradient = DynamicGradients.fromCompatibilityScore(score)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(compatibilityGradient)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$partnerAName & $partnerBName",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Column {
                    Text(
                        text = "${score.toInt()}%",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White
                    )
                    Text(
                        text = when {
                            score >= 90 -> "Soulmate Connection"
                            score >= 75 -> "Excellent Match"
                            score >= 60 -> "Good Compatibility"
                            score >= 45 -> "Moderate Match"
                            score >= 30 -> "Needs Work"
                            else -> "Challenging"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

// ============================================================================
// EXAMPLE 4: Zodiac Sign Background
// ============================================================================

@Composable
fun ZodiacProfileScreen(zodiacSign: String) {
    val zodiacGradient = DynamicGradients.fromZodiacSign(zodiacSign)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(zodiacGradient)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = zodiacSign,
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Rest of content
        }
    }
}

// ============================================================================
// EXAMPLE 5: Time-Aware Splash Screen
// ============================================================================

@Composable
fun TimeAwareSplashScreen() {
    val currentGradient = DynamicGradients.fromTimeOfDay()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(currentGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "SpiritAtlas",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
            Text(
                text = getGreetingForTime(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

private fun getGreetingForTime(): String {
    val hour = LocalDateTime.now().hour
    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }
}

// ============================================================================
// EXAMPLE 6: Animated Gradient Background
// ============================================================================

@Composable
fun MeditationScreenWithAnimation() {
    val breathingGradient = AnimatedGradients.breathing(
        colors = listOf(
            NightSky,
            CosmicViolet,
            MysticPurple,
            SpiritualPurple
        ),
        durationMillis = 4000,  // 4-second breath cycle
        minAlpha = 0.6f,
        maxAlpha = 1.0f,
        direction = GradientDirection.Radial
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(breathingGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Breathe",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
            Text(
                text = "Follow the rhythm",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

// ============================================================================
// EXAMPLE 7: Flowing Chakra Energy
// ============================================================================

@Composable
fun ChakraEnergyScreen() {
    val chakraFlow = AnimatedGradients.chakraFlow()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(chakraFlow),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Energy Flowing",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )
    }
}

// ============================================================================
// EXAMPLE 8: Mood-Based Background
// ============================================================================

@Composable
fun JournalEntryScreen(currentMood: String) {
    val moodGradient = DynamicGradients.fromMood(currentMood)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(moodGradient)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "How are you feeling?",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Text(
                text = "Current mood: $currentMood",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
            // Journal content
        }
    }
}

// ============================================================================
// EXAMPLE 9: Intention Manifestation Card
// ============================================================================

@Composable
fun ManifestationCard(intention: String) {
    val intentionGradient = DynamicGradients.fromIntention(intention)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(intentionGradient)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "I manifest",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                    text = intention.uppercase(),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )
            }
        }
    }
}

// ============================================================================
// EXAMPLE 10: Seasonal Dashboard
// ============================================================================

@Composable
fun SeasonalDashboard() {
    val seasonGradient = DynamicGradients.fromSeason()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(seasonGradient)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = getCurrentSeasonName(),
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Text(
                text = "Energy of the season",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Dashboard content
        }
    }
}

private fun getCurrentSeasonName(): String {
    val month = LocalDateTime.now().monthValue
    return when (month) {
        3, 4, 5 -> "Spring"
        6, 7, 8 -> "Summer"
        9, 10, 11 -> "Autumn"
        else -> "Winter"
    }
}

// ============================================================================
// EXAMPLE 11: Animated Preset Showcase
// ============================================================================

@Composable
fun AnimatedPresetsShowcase() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedPresetCard("Aurora", AnimatedGradients.aurora())
        AnimatedPresetCard("Cosmic Pulse", AnimatedGradients.cosmicPulse())
        AnimatedPresetCard("Sacred Geometry", AnimatedGradients.sacredGeometry())
        AnimatedPresetCard("Tantric Energy", AnimatedGradients.tantricEnergy())
        AnimatedPresetCard("Golden Hour", AnimatedGradients.goldenHour())
        AnimatedPresetCard("Heart Opening", AnimatedGradients.heartOpening())
    }
}

@Composable
private fun AnimatedPresetCard(name: String, gradient: Brush) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
    }
}

// ============================================================================
// EXAMPLE 12: Transit-Based Alert
// ============================================================================

@Composable
fun PlanetaryTransitAlert(planetName: String, transitType: String, message: String) {
    val transitGradient = DynamicGradients.fromTransit(planetName, transitType)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(transitGradient)
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$planetName $transitType",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}
