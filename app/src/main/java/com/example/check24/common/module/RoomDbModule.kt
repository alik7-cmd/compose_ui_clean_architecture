package com.example.check24.common.module

import android.content.Context
import com.example.check24.common.CheckLocalDatabase
import com.example.check24.details.data.ProductDetailsRepositoryImpl
import com.example.check24.details.domain.ProductDetailsRepository
import com.example.check24.overview.data.dao.ProductOverviewDao
import com.example.check24.overview.data.ProductOverviewRepositoryImpl
import com.example.check24.overview.data.RemoteDataSource
import com.example.check24.overview.domain.ProductOverviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = CheckLocalDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideDao(appDatabase: CheckLocalDatabase) = appDatabase.productOverviewDao()
}

@Module(includes = [RoomDbModule::class])
@InstallIn(SingletonComponent::class)
object ProduceOverviewRepositoryModule{

    @Provides
    @Singleton
    fun provideProductRepositoryModule(remoteDataSource: RemoteDataSource, dao: ProductOverviewDao) : ProductOverviewRepository {
        return ProductOverviewRepositoryImpl(remoteDataSource, dao)
    }
}

@Module(includes = [RoomDbModule::class])
@InstallIn(SingletonComponent::class)
object ProductDetailsRepositoryModule{

    @Provides
    @Singleton
    fun productDetailsRepository(dao: ProductOverviewDao) : ProductDetailsRepository {
        return ProductDetailsRepositoryImpl(dao)
    }

}