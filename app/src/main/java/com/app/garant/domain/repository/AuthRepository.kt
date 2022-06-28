package com.app.garant.domain.repository

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(loginRequest: LoginRequest): Flow<Result<LoginResponse>>
}