package com.app.garant.presenter.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenSuccessfulChangeNumberBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class SuccessfulNumberChangeScreen : Fragment(R.layout.screen_successful_change_number) {

    private val bind by viewBinding(ScreenSuccessfulChangeNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.profile.setOnClickListener {
            findNavController().navigate(R.id.action_successfulNumberChangePage2_to_profileScreen)
        }
    }


}