package com.app.garant.presenter.adapters.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.garant.presenter.screens.main.StockPage
import com.app.garant.presenter.screens.main.PopularPage
import com.app.garant.presenter.screens.main.TopSellingPage

class ProductPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0->{
                val topSelling = TopSellingPage()
                topSelling
            }
            1->{
                val stockPage = StockPage()
                stockPage
            }
            else->{
                val popularPage = PopularPage()
                popularPage
            }
        }
    }
}