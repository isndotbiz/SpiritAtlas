package com.spiritatlas.data.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0012R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/spiritatlas/data/ai/OllamaProvider;", "Lcom/spiritatlas/domain/ai/AiTextProvider;", "retrofit", "Lretrofit2/Retrofit;", "(Lretrofit2/Retrofit;)V", "api", "Lcom/spiritatlas/data/ai/OllamaApi;", "kotlin.jvm.PlatformType", "buildPrompt", "", "context", "Lcom/spiritatlas/domain/ai/EnrichmentContext;", "generateEnrichment", "Lcom/spiritatlas/core/common/Result;", "Lcom/spiritatlas/domain/ai/EnrichmentResult;", "(Lcom/spiritatlas/domain/ai/EnrichmentContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isAvailable", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class OllamaProvider implements com.spiritatlas.domain.ai.AiTextProvider {
    private final com.spiritatlas.data.ai.OllamaApi api = null;
    
    @javax.inject.Inject()
    public OllamaProvider(@javax.inject.Named(value = "ollama")
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
}