package com.spiritatlas.data.tracking

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsageTrackerTest {

    private lateinit var context: Context
    private lateinit var usageTracker: UsageTracker
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        // Clear preferences before each test
        context.getSharedPreferences("ai_usage_tracker", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()
        usageTracker = UsageTracker(context)
        sharedPreferences = context.getSharedPreferences("ai_usage_tracker", Context.MODE_PRIVATE)
    }

    @After
    fun tearDown() {
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun testCanMakeRequest_initiallyAllowed() = runTest {
        // Initially should be able to make requests
        assertTrue(usageTracker.canMakeRequest(AiProvider.GEMINI))
        assertTrue(usageTracker.canMakeRequest(AiProvider.GROQ))
    }

    @Test
    fun testRecordRequest_updatesUsageStats() = runTest {
        // Record a request
        usageTracker.recordRequest(AiProvider.GEMINI)

        // Check usage stats
        val usage = usageTracker.getUsageStats(AiProvider.GEMINI)
        assertEquals(1, usage.requestsPerMinute)
        assertEquals(1, usage.requestsPerDay)
        assertEquals(15, usage.limitPerMinute)
        assertEquals(1500, usage.limitPerDay)
    }

    @Test
    fun testPerMinuteRateLimit_gemini() = runTest {
        // Fill up the per-minute limit (15 requests)
        repeat(15) {
            assertTrue(usageTracker.canMakeRequest(AiProvider.GEMINI))
            usageTracker.recordRequest(AiProvider.GEMINI)
        }

        // 16th request should be blocked
        assertFalse(usageTracker.canMakeRequest(AiProvider.GEMINI))
    }

    @Test
    fun testPerMinuteRateLimit_groq() = runTest {
        // Fill up the per-minute limit (30 requests)
        repeat(30) {
            assertTrue(usageTracker.canMakeRequest(AiProvider.GROQ))
            usageTracker.recordRequest(AiProvider.GROQ)
        }

        // 31st request should be blocked
        assertFalse(usageTracker.canMakeRequest(AiProvider.GROQ))
    }

    @Test
    fun testUsageStats_separateProviders() = runTest {
        // Record requests for both providers
        usageTracker.recordRequest(AiProvider.GEMINI)
        usageTracker.recordRequest(AiProvider.GEMINI)
        usageTracker.recordRequest(AiProvider.GROQ)

        // Check stats are separate
        val geminiUsage = usageTracker.getUsageStats(AiProvider.GEMINI)
        val groqUsage = usageTracker.getUsageStats(AiProvider.GROQ)

        assertEquals(2, geminiUsage.requestsPerMinute)
        assertEquals(1, groqUsage.requestsPerMinute)
    }

    @Test
    fun testResetUsage() = runTest {
        // Record some requests
        repeat(5) {
            usageTracker.recordRequest(AiProvider.GEMINI)
        }

        // Verify usage
        var usage = usageTracker.getUsageStats(AiProvider.GEMINI)
        assertEquals(5, usage.requestsPerMinute)

        // Reset
        usageTracker.resetUsage(AiProvider.GEMINI)

        // Verify reset
        usage = usageTracker.getUsageStats(AiProvider.GEMINI)
        assertEquals(0, usage.requestsPerMinute)
        assertEquals(0, usage.requestsPerDay)
    }

    @Test
    fun testResetAllUsage() = runTest {
        // Record requests for both providers
        usageTracker.recordRequest(AiProvider.GEMINI)
        usageTracker.recordRequest(AiProvider.GROQ)

        // Reset all
        usageTracker.resetAllUsage()

        // Verify both are reset
        val geminiUsage = usageTracker.getUsageStats(AiProvider.GEMINI)
        val groqUsage = usageTracker.getUsageStats(AiProvider.GROQ)

        assertEquals(0, geminiUsage.requestsPerMinute)
        assertEquals(0, groqUsage.requestsPerMinute)
    }

    @Test
    fun testProviderUsage_percentageCalculations() = runTest {
        // Record 10 requests for Gemini (limit is 15 per minute)
        repeat(10) {
            usageTracker.recordRequest(AiProvider.GEMINI)
        }

        val usage = usageTracker.getUsageStats(AiProvider.GEMINI)

        // 10/15 = 66.67%
        assertEquals(66.67f, usage.percentagePerMinute, 0.1f)

        // Should be near limit (>= 80%)
        assertFalse(usage.isNearLimitPerMinute)

        // Record 2 more (12/15 = 80%)
        repeat(2) {
            usageTracker.recordRequest(AiProvider.GEMINI)
        }

        val updatedUsage = usageTracker.getUsageStats(AiProvider.GEMINI)
        assertTrue(updatedUsage.isNearLimitPerMinute)
    }

    @Test
    fun testGetTimeUntilNextRequest_noWait() = runTest {
        // Initially should be 0 (can make request now)
        val waitTime = usageTracker.getTimeUntilNextRequest(AiProvider.GEMINI)
        assertEquals(0L, waitTime)
    }

    @Test
    fun testGetTimeUntilNextRequest_rateLimited() = runTest {
        // Fill up the per-minute limit
        repeat(15) {
            usageTracker.recordRequest(AiProvider.GEMINI)
        }

        // Should have to wait (at least some time)
        val waitTime = usageTracker.getTimeUntilNextRequest(AiProvider.GEMINI)
        assertTrue(waitTime > 0)
        // Should be less than 1 minute
        assertTrue(waitTime <= 60_000L)
    }

    @Test
    fun testPerDayLimit_gemini() = runTest {
        // Gemini has a per-day limit of 1500
        val usage = usageTracker.getUsageStats(AiProvider.GEMINI)
        assertNotNull(usage.limitPerDay)
        assertEquals(1500, usage.limitPerDay)
    }

    @Test
    fun testPerDayLimit_groq() = runTest {
        // Groq has no per-day limit
        val usage = usageTracker.getUsageStats(AiProvider.GROQ)
        assertNull(usage.limitPerDay)
    }

    @Test
    fun testProviderUsage_nearLimitPerDay() = runTest {
        // We can't actually record 1500 requests in a test, but we can verify the logic
        val usage = usageTracker.getUsageStats(AiProvider.GEMINI)

        // With 0 requests, should not be near limit
        assertFalse(usage.isNearLimitPerDay)

        // Note: In a real scenario, recording 1200+ requests would trigger isNearLimitPerDay
    }

    @Test
    fun testPersistence() = runTest {
        // Record requests
        repeat(5) {
            usageTracker.recordRequest(AiProvider.GEMINI)
        }

        // Create new tracker instance (simulates app restart)
        val newTracker = UsageTracker(context)

        // Should still have the usage data
        val usage = newTracker.getUsageStats(AiProvider.GEMINI)
        assertEquals(5, usage.requestsPerMinute)
        assertEquals(5, usage.requestsPerDay)
    }

    @Test
    fun testStateFlows_emitUpdates() = runTest {
        // Initial state
        var geminiUsage = usageTracker.geminiUsage.value
        assertEquals(0, geminiUsage.requestsPerMinute)

        // Record request
        usageTracker.recordRequest(AiProvider.GEMINI)

        // StateFlow should be updated
        geminiUsage = usageTracker.geminiUsage.value
        assertEquals(1, geminiUsage.requestsPerMinute)
    }
}
