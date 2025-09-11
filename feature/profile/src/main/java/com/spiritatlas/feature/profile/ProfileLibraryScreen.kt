package com.spiritatlas.feature.profile

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.core.ui.theme.*
import com.spiritatlas.domain.model.*

/**
 * Modern profile library with Co-Star inspired design
 * Shows all profiles, allows sharing, and enables compatibility analysis
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLibraryScreen(
    onNavigateToProfile: (String) -> Unit,
    onNavigateToCompatibility: (String, String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ProfileLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val profiles by viewModel.profiles.collectAsState()
    
    var selectedForComparison by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    
    SpiritualGradientBackground(gradientType = SpiritualGradientType.MYSTIC) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Modern App Bar
            ProfileLibraryTopBar(
                showSearch = showSearch,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onToggleSearch = { showSearch = !showSearch },
                onNavigateBack = onNavigateBack,
                selectedCount = selectedForComparison.size,
                onClearSelection = { selectedForComparison = emptySet() },
                onCompareSelected = {
                    if (selectedForComparison.size == 2) {
                        val profileIds = selectedForComparison.toList()
                        onNavigateToCompatibility(profileIds[0], profileIds[1])
                    }
                }
            )
            
            // Profile Grid/List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Quick Actions Section
                item {
                    QuickActionsSection(
                        onCreateNewProfile = { onNavigateToProfile("") },
                        onImportProfile = { viewModel.importProfile() },
                        onExportAll = { viewModel.exportAllProfiles() }
                    )
                }
                
                // Profiles
                items(
                    items = profiles.filter { profile ->
                        if (searchQuery.isBlank()) true
                        else profile.profileName.contains(searchQuery, ignoreCase = true) ||
                             profile.displayName?.contains(searchQuery, ignoreCase = true) == true
                    },
                    key = { it.id }
                ) { profile ->
                    ModernProfileCard(
                        profile = profile,
                        isSelected = selectedForComparison.contains(profile.id),
                        onCardClick = { onNavigateToProfile(profile.id) },
                        onSelectionToggle = { 
                            selectedForComparison = if (selectedForComparison.contains(profile.id)) {
                                selectedForComparison - profile.id
                            } else if (selectedForComparison.size < 2) {
                                selectedForComparison + profile.id
                            } else selectedForComparison
                        },
                        onShareProfile = { viewModel.shareProfile(profile.id) },
                        onEnrichProfile = { viewModel.enrichProfile(profile.id) },
                        selectionMode = selectedForComparison.isNotEmpty()
                    )
                }
                
                // Empty state
                if (profiles.isEmpty()) {
                    item {
                        EmptyProfileLibrary(
                            onCreateProfile = { onNavigateToProfile("") }
                        )
                    }
                }
            }
        }
    }

    // Handle UI state changes
    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            // Show snackbar with message
            viewModel.clearMessage()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLibraryTopBar(
    showSearch: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onNavigateBack: () -> Unit,
    selectedCount: Int,
    onClearSelection: () -> Unit,
    onCompareSelected: () -> Unit
) {
    if (selectedCount > 0) {
        // Selection mode top bar
        TopAppBar(
            title = {
                Text(
                    text = "$selectedCount selected",
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = onClearSelection) {
                    Icon(Icons.Default.Close, contentDescription = "Clear selection")
                }
            },
            actions = {
                if (selectedCount == 2) {
                    SpiritualButton(
                        text = "Compare",
                        onClick = onCompareSelected,
                        style = SpiritualButtonStyle.PRIMARY,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        )
    } else if (showSearch) {
        // Search mode top bar
        TopAppBar(
            title = {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search profiles...") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onToggleSearch) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close search")
                }
            },
            actions = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        )
    } else {
        // Normal mode top bar
        TopAppBar(
            title = {
                Text(
                    text = "Profile Library",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = SpiritualPurple
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = onToggleSearch) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ModernProfileCard(
    profile: UserProfile,
    isSelected: Boolean,
    onCardClick: () -> Unit,
    onSelectionToggle: () -> Unit,
    onShareProfile: () -> Unit,
    onEnrichProfile: () -> Unit,
    selectionMode: Boolean
) {
    val cardModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(
            if (isSelected) {
                SpiritualPurple.copy(alpha = 0.1f)
            } else {
                Color.White.copy(alpha = 0.9f)
            }
        )
    
    GlassmorphCard(
        modifier = cardModifier,
        elevation = if (isSelected) 3 else 1
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = profile.profileName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SpiritualPurple,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                profile.displayName?.let { displayName ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = displayName,
                        fontSize = 14.sp,
                        color = DeepTeal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Completion indicator
                ProfileCompletionIndicator(
                    completionPercentage = profile.profileCompletion.completionPercentage,
                    showLabel = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                
                // Enrichment status
                if (profile.enrichmentResult != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Enriched",
                            tint = CosmicOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Enriched",
                            fontSize = 12.sp,
                            color = CosmicOrange,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Action buttons
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectionMode) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { onSelectionToggle() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = SpiritualPurple
                        )
                    )
                } else {
                    // Action buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Share button
                        IconButton(
                            onClick = onShareProfile,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = SpiritualPurple
                            )
                        }
                        
                        // Enrich button
                        IconButton(
                            onClick = onEnrichProfile,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Enrich",
                                tint = CosmicOrange
                            )
                        }
                    }
                    
                    // Open button
                    SpiritualButton(
                        text = "Open",
                        onClick = onCardClick,
                        style = SpiritualButtonStyle.PRIMARY,
                        modifier = Modifier.width(80.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionsSection(
    onCreateNewProfile: () -> Unit,
    onImportProfile: () -> Unit,
    onExportAll: () -> Unit
) {
    SpiritualSectionHeader(
        title = "Quick Actions",
        subtitle = "Create, import, or manage profiles"
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SpiritualButton(
            text = "➕ New Profile",
            onClick = onCreateNewProfile,
            style = SpiritualButtonStyle.PRIMARY,
            modifier = Modifier.weight(1f)
        )
        
        SpiritualButton(
            text = "📥 Import",
            onClick = onImportProfile,
            style = SpiritualButtonStyle.SECONDARY,
            modifier = Modifier.weight(1f)
        )
    }
    
    Spacer(modifier = Modifier.height(32.dp))
    
    SpiritualSectionHeader(
        title = "All Profiles",
        subtitle = "Select two profiles to analyze compatibility"
    )
    
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun EmptyProfileLibrary(
    onCreateProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "✨",
            fontSize = 64.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No Profiles Yet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Create your first spiritual profile to begin your journey of self-discovery and find your perfect compatibility matches.",
            fontSize = 16.sp,
            color = DeepTeal,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        SpiritualButton(
            text = "Create First Profile",
            onClick = onCreateProfile,
            style = SpiritualButtonStyle.PRIMARY,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}
