package com.spiritatlas.data.ai

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Anthropic Claude provider - Excellent reasoning and analysis
 *
 * Authentication Methods:
 * 1. OAuth 2.0 (preferred, when available) - Secure token-based authentication
 * 2. API Key (fallback) - User-provided API key from settings
 *
 * OAuth Flow:
 * - Call setOAuthToken() with access token, refresh token, and expiration
 * - Provider automatically refreshes tokens before expiration
 * - Tokens stored securely in EncryptedSharedPreferences
 * - Falls back to API key if OAuth tokens are unavailable
 *
 * Supported models:
 * - claude-sonnet-4-5 (best balance, reasoning)
 * - claude-opus-4-5 (highest quality)
 * - claude-haiku-4 (fast, affordable)
 *
 * Lean implementation using OkHttp (no Anthropic SDK bloat)
 */
@Singleton
class ClaudeProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @ApplicationContext private val context: Context
) : AiTextProvider {

    private var userApiKey: String? = null
    private val baseUrl = "https://api.anthropic.com/v1"
    private val apiVersion = "2023-06-01"

    // OAuth configuration
    // TODO: Update OAuth endpoints when Anthropic officially releases OAuth support
    // As of January 2025, Anthropic does not yet provide public OAuth 2.0 endpoints.
    // This implementation is ready and will work once OAuth is available.
    private val oauthTokenUrl = "https://auth.anthropic.com/oauth/token" // Hypothetical
    private val oauthAuthUrl = "https://auth.anthropic.com/oauth/authorize" // Hypothetical

    // Encrypted storage for OAuth tokens
    private val encryptedPrefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "claude_oauth_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Thread-safe token refresh
    private val tokenRefreshMutex = Mutex()

    // OAuth token state (in-memory cache)
    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null
    private var cachedExpiresAt: Long = 0L

    /**
     * Sets the API key for authentication (legacy/fallback method)
     */
    fun setApiKey(apiKey: String) {
        userApiKey = apiKey
    }

    /**
     * Sets OAuth credentials for token-based authentication
     *
     * @param accessToken The OAuth access token
     * @param refreshToken The OAuth refresh token for renewing access
     * @param expiresAt Unix timestamp (milliseconds) when the access token expires
     */
    fun setOAuthToken(accessToken: String, refreshToken: String, expiresAt: Long) {
        // Update in-memory cache
        cachedAccessToken = accessToken
        cachedRefreshToken = refreshToken
        cachedExpiresAt = expiresAt

        // Persist to encrypted storage
        encryptedPrefs.edit().apply {
            putString(PREF_ACCESS_TOKEN, accessToken)
            putString(PREF_REFRESH_TOKEN, refreshToken)
            putLong(PREF_EXPIRES_AT, expiresAt)
            apply()
        }
    }

    /**
     * Clears all OAuth credentials from memory and secure storage
     */
    fun clearOAuthCredentials() {
        // Clear in-memory cache
        cachedAccessToken = null
        cachedRefreshToken = null
        cachedExpiresAt = 0L

        // Clear encrypted storage
        encryptedPrefs.edit().apply {
            remove(PREF_ACCESS_TOKEN)
            remove(PREF_REFRESH_TOKEN)
            remove(PREF_EXPIRES_AT)
            apply()
        }
    }

    /**
     * Loads OAuth tokens from encrypted storage into memory cache
     */
    private fun loadOAuthTokensFromStorage() {
        if (cachedAccessToken == null) {
            cachedAccessToken = encryptedPrefs.getString(PREF_ACCESS_TOKEN, null)
            cachedRefreshToken = encryptedPrefs.getString(PREF_REFRESH_TOKEN, null)
            cachedExpiresAt = encryptedPrefs.getLong(PREF_EXPIRES_AT, 0L)
        }
    }

    /**
     * Checks if OAuth tokens are available and valid
     */
    private fun hasValidOAuthTokens(): Boolean {
        loadOAuthTokensFromStorage()
        return !cachedAccessToken.isNullOrBlank() &&
               !cachedRefreshToken.isNullOrBlank()
    }

    /**
     * Checks if the current access token is expired or will expire soon
     * @param bufferMs Milliseconds before expiration to consider token expired (default 5 minutes)
     */
    private fun isTokenExpired(bufferMs: Long = 5 * 60 * 1000): Boolean {
        loadOAuthTokensFromStorage()
        val now = System.currentTimeMillis()
        return cachedExpiresAt <= (now + bufferMs)
    }

    /**
     * Refreshes the OAuth access token using the refresh token
     *
     * This method is thread-safe and will only perform one refresh at a time.
     * If multiple requests detect an expired token simultaneously, only one
     * will perform the refresh while others wait for the result.
     *
     * @return Result indicating success or failure of the refresh operation
     */
    suspend fun refreshAccessToken(): Result<Unit> = withContext(Dispatchers.IO) {
        if (!OAUTH_AVAILABLE) {
            return@withContext Result.Error(
                UnsupportedOperationException(
                    "OAuth is not yet available from Anthropic. " +
                    "This implementation is ready pending official OAuth support."
                )
            )
        }

        tokenRefreshMutex.withLock {
            // Check if token was already refreshed by another coroutine
            if (!isTokenExpired(bufferMs = 0)) {
                return@withContext Result.Success(Unit)
            }

            loadOAuthTokensFromStorage()
            val refreshToken = cachedRefreshToken
                ?: return@withContext Result.Error(IllegalStateException("No refresh token available"))

            try {
                // TODO: Update client_id and client_secret when OAuth is available
                val requestBody = FormBody.Builder()
                    .add("grant_type", "refresh_token")
                    .add("refresh_token", refreshToken)
                    .add("client_id", "your_client_id") // Replace with actual client ID
                    .add("client_secret", "your_client_secret") // Replace with actual secret
                    .build()

                val request = Request.Builder()
                    .url(oauthTokenUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(requestBody)
                    .build()

                val response = okHttpClient.newCall(request).execute()

                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "Unknown error"
                    return@withContext Result.Error(
                        Exception("OAuth token refresh failed: ${response.code} - $errorBody")
                    )
                }

                val responseBody = response.body?.string()
                    ?: return@withContext Result.Error(Exception("Empty token response"))

                val jsonResponse = JSONObject(responseBody)
                val newAccessToken = jsonResponse.getString("access_token")
                val newRefreshToken = jsonResponse.optString("refresh_token", refreshToken)
                val expiresIn = jsonResponse.getInt("expires_in") // seconds
                val expiresAt = System.currentTimeMillis() + (expiresIn * 1000L)

                // Update tokens
                setOAuthToken(newAccessToken, newRefreshToken, expiresAt)

                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    /**
     * Gets a valid access token, refreshing if necessary
     *
     * @return The access token, or null if unavailable
     */
    private suspend fun getValidAccessToken(): String? {
        if (!hasValidOAuthTokens()) {
            return null
        }

        // Refresh token if expired or expiring soon
        if (isTokenExpired()) {
            when (val refreshResult = refreshAccessToken()) {
                is Result.Success -> {
                    // Token refreshed successfully
                }
                is Result.Error -> {
                    // Refresh failed, token is invalid
                    return null
                }
                is Result.Loading -> {
                    // Should not happen in this context
                    return null
                }
            }
        }

        loadOAuthTokensFromStorage()
        return cachedAccessToken
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        val prompt = PromptTemplates.buildEnrichmentPrompt(context)
        val systemPrompt = PromptTemplates.getSystemPrompt()
        val model = selectModel(context.completedFields)

        generateCompletion(
            messages = listOf(
                ClaudeMessage(ClaudeRole.USER, prompt)
            ),
            systemPrompt = systemPrompt,
            model = model,
            maxTokens = 4000,
            temperature = 0.7
        )
    }

    /**
     * Generates a follow-up response in an ongoing conversation
     */
    suspend fun generateFollowUp(
        conversationHistory: List<ClaudeMessage>,
        systemPrompt: String? = null,
        model: String? = null
    ): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        generateCompletion(
            messages = conversationHistory,
            systemPrompt = systemPrompt ?: PromptTemplates.getSystemPrompt(),
            model = model ?: "claude-sonnet-4-5",
            maxTokens = 3000,
            temperature = 0.7
        )
    }

    /**
     * Generates daily insights with optimized token usage
     */
    suspend fun generateDailyInsights(
        prompt: String,
        systemPrompt: String? = null
    ): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        generateCompletion(
            messages = listOf(
                ClaudeMessage(ClaudeRole.USER, prompt)
            ),
            systemPrompt = systemPrompt ?: PromptTemplates.getSystemPrompt(),
            model = "claude-haiku-4", // Fast and affordable for daily insights
            maxTokens = 2000,
            temperature = 0.6
        )
    }

    /**
     * Generates compatibility insights with enhanced context
     */
    suspend fun generateCompatibilityInsights(
        profile1Summary: String,
        profile2Summary: String,
        calculatedScores: Map<String, Double>
    ): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        val prompt = PromptTemplates.buildCompatibilityInsightsPrompt(
            profile1Summary,
            profile2Summary,
            calculatedScores
        )

        generateCompletion(
            messages = listOf(
                ClaudeMessage(ClaudeRole.USER, prompt)
            ),
            systemPrompt = PromptTemplates.getSystemPrompt(),
            model = "claude-sonnet-4-5", // Balance quality and cost
            maxTokens = 3500,
            temperature = 0.7
        )
    }

    /**
     * Core completion method used by all generation functions
     */
    private suspend fun generateCompletion(
        messages: List<ClaudeMessage>,
        systemPrompt: String,
        model: String,
        maxTokens: Int,
        temperature: Double
    ): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        // Determine authentication method: OAuth (preferred) or API Key (fallback)
        val oauthToken = getValidAccessToken()
        val apiKey = userApiKey

        // Require at least one authentication method
        if (oauthToken == null && apiKey.isNullOrBlank()) {
            return@withContext Result.Error(
                IllegalStateException("Claude authentication not configured. Please provide OAuth token or API key.")
            )
        }

        try {
            val requestBody = JSONObject().apply {
                put("model", model)
                put("max_tokens", maxTokens)
                put("messages", JSONArray().apply {
                    messages.forEach { message ->
                        put(JSONObject().apply {
                            put("role", message.role.value)
                            put("content", message.content)
                        })
                    }
                })
                put("system", systemPrompt)
                put("temperature", temperature)
            }.toString()

            // Build request with appropriate authentication
            val requestBuilder = Request.Builder()
                .url("$baseUrl/messages")
                .addHeader("anthropic-version", apiVersion)
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody("application/json".toMediaType()))

            // Use OAuth token if available, otherwise fall back to API key
            if (oauthToken != null) {
                requestBuilder.addHeader("Authorization", "Bearer $oauthToken")
            } else {
                requestBuilder.addHeader("x-api-key", apiKey!!)
            }

            val request = requestBuilder.build()
            val response = okHttpClient.newCall(request).execute()

            // Handle OAuth-specific errors
            when (response.code) {
                401 -> {
                    // Unauthorized - token may be invalid
                    if (oauthToken != null) {
                        // Clear invalid OAuth tokens
                        clearOAuthCredentials()
                        return@withContext Result.Error(
                            Exception("OAuth token invalid or expired. Please re-authenticate.")
                        )
                    }
                    return@withContext Result.Error(
                        Exception("Authentication failed. Please check your API key.")
                    )
                }
                403 -> {
                    // Forbidden - insufficient permissions or rate limit
                    return@withContext Result.Error(
                        Exception("Access forbidden. Check your account permissions or rate limits.")
                    )
                }
                429 -> {
                    // Rate limit exceeded
                    val retryAfter = response.header("retry-after")
                    return@withContext Result.Error(
                        Exception("Rate limit exceeded. ${retryAfter?.let { "Retry after $it seconds." } ?: ""}")
                    )
                }
            }

            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: "Unknown error"
                return@withContext Result.Error(
                    Exception("Claude API error: ${response.code} - $errorBody")
                )
            }

            val responseBody = response.body?.string()
                ?: return@withContext Result.Error(Exception("Empty response"))

            val jsonResponse = JSONObject(responseBody)
            val content = jsonResponse
                .getJSONArray("content")
                .getJSONObject(0)
                .getString("text")

            Result.Success(EnrichmentResult(
                text = content,
                confidence = 0.95f // Claude has excellent reliability and reasoning
            ))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun isAvailable(): Boolean {
        // Available if either OAuth tokens or API key is configured
        return hasValidOAuthTokens() || !userApiKey.isNullOrBlank()
    }

    /**
     * Checks if OAuth authentication is currently being used
     */
    fun isUsingOAuth(): Boolean {
        return hasValidOAuthTokens()
    }

    private fun selectModel(completedFields: Int): String {
        return when {
            completedFields >= 24 -> "claude-opus-4-5"  // Best quality
            completedFields >= 10 -> "claude-sonnet-4-5"  // Best balance
            else -> "claude-haiku-4"  // Fast and affordable
        }
    }

    private fun buildEnrichmentPrompt(context: EnrichmentContext): String {
        // Legacy method - now delegated to PromptTemplates
        return PromptTemplates.buildEnrichmentPrompt(context)
    }

    private fun formatMapDetailed(map: Map<String, Any>): String {
        return if (map.isEmpty()) {
            "No data provided"
        } else {
            map.entries.joinToString("\n") { (key, value) ->
                "• $key: $value"
            }
        }
    }

    companion object {
        const val PROVIDER_NAME = "Claude (Anthropic)"

        /**
         * OAuth 2.0 availability flag
         *
         * As of January 2025, Anthropic does not provide public OAuth 2.0 endpoints.
         * This implementation is complete and ready to use once OAuth becomes available.
         *
         * When OAuth is released by Anthropic:
         * 1. Set this flag to true
         * 2. Update oauthTokenUrl and oauthAuthUrl with official endpoints
         * 3. Configure client_id and client_secret in the refresh flow
         * 4. Test the complete OAuth flow end-to-end
         */
        const val OAUTH_AVAILABLE = false

        // EncryptedSharedPreferences keys
        private const val PREF_ACCESS_TOKEN = "oauth_access_token"
        private const val PREF_REFRESH_TOKEN = "oauth_refresh_token"
        private const val PREF_EXPIRES_AT = "oauth_expires_at"

        // Pricing (user's account)
        const val OPUS_INPUT_PER_1M = 15.00  // $15/1M tokens
        const val OPUS_OUTPUT_PER_1M = 75.00
        const val SONNET_INPUT_PER_1M = 3.00  // $3/1M tokens
        const val SONNET_OUTPUT_PER_1M = 15.00
        const val HAIKU_INPUT_PER_1M = 0.80  // $0.80/1M tokens
        const val HAIKU_OUTPUT_PER_1M = 4.00
    }
}

/**
 * Represents a message in a Claude conversation
 */
data class ClaudeMessage(
    val role: ClaudeRole,
    val content: String
)

/**
 * Message role in Claude API
 */
enum class ClaudeRole(val value: String) {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system")
}
