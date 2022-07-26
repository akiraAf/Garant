package com.app.garant.presenter.screens.main

import android.os.Bundle
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
import com.app.garant.data.response.category.Data
import com.app.garant.databinding.PageTopsellingBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.main.ProdutsPageViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.ProdutsPageViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductsPage : Fragment(R.layout.page_topselling) {

    private val bind by viewBinding(PageTopsellingBinding::bind)
    private val viewModel: ProdutsPageViewModel by viewModels<ProdutsPageViewModelImpl>()
    private var productData: List<Data>? = null

    private val adapterProduct by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterProduct.notifyDataSetChanged()

        for (products in StaticValue.mainScreenProduct) {
            if (products.name == StaticValue.nameCategory) {
                productData = products.products
                adapterProduct.submitList(productData)
                adapterProduct.notifyDataSetChanged()
            }
        }
        bind.recycler.adapter = adapterProduct
        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }


        adapterProduct.setCartListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                viewModel.addCart(CartRequest(1, idProduct))
                StaticValue.cartAmount.value = Unit
                viewModel.errorFlowS.onEach {
                    delay(5000)
                    StaticValue.mainRequest.value = Unit
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeCart(CartDeleteRequest(idProduct))
                StaticValue.cartAmount.value = Unit
                viewModel.errorFlowR.onEach {
                    delay(5000)
                    StaticValue.mainRequest.value = Unit
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        adapterProduct.setFavoriteListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartAdd.onEach {
                    showToast(it)
                    StaticValue.mainRequest.value = Unit
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartRemove.onEach {
                    showToast(it)
                    StaticValue.mainRequest.value = Unit
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        for (products in StaticValue.mainScreenProduct) {
            if (products.name == StaticValue.nameCategory) {
                productData = products.products
                adapterProduct.submitList(productData)
                adapterProduct.notifyDataSetChanged()
            }
        }
        bind.recycler.adapter = adapterProduct
        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    override fun onStop() {
        super.onStop()
        adapterProduct.notifyDataSetChanged()
    }


}

