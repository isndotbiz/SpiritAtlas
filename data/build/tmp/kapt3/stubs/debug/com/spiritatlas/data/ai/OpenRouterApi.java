package com.spiritatlas.data.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/spiritatlas/data/ai/OpenRouterApi;", "", "complete", "Lcom/spiritatlas/data/ai/CompletionResponse;", "authorization", "", "request", "Lcom/spiritatlas/data/ai/CompletionRequest;", "(Ljava/lang/String;Lcom/spiritatlas/data/ai/CompletionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public abstract interface OpenRouterApi {
    
    @retrofit2.http.POST(value = "/api/v1/chat/completions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object complete(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authorization, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.ai.CompletionRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spiritatlas.data.ai.CompletionResponse> $completion);
}