package com.app.garant.presenter.viewModel.viewModelimpl.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.other.StaticValue
import com.app.garant.data.response.branch.BranchResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.other.BranchScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), BranchScreenViewModel {

    override val successFlowBranch = eventValueFlow<BranchResponse>()
    override val progressFlowBranch = eventValueFlow<Boolean>()
    override val errorFlowBranch = eventValueFlow<String>()

    override fun getBrand() {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlowBranch.emit(true)
        }

        categoryRepository.getBranch().onEach {
            it.onSuccess {
                progressFlowBranch.emit(false)
                successFlowBranch.emit(it)
            }
            it.onFailure { throwable ->
                progressFlowBranch.emit(false)
                errorFlowBranch.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }
}