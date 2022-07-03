package com.app.garant.data.api

import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("user/change-phone")
    suspend fun changePhone(@Body data: ChangePhoneRequest): Response<ChangePhoneResponse>

    @POST("user/update-phone")
    suspend fun updatePhone(@Body data: UpdatePhoneRequest): Response<UpdatePhoneResponce>
}