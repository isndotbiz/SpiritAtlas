package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.*

/**
 * Progress indicators for SpiritAtlas
 *
 * Features:
 * 1. Linear progress with spiritual gradient
 * 2. Circular progress with mandala pattern
 * 3. Step indicators for multi-step flows
 * 4. Determinate vs indeterminate variants
 * 5. Chakra-based progress visualization
 *
 * All animations target 60 FPS performance.
 */

// ============================================================================
// LINEAR PROGRESS INDICATORS
// ============================================================================

/**
 * Spiritual linear progress indicator with gradient
 *
 * @param progress Current progress (0.0 to 1.0) - null for indeterminate
 * @param modifier Modifier for the indicator
 * @param gradient Gradient colors to use
 * @param trackColor Color of the track (background)
 * @param height Height of the progress bar
 */
@Composable
fun SpiritualLinearProgress(
    progress: Float?,
    modifier: Modifier = Modifier,
    gradient: List<Color> = SpiritualGradient,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
    height: Dp = 8.dp
) {
    if (progress != null) {
        // Determinate progress
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw track
            drawRoundRect(
                color = trackColor,
                size = Size(canvasWidth, canvasHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    canvasHeight / 2,
                    canvasHeight / 2
                )
            )

            // Draw progress
            if (progress > 0f) {
                val progressWidth = canvasWidth * progress.coerceIn(0f, 1f)
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = gradient,
                        startX = 0f,
                        endX = progressWidth
                    ),
                    size = Size(progressWidth, canvasHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                        canvasHeight / 2,
                        canvasHeight / 2
                    )
                )
            }
        }
    } else {
        // Indeterminate progress
        SpiritualLinearProgressIndeterminate(
            modifier = modifier,
            gradient = gradient,
            trackColor = trackColor,
            height = height
        )
    }
}

/**
 * Indeterminate linear progress with moving gradient
 */
@Composable
private fun SpiritualLinearProgressIndeterminate(
    modifier: Modifier = Modifier,
    gradient: List<Color>,
    trackColor: Color,
    height: Dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "linearProgress")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progressOffset"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Draw track
        drawRoundRect(
            color = trackColor,
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                canvasHeight / 2,
                canvasHeight / 2
            )
        )

        // Draw moving progress segment
        val segmentWidth = canvasWidth * 0.3f
        val segmentStart = (canvasWidth + segmentWidth) * offset - segmentWidth

        if (segmentStart < canvasWidth) {
            val clipStart = maxOf(0f, segmentStart)
            val clipEnd = minOf(canvasWidth, segmentStart + segmentWidth)
            val clipWidth = clipEnd - clipStart

            if (clipWidth > 0) {
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = gradient,
                        startX = segmentStart,
                        endX = segmentStart + segmentWidth
                    ),
                    topLeft = Offset(clipStart, 0f),
                    size = Size(clipWidth, canvasHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                        canvasHeight / 2,
                        canvasHeight / 2
                    )
                )
            }
        }
    }
}

/**
 * Chakra linear progress - colors change as progress increases
 */
@Composable
fun ChakraLinearProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    height: Dp = 10.dp
) {
    val currentChakraIndex = (progress * 6).toInt().coerceIn(0, 6)
    val chakraColors = remember { SpiritualColors.ChakraColors }

    Column(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw track with subtle chakra colors
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = chakraColors.map { it.copy(alpha = 0.2f) }
                ),
                size = Size(canvasWidth, canvasHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    canvasHeight / 2,
                    canvasHeight / 2
                )
            )

            // Draw progress up to current chakra
            if (progress > 0f) {
                val progressWidth = canvasWidth * progress.coerceIn(0f, 1f)
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = chakraColors.take(currentChakraIndex + 1)
                    ),
                    size = Size(progressWidth, canvasHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                        canvasHeight / 2,
                        canvasHeight / 2
                    )
                )
            }
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val chakraNames = listOf(
                    "Root", "Sacral", "Solar Plexus", "Heart", "Throat", "Third Eye", "Crown"
                )
                Text(
                    text = chakraNames.getOrNull(currentChakraIndex) ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = chakraColors.getOrNull(currentChakraIndex) ?: Color.White,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// ============================================================================
// CIRCULAR PROGRESS INDICATORS
// ============================================================================

/**
 * Spiritual circular progress with mandala pattern
 *
 * @param progress Current progress (0.0 to 1.0) - null for indeterminate
 * @param size Size of the circular indicator
 * @param strokeWidth Width of the progress ring
 * @param gradient Gradient colors to use
 * @param showPercentage Whether to show percentage text
 */
@Composable
fun SpiritualCircularProgress(
    progress: Float?,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    strokeWidth: Dp = 8.dp,
    gradient: List<Color> = SpiritualGradient,
    showPercentage: Boolean = true
) {
    if (progress != null) {
        // Determinate progress
        Box(
            modifier = modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(size)) {
                val canvasSize = size.toPx()
                val center = Offset(canvasSize / 2, canvasSize / 2)
                val radius = (canvasSize - strokeWidth.toPx()) / 2

                // Draw track
                drawCircle(
                    color = Color.White.copy(alpha = 0.1f),
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeWidth.toPx())
                )

                // Draw progress arc
                if (progress > 0f) {
                    val sweepAngle = 360f * progress.coerceIn(0f, 1f)

                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = gradient,
                            center = center
                        ),
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(
                            center.x - radius,
                            center.y - radius
                        ),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(
                            width = strokeWidth.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }

            if (showPercentage) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    } else {
        // Indeterminate progress
        SpiritualCircularProgressIndeterminate(
            modifier = modifier,
            size = size,
            strokeWidth = strokeWidth,
            gradient = gradient
        )
    }
}

/**
 * Indeterminate circular progress with rotating arc
 */
@Composable
private fun SpiritualCircularProgressIndeterminate(
    modifier: Modifier = Modifier,
    size: Dp,
    strokeWidth: Dp,
    gradient: List<Color>
) {
    val infiniteTransition = rememberInfiniteTransition(label = "circularProgress")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val arcSweep by infiniteTransition.animateFloat(
        initialValue = 30f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arcSweep"
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        val canvasSize = size.toPx()
        val center = Offset(canvasSize / 2, canvasSize / 2)
        val radius = (canvasSize - strokeWidth.toPx()) / 2

        rotate(rotation, center) {
            drawArc(
                brush = Brush.sweepGradient(
                    colors = gradient,
                    center = center
                ),
                startAngle = 0f,
                sweepAngle = arcSweep,
                useCenter = false,
                topLeft = Offset(
                    center.x - radius,
                    center.y - radius
                ),
                size = Size(radius * 2, radius * 2),
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
    }
}

/**
 * Mandala circular progress - ornate pattern that fills as progress increases
 */
@Composable
fun MandalaCircularProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    showPercentage: Boolean = true
) {
    val petalCount = 8

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val canvasSize = size.toPx()
            val center = Offset(canvasSize / 2, canvasSize / 2)
            val radius = canvasSize / 2 * 0.8f

            val filledPetals = (progress * petalCount).toInt()
            val partialPetal = (progress * petalCount) - filledPetals

            for (i in 0 until petalCount) {
                val angle = (i * 360f / petalCount) * (PI / 180f)

                val alpha = when {
                    i < filledPetals -> 1f
                    i == filledPetals -> partialPetal
                    else -> 0.15f
                }

                val color = SpiritualColors.ChakraColors[i % 7].copy(alpha = alpha.toFloat())

                drawMandalaProgressPetal(
                    center = center,
                    radius = radius,
                    angle = angle.toFloat(),
                    color = color
                )
            }

            // Draw center circle
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        SpiritualPurple.copy(alpha = 0.8f),
                        MysticViolet.copy(alpha = 0.6f)
                    ),
                    center = center,
                    radius = radius * 0.25f
                ),
                radius = radius * 0.25f,
                center = center
            )
        }

        if (showPercentage) {
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

private fun DrawScope.drawMandalaProgressPetal(
    center: Offset,
    radius: Float,
    angle: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(center.x, center.y)

        val petalRadius = radius * 0.6f
        val petalX = center.x + petalRadius * cos(angle)
        val petalY = center.y + petalRadius * sin(angle)

        quadraticTo(
            petalX + petalRadius * 0.3f * cos(angle + PI.toFloat() / 2),
            petalY + petalRadius * 0.3f * sin(angle + PI.toFloat() / 2),
            center.x + radius * cos(angle),
            center.y + radius * sin(angle)
        )

        quadraticTo(
            petalX + petalRadius * 0.3f * cos(angle - PI.toFloat() / 2),
            petalY + petalRadius * 0.3f * sin(angle - PI.toFloat() / 2),
            center.x,
            center.y
        )

        close()
    }

    drawPath(path = path, color = color)
    drawPath(
        path = path,
        color = Color.White.copy(alpha = 0.2f),
        style = Stroke(width = 1.dp.toPx())
    )
}

// ============================================================================
// STEP INDICATORS
// ============================================================================

/**
 * Step indicator for multi-step flows
 *
 * @param currentStep Current step (0-indexed)
 * @param totalSteps Total number of steps
 * @param stepLabels Optional labels for each step
 */
@Composable
fun SpiritualStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    stepLabels: List<String>? = null,
    showConnectors: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (step in 0 until totalSteps) {
                // Step circle
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    StepCircle(
                        stepNumber = step + 1,
                        isCompleted = step < currentStep,
                        isCurrent = step == currentStep,
                        totalSteps = totalSteps
                    )
                }

                // Connector line
                if (showConnectors && step < totalSteps - 1) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        StepConnector(
                            isCompleted = step < currentStep
                        )
                    }
                }
            }
        }

        if (stepLabels != null && stepLabels.size == totalSteps) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                stepLabels.forEachIndexed { index, label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            index < currentStep -> SpiritualColors.ChakraColors[index % 7]
                            index == currentStep -> SpiritualPurple
                            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        },
                        fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun StepCircle(
    stepNumber: Int,
    isCompleted: Boolean,
    isCurrent: Boolean,
    totalSteps: Int
) {
    val chakraColor = SpiritualColors.ChakraColors[(stepNumber - 1) % 7]

    Box(
        modifier = Modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(40.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2

            when {
                isCompleted -> {
                    // Filled circle for completed steps
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                chakraColor,
                                chakraColor.copy(alpha = 0.8f)
                            ),
                            center = center,
                            radius = radius
                        ),
                        radius = radius,
                        center = center
                    )
                }
                isCurrent -> {
                    // Pulsing ring for current step
                    drawCircle(
                        color = chakraColor.copy(alpha = 0.3f),
                        radius = radius,
                        center = center
                    )
                    drawCircle(
                        color = chakraColor,
                        radius = radius,
                        center = center,
                        style = Stroke(width = 3.dp.toPx())
                    )
                }
                else -> {
                    // Empty circle for future steps
                    drawCircle(
                        color = Color.White.copy(alpha = 0.2f),
                        radius = radius,
                        center = center,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }
        }

        if (isCompleted) {
            // Checkmark
            Text(
                text = "✓",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = stepNumber.toString(),
                color = if (isCurrent) chakraColor else Color.White.copy(alpha = 0.5f),
                fontSize = 14.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun StepConnector(
    isCompleted: Boolean
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
    ) {
        drawLine(
            brush = if (isCompleted) {
                Brush.horizontalGradient(
                    colors = listOf(
                        SpiritualPurple,
                        AuroraPink
                    )
                )
            } else {
                Brush.horizontalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.2f)
                    )
                )
            },
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = size.height
        )
    }
}

/**
 * Vertical step indicator (for side navigation)
 */
@Composable
fun VerticalStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    stepLabels: List<String>? = null
) {
    Column(
        modifier = modifier
    ) {
        for (step in 0 until totalSteps) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepCircle(
                    stepNumber = step + 1,
                    isCompleted = step < currentStep,
                    isCurrent = step == currentStep,
                    totalSteps = totalSteps
                )

                Spacer(modifier = Modifier.width(12.dp))

                stepLabels?.getOrNull(step)?.let { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = when {
                            step < currentStep -> SpiritualColors.ChakraColors[step % 7]
                            step == currentStep -> SpiritualPurple
                            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        },
                        fontWeight = if (step == currentStep) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            if (step < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .padding(start = 19.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawLine(
                            color = if (step < currentStep) {
                                SpiritualPurple
                            } else {
                                Color.White.copy(alpha = 0.2f)
                            },
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = size.width
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// SPECIALIZED PROGRESS INDICATORS
// ============================================================================

/**
 * Profile completion progress
 * Shows progress through profile setup sections
 */
@Composable
fun ProfileCompletionProgress(
    sectionsCompleted: Int,
    totalSections: Int,
    modifier: Modifier = Modifier
) {
    val progress = sectionsCompleted.toFloat() / totalSections.toFloat()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Profile Completion",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$sectionsCompleted / $totalSections",
                style = MaterialTheme.typography.bodyMedium,
                color = SpiritualPurple
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChakraLinearProgress(
            progress = progress,
            showLabel = false,
            height = 12.dp
        )
    }
}

/**
 * Loading progress with message
 * Animated progress bar with descriptive text
 */
@Composable
fun LoadingProgressWithMessage(
    progress: Float?,
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        SpiritualLinearProgress(
            progress = progress,
            height = 6.dp
        )

        if (progress != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
