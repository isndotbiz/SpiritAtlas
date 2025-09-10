package com.spiritatlas.data.di

import com.spiritatlas.data.consent.ConsentManager
import android.content.Context
import androidx.work.WorkManager
import com.spiritatlas.data.repository.UserRepositoryImpl
import com.spiritatlas.data.settings.AiSettingsRepositoryImpl
import com.spiritatlas.domain.ai.AiSettingsRepository
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindConsentRepository(impl: ConsentManager): ConsentRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
    
    @Binds
    abstract fun bindAiSettingsRepository(impl: AiSettingsRepositoryImpl): AiSettingsRepository
    
}
