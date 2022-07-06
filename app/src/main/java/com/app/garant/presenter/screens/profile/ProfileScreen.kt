package com.app.garant.presenter.screens.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.ScreenProfileBinding
import com.app.garant.presenter.dialogs.DialogLogout
import com.app.garant.presenter.dialogs.DialogLanguage
import com.app.garant.presenter.viewModel.profile.ProfileViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.profile.ProfileViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ProfileScreen : Fragment(R.layout.screen_profile) {

    private val bind by viewBinding(ScreenProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.account.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_accountPage)
        }

        bind.favorite.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_favoritesPage)
        }

        bind.changePhoneNumber.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_changePhoneNumberPage)
        }

        bind.orderHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreen_to_emptyHistory)
        }


        viewModel.errorFlow.onEach {
            showToast("Ошибка")
        }

        bind.logout.setOnClickListener {
            val dialog = DialogLogout()
            dialog.show(parentFragmentManager, "DialogLogOut")
            dialog.setNo {
                dialog.dismiss()
            }

            dialog.setYes {
                viewModel.getLogout()
                dialog.dismiss()
                viewModel.successFlow.onEach {
                    MyPref(App.instance).startScreen = true
                }.launchIn(lifecycleScope)
                StaticValue.screenNavigateLiveData.value = Unit
            }
        }

        bind.language.setOnClickListener {
            val language = DialogLanguage()
            language.show(parentFragmentManager, "DialogLanguage")
            language.setRu {
                language.dismiss()
                showToast("Выбран русский язык")
            }
            language.setUz {
                language.dismiss()
                showToast("O‘zbek tili tanlandi")
            }
        }
    }


}