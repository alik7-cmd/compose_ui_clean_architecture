package com.example.check24.module

import com.example.check24.fake.TestDispatcher
import com.example.check24.common.dispatcher.DispatcherProvider
import com.example.check24.common.module.DispatcherModule
import com.example.check24.common.module.PreferenceModule
import com.example.check24.common.preference.SharedPreference
import com.example.check24.fake.FakeSharedPreference
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DispatcherModule::class, PreferenceModule::class])
abstract class TestModule {

    @Binds
    @Singleton
    abstract fun provideTestDispatcher(dispatcherProvider: TestDispatcher): DispatcherProvider

    @Binds
    @Singleton
    abstract fun provideFakeSharedPreference(fakeSharedPreference: FakeSharedPreference) : SharedPreference
}