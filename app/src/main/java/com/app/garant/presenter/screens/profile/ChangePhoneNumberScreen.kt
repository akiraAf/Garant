package com.app.garant.presenter.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenChangeNumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ChangePhoneNumberScreen : Fragment(R.layout.screen_change_number) {
    private val bind by viewBinding(ScreenChangeNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.next.setOnClickListener {
            findNavController().navigate(R.id.action_changePhoneNumberPage_to_receiveConfirmationCodePage2)
        }
    }

}