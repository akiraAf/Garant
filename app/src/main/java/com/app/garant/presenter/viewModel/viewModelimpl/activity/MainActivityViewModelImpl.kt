package com.app.garant.presenter.viewModel.viewModelimpl.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.domain.repository.AuthRepository
import com.app.garant.presenter.viewModel.activity.MainActivityViewModel
import com.app.garant.utils.eventValueFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModelImpl @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel(),
    MainActivityViewModel {

    override val errorFlow = eventValueFlow<Unit>()
    override val screenController = eventValueFlow<Unit>()

    override fun chekLogin() {
        TODO("Not yet implemented")
    }


}