package com.app.garant.presenter.screens.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenEmptyFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EmptyFavoriteScreen : Fragment(R.layout.screen_empty_favorite) {

    private val bind by viewBinding(ScreenEmptyFavoriteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.goToCatalog.setOnClickListener {
            findNavController().navigate(R.id.action_emptyFavoritePage_to_catalogPage)
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}