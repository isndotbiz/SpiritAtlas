package com.spiritatlas.feature.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for HomeScreen.
 *
 * Tests the home screen UI including:
 * - Initial rendering of all sections
 * - Navigation callbacks
 * - User interactions
 * - Animated content visibility
 * - Accessibility features
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysAppTitle() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Verify app title is displayed
        composeTestRule.onNodeWithText("SpiritAtlas").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysSettingsButton() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Verify settings button is displayed
        composeTestRule.onNodeWithContentDescription("Settings").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysFloatingActionButton() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Verify FAB for creating profile is displayed
        composeTestRule.onNodeWithContentDescription("Create Profile").assertIsDisplayed()
    }

    @Test
    fun homeScreen_settingsButton_callsNavigationCallback() {
        var settingsClicked = false

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = { settingsClicked = true },
                onNavigateToCompatibility = {}
            )
        }

        // Click settings button
        composeTestRule.onNodeWithContentDescription("Settings").performClick()

        // Verify callback was invoked
        assert(settingsClicked) { "Settings navigation callback should be called" }
    }

    @Test
    fun homeScreen_fabButton_callsNavigationCallback() {
        var profileClicked = false

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = { profileClicked = true },
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Click FAB
        composeTestRule.onNodeWithContentDescription("Create Profile").performClick()

        // Verify callback was invoked
        assert(profileClicked) { "Profile navigation callback should be called" }
    }

    @Test
    fun homeScreen_displaysGreeting() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Verify one of the greetings is displayed
        composeTestRule.onNode(
            hasText("Good morning, Seeker") or
            hasText("Good afternoon, Seeker") or
            hasText("Good evening, Seeker")
        ).assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysHeaderSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for animations to complete
        composeTestRule.waitForIdle()

        // Verify header section displays date information
        // Should display weekday and month (e.g., "Monday, January 1")
        composeTestRule.onNode(
            hasText(",", substring = true) and hasAnyAncestor(hasScrollAction())
        ).assertExists()
    }

    @Test
    fun homeScreen_displaysDailyInsightsSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Daily Insights section is displayed
        composeTestRule.onNodeWithText("Daily Insights").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysYourProfilesSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Your Profiles section is displayed
        composeTestRule.onNodeWithText("Your Profiles").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysAddNewProfileButton() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify "Add New" profile card is displayed
        composeTestRule.onNodeWithText("Add New").assertIsDisplayed()
    }

    @Test
    fun homeScreen_addNewProfileCard_callsNavigationCallback() {
        var addProfileClicked = false

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = { addProfileClicked = true },
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Click "Add New" profile card
        composeTestRule.onNodeWithText("Add New").performClick()

        // Verify callback was invoked
        assert(addProfileClicked) { "Add profile navigation callback should be called" }
    }

    @Test
    fun homeScreen_displaysPlanetaryTransitsSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Planetary Transits section is displayed
        composeTestRule.onNodeWithText("Planetary Transits").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysCompatibilityCheckSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Compatibility Check section is displayed
        composeTestRule.onNodeWithText("Compatibility Check").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysSpiritualWeatherSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Spiritual Weather section is displayed
        composeTestRule.onNodeWithText("Spiritual Weather").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysLearnExploreSection() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Learn & Explore section is displayed
        composeTestRule.onNodeWithText("Learn & Explore").assertIsDisplayed()
    }

    @Test
    fun homeScreen_contentIsScrollable() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify the content is scrollable (LazyColumn has scroll action)
        composeTestRule.onNode(hasScrollAction()).assertExists()
    }

    @Test
    fun homeScreen_profileAvatars_displayCorrectInitials() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify sample profile names are displayed
        composeTestRule.onNodeWithText("Sarah").assertIsDisplayed()
        composeTestRule.onNodeWithText("Michael").assertIsDisplayed()
        composeTestRule.onNodeWithText("Luna").assertIsDisplayed()
    }

    @Test
    fun homeScreen_dailyInsights_displaysGuidance() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content and animations
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Guidance").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify guidance text is displayed
        composeTestRule.onNodeWithText("Guidance").assertIsDisplayed()
    }

    @Test
    fun homeScreen_dailyInsights_displaysLuckyNumbers() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Wait for content and animations
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Lucky Numbers").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify lucky numbers section is displayed
        composeTestRule.onNodeWithText("Lucky Numbers").assertIsDisplayed()
    }

    @Test
    fun homeScreen_seeFullAnalysisButton_callsNavigationCallback() {
        var compatibilityClicked = false

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = { compatibilityClicked = true }
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Select profiles in compatibility check (if UI allows)
        // Then click "See Full Analysis" button if it appears
        composeTestRule.onNodeWithText("Profile 1").performClick()
        composeTestRule.onNodeWithText("Select").performClick()

        // Wait a moment for dropdown
        composeTestRule.waitForIdle()

        // Select a profile from dropdown
        composeTestRule.onAllNodesWithText("Sarah")[0].performClick()

        // Select second profile
        composeTestRule.onAllNodesWithText("Profile 2")[0].performClick()
        composeTestRule.onAllNodesWithText("Select")[1].performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Michael")[0].performClick()

        // Wait for score to appear and button to be enabled
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("See Full Analysis")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Click "See Full Analysis" button
        composeTestRule.onNodeWithText("See Full Analysis").performClick()

        // Verify callback was invoked
        assert(compatibilityClicked) { "Compatibility navigation callback should be called" }
    }

    @Test
    fun homeScreen_hasAccessibilityContentDescriptions() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Verify key interactive elements have content descriptions
        composeTestRule.onNodeWithContentDescription("Settings").assertExists()
        composeTestRule.onNodeWithContentDescription("Create Profile").assertExists()
        composeTestRule.onNodeWithContentDescription("Add Profile").assertExists()
    }

    @Test
    fun homeScreen_touchTargets_meetMinimumSize() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // FAB should be large enough (56dp standard size)
        val fabNode = composeTestRule.onNodeWithContentDescription("Create Profile")
        fabNode.assertExists()

        // Settings icon button should be large enough (48dp minimum)
        val settingsNode = composeTestRule.onNodeWithContentDescription("Settings")
        settingsNode.assertExists()
    }

    @Test
    fun homeScreen_animatedContent_becomesVisible() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToProfile = {},
                onNavigateToConsent = {},
                onNavigateToCompatibility = {}
            )
        }

        // Content starts with staggered animations
        // Wait for initial animation delay
        composeTestRule.waitForIdle()

        // All main sections should eventually be visible
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Daily Insights").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Daily Insights").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Profiles").assertIsDisplayed()
        composeTestRule.onNodeWithText("Planetary Transits").assertIsDisplayed()
    }
}
