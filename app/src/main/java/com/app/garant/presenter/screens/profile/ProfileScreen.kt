package com.app.garant.presenter.screens.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenProfileBinding
import com.app.garant.presenter.dialogs.DialogLogout
import com.app.garant.presenter.dialogs.DialogLanguage

class ProfileScreen : Fragment(R.layout.screen_profile) {

    private val bind by viewBinding(ScreenProfileBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.account.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_accountPage)
        }

        bind.favorite.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_favoritesPage)
        }

        bind.changePhoneNumber.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_changePhoneNumberPage)
        }

        bind.orderHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_installmentHistoryScreen)
        }

        bind.logout.setOnClickListener {
            val dialog = DialogLogout()
            dialog.show(parentFragmentManager, "DialogLogOut")
        }

        bind.language.setOnClickListener {
            val language= DialogLanguage()
            language.show(parentFragmentManager, "DialogLanguage")
        }
    }




}