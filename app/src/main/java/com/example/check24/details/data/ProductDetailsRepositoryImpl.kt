package com.example.check24.details.data

import com.example.check24.details.domain.ProductDetailsRepository
import com.example.check24.overview.dao.ProductOverviewDao
import com.example.check24.overview.domain.entity.ProductEntity
import javax.inject.Inject

class ProductDetailsRepositoryImpl @Inject constructor(val dao: ProductOverviewDao) : ProductDetailsRepository {
    override suspend fun insertFavourite(entity: ProductEntity) {
        dao.insert(entity)
    }

    override suspend fun deleteFavourite(entity: ProductEntity) {
        dao.delete(entity)
    }
}