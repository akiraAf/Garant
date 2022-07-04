package com.app.garant.presenter.screens.others

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenEmptyHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EmptyHistoryScreen : Fragment(R.layout.screen_empty_history) {

    private val bind by viewBinding(ScreenEmptyHistoryBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}