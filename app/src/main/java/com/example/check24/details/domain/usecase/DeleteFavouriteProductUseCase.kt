package com.example.check24.details.domain.usecase

import com.example.check24.details.domain.ProductDetailsRepository
import com.example.check24.overview.domain.entity.ProductEntity
import javax.inject.Inject

class DeleteFavouriteProductUseCase @Inject constructor(val repository: ProductDetailsRepository) {
    suspend operator fun invoke(entity: ProductEntity){
        repository.deleteFavourite(entity)
    }
}