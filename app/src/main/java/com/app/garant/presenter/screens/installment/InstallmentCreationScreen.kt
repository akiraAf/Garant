package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentCreationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class InstallmentCreationScreen : Fragment(R.layout.page_installment_creation) {

    private val bind by viewBinding(PageInstallmentCreationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.next.setOnClickListener {
            findNavController().navigate(R.id.action_installmentCreationPage_to_installmentDeliveryPage)
        }

        //val orderAdapter by lazy { OrderAdapter(data, true) }

      //  bind.recycler.adapter = orderAdapter
        bind.recycler.layoutManager = LinearLayoutManager(activity)
        month_btn()
    }


    private fun month_btn() {
        val param = LinearLayout.LayoutParams(
            170,
            170,
            0F
        )

        val paramDef = LinearLayout.LayoutParams(
            210,
            170,
            0F
        )
        param.setMargins(15, 0, 15, 0)
        paramDef.setMargins(10, 0, 10, 0)


        bind.apply {

            button1Month.layoutParams = param
            button3Month.layoutParams = param
            button6Month.layoutParams = param
            button9Month.layoutParams = param
            button12Month.layoutParams = param

            button1Month.setOnClickListener {
                button1Month.layoutParams = paramDef
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }

            button3Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = paramDef
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }
            button6Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = paramDef
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }
            button9Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = paramDef
                button12Month.layoutParams = param
            }
            button12Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = paramDef
            }
        }
    }


}