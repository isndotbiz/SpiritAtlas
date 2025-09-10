package com.spiritatlas.data.settings

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
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

    private val modeFlow = MutableStateFlow(readMode())

    override fun observeMode(): Flow<AiProviderMode> = modeFlow

    override suspend fun setMode(mode: AiProviderMode) {
        prefs.edit().putString(keyMode, mode.name).apply()
        modeFlow.value = mode
    }

    private fun readMode(): AiProviderMode {
        val value = prefs.getString(keyMode, AiProviderMode.AUTO.name)
        return runCatching { AiProviderMode.valueOf(value!!) }.getOrDefault(AiProviderMode.AUTO)
    }
}


