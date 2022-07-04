package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.installment.InstallmentInfoPagerAdapter
import com.app.garant.databinding.PageInstallmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentScreen : Fragment(R.layout.page_installment) {

    private val bind by viewBinding(PageInstallmentBinding::bind)
    private val tabTitles = listOf("Договор", "Оплата", "История оплаты")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.pager.adapter = InstallmentInfoPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(bind.tabLayout, bind.pager){tab, position->
            tab.text = tabTitles[position]
        }.attach()

        bind.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}