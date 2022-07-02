package com.app.garant.domain.repository

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.request.auth.VerifyRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.auth.LogoutResponse
import com.app.garant.data.response.auth.VerifyResponse
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(loginRequest: LoginRequest): Flow<Result<LoginResponse>>

    fun verify(verifyRequest: VerifyRequest): Flow<Result<VerifyResponse>>

    fun logout(): Flow<Result<LogoutResponse>>

    fun changePhone(changePhoneRequest: ChangePhoneRequest): Flow<Result<ChangePhoneResponse>>

    fun updatePhone(updatePhoneRequest: UpdatePhoneRequest): Flow<Result<UpdatePhoneResponce>>
}