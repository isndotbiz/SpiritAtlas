package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.components.DateTimePicker
import com.spiritatlas.core.ui.components.ModernDropdown
import com.spiritatlas.domain.model.*

/**
 * Extracted profile section components for better code organization
 * Reduces the size of ProfileScreen.kt and improves maintainability
 */

@Composable
fun ProfileSectionTabs(
    activeSection: ProfileSection,
    onSectionSelected: (ProfileSection) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = activeSection.ordinal,
        modifier = modifier.fillMaxWidth()
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
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "✨ Core Identity (Required - Pick 3 minimum)",
        modifier = modifier
    ) {
        ProfileTextField(
            value = profile.name,
            onValueChange = { onProfileUpdate(profile.copy(name = it)) },
            label = "Full Birth Name *",
            placeholder = "e.g., Luna Alexandra Moonwhisper",
            supportingText = "🔢 Essential for numerology calculations"
        )
        
        ProfileTextField(
            value = profile.displayName,
            onValueChange = { onProfileUpdate(profile.copy(displayName = it)) },
            label = "Preferred Name *",
            placeholder = "e.g., Luna",
            supportingText = "👤 What you like to be called"
        )
        
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
        
        // Birth Place (simplified for demo)
        ProfileTextField(
            value = profile.birthPlace?.city,
            onValueChange = { cityName ->
                val currentPlace = profile.birthPlace
                val newPlace = if (cityName.isNullOrBlank()) null else BirthPlace(
                    city = cityName,
                    country = currentPlace?.country ?: "",
                    latitude = currentPlace?.latitude ?: 0.0,
                    longitude = currentPlace?.longitude ?: 0.0
                )
                onProfileUpdate(profile.copy(birthPlace = newPlace))
            },
            label = "Birth City *",
            placeholder = "e.g., Sedona",
            supportingText = "🌍 Location affects astrological calculations"
        )
    }
}

@Composable
fun AdditionalNamesSection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "🏷️ Additional Names", 
        subtitle = "Enhance numerology accuracy",
        modifier = modifier
    ) {
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
fun FamilyAncestrySection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "👨‍👩‍👧‍👦 Family & Ancestry", 
        subtitle = "Deeper karmic insights",
        modifier = modifier
    ) {
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
fun PhysicalEnergeticSection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "⚡ Physical & Energetic", 
        subtitle = "Energy flow patterns",
        modifier = modifier
    ) {
        // Gender dropdown with modern UI
        ModernDropdown(
            selectedValue = profile.gender,
            onValueChange = { newGender ->
                onProfileUpdate(profile.copy(gender = newGender))
            },
            options = Gender.values().toList(),
            label = "Gender Energy",
            placeholder = "Select gender energy type",
            displayTransform = { gender ->
                when (gender) {
                    Gender.MASCULINE -> "♂️ Masculine Energy"
                    Gender.FEMININE -> "♀️ Feminine Energy"  
                    Gender.NON_BINARY -> "⚧️ Non-Binary Energy"
                    Gender.PREFER_NOT_TO_SAY -> "🌟 Prefer Not to Say"
                }
            },
            modifier = Modifier.fillMaxWidth()
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
fun TimingCyclesSection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "🕐 Key Timing", 
        subtitle = "Essential life moments",
        modifier = modifier
    ) {
        Text(
            "Precise timing for key life moments", 
            style = MaterialTheme.typography.bodyMedium, 
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
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
fun EnvironmentalSection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "🌙 Environmental", 
        subtitle = "Birth environment influences",
        modifier = modifier
    ) {
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
fun LifePatternsSection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "🌱 Life Patterns", 
        subtitle = "Personal development markers",
        modifier = modifier
    ) {
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
fun PreferencesSection(
    profile: UserProfile, 
    onProfileUpdate: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = "⚙️ Calculation Preferences", 
        subtitle = "How to calculate your insights",
        modifier = modifier
    ) {
        Text(
            "Zodiac System: ${if (profile.preferences.usesSiderealZodiac) "Sidereal" else "Tropical"}", 
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "Numerology: ${profile.preferences.numerologySystem}", 
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "Detail Level: ${profile.preferences.detailLevel}", 
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Helper components
@Composable
fun SectionCard(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            content()
        }
    }
}

@Composable
fun ProfileTextField(
    value: String?,
    onValueChange: (String?) -> Unit,
    label: String,
    placeholder: String = "",
    supportingText: String? = null,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value ?: "",
        onValueChange = { newValue ->
            onValueChange(if (newValue.isBlank()) null else newValue)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        supportingText = supportingText?.let { { Text(it) } },
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    )
}
