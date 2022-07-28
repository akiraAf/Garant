package com.app.garant.presenter.screens.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PagePaymentPassedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class PaymentPassedScreen : Fragment(R.layout.page_payment_passed) {
    private val bind by viewBinding(PagePaymentPassedBinding::bind)

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
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_doc)
        }

    }

}