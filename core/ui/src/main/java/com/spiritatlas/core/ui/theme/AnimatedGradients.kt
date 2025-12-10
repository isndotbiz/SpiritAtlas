package com.spiritatlas.core.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Animated Gradient System for SpiritAtlas
 *
 * Three main animation types:
 * 1. Flowing Gradients - Animated offset (scrolling effect)
 * 2. Breathing Gradients - Pulsing colors (expansion/contraction)
 * 3. Rotating Gradients - Angle animation (spinning effect)
 *
 * Each animation can be customized with duration and easing curves
 * to create the perfect spiritual aesthetic for any UI element.
 */
object AnimatedGradients {

    // ============================================================================
    // FLOWING GRADIENTS - Animated offset creates scrolling effect
    // ============================================================================

    /**
     * Horizontal flowing gradient - scrolls left to right
     *
     * @param colors List of colors in the gradient
     * @param durationMillis Duration of one complete cycle (default 3000ms)
     * @param easing Animation easing function (default Linear)
     * @return Animated gradient brush
     */
    @Composable
    fun flowingHorizontal(
        colors: List<Color>,
        durationMillis: Int = 3000,
        easing: Easing = LinearEasing
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "flowing_horizontal")
        val offset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = easing),
                repeatMode = RepeatMode.Restart
            ),
            label = "horizontal_offset"
        )

        // Create extended color list for seamless looping
        val extendedColors = colors + colors

        return Brush.horizontalGradient(
            colors = extendedColors,
            startX = -1000f * offset,
            endX = 1000f * (1 - offset),
            tileMode = TileMode.Mirror
        )
    }

    /**
     * Vertical flowing gradient - scrolls top to bottom
     */
    @Composable
    fun flowingVertical(
        colors: List<Color>,
        durationMillis: Int = 3000,
        easing: Easing = LinearEasing
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "flowing_vertical")
        val offset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = easing),
                repeatMode = RepeatMode.Restart
            ),
            label = "vertical_offset"
        )

        val extendedColors = colors + colors

        return Brush.verticalGradient(
            colors = extendedColors,
            startY = -1000f * offset,
            endY = 1000f * (1 - offset),
            tileMode = TileMode.Mirror
        )
    }

    /**
     * Diagonal flowing gradient - scrolls diagonally
     */
    @Composable
    fun flowingDiagonal(
        colors: List<Color>,
        durationMillis: Int = 3000,
        easing: Easing = LinearEasing
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "flowing_diagonal")
        val offset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = easing),
                repeatMode = RepeatMode.Restart
            ),
            label = "diagonal_offset"
        )

        val extendedColors = colors + colors

        return Brush.linearGradient(
            colors = extendedColors,
            start = Offset(-1000f * offset, -1000f * offset),
            end = Offset(1000f * (1 - offset), 1000f * (1 - offset)),
            tileMode = TileMode.Mirror
        )
    }

    /**
     * Radial flowing gradient - pulses from center outward
     */
    @Composable
    fun flowingRadial(
        colors: List<Color>,
        durationMillis: Int = 3000,
        easing: Easing = FastOutSlowInEasing
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "flowing_radial")
        val radius by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = easing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "radial_radius"
        )

        return Brush.radialGradient(
            colors = colors,
            radius = radius * 1000f,
            tileMode = TileMode.Mirror
        )
    }

    // ============================================================================
    // BREATHING GRADIENTS - Pulsing colors (expansion/contraction)
    // ============================================================================

    /**
     * Breathing gradient - colors pulse in and out
     *
     * Creates a meditative, calming effect like breathing
     *
     * @param colors List of colors to breathe between
     * @param durationMillis Duration of one breath cycle (default 4000ms)
     * @param minAlpha Minimum alpha during breathe out (default 0.5f)
     * @param maxAlpha Maximum alpha during breathe in (default 1.0f)
     */
    @Composable
    fun breathing(
        colors: List<Color>,
        durationMillis: Int = 4000,
        minAlpha: Float = 0.5f,
        maxAlpha: Float = 1.0f,
        direction: GradientDirection = GradientDirection.Vertical
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "breathing")
        val alpha by infiniteTransition.animateFloat(
            initialValue = minAlpha,
            targetValue = maxAlpha,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "breathing_alpha"
        )

        val breathingColors = colors.map { it.copy(alpha = alpha) }

        return when (direction) {
            GradientDirection.Horizontal -> Brush.horizontalGradient(breathingColors)
            GradientDirection.Vertical -> Brush.verticalGradient(breathingColors)
            GradientDirection.Diagonal -> Brush.linearGradient(
                colors = breathingColors,
                start = Offset.Zero,
                end = Offset(1000f, 1000f)
            )
            GradientDirection.Radial -> Brush.radialGradient(breathingColors)
        }
    }

    /**
     * Color morphing gradient - cycles through color list
     *
     * Smoothly transitions between multiple colors in sequence
     */
    @Composable
    fun colorMorphing(
        colors: List<Color>,
        durationMillis: Int = 5000,
        direction: GradientDirection = GradientDirection.Vertical
    ): Brush {
        require(colors.size >= 2) { "Need at least 2 colors for morphing" }

        val infiniteTransition = rememberInfiniteTransition(label = "color_morphing")
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = colors.size.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis * colors.size, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "morph_progress"
        )

        // Calculate current color index and blend ratio
        val currentIndex = progress.toInt() % colors.size
        val nextIndex = (currentIndex + 1) % colors.size
        val blendRatio = progress - progress.toInt()

        // Blend between current and next color
        val currentColor = colors[currentIndex]
        val nextColor = colors[nextIndex]
        val blendedColor = currentColor.blend(nextColor, blendRatio)

        // Create gradient with blended colors
        val gradientColors = listOf(
            blendedColor,
            blendedColor.blend(colors[(nextIndex + 1) % colors.size], 0.5f),
            colors[(nextIndex + 1) % colors.size]
        )

        return when (direction) {
            GradientDirection.Horizontal -> Brush.horizontalGradient(gradientColors)
            GradientDirection.Vertical -> Brush.verticalGradient(gradientColors)
            GradientDirection.Diagonal -> Brush.linearGradient(
                colors = gradientColors,
                start = Offset.Zero,
                end = Offset(1000f, 1000f)
            )
            GradientDirection.Radial -> Brush.radialGradient(gradientColors)
        }
    }

    /**
     * Shimmer breathing - combines breathing with shimmer highlight
     */
    @Composable
    fun shimmerBreathing(
        baseColors: List<Color>,
        highlightColor: Color = Color.White,
        durationMillis: Int = 3000
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "shimmer_breathing")

        val breatheAlpha by infiniteTransition.animateFloat(
            initialValue = 0.6f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "breathe_alpha"
        )

        val shimmerOffset by infiniteTransition.animateFloat(
            initialValue = -1f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_offset"
        )

        val breathingColors = baseColors.map { it.copy(alpha = breatheAlpha) }

        // Insert shimmer highlight
        val colors = mutableListOf<Color>()
        colors.addAll(breathingColors)
        if (shimmerOffset in 0f..1f) {
            val insertIndex = (shimmerOffset * (breathingColors.size - 1)).toInt()
            colors.add(insertIndex, highlightColor.copy(alpha = 0.3f * breatheAlpha))
        }

        return Brush.horizontalGradient(colors)
    }

    // ============================================================================
    // ROTATING GRADIENTS - Angle animation (spinning effect)
    // ============================================================================

    /**
     * Rotating gradient - spins around center point
     *
     * @param colors List of colors in the gradient
     * @param durationMillis Duration of one complete rotation (default 5000ms)
     * @param clockwise Rotation direction (default true)
     */
    @Composable
    fun rotating(
        colors: List<Color>,
        durationMillis: Int = 5000,
        clockwise: Boolean = true,
        centerX: Float = 500f,
        centerY: Float = 500f
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "rotating")
        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = if (clockwise) 360f else -360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation_angle"
        )

        // Convert angle to radians and calculate gradient end point
        val angleRad = angle * PI.toFloat() / 180f
        val radius = 1000f

        val startX = centerX - cos(angleRad) * radius
        val startY = centerY - sin(angleRad) * radius
        val endX = centerX + cos(angleRad) * radius
        val endY = centerY + sin(angleRad) * radius

        return Brush.linearGradient(
            colors = colors,
            start = Offset(startX, startY),
            end = Offset(endX, endY)
        )
    }

    /**
     * Spiral gradient - rotating with expanding radius
     */
    @Composable
    fun spiral(
        colors: List<Color>,
        rotationDurationMillis: Int = 5000,
        pulseDurationMillis: Int = 3000
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "spiral")

        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(rotationDurationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "spiral_angle"
        )

        val radius by infiniteTransition.animateFloat(
            initialValue = 500f,
            targetValue = 1500f,
            animationSpec = infiniteRepeatable(
                animation = tween(pulseDurationMillis, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "spiral_radius"
        )

        val angleRad = angle * PI.toFloat() / 180f
        val centerX = 500f
        val centerY = 500f

        return Brush.radialGradient(
            colors = colors,
            center = Offset(
                centerX + cos(angleRad) * (radius * 0.3f),
                centerY + sin(angleRad) * (radius * 0.3f)
            ),
            radius = radius
        )
    }

    /**
     * Wave gradient - oscillating linear gradient
     */
    @Composable
    fun wave(
        colors: List<Color>,
        durationMillis: Int = 4000,
        amplitude: Float = 500f
    ): Brush {
        val infiniteTransition = rememberInfiniteTransition(label = "wave")
        val phase by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2 * PI.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "wave_phase"
        )

        val centerX = 500f
        val centerY = 500f

        val startX = centerX
        val startY = centerY - amplitude * sin(phase)
        val endX = centerX
        val endY = centerY + amplitude * sin(phase)

        return Brush.linearGradient(
            colors = colors,
            start = Offset(startX, startY),
            end = Offset(endX, endY)
        )
    }

    // ============================================================================
    // PRESET ANIMATED GRADIENTS - Ready-to-use spiritual animations
    // ============================================================================

    /**
     * Aurora animation - flowing northern lights
     */
    @Composable
    fun aurora(): Brush {
        return flowingHorizontal(
            colors = listOf(
                Color(0xFF00FF87), // Bright aurora green
                Color(0xFF60EFFF), // Electric cyan
                AuroraPink,
                MysticPurple,
                ChakraIndigo
            ),
            durationMillis = 5000,
            easing = FastOutSlowInEasing
        )
    }

    /**
     * Chakra flow animation - energy centers flowing
     */
    @Composable
    fun chakraFlow(): Brush {
        return flowingVertical(
            colors = listOf(
                ChakraRed,
                ChakraOrange,
                ChakraYellow,
                ChakraGreen,
                ChakraBlue,
                ChakraIndigo,
                ChakraCrown
            ),
            durationMillis = 7000,
            easing = LinearEasing
        )
    }

    /**
     * Cosmic pulse animation - breathing universe
     */
    @Composable
    fun cosmicPulse(): Brush {
        return breathing(
            colors = listOf(
                NightSky,
                CosmicViolet,
                MysticPurple,
                SpiritualPurple
            ),
            durationMillis = 5000,
            minAlpha = 0.7f,
            maxAlpha = 1.0f,
            direction = GradientDirection.Radial
        )
    }

    /**
     * Sacred geometry animation - rotating mandala
     */
    @Composable
    fun sacredGeometry(): Brush {
        return rotating(
            colors = listOf(
                SacredGold,
                AuraGold,
                SpiritualPurple,
                MysticViolet,
                CosmicViolet
            ),
            durationMillis = 8000,
            clockwise = true
        )
    }

    /**
     * Tantric energy animation - passionate flow
     */
    @Composable
    fun tantricEnergy(): Brush {
        return flowingDiagonal(
            colors = listOf(
                TantricRose,
                SensualCoral,
                IntimacyPurple,
                MysticPurple
            ),
            durationMillis = 4000,
            easing = FastOutSlowInEasing
        )
    }

    /**
     * Elemental cycle animation - earth, water, air, fire
     */
    @Composable
    fun elementalCycle(): Brush {
        return colorMorphing(
            colors = listOf(
                EarthGreen,  // Earth
                WaterTeal,   // Water
                AirCyan,     // Air
                FireOrange   // Fire
            ),
            durationMillis = 3000,
            direction = GradientDirection.Horizontal
        )
    }

    /**
     * Moonlight shimmer animation - gentle lunar glow
     */
    @Composable
    fun moonlightShimmer(): Brush {
        return shimmerBreathing(
            baseColors = listOf(
                NightSky,
                Color(0xFF334155),
                MoonlightSilver
            ),
            highlightColor = Color.White,
            durationMillis = 4000
        )
    }

    /**
     * Golden hour animation - sunrise/sunset flow
     */
    @Composable
    fun goldenHour(): Brush {
        return flowingHorizontal(
            colors = listOf(
                FireOrange,
                SacredGold,
                AuraGold,
                ChakraYellow,
                Color(0xFFFEF3C7)
            ),
            durationMillis = 6000,
            easing = FastOutSlowInEasing
        )
    }

    /**
     * Third eye activation - pulsing intuition
     */
    @Composable
    fun thirdEyeActivation(): Brush {
        return breathing(
            colors = listOf(
                Color(0xFFA5B4FC), // Light indigo
                ChakraIndigo,
                Color(0xFF4F46E5), // Deep indigo
                MysticPurple
            ),
            durationMillis = 3000,
            minAlpha = 0.6f,
            maxAlpha = 1.0f,
            direction = GradientDirection.Radial
        )
    }

    /**
     * Heart opening animation - expanding love
     */
    @Composable
    fun heartOpening(): Brush {
        return flowingRadial(
            colors = listOf(
                TantricRose.copy(alpha = 0.8f),
                IntimacyPurple.copy(alpha = 0.6f),
                ChakraGreen.copy(alpha = 0.7f),
                EarthGreen.copy(alpha = 0.5f)
            ),
            durationMillis = 5000,
            easing = FastOutSlowInEasing
        )
    }
}

// ============================================================================
// GRADIENT DIRECTION ENUM
// ============================================================================

enum class GradientDirection {
    Horizontal,
    Vertical,
    Diagonal,
    Radial
}
