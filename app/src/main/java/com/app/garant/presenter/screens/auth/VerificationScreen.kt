package com.app.garant.presenter.screens.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.VerifyRequest
import com.app.garant.databinding.ScreenVerificationBinding
import com.app.garant.presenter.viewModel.auth.VerifyViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.auth.VerifyViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.scope
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class VerificationScreen() : Fragment(R.layout.screen_verification) {

    private val bind by viewBinding(ScreenVerificationBinding::bind)
    private val viewModel: VerifyViewModel by viewModels<VerifyViewModelImpl>()
    private var phone: String = ""
    private var verifyCode = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(PHONE)?.let {
            phone = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = bind.scope {
        super.onViewCreated(view, savedInstanceState)


        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }


        bind.confirm.setOnClickListener {
            if (inputOne.text.toString().isNotEmpty() &&
                inputTwo.text.toString().isNotEmpty() &&
                inputThree.text.toString().isNotEmpty() &&
                inputFour.text.toString().isNotEmpty()
            ) {
                verifyCode = ""
                verifyCode += inputOne.text?.toString()
                verifyCode += inputTwo.text?.toString()
                verifyCode += inputThree.text?.toString()
                verifyCode += inputFour.text?.toString()
                showToast(phone.trim())

                if (phone.isNotEmpty() && verifyCode.length == 4)
                    viewModel.sendVerify(
                        VerifyRequest(
                            phone.toLong(),
                            verifyCode.trim().toInt()
                        )
                    )
            } else {
                showToast("Введите код подтверждения")
            }
        }


        view.setOnClickListener {
            it.hideKeyboard()
        }

        changeFocus()

        viewModel.errorFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.successFlow.onEach {
            findNavController().navigate(R.id.action_verificationScreen_to_navigationScreen)
            val pref = MyPref(App.instance)
            pref.startScreen = true
        }.launchIn(lifecycleScope)

    }


    private fun changeFocus() {
        bind.inputOne.addTextChangedListener(textWatcher(bind.inputTwo))
        bind.inputTwo.addTextChangedListener(textWatcher(bind.inputThree))
        bind.inputThree.addTextChangedListener(textWatcher(bind.inputFour))
    }

    private fun textWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 1) {
                    editText.requestFocus()
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

    companion object {
        private const val PHONE: String = "PHONE"
    }

}