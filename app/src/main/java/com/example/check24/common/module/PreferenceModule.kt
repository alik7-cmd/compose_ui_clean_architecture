package com.example.check24.common.module

import android.content.Context
import com.example.check24.common.preference.SharedPreference
import com.example.check24.common.preference.SharedPreferenceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): SharedPreference {
        return SharedPreferenceImpl(context)
    }
}