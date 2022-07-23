package com.app.garant.presenter.adapters.installment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.garant.presenter.screens.others.InstallmentRecyclerView
import com.app.garant.presenter.screens.others.OrderRecyclerView


class InstallmentPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0->{
                val frag = InstallmentRecyclerView();
                frag
            }
            else->{
                val frag = OrderRecyclerView();
                frag
            }
        }

    }

}