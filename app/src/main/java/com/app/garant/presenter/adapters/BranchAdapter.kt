package com.app.garant.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.garant.databinding.ItemBranchBinding

class BranchAdapter:RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBranchBinding.inflate(inflater, parent, false)
        return BranchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        with(holder.binding){
            address.text = "Г. Ташкент, Шайхонтохурский\nр-н ул. Абая, 15"
        }
    }

    override fun getItemCount(): Int = 6




    class BranchViewHolder(
        val binding:ItemBranchBinding)
        :RecyclerView.ViewHolder(binding.root)
}