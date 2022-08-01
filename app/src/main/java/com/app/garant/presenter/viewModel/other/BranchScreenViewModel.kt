package com.app.garant.presenter.viewModel.other

import com.app.garant.data.response.branch.BranchResponse
import kotlinx.coroutines.flow.Flow

interface BranchScreenViewModel {

    val errorFlowBranch: Flow<String>
    val successFlowBranch: Flow<BranchResponse>
    val progressFlowBranch: Flow<Boolean>
    fun getBrand()
}