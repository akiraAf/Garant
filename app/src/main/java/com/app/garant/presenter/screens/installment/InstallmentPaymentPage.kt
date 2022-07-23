package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentPaymentBinding
import com.app.garant.presenter.adapters.installment.InstallmentPaymentAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class InstallmentPaymentPage : Fragment(R.layout.page_installment_payment) {
    private val bind by viewBinding(PageInstallmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paymentAdapter = InstallmentPaymentAdapter()

        bind.paymentRV.adapter = paymentAdapter
        bind.paymentRV.layoutManager = LinearLayoutManager(activity)
    }


}