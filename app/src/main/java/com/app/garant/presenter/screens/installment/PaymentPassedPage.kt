package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PagePaymentPassedBinding


class PaymentPassedPage : Fragment(R.layout.page_payment_passed) {
    private val bind by viewBinding(PagePaymentPassedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.toMainBnt.setOnClickListener {
           findNavController().navigate(R.id.action_paymentPassedPage_to_mainPage2)
        }

        bind.goAgreementBnt.setOnClickListener {
            findNavController().navigate(R.id.action_paymentPassedPage_to_nav_doc2)
        }

    }

}