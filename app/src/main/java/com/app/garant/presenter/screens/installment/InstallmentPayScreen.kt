package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentPayBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class InstallmentPayScreen : Fragment(R.layout.page_installment_pay) {
   private val bind by viewBinding(PageInstallmentPayBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
        bind.pay.setOnClickListener {
            findNavController().navigate(R.id.action_installmentPayPage_to_paymentPassedPage)
        }
    }
}