package com.app.garant.presenter.screens.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.presenter.adapters.OrderAdapter
import com.app.garant.databinding.ScreenBasketBinding
import com.app.garant.presenter.dialogs.DialogCleanBasket
import com.app.garant.models.OrderData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BasketScreen : Fragment(R.layout.screen_basket) {

    private val bind by viewBinding(ScreenBasketBinding::bind)
    private val orderData = StaticValue.orderData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderAdapter = OrderAdapter(orderData)
        val layoutManager = LinearLayoutManager(requireActivity())


        bind.basketRV.adapter = orderAdapter
        bind.basketRV.layoutManager = layoutManager


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
        month_btn()
    }


    private fun month_btn() {
        val param = LinearLayout.LayoutParams(
            170,
            170,
            0F
        )

        val paramDef = LinearLayout.LayoutParams(
            210,
            170,
            0F
        )
        param.setMargins(15, 0, 15, 0)
        paramDef.setMargins(10, 0, 10, 0)


        bind.apply {

            button1Month.layoutParams = param
            button3Month.layoutParams = param
            button6Month.layoutParams = param
            button9Month.layoutParams = param
            button12Month.layoutParams = param

            button1Month.setOnClickListener {
                button1Month.layoutParams = paramDef
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }

            button3Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = paramDef
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }
            button6Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = paramDef
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }
            button9Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = paramDef
                button12Month.layoutParams = param
            }
            button12Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = paramDef
            }
        }
    }

}