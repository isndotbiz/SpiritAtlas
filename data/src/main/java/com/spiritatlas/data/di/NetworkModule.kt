package com.spiritatlas.data.di

import com.spiritatlas.data.BuildConfig
import com.spiritatlas.data.ai.OpenRouterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @Named("openrouter")
    fun provideOpenRouterOkHttp(logging: HttpLoggingInterceptor): OkHttpClient {
        // Certificate pinning for OpenRouter API security
        // Pins should be updated when certificates rotate (typically annually)
        val certificatePinner = CertificatePinner.Builder()
            .add("openrouter.ai", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=") // Primary pin - UPDATE WITH ACTUAL PIN
            .add("openrouter.ai", "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=") // Backup pin - UPDATE WITH ACTUAL PIN
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .certificatePinner(certificatePinner)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("ollama")
    fun provideOllamaOkHttp(logging: HttpLoggingInterceptor): OkHttpClient {
        // Ollama is local, no certificate pinning needed
        // Longer timeouts for local AI model processing
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS) // Local models can be slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("openrouter")
    fun provideOpenRouterRetrofit(@Named("openrouter") client: OkHttpClient): Retrofit {
        val baseUrl = BuildConfig.OPENROUTER_BASE_URL.trimEnd('/') + "/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("ollama")
    fun provideOllamaRetrofit(@Named("ollama") client: OkHttpClient): Retrofit {
        val baseUrl = BuildConfig.OLLAMA_BASE_URL.trimEnd('/') + "/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenRouterApi(@Named("openrouter") retrofit: Retrofit): OpenRouterApi {
        return retrofit.create(OpenRouterApi::class.java)
    }
}


