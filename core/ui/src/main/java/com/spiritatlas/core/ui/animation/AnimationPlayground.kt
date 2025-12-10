package com.spiritatlas.core.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt

/**
 * Animation Playground - Debug Screen
 *
 * Interactive testing environment for all animations with:
 * - Real-time FPS counter
 * - Performance metrics
 * - Animation speed controls
 * - Reduce motion testing
 * - Particle count adjustment
 * - All animations in one place for testing
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationPlaygroundScreen() {
  var reduceMotion by remember { mutableStateOf(false) }
  var speedMultiplier by remember { mutableStateOf(1.0f) }
  var particleMultiplier by remember { mutableStateOf(1.0f) }
  var showFps by remember { mutableStateOf(true) }
  var performanceTier by remember { mutableStateOf(PerformanceTier.HIGH) }

  val config = remember(reduceMotion, speedMultiplier, particleMultiplier, performanceTier) {
    AnimationConfig(
      reduceMotion = reduceMotion,
      performanceTier = performanceTier,
      isFirstTime = false,
      speedMultiplier = speedMultiplier,
      enablePerformanceMonitoring = true,
      particleMultiplier = particleMultiplier
    )
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Animation Playground",
            fontWeight = FontWeight.Bold,
            color = AuraGold
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = DarkBackground
        ),
        actions = {
          // FPS counter toggle
          IconToggleButton(
            checked = showFps,
            onCheckedChange = { showFps = it }
          ) {
            Icon(
              imageVector = Icons.Default.Speed,
              contentDescription = "Toggle FPS",
              tint = if (showFps) AuraGold else Color.Gray
            )
          }
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(DarkBackground)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // FPS Counter
        if (showFps) {
          FPSCounter()
        }

        // Controls Section
        ControlsSection(
          reduceMotion = reduceMotion,
          onReduceMotionChange = { reduceMotion = it },
          speedMultiplier = speedMultiplier,
          onSpeedChange = { speedMultiplier = it },
          particleMultiplier = particleMultiplier,
          onParticleMultiplierChange = { particleMultiplier = it },
          performanceTier = performanceTier,
          onPerformanceTierChange = { performanceTier = it }
        )

        // Animation Test Sections
        FadeAnimationsSection(config)
        ScaleAnimationsSection(config)
        StaggerAnimationsSection(config)
        SpiritualAnimationsSection(config)
        ParticleAnimationsSection(config)
      }
    }
  }
}

/**
 * Real-time FPS Counter
 * Measures actual rendering performance
 */
@Composable
fun FPSCounter() {
  var fps by remember { mutableStateOf(0f) }
  var frameCount by remember { mutableStateOf(0) }
  var lastTime by remember { mutableStateOf(System.nanoTime()) }

  LaunchedEffect(Unit) {
    while (isActive) {
      frameCount++
      val currentTime = System.nanoTime()
      val deltaTime = (currentTime - lastTime) / 1_000_000_000.0

      if (deltaTime >= 1.0) {
        fps = frameCount / deltaTime.toFloat()
        frameCount = 0
        lastTime = currentTime
      }

      delay(16) // ~60 FPS frame time
    }
  }

  val fpsColor = when {
    fps >= 55f -> ChakraGreen // Excellent
    fps >= 45f -> ChakraYellow // Good
    fps >= 30f -> ChakraOrange // Acceptable
    else -> ChakraRed // Poor
  }

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = Color.Black.copy(alpha = 0.5f)
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Performance",
          style = MaterialTheme.typography.titleMedium,
          color = Color.White
        )
        Text(
          text = "Target: 60 FPS",
          style = MaterialTheme.typography.bodySmall,
          color = Color.Gray
        )
      }

      Text(
        text = "${fps.roundToInt()} FPS",
        style = MaterialTheme.typography.headlineLarge,
        color = fpsColor,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

/**
 * Animation Controls
 */
@Composable
fun ControlsSection(
  reduceMotion: Boolean,
  onReduceMotionChange: (Boolean) -> Unit,
  speedMultiplier: Float,
  onSpeedChange: (Float) -> Unit,
  particleMultiplier: Float,
  onParticleMultiplierChange: (Float) -> Unit,
  performanceTier: PerformanceTier,
  onPerformanceTierChange: (PerformanceTier) -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = Color.White.copy(alpha = 0.05f)
    )
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text(
        text = "Animation Controls",
        style = MaterialTheme.typography.titleLarge,
        color = CosmicBlue,
        fontWeight = FontWeight.Bold
      )

      // Reduce Motion Toggle
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Reduce Motion",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
          )
          Text(
            text = "Accessibility mode - minimal animations",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
          )
        }
        Switch(
          checked = reduceMotion,
          onCheckedChange = onReduceMotionChange
        )
      }

      Divider(color = Color.Gray.copy(alpha = 0.3f))

      // Speed Control
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Animation Speed",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
          )
          Text(
            text = "${(speedMultiplier * 100).roundToInt()}%",
            style = MaterialTheme.typography.bodyLarge,
            color = AuraGold,
            fontWeight = FontWeight.Bold
          )
        }
        Slider(
          value = speedMultiplier,
          onValueChange = onSpeedChange,
          valueRange = 0.25f..2.0f,
          steps = 6,
          enabled = !reduceMotion
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("Slow (0.25x)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
          Text("Fast (2x)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
      }

      Divider(color = Color.Gray.copy(alpha = 0.3f))

      // Particle Count Control
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Particle Density",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
          )
          Text(
            text = "${(particleMultiplier * 100).roundToInt()}%",
            style = MaterialTheme.typography.bodyLarge,
            color = SpiritualPurple,
            fontWeight = FontWeight.Bold
          )
        }
        Slider(
          value = particleMultiplier,
          onValueChange = onParticleMultiplierChange,
          valueRange = 0.0f..1.0f,
          enabled = !reduceMotion
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("None", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
          Text("Max", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
      }

      Divider(color = Color.Gray.copy(alpha = 0.3f))

      // Performance Tier
      Column {
        Text(
          text = "Performance Tier",
          style = MaterialTheme.typography.bodyLarge,
          color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          PerformanceTier.entries.forEach { tier ->
            FilterChip(
              selected = performanceTier == tier,
              onClick = { onPerformanceTierChange(tier) },
              label = { Text(tier.name) },
              enabled = !reduceMotion
            )
          }
        }
      }
    }
  }
}

/**
 * Fade Animations Test Section
 */
@Composable
fun FadeAnimationsSection(config: AnimationConfig) {
  var visible by remember { mutableStateOf(true) }

  TestSection(title = "Fade Animations") {
    Button(
      onClick = { visible = !visible },
      colors = ButtonDefaults.buttonColors(containerColor = CosmicBlue)
    ) {
      Text(if (visible) "Hide" else "Show")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Box(
      modifier = Modifier
        .size(100.dp)
        .background(SpiritualPurple)
        .optimizedFadeIn(visible = visible, config = config),
      contentAlignment = Alignment.Center
    ) {
      Text("Fade In", color = Color.White)
    }
  }
}

/**
 * Scale Animations Test Section
 */
@Composable
fun ScaleAnimationsSection(config: AnimationConfig) {
  var pressed by remember { mutableStateOf(false) }
  var showOvershoot by remember { mutableStateOf(false) }

  TestSection(title = "Scale Animations") {
    // Button press animation
    Button(
      onClick = { pressed = !pressed },
      modifier = Modifier.optimizedButtonPress(pressed = pressed, config = config)
    ) {
      Text("Press Me")
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Overshoot animation
    Button(
      onClick = { showOvershoot = !showOvershoot },
      colors = ButtonDefaults.buttonColors(containerColor = AuraGold)
    ) {
      Text(if (showOvershoot) "Hide" else "Show with Overshoot")
    }

    if (showOvershoot) {
      Box(
        modifier = Modifier
          .size(100.dp)
          .background(TantricRose)
          .scaleWithOvershoot(targetScale = 1f, config = config),
        contentAlignment = Alignment.Center
      ) {
        Text("Bounce!", color = Color.White)
      }
    }
  }
}

/**
 * Stagger Animations Test Section
 */
@Composable
fun StaggerAnimationsSection(config: AnimationConfig) {
  var visible by remember { mutableStateOf(true) }

  TestSection(title = "Stagger Animations") {
    Button(
      onClick = { visible = !visible },
      colors = ButtonDefaults.buttonColors(containerColor = ChakraGreen)
    ) {
      Text(if (visible) "Hide List" else "Show List")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      repeat(5) { index ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .staggeredScaleIn(
              index = index,
              visible = visible,
              config = config
            ),
          colors = CardDefaults.cardColors(
            containerColor = ChakraBlue.copy(alpha = 0.3f)
          )
        ) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            Text("Item ${index + 1}", color = Color.White)
          }
        }
      }
    }
  }
}

/**
 * Spiritual Animations Test Section
 */
@Composable
fun SpiritualAnimationsSection(config: AnimationConfig) {
  TestSection(title = "Spiritual Animations") {
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Breathing
      Box(
        modifier = Modifier
          .size(80.dp)
          .background(ChakraCrown)
          .optimizedBreathing(config = config),
        contentAlignment = Alignment.Center
      ) {
        Text("Breathe", color = Color.White, style = MaterialTheme.typography.bodySmall)
      }

      // Pulsing
      Box(
        modifier = Modifier
          .size(80.dp)
          .background(ChakraRed)
          .optimizedPulse(config = config),
        contentAlignment = Alignment.Center
      ) {
        Text("Pulse", color = Color.White, style = MaterialTheme.typography.bodySmall)
      }

      // Levitation
      Box(
        modifier = Modifier
          .size(80.dp)
          .background(AuraGold)
          .optimizedLevitation(config = config),
        contentAlignment = Alignment.Center
      ) {
        Text("Float", color = Color.Black, style = MaterialTheme.typography.bodySmall)
      }
    }
  }
}

/**
 * Particle Animations Test Section
 */
@Composable
fun ParticleAnimationsSection(config: AnimationConfig) {
  TestSection(title = "Particle Effects") {
    val particleCount = config.adjustParticleCount(30)

    Column {
      Text(
        text = "Particle Count: $particleCount",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Show chakra spinner with dynamic particle count
      Box(
        modifier = Modifier
          .size(150.dp)
          .background(Color.Black.copy(alpha = 0.5f))
      ) {
        if (!config.reduceMotion) {
          SpinningChakraWithParticles(
            modifier = Modifier.fillMaxSize(),
            chakraIndex = 6,
            particleCount = particleCount,
            rotationDurationMs = config.adjustDuration(8000)
          )
        } else {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            Text(
              "Particles disabled\n(Reduce Motion)",
              color = Color.Gray,
              style = MaterialTheme.typography.bodySmall
            )
          }
        }
      }
    }
  }
}

/**
 * Helper composable for test sections
 */
@Composable
fun TestSection(
  title: String,
  content: @Composable ColumnScope.() -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = Color.White.copy(alpha = 0.05f)
    )
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = StardustBlue,
        fontWeight = FontWeight.Bold
      )
      content()
    }
  }
}
