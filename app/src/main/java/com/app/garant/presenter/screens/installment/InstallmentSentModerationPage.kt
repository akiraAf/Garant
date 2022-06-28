package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageInstallmentSentModerationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentSentModerationPage : Fragment(R.layout.page_installment_sent_moderation) {

    private val bind by viewBinding(PageInstallmentSentModerationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}