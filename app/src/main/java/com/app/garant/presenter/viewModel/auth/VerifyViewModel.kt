package com.app.garant.presenter.viewModel.auth

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.request.auth.VerifyRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.auth.VerifyResponse
import kotlinx.coroutines.flow.Flow

interface VerifyViewModel {
    val errorFlow: Flow<String>
    val errorFlowLog: Flow<String>
    val successFlow: Flow<VerifyResponse>
    val successFlowLog: Flow<LoginResponse>
    val progressFlow: Flow<Boolean>
    fun sendVerify(request: VerifyRequest)
    fun login(request: LoginRequest)
}