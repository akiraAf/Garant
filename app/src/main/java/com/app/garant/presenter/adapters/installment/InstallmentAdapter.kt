package com.app.garant.presenter.adapters.installment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemInstallmentBinding

class InstallmentAdapter() :
    RecyclerView.Adapter<InstallmentAdapter.VH>() {

    private var itemListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_installment, parent, false)
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load(position)
    }

    override fun getItemCount() = 3

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {

        private val bind by viewBinding(ItemInstallmentBinding::bind)
        fun load(i: Int) {

        }

    }

    fun setColorType(type:String):String{
        return when(type){
            "Одобрено"->"#01E727"
            "Активный"->"#01E727"
            "Ожидает предоплату"->"#E7B901"
            "Ожидает доставки"->"#E7B901"
            "Доставлено"->"#01E727"
            "Отменено"->"#FF8787"
            "Не одобрено"->"#FF8787"
            "Просрочка"->"#F55146"
            else->"#787878"
        }
    }

    fun setListenerClick(function: (String) -> Unit) {
        itemListener = function
    }
}
