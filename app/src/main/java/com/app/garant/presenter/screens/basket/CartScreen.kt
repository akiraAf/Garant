package com.app.garant.presenter.screens.basket

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.data.response.cart.CartParchRequest
import com.app.garant.data.response.cart.Product
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
    private val cartAdapter by lazy { CartAdapter() }
    private val cartAdapterUn by lazy { CartAdapter() }
    private var available = ArrayList<Product>()
    private var unavailable = ArrayList<Product>()
    private val args by navArgs<CartScreenArgs>()
    private var flag = false
    private var countFlag = true
    private var countReq = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        flag = arguments!!.getBoolean("GO_TO_MAIN")

        bind.goToCatalog.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_catalog)
        }

        viewModel.errorPut.onEach {
            Log.i("LOL", it + "CartScreen")
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successPut.onEach {
            delay(3000)
            viewModel.getCart()
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        StaticValue.cartAmount.value = Unit
        viewModel.getCart()


        viewModel.progressFlowProduct.onEach {
            bind.progress.visibility = View.VISIBLE
        }.launchIn(lifecycleScope)


        viewModel.errorFlowProduct.onEach {
            bind.progress.visibility = View.GONE
            bind.emptyCart.visibility = View.VISIBLE
            bind.NestedScrollView.visibility = View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.successFlowCartAvailable.onEach {
            bind.progress.visibility = View.GONE
            if (it.isNotEmpty()) {
                bind.emptyCart.visibility = View.GONE
                bind.NestedScrollView.visibility = View.VISIBLE
            }
            available = it
            cartAdapter.submitList(available)
            bind.basketRV.adapter = cartAdapter
            bind.basketRV.layoutManager = layoutManager

            cartAdapter.setAddListenerClick { count, product_id, absoluteAdapterPosition ->
                if (countReq < 20) {
                    viewModel.countCart(CartParchRequest(product_id = product_id, count = count))
                    viewModel.successPatch.onEach {
                        delay(2000).apply {
                            viewModel.getCart()
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                    ++countReq
                }
            }
            cartAdapter.setRemoveListenerClick { count, product_id, absoluteAdapterPosition ->
                if (countReq < 20) {
                    viewModel.countCart(CartParchRequest(product_id = product_id, count = count))
                    viewModel.successPatch.onEach {
                        delay(2000).apply {
                            viewModel.getCart()
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                    ++countReq
                }
            }


            bind.bookInstallment.setOnClickListener {
                val action: NavDirections =
                    CartScreenDirections.actionBasketPageToCheckBasketPage(
                        available.toTypedArray()
                    )
                findNavController().navigate(action)
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlowCartUnavailable.onEach {
            bind.progress.visibility = View.GONE
            if (it.isNotEmpty()) {
                bind.emptyCart.visibility = View.GONE
                bind.NestedScrollView.visibility = View.VISIBLE
            }
            unavailable = it
            if (it.isNotEmpty()) {
                bind.unavailableTv.visibility = View.VISIBLE
                cartAdapterUn.submitList(unavailable)
                bind.unavailableRV.adapter = cartAdapterUn
                bind.unavailableRV.layoutManager = LinearLayoutManager(requireContext())
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.successFlowCart.onEach {
            bind.amountProducts.text = it.count.toString() + " штук"
            bind.fullPayment.text = price_converter(it.total_price.toLong())
            bind.monthPay.text = price_converter(it.monthly_price.toLong())
            bind.costTv.text = price_converter(it.total_price.toLong())
            month_btns(it.month.id)
            navigation()
            if (flag)
                findNavController().navigate(R.id.nav_main)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlowDelete.onEach {
            Log.i("LOL", it)
        }.launchIn(lifecycleScope)

        cartAdapter.setDeleteListenerClick { id, index ->
            val dialog = DialogDeleteItemBasket()
            dialog.show(parentFragmentManager, "DialogDelete")
            dialog.setNo {
                dialog.dismiss()
            }
            dialog.setYes {
                viewModel.deleteCart(
                    CartDeleteRequest(id)
                )
                viewModel.errorFlowDeleteAvailable.onEach {
                    delay(1000)
                    isEmptyArray()
                    cartAdapter.notifyDataSetChanged()
                }.launchIn(viewLifecycleOwner.lifecycleScope)
                dialog.dismiss()
            }
        }

        cartAdapterUn.setDeleteListenerClick { id, index ->
            val dialog = DialogDeleteItemBasket()
            dialog.show(parentFragmentManager, "DialogDelete")
            dialog.setNo {
                dialog.dismiss()
            }
            dialog.setYes {
                viewModel.deleteCart(
                    CartDeleteRequest(id)
                )
                viewModel.errorFlowDeleteAvailable.onEach {
                    delay(1000)
                    isEmptyArray()
                    cartAdapterUn.notifyDataSetChanged()
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
                viewModel.putCartMonth(CartMonthRequest(1))
            }

            button3Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = paramDef
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
                viewModel.putCartMonth(CartMonthRequest(2))
            }
            button6Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = paramDef
                button9Month.layoutParams = param
                button12Month.layoutParams = param
                viewModel.putCartMonth(CartMonthRequest(3))
            }
            button9Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = paramDef
                button12Month.layoutParams = param
                viewModel.putCartMonth(CartMonthRequest(4))
            }
            button12Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = paramDef
                viewModel.putCartMonth(CartMonthRequest(5))
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
                bind.emptyCart.visibility = View.VISIBLE
                bind.NestedScrollView.visibility = View.GONE
                StaticValue.cartCheck = true
                dialog.dismiss()
            }
        }

        bind.buyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_basketPage_to_nav_ordering)
        }


    }

    private fun price_converter(price: Long): String {
        numberFormat.maximumFractionDigits = 0;
        val convert = numberFormat.format(price)
        return (convert.replace(",", " ") + " cум")
    }

    private fun isEmptyArray() {
        if (unavailable.isEmpty()) {
            bind.unavailableTv.visibility = View.GONE
        }
        if (unavailable.isEmpty() && available.isEmpty()) {
            bind.emptyCart.visibility = View.VISIBLE
            bind.NestedScrollView.visibility = View.GONE
            bind.progress.visibility = View.GONE
        }
        viewModel.getCart()
        StaticValue.cartAmount.value = Unit
    }


}