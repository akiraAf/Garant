package com.app.garant.presenter.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.garant.presenter.screens.installment.HistoryPaymentPage
import com.app.garant.presenter.screens.installment.InstallmentContractPage
import com.app.garant.presenter.screens.installment.InstallmentPaymentPage

class InstallmentInfoPagerAdapter(fm:FragmentManager,
lifecycle: Lifecycle):FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> InstallmentContractPage()
            1-> InstallmentPaymentPage()
            else-> HistoryPaymentPage()
        }
    }
}