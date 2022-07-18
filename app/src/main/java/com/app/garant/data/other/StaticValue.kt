package com.app.garant.data.other

import android.util.ArraySet
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.app.garant.data.response.category.Data
import com.app.garant.data.response.category.categories.Category
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.models.OrderData
import com.app.garant.presenter.screens.NavigationScreen

object StaticValue {
    var nameCategory = ""
    private var productCatalogResponse: ProductResponse? = null

    var subCategory: List<Category>? = null
    val navigationToScreen = MutableLiveData<Unit>()
    val screenLogoutLiveData = MutableLiveData<Unit>()
    val filter = MutableLiveData<Unit>()
    val orderData = ArrayList<Data>()



}