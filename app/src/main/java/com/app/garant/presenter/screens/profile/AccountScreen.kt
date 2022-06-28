package com.app.garant.presenter.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AccountScreen : Fragment(R.layout.screen_account) {

    private val bind by viewBinding(ScreenAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.save.setOnClickListener {
            findNavController().navigate(R.id.action_accountPage_to_profileScreen)
        }

    }



}