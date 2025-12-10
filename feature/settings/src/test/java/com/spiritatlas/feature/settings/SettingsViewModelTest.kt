package com.spiritatlas.feature.settings

import android.content.Context
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.data.tracking.ProviderUsage
import com.spiritatlas.data.tracking.UsageRepository
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.model.HouseSystem
import com.spiritatlas.domain.model.NumerologySystem
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import com.spiritatlas.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SettingsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var consentRepository: ConsentRepository
    private lateinit var aiSettingsRepository: AiSettingsRepository
    private lateinit var userRepository: UserRepository
    private lateinit var usageRepository: UsageRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        context = mockk(relaxed = true)
        consentRepository = mockk(relaxed = true)
        aiSettingsRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
        usageRepository = mockk(relaxed = true)

        // Mock SharedPreferences
        val sharedPreferences = mockk<android.content.SharedPreferences>(relaxed = true)
        val editor = mockk<android.content.SharedPreferences.Editor>(relaxed = true)
        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.putInt(any(), any()) } returns editor
        every { editor.apply() } returns Unit
        every { sharedPreferences.getString(any(), any()) } returns null
        every { sharedPreferences.getBoolean(any(), any()) } returns false
        every { sharedPreferences.getInt(any(), any()) } returns 0

        // Mock repository defaults
        every { aiSettingsRepository.observeMode() } returns flowOf(AiProviderMode.AUTO)
        every { consentRepository.getConsentStatus(any()) } returns flowOf(ConsentStatus.NOT_ASKED)
        every { usageRepository.observeGeminiUsage() } returns flowOf(ProviderUsage(0, 0, 15, 1500))
        every { usageRepository.observeGroqUsage() } returns flowOf(ProviderUsage(0, 0, 30, null))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setAiProviderMode updates provider mode`() = runTest {
        val viewModel = createViewModel()

        viewModel.setAiProviderMode(AiProviderMode.GEMINI)
        advanceUntilIdle()

        coVerify { aiSettingsRepository.setMode(AiProviderMode.GEMINI) }
    }

    @Test
    fun `toggleConsent grants consent when true`() = runTest {
        val viewModel = createViewModel()

        viewModel.toggleConsent(ConsentType.AI_ENRICHMENT, true)
        advanceUntilIdle()

        coVerify { consentRepository.updateConsent(ConsentType.AI_ENRICHMENT, ConsentStatus.GRANTED) }
    }

    @Test
    fun `toggleConsent denies consent when false`() = runTest {
        val viewModel = createViewModel()

        viewModel.toggleConsent(ConsentType.AI_ENRICHMENT, false)
        advanceUntilIdle()

        coVerify { consentRepository.updateConsent(ConsentType.AI_ENRICHMENT, ConsentStatus.DENIED) }
    }

    @Test
    fun `clearAllData calls repository and dismisses dialog`() = runTest {
        coEvery { userRepository.clearUserData() } returns Unit

        val viewModel = createViewModel()
        viewModel.showClearDataDialog()

        assertThat(viewModel.showClearDataDialog.value).isTrue()

        viewModel.clearAllData()
        advanceUntilIdle()

        coVerify { userRepository.clearUserData() }
        assertThat(viewModel.showClearDataDialog.value).isFalse()
    }

    @Test
    fun `showClearDataDialog sets dialog state`() = runTest {
        val viewModel = createViewModel()

        assertThat(viewModel.showClearDataDialog.value).isFalse()

        viewModel.showClearDataDialog()
        assertThat(viewModel.showClearDataDialog.value).isTrue()
    }

    @Test
    fun `dismissClearDataDialog clears dialog state`() = runTest {
        val viewModel = createViewModel()
        viewModel.showClearDataDialog()

        viewModel.dismissClearDataDialog()
        assertThat(viewModel.showClearDataDialog.value).isFalse()
    }

    @Test
    fun `resetAiUsage calls repository`() = runTest {
        coEvery { usageRepository.resetAllUsage() } returns Unit

        val viewModel = createViewModel()
        viewModel.resetAiUsage()
        advanceUntilIdle()

        coVerify { usageRepository.resetAllUsage() }
    }

    @Test
    fun `loadProviderStatuses loads all provider statuses`() = runTest {
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns true
        coEvery { aiSettingsRepository.getOpenAiApiKey() } returns "test-key"
        coEvery { aiSettingsRepository.getClaudeApiKey() } returns null
        coEvery { aiSettingsRepository.getOpenRouterApiKey() } returns "router-key"

        val viewModel = createViewModel()
        advanceUntilIdle()

        val statuses = viewModel.providerStatuses.value
        assertThat(statuses).isNotEmpty()
        assertThat(statuses[AiProviderMode.OPENAI]?.hasApiKey).isTrue()
        assertThat(statuses[AiProviderMode.CLAUDE]?.hasApiKey).isFalse()
        assertThat(statuses[AiProviderMode.OPENROUTER]?.hasApiKey).isTrue()
    }

    @Test
    fun `setProviderApiKey updates OpenAI key`() = runTest {
        coEvery { aiSettingsRepository.setOpenAiApiKey(any()) } returns Unit
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns true

        val viewModel = createViewModel()
        viewModel.setProviderApiKey(AiProviderMode.OPENAI, "new-key")
        advanceUntilIdle()

        coVerify { aiSettingsRepository.setOpenAiApiKey("new-key") }
    }

    @Test
    fun `setProviderApiKey updates Claude key`() = runTest {
        coEvery { aiSettingsRepository.setClaudeApiKey(any()) } returns Unit
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns true

        val viewModel = createViewModel()
        viewModel.setProviderApiKey(AiProviderMode.CLAUDE, "claude-key")
        advanceUntilIdle()

        coVerify { aiSettingsRepository.setClaudeApiKey("claude-key") }
    }

    @Test
    fun `setProviderApiKey updates OpenRouter key`() = runTest {
        coEvery { aiSettingsRepository.setOpenRouterApiKey(any()) } returns Unit
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns true

        val viewModel = createViewModel()
        viewModel.setProviderApiKey(AiProviderMode.OPENROUTER, "router-key")
        advanceUntilIdle()

        coVerify { aiSettingsRepository.setOpenRouterApiKey("router-key") }
    }

    @Test
    fun `setProviderApiKey with blank key sets null`() = runTest {
        coEvery { aiSettingsRepository.setOpenAiApiKey(any()) } returns Unit
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns true

        val viewModel = createViewModel()
        viewModel.setProviderApiKey(AiProviderMode.OPENAI, "")
        advanceUntilIdle()

        coVerify { aiSettingsRepository.setOpenAiApiKey(null) }
    }

    @Test
    fun `testProviderConnection shows testing status`() = runTest {
        coEvery { aiSettingsRepository.isProviderAvailable(AiProviderMode.GEMINI) } coAnswers {
            kotlinx.coroutines.delay(100)
            true
        }

        val viewModel = createViewModel()
        viewModel.testProviderConnection(AiProviderMode.GEMINI)

        // Should be in TESTING status initially
        val result = viewModel.testConnectionResult.value
        assertThat(result?.status).isEqualTo(TestStatus.TESTING)
        assertThat(result?.provider).isEqualTo(AiProviderMode.GEMINI)
    }

    @Test
    fun `testProviderConnection succeeds when provider available`() = runTest {
        coEvery { aiSettingsRepository.isProviderAvailable(AiProviderMode.GEMINI) } returns true

        val viewModel = createViewModel()
        viewModel.testProviderConnection(AiProviderMode.GEMINI)
        advanceUntilIdle()

        val result = viewModel.testConnectionResult.value
        assertThat(result?.status).isEqualTo(TestStatus.SUCCESS)
        assertThat(result?.message).isEqualTo("Connection successful")
    }

    @Test
    fun `testProviderConnection fails when provider unavailable`() = runTest {
        coEvery { aiSettingsRepository.isProviderAvailable(AiProviderMode.OPENAI) } returns false

        val viewModel = createViewModel()
        viewModel.testProviderConnection(AiProviderMode.OPENAI)
        advanceUntilIdle()

        val result = viewModel.testConnectionResult.value
        assertThat(result?.status).isEqualTo(TestStatus.FAILED)
        assertThat(result?.message).contains("unavailable")
    }

    @Test
    fun `testProviderConnection fails on exception`() = runTest {
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } throws RuntimeException("Network error")

        val viewModel = createViewModel()
        viewModel.testProviderConnection(AiProviderMode.GROQ)
        advanceUntilIdle()

        val result = viewModel.testConnectionResult.value
        assertThat(result?.status).isEqualTo(TestStatus.FAILED)
        assertThat(result?.message).contains("Network error")
    }

    @Test
    fun `clearTestConnectionResult clears result`() = runTest {
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns true

        val viewModel = createViewModel()
        viewModel.testProviderConnection(AiProviderMode.GEMINI)
        advanceUntilIdle()

        assertThat(viewModel.testConnectionResult.value).isNotNull()

        viewModel.clearTestConnectionResult()
        assertThat(viewModel.testConnectionResult.value).isNull()
    }

    @Test
    fun `setHapticEnabled updates haptic preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setHapticEnabled(true)
        advanceUntilIdle()

        viewModel.hapticEnabled.test {
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun `setSoundEnabled updates sound preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setSoundEnabled(false)
        advanceUntilIdle()

        viewModel.soundEnabled.test {
            assertThat(awaitItem()).isFalse()
        }
    }

    @Test
    fun `setDailyInsightsEnabled updates notification preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setDailyInsightsEnabled(true)
        advanceUntilIdle()

        viewModel.dailyInsightsEnabled.test {
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun `setTransitAlertsEnabled updates alert preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setTransitAlertsEnabled(false)
        advanceUntilIdle()

        viewModel.transitAlertsEnabled.test {
            assertThat(awaitItem()).isFalse()
        }
    }

    @Test
    fun `setNotificationTime updates time preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setNotificationTime(9, 30)
        advanceUntilIdle()

        viewModel.notificationTime.test {
            val (hour, minute) = awaitItem()
            assertThat(hour).isEqualTo(9)
            assertThat(minute).isEqualTo(30)
        }
    }

    @Test
    fun `setThemeMode updates theme preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setThemeMode(ThemeMode.DARK)
        advanceUntilIdle()

        viewModel.themeMode.test {
            assertThat(awaitItem()).isEqualTo(ThemeMode.DARK)
        }
    }

    @Test
    fun `setUseDynamicColors updates dynamic color preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setUseDynamicColors(true)
        advanceUntilIdle()

        viewModel.useDynamicColors.test {
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun `setAccentColor updates accent preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setAccentColor(AccentColor.COSMIC_BLUE)
        advanceUntilIdle()

        viewModel.accentColor.test {
            assertThat(awaitItem()).isEqualTo(AccentColor.COSMIC_BLUE)
        }
    }

    @Test
    fun `setReduceAnimations updates animation preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setReduceAnimations(true)
        advanceUntilIdle()

        viewModel.reduceAnimations.test {
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun `setNumerologySystem updates numerology preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setNumerologySystem(NumerologySystem.PYTHAGOREAN)
        advanceUntilIdle()

        viewModel.numerologySystem.test {
            assertThat(awaitItem()).isEqualTo(NumerologySystem.PYTHAGOREAN)
        }
    }

    @Test
    fun `setHouseSystem updates house system preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setHouseSystem(HouseSystem.PLACIDUS)
        advanceUntilIdle()

        viewModel.houseSystem.test {
            assertThat(awaitItem()).isEqualTo(HouseSystem.PLACIDUS)
        }
    }

    @Test
    fun `setUseSidereal updates sidereal preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setUseSidereal(false)
        advanceUntilIdle()

        viewModel.useSidereal.test {
            assertThat(awaitItem()).isFalse()
        }
    }

    @Test
    fun `setAyanamsa updates ayanamsa preference`() = runTest {
        val viewModel = createViewModel()

        viewModel.setAyanamsa("Lahiri")
        advanceUntilIdle()

        viewModel.ayanamsa.test {
            assertThat(awaitItem()).isEqualTo("Lahiri")
        }
    }

    @Test
    fun `aiProviderMode reflects repository state`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.AUTO)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.aiProviderMode.test {
            assertThat(awaitItem()).isEqualTo(AiProviderMode.AUTO)

            modeFlow.value = AiProviderMode.GEMINI
            assertThat(awaitItem()).isEqualTo(AiProviderMode.GEMINI)
        }
    }

    @Test
    fun `consentMap includes all consent types`() = runTest {
        every { consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT) } returns
            flowOf(ConsentStatus.GRANTED)
        every { consentRepository.getConsentStatus(ConsentType.CLOUD_SYNC) } returns
            flowOf(ConsentStatus.DENIED)
        every { consentRepository.getConsentStatus(ConsentType.ANALYTICS) } returns
            flowOf(ConsentStatus.NOT_ASKED)

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.consentMap.test {
            val map = awaitItem()
            assertThat(map[ConsentType.AI_ENRICHMENT]).isEqualTo(ConsentStatus.GRANTED)
            assertThat(map[ConsentType.CLOUD_SYNC]).isEqualTo(ConsentStatus.DENIED)
            assertThat(map[ConsentType.ANALYTICS]).isEqualTo(ConsentStatus.NOT_ASKED)
        }
    }

    private fun createViewModel(): SettingsViewModel {
        coEvery { aiSettingsRepository.isProviderAvailable(any()) } returns false
        coEvery { aiSettingsRepository.getOpenAiApiKey() } returns null
        coEvery { aiSettingsRepository.getClaudeApiKey() } returns null
        coEvery { aiSettingsRepository.getOpenRouterApiKey() } returns null

        return SettingsViewModel(
            context,
            consentRepository,
            aiSettingsRepository,
            userRepository,
            usageRepository
        )
    }
}
