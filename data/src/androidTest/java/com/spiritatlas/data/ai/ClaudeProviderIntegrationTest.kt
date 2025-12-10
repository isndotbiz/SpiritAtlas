package com.spiritatlas.data.ai

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AccuracyLevel
import com.spiritatlas.domain.ai.EnrichmentContext
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Integration test for ClaudeProvider AI integration
 * Tests API calls, authentication, error handling, and response parsing
 */
@RunWith(AndroidJUnit4::class)
class ClaudeProviderIntegrationTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var provider: ClaudeProvider
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        mockServer = MockWebServer()
        mockServer.start()

        // Create OkHttpClient for testing
        val testClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        provider = ClaudeProvider(testClient, context)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun isAvailable_returnsFalseWhenNoAuthConfigured() = runTest {
        // Given - no API key or OAuth token set

        // When
        val available = provider.isAvailable()

        // Then
        assertThat(available).isFalse()
    }

    @Test
    fun isAvailable_returnsTrueWhenApiKeyConfigured() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        // When
        val available = provider.isAvailable()

        // Then
        assertThat(available).isTrue()
    }

    @Test
    fun isAvailable_returnsTrueWhenOAuthConfigured() = runTest {
        // Given
        val futureExpiry = System.currentTimeMillis() + 3600000 // 1 hour
        provider.setOAuthToken("access_token", "refresh_token", futureExpiry)

        // When
        val available = provider.isAvailable()

        // Then
        assertThat(available).isTrue()
    }

    @Test
    fun isUsingOAuth_detectsOAuthAuthentication() = runTest {
        // Given - no auth
        assertThat(provider.isUsingOAuth()).isFalse()

        // When - set OAuth tokens
        val futureExpiry = System.currentTimeMillis() + 3600000
        provider.setOAuthToken("access_token", "refresh_token", futureExpiry)

        // Then
        assertThat(provider.isUsingOAuth()).isTrue()
    }

    @Test
    fun clearOAuthCredentials_removesTokens() = runTest {
        // Given
        val futureExpiry = System.currentTimeMillis() + 3600000
        provider.setOAuthToken("access_token", "refresh_token", futureExpiry)
        assertThat(provider.isUsingOAuth()).isTrue()

        // When
        provider.clearOAuthCredentials()

        // Then
        assertThat(provider.isUsingOAuth()).isFalse()
    }

    @Test
    fun generateEnrichment_requiresAuthentication() = runTest {
        // Given - no authentication
        val context = createTestEnrichmentContext()

        // When
        val result = provider.generateEnrichment(context)

        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("authentication not configured")
    }

    @Test
    fun generateEnrichment_sendsCorrectRequestWithApiKey() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(createClaudeSuccessResponse())
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        val result = provider.generateEnrichment(enrichmentContext)

        // Then
        val request = mockServer.takeRequest(5, TimeUnit.SECONDS)
        assertThat(request).isNotNull()
        assertThat(request?.getHeader("x-api-key")).isEqualTo("test_api_key")
        assertThat(request?.getHeader("Content-Type")).isEqualTo("application/json")
        assertThat(request?.getHeader("anthropic-version")).isNotNull()
    }

    @Test
    fun generateEnrichment_parsesSuccessResponse() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(createClaudeSuccessResponse())
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        val result = provider.generateEnrichment(enrichmentContext)

        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val success = result as Result.Success
        assertThat(success.data.text).contains("spiritual analysis")
        assertThat(success.data.confidence).isEqualTo(0.95f)
    }

    @Test
    fun generateEnrichment_handlesUnauthorizedError() = runTest {
        // Given
        provider.setApiKey("invalid_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("""{"error": {"message": "Invalid API key"}}""")
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        val result = provider.generateEnrichment(enrichmentContext)

        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("Authentication failed")
    }

    @Test
    fun generateEnrichment_handlesForbiddenError() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody("""{"error": {"message": "Rate limit exceeded"}}""")
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        val result = provider.generateEnrichment(enrichmentContext)

        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("Access forbidden")
    }

    @Test
    fun generateEnrichment_handlesServerError() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("""{"error": {"message": "Internal server error"}}""")
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        val result = provider.generateEnrichment(enrichmentContext)

        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("Claude API error: 500")
    }

    @Test
    fun generateEnrichment_includesEnrichmentContext() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(createClaudeSuccessResponse())
        )

        val enrichmentContext = EnrichmentContext(
            completedFields = 15,
            totalFields = 27,
            accuracyLevel = AccuracyLevel.GOOD,
            numerology = mapOf("lifePath" to 7, "destiny" to 11),
            astrology = mapOf("sunSign" to "Gemini", "moonSign" to "Pisces"),
            energyProfile = mapOf("type" to "Generator", "authority" to "Sacral"),
            personalDetails = mapOf("name" to "Test User")
        )

        // When
        val result = provider.generateEnrichment(enrichmentContext)

        // Then
        val request = mockServer.takeRequest(5, TimeUnit.SECONDS)
        val requestBody = request?.body?.readUtf8() ?: ""

        // Verify context is included in prompt
        assertThat(requestBody).contains("15/27 fields")
        assertThat(requestBody).contains("lifePath")
        assertThat(requestBody).contains("sunSign")
    }

    @Test
    fun generateEnrichment_selectsModelBasedOnCompletedFields() = runTest {
        // Test with minimal fields - should use Haiku
        testModelSelection(completedFields = 5, expectedModel = "claude-haiku-4")

        // Test with moderate fields - should use Sonnet
        testModelSelection(completedFields = 15, expectedModel = "claude-sonnet-4-5")

        // Test with many fields - should use Opus
        testModelSelection(completedFields = 25, expectedModel = "claude-opus-4-5")
    }

    private suspend fun testModelSelection(completedFields: Int, expectedModel: String) {
        // Reset provider
        val testClient = OkHttpClient.Builder().build()
        val testProvider = ClaudeProvider(testClient, context)
        testProvider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(createClaudeSuccessResponse())
        )

        val context = createTestEnrichmentContext(completedFields = completedFields)
        testProvider.generateEnrichment(context)

        val request = mockServer.takeRequest(5, TimeUnit.SECONDS)
        val requestBody = request?.body?.readUtf8() ?: ""
        assertThat(requestBody).contains(expectedModel)
    }

    @Test
    fun refreshAccessToken_failsWhenOAuthNotAvailable() = runTest {
        // Given
        val futureExpiry = System.currentTimeMillis() + 3600000
        provider.setOAuthToken("access_token", "refresh_token", futureExpiry)

        // When
        val result = provider.refreshAccessToken()

        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("OAuth is not yet available")
    }

    @Test
    fun oauthTokenPersistence_survivesProviderRecreation() = runTest {
        // Given
        val futureExpiry = System.currentTimeMillis() + 3600000
        provider.setOAuthToken("test_access", "test_refresh", futureExpiry)
        assertThat(provider.isUsingOAuth()).isTrue()

        // When - Create new provider instance
        val testClient = OkHttpClient.Builder().build()
        val newProvider = ClaudeProvider(testClient, context)

        // Then - OAuth tokens should be loaded from encrypted storage
        assertThat(newProvider.isUsingOAuth()).isTrue()

        // Cleanup
        newProvider.clearOAuthCredentials()
    }

    @Test
    fun generateEnrichment_includesSystemPrompt() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(createClaudeSuccessResponse())
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        provider.generateEnrichment(enrichmentContext)

        // Then
        val request = mockServer.takeRequest(5, TimeUnit.SECONDS)
        val requestBody = request?.body?.readUtf8() ?: ""

        assertThat(requestBody).contains("system")
        assertThat(requestBody).contains("spiritual advisor")
    }

    @Test
    fun generateEnrichment_requestsStructuredOutput() = runTest {
        // Given
        provider.setApiKey("test_api_key")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(createClaudeSuccessResponse())
        )

        val enrichmentContext = createTestEnrichmentContext()

        // When
        provider.generateEnrichment(enrichmentContext)

        // Then
        val request = mockServer.takeRequest(5, TimeUnit.SECONDS)
        val requestBody = request?.body?.readUtf8() ?: ""

        // Verify prompt asks for structured sections
        assertThat(requestBody).contains("Life Purpose")
        assertThat(requestBody).contains("Natural Strengths")
        assertThat(requestBody).contains("Growth Challenges")
    }

    // Helper functions
    private fun createTestEnrichmentContext(
        completedFields: Int = 15,
        totalFields: Int = 27
    ): EnrichmentContext {
        return EnrichmentContext(
            completedFields = completedFields,
            totalFields = totalFields,
            accuracyLevel = AccuracyLevel.GOOD,
            numerology = mapOf("lifePath" to 7),
            astrology = mapOf("sunSign" to "Gemini"),
            energyProfile = mapOf("type" to "Generator"),
            personalDetails = mapOf("name" to "Test User")
        )
    }

    private fun createClaudeSuccessResponse(): String {
        return """
        {
            "id": "msg_test123",
            "type": "message",
            "role": "assistant",
            "content": [
                {
                    "type": "text",
                    "text": "This is a comprehensive spiritual analysis based on your profile..."
                }
            ],
            "model": "claude-sonnet-4-5",
            "stop_reason": "end_turn",
            "usage": {
                "input_tokens": 100,
                "output_tokens": 200
            }
        }
        """.trimIndent()
    }
}
