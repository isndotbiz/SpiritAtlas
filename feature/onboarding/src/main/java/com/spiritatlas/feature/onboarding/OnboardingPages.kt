package com.spiritatlas.feature.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val illustration: @Composable () -> Unit,
    val gradientColors: List<Color>
)

fun getOnboardingPages(): List<OnboardingPage> = listOf(
    OnboardingPage(
        title = "Discover Your Cosmic Blueprint",
        subtitle = "Unlock the mysteries of your spiritual journey through ancient wisdom and modern insight",
        illustration = { WelcomeIllustration() },
        gradientColors = listOf(SpiritualPurple, MysticViolet, CosmicBlue)
    ),
    OnboardingPage(
        title = "Four Sacred Systems",
        subtitle = "Explore Astrology, Numerology, Ayurveda, and Human Design in perfect harmony",
        illustration = { FourSystemsIllustration() },
        gradientColors = listOf(CosmicBlue, ChakraIndigo, SpiritualPurple)
    ),
    OnboardingPage(
        title = "Privacy First, Always",
        subtitle = "Your data stays on your device. You control which AI provider to use, if any",
        illustration = { PrivacyIllustration() },
        gradientColors = listOf(ChakraGreen, DeepTeal, CosmicBlue)
    ),
    OnboardingPage(
        title = "Couples Compatibility",
        subtitle = "Discover relationship dynamics and deepen your connection through cosmic alignment",
        illustration = { CouplesIllustration() },
        gradientColors = listOf(TantricRose, IntimacyPurple, SpiritualPurple)
    ),
    OnboardingPage(
        title = "Begin Your Journey",
        subtitle = "Create your first profile and unlock personalized insights from the cosmos",
        illustration = { GetStartedIllustration() },
        gradientColors = listOf(AuraGold, ChakraOrange, TantricRose)
    )
)

// Welcome Page - Animated stars and constellation
@Composable
fun WelcomeIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "welcome")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Generate stable random stars
    val stars = remember {
        List(30) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 3f + 1f,
                twinkleSpeed = Random.nextFloat() * 2f + 1f,
                twinkleOffset = Random.nextFloat() * 1000f
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.minDimension / 3

            // Draw constellation connections
            val points = listOf(
                Offset(centerX - radius * 0.5f, centerY - radius * 0.5f),
                Offset(centerX + radius * 0.5f, centerY - radius * 0.3f),
                Offset(centerX + radius * 0.3f, centerY + radius * 0.5f),
                Offset(centerX - radius * 0.3f, centerY + radius * 0.4f),
                Offset(centerX - radius * 0.5f, centerY)
            )

            // Draw constellation lines with glow
            for (i in 0 until points.size - 1) {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SpiritualPurple.copy(alpha = 0.3f),
                            CosmicBlue.copy(alpha = 0.5f)
                        )
                    ),
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 2f,
                    cap = StrokeCap.Round
                )
            }

            // Draw constellation stars
            points.forEach { point ->
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White,
                            SpiritualPurple.copy(alpha = 0.8f),
                            Color.Transparent
                        )
                    ),
                    center = point,
                    radius = 8f
                )
            }
        }

        // Twinkling stars overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            stars.forEach { star ->
                val x = star.x * size.width
                val y = star.y * size.height
                val time = (rotation + star.twinkleOffset) * star.twinkleSpeed
                val alpha = (sin(Math.toRadians(time.toDouble())).toFloat() + 1f) / 2f * 0.7f + 0.3f

                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    center = Offset(x, y),
                    radius = star.size
                )
            }
        }
    }
}

// Four Systems - Four quadrants with symbols
@Composable
fun FourSystemsIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "systems")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val quadrantSize = size.minDimension / 3

            // Draw four quadrants with different colors
            val quadrants = listOf(
                Triple(centerX - quadrantSize / 2, centerY - quadrantSize / 2, ChakraIndigo),
                Triple(centerX + quadrantSize / 2, centerY - quadrantSize / 2, CosmicBlue),
                Triple(centerX - quadrantSize / 2, centerY + quadrantSize / 2, ChakraGreen),
                Triple(centerX + quadrantSize / 2, centerY + quadrantSize / 2, IntimacyPurple)
            )

            quadrants.forEach { (x, y, color) ->
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = 0.6f),
                            color.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    ),
                    center = Offset(x, y),
                    radius = quadrantSize / 2 * scale
                )
            }

            // Draw connecting lines
            drawLine(
                color = SpiritualPurple.copy(alpha = 0.3f),
                start = Offset(centerX - quadrantSize / 2, centerY - quadrantSize / 2),
                end = Offset(centerX + quadrantSize / 2, centerY + quadrantSize / 2),
                strokeWidth = 2f
            )
            drawLine(
                color = SpiritualPurple.copy(alpha = 0.3f),
                start = Offset(centerX + quadrantSize / 2, centerY - quadrantSize / 2),
                end = Offset(centerX - quadrantSize / 2, centerY + quadrantSize / 2),
                strokeWidth = 2f
            )

            // Center glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        AuraGold.copy(alpha = 0.8f),
                        AuraGold.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                ),
                center = Offset(centerX, centerY),
                radius = 30f * scale
            )
        }

        // System labels
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SystemLabel("☆ Astrology")
                SystemLabel("∞ Numerology")
            }
            Spacer(modifier = Modifier.height(100.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SystemLabel("☯ Ayurveda")
                SystemLabel("◇ Human Design")
            }
        }
    }
}

@Composable
fun SystemLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = Color.White.copy(alpha = 0.9f),
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(8.dp)
    )
}

// Privacy - Shield with lock
@Composable
fun PrivacyIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "privacy")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val shieldWidth = size.width * 0.6f
            val shieldHeight = size.height * 0.7f

            // Draw shield shape with gradient
            val shieldPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(centerX, centerY - shieldHeight / 2)
                lineTo(centerX + shieldWidth / 2, centerY - shieldHeight / 3)
                lineTo(centerX + shieldWidth / 2, centerY + shieldHeight / 4)
                lineTo(centerX, centerY + shieldHeight / 2)
                lineTo(centerX - shieldWidth / 2, centerY + shieldHeight / 4)
                lineTo(centerX - shieldWidth / 2, centerY - shieldHeight / 3)
                close()
            }

            drawPath(
                path = shieldPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ChakraGreen.copy(alpha = 0.6f),
                        DeepTeal.copy(alpha = 0.4f)
                    )
                )
            )

            // Draw shield outline
            drawPath(
                path = shieldPath,
                color = ChakraGreen,
                style = Stroke(width = 3f)
            )

            // Draw lock icon in center
            val lockSize = 40f * pulse
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        Color.White.copy(alpha = 0.6f)
                    )
                ),
                center = Offset(centerX, centerY - 10f),
                radius = lockSize / 2
            )

            // Lock body
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        Color.White.copy(alpha = 0.7f)
                    )
                ),
                topLeft = Offset(centerX - lockSize / 3, centerY),
                size = androidx.compose.ui.geometry.Size(lockSize / 1.5f, lockSize / 1.5f)
            )
        }
    }
}

// Couples - Two orbiting elements
@Composable
fun CouplesIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "couples")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(250.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val orbitRadius = size.minDimension / 3

            // Draw connecting orbit path
            drawCircle(
                color = TantricRose.copy(alpha = 0.2f),
                center = Offset(centerX, centerY),
                radius = orbitRadius,
                style = Stroke(width = 2f)
            )

            // Calculate positions
            val angle1Rad = Math.toRadians(angle.toDouble())
            val angle2Rad = Math.toRadians((angle + 180).toDouble())

            val x1 = centerX + orbitRadius * cos(angle1Rad).toFloat()
            val y1 = centerY + orbitRadius * sin(angle1Rad).toFloat()
            val x2 = centerX + orbitRadius * cos(angle2Rad).toFloat()
            val y2 = centerY + orbitRadius * sin(angle2Rad).toFloat()

            // Draw connection line
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TantricRose.copy(alpha = 0.6f),
                        IntimacyPurple.copy(alpha = 0.6f)
                    ),
                    start = Offset(x1, y1),
                    end = Offset(x2, y2)
                ),
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Draw first orb (feminine)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        TantricRose,
                        TantricRose.copy(alpha = 0.7f),
                        TantricRose.copy(alpha = 0.3f)
                    )
                ),
                center = Offset(x1, y1),
                radius = 30f
            )

            // Draw second orb (masculine)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        IntimacyPurple,
                        IntimacyPurple.copy(alpha = 0.7f),
                        IntimacyPurple.copy(alpha = 0.3f)
                    )
                ),
                center = Offset(x2, y2),
                radius = 30f
            )

            // Draw center heart glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        AuraGold.copy(alpha = 0.6f),
                        AuraGold.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),
                center = Offset(centerX, centerY),
                radius = 40f
            )
        }
    }
}

// Get Started - Rising sun/mandala
@Composable
fun GetStartedIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "started")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(250.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw mandala rings
            for (i in 1..5) {
                val radius = (size.minDimension / 2) * (i / 5f) * scale
                val alpha = (6 - i) / 6f * 0.6f

                drawCircle(
                    color = AuraGold.copy(alpha = alpha),
                    center = Offset(centerX, centerY),
                    radius = radius,
                    style = Stroke(width = 2f)
                )
            }

            // Draw rays
            val numRays = 12
            val rayLength = size.minDimension / 3

            for (i in 0 until numRays) {
                val angle = Math.toRadians((rotation + i * (360f / numRays)).toDouble())
                val startRadius = size.minDimension / 8
                val endRadius = startRadius + rayLength

                val startX = centerX + startRadius * cos(angle).toFloat()
                val startY = centerY + startRadius * sin(angle).toFloat()
                val endX = centerX + endRadius * cos(angle).toFloat()
                val endY = centerY + endRadius * sin(angle).toFloat()

                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            AuraGold,
                            ChakraOrange.copy(alpha = 0.6f),
                            Color.Transparent
                        ),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY)
                    ),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
            }

            // Draw center sun
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        AuraGold,
                        ChakraOrange.copy(alpha = 0.8f),
                        Color.Transparent
                    )
                ),
                center = Offset(centerX, centerY),
                radius = size.minDimension / 8 * scale
            )
        }
    }
}

// Helper data class for stars
private data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val twinkleSpeed: Float,
    val twinkleOffset: Float
)
