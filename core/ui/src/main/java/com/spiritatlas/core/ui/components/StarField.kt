package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.spiritatlas.core.ui.theme.MoonlightSilver
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

/**
 * Star data for a single star in the field
 * @param x Normalized x position (0.0 to 1.0)
 * @param y Normalized y position (0.0 to 1.0)
 * @param size Base size of the star
 * @param twinklePhase Phase offset for twinkle animation
 * @param layer Parallax layer (0 = far, 1 = mid, 2 = near)
 */
private data class StarData(
    val x: Float,
    val y: Float,
    val size: Float,
    val twinklePhase: Float,
    val layer: Int
)

/**
 * Parallax layer configuration
 * @param speed Movement speed multiplier
 * @param starSize Size multiplier for stars in this layer
 * @param opacity Base opacity for stars in this layer
 * @param starCount Number of stars in this layer
 */
private data class ParallaxLayer(
    val speed: Float,
    val starSize: Float,
    val opacity: Float,
    val starCount: Int
)

/**
 * Animated star field background with parallax layers
 *
 * Creates a performance-optimized twinkling star field with three parallax layers:
 * - Far layer (slow, small, dim stars)
 * - Mid layer (medium speed, medium stars)
 * - Near layer (fast, large, bright stars)
 *
 * Performance optimizations:
 * - Pre-generated star positions (computed once)
 * - Single Canvas composable (no overdraw)
 * - Efficient math operations (sin lookup)
 * - Minimal allocations per frame
 * - Targets 60 FPS on most devices
 *
 * @param modifier Modifier for the canvas
 * @param starCount Total number of stars across all layers (distributed based on layer config)
 * @param twinkleSpeed Speed multiplier for twinkle animation (higher = faster)
 * @param color Base color for the stars
 * @param enableParallax Enable parallax movement effect (set to false for static field)
 */
@Composable
fun StarField(
    modifier: Modifier = Modifier,
    starCount: Int = 100,
    twinkleSpeed: Float = 2f,
    color: Color = MoonlightSilver,
    enableParallax: Boolean = true
) {
    // Define three parallax layers with different characteristics
    val parallaxLayers = remember {
        listOf(
            ParallaxLayer(
                speed = 0.3f,      // Far layer: slow
                starSize = 0.8f,   // Small stars
                opacity = 0.4f,    // Dim
                starCount = (starCount * 0.5f).toInt() // 50% of stars
            ),
            ParallaxLayer(
                speed = 0.6f,      // Mid layer: medium
                starSize = 1.0f,   // Medium stars
                opacity = 0.6f,    // Medium brightness
                starCount = (starCount * 0.3f).toInt() // 30% of stars
            ),
            ParallaxLayer(
                speed = 1.0f,      // Near layer: fast
                starSize = 1.3f,   // Large stars
                opacity = 0.8f,    // Bright
                starCount = (starCount * 0.2f).toInt() // 20% of stars
            )
        )
    }

    // Generate stars once and remember them
    val stars = remember(starCount) {
        buildList {
            parallaxLayers.forEachIndexed { layerIndex, layer ->
                repeat(layer.starCount) {
                    add(
                        StarData(
                            x = Random.nextFloat(),
                            y = Random.nextFloat(),
                            size = (Random.nextFloat() * 1.5f + 0.5f) * layer.starSize,
                            twinklePhase = Random.nextFloat() * 2 * PI.toFloat(),
                            layer = layerIndex
                        )
                    )
                }
            }
        }
    }

    // Infinite animation for twinkle and parallax movement
    val infiniteTransition = rememberInfiniteTransition(label = "starField")

    // Time value for twinkle animation
    val twinkleTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (3000 / twinkleSpeed).toInt(),
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "twinkleTime"
    )

    // Time value for parallax movement (slower cycle)
    val parallaxTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 20000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "parallaxTime"
    )

    Canvas(modifier = modifier) {
        stars.forEach { star ->
            val layer = parallaxLayers[star.layer]

            // Calculate twinkle alpha (0.3 to 1.0 range for subtle effect)
            val twinkleAlpha = 0.3f + 0.7f * (sin(twinkleTime * twinkleSpeed + star.twinklePhase) * 0.5f + 0.5f)
            val finalAlpha = twinkleAlpha * layer.opacity

            // Calculate parallax offset if enabled
            val parallaxOffset = if (enableParallax) {
                val offsetAmount = parallaxTime * layer.speed * 0.1f // Subtle movement
                Offset(
                    x = sin(parallaxTime * 2 * PI.toFloat() * layer.speed) * offsetAmount,
                    y = 0f // Only horizontal parallax to avoid vertical scrolling issues
                )
            } else {
                Offset.Zero
            }

            // Calculate final star position
            val starX = size.width * star.x + parallaxOffset.x * size.width
            val starY = size.height * star.y + parallaxOffset.y * size.height

            // Wrap around screen edges for seamless parallax
            val wrappedX = when {
                starX < 0 -> starX + size.width
                starX > size.width -> starX - size.width
                else -> starX
            }

            drawStar(
                center = Offset(wrappedX, starY),
                size = star.size,
                color = color.copy(alpha = finalAlpha)
            )
        }
    }
}

/**
 * Draw a single star with a subtle glow effect
 * Optimized to minimize draw calls
 */
private fun DrawScope.drawStar(
    center: Offset,
    size: Float,
    color: Color
) {
    // Outer glow (subtle, larger radius)
    drawCircle(
        color = color.copy(alpha = color.alpha * 0.2f),
        radius = size * 2f,
        center = center
    )

    // Main star body
    drawCircle(
        color = color,
        radius = size,
        center = center
    )

    // Bright center point (adds sparkle)
    drawCircle(
        color = Color.White.copy(alpha = color.alpha * 0.8f),
        radius = size * 0.4f,
        center = center
    )
}
