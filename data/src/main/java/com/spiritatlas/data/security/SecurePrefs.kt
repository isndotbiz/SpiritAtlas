package com.spiritatlas.data.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Secure preferences implementation using EncryptedSharedPreferences
 * for storing sensitive data like API keys, user tokens, etc.
 */
@Singleton
class SecurePrefs @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Store a sensitive string value securely
     */
    fun putSecureString(key: String, value: String?) {
        encryptedPrefs.edit()
            .putString(key, value)
            .apply()
    }

    /**
     * Retrieve a securely stored string value
     */
    fun getSecureString(key: String, defaultValue: String? = null): String? {
        return encryptedPrefs.getString(key, defaultValue)
    }

    /**
     * Store a sensitive boolean value securely
     */
    fun putSecureBoolean(key: String, value: Boolean) {
        encryptedPrefs.edit()
            .putBoolean(key, value)
            .apply()
    }

    /**
     * Retrieve a securely stored boolean value
     */
    fun getSecureBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return encryptedPrefs.getBoolean(key, defaultValue)
    }

    /**
     * Store a sensitive long value securely
     */
    fun putSecureLong(key: String, value: Long) {
        encryptedPrefs.edit()
            .putLong(key, value)
            .apply()
    }

    /**
     * Retrieve a securely stored long value
     */
    fun getSecureLong(key: String, defaultValue: Long = 0L): Long {
        return encryptedPrefs.getLong(key, defaultValue)
    }

    /**
     * Check if a secure key exists
     */
    fun containsSecureKey(key: String): Boolean {
        return encryptedPrefs.contains(key)
    }

    /**
     * Remove a securely stored value
     */
    fun removeSecureKey(key: String) {
        encryptedPrefs.edit()
            .remove(key)
            .apply()
    }

    /**
     * Clear all securely stored data - use with caution!
     */
    fun clearAllSecureData() {
        encryptedPrefs.edit()
            .clear()
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "spirit_atlas_secure_prefs"
        
        // Common secure preference keys
        object Keys {
            const val USER_API_TOKEN = "user_api_token"
            const val OPENROUTER_USER_KEY = "openrouter_user_key"  
            const val LAST_SYNC_TIMESTAMP = "last_sync_timestamp"
            const val USER_PREMIUM_STATUS = "user_premium_status"
        }
    }
}
