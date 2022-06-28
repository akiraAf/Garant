package com.app.garant.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.models.ProductData

class ProductsAdapter(val data: ArrayList<ProductData>, val flagFavorite:Boolean = false) :
    RecyclerView.Adapter<ProductsAdapter.VH>() {

    private val flag = this.flagFavorite
    private var itemListener: ((String) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load(position)
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {

        private val bind by viewBinding(ItemProductBinding::bind)
        fun load(i: Int) {

            bind.apply {
                installmentCost.text = data[i].installmentCost
                month.text = data[i].month
                fullCost.text = data[i].fullCost
                descriptionProduct.text = data[i].descriptionProduct
                productImageView.setImageResource(data[i].productImageView)
                favorite.isChecked = flag
            }

            bind.parent.setOnClickListener {
                itemListener?.invoke(data.toString())

            }
        }

    }

    fun setListenerClick(function: (String) -> Unit) {
        itemListener = function
    }

}