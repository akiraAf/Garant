package com.app.garant.presenter.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.Product
import com.app.garant.databinding.PageTopsellingBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.main.MainScreenViewModel
import com.app.garant.presenter.viewModel.main.ProdutsPageViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.MainScreenViewModelImpl
import com.app.garant.presenter.viewModel.viewModelimpl.main.ProdutsPageViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsPage(private val positionOnViewPager: Int) : Fragment(R.layout.page_topselling) {

    private val bind by viewBinding(PageTopsellingBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>({ requireParentFragment() })

    private val adapterProduct by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.recycler.adapter = adapterProduct
        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        initClicks()
        initObserver()
    }

    private fun initClicks() {
        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }

        adapterProduct.setCartListenerClick { product, index, isChecked ->
            if (isChecked) {
                viewModel.addCart(CartRequest(1, product.id))
            } else {
                viewModel.removeCart(CartDeleteRequest(product.id))
            }
        }

        adapterProduct.setFavoriteListenerClick { product, index, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(FavoriteRequest(product.id))
            } else {
                viewModel.removeFavorite(FavoriteRequest(product.id))
            }
        }
    }

    private fun initObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.successFlow.collect { productResponse ->
                val products = productResponse[positionOnViewPager].products
                adapterProduct.submitList(products)
                adapterProduct.notifyDataSetChanged()
            }

        }

        viewModel.successFlowCartRemove.onEach {
            viewModel.getProducts()
            showToast("success")
        }.launchIn(lifecycleScope)

        viewModel.errorFlowCartRemove.onEach {
            Log.d("RRR", "error removeCart: $it")
        }.launchIn(lifecycleScope)

        viewModel.successFlowCartAdd.onEach {
            showToast("success")
        }.launchIn(lifecycleScope)

        viewModel.errorFlowCartAdd.onEach {
            Log.d("RRR", "error addCart: $it")
        }.launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        bind.recycler.adapter = adapterProduct
        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }
}

