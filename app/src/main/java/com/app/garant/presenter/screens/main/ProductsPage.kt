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
import dagger.hilt.android.AndroidEntryPoint
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

        for (products in StaticValue.mainScreenProduct) {
            if (products.name == StaticValue.nameCategory) {
                productData = products.products
                adapterProduct.submitList(productData)
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
                viewModel.successFlowCartRemove.onEach {
                    adapterProduct.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeCart(CartDeleteRequest(idProduct))
                viewModel.successFlowCartAdd.onEach {
                    adapterProduct.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        adapterProduct.setFavoriteListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartRemove.onEach {
                    adapterProduct.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartAdd.onEach {
                    adapterProduct.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        bind.recycler.adapter = adapterProduct
        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }


}

