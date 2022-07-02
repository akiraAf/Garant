package com.app.garant.presenter.adapters.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.garant.R
import com.app.garant.databinding.ItemSubcategoryBinding

class SubcategoryAdapter:RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    private var itemListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubcategoryBinding.inflate(inflater, parent, false)
        return SubcategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        with(holder.binding){
            subcategoryImg.setImageResource(R.drawable.ic_category)
            subcategoryName.setText(R.string.mobile_phones_and_smart_phones)
            parent.setOnClickListener {
                itemListener?.invoke("data.toString()")

            }
        }
    }

    override fun getItemCount(): Int = 10


    class SubcategoryViewHolder(
        val binding: ItemSubcategoryBinding
    ):RecyclerView.ViewHolder(binding.root)

    fun setListenerClick(function: (String) -> Unit) {
        itemListener = function
    }
}