package com.app.garant.data.api

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.auth.VerifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body data: LoginRequest):Response<LoginResponse>

    @POST("auth/verify")
    suspend fun verify(@Body data: LoginRequest): Response<VerifyResponse>

}