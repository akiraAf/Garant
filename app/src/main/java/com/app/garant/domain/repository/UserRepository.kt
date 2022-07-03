package com.app.garant.domain.repository

import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun changePhone(changePhoneRequest: ChangePhoneRequest): Flow<Result<ChangePhoneResponse>>

    fun updatePhone(updatePhoneRequest: UpdatePhoneRequest): Flow<Result<UpdatePhoneResponce>>
}