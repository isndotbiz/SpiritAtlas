package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.core.ui.theme.*
import com.spiritatlas.domain.repository.CompatibilityAnalysis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityAnalysisScreen(
    profile1Id: String,
    profile2Id: String,
    onNavigateBack: () -> Unit,
    viewModel: CompatibilityAnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(profile1Id, profile2Id) {
        viewModel.analyzeCompatibility(profile1Id, profile2Id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = null,
                            tint = SpiritualPurple
                        )
                        Text(
                            "Compatibility Analysis",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is CompatibilityAnalysisUiState.Loading -> {
                CompatibilityLoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            is CompatibilityAnalysisUiState.Error -> {
                CompatibilityErrorState(
                    error = state.message,
                    onRetry = { viewModel.analyzeCompatibility(profile1Id, profile2Id) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            is CompatibilityAnalysisUiState.Success -> {
                CompatibilityResultContent(
                    analysis = state.analysis,
                    profile1Name = state.profile1Name,
                    profile2Name = state.profile2Name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun CompatibilityLoadingState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ModernCard(
            modifier = Modifier.padding(32.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CircularProgressIndicator(
                    color = SpiritualPurple,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(64.dp)
                )
                
                Text(
                    text = "🔮 Analyzing Soul Connection",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "Calculating spiritual compatibility...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CompatibilityErrorState(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ModernCard(
            modifier = Modifier.padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "⚠️ Analysis Failed",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
                
                ModernButton(
                    text = "Try Again",
                    onClick = onRetry,
                    variant = ButtonVariant.Primary
                )
            }
        }
    }
}

@Composable
private fun CompatibilityResultContent(
    analysis: CompatibilityAnalysis,
    profile1Name: String,
    profile2Name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with overall score
        CompatibilityScoreCard(
            overallScore = analysis.overallScore,
            profile1Name = profile1Name,
            profile2Name = profile2Name
        )
        
        // Detailed compatibility scores
        CompatibilityBreakdownCard(analysis = analysis)
        
        // Strengths section
        if (analysis.strengths.isNotEmpty()) {
            CompatibilityListCard(
                title = "💪 Relationship Strengths",
                items = analysis.strengths,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
        
        // Challenges section
        if (analysis.challenges.isNotEmpty()) {
            CompatibilityListCard(
                title = "⚡ Growth Areas",
                items = analysis.challenges,
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
        }
        
        // Recommendations
        if (analysis.recommendations.isNotEmpty()) {
            CompatibilityListCard(
                title = "🌟 Recommendations",
                items = analysis.recommendations,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
        
        // Tantric practices
        if (analysis.tantricPractices.isNotEmpty()) {
            TantricPracticesCard(practices = analysis.tantricPractices)
        }
    }
}

@Composable
private fun CompatibilityScoreCard(
    overallScore: Float,
    profile1Name: String,
    profile2Name: String,
    modifier: Modifier = Modifier
) {
    GradientCard(
        modifier = modifier,
        gradientColors = listOf(SpiritualPurple, MysticViolet, TantricRose)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "$profile1Name × $profile2Name",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "${(overallScore * 100).toInt()}%",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            
            Text(
                text = when {
                    overallScore >= 0.9f -> "Soul Mates 💫"
                    overallScore >= 0.8f -> "Highly Compatible 💖"
                    overallScore >= 0.7f -> "Great Potential 🌟"
                    overallScore >= 0.6f -> "Good Match 💝"
                    else -> "Growth Opportunity 🌱"
                },
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CompatibilityBreakdownCard(
    analysis: CompatibilityAnalysis,
    modifier: Modifier = Modifier
) {
    ModernCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "📊 Compatibility Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            CompatibilityBar(
                label = "💖 Spiritual",
                score = analysis.spiritualCompatibility,
                color = SpiritualPurple
            )
            
            CompatibilityBar(
                label = "💝 Emotional",
                score = analysis.emotionalCompatibility,
                color = TantricRose
            )
            
            CompatibilityBar(
                label = "🔥 Physical",
                score = analysis.physicalCompatibility,
                color = SensualCoral
            )
            
            CompatibilityBar(
                label = "🧠 Intellectual", 
                score = analysis.intellectualCompatibility,
                color = CosmicBlue
            )
        }
    }
}

@Composable
private fun CompatibilityBar(
    label: String,
    score: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${(score * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        
        LinearProgressIndicator(
            progress = { score },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun CompatibilityListCard(
    title: String,
    items: List<String>,
    color: Color,
    modifier: Modifier = Modifier
) {
    ModernCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            items.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("•", color = MaterialTheme.colorScheme.primary)
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun TantricPracticesCard(
    practices: List<com.spiritatlas.domain.tantric.TantricContent>,
    modifier: Modifier = Modifier
) {
    ModernCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = IntimacyPurple.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = IntimacyPurple
                )
                Text(
                    text = "🌸 Recommended Tantric Practices",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            practices.forEach { practice ->
                ModernCard(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = practice.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Text(
                            text = practice.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(practice.rating) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = TantricRose,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
