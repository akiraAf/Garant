package com.app.garant.presenter.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenReceiveConfirmationCodeBinding


class ReceiveConfirmationCodeScreen : Fragment(R.layout.screen_receive_confirmation_code) {
    private val bind by viewBinding(ScreenReceiveConfirmationCodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.confirmBtn.setOnClickListener {
            findNavController().navigate(R.id.action_receiveConfirmationCodePage2_to_successfulNumberChangePage2)
        }
    }
}