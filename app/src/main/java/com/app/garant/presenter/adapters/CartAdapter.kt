package com.app.garant.presenter.adapters

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import androidx.recyclerview.widget.ListAdapter
import com.app.garant.data.response.cart.Product
import com.app.garant.databinding.ItemOrderBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide
import java.util.*

class CartAdapter : ListAdapter<Product, CartAdapter.CartVH>(MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null
    private var itemDeleteListener: ((Int, Int) -> Unit)? = null
    private val numberFormat = NumberFormat.getNumberInstance(Locale.CANADA)

    object MyDifUtils : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class CartVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemOrderBinding::bind)

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
            val value = getItem(absoluteAdapterPosition)
            name.text = value.name
            price.text = price_converter(value.price.toLong())
            Glide.with(image.context).load(value.image).into(image)
            count.text = value.count.toString() + " шт"
            bind.close.setOnClickListener {
                itemDeleteListener?.invoke(value.id, absoluteAdapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartVH =
        CartVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

    fun setDeleteListenerClick(function: (Int, Int) -> Unit) {
        itemDeleteListener = function
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.load()
    }
}