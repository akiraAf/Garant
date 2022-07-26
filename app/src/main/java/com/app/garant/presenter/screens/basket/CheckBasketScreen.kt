package com.app.garant.presenter.screens.basket

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenBasketCheckBinding
import com.app.garant.presenter.adapters.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class CheckBasketScreen : Fragment(R.layout.screen_basket_check) {

    private val bind by viewBinding(ScreenBasketCheckBinding::bind)
    private val orderAdapter by lazy { CartAdapter() }
    private val args by navArgs<CheckBasketScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrayList = args.cartProducts

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.bookInstallment.setOnClickListener {
            val action: NavDirections =
                CheckBasketScreenDirections.actionCheckBasketPageToAccountScreen(true)
            findNavController().navigate(action)
        }
        orderAdapter.submitList(arrayList.toList())

        bind.basketRV.adapter = orderAdapter
        bind.basketRV.layoutManager = LinearLayoutManager(requireContext())

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