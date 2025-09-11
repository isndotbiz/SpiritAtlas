package com.spiritatlas.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToConsent: () -> Unit,
    onNavigateToCompatibility: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    consentDebugViewModel: ConsentDebugViewModel = hiltViewModel()
) {
    val providerLabel by viewModel.providerLabel.collectAsState()
    val consentDebug by consentDebugViewModel.debugText.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SpiritAtlas") },
                actions = {
                    IconButton(onClick = onNavigateToConsent) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to SpiritAtlas",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            AssistChip(onClick = {}, label = { Text(providerLabel) })
            Spacer(modifier = Modifier.height(8.dp))
            if (consentDebug.isNotBlank()) {
                Text(consentDebug, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToProfile,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("✨ Spiritual Profiles")
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = onNavigateToCompatibility,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("💫 Compatibility Analysis")
            }
        }
    }
}


