package com.spiritatlas.data.settings

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.spiritatlas.data.BuildConfig
import com.spiritatlas.data.ai.ClaudeProvider
import com.spiritatlas.data.ai.GeminiProvider
import com.spiritatlas.data.ai.GroqProvider
import com.spiritatlas.data.ai.OllamaProvider
import com.spiritatlas.data.ai.OpenAIProvider
import com.spiritatlas.data.ai.OpenRouterProvider
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val openAIProvider: OpenAIProvider,
    private val claudeProvider: ClaudeProvider,
    private val geminiProvider: GeminiProvider,
    private val groqProvider: GroqProvider,
    private val ollamaProvider: OllamaProvider
) : AiSettingsRepository {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "ai_settings_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val keyMode = "ai_provider_mode"
    private val keyOpenAiApiKey = "openai_api_key"
    private val keyClaudeApiKey = "claude_api_key"
    private val keyOpenRouterApiKey = "openrouter_api_key"

    private val modeFlow = MutableStateFlow(readMode())

    init {
        // Initialize providers with stored keys
        val openAiKey = prefs.getString(keyOpenAiApiKey, null)
        if (!openAiKey.isNullOrBlank()) {
            openAIProvider.setApiKey(openAiKey)
        }

        val claudeKey = prefs.getString(keyClaudeApiKey, null)
        if (!claudeKey.isNullOrBlank()) {
            claudeProvider.setApiKey(claudeKey)
        }
    }

    override fun observeMode(): Flow<AiProviderMode> = modeFlow

    override suspend fun setMode(mode: AiProviderMode) {
        prefs.edit().putString(keyMode, mode.name).apply()
        modeFlow.value = mode
    }

    override suspend fun setOpenAiApiKey(apiKey: String?) {
        if (apiKey.isNullOrBlank()) {
            prefs.edit().remove(keyOpenAiApiKey).apply()
            openAIProvider.setApiKey("")
        } else {
            prefs.edit().putString(keyOpenAiApiKey, apiKey).apply()
            openAIProvider.setApiKey(apiKey)
        }
    }

    override suspend fun getOpenAiApiKey(): String? {
        return prefs.getString(keyOpenAiApiKey, null)
    }

    override suspend fun setClaudeApiKey(apiKey: String?) {
        if (apiKey.isNullOrBlank()) {
            prefs.edit().remove(keyClaudeApiKey).apply()
            claudeProvider.setApiKey("")
        } else {
            prefs.edit().putString(keyClaudeApiKey, apiKey).apply()
            claudeProvider.setApiKey(apiKey)
        }
    }

    override suspend fun getClaudeApiKey(): String? {
        return prefs.getString(keyClaudeApiKey, null)
    }

    override suspend fun setOpenRouterApiKey(apiKey: String?) {
        if (apiKey.isNullOrBlank()) {
            prefs.edit().remove(keyOpenRouterApiKey).apply()
        } else {
            prefs.edit().putString(keyOpenRouterApiKey, apiKey).apply()
        }
    }

    override suspend fun getOpenRouterApiKey(): String? {
        return prefs.getString(keyOpenRouterApiKey, null)
    }

    override suspend fun isProviderAvailable(provider: AiProviderMode): Boolean {
        return when (provider) {
            AiProviderMode.AUTO -> true
            AiProviderMode.GEMINI -> geminiProvider.isAvailable()
            AiProviderMode.GROQ -> groqProvider.isAvailable()
            AiProviderMode.OPENAI -> openAIProvider.isAvailable()
            AiProviderMode.CLAUDE -> claudeProvider.isAvailable()
            AiProviderMode.OPENROUTER -> BuildConfig.OPENROUTER_API_KEY.isNotEmpty() ||
                !prefs.getString(keyOpenRouterApiKey, null).isNullOrBlank()
            AiProviderMode.LOCAL -> ollamaProvider.isAvailable()
        }
    }

    private fun readMode(): AiProviderMode {
        val value = prefs.getString(keyMode, AiProviderMode.AUTO.name)
        return runCatching { AiProviderMode.valueOf(value!!) }.getOrDefault(AiProviderMode.AUTO)
    }
}


