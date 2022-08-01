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
import com.app.garant.data.response.branch.BranchResponse
import com.app.garant.data.response.branch.Data
import com.app.garant.data.response.cart.Product
import com.app.garant.databinding.ItemBranchBinding
import com.app.garant.databinding.ItemProductBinding
import com.app.garant.utils.scope
import com.bumptech.glide.Glide
import java.util.*

class BranchAdapter : ListAdapter<Data, BranchAdapter.BranchViewHolder>(MyDifUtils) {

    private var itemListener: ((Int) -> Unit)? = null

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

    inner class BranchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemBranchBinding::bind)

        init {
            itemView.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun load() = bind.scope {
            val value = getItem(absoluteAdapterPosition)
            address.text = value.address
        }
    }


    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.load()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder =
        BranchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_branch, parent, false)
        )

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }


}