package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentDeliveryBinding


class InstallmentDeliveryPage : Fragment(R.layout.page_installment_delivery) {

    private val bind by viewBinding(PageInstallmentDeliveryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind.radioDelivery.setOnClickListener {
            bind.selfType.visibility = View.GONE
            bind.deliveryType.visibility = View.VISIBLE
        }
        bind.radioSelfDelivery.setOnClickListener{
            bind.selfType.visibility = View.VISIBLE
            bind.deliveryType.visibility = View.GONE
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.next.setOnClickListener {
            findNavController().navigate(R.id.action_installmentDeliveryPage_to_installmentPayPage)
        }

        bind.choiceBranch.setOnClickListener {
            findNavController().navigate(R.id.action_installmentDeliveryPage_to_branchSelectionPage2)
        }
    }

}