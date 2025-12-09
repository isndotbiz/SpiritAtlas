package com.spiritatlas.data.di

import com.spiritatlas.data.repository.CompatibilityRepositoryImpl
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.repository.CompatibilityRepository
import com.spiritatlas.domain.service.AiCompatibilityService
import com.spiritatlas.domain.service.AiCompatibilityServiceImpl
import com.spiritatlas.domain.service.CompatibilityAnalysisEngine
import com.spiritatlas.domain.service.EnhancedCouplesAnalysisEngine
import com.spiritatlas.domain.service.optimized.OptimizedCompatibilityAnalysisEngine
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CompatibilityModule {

    @Binds
    @Singleton
    abstract fun bindCompatibilityRepository(
        compatibilityRepositoryImpl: CompatibilityRepositoryImpl
    ): CompatibilityRepository

    @Binds
    @Singleton
    abstract fun bindAiCompatibilityService(
        aiCompatibilityServiceImpl: AiCompatibilityServiceImpl
    ): AiCompatibilityService

    companion object {
        @Provides
        @Singleton
        fun provideCompatibilityAnalysisEngine(
            aiCompatibilityService: AiCompatibilityService?
        ): CompatibilityAnalysisEngine {
            return CompatibilityAnalysisEngine(aiCompatibilityService)
        }

        @Provides
        @Singleton
        fun provideOptimizedCompatibilityAnalysisEngine(
            aiCompatibilityService: AiCompatibilityService?
        ): OptimizedCompatibilityAnalysisEngine {
            return OptimizedCompatibilityAnalysisEngine(aiCompatibilityService)
        }

        @Provides
        @Singleton
        fun provideEnhancedCouplesAnalysisEngine(): EnhancedCouplesAnalysisEngine {
            return EnhancedCouplesAnalysisEngine()
        }
    }
}
