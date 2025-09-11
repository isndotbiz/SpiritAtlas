package com.spiritatlas.feature.compatibility

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spiritatlas.domain.model.*

/**
 * Results content displaying comprehensive compatibility analysis
 */
@Composable
fun ResultsContent(
    compatibilityReport: CompatibilityReport?,
    isAnalyzing: Boolean
) {
    when {
        isAnalyzing -> {
            AnalyzingState()
        }
        compatibilityReport != null -> {
            CompatibilityResults(report = compatibilityReport)
        }
        else -> {
            NoResultsState()
        }
    }
}

@Composable
private fun AnalyzingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text(
                text = "✨ Analyzing spiritual compatibility...",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "This may take a few moments as we calculate numerology, astrology, and tantric compatibility patterns.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
private fun NoResultsState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🔮",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "No Analysis Yet",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Select two profiles and run an analysis to see comprehensive compatibility results here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
private fun CompatibilityResults(report: CompatibilityReport) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Overall compatibility header
        OverallCompatibilityCard(report)
        
        // Compatibility scores breakdown
        ScoresBreakdownCard(report.overallScore)
        
        // Key insights
        if (report.insights.isNotEmpty()) {
            InsightsCard(report.insights)
        }
        
        // Strengths
        if (report.strengths.isNotEmpty()) {
            StrengthsCard(report.strengths)
        }
        
        // Challenges
        if (report.challenges.isNotEmpty()) {
            ChallengesCard(report.challenges)
        }
        
        // Recommendations
        if (report.recommendations.isNotEmpty()) {
            RecommendationsCard(report.recommendations)
        }
        
        // Tantric compatibility
        if (report.tantricMatches.isNotEmpty()) {
            TantricMatchesCard(report.tantricMatches)
        }
        
        // Spacer for bottom navigation
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun OverallCompatibilityCard(report: CompatibilityReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (report.compatibilityLevel) {
                CompatibilityLevel.SOULMATE -> Color(0xFFE1F5FE)
                CompatibilityLevel.EXCELLENT -> Color(0xFFE8F5E8)
                CompatibilityLevel.GOOD -> Color(0xFFFFF3E0)
                CompatibilityLevel.MODERATE -> Color(0xFFFFF8E1)
                CompatibilityLevel.CHALLENGING -> Color(0xFFFFF3E0)
                CompatibilityLevel.INCOMPATIBLE -> Color(0xFFFFEBEE)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = report.compatibilityLevel.icon,
                style = MaterialTheme.typography.displayLarge
            )
            
            Text(
                text = report.compatibilityLevel.displayName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "${String.format("%.1f", report.overallScore.totalScore)}% Compatible",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = "${report.profileA.displayName ?: report.profileA.name} & ${report.profileB.displayName ?: report.profileB.name}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ScoresBreakdownCard(scores: CompatibilityScores) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "📊 Compatibility Breakdown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            scores.scoreBreakdown.forEach { (category, score) ->
                ScoreBar(
                    category = category,
                    score = score,
                    icon = when (category) {
                        "Numerology" -> "🔢"
                        "Astrology" -> "⭐"
                        "Tantric" -> "💫"
                        "Energetic" -> "⚡"
                        "Communication" -> "💬"
                        "Emotional" -> "❤️"
                        else -> "✨"
                    }
                )
            }
        }
    }
}

@Composable
private fun ScoreBar(
    category: String,
    score: Double,
    icon: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$icon $category",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${String.format("%.1f", score)}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        LinearProgressIndicator(
            progress = { (score / 100f).toFloat() },
            modifier = Modifier.fillMaxWidth(),
            color = when {
                score >= 85 -> Color(0xFF4CAF50)
                score >= 70 -> Color(0xFF2196F3)
                score >= 55 -> Color(0xFFFF9800)
                else -> Color(0xFFF44336)
            }
        )
    }
}

@Composable
private fun InsightsCard(insights: List<RelationshipInsight>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🌟 Key Insights",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            insights.forEach { insight ->
                InsightItem(insight = insight)
            }
        }
    }
}

@Composable
private fun InsightItem(insight: RelationshipInsight) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = insight.icon, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = insight.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Text(
                text = insight.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun StrengthsCard(strengths: List<CompatibilityStrength>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "✨ Relationship Strengths",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            strengths.forEach { strength ->
                StrengthItem(strength = strength)
            }
        }
    }
}

@Composable
private fun StrengthItem(strength: CompatibilityStrength) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = strength.icon, style = MaterialTheme.typography.titleMedium)
            Text(
                text = strength.aspect,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "${String.format("%.1f", strength.score)}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        
        Text(
            text = strength.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ChallengesCard(challenges: List<CompatibilityChallenge>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "⚠️ Growth Areas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            challenges.forEach { challenge ->
                ChallengeItem(challenge = challenge)
            }
        }
    }
}

@Composable
private fun ChallengeItem(challenge: CompatibilityChallenge) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = challenge.icon, style = MaterialTheme.typography.titleMedium)
            Text(
                text = challenge.aspect,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
        
        Text(
            text = challenge.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
        
        if (challenge.solutions.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Solutions:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                challenge.solutions.forEach { solution ->
                    Text(
                        text = "• $solution",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<CompatibilityRecommendation>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "💡 Personalized Recommendations",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            recommendations.forEach { recommendation ->
                RecommendationItem(recommendation = recommendation)
            }
        }
    }
}

@Composable
private fun RecommendationItem(recommendation: CompatibilityRecommendation) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = recommendation.icon, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = recommendation.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Text(
                text = recommendation.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TantricMatchesCard(tantricMatches: List<TantricCompatibility>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🔥 Tantric Compatibility",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            
            tantricMatches.forEach { match ->
                TantricMatchItem(match = match)
            }
        }
    }
}

@Composable
private fun TantricMatchItem(match: TantricCompatibility) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = match.contentType,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${String.format("%.1f", match.compatibilityScore)}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Text(
                text = match.reason,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "💡 ${match.recommendation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
