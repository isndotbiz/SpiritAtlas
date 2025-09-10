package com.spiritatlas.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import android.widget.Toast
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spiritatlas.app.navigation.Screen
import com.spiritatlas.core.ui.theme.SpiritAtlasTheme
import com.spiritatlas.data.worker.EnrichmentWorker
import com.spiritatlas.feature.consent.ConsentScreen
import com.spiritatlas.feature.home.HomeScreen
import com.spiritatlas.feature.profile.ProfileScreen
import com.spiritatlas.feature.profile.ProfileListScreen
import com.spiritatlas.feature.profile.ProfileComparisonScreen
import com.spiritatlas.feature.profile.EnrichmentResultScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpiritAtlasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpiritAtlasNavigation()
                }
            }
        }
    }
}

@Composable
fun SpiritAtlasNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Screen.ProfileList.route) },
                onNavigateToConsent = { navController.navigate(Screen.Consent.route) }
            )
        }
        
        composable(Screen.ProfileList.route) {
            ProfileListScreen(
                onNavigateToProfile = { profileId ->
                    navController.navigate(Screen.Profile.createRoute(profileId))
                },
                onNavigateToComparison = { profileId1, profileId2 ->
                    navController.navigate(Screen.ProfileComparison.createRoute(profileId1, profileId2))
                },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEnrichment = { profileId ->
                    navController.navigate(Screen.EnrichmentResult.createRoute(profileId))
                }
            )
        }
        
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId")
            ProfileScreen(
                profileId = profileId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.ProfileComparison.route,
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
        
        composable(
            route = Screen.EnrichmentResult.route,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId") ?: ""
            EnrichmentResultScreen(
                profileId = profileId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Consent.route) {
            ConsentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}


