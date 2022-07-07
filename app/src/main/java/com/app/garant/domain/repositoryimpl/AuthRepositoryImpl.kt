package com.app.garant.domain.repositoryimpl

import com.app.garant.app.App
import com.app.garant.data.api.AuthApi
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.request.auth.VerifyRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.auth.LogoutResponse
import com.app.garant.data.response.auth.MeResponse
import com.app.garant.data.response.auth.VerifyResponse
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
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
        val response = api.login(loginRequest)

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)


    override fun verify(verifyRequest: VerifyRequest): Flow<Result<VerifyResponse>> = flow {
        val response = api.verify(verifyRequest)

        if (response.isSuccessful) {
            pref.access_token = response.body()!!.token
            pref.authControll = true
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)


    override fun logout(): Flow<Result<LogoutResponse>> = flow {
        val response = api.logout()
        emit(Result.success(response.body()!!))
        if (response.isSuccessful) {
            pref.authControll = false
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        val errorMessage = Throwable("Проблемы с сервером")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


    override fun me(): Flow<Result<MeResponse>> = flow {
        val response = api.me()

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        val errorMessage = Throwable("Проблемы с сервером")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


}