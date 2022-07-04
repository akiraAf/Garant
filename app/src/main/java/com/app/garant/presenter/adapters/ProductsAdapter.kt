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
import com.app.garant.data.response.category.product.Product
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide

class ProductsAdapter : ListAdapter<ProductResponseItem, ProductsAdapter.ProductVH>(MyDifUtils) {

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

    inner class ProductVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemProductBinding::bind)

        init {
            Log.i("MDA", "ADAPTER")
            itemView.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun load() = bind.scope {
            val values = getItem(0) as ProductResponseItem
            val value = values.products[0]
            name.text = value.name
            price.text = value.price.toString()
            monthlyPrice.text = value.monthly_price.toString()
            Glide.with(productImageView.context).load(value.image).into(productImageView)
            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }
    }


    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.load()
        Log.i("MDA", "ADAPTER")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH =
        ProductVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

}