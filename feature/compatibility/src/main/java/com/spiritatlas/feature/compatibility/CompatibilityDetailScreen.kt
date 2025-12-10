package com.spiritatlas.feature.compatibility

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.core.ui.components.CosmicConnectionBackground
import com.spiritatlas.core.ui.components.SimpleSpiritualBackground
import com.spiritatlas.core.ui.components.ZodiacImage
import com.spiritatlas.core.ui.components.ProgressiveBackgroundImage
import com.spiritatlas.core.ui.theme.*
import com.spiritatlas.domain.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*
import kotlin.random.Random

/**
 * Comprehensive Compatibility Detail Screen for SpiritAtlas
 *
 * Features:
 * 1. Hero Section with animated profiles and score
 * 2. 12-Dimension Radar Chart
 * 3. Synastry Highlights (Strengths & Challenges)
 * 4. Numerology Match Analysis
 * 5. Astrological Synastry
 * 6. Human Design Connection
 * 7. Ayurvedic Balance
 * 8. Tantric Connection (with consent)
 * 9. Action Plans (Today, Week, Ongoing)
 * 10. Share & Save Options
 */

@Composable
fun CompatibilityDetailScreen(
    report: CompatibilityReport,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier,
    viewMode: CompatibilityViewMode = CompatibilityViewMode.VERTICAL_SCROLL
) {
    val context = LocalContext.current

    // Compatibility screen with cosmic connection background
    CosmicConnectionBackground {
        Scaffold(
            topBar = {
                CompatibilityDetailTopBar(
                    onBack = onBack,
                    onSave = onSave,
                    onShare = onShare
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            when (viewMode) {
                CompatibilityViewMode.VERTICAL_SCROLL -> {
                    VerticalScrollView(
                        report = report,
                        modifier = modifier.padding(paddingValues)
                    )
                }
                CompatibilityViewMode.HORIZONTAL_PAGER -> {
                    HorizontalPagerView(
                        report = report,
                        modifier = modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

// ===========================================
// TOP BAR
// ===========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompatibilityDetailTopBar(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Compatibility Analysis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Save to favorites",
                    tint = TantricRose
                )
            }
            IconButton(onClick = onShare) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share analysis"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

// ===========================================
// VERTICAL SCROLL VIEW
// ===========================================

@Composable
private fun VerticalScrollView(
    report: CompatibilityReport,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 1. Hero Section
        HeroSection(report = report)

        // 2. Score Breakdown with Radar Chart
        ScoreBreakdownSection(report = report)

        // 3. Synastry Highlights
        SynastryHighlightsSection(report = report)

        // 4. Numerology Match
        NumerologyMatchSection(report = report)

        // 5. Astrological Synastry
        AstrologicalSynastrySection(report = report)

        // 6. Human Design Connection
        HumanDesignConnectionSection(report = report)

        // 7. Ayurvedic Balance
        AyurvedicBalanceSection(report = report)

        // 8. Tantric Connection
        if (shouldShowTantricSection(report)) {
            TantricConnectionSection(report = report)
        }

        // 9. Action Plans
        ActionPlansSection(report = report)

        // Bottom spacing
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ===========================================
// HORIZONTAL PAGER VIEW
// ===========================================

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerView(
    report: CompatibilityReport,
    modifier: Modifier = Modifier
) {
    val sections = buildList {
        add("Hero")
        add("Scores")
        add("Highlights")
        add("Numerology")
        add("Astrology")
        add("Human Design")
        add("Ayurveda")
        if (shouldShowTantricSection(report)) add("Tantric")
        add("Actions")
    }

    val pagerState = rememberPagerState(pageCount = { sections.size })

    Column(modifier = modifier.fillMaxSize()) {
        // Page indicator
        PagerIndicator(
            currentPage = pagerState.currentPage,
            pageCount = sections.size,
            sections = sections,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                when (page) {
                    0 -> HeroSection(report = report)
                    1 -> ScoreBreakdownSection(report = report)
                    2 -> SynastryHighlightsSection(report = report)
                    3 -> NumerologyMatchSection(report = report)
                    4 -> AstrologicalSynastrySection(report = report)
                    5 -> HumanDesignConnectionSection(report = report)
                    6 -> AyurvedicBalanceSection(report = report)
                    7 -> if (shouldShowTantricSection(report)) {
                        TantricConnectionSection(report = report)
                    } else {
                        ActionPlansSection(report = report)
                    }
                    8 -> ActionPlansSection(report = report)
                }
            }
        }
    }
}

@Composable
private fun PagerIndicator(
    currentPage: Int,
    pageCount: Int,
    sections: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(sections.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (currentPage == index) 10.dp else 6.dp)
                        .background(
                            if (currentPage == index) SpiritualPurple else Color.Gray.copy(alpha = 0.3f),
                            CircleShape
                        )
                )
                if (currentPage == index) {
                    Text(
                        text = sections[index],
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ===========================================
// SECTION 1: HERO SECTION
// ===========================================

@Composable
private fun HeroSection(report: CompatibilityReport) {
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(800)
        showCelebration = report.overallScore.totalScore >= 80
    }

    // INTEGRATION: ProgressiveBackgroundImage for compatibility hero with blur-up effect
    // Using spiritual background that progressively loads with LQIP
    // TODO: Pass drawable resources from app module as parameters
    /*
    ProgressiveBackgroundImage(
        backgroundResourceId = com.spiritatlas.core.ui.R.drawable.img_003_splash_screen_background,
        lqipResourceId = com.spiritatlas.core.ui.R.drawable.img_006_loading_spinner_icon,
        modifier = Modifier.fillMaxWidth(),
        alpha = 0.25f, // Subtle background that doesn't compete with content
        dimAmount = 0.7f, // Dim for readability
        contentScale = ContentScale.Crop
    ) {
    */
        GlassmorphCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = 3
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Avatars with Connection Animation
            ProfileAvatarsWithConnection(
                profile1 = report.profileA,
                profile2 = report.profileB,
                score = report.overallScore.totalScore
            )

            // Animated Compatibility Score
            AnimatedCompatibilityScore(
                score = report.overallScore.totalScore,
                level = report.compatibilityLevel
            )

            // Celebration Effect for High Scores
            AnimatedVisibility(
                visible = showCelebration,
                enter = scaleIn() + fadeIn()
            ) {
                ConfettiEffect(modifier = Modifier.height(100.dp))
            }

            // Level Description
            Text(
                text = getLevelDescription(report.compatibilityLevel),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
    // } // End ProgressiveBackgroundImage - TODO: Re-enable when drawable resources are available
}

@Composable
private fun ProfileAvatarsWithConnection(
    profile1: UserProfile,
    profile2: UserProfile,
    score: Double
) {
    var showConnection by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        showConnection = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        // Connection Animation
        AnimatedVisibility(
            visible = showConnection,
            enter = fadeIn(tween(1000))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawEnergyConnection(score)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile 1 Avatar
            ProfileAvatar(
                name = profile1.displayName ?: profile1.profileName,
                zodiacSign = getZodiacSign(profile1)
            )

            // Energy Icon in Center
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .scale(if (showConnection) 1f else 0f)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                getScoreColor(score).copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getEnergyEmoji(score),
                    fontSize = 32.sp
                )
            }

            // Profile 2 Avatar
            ProfileAvatar(
                name = profile2.displayName ?: profile2.profileName,
                zodiacSign = getZodiacSign(profile2)
            )
        }
    }
}

@Composable
private fun ProfileAvatar(
    name: String,
    zodiacSign: String
) {
    var isAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isAnimated = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.scale(if (isAnimated) 1f else 0.8f)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MysticViolet
                        )
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.firstOrNull()?.uppercase() ?: "?",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = zodiacSign,
            fontSize = 20.sp
        )
    }
}

private fun DrawScope.drawEnergyConnection(score: Double) {
    val center = Offset(size.width / 2, size.height / 2)
    val leftCircle = Offset(size.width * 0.25f, size.height / 2)
    val rightCircle = Offset(size.width * 0.75f, size.height / 2)

    val color = getScoreColor(score)

    // Multiple connection lines based on score
    val connectionCount = ((score / 100.0) * 5).toInt().coerceAtLeast(1)

    for (i in 0 until connectionCount) {
        val offsetY = (i - connectionCount / 2) * 15f

        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(
                    color.copy(alpha = 0.2f),
                    color.copy(alpha = 0.6f),
                    color.copy(alpha = 0.2f)
                ),
                start = leftCircle,
                end = rightCircle
            ),
            start = leftCircle.copy(y = leftCircle.y + offsetY),
            end = rightCircle.copy(y = rightCircle.y + offsetY),
            strokeWidth = 3f
        )
    }
}

@Composable
private fun AnimatedCompatibilityScore(
    score: Double,
    level: CompatibilityLevel
) {
    var animatedScore by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(score) {
        animate(
            initialValue = 0f,
            targetValue = score.toFloat(),
            animationSpec = tween(2000, easing = FastOutSlowInEasing)
        ) { value, _ ->
            animatedScore = value
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Large Score Display
        Text(
            text = "${animatedScore.toInt()}%",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = getScoreColor(score)
        )

        // Level with Icon
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = level.icon, fontSize = 24.sp)
            Text(
                text = level.displayName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = getScoreColor(score)
            )
        }
    }
}

// ===========================================
// SECTION 2: SCORE BREAKDOWN WITH RADAR
// ===========================================

@Composable
private fun ScoreBreakdownSection(report: CompatibilityReport) {
    var selectedDimension by remember { mutableStateOf<String?>(null) }

    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Compatibility Dimensions",
                subtitle = "Tap a dimension for details"
            )

            // 12-Dimension Radar Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentAlignment = Alignment.Center
            ) {
                TwelveDimensionRadarChart(
                    scores = report.overallScore,
                    onDimensionSelected = { selectedDimension = it }
                )
            }

            // Dimension Detail
            selectedDimension?.let { dimension ->
                AnimatedVisibility(
                    visible = true,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    DimensionDetailCard(
                        dimension = dimension,
                        score = getDimensionScore(report.overallScore, dimension),
                        onDismiss = { selectedDimension = null }
                    )
                }
            }
        }
    }
}

@Composable
private fun TwelveDimensionRadarChart(
    scores: CompatibilityScores,
    onDimensionSelected: (String) -> Unit
) {
    var animationProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(1500, easing = FastOutSlowInEasing)
        ) { value, _ ->
            animationProgress = value
        }
    }

    val dimensions = mapOf(
        "Numerology" to scores.numerologyScore,
        "Astrology" to scores.astrologyScore,
        "Tantric" to scores.tantricScore,
        "Energetic" to scores.energeticScore,
        "Communication" to scores.communicationScore,
        "Emotional" to scores.emotionalScore,
        "Spiritual" to ((scores.numerologyScore + scores.astrologyScore) / 2),
        "Physical" to ((scores.tantricScore + scores.energeticScore) / 2),
        "Mental" to scores.communicationScore,
        "Values" to scores.emotionalScore,
        "Goals" to ((scores.numerologyScore + scores.emotionalScore) / 2),
        "Growth" to scores.energeticScore
    )

    // Extract colors from theme before Canvas (can't use MaterialTheme inside DrawScope)
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val tappedDimension = detectDimensionTap(
                        offset,
                        Size(size.width.toFloat(), size.height.toFloat()),
                        dimensions.keys.toList()
                    )
                    tappedDimension?.let(onDimensionSelected)
                }
            }
    ) {
        drawTwelveDimensionRadar(
            dimensions = dimensions,
            animationProgress = animationProgress,
            primaryColor = primaryColor,
            onPrimaryColor = onPrimaryColor
        )
    }
}

private fun DrawScope.drawTwelveDimensionRadar(
    dimensions: Map<String, Double>,
    animationProgress: Float,
    primaryColor: Color,
    onPrimaryColor: Color
) {
    val center = Offset(size.width / 2, size.height / 2)
    val maxRadius = size.minDimension / 2 * 0.7f
    val numberOfDimensions = dimensions.size

    // Draw background circles (grid)
    for (i in 1..5) {
        val radius = maxRadius * (i / 5f)
        drawCircle(
            color = Color.Gray.copy(alpha = 0.1f),
            radius = radius,
            center = center,
            style = Stroke(width = 1f)
        )
    }

    // Draw axes
    dimensions.keys.forEachIndexed { index, _ ->
        val angle = (index * 360f / numberOfDimensions) - 90f
        val rad = Math.toRadians(angle.toDouble())
        val endX = center.x + maxRadius * cos(rad).toFloat()
        val endY = center.y + maxRadius * sin(rad).toFloat()

        drawLine(
            color = Color.Gray.copy(alpha = 0.2f),
            start = center,
            end = Offset(endX, endY),
            strokeWidth = 1f
        )
    }

    // Draw data polygon
    val path = Path()
    val points = mutableListOf<Offset>()

    dimensions.entries.forEachIndexed { index, (_, score) ->
        val angle = (index * 360f / numberOfDimensions) - 90f
        val rad = Math.toRadians(angle.toDouble())
        val normalizedScore = (score / 100.0).toFloat() * animationProgress
        val radius = maxRadius * normalizedScore
        val x = center.x + radius * cos(rad).toFloat()
        val y = center.y + radius * sin(rad).toFloat()

        points.add(Offset(x, y))

        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    // Fill polygon with gradient
    val gradient = Brush.radialGradient(
        colors = listOf(
            SpiritualPurple.copy(alpha = 0.4f),
            MysticViolet.copy(alpha = 0.2f)
        ),
        center = center,
        radius = maxRadius
    )

    drawPath(path = path, brush = gradient)

    // Draw polygon outline
    drawPath(
        path = path,
        color = primaryColor,
        style = Stroke(width = 3f)
    )

    // Draw points
    points.forEach { point ->
        drawCircle(
            color = primaryColor,
            radius = 6f,
            center = point
        )

        drawCircle(
            color = onPrimaryColor,
            radius = 2f,
            center = point
        )
    }
}

@Composable
private fun DimensionDetailCard(
    dimension: String,
    score: Double,
    onDismiss: () -> Unit
) {
    ModernCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dimension,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${score.toInt()}% Compatible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = getScoreColor(score)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (score / 100f).toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                    color = getScoreColor(score)
                )
            }

            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss"
                )
            }
        }
    }
}

// ===========================================
// SECTION 3: SYNASTRY HIGHLIGHTS
// ===========================================

@Composable
private fun SynastryHighlightsSection(report: CompatibilityReport) {
    var flippedCards by remember { mutableStateOf(setOf<Int>()) }

    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Synastry Highlights",
                subtitle = "Your top strengths and growth areas"
            )

            // Top 3 Strengths
            Text(
                text = "Top Strengths",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = ChakraGreen
            )

            report.strengths.take(3).forEachIndexed { index, strength ->
                FlippableStrengthCard(
                    strength = strength,
                    index = index,
                    isFlipped = flippedCards.contains(index),
                    onFlip = {
                        flippedCards = if (flippedCards.contains(index)) {
                            flippedCards - index
                        } else {
                            flippedCards + index
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Top 3 Challenges
            Text(
                text = "Growth Areas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TantricRose
            )

            report.challenges.take(3).forEachIndexed { index, challenge ->
                FlippableChallengeCard(
                    challenge = challenge,
                    index = index + 100, // Offset to avoid ID collision
                    isFlipped = flippedCards.contains(index + 100),
                    onFlip = {
                        flippedCards = if (flippedCards.contains(index + 100)) {
                            flippedCards - (index + 100)
                        } else {
                            flippedCards + (index + 100)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FlippableStrengthCard(
    strength: CompatibilityStrength,
    index: Int,
    isFlipped: Boolean,
    onFlip: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 100L)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally() + fadeIn()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { onFlip() }
                },
            colors = CardDefaults.cardColors(
                containerColor = ChakraGreen.copy(alpha = 0.1f)
            )
        ) {
            AnimatedContent(
                targetState = isFlipped,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "flip"
            ) { flipped ->
                if (!flipped) {
                    // Front: Aspect & Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(ChakraGreen.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "✨", fontSize = 24.sp)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strength.aspect,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ChakraGreen
                            )
                            Text(
                                text = "Tap to reveal",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }

                        Text(
                            text = "${strength.score.toInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = ChakraGreen
                        )
                    }
                } else {
                    // Back: Full Description
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = strength.aspect,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ChakraGreen
                        )
                        Text(
                            text = strength.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Tap to close",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FlippableChallengeCard(
    challenge: CompatibilityChallenge,
    index: Int,
    isFlipped: Boolean,
    onFlip: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay((index - 100) * 100L)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally() + fadeIn()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { onFlip() }
                },
            colors = CardDefaults.cardColors(
                containerColor = TantricRose.copy(alpha = 0.1f)
            )
        ) {
            AnimatedContent(
                targetState = isFlipped,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "flip"
            ) { flipped ->
                if (!flipped) {
                    // Front
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(TantricRose.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "⚡", fontSize = 24.sp)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = challenge.aspect,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TantricRose
                            )
                            Text(
                                text = "Tap for solutions",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                } else {
                    // Back with Solutions
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = challenge.aspect,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TantricRose
                        )
                        Text(
                            text = challenge.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        if (challenge.solutions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Solutions:",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            challenge.solutions.forEach { solution ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text("•", color = MaterialTheme.colorScheme.primary)
                                    Text(
                                        text = solution,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ===========================================
// SECTION 4: NUMEROLOGY MATCH
// ===========================================

@Composable
private fun NumerologyMatchSection(report: CompatibilityReport) {
    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Numerology Match",
                subtitle = "Life paths, expression & soul urge"
            )

            // Life Path Compatibility
            NumerologyNumberDisplay(
                label = "Life Path",
                number1 = getLifePathNumber(report.profileA),
                number2 = getLifePathNumber(report.profileB),
                compatibility = calculateLifePathCompatibility(report.profileA, report.profileB)
            )

            // Expression Number
            NumerologyNumberDisplay(
                label = "Expression",
                number1 = getExpressionNumber(report.profileA),
                number2 = getExpressionNumber(report.profileB),
                compatibility = calculateExpressionCompatibility(report.profileA, report.profileB)
            )

            // Soul Urge
            NumerologyNumberDisplay(
                label = "Soul Urge",
                number1 = getSoulUrgeNumber(report.profileA),
                number2 = getSoulUrgeNumber(report.profileB),
                compatibility = calculateSoulUrgeCompatibility(report.profileA, report.profileB)
            )
        }
    }
}

@Composable
private fun NumerologyNumberDisplay(
    label: String,
    number1: Int,
    number2: Int,
    compatibility: Double
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Number 1
            NumerologyCircle(number = number1)

            // Compatibility Bar
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${compatibility.toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = getScoreColor(compatibility)
                )
                LinearProgressIndicator(
                    progress = { (compatibility / 100f).toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = getScoreColor(compatibility)
                )
            }

            // Number 2
            NumerologyCircle(number = number2)
        }
    }
}

@Composable
private fun NumerologyCircle(number: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                SpiritualColors.NumerologyColors[number] ?: SpiritualPurple,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// ===========================================
// SECTION 5: ASTROLOGICAL SYNASTRY
// ===========================================

@Composable
private fun AstrologicalSynastrySection(report: CompatibilityReport) {
    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Astrological Synastry",
                subtitle = "Sun, Moon, Venus & Mars connections"
            )

            // Sun Sign
            AstrologySignMatch(
                label = "Sun Sign",
                sign1 = getSunSign(report.profileA),
                sign2 = getSunSign(report.profileB),
                description = "Core identity & ego compatibility"
            )

            // Moon Sign
            AstrologySignMatch(
                label = "Moon Sign",
                sign1 = getMoonSign(report.profileA),
                sign2 = getMoonSign(report.profileB),
                description = "Emotional needs & nurturing styles"
            )

            // Venus/Mars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Venus",
                        style = MaterialTheme.typography.labelLarge,
                        color = TantricRose
                    )
                    Text(
                        text = getVenusSign(report.profileA),
                        fontSize = 24.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Mars",
                        style = MaterialTheme.typography.labelLarge,
                        color = ChakraRed
                    )
                    Text(
                        text = getMarsSign(report.profileB),
                        fontSize = 24.sp
                    )
                }
            }

            Text(
                text = "Attraction & passion dynamics",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun AstrologySignMatch(
    label: String,
    sign1: String,
    sign2: String,
    description: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = sign1, fontSize = 32.sp)
            Text(
                text = "×",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = sign2, fontSize = 32.sp)
        }

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

// ===========================================
// SECTION 6: HUMAN DESIGN CONNECTION
// ===========================================

@Composable
private fun HumanDesignConnectionSection(report: CompatibilityReport) {
    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Human Design Connection",
                subtitle = "Type interactions & channel bridges"
            )

            // Types
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HumanDesignTypeCard(
                    type = getHumanDesignType(report.profileA),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                HumanDesignTypeCard(
                    type = getHumanDesignType(report.profileB),
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // Interaction Analysis
            Text(
                text = "Type Interaction",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = getHumanDesignInteraction(report.profileA, report.profileB),
                style = MaterialTheme.typography.bodyMedium
            )

            // Channel Connections
            Text(
                text = "Channel Connections",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Electromagnetic connections create dynamic energy flow",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun HumanDesignTypeCard(
    type: String,
    modifier: Modifier = Modifier
) {
    ModernCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = getHumanDesignIcon(type),
                fontSize = 32.sp
            )
            Text(
                text = type,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ===========================================
// SECTION 7: AYURVEDIC BALANCE
// ===========================================

@Composable
private fun AyurvedicBalanceSection(report: CompatibilityReport) {
    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Ayurvedic Balance",
                subtitle = "Dosha compatibility & lifestyle harmony"
            )

            // Dosha Compatibility
            DoshaDisplay(
                dosha1 = getDosha(report.profileA),
                dosha2 = getDosha(report.profileB)
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // Dietary Harmony
            InfoRow(
                icon = "🍽️",
                label = "Dietary Harmony",
                value = "Complementary",
                color = ChakraGreen
            )

            // Lifestyle Alignment
            InfoRow(
                icon = "🌅",
                label = "Lifestyle Alignment",
                value = "Balanced",
                color = AuraGold
            )

            // Recommendations
            Text(
                text = "Balance Recommendations",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "• Practice yoga together to harmonize energies\n• Share meals mindfully to strengthen bond\n• Align daily routines for better flow",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun DoshaDisplay(
    dosha1: String,
    dosha2: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DoshaCircle(dosha = dosha1)

        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Doshas connection heart icon",
            tint = TantricRose,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically)
        )

        DoshaCircle(dosha = dosha2)
    }
}

@Composable
private fun DoshaCircle(dosha: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(getDoshaColor(dosha), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dosha.first().uppercase(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text = dosha,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun InfoRow(
    icon: String,
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

// ===========================================
// SECTION 8: TANTRIC CONNECTION
// ===========================================

@Composable
private fun TantricConnectionSection(report: CompatibilityReport) {
    // INTEGRATION: ProgressiveBackgroundImage for tantric section with romantic aesthetic
    // TODO: Pass drawable resources from app module as parameters
    /*
    ProgressiveBackgroundImage(
        backgroundResourceId = com.spiritatlas.core.ui.R.drawable.img_007_app_store_feature_graphic,
        lqipResourceId = com.spiritatlas.core.ui.R.drawable.img_006_loading_spinner_icon,
        modifier = Modifier.fillMaxWidth(),
        alpha = 0.2f, // Very subtle for intimate content
        dimAmount = 0.8f, // Heavy dim for text readability
        contentScale = ContentScale.Crop
    ) {
    */
        GlassmorphCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Tantric Connection",
                subtitle = "Sacred intimacy & energy flow"
            )

            // Energy Type Compatibility
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TantricEnergyType(
                    type = getTantricType(report.profileA),
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    TantricRose.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🔥", fontSize = 24.sp)
                }

                TantricEnergyType(
                    type = getTantricType(report.profileB),
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // Intimacy Insights
            Text(
                text = "Intimacy Insights",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            report.tantricMatches.take(2).forEach { match ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            TantricRose.copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "💫", fontSize = 16.sp)
                    Text(
                        text = match.recommendation,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Sacred Connection Advice
            Text(
                text = "Sacred Connection",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Cultivate sacred space through mindful presence, deep eye contact, and conscious breathing together.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
    // } // End ProgressiveBackgroundImage - TODO: Re-enable when drawable resources are available
}

@Composable
private fun TantricEnergyType(
    type: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    SpiritualColors.TantricColors[type] ?: TantricRose,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = type.first().uppercase(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text = type,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

// ===========================================
// SECTION 9: ACTION PLANS
// ===========================================

@Composable
private fun ActionPlansSection(report: CompatibilityReport) {
    var checkedItems by remember { mutableStateOf(setOf<String>()) }

    GlassmorphCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SpiritualSectionHeader(
                title = "Action Plans",
                subtitle = "Practical steps for growth"
            )

            // Immediate Actions (Today)
            ActionPlanCategory(
                title = "Today",
                icon = "☀️",
                color = MaterialTheme.colorScheme.secondary,
                actions = listOf(
                    "Express appreciation for each other",
                    "Share a meal together mindfully",
                    "Practice active listening"
                ),
                checkedItems = checkedItems,
                onCheckedChange = { action ->
                    checkedItems = if (checkedItems.contains(action)) {
                        checkedItems - action
                    } else {
                        checkedItems + action
                    }
                }
            )

            // Short-term (This Week)
            ActionPlanCategory(
                title = "This Week",
                icon = "📅",
                color = MaterialTheme.colorScheme.primary,
                actions = listOf(
                    "Plan a date focused on shared interests",
                    "Discuss long-term goals together",
                    "Try a new spiritual practice as a couple"
                ),
                checkedItems = checkedItems,
                onCheckedChange = { action ->
                    checkedItems = if (checkedItems.contains(action)) {
                        checkedItems - action
                    } else {
                        checkedItems + action
                    }
                }
            )

            // Long-term (Ongoing)
            ActionPlanCategory(
                title = "Ongoing",
                icon = "🌱",
                color = ChakraGreen,
                actions = listOf(
                    "Regular check-ins on emotional needs",
                    "Continue learning about each other",
                    "Nurture individual growth while supporting each other"
                ),
                checkedItems = checkedItems,
                onCheckedChange = { action ->
                    checkedItems = if (checkedItems.contains(action)) {
                        checkedItems - action
                    } else {
                        checkedItems + action
                    }
                }
            )
        }
    }
}

@Composable
private fun ActionPlanCategory(
    title: String,
    icon: String,
    color: Color,
    actions: List<String>,
    checkedItems: Set<String>,
    onCheckedChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        actions.forEach { action ->
            ActionChecklistItem(
                action = action,
                isChecked = checkedItems.contains(action),
                onCheckedChange = { onCheckedChange(action) },
                color = color
            )
        }
    }
}

@Composable
private fun ActionChecklistItem(
    action: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color.copy(alpha = 0.1f),
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = color,
                checkmarkColor = Color.White
            )
        )

        Text(
            text = action,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            textDecoration = if (isChecked) {
                androidx.compose.ui.text.style.TextDecoration.LineThrough
            } else null,
            color = if (isChecked) {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

// ===========================================
// CONFETTI EFFECT
// ===========================================

@Composable
private fun ConfettiEffect(modifier: Modifier = Modifier) {
    // Extract colors from theme before remember block
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    val particles = remember(primaryColor, secondaryColor) {
        List(30) {
            ConfettiParticle(
                x = Random.nextFloat(),
                initialY = -0.2f,
                speed = Random.nextFloat() * 0.5f + 0.3f,
                color = listOf(
                    primaryColor,
                    secondaryColor,
                    TantricRose,
                    ChakraGreen,
                    CosmicBlue
                ).random()
            )
        }
    }

    var animationProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            animate(
                initialValue = animationProgress,
                targetValue = animationProgress + 0.01f,
                animationSpec = tween(16, easing = LinearEasing)
            ) { value, _ ->
                animationProgress = value % 1f
            }
        }
    }

    Canvas(modifier = modifier.fillMaxWidth()) {
        particles.forEach { particle ->
            val x = size.width * particle.x
            val y = size.height * (particle.initialY + particle.speed * animationProgress)

            if (y < size.height) {
                drawCircle(
                    color = particle.color,
                    radius = 6f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

private data class ConfettiParticle(
    val x: Float,
    val initialY: Float,
    val speed: Float,
    val color: Color
)

// ===========================================
// HELPER FUNCTIONS
// ===========================================

private fun shouldShowTantricSection(report: CompatibilityReport): Boolean {
    return report.tantricMatches.isNotEmpty()
}

private fun getLevelDescription(level: CompatibilityLevel): String {
    return when (level) {
        CompatibilityLevel.SOULMATE -> "Deep spiritual connection with exceptional harmony across all dimensions"
        CompatibilityLevel.EXCELLENT -> "Strong compatibility with great potential for lasting partnership"
        CompatibilityLevel.GOOD -> "Solid foundation with room for growth and deeper connection"
        CompatibilityLevel.MODERATE -> "Compatible with conscious effort and mutual understanding"
        CompatibilityLevel.CHALLENGING -> "Requires significant work but growth is possible"
        CompatibilityLevel.INCOMPATIBLE -> "Fundamental differences that may be difficult to reconcile"
    }
}

private fun getScoreColor(score: Double): Color {
    return when {
        score >= 80 -> ChakraGreen
        score >= 60 -> AuraGold
        score >= 40 -> TantricRose
        else -> ChakraRed
    }
}

private fun getEnergyEmoji(score: Double): String {
    return when {
        score >= 90 -> "💫"
        score >= 80 -> "✨"
        score >= 70 -> "⭐"
        score >= 60 -> "🌟"
        score >= 50 -> "💝"
        else -> "💛"
    }
}

private fun getZodiacSign(profile: UserProfile): String {
    // Placeholder - would calculate from birthDateTime
    return listOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓").random()
}

private fun getDimensionScore(scores: CompatibilityScores, dimension: String): Double {
    return when (dimension) {
        "Numerology" -> scores.numerologyScore
        "Astrology" -> scores.astrologyScore
        "Tantric" -> scores.tantricScore
        "Energetic" -> scores.energeticScore
        "Communication" -> scores.communicationScore
        "Emotional" -> scores.emotionalScore
        "Spiritual" -> (scores.numerologyScore + scores.astrologyScore) / 2
        "Physical" -> (scores.tantricScore + scores.energeticScore) / 2
        "Mental" -> scores.communicationScore
        "Values" -> scores.emotionalScore
        "Goals" -> (scores.numerologyScore + scores.emotionalScore) / 2
        "Growth" -> scores.energeticScore
        else -> 0.0
    }
}

private fun detectDimensionTap(
    tapOffset: Offset,
    canvasSize: androidx.compose.ui.geometry.Size,
    dimensions: List<String>
): String? {
    val center = Offset(canvasSize.width / 2, canvasSize.height / 2)
    val maxRadius = canvasSize.minDimension / 2 * 0.7f
    val labelRadius = maxRadius * 1.15f

    dimensions.forEachIndexed { index, dimension ->
        val angle = (index * 360f / dimensions.size) - 90f
        val rad = Math.toRadians(angle.toDouble())
        val x = center.x + labelRadius * cos(rad).toFloat()
        val y = center.y + labelRadius * sin(rad).toFloat()

        val distance = sqrt((tapOffset.x - x).pow(2) + (tapOffset.y - y).pow(2))
        if (distance < 40f) {
            return dimension
        }
    }

    return null
}

private fun getLifePathNumber(profile: UserProfile): Int {
    // Placeholder - would calculate from birthdate
    return (1..9).random()
}

private fun getExpressionNumber(profile: UserProfile): Int {
    return (1..9).random()
}

private fun getSoulUrgeNumber(profile: UserProfile): Int {
    return (1..9).random()
}

private fun calculateLifePathCompatibility(profile1: UserProfile, profile2: UserProfile): Double {
    return (60..95).random().toDouble()
}

private fun calculateExpressionCompatibility(profile1: UserProfile, profile2: UserProfile): Double {
    return (60..95).random().toDouble()
}

private fun calculateSoulUrgeCompatibility(profile1: UserProfile, profile2: UserProfile): Double {
    return (60..95).random().toDouble()
}

private fun getSunSign(profile: UserProfile): String {
    return listOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓").random()
}

private fun getMoonSign(profile: UserProfile): String {
    return listOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓").random()
}

private fun getVenusSign(profile: UserProfile): String {
    return listOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓").random()
}

private fun getMarsSign(profile: UserProfile): String {
    return listOf("♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓").random()
}

private fun getHumanDesignType(profile: UserProfile): String {
    return listOf("Generator", "Manifestor", "Projector", "Reflector", "Manifesting Generator").random()
}

private fun getHumanDesignIcon(type: String): String {
    return when (type) {
        "Generator" -> "⚡"
        "Manifestor" -> "🔥"
        "Projector" -> "🔮"
        "Reflector" -> "🌙"
        "Manifesting Generator" -> "⚡🔥"
        else -> "✨"
    }
}

private fun getHumanDesignInteraction(profile1: UserProfile, profile2: UserProfile): String {
    return "Your types create a dynamic and balanced partnership with complementary energies."
}

private fun getDosha(profile: UserProfile): String {
    return listOf("Vata", "Pitta", "Kapha").random()
}

private fun getDoshaColor(dosha: String): Color {
    return when (dosha) {
        "Vata" -> CosmicBlue
        "Pitta" -> ChakraRed
        "Kapha" -> ChakraGreen
        else -> SpiritualPurple
    }
}

private fun getTantricType(profile: UserProfile): String {
    return listOf("Passionate", "Sensual", "Sacred", "Divine", "Mystical").random()
}

// View Mode Enum
enum class CompatibilityViewMode {
    VERTICAL_SCROLL,
    HORIZONTAL_PAGER
}
