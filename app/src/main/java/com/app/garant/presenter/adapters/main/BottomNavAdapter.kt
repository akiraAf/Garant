package com.app.garant.presenter.adapters.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.garant.presenter.containers.*

class BottomNavAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
):FragmentStateAdapter(fm,lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> {
               val mainPage = ContainerMain()
               mainPage
           }

           1 -> {
               val catalogPage = ContainerCatalog()
               catalogPage

           }

           2 -> {
               val shpCartPage = ContainerCart()
               shpCartPage
           }

           3 -> {
               val contractsPage = ContainerDoc()
               contractsPage
           }

           else -> {
               val profilePage = ContainerProfile()
               profilePage
           }
       }
    }
}