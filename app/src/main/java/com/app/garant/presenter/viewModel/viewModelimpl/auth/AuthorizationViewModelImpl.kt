package com.app.garant.presenter.viewModel.viewModelimpl.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.presenter.viewModel.auth.AuthorizationViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModelImpl @Inject constructor(private val authRepository: AuthRepository)

    : ViewModel(), AuthorizationViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<LoginResponse>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun login(request: LoginRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo bo'ldi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        authRepository.login(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)

    }


}