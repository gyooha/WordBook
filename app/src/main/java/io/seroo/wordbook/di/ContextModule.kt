package io.seroo.wordbook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.seroo.wordbook.ContextWrapper
import io.seroo.wordbook.ContextWrapperImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class ContextModule {

    @Binds
    abstract fun bindsContextWrapper(contextWrapperImpl: ContextWrapperImpl): ContextWrapper
}