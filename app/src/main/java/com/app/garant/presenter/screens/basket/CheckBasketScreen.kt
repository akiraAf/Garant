package com.app.garant.presenter.screens.basket

import android.icu.text.NumberFormat
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.databinding.ScreenBasketCheckBinding
import com.app.garant.presenter.adapters.CartAdapter
import com.app.garant.presenter.adapters.cart.CheckCartAdapter
import com.app.garant.presenter.viewModel.cart.CartViewModel
import com.app.garant.presenter.viewModel.cart.CheckCartViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.cart.CartScreenViewModelImpl
import com.app.garant.presenter.viewModel.viewModelimpl.cart.CheckCartScreenViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint

class CheckBasketScreen : Fragment(R.layout.screen_basket_check) {

    private val bind by viewBinding(ScreenBasketCheckBinding::bind)
    private val orderAdapter by lazy { CheckCartAdapter() }
    private val viewModel: CheckCartViewModel by viewModels<CheckCartScreenViewModelImpl>()
    private val numberFormat = NumberFormat.getNumberInstance(Locale.CANADA)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.bookInstallment.setOnClickListener {
            val action: NavDirections =
                CheckBasketScreenDirections.actionCheckBasketPageToAccountScreen(true)
            findNavController().navigate(action)
        }
        viewModel.getCart()
        viewModel.successFlowAv.onEach {
            orderAdapter.submitList(it)
            bind.basketRV.adapter = orderAdapter
            bind.basketRV.layoutManager = LinearLayoutManager(requireContext())
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlow.onEach {

            bind.monthPayment.text = price_converter(it.monthly_price.toLong())
            bind.totalPrice.hint = price_converter(it.total_price.toLong())
            month_btns(it.month.id)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun month_btns(id: Int) {


        val param = LinearLayout.LayoutParams(
            170,
            170,
            0F
        )

        val paramDef = LinearLayout.LayoutParams(
            250,
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


            button1Month.isEnabled = false
            button3Month.isEnabled = false
            button6Month.isEnabled = false
            button9Month.isEnabled = false
            button12Month.isEnabled = false


            when (id) {
                5 -> {
                    bind.button12Month.isChecked = true
                    button12Month.layoutParams = paramDef
                }
                4 -> {
                    bind.button9Month.isChecked = true
                    button9Month.layoutParams = paramDef
                }
                3 -> {
                    bind.button6Month.isChecked = true
                    button6Month.layoutParams = paramDef
                }
                2 -> {
                    bind.button3Month.isChecked = true
                    button3Month.layoutParams = paramDef
                }
                1 -> {
                    bind.button1Month.isChecked = true
                    button1Month.layoutParams = paramDef
                }
            }
        }
    }

    private fun price_converter(price: Long): String {
        numberFormat.maximumFractionDigits = 0;
        val convert = numberFormat.format(price)
        return (convert.replace(",", " ") + " cум")
    }

}