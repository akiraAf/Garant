package com.app.garant.presenter.viewModel.profile

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.profile.ChangePhoneResponse
import kotlinx.coroutines.flow.Flow

interface ChangeNumberViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<ChangePhoneResponse>
    val progressFlow: Flow<Boolean>
    fun changeNumber(request: ChangePhoneRequest)
}