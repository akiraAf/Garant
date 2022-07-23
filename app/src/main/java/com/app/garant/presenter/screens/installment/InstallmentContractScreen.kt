package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentContractBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentContractScreen : Fragment(R.layout.page_installment_contract) {

    private val bind by viewBinding(PageInstallmentContractBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //     bind.orderRV.adapter = OrderAdapter(orderData, true)
        bind.orderRV.layoutManager = LinearLayoutManager(activity)
    }

}