package com.example.check24.overview.data

import com.example.check24.common.BaseResult
import com.example.check24.common.utils.DateUtil
import com.example.check24.overview.data.api.ProductOverviewApi
import com.example.check24.overview.data.dto.Product
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity
import com.example.check24.pMap
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val api: ProductOverviewApi) : RemoteDataSource {
    override suspend fun getProductOverview(filterCategory: FilterCategory): BaseResult<MutableList<ProductEntity>, String> {

        return try {
            val response = api.getProductDetails()
            val entityList = mutableListOf<ProductEntity>()

            response.products.pMap {
                entityList.add(it.toProductEntity(false))
            }

            BaseResult.Success(entityList)
        } catch (ex: Exception) {
            BaseResult.Error(ex.message ?: "")
        }


    }
}

fun Product.toProductEntity(isFooter: Boolean) = ProductEntity(
    0,
    isFooter,
    available,
    description,
    imageURL,
    longDescription,
    name,
    price.currency,
    price.value.toString(),
    rating.toFloat(),
    DateUtil.getDateFromMiliSecond(releaseDate.toLong()),
    type,
    false
)