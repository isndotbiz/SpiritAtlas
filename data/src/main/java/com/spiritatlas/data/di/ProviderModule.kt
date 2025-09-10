package com.spiritatlas.data.di

import com.spiritatlas.data.ai.CombinedAiProvider
import com.spiritatlas.domain.ai.AiTextProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {
    @Binds
    abstract fun bindAiProvider(impl: CombinedAiProvider): AiTextProvider
}


