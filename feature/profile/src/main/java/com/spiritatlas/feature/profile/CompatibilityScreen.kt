package com.spiritatlas.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.core.ui.theme.*
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.service.DetailedCouplesReport
import com.spiritatlas.domain.service.CouplesStrength
import com.spiritatlas.domain.service.CouplesChallenge
import com.spiritatlas.domain.service.CouplesActionPlan

// Type aliases for backward compatibility
typealias RelationshipStrength = CouplesStrength
typealias RelationshipChallenge = CouplesChallenge
typealias RelationshipActionPlan = CouplesActionPlan

/**
 * Modern Compatibility Analysis Screen - Co-Star Inspired ✨
 * Beautiful UI for analyzing spiritual and romantic compatibility between two profiles
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityScreen(
    profile1Id: String,
    profile2Id: String,
    onNavigateBack: () -> Unit,
    onShareCompatibility: () -> Unit,
    viewModel: CompatibilityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val compatibilityReport by viewModel.compatibilityReport.collectAsState()

    LaunchedEffect(profile1Id, profile2Id) {
        viewModel.analyzeCompatibility(profile1Id, profile2Id)
    }

    SpiritualGradientBackground(gradientType = SpiritualGradientType.SUNSET) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Modern App Bar
            CompatibilityTopBar(
                onNavigateBack = onNavigateBack,
                onShareReport = onShareCompatibility,
                isLoading = uiState.isLoading
            )

            when {
                uiState.isLoading -> {
                    LoadingCompatibilityAnalysis()
                }
                uiState.error != null -> {
                    val errorMessage = uiState.error
                    CompatibilityError(
                        error = errorMessage!!,
                        onRetry = { viewModel.analyzeCompatibility(profile1Id, profile2Id) }
                    )
                }
                compatibilityReport != null -> {
                    CompatibilityResults(
                        report = compatibilityReport!!,
                        onShareReport = onShareCompatibility
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityTopBar(
    onNavigateBack: () -> Unit,
    onShareReport: () -> Unit,
    isLoading: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = "Compatibility Analysis",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = SpiritualPurple
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = SpiritualPurple
                )
            }
        },
        actions = {
            if (!isLoading) {
                IconButton(onClick = onShareReport) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Report",
                        tint = SpiritualPurple
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun LoadingCompatibilityAnalysis() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated compatibility hearts
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                val delay = index * 200L
                var isAnimating by remember { mutableStateOf(false) }
                
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(delay)
                    isAnimating = true
                }
                
                Box(
                    modifier = Modifier
                        .size(if (isAnimating) 60.dp else 40.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    SpiritualPurple.copy(alpha = 0.8f),
                                    CosmicOrange.copy(alpha = 0.4f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💕",
                        fontSize = if (isAnimating) 24.sp else 16.sp
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Analyzing Spiritual Compatibility",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Combining numerology, astrology, and tantric wisdom to reveal your divine connection...",
            fontSize = 16.sp,
            color = DeepTeal,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        CircularProgressIndicator(
            color = CosmicOrange,
            strokeWidth = 3.dp
        )
    }
}

@Composable
fun CompatibilityError(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "⚡",
            fontSize = 64.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Analysis Interrupted",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = error,
            fontSize = 16.sp,
            color = DeepTeal,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        SpiritualButton(
            text = "Try Again",
            onClick = onRetry,
            style = SpiritualButtonStyle.PRIMARY
        )
    }
}

@Composable
fun CompatibilityResults(
    report: DetailedCouplesReport,
    onShareReport: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Section
        item {
            CompatibilityHeroCard(report = report)
        }
        
        // Overall Score
        item {
            OverallCompatibilityCard(
                overallScore = report.overallCompatibility,
                spiritualScore = report.compatibilityBreakdown.spiritual,
                emotionalScore = report.compatibilityBreakdown.emotional,
                physicalScore = report.compatibilityBreakdown.physical
            )
        }
        
        // Key Strengths
        item {
            StrengthsCard(strengths = report.strengthsAnalysis)
        }
        
        // Key Challenges  
        item {
            ChallengesCard(challenges = report.challengesAnalysis)
        }
        
        // Action Plan
        item {
            ActionPlanCard(actionPlan = report.actionPlan)
        }
        
        // Share Button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            
            SpiritualButton(
                text = "Share Compatibility Report",
                onClick = onShareReport,
                style = SpiritualButtonStyle.PRIMARY,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CompatibilityHeroCard(report: DetailedCouplesReport) {
    GlassmorphCard(elevation = 3) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Partner Names
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Partner 1
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                SpiritualPurple.copy(alpha = 0.2f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            fontSize = 32.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Partner 1", // Would use actual names from report
                        fontWeight = FontWeight.SemiBold,
                        color = SpiritualPurple
                    )
                }
                
                // Connection Symbol
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "💕",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CompatibilityScoreDisplay(
                        score = report.overallCompatibility,
                        size = CompatibilityDisplaySize.LARGE
                    )
                }
                
                // Partner 2
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                CosmicOrange.copy(alpha = 0.2f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            fontSize = 32.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Partner 2", // Would use actual names from report
                        fontWeight = FontWeight.SemiBold,
                        color = CosmicOrange
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Connection Summary
            Text(
                text = getCompatibilityDescription(report.overallCompatibility),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = DeepTeal,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun OverallCompatibilityCard(
    overallScore: Double,
    spiritualScore: Double,
    emotionalScore: Double,
    physicalScore: Double
) {
    GlassmorphCard(elevation = 2) {
        SpiritualSectionHeader(
            title = "Compatibility Breakdown",
            subtitle = "Your connection across different dimensions"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Score Breakdown
        val scores = listOf(
            "Spiritual Connection" to spiritualScore,
            "Emotional Harmony" to emotionalScore,
            "Physical Chemistry" to physicalScore
        )
        
        scores.forEach { (label, score) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    color = SpiritualPurple
                )
                
                CompatibilityScoreDisplay(
                    score = score,
                    size = CompatibilityDisplaySize.SMALL
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun StrengthsCard(strengths: List<RelationshipStrength>) {
    GlassmorphCard(elevation = 2) {
        SpiritualSectionHeader(
            title = "✨ Your Strengths",
            subtitle = "What makes your connection special"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        strengths.take(3).forEach { strength ->
            StrengthItem(strength = strength)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun StrengthItem(strength: RelationshipStrength) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    CosmicBlue.copy(alpha = 0.2f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "💎",
                fontSize = 12.sp
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = strength.title,
                fontWeight = FontWeight.SemiBold,
                color = SpiritualPurple,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = strength.description,
                color = DeepTeal,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun ChallengesCard(challenges: List<RelationshipChallenge>) {
    GlassmorphCard(elevation = 2) {
        SpiritualSectionHeader(
            title = "⚡ Growth Areas",
            subtitle = "Opportunities for deeper connection"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        challenges.take(3).forEach { challenge ->
            ChallengeItem(challenge = challenge)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ChallengeItem(challenge: RelationshipChallenge) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    CosmicOrange.copy(alpha = 0.2f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🌱",
                fontSize = 12.sp
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = challenge.title,
                fontWeight = FontWeight.SemiBold,
                color = SpiritualPurple,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = challenge.description,
                color = DeepTeal,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun ActionPlanCard(actionPlan: RelationshipActionPlan) {
    GlassmorphCard(elevation = 2) {
        SpiritualSectionHeader(
            title = "🎯 Action Plan",
            subtitle = "Steps to deepen your connection"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        val actions = listOf(
            "Immediate" to actionPlan.immediateActions,
            "Short-term" to actionPlan.shortTermGoals,
            "Long-term" to actionPlan.longTermVision
        )
        
        actions.forEach { (timeframe, actionList) ->
            if (actionList.isNotEmpty()) {
                Text(
                    text = "$timeframe Actions",
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                actionList.take(2).forEach { action ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "•",
                            color = CosmicOrange,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = action.title,
                            color = DeepTeal,
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
                
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

private fun getCompatibilityDescription(score: Double): String {
    return when {
        score >= 85 -> "Cosmic soulmate connection ✨ Your energies dance in perfect harmony"
        score >= 70 -> "Beautiful spiritual alignment 💫 Strong potential for lasting love"
        score >= 55 -> "Meaningful connection 🌙 Growth opportunities through differences"
        score >= 40 -> "Interesting dynamic ⚡ Challenges that spark evolution"
        else -> "Complex relationship 🌊 Deep lessons in love and understanding"
    }
}
