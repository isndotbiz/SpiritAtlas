package com.spiritatlas.core.ui.effects

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

// TODO: This is a placeholder implementation. The original file has been stubbed out.

@Composable
fun ParticleEffect(modifier: Modifier = Modifier, particleCount: Int = 100, color: Color = Color.White) {
    val particles = remember { mutableStateListOf<Particle>() }

    LaunchedEffect(Unit) {
        for (i in 0 until particleCount) {
            particles.add(Particle(randomOffset(), randomOffset(), Color.White))
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            drawCircle(particle.color, 5f, center = Offset(particle.x, particle.y))
        }
    }
}

data class Particle(val x: Float, val y: Float, val color: Color)

private fun randomOffset(): Float {
    return Random.nextFloat() * 1000
}
