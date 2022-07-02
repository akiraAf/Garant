package com.app.garant.presenter.viewModel.viewModelimpl.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.presenter.viewModel.profile.UpdatePhoneViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePhoneViewModelImpl @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel(), UpdatePhoneViewModel {


    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<UpdatePhoneResponce>()
    override val progressFlow = eventValueFlow<Boolean>()


    override fun updatePhone(request: UpdatePhoneRequest) {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }
        authRepository.updatePhone(request).onEach {
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