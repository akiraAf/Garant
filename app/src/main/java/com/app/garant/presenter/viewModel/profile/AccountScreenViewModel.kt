package com.app.garant.presenter.viewModel.profile

import android.net.Uri
import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.data.request.profile.request.UserRequest
import com.app.garant.data.response.profile.account.DocumentResponse
import com.app.garant.data.response.profile.account.UserResponse
import com.app.garant.data.response.profile.account.regions.RegionResponse
import kotlinx.coroutines.flow.Flow

interface AccountScreenViewModel {

    val errorFlow: Flow<String>
    val successFlow: Flow<RegionResponse>
    val progressFlow: Flow<Boolean>
    val successFlowRegion: Flow<ArrayList<String>>
    val successFlowCity: Flow<ArrayList<String>>
    val successFlowDistrict: Flow<ArrayList<String>>
    val successFlowDistrictId: Flow<ArrayList<Int>>
    val successFlowProfession: Flow<ArrayList<String>>
    val successFlowProfessionId: Flow<ArrayList<Int>>
    val successFlowDoc: Flow<DocumentResponse>
    val successFlowUserInfo: Flow<UserResponse>

    fun getRegionsName()
    fun getRegionCity(region: String, city: String)
    fun getProfession()
    fun sendDocuments(request: DocumentRequest)
    fun sendUserInfo(userInfo: UserRequest)

}