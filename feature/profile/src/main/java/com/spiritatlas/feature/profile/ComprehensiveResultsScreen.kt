package com.spiritatlas.feature.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.domain.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprehensiveResultsScreen(
    profile: EnhancedUserProfile,
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${profile.name}'s Spiritual Insights") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profile completion indicator
            ProfileCompletionCard(profile)
            
            // Basic Information Summary
            ExpandableInsightSection("📋 Basic Information") {
                ProfileBasicInfo(profile)
            }
            
            // Numerology Insights
            profile.numerologyProfile?.let { numerologyProfile ->
                ExpandableInsightSection("🔢 Numerology Insights") {
                    NumerologyInsights(numerologyProfile)
                }
            }

            // Astrology Insights
            profile.astroProfile?.let { astroProfile ->
                ExpandableInsightSection("⭐ Astrology Insights") {
                    AstrologyInsights(astroProfile)
                }
            }
            
            // Ayurveda Constitution
            if (profile.doshaProfile != null) {
                ExpandableInsightSection("🌿 Ayurveda Constitution") {
                    AyurvedaInsights(profile.doshaProfile!!)
                }
            }
            
            // Parental Influence (if data available)
            if (profile.hasParentalData) {
                ExpandableInsightSection("👪 Parental Influence") {
                    ParentalInfluenceInsights(profile)
                }
            }
            
            // Relationship Insights (if data available)
            if (profile.hasRelationshipData) {
                ExpandableInsightSection("💕 Relationship Insights") {
                    RelationshipInsights(profile)
                }
            }
            
            // Human Design (if available)
            profile.energyProfile?.let { energyProfile ->
                ExpandableInsightSection("⚡ Energy Profile") {
                    EnergyProfileInsights(energyProfile)
                }
            }
        }
    }
}

@Composable
private fun ProfileCompletionCard(profile: EnhancedUserProfile) {
    val completionPercentage = (profile.comprehensiveScore * 100).toInt()
    
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Profile Completion",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "$completionPercentage%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            LinearProgressIndicator(
                progress = profile.comprehensiveScore,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Text(
                buildString {
                    append("Includes: ")
                    val sections = mutableListOf<String>()
                    sections.add("Basic info")
                    if (profile.hasParentalData) sections.add("Parental data")
                    if (profile.hasRelationshipData) sections.add("Relationships")
                    if (profile.hasAyurvedaData) sections.add("Ayurveda")
                    append(sections.joinToString(", "))
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ExpandableInsightSection(
    title: String,
    initiallyExpanded: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }
    
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }
            
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun ProfileBasicInfo(profile: EnhancedUserProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        InfoRow("Name", profile.name)
        InfoRow("Birth Date", profile.birthDate.toString())
        profile.birthTime?.let { InfoRow("Birth Time", it) }
        profile.birthLocation?.let { InfoRow("Birth Location", it) }
    }
}

@Composable
private fun AyurvedaInsights(doshaProfile: DoshaProfile) {
    val dominant = doshaProfile.dominant
    val percentage = (doshaProfile.dominantPercentage * 100).toInt()
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Your Dominant Dosha: ${dominant.displayName} (${percentage}%)",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            dominant.description,
            style = MaterialTheme.typography.bodyMedium
        )
        
        Text(
            "Element: ${dominant.element}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        // Dosha breakdown
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DoshaIndicator("V", doshaProfile.vata, Dosha.VATA == dominant)
            DoshaIndicator("P", doshaProfile.pitta, Dosha.PITTA == dominant)
            DoshaIndicator("K", doshaProfile.kapha, Dosha.KAPHA == dominant)
        }
    }
}

@Composable
private fun DoshaIndicator(
    label: String,
    value: Int,
    isDominant: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Badge(
            containerColor = if (isDominant) 
                MaterialTheme.colorScheme.primary 
            else MaterialTheme.colorScheme.outline
        ) {
            Text(
                text = value.toString(),
                color = if (isDominant) 
                    MaterialTheme.colorScheme.onPrimary 
                else MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun ParentalInfluenceInsights(profile: EnhancedUserProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Generational Patterns",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        
        profile.motherBirthDate?.let {
            InfoRow("Mother's Birth Date", it.toString())
            Text(
                "Maternal influences and nurturing patterns can be analyzed through birth timing.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        profile.fatherBirthDate?.let {
            InfoRow("Father's Birth Date", it.toString())
            Text(
                "Paternal influences and structural patterns reflected in timing.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RelationshipInsights(profile: EnhancedUserProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Relationship Timeline",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        
        profile.firstKissDate?.let {
            InfoRow("First Kiss", it.toString())
            Text(
                "Romantic milestones can reveal patterns in relationship timing and emotional development.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        if (profile.relationshipMilestones.isNotEmpty()) {
            Text("Additional Milestones:", style = MaterialTheme.typography.labelMedium)
            profile.relationshipMilestones.forEach { (event, date) ->
                InfoRow(event, date.toString())
            }
        }
    }
}

@Composable
private fun NumerologyInsights(numerologyProfile: NumerologyProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        InfoRow("Life Path Number", numerologyProfile.lifePathNumber?.toString() ?: "Not calculated")
        InfoRow("Expression Number", numerologyProfile.expressionNumber?.toString() ?: "Not calculated")
        // Add more numerology insights as available
    }
}

@Composable
private fun AstrologyInsights(astroProfile: AstroProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        InfoRow("Sun Sign", astroProfile.sunSign ?: "Not calculated")
        InfoRow("Moon Sign", astroProfile.moonSign ?: "Not calculated")
        InfoRow("Rising Sign", astroProfile.risingSign ?: "Not calculated")
        // Add more astrology insights as available
    }
}

@Composable
private fun EnergyProfileInsights(energyProfile: EnergyProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        InfoRow("Energy Type", energyProfile.type ?: "Not calculated")
        InfoRow("Strategy", energyProfile.strategy ?: "Not available")
        // Add more energy profile insights as available
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}
