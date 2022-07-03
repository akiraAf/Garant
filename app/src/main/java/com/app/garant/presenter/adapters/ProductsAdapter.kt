package com.app.garant.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.product.Product
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.models.ProductData
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
            val value = getItem(absoluteAdapterPosition) as Product

            installmentCost.text = value.monthly_price.toString()
            month.text = "x 12 мес"
            fullCost.text = value.price.toString()
            descriptionProduct.text = value.name
            Glide.with(productImageView.context).load(value.image).into(productImageView)

            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)

            }
        }

    }

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

}