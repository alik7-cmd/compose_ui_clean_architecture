package com.example.check24.details.domain

import com.example.check24.overview.domain.entity.ProductEntity

interface ProductDetailsRepository {

    suspend fun insertFavourite(entity: ProductEntity)

    suspend fun deleteFavourite(entity: ProductEntity)
}