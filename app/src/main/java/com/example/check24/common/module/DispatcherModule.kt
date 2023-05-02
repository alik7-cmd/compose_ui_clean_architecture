package com.example.check24.common.module

import com.example.check24.common.dispatcher.DispatcherProvider
import com.example.check24.common.dispatcher.StandardDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcher() : DispatcherProvider {
        return StandardDispatcher()
    }
}