package com.app.garant.presenter.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenNothingFoundBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NothingFoundScreen : Fragment(R.layout.screen_nothing_found) {

    private val bind by viewBinding(ScreenNothingFoundBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}