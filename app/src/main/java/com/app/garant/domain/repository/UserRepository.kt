package com.app.garant.domain.repository

import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.request.profile.request.UserRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.data.response.profile.account.DocumentResponse
import com.app.garant.data.response.profile.account.UserResponse
import com.app.garant.data.response.profile.account.regions.RegionResponse
import com.app.garant.data.response.profile.account.regions_names.RegionsNameResponse
import com.app.garant.data.response.profile.profession.ProfessionResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun changePhone(changePhoneRequest: ChangePhoneRequest): Flow<Result<ChangePhoneResponse>>

    fun updatePhone(updatePhoneRequest: UpdatePhoneRequest): Flow<Result<UpdatePhoneResponce>>

    fun getRegion(): Flow<Result<RegionResponse>>

    fun getRegionNames(): Flow<Result<RegionsNameResponse>>

    fun getProfession(): Flow<Result<ProfessionResponse>>

    fun sendDocuments(documentRequest: DocumentRequest): Flow<Result<DocumentResponse>>

    fun sendUserInfo(userInfo: UserRequest): Flow<Result<UserResponse>>
}