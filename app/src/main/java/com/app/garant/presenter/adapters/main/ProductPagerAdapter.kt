package com.app.garant.presenter.adapters.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.garant.presenter.screens.main.ProductsPage

class ProductPagerAdapter(
    private val pagesCount: Int,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = pagesCount

    override fun createFragment(position: Int): Fragment {
        return ProductsPage(position)
    }
}