package com.app.garant.presenter.viewModel.auth

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthorizationViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<LoginResponse>
    val progressFlow: Flow<Boolean>
    fun login(request: LoginRequest)
}