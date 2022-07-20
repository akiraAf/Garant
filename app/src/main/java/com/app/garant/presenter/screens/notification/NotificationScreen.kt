package com.app.garant.presenter.screens.notification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.presenter.adapters.notification.NotificationAdapter
import com.app.garant.databinding.ScreenNotificationBinding
import com.app.garant.presenter.dialogs.DialogLogout
import com.app.garant.presenter.dialogs.DialogNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class NotificationScreen : Fragment(R.layout.screen_notification) {

    private val bind by viewBinding(ScreenNotificationBinding::bind)
    private val notificationAdapter = NotificationAdapter()
    private val layoutManager = LinearLayoutManager(activity)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.notificationRV.layoutManager = layoutManager
        bind.notificationRV.adapter = notificationAdapter

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        notificationAdapter.setListenerClick {
            val dialog = DialogNotification()
            dialog.show(parentFragmentManager, "DialogNotification")
            dialog.setNo {
                dialog.dismiss()
            }

            dialog.setYes {
                dialog.dismiss()
            }
        }

    }
}