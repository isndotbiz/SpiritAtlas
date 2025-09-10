package com.spiritatlas.data.di

import com.spiritatlas.data.ai.OllamaProvider
import com.spiritatlas.data.ai.OpenRouterProvider
import com.spiritatlas.domain.ai.AiTextProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class Cloud

@Qualifier
annotation class Local

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingsModule {
    @Binds
    @Singleton
    @Cloud
    abstract fun bindCloudAiProvider(impl: OpenRouterProvider): AiTextProvider

    @Binds
    @Singleton
    @Local
    abstract fun bindLocalAiProvider(impl: OllamaProvider): AiTextProvider
}


