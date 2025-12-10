package com.spiritatlas.feature.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.components.EnhancedGlassmorphCard
import com.spiritatlas.core.ui.theme.ThemeVariant
import com.spiritatlas.core.ui.theme.ThemeVariantConfig
import com.spiritatlas.core.ui.theme.getAllThemeVariants

/**
 * Theme selector component for choosing between different spiritual themes
 *
 * @param currentTheme The currently selected theme variant
 * @param onThemeSelected Callback when a theme is selected
 * @param modifier Optional modifier for the container
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeSelector(
    currentTheme: ThemeVariant,
    onThemeSelected: (ThemeVariant) -> Unit,
    modifier: Modifier = Modifier
) {
    val themes = getAllThemeVariants()

    EnhancedGlassmorphCard(
        elevation = 2,
        glowColor = MaterialTheme.colorScheme.primary,
        enableGlow = true,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Palette,
                    contentDescription = "Theme palette icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Column {
                    Text(
                        text = "Theme Style",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Choose your spiritual color palette",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Theme grid
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 2
            ) {
                themes.forEach { themeConfig ->
                    ThemeCard(
                        config = themeConfig,
                        isSelected = themeConfig.variant == currentTheme,
                        onClick = { onThemeSelected(themeConfig.variant) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

/**
 * Individual theme card showing theme preview and info
 */
@Composable
private fun ThemeCard(
    config: ThemeVariantConfig,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "border_color"
    )

    val containerColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
        } else {
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "container_color"
    )

    Card(
        modifier = modifier
            .height(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with emoji and name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = config.emoji,
                        fontSize = 24.sp
                    )
                    Text(
                        text = config.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected theme",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Color preview swatches
            ThemeColorPreview(config = config)

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = config.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                fontSize = 11.sp
            )
        }
    }
}

/**
 * Color preview showing the theme's primary colors
 */
@Composable
private fun ThemeColorPreview(
    config: ThemeVariantConfig
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Primary color
        ColorSwatch(
            color = config.darkColorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        // Secondary color
        ColorSwatch(
            color = config.darkColorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        // Tertiary color
        ColorSwatch(
            color = config.darkColorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
        // Gradient preview
        GradientSwatch(
            gradient = config.gradients.primary,
            modifier = Modifier.weight(1.5f)
        )
    }
}

/**
 * Single color swatch
 */
@Composable
private fun ColorSwatch(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
            )
    )
}

/**
 * Gradient swatch
 */
@Composable
private fun GradientSwatch(
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(gradient)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
            )
    )
}

/**
 * Compact theme selector showing current theme with expand option
 */
@Composable
fun CompactThemeSelector(
    currentTheme: ThemeVariant,
    modifier: Modifier = Modifier
) {
    val config = getAllThemeVariants().find { it.variant == currentTheme }
        ?: getAllThemeVariants().first()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            brush = config.gradients.primary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = config.emoji,
                        fontSize = 24.sp
                    )
                }
                Column {
                    Text(
                        text = "Current Theme",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = config.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

/**
 * Detailed theme info card for expanded view
 */
@Composable
fun ThemeDetailCard(
    config: ThemeVariantConfig,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(brush = config.gradients.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = config.emoji,
                        fontSize = 28.sp
                    )
                }
                Column {
                    Text(
                        text = config.displayName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = config.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Color psychology
            InfoSection(
                title = "Color Psychology",
                content = config.colorPsychology
            )

            // Recommended for
            InfoSection(
                title = "Recommended For",
                content = config.recommendedFor
            )

            // Color preview
            Text(
                text = "Color Palette",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            ThemeColorPreview(config = config)
        }
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
        )
    }
}
