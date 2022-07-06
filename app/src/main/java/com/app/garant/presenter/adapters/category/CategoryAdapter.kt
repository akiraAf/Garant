package com.app.garant.presenter.adapters.category

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
import com.app.garant.data.response.category.Data
import com.app.garant.data.response.category.categories.Category
import com.app.garant.data.response.category.categories.CategoryResponseItem
import com.app.garant.databinding.ItemCatalogBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.bumptech.glide.Glide


class CategoryAdapter : ListAdapter<CategoryResponseItem, CategoryAdapter.CategoryVH>(MyDifUtils) {

    object MyDifUtils : DiffUtil.ItemCallback<CategoryResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }


    private var itemListener: ((List<Category>, String) -> Unit)? = null

    inner class CategoryVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemCatalogBinding::bind)

        fun load() {
            val value = getItem(absoluteAdapterPosition)
            bind.name.text = value.name
            Glide.with(bind.img.context).load(value.image).into(bind.img)
            value.categories
            bind.parent.setOnClickListener {
                StaticValue.subCategory = value.categories
                itemListener?.invoke(value.categories, value.name)
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryVH, position: Int) {
        holder.load()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CategoryVH =
        CategoryVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        )

    fun setListenerClick(function: (List<Category>, String) -> Unit) {
        itemListener = function
    }
}