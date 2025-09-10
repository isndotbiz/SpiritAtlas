package com.spiritatlas.data.consent

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConsentManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ConsentRepository {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "consent_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    private val consentFlows = mutableMapOf<ConsentType, MutableStateFlow<ConsentStatus>>()
    
    init {
        ConsentType.values().forEach { type ->
            val status = ConsentStatus.valueOf(
                prefs.getString(type.name, ConsentStatus.NOT_ASKED.name) ?: ConsentStatus.NOT_ASKED.name
            )
            consentFlows[type] = MutableStateFlow(status)
        }
    }
    
    override suspend fun updateConsent(type: ConsentType, status: ConsentStatus) {
        prefs.edit().putString(type.name, status.name).apply()
        consentFlows[type]?.value = status
    }
    
    override fun getConsentStatus(type: ConsentType): Flow<ConsentStatus> {
        return consentFlows[type] ?: MutableStateFlow(ConsentStatus.NOT_ASKED)
    }
    
    override fun getAllConsentStatuses(): Flow<Map<ConsentType, ConsentStatus>> {
        return MutableStateFlow(consentFlows.mapValues { it.value.value })
    }
}


