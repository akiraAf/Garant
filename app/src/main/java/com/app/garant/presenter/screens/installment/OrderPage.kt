package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.OrderAdapter
import com.app.garant.databinding.PageOrderBinding
import com.app.garant.models.OrderData

class OrderPage : Fragment(R.layout.page_order) {
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
    private val bind by viewBinding(PageOrderBinding::bind)
    private  val orderAdapter by lazy {  OrderAdapter(orderData) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bind.orderRV.layoutManager = LinearLayoutManager(activity)
        bind.orderRV.adapter = orderAdapter

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}