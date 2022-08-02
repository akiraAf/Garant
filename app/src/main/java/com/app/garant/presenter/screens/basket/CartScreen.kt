package com.app.garant.presenter.screens.basket

import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
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
import com.app.garant.utils.scope
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CartScreen : Fragment(R.layout.screen_basket) {

    private val bind by viewBinding(ScreenBasketBinding::bind)
    private val viewModel: CartViewModel by viewModels<CartScreenViewModelImpl>()
    private val numberFormat = NumberFormat.getNumberInstance(Locale.CANADA)
    private val cartAdapter by lazy { CartAdapter() }
    private val cartAdapterUn by lazy { CartAdapter() }

    private var cartData = mutableListOf<Product>()
    private var cartDataUn = mutableListOf<Product>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCart()
        initClicks()
        initObservables()

        bind.basketRV.adapter = cartAdapter
        bind.basketRV.layoutManager = LinearLayoutManager(requireContext())

        bind.unavailableRV.adapter = cartAdapterUn
        bind.unavailableRV.layoutManager = LinearLayoutManager(requireContext())

    }


    private fun initClicks() {

        cartAdapter.setDeleteListenerClick { product, index ->
            val dialog = DialogDeleteItemBasket()
            dialog.show(parentFragmentManager, "DialogDelete")
            dialog.setNo {
                dialog.dismiss()
            }
            dialog.setYes {
                viewModel.deleteCart(
                    CartDeleteRequest(product.id)
                )
                cartData.remove(product)
                cartAdapter.submitList(cartData)
                cartIsEmpty()
                dialog.dismiss()
            }
        }

        cartAdapterUn.setDeleteListenerClick { product, index ->
            val dialog = DialogDeleteItemBasket()
            dialog.show(parentFragmentManager, "DialogDelete")
            dialog.setNo {
                dialog.dismiss()
            }
            dialog.setYes {
                viewModel.deleteCart(
                    CartDeleteRequest(product.id)
                )
                cartDataUn.remove(product)
                cartAdapterUn.submitList(cartDataUn)
                cartIsEmpty()
                dialog.dismiss()
            }
        }


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
                dialog.dismiss()
            }
        }

        bind.buyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_basketPage_to_nav_ordering)
        }

        bind.bookInstallment.setOnClickListener {
            if (cartData.isNotEmpty()) {
                val action: NavDirections =
                    CartScreenDirections.actionBasketPageToCheckBasketPage(
                        cartData.toTypedArray()
                    )
                findNavController().navigate(action)
            } else {
                showToast("Доступных товаров для заказа не имеется")
            }
        }

        bind.goToCatalog.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_catalog)
        }

        cartAdapter.setAddListenerClick { count,
                                          product_id,
                                          absoluteAdapterPosition ->
            viewModel.countCart(
                CartParchRequest(
                    product_id = product_id,
                    count = count
                )
            )
        }

        cartAdapter.setRemoveListenerClick { count,
                                             product_id,
                                             absoluteAdapterPosition ->
            viewModel.countCart(
                CartParchRequest(
                    product_id = product_id,
                    count = count
                )
            )
        }
    }

    private fun initObservables() {

        viewModel.errorFlowDeleteAvailable.onEach {
            cartAdapterUn.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorPutCart.onEach {
            Log.i("LOL", it + "CartScreen")
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.progressFlowGetCart.onEach {
            delay(1000)
            bind.progress.isVisible = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.errorFlowGetCart.onEach {
            Log.i("LOL", it + "CartScreen")
            bind.emptyCart.visibility = View.VISIBLE
            bind.NestedScrollView.visibility = View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.successFlowCartAvailable.onEach {
            if (it.isNotEmpty()) {
                bind.emptyCart.visibility = View.GONE
                bind.NestedScrollView.visibility = View.VISIBLE
            }
            cartData.clear()
            cartData.addAll(it)
            cartAdapter.submitList(it)
            cartAdapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.successFlowCartUnavailable.onEach {
            if (it.isNotEmpty()) {
                bind.emptyCart.visibility = View.GONE
                bind.unavailableTv.visibility = View.VISIBLE
                bind.NestedScrollView.visibility = View.VISIBLE
            }
            cartDataUn.clear()
            cartDataUn.addAll(it)
            cartAdapterUn.submitList(it)
            cartAdapterUn.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlowGetCart.onEach {
            bind.amountProducts.text = it.count.toString() + " штук"
            bind.fullPayment.text = price_converter(it.products_price.toLong())
            bind.monthPay.text = price_converter(it.monthly_price.toLong())
            bind.costTv.text = price_converter(it.total_price.toLong())
            month_btns(it.month.id)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progressPutCart.collect {
                delay(500)
                bind.progress.isVisible = it
                btnIsEnable(it)
            }
        }


    }

    private fun price_converter(price: Long): String {
        numberFormat.maximumFractionDigits = 0
        val convert = numberFormat.format(price)
        return (convert.replace(",", " ") + " cум")
    }

    override fun onResume() {
        super.onResume()
        bind.progress.isVisible = true
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCart()
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

    private fun btnIsEnable(bool: Boolean) = bind.scope {
        button1Month.isEnabled = !bool
        button3Month.isEnabled = !bool
        button6Month.isEnabled = !bool
        button9Month.isEnabled = !bool
        button12Month.isEnabled = !bool
    }

    private fun cartIsEmpty() {
        if (cartData.isEmpty() && cartDataUn.isEmpty()) {
            bind.emptyCart.visibility = View.GONE
            bind.NestedScrollView.visibility = View.VISIBLE
        } else if (cartDataUn.isEmpty()) {
            bind.unavailableTv.isVisible = false
        }
    }
}