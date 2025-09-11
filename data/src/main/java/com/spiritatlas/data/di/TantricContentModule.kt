package com.spiritatlas.data.di

import com.spiritatlas.data.repository.TantricContentRepositoryImpl
import com.spiritatlas.domain.repository.TantricContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TantricContentModule {

    @Binds
    @Singleton
    abstract fun bindTantricContentRepository(
        tantricContentRepositoryImpl: TantricContentRepositoryImpl
    ): TantricContentRepository
}
