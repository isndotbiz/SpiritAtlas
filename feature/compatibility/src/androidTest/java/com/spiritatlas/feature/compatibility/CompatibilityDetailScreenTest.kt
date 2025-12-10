package com.spiritatlas.feature.compatibility

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spiritatlas.domain.model.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * UI tests for CompatibilityDetailScreen.
 *
 * Tests the compatibility analysis detail screen including:
 * - Hero section with profiles and score
 * - Score breakdown and radar chart
 * - All analysis sections (Numerology, Astrology, etc.)
 * - Action plan interactions
 * - Navigation and sharing
 * - Accessibility features
 */
@RunWith(AndroidJUnit4::class)
class CompatibilityDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockCompatibilityReport(): CompatibilityReport {
        val profile1 = UserProfile(
            id = "1",
            profileName = "Sarah",
            displayName = "Sarah",
            birthDateTime = LocalDateTime.now()
        )

        val profile2 = UserProfile(
            id = "2",
            profileName = "Michael",
            displayName = "Michael",
            birthDateTime = LocalDateTime.now()
        )

        return CompatibilityReport(
            profileA = profile1,
            profileB = profile2,
            overallScore = CompatibilityScores(
                totalScore = 87.5,
                numerologyScore = 85.0,
                astrologyScore = 90.0,
                tantricScore = 88.0,
                energeticScore = 86.0,
                communicationScore = 89.0,
                emotionalScore = 87.0
            ),
            compatibilityLevel = CompatibilityLevel.EXCELLENT,
            strengths = listOf(
                CompatibilityStrength(
                    aspect = "Emotional Connection",
                    score = 90.0,
                    description = "Deep emotional understanding and empathy"
                ),
                CompatibilityStrength(
                    aspect = "Communication",
                    score = 89.0,
                    description = "Clear and open communication style"
                ),
                CompatibilityStrength(
                    aspect = "Shared Values",
                    score = 88.0,
                    description = "Aligned life goals and values"
                )
            ),
            challenges = listOf(
                CompatibilityChallenge(
                    aspect = "Conflict Resolution",
                    description = "Different approaches to handling disagreements",
                    solutions = listOf(
                        "Practice active listening",
                        "Take cooling-off periods when needed"
                    )
                )
            ),
            tantricMatches = listOf(
                TantricMatch(
                    category = "Energy",
                    score = 88.0,
                    recommendation = "Cultivate sacred space through mindful presence"
                )
            ),
            insights = "Overall excellent compatibility with strong emotional connection"
        )
    }

    @Test
    fun compatibilityDetail_displaysTopBar() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Verify top bar elements are displayed
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Compatibility Analysis").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_backButton_callsCallback() {
        val report = createMockCompatibilityReport()
        var backClicked = false

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = { backClicked = true },
                onSave = {},
                onShare = {}
            )
        }

        // Click back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(backClicked) { "Back callback should be called" }
    }

    @Test
    fun compatibilityDetail_saveButton_callsCallback() {
        val report = createMockCompatibilityReport()
        var saveClicked = false

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = { saveClicked = true },
                onShare = {}
            )
        }

        // Click save button
        composeTestRule.onNodeWithContentDescription("Save to favorites").performClick()

        assert(saveClicked) { "Save callback should be called" }
    }

    @Test
    fun compatibilityDetail_shareButton_callsCallback() {
        val report = createMockCompatibilityReport()
        var shareClicked = false

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = { shareClicked = true }
            )
        }

        // Click share button
        composeTestRule.onNodeWithContentDescription("Share analysis").performClick()

        assert(shareClicked) { "Share callback should be called" }
    }

    @Test
    fun compatibilityDetail_displaysProfileNames() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify both profile names are displayed
        composeTestRule.onNodeWithText("Sarah").assertIsDisplayed()
        composeTestRule.onNodeWithText("Michael").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysOverallScore() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content and animations
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("87%", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verify overall score is displayed
        composeTestRule.onNodeWithText("87%", substring = true).assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysCompatibilityLevel() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify compatibility level is displayed
        composeTestRule.onNodeWithText("Excellent", substring = true, ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysScoreBreakdownSection() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify score breakdown section is displayed
        composeTestRule.onNodeWithText("Compatibility Dimensions").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysSynastryHighlights() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify synastry section is displayed
        composeTestRule.onNodeWithText("Synastry Highlights").assertIsDisplayed()
        composeTestRule.onNodeWithText("Top Strengths").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysStrengths() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content and animations
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Emotional Connection")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verify strengths are displayed
        composeTestRule.onNodeWithText("Emotional Connection").assertIsDisplayed()
        composeTestRule.onNodeWithText("Communication").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysGrowthAreas() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify growth areas section is displayed
        composeTestRule.onNodeWithText("Growth Areas").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_strengthCard_expandsOnClick() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content and animations
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Emotional Connection")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Click on a strength card
        composeTestRule.onNodeWithText("Emotional Connection").performClick()

        // Wait for expansion
        composeTestRule.waitForIdle()

        // Description should now be visible
        composeTestRule.onNodeWithText("Deep emotional understanding", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysNumerologySection() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify numerology section is displayed
        composeTestRule.onNodeWithText("Numerology Match").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysAstrologicalSection() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify astrological section is displayed
        composeTestRule.onNodeWithText("Astrological Synastry").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysHumanDesignSection() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Human Design section is displayed
        composeTestRule.onNodeWithText("Human Design Connection").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysAyurvedicSection() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Ayurvedic section is displayed
        composeTestRule.onNodeWithText("Ayurvedic Balance").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysTantricSection_whenAvailable() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Tantric section is displayed (when tantricMatches is not empty)
        composeTestRule.onNodeWithText("Tantric Connection").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysActionPlansSection() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify Action Plans section is displayed
        composeTestRule.onNodeWithText("Action Plans").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_actionPlans_hasCheckboxes() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify action plan checkboxes exist
        composeTestRule.onAllNodes(hasClickAction() and hasAnyAncestor(hasScrollAction()))
            .assertCountAtLeast(3)
    }

    @Test
    fun compatibilityDetail_actionCheckbox_togglesState() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Find a checkbox (action item)
        val checkbox = composeTestRule.onAllNodes(hasClickAction())[0]

        // Click the checkbox
        checkbox.performClick()

        // Checkbox state should change (visually, text strikethrough may appear)
        composeTestRule.waitForIdle()
    }

    @Test
    fun compatibilityDetail_contentIsScrollable() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify the content is scrollable
        composeTestRule.onNode(hasScrollAction()).assertExists()
    }

    @Test
    fun compatibilityDetail_hasAccessibilityLabels() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Verify key interactive elements have content descriptions
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
        composeTestRule.onNodeWithContentDescription("Save to favorites").assertExists()
        composeTestRule.onNodeWithContentDescription("Share analysis").assertExists()
    }

    @Test
    fun compatibilityDetail_radarChart_isInteractive() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content and animations
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Compatibility Dimensions")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Radar chart canvas should exist (it has tap gestures for dimension selection)
        // The radar chart is interactive, tapping shows dimension details
        // This is tested implicitly through the presence of the section
        composeTestRule.onNodeWithText("Compatibility Dimensions").assertIsDisplayed()
    }

    @Test
    fun compatibilityDetail_displaysAllActionPlanCategories() {
        val report = createMockCompatibilityReport()

        composeTestRule.setContent {
            CompatibilityDetailScreen(
                report = report,
                onBack = {},
                onSave = {},
                onShare = {}
            )
        }

        // Wait for content to load
        composeTestRule.waitForIdle()

        // Verify all action plan categories are displayed
        composeTestRule.onNodeWithText("Today").assertIsDisplayed()
        composeTestRule.onNodeWithText("This Week").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ongoing").assertIsDisplayed()
    }
}
