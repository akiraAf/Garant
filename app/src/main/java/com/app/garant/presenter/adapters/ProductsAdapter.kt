package com.app.garant.presenter.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide

class ProductsAdapter() : ListAdapter<ProductResponseItem, ProductsAdapter.VH>(MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null

    object MyDifUtils : DiffUtil.ItemCallback<ProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(

            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load()
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemProductBinding::bind)

        init {
            itemView.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun load() = bind.scope {
            val values = getItem(absoluteAdapterPosition) as ProductResponseItem
            name.text = values.products[0].name
            Log.i("LOL", name.text.toString())
            price.text = values.products[0].price.toString()
            monthlyPrice.text = values.products[0].monthly_price.toString()
            Glide.with(productImageView.context)
                .load(values.products[0].image)
                .into(productImageView)
            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }
    }

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

}