package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.HistoryInstallmentPaymentAdapter
import com.app.garant.databinding.PageInstallmentHistoryPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentHistoryPaymentPage : Fragment(R.layout.page_installment_history_payment) {
    private val bind by viewBinding(PageInstallmentHistoryPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.historyPaymentRV.adapter = HistoryInstallmentPaymentAdapter()
        bind.historyPaymentRV.layoutManager = LinearLayoutManager(activity)
    }

}