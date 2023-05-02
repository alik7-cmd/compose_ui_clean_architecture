package com.example.check24.overview.data.api

import com.example.check24.overview.data.dto.ProductOverviewResponse
import retrofit2.http.GET

interface ProductOverviewApi {

    @GET("/products-test.json")
    suspend fun getProductDetails() : ProductOverviewResponse
}