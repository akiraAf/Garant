package com.app.garant.presenter.screens.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenVerificationBinding
import com.app.garant.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VerificationScreen() : Fragment(R.layout.screen_verification) {

    private val bind by viewBinding(ScreenVerificationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeFocus()
        navigation()

        view.setOnClickListener {
            it.hideKeyboard()
        }
    }


    private fun navigation(){
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.confirm.setOnClickListener {
            findNavController().navigate(R.id.action_verificationScreen_to_navigationScreen)
        }
    }

    private fun changeFocus(){
        bind.inputOne.addTextChangedListener(textWatcher(bind.inputTwo))
        bind.inputTwo.addTextChangedListener(textWatcher(bind.inputThree))
        bind.inputThree.addTextChangedListener(textWatcher(bind.inputFour))
    }

    private fun textWatcher(editText: EditText):TextWatcher {
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


}