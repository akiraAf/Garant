package com.app.garant.presenter.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageOrderingDeliveryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderUserInfoScreen : Fragment(R.layout.page_ordering_delivery) {

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

        bind.map.setOnClickListener {
            findNavController().navigate(R.id.action_orderingDeliveryPage_to_screenMap)
        }

        bind.deliveryType.isVisible = true

        bind.radioDelivery.setOnCheckedChangeListener { buttonView, isChecked ->
            bind.deliveryType.isVisible = isChecked
        }

        bind.radioSelfDelivery.setOnCheckedChangeListener { buttonView, isChecked ->
            bind.selfType.isVisible = isChecked
        }
    }

}