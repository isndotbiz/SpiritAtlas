package com.spiritatlas.feature.consent;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\bJ\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0011J\u0016\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/spiritatlas/feature/consent/ConsentViewModel;", "Landroidx/lifecycle/ViewModel;", "consentRepository", "Lcom/spiritatlas/domain/repository/ConsentRepository;", "aiSettingsRepository", "Lcom/spiritatlas/domain/ai/AiSettingsRepository;", "(Lcom/spiritatlas/domain/repository/ConsentRepository;Lcom/spiritatlas/domain/ai/AiSettingsRepository;)V", "consentMap", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/spiritatlas/domain/repository/ConsentType;", "Lcom/spiritatlas/domain/model/ConsentStatus;", "getConsentMap", "()Lkotlinx/coroutines/flow/StateFlow;", "autoGrantAllConsents", "", "providerModeState", "Lcom/spiritatlas/domain/ai/AiProviderMode;", "setProviderMode", "mode", "toggle", "type", "granted", "", "consent_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ConsentViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.domain.repository.ConsentRepository consentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.domain.ai.AiSettingsRepository aiSettingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<com.spiritatlas.domain.repository.ConsentType, com.spiritatlas.domain.model.ConsentStatus>> consentMap = null;
    
    @javax.inject.Inject()
    public ConsentViewModel(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.ConsentRepository consentRepository, @org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.ai.AiSettingsRepository aiSettingsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<com.spiritatlas.domain.repository.ConsentType, com.spiritatlas.domain.model.ConsentStatus>> getConsentMap() {
        return null;
    }
    
    public final void toggle(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.ConsentType type, boolean granted) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.spiritatlas.domain.ai.AiProviderMode> providerModeState() {
        return null;
    }
    
    public final void setProviderMode(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.ai.AiProviderMode mode) {
    }
    
    /**
     * Auto-grant all consents for simplified UX
     */
    public final void autoGrantAllConsents() {
    }
}