package com.spiritatlas.feature.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.core.ui.haptics.HapticPreferences
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.model.HouseSystem
import com.spiritatlas.domain.model.NumerologySystem
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import com.spiritatlas.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  @ApplicationContext private val context: Context,
  private val consentRepository: ConsentRepository,
  private val aiSettingsRepository: AiSettingsRepository,
  private val userRepository: UserRepository
) : ViewModel() {

  private val hapticPreferences = HapticPreferences(context)
  private val settingsPreferences = SettingsPreferences(context)

  // Profile Settings
  private val _numerologySystem = MutableStateFlow(settingsPreferences.getNumerologySystem())
  val numerologySystem: StateFlow<NumerologySystem> = _numerologySystem.asStateFlow()

  private val _houseSystem = MutableStateFlow(settingsPreferences.getHouseSystem())
  val houseSystem: StateFlow<HouseSystem> = _houseSystem.asStateFlow()

  private val _useSidereal = MutableStateFlow(settingsPreferences.getUseSidereal())
  val useSidereal: StateFlow<Boolean> = _useSidereal.asStateFlow()

  private val _ayanamsa = MutableStateFlow(settingsPreferences.getAyanamsa())
  val ayanamsa: StateFlow<String> = _ayanamsa.asStateFlow()

  // AI & Privacy
  val aiProviderMode: StateFlow<AiProviderMode> =
    aiSettingsRepository.observeMode().stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      AiProviderMode.CLOUD
    )

  val consentMap: StateFlow<Map<ConsentType, ConsentStatus>> =
    combine(
      ConsentType.values().map { type ->
        consentRepository.getConsentStatus(type)
      }
    ) { statuses ->
      ConsentType.values().zip(statuses.toList()).toMap()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

  // Appearance
  private val _themeMode = MutableStateFlow(settingsPreferences.getThemeMode())
  val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

  private val _useDynamicColors = MutableStateFlow(settingsPreferences.getUseDynamicColors())
  val useDynamicColors: StateFlow<Boolean> = _useDynamicColors.asStateFlow()

  private val _accentColor = MutableStateFlow(settingsPreferences.getAccentColor())
  val accentColor: StateFlow<AccentColor> = _accentColor.asStateFlow()

  private val _reduceAnimations = MutableStateFlow(settingsPreferences.getReduceAnimations())
  val reduceAnimations: StateFlow<Boolean> = _reduceAnimations.asStateFlow()

  // Notifications
  private val _dailyInsightsEnabled = MutableStateFlow(settingsPreferences.getDailyInsightsEnabled())
  val dailyInsightsEnabled: StateFlow<Boolean> = _dailyInsightsEnabled.asStateFlow()

  private val _transitAlertsEnabled = MutableStateFlow(settingsPreferences.getTransitAlertsEnabled())
  val transitAlertsEnabled: StateFlow<Boolean> = _transitAlertsEnabled.asStateFlow()

  private val _notificationTime = MutableStateFlow(settingsPreferences.getNotificationTime())
  val notificationTime: StateFlow<Pair<Int, Int>> = _notificationTime.asStateFlow()

  // Haptics & Sound
  private val _hapticEnabled = MutableStateFlow(hapticPreferences.isHapticEnabled())
  val hapticEnabled: StateFlow<Boolean> = _hapticEnabled.asStateFlow()

  private val _soundEnabled = MutableStateFlow(settingsPreferences.getSoundEnabled())
  val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

  // Loading/Error States
  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  private val _showClearDataDialog = MutableStateFlow(false)
  val showClearDataDialog: StateFlow<Boolean> = _showClearDataDialog.asStateFlow()

  // Profile Settings Actions
  fun setNumerologySystem(system: NumerologySystem) {
    viewModelScope.launch {
      settingsPreferences.setNumerologySystem(system)
      _numerologySystem.value = system
    }
  }

  fun setHouseSystem(system: HouseSystem) {
    viewModelScope.launch {
      settingsPreferences.setHouseSystem(system)
      _houseSystem.value = system
    }
  }

  fun setUseSidereal(sidereal: Boolean) {
    viewModelScope.launch {
      settingsPreferences.setUseSidereal(sidereal)
      _useSidereal.value = sidereal
    }
  }

  fun setAyanamsa(ayanamsa: String) {
    viewModelScope.launch {
      settingsPreferences.setAyanamsa(ayanamsa)
      _ayanamsa.value = ayanamsa
    }
  }

  // AI & Privacy Actions
  fun setAiProviderMode(mode: AiProviderMode) {
    viewModelScope.launch {
      aiSettingsRepository.setMode(mode)
    }
  }

  fun toggleConsent(type: ConsentType, granted: Boolean) {
    viewModelScope.launch {
      consentRepository.updateConsent(
        type,
        if (granted) ConsentStatus.GRANTED else ConsentStatus.DENIED
      )
    }
  }

  fun clearAiCache() {
    viewModelScope.launch {
      _isLoading.value = true
      // TODO: Implement AI cache clearing
      _isLoading.value = false
    }
  }

  // Appearance Actions
  fun setThemeMode(mode: ThemeMode) {
    viewModelScope.launch {
      settingsPreferences.setThemeMode(mode)
      _themeMode.value = mode
    }
  }

  fun setUseDynamicColors(enabled: Boolean) {
    viewModelScope.launch {
      settingsPreferences.setUseDynamicColors(enabled)
      _useDynamicColors.value = enabled
    }
  }

  fun setAccentColor(color: AccentColor) {
    viewModelScope.launch {
      settingsPreferences.setAccentColor(color)
      _accentColor.value = color
    }
  }

  fun setReduceAnimations(enabled: Boolean) {
    viewModelScope.launch {
      settingsPreferences.setReduceAnimations(enabled)
      _reduceAnimations.value = enabled
    }
  }

  // Notifications Actions
  fun setDailyInsightsEnabled(enabled: Boolean) {
    viewModelScope.launch {
      settingsPreferences.setDailyInsightsEnabled(enabled)
      _dailyInsightsEnabled.value = enabled
    }
  }

  fun setTransitAlertsEnabled(enabled: Boolean) {
    viewModelScope.launch {
      settingsPreferences.setTransitAlertsEnabled(enabled)
      _transitAlertsEnabled.value = enabled
    }
  }

  fun setNotificationTime(hour: Int, minute: Int) {
    viewModelScope.launch {
      settingsPreferences.setNotificationTime(hour, minute)
      _notificationTime.value = hour to minute
    }
  }

  // Haptics & Sound Actions
  fun setHapticEnabled(enabled: Boolean) {
    viewModelScope.launch {
      hapticPreferences.setHapticEnabled(enabled)
      _hapticEnabled.value = enabled
    }
  }

  fun setSoundEnabled(enabled: Boolean) {
    viewModelScope.launch {
      settingsPreferences.setSoundEnabled(enabled)
      _soundEnabled.value = enabled
    }
  }

  // Data Actions
  fun exportAllProfiles() {
    viewModelScope.launch {
      _isLoading.value = true
      // TODO: Implement profile export
      _isLoading.value = false
    }
  }

  fun importProfiles() {
    viewModelScope.launch {
      _isLoading.value = true
      // TODO: Implement profile import
      _isLoading.value = false
    }
  }

  fun showClearDataDialog() {
    _showClearDataDialog.value = true
  }

  fun dismissClearDataDialog() {
    _showClearDataDialog.value = false
  }

  fun clearAllData() {
    viewModelScope.launch {
      _isLoading.value = true
      try {
        userRepository.clearUserData()
        _showClearDataDialog.value = false
      } finally {
        _isLoading.value = false
      }
    }
  }
}

// Settings Preferences Manager
class SettingsPreferences(context: Context) {
  private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  companion object {
    private const val PREFS_NAME = "settings_preferences"

    // Profile Settings
    private const val KEY_NUMEROLOGY_SYSTEM = "numerology_system"
    private const val KEY_HOUSE_SYSTEM = "house_system"
    private const val KEY_USE_SIDEREAL = "use_sidereal"
    private const val KEY_AYANAMSA = "ayanamsa"

    // Appearance
    private const val KEY_THEME_MODE = "theme_mode"
    private const val KEY_USE_DYNAMIC_COLORS = "use_dynamic_colors"
    private const val KEY_ACCENT_COLOR = "accent_color"
    private const val KEY_REDUCE_ANIMATIONS = "reduce_animations"

    // Notifications
    private const val KEY_DAILY_INSIGHTS = "daily_insights_enabled"
    private const val KEY_TRANSIT_ALERTS = "transit_alerts_enabled"
    private const val KEY_NOTIFICATION_HOUR = "notification_hour"
    private const val KEY_NOTIFICATION_MINUTE = "notification_minute"

    // Sound
    private const val KEY_SOUND_ENABLED = "sound_enabled"
  }

  // Profile Settings
  fun getNumerologySystem(): NumerologySystem {
    val value = prefs.getString(KEY_NUMEROLOGY_SYSTEM, NumerologySystem.CHALDEAN.name)
    return NumerologySystem.valueOf(value ?: NumerologySystem.CHALDEAN.name)
  }

  fun setNumerologySystem(system: NumerologySystem) {
    prefs.edit().putString(KEY_NUMEROLOGY_SYSTEM, system.name).apply()
  }

  fun getHouseSystem(): HouseSystem {
    val value = prefs.getString(KEY_HOUSE_SYSTEM, HouseSystem.WHOLE_SIGN.name)
    return HouseSystem.valueOf(value ?: HouseSystem.WHOLE_SIGN.name)
  }

  fun setHouseSystem(system: HouseSystem) {
    prefs.edit().putString(KEY_HOUSE_SYSTEM, system.name).apply()
  }

  fun getUseSidereal(): Boolean {
    return prefs.getBoolean(KEY_USE_SIDEREAL, true)
  }

  fun setUseSidereal(sidereal: Boolean) {
    prefs.edit().putBoolean(KEY_USE_SIDEREAL, sidereal).apply()
  }

  fun getAyanamsa(): String {
    return prefs.getString(KEY_AYANAMSA, "Lahiri") ?: "Lahiri"
  }

  fun setAyanamsa(ayanamsa: String) {
    prefs.edit().putString(KEY_AYANAMSA, ayanamsa).apply()
  }

  // Appearance
  fun getThemeMode(): ThemeMode {
    val value = prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
    return ThemeMode.valueOf(value ?: ThemeMode.SYSTEM.name)
  }

  fun setThemeMode(mode: ThemeMode) {
    prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
  }

  fun getUseDynamicColors(): Boolean {
    return prefs.getBoolean(KEY_USE_DYNAMIC_COLORS, true)
  }

  fun setUseDynamicColors(enabled: Boolean) {
    prefs.edit().putBoolean(KEY_USE_DYNAMIC_COLORS, enabled).apply()
  }

  fun getAccentColor(): AccentColor {
    val value = prefs.getString(KEY_ACCENT_COLOR, AccentColor.SPIRITUAL_PURPLE.name)
    return AccentColor.valueOf(value ?: AccentColor.SPIRITUAL_PURPLE.name)
  }

  fun setAccentColor(color: AccentColor) {
    prefs.edit().putString(KEY_ACCENT_COLOR, color.name).apply()
  }

  fun getReduceAnimations(): Boolean {
    return prefs.getBoolean(KEY_REDUCE_ANIMATIONS, false)
  }

  fun setReduceAnimations(enabled: Boolean) {
    prefs.edit().putBoolean(KEY_REDUCE_ANIMATIONS, enabled).apply()
  }

  // Notifications
  fun getDailyInsightsEnabled(): Boolean {
    return prefs.getBoolean(KEY_DAILY_INSIGHTS, true)
  }

  fun setDailyInsightsEnabled(enabled: Boolean) {
    prefs.edit().putBoolean(KEY_DAILY_INSIGHTS, enabled).apply()
  }

  fun getTransitAlertsEnabled(): Boolean {
    return prefs.getBoolean(KEY_TRANSIT_ALERTS, true)
  }

  fun setTransitAlertsEnabled(enabled: Boolean) {
    prefs.edit().putBoolean(KEY_TRANSIT_ALERTS, enabled).apply()
  }

  fun getNotificationTime(): Pair<Int, Int> {
    val hour = prefs.getInt(KEY_NOTIFICATION_HOUR, 9)
    val minute = prefs.getInt(KEY_NOTIFICATION_MINUTE, 0)
    return hour to minute
  }

  fun setNotificationTime(hour: Int, minute: Int) {
    prefs.edit()
      .putInt(KEY_NOTIFICATION_HOUR, hour)
      .putInt(KEY_NOTIFICATION_MINUTE, minute)
      .apply()
  }

  // Sound
  fun getSoundEnabled(): Boolean {
    return prefs.getBoolean(KEY_SOUND_ENABLED, true)
  }

  fun setSoundEnabled(enabled: Boolean) {
    prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply()
  }
}

// Enums
enum class ThemeMode {
  SYSTEM, LIGHT, DARK
}

enum class AccentColor(val displayName: String, val colorHex: Long) {
  SPIRITUAL_PURPLE("Spiritual Purple", 0xFF8B5CF6),
  MYSTIC_VIOLET("Mystic Violet", 0xFF7C3AED),
  COSMIC_BLUE("Cosmic Blue", 0xFF3B82F6),
  CHAKRA_GREEN("Chakra Green", 0xFF22C55E),
  TANTRIC_ROSE("Tantric Rose", 0xFFEC4899),
  AURA_GOLD("Aura Gold", 0xFFFBBF24),
  CHAKRA_INDIGO("Chakra Indigo", 0xFF6366F1)
}
