package com.app.garant.presenter.screens.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
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
import com.app.garant.databinding.ScreenFavoritesBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.dialogs.DialogFilter
import com.app.garant.presenter.viewModel.profile.FavoriteScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.FavoriteScreenViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoritesScreen : Fragment(R.layout.screen_favorites) {

    private val bind by viewBinding(ScreenFavoritesBinding::bind)
    private val viewModel: FavoriteScreenViewModel by viewModels<FavoriteScreenViewModelImpl>()
    private val adapterProduct by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorite()

        bind.favoriteRV.adapter = adapterProduct
        bind.favoriteRV.layoutManager = GridLayoutManager(requireContext(), 2)

        initObserver()
        initClicks()
    }

    private fun initObserver() {

        viewModel.progressFlowGetFavorite.onEach {
            bind.progress.isVisible = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlowGetFavorite.onEach {
            delay(1000)
            if (it.data.isEmpty()) {
                bind.NestedScrollView.visibility = View.GONE
                bind.emptyFavorite.visibility = View.VISIBLE
            } else {
                bind.NestedScrollView.visibility = View.VISIBLE
                bind.emptyFavorite.visibility = View.GONE
            }
            adapterProduct.submitList(it.data)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }

    private fun initClicks() {
        adapterProduct.setFavoriteListenerClick { product, index, isChecked ->
            if (!isChecked) {
                viewModel.removeFavorite(FavoriteRequest(product.id))
                adapterProduct.notifyItemRemoved(index)
            } else {
                viewModel.addFavorite(FavoriteRequest(product.id))
            }
        }


        adapterProduct.setCartListenerClick { product, index, isChecked ->
            if (isChecked) {
                viewModel.addCart(CartRequest(1, product.id))
            } else {
                viewModel.removeCart(CartDeleteRequest(product.id))
            }
        }

        adapterProduct.setListenerClick {

        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.goToCatalog.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_catalog)
        }

    }


}