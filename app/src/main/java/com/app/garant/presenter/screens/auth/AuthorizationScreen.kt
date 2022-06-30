package com.app.garant.presenter.screens.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.databinding.ScreenAuthorizationBinding
import com.app.garant.presenter.viewModel.auth.AuthorizationViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.auth.AuthorizationViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AuthorizationScreen : Fragment(R.layout.screen_authorization) {

    private val bind by viewBinding(ScreenAuthorizationBinding::bind)
    private val viewModel: AuthorizationViewModel by viewModels<AuthorizationViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.setOnClickListener {
            it.hideKeyboard()
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.successFlow.onEach {
            showToast("LOGIN SUCCSESS")
            findNavController().navigate(R.id.action_authorizationScreen_to_verificationScreen)
        }.launchIn(lifecycleScope)


        bind.next.setOnClickListener {


            val phoneNumber = bind.inputPhoneNumber.text?.toString()
                ?.replace("+", "")
                ?.replace(" ", "")
                ?.replace("-", "")
                ?.replace("(", "")
                ?.replace(")", "")

            if (phoneNumber!!.startsWith("9989")) {
                showToast(phoneNumber)
                viewModel.login(
                    LoginRequest(
                        "998${bind.inputPhoneNumber.unMasked}"
                    )
                )
            }
//            if (phoneNumber?.length!! <= 12) {
//                findNavController().navigate(R.id.action_authorizationScreen_to_verificationScreen)
//            } else

            bind.progressBar.visibility = View.GONE

        }


    }
}