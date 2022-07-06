package com.app.garant.presenter.screens


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.presenter.adapters.main.BottomNavAdapter
import com.app.garant.databinding.ScreenNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NavigationScreen : Fragment(R.layout.screen_navigation) {


    private val bind by viewBinding(ScreenNavigationBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.pager.adapter = BottomNavAdapter(childFragmentManager, lifecycle)
        bind.pager.isUserInputEnabled = false

        bind.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    bind.pager.setCurrentItem(0, true)
                }
                R.id.action_catalog -> {
                    bind.pager.setCurrentItem(1, true)
                }
                R.id.action_cart -> {
                    bind.pager.setCurrentItem(2, true)
                }
                R.id.action_documents -> {
                    bind.pager.setCurrentItem(3, true)
                }
                else -> {
                    bind.pager.setCurrentItem(4, true)
                }
            }
            return@setOnItemSelectedListener true
        }

    }


}