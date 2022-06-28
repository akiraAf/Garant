package com.app.garant.presenter.screens.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenLanguageBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageScreen : Fragment(R.layout.screen_language) {

    private val bind by viewBinding(ScreenLanguageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.russianLanguageBg.setOnClickListener{
            openAuth()
        }

        bind.uzbekLanguageBg.setOnClickListener{
            openAuth()
        }
    }

    fun openAuth(){
        findNavController().navigate(R.id.action_languageScreen_to_authorizationScreen)
    }

}