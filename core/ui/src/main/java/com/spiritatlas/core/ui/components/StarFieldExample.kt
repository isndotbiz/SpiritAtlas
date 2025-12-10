package com.spiritatlas.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.CosmicBlue
import com.spiritatlas.core.ui.theme.MoonlightSilver
import com.spiritatlas.core.ui.theme.SpiritualGradients
import com.spiritatlas.core.ui.theme.SpiritualPurple

/**
 * StarField Component Examples
 *
 * Demonstrates various use cases and configurations of the StarField component
 */

/**
 * Example 1: Basic StarField with default settings
 * Perfect for background animations in most screens
 */
@Preview(showBackground = true)
@Composable
fun StarFieldBasicExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        StarField(
            modifier = Modifier.fillMaxSize()
        )

        // Content over star field
        Text(
            text = "Basic StarField",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

/**
 * Example 2: Dense star field with fast twinkle
 * Great for intense cosmic scenes or loading screens
 */
@Preview(showBackground = true)
@Composable
fun StarFieldDenseExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        StarField(
            modifier = Modifier.fillMaxSize(),
            starCount = 200,
            twinkleSpeed = 3f
        )

        Text(
            text = "Dense Star Field\n200 stars, fast twinkle",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

/**
 * Example 3: Colored star field with custom tint
 * Shows how to customize star color for different moods
 */
@Preview(showBackground = true)
@Composable
fun StarFieldColoredExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Purple tinted stars
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            StarField(
                modifier = Modifier.fillMaxSize(),
                color = SpiritualPurple,
                starCount = 80
            )
            Text(
                text = "Purple Stars",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Blue tinted stars
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            StarField(
                modifier = Modifier.fillMaxSize(),
                color = CosmicBlue,
                starCount = 80
            )
            Text(
                text = "Blue Stars",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * Example 4: Static star field (no parallax)
 * Useful when you want stars without movement distraction
 */
@Preview(showBackground = true)
@Composable
fun StarFieldStaticExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        StarField(
            modifier = Modifier.fillMaxSize(),
            enableParallax = false,
            starCount = 150
        )

        Text(
            text = "Static Star Field\n(no parallax movement)",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

/**
 * Example 5: StarField with gradient background
 * Combines star field with gradient for a rich cosmic look
 */
@Preview(showBackground = true)
@Composable
fun StarFieldWithGradientExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF1B1B3A),
                        Color(0xFF2D1B42)
                    )
                )
            )
    ) {
        StarField(
            modifier = Modifier.fillMaxSize(),
            starCount = 120,
            color = MoonlightSilver
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "StarField + Gradient",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Perfect for splash screens",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Example 6: Slow, sparse star field
 * Minimalist approach for subtle backgrounds
 */
@Preview(showBackground = true)
@Composable
fun StarFieldMinimalExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D1A))
    ) {
        StarField(
            modifier = Modifier.fillMaxSize(),
            starCount = 30,
            twinkleSpeed = 1f
        )

        Text(
            text = "Minimal Star Field\n30 stars, slow animation",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

/**
 * Example 7: Full screen spiritual background
 * Production-ready example for app screens
 */
@Preview(showBackground = true)
@Composable
fun StarFieldProductionExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualGradients.cosmicNight)
    ) {
        // Background star field
        StarField(
            modifier = Modifier.fillMaxSize(),
            starCount = 100,
            twinkleSpeed = 2f,
            color = MoonlightSilver
        )

        // Content example
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "SpiritAtlas",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Discover Your Cosmic Blueprint",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.weight(2f))
        }
    }
}

/**
 * USAGE GUIDE:
 *
 * Basic usage:
 * ```kotlin
 * Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
 *     StarField(modifier = Modifier.fillMaxSize())
 * }
 * ```
 *
 * Customized:
 * ```kotlin
 * StarField(
 *     modifier = Modifier.fillMaxSize(),
 *     starCount = 150,           // More stars = denser field
 *     twinkleSpeed = 2.5f,       // Faster twinkling
 *     color = SpiritualPurple,   // Custom star color
 *     enableParallax = true      // Enable movement effect
 * )
 * ```
 *
 * Performance tips:
 * - Use starCount between 50-200 for best performance
 * - Lower star counts (30-80) for low-end devices
 * - Disable parallax if UI feels heavy
 * - Component is optimized for 60 FPS on most devices
 *
 * Best practices:
 * - Always use dark background behind StarField
 * - Adjust star color to match your theme
 * - Use static mode (enableParallax=false) for text-heavy screens
 * - Combine with gradients for rich visual depth
 */
