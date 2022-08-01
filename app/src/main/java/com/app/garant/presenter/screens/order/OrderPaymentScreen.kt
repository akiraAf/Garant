package com.app.garant.presenter.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageOrderingPaymentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class OrderPaymentScreen : Fragment(R.layout.page_ordering_payment) {

    private val bind by viewBinding(PageOrderingPaymentBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   bind.orderRV.adapter = OrderAdapter(orderData, true)
        bind.orderRV.layoutManager = LinearLayoutManager(activity)

        bind.pay.setOnClickListener {
            findNavController().navigate(R.id.action_orderingPaymentPage_to_orderProcessingPage)
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}