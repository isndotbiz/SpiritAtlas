package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.SacredGeometryBackground
import com.spiritatlas.domain.model.UserProfile
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ProfileListScreen(
    onNavigateToProfile: (profileId: String?) -> Unit, // null for new profile
    onNavigateToComparison: (profileId1: String, profileId2: String) -> Unit,
    onNavigateToCompatibility: () -> Unit = {},
    onNavigateBack: () -> Unit,
    onNavigateToEnrichment: (profileId: String) -> Unit,
    viewModel: ProfileListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<String?>(null) }
    var selectionMode by remember { mutableStateOf(false) }
    var selectedProfiles by remember { mutableStateOf(setOf<String>()) }

    // Profile list with sacred geometry background
    SacredGeometryBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Spiritual Profiles",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row {
                if (selectionMode && selectedProfiles.size == 2) {
                    IconButton(
                        onClick = {
                            val profiles = selectedProfiles.toList()
                            onNavigateToComparison(profiles[0], profiles[1])
                        }
                    ) {
                        Icon(Icons.Default.Compare, contentDescription = "Compare")
                    }
                }

                IconButton(
                    onClick = {
                        selectionMode = !selectionMode
                        if (!selectionMode) {
                            selectedProfiles = emptySet()
                        }
                    }
                ) {
                    Icon(
                        if (selectionMode) Icons.Default.Close else Icons.Default.CheckCircle,
                        contentDescription = if (selectionMode) "Cancel Selection" else "Select Profiles"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Create New Profile Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onClick = { onNavigateToProfile(null) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create New Profile",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Create New Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Start a new spiritual profile",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Compatibility Analysis Button  
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onClick = onNavigateToCompatibility,
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Psychology,
                    contentDescription = "Compatibility Analysis",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "💫 Compatibility Analysis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Discover spiritual relationship compatibility",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Profile Count and Selection Status
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${uiState.profiles.size} profiles",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (selectionMode) {
                Text(
                    text = "${selectedProfiles.size} selected" + 
                        if (selectedProfiles.size == 2) " (ready to compare)" else "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Profiles List
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.profiles.isEmpty()) {
            // Empty State
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.PersonAdd,
                        contentDescription = "Add your first profile",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Profiles Yet",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Create your first spiritual profile to get started with personalized insights.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.profiles, key = { it.id }) { profile ->
                    ProfileCard(
                        profile = profile,
                        isSelected = selectedProfiles.contains(profile.id),
                        selectionMode = selectionMode,
                        onSelect = { profileId ->
                            selectedProfiles = if (selectedProfiles.contains(profileId)) {
                                selectedProfiles - profileId
                            } else if (selectedProfiles.size < 2) {
                                selectedProfiles + profileId
                            } else {
                                selectedProfiles
                            }
                        },
                        onClick = { 
                            if (selectionMode) {
                                // Toggle selection
                                selectedProfiles = if (selectedProfiles.contains(profile.id)) {
                                    selectedProfiles - profile.id
                                } else if (selectedProfiles.size < 2) {
                                    selectedProfiles + profile.id
                                } else {
                                    selectedProfiles
                                }
                            } else {
                                onNavigateToProfile(profile.id)
                            }
                        },
                        onDelete = { showDeleteDialog = profile.id },
                        onRunEnrichment = { onNavigateToEnrichment(profile.id) }
                    )
                }
            }
        }
    }

    // Delete confirmation dialog
    showDeleteDialog?.let { profileId ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Profile?") },
            text = { Text("Are you sure you want to delete this profile? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteProfile(profileId)
                        showDeleteDialog = null
                        selectedProfiles = selectedProfiles - profileId
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancel")
                }
            }
        )
        }
    }
}

@Composable
private fun ProfileCard(
    profile: UserProfile,
    isSelected: Boolean,
    selectionMode: Boolean,
    onSelect: (String) -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onRunEnrichment: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder().copy(
                width = 2.dp,
                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
            )
        } else null
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Selection checkbox or profile icon
            if (selectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelect(profile.id) }
                )
            } else {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile avatar",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Profile info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = profile.profileName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                profile.name?.let { name ->
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    profile.birthDateTime?.let { birthDate ->
                        Text(
                            text = birthDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = "${profile.profileCompletion.completedFields}/${profile.profileCompletion.totalFields} fields",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Accuracy level indicator
                Text(
                    text = "Accuracy: ${profile.profileCompletion.accuracyLevel.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.labelSmall,
                    color = when (profile.profileCompletion.accuracyLevel.name) {
                        "MAXIMUM" -> MaterialTheme.colorScheme.primary
                        "EXCELLENT" -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            // Action buttons (only when not in selection mode)
            if (!selectionMode) {
                Row {
                    IconButton(onClick = onRunEnrichment) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = "Run AI Enrichment",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Profile",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
