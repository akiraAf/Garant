package com.app.garant.presenter.screens.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.databinding.ScreenAccountBinding
import com.app.garant.presenter.viewModel.profile.AccountScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.AccountScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.skydoves.powerspinner.IconSpinnerItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class AccountScreen : Fragment(R.layout.screen_account) {

    private val bind by viewBinding(ScreenAccountBinding::bind)
    private val viewModel: AccountScreenViewModel by viewModels<AccountScreenViewModelImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumber = MyPref(App.instance).phoneNumber
        bind.inputPhoneNumber.setText("  $phoneNumber")
        bind.inputPhoneNumber.isEnabled = false


        bind.uploadPassportBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        viewModel.errorFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.errorFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        bind.save.setOnClickListener {
            findNavController().navigate(R.id.action_accountPage_to_profileScreen)

        }

        view.setOnClickListener {
            it.hideKeyboard()
        }

        // viewModel success flow regions and profession
        getAddress()
        getProfession()
    }

    private fun getAddress() {

        viewModel.getRegionsName()

        bind.inputRegion.isEnabled = false
        bind.inputCity.isEnabled = false
        bind.inputArea.isEnabled = false

        val regionSpinnerPreference = bind.inputRegion
        val citySpinnerPreference = bind.inputCity

        regionSpinnerPreference.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            viewModel.getRegionCity(newItem, "")
            bind.inputCity.isEnabled = true
            bind.inputCity.clearSelectedItem()
            bind.inputArea.clearSelectedItem()
            citySpinnerPreference.setOnSpinnerItemSelectedListener<String> { oldIndexX, oldItemX, newIndexX, newItemX ->
                bind.inputArea.isEnabled = true
                bind.inputArea.clearSelectedItem()
                viewModel.getRegionCity(newItem, newItemX)
            }
        }

        viewModel.successFlowRegion.onEach { regions ->
            if (regions.isNotEmpty()) {
                bind.inputRegion.setItems(regions)
            }
            bind.inputRegion.isEnabled = true
        }.launchIn(lifecycleScope)

        viewModel.successFlowCity.onEach { city ->
            bind.inputCity.setItems(city)
        }.launchIn(lifecycleScope)

        viewModel.successFlowDistrict.onEach { district ->
            bind.inputArea.setItems(district)
        }.launchIn(lifecycleScope)

    }


    private fun getProfession() {
        viewModel.getProfession()

        viewModel.successFlowProfession.onEach {
            bind.profession.setItems(it)
        }.launchIn(lifecycleScope)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            viewModel.sendDocuments(
                DocumentRequest(
                    uri,
                    "passport"
                )
            )
            bind.selfiePassportExample.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            showToast(ImagePicker.getError(data))
        } else {
            showToast("Task Cancelled")
        }
    }

}

