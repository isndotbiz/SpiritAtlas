package com.spiritatlas.data.security;

/**
 * Secure preferences implementation using EncryptedSharedPreferences
 * for storing sensitive data like API keys, user tokens, etc.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u0010J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u0016J\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0012J\u0016\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0010J\u0016\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0016J\u0018\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012J\u000e\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/spiritatlas/data/security/SecurePrefs;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "encryptedPrefs", "Landroid/content/SharedPreferences;", "getEncryptedPrefs", "()Landroid/content/SharedPreferences;", "encryptedPrefs$delegate", "Lkotlin/Lazy;", "masterKey", "Landroidx/security/crypto/MasterKey;", "clearAllSecureData", "", "containsSecureKey", "", "key", "", "getSecureBoolean", "defaultValue", "getSecureLong", "", "getSecureString", "putSecureBoolean", "value", "putSecureLong", "putSecureString", "removeSecureKey", "Companion", "data_debug"})
public final class SecurePrefs {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.security.crypto.MasterKey masterKey = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy encryptedPrefs$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "spirit_atlas_secure_prefs";
    @org.jetbrains.annotations.NotNull()
    public static final com.spiritatlas.data.security.SecurePrefs.Companion Companion = null;
    
    @javax.inject.Inject()
    public SecurePrefs(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.content.SharedPreferences getEncryptedPrefs() {
        return null;
    }
    
    /**
     * Store a sensitive string value securely
     */
    public final void putSecureString(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    /**
     * Retrieve a securely stored string value
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSecureString(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.Nullable()
    java.lang.String defaultValue) {
        return null;
    }
    
    /**
     * Store a sensitive boolean value securely
     */
    public final void putSecureBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean value) {
    }
    
    /**
     * Retrieve a securely stored boolean value
     */
    public final boolean getSecureBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean defaultValue) {
        return false;
    }
    
    /**
     * Store a sensitive long value securely
     */
    public final void putSecureLong(@org.jetbrains.annotations.NotNull()
    java.lang.String key, long value) {
    }
    
    /**
     * Retrieve a securely stored long value
     */
    public final long getSecureLong(@org.jetbrains.annotations.NotNull()
    java.lang.String key, long defaultValue) {
        return 0L;
    }
    
    /**
     * Check if a secure key exists
     */
    public final boolean containsSecureKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return false;
    }
    
    /**
     * Remove a securely stored value
     */
    public final void removeSecureKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    /**
     * Clear all securely stored data - use with caution!
     */
    public final void clearAllSecureData() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0005B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/spiritatlas/data/security/SecurePrefs$Companion;", "", "()V", "PREFS_NAME", "", "Keys", "data_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/spiritatlas/data/security/SecurePrefs$Companion$Keys;", "", "()V", "LAST_SYNC_TIMESTAMP", "", "OPENROUTER_USER_KEY", "USER_API_TOKEN", "USER_PREMIUM_STATUS", "data_debug"})
        public static final class Keys {
            @org.jetbrains.annotations.NotNull()
            public static final java.lang.String USER_API_TOKEN = "user_api_token";
            @org.jetbrains.annotations.NotNull()
            public static final java.lang.String OPENROUTER_USER_KEY = "openrouter_user_key";
            @org.jetbrains.annotations.NotNull()
            public static final java.lang.String LAST_SYNC_TIMESTAMP = "last_sync_timestamp";
            @org.jetbrains.annotations.NotNull()
            public static final java.lang.String USER_PREMIUM_STATUS = "user_premium_status";
            @org.jetbrains.annotations.NotNull()
            public static final com.spiritatlas.data.security.SecurePrefs.Companion.Keys INSTANCE = null;
            
            private Keys() {
                super();
            }
        }
    }
}