package com.app.garant.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemCatalogBinding


class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CatalogItem>() {

    private val categoryText = arrayListOf<String>("Крупная бытовая\nтехника", "Мелкая бытовая\nтехника",
        "Ноутбуки\nи компьютеры",
        "Телефоны\nи смартфоны",
        "Мягкая мебель",
        "Корпусная\nмебель",
        "Книги",
        "Ковры",
        "Посуда",
        "Спорт инвертарь,\nтренажеры")

    private var itemListener: ((String) -> Unit)? = null

    inner class CatalogItem(view: View):RecyclerView.ViewHolder(view) {
        private val bind by viewBinding(ItemCatalogBinding::bind)

        fun load(i:Int){
            bind.img.setImageResource(R.drawable.fen)
            bind.name.text = categoryText[i]
            bind.parent.setOnClickListener {
                itemListener?.invoke("data.toString()")

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogItem =
        CatalogItem(LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false))

    override fun onBindViewHolder(holder: CatalogItem, position: Int) {
        holder.load(position)
    }

    override fun getItemCount(): Int = 10

    fun setListenerClick(function: (String) -> Unit) {
        itemListener = function
    }
}