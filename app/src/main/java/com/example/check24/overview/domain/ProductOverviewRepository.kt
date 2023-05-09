package com.example.check24.overview.domain

import com.example.check24.common.BaseResult
import com.example.check24.overview.domain.entity.ProductEntity
import com.example.check24.overview.domain.entity.ProductOverviewEntity
import kotlinx.coroutines.flow.Flow

interface ProductOverviewRepository {

    fun getProductOverview(filterCategory: FilterCategory): Flow<BaseResult<List<ProductEntity>, String>>
}