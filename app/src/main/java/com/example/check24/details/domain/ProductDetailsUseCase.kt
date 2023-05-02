package com.example.check24.details.domain

import com.example.check24.details.domain.usecase.DeleteFavouriteProductUseCase
import com.example.check24.details.domain.usecase.InsertFavouriteProductUseCase

data class ProductDetailsUseCase(
    val insertFavouriteProductUseCase: InsertFavouriteProductUseCase,
    val deleteFavouriteProductUseCase: DeleteFavouriteProductUseCase
)
