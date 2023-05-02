package com.example.check24.overview.data.dto

data class ProductOverviewResponse(
    val filters: List<String>,
    val header: Header,
    val products: List<Product>
)

data class Header(
    val headerDescription: String,
    val headerTitle: String
)

data class Product(
    val available: Boolean,
    val color: String,
    val colorCode: String,
    val description: String,
    val id: Int,
    val imageURL: String,
    val longDescription: String,
    val name: String,
    val price: Price,
    val rating: Double,
    val releaseDate: Int,
    val type: String
)

data class Price(
    val currency: String,
    val value: Double
)