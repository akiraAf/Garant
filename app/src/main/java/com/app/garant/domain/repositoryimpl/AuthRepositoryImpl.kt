package com.app.garant.domain.repositoryimpl

import com.app.garant.data.api.AuthApi
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi, private val pref: MyPref) :
    AuthRepository {


    override fun login(loginRequest: LoginRequest): kotlinx.coroutines.flow.Flow<Result<LoginResponse>> =
        flow {
            val response = api.login(loginRequest)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.message())))
            }
        }.catch() {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)
}