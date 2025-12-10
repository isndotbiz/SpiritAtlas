package com.spiritatlas.feature.profile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spiritatlas.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * UI tests for ProfileScreen.
 *
 * Tests the profile creation and editing UI including:
 * - Form rendering and field validation
 * - Section navigation
 * - Data input handling
 * - Save functionality
 * - Loading states
 * - Error states
 * - Accessibility features
 */
@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockViewModel(): ProfileViewModel {
        return object : ProfileViewModel() {
            private val _uiState = MutableStateFlow(
                ProfileUiState(
                    currentProfile = UserProfile(),
                    activeSection = ProfileSection.CORE,
                    isSaving = false,
                    saveSuccess = false,
                    errorMessage = null
                )
            )

            override val uiState = _uiState

            override fun updateProfile(profile: UserProfile) {
                _uiState.value = _uiState.value.copy(currentProfile = profile)
            }

            override fun setActiveSection(section: ProfileSection) {
                _uiState.value = _uiState.value.copy(activeSection = section)
            }

            override fun saveProfile() {
                _uiState.value = _uiState.value.copy(isSaving = true)
                // Simulate save
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    saveSuccess = true
                )
            }

            override fun clearError() {
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }

            override fun loadTier0Profile() {
                _uiState.value = _uiState.value.copy(
                    currentProfile = UserProfile(
                        name = "Test User",
                        displayName = "Test",
                        birthDateTime = LocalDateTime.now()
                    )
                )
            }

            override fun loadDemoProfile() {
                loadTier0Profile()
            }

            override fun clearProfile() {
                _uiState.value = _uiState.value.copy(currentProfile = UserProfile())
            }
        }
    }

    @Test
    fun profileScreen_displaysTopBar() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Top bar should be visible (has back button and save)
        composeTestRule.onNodeWithContentDescription("Navigate back").assertIsDisplayed()
    }

    @Test
    fun profileScreen_backButton_callsNavigationCallback() {
        var backPressed = false

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = { backPressed = true },
                viewModel = createMockViewModel()
            )
        }

        // Click back button
        composeTestRule.onNodeWithContentDescription("Navigate back").performClick()

        assert(backPressed) { "Back navigation callback should be called" }
    }

    @Test
    fun profileScreen_displaysCoreIdentitySection() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Core Identity section is displayed
        composeTestRule.onNode(
            hasText("Core Identity", substring = true)
        ).assertIsDisplayed()
    }

    @Test
    fun profileScreen_displaysRequiredFields() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify required fields are displayed
        composeTestRule.onNodeWithText("Full Birth Name *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Preferred Name *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Birth Date & Time *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Birth City *").assertIsDisplayed()
    }

    @Test
    fun profileScreen_nameField_acceptsTextInput() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Find and input text in name field
        composeTestRule.onNode(
            hasSetTextAction() and hasText("Full Birth Name *", substring = true)
        ).performTextInput("Luna Alexandra Moonwhisper")

        // Verify text was entered
        composeTestRule.onNodeWithText("Luna Alexandra Moonwhisper").assertExists()
    }

    @Test
    fun profileScreen_displayNameField_acceptsTextInput() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Find and input text in display name field
        composeTestRule.onAllNodes(hasSetTextAction())[1].performTextInput("Luna")

        // Verify text was entered
        composeTestRule.onNodeWithText("Luna").assertExists()
    }

    @Test
    fun profileScreen_displaysSectionTabs() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify section tabs are displayed (should see Core tab at minimum)
        ProfileSection.values().take(3).forEach { section ->
            composeTestRule.onNodeWithText(section.displayName).assertIsDisplayed()
        }
    }

    @Test
    fun profileScreen_sectionTab_switchesContent() {
        val viewModel = createMockViewModel()

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on Names section tab
        composeTestRule.onNodeWithText(ProfileSection.NAMES.displayName).performClick()

        // Wait for section to change
        composeTestRule.waitForIdle()

        // Verify Names section content is now displayed
        composeTestRule.onNode(
            hasText("Additional Names", substring = true)
        ).assertIsDisplayed()
    }

    @Test
    fun profileScreen_familySection_displaysParentFields() {
        val viewModel = createMockViewModel()

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Navigate to Family section
        composeTestRule.onNodeWithText(ProfileSection.FAMILY.displayName).performClick()
        composeTestRule.waitForIdle()

        // Verify family fields are displayed
        composeTestRule.onNodeWithText("Mother's Full Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Father's Full Name").assertIsDisplayed()
    }

    @Test
    fun profileScreen_physicalSection_displaysGenderDropdown() {
        val viewModel = createMockViewModel()

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Navigate to Physical section
        composeTestRule.onNodeWithText(ProfileSection.PHYSICAL.displayName).performClick()
        composeTestRule.waitForIdle()

        // Verify gender dropdown is displayed
        composeTestRule.onNode(
            hasText("Gender Energy", substring = true)
        ).assertIsDisplayed()
    }

    @Test
    fun profileScreen_displaysAccuracyIndicator() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Accuracy indicator should be visible (shows profile completion)
        // It will display some percentage or tier information
        composeTestRule.onNode(
            hasText("%", substring = true) or hasText("Tier", substring = true)
        ).assertExists()
    }

    @Test
    fun profileScreen_saveButton_triggersVsave() {
        val viewModel = createMockViewModel()
        var saveClicked = false

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = object : ProfileViewModel() {
                    override val uiState = viewModel.uiState
                    override fun updateProfile(profile: UserProfile) =
                        viewModel.updateProfile(profile)
                    override fun setActiveSection(section: ProfileSection) =
                        viewModel.setActiveSection(section)
                    override fun clearError() = viewModel.clearError()
                    override fun loadTier0Profile() = viewModel.loadTier0Profile()
                    override fun loadDemoProfile() = viewModel.loadDemoProfile()
                    override fun clearProfile() = viewModel.clearProfile()

                    override fun saveProfile() {
                        saveClicked = true
                        viewModel.saveProfile()
                    }
                }
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Find and click save button (in top bar or menu)
        composeTestRule.onNodeWithContentDescription("Save profile").performClick()

        assert(saveClicked) { "Save profile should be triggered" }
    }

    @Test
    fun profileScreen_saveSuccess_displaysSuccessMessage() {
        val viewModel = object : ProfileViewModel() {
            private val _uiState = MutableStateFlow(
                ProfileUiState(
                    currentProfile = UserProfile(),
                    activeSection = ProfileSection.CORE,
                    isSaving = false,
                    saveSuccess = true,
                    errorMessage = null
                )
            )

            override val uiState = _uiState
            override fun updateProfile(profile: UserProfile) {}
            override fun setActiveSection(section: ProfileSection) {}
            override fun saveProfile() {}
            override fun clearError() {}
            override fun loadTier0Profile() {}
            override fun loadDemoProfile() {}
            override fun clearProfile() {}
        }

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify success message is displayed
        composeTestRule.onNodeWithText("Profile saved successfully", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun profileScreen_errorState_displaysErrorMessage() {
        val viewModel = object : ProfileViewModel() {
            private val _uiState = MutableStateFlow(
                ProfileUiState(
                    currentProfile = UserProfile(),
                    activeSection = ProfileSection.CORE,
                    isSaving = false,
                    saveSuccess = false,
                    errorMessage = "Failed to save profile"
                )
            )

            override val uiState = _uiState
            override fun updateProfile(profile: UserProfile) {}
            override fun setActiveSection(section: ProfileSection) {}
            override fun saveProfile() {}
            override fun clearError() {}
            override fun loadTier0Profile() {}
            override fun loadDemoProfile() {}
            override fun clearProfile() {}
        }

        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify error message is displayed
        composeTestRule.onNodeWithText("Failed to save profile").assertIsDisplayed()
    }

    @Test
    fun profileScreen_contentIsScrollable() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify the content is scrollable (LazyColumn has scroll action)
        composeTestRule.onNode(hasScrollAction()).assertExists()
    }

    @Test
    fun profileScreen_placeholderTexts_provideGuidance() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify placeholder texts are helpful (e.g., examples)
        composeTestRule.onNode(
            hasText("e.g.,", substring = true)
        ).assertExists()
    }

    @Test
    fun profileScreen_hasAccessibilityLabels() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Verify key interactive elements have proper semantics
        composeTestRule.onNodeWithContentDescription("Navigate back").assertExists()
        composeTestRule.onAllNodes(hasSetTextAction()).assertCountAtLeast(3)
    }

    @Test
    fun profileScreen_supportingTexts_provideContext() {
        composeTestRule.setContent {
            ProfileScreen(
                profileId = null,
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify supporting texts explain why fields are needed
        composeTestRule.onNode(
            hasText("numerology", substring = true, ignoreCase = true)
        ).assertExists()

        composeTestRule.onNode(
            hasText("astrology", substring = true, ignoreCase = true)
        ).assertExists()
    }
}
