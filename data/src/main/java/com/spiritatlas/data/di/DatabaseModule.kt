package com.spiritatlas.data.di

import android.content.Context
import com.spiritatlas.data.database.SpiritAtlasDatabase
import com.spiritatlas.data.database.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing database-related dependencies
 * Privacy-first: Database setup with encrypted storage ✨
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
    fun provideUserProfileDao(
        database: SpiritAtlasDatabase
    ): UserProfileDao {
        return database.userProfileDao()
    }
}
