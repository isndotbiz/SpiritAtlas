package com.spiritatlas.feature.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.haptics.rememberHapticFeedback
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.model.HouseSystem
import com.spiritatlas.domain.model.NumerologySystem
import com.spiritatlas.domain.repository.ConsentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  onNavigateBack: () -> Unit,
  onNavigateToConsent: () -> Unit,
  viewModel: SettingsViewModel = hiltViewModel()
) {
  val context = LocalContext.current
  val haptic = rememberHapticFeedback()

  // Collect all states
  val numerologySystem by viewModel.numerologySystem.collectAsState()
  val houseSystem by viewModel.houseSystem.collectAsState()
  val useSidereal by viewModel.useSidereal.collectAsState()
  val ayanamsa by viewModel.ayanamsa.collectAsState()

  val aiProviderMode by viewModel.aiProviderMode.collectAsState()
  val consentMap by viewModel.consentMap.collectAsState()

  val themeMode by viewModel.themeMode.collectAsState()
  val useDynamicColors by viewModel.useDynamicColors.collectAsState()
  val accentColor by viewModel.accentColor.collectAsState()
  val reduceAnimations by viewModel.reduceAnimations.collectAsState()

  val dailyInsightsEnabled by viewModel.dailyInsightsEnabled.collectAsState()
  val transitAlertsEnabled by viewModel.transitAlertsEnabled.collectAsState()
  val notificationTime by viewModel.notificationTime.collectAsState()

  val hapticEnabled by viewModel.hapticEnabled.collectAsState()
  val soundEnabled by viewModel.soundEnabled.collectAsState()

  val isLoading by viewModel.isLoading.collectAsState()
  val showClearDataDialog by viewModel.showClearDataDialog.collectAsState()

  // Expanded section tracking
  var expandedSection by remember { mutableStateOf<String?>("Profile") }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
          )
        },
        navigationIcon = {
          IconButton(onClick = {
            haptic.performHaptic(HapticFeedbackType.MEDIUM)
            onNavigateBack()
          }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Profile Settings Section
        item {
          SettingsSection(
            title = "Profile Settings",
            icon = Icons.Filled.Person,
            isExpanded = expandedSection == "Profile",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "Profile") null else "Profile"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              SettingsDropdown(
                label = "Numerology System",
                options = NumerologySystem.values().map { it.name },
                selectedOption = numerologySystem.name,
                onOptionSelected = {
                  haptic.performHaptic(HapticFeedbackType.SELECTION)
                  viewModel.setNumerologySystem(NumerologySystem.valueOf(it))
                },
                description = "Choose calculation method for numerology"
              )

              SettingsToggle(
                label = "Use Sidereal Zodiac",
                description = "Sidereal (Vedic) vs Tropical (Western) astrology",
                checked = useSidereal,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setUseSidereal(it)
                },
                icon = Icons.Filled.Stars
              )

              SettingsDropdown(
                label = "House System",
                options = HouseSystem.values().map { it.name },
                selectedOption = houseSystem.name,
                onOptionSelected = {
                  haptic.performHaptic(HapticFeedbackType.SELECTION)
                  viewModel.setHouseSystem(HouseSystem.valueOf(it))
                },
                description = "Astrological house calculation method"
              )

              SettingsDropdown(
                label = "Ayanamsa",
                options = listOf("Lahiri", "Raman", "Krishnamurti", "Fagan-Bradley"),
                selectedOption = ayanamsa,
                onOptionSelected = {
                  haptic.performHaptic(HapticFeedbackType.SELECTION)
                  viewModel.setAyanamsa(it)
                },
                description = "Precession calculation method for sidereal zodiac"
              )
            }
          }
        }

        // AI & Privacy Section
        item {
          SettingsSection(
            title = "AI & Privacy",
            icon = Icons.Filled.Security,
            isExpanded = expandedSection == "AIPrivacy",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "AIPrivacy") null else "AIPrivacy"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              AiProviderSelector(
                selectedMode = aiProviderMode,
                onModeSelected = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setAiProviderMode(it)
                }
              )

              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

              Text(
                text = "Data Permissions",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
              )

              SettingsToggle(
                label = "AI Enrichment",
                description = "Allow AI to analyze profiles and provide insights",
                checked = consentMap[ConsentType.AI_ENRICHMENT] == ConsentStatus.GRANTED,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.toggleConsent(ConsentType.AI_ENRICHMENT, it)
                },
                icon = Icons.Filled.Psychology
              )

              SettingsToggle(
                label = "Cloud Sync",
                description = "Sync profiles using encrypted cloud storage",
                checked = consentMap[ConsentType.CLOUD_SYNC] == ConsentStatus.GRANTED,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.toggleConsent(ConsentType.CLOUD_SYNC, it)
                },
                icon = Icons.Filled.Cloud
              )

              SettingsToggle(
                label = "Analytics",
                description = "Help improve the app with anonymous usage data",
                checked = consentMap[ConsentType.ANALYTICS] == ConsentStatus.GRANTED,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.toggleConsent(ConsentType.ANALYTICS, it)
                },
                icon = Icons.Filled.Analytics
              )

              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

              SettingsButton(
                label = "Clear AI Cache",
                description = "Remove cached AI responses",
                icon = Icons.Filled.DeleteSweep,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.HEAVY)
                  viewModel.clearAiCache()
                }
              )

              SettingsButton(
                label = "Manage Consents",
                description = "View detailed privacy settings",
                icon = Icons.Filled.Policy,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  onNavigateToConsent()
                }
              )
            }
          }
        }

        // Appearance Section
        item {
          SettingsSection(
            title = "Appearance",
            icon = Icons.Filled.Palette,
            isExpanded = expandedSection == "Appearance",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "Appearance") null else "Appearance"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              ThemeSelector(
                selectedMode = themeMode,
                onModeSelected = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setThemeMode(it)
                }
              )

              SettingsToggle(
                label = "Dynamic Colors",
                description = "Use system wallpaper colors (Android 12+)",
                checked = useDynamicColors,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setUseDynamicColors(it)
                },
                icon = Icons.Filled.ColorLens
              )

              AccentColorPicker(
                selectedColor = accentColor,
                onColorSelected = {
                  haptic.performHaptic(HapticFeedbackType.SELECTION)
                  viewModel.setAccentColor(it)
                }
              )

              SettingsToggle(
                label = "Reduce Animations",
                description = "Minimize motion for better performance",
                checked = reduceAnimations,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setReduceAnimations(it)
                },
                icon = Icons.Filled.Animation
              )
            }
          }
        }

        // Notifications Section
        item {
          SettingsSection(
            title = "Notifications",
            icon = Icons.Filled.Notifications,
            isExpanded = expandedSection == "Notifications",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "Notifications") null else "Notifications"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              SettingsToggle(
                label = "Daily Insights",
                description = "Receive daily spiritual guidance",
                checked = dailyInsightsEnabled,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setDailyInsightsEnabled(it)
                },
                icon = Icons.Filled.LightMode
              )

              SettingsToggle(
                label = "Transit Alerts",
                description = "Notifications for important astrological transits",
                checked = transitAlertsEnabled,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setTransitAlertsEnabled(it)
                },
                icon = Icons.Filled.TrendingUp
              )

              SettingsButton(
                label = "Notification Time",
                description = "${String.format("%02d:%02d", notificationTime.first, notificationTime.second)}",
                icon = Icons.Filled.Schedule,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  // TODO: Show time picker dialog
                }
              )
            }
          }
        }

        // Haptics & Sound Section
        item {
          SettingsSection(
            title = "Haptics & Sound",
            icon = Icons.Filled.Vibration,
            isExpanded = expandedSection == "Haptics",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "Haptics") null else "Haptics"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              SettingsToggle(
                label = "Haptic Feedback",
                description = "Vibration on interactions",
                checked = hapticEnabled,
                onCheckedChange = {
                  if (it) haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setHapticEnabled(it)
                },
                icon = Icons.Filled.Vibration
              )

              SettingsToggle(
                label = "Sound Effects",
                description = "Audio feedback for interactions",
                checked = soundEnabled,
                onCheckedChange = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  viewModel.setSoundEnabled(it)
                },
                icon = Icons.Filled.VolumeUp
              )
            }
          }
        }

        // Data Section
        item {
          SettingsSection(
            title = "Data",
            icon = Icons.Filled.Storage,
            isExpanded = expandedSection == "Data",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "Data") null else "Data"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              SettingsButton(
                label = "Export All Profiles",
                description = "Save profiles to file",
                icon = Icons.Filled.Upload,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.HEAVY)
                  viewModel.exportAllProfiles()
                }
              )

              SettingsButton(
                label = "Import Profiles",
                description = "Load profiles from file",
                icon = Icons.Filled.Download,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.HEAVY)
                  viewModel.importProfiles()
                }
              )

              SettingsButton(
                label = "Clear All Data",
                description = "Delete all profiles and settings",
                icon = Icons.Filled.Delete,
                iconTint = MaterialTheme.colorScheme.error,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.WARNING)
                  viewModel.showClearDataDialog()
                }
              )
            }
          }
        }

        // About Section
        item {
          SettingsSection(
            title = "About",
            icon = Icons.Filled.Info,
            isExpanded = expandedSection == "About",
            onToggleExpand = {
              haptic.performHaptic(HapticFeedbackType.LIGHT)
              expandedSection = if (expandedSection == "About") null else "About"
            }
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              SettingsButton(
                label = "App Version",
                description = "1.0.0 (Beta)",
                icon = Icons.Filled.AppSettingsAlt,
                onClick = { }
              )

              SettingsButton(
                label = "Privacy Policy",
                description = "View our privacy policy",
                icon = Icons.Filled.PrivacyTip,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://spiritatlas.app/privacy")))
                }
              )

              SettingsButton(
                label = "Terms of Service",
                description = "View terms and conditions",
                icon = Icons.Filled.Gavel,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://spiritatlas.app/terms")))
                }
              )

              SettingsButton(
                label = "Open Source Licenses",
                description = "Third-party software licenses",
                icon = Icons.Filled.Code,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  // TODO: Show licenses screen
                }
              )

              SettingsButton(
                label = "Rate App",
                description = "Share your feedback on Google Play",
                icon = Icons.Filled.Star,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}")))
                }
              )

              SettingsButton(
                label = "Share App",
                description = "Tell friends about SpiritAtlas",
                icon = Icons.Filled.Share,
                onClick = {
                  haptic.performHaptic(HapticFeedbackType.MEDIUM)
                  val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out SpiritAtlas - Your comprehensive spiritual companion! https://spiritatlas.app")
                    type = "text/plain"
                  }
                  context.startActivity(Intent.createChooser(sendIntent, "Share SpiritAtlas"))
                }
              )
            }
          }
        }

        // Bottom spacing
        item {
          Spacer(modifier = Modifier.height(32.dp))
        }
      }

      // Loading overlay
      if (isLoading) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }
    }
  }

  // Clear Data Confirmation Dialog
  if (showClearDataDialog) {
    AlertDialog(
      onDismissRequest = { viewModel.dismissClearDataDialog() },
      title = { Text("Clear All Data?") },
      text = { Text("This will permanently delete all profiles, settings, and cached data. This action cannot be undone.") },
      confirmButton = {
        TextButton(
          onClick = {
            haptic.performHaptic(HapticFeedbackType.HEAVY)
            viewModel.clearAllData()
          },
          colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.error
          )
        ) {
          Text("Clear All")
        }
      },
      dismissButton = {
        TextButton(onClick = { viewModel.dismissClearDataDialog() }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
private fun SettingsSection(
  title: String,
  icon: ImageVector,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit,
  content: @Composable () -> Unit
) {
  val rotationAngle by animateFloatAsState(
    targetValue = if (isExpanded) 180f else 0f,
    animationSpec = tween(300), label = "rotation"
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .animateContentSize(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
  ) {
    Column {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onToggleExpand)
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
          )
          Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
          )
        }
        Icon(
          imageVector = Icons.Filled.KeyboardArrowDown,
          contentDescription = if (isExpanded) "Collapse" else "Expand",
          modifier = Modifier.rotate(rotationAngle)
        )
      }

      AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
          HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))
          content()
        }
      }
    }
  }
}

@Composable
private fun SettingsToggle(
  label: String,
  description: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  icon: ImageVector,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    shape = RoundedCornerShape(12.dp)
  ) {
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
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(20.dp)
        )
        Column {
          Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
      Switch(
        checked = checked,
        onCheckedChange = onCheckedChange
      )
    }
  }
}

@Composable
private fun SettingsButton(
  label: String,
  description: String,
  icon: ImageVector,
  onClick: () -> Unit,
  iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    onClick = onClick,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = iconTint,
        modifier = Modifier.size(20.dp)
      )
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = label,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = description,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
      Icon(
        imageVector = Icons.Filled.ChevronRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDropdown(
  label: String,
  options: List<String>,
  selectedOption: String,
  onOptionSelected: (String) -> Unit,
  description: String,
  modifier: Modifier = Modifier
) {
  var expanded by remember { mutableStateOf(false) }

  Card(
    modifier = modifier.fillMaxWidth(),
    onClick = { expanded = true },
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium
      )
      Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 2.dp)
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = selectedOption.replace("_", " "),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.SemiBold
        )
        Icon(
          imageVector = Icons.Filled.ArrowDropDown,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        options.forEach { option ->
          DropdownMenuItem(
            text = { Text(option.replace("_", " ")) },
            onClick = {
              onOptionSelected(option)
              expanded = false
            },
            leadingIcon = if (option == selectedOption) {
              { Icon(Icons.Filled.Check, contentDescription = null) }
            } else null
          )
        }
      }
    }
  }
}

@Composable
private fun AiProviderSelector(
  selectedMode: AiProviderMode,
  onModeSelected: (AiProviderMode) -> Unit
) {
  Column(
    modifier = Modifier.padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = "AI Provider",
      style = MaterialTheme.typography.titleSmall,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.padding(bottom = 4.dp)
    )

    AiProviderMode.values().forEach { mode ->
      val (label, description, icon) = when (mode) {
        AiProviderMode.LOCAL -> Triple("Local (Ollama)", "Runs on your device - completely private", "🏠")
        AiProviderMode.CLOUD -> Triple("Cloud (OpenRouter)", "Advanced models - faster and more accurate", "☁️")
        AiProviderMode.AUTO -> Triple("Automatic", "Switches between local and cloud intelligently", "⚡")
      }

      Card(
        onClick = { onModeSelected(mode) },
        colors = CardDefaults.cardColors(
          containerColor = if (selectedMode == mode) {
            MaterialTheme.colorScheme.primaryContainer
          } else {
            MaterialTheme.colorScheme.surface
          }
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Text(text = icon, style = MaterialTheme.typography.headlineSmall)
            Column {
              Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
          RadioButton(
            selected = selectedMode == mode,
            onClick = { onModeSelected(mode) }
          )
        }
      }
    }
  }
}

@Composable
private fun ThemeSelector(
  selectedMode: ThemeMode,
  onModeSelected: (ThemeMode) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    ThemeMode.values().forEach { mode ->
      val (label, icon) = when (mode) {
        ThemeMode.SYSTEM -> "System" to Icons.Filled.PhoneAndroid
        ThemeMode.LIGHT -> "Light" to Icons.Filled.LightMode
        ThemeMode.DARK -> "Dark" to Icons.Filled.DarkMode
      }

      Card(
        onClick = { onModeSelected(mode) },
        modifier = Modifier.weight(1f),
        colors = CardDefaults.cardColors(
          containerColor = if (selectedMode == mode) {
            MaterialTheme.colorScheme.primaryContainer
          } else {
            MaterialTheme.colorScheme.surface
          }
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selectedMode == mode) {
              MaterialTheme.colorScheme.primary
            } else {
              MaterialTheme.colorScheme.onSurfaceVariant
            }
          )
          Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (selectedMode == mode) FontWeight.Bold else FontWeight.Normal
          )
        }
      }
    }
  }
}

@Composable
private fun AccentColorPicker(
  selectedColor: AccentColor,
  onColorSelected: (AccentColor) -> Unit
) {
  Column(
    modifier = Modifier.padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = "Accent Color",
      style = MaterialTheme.typography.titleSmall,
      fontWeight = FontWeight.SemiBold
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      AccentColor.values().forEach { color ->
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(color.colorHex))
            .clickable { onColorSelected(color) }
            .then(
              if (selectedColor == color) {
                Modifier.padding(4.dp)
              } else Modifier
            ),
          contentAlignment = Alignment.Center
        ) {
          if (selectedColor == color) {
            Icon(
              imageVector = Icons.Filled.Check,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
    Text(
      text = selectedColor.displayName,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
