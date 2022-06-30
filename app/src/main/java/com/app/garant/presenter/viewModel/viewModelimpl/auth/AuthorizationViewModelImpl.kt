package com.app.garant.presenter.viewModel.viewModelimpl.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.presenter.viewModel.auth.AuthorizationViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModelImpl @Inject constructor(private val authRepository: AuthRepository)

    : ViewModel(), AuthorizationViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<Unit>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun login(request: LoginRequest) {
        if (!isConnected()) {
            return
        }
        authRepository.login(request).onEach {
            it.onSuccess {
                successFlow.emit(Unit)
                progressFlow.emit(false)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}