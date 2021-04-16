package io.seroo.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.seroo.data.CoroutineDispatcher
import io.seroo.data.repository.FileRepository
import io.seroo.data.repository.FileRepositoryImpl
import io.seroo.data.repository.WordRepository
import io.seroo.data.repository.WordRepositoryImpl
import io.seroo.data.service.FileService
import io.seroo.data.service.FileServiceImpl
import io.seroo.data.source.WordLocalDataSource
import io.seroo.data.source.WordLocalDataSourceImpl
import io.seroo.data.source.local.FileLocalDataSource
import io.seroo.data.source.local.FileLocalDataSourceImpl
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideCoroutineDispatcher() = CoroutineDispatcher()

        @Provides
        @Singleton
        fun json(): Json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    @Binds
    @Singleton
    abstract fun bindWordRepository(workRepositoryImpl: WordRepositoryImpl): WordRepository

    @Binds
    @Singleton
    abstract fun bindWordLocalDataSource(wordLocalDataSourceImpl: WordLocalDataSourceImpl): WordLocalDataSource

    @Binds
    @Singleton
    abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

    @Binds
    @Singleton
    abstract fun bindFileLocalDataSource(fileLocalDataSourceImpl: FileLocalDataSourceImpl): FileLocalDataSource

    @Binds
    @Singleton
    abstract fun bindFileService(fileServiceImpl: FileServiceImpl): FileService
}