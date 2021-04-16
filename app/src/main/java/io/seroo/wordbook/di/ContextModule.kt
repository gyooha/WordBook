package io.seroo.wordbook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import io.seroo.wordbook.ContextWrapper
import io.seroo.wordbook.ContextWrapperImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ContextModule {

    @Binds
    abstract fun bindsContextWrapper(contextWrapperImpl: ContextWrapperImpl): ContextWrapper
}