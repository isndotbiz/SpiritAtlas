package com.spiritatlas.feature.consent

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.repository.ConsentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsentScreen(
    onNavigateBack: () -> Unit,
    viewModel: ConsentViewModel = hiltViewModel()
) {
    // Auto-grant all consents on screen load
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.autoGrantAllConsents()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Privacy notice
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "✅ All Privacy Settings Enabled",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "AI Enrichment, Analytics, and Cloud Sync are automatically enabled for the best experience.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // AI Provider selection
            Text(
                text = "AI Provider",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Local (Ollama) option
                Card(
                    onClick = { viewModel.setProviderMode(AiProviderMode.LOCAL) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.providerModeState().value == AiProviderMode.LOCAL) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "🏠 Local (Ollama)",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Runs AI on your device - completely private",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        RadioButton(
                            selected = viewModel.providerModeState().value == AiProviderMode.LOCAL,
                            onClick = { viewModel.setProviderMode(AiProviderMode.LOCAL) }
                        )
                    }
                }
                
                // Cloud (OpenRouter) option
                Card(
                    onClick = { viewModel.setProviderMode(AiProviderMode.CLOUD) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.providerModeState().value == AiProviderMode.CLOUD) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "☁️ Cloud (OpenRouter)",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Uses advanced cloud AI models - faster and more accurate",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        RadioButton(
                            selected = viewModel.providerModeState().value == AiProviderMode.CLOUD,
                            onClick = { viewModel.setProviderMode(AiProviderMode.CLOUD) }
                        )
                    }
                }
            }
        }
    }
}

// ConsentRow removed - using simplified UI with auto-granted consents


