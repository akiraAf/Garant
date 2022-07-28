package com.app.garant.presenter.screens.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
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
import com.app.garant.data.response.favorite.Data
import com.app.garant.data.response.favorite.FavoriteResponse
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

        adapterProduct.notifyDataSetChanged()
        viewModel.successFlowFavorite.onEach {
            delay(1000)
            bind.progress.visibility = View.GONE
            if (it.data.isEmpty()) {
                bind.NestedScrollView.visibility = View.GONE
                bind.emptyFavorite.visibility = View.VISIBLE
            } else {
                bind.NestedScrollView.visibility = View.VISIBLE
                bind.emptyFavorite.visibility = View.GONE
            }
            adapterProduct.submitList(it.data)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        bind.favoriteRV.adapter = adapterProduct
        bind.favoriteRV.layoutManager = GridLayoutManager(requireContext(), 2)

        adapterProduct.setCartListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                viewModel.addCart(CartRequest(1, idProduct))
//                viewModel.errorFlowCartRemove.onEach {
//                    adapterProduct.notifyItemChanged(index)
//                    viewModel.getFavorite()
//                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeCart(CartDeleteRequest(idProduct))
                adapterProduct.notifyItemRemoved(index)
//                viewModel.errorFlowCartAdd.onEach {
//                    adapterProduct.notifyItemChanged(index)
//                    viewModel.getFavorite()
//                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        adapterProduct.setListenerClick {
        }
        adapterProduct.setFavoriteListenerClick { idProduct, index, isChecked ->
            if (!isChecked) {
                viewModel.removeFavorite(FavoriteRequest(idProduct))
                viewModel.errorFlowFavoriteRemove.onEach {
//                    delay(3000)
                    adapterProduct.notifyItemRemoved(index)
//                    adapterProduct.notifyDataSetChanged()
//                    viewModel.getFavorite()
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
        panel()
    }

    private fun panel() {
        bind.sort.setOnClickListener {
            val wrapper: Context =
                ContextThemeWrapper(requireContext(), R.style.Widget_App_PopupMenu)
            val popUpMenu = PopupMenu(wrapper, it)
            popUpMenu.inflate(R.menu.pop_menu)
            popUpMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.cheaper_products_filter -> {
                        StaticValue.filter.value = Unit
                    }
                    R.id.discount_products_filter -> {}
                    R.id.new_products_filter -> {}
                    R.id.expensive_products_filter -> {}
                }
                false
            }
            popUpMenu.show()
        }

        bind.goToCatalog.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_catalog)
        }

        bind.filter.setOnClickListener {
            val dialog = DialogFilter()
            dialog.show(childFragmentManager, "DIALOG_FILTER")
            dialog.setReset {
                dialog.dismiss()
            }
            dialog.setUse {
                dialog.dismiss()
            }
        }
    }
}