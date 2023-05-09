package com.example.check24.overview.domain.usecase

import com.example.check24.common.BaseResult
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.ProductOverviewRepository
import com.example.check24.overview.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductOverviewUseCase @Inject constructor(private val repository: ProductOverviewRepository) {
    operator fun invoke(filterCategory: FilterCategory, isRefreshing : Boolean): Flow<BaseResult<List<ProductEntity>, String>> {
        return repository.getProductOverview(filterCategory,isRefreshing)
    }
}