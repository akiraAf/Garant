package com.app.garant.presenter.screens.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.BranchAdapter
import com.app.garant.databinding.ScreenBranchSelectionBinding
import com.app.garant.presenter.viewModel.other.BranchScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.other.BranchScreenViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint


class BranchScreen : Fragment(R.layout.screen_branch_selection) {
    private val bind by viewBinding(ScreenBranchSelectionBinding::bind)
    private val branchAdapter by lazy { BranchAdapter() }
    private val viewModel: BranchScreenViewModel by viewModels<BranchScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBrand()

        viewModel.successFlowBranch.onEach {
            branchAdapter.submitList(it.data)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlowBranch.onEach {
            showToast(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        bind.branchRV.layoutManager = LinearLayoutManager(requireContext())
        bind.branchRV.adapter = branchAdapter
        bind.choice.setOnClickListener {

        }
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.choice.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}