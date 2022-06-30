package com.app.garant.domain.repositoryimpl

import com.app.garant.data.api.AuthApi
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi, private val pref: MyPref) :
    AuthRepository {

    override fun login(loginRequest: LoginRequest): Flow<Result<LoginResponse>> = flow {
        val responce = api.login(loginRequest)
        if (responce.isSuccessful) {
            emit(Result.success<LoginResponse>(responce.body()!!))
        } else {
            emit(Result.failure(Throwable(responce.errorBody().toString())))
        }
    }.catch {
//        val errorMessage = Throwable("Sever bilan muammo bo'ldi")
        val errorMessage = Throwable(it.message)
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)
}