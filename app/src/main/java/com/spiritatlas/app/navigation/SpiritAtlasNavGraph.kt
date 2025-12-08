package com.spiritatlas.app.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.spiritatlas.app.NavigationEvent
import com.spiritatlas.app.SplashScreen
import com.spiritatlas.data.worker.EnrichmentWorker
import com.spiritatlas.feature.compatibility.CompatibilityScreen
import com.spiritatlas.feature.consent.ConsentScreen
import com.spiritatlas.feature.home.HomeScreen
import com.spiritatlas.feature.profile.EnrichmentResultScreen
import com.spiritatlas.feature.profile.ProfileComparisonScreen
import com.spiritatlas.feature.profile.ProfileListScreen
import com.spiritatlas.feature.profile.ProfileScreen

/**
 * Main navigation graph for SpiritAtlas with beautiful spiritual transitions.
 * Each destination is configured with appropriate transitions that match its purpose.
 */
@Composable
fun SpiritAtlasNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash screen - Beautiful animated entry
        spiritualComposable(
            route = Screen.Splash.route,
            transitionType = SpiritTransitions.homeToFeature
        ) {
            SplashScreen(navController = navController)
        }

        // Home screen - Entry point with fade through transition
        spiritualComposable(
            route = Screen.Home.route,
            transitionType = SpiritTransitions.homeToFeature
        ) {
            HomeScreen(
                onNavigateToProfile = {
                    navController.navigateWithTransition(
                        route = Screen.ProfileList.route,
                        transitionType = SpiritTransitions.homeToFeature
                    )
                },
                onNavigateToConsent = {
                    navController.navigateToModal(
                        route = Screen.Consent.route,
                        transitionType = SpiritTransitions.toModal
                    )
                },
                onNavigateToCompatibility = {
                    navController.navigateWithTransition(
                        route = Screen.Compatibility.route,
                        transitionType = SpiritTransitions.homeToFeature
                    )
                }
            )
        }

        // Profile list screen - Shows all profiles with fade through
        spiritualComposable(
            route = Screen.ProfileList.route,
            transitionType = SpiritTransitions.homeToFeature
        ) {
            ProfileListScreen(
                onNavigateToProfile = { profileId ->
                    navController.navigateToDetail(
                        route = Screen.Profile.createRoute(profileId),
                        transitionType = SpiritTransitions.profileListToDetail
                    )
                },
                onNavigateToComparison = { profileId1, profileId2 ->
                    navController.navigateWithTransition(
                        route = Screen.ProfileComparison.createRoute(profileId1, profileId2),
                        transitionType = SpiritTransitions.profileToComparison
                    )
                },
                onNavigateToCompatibility = {
                    navController.navigateWithTransition(
                        route = Screen.Compatibility.route,
                        transitionType = SpiritTransitions.homeToFeature
                    )
                },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEnrichment = { profileId ->
                    navController.navigateToDetail(
                        route = Screen.EnrichmentResult.createRoute(profileId),
                        transitionType = SpiritTransitions.toEnrichmentResults
                    )
                }
            )
        }

        // Profile detail screen - Shared element-style transition from list
        spiritualComposable(
            route = Screen.Profile.route,
            transitionType = SpiritTransitions.profileListToDetail,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId")
            ProfileScreen(
                profileId = profileId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Profile comparison screen - Horizontal slide (comparing side by side)
        spiritualComposable(
            route = Screen.ProfileComparison.route,
            transitionType = SpiritTransitions.profileToComparison,
            arguments = listOf(
                navArgument("profileId1") { type = NavType.StringType },
                navArgument("profileId2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val profileId1 = backStackEntry.arguments?.getString("profileId1") ?: ""
            val profileId2 = backStackEntry.arguments?.getString("profileId2") ?: ""
            ProfileComparisonScreen(
                profileId1 = profileId1,
                profileId2 = profileId2,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Enrichment result screen - Zoom in to focus on spiritual insights
        spiritualComposable(
            route = Screen.EnrichmentResult.route,
            transitionType = SpiritTransitions.toEnrichmentResults,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId") ?: ""
            EnrichmentResultScreen(
                profileId = profileId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Consent screen - Slide up modal for important decisions
        spiritualComposable(
            route = Screen.Consent.route,
            transitionType = SpiritTransitions.toModal
        ) {
            ConsentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Compatibility screen - Circular reveal for dramatic results
        spiritualComposable(
            route = Screen.Compatibility.route,
            transitionType = SpiritTransitions.toCompatibilityResults
        ) {
            CompatibilityScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
