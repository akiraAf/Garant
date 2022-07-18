package com.app.garant.presenter.screens.profile

import android.app.Activity
import android.os.Bundle
import android.view.View
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
import com.app.garant.utils.FileUtils
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.skydoves.powerspinner.IconSpinnerItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File


@AndroidEntryPoint
class AccountScreen : Fragment(R.layout.screen_account) {

    private val bind by viewBinding(ScreenAccountBinding::bind)
    private val viewModel: AccountScreenViewModel by viewModels<AccountScreenViewModelImpl>()
    private var file: File? = null
    private var value: DocumentRequest? = null
    private var regionX: String? = null
    private var city: String? = null
    private var district: String? = null


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    bind.selfiePassportExample.setImageURI(fileUri)
                    file = File(FileUtils.getPath(requireContext(), fileUri))
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (MyPref(App.instance).account)
            bind.save.text = "ДАЛЕЕ"

        val phoneNumber = MyPref(App.instance).phoneNumber
        bind.inputPhoneNumber.setText("  $phoneNumber")
        bind.inputPhoneNumber.isEnabled = false


        bind.uploadPassportBtn.setOnClickListener {
            ImagePicker.with(requireActivity())
                .compress(1024)
                .galleryOnly()
                .crop()
                .saveDir(
                    File(requireContext().getExternalFilesDir(null)?.absolutePath, "MyImage")
                )
                .createIntent {
                    startForProfileImageResult.launch(it)
                }
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
            if (bind.inputExtraPhoneNumber.text!!.isNotEmpty() && regionX!!.isNotEmpty() && city!!.isNotEmpty()
                && district!!.isNotEmpty() && bind.address.text!!.isNotEmpty() && bind.work.text!!.isNotEmpty()
            ) {
                if (file != null) {
                    viewModel.sendDocuments(
                        DocumentRequest(
                            file!!,
                            "passport"
                        )
                    )

                    MyPref(App.instance).account = true
                    findNavController().navigate(R.id.action_accountPage_to_profileScreen)
                } else
                    showToast("Загрузите фото или скан паспорта")
            } else showToast("Заполните все поля")

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
        val districtSpinnerPreference = bind.inputArea

        regionSpinnerPreference.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            viewModel.getRegionCity(newItem, "")
            regionX = newItem
            bind.inputCity.isEnabled = true
            bind.inputCity.clearSelectedItem()
            bind.inputArea.clearSelectedItem()
            citySpinnerPreference.setOnSpinnerItemSelectedListener<String> { oldIndexX, oldItemX, newIndexX, newItemX ->
                bind.inputArea.isEnabled = true
                city = newItemX
                bind.inputArea.clearSelectedItem()
                viewModel.getRegionCity(newItem, newItemX)
                districtSpinnerPreference.setOnSpinnerItemSelectedListener<String> { oldIndexY, oldItemY, newIndexY, newItemY ->
                    district = newItemY
                }
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


}

