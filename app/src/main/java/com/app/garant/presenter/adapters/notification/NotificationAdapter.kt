package com.app.garant.presenter.adapters.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.garant.databinding.ItemNotificationBinding
import com.app.garant.utils.scope

private var itemListener: ((Int) -> Unit)? = null

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificationBinding.inflate(inflater, parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.binding.scope {
            if (position == 3)
                image.visibility = View.VISIBLE
            else
                buttons.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int = 6

    class NotificationViewHolder(
        val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.no.setOnClickListener {
                itemListener?.invoke(absoluteAdapterPosition)
            }
        }
    }

    fun setListenerClick(function: (Int) -> Unit) {
        itemListener = function
    }

}