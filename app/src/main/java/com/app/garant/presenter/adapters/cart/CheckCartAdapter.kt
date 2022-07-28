package com.app.garant.presenter.adapters.cart

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
import com.app.garant.databinding.ItemCheckCartBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide
import java.util.*

class CheckCartAdapter :
    ListAdapter<Product, CheckCartAdapter.CheckCartVH>(CheckCartAdapter.MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null
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

    inner class CheckCartVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemCheckCartBinding::bind)

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
            amount.text = value.count.toString() + " шт"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckCartAdapter.CheckCartVH =
        CheckCartVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_check_cart, parent, false)
        )

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

    override fun onBindViewHolder(holder: CheckCartVH, position: Int) {
        holder.load()
    }
}