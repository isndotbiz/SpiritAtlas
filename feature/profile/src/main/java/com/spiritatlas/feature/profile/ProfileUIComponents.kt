package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spiritatlas.domain.model.*

/**
 * UI components for ProfileScreen top bar and accuracy indicator
 * Extracted for better code organization and reusability
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    isEditing: Boolean,
    completion: ProfileCompletion,
    isSaving: Boolean,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
    onLoadTier: (Int) -> Unit,
    onLoadDemo: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    
    TopAppBar(
        title = {
            Text(
                if (isEditing) "Edit Profile" else "New Profile",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            // Save button
            Button(
                onClick = onSave,
                enabled = !isSaving && completion.completedFields > 0,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save")
                }
            }
            
            // Menu for test profiles
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
            
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Load Demo Profile") },
                    onClick = {
                        onLoadDemo()
                        showMenu = false
                    }
                )
                
                DropdownMenuItem(
                    text = { Text("Clear Profile") },
                    onClick = {
                        onClear()
                        showMenu = false
                    }
                )
                
                HorizontalDivider()
                
                DropdownMenuItem(
                    text = { Text("Tier 0 (Minimal)") },
                    onClick = {
                        onLoadTier(0)
                        showMenu = false
                    }
                )
                
                DropdownMenuItem(
                    text = { Text("Tier 1 (Basic)") },
                    onClick = {
                        onLoadTier(1)
                        showMenu = false
                    }
                )
                
                DropdownMenuItem(
                    text = { Text("Tier 2 (Good)") },
                    onClick = {
                        onLoadTier(2)
                        showMenu = false
                    }
                )
                
                DropdownMenuItem(
                    text = { Text("Tier 3 (Excellent)") },
                    onClick = {
                        onLoadTier(3)
                        showMenu = false
                    }
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun AccuracyIndicator(
    completion: ProfileCompletion,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (completion.accuracyLevel) {
                AccuracyLevel.MINIMAL -> MaterialTheme.colorScheme.errorContainer
                AccuracyLevel.BASIC -> MaterialTheme.colorScheme.surfaceVariant
                AccuracyLevel.GOOD -> MaterialTheme.colorScheme.primaryContainer
                AccuracyLevel.EXCELLENT -> MaterialTheme.colorScheme.secondaryContainer
                AccuracyLevel.MAXIMUM -> MaterialTheme.colorScheme.tertiaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = when (completion.accuracyLevel) {
                        AccuracyLevel.MINIMAL -> "⚠️ Need at least 3 fields"
                        AccuracyLevel.BASIC -> "✅ Basic accuracy - Add more for deeper insights!"
                        AccuracyLevel.GOOD -> "🌟 Good accuracy - Nice progress!"
                        AccuracyLevel.EXCELLENT -> "✨ Excellent accuracy - Amazing detail!"
                        AccuracyLevel.MAXIMUM -> "🔮 Maximum accuracy - Perfect spiritual profile!"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Modern progress bar with lambda
            LinearProgressIndicator(
                progress = { (completion.completionPercentage / 100f).toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "${completion.completionPercentage.toInt()}% complete (${completion.completedFields}/${completion.totalFields} fields)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
