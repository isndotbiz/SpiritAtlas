package com.spiritatlas.feature.consent

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.DimmedSpiritualBackground
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

    // Consent/settings screen - using scaffold without custom background for now
    Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.consent_screen_title)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.consent_back_button)
                            )
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
                        text = stringResource(R.string.consent_privacy_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = stringResource(R.string.consent_privacy_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Consent toggles
            Text(
                text = stringResource(R.string.consent_data_permissions_header),
                style = MaterialTheme.typography.headlineSmall
            )

            ConsentToggle(
                title = stringResource(R.string.consent_ai_enrichment_title),
                description = stringResource(R.string.consent_ai_enrichment_description),
                isEnabled = consentMap[ConsentType.AI_ENRICHMENT] == ConsentStatus.GRANTED,
                onToggle = { viewModel.toggle(ConsentType.AI_ENRICHMENT, it) }
            )

            ConsentToggle(
                title = stringResource(R.string.consent_cloud_sync_title),
                description = stringResource(R.string.consent_cloud_sync_description),
                isEnabled = consentMap[ConsentType.CLOUD_SYNC] == ConsentStatus.GRANTED,
                onToggle = { viewModel.toggle(ConsentType.CLOUD_SYNC, it) }
            )

            ConsentToggle(
                title = stringResource(R.string.consent_analytics_title),
                description = stringResource(R.string.consent_analytics_description),
                isEnabled = consentMap[ConsentType.ANALYTICS] == ConsentStatus.GRANTED,
                onToggle = { viewModel.toggle(ConsentType.ANALYTICS, it) }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // AI Provider selection
            Text(
                text = stringResource(R.string.consent_ai_provider_header),
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
                                text = stringResource(R.string.consent_provider_local_title),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = stringResource(R.string.consent_provider_local_description),
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
                    onClick = { viewModel.setProviderMode(AiProviderMode.AUTO) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (providerMode == AiProviderMode.AUTO) {
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
                                text = stringResource(R.string.consent_provider_cloud_title),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = stringResource(R.string.consent_provider_cloud_description),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        RadioButton(
                            selected = providerMode == AiProviderMode.AUTO,
                            onClick = { viewModel.setProviderMode(AiProviderMode.AUTO) }
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


