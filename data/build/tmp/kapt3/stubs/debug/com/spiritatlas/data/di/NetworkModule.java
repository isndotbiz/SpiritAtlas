package com.spiritatlas.data.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\u0012\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u0006H\u0007J\u0012\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\r\u001a\u00020\tH\u0007J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\u0012\u0010\u000f\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u0006H\u0007\u00a8\u0006\u0010"}, d2 = {"Lcom/spiritatlas/data/di/NetworkModule;", "", "()V", "provideLoggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "provideOllamaOkHttp", "Lokhttp3/OkHttpClient;", "logging", "provideOllamaRetrofit", "Lretrofit2/Retrofit;", "client", "provideOpenRouterApi", "Lcom/spiritatlas/data/ai/OpenRouterApi;", "retrofit", "provideOpenRouterOkHttp", "provideOpenRouterRetrofit", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.spiritatlas.data.di.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.logging.HttpLoggingInterceptor provideLoggingInterceptor() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @javax.inject.Named(value = "openrouter")
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOpenRouterOkHttp(@org.jetbrains.annotations.NotNull()
    okhttp3.logging.HttpLoggingInterceptor logging) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @javax.inject.Named(value = "ollama")
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOllamaOkHttp(@org.jetbrains.annotations.NotNull()
    okhttp3.logging.HttpLoggingInterceptor logging) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @javax.inject.Named(value = "openrouter")
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideOpenRouterRetrofit(@javax.inject.Named(value = "openrouter")
    @org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient client) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @javax.inject.Named(value = "ollama")
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideOllamaRetrofit(@javax.inject.Named(value = "ollama")
    @org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient client) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.spiritatlas.data.ai.OpenRouterApi provideOpenRouterApi(@javax.inject.Named(value = "openrouter")
    @org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
}