package io.seroo.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.seroo.data.db.WordDataBase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(application: Application) = Room.databaseBuilder(application, WordDataBase::class.java, "word_db").build()
}