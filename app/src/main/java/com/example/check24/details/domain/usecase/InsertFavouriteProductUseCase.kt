package com.example.check24.details.domain.usecase

import com.example.check24.details.domain.ProductDetailsRepository
import com.example.check24.overview.domain.entity.ProductEntity
import javax.inject.Inject

class InsertFavouriteProductUseCase @Inject constructor(val repository: ProductDetailsRepository) {
    suspend operator fun invoke(entity: ProductEntity){
        repository.insertFavourite(entity)
    }
}