package com.example.check24.overview.data

import com.example.check24.common.BaseResult
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity

interface RemoteDataSource {
    suspend fun getProductOverview(filterCategory: FilterCategory) :BaseResult<MutableList<ProductEntity>, String>
}