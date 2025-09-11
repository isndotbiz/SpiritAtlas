package com.spiritatlas.feature.compatibility

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.components.ModernButton
import com.spiritatlas.domain.model.UserProfile
import java.time.format.DateTimeFormatter

/**
 * Profile selection content for choosing two profiles for compatibility analysis
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSelectionContent(
    uiState: CompatibilityUiState,
    onSelectProfileA: (UserProfile) -> Unit,
    onSelectProfileB: (UserProfile) -> Unit,
    onAnalyzeCompatibility: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Instruction Card
        InstructionCard()
        
        // Profile Selection Section
        ProfileSelectionSection(
            availableProfiles = uiState.availableProfiles,
            selectedProfileA = uiState.selectedProfileA,
            selectedProfileB = uiState.selectedProfileB,
            isLoadingProfiles = uiState.isLoadingProfiles,
            onSelectProfileA = onSelectProfileA,
            onSelectProfileB = onSelectProfileB
        )
        
        // Analysis Button
        AnalysisSection(
            selectedProfileA = uiState.selectedProfileA,
            selectedProfileB = uiState.selectedProfileB,
            isAnalyzing = uiState.isAnalyzing,
            onAnalyzeCompatibility = onAnalyzeCompatibility
        )
    }
}

@Composable
private fun InstructionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
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
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Spiritual Compatibility Analysis",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            Text(
                text = "Select two profiles to analyze their compatibility across numerology, astrology, tantric energy, and spiritual alignment. Our AI will provide personalized insights and recommendations.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ProfileSelectionSection(
    availableProfiles: List<UserProfile>,
    selectedProfileA: UserProfile?,
    selectedProfileB: UserProfile?,
    isLoadingProfiles: Boolean,
    onSelectProfileA: (UserProfile) -> Unit,
    onSelectProfileB: (UserProfile) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select Profiles for Analysis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            if (isLoadingProfiles) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Profile A Selection
                ProfileSelection(
                    title = "First Profile",
                    emoji = "👤",
                    selectedProfile = selectedProfileA,
                    availableProfiles = availableProfiles,
                    excludedProfile = selectedProfileB,
                    onSelectProfile = onSelectProfileA
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // VS Indicator
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💫 VS 💫",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Profile B Selection
                ProfileSelection(
                    title = "Second Profile",
                    emoji = "👥",
                    selectedProfile = selectedProfileB,
                    availableProfiles = availableProfiles,
                    excludedProfile = selectedProfileA,
                    onSelectProfile = onSelectProfileB
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileSelection(
    title: String,
    emoji: String,
    selectedProfile: UserProfile?,
    availableProfiles: List<UserProfile>,
    excludedProfile: UserProfile?,
    onSelectProfile: (UserProfile) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$emoji $title",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedProfile?.let { "${it.displayName ?: it.name}" } ?: "Select a profile...",
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                availableProfiles
                    .filter { it.id != excludedProfile?.id }
                    .forEach { profile ->
                        DropdownMenuItem(
                            text = {
                                ProfileDropdownItem(profile = profile)
                            },
                            onClick = {
                                onSelectProfile(profile)
                                expanded = false
                            },
                            leadingIcon = {
                                if (selectedProfile?.id == profile.id) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Selected",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
            }
        }
        
        // Selected profile preview
        selectedProfile?.let { profile ->
            ProfilePreviewCard(profile = profile)
        }
    }
}

@Composable
private fun ProfileDropdownItem(profile: UserProfile) {
    Column {
        Text(
            text = profile.displayName ?: profile.name ?: "Unnamed Profile",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "${profile.profileCompletion.completedFields}/${profile.profileCompletion.totalFields} fields • ${profile.profileCompletion.accuracyLevel.name}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProfilePreviewCard(profile: UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = profile.displayName ?: profile.name ?: "Unnamed Profile",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            profile.birthDateTime?.let { birthDate ->
                Text(
                    text = "Born: ${birthDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            profile.birthPlace?.let { birthPlace ->
                Text(
                    text = "📍 ${birthPlace.city}, ${birthPlace.country}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Profile completion indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LinearProgressIndicator(
                    progress = { (profile.profileCompletion.completionPercentage / 100f).toFloat() },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${profile.profileCompletion.completionPercentage.toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AnalysisSection(
    selectedProfileA: UserProfile?,
    selectedProfileB: UserProfile?,
    isAnalyzing: Boolean,
    onAnalyzeCompatibility: () -> Unit
) {
    val canAnalyze = selectedProfileA != null && selectedProfileB != null && !isAnalyzing
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (canAnalyze) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (canAnalyze) {
                    "🔮 Ready for Analysis!"
                } else if (isAnalyzing) {
                    "✨ Analyzing Compatibility..."
                } else {
                    "Select both profiles to continue"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            if (isAnalyzing) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Analyzing spiritual compatibility...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                ModernButton(
                    text = "Analyze Compatibility",
                    onClick = onAnalyzeCompatibility,
                    enabled = canAnalyze,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
