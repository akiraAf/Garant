package com.app.garant.presenter.screens


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.ScreenNavigationBinding
import com.app.garant.presenter.viewModel.navigation.NavigationScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.navigation.NavigationScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NavigationScreen : Fragment(R.layout.screen_navigation) {
    private val bind by viewBinding(ScreenNavigationBinding::bind)
    private val viewModel: NavigationScreenViewModel by viewModels<NavigationScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.countCart.visibility = View.GONE
        // nav bottom connection frag container
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(bind.bottomNavigationBar, navController)

        StaticValue.cartAmount.observe(viewLifecycleOwner, observer)

        viewModel.getAmount()
        viewModel.successFlow.onEach {
            if (it == 0) {
                bind.countCart.visibility = View.GONE
            } else {
                bind.countCart.visibility = View.VISIBLE
                bind.countCart.text = it.toString()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlow.onEach {
            bind.countCart.visibility = View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)



        activity?.onBackPressed()

    }

    private val observer = Observer<Unit> {
        viewModel.getAmount()
    }


}