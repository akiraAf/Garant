package com.app.garant.presenter.screens


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.ScreenNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NavigationScreen : Fragment(R.layout.screen_navigation) {


    private val bind by viewBinding(ScreenNavigationBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // nav bottom connection frag container
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(bind.bottomNavigationBar, navController)

        activity?.onBackPressed()

    }


}