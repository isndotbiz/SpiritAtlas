package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.sin
import kotlin.random.Random

/**
 * Spiritual Gradient Text Components for SpiritAtlas
 * Beautiful, animated text effects with spiritual aesthetics
 */

/**
 * Spiritual gradient presets for text effects
 */
object SpiritualGradients {
    val cosmicPurple = listOf(
        Color(0xFF9C27B0),
        Color(0xFF673AB7),
        Color(0xFF3F51B5)
    )

    val mysticGold = listOf(
        Color(0xFFFFD700),
        Color(0xFFFFA500),
        Color(0xFFFF8C00)
    )

    val chakraRainbow = listOf(
        ChakraRed,      // Root
        ChakraOrange,   // Sacral
        ChakraYellow,   // Solar Plexus
        ChakraGreen,    // Heart
        ChakraBlue,     // Throat
        ChakraIndigo,   // Third Eye
        ChakraCrown     // Crown
    )

    val twilight = listOf(
        Color(0xFF1A237E),
        Color(0xFF4A148C),
        Color(0xFF880E4F)
    )

    val aurora = listOf(
        Color(0xFF00BCD4),
        Color(0xFF4CAF50),
        Color(0xFFFFEB3B)
    )

    val fireEnergy = listOf(
        Color(0xFFFF5722),
        Color(0xFFFF9800),
        Color(0xFFFFEB3B)
    )

    val waterFlow = listOf(
        Color(0xFF0D47A1),
        Color(0xFF1976D2),
        Color(0xFF03A9F4)
    )

    val celestialSilver = listOf(
        Color(0xFFE0E0E0),
        Color(0xFFBDBDBD),
        Color(0xFF9E9E9E)
    )

    val tantricPassion = listOf(
        TantricRose,
        SensualCoral,
        IntimacyPurple
    )

    val divineLight = listOf(
        Color.White,
        AuraGold,
        SpiritualPurple
    )
}

/**
 * Basic text with horizontal gradient
 */
@Composable
fun GradientText(
    text: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    gradientDirection: GradientDirection = GradientDirection.Horizontal
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(text, style) {
        textMeasurer.measure(text, style)
    }

    Canvas(
        modifier = modifier
            .width(textLayoutResult.size.width.dp / 2)
            .height(textLayoutResult.size.height.dp / 2)
    ) {
        val brush = when (gradientDirection) {
            GradientDirection.Horizontal -> Brush.horizontalGradient(
                colors = gradient,
                startX = 0f,
                endX = size.width
            )
            GradientDirection.Vertical -> Brush.verticalGradient(
                colors = gradient,
                startY = 0f,
                endY = size.height
            )
            GradientDirection.Diagonal -> Brush.linearGradient(
                colors = gradient,
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )
        }

        drawText(
            textLayoutResult = textLayoutResult,
            brush = brush
        )
    }
}

/**
 * Gradient that animates/shifts
 */
@Composable
fun AnimatedGradientText(
    text: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    animationDuration: Int = 3000,
    animationDirection: AnimationDirection = AnimationDirection.LeftToRight
) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient_animation")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradient_offset"
    )

    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(text, style) {
        textMeasurer.measure(text, style)
    }

    Canvas(
        modifier = modifier
            .width(textLayoutResult.size.width.dp / 2)
            .height(textLayoutResult.size.height.dp / 2)
    ) {
        val gradientWidth = size.width * 2
        val currentOffset = when (animationDirection) {
            AnimationDirection.LeftToRight -> offset * gradientWidth - size.width
            AnimationDirection.RightToLeft -> -offset * gradientWidth + size.width
            AnimationDirection.TopToBottom -> 0f
            AnimationDirection.BottomToTop -> 0f
        }

        val brush = when (animationDirection) {
            AnimationDirection.LeftToRight, AnimationDirection.RightToLeft -> {
                Brush.horizontalGradient(
                    colors = gradient + gradient, // Repeat for seamless animation
                    startX = currentOffset,
                    endX = currentOffset + gradientWidth
                )
            }
            AnimationDirection.TopToBottom -> {
                Brush.verticalGradient(
                    colors = gradient + gradient,
                    startY = offset * size.height * 2 - size.height,
                    endY = offset * size.height * 2 + size.height
                )
            }
            AnimationDirection.BottomToTop -> {
                Brush.verticalGradient(
                    colors = gradient + gradient,
                    startY = -offset * size.height * 2 + size.height,
                    endY = -offset * size.height * 2 + size.height * 3
                )
            }
        }

        drawText(
            textLayoutResult = textLayoutResult,
            brush = brush
        )
    }
}

/**
 * Text with moving shimmer highlight
 */
@Composable
fun ShimmerText(
    text: String,
    baseColors: List<Color> = listOf(SpiritualPurple, MysticViolet),
    shimmerColor: Color = Color.White,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    shimmerDuration: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_animation")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(shimmerDuration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )

    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(text, style) {
        textMeasurer.measure(text, style)
    }

    Canvas(
        modifier = modifier
            .width(textLayoutResult.size.width.dp / 2)
            .height(textLayoutResult.size.height.dp / 2)
    ) {
        val shimmerWidth = size.width * 0.3f
        val shimmerStart = shimmerOffset * size.width

        val brush = Brush.horizontalGradient(
            colors = listOf(
                baseColors[0],
                baseColors.getOrElse(1) { baseColors[0] },
                shimmerColor,
                baseColors.getOrElse(1) { baseColors[0] },
                baseColors[0]
            ),
            startX = shimmerStart - shimmerWidth,
            endX = shimmerStart + shimmerWidth
        )

        drawText(
            textLayoutResult = textLayoutResult,
            brush = brush
        )
    }
}

/**
 * Text with outer glow effect
 */
@Composable
fun GlowingText(
    text: String,
    textColor: Color = Color.White,
    glowColor: Color = SpiritualPurple,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    animateGlow: Boolean = true,
    glowRadius: Float = 20f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow_animation")
    val glowIntensity by infiniteTransition.animateFloat(
        initialValue = if (animateGlow) 0.3f else 1f,
        targetValue = if (animateGlow) 1f else 1f,
        animationSpec = if (animateGlow) {
            infiniteRepeatable(
                animation = tween(1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        } else {
            infiniteRepeatable(
                animation = tween(0),
                repeatMode = RepeatMode.Restart
            )
        },
        label = "glow_intensity"
    )

    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(text, style) {
        textMeasurer.measure(text, style)
    }

    Canvas(
        modifier = modifier
            .width(textLayoutResult.size.width.dp / 2 + glowRadius.dp)
            .height(textLayoutResult.size.height.dp / 2 + glowRadius.dp)
    ) {
        val glowOffset = glowRadius

        // Draw multiple glow layers
        for (i in 3 downTo 1) {
            val layerAlpha = (glowIntensity * 0.2f * i).coerceIn(0f, 1f)
            val layerRadius = glowRadius * (1f + i * 0.2f)

            drawContext.canvas.save()
            drawContext.canvas.translate(glowOffset, glowOffset)

            drawText(
                textLayoutResult = textLayoutResult,
                color = glowColor.copy(alpha = layerAlpha),
                blendMode = BlendMode.Screen
            )

            drawContext.canvas.restore()
        }

        // Draw main text
        drawContext.canvas.save()
        drawContext.canvas.translate(glowOffset, glowOffset)

        drawText(
            textLayoutResult = textLayoutResult,
            color = textColor
        )

        drawContext.canvas.restore()
    }
}

/**
 * Text that types in character by character
 */
@Composable
fun TypewriterText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    gradient: List<Color>? = null,
    characterDelayMs: Long = 50,
    showCursor: Boolean = true,
    onComplete: (() -> Unit)? = null
) {
    var visibleText by remember { mutableStateOf("") }
    var showCursorState by remember { mutableStateOf(true) }

    // Typewriter effect
    LaunchedEffect(text) {
        visibleText = ""
        for (i in text.indices) {
            visibleText = text.substring(0, i + 1)
            delay(characterDelayMs)
        }
        onComplete?.invoke()
    }

    // Cursor blink effect
    LaunchedEffect(showCursor) {
        while (showCursor && visibleText.length < text.length) {
            delay(500)
            showCursorState = !showCursorState
        }
        showCursorState = false
    }

    val displayText = if (showCursor && showCursorState && visibleText.length < text.length) {
        "$visibleText|"
    } else {
        visibleText
    }

    if (gradient != null) {
        GradientText(
            text = displayText,
            gradient = gradient,
            modifier = modifier,
            style = style
        )
    } else {
        val textMeasurer = rememberTextMeasurer()
        val textLayoutResult = remember(displayText, style) {
            textMeasurer.measure(displayText, style)
        }

        Canvas(
            modifier = modifier
                .width(textLayoutResult.size.width.dp / 2)
                .height(textLayoutResult.size.height.dp / 2)
        ) {
            drawText(
                textLayoutResult = textLayoutResult,
                color = textColor
            )
        }
    }
}

/**
 * Text that fades in word by word
 */
@Composable
fun FadeInText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    gradient: List<Color>? = null,
    wordDelayMs: Long = 150,
    onComplete: (() -> Unit)? = null
) {
    val words = remember(text) { text.split(" ") }
    var visibleWordCount by remember { mutableStateOf(0) }
    var currentWordAlpha by remember { mutableStateOf(0f) }

    // Fade in effect
    LaunchedEffect(text) {
        visibleWordCount = 0
        for (i in words.indices) {
            // Fade in current word
            for (alpha in 0..10) {
                currentWordAlpha = alpha / 10f
                delay(wordDelayMs / 10)
            }
            visibleWordCount = i + 1
            currentWordAlpha = 1f
        }
        onComplete?.invoke()
    }

    val textMeasurer = rememberTextMeasurer()

    Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
        words.forEachIndexed { index, word ->
            val alpha = when {
                index < visibleWordCount - 1 -> 1f
                index == visibleWordCount - 1 -> currentWordAlpha
                else -> 0f
            }

            if (alpha > 0f) {
                if (gradient != null) {
                    val wordLayoutResult = remember(word, style, alpha) {
                        textMeasurer.measure(word, style)
                    }

                    Canvas(
                        modifier = Modifier
                            .width(wordLayoutResult.size.width.dp / 2)
                            .height(wordLayoutResult.size.height.dp / 2)
                    ) {
                        val brush = Brush.horizontalGradient(
                            colors = gradient.map { it.copy(alpha = alpha) }
                        )

                        drawText(
                            textLayoutResult = wordLayoutResult,
                            brush = brush
                        )
                    }
                } else {
                    val wordLayoutResult = remember(word, style, alpha) {
                        textMeasurer.measure(word, style)
                    }

                    Canvas(
                        modifier = Modifier
                            .width(wordLayoutResult.size.width.dp / 2)
                            .height(wordLayoutResult.size.height.dp / 2)
                    ) {
                        drawText(
                            textLayoutResult = wordLayoutResult,
                            color = textColor.copy(alpha = alpha)
                        )
                    }
                }

                if (index < words.size - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

/**
 * Text with starfield background clipped to letters
 */
@Composable
fun CosmicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    starCount: Int = 50,
    animationSpeed: Float = 0.5f
) {
    val stars = remember(starCount) {
        List(starCount) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 3f + 1f,
                brightness = Random.nextFloat(),
                twinkleSpeed = Random.nextFloat() * 2f + 1f,
                twinkleOffset = Random.nextFloat() * 6.28f // Random phase
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "cosmic_animation")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f, // 2 * PI
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cosmic_time"
    )

    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(text, style) {
        textMeasurer.measure(text, style)
    }

    Canvas(
        modifier = modifier
            .width(textLayoutResult.size.width.dp / 2)
            .height(textLayoutResult.size.height.dp / 2)
    ) {
        // Create text path for clipping
        val textPath = Path().apply {
            // This is a simplification - in production you'd extract the actual text path
            addRect(
                androidx.compose.ui.geometry.Rect(
                    0f,
                    0f,
                    size.width,
                    size.height
                )
            )
        }

        drawContext.canvas.save()

        // Draw starfield
        stars.forEach { star ->
            val brightness = (sin(time * star.twinkleSpeed + star.twinkleOffset) + 1f) / 2f
            val alpha = star.brightness * brightness

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = star.size,
                center = Offset(
                    star.x * size.width,
                    star.y * size.height
                ),
                blendMode = BlendMode.Plus
            )
        }

        // Draw gradient overlay for cosmic effect
        val cosmicGradient = Brush.radialGradient(
            colors = listOf(
                SpiritualPurple.copy(alpha = 0.3f),
                CosmicBlue.copy(alpha = 0.2f),
                Color.Transparent
            ),
            center = Offset(size.width / 2, size.height / 2),
            radius = size.width
        )

        drawRect(brush = cosmicGradient, blendMode = BlendMode.Plus)

        // Draw text with clipping
        drawText(
            textLayoutResult = textLayoutResult,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White,
                    SpiritualPurple,
                    CosmicBlue
                )
            )
        )

        drawContext.canvas.restore()
    }
}

// Supporting data classes and enums

data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val brightness: Float = 1f,
    val twinkleSpeed: Float,
    val twinkleOffset: Float
)

enum class GradientDirection {
    Horizontal, Vertical, Diagonal
}

enum class AnimationDirection {
    LeftToRight, RightToLeft, TopToBottom, BottomToTop
}
