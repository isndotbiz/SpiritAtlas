package com.spiritatlas.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.spiritatlas.app.navigation.*
import com.spiritatlas.core.ui.haptics.HapticFeedbackController
import com.spiritatlas.core.ui.theme.SpiritAtlasTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Main activity for SpiritAtlas with enhanced navigation features:
 * - Deep link support (spiritualatlas:// and https://spiritualatlas.app)
 * - Navigation analytics tracking
 * - Haptic feedback integration
 * - Gesture navigation support
 * - Lifecycle-aware back stack management
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var analytics: NavigationAnalytics
    private lateinit var hapticController: HapticFeedbackController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize analytics and haptics
        analytics = NavigationAnalytics(this)
        hapticController = HapticFeedbackController(this)

        Timber.d("MainActivity created")

        setContent {
            SpiritAtlasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpiritAtlasNavigation(
                        initialIntent = intent,
                        analytics = analytics,
                        hapticController = hapticController
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle deep links when app is already running
        Timber.d("New intent received: ${intent.data}")
        setIntent(intent)
    }

    override fun onPause() {
        super.onPause()
        // Track potential drop-off point
        lifecycleScope.launch {
            analytics.getSessionSummary().navigationPath.lastOrNull()?.let { lastScreen ->
                analytics.trackDropOff(lastScreen)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Log analytics summary on app background
        Timber.d("Session summary:\n${analytics.exportAnalytics()}")
    }
}

@Composable
fun SpiritAtlasNavigation(
    initialIntent: Intent? = null,
    analytics: NavigationAnalytics,
    hapticController: HapticFeedbackController
) {
    val navController = rememberNavController()

    // Set up analytics tracking for all navigation events
    LaunchedEffect(navController) {
        navController.setupAnalytics(analytics)
    }

    // Handle deep links on initial launch
    LaunchedEffect(initialIntent) {
        if (initialIntent != null) {
            val handled = DeepLinkHandler.handleDeepLink(
                intent = initialIntent,
                navController = navController,
                analytics = analytics
            )

            if (handled) {
                Timber.i("Deep link handled successfully: ${initialIntent.data}")
            } else if (initialIntent.data != null) {
                Timber.w("Failed to handle deep link: ${initialIntent.data}")
                analytics.trackNavigationError(
                    errorType = "deep_link_failed",
                    details = "URI: ${initialIntent.data}"
                )
            }
        }
    }

    // Main navigation graph
    SpiritAtlasNavGraph(navController = navController)
}


