package com.spiritatlas.app.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument

/**
 * Example usage patterns for spiritual navigation transitions.
 *
 * This file demonstrates various ways to use the transition system,
 * from simple to advanced patterns.
 */

// ============================================================================
// EXAMPLE 1: Basic Usage - Using predefined transition types
// ============================================================================

@Composable
fun ExampleBasicTransitions(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Simple fade through transition
        spiritualComposable(
            route = "home",
            transitionType = TransitionType.FADE_THROUGH
        ) {
            // Home screen content
        }

        // Profile detail with shared element feel
        spiritualComposable(
            route = "profile/{id}",
            transitionType = TransitionType.SHARED_ELEMENT,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            // Profile screen content
        }

        // Modal with slide up
        spiritualComposable(
            route = "settings",
            transitionType = TransitionType.SLIDE_UP
        ) {
            // Settings screen content
        }
    }
}

// ============================================================================
// EXAMPLE 2: Custom Transitions - Mixing predefined with custom animations
// ============================================================================

@Composable
fun ExampleCustomTransitions(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Custom enter/exit with full control
        spiritualComposable(
            route = "home",
            enterTransition = fadeIn(tween(SpiritDurations.MEDIUM)),
            exitTransition = fadeOut(tween(SpiritDurations.MEDIUM)),
            popEnterTransition = fadeIn(tween(SpiritDurations.FAST)),
            popExitTransition = fadeOut(tween(SpiritDurations.FAST))
        ) {
            // Home screen with custom timing
        }

        // Combining multiple animation primitives
        spiritualComposable(
            route = "detail",
            enterTransition = scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(SpiritDurations.SLOW)
            ) + fadeIn(tween(SpiritDurations.SLOW)),
            exitTransition = scaleOut(
                targetScale = 1.2f,
                animationSpec = tween(SpiritDurations.SLOW)
            ) + fadeOut(tween(SpiritDurations.SLOW))
        ) {
            // Detail screen with custom scale + fade
        }
    }
}

// ============================================================================
// EXAMPLE 3: NavController Extensions - Semantic navigation methods
// ============================================================================

fun navigationExamples(navController: NavHostController) {
    // Navigate with default fade through
    navController.navigateWithTransition("profile/123")

    // Navigate with specific transition type
    navController.navigateWithTransition(
        route = "profile/123",
        transitionType = TransitionType.ZOOM_IN
    )

    // Navigate to detail screen (zoom in by default)
    navController.navigateToDetail("details/456")

    // Navigate to modal (slide up by default)
    navController.navigateToModal("settings")

    // Navigate with dramatic revelation (circular reveal)
    navController.navigateToRevelation("compatibility_result")

    // Top-level navigation with state saving (for tabs)
    navController.navigateToTopLevel("home")
    navController.navigateToTopLevel("explore")
    navController.navigateToTopLevel("profile")

    // Custom navigation with builder options
    navController.navigateWithTransition(
        route = "profile/123",
        transitionType = TransitionType.SLIDE_HORIZONTAL
    ) {
        // Avoid multiple instances
        launchSingleTop = true
        // Save state for back navigation
        popUpTo("home") {
            saveState = true
        }
    }
}

// ============================================================================
// EXAMPLE 4: Context-Aware Transitions - Different transitions based on state
// ============================================================================

@Composable
fun ExampleContextAwareTransitions(
    navController: NavHostController,
    isFirstLaunch: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        // Onboarding with different transition on first launch
        spiritualComposable(
            route = "onboarding",
            transitionType = if (isFirstLaunch) {
                TransitionType.CIRCULAR_REVEAL // Dramatic first impression
            } else {
                TransitionType.FADE_THROUGH // Standard return
            }
        ) {
            // Onboarding content
        }
    }
}

// ============================================================================
// EXAMPLE 5: Transition Chains - Multiple screens with coordinated animations
// ============================================================================

fun navigationChainExample(navController: NavHostController) {
    // Profile selection flow
    navController.navigateWithTransition(
        route = "profile_list",
        transitionType = TransitionType.FADE_THROUGH
    )

    // User selects a profile -> zoom in for focus
    navController.navigateToDetail(
        route = "profile/123",
        transitionType = TransitionType.ZOOM_IN
    )

    // View compatibility -> dramatic reveal
    navController.navigateToRevelation(
        route = "compatibility/123/456"
    )

    // Open detailed analysis -> slide horizontal for comparison feel
    navController.navigateWithTransition(
        route = "analysis/123/456",
        transitionType = TransitionType.SLIDE_HORIZONTAL
    )
}

// ============================================================================
// EXAMPLE 6: Performance Optimization - Disabling animations when needed
// ============================================================================

@Composable
fun ExamplePerformanceOptimization(
    navController: NavHostController,
    reduceMotion: Boolean // From system accessibility settings
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        spiritualComposable(
            route = "home",
            transitionType = if (reduceMotion) {
                TransitionType.NONE // Instant transition for accessibility
            } else {
                TransitionType.FADE_THROUGH // Beautiful animation
            }
        ) {
            // Home screen
        }

        // Heavy screen - use faster transition
        spiritualComposable(
            route = "heavy_content",
            transitionType = if (reduceMotion) {
                TransitionType.NONE
            } else {
                TransitionType.CROSS_FADE // Simpler animation
            }
        ) {
            // Screen with complex content
        }
    }
}

// ============================================================================
// EXAMPLE 7: SpiritTransitions Object - Using predefined app-specific configs
// ============================================================================

@Composable
fun ExampleAppSpecificTransitions(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        spiritualComposable(
            route = "home",
            transitionType = SpiritTransitions.default
        ) {
            // Home screen
        }

        spiritualComposable(
            route = "profile_list",
            transitionType = SpiritTransitions.homeToFeature
        ) {
            // Profile list
        }

        spiritualComposable(
            route = "profile/{id}",
            transitionType = SpiritTransitions.profileListToDetail,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            // Profile detail
        }

        spiritualComposable(
            route = "compatibility_result",
            transitionType = SpiritTransitions.toCompatibilityResults
        ) {
            // Compatibility results with dramatic reveal
        }

        spiritualComposable(
            route = "consent",
            transitionType = SpiritTransitions.toModal
        ) {
            // Consent modal
        }
    }
}

// ============================================================================
// EXAMPLE 8: Nested Navigation - Different transitions for different graphs
// ============================================================================

@Composable
fun ExampleNestedNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        // Main app flow - smooth peer transitions
        spiritualComposable(
            route = "main",
            transitionType = TransitionType.FADE_THROUGH
        ) {
            // Main content
        }

        // Settings flow - modal presentation
        spiritualComposable(
            route = "settings_graph",
            transitionType = TransitionType.SLIDE_UP
        ) {
            // Settings nav host with its own transitions
        }

        // Onboarding flow - dramatic reveals
        spiritualComposable(
            route = "onboarding_graph",
            transitionType = TransitionType.CIRCULAR_REVEAL
        ) {
            // Onboarding nav host
        }
    }
}

// ============================================================================
// EXAMPLE 9: Deep Link Handling - Appropriate transitions for deep links
// ============================================================================

fun handleDeepLink(
    navController: NavHostController,
    deepLinkRoute: String,
    isDirectEntry: Boolean
) {
    val transitionType = if (isDirectEntry) {
        // Coming from outside the app - use gentler transition
        TransitionType.FADE_THROUGH
    } else {
        // Internal navigation - use contextual transition
        when {
            deepLinkRoute.contains("profile") -> TransitionType.ZOOM_IN
            deepLinkRoute.contains("compatibility") -> TransitionType.CIRCULAR_REVEAL
            deepLinkRoute.contains("comparison") -> TransitionType.SLIDE_HORIZONTAL
            else -> TransitionType.FADE_THROUGH
        }
    }

    navController.navigateWithTransition(
        route = deepLinkRoute,
        transitionType = transitionType
    )
}

// ============================================================================
// EXAMPLE 10: Animation Duration Customization - Adjust for different screens
// ============================================================================

object CustomDurations {
    // Quick transitions for simple screens
    const val LIGHT_SCREEN = SpiritDurations.FAST

    // Standard transitions for most screens
    const val NORMAL_SCREEN = SpiritDurations.MEDIUM

    // Slower transitions for complex or important screens
    const val HEAVY_SCREEN = SpiritDurations.SLOW

    // Extra slow for dramatic moments
    const val REVELATION = SpiritDurations.MYSTICAL
}

/**
 * Summary of best practices:
 *
 * 1. Use predefined SpiritTransitions for consistency
 * 2. Match transition to the semantic meaning of navigation
 * 3. Use longer durations (400-600ms) for spiritual, mystical feel
 * 4. Combine scale + fade for depth and dimension
 * 5. Use circular reveal sparingly for dramatic moments
 * 6. Consider accessibility - provide option to disable animations
 * 7. Use CROSS_FADE for tab navigation (no spatial displacement)
 * 8. Use SLIDE_UP for modals and bottom sheets
 * 9. Use ZOOM_IN when focusing on specific content
 * 10. Keep performance in mind - simpler transitions for heavy screens
 *
 * Spiritual Design Principles:
 * - Transitions should feel flowing, not jarring
 * - Longer durations create contemplative, mindful experience
 * - Scale + fade creates sense of consciousness expanding/contracting
 * - Circular reveal for moments of enlightenment or discovery
 * - Smooth curves (FastOutSlowIn) mirror natural energy flow
 */
