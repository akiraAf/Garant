package com.app.garant.presenter.screens.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.OrderAdapter
import com.app.garant.databinding.ScreenBasketBinding
import com.app.garant.presenter.dialogs.DialogCleanBasket
import com.app.garant.models.OrderData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BasketScreen : Fragment(R.layout.screen_basket) {

    private val bind by viewBinding(ScreenBasketBinding::bind)
    private val orderData = ArrayList<OrderData>()
    private val orderAdapter = OrderAdapter(orderData)
    private val layoutManager = LinearLayoutManager(activity)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        bind.basketRV.adapter = orderAdapter
        bind.basketRV.layoutManager = layoutManager

        bind.unavailableRV.adapter = orderAdapter
        bind.unavailableRV.layoutManager = layoutManager


        bind.cleanBasket.setOnClickListener {
            val dialog = DialogCleanBasket()
            dialog.show(childFragmentManager, "clean_basket")
        }

        bind.buyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_basketPage_to_nav_ordering)
        }

        bind.bookInstallment.setOnClickListener {
            findNavController().navigate(R.id.action_basketPage_to_checkBasketPage)
        }

    }

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


}