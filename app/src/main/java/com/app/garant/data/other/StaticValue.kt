package com.app.garant.data.other

import androidx.lifecycle.MutableLiveData
import com.app.garant.data.response.category.categories.Category
import com.app.garant.data.response.category.product.ProductResponse

object StaticValue {
    var nameCategory = ""
    private var productCatalogResponse: ProductResponse? = null

    var subCategory: List<Category>? = null
    val screenNavigateLiveData=MutableLiveData<Unit>()

}