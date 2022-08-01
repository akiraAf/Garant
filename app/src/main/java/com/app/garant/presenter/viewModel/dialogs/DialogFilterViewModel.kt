package com.app.garant.presenter.viewModel.dialogs

import com.app.garant.data.response.brand.BrandResponse
import kotlinx.coroutines.flow.Flow

interface DialogFilterViewModel {

    val successFlowGetBrand: Flow<BrandResponse>
    val progressFlowGetBrand: Flow<Boolean>
    val errorFlowGetBrand: Flow<String>

    fun getBrand()
}