package com.app.garant.presenter.viewModel.profile

import com.app.garant.data.response.auth.LogoutResponse
import kotlinx.coroutines.flow.Flow

interface ProfileViewModel {
    val errorFlow: Flow<String>
    val progressFlow: Flow<Boolean>
    val successFlow: Flow<LogoutResponse>
    fun getLogout()
}