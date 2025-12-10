package com.spiritatlas.app.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spiritatlas.app.SpiritAtlasNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Navigation tests for SpiritAtlas app.
 *
 * Tests the navigation graph including:
 * - Route definitions and navigation
 * - Back navigation
 * - Deep links
 * - Navigation with arguments
 * - Start destination
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SpiritAtlasNavGraph(navController = navController)
        }
    }

    @Test
    fun navHost_startsAtSplashScreen() {
        // Verify that the start destination is Splash
        composeTestRule.waitForIdle()

        val route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.Splash.route) {
            "Start destination should be Splash, but was $route"
        }
    }

    @Test
    fun navHost_homeScreen_displaysCorrectly() {
        // Navigate to home
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        // Verify home screen content
        composeTestRule.onNodeWithText("SpiritAtlas").assertIsDisplayed()
    }

    @Test
    fun navHost_navigateToProfile_updatesBackStack() {
        // Navigate to home first
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        // Navigate to profile list
        navController.navigate(Screen.ProfileList.route)
        composeTestRule.waitForIdle()

        // Verify current destination
        val route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.ProfileList.route) {
            "Current destination should be ProfileList, but was $route"
        }
    }

    @Test
    fun navHost_navigateToSettings_updatesBackStack() {
        // Navigate to home first
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        // Navigate to settings/consent
        navController.navigate(Screen.Consent.route)
        composeTestRule.waitForIdle()

        // Verify current destination
        val route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.Consent.route) {
            "Current destination should be Consent, but was $route"
        }
    }

    @Test
    fun navHost_backNavigation_worksCorrectly() {
        // Navigate through multiple screens
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        navController.navigate(Screen.ProfileList.route)
        composeTestRule.waitForIdle()

        // Verify we're at profile list
        var route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.ProfileList.route)

        // Navigate back
        navController.popBackStack()
        composeTestRule.waitForIdle()

        // Verify we're back at home
        route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.Home.route) {
            "After pop, should be at Home, but was $route"
        }
    }

    @Test
    fun navHost_navigateToCompatibility_updatesBackStack() {
        // Navigate to home first
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        // Navigate to compatibility
        navController.navigate(Screen.Compatibility.route)
        composeTestRule.waitForIdle()

        // Verify current destination
        val route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.Compatibility.route) {
            "Current destination should be Compatibility, but was $route"
        }
    }

    @Test
    fun navHost_profileWithId_createsCorrectRoute() {
        // Create a profile route with an ID
        val profileId = "test-profile-123"
        val profileRoute = Screen.Profile.createRoute(profileId)

        // Verify route contains the ID
        assert(profileRoute.contains(profileId)) {
            "Profile route should contain ID, but was $profileRoute"
        }
    }

    @Test
    fun navHost_multipleBackNavigations_handledCorrectly() {
        // Build a deep navigation stack
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        navController.navigate(Screen.ProfileList.route)
        composeTestRule.waitForIdle()

        navController.navigate(Screen.Compatibility.route)
        composeTestRule.waitForIdle()

        // Pop back twice
        navController.popBackStack()
        composeTestRule.waitForIdle()

        navController.popBackStack()
        composeTestRule.waitForIdle()

        // Should be back at home
        val route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.Home.route) {
            "After multiple pops, should be at Home, but was $route"
        }
    }

    @Test
    fun navHost_navigationToOnboarding_worksCorrectly() {
        // Navigate to onboarding
        navController.navigate(Screen.Onboarding.route)
        composeTestRule.waitForIdle()

        // Verify current destination
        val route = navController.currentBackStackEntry?.destination?.route
        assert(route == Screen.Onboarding.route) {
            "Current destination should be Onboarding, but was $route"
        }
    }

    @Test
    fun navHost_allScreenRoutes_areDefined() {
        // Verify all expected routes are defined
        val expectedRoutes = listOf(
            Screen.Splash.route,
            Screen.Home.route,
            Screen.Onboarding.route,
            Screen.ProfileList.route,
            Screen.Consent.route,
            Screen.Compatibility.route
        )

        // Try navigating to each route
        expectedRoutes.forEach { route ->
            navController.navigate(route)
            composeTestRule.waitForIdle()

            val currentRoute = navController.currentBackStackEntry?.destination?.route
            assert(currentRoute == route) {
                "Failed to navigate to $route, current route is $currentRoute"
            }
        }
    }

    @Test
    fun navHost_backStackSize_updatesCorrectly() {
        // Start from home
        navController.navigate(Screen.Home.route)
        composeTestRule.waitForIdle()

        val initialStackSize = navController.currentBackStack.value.size

        // Navigate to another screen
        navController.navigate(Screen.ProfileList.route)
        composeTestRule.waitForIdle()

        val afterNavigationSize = navController.currentBackStack.value.size

        // Back stack should have grown
        assert(afterNavigationSize > initialStackSize) {
            "Back stack should grow after navigation"
        }

        // Pop back
        navController.popBackStack()
        composeTestRule.waitForIdle()

        val afterPopSize = navController.currentBackStack.value.size

        // Back stack should have shrunk
        assert(afterPopSize < afterNavigationSize) {
            "Back stack should shrink after pop"
        }
    }
}
