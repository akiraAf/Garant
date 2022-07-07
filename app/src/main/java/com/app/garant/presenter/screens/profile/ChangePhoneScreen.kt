package com.app.garant.presenter.screens.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.profile.ChangePhoneRequest
import com.app.garant.databinding.ScreenChangeNumberBinding
import com.app.garant.presenter.viewModel.profile.ChangeNumberViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.ChangeNumberViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.isConnected
import com.app.garant.utils.scope
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ChangePhoneScreen : Fragment(R.layout.screen_change_number) {

    private val bind by viewBinding(ScreenChangeNumberBinding::bind)
    private val viewModel: ChangeNumberViewModel by viewModels<ChangeNumberViewModelImpl>()
    private val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = bind.scope {
        super.onViewCreated(view, savedInstanceState)



        bind.inputCurrentNumber.setText("  ${MyPref(App.instance).phoneNumber}")

        view.setOnClickListener {
            it.hideKeyboard()
        }
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.successFlow.onEach {
            findNavController().navigate(
                R.id.action_changePhoneNumberPage_to_receiveConfirmationCodePage2,
                bundle
            )
        }.launchIn(lifecycleScope)

        viewModel.errorFlow.onEach {
            //    showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.progressFlow.onEach {
        }.launchIn(lifecycleScope)

        bind.next.setOnClickListener {

            val currentNumber = "998${inputCurrentNumber.unMasked}"
            val newNumber = "998${inputNewNumber.unMasked}"

            bundle.putString(PHONE_ARG, newNumber)
            bundle.putString(PHONE_ARG_TEXT_VIEW, inputNewNumber.text.toString())

            if (currentNumber == newNumber)
                showToast("Два одиннаковых номер телефона")
            else if (isConnected()) {
                viewModel.changeNumber(ChangePhoneRequest(newNumber.toLong()))
                findNavController().navigate(
                    R.id.action_changePhoneNumberPage_to_receiveConfirmationCodePage2,
                    bundle
                )
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
        const val PHONE_ARG = "PHONE_ARG"
        const val PHONE_ARG_TEXT_VIEW = "PHONE_ARG_TEXT_VIEW"
    }
}