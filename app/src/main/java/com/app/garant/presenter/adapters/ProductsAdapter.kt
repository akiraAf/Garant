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
import com.app.garant.data.response.category.product.ProductCategoryResponse
import com.app.garant.data.response.category.product.ProductCategoryResponseItem
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide

class ProductsAdapter() : ListAdapter<ProductCategoryResponse, ProductsAdapter.VH>(MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null

    object MyDifUtils : DiffUtil.ItemCallback<ProductCategoryResponse>() {
        override fun areItemsTheSame(
            oldItem: ProductCategoryResponse,
            newItem: ProductCategoryResponse
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(

            oldItem: ProductCategoryResponse,
            newItem: ProductCategoryResponse
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
            val values = getItem(absoluteAdapterPosition) as ProductCategoryResponseItem
            val i = absoluteAdapterPosition
//            if (values.name == "Grady_PLC") {
                name.text = values.products[i].name
                monthlyPrice.text = values.products[i].monthly_price.toString()
                price.text = values.products[i].price.toString()
                Glide.with(productImageView.context).load(values.products[i].image)
                    .into(productImageView)
//            }

            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }
    }

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

}