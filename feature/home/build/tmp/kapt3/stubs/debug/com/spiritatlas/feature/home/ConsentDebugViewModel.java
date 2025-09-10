package com.spiritatlas.feature.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/spiritatlas/feature/home/ConsentDebugViewModel;", "Landroidx/lifecycle/ViewModel;", "consentRepository", "Lcom/spiritatlas/domain/repository/ConsentRepository;", "(Lcom/spiritatlas/domain/repository/ConsentRepository;)V", "ai", "Lkotlinx/coroutines/flow/Flow;", "Lcom/spiritatlas/domain/model/ConsentStatus;", "analytics", "cloud", "debugText", "Lkotlinx/coroutines/flow/StateFlow;", "", "getDebugText", "()Lkotlinx/coroutines/flow/StateFlow;", "home_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ConsentDebugViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.domain.repository.ConsentRepository consentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.spiritatlas.domain.model.ConsentStatus> ai = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.spiritatlas.domain.model.ConsentStatus> cloud = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.spiritatlas.domain.model.ConsentStatus> analytics = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> debugText = null;
    
    @javax.inject.Inject()
    public ConsentDebugViewModel(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.ConsentRepository consentRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getDebugText() {
        return null;
    }
}