package com.app.garant.presenter.adapters.search

import com.app.garant.databinding.ItemSearchBinding
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.response.category.categories.Category
import com.app.garant.data.response.category.categories.CategoryResponseItem
import com.app.garant.databinding.ItemCatalogBinding
import com.bumptech.glide.Glide


class SearchAdapter : ListAdapter<String, SearchAdapter.SearchVH>(MyDifUtils) {

    object MyDifUtils : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }


    private var itemListener: ((String) -> Unit)? = null

    inner class SearchVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemSearchBinding::bind)

        fun load() {
            val value = getItem(absoluteAdapterPosition)
            bind.text.text = value
            bind.item.setOnClickListener {
                itemListener?.invoke(value)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchVH, position: Int) {
        holder.load()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchVH =
        SearchVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        )

    fun setListenerClick(function: (String) -> Unit) {
        itemListener = function
    }
}