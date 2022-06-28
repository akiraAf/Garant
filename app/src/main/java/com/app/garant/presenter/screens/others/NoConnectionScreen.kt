package com.app.garant.presenter.screens.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenNoConnectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NoConnectionScreen : Fragment(R.layout.screen_no_connection) {
    private val bind by viewBinding(ScreenNoConnectionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}