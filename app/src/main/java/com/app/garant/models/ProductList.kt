package com.app.garant.models

data class AllProductResponse(
    val data: ProductList
)

data class ProductList(
    val data: List<ProductData>)

data class ProductData(
    var installmentCost:String,
    var month:String,
    var fullCost:String,
    var descriptionProduct:String,
    var productImageView: Int)
