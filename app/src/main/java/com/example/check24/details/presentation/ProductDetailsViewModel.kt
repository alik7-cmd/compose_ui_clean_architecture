package com.example.check24.details.presentation

import androidx.lifecycle.ViewModel
import com.example.check24.details.domain.usecase.ProductDetailsUseCase
import com.example.check24.overview.domain.entity.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    val insertDeleteUseCase : ProductDetailsUseCase
) : ViewModel() {
    var data: ProductEntity? = null


    suspend fun insert(entity: ProductEntity){
        insertDeleteUseCase.insertFavouriteProductUseCase(entity)
    }

    suspend fun delete(entity: ProductEntity){
        insertDeleteUseCase.deleteFavouriteProductUseCase(entity)
    }
}