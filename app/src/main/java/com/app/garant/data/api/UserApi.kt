package com.app.garant.data.api

import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.request.profile.request.UserRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.data.response.profile.account.DocumentResponse
import com.app.garant.data.response.profile.account.UserResponse
import com.app.garant.data.response.profile.account.regions.RegionResponse
import com.app.garant.data.response.profile.account.regions_names.RegionsNameResponse
import com.app.garant.data.response.profile.account.user_info.UserInfoResponse
import com.app.garant.data.response.profile.profession.ProfessionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("user/change-phone")
    suspend fun changePhone(@Body data: ChangePhoneRequest): Response<ChangePhoneResponse>

    @POST("user/update-phone")
    suspend fun updatePhone(@Body data: UpdatePhoneRequest): Response<UpdatePhoneResponce>

    @GET("user")
    suspend fun getUserInfo(): Response<UserInfoResponse>

    @GET("region")
    suspend fun getRegion(): Response<RegionResponse>

    @GET("region/list")
    suspend fun getRegionsName(): Response<RegionsNameResponse>

    @GET("profession")
    suspend fun getProfession(): Response<ProfessionResponse>

    @Multipart
    @POST("document")
    suspend fun sendPassport(
        @Part("type") type: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<DocumentResponse>

    @POST("request")
    suspend fun sendUserInfo(@Body data: UserRequest): Response<UserResponse>

}