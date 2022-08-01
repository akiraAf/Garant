package com.app.garant.presenter.viewModel.viewModelimpl.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.brand.BrandResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.dialogs.DialogFilterViewModel
import com.app.garant.utils.eventValueFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogFilterViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : DialogFilterViewModel, ViewModel() {


    override val successFlowGetBrand = eventValueFlow<BrandResponse>()
    override val progressFlowGetBrand = eventValueFlow<Boolean>()
    override val errorFlowGetBrand = eventValueFlow<String>()


    override fun getBrand() {
        viewModelScope.launch {

            progressFlowGetBrand.emit(true)

            categoryRepository.getBrand().collect {
                it.onSuccess {
                    progressFlowGetBrand.emit(false)
                    successFlowGetBrand.emit(it)
                }
                it.onFailure { throwable ->
                    progressFlowGetBrand.emit(false)
                    errorFlowGetBrand.emit(throwable.message.toString())
                }
            }
        }
    }

}