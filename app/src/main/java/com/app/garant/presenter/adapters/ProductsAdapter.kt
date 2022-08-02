package com.app.garant.presenter.adapters

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.cart.Product
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide
import java.util.*

class ProductsAdapter : ListAdapter<Product, ProductsAdapter.ProductVH>(MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null
    private var itemCartListener: ((Product, Int, Boolean) -> Unit)? = null
    private var itemFavoriteListener: ((Product, Int, Boolean) -> Unit)? = null
    private val numberFormat = NumberFormat.getNumberInstance(Locale.CANADA)

    object MyDifUtils : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class ProductVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemProductBinding::bind)

        init {
            itemView.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }

        private fun price_converter(price: Long): String {
            numberFormat.maximumFractionDigits = 0;
            val convert = numberFormat.format(price)
            return (convert.replace(",", " ") + " cум")
        }

        fun load() = bind.scope {
            val item: Product = getItem(absoluteAdapterPosition)
            name.text = item.name
            price.text = price_converter(item.price.toLong())
            monthlyPrice.text = price_converter(item.monthly_price.toLong())
            Glide.with(productImageView.context).load(item.image).into(productImageView)
            bind.btnAddToBasket.isChecked = item.is_cart == 1
            bind.favorite.isChecked = item.is_favorite == 1
            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }

            bind.btnAddToBasket.setOnCheckedChangeListener { buttonView, isChecked ->
                itemCartListener?.invoke(item, absoluteAdapterPosition, isChecked)
                item.is_cart = if (isChecked) 1 else 0
            }

            bind.favorite.setOnCheckedChangeListener { buttonView, isChecked ->
                itemFavoriteListener?.invoke(item, absoluteAdapterPosition, isChecked)
                item.is_favorite = if (isChecked) 1 else 0
            }

        }
    }


    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.load()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH =
        ProductVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

    fun setCartListenerClick(function: (Product, Int, Boolean) -> Unit) {
        itemCartListener = function
    }

    fun setFavoriteListenerClick(function: (Product, Int, Boolean) -> Unit) {
        itemFavoriteListener = function
    }


}