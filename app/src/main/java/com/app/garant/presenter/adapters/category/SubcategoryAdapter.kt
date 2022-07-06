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
import com.app.garant.data.response.category.categories.Category
import com.app.garant.data.response.category.categories.CategoryResponseItem
import com.app.garant.databinding.ItemCatalogBinding
import com.app.garant.databinding.ItemSubcategoryBinding
import com.bumptech.glide.Glide

class SubcategoryAdapter : ListAdapter<Category, SubcategoryAdapter.SubcategoryVH>(MyDifUtils) {

    object MyDifUtils : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }
    }

    private var itemListener: ((Int) -> Unit)? = null

    inner class SubcategoryVH(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemSubcategoryBinding::bind)

        fun load() {
            val value = getItem(absoluteAdapterPosition)
            bind.subcategoryName.text = value.name
            Glide.with(bind.subcategoryImg.context).load(value.image).into(bind.subcategoryImg)
            bind.parent.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: SubcategoryAdapter.SubcategoryVH, position: Int) {
        holder.load()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubcategoryAdapter.SubcategoryVH =
        SubcategoryVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_subcategory, parent, false)
        )

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }
}