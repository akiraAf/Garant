package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.OrderAdapter
import com.app.garant.databinding.PageInstallmentCreationBinding
import com.app.garant.models.OrderData


class InstallmentCreationPage : Fragment(R.layout.page_installment_creation) {

    private val bind by viewBinding(PageInstallmentCreationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.next.setOnClickListener {
            findNavController().navigate(R.id.action_installmentCreationPage_to_installmentDeliveryPage)
        }
        val data =ArrayList<OrderData>()
        data.add(OrderData("Apple iPhone 12\n128GB", "10 700 000 сум", "3 шт."))
        data.add(OrderData("Apple iPhone 12\n128GB", "10 700 000 сум", "3 шт."))
        data.add(OrderData("Apple iPhone 12\n128GB", "10 700 000 сум", "3 шт."))
        data.add(OrderData("Apple iPhone 12\n128GB", "10 700 000 сум", "3 шт."))

        val orderAdapter by lazy { OrderAdapter(data, true) }

        bind.recycler.adapter = orderAdapter
        bind.recycler.layoutManager = LinearLayoutManager(activity)
    }

}