package com.spiritatlas.feature.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.EnhancedGlassmorphCard
import com.spiritatlas.core.ui.components.GradientText
import com.spiritatlas.core.ui.components.SpiritualGradients as TextGradients
import com.spiritatlas.core.ui.theme.SpiritualGradients
import androidx.compose.foundation.layout.Box
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.haptics.rememberHapticFeedback
import com.spiritatlas.core.ui.theme.SpiritualPurple
import com.spiritatlas.domain.ai.AiProviderMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToConsent: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val haptic = rememberHapticFeedback()

    // Collect AI provider states
    val aiProviderMode by viewModel.aiProviderMode.collectAsState()
    val providerStatuses by viewModel.providerStatuses.collectAsState()
    val testConnectionResult by viewModel.testConnectionResult.collectAsState()

    // Collect theme state
    val themeVariant by viewModel.themeVariant.collectAsState()

    // Settings screen with spiritual gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualGradients.cosmicNight)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("") }, // Empty, we'll use custom header
                    navigationIcon = {
                        IconButton(onClick = {
                            haptic.performHaptic(HapticFeedbackType.MEDIUM)
                            onNavigateBack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header with gradient text
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GradientText(
                        text = "Settings",
                        gradient = TextGradients.cosmicPurple,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Configure your AI provider preferences",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // AI Provider Selection Card
            item {
                EnhancedGlassmorphCard(
                    elevation = 2,
                    glowColor = SpiritualPurple,
                    enableGlow = true
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Section header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Psychology,
                                contentDescription = "AI provider configuration icon",
                                tint = SpiritualPurple,
                                modifier = Modifier.size(28.dp)
                            )
                            Column {
                                Text(
                                    text = "AI Provider",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Select your preferred AI provider for insights",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )

                        // Current selection
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = SpiritualPurple.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Currently Selected",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = getProviderDisplayName(aiProviderMode),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SpiritualPurple
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Selected AI provider indicator",
                                    tint = SpiritualPurple,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        // Provider list
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AiProviderMode.values().forEach { provider ->
                                ProviderListItem(
                                    provider = provider,
                                    isSelected = aiProviderMode == provider,
                                    status = providerStatuses[provider],
                                    testResult = testConnectionResult?.takeIf { it.provider == provider },
                                    onSelect = {
                                        haptic.performHaptic(HapticFeedbackType.MEDIUM)
                                        viewModel.setAiProviderMode(provider)
                                    },
                                    onApiKeyChanged = { key ->
                                        viewModel.setProviderApiKey(provider, key)
                                    },
                                    onTestConnection = {
                                        haptic.performHaptic(HapticFeedbackType.MEDIUM)
                                        viewModel.testProviderConnection(provider)
                                    },
                                    onDismissTestResult = {
                                        viewModel.clearTestConnectionResult()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Theme Selector Card
            item {
                ThemeSelector(
                    currentTheme = themeVariant,
                    onThemeSelected = { variant ->
                        haptic.performHaptic(HapticFeedbackType.MEDIUM)
                        viewModel.setThemeVariant(variant)
                    }
                )
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProviderListItem(
    provider: AiProviderMode,
    isSelected: Boolean,
    status: ProviderStatus?,
    testResult: TestConnectionResult?,
    onSelect: () -> Unit,
    onApiKeyChanged: (String) -> Unit,
    onTestConnection: () -> Unit,
    onDismissTestResult: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var apiKeyInput by remember(status?.apiKey) { mutableStateOf(status?.apiKey ?: "") }
    var showApiKey by remember { mutableStateOf(false) }

    val (displayName, description, emoji) = getProviderInfo(provider)
    val requiresUserKey = provider == AiProviderMode.OPENAI || provider == AiProviderMode.CLAUDE
    val supportsUserKey = requiresUserKey || provider == AiProviderMode.OPENROUTER
    val isAvailable = status?.isAvailable ?: false

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (!supportsUserKey) {
                onSelect()
            } else {
                expanded = !expanded
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                SpiritualPurple.copy(alpha = 0.15f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder().copy(
                width = 2.dp,
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    listOf(SpiritualPurple, SpiritualPurple.copy(alpha = 0.5f))
                )
            )
        } else null
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Main row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Emoji icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) SpiritualPurple.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surface
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = emoji,
                            fontSize = 24.sp
                        )
                    }

                    // Provider info
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = displayName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            // Status badge
                            ProviderStatusChip(
                                isAvailable = isAvailable,
                                requiresUserKey = requiresUserKey,
                                hasApiKey = status?.hasApiKey ?: false
                            )
                        }
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Selection indicator / expand button
                if (supportsUserKey) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    RadioButton(
                        selected = isSelected,
                        onClick = onSelect,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = SpiritualPurple
                        )
                    )
                }
            }

            // Expanded API key configuration
            AnimatedVisibility(
                visible = expanded && supportsUserKey,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    // API key input
                    OutlinedTextField(
                        value = apiKeyInput,
                        onValueChange = { apiKeyInput = it },
                        label = { Text("API Key") },
                        placeholder = { Text("sk-...") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showApiKey) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showApiKey = !showApiKey }) {
                                Icon(
                                    imageVector = if (showApiKey) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = if (showApiKey) "Hide" else "Show"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onApiKeyChanged(apiKeyInput)
                            }
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SpiritualPurple,
                            focusedLabelColor = SpiritualPurple,
                            cursorColor = SpiritualPurple
                        )
                    )

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                onApiKeyChanged(apiKeyInput)
                                onSelect()
                            },
                            modifier = Modifier.weight(1f),
                            enabled = apiKeyInput.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SpiritualPurple
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = "Save configuration", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Save & Select")
                        }

                        OutlinedButton(
                            onClick = onTestConnection,
                            modifier = Modifier.weight(1f),
                            enabled = isAvailable,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = SpiritualPurple
                            )
                        ) {
                            Icon(Icons.Filled.Cable, contentDescription = "Test connection", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Test")
                        }
                    }

                    // Test connection result
                    if (testResult != null) {
                        TestConnectionResultCard(
                            result = testResult,
                            onDismiss = onDismissTestResult
                        )
                    }

                    // Warning for required keys
                    if (requiresUserKey && !isAvailable) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = "Error information",
                                    tint = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Text(
                                    text = "API key required to use this provider",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun ProviderStatusChip(
    isAvailable: Boolean,
    requiresUserKey: Boolean,
    hasApiKey: Boolean
) {
    val (text, containerColor, contentColor) = when {
        isAvailable -> Triple(
            "Available",
            Color(0xFF22C55E).copy(alpha = 0.2f),
            Color(0xFF22C55E)
        )
        requiresUserKey && !hasApiKey -> Triple(
            "Key Required",
            MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.error
        )
        !requiresUserKey -> Triple(
            "App Key",
            SpiritualPurple.copy(alpha = 0.2f),
            SpiritualPurple
        )
        else -> Triple(
            "Unavailable",
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Surface(
        shape = RoundedCornerShape(6.dp),
        color = containerColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TestConnectionResultCard(
    result: TestConnectionResult,
    onDismiss: () -> Unit
) {
    val (icon, containerColor, contentColor) = when (result.status) {
        TestStatus.TESTING -> Triple(
            Icons.Filled.Sync,
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer
        )
        TestStatus.SUCCESS -> Triple(
            Icons.Filled.CheckCircle,
            Color(0xFF22C55E).copy(alpha = 0.2f),
            Color(0xFF22C55E)
        )
        TestStatus.FAILED -> Triple(
            Icons.Filled.Error,
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer
        )
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    icon,
                    contentDescription = when (result.status) {
                        TestStatus.TESTING -> "Testing connection"
                        TestStatus.SUCCESS -> "Connection successful"
                        TestStatus.FAILED -> "Connection failed"
                    },
                    tint = contentColor,
                    modifier = Modifier.size(20.dp)
                )
                Column {
                    Text(
                        text = when (result.status) {
                            TestStatus.TESTING -> "Testing connection..."
                            TestStatus.SUCCESS -> "Connection successful"
                            TestStatus.FAILED -> "Connection failed"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = contentColor
                    )
                    if (result.message != null && result.status != TestStatus.TESTING) {
                        Text(
                            text = result.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            if (result.status != TestStatus.TESTING) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Dismiss",
                        tint = contentColor
                    )
                }
            }
        }
    }
}

// Helper functions
private fun getProviderDisplayName(provider: AiProviderMode): String = when (provider) {
    AiProviderMode.LOCAL -> "Local (Ollama)"
    AiProviderMode.AUTO -> "Automatic"
    AiProviderMode.GEMINI -> "Gemini"
    AiProviderMode.GROQ -> "Groq"
    AiProviderMode.OPENAI -> "OpenAI"
    AiProviderMode.CLAUDE -> "Claude"
    AiProviderMode.OPENROUTER -> "OpenRouter"
}

private fun getProviderInfo(provider: AiProviderMode): Triple<String, String, String> = when (provider) {
    AiProviderMode.AUTO -> Triple(
        "Automatic",
        "Intelligently switches between providers",
        "⚡"
    )
    AiProviderMode.GEMINI -> Triple(
        "Gemini",
        "Google Gemini 2.5 Flash - fast and free",
        "💎"
    )
    AiProviderMode.GROQ -> Triple(
        "Groq",
        "Groq Llama 3.3 70B - fast and free",
        "🚀"
    )
    AiProviderMode.OPENAI -> Triple(
        "OpenAI",
        "GPT-4o - requires API key",
        "🤖"
    )
    AiProviderMode.CLAUDE -> Triple(
        "Claude",
        "Anthropic Claude - requires API key",
        "🧠"
    )
    AiProviderMode.OPENROUTER -> Triple(
        "OpenRouter",
        "Multiple models available",
        "☁️"
    )
    AiProviderMode.LOCAL -> Triple(
        "Local (Ollama)",
        "Runs on your device - completely private",
        "🏠"
    )
}
