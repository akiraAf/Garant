package com.app.garant.presenter.screens.basket

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.databinding.ScreenBasketBinding
import com.app.garant.presenter.adapters.CartAdapter
import com.app.garant.presenter.dialogs.DialogCleanBasket
import com.app.garant.presenter.dialogs.DialogDeleteItemBasket
import com.app.garant.presenter.viewModel.cart.CartViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.cart.CartScreenViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint
class CartScreen : Fragment(R.layout.screen_basket) {

    private val bind by viewBinding(ScreenBasketBinding::bind)
    private val viewModel: CartViewModel by viewModels<CartScreenViewModelImpl>()
    private val numberFormat = NumberFormat.getNumberInstance(Locale.CANADA)
    private val cartAdapter = CartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCart()

        viewModel.successFlowCart.onEach {
            cartAdapter.submitList(it.products)
            bind.basketRV.adapter = cartAdapter
            bind.basketRV.layoutManager = layoutManager
            bind.amountProducts.text = it.count.toString() + " штук"
            bind.fullPayment.text = price_converter(it.total_price.toLong())
            bind.monthPay.text = price_converter(it.monthly_price.toLong())
            bind.costTv.text = price_converter(it.total_price.toLong())
            month_btns(it.month.id)
            navigation()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlowDelete.onEach {
            Log.i("LOL", it)
        }.launchIn(lifecycleScope)

        cartAdapter.setDeleteListenerClick { id, index ->
            val dialog = DialogDeleteItemBasket()
            dialog.show(parentFragmentManager, "DialogLogOut")
            dialog.setNo {
                dialog.dismiss()
            }
            dialog.setYes {
                viewModel.deleteCart(
                    CartDeleteRequest(id)
                )
                viewModel.successFlowDelete.onEach {
                    cartAdapter.notifyItemRemoved(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
                dialog.dismiss()
            }
        }

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

    private fun navigation() {

        bind.cleanBasket.setOnClickListener {
            val dialog = DialogCleanBasket()
            dialog.show(parentFragmentManager, "clean_basket")
            dialog.setNo {
                dialog.dismiss()
            }
            dialog.setYes {
                viewModel.deleteAllCart()
                findNavController().navigate(R.id.cartEmptyPage)
                dialog.dismiss()
            }
        }

        bind.buyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_basketPage_to_nav_ordering)
        }

        bind.bookInstallment.setOnClickListener {
            findNavController().navigate(R.id.action_basketPage_to_checkBasketPage)
        }
    }

    private fun price_converter(price: Long): String {
        numberFormat.maximumFractionDigits = 0;
        val convert = numberFormat.format(price)
        return (convert.replace(",", " ") + " cум")
    }


}