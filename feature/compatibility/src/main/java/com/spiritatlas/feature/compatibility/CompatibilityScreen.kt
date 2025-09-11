package com.spiritatlas.feature.compatibility

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.CompatibilityCriteria

/**
 * Main compatibility analysis screen with tab-based navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityScreen(
    onNavigateBack: () -> Unit,
    viewModel: CompatibilityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CompatibilityTopBar(
                onNavigateBack = onNavigateBack,
                compatibilityReport = uiState.compatibilityReport,
                onShare = { report ->
                    shareCompatibilityReport(context, viewModel.shareReport(report))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Error handling
            uiState.errorMessage?.let { error ->
                ErrorBanner(
                    error = error,
                    onDismiss = { viewModel.clearError() }
                )
            }

            // Tab navigation
            CompatibilityTabRow(
                selectedTab = uiState.activeTab,
                onTabSelected = { viewModel.setActiveTab(it) }
            )

            // Content based on selected tab
            when (uiState.activeTab) {
                CompatibilityTab.SELECTION -> {
                    ProfileSelectionContent(
                        uiState = uiState,
                        onSelectProfileA = viewModel::selectProfileA,
                        onSelectProfileB = viewModel::selectProfileB,
                        onAnalyzeCompatibility = viewModel::analyzeCompatibility
                    )
                }
                CompatibilityTab.RESULTS -> {
                    ResultsContent(
                        compatibilityReport = uiState.compatibilityReport,
                        isAnalyzing = uiState.isAnalyzing
                    )
                }
                CompatibilityTab.HISTORY -> {
                    HistoryContent(
                        cachedReports = uiState.cachedReports,
                        onLoadReport = { /* TODO: Implement */ }
                    )
                }
                CompatibilityTab.SEARCH -> {
                    SearchContent(
                        compatibleMatches = uiState.compatibleMatches,
                        isSearching = uiState.isSearching,
                        onSearchProfiles = { profile: UserProfile, criteria: CompatibilityCriteria ->
                            viewModel.searchCompatibleProfiles(profile, criteria)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompatibilityTopBar(
    onNavigateBack: () -> Unit,
    compatibilityReport: CompatibilityReport?,
    onShare: (CompatibilityReport) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Compatibility Analysis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            // Share button - only show when we have a report
            compatibilityReport?.let { report ->
                IconButton(
                    onClick = { onShare(report) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Report"
                    )
                }
            }
        }
    )
}

@Composable
private fun ErrorBanner(
    error: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Dismiss",
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
private fun CompatibilityTabRow(
    selectedTab: CompatibilityTab,
    onTabSelected: (CompatibilityTab) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = Modifier.fillMaxWidth()
    ) {
        CompatibilityTab.values().forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = tab.icon,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = tab.displayName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )
        }
    }
}

/**
 * Share compatibility report via Android intent
 */
private fun shareCompatibilityReport(context: Context, reportText: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, reportText)
        putExtra(Intent.EXTRA_SUBJECT, "Spiritual Compatibility Analysis")
    }
    
    val chooserIntent = Intent.createChooser(shareIntent, "Share Compatibility Report")
    context.startActivity(chooserIntent)
}
