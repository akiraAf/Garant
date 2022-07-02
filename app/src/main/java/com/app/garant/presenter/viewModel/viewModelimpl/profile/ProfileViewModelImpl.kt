package com.app.garant.presenter.viewModel.viewModelimpl.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.auth.LogoutResponse
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.presenter.viewModel.profile.ProfileViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(private val repository: AuthRepository) :
    ViewModel(), ProfileViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val successFlow = eventValueFlow<LogoutResponse>()

    override fun getLogout() {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }

        repository.logout().onEach { value ->
            value.onSuccess { it ->
                progressFlow.emit(false)
                successFlow.emit(it)
            }

            value.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)

    }
}