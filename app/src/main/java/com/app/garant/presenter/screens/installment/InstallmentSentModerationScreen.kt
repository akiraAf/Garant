package com.app.garant.presenter.screens.installment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.PageInstallmentSentModerationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentSentModerationScreen : Fragment(R.layout.page_installment_sent_moderation) {

    private val bind by viewBinding(PageInstallmentSentModerationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.button.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("GO_TO_MAIN", true)
            findNavController().navigate(
                R.id.action_installmentSentModerationScreen_to_basketPage,
                bundle
            )
        }
    }

}