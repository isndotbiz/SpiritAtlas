package com.spiritatlas.data.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\u0013\u001a\u00020\t2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\u00020\u001cH\u0096@\u00a2\u0006\u0002\u0010\u001dR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/spiritatlas/data/ai/OpenRouterProvider;", "Lcom/spiritatlas/domain/ai/AiTextProvider;", "retrofit", "Lretrofit2/Retrofit;", "(Lretrofit2/Retrofit;)V", "api", "Lcom/spiritatlas/data/ai/OpenRouterApi;", "kotlin.jvm.PlatformType", "buildBasicPrompt", "", "context", "Lcom/spiritatlas/domain/ai/EnrichmentContext;", "words", "", "buildComprehensivePrompt", "buildDetailedPrompt", "buildMasterPrompt", "buildMinimalPrompt", "buildPrompt", "formatMap", "map", "", "", "generateEnrichment", "Lcom/spiritatlas/core/common/Result;", "Lcom/spiritatlas/domain/ai/EnrichmentResult;", "(Lcom/spiritatlas/domain/ai/EnrichmentContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isAvailable", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class OpenRouterProvider implements com.spiritatlas.domain.ai.AiTextProvider {
    @org.jetbrains.annotations.NotNull()
    private final retrofit2.Retrofit retrofit = null;
    private final com.spiritatlas.data.ai.OpenRouterApi api = null;
    
    @javax.inject.Inject()
    public OpenRouterProvider(@javax.inject.Named(value = "openrouter")
    @org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        super();
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
    
    private final java.lang.String buildPrompt(com.spiritatlas.domain.ai.EnrichmentContext context) {
        return null;
    }
    
    private final java.lang.String buildMinimalPrompt(com.spiritatlas.domain.ai.EnrichmentContext context, int words) {
        return null;
    }
    
    private final java.lang.String buildBasicPrompt(com.spiritatlas.domain.ai.EnrichmentContext context, int words) {
        return null;
    }
    
    private final java.lang.String buildDetailedPrompt(com.spiritatlas.domain.ai.EnrichmentContext context, int words) {
        return null;
    }
    
    private final java.lang.String buildComprehensivePrompt(com.spiritatlas.domain.ai.EnrichmentContext context, int words) {
        return null;
    }
    
    private final java.lang.String buildMasterPrompt(com.spiritatlas.domain.ai.EnrichmentContext context, int words) {
        return null;
    }
    
    private final java.lang.String formatMap(java.util.Map<java.lang.String, ? extends java.lang.Object> map) {
        return null;
    }
}