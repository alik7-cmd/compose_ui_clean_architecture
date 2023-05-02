package com.example.check24.overview.data

import com.example.check24.common.BaseResult
import com.example.check24.common.utils.DateUtil
import com.example.check24.overview.dao.ProductOverviewDao
import com.example.check24.overview.data.api.ProductOverviewApi
import com.example.check24.overview.data.dto.Product
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.ProductOverviewRepository
import com.example.check24.overview.domain.entity.ProductEntity
import com.example.check24.overview.domain.entity.ProductOverviewEntity
import com.example.check24.pMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductOverviewRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dao: ProductOverviewDao
) : ProductOverviewRepository {
    private var ERROR_THRESHOLD = 4

    override suspend fun getProductOverview(filterCategory: FilterCategory): Flow<BaseResult<List<ProductEntity>, String>> {
        return flow {

            if (ERROR_THRESHOLD == 0) {
                emit(BaseResult.Error(""))
                ERROR_THRESHOLD = 3

            } else {
                val productByFilter = when (filterCategory) {
                    FilterCategory.ALL -> dao.getAllProduct()
                    FilterCategory.AVAILABLE -> dao.getAllAvailableProduct()
                    FilterCategory.FAVOURITE -> dao.getAllProduct()
                }
                productByFilter.add(ProductEntity(true))
                emit(BaseResult.Success(productByFilter))
                if (productByFilter.isEmpty() || checkIfOnlyFooter(productByFilter)) {
                    val remoteProduct = remoteDataSource.getProductOverview(filterCategory)
                    if (remoteProduct is BaseResult.Success) {
                        saveInLocal(remoteProduct.data)
                        ERROR_THRESHOLD--
                        remoteProduct.data.add(ProductEntity(true))
                        emit(remoteProduct)
                    }
                }
            }


        }
    }

    private fun checkIfOnlyFooter(list: List<ProductEntity>) : Boolean{
        return(list.size == 1 && list[0].isFooter)
    }

    private suspend fun saveInLocal(repos: List<ProductEntity>) {
        dao.nukeTable()
        dao.insertAll(repos)
    }
}




