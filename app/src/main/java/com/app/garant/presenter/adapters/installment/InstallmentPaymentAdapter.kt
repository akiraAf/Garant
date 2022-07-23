package com.app.garant.presenter.adapters.installment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemPaymentBinding


class InstallmentPaymentAdapter( ) :
    RecyclerView.Adapter<InstallmentPaymentAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load(position)
    }

    override fun getItemCount(): Int  =3


    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemPaymentBinding::bind)
        fun load(i: Int) {



        }

    }
}