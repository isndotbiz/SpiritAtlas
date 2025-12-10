package com.spiritatlas.core.ui.decoration

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Sacred Geometry patterns for spiritual visualizations
 * Includes Flower of Life, Merkaba, Sri Yantra, and other sacred patterns
 */

/**
 * Flower of Life - Ancient sacred geometry pattern
 * Represents the fundamental forms of space and time
 */
@Composable
fun FlowerOfLife(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFFD700),
    strokeWidth: Float = 2f,
    rings: Int = 3
) {
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / (2 * (rings + 1))

        // Draw center circle
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = strokeWidth.dp.toPx())
        )

        // Draw surrounding circles in rings
        for (ring in 1..rings) {
            val circlesInRing = ring * 6
            val ringRadius = radius * ring

            for (i in 0 until circlesInRing) {
                val angle = (2 * PI * i / circlesInRing).toFloat()
                val x = centerX + cos(angle) * ringRadius
                val y = centerY + sin(angle) * ringRadius

                drawCircle(
                    color = color,
                    radius = radius,
                    center = Offset(x, y),
                    style = Stroke(width = strokeWidth.dp.toPx())
                )
            }
        }

        // Draw the 6 primary circles around center
        for (i in 0 until 6) {
            val angle = (2 * PI * i / 6).toFloat()
            val x = centerX + cos(angle) * radius
            val y = centerY + sin(angle) * radius

            drawCircle(
                color = color,
                radius = radius,
                center = Offset(x, y),
                style = Stroke(width = strokeWidth.dp.toPx())
            )
        }
    }
}

/**
 * Merkaba (Star Tetrahedron) - 3D geometric shape used in meditation
 * Represents the spirit/body surrounded by counter-rotating fields of light
 */
@Composable
fun Merkaba(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFB388FF),
    strokeWidth: Float = 2f
) {
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 3

        // Upward pointing tetrahedron
        val upwardPath = Path().apply {
            val height = radius * sqrt(3.0).toFloat()
            val top = Offset(centerX, centerY - height / 2)
            val bottomLeft = Offset(centerX - radius, centerY + height / 4)
            val bottomRight = Offset(centerX + radius, centerY + height / 4)

            moveTo(top.x, top.y)
            lineTo(bottomLeft.x, bottomLeft.y)
            lineTo(bottomRight.x, bottomRight.y)
            close()
        }

        // Downward pointing tetrahedron
        val downwardPath = Path().apply {
            val height = radius * sqrt(3.0).toFloat()
            val bottom = Offset(centerX, centerY + height / 2)
            val topLeft = Offset(centerX - radius, centerY - height / 4)
            val topRight = Offset(centerX + radius, centerY - height / 4)

            moveTo(bottom.x, bottom.y)
            lineTo(topLeft.x, topLeft.y)
            lineTo(topRight.x, topRight.y)
            close()
        }

        // Draw both tetrahedrons
        drawPath(
            path = upwardPath,
            color = color,
            style = Stroke(width = strokeWidth.dp.toPx())
        )
        drawPath(
            path = downwardPath,
            color = color,
            style = Stroke(width = strokeWidth.dp.toPx())
        )

        // Draw center circle for meditation focus
        drawCircle(
            color = color.copy(alpha = 0.5f),
            radius = radius / 4,
            center = Offset(centerX, centerY)
        )
    }
}

/**
 * Sri Yantra - Ancient Hindu tantra symbol
 * Represents the union of masculine and feminine divine energies
 */
@Composable
fun SriYantra(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF6B6B),
    strokeWidth: Float = 1.5f
) {
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val baseSize = size.minDimension / 2.5f

        // Outer square with gates (bhupura)
        drawSquareWithGates(centerX, centerY, baseSize * 1.4f, color, strokeWidth)

        // Three concentric circles
        for (i in 1..3) {
            val radius = baseSize * (1.0f - i * 0.08f)
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(centerX, centerY),
                style = Stroke(width = strokeWidth.dp.toPx())
            )
        }

        // 16-petaled lotus
        drawLotus(centerX, centerY, baseSize * 0.85f, 16, color, strokeWidth)

        // 8-petaled lotus
        drawLotus(centerX, centerY, baseSize * 0.65f, 8, color, strokeWidth)

        // Nine interlocking triangles (simplified representation)
        drawInterlockingTriangles(centerX, centerY, baseSize * 0.5f, color, strokeWidth)

        // Bindu (center point)
        drawCircle(
            color = color,
            radius = 4.dp.toPx(),
            center = Offset(centerX, centerY)
        )
    }
}

/**
 * Metatron's Cube - Contains all 5 Platonic Solids
 * Represents the blueprint of the universe
 */
@Composable
fun MetatronsCube(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF4DD0E1),
    strokeWidth: Float = 1.5f
) {
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 3

        // 13 circles in Metatron's Cube pattern
        val circles = mutableListOf<Offset>()

        // Center circle
        circles.add(Offset(centerX, centerY))

        // 6 surrounding circles (hexagon)
        for (i in 0 until 6) {
            val angle = (PI / 3 * i).toFloat()
            circles.add(
                Offset(
                    centerX + cos(angle) * radius,
                    centerY + sin(angle) * radius
                )
            )
        }

        // 6 outer circles
        for (i in 0 until 6) {
            val angle = (PI / 3 * i + PI / 6).toFloat()
            circles.add(
                Offset(
                    centerX + cos(angle) * radius * sqrt(3.0).toFloat(),
                    centerY + sin(angle) * radius * sqrt(3.0).toFloat()
                )
            )
        }

        // Draw all circles
        circles.forEach { center ->
            drawCircle(
                color = color,
                radius = radius / 3,
                center = center,
                style = Stroke(width = strokeWidth.dp.toPx())
            )
        }

        // Connect all circles to create the cube structure
        for (i in circles.indices) {
            for (j in i + 1 until circles.size) {
                drawLine(
                    color = color.copy(alpha = 0.3f),
                    start = circles[i],
                    end = circles[j],
                    strokeWidth = (strokeWidth * 0.7f).dp.toPx()
                )
            }
        }
    }
}

/**
 * Vesica Piscis - Intersection of two circles
 * Represents the womb of creation
 */
@Composable
fun VesicaPiscis(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFE1BEE7),
    strokeWidth: Float = 2f,
    filled: Boolean = false
) {
    Canvas(modifier = modifier.aspectRatio(2f)) {
        val centerY = size.height / 2
        val radius = size.height / 2
        val offset = radius / 2

        // Left circle
        val leftCenter = Offset(size.width / 2 - offset, centerY)
        // Right circle
        val rightCenter = Offset(size.width / 2 + offset, centerY)

        if (filled) {
            // Draw filled vesica piscis shape
            val path = Path().apply {
                // Calculate intersection points
                val top = Offset(size.width / 2, centerY - radius * sqrt(3.0).toFloat() / 2)
                val bottom = Offset(size.width / 2, centerY + radius * sqrt(3.0).toFloat() / 2)

                moveTo(top.x, top.y)
                // Arc from top to bottom (right side)
                arcTo(
                    rect = androidx.compose.ui.geometry.Rect(
                        rightCenter.x - radius,
                        rightCenter.y - radius,
                        rightCenter.x + radius,
                        rightCenter.y + radius
                    ),
                    startAngleDegrees = 240f,
                    sweepAngleDegrees = 120f,
                    forceMoveTo = false
                )
                // Arc from bottom to top (left side)
                arcTo(
                    rect = androidx.compose.ui.geometry.Rect(
                        leftCenter.x - radius,
                        leftCenter.y - radius,
                        leftCenter.x + radius,
                        leftCenter.y + radius
                    ),
                    startAngleDegrees = 60f,
                    sweepAngleDegrees = 120f,
                    forceMoveTo = false
                )
                close()
            }
            drawPath(
                path = path,
                color = color.copy(alpha = 0.3f)
            )
        }

        // Draw circles
        drawCircle(
            color = color,
            radius = radius,
            center = leftCenter,
            style = Stroke(width = strokeWidth.dp.toPx())
        )
        drawCircle(
            color = color,
            radius = radius,
            center = rightCenter,
            style = Stroke(width = strokeWidth.dp.toPx())
        )
    }
}

// === Helper Functions ===

private fun DrawScope.drawSquareWithGates(
    centerX: Float,
    centerY: Float,
    size: Float,
    color: Color,
    strokeWidth: Float
) {
    val halfSize = size / 2
    val gateSize = size * 0.15f

    // Draw square with T-shaped gates on each side
    val path = Path().apply {
        // Top side with gate
        moveTo(centerX - halfSize, centerY - halfSize)
        lineTo(centerX - gateSize, centerY - halfSize)
        lineTo(centerX - gateSize, centerY - halfSize - gateSize / 2)
        lineTo(centerX + gateSize, centerY - halfSize - gateSize / 2)
        lineTo(centerX + gateSize, centerY - halfSize)
        lineTo(centerX + halfSize, centerY - halfSize)

        // Right side with gate
        lineTo(centerX + halfSize, centerY - gateSize)
        lineTo(centerX + halfSize + gateSize / 2, centerY - gateSize)
        lineTo(centerX + halfSize + gateSize / 2, centerY + gateSize)
        lineTo(centerX + halfSize, centerY + gateSize)
        lineTo(centerX + halfSize, centerY + halfSize)

        // Bottom side with gate
        lineTo(centerX + gateSize, centerY + halfSize)
        lineTo(centerX + gateSize, centerY + halfSize + gateSize / 2)
        lineTo(centerX - gateSize, centerY + halfSize + gateSize / 2)
        lineTo(centerX - gateSize, centerY + halfSize)
        lineTo(centerX - halfSize, centerY + halfSize)

        // Left side with gate
        lineTo(centerX - halfSize, centerY + gateSize)
        lineTo(centerX - halfSize - gateSize / 2, centerY + gateSize)
        lineTo(centerX - halfSize - gateSize / 2, centerY - gateSize)
        lineTo(centerX - halfSize, centerY - gateSize)
        close()
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth.dp.toPx())
    )
}

private fun DrawScope.drawLotus(
    centerX: Float,
    centerY: Float,
    radius: Float,
    petals: Int,
    color: Color,
    strokeWidth: Float
) {
    val petalWidth = (2 * PI / petals).toFloat()

    for (i in 0 until petals) {
        val angle = (2 * PI * i / petals).toFloat()
        val path = Path().apply {
            val startAngle = angle - petalWidth / 2
            val endAngle = angle + petalWidth / 2

            moveTo(centerX, centerY)
            lineTo(
                centerX + cos(startAngle) * radius,
                centerY + sin(startAngle) * radius
            )

            val ctrlRadius = radius * 1.3f
            quadraticBezierTo(
                centerX + cos(angle) * ctrlRadius,
                centerY + sin(angle) * ctrlRadius,
                centerX + cos(endAngle) * radius,
                centerY + sin(endAngle) * radius
            )
            close()
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokeWidth.dp.toPx())
        )
    }
}

private fun DrawScope.drawInterlockingTriangles(
    centerX: Float,
    centerY: Float,
    size: Float,
    color: Color,
    strokeWidth: Float
) {
    // 5 upward pointing triangles
    for (i in 0 until 5) {
        val scale = 1.0f - i * 0.15f
        val offset = i * size * 0.1f
        drawTriangle(
            centerX,
            centerY + offset,
            size * scale,
            true,
            color,
            strokeWidth
        )
    }

    // 4 downward pointing triangles
    for (i in 0 until 4) {
        val scale = 1.0f - i * 0.18f
        val offset = i * size * 0.12f
        drawTriangle(
            centerX,
            centerY - offset,
            size * scale * 0.85f,
            false,
            color,
            strokeWidth
        )
    }
}

private fun DrawScope.drawTriangle(
    centerX: Float,
    centerY: Float,
    size: Float,
    pointUp: Boolean,
    color: Color,
    strokeWidth: Float
) {
    val path = Path()
    val height = size * sqrt(3.0).toFloat() / 2

    if (pointUp) {
        path.moveTo(centerX, centerY - height * 2 / 3)
        path.lineTo(centerX - size / 2, centerY + height / 3)
        path.lineTo(centerX + size / 2, centerY + height / 3)
    } else {
        path.moveTo(centerX, centerY + height * 2 / 3)
        path.lineTo(centerX - size / 2, centerY - height / 3)
        path.lineTo(centerX + size / 2, centerY - height / 3)
    }
    path.close()

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth.dp.toPx())
    )
}
