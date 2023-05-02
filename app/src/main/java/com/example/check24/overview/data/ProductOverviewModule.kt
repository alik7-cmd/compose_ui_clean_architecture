package com.example.check24.overview.data

import com.example.check24.common.module.NetworkModule
import com.example.check24.overview.dao.ProductOverviewDao
import com.example.check24.overview.data.api.ProductOverviewApi
import com.example.check24.overview.domain.ProductOverviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ProductOverViewApiModule{

    @Provides
    @Singleton
    fun provideProductOverviewApi(retrofit: Retrofit) : ProductOverviewApi {
        return retrofit.create(ProductOverviewApi::class.java)
    }

}

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RepoRepositoryModule{

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiInterface: ProductOverviewApi) : RemoteDataSource {
        return RemoteDataSourceImpl(apiInterface)
    }


}