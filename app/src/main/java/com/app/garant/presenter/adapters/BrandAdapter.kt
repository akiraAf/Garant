package com.app.garant.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import androidx.recyclerview.widget.ListAdapter
import com.app.garant.data.response.brand.BrandResponseItem
import com.app.garant.databinding.ItemFilterBinding
import com.app.garant.utils.scope

class BrandAdapter : ListAdapter<BrandResponseItem, BrandAdapter.BrandVH>(MyDifUtils) {

    object MyDifUtils : DiffUtil.ItemCallback<BrandResponseItem>() {
        override fun areItemsTheSame(
            oldItem: BrandResponseItem,
            newItem: BrandResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: BrandResponseItem,
            newItem: BrandResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class BrandVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemFilterBinding::bind)

        fun load() = bind.scope {
            val value = getItem(absoluteAdapterPosition)
            name.text = value.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAdapter.BrandVH =
        BrandVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        )


    override fun onBindViewHolder(holder: BrandVH, position: Int) {
        holder.load()
    }
}