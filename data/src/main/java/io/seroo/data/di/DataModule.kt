package io.seroo.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.seroo.data.CoroutineDispatcher
import io.seroo.data.repository.WordRepository
import io.seroo.data.repository.WordRepositoryImpl
import io.seroo.data.source.WordLocalDataSource
import io.seroo.data.source.WordLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideCoroutineDispatcher() = CoroutineDispatcher()
    }

    @Binds
    @Singleton
    abstract fun bindWordRepository(workRepositoryImpl: WordRepositoryImpl): WordRepository

    @Binds
    @Singleton
    abstract fun bindWordLocalDataSource(wordLocalDataSourceImpl: WordLocalDataSourceImpl): WordLocalDataSource
}