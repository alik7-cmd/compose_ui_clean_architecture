package com.example.check24.overview.data

import com.example.check24.common.BaseResult
import com.example.check24.common.ui.AppConstant
import com.example.check24.overview.dao.ProductOverviewDao
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
    private var ERROR_THRESHOLD = AppConstant.ERROR_THRESHOLD

    override suspend fun getProductOverview(filterCategory: FilterCategory): Flow<BaseResult<List<ProductEntity>, String>> {
        return flow {

            if (ERROR_THRESHOLD == 0) {
                emit(BaseResult.Error(""))
                ERROR_THRESHOLD = AppConstant.ERROR_THRESHOLD

            } else {
                val productByFilter = when (filterCategory) {
                    FilterCategory.ALL -> dao.getAllProduct()
                    FilterCategory.AVAILABLE -> dao.getAllAvailableProduct()
                    FilterCategory.FAVOURITE -> dao.getAllFavouriteProduct()
                }
                if (productByFilter.isNotEmpty()) {
                    productByFilter.add(ProductEntity(true))
                }
                ERROR_THRESHOLD--
                emit(BaseResult.Success(productByFilter))
                if (filterCategory == FilterCategory.ALL && productByFilter.isEmpty()) {
                    val remoteProduct = remoteDataSource.getProductOverview(filterCategory)
                    if (remoteProduct is BaseResult.Success) {
                        saveInLocal(remoteProduct.data)
                        remoteProduct.data.add(ProductEntity(true))
                        emit(remoteProduct)
                    }
                }
            }


        }
    }

    private suspend fun saveInLocal(repos: List<ProductEntity>) {
        dao.nukeTable()
        dao.insertAll(repos)
    }
}




