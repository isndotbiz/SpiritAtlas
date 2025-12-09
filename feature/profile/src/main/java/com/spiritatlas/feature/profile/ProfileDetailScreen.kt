package com.spiritatlas.feature.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ayurveda.Dosha
import com.spiritatlas.core.humandesign.EnergyProfileCalculator
import com.spiritatlas.domain.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.*

/**
 * ProfileDetailScreen - Beautiful READ-ONLY view of a spiritual profile
 *
 * This is the comprehensive detail/view screen showing all calculated insights.
 * Use ProfileScreen.kt for editing the profile data.
 *
 * Features:
 * - Hero header with avatar and zodiac ring
 * - Quick stats badges
 * - Astrology, Numerology, Human Design, Ayurveda sections
 * - AI-generated insights
 * - Action buttons (Edit, Share, Compare, Delete)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailScreen(
    profileId: String,
    onNavigateBack: () -> Unit,
    onEditProfile: () -> Unit,
    onCompareProfile: () -> Unit,
    onShareProfile: () -> Unit,
    onDeleteProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val profile = uiState.currentProfile

    LaunchedEffect(profileId) {
        viewModel.loadProfile(profileId)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProfileDetailTopBar(
                profile = profile,
                scrollBehavior = scrollBehavior,
                onNavigateBack = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditProfile,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
            }
        }
    ) { paddingValues ->
        ProfileDetailContent(
            profile = profile,
            paddingValues = paddingValues,
            onCompareProfile = onCompareProfile,
            onShareProfile = onShareProfile,
            onDeleteProfile = onDeleteProfile
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileDetailTopBar(
    profile: UserProfile,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateBack: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Column {
                Text(
                    text = profile.displayName ?: profile.name ?: "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Spiritual Profile",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Composable
private fun ProfileDetailContent(
    profile: UserProfile,
    paddingValues: PaddingValues,
    onCompareProfile: () -> Unit,
    onShareProfile: () -> Unit,
    onDeleteProfile: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero Header
        item {
            HeroHeader(profile)
        }

        // 2. Quick Stats Row
        item {
            QuickStatsRow(profile)
        }

        // 3. Astrology Section
        if (profile.birthDateTime != null && profile.birthPlace != null) {
            item {
                AstrologySection(profile)
            }
        }

        // 4. Numerology Section
        if (profile.name != null) {
            item {
                NumerologySection(profile)
            }
        }

        // 5. Human Design Section
        if (profile.birthDateTime != null) {
            item {
                HumanDesignSection(profile)
            }
        }

        // 6. Ayurveda Section
        item {
            AyurvedaSection(profile)
        }

        // 7. AI Insights Section
        if (profile.enrichmentResult != null) {
            item {
                AIInsightsSection(profile)
            }
        }

        // 8. Actions Footer
        item {
            ActionsFooter(
                onCompare = onCompareProfile,
                onShare = onShareProfile,
                onDelete = onDeleteProfile
            )
        }
    }
}

// ==================== SECTION 1: HERO HEADER ====================

@Composable
private fun HeroHeader(profile: UserProfile) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            // Background gradient
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8B5CF6).copy(alpha = 0.3f),
                            Color(0xFFEC4899).copy(alpha = 0.2f),
                            Color(0xFF3B82F6).copy(alpha = 0.1f)
                        )
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Large avatar with zodiac ring
                AvatarWithZodiacRing(profile)

                Spacer(modifier = Modifier.height(16.dp))

                // Name with gradient text
                Text(
                    text = profile.displayName ?: profile.name ?: "Unknown",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B5CF6)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Key numbers row
                KeyNumbersRow(profile)
            }
        }
    }
}

@Composable
private fun AvatarWithZodiacRing(profile: UserProfile) {
    val infiniteTransition = rememberInfiniteTransition(label = "zodiac")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Zodiac ring
        Canvas(
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer { rotationZ = rotation }
        ) {
            val radius = size.width / 2
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF8B5CF6),
                        Color(0xFFEC4899),
                        Color(0xFF3B82F6),
                        Color(0xFF8B5CF6)
                    )
                ),
                radius = radius,
                style = Stroke(width = 4f)
            )

            // Zodiac symbols positions (simplified)
            val symbols = listOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓")
            symbols.forEachIndexed { index, _ ->
                val angle = (index * 30 - 90).toDouble() * PI / 180
                val x = (center.x + radius * 0.8f * cos(angle)).toFloat()
                val y = (center.y + radius * 0.8f * sin(angle)).toFloat()

                drawCircle(
                    color = Color.White.copy(alpha = 0.3f),
                    radius = 4f,
                    center = Offset(x, y)
                )
            }
        }

        // Avatar circle
        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = (profile.displayName?.firstOrNull() ?: profile.name?.firstOrNull() ?: "?").toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun KeyNumbersRow(profile: UserProfile) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Life Path (calculated from birth date)
        profile.birthDateTime?.let { birthDate ->
            val lifePathNumber = calculateLifePathNumber(birthDate)
            KeyNumberBadge("Life Path", lifePathNumber.toString())
        }

        // Dosha
        KeyNumberBadge("Dosha", "V/P/K")

        // Sun Sign placeholder
        profile.birthDateTime?.let {
            val sunSign = calculateSunSign(it)
            KeyNumberBadge("Sun", sunSign)
        }
    }
}

@Composable
private fun KeyNumberBadge(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}

// ==================== SECTION 2: QUICK STATS ROW ====================

@Composable
private fun QuickStatsRow(profile: UserProfile) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        // Zodiac badge
        profile.birthDateTime?.let { birthDate ->
            item {
                QuickStatBadge(
                    icon = "♈",
                    title = calculateSunSign(birthDate),
                    subtitle = "Sun Sign",
                    color = Color(0xFFEF4444)
                )
            }
        }

        // Numerology badge
        profile.name?.let {
            item {
                QuickStatBadge(
                    icon = calculateLifePathNumber(profile.birthDateTime ?: LocalDateTime.now()).toString(),
                    title = "Life Path",
                    subtitle = "Numerology",
                    color = Color(0xFF8B5CF6)
                )
            }
        }

        // HD Type badge
        profile.birthDateTime?.let {
            item {
                QuickStatBadge(
                    icon = "⚡",
                    title = "Initiator",
                    subtitle = "HD Type",
                    color = Color(0xFF3B82F6)
                )
            }
        }

        // Dosha badge
        item {
            QuickStatBadge(
                icon = "🌿",
                title = "Vata",
                subtitle = "Dosha",
                color = Color(0xFF22C55E)
            )
        }
    }
}

@Composable
private fun QuickStatBadge(
    icon: String,
    title: String,
    subtitle: String,
    color: Color
) {
    var showTooltip by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { showTooltip = !showTooltip },
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ==================== SECTION 3: ASTROLOGY ====================

@Composable
private fun AstrologySection(profile: UserProfile) {
    var expanded by remember { mutableStateOf(false) }

    ExpandableProfileSection(
        title = "⭐ Astrology",
        subtitle = "Celestial Blueprint",
        expanded = expanded,
        onToggle = { expanded = !expanded }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mini birth chart visualization
            MiniBirthChart(profile)

            // Sun, Moon, Rising
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                profile.birthDateTime?.let { birthDate ->
                    AstroSignCard("Sun", calculateSunSign(birthDate), Color(0xFFFBBF24))
                    AstroSignCard("Moon", calculateMoonSign(birthDate), Color(0xFF8B5CF6))
                    AstroSignCard("Rising", calculateRisingSign(birthDate), Color(0xFF3B82F6))
                }
            }

            // Key planetary positions
            if (expanded) {
                PlanetaryPositions(profile)
            }
        }
    }
}

@Composable
private fun MiniBirthChart(profile: UserProfile) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2 - 20

            // Outer circle
            drawCircle(
                color = Color(0xFF8B5CF6).copy(alpha = 0.2f),
                radius = radius,
                center = center,
                style = Stroke(width = 2f)
            )

            // 12 houses
            for (i in 0 until 12) {
                val angle = (i * 30 - 90).toDouble() * PI / 180
                val startX = center.x
                val startY = center.y
                val endX = (center.x + radius * cos(angle)).toFloat()
                val endY = (center.y + radius * sin(angle)).toFloat()

                drawLine(
                    color = Color(0xFF8B5CF6).copy(alpha = 0.15f),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 1f
                )
            }

            // Inner circle
            drawCircle(
                color = Color(0xFFEC4899).copy(alpha = 0.1f),
                radius = radius * 0.5f,
                center = center
            )
        }

        // Center text
        Text(
            text = "Birth Chart",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AstroSignCard(label: String, sign: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            color = color.copy(alpha = 0.2f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = getZodiacSymbol(sign),
                    style = MaterialTheme.typography.headlineMedium,
                    color = color
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = sign,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PlanetaryPositions(profile: UserProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Planetary Positions",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        PlanetRow("Mercury", "Gemini", "15°24'", Color(0xFF8B5CF6))
        PlanetRow("Venus", "Taurus", "22°18'", Color(0xFFEC4899))
        PlanetRow("Mars", "Aries", "8°42'", Color(0xFFEF4444))
    }
}

@Composable
private fun PlanetRow(planet: String, sign: String, degree: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )
            Text(
                text = planet,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = sign,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = degree,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ==================== SECTION 4: NUMEROLOGY ====================

@Composable
private fun NumerologySection(profile: UserProfile) {
    var expanded by remember { mutableStateOf(false) }

    ExpandableProfileSection(
        title = "🔢 Numerology",
        subtitle = "Sacred Numbers",
        expanded = expanded,
        onToggle = { expanded = !expanded }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Life Path with interpretation
            val lifePathNumber = calculateLifePathNumber(profile.birthDateTime ?: LocalDateTime.now())
            LifePathDisplay(lifePathNumber)

            // Core numbers grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NumerologyNumberCard("Expression", "5", Modifier.weight(1f))
                NumerologyNumberCard("Soul Urge", "7", Modifier.weight(1f))
            }

            if (expanded) {
                // Personal year cycle
                PersonalYearDisplay(getCurrentPersonalYear(profile))

                // Lucky numbers
                LuckyNumbersDisplay()
            }
        }
    }
}

@Composable
private fun LifePathDisplay(number: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getNumerologyColorForProfile(number).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Large number circle
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                getNumerologyColorForProfile(number).copy(alpha = 0.3f),
                                getNumerologyColorForProfile(number).copy(alpha = 0.1f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = getNumerologyColorForProfile(number)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Life Path Number",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = getLifePathMeaning(number),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun NumerologyNumberCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = getNumerologyColorForProfile(value.toIntOrNull() ?: 1)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PersonalYearDisplay(year: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.size(40.dp)
            )
            Column {
                Text(
                    text = "Personal Year $year",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Year of ${getPersonalYearTheme(year)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun LuckyNumbersDisplay() {
    Column {
        Text(
            text = "Lucky Numbers",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf(3, 7, 11, 21, 33)) { number ->
                Surface(
                    shape = CircleShape,
                    color = getNumerologyColorForProfile(number).copy(alpha = 0.2f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = number.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = getNumerologyColorForProfile(number)
                        )
                    }
                }
            }
        }
    }
}

// ==================== SECTION 5: HUMAN DESIGN ====================

@Composable
private fun HumanDesignSection(profile: UserProfile) {
    var expanded by remember { mutableStateOf(false) }

    ExpandableProfileSection(
        title = "⚡ Human Design",
        subtitle = "Energy Blueprint",
        expanded = expanded,
        onToggle = { expanded = !expanded }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Type and Strategy
            profile.birthDateTime?.let { birthDate ->
                val energyType = calculateEnergyType(birthDate)
                HumanDesignTypeCard(energyType)
            }

            // Authority
            InfoCard("Authority", "Emotional", "Wait for emotional clarity")

            // Profile
            InfoCard("Profile", "3/5", "Experimenter / Heretic")

            if (expanded) {
                // Mini bodygraph placeholder
                MiniBodygraph()
            }
        }
    }
}

@Composable
private fun HumanDesignTypeCard(type: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3B82F6).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                color = Color(0xFF3B82F6).copy(alpha = 0.2f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "⚡",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B82F6)
                )
                Text(
                    text = "Strategy: Inform before acting",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MiniBodygraph() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            // Simplified bodygraph representation
            Canvas(modifier = Modifier.size(120.dp)) {
                val centerX = size.width / 2
                val centerY = size.height / 2

                // Head center
                drawCircle(
                    color = Color(0xFFFBBF24),
                    radius = 15f,
                    center = Offset(centerX, 20f)
                )

                // Throat center
                drawCircle(
                    color = Color(0xFF22C55E),
                    radius = 18f,
                    center = Offset(centerX, centerY - 30)
                )

                // Heart center
                drawCircle(
                    color = Color(0xFFEF4444),
                    radius = 15f,
                    center = Offset(centerX, centerY)
                )

                // Sacral center
                drawCircle(
                    color = Color(0xFFEF4444),
                    radius = 20f,
                    center = Offset(centerX, centerY + 40)
                )
            }

            Text(
                text = "Bodygraph",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
            )
        }
    }
}

// ==================== SECTION 6: AYURVEDA ====================

@Composable
private fun AyurvedaSection(profile: UserProfile) {
    var expanded by remember { mutableStateOf(false) }

    ExpandableProfileSection(
        title = "🌿 Ayurveda",
        subtitle = "Constitutional Balance",
        expanded = expanded,
        onToggle = { expanded = !expanded }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dosha balance visualization
            DoshaBalanceVisualization(35, 40, 25)

            if (expanded) {
                // Diet recommendations
                RecommendationCard(
                    title = "Diet Recommendations",
                    icon = Icons.Default.Restaurant,
                    items = listOf(
                        "Favor warm, cooked foods",
                        "Avoid cold, raw vegetables",
                        "Include warming spices"
                    )
                )

                // Lifestyle tips
                RecommendationCard(
                    title = "Lifestyle Tips",
                    icon = Icons.Default.SelfImprovement,
                    items = listOf(
                        "Maintain regular routine",
                        "Practice grounding exercises",
                        "Get adequate rest"
                    )
                )

                // Season awareness
                InfoCard(
                    "Seasonal Balance",
                    "Fall/Winter",
                    "Vata season - increase warmth and routine"
                )
            }
        }
    }
}

@Composable
private fun DoshaBalanceVisualization(vata: Int, pitta: Int, kapha: Int) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Dosha Balance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Vata bar
            DoshaBar("Vata (Air)", vata, Color(0xFF8B5CF6))

            // Pitta bar
            DoshaBar("Pitta (Fire)", pitta, Color(0xFFEF4444))

            // Kapha bar
            DoshaBar("Kapha (Earth)", kapha, Color(0xFF22C55E))

            Text(
                text = "Primary: Pitta (40%)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun DoshaBar(label: String, percentage: Int, color: Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        LinearProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun RecommendationCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    items: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null)
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            items.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                MaterialTheme.colorScheme.onSecondaryContainer,
                                CircleShape
                            )
                    )
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

// ==================== SECTION 7: AI INSIGHTS ====================

@Composable
private fun AIInsightsSection(profile: UserProfile) {
    var expanded by remember { mutableStateOf(true) }

    ExpandableProfileSection(
        title = "✨ AI Insights",
        subtitle = "Synthesized Wisdom",
        expanded = expanded,
        onToggle = { expanded = !expanded }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // AI-generated synthesis
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF8B5CF6).copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = profile.enrichmentResult ?: "No AI insights generated yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 24.sp
                    )

                    profile.enrichmentGeneratedAt?.let { timestamp ->
                        Text(
                            text = "Generated: ${formatTimestamp(timestamp)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Personality traits
            InsightCard(
                title = "Core Traits",
                items = listOf("Creative", "Intuitive", "Leader", "Compassionate")
            )

            // Life purpose insights
            InsightCard(
                title = "Life Purpose",
                items = listOf("Teaching", "Healing", "Innovation", "Service")
            )
        }
    }
}

@Composable
private fun InsightCard(title: String, items: List<String>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { trait ->
                    AssistChip(
                        onClick = { },
                        label = { Text(trait) }
                    )
                }
            }
        }
    }
}

// ==================== SECTION 8: ACTIONS FOOTER ====================

@Composable
private fun ActionsFooter(
    onCompare: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Share button
            OutlinedButton(
                onClick = onShare,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share Profile")
            }

            // Compare button
            OutlinedButton(
                onClick = onCompare,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CompareArrows, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compare with Another")
            }

            // Delete button
            var showDeleteDialog by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete Profile")
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Delete Profile?") },
                    text = { Text("This action cannot be undone.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDeleteDialog = false
                                onDelete()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

// ==================== HELPER COMPONENTS ====================

@Composable
private fun ExpandableProfileSection(
    title: String,
    subtitle: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = onToggle) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            // Content
            content()
        }
    }
}

@Composable
private fun InfoCard(title: String, value: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ==================== UTILITY FUNCTIONS ====================

private fun calculateLifePathNumber(dateTime: LocalDateTime): Int {
    val day = dateTime.dayOfMonth
    val month = dateTime.monthValue
    val year = dateTime.year

    fun reduceToSingleDigit(num: Int): Int {
        var result = num
        while (result > 9 && result != 11 && result != 22 && result != 33) {
            result = result.toString().map { it.toString().toInt() }.sum()
        }
        return result
    }

    val daySum = reduceToSingleDigit(day)
    val monthSum = reduceToSingleDigit(month)
    val yearSum = reduceToSingleDigit(year)

    return reduceToSingleDigit(daySum + monthSum + yearSum)
}

private fun calculateSunSign(dateTime: LocalDateTime): String {
    val month = dateTime.monthValue
    val day = dateTime.dayOfMonth

    return when {
        (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
        (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
        (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
        (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
        (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
        (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
        (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
        (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
        (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
        (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Capricorn"
        (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
        else -> "Pisces"
    }
}

private fun calculateMoonSign(dateTime: LocalDateTime): String {
    // Simplified - would need lunar tables for accuracy
    return "Scorpio"
}

private fun calculateRisingSign(dateTime: LocalDateTime): String {
    // Simplified - would need birth time and location for accuracy
    return "Libra"
}

private fun calculateEnergyType(dateTime: LocalDateTime): String {
    val day = dateTime.dayOfMonth
    val month = dateTime.monthValue
    val year = dateTime.year
    val sum = (day + month + year) % 5

    return when (sum) {
        0 -> "Initiator"
        1 -> "Builder"
        2 -> "Guide"
        3 -> "Refiner"
        else -> "Observer"
    }
}

private fun getZodiacSymbol(sign: String): String {
    return when (sign) {
        "Aries" -> "♈"
        "Taurus" -> "♉"
        "Gemini" -> "♊"
        "Cancer" -> "♋"
        "Leo" -> "♌"
        "Virgo" -> "♍"
        "Libra" -> "♎"
        "Scorpio" -> "♏"
        "Sagittarius" -> "♐"
        "Capricorn" -> "♑"
        "Aquarius" -> "♒"
        "Pisces" -> "♓"
        else -> "✨"
    }
}

private fun getNumerologyColorForProfile(number: Int): Color {
    return when (number) {
        1 -> Color(0xFFEF4444)
        2 -> Color(0xFFF97316)
        3 -> Color(0xFFFBBF24)
        4 -> Color(0xFF22C55E)
        5 -> Color(0xFF3B82F6)
        6 -> Color(0xFF6366F1)
        7 -> Color(0xFF8B5CF6)
        8 -> Color(0xFFFBBF24)
        9 -> Color(0xFF9CA3AF)
        11, 22, 33 -> Color(0xFFEC4899)
        else -> Color(0xFF9CA3AF)
    }
}

private fun getLifePathMeaning(number: Int): String {
    return when (number) {
        1 -> "The Leader - Independent, pioneering, and innovative"
        2 -> "The Peacemaker - Diplomatic, cooperative, and sensitive"
        3 -> "The Creative - Expressive, optimistic, and inspiring"
        4 -> "The Builder - Practical, disciplined, and hardworking"
        5 -> "The Freedom Seeker - Adventurous, versatile, and dynamic"
        6 -> "The Nurturer - Responsible, loving, and service-oriented"
        7 -> "The Seeker - Analytical, spiritual, and introspective"
        8 -> "The Powerhouse - Ambitious, successful, and material"
        9 -> "The Humanitarian - Compassionate, generous, and idealistic"
        11 -> "The Illuminator - Intuitive, visionary, and inspirational"
        22 -> "The Master Builder - Powerful, practical, and transformative"
        33 -> "The Master Teacher - Nurturing, responsible, and selfless"
        else -> "Your unique spiritual path"
    }
}

private fun getCurrentPersonalYear(profile: UserProfile): Int {
    val currentYear = LocalDateTime.now().year
    val birthDay = profile.birthDateTime?.dayOfMonth ?: 1
    val birthMonth = profile.birthDateTime?.monthValue ?: 1

    fun reduceToSingleDigit(num: Int): Int {
        var result = num
        while (result > 9 && result != 11 && result != 22 && result != 33) {
            result = result.toString().map { it.toString().toInt() }.sum()
        }
        return result
    }

    val daySum = reduceToSingleDigit(birthDay)
    val monthSum = reduceToSingleDigit(birthMonth)
    val yearSum = reduceToSingleDigit(currentYear)

    return reduceToSingleDigit(daySum + monthSum + yearSum)
}

private fun getPersonalYearTheme(year: Int): String {
    return when (year) {
        1 -> "New Beginnings"
        2 -> "Cooperation"
        3 -> "Creativity"
        4 -> "Foundation Building"
        5 -> "Change and Freedom"
        6 -> "Love and Responsibility"
        7 -> "Spiritual Growth"
        8 -> "Power and Success"
        9 -> "Completion"
        else -> "Transformation"
    }
}

private fun formatTimestamp(timestamp: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")
    return timestamp.format(formatter)
}
