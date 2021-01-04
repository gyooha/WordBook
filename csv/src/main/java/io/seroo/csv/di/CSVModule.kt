package io.seroo.csv.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.seroo.csv.CSVLocalDataSource
import io.seroo.csv.CSVLocalDataSourceImpl
import io.seroo.csv.CSVRepository
import io.seroo.csv.CSVRepositoryImpl
import io.seroo.csv.CSVService
import io.seroo.csv.CSVServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class CSVModule {

    @Binds
    @Singleton
    abstract fun bindsCSVService(csvServiceImpl: CSVServiceImpl): CSVService

    @Binds
    @Singleton
    abstract fun bindsCSVLocalDataSource(csvLocalDataSourceImpl: CSVLocalDataSourceImpl): CSVLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsCSVRepository(csvRepositoryImpl: CSVRepositoryImpl): CSVRepository
}