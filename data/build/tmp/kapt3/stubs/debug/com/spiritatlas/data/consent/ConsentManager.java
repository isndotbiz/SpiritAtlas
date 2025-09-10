package com.spiritatlas.data.consent;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\t0\u00100\u000fH\u0016J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\tH\u0096@\u00a2\u0006\u0002\u0010\u0016R \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/spiritatlas/data/consent/ConsentManager;", "Lcom/spiritatlas/domain/repository/ConsentRepository;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "consentFlows", "", "Lcom/spiritatlas/domain/repository/ConsentType;", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/spiritatlas/domain/model/ConsentStatus;", "masterKey", "Landroidx/security/crypto/MasterKey;", "prefs", "Landroid/content/SharedPreferences;", "getAllConsentStatuses", "Lkotlinx/coroutines/flow/Flow;", "", "getConsentStatus", "type", "updateConsent", "", "status", "(Lcom/spiritatlas/domain/repository/ConsentType;Lcom/spiritatlas/domain/model/ConsentStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class ConsentManager implements com.spiritatlas.domain.repository.ConsentRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.security.crypto.MasterKey masterKey = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.spiritatlas.domain.repository.ConsentType, kotlinx.coroutines.flow.MutableStateFlow<com.spiritatlas.domain.model.ConsentStatus>> consentFlows = null;
    
    @javax.inject.Inject()
    public ConsentManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateConsent(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.ConsentType type, @org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.ConsentStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.spiritatlas.domain.model.ConsentStatus> getConsentStatus(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.ConsentType type) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.Map<com.spiritatlas.domain.repository.ConsentType, com.spiritatlas.domain.model.ConsentStatus>> getAllConsentStatuses() {
        return null;
    }
}