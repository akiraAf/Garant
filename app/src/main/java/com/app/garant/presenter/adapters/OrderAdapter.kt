package com.app.garant.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemOrderBinding
import com.app.garant.models.OrderData

class OrderAdapter(val data: ArrayList<OrderData>, val flagEdit: Boolean = false) : RecyclerView.Adapter<OrderAdapter.VH>() {

    private val flag: Boolean = this.flagEdit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load(position)
    }

    override fun getItemCount()= data.size

    inner class VH(
        val view: View
    ) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemOrderBinding::bind)
        fun load(i:Int){
            bind.apply {

                name.text = data[i].name
                price.text = data[i].price
                amount.text = data[i].amount

                if (flagEdit) {
                    edit.visibility = View.GONE
                    close.visibility = View.GONE
                    amount.visibility = View.VISIBLE
                } else {
                    edit.visibility = View.VISIBLE
                    close.visibility = View.VISIBLE
                    amount.visibility = View.GONE
                }

            }
        }
    }
}