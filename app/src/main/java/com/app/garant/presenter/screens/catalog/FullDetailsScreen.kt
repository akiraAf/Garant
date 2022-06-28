package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenFullDetailsBinding

class FullDetailsScreen : Fragment(R.layout.screen_full_details) {

    private val bind by viewBinding(ScreenFullDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.close.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}