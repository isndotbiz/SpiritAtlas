package com.spiritatlas.data.di

import android.content.Context
import com.spiritatlas.data.database.SpiritAtlasDatabase
import com.spiritatlas.data.database.dao.AiResponseCacheDao
import com.spiritatlas.data.database.dao.CompatibilityReportDao
import com.spiritatlas.data.database.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing database-related dependencies
 * Privacy-first: Database setup with encrypted storage
 * Optimized: Multi-tier caching with intelligent strategies
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSpiritAtlasDatabase(
        @ApplicationContext context: Context
    ): SpiritAtlasDatabase {
        return SpiritAtlasDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(
        database: SpiritAtlasDatabase
    ): UserProfileDao {
        return database.userProfileDao()
    }

    @Provides
    @Singleton
    fun provideCompatibilityReportDao(
        database: SpiritAtlasDatabase
    ): CompatibilityReportDao {
        return database.compatibilityReportDao()
    }

    @Provides
    @Singleton
    fun provideAiResponseCacheDao(
        database: SpiritAtlasDatabase
    ): AiResponseCacheDao {
        return database.aiResponseCacheDao()
    }
}
