package com.app.garant.data.other

import androidx.lifecycle.MutableLiveData
import com.app.garant.data.response.category.categories.Category
import com.app.garant.data.response.category.product.ProductResponse

object StaticValue {
    var nameCategory = ""
    private var productCatalogResponse: ProductResponse? = null

    var subCategory: List<Category>? = null
    val screenLogoutLiveData = MutableLiveData<Unit>()
    val cartAmount = MutableLiveData<Unit>()
    val mainRequest = MutableLiveData<Unit>()
    val goToMain = MutableLiveData<Unit>()
    var mainScreenProduct = ProductResponse()
    val filter = MutableLiveData<Unit>()
    var cartCheck: Boolean = true

}