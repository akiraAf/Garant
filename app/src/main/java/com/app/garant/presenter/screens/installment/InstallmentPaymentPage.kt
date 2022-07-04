package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentPaymentBinding
import com.app.garant.models.PaymentInfo
import com.app.garant.presenter.adapters.installment.InstallmentPaymentAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class InstallmentPaymentPage : Fragment(R.layout.page_installment_payment) {
    private val bind by viewBinding(PageInstallmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data: ArrayList<PaymentInfo> = ArrayList()

        for (i in 1..10) {
            if (i % 2 == 0)
                data.add(
                    PaymentInfo(
                        "" + i,
                        "4 апреля",
                        "7 445 000",
                        "7 445 000",
                        R.drawable.ic_check
                    )
                )
            else if (i != 5 && i != 7)
                data.add(PaymentInfo("" + i, "4 май", "6 445 000", "0", R.drawable.ic_clock))

            if (i == 5 || i == 7)
                data.add(PaymentInfo("" + i, "4 май", "6 445 000", "0", R.drawable.ic_alarm))
        }
        val paymentAdapter = InstallmentPaymentAdapter(data)

        bind.paymentRV.adapter = paymentAdapter
        bind.paymentRV.layoutManager = LinearLayoutManager(activity)
    }


}