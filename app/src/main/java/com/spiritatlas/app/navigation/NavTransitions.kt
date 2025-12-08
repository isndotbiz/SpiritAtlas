package com.spiritatlas.app.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

/**
 * Transition types for spiritual navigation flows.
 * Each transition type creates a unique feeling aligned with the spiritual nature of the app.
 */
enum class TransitionType {
    /**
     * Shared element transition for profile cards expanding to detail screens.
     * Creates a seamless morphing effect.
     */
    SHARED_ELEMENT,

    /**
     * Fade through pattern - Material Design motion for peer-level navigation.
     * One screen fades out, then the next fades in.
     */
    FADE_THROUGH,

    /**
     * Slide up from bottom - Perfect for bottom sheets and modal presentations.
     * Gives a sense of revealing hidden knowledge.
     */
    SLIDE_UP,

    /**
     * Slide horizontal - Traditional forward/back navigation.
     * Left slide for forward, right slide for back.
     */
    SLIDE_HORIZONTAL,

    /**
     * Zoom in - Detail views that expand from center.
     * Creates focus and draws attention to important content.
     */
    ZOOM_IN,

    /**
     * Cross fade - Smooth blending for tab switches.
     * Peaceful transition without spatial movement.
     */
    CROSS_FADE,

    /**
     * Circular reveal - Dramatic transition for significant moments.
     * Perfect for compatibility results and revelations.
     */
    CIRCULAR_REVEAL,

    /**
     * Scale with fade - Mystical floating effect.
     * Screen gently scales and fades, like consciousness expanding.
     */
    SCALE_FADE,

    /**
     * None - Instant transition with no animation.
     */
    NONE
}

/**
 * Spiritual animation durations - longer for mystical, flowing feel.
 */
object SpiritDurations {
    const val FAST = 300
    const val MEDIUM = 450
    const val SLOW = 600
    const val MYSTICAL = 800
}

/**
 * Custom easing curves for spiritual feel.
 */
object SpiritEasing {
    val Emphasized = FastOutSlowInEasing
    val Gentle = LinearEasing

    fun mysticalSpring() = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
}

/**
 * Shared element transition - morphing profile cards to detail screens.
 */
fun AnimatedContentTransitionScope<NavBackStackEntry>.sharedElementTransition(
    forward: Boolean = true
): ContentTransform {
    return if (forward) {
        // Entering detail screen
        scaleIn(
            initialScale = 0.85f,
            animationSpec = tween(
                durationMillis = SpiritDurations.MEDIUM,
                easing = SpiritEasing.Emphasized
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = SpiritDurations.MEDIUM,
                easing = SpiritEasing.Emphasized
            )
        ) togetherWith scaleOut(
            targetScale = 1.1f,
            animationSpec = tween(
                durationMillis = SpiritDurations.MEDIUM,
                easing = SpiritEasing.Emphasized
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = SpiritDurations.FAST,
                easing = SpiritEasing.Emphasized
            )
        )
    } else {
        // Exiting detail screen
        scaleIn(
            initialScale = 1.1f,
            animationSpec = tween(
                durationMillis = SpiritDurations.MEDIUM,
                easing = SpiritEasing.Emphasized
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = SpiritDurations.MEDIUM,
                easing = SpiritEasing.Emphasized
            )
        ) togetherWith scaleOut(
            targetScale = 0.85f,
            animationSpec = tween(
                durationMillis = SpiritDurations.MEDIUM,
                easing = SpiritEasing.Emphasized
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = SpiritDurations.FAST,
                easing = SpiritEasing.Emphasized
            )
        )
    }
}

/**
 * Fade through - Material Design motion pattern for peer navigation.
 * Screen fades out completely before new screen fades in.
 */
fun fadeThroughEnter(): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        delayMillis = SpiritDurations.FAST / 2,
        easing = SpiritEasing.Emphasized
    )
) + scaleIn(
    initialScale = 0.92f,
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        delayMillis = SpiritDurations.FAST / 2,
        easing = SpiritEasing.Emphasized
    )
)

fun fadeThroughExit(): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.FAST,
        easing = SpiritEasing.Emphasized
    )
) + scaleOut(
    targetScale = 1.08f,
    animationSpec = tween(
        durationMillis = SpiritDurations.FAST,
        easing = SpiritEasing.Emphasized
    )
)

/**
 * Slide up transition - for bottom sheets and modals.
 * Reveals content from below like uncovering hidden wisdom.
 */
fun slideUpEnter(): EnterTransition = slideInVertically(
    initialOffsetY = { fullHeight -> fullHeight },
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
)

fun slideUpExit(): ExitTransition = slideOutVertically(
    targetOffsetY = { fullHeight -> fullHeight },
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
)

/**
 * Slide horizontal - traditional navigation with spiritual timing.
 */
fun slideHorizontalEnter(forward: Boolean = true): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> if (forward) fullWidth else -fullWidth },
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
)

fun slideHorizontalExit(forward: Boolean = true): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> if (forward) -fullWidth / 3 else fullWidth },
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Emphasized
    )
)

/**
 * Zoom in - detail views that expand from center with focus.
 */
fun zoomInEnter(): EnterTransition = scaleIn(
    initialScale = 0.5f,
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
)

fun zoomInExit(): ExitTransition = scaleOut(
    targetScale = 0.5f,
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
)

/**
 * Cross fade - peaceful blending without spatial movement.
 */
fun crossFadeEnter(): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Gentle
    )
)

fun crossFadeExit(): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.MEDIUM,
        easing = SpiritEasing.Gentle
    )
)

/**
 * Circular reveal - dramatic transition for compatibility and revelations.
 * Simulated with combined scale and fade for compatibility.
 */
fun circularRevealEnter(): EnterTransition = scaleIn(
    initialScale = 0.0f,
    animationSpec = tween(
        durationMillis = SpiritDurations.MYSTICAL,
        easing = SpiritEasing.Emphasized
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.MYSTICAL,
        easing = SpiritEasing.Emphasized
    )
)

fun circularRevealExit(): ExitTransition = scaleOut(
    targetScale = 0.0f,
    animationSpec = tween(
        durationMillis = SpiritDurations.MYSTICAL,
        easing = SpiritEasing.Emphasized
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.MYSTICAL,
        easing = SpiritEasing.Emphasized
    )
)

/**
 * Scale fade - mystical floating effect with consciousness expansion.
 */
fun scaleFadeEnter(): EnterTransition = scaleIn(
    initialScale = 0.75f,
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
) + fadeIn(
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
)

fun scaleFadeExit(): ExitTransition = scaleOut(
    targetScale = 1.25f,
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = SpiritDurations.SLOW,
        easing = SpiritEasing.Emphasized
    )
)

/**
 * Gets the enter transition for a given transition type.
 */
fun getEnterTransition(type: TransitionType, forward: Boolean = true): EnterTransition {
    return when (type) {
        TransitionType.SHARED_ELEMENT -> scaleFadeEnter() // Fallback for shared element
        TransitionType.FADE_THROUGH -> fadeThroughEnter()
        TransitionType.SLIDE_UP -> slideUpEnter()
        TransitionType.SLIDE_HORIZONTAL -> slideHorizontalEnter(forward)
        TransitionType.ZOOM_IN -> zoomInEnter()
        TransitionType.CROSS_FADE -> crossFadeEnter()
        TransitionType.CIRCULAR_REVEAL -> circularRevealEnter()
        TransitionType.SCALE_FADE -> scaleFadeEnter()
        TransitionType.NONE -> EnterTransition.None
    }
}

/**
 * Gets the exit transition for a given transition type.
 */
fun getExitTransition(type: TransitionType, forward: Boolean = true): ExitTransition {
    return when (type) {
        TransitionType.SHARED_ELEMENT -> scaleFadeExit() // Fallback for shared element
        TransitionType.FADE_THROUGH -> fadeThroughExit()
        TransitionType.SLIDE_UP -> slideUpExit()
        TransitionType.SLIDE_HORIZONTAL -> slideHorizontalExit(forward)
        TransitionType.ZOOM_IN -> zoomInExit()
        TransitionType.CROSS_FADE -> crossFadeExit()
        TransitionType.CIRCULAR_REVEAL -> circularRevealExit()
        TransitionType.SCALE_FADE -> scaleFadeExit()
        TransitionType.NONE -> ExitTransition.None
    }
}

/**
 * Extension function for NavGraphBuilder to create spiritual composable destinations.
 * Applies consistent enter/exit transitions for a unified spiritual experience.
 */
fun NavGraphBuilder.spiritualComposable(
    route: String,
    transitionType: TransitionType = TransitionType.FADE_THROUGH,
    arguments: List<androidx.navigation.NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = { getEnterTransition(transitionType, forward = true) },
        exitTransition = { getExitTransition(transitionType, forward = true) },
        popEnterTransition = { getEnterTransition(transitionType, forward = false) },
        popExitTransition = { getExitTransition(transitionType, forward = false) }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}

/**
 * Extension function with explicit enter/exit transitions for maximum control.
 */
fun NavGraphBuilder.spiritualComposable(
    route: String,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    popEnterTransition: EnterTransition = enterTransition,
    popExitTransition: ExitTransition = exitTransition,
    arguments: List<androidx.navigation.NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}

/**
 * Extension function for NavController to navigate with specific transition.
 * Provides a simple API for transitioning between screens.
 */
fun NavController.navigateWithTransition(
    route: String,
    transitionType: TransitionType = TransitionType.FADE_THROUGH,
    builder: (androidx.navigation.NavOptionsBuilder.() -> Unit)? = null
) {
    navigate(route) {
        // Apply any custom builder options
        builder?.invoke(this)
    }
}

/**
 * Extension function to navigate with single top and state saving.
 * Perfect for tab navigation with cross-fade transition.
 */
fun NavController.navigateToTopLevel(
    route: String,
    transitionType: TransitionType = TransitionType.CROSS_FADE
) {
    navigate(route) {
        // Pop up to the start destination to avoid building a large stack
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        // Avoid multiple copies of the same destination
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

/**
 * Extension function for detail screen navigation with zoom-in effect.
 */
fun NavController.navigateToDetail(
    route: String,
    transitionType: TransitionType = TransitionType.ZOOM_IN
) {
    navigateWithTransition(route, transitionType)
}

/**
 * Extension function for modal presentations with slide-up effect.
 */
fun NavController.navigateToModal(
    route: String,
    transitionType: TransitionType = TransitionType.SLIDE_UP
) {
    navigateWithTransition(route, transitionType)
}

/**
 * Extension function for dramatic revelations with circular reveal.
 */
fun NavController.navigateToRevelation(
    route: String,
    transitionType: TransitionType = TransitionType.CIRCULAR_REVEAL
) {
    navigateWithTransition(route, transitionType)
}

/**
 * Predefined transition configurations for specific navigation flows in SpiritAtlas.
 */
object SpiritTransitions {
    // Home to feature screens - fade through for peer navigation
    val homeToFeature = TransitionType.FADE_THROUGH

    // Profile list to profile detail - shared element feel with scale
    val profileListToDetail = TransitionType.SHARED_ELEMENT

    // Profile to comparison - horizontal slide (feels like comparing side by side)
    val profileToComparison = TransitionType.SLIDE_HORIZONTAL

    // Any screen to compatibility results - circular reveal for dramatic effect
    val toCompatibilityResults = TransitionType.CIRCULAR_REVEAL

    // Profile to enrichment results - zoom in to focus on insights
    val toEnrichmentResults = TransitionType.ZOOM_IN

    // Any screen to consent/settings - slide up modal
    val toModal = TransitionType.SLIDE_UP

    // Tab switches - peaceful cross fade
    val tabSwitch = TransitionType.CROSS_FADE

    // Default for standard navigation
    val default = TransitionType.FADE_THROUGH
}
