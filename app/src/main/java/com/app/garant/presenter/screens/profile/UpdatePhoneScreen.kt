package com.app.garant.presenter.screens.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.databinding.ScreenReceiveConfirmationCodeBinding
import com.app.garant.presenter.viewModel.profile.UpdatePhoneViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.UpdatePhoneViewModelImpl
import com.app.garant.utils.isConnected
import com.app.garant.utils.scope
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class UpdatePhoneScreen : Fragment(R.layout.screen_receive_confirmation_code) {
    private val bind by viewBinding(ScreenReceiveConfirmationCodeBinding::bind)
    private val viewModel: UpdatePhoneViewModel by viewModels<UpdatePhoneViewModelImpl>()
    private lateinit var phoneNumber: String
    private lateinit var phoneNumberTextView: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = bind.scope {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }



        arguments?.getString(PHONE_ARG)?.let {
            phoneNumber = it
        }
        arguments?.getString(PHONE_ARG_TEXT_VIEW)?.let {
            phoneNumberTextView = it.drop(13)
        }

        enterCodeTextView.text =
            "Введите код подтверждения\n отправленный на ваш номер ***${phoneNumberTextView}"


        viewModel.successFlow.onEach {
            findNavController().navigate(R.id.action_receiveConfirmationCodePage2_to_successfulNumberChangePage2)
        }.launchIn(lifecycleScope)


        bind.confirmBtn.setOnClickListener {
            val code_receive = inputCode.text.toString()
            if (code_receive.isNotEmpty()) {
                if (isConnected()) {
                    viewModel.updatePhone(
                        UpdatePhoneRequest(
                            phoneNumber.toLong(),
                            code_receive.toInt()
                        )
                    )
                    findNavController().navigate(R.id.action_receiveConfirmationCodePage2_to_successfulNumberChangePage2)
                }
            } else {
                showToast("Введите код потверждения")
            }
        }
    }

    companion object {
        const val PHONE_ARG = "PHONE_ARG"
        const val PHONE_ARG_TEXT_VIEW = "PHONE_ARG_TEXT_VIEW"
    }
}