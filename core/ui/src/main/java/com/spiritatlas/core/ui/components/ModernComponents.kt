package com.spiritatlas.core.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import com.spiritatlas.core.ui.theme.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.border
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription

/**
 * Modern UI Components for SpiritAtlas
 * Enhanced with rounded corners, shadows, gradients, and spiritual styling
 */

@Composable
fun ModernButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    variant: ButtonVariant = ButtonVariant.Primary,
    size: ButtonSize = ButtonSize.Medium
) {
    val shape = when (size) {
        ButtonSize.Small -> RoundedCornerShape(12.dp)
        ButtonSize.Medium -> RoundedCornerShape(16.dp)
        ButtonSize.Large -> RoundedCornerShape(20.dp)
    }
    
    val buttonColors = when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        ButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
        ButtonVariant.Tertiary -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
        ButtonVariant.Outline -> ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
        ButtonVariant.Text -> ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
        ButtonVariant.Spiritual -> ButtonDefaults.buttonColors(
            containerColor = SpiritualPurple,
            contentColor = Color.White
        )
    }
    
    val contentPadding = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    }
    
    val buttonModifier = modifier
        .shadow(
            elevation = if (variant == ButtonVariant.Outline || variant == ButtonVariant.Text) 0.dp else 4.dp,
            shape = shape
        )
    
    when (variant) {
        ButtonVariant.Outline -> OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier,
            colors = buttonColors,
            shape = shape,
            contentPadding = contentPadding,
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.linearGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            )
        ) {
            ButtonContent(text, icon, size)
        }
        
        ButtonVariant.Text -> TextButton(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier,
            colors = buttonColors,
            shape = shape,
            contentPadding = contentPadding
        ) {
            ButtonContent(text, icon, size)
        }
        
        else -> Button(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier,
            colors = buttonColors,
            shape = shape,
            contentPadding = contentPadding
        ) {
            ButtonContent(text, icon, size)
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    icon: ImageVector?,
    size: ButtonSize
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.animateContentSize()
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(
                    when (size) {
                        ButtonSize.Small -> 16.dp
                        ButtonSize.Medium -> 20.dp  
                        ButtonSize.Large -> 24.dp
                    }
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            fontSize = when (size) {
                ButtonSize.Small -> 12.sp
                ButtonSize.Medium -> 14.sp
                ButtonSize.Large -> 16.sp
            },
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ModernCard(
    modifier: Modifier = Modifier,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    colors: CardColors = CardDefaults.cardColors(),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = elevation,
            content = content
        )
    }
}

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(SpiritualPurple, MysticViolet),
    contentColor: Color = Color.White,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset.Infinite
                )
            )
            .padding(16.dp),
        content = content
    )
}

@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconContentDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        placeholder = placeholder?.let { { Text(it) } },
        supportingText = supportingText?.let { { Text(it) } },
        isError = isError,
        enabled = enabled,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                    Icon(
                        imageVector = it,
                        contentDescription = trailingIconContentDescription,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun ChakraIndicator(
    chakraIndex: Int,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val chakraColor = SpiritualColors.ChakraColors.getOrNull(chakraIndex) ?: SpiritualPurple
    
    Box(
        modifier = modifier
            .size(if (isActive) 24.dp else 16.dp)
            .clip(CircleShape)
            .background(
                if (isActive) chakraColor else chakraColor.copy(alpha = 0.3f)
            )
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isActive) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Active chakra",
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
fun TantricTypeChip(
    tantricType: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        SpiritualColors.TantricColors[tantricType] ?: SpiritualPurple
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val contentColor = if (isSelected) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = tantricType,
                color = contentColor,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            )
        },
        modifier = modifier
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = backgroundColor,
            containerColor = backgroundColor
        )
    )
}

@Composable
fun ProgressIndicatorCard(
    title: String,
    progress: Float,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    ModernCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = color,
                trackColor = color.copy(alpha = 0.2f)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = color,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun LoadingShimmer(
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 200.dp
) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    )
    
    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = androidx.compose.ui.geometry.Offset(-1000f, 0f),
                    end = androidx.compose.ui.geometry.Offset(1000f, 0f)
                )
            )
    )
}

enum class ButtonVariant {
    Primary, Secondary, Tertiary, Outline, Text, Spiritual
}

enum class ButtonSize {
    Small, Medium, Large
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ModernDropdown(
    selectedValue: T?,
    onValueChange: (T) -> Unit,
    options: List<T>,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Select an option",
    displayTransform: @Composable (T) -> String = { it.toString() },
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded && enabled },
        modifier = modifier
    ) {
        ModernTextField(
            value = selectedValue?.let { displayTransform(it) } ?: "",
            onValueChange = { }, // Read-only field
            label = label,
            placeholder = placeholder,
            enabled = enabled,
            trailingIcon = Icons.Default.ArrowDropDown,
            onTrailingIconClick = { if (enabled) expanded = !expanded },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = displayTransform(option),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

// === NEW GLASSMORPHISM COMPONENTS ===

/**
 * Modern glassmorphism card with ethereal design
 * Perfect for profile cards and content sections
 */
@Composable
fun GlassmorphCard(
    modifier: Modifier = Modifier,
    elevation: Int = 1, // 1=subtle, 2=medium, 3=high
    content: @Composable ColumnScope.() -> Unit
) {
    val backgroundAlpha = when(elevation) {
        1 -> 0.1f
        2 -> 0.15f
        3 -> 0.2f
        else -> 0.1f
    }

    val borderAlpha = when(elevation) {
        1 -> 0.2f
        2 -> 0.3f
        3 -> 0.4f
        else -> 0.2f
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = borderAlpha),
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}

/**
 * Enhanced glassmorphism card with multi-layer depth, glow effects, and sophisticated shadows
 *
 * Features:
 * - Multi-layer shadow system for depth perception
 * - Animated glow effect that pulses
 * - Gradient backgrounds for visual richness
 * - Customizable glow color for spiritual theming
 * - Outer glow ring that creates ethereal presence
 * - Gradient borders with white fade effect
 *
 * @param modifier Modifier to be applied to the card
 * @param elevation Shadow depth level (1=subtle, 2=medium, 3=high, 4=ultra)
 * @param glowColor Color of the animated glow effect (default: SpiritualPurple)
 * @param enableGlow Whether to show the animated glow effect (default: true)
 * @param enableGradientBorder Whether to show the gradient border (default: true)
 * @param content Content to be displayed inside the card
 */
@Composable
fun EnhancedGlassmorphCard(
    modifier: Modifier = Modifier,
    elevation: Int = 2, // 1=subtle, 2=medium, 3=high, 4=ultra
    glowColor: Color = SpiritualPurple,
    enableGlow: Boolean = true,
    enableGradientBorder: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    // Animated glow effect
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val glowSize by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowSize"
    )

    // Multi-layer shadow and elevation
    val shadowElevation = when(elevation) {
        1 -> 4.dp
        2 -> 8.dp
        3 -> 16.dp
        4 -> 24.dp
        else -> 8.dp
    }

    val blurRadius = when(elevation) {
        1 -> 8.dp
        2 -> 16.dp
        3 -> 24.dp
        4 -> 32.dp
        else -> 16.dp
    }

    val backgroundAlpha = when(elevation) {
        1 -> 0.08f
        2 -> 0.12f
        3 -> 0.18f
        4 -> 0.25f
        else -> 0.12f
    }

    val borderAlpha = when(elevation) {
        1 -> 0.25f
        2 -> 0.35f
        3 -> 0.45f
        4 -> 0.55f
        else -> 0.35f
    }

    // Gradient for depth
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha * 1.2f),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha * 0.8f)
        )
    )

    val borderGradient = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = borderAlpha),
            Color.White.copy(alpha = borderAlpha * 0.3f),
            Color.White.copy(alpha = borderAlpha * 0.1f)
        )
    )

    Box(
        modifier = modifier
    ) {
        // Outer glow ring (animated)
        if (enableGlow) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(-4.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = glowColor.copy(alpha = glowAlpha * 0.4f),
                            size = size * glowSize,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                    .blur(blurRadius / 2)
            )
        }

        // Main card with multi-layer effects
        Card(
            modifier = Modifier
                .shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = if (enableGlow) glowColor.copy(alpha = 0.3f) else Color.Black,
                    spotColor = if (enableGlow) glowColor.copy(alpha = 0.5f) else Color.Black
                )
                .clip(RoundedCornerShape(24.dp))
                .background(backgroundGradient)
                .then(
                    if (enableGradientBorder) {
                        Modifier.border(
                            width = 1.5.dp,
                            brush = borderGradient,
                            shape = RoundedCornerShape(24.dp)
                        )
                    } else {
                        Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = borderAlpha),
                            shape = RoundedCornerShape(24.dp)
                        )
                    }
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp // We handle elevation manually above
            )
        ) {
            // Inner gradient overlay for extra depth
            Box(
                modifier = Modifier
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                if (enableGlow) glowColor.copy(alpha = 0.05f) else Color.Transparent,
                                Color.Transparent
                            ),
                            radius = 800f
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    content = content
                )
            }
        }
    }
}

/**
 * Gradient background with spiritual energy
 */
@Composable
fun SpiritualGradientBackground(
    gradientType: SpiritualGradientType = SpiritualGradientType.MYSTIC,
    content: @Composable BoxScope.() -> Unit
) {
    val gradient = when (gradientType) {
        SpiritualGradientType.MYSTIC -> Brush.verticalGradient(
            colors = listOf(
                SpiritualPurple.copy(alpha = 0.1f),
                MysticViolet.copy(alpha = 0.05f)
            )
        )
        SpiritualGradientType.SUNSET -> Brush.verticalGradient(
            colors = listOf(
                TantricRose.copy(alpha = 0.1f),
                AuraGold.copy(alpha = 0.05f)
            )
        )
        SpiritualGradientType.OCEAN -> Brush.verticalGradient(
            colors = listOf(
                CosmicBlue.copy(alpha = 0.1f),
                SpiritualPurple.copy(alpha = 0.05f)
            )
        )
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        content()
    }
}

enum class SpiritualGradientType {
    MYSTIC, SUNSET, OCEAN
}

/**
 * Modern rounded button with spiritual energy
 */
@Composable
fun SpiritualButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    style: SpiritualButtonStyle = SpiritualButtonStyle.PRIMARY,
    enabled: Boolean = true
) {
    val colors = when (style) {
        SpiritualButtonStyle.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = SpiritualPurple,
            contentColor = Color.White
        )
        SpiritualButtonStyle.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = AuraGold,
            contentColor = Color.White
        )
        SpiritualButtonStyle.GHOST -> ButtonDefaults.outlinedButtonColors(
            contentColor = SpiritualPurple
        )
    }
    
    when (style) {
        SpiritualButtonStyle.GHOST -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier.height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = colors,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            SpiritualPurple,
                            MysticViolet
                        )
                    )
                ),
                enabled = enabled
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
        else -> {
            Button(
                onClick = onClick,
                modifier = modifier.height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = colors,
                enabled = enabled
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

enum class SpiritualButtonStyle {
    PRIMARY, SECONDARY, GHOST
}

/**
 * Profile completion indicator with chakra colors
 * Accessibility: Includes semantic description for screen readers
 */
@Composable
fun ProfileCompletionIndicator(
    completionPercentage: Double,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true
) {
    val color = when {
        completionPercentage >= 80 -> CosmicBlue
        completionPercentage >= 60 -> AuraGold
        completionPercentage >= 40 -> TantricRose
        else -> MaterialTheme.colorScheme.error
    }

    Column(
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "Profile completion: ${completionPercentage.toInt()} percent"
            stateDescription = when {
                completionPercentage >= 80 -> "Excellent, almost complete"
                completionPercentage >= 60 -> "Good progress"
                completionPercentage >= 40 -> "Moderate progress"
                else -> "Just getting started"
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = { (completionPercentage / 100).toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        if (showLabel) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${completionPercentage.toInt()}% Complete",
                style = MaterialTheme.typography.bodySmall,
                color = SpiritualPurple,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Compatibility score display with color coding
 * Accessibility: Clear semantic description with match quality
 */
@Composable
fun CompatibilityScoreDisplay(
    score: Double,
    modifier: Modifier = Modifier,
    size: CompatibilityDisplaySize = CompatibilityDisplaySize.MEDIUM
) {
    val color = when {
        score >= 80 -> CosmicBlue
        score >= 60 -> AuraGold
        score >= 40 -> TantricRose
        else -> MaterialTheme.colorScheme.error
    }

    val matchQuality = when {
        score >= 80 -> "excellent match"
        score >= 60 -> "good match"
        score >= 40 -> "moderate match"
        else -> "challenging match"
    }

    val textSize = when (size) {
        CompatibilityDisplaySize.SMALL -> 16.sp
        CompatibilityDisplaySize.MEDIUM -> 24.sp
        CompatibilityDisplaySize.LARGE -> 32.sp
    }

    val padding = when (size) {
        CompatibilityDisplaySize.SMALL -> 8.dp
        CompatibilityDisplaySize.MEDIUM -> 12.dp
        CompatibilityDisplaySize.LARGE -> 16.dp
    }

    Box(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                contentDescription = "Compatibility score: ${score.toInt()} percent, $matchQuality"
                role = androidx.compose.ui.semantics.Role.Image
            }
            .background(
                color.copy(alpha = 0.1f),
                RoundedCornerShape(50)
            )
            .border(
                1.dp,
                color.copy(alpha = 0.3f),
                RoundedCornerShape(50)
            )
            .padding(horizontal = padding * 2, vertical = padding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${score.toInt()}%",
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

enum class CompatibilityDisplaySize {
    SMALL, MEDIUM, LARGE
}

/**
 * Section header with mystical styling
 */
@Composable
fun SpiritualSectionHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )
        
        subtitle?.let { sub ->
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = sub,
                style = MaterialTheme.typography.bodyMedium,
                color = SpiritualPurple
            )
        }
    }
}
