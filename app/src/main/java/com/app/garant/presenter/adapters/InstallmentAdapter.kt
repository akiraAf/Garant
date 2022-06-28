package com.app.garant.presenter.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemInstallmentBinding
import com.app.garant.models.InstallmentHistoryData

class InstallmentAdapter(val data: ArrayList<InstallmentHistoryData>) :
    RecyclerView.Adapter<InstallmentAdapter.VH>() {

    private var itemListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentAdapter.VH =
        VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_installment, parent, false)
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.load(position)
    }

    override fun getItemCount() = data.size

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {

        private val bind by viewBinding(ItemInstallmentBinding::bind)
        fun load(i: Int) {
            bind.apply {
                name.text = data[i].name
                type.text = data[i].type
                type.setTextColor(Color.parseColor(setColorType(type.text.toString())))
                date.text = data[i].date

            }

            bind.parent.setOnClickListener {
                itemListener?.invoke(data.toString())

            }
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
