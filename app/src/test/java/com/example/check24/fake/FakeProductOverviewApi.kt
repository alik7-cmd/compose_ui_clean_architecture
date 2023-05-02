package com.example.check24.fake

import com.example.check24.JsonProvider
import com.example.check24.overview.data.api.ProductOverviewApi
import com.example.check24.overview.data.dto.ProductOverviewResponse

class FakeProductOverviewApi : ProductOverviewApi {

    var failUserApi: Boolean = false
    var wrongResponse: Boolean = false

    override suspend fun getProductDetails(): ProductOverviewResponse {
        if(failUserApi) throw java.lang.Exception("")
        val listOfProduct = JsonProvider.objectFromJsonFileWithType<ProductOverviewResponse>(PRODUCT_JSON)
        return listOfProduct

    }

    companion object{
        const val PRODUCT_JSON = "/json/products.json"
    }
}