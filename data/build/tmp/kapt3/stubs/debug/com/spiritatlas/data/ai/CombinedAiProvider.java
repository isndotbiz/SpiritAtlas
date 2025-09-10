package com.spiritatlas.data.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0082@\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/spiritatlas/data/ai/CombinedAiProvider;", "Lcom/spiritatlas/domain/ai/AiTextProvider;", "openRouterProvider", "Lcom/spiritatlas/data/ai/OpenRouterProvider;", "ollamaProvider", "Lcom/spiritatlas/data/ai/OllamaProvider;", "aiSettingsRepository", "Lcom/spiritatlas/domain/ai/AiSettingsRepository;", "(Lcom/spiritatlas/data/ai/OpenRouterProvider;Lcom/spiritatlas/data/ai/OllamaProvider;Lcom/spiritatlas/domain/ai/AiSettingsRepository;)V", "generateEnrichment", "Lcom/spiritatlas/core/common/Result;", "Lcom/spiritatlas/domain/ai/EnrichmentResult;", "context", "Lcom/spiritatlas/domain/ai/EnrichmentContext;", "(Lcom/spiritatlas/domain/ai/EnrichmentContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isAvailable", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectProvider", "data_debug"})
public final class CombinedAiProvider implements com.spiritatlas.domain.ai.AiTextProvider {
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.data.ai.OpenRouterProvider openRouterProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.data.ai.OllamaProvider ollamaProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.domain.ai.AiSettingsRepository aiSettingsRepository = null;
    
    @javax.inject.Inject()
    public CombinedAiProvider(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.ai.OpenRouterProvider openRouterProvider, @org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.ai.OllamaProvider ollamaProvider, @org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.ai.AiSettingsRepository aiSettingsRepository) {
        super();
    }
    
    private final java.lang.Object selectProvider(kotlin.coroutines.Continuation<? super com.spiritatlas.domain.ai.AiTextProvider> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object generateEnrichment(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.ai.EnrichmentContext context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spiritatlas.core.common.Result<com.spiritatlas.domain.ai.EnrichmentResult>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object isAvailable(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}