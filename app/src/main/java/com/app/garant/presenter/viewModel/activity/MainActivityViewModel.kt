package com.app.garant.presenter.viewModel.activity

import kotlinx.coroutines.flow.Flow

interface MainActivityViewModel {
    val screenController: Flow<Unit>
    val errorFlow: Flow<Unit>

    fun chekLogin()
}