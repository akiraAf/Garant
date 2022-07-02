package com.app.garant.presenter.adapters.installment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.garant.databinding.ItemHistoryPaymentBinding

class HistoryInstallmentPaymentAdapter :
    RecyclerView.Adapter<HistoryInstallmentPaymentAdapter.HistoryInstallmentPaymentViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryInstallmentPaymentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryPaymentBinding.inflate(inflater, parent, false)
        return HistoryInstallmentPaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryInstallmentPaymentViewHolder, position: Int) {

        with(holder.binding){
            date.text ="21 март 2022 18:24"
            cost.text = "1 700 000 сум"
        }
    }

    override fun getItemCount(): Int = 6


    inner class HistoryInstallmentPaymentViewHolder(
        val binding:ItemHistoryPaymentBinding)
        :RecyclerView.ViewHolder(binding.root)

}