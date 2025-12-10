package com.spiritatlas.app

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.spiritatlas.app.navigation.Screen
import com.spiritatlas.core.ui.R
import com.spiritatlas.core.ui.components.SimpleSpiritualBackground
import com.spiritatlas.core.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Beautiful animated splash screen for SpiritAtlas
 *
 * Animation sequence (2.5 seconds total):
 * 1. 0-500ms: Stars fade in and twinkle
 * 2. 500-1000ms: Logo scales up from 0.5 to 1.0 with overshoot
 * 3. 1000-1500ms: Sacred geometry circles draw around logo
 * 4. 1500-2000ms: App name types in with gradient
 * 5. 2000-2500ms: Tagline fades in, then whole screen fades to main
 */
@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val animationProgress by viewModel.animationProgress.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()

    // Handle navigation events
    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            when (event) {
                is NavigationEvent.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
                is NavigationEvent.NavigateToOnboarding -> {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
            viewModel.onNavigationHandled()
        }
    }

    // Use splash screen background image
    SimpleSpiritualBackground(
        backgroundResourceId = R.drawable.img_003_splash_screen_background,
        alpha = 0.4f
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Background stars (0-500ms)
            StarField(
                modifier = Modifier.fillMaxSize(),
                visible = animationProgress >= 0f
            )

            // Main content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(280.dp)
                ) {
                    // Sacred geometry circles (1000-1500ms)
                    SacredGeometryCircles(
                        modifier = Modifier.fillMaxSize(),
                        progress = ((animationProgress - 1000f) / 500f).coerceIn(0f, 1f)
                    )

                    // Logo with scale animation (500-1000ms)
                    SpiritAtlasLogo(
                        modifier = Modifier.size(180.dp),
                        progress = ((animationProgress - 500f) / 500f).coerceIn(0f, 1f)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // App name with gradient (1500-2000ms)
                GradientAppName(
                    progress = ((animationProgress - 1500f) / 500f).coerceIn(0f, 1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tagline (2000-2500ms)
                TaglineText(
                    alpha = ((animationProgress - 2000f) / 500f).coerceIn(0f, 1f)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // Screen fade out (2500ms+)
            if (animationProgress >= 2500f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = ((animationProgress - 2500f) / 300f).coerceIn(0f, 1f)))
                )
            }
        }
    }
}

/**
 * Animated star field background
 */
@Composable
private fun StarField(
    modifier: Modifier = Modifier,
    visible: Boolean,
    starCount: Int = 50
) {
    val stars = remember {
        List(starCount) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 2f + 1f,
                twinkleSpeed = Random.nextFloat() * 2f + 1f,
                twinkleOffset = Random.nextFloat() * 2 * PI.toFloat()
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "starTime"
    )

    val fadeAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(500),
        label = "starFade"
    )

    Canvas(modifier = modifier.alpha(fadeAlpha)) {
        stars.forEach { star ->
            val twinkle = 0.3f + 0.7f * (sin(time * star.twinkleSpeed + star.twinkleOffset) * 0.5f + 0.5f)
            val alpha = twinkle * fadeAlpha

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = star.size,
                center = Offset(
                    x = size.width * star.x,
                    y = size.height * star.y
                )
            )
        }
    }
}

/**
 * Sacred geometry circles that draw around the logo
 */
@Composable
private fun SacredGeometryCircles(
    modifier: Modifier = Modifier,
    progress: Float
) {
    val rotation by rememberInfiniteTransition(label = "geometry").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "geometryRotation"
    )

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)

        // Outer circle with sacred geometry pattern
        drawCircle(
            color = SpiritualPurple.copy(alpha = 0.3f * progress),
            radius = size.minDimension / 2f * progress,
            center = center,
            style = Stroke(width = 2f)
        )

        // Middle circle
        drawCircle(
            color = MysticViolet.copy(alpha = 0.4f * progress),
            radius = size.minDimension / 2.5f * progress,
            center = center,
            style = Stroke(width = 2f)
        )

        // Inner circle
        drawCircle(
            color = CosmicBlue.copy(alpha = 0.5f * progress),
            radius = size.minDimension / 3.5f * progress,
            center = center,
            style = Stroke(width = 2f)
        )

        // Rotating sacred geometry petals
        if (progress > 0.5f) {
            rotate(rotation, pivot = center) {
                val petalProgress = (progress - 0.5f) * 2f
                for (i in 0 until 6) {
                    val angle = (i * 60f) * PI.toFloat() / 180f
                    val petalRadius = size.minDimension / 4f
                    val petalCenter = Offset(
                        center.x + cos(angle) * petalRadius * petalProgress,
                        center.y + sin(angle) * petalRadius * petalProgress
                    )

                    drawCircle(
                        color = AuraGold.copy(alpha = 0.2f * petalProgress),
                        radius = petalRadius * 0.6f,
                        center = petalCenter,
                        style = Stroke(width = 1.5f)
                    )
                }
            }
        }
    }
}

/**
 * SpiritAtlas logo with animation
 * Features: Stylized atlas/globe with zodiac symbols, glowing aura, particle trails
 */
@Composable
private fun SpiritAtlasLogo(
    modifier: Modifier = Modifier,
    progress: Float
) {
    // Scale with overshoot effect
    val scale by animateFloatAsState(
        targetValue = if (progress > 0f) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "logoScale"
    )

    val rotation by rememberInfiniteTransition(label = "logo").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "logoRotation"
    )

    Box(
        modifier = modifier.scale(scale),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2.5f

            // Glowing aura
            for (i in 1..3) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SpiritualPurple.copy(alpha = 0.3f / i),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius * (1.2f + i * 0.2f)
                    ),
                    radius = radius * (1.2f + i * 0.2f),
                    center = center
                )
            }

            // Main globe with gradient
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CosmicBlue.copy(alpha = 0.8f),
                        SpiritualPurple.copy(alpha = 0.9f),
                        MysticViolet
                    ),
                    center = center
                ),
                radius = radius,
                center = center
            )

            // Globe outline
            drawCircle(
                color = AuraGold.copy(alpha = 0.6f),
                radius = radius,
                center = center,
                style = Stroke(width = 3f)
            )

            // Rotating zodiac symbols ring
            rotate(rotation, pivot = center) {
                val symbolRadius = radius * 0.75f
                for (i in 0 until 12) {
                    val angle = (i * 30f) * PI.toFloat() / 180f
                    val x = center.x + cos(angle) * symbolRadius
                    val y = center.y + sin(angle) * symbolRadius

                    drawCircle(
                        color = AuraGold.copy(alpha = 0.5f),
                        radius = 3f,
                        center = Offset(x, y)
                    )
                }
            }

            // Center star
            drawPath(
                path = createStarPath(center, radius * 0.3f),
                color = Color.White.copy(alpha = 0.9f)
            )

            // Particle trails
            rotate(rotation * 0.5f, pivot = center) {
                for (i in 0 until 8) {
                    val angle = (i * 45f) * PI.toFloat() / 180f
                    val trailRadius = radius * 1.3f

                    for (j in 0..3) {
                        val distance = trailRadius + j * 5f
                        drawCircle(
                            color = SpiritualPurple.copy(alpha = 0.3f * (1 - j * 0.25f)),
                            radius = 2f - j * 0.4f,
                            center = Offset(
                                center.x + cos(angle) * distance,
                                center.y + sin(angle) * distance
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * App name with gradient text reveal
 */
@Composable
private fun GradientAppName(
    progress: Float
) {
    val visibleChars = (progress * "SpiritAtlas".length).toInt()
    val displayText = "SpiritAtlas".take(visibleChars)

    Text(
        text = displayText,
        fontSize = 42.sp,
        fontWeight = FontWeight.Bold,
        style = androidx.compose.ui.text.TextStyle(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    SpiritualPurple,
                    MysticViolet,
                    CosmicBlue,
                    AuraGold
                )
            )
        ),
        modifier = Modifier.alpha(progress)
    )
}

/**
 * Tagline with fade in
 */
@Composable
private fun TaglineText(
    alpha: Float
) {
    Text(
        text = "Discover Your Cosmic Blueprint",
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        color = Color.White.copy(alpha = 0.7f),
        modifier = Modifier.alpha(alpha)
    )
}

/**
 * Star data class
 */
private data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val twinkleSpeed: Float,
    val twinkleOffset: Float
)

/**
 * Create a star path
 */
private fun createStarPath(center: Offset, radius: Float, points: Int = 5): Path {
    val path = Path()
    val angleStep = PI.toFloat() / points

    for (i in 0 until points * 2) {
        val angle = i * angleStep - PI.toFloat() / 2
        val r = if (i % 2 == 0) radius else radius * 0.4f
        val x = center.x + r * cos(angle)
        val y = center.y + r * sin(angle)

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    return path
}
