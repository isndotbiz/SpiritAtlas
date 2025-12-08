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
    val consentMap by viewModel.consentMap.collectAsState()
    val providerMode by viewModel.providerModeState().collectAsState()

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
                        text = "Privacy & Data Settings",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Control how your data is used. Your choices are saved locally and encrypted.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Consent toggles
            Text(
                text = "Data Permissions",
                style = MaterialTheme.typography.headlineSmall
            )

            ConsentToggle(
                title = "AI Enrichment",
                description = "Allow AI to analyze your profile and provide personalized spiritual insights",
                isEnabled = consentMap[ConsentType.AI_ENRICHMENT] == ConsentStatus.GRANTED,
                onToggle = { viewModel.toggle(ConsentType.AI_ENRICHMENT, it) }
            )

            ConsentToggle(
                title = "Cloud Sync",
                description = "Sync your profiles across devices using encrypted cloud storage",
                isEnabled = consentMap[ConsentType.CLOUD_SYNC] == ConsentStatus.GRANTED,
                onToggle = { viewModel.toggle(ConsentType.CLOUD_SYNC, it) }
            )

            ConsentToggle(
                title = "Analytics",
                description = "Help improve the app by sharing anonymous usage data",
                isEnabled = consentMap[ConsentType.ANALYTICS] == ConsentStatus.GRANTED,
                onToggle = { viewModel.toggle(ConsentType.ANALYTICS, it) }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            
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
                        containerColor = if (providerMode == AiProviderMode.LOCAL) {
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
                            selected = providerMode == AiProviderMode.LOCAL,
                            onClick = { viewModel.setProviderMode(AiProviderMode.LOCAL) }
                        )
                    }
                }
                
                // Cloud (OpenRouter) option
                Card(
                    onClick = { viewModel.setProviderMode(AiProviderMode.CLOUD) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (providerMode == AiProviderMode.CLOUD) {
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
                            selected = providerMode == AiProviderMode.CLOUD,
                            onClick = { viewModel.setProviderMode(AiProviderMode.CLOUD) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConsentToggle(
    title: String,
    description: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle
            )
        }
    }
}


