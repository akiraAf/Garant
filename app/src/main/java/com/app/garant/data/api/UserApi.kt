package com.app.garant.data.api

import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.data.response.profile.account.DocumentResponse
import com.app.garant.data.response.profile.account.regions.RegionResponse
import com.app.garant.data.response.profile.account.regions_names.RegionsNameResponse
import com.app.garant.data.response.profile.profession.ProfessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.File

interface UserApi {

    @POST("user/change-phone")
    suspend fun changePhone(@Body data: ChangePhoneRequest): Response<ChangePhoneResponse>

    @POST("user/update-phone")
    suspend fun updatePhone(@Body data: UpdatePhoneRequest): Response<UpdatePhoneResponce>

    @GET("region")
    suspend fun getRegion(): Response<RegionResponse>

    @GET("region/list")
    suspend fun getRegionsName(): Response<RegionsNameResponse>

    @GET("profession")
    suspend fun getProfession(): Response<ProfessionResponse>

    @POST("document")
    suspend fun sendPassport(@Body data: DocumentRequest): Response<DocumentResponse>

}