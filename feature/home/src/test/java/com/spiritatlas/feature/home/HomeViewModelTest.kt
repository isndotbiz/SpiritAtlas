package com.spiritatlas.feature.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import io.mockk.every
import io.mockk.mockk
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

class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var aiSettingsRepository: AiSettingsRepository
    private lateinit var consentRepository: ConsentRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        aiSettingsRepository = mockk(relaxed = true)
        consentRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial provider label is AUTO`() = runTest {
        every { aiSettingsRepository.observeMode() } returns flowOf(AiProviderMode.AUTO)

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            val label = awaitItem()
            assertThat(label).isEqualTo("AI Provider: Auto (prefers OpenRouter)")
        }
    }

    @Test
    fun `provider label updates when mode changes to LOCAL`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.AUTO)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            assertThat(awaitItem()).isEqualTo("AI Provider: Auto (prefers OpenRouter)")

            modeFlow.value = AiProviderMode.LOCAL
            assertThat(awaitItem()).isEqualTo("AI Provider: Ollama")
        }
    }

    @Test
    fun `provider label updates when mode changes to GEMINI`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.AUTO)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            skipItems(1) // Skip initial AUTO

            modeFlow.value = AiProviderMode.GEMINI
            assertThat(awaitItem()).isEqualTo("AI Provider: Gemini")
        }
    }

    @Test
    fun `provider label updates when mode changes to GROQ`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.GROQ)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            assertThat(awaitItem()).isEqualTo("AI Provider: Groq")
        }
    }

    @Test
    fun `provider label updates when mode changes to OPENAI`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.OPENAI)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            assertThat(awaitItem()).isEqualTo("AI Provider: OpenAI")
        }
    }

    @Test
    fun `provider label updates when mode changes to CLAUDE`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.CLAUDE)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            assertThat(awaitItem()).isEqualTo("AI Provider: Claude")
        }
    }

    @Test
    fun `provider label updates when mode changes to OPENROUTER`() = runTest {
        val modeFlow = MutableStateFlow(AiProviderMode.OPENROUTER)
        every { aiSettingsRepository.observeMode() } returns modeFlow

        val viewModel = HomeViewModel(aiSettingsRepository, consentRepository)
        advanceUntilIdle()

        viewModel.providerLabel.test {
            assertThat(awaitItem()).isEqualTo("AI Provider: OpenRouter")
        }
    }
}

class ConsentDebugViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var consentRepository: ConsentRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        consentRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `debugText shows all consent statuses`() = runTest {
        every { consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT) } returns
            flowOf(ConsentStatus.GRANTED)
        every { consentRepository.getConsentStatus(ConsentType.CLOUD_SYNC) } returns
            flowOf(ConsentStatus.DENIED)
        every { consentRepository.getConsentStatus(ConsentType.ANALYTICS) } returns
            flowOf(ConsentStatus.NOT_ASKED)

        val viewModel = ConsentDebugViewModel(consentRepository)
        advanceUntilIdle()

        viewModel.debugText.test {
            val text = awaitItem()
            assertThat(text).contains("AI_ENRICHMENT=GRANTED")
            assertThat(text).contains("CLOUD_SYNC=DENIED")
            assertThat(text).contains("ANALYTICS=NOT_ASKED")
        }
    }

    @Test
    fun `debugText updates when consent changes`() = runTest {
        val aiConsentFlow = MutableStateFlow(ConsentStatus.NOT_ASKED)
        val cloudConsentFlow = MutableStateFlow(ConsentStatus.NOT_ASKED)
        val analyticsConsentFlow = MutableStateFlow(ConsentStatus.NOT_ASKED)

        every { consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT) } returns aiConsentFlow
        every { consentRepository.getConsentStatus(ConsentType.CLOUD_SYNC) } returns cloudConsentFlow
        every { consentRepository.getConsentStatus(ConsentType.ANALYTICS) } returns analyticsConsentFlow

        val viewModel = ConsentDebugViewModel(consentRepository)
        advanceUntilIdle()

        viewModel.debugText.test {
            // Initial state
            val initial = awaitItem()
            assertThat(initial).contains("AI_ENRICHMENT=NOT_ASKED")

            // Update AI consent
            aiConsentFlow.value = ConsentStatus.GRANTED
            val updated = awaitItem()
            assertThat(updated).contains("AI_ENRICHMENT=GRANTED")
            assertThat(updated).contains("CLOUD_SYNC=NOT_ASKED")
        }
    }

    @Test
    fun `debugText shows all consents granted`() = runTest {
        every { consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT) } returns
            flowOf(ConsentStatus.GRANTED)
        every { consentRepository.getConsentStatus(ConsentType.CLOUD_SYNC) } returns
            flowOf(ConsentStatus.GRANTED)
        every { consentRepository.getConsentStatus(ConsentType.ANALYTICS) } returns
            flowOf(ConsentStatus.GRANTED)

        val viewModel = ConsentDebugViewModel(consentRepository)
        advanceUntilIdle()

        viewModel.debugText.test {
            val text = awaitItem()
            assertThat(text).contains("AI_ENRICHMENT=GRANTED")
            assertThat(text).contains("CLOUD_SYNC=GRANTED")
            assertThat(text).contains("ANALYTICS=GRANTED")
        }
    }

    @Test
    fun `debugText shows all consents denied`() = runTest {
        every { consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT) } returns
            flowOf(ConsentStatus.DENIED)
        every { consentRepository.getConsentStatus(ConsentType.CLOUD_SYNC) } returns
            flowOf(ConsentStatus.DENIED)
        every { consentRepository.getConsentStatus(ConsentType.ANALYTICS) } returns
            flowOf(ConsentStatus.DENIED)

        val viewModel = ConsentDebugViewModel(consentRepository)
        advanceUntilIdle()

        viewModel.debugText.test {
            val text = awaitItem()
            assertThat(text).contains("AI_ENRICHMENT=DENIED")
            assertThat(text).contains("CLOUD_SYNC=DENIED")
            assertThat(text).contains("ANALYTICS=DENIED")
        }
    }
}
