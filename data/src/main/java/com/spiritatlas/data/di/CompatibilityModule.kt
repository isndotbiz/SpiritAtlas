package com.spiritatlas.data.di

import com.spiritatlas.data.repository.CompatibilityRepositoryImpl
import com.spiritatlas.domain.repository.CompatibilityRepository
import com.spiritatlas.domain.service.CompatibilityAnalysisEngine
import com.spiritatlas.domain.service.EnhancedCouplesAnalysisEngine
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
    
    companion object {
        @Provides
        @Singleton
        fun provideCompatibilityAnalysisEngine(): CompatibilityAnalysisEngine {
            return CompatibilityAnalysisEngine()
        }
        
        @Provides
        @Singleton
        fun provideEnhancedCouplesAnalysisEngine(): EnhancedCouplesAnalysisEngine {
            return EnhancedCouplesAnalysisEngine()
        }
    }
}
