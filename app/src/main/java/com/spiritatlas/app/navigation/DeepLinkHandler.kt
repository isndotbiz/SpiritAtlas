package com.spiritatlas.app.navigation

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import timber.log.Timber

/**
 * Lightweight deep link handler for SpiritAtlas.
 *
 * Supports both app links (`https://spiritualatlas.app/...`) and custom scheme
 * links (`spiritualatlas://...`) to route into the Compose navigation graph.
 */
object DeepLinkHandler {
    private const val DEEP_LINK_SCHEME = "spiritualatlas"
    private const val APP_LINK_HOST = "spiritualatlas.app"

    fun handleDeepLink(
        intent: Intent?,
        navController: NavController,
        analytics: NavigationAnalytics? = null
    ): Boolean {
        val uri = intent?.data ?: return false
        return handleUri(uri, navController, analytics)
    }

    fun createDeepLinkUri(screen: Screen, vararg params: Pair<String, String>): Uri {
        return Uri.parse("$DEEP_LINK_SCHEME://${buildPath(screen, params)}")
    }

    fun createAppLinkUri(screen: Screen, vararg params: Pair<String, String>): Uri {
        return Uri.parse("https://$APP_LINK_HOST/${buildPath(screen, params)}")
    }

    private fun handleUri(
        uri: Uri,
        navController: NavController,
        analytics: NavigationAnalytics?
    ): Boolean {
        val schemeSupported = uri.scheme == DEEP_LINK_SCHEME ||
            (uri.scheme == "https" && uri.host == APP_LINK_HOST)

        if (!schemeSupported) {
            Timber.w("Unsupported deep link: $uri")
            return false
        }

        val segments = uri.pathSegments
        val first = segments.getOrNull(0)

        val handled = when (first) {
            null, "", "home" -> {
                navController.navigate(Screen.Home.route)
                true
            }
            "onboarding" -> {
                navController.navigate(Screen.Onboarding.route)
                true
            }
            "profile", "profiles" -> {
                val profileId = segments.getOrNull(1)
                val route = if (profileId.isNullOrBlank()) {
                    Screen.Profile.createRoute(null)
                } else {
                    Screen.Profile.createRoute(profileId)
                }
                navController.navigate(route)
                true
            }
            "profile_comparison", "comparison" -> {
                val firstId = segments.getOrNull(1)
                val secondId = segments.getOrNull(2)
                if (firstId.isNullOrBlank() || secondId.isNullOrBlank()) {
                    false
                } else {
                    navController.navigate(Screen.ProfileComparison.createRoute(firstId, secondId))
                    true
                }
            }
            "enrichment", "enrichment_result" -> {
                val profileId = segments.getOrNull(1)
                if (profileId.isNullOrBlank()) {
                    false
                } else {
                    navController.navigate(Screen.EnrichmentResult.createRoute(profileId))
                    true
                }
            }
            "consent" -> {
                navController.navigate(Screen.Consent.route)
                true
            }
            "compatibility" -> {
                navController.navigate(Screen.Compatibility.route)
                true
            }
            else -> false
        }

        if (handled) {
            analytics?.trackDeepLink(uri.toString())
        }

        return handled
    }

    private fun buildPath(screen: Screen, params: Array<out Pair<String, String>>): String {
        return when (screen) {
            Screen.Home -> "home"
            Screen.Onboarding -> "onboarding"
            Screen.ProfileList -> "profiles"
            is Screen.Profile -> {
                val id = params.firstOrNull { it.first == "profileId" }?.second
                "profile/${id ?: "new"}"
            }
            is Screen.ProfileComparison -> {
                val firstId = params.firstOrNull { it.first == "profileId1" }?.second ?: ""
                val secondId = params.firstOrNull { it.first == "profileId2" }?.second ?: ""
                "comparison/$firstId/$secondId"
            }
            is Screen.EnrichmentResult -> {
                val profileId = params.firstOrNull { it.first == "profileId" }?.second ?: ""
                "enrichment/$profileId"
            }
            Screen.Consent -> "consent"
            Screen.Compatibility -> "compatibility"
            else -> "home"
        }
    }
}
