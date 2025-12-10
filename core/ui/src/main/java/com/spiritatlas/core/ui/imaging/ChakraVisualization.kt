package com.spiritatlas.core.ui.imaging

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.spiritatlas.core.ui.theme.SpiritualColors
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Comprehensive chakra visualization system with multiple display modes.
 *
 * Features:
 * - Vertical chakra column with energy indicators
 * - Animated energy flow visualization
 * - Radial chakra balance wheel
 * - Individual chakra detail cards
 *
 * Usage:
 * ```
 * ChakraProgressionColumn(
 *     chakraStates = profileChakras,
 *     onChakraClick = { navigateToChakraDetail(it) }
 * )
 * ```
 */

/**
 * Chakra state data class
 */
data class ChakraState(
    val chakra: Chakra,
    val energyLevel: Float,        // 0.0 to 1.0
    val isBlocked: Boolean = false,
    val isExpanded: Boolean = false,
    val notes: String? = null
)

/**
 * Vertical chakra progression column showing all 7 chakras
 */
@Composable
fun ChakraProgressionColumn(
    chakraStates: List<ChakraState>,
    onChakraClick: ((Chakra) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        chakraStates.forEachIndexed { index, state ->
            ChakraRow(
                state = state,
                position = index,
                onClick = { onChakraClick?.invoke(state.chakra) }
            )
        }
    }
}

/**
 * Individual chakra row with icon, name, and energy bar
 */
@Composable
private fun ChakraRow(
    state: ChakraState,
    position: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Chakra icon with glow effect
        Box(modifier = Modifier.size(48.dp)) {
            DynamicIconProvider.ChakraIcon(
                chakra = state.chakra,
                displayMode = IconDisplayMode.VECTOR_SMALL,
                showGlow = state.energyLevel > 0.7f
            )

            // High energy pulsing glow
            if (state.energyLevel > 0.7f) {
                val infiniteTransition = rememberInfiniteTransition(label = "chakra_glow")
                val glowAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.2f,
                    targetValue = 0.5f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "glow_alpha"
                )

                Canvas(Modifier.fillMaxSize()) {
                    drawCircle(
                        color = SpiritualColors.ChakraColors[position].copy(alpha = glowAlpha),
                        radius = size.minDimension / 2f * 1.3f
                    )
                }
            }

            // Blocked indicator
            if (state.isBlocked) {
                Canvas(Modifier.fillMaxSize()) {
                    drawLine(
                        color = Color.Red.copy(alpha = 0.6f),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color.Red.copy(alpha = 0.6f),
                        start = Offset(size.width, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.chakra.sanskritName,
                style = MaterialTheme.typography.titleMedium
            )

            // Energy level bar
            LinearProgressIndicator(
                progress = { state.energyLevel },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = SpiritualColors.ChakraColors[position],
                trackColor = SpiritualColors.ChakraColors[position].copy(alpha = 0.2f)
            )

            // Energy percentage
            Text(
                text = "${(state.energyLevel * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Expanded visualization preview
        if (state.isExpanded) {
            Spacer(Modifier.width(8.dp))
            AsyncImage(
                model = "chakras/chakra_${state.chakra.sanskritName.lowercase()}.png",
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )
        }
    }
}

/**
 * Animated energy flow rising through chakras
 */
@Composable
fun ChakraEnergyFlowAnimation(
    modifier: Modifier = Modifier,
    flowSpeed: Long = 3000L
) {
    val infiniteTransition = rememberInfiniteTransition(label = "chakra_flow")
    val energyPosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(flowSpeed.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "energy_position"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val chakraPositions = listOf(
            Offset(size.width / 2f, size.height * 0.9f),  // Root
            Offset(size.width / 2f, size.height * 0.75f), // Sacral
            Offset(size.width / 2f, size.height * 0.6f),  // Solar Plexus
            Offset(size.width / 2f, size.height * 0.45f), // Heart
            Offset(size.width / 2f, size.height * 0.3f),  // Throat
            Offset(size.width / 2f, size.height * 0.15f), // Third Eye
            Offset(size.width / 2f, size.height * 0.05f)  // Crown
        )

        // Draw chakra points
        chakraPositions.forEachIndexed { index, position ->
            drawCircle(
                color = SpiritualColors.ChakraColors[index],
                radius = 12f,
                center = position
            )
        }

        // Draw connecting line
        for (i in 0 until chakraPositions.size - 1) {
            drawLine(
                color = Color.White.copy(alpha = 0.2f),
                start = chakraPositions[i],
                end = chakraPositions[i + 1],
                strokeWidth = 2f
            )
        }

        // Draw energy flow particle
        val currentSegment = (energyPosition * 6).toInt()
        val segmentProgress = (energyPosition * 6) % 1f

        if (currentSegment < 6) {
            val start = chakraPositions[currentSegment]
            val end = chakraPositions[currentSegment + 1]
            val currentPos = Offset(
                start.x + (end.x - start.x) * segmentProgress,
                start.y + (end.y - start.y) * segmentProgress
            )

            // Glowing energy particle
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        SpiritualColors.ChakraColors[currentSegment],
                        Color.Transparent
                    )
                ),
                radius = 16f,
                center = currentPos
            )

            // Energy trail
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(
                        SpiritualColors.ChakraColors[currentSegment].copy(alpha = 0.6f),
                        SpiritualColors.ChakraColors[currentSegment].copy(alpha = 0.0f)
                    ),
                    start = start,
                    end = currentPos
                ),
                start = start,
                end = currentPos,
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * Radial chakra balance wheel showing relative energy levels
 */
@Composable
fun ChakraBalanceWheel(
    chakraLevels: List<Float>, // 0.0 to 1.0 for each of 7 chakras
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(280.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = size.minDimension / 2f * 0.8f

        // Draw background circles
        for (i in 1..5) {
            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                radius = maxRadius * i / 5f,
                center = center,
                style = Stroke(width = 1f)
            )
        }

        // Draw each chakra as a spoke
        chakraLevels.forEachIndexed { index, level ->
            val angle = (index * 360f / 7f - 90f) * (PI / 180f).toFloat()
            val endX = center.x + cos(angle) * maxRadius * level
            val endY = center.y + sin(angle) * maxRadius * level

            // Spoke line
            drawLine(
                color = SpiritualColors.ChakraColors[index],
                start = center,
                end = Offset(endX, endY),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )

            // End point circle
            drawCircle(
                color = SpiritualColors.ChakraColors[index],
                radius = 10f,
                center = Offset(endX, endY)
            )

            // Chakra label position (outside wheel)
            val labelX = center.x + cos(angle) * (maxRadius + 40f)
            val labelY = center.y + sin(angle) * (maxRadius + 40f)

            // Small chakra icon indicator
            drawCircle(
                color = SpiritualColors.ChakraColors[index],
                radius = 8f,
                center = Offset(labelX, labelY)
            )
        }

        // Connect points to form filled shape
        val path = Path().apply {
            chakraLevels.forEachIndexed { index, level ->
                val angle = (index * 360f / 7f - 90f) * (PI / 180f).toFloat()
                val x = center.x + cos(angle) * maxRadius * level
                val y = center.y + sin(angle) * maxRadius * level

                if (index == 0) moveTo(x, y)
                else lineTo(x, y)
            }
            close()
        }

        // Fill shape with rainbow gradient
        drawPath(
            path = path,
            brush = Brush.radialGradient(
                colors = SpiritualColors.ChakraColors.map { it.copy(alpha = 0.2f) },
                center = center,
                radius = maxRadius
            )
        )

        // Center circle with yin-yang style
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White,
                    com.spiritatlas.core.ui.theme.MysticPurple.copy(alpha = 0.6f)
                )
            ),
            radius = 24f,
            center = center
        )

        drawCircle(
            color = Color.White.copy(alpha = 0.8f),
            radius = 20f,
            center = center,
            style = Stroke(width = 2f)
        )
    }
}

/**
 * Chakra energy ring visualization (similar to compatibility rings)
 */
@Composable
fun ChakraEnergyRings(
    chakraLevels: List<Float>,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true
) {
    Box(modifier = modifier.size(300.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val baseRadius = size.minDimension / 2f * 0.2f
            val strokeWidth = 16f
            val spacing = 20f

            chakraLevels.forEachIndexed { index, level ->
                val radius = baseRadius + (index * spacing)

                // Background ring
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.15f),
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeWidth)
                )

                // Progress arc
                drawArc(
                    color = SpiritualColors.ChakraColors[index],
                    startAngle = -90f,
                    sweepAngle = 360f * level,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }

        // Optional center label
        if (showLabels) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val averageLevel = chakraLevels.average()
                Text(
                    text = "${(averageLevel * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Compact chakra status indicator for profile cards
 */
@Composable
fun CompactChakraIndicator(
    dominantChakra: Chakra,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpiritualColors.ChakraColors.forEachIndexed { index, color ->
            val isActive = index + 1 == dominantChakra.number
            val size = if (isActive) 12.dp else 6.dp
            val alpha = if (isActive) 1f else 0.3f

            Canvas(modifier = Modifier.size(size)) {
                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = this.size.minDimension / 2f
                )
            }
        }
    }
}
