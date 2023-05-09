package com.example.check24.details.data

import com.example.check24.details.domain.usecase.ProductDetailsUseCase
import com.example.check24.details.domain.usecase.DeleteFavouriteProductUseCase
import com.example.check24.details.domain.usecase.InsertFavouriteProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideProductDetailsUseCase(
        insertFavouriteProductUseCase: InsertFavouriteProductUseCase,
        deleteFavouriteProductUseCase: DeleteFavouriteProductUseCase
    ): ProductDetailsUseCase {

        return ProductDetailsUseCase(insertFavouriteProductUseCase, deleteFavouriteProductUseCase)
    }


}