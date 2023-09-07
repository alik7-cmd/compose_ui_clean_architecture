package com.example.check24.overview.data

import com.example.check24.common.BaseResult
import com.example.check24.overview.data.dao.ProductOverviewDao
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.ProductOverviewRepository
import com.example.check24.overview.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductOverviewRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dao: ProductOverviewDao
) : ProductOverviewRepository {

    override fun getProductOverview(
        filterCategory: FilterCategory,
        isRefreshing: Boolean
    ): Flow<BaseResult<List<ProductEntity>, String>> {
        return flow {
            emit(BaseResult.Loading)
            if (isRefreshing) {
                deleteAllData()
            }

            val productByFilter = when (filterCategory) {
                FilterCategory.ALL -> dao.getAllProduct()
                FilterCategory.AVAILABLE -> dao.getAllAvailableProduct()
                FilterCategory.FAVOURITE -> dao.getAllFavouriteProduct()
            }
            if (shouldFetchFromServer(filterCategory, productByFilter)) {
                val remoteProduct = remoteDataSource.getProductOverview(filterCategory)
                if (remoteProduct is BaseResult.Success) {
                    saveInLocal(remoteProduct.data)
                    emit(remoteProduct)
                }
            } else {
                emit(BaseResult.Success(productByFilter))
            }
        }
    }

    private suspend fun saveInLocal(repos: List<ProductEntity>) {
        dao.nukeTable()
        dao.insertAll(repos)
    }

    private suspend fun deleteAllData() {
        dao.nukeTable()
    }

    private fun shouldFetchFromServer(
        filterCategory: FilterCategory,
        productList: List<ProductEntity>
    ): Boolean {
        return filterCategory == FilterCategory.ALL && productList.isEmpty()
    }
}




