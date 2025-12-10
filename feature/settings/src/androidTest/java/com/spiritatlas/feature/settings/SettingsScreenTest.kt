package com.spiritatlas.feature.settings

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spiritatlas.domain.ai.AiProviderMode
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for SettingsScreen.
 *
 * Tests the settings UI including:
 * - AI provider selection
 * - Provider configuration
 * - API key input
 * - Test connection functionality
 * - Navigation
 * - Accessibility features
 */
@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockViewModel(): SettingsViewModel {
        return object : SettingsViewModel() {
            private val _aiProviderMode = MutableStateFlow(AiProviderMode.LOCAL)
            private val _providerStatuses = MutableStateFlow(
                mapOf(
                    AiProviderMode.LOCAL to ProviderStatus(isAvailable = true, hasApiKey = false),
                    AiProviderMode.GEMINI to ProviderStatus(isAvailable = true, hasApiKey = false),
                    AiProviderMode.GROQ to ProviderStatus(isAvailable = true, hasApiKey = false),
                    AiProviderMode.OPENROUTER to ProviderStatus(isAvailable = true, hasApiKey = false)
                )
            )
            private val _testConnectionResult = MutableStateFlow<TestConnectionResult?>(null)

            override val aiProviderMode = _aiProviderMode
            override val providerStatuses = _providerStatuses
            override val testConnectionResult = _testConnectionResult

            override fun setAiProviderMode(mode: AiProviderMode) {
                _aiProviderMode.value = mode
            }

            override fun setProviderApiKey(provider: AiProviderMode, apiKey: String) {
                // Mock implementation
            }

            override fun testProviderConnection(provider: AiProviderMode) {
                _testConnectionResult.value = TestConnectionResult(
                    provider = provider,
                    status = TestStatus.SUCCESS,
                    message = "Connection successful"
                )
            }

            override fun clearTestConnectionResult() {
                _testConnectionResult.value = null
            }
        }
    }

    @Test
    fun settingsScreen_displaysTitle() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Verify settings title is displayed
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_displaysBackButton() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Verify back button is displayed
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_backButton_callsNavigationCallback() {
        var backPressed = false

        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = { backPressed = true },
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Click back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(backPressed) { "Back navigation callback should be called" }
    }

    @Test
    fun settingsScreen_displaysAiProviderSection() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify AI Provider section is displayed
        composeTestRule.onNodeWithText("AI Provider").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_displaysCurrentlySelectedProvider() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify currently selected provider is displayed
        composeTestRule.onNodeWithText("Currently Selected").assertIsDisplayed()
        composeTestRule.onNodeWithText("Local (Ollama)").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_displaysAllProviders() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify all provider options are displayed
        composeTestRule.onNodeWithText("Gemini").assertIsDisplayed()
        composeTestRule.onNodeWithText("Groq").assertIsDisplayed()
        composeTestRule.onNodeWithText("OpenRouter").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_providerCards_displayStatusBadges() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify status badges are displayed (Available, App Key, etc.)
        composeTestRule.onAllNodesWithText("Available").assertCountAtLeast(1)
    }

    @Test
    fun settingsScreen_selectProvider_updatesSelection() {
        val viewModel = createMockViewModel()

        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on a different provider (Gemini)
        composeTestRule.onNodeWithText("Gemini").performClick()

        // Wait for state update
        composeTestRule.waitForIdle()

        // Verify selection changed (UI should reflect new selection)
        // The currently selected text should update
        assert(viewModel.aiProviderMode.value == AiProviderMode.GEMINI) {
            "AI Provider should be updated to Gemini"
        }
    }

    @Test
    fun settingsScreen_providerCard_expandsForApiKeyEntry() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on OpenRouter which supports API key
        composeTestRule.onNodeWithText("OpenRouter").performClick()

        // Wait for expansion
        composeTestRule.waitForIdle()

        // API key field should be visible
        composeTestRule.onNode(
            hasSetTextAction() and hasText("API Key", substring = true)
        ).assertExists()
    }

    @Test
    fun settingsScreen_apiKeyField_acceptsInput() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on OpenRouter to expand
        composeTestRule.onNodeWithText("OpenRouter").performClick()
        composeTestRule.waitForIdle()

        // Input API key
        composeTestRule.onNode(
            hasSetTextAction() and hasText("API Key", substring = true)
        ).performTextInput("test-api-key-123")

        // Verify text was entered
        composeTestRule.onNodeWithText("test-api-key-123").assertExists()
    }

    @Test
    fun settingsScreen_apiKeyField_hasVisibilityToggle() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on OpenRouter to expand
        composeTestRule.onNodeWithText("OpenRouter").performClick()
        composeTestRule.waitForIdle()

        // Visibility toggle should exist (Show/Hide button)
        composeTestRule.onNode(
            hasContentDescription("Show") or hasContentDescription("Hide")
        ).assertExists()
    }

    @Test
    fun settingsScreen_saveButton_availableWithApiKey() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on OpenRouter to expand
        composeTestRule.onNodeWithText("OpenRouter").performClick()
        composeTestRule.waitForIdle()

        // Input API key
        composeTestRule.onNode(
            hasSetTextAction() and hasText("API Key", substring = true)
        ).performTextInput("test-key")

        composeTestRule.waitForIdle()

        // Save button should be visible and enabled
        composeTestRule.onNode(
            hasText("Save", substring = true) and isEnabled()
        ).assertExists()
    }

    @Test
    fun settingsScreen_testButton_available() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on a provider to expand
        composeTestRule.onNodeWithText("OpenRouter").performClick()
        composeTestRule.waitForIdle()

        // Test button should be visible
        composeTestRule.onNode(
            hasText("Test", substring = true)
        ).assertExists()
    }

    @Test
    fun settingsScreen_testConnection_showsResult() {
        val viewModel = createMockViewModel()

        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Expand provider
        composeTestRule.onNodeWithText("Gemini").performClick()
        composeTestRule.waitForIdle()

        // Click test button
        composeTestRule.onNodeWithText("Test").performClick()

        // Wait for test result
        composeTestRule.waitForIdle()

        // Test result should be displayed
        composeTestRule.onNodeWithText("Connection successful").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_contentIsScrollable() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify the content is scrollable
        composeTestRule.onNode(hasScrollAction()).assertExists()
    }

    @Test
    fun settingsScreen_hasAccessibilityLabels() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Verify key interactive elements have proper accessibility
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }

    @Test
    fun settingsScreen_providerDescriptions_provideContext() {
        composeTestRule.setContent {
            SettingsScreen(
                onNavigateBack = {},
                onNavigateToConsent = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify providers have descriptive text
        composeTestRule.onNode(
            hasText("fast and free", substring = true, ignoreCase = true)
        ).assertExists()

        composeTestRule.onNode(
            hasText("completely private", substring = true, ignoreCase = true)
        ).assertExists()
    }
}
