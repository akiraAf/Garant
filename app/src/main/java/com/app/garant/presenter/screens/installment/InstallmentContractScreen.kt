package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.OrderAdapter
import com.app.garant.databinding.PageInstallmentContractBinding
import com.app.garant.models.OrderData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentContractScreen : Fragment(R.layout.page_installment_contract) {

    private val bind by viewBinding(PageInstallmentContractBinding::bind)
    private val orderData = ArrayList<OrderData>()

    private fun initData() {
        for (i in 1..4)
            orderData.add(
                OrderData(
                    "Apple iPhone 12\n128GB",
                    "10 700 000 сум",
                    "3 шт."
                )
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
   //     bind.orderRV.adapter = OrderAdapter(orderData, true)
        bind.orderRV.layoutManager = LinearLayoutManager(activity)
    }

}