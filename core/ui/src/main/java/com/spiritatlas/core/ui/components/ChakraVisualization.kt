package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Data class for chakra state with resource ID and name
 */
data class ChakraState(
    val activation: Float, // 0.0 to 1.0
    val chakraNumber: Int,
    val chakraResourceId: Int,
    val chakraName: String
)

/**
 * Vertical chakra visualization from root to crown.
 */
@Composable
fun ChakraVisualization(
    chakras: List<ChakraState>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        chakras.forEach { chakraState ->
            ChakraRow(
                chakraResourceId = chakraState.chakraResourceId,
                chakraName = chakraState.chakraName,
                chakraNumber = chakraState.chakraNumber,
                activation = chakraState.activation,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Single chakra row with image and activation indicator.
 *
 * @param chakraResourceId The drawable resource ID from app module
 * @param chakraName Name of the chakra
 */
@Composable
fun ChakraRow(
    chakraResourceId: Int,
    chakraName: String,
    chakraNumber: Int,
    activation: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Chakra image
        Image(
            painter = painterResource(chakraResourceId),
            contentDescription = chakraName,
            modifier = Modifier.size(48.dp),
            alpha = 0.3f + (activation * 0.7f) // Dim if not active
        )

        // Chakra info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chakraName,
                style = MaterialTheme.typography.bodyLarge
            )

            LinearProgressIndicator(
                progress = { activation },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = getChakraColor(chakraNumber)
            )
        }
    }
}

/**
 * Simple chakra image display.
 *
 * @param chakraResourceId The drawable resource ID from app module
 */
@Composable
fun ChakraImage(
    chakraResourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 48.dp,
    alpha: Float = 1f
) {
    Image(
        painter = painterResource(chakraResourceId),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        alpha = alpha
    )
}

private fun getChakraColor(chakraNumber: Int): Color = when (chakraNumber) {
    1 -> Color(0xFFEF4444) // Red
    2 -> Color(0xFFF97316) // Orange
    3 -> Color(0xFFFDE047) // Yellow
    4 -> Color(0xFF22C55E) // Green
    5 -> Color(0xFF3B82F6) // Blue
    6 -> Color(0xFF6366F1) // Indigo
    7 -> Color(0xFF8B5CF6) // Violet
    else -> Color.Gray
}
