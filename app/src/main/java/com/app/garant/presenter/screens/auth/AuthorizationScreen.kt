package com.app.garant.presenter.screens.auth

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.LoginRequest
import com.app.garant.databinding.ScreenAuthorizationBinding
import com.app.garant.presenter.viewModel.auth.AuthorizationViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.auth.AuthorizationViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.isConnected
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationScreen : Fragment(R.layout.screen_authorization) {

    private val bind by viewBinding(ScreenAuthorizationBinding::bind)
    private val viewModel: AuthorizationViewModel by viewModels<AuthorizationViewModelImpl>()
    private var phoneSend = ""
    private val bundle = Bundle()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        view.setOnClickListener {
            it.hideKeyboard()
        }

        bind.inputPhoneNumber.setText("-")

        bind.next.setOnClickListener {
            val phoneNumber = bind.inputPhoneNumber.text?.toString()
                ?.replace("+", "")
                ?.replace(" ", "")
                ?.replace("-", "")
                ?.replace("(", "")
                ?.replace(")", "")
                ?.replace("_", "")

            MyPref(App.instance).phoneNumber = bind.inputPhoneNumber.unMasked
            if (phoneNumber != null) {
                bundle.putString(PHONE, phoneNumber)
            }

            if (phoneNumber!!.startsWith("9989") && phoneNumber.length == 12) {
                if (isConnected()) {
                    viewModel.login(
                        LoginRequest(
                            "998${bind.inputPhoneNumber.unMasked}".trim().toLong()
                        )
                    )
                    findNavController().navigate(
                        R.id.action_authorizationScreen_to_verificationScreen,
                        bundle
                    )
                }
            } else {
                val dialogBuilder = AlertDialog.Builder(activity!!)
                dialogBuilder.setMessage("Введите корректный номер телефона")
                    .setCancelable(true)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                        view.isClickable = false
                    })

                val alert = dialogBuilder.create()
                alert.show()
            }

        }

    }

    companion object {
        private const val PHONE: String = "PHONE"
    }


}
