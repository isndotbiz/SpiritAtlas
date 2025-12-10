package com.spiritatlas.app.navigation

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Navigation analytics tracker for SpiritAtlas.
 *
 * Tracks:
 * - Screen views and time spent per screen
 * - Navigation paths and patterns
 * - Drop-off points (screens where users exit)
 * - Deep link usage
 * - Navigation errors
 *
 * All analytics are stored locally and can be exported for analysis.
 * No personal data is collected or transmitted.
 */
class NavigationAnalytics(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "navigation_analytics",
        Context.MODE_PRIVATE
    )

    private val sessionStartTime = System.currentTimeMillis()
    private val screenViewTimes = ConcurrentHashMap<String, Long>()
    private var currentScreen: String? = null
    private var currentScreenStartTime: Long = 0

    private val navigationPath = mutableListOf<String>()
    private val screenViewCounts = mutableMapOf<String, Int>()
    private val transitionCounts = mutableMapOf<String, Int>()

    companion object {
        private const val PREFS_KEY_TOTAL_SESSIONS = "total_sessions"
        private const val PREFS_KEY_SCREEN_VIEW_PREFIX = "screen_view_"
        private const val PREFS_KEY_TRANSITION_PREFIX = "transition_"
        private const val PREFS_KEY_DROP_OFF_PREFIX = "drop_off_"
        private const val PREFS_KEY_DEEP_LINK_PREFIX = "deep_link_"

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    }

    init {
        // Increment session counter
        val totalSessions = prefs.getInt(PREFS_KEY_TOTAL_SESSIONS, 0)
        prefs.edit().putInt(PREFS_KEY_TOTAL_SESSIONS, totalSessions + 1).apply()

        Timber.d("NavigationAnalytics initialized. Session #${totalSessions + 1}")
    }

    /**
     * Tracks a screen view. Should be called when navigating to a new screen.
     */
    fun trackScreenView(destination: NavDestination) {
        val screenName = destination.route ?: "unknown"
        trackScreenView(screenName)
    }

    fun trackScreenView(screenName: String) {
        val now = System.currentTimeMillis()

        // Record time spent on previous screen
        currentScreen?.let { previousScreen ->
            val timeSpent = now - currentScreenStartTime
            val totalTime = screenViewTimes.getOrDefault(previousScreen, 0L) + timeSpent
            screenViewTimes[previousScreen] = totalTime

            // Update persistent storage
            prefs.edit()
                .putLong("$PREFS_KEY_SCREEN_VIEW_PREFIX${previousScreen}_time", totalTime)
                .apply()
        }

        // Track new screen
        currentScreen = screenName
        currentScreenStartTime = now

        // Increment view count
        val viewCount = screenViewCounts.getOrDefault(screenName, 0) + 1
        screenViewCounts[screenName] = viewCount

        // Update persistent storage
        prefs.edit()
            .putInt("$PREFS_KEY_SCREEN_VIEW_PREFIX$screenName", viewCount)
            .apply()

        // Add to navigation path
        navigationPath.add(screenName)

        Timber.d("Screen view: $screenName (view #$viewCount)")
    }

    /**
     * Tracks a transition between two screens.
     */
    fun trackTransition(from: String, to: String) {
        val transitionKey = "$from->$to"
        val count = transitionCounts.getOrDefault(transitionKey, 0) + 1
        transitionCounts[transitionKey] = count

        // Update persistent storage
        prefs.edit()
            .putInt("$PREFS_KEY_TRANSITION_PREFIX$transitionKey", count)
            .apply()

        Timber.d("Navigation transition: $from -> $to (count: $count)")
    }

    /**
     * Tracks a deep link usage.
     */
    fun trackDeepLink(deepLinkUri: String) {
        val count = prefs.getInt("$PREFS_KEY_DEEP_LINK_PREFIX$deepLinkUri", 0) + 1
        prefs.edit()
            .putInt("$PREFS_KEY_DEEP_LINK_PREFIX$deepLinkUri", count)
            .apply()

        Timber.d("Deep link used: $deepLinkUri (count: $count)")
    }

    /**
     * Tracks deep link navigation result.
     */
    fun trackDeepLinkNavigation(screen: String, params: Map<String, String> = emptyMap()) {
        val paramsStr = if (params.isEmpty()) "" else " with $params"
        Timber.d("Deep link navigation to: $screen$paramsStr")

        trackScreenView("deeplink_$screen")
    }

    /**
     * Tracks a navigation error.
     */
    fun trackNavigationError(errorType: String, details: String) {
        val timestamp = dateFormat.format(Date())
        val errorKey = "nav_error_${timestamp}_${errorType}"

        prefs.edit()
            .putString(errorKey, details)
            .apply()

        Timber.e("Navigation error: $errorType - $details")
    }

    /**
     * Tracks a drop-off (user exiting the app from a specific screen).
     */
    fun trackDropOff(screenName: String) {
        val count = prefs.getInt("$PREFS_KEY_DROP_OFF_PREFIX$screenName", 0) + 1
        prefs.edit()
            .putInt("$PREFS_KEY_DROP_OFF_PREFIX$screenName", count)
            .apply()

        Timber.d("Drop-off recorded at: $screenName (count: $count)")
    }

    /**
     * Gets analytics summary for the current session.
     */
    fun getSessionSummary(): SessionAnalytics {
        val sessionDuration = System.currentTimeMillis() - sessionStartTime

        return SessionAnalytics(
            sessionDuration = sessionDuration,
            screenViews = screenViewCounts.toMap(),
            screenViewTimes = screenViewTimes.toMap(),
            navigationPath = navigationPath.toList(),
            transitions = transitionCounts.toMap()
        )
    }

    /**
     * Gets all-time analytics from persistent storage.
     */
    fun getAllTimeAnalytics(): AllTimeAnalytics {
        val totalSessions = prefs.getInt(PREFS_KEY_TOTAL_SESSIONS, 0)

        // Collect screen views
        val screenViews = mutableMapOf<String, Int>()
        val screenTimes = mutableMapOf<String, Long>()
        prefs.all.forEach { (key, value) ->
            when {
                key.startsWith(PREFS_KEY_SCREEN_VIEW_PREFIX) && !key.endsWith("_time") -> {
                    val screenName = key.removePrefix(PREFS_KEY_SCREEN_VIEW_PREFIX)
                    screenViews[screenName] = value as? Int ?: 0
                }
                key.startsWith(PREFS_KEY_SCREEN_VIEW_PREFIX) && key.endsWith("_time") -> {
                    val screenName = key.removePrefix(PREFS_KEY_SCREEN_VIEW_PREFIX).removeSuffix("_time")
                    screenTimes[screenName] = value as? Long ?: 0L
                }
            }
        }

        // Collect transitions
        val transitions = mutableMapOf<String, Int>()
        prefs.all.forEach { (key, value) ->
            if (key.startsWith(PREFS_KEY_TRANSITION_PREFIX)) {
                val transitionKey = key.removePrefix(PREFS_KEY_TRANSITION_PREFIX)
                transitions[transitionKey] = value as? Int ?: 0
            }
        }

        // Collect drop-offs
        val dropOffs = mutableMapOf<String, Int>()
        prefs.all.forEach { (key, value) ->
            if (key.startsWith(PREFS_KEY_DROP_OFF_PREFIX)) {
                val screenName = key.removePrefix(PREFS_KEY_DROP_OFF_PREFIX)
                dropOffs[screenName] = value as? Int ?: 0
            }
        }

        // Collect deep links
        val deepLinks = mutableMapOf<String, Int>()
        prefs.all.forEach { (key, value) ->
            if (key.startsWith(PREFS_KEY_DEEP_LINK_PREFIX)) {
                val deepLink = key.removePrefix(PREFS_KEY_DEEP_LINK_PREFIX)
                deepLinks[deepLink] = value as? Int ?: 0
            }
        }

        return AllTimeAnalytics(
            totalSessions = totalSessions,
            screenViews = screenViews,
            screenViewTimes = screenTimes,
            transitions = transitions,
            dropOffs = dropOffs,
            deepLinks = deepLinks
        )
    }

    /**
     * Resets all analytics data. Use for privacy or debugging.
     */
    fun resetAnalytics() {
        prefs.edit().clear().apply()
        screenViewTimes.clear()
        navigationPath.clear()
        screenViewCounts.clear()
        transitionCounts.clear()
        Timber.d("All analytics data cleared")
    }

    /**
     * Exports analytics as a readable string for debugging or support.
     */
    fun exportAnalytics(): String {
        val session = getSessionSummary()
        val allTime = getAllTimeAnalytics()

        return buildString {
            appendLine("=== SpiritAtlas Navigation Analytics ===")
            appendLine()
            appendLine("Current Session:")
            appendLine("  Duration: ${session.sessionDuration / 1000}s")
            appendLine("  Screens viewed: ${session.screenViews.size}")
            appendLine("  Navigation path: ${session.navigationPath.joinToString(" -> ")}")
            appendLine()
            appendLine("All-Time Statistics:")
            appendLine("  Total sessions: ${allTime.totalSessions}")
            appendLine()
            appendLine("Screen Views (All-Time):")
            allTime.screenViews.entries
                .sortedByDescending { it.value }
                .forEach { (screen, count) ->
                    val avgTime = (allTime.screenViewTimes[screen] ?: 0L) / count / 1000
                    appendLine("  $screen: $count views (avg ${avgTime}s)")
                }
            appendLine()
            appendLine("Top Transitions:")
            allTime.transitions.entries
                .sortedByDescending { it.value }
                .take(10)
                .forEach { (transition, count) ->
                    appendLine("  $transition: $count times")
                }
            appendLine()
            appendLine("Drop-off Points:")
            allTime.dropOffs.entries
                .sortedByDescending { it.value }
                .forEach { (screen, count) ->
                    appendLine("  $screen: $count exits")
                }
            appendLine()
            appendLine("Deep Links Used:")
            allTime.deepLinks.entries
                .sortedByDescending { it.value }
                .forEach { (link, count) ->
                    appendLine("  $link: $count times")
                }
        }
    }
}

/**
 * Data class for current session analytics.
 */
data class SessionAnalytics(
    val sessionDuration: Long,
    val screenViews: Map<String, Int>,
    val screenViewTimes: Map<String, Long>,
    val navigationPath: List<String>,
    val transitions: Map<String, Int>
)

/**
 * Data class for all-time analytics.
 */
data class AllTimeAnalytics(
    val totalSessions: Int,
    val screenViews: Map<String, Int>,
    val screenViewTimes: Map<String, Long>,
    val transitions: Map<String, Int>,
    val dropOffs: Map<String, Int>,
    val deepLinks: Map<String, Int>
)

/**
 * NavController extension to automatically track navigation.
 */
fun NavController.setupAnalytics(analytics: NavigationAnalytics) {
    addOnDestinationChangedListener { _, destination, _ ->
        analytics.trackScreenView(destination)
    }
}
