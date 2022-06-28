package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageOrderingDeliveryBinding


class OrderingDeliveryPage : Fragment(R.layout.page_ordering_delivery) {

    private val bind by viewBinding(PageOrderingDeliveryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.next.setOnClickListener {
            findNavController().navigate(R.id.action_orderingDeliveryPage_to_orderingPaymentPage)
        }
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.choiceBranch.setOnClickListener {
            findNavController().navigate(R.id.action_orderingDeliveryPage_to_branchSelectionPage)
        }

        bind.radioDelivery.setOnClickListener {
            bind.selfType.visibility = View.GONE
            bind.deliveryType.visibility = View.VISIBLE
        }
        bind.radioSelfDelivery.setOnClickListener{
            bind.selfType.visibility = View.VISIBLE
            bind.deliveryType.visibility = View.GONE
        }
    }

}