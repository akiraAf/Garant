package com.app.garant.presenter.screens.installment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemPaymentBinding
import com.app.garant.models.PaymentInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentPaymentAdapter(val data: ArrayList<PaymentInfo>, ) :
    RecyclerView.Adapter<InstallmentPaymentAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):VH = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load(position)
    }

    override fun getItemCount(): Int  = data.size


    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemPaymentBinding::bind)
        fun load(i: Int) {

            bind.apply {
                name.text = data[i].number
                date.text = data[i].date
                cost.text = data[i].cost
                remainder.text = data[i].remainder
                status.setImageResource(data[i].status)

            }

        }

    }
}