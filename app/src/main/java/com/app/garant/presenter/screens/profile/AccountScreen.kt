package com.app.garant.presenter.screens.profile

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.data.request.profile.request.UserRequest
import com.app.garant.databinding.ScreenAccountBinding
import com.app.garant.presenter.viewModel.profile.AccountScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.AccountScreenViewModelImpl
import com.app.garant.utils.FileUtils
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
    private var districtId: Int? = null
    private var workplace: String? = null
    private var workplaceId: Int? = null
    private var type: String? = null
    private val checkAcc = MyPref(App.instance).account
    private val args by navArgs<AccountScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.entryFlag) {
            bind.save.text = "ДАЛЕЕ"
        }
        viewModel.getUserInfo()
        if (checkAcc) {

        }

        val phoneNumber = MyPref(App.instance).phoneNumber
        bind.inputPhoneNumber.setText("  $phoneNumber")
        bind.inputPhoneNumber.isEnabled = false


        viewModel.errorFlow.onEach {
            //       showToast(it)
        }.launchIn(lifecycleScope)

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.errorFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)


        view.setOnClickListener {
            it.hideKeyboard()
        }

        // viewModel success flow regions, profession and save
        getAddress()
        getProfession()
        getUserInfo()
        save()
        docUpload()
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
                    viewModel.successFlowDistrictId.onEach { districtId = it[newIndexY] }
                        .launchIn(lifecycleScope)
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

        val workplaceSpinnerPreference = bind.profession
        workplaceSpinnerPreference.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            workplace = newItem
            viewModel.successFlowProfessionId.onEach { workplaceId = it[newIndex] }
                .launchIn(lifecycleScope)
        }

    }

    private fun save() {
        bind.save.setOnClickListener {
            if (args.entryFlag) {
                findNavController().navigate(R.id.action_accountScreen_to_installmentSentModerationScreen)
            } else if (checkAcc) {
                if (bind.inputExtraPhoneNumber.text!!.isNotEmpty()
                    && regionX!!.isNotEmpty()
                    && city!!.isNotEmpty()
                    && district!!.isNotEmpty()
                    && bind.inputAddress.text!!.isNotEmpty()
                    && bind.work.text!!.isNotEmpty()
                    && type!!.isNotEmpty()
                ) {
                    viewModel.sendUserInfo(
                        UserRequest(
                            bind.inputAddress.text!!.toString(),
                            bind.work.text!!.toString(),
                            districtId!!,
                            "998${bind.inputExtraPhoneNumber.unMasked}".toLong(),
                            workplaceId!!
                        )
                    )

                    MyPref(App.instance).account = true
                    showToast("Загрузите фото или скан паспорта")
                } else showToast("Заполните все поля")
            } else if (!checkAcc) {
                if (bind.inputAddress.text!!.toString().isNotEmpty() &&
                    workplace!!.isNotBlank() &&
                    districtId!!.toString().isNotBlank() &&
                    bind.inputExtraPhoneNumber.unMasked.isNotBlank() &&
                    workplaceId.toString().isNotBlank()
                )
                    viewModel.sendUserInfo(
                        UserRequest(
                            bind.inputAddress.text!!.toString(),
                            bind.work.text!!.toString(),
                            districtId!!,
                            "998${bind.inputExtraPhoneNumber.unMasked}".toLong(),
                            workplaceId!!
                        )
                    )
                findNavController().navigate(R.id.action_accountScreen_to_profileScreen)
            }
        }
    }

    private fun getUserInfo() {

        viewModel.successFlowGetUserInfo.onEach {
            bind.header.isVisible = true
            bind.progress.isVisible = false
            if (it.documents.passport == "waiting") {
                bind.expectationPassportTextView.setText(R.string.awaiting_confirmation)
                bind.expectationPassportTextView.setTextColor(Color.parseColor("#E7B901"))
            } else if (it.documents.passport == "confirmed") {
                bind.expectationPassportTextView.setText(R.string.confirmed)
                bind.expectationPassportTextView.setTextColor(Color.parseColor("#64D6A2"))
            }

            if (it.documents.registration == "waiting") {
                bind.expectationRegistrationTextView.setText(R.string.awaiting_confirmation)
                bind.expectationRegistrationTextView.setTextColor(Color.parseColor("#E7B901"))
            } else if (it.documents.registration == "confirmed") {
                bind.expectationRegistrationTextView.setText(R.string.confirmed)
                bind.expectationRegistrationTextView.setTextColor(Color.parseColor("#64D6A2"))
            }

            if (it.documents.selfie == "waiting") {
                bind.expectationSelfiePassportTextView.setText(R.string.awaiting_confirmation)
                bind.expectationSelfiePassportTextView.setTextColor(Color.parseColor("#E7B901"))
            } else if (it.documents.selfie == "confirmed") {
                bind.expectationSelfiePassportTextView.setText(R.string.confirmed)
                bind.expectationSelfiePassportTextView.setTextColor(Color.parseColor("#64D6A2"))
            }
            bind.inputExtraPhoneNumber.setText(it.contacts.additional_phone.toString())
            bind.inputRegion.text = it.address.region.name
            bind.inputCity.text = it.address.city.name
            bind.inputAddress.setText(it.address.address)
            bind.inputArea.text = it.address.district.name
            bind.profession.text = it.profession.profession.name
            bind.work.setText(it.profession.company)
            districtId = it.address.district.id
            workplaceId = it.profession.profession.id
            workplace = it.profession.company
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun uploadPic(textView: TextView) {
        textView.setText(R.string.awaiting_confirmation)
        textView.setTextColor(Color.parseColor("#E7B901"))
    }

    private fun docUpload() {
        bind.uploadPassportBtn.setOnClickListener {
            imgPiker()
            type = "passport"
        }

        bind.uploadRegistrationBtn.setOnClickListener {
            imgPiker()
            type = "registration"
        }

        bind.selfiePassportBtn.setOnClickListener {
            imgPiker()
            type = "selfie"
        }
    }

    private fun imgPiker() {
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

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    if (type == "passport")
                        uploadPic(bind.expectationPassportTextView)
                    else if (type == "selfie")
                        uploadPic(bind.expectationSelfiePassportTextView)
                    else if (type == "registration")
                        uploadPic(bind.expectationRegistrationTextView)
                    file = File(FileUtils.getPath(requireContext(), fileUri))
                    if (file != null) {
                        viewModel.sendDocuments(
                            DocumentRequest(
                                file!!,
                                type!!
                            )
                        )
                    }
                }
            }
        }
}