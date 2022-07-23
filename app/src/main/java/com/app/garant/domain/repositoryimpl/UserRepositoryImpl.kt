package com.app.garant.domain.repositoryimpl


import com.app.garant.data.api.UserApi
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.request.profile.request.UserRequest
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.data.response.profile.account.DocumentResponse
import com.app.garant.data.response.profile.account.UserResponse
import com.app.garant.data.response.profile.account.regions.RegionResponse
import com.app.garant.data.response.profile.account.regions_names.RegionsNameResponse
import com.app.garant.data.response.profile.profession.ProfessionResponse
import com.app.garant.domain.repository.UserRepository
import com.app.garant.utils.toRequestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: UserApi, private val pref: MyPref) :
    UserRepository {
    private var regionResponse: RegionResponse? = null

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


    override fun getRegion(): Flow<Result<RegionResponse>> = flow<Result<RegionResponse>> {
        val response = api.getRegion()
        if (response.isSuccessful) {
            regionResponse = response.body()!!
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)


    override fun getRegionNames(): Flow<Result<RegionsNameResponse>> = flow {
        val response = api.getRegionsName()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)


    override fun getProfession(): Flow<Result<ProfessionResponse>> = flow {
        val response = api.getProfession()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)


    override fun sendDocuments(documentRequest: DocumentRequest): Flow<Result<DocumentResponse>> =
        flow {
            val response = api.sendPassport(
                documentRequest.type.toRequestBody("text/plain".toMediaTypeOrNull()),
                documentRequest.file.toRequestData("image")
            )
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override fun sendUserInfo(userInfo: UserRequest): Flow<Result<UserResponse>> =
        flow {
            val response = api.sendUserInfo(userInfo)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)




}