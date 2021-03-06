package com.app.garant.presenter.viewModel.profile

import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import kotlinx.coroutines.flow.Flow

interface UpdatePhoneViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<UpdatePhoneResponce>
    val progressFlow: Flow<Boolean>
    fun updatePhone(request: UpdatePhoneRequest)

    val errorFlowChange: Flow<String>
    val successFlowChange: Flow<ChangePhoneResponse>
    val progressFlowChange: Flow<Boolean>
    fun changeNumber(request: ChangePhoneRequest)
}