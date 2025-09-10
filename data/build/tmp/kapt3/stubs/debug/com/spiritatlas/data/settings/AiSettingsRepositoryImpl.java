package com.spiritatlas.data.settings;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0002J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/spiritatlas/data/settings/AiSettingsRepositoryImpl;", "Lcom/spiritatlas/domain/ai/AiSettingsRepository;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "keyMode", "", "masterKey", "Landroidx/security/crypto/MasterKey;", "modeFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/spiritatlas/domain/ai/AiProviderMode;", "prefs", "Landroid/content/SharedPreferences;", "observeMode", "Lkotlinx/coroutines/flow/Flow;", "readMode", "setMode", "", "mode", "(Lcom/spiritatlas/domain/ai/AiProviderMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class AiSettingsRepositoryImpl implements com.spiritatlas.domain.ai.AiSettingsRepository {
    @org.jetbrains.annotations.NotNull()
    private final androidx.security.crypto.MasterKey masterKey = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String keyMode = "ai_provider_mode";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.spiritatlas.domain.ai.AiProviderMode> modeFlow = null;
    
    @javax.inject.Inject()
    public AiSettingsRepositoryImpl(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.spiritatlas.domain.ai.AiProviderMode> observeMode() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setMode(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.ai.AiProviderMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.spiritatlas.domain.ai.AiProviderMode readMode() {
        return null;
    }
}