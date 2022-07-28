package com.app.garant.presenter.screens.profile

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.request.profile.ChangePhoneRequest
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
    private var phone = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = bind.scope {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }


        arguments?.getString(PHONE_ARG)?.let {
            phoneNumber = it
        }



        arguments?.getString(PHONE_ARG_TEXT_VIEW)?.let {
            phone = it
            phoneNumberTextView = it.drop(13)
        }

        bind.reset.setOnClickListener {
            viewModel.changeNumber(ChangePhoneRequest(phone.toLong()))
        }

        enterCodeTextView.text =
            "Введите код подтверждения\n отправленный на ваш номер ***${phoneNumberTextView}"

        bind.reset.setOnClickListener {
            bind.reset.visibility = View.GONE
            timerX.start()
        }

        timerX.start()


        viewModel.successFlow.onEach {
            findNavController().navigate(R.id.action_receiveConfirmationCodePage2_to_successfulNumberChangePage2)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.errorFlow.onEach {
            showToast("Неправильный код верификации")
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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
                }
            } else {
                showToast("Введите код потверждения")
            }
        }
    }

    private var timerX = object : CountDownTimer(25000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 > 9L)
                bind.timer.text = "00:${(millisUntilFinished / 1000)}"
            else
                bind.timer.text = "00:0${(millisUntilFinished / 1000)}"
        }

        override fun onFinish() {
            bind.timer.text = "Отправить код повторно"
            bind.reset.visibility = View.VISIBLE
        }

    }

    override fun onStop() {
        super.onStop()
        timerX.cancel()
    }

    companion object {
        const val PHONE_ARG = "PHONE_ARG"
        const val PHONE_ARG_TEXT_VIEW = "PHONE_ARG_TEXT_VIEW"
    }
}