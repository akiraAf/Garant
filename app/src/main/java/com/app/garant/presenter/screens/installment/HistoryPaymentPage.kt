package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.HistoryPaymentAdapter
import com.app.garant.databinding.PageHistotyPaymentBinding
import com.app.garant.models.PaymentInfo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class HistoryPaymentPage : Fragment(R.layout.page_histoty_payment) {

    private val bind by viewBinding(PageHistotyPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data: ArrayList<PaymentInfo> = ArrayList()

        for (i in 1..6){
            data.add(PaymentInfo(""+i,"21 март 2022 18:24", "1 700 000 сум", "0",
                R.drawable.ic_check
            ))
        }
        val paymentAdapter = HistoryPaymentAdapter(data)

        bind.recycler.adapter = paymentAdapter
        bind.recycler.layoutManager = LinearLayoutManager(activity)
    }


}