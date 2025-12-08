package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        title = stringResource(R.string.core_identity_title),
        modifier = modifier
    ) {
        ProfileTextField(
            value = profile.name,
            onValueChange = { onProfileUpdate(profile.copy(name = it)) },
            label = stringResource(R.string.full_birth_name_label),
            placeholder = stringResource(R.string.full_birth_name_placeholder),
            supportingText = stringResource(R.string.full_birth_name_supporting)
        )

        ProfileTextField(
            value = profile.displayName,
            onValueChange = { onProfileUpdate(profile.copy(displayName = it)) },
            label = stringResource(R.string.preferred_name_label),
            placeholder = stringResource(R.string.preferred_name_placeholder),
            supportingText = stringResource(R.string.preferred_name_supporting)
        )

        // Birth Date/Time with proper picker
        DateTimePicker(
            selectedDateTime = profile.birthDateTime,
            onDateTimeChange = { newDateTime ->
                onProfileUpdate(profile.copy(birthDateTime = newDateTime))
            },
            label = stringResource(R.string.birth_date_time_label),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.birth_time_supporting),
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
            label = stringResource(R.string.birth_city_label),
            placeholder = stringResource(R.string.birth_city_placeholder),
            supportingText = stringResource(R.string.birth_city_supporting)
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
        title = stringResource(R.string.additional_names_title),
        subtitle = stringResource(R.string.additional_names_subtitle),
        modifier = modifier
    ) {
        ProfileTextField(
            value = profile.middleName,
            onValueChange = { onProfileUpdate(profile.copy(middleName = it)) },
            label = stringResource(R.string.middle_name_label),
            placeholder = stringResource(R.string.middle_name_placeholder)
        )
        ProfileTextField(
            value = profile.nickname,
            onValueChange = { onProfileUpdate(profile.copy(nickname = it)) },
            label = stringResource(R.string.nickname_label),
            placeholder = stringResource(R.string.nickname_placeholder)
        )
        ProfileTextField(
            value = profile.spiritualName,
            onValueChange = { onProfileUpdate(profile.copy(spiritualName = it)) },
            label = stringResource(R.string.spiritual_name_label),
            placeholder = stringResource(R.string.spiritual_name_placeholder)
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
        title = stringResource(R.string.family_ancestry_title),
        subtitle = stringResource(R.string.family_ancestry_subtitle),
        modifier = modifier
    ) {
        ProfileTextField(
            value = profile.motherName,
            onValueChange = { onProfileUpdate(profile.copy(motherName = it)) },
            label = stringResource(R.string.mother_name_label),
            placeholder = stringResource(R.string.mother_name_placeholder)
        )
        ProfileTextField(
            value = profile.fatherName,
            onValueChange = { onProfileUpdate(profile.copy(fatherName = it)) },
            label = stringResource(R.string.father_name_label),
            placeholder = stringResource(R.string.father_name_placeholder)
        )
        ProfileTextField(
            value = profile.ancestry,
            onValueChange = { onProfileUpdate(profile.copy(ancestry = it)) },
            label = stringResource(R.string.cultural_ancestry_label),
            placeholder = stringResource(R.string.cultural_ancestry_placeholder)
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
        title = stringResource(R.string.physical_energetic_title),
        subtitle = stringResource(R.string.physical_energetic_subtitle),
        modifier = modifier
    ) {
        // Gender dropdown with modern UI
        ModernDropdown(
            selectedValue = profile.gender,
            onValueChange = { newGender ->
                onProfileUpdate(profile.copy(gender = newGender))
            },
            options = Gender.values().toList(),
            label = stringResource(R.string.gender_energy_label),
            placeholder = stringResource(R.string.gender_energy_placeholder),
            displayTransform = { gender ->
                when (gender) {
                    Gender.MASCULINE -> stringResource(R.string.masculine_energy)
                    Gender.FEMININE -> stringResource(R.string.feminine_energy)
                    Gender.NON_BINARY -> stringResource(R.string.non_binary_energy)
                    Gender.PREFER_NOT_TO_SAY -> stringResource(R.string.prefer_not_to_say)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        ProfileTextField(
            value = profile.eyeColor,
            onValueChange = { onProfileUpdate(profile.copy(eyeColor = it)) },
            label = stringResource(R.string.eye_color_label),
            placeholder = stringResource(R.string.eye_color_placeholder)
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
        title = stringResource(R.string.key_timing_title),
        subtitle = stringResource(R.string.key_timing_subtitle),
        modifier = modifier
    ) {
        Text(
            stringResource(R.string.precise_timing_description),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DateTimePicker(
            selectedDateTime = profile.firstBreath,
            onDateTimeChange = { newDateTime ->
                onProfileUpdate(profile.copy(firstBreath = newDateTime))
            },
            label = stringResource(R.string.first_breath_label),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        DateTimePicker(
            selectedDateTime = profile.firstSteps,
            onDateTimeChange = { newDateTime ->
                onProfileUpdate(profile.copy(firstSteps = newDateTime))
            },
            label = stringResource(R.string.first_steps_label),
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
        title = stringResource(R.string.environmental_title),
        subtitle = stringResource(R.string.environmental_subtitle),
        modifier = modifier
    ) {
        ProfileTextField(
            value = profile.moonPhase,
            onValueChange = { onProfileUpdate(profile.copy(moonPhase = it)) },
            label = stringResource(R.string.moon_phase_label),
            placeholder = stringResource(R.string.moon_phase_placeholder)
        )
        ProfileTextField(
            value = profile.weatherConditions,
            onValueChange = { onProfileUpdate(profile.copy(weatherConditions = it)) },
            label = stringResource(R.string.weather_conditions_label),
            placeholder = stringResource(R.string.weather_conditions_placeholder)
        )
        ProfileTextField(
            value = profile.hospitalName,
            onValueChange = { onProfileUpdate(profile.copy(hospitalName = it)) },
            label = stringResource(R.string.hospital_name_label),
            placeholder = stringResource(R.string.hospital_name_placeholder)
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
        title = stringResource(R.string.life_patterns_title),
        subtitle = stringResource(R.string.life_patterns_subtitle),
        modifier = modifier
    ) {
        ProfileTextField(
            value = profile.firstWord,
            onValueChange = { onProfileUpdate(profile.copy(firstWord = it)) },
            label = stringResource(R.string.first_word_label),
            placeholder = stringResource(R.string.first_word_placeholder)
        )
        Text(
            text = stringResource(R.string.life_events_description),
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
        title = stringResource(R.string.calculation_preferences_title),
        subtitle = stringResource(R.string.calculation_preferences_subtitle),
        modifier = modifier
    ) {
        Text(
            stringResource(
                R.string.zodiac_system_format,
                if (profile.preferences.usesSiderealZodiac)
                    stringResource(R.string.zodiac_sidereal)
                else
                    stringResource(R.string.zodiac_tropical)
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            stringResource(R.string.numerology_format, profile.preferences.numerologySystem),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            stringResource(R.string.detail_level_format, profile.preferences.detailLevel),
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
