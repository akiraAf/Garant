package com.app.garant.presenter.adapters

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.Data
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide
import java.util.*

class ProductsAdapter : ListAdapter<Data, ProductsAdapter.ProductVH>(MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null
    private val numberFormat = NumberFormat.getNumberInstance(Locale.CANADA)

    object MyDifUtils : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
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
            Log.i("LOL", convert)
            return (convert.replace(",", " ") + " cум")
        }

        fun load() = bind.scope {
            val value = getItem(absoluteAdapterPosition)
            name.text = value.name
            price.text = price_converter(value.price.toLong())
            monthlyPrice.text = price_converter(value.monthly_price.toLong())
            Glide.with(productImageView.context).load(value.image).into(productImageView)
            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
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

}