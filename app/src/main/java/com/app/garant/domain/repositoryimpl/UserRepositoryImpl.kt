package com.app.garant.domain.repositoryimpl

import com.app.garant.data.api.AuthApi
import com.app.garant.data.api.UserApi
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: UserApi, private val pref: MyPref) :
    UserRepository {
    override fun changePhone(changePhoneRequest: ChangePhoneRequest): Flow<Result<ChangePhoneResponse>> =
        flow {
            val response = api.changePhone(changePhoneRequest)

            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override fun updatePhone(updatePhoneRequest: UpdatePhoneRequest): Flow<Result<UpdatePhoneResponce>> =
        flow {
            val response = api.updatePhone(updatePhoneRequest)

            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)
}