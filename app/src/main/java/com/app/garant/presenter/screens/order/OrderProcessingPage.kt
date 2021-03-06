package com.app.garant.presenter.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageOrderProcessingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderProcessingPage : Fragment(R.layout.page_order_processing) {
    private val bind by viewBinding(PageOrderProcessingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.toMainBnt.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_main)
        }

        bind.goAgreementBnt.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_doc)
        }

    }
}