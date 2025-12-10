package com.spiritatlas.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Examples and usage guide for GradientText components
 * Demonstrates all gradient text effects available in SpiritAtlas
 */

@Composable
fun GradientTextShowcase(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Section: Basic Gradient Text
        SectionHeader("Basic Gradient Text")

        GradientText(
            text = "Discover Your Path",
            gradient = SpiritualGradients.cosmicPurple,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        GradientText(
            text = "Mystical Journey Awaits",
            gradient = SpiritualGradients.mysticGold,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            gradientDirection = GradientDirection.Horizontal
        )

        GradientText(
            text = "Vertical Gradient",
            gradient = SpiritualGradients.waterFlow,
            style = MaterialTheme.typography.headlineSmall,
            gradientDirection = GradientDirection.Vertical
        )

        GradientText(
            text = "Diagonal Flow",
            gradient = SpiritualGradients.aurora,
            style = MaterialTheme.typography.headlineSmall,
            gradientDirection = GradientDirection.Diagonal
        )

        // Section: Animated Gradient Text
        SectionHeader("Animated Gradient Text")

        AnimatedGradientText(
            text = "Spiritual Atlas",
            gradient = SpiritualGradients.chakraRainbow,
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            animationDuration = 3000,
            animationDirection = AnimationDirection.LeftToRight
        )

        AnimatedGradientText(
            text = "Flowing Energy",
            gradient = SpiritualGradients.fireEnergy,
            style = MaterialTheme.typography.headlineLarge,
            animationDuration = 2000,
            animationDirection = AnimationDirection.RightToLeft
        )

        AnimatedGradientText(
            text = "Rising Consciousness",
            gradient = SpiritualGradients.twilight,
            style = MaterialTheme.typography.headlineMedium,
            animationDuration = 4000,
            animationDirection = AnimationDirection.TopToBottom
        )

        // Section: Shimmer Text
        SectionHeader("Shimmer Text Effects")

        ShimmerText(
            text = "Shimmering Light",
            baseColors = listOf(
                SpiritualGradients.cosmicPurple[0],
                SpiritualGradients.cosmicPurple[1]
            ),
            shimmerColor = Color.White,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            shimmerDuration = 2000
        )

        ShimmerText(
            text = "Divine Radiance",
            baseColors = SpiritualGradients.mysticGold,
            shimmerColor = Color(0xFFFFFFFF),
            style = MaterialTheme.typography.headlineMedium,
            shimmerDuration = 1500
        )

        // Section: Glowing Text
        SectionHeader("Glowing Text Effects")

        GlowingText(
            text = "Illuminated Wisdom",
            textColor = Color.White,
            glowColor = SpiritualGradients.cosmicPurple[0],
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            animateGlow = true,
            glowRadius = 20f
        )

        GlowingText(
            text = "Sacred Energy",
            textColor = Color.White,
            glowColor = SpiritualGradients.fireEnergy[0],
            style = MaterialTheme.typography.headlineLarge,
            animateGlow = true,
            glowRadius = 15f
        )

        GlowingText(
            text = "Static Aura",
            textColor = SpiritualGradients.mysticGold[0],
            glowColor = SpiritualGradients.mysticGold[1],
            style = MaterialTheme.typography.headlineMedium,
            animateGlow = false,
            glowRadius = 12f
        )

        // Section: Typewriter Text
        SectionHeader("Typewriter Text Effects")

        TypewriterText(
            text = "Your destiny unfolds...",
            style = MaterialTheme.typography.headlineMedium,
            textColor = MaterialTheme.colorScheme.onSurface,
            characterDelayMs = 80,
            showCursor = true,
            onComplete = {
                // Action when typing completes
                println("Typewriter animation complete!")
            }
        )

        TypewriterText(
            text = "Chakras aligning",
            gradient = SpiritualGradients.chakraRainbow,
            style = MaterialTheme.typography.headlineSmall,
            characterDelayMs = 60,
            showCursor = true
        )

        TypewriterText(
            text = "Fast cosmic typing",
            gradient = SpiritualGradients.cosmicPurple,
            style = MaterialTheme.typography.titleLarge,
            characterDelayMs = 30,
            showCursor = false
        )

        // Section: Fade In Text
        SectionHeader("Fade In Text Effects")

        FadeInText(
            text = "Welcome to your spiritual journey",
            style = MaterialTheme.typography.headlineMedium,
            textColor = MaterialTheme.colorScheme.onSurface,
            wordDelayMs = 200,
            onComplete = {
                println("Fade in complete!")
            }
        )

        FadeInText(
            text = "Ancient wisdom reveals itself",
            gradient = SpiritualGradients.mysticGold,
            style = MaterialTheme.typography.headlineSmall,
            wordDelayMs = 150
        )

        FadeInText(
            text = "Quick fade in effect demonstration",
            gradient = SpiritualGradients.aurora,
            style = MaterialTheme.typography.titleLarge,
            wordDelayMs = 100
        )

        // Section: Cosmic Text
        SectionHeader("Cosmic Text Effects")

        CosmicText(
            text = "Starfield Magic",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            starCount = 80,
            animationSpeed = 0.5f
        )

        CosmicText(
            text = "Celestial Beauty",
            style = MaterialTheme.typography.headlineLarge,
            starCount = 60,
            animationSpeed = 0.8f
        )

        CosmicText(
            text = "Cosmic Energy",
            style = MaterialTheme.typography.headlineMedium,
            starCount = 50,
            animationSpeed = 1.0f
        )

        // Section: Mixed Gradient Presets
        SectionHeader("All Gradient Presets")

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PresetExample("Cosmic Purple", SpiritualGradients.cosmicPurple)
            PresetExample("Mystic Gold", SpiritualGradients.mysticGold)
            PresetExample("Chakra Rainbow", SpiritualGradients.chakraRainbow)
            PresetExample("Twilight", SpiritualGradients.twilight)
            PresetExample("Aurora", SpiritualGradients.aurora)
            PresetExample("Fire Energy", SpiritualGradients.fireEnergy)
            PresetExample("Water Flow", SpiritualGradients.waterFlow)
            PresetExample("Celestial Silver", SpiritualGradients.celestialSilver)
            PresetExample("Tantric Passion", SpiritualGradients.tantricPassion)
            PresetExample("Divine Light", SpiritualGradients.divineLight)
        }

        // Usage Examples Section
        SectionHeader("Practical Usage Examples")

        UsageExampleCard(
            title = "Hero Title",
            description = "Perfect for main headings and hero sections"
        ) {
            AnimatedGradientText(
                text = "Transform Your Spirit",
                gradient = SpiritualGradients.cosmicPurple,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                animationDuration = 4000
            )
        }

        UsageExampleCard(
            title = "Section Header",
            description = "Eye-catching section titles"
        ) {
            GradientText(
                text = "Meditation Practices",
                gradient = SpiritualGradients.twilight,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        UsageExampleCard(
            title = "Call to Action",
            description = "Animated CTAs that draw attention"
        ) {
            ShimmerText(
                text = "Begin Your Journey Today",
                baseColors = SpiritualGradients.fireEnergy,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        UsageExampleCard(
            title = "Loading State",
            description = "Engaging loading messages"
        ) {
            TypewriterText(
                text = "Analyzing your spiritual profile...",
                gradient = SpiritualGradients.aurora,
                style = MaterialTheme.typography.titleLarge,
                characterDelayMs = 50
            )
        }

        UsageExampleCard(
            title = "Welcome Message",
            description = "Warm, animated welcomes"
        ) {
            FadeInText(
                text = "Welcome back, seeker of truth",
                gradient = SpiritualGradients.divineLight,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionHeader(title: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Box(
            modifier = Modifier
                .height(2.dp)
                .width(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun PresetExample(name: String, gradient: List<Color>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        GradientText(
            text = "Sample Text",
            gradient = gradient,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
private fun UsageExampleCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    ModernCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                content()
            }
        }
    }
}

/**
 * Standalone preview screen for gradient text components
 */
@Composable
fun GradientTextDemoScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        GradientTextShowcase()
    }
}

/**
 * Quick examples for common use cases
 */
object GradientTextUsageExamples {

    // Hero title example
    @Composable
    fun HeroTitle() {
        AnimatedGradientText(
            text = "Discover Your Spiritual Path",
            gradient = SpiritualGradients.cosmicPurple,
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            animationDuration = 3000
        )
    }

    // Feature section header
    @Composable
    fun FeatureHeader() {
        GradientText(
            text = "Sacred Features",
            gradient = SpiritualGradients.mysticGold,
            style = MaterialTheme.typography.headlineLarge
        )
    }

    // Loading message
    @Composable
    fun LoadingMessage() {
        TypewriterText(
            text = "Connecting to universal energy...",
            gradient = SpiritualGradients.aurora,
            characterDelayMs = 60,
            showCursor = true
        )
    }

    // Welcome back message
    @Composable
    fun WelcomeMessage(userName: String) {
        FadeInText(
            text = "Welcome back, $userName",
            gradient = SpiritualGradients.divineLight,
            style = MaterialTheme.typography.headlineMedium,
            wordDelayMs = 150
        )
    }

    // Call to action
    @Composable
    fun CallToAction() {
        ShimmerText(
            text = "Start Your Journey",
            baseColors = SpiritualGradients.fireEnergy,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            shimmerDuration = 2000
        )
    }

    // Mystical quote
    @Composable
    fun MysticalQuote() {
        GlowingText(
            text = "The universe within you",
            textColor = Color.White,
            glowColor = SpiritualGradients.cosmicPurple[0],
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            ),
            animateGlow = true
        )
    }

    // Cosmic branding
    @Composable
    fun AppLogo() {
        CosmicText(
            text = "SpiritAtlas",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            starCount = 100,
            animationSpeed = 0.5f
        )
    }
}
