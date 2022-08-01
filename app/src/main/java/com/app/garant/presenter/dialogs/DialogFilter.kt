package com.app.garant.presenter.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.DialogFilterBinding
import com.app.garant.presenter.adapters.BrandAdapter
import com.app.garant.presenter.viewModel.dialogs.DialogFilterViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.dialogs.DialogFilterViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DialogFilter : DialogFragment(R.layout.dialog_filter) {
    private val bind by viewBinding(DialogFilterBinding::bind)

    private val viewModel: DialogFilterViewModel by viewModels<DialogFilterViewModelImpl>()
    private var use: ((Unit) -> Unit)? = null
    private var reset: ((Unit) -> Unit)? = null
    private val brandAdapter by lazy { BrandAdapter() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getBrand()
        viewModel.successFlowGetBrand.onEach {
            brandAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        bind.yesBtn
            .setOnClickListener {
                use?.invoke(Unit)
            }

        bind.noBtn
            .setOnClickListener {
                reset?.invoke(Unit)
            }


        bind.recycler.adapter = brandAdapter
        bind.recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    fun setUse(f: (Unit) -> Unit) {
        use = f
    }

    fun setReset(f: (Unit) -> Unit) {
        reset = f
    }
}