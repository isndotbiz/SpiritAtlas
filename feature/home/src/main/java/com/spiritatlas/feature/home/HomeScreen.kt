package com.spiritatlas.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.CompatibilityDisplaySize
import com.spiritatlas.core.ui.components.CompatibilityScoreDisplay
import com.spiritatlas.core.ui.components.GlassmorphCard
import com.spiritatlas.core.ui.components.SpiritualButton
import com.spiritatlas.core.ui.components.SpiritualButtonStyle
import com.spiritatlas.core.ui.components.SpiritualGradientBackground
import com.spiritatlas.core.ui.components.SpiritualGradientType
import com.spiritatlas.core.ui.components.SpiritualPullRefresh
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.ChakraBlue
import com.spiritatlas.core.ui.theme.ChakraGreen
import com.spiritatlas.core.ui.theme.ChakraOrange
import com.spiritatlas.core.ui.theme.CosmicBlue
import com.spiritatlas.core.ui.theme.IntimacyPurple
import com.spiritatlas.core.ui.theme.MysticViolet
import com.spiritatlas.core.ui.theme.SensualCoral
import com.spiritatlas.core.ui.theme.SpiritualPurple
import com.spiritatlas.core.ui.theme.TantricRose
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToConsent: () -> Unit,
    onNavigateToCompatibility: () -> Unit = {},
    consentDebugViewModel: ConsentDebugViewModel = hiltViewModel()
) {
    val consentDebug by consentDebugViewModel.debugText.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Staggered animation state for sections
    var sectionsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        sectionsVisible = true
    }

    SpiritualGradientBackground(gradientType = SpiritualGradientType.MYSTIC) {
        Scaffold(
            topBar = {
                HomeTopBar(onSettingsClick = onNavigateToConsent)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToProfile,
                    containerColor = SpiritualPurple,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Create Profile")
                }
            }
        ) { paddingValues ->
            SpiritualPullRefresh(
                isRefreshing = isRefreshing,
                onRefresh = {
                    // Simulate refresh
                    scope.launch {
                        delay(2000)
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header Section
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600)) + slideInVertically { -it / 2 }
                        ) {
                            HeaderSection()
                        }
                    }

                    // Debug Info (temporary)
                    if (consentDebug.isNotBlank()) {
                        item {
                            Text(
                                text = consentDebug,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }

                    // Daily Insights Card
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600, delayMillis = 100)) + slideInVertically { -it / 2 }
                        ) {
                            DailyInsightsCard()
                        }
                    }

                    // Quick Profile Access
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600, delayMillis = 200)) + slideInVertically { -it / 2 }
                        ) {
                            QuickProfileAccess(onNavigateToProfile = onNavigateToProfile)
                        }
                    }

                    // Transit Alerts
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600, delayMillis = 300)) + slideInVertically { -it / 2 }
                        ) {
                            TransitAlertsSection()
                        }
                    }

                    // Compatibility Quick Check
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600, delayMillis = 400)) + slideInVertically { -it / 2 }
                        ) {
                            CompatibilityQuickCheck(onSeeFullAnalysis = onNavigateToCompatibility)
                        }
                    }

                    // Spiritual Weather Widget
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600, delayMillis = 500)) + slideInVertically { -it / 2 }
                        ) {
                            SpiritualWeatherWidget()
                        }
                    }

                    // Featured Content
                    item {
                        AnimatedVisibility(
                            visible = sectionsVisible,
                            enter = fadeIn(tween(600, delayMillis = 600)) + slideInVertically { -it / 2 }
                        ) {
                            FeaturedContentSection()
                        }
                    }

                    // Bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(onSettingsClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "SpiritAtlas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = SpiritualPurple
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
private fun HeaderSection() {
    val currentTime = remember { LocalTime.now() }
    val greeting = when (currentTime.hour) {
        in 0..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        else -> "Good evening"
    }

    val moonPhase = remember { getCurrentMoonPhase() }
    val todayDate = remember {
        LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
    }

    GlassmorphCard(
        elevation = 2,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Greeting
            Text(
                text = "$greeting, Seeker",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = SpiritualPurple
            )

            // Date and Moon Phase
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = todayDate,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = getAstrologicalSignificance(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                MoonPhaseIcon(moonPhase)
            }
        }
    }
}

@Composable
private fun MoonPhaseIcon(phase: MoonPhase) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Canvas(modifier = Modifier.size(48.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2.5f

            // Draw moon
            drawCircle(
                color = Color.White.copy(alpha = 0.9f),
                radius = radius,
                center = center
            )

            // Draw shadow for phase effect
            val shadowOffset = (phase.illumination - 0.5f) * 2f * radius
            drawCircle(
                color = Color.Black.copy(alpha = 0.6f),
                radius = radius,
                center = center.copy(x = center.x + shadowOffset)
            )
        }

        Text(
            text = phase.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DailyInsightsCard() {
    var insightsRevealed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        insightsRevealed = true
    }

    GlassmorphCard(
        elevation = 2,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daily Insights",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple
                )
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = AuraGold,
                    modifier = Modifier.size(28.dp)
                )
            }

            AnimatedVisibility(
                visible = insightsRevealed,
                enter = fadeIn(tween(800)) + expandVertically()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Personalized guidance
                    InsightRow(
                        icon = Icons.Filled.Info,
                        title = "Guidance",
                        content = "Today is favorable for introspection and spiritual growth"
                    )

                    align()

                    // Lucky numbers
                    InsightRow(
                        icon = Icons.Filled.Star,
                        title = "Lucky Numbers",
                        content = "3, 7, 12, 21"
                    )

                    align()

                    // Favorable activities
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = ChakraGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Favorable: Meditation, creative work, social connections",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = null,
                                tint = ChakraOrange,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Avoid: Conflict, major decisions, overexertion",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun align() {}

@Composable
private fun InsightRow(
    icon: ImageVector,
    title: String,
    content: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = SpiritualPurple,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = SpiritualPurple
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun QuickProfileAccess(onNavigateToProfile: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Your Profiles",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sample profiles (in real app, these would come from viewModel)
            items(listOf("Sarah", "Michael", "Luna")) { name ->
                ProfileAvatarCard(
                    name = name,
                    onClick = { /* Navigate to profile detail */ },
                    onLongPress = { /* Show quick actions */ }
                )
            }

            // Add new button
            item {
                AddProfileCard(onClick = onNavigateToProfile)
            }
        }
    }
}

@Composable
private fun ProfileAvatarCard(
    name: String,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Column(
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        tryAwaitRelease()
                    },
                    onTap = { onClick() },
                    onLongPress = { onLongPress() }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(SpiritualPurple, MysticViolet)
                    )
                )
                .border(3.dp, Color.White.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun AddProfileCard(onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(SpiritualPurple, MysticViolet)
                    ),
                    shape = CircleShape
                )
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add Profile",
                tint = SpiritualPurple,
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = "Add New",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = SpiritualPurple
        )
    }
}

@Composable
private fun TransitAlertsSection() {
    GlassmorphCard(
        elevation = 2,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Planetary Transits",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple
                )
                Icon(
                    Icons.Filled.Info,
                    contentDescription = null,
                    tint = CosmicBlue
                )
            }

            // Transit items
            TransitItem(
                planet = "Mercury Retrograde",
                effect = "Communication challenges - double check messages",
                intensity = 0.7f,
                color = CosmicBlue
            )

            TransitItem(
                planet = "Venus in Leo",
                effect = "Favorable for romance and creative expression",
                intensity = 0.9f,
                color = TantricRose
            )

            // Visual transit diagram
            TransitDiagram()
        }
    }
}

@Composable
private fun TransitItem(
    planet: String,
    effect: String,
    intensity: Float,
    color: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = planet,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
            Text(
                text = "${(intensity * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
        }

        Text(
            text = effect,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        LinearProgressIndicator(
            progress = { intensity },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun TransitDiagram() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val orbits = 3
        val maxRadius = size.minDimension / 2.5f

        // Draw orbits
        for (i in 1..orbits) {
            val radius = (maxRadius / orbits) * i
            drawCircle(
                color = SpiritualPurple.copy(alpha = 0.2f),
                radius = radius,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f)
            )
        }

        // Draw planets at various positions
        val planets = listOf(
            Pair(0.4f, 45f),
            Pair(0.7f, 120f),
            Pair(1f, 200f)
        )

        planets.forEachIndexed { index, (radiusRatio, angle) ->
            val planetRadius = maxRadius * radiusRatio
            val radian = Math.toRadians(angle.toDouble())
            val planetX = center.x + planetRadius * cos(radian).toFloat()
            val planetY = center.y + planetRadius * sin(radian).toFloat()

            val planetColors = listOf(CosmicBlue, TantricRose, AuraGold)

            drawCircle(
                color = planetColors[index].copy(alpha = 0.3f),
                radius = 12f,
                center = Offset(planetX, planetY)
            )

            drawCircle(
                color = planetColors[index],
                radius = 6f,
                center = Offset(planetX, planetY)
            )
        }

        // Draw sun in center
        drawCircle(
            color = AuraGold.copy(alpha = 0.3f),
            radius = 16f,
            center = center
        )
        drawCircle(
            color = AuraGold,
            radius = 10f,
            center = center
        )
    }
}

@Composable
private fun CompatibilityQuickCheck(onSeeFullAnalysis: () -> Unit) {
    var profile1 by remember { mutableStateOf<String?>(null) }
    var profile2 by remember { mutableStateOf<String?>(null) }
    var showScore by remember { mutableStateOf(false) }
    val score = remember { 87.5 }

    GlassmorphCard(
        elevation = 2,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Compatibility Check",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SpiritualPurple
            )

            // Profile selectors
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileSelector(
                    label = "Profile 1",
                    selectedProfile = profile1,
                    onProfileSelected = { profile1 = it },
                    modifier = Modifier.weight(1f)
                )

                ProfileSelector(
                    label = "Profile 2",
                    selectedProfile = profile2,
                    onProfileSelected = { profile2 = it },
                    modifier = Modifier.weight(1f)
                )
            }

            // Compatibility score
            if (profile1 != null && profile2 != null) {
                LaunchedEffect(profile1, profile2) {
                    delay(500)
                    showScore = true
                }

                AnimatedVisibility(
                    visible = showScore,
                    enter = fadeIn(tween(600)) + scaleIn(tween(600))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CompatibilityScoreDisplay(
                            score = score,
                            size = CompatibilityDisplaySize.LARGE
                        )

                        Text(
                            text = "Excellent Match!",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = CosmicBlue
                        )

                        SpiritualButton(
                            onClick = onSeeFullAnalysis,
                            text = "See Full Analysis",
                            style = SpiritualButtonStyle.PRIMARY,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileSelector(
    label: String,
    selectedProfile: String?,
    onProfileSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val profiles = listOf("Sarah", "Michael", "Luna")
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { expanded = true }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedProfile ?: "Select",
                style = MaterialTheme.typography.bodyMedium,
                color = if (selectedProfile != null) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            profiles.forEach { profile ->
                DropdownMenuItem(
                    text = { Text(profile) },
                    onClick = {
                        onProfileSelected(profile)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SpiritualWeatherWidget() {
    val energyLevel = remember { 0.75f }

    GlassmorphCard(
        elevation = 2,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Spiritual Weather",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple
                )
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Energy visualization
            EnergyLevelVisualization(energyLevel)

            // Energy description
            val energyDescription = when {
                energyLevel >= 0.8f -> "High cosmic energy - perfect for manifestation"
                energyLevel >= 0.6f -> "Balanced energy - good for most activities"
                energyLevel >= 0.4f -> "Moderate energy - focus on grounding practices"
                else -> "Low energy - prioritize rest and restoration"
            }

            Text(
                text = energyDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Tips
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SpiritualPurple.copy(alpha = 0.1f))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Recommended Activities",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = SpiritualPurple
                )
                Text(
                    text = "• Morning meditation\n• Creative projects\n• Connect with loved ones",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun EnergyLevelVisualization(energyLevel: Float) {
    val animatedEnergy by animateFloatAsState(
        targetValue = energyLevel,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = size.minDimension / 2.5f

            // Draw energy rings
            for (i in 1..3) {
                val radius = maxRadius * (i / 3f)
                val alpha = if (i / 3f <= animatedEnergy) 0.6f else 0.2f

                drawCircle(
                    color = SpiritualPurple.copy(alpha = alpha),
                    radius = radius,
                    center = center,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                )
            }

            // Draw energy indicator
            drawCircle(
                color = AuraGold.copy(alpha = 0.3f),
                radius = maxRadius * animatedEnergy * 1.2f,
                center = center
            )

            drawCircle(
                color = AuraGold,
                radius = maxRadius * animatedEnergy,
                center = center
            )
        }

        Text(
            text = "${(animatedEnergy * 100).toInt()}%",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun FeaturedContentSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Learn & Explore",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getFeaturedContent()) { content ->
                FeaturedContentCard(content)
            }
        }
    }
}

@Composable
private fun FeaturedContentCard(content: FeaturedContent) {
    GlassmorphCard(
        elevation = 1,
        modifier = Modifier
            .width(280.dp)
            .clickable { /* Navigate to content */ }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = content.gradientColors
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    content.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Title
            Text(
                text = content.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Description
            Text(
                text = content.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            // Read more
            Text(
                text = "Read more →",
                style = MaterialTheme.typography.labelMedium,
                color = SpiritualPurple,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Helper data classes and functions

private data class MoonPhase(
    val name: String,
    val illumination: Float
)

private fun getCurrentMoonPhase(): MoonPhase {
    // Simplified moon phase calculation
    // In production, use proper astronomical calculation
    return MoonPhase("Waxing Gibbous", 0.75f)
}

private fun getAstrologicalSignificance(): String {
    // In production, calculate based on actual date and astrological data
    return "Sun in Sagittarius"
}

private data class FeaturedContent(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color>
)

private fun getFeaturedContent(): List<FeaturedContent> {
    return listOf(
        FeaturedContent(
            title = "Understanding Numerology",
            description = "Discover the hidden meanings behind numbers in your life",
            icon = Icons.Filled.Star,
            gradientColors = listOf(SpiritualPurple, MysticViolet)
        ),
        FeaturedContent(
            title = "Birth Chart Basics",
            description = "Learn how planetary positions at birth shape your personality",
            icon = Icons.Filled.Star,
            gradientColors = listOf(CosmicBlue, SpiritualPurple)
        ),
        FeaturedContent(
            title = "Chakra Balancing",
            description = "Align your energy centers for optimal wellbeing",
            icon = Icons.Filled.Favorite,
            gradientColors = listOf(ChakraGreen, ChakraBlue)
        ),
        FeaturedContent(
            title = "Moon Phases & You",
            description = "Work with lunar cycles for manifestation and release",
            icon = Icons.Filled.Star,
            gradientColors = listOf(TantricRose, IntimacyPurple)
        ),
        FeaturedContent(
            title = "Tantric Wisdom",
            description = "Ancient practices for modern relationships",
            icon = Icons.Filled.Favorite,
            gradientColors = listOf(TantricRose, SensualCoral)
        )
    )
}
