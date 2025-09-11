package com.spiritatlas.feature.compatibility

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.CompatibilityCriteria
import com.spiritatlas.domain.repository.ProfileMatch
import java.time.format.DateTimeFormatter

/**
 * History content showing cached compatibility reports
 */
@Composable
fun HistoryContent(
    cachedReports: List<CompatibilityReport>,
    onLoadReport: (CompatibilityReport) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cachedReports.isEmpty()) {
            EmptyHistoryState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "📚 Compatibility History",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                
                items(cachedReports) { report ->
                    HistoryReportCard(
                        report = report,
                        onLoadReport = onLoadReport
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun EmptyHistoryState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "No History Yet",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Your completed compatibility analyses will appear here for easy access.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
private fun HistoryReportCard(
    report: CompatibilityReport,
    onLoadReport: (CompatibilityReport) -> Unit
) {
    Card(
        onClick = { onLoadReport(report) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profiles header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${report.profileA.displayName ?: report.profileA.name} & ${report.profileB.displayName ?: report.profileB.name}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = report.generatedAt.format(DateTimeFormatter.ofPattern("MMM d, yyyy - h:mm a")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = report.compatibilityLevel.icon,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${String.format("%.1f", report.overallScore.totalScore)}%",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Quick insights preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text(report.compatibilityLevel.displayName) },
                    modifier = Modifier.weight(1f)
                )
                if (report.insights.isNotEmpty()) {
                    AssistChip(
                        onClick = { },
                        label = { Text("${report.insights.size} insights") }
                    )
                }
            }
        }
    }
}

/**
 * Search content for finding compatible profiles
 */
@Composable
fun SearchContent(
    compatibleMatches: List<ProfileMatch>,
    isSearching: Boolean,
    onSearchProfiles: (UserProfile, CompatibilityCriteria) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Search instructions
        SearchInstructionCard()
        
        // Search functionality (simplified for now)
        SearchPlaceholder()
        
        // Search results
        if (isSearching) {
            SearchingState()
        } else if (compatibleMatches.isNotEmpty()) {
            SearchResults(matches = compatibleMatches)
        } else {
            NoSearchResultsState()
        }
    }
}

@Composable
private fun SearchInstructionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "Find Compatible Matches",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            
            Text(
                text = "Discover profiles that match your spiritual energy and compatibility preferences. Search by compatibility score, astrological elements, numerological patterns, and more.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
private fun SearchPlaceholder() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🔍 Advanced Search",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = "Profile matching and compatibility search functionality will be available in the next version. For now, use the Selection tab to analyze specific profile pairs.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Button(
                onClick = { /* TODO: Implement search */ },
                enabled = false
            ) {
                Text("Coming Soon")
            }
        }
    }
}

@Composable
private fun SearchingState() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text(
                text = "🔍 Searching for compatible matches...",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SearchResults(matches: List<ProfileMatch>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "✨ Compatible Matches (${matches.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.heightIn(max = 400.dp)
            ) {
                items(matches) { match ->
                    MatchResultCard(match = match)
                }
            }
        }
    }
}

@Composable
private fun MatchResultCard(match: ProfileMatch) {
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = match.profile.displayName ?: match.profile.name ?: "Unknown",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = match.compatibilityPreview.compatibilityLevel.icon,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${String.format("%.1f", match.compatibilityPreview.overallScore)}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Text(
                text = match.matchReason,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text("✨ ${match.compatibilityPreview.topStrength}") },
                    modifier = Modifier.weight(1f)
                )
                if (match.compatibilityPreview.topChallenge.isNotBlank()) {
                    AssistChip(
                        onClick = { },
                        label = { Text("⚠️ ${match.compatibilityPreview.topChallenge}") }
                    )
                }
            }
        }
    }
}

@Composable
private fun NoSearchResultsState() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🔍",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "Ready to Search",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Configure your search preferences and find your perfect spiritual matches.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
