package com.spiritatlas.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*

/**
 * Extended Spiritual Icons Example Showcase
 * Demonstrates all new icons from SpiritualIconsExtended.kt
 */

@Composable
fun SpiritualIconsExtendedExample(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "SpiritAtlas Extended Icons",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "35+ new spiritual icons for complete app coverage",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Tantric Symbols
        item {
            IconCategoryCard(
                title = "Tantric Symbols",
                description = "Sacred intimacy and energy from Tantric philosophy"
            ) {
                IconGridRow {
                    IconItem("Yoni") { YoniIcon(size = 40.dp) }
                    IconItem("Lingam") { LingamIcon(size = 40.dp) }
                    IconItem("Bindu") { BinduIcon(size = 40.dp) }
                    IconItem("Kundalini") { KundaliniIcon(size = 40.dp) }
                }
            }
        }

        // Human Design Types
        item {
            IconCategoryCard(
                title = "Human Design Energy Types",
                description = "Four energy types from Human Design system"
            ) {
                IconGridRow {
                    IconItem("Manifestor") { ManifestorIcon(size = 40.dp) }
                    IconItem("Generator") { GeneratorIcon(size = 40.dp) }
                    IconItem("Projector") { ProjectorIcon(size = 40.dp) }
                    IconItem("Reflector") { ReflectorIcon(size = 40.dp) }
                }
            }
        }

        // Relationship Icons
        item {
            IconCategoryCard(
                title = "Relationship & Connection",
                description = "Compatibility and relationship energy visualization"
            ) {
                IconGridRow {
                    IconItem("Connection") { ConnectionIcon(size = 40.dp) }
                    IconItem("Harmony") { HarmonyIcon(size = 40.dp) }
                    IconItem("Attraction") { AttractionIcon(size = 40.dp) }
                    IconItem("Bond") { BondIcon(size = 40.dp) }
                }
            }
        }

        // Meditation States
        item {
            IconCategoryCard(
                title = "Meditation States",
                description = "States of consciousness and awareness"
            ) {
                IconGridRow {
                    IconItem("Calm") { CalmIcon(size = 40.dp) }
                    IconItem("Focus") { FocusIcon(size = 40.dp) }
                    IconItem("Expansion") { ExpansionIcon(size = 40.dp) }
                    IconItem("Transcendence") { TranscendenceIcon(size = 40.dp) }
                }
            }
        }

        // Energy States
        item {
            IconCategoryCard(
                title = "Energy States",
                description = "Vital energy levels and flow states"
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconGridRow {
                        IconItem("High Energy") { HighEnergyIcon(size = 40.dp) }
                        IconItem("Low Energy") { LowEnergyIcon(size = 40.dp) }
                        IconItem("Balanced") { BalancedEnergyIcon(size = 40.dp) }
                    }
                    IconGridRow {
                        IconItem("Flowing") { FlowingEnergyIcon(size = 40.dp) }
                        IconItem("Blocked") { BlockedEnergyIcon(size = 40.dp) }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Ayurvedic Doshas
        item {
            IconCategoryCard(
                title = "Ayurvedic Doshas",
                description = "Three constitutional types from Ayurveda"
            ) {
                IconGridRow {
                    IconItem("Vata") { VataIcon(size = 40.dp) }
                    IconItem("Pitta") { PittaIcon(size = 40.dp) }
                    IconItem("Kapha") { KaphaIcon(size = 40.dp) }
                }
            }
        }

        // Sacred Symbols
        item {
            IconCategoryCard(
                title = "Sacred Symbols",
                description = "Universal spiritual and protective symbols"
            ) {
                IconGridRow {
                    IconItem("Om") { OmIcon(size = 40.dp) }
                    IconItem("Hamsa") { HamsaIcon(size = 40.dp) }
                    IconItem("Ankh") { AnkhIcon(size = 40.dp) }
                    IconItem("Eye of Horus") { EyeOfHorusIcon(size = 40.dp) }
                }
            }
        }

        // Size Variations
        item {
            IconCategoryCard(
                title = "Size Variations",
                description = "Same icon at different sizes"
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        YoniIcon(size = 16.dp)
                        Text("16dp", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        YoniIcon(size = 24.dp)
                        Text("24dp", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        YoniIcon(size = 32.dp)
                        Text("32dp", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        YoniIcon(size = 48.dp)
                        Text("48dp", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        YoniIcon(size = 64.dp)
                        Text("64dp", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Color Variations
        item {
            IconCategoryCard(
                title = "Color Variations",
                description = "Same icon with different theme colors"
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        BinduIcon(size = 40.dp, color = SacredGold)
                        Text("Gold", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        BinduIcon(size = 40.dp, color = SpiritualPurple)
                        Text("Purple", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        BinduIcon(size = 40.dp, color = TantricRose)
                        Text("Rose", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        BinduIcon(size = 40.dp, color = WaterTeal)
                        Text("Teal", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Glow Effect Comparison
        item {
            IconCategoryCard(
                title = "Glow Effect Comparison",
                description = "Icons with and without glow"
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.Black.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            ManifestorIcon(size = 48.dp, glowEffect = true)
                        }
                        Text("With Glow", style = MaterialTheme.typography.labelSmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.Black.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            ManifestorIcon(size = 48.dp, glowEffect = false)
                        }
                        Text("Without Glow", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Usage Examples
        item {
            IconCategoryCard(
                title = "Real-World Usage Examples",
                description = "How icons appear in actual UI components"
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Profile header example
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        YoniIcon(size = 24.dp)
                        Text("Sarah's Profile", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.weight(1f))
                        VataIcon(size = 20.dp)
                        Text("Vata", style = MaterialTheme.typography.labelSmall)
                    }

                    // Compatibility card example
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        ConnectionIcon(size = 32.dp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Soul Connection", fontWeight = FontWeight.Medium)
                            Text("92% Compatible", style = MaterialTheme.typography.bodySmall)
                        }
                        HarmonyIcon(size = 28.dp)
                    }

                    // Energy status example
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        HighEnergyIcon(size = 24.dp)
                        Text("Your Energy: High", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.weight(1f))
                        FlowingEnergyIcon(size = 20.dp)
                        Text("Flowing", style = MaterialTheme.typography.labelSmall)
                    }

                    // Meditation state example
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        TranscendenceIcon(size = 32.dp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Transcendent State", fontWeight = FontWeight.Medium)
                            Text("15 min session", style = MaterialTheme.typography.bodySmall)
                        }
                        OmIcon(size = 24.dp)
                    }
                }
            }
        }

        // Footer
        item {
            Text(
                text = "Total: 35+ new icons",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "All icons are vector-based, scalable, and support custom colors",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun IconCategoryCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            content()
        }
    }
}

@Composable
private fun IconGridRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
private fun RowScope.IconItem(
    label: String,
    icon: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun SpiritualIconsExtendedExamplePreview() {
    MaterialTheme {
        SpiritualIconsExtendedExample()
    }
}
