package com.app.garant.data.api

import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.request.auth.VerifyRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.auth.LogoutResponse
import com.app.garant.data.response.auth.VerifyResponse
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body data: LoginRequest): Response<LoginResponse>

    @POST("auth/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<VerifyResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<LogoutResponse>

}