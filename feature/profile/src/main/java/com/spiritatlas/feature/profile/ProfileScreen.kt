package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.DateTimePicker
import com.spiritatlas.domain.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(profileId) {
        viewModel.loadProfile(profileId)
    }
    
    Scaffold(
        topBar = {
            ProfileTopBar(
                isEditing = profileId != null && profileId != "new",
                completion = uiState.currentProfile.profileCompletion,
                isSaving = uiState.isSaving,
                onNavigateBack = onNavigateBack,
                onSave = { viewModel.saveProfile() },
                onLoadTier = { tier -> 
                    when(tier) {
                        0 -> viewModel.loadTier0Profile()
                        1 -> viewModel.loadTier1Profile()
                        2 -> viewModel.loadTier2Profile()
                        3 -> viewModel.loadTier3Profile()
                    }
                },
                onLoadDemo = { viewModel.loadDemoProfile() },
                onClear = { viewModel.clearProfile() }
            )
        }
    ) { paddingValues ->
        ProfileContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onProfileUpdate = { viewModel.updateProfile(it) },
            onSectionSelected = { viewModel.setActiveSection(it) },
            onClearError = { viewModel.clearError() }
        )
    }
}

@Composable
fun AccuracyIndicator(completion: ProfileCompletion) {
    Card(
        modifier = Modifier
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
        )
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
            
            // Progress bar
            LinearProgressIndicator(
                progress = (completion.completionPercentage / 100f).toFloat(),
                modifier = Modifier.fillMaxWidth()
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

@Composable
fun ProfileSectionTabs(
    activeSection: ProfileSection,
    onSectionSelected: (ProfileSection) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = activeSection.ordinal,
        modifier = Modifier.fillMaxWidth()
    ) {
        ProfileSection.values().forEach { section ->
            Tab(
                selected = activeSection == section,
                onClick = { onSectionSelected(section) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = section.icon,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = section.displayName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun CoreIdentitySection(
    profile: UserProfile,
    onProfileUpdate: (UserProfile) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "✨ Core Identity (Required - Pick 3 minimum)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = profile.name ?: "",
                onValueChange = { 
                    onProfileUpdate(profile.copy(name = if (it.isBlank()) null else it))
                },
                label = { Text("Full Birth Name *") },
                placeholder = { Text("e.g., Luna Alexandra Moonwhisper") },
                supportingText = { Text("🔢 Essential for numerology calculations") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = profile.displayName ?: "",
                onValueChange = { 
                    onProfileUpdate(profile.copy(displayName = if (it.isBlank()) null else it))
                },
                label = { Text("Preferred Name *") },
                placeholder = { Text("e.g., Luna") },
                supportingText = { Text("👤 What you like to be called") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Birth Date/Time with proper picker
            DateTimePicker(
                selectedDateTime = profile.birthDateTime,
                onDateTimeChange = { newDateTime ->
                    onProfileUpdate(profile.copy(birthDateTime = newDateTime))
                },
                label = "Birth Date & Time *",
                modifier = Modifier.fillMaxWidth()
            )
            
            Text(
                text = "🕐 Exact time crucial for astrology accuracy",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Birth Place (simplified for demo) 
            OutlinedTextField(
                value = profile.birthPlace?.city ?: "",
                onValueChange = { 
                    val currentPlace = profile.birthPlace
                    val newPlace = if (it.isBlank()) null else BirthPlace(
                        city = it,
                        country = currentPlace?.country ?: "",
                        latitude = currentPlace?.latitude ?: 0.0,
                        longitude = currentPlace?.longitude ?: 0.0
                    )
                    onProfileUpdate(profile.copy(birthPlace = newPlace))
                },
                label = { Text("Birth City *") },
                placeholder = { Text("e.g., Sedona") },
                supportingText = { Text("🌍 Location affects astrological calculations") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Placeholder sections (simplified for demo)
@Composable
fun AdditionalNamesSection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("🏷️ Additional Names", "Enhance numerology accuracy") {
        ProfileTextField(
            value = profile.middleName,
            onValueChange = { onProfileUpdate(profile.copy(middleName = it)) },
            label = "Middle Name(s)",
            placeholder = "e.g., Alexandra Rose"
        )
        ProfileTextField(
            value = profile.nickname,
            onValueChange = { onProfileUpdate(profile.copy(nickname = it)) },
            label = "Nickname",
            placeholder = "e.g., Lunar"
        )
        ProfileTextField(
            value = profile.spiritualName,
            onValueChange = { onProfileUpdate(profile.copy(spiritualName = it)) },
            label = "Spiritual Name",
            placeholder = "e.g., Moonwhisper"
        )
    }
}

@Composable
fun FamilyAncestrySection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("👨‍👩‍👧‍👦 Family & Ancestry", "Deeper karmic insights") {
        ProfileTextField(
            value = profile.motherName,
            onValueChange = { onProfileUpdate(profile.copy(motherName = it)) },
            label = "Mother's Full Name",
            placeholder = "e.g., Celeste Maya Starweaver"
        )
        ProfileTextField(
            value = profile.fatherName,
            onValueChange = { onProfileUpdate(profile.copy(fatherName = it)) },
            label = "Father's Full Name", 
            placeholder = "e.g., Orion David Starweaver"
        )
        ProfileTextField(
            value = profile.ancestry,
            onValueChange = { onProfileUpdate(profile.copy(ancestry = it)) },
            label = "Cultural Ancestry",
            placeholder = "e.g., Celtic-Cherokee-Italian"
        )
    }
}

@Composable
fun PhysicalEnergeticSection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("⚡ Physical & Energetic", "Energy flow patterns") {
        // Gender dropdown (simplified)
        ProfileTextField(
            value = profile.gender?.toString(),
            onValueChange = { /* TODO: Implement dropdown */ },
            label = "Gender Energy",
            placeholder = "Select gender energy type"
        )
        ProfileTextField(
            value = profile.eyeColor,
            onValueChange = { onProfileUpdate(profile.copy(eyeColor = it)) },
            label = "Eye Color",
            placeholder = "e.g., Deep ocean blue with golden flecks"
        )
    }
}

@Composable
fun TimingCyclesSection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("🕐 Key Timing", "Essential life moments") {
        Text("Precise timing for key life moments", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 16.dp))
        
        DateTimePicker(
            selectedDateTime = profile.firstBreath,
            onDateTimeChange = { newDateTime ->
                onProfileUpdate(profile.copy(firstBreath = newDateTime))
            },
            label = "First Breath Moment",
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
        
        DateTimePicker(
            selectedDateTime = profile.firstSteps,
            onDateTimeChange = { newDateTime ->
                onProfileUpdate(profile.copy(firstSteps = newDateTime))
            },
            label = "First Steps Date",
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
    }
}

@Composable
fun EnvironmentalSection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("🌙 Environmental", "Birth environment influences") {
        ProfileTextField(
            value = profile.moonPhase,
            onValueChange = { onProfileUpdate(profile.copy(moonPhase = it)) },
            label = "Moon Phase at Birth",
            placeholder = "e.g., Waxing Gibbous (84% illumination)"
        )
        ProfileTextField(
            value = profile.weatherConditions,
            onValueChange = { onProfileUpdate(profile.copy(weatherConditions = it)) },
            label = "Weather Conditions",
            placeholder = "e.g., Clear night, full moon visible"
        )
        ProfileTextField(
            value = profile.hospitalName,
            onValueChange = { onProfileUpdate(profile.copy(hospitalName = it)) },
            label = "Hospital/Birth Center",
            placeholder = "e.g., Sedona Sacred Birth Center"
        )
    }
}

@Composable
fun LifePatternsSection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("🌱 Life Patterns", "Personal development markers") {
        ProfileTextField(
            value = profile.firstWord,
            onValueChange = { onProfileUpdate(profile.copy(firstWord = it)) },
            label = "First Word",
            placeholder = "e.g., Light"
        )
        Text(
            text = "Life events and spiritual awakenings can be added here",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun PreferencesSection(profile: UserProfile, onProfileUpdate: (UserProfile) -> Unit) {
    SectionCard("⚙️ Calculation Preferences", "How to calculate your insights") {
        Text("Zodiac System: ${if (profile.preferences.usesSiderealZodiac) "Sidereal" else "Tropical"}", 
             style = MaterialTheme.typography.bodyMedium)
        Text("Numerology: ${profile.preferences.numerologySystem}", 
             style = MaterialTheme.typography.bodyMedium)
        Text("Detail Level: ${profile.preferences.detailLevel}", 
             style = MaterialTheme.typography.bodyMedium)
    }
}

// Helper components
@Composable
fun SectionCard(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun ProfileTextField(
    value: String?,
    onValueChange: (String?) -> Unit,
    label: String,
    placeholder: String
) {
    OutlinedTextField(
        value = value ?: "",
        onValueChange = { onValueChange(if (it.isBlank()) null else it) },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

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
    onClear: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    
    TopAppBar(
        title = { 
            Column {
                Text(if (isEditing) "Edit Profile ✨" else "Create Profile ✨")
                Text(
                    text = "${completion.completedFields}/${completion.totalFields} fields • ${completion.accuracyLevel.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { showMenu = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                (0..3).forEach { tier ->
                    DropdownMenuItem(
                        text = { Text("Tier $tier (${getTierDescription(tier)})") },
                        onClick = { onLoadTier(tier); showMenu = false }
                    )
                }
                HorizontalDivider()
                DropdownMenuItem(
                    text = { Text("Load Demo") },
                    onClick = { onLoadDemo(); showMenu = false }
                )
                DropdownMenuItem(
                    text = { Text("Clear Profile") },
                    onClick = { onClear(); showMenu = false }
                )
            }
            
            TextButton(
                onClick = onSave,
                enabled = !isSaving
            ) {
                Text(if (isSaving) "🔄 Saving..." else "💾 Save")
            }
        }
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    onProfileUpdate: (UserProfile) -> Unit,
    onSectionSelected: (ProfileSection) -> Unit,
    onClearError: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StatusMessages(
            errorMessage = uiState.errorMessage,
            saveSuccess = uiState.saveSuccess,
            onClearError = onClearError
        )
        
        AccuracyIndicator(uiState.currentProfile.profileCompletion)
        Spacer(modifier = Modifier.height(16.dp))
        
        ProfileSectionTabs(
            activeSection = uiState.activeSection,
            onSectionSelected = onSectionSelected
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ProfileSectionContent(
            activeSection = uiState.activeSection,
            profile = uiState.currentProfile,
            onProfileUpdate = onProfileUpdate
        )
        
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun StatusMessages(
    errorMessage: String?,
    saveSuccess: Boolean,
    onClearError: () -> Unit
) {
    errorMessage?.let { error ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Text(
                text = "⚠️ $error",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
    
    if (saveSuccess) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "✨ Profile saved successfully!",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ProfileSectionContent(
    activeSection: ProfileSection,
    profile: UserProfile,
    onProfileUpdate: (UserProfile) -> Unit
) {
    when (activeSection) {
        ProfileSection.CORE -> CoreIdentitySection(profile, onProfileUpdate)
        ProfileSection.NAMES -> AdditionalNamesSection(profile, onProfileUpdate)
        ProfileSection.FAMILY -> FamilyAncestrySection(profile, onProfileUpdate)
        ProfileSection.PHYSICAL -> PhysicalEnergeticSection(profile, onProfileUpdate)
        ProfileSection.TIMING -> TimingCyclesSection(profile, onProfileUpdate)
        ProfileSection.ENVIRONMENTAL -> EnvironmentalSection(profile, onProfileUpdate)
        ProfileSection.LIFE_PATTERNS -> LifePatternsSection(profile, onProfileUpdate)
        ProfileSection.PREFERENCES -> PreferencesSection(profile, onProfileUpdate)
    }
}

fun getTierDescription(tier: Int): String = when (tier) {
    0 -> "3-9 fields, 300 words"
    1 -> "10-18 fields, 900 words"
    2 -> "19-27 fields, 1800 words"
    3 -> "28-36 fields, 2700 words"
    else -> "Unknown tier"
}



