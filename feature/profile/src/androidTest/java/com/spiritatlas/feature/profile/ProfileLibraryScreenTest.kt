package com.spiritatlas.feature.profile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spiritatlas.domain.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * UI tests for ProfileLibraryScreen.
 *
 * Tests the profile library UI including:
 * - Profile list rendering
 * - Search functionality
 * - Selection for comparison
 * - Navigation actions
 * - Empty state
 * - Profile actions (share, delete, enrich)
 */
@RunWith(AndroidJUnit4::class)
class ProfileLibraryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockProfiles(): List<UserProfile> {
        return listOf(
            UserProfile(
                id = "1",
                profileName = "Sarah",
                displayName = "Sarah",
                birthDateTime = LocalDateTime.now()
            ),
            UserProfile(
                id = "2",
                profileName = "Michael",
                displayName = "Michael",
                birthDateTime = LocalDateTime.now()
            ),
            UserProfile(
                id = "3",
                profileName = "Luna",
                displayName = "Luna",
                birthDateTime = LocalDateTime.now()
            )
        )
    }

    private fun createMockViewModel(profiles: List<UserProfile> = createMockProfiles()): ProfileLibraryViewModel {
        return object : ProfileLibraryViewModel() {
            private val _profiles = MutableStateFlow(profiles)
            private val _uiState = MutableStateFlow(ProfileLibraryUiState())

            override val profiles = _profiles
            override val uiState = _uiState

            override fun shareProfile(profileId: String) {
                // Mock implementation
            }

            override fun enrichProfile(profileId: String) {
                // Mock implementation
            }

            override fun deleteProfile(profileId: String) {
                _profiles.value = _profiles.value.filter { it.id != profileId }
            }

            override fun importProfile() {
                // Mock implementation
            }

            override fun exportAllProfiles() {
                // Mock implementation
            }
        }
    }

    @Test
    fun profileLibrary_displaysBackButton() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Verify back button is displayed
        composeTestRule.onNodeWithContentDescription("Navigate back").assertIsDisplayed()
    }

    @Test
    fun profileLibrary_backButton_callsNavigationCallback() {
        var backPressed = false

        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = { backPressed = true },
                viewModel = createMockViewModel()
            )
        }

        // Click back button
        composeTestRule.onNodeWithContentDescription("Navigate back").performClick()

        assert(backPressed) { "Back navigation callback should be called" }
    }

    @Test
    fun profileLibrary_displaysAllProfiles() {
        val profiles = createMockProfiles()

        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel(profiles)
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify all profiles are displayed
        profiles.forEach { profile ->
            composeTestRule.onNodeWithText(profile.profileName).assertIsDisplayed()
        }
    }

    @Test
    fun profileLibrary_emptyState_displaysMessage() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel(emptyList())
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify empty state message is displayed
        composeTestRule.onNode(
            hasText("Create", substring = true) or hasText("empty", substring = true, ignoreCase = true)
        ).assertExists()
    }

    @Test
    fun profileLibrary_displaysQuickActions() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Quick actions should be visible
        composeTestRule.onNode(
            hasClickAction() and hasAnyAncestor(hasScrollAction())
        ).assertExists()
    }

    @Test
    fun profileLibrary_profileCard_callsNavigationOnClick() {
        var profileClicked = false
        var clickedProfileId = ""

        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = { id ->
                    profileClicked = true
                    clickedProfileId = id
                },
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click on a profile card
        composeTestRule.onNodeWithText("Sarah").performClick()

        assert(profileClicked) { "Profile navigation callback should be called" }
        assert(clickedProfileId.isNotEmpty()) { "Profile ID should be passed" }
    }

    @Test
    fun profileLibrary_searchButton_togglesSearchField() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Click search button
        composeTestRule.onNodeWithContentDescription("Search profiles").performClick()

        // Wait for animation
        composeTestRule.waitForIdle()

        // Search field should be visible
        composeTestRule.onNode(hasSetTextAction() and hasAnyAncestor(hasScrollAction().not()))
            .assertExists()
    }

    @Test
    fun profileLibrary_search_filtersProfiles() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Open search
        composeTestRule.onNodeWithContentDescription("Search profiles").performClick()
        composeTestRule.waitForIdle()

        // Type search query
        composeTestRule.onNode(hasSetTextAction() and hasAnyAncestor(hasScrollAction().not()))
            .performTextInput("Sarah")

        // Wait for filtering
        composeTestRule.waitForIdle()

        // Only Sarah should be visible
        composeTestRule.onNodeWithText("Sarah").assertIsDisplayed()

        // Other profiles should not be displayed (or filtered out)
        // Note: Depending on implementation, they might not exist or just not be displayed
        val michaelNodes = composeTestRule.onAllNodesWithText("Michael").fetchSemanticsNodes()
        assert(michaelNodes.isEmpty()) { "Michael should be filtered out" }
    }

    @Test
    fun profileLibrary_selectProfile_enablesComparisonMode() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Long press or click selection on a profile card
        // (Assuming there's a selection mode triggered by long press or button)
        val firstProfile = composeTestRule.onAllNodesWithText("Sarah")[0]
        firstProfile.performTouchInput { longClick() }

        // Wait for selection mode
        composeTestRule.waitForIdle()

        // Comparison UI should appear (clear selection button, compare button, etc.)
        composeTestRule.onNode(
            hasText("Clear", substring = true) or
            hasText("Compare", substring = true) or
            hasContentDescription("Clear selection")
        ).assertExists()
    }

    @Test
    fun profileLibrary_selectTwoProfiles_enablesCompareButton() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Select first profile (long press)
        composeTestRule.onAllNodesWithText("Sarah")[0]
            .performTouchInput { longClick() }

        composeTestRule.waitForIdle()

        // Select second profile (click)
        composeTestRule.onAllNodesWithText("Michael")[0].performClick()

        composeTestRule.waitForIdle()

        // Compare button should be enabled and visible
        composeTestRule.onNode(
            (hasText("Compare", substring = true) or hasContentDescription("Compare")) and isEnabled()
        ).assertExists()
    }

    @Test
    fun profileLibrary_compareButton_callsCompatibilityNavigation() {
        var compatibilityNavigated = false
        var profileId1 = ""
        var profileId2 = ""

        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { id1, id2 ->
                    compatibilityNavigated = true
                    profileId1 = id1
                    profileId2 = id2
                },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Select two profiles
        composeTestRule.onAllNodesWithText("Sarah")[0].performTouchInput { longClick() }
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Michael")[0].performClick()
        composeTestRule.waitForIdle()

        // Click compare button
        composeTestRule.onNode(
            (hasText("Compare", substring = true) or hasContentDescription("Compare")) and isEnabled()
        ).performClick()

        assert(compatibilityNavigated) { "Compatibility navigation should be called" }
        assert(profileId1.isNotEmpty() && profileId2.isNotEmpty()) { "Both profile IDs should be passed" }
    }

    @Test
    fun profileLibrary_contentIsScrollable() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify the content is scrollable
        composeTestRule.onNode(hasScrollAction()).assertExists()
    }

    @Test
    fun profileLibrary_profileCards_displayProfileInfo() {
        val profiles = createMockProfiles()

        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel(profiles)
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify profile names are displayed
        profiles.forEach { profile ->
            composeTestRule.onNodeWithText(profile.profileName).assertIsDisplayed()
        }
    }

    @Test
    fun profileLibrary_hasAccessibilityLabels() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Verify key interactive elements have proper accessibility
        composeTestRule.onNodeWithContentDescription("Navigate back").assertExists()
        composeTestRule.onNodeWithContentDescription("Search profiles").assertExists()
    }

    @Test
    fun profileLibrary_clearSelection_resetsSelectionMode() {
        composeTestRule.setContent {
            ProfileLibraryScreen(
                onNavigateToProfile = {},
                onNavigateToCompatibility = { _, _ -> },
                onNavigateBack = {},
                viewModel = createMockViewModel()
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Select a profile
        composeTestRule.onAllNodesWithText("Sarah")[0].performTouchInput { longClick() }
        composeTestRule.waitForIdle()

        // Clear selection button should appear
        val clearButton = composeTestRule.onNode(
            hasText("Clear", substring = true) or hasContentDescription("Clear selection")
        )
        clearButton.assertExists()
        clearButton.performClick()

        composeTestRule.waitForIdle()

        // Clear button should disappear (selection mode exited)
        composeTestRule.onNode(
            hasText("Clear", substring = true) or hasContentDescription("Clear selection")
        ).assertDoesNotExist()
    }
}
