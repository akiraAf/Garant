package com.app.garant.presenter.viewModel.viewModelimpl.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.auth.VerifyRequest
import com.app.garant.data.response.auth.VerifyResponse
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.presenter.viewModel.auth.VerifyViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModelImpl @Inject constructor(private val authRepository: AuthRepository) :
    VerifyViewModel,
    ViewModel() {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<VerifyResponse>()
    override val progressFlow = eventValueFlow<Boolean>()


    override fun sendVerify(request: VerifyRequest) {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }
        authRepository.verify(request).onEach {
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