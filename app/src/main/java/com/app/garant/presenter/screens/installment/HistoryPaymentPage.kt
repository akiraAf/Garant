package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.installment.HistoryPaymentAdapter
import com.app.garant.databinding.PageHistotyPaymentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class HistoryPaymentPage : Fragment(R.layout.page_histoty_payment) {

    private val bind by viewBinding(PageHistotyPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.recycler.layoutManager = LinearLayoutManager(activity)
    }


}