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
import com.app.garant.databinding.ScreenFavoritesBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.dialogs.DialogFilter
import com.app.garant.presenter.viewModel.profile.FavoriteScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.FavoriteScreenViewModelImpl
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

        viewModel.successFlowFavorite.onEach {
            adapterProduct.submitList(it.data)
            delay(1000)
            bind.favoriteRV.adapter = adapterProduct
            bind.favoriteRV.layoutManager = GridLayoutManager(requireContext(), 2)
            bind.progress.visibility = View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

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
                viewModel.successFlowFavoriteAdd.onEach {
                    adapterProduct.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowFavoriteRemove.onEach {
                    adapterProduct.notifyItemChanged(index)
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