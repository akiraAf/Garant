package com.app.garant.presenter.viewModel.viewModelimpl.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.data.response.profile.ChangePhoneResponse
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.domain.repository.UserRepository
import com.app.garant.presenter.viewModel.profile.ChangeNumberViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeNumberViewModelImpl @Inject constructor(private val userRepository: UserRepository) :
    ViewModel(),
    ChangeNumberViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<ChangePhoneResponse>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun changeNumber(request: ChangePhoneRequest) {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }
        userRepository.changePhone(request).onEach {
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