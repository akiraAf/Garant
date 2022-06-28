package com.app.garant.presenter.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.DialogCleanBasketBinding

class DialogCleanBasket : DialogFragment(R.layout.dialog_clean_basket) {

    private val bind by viewBinding(DialogCleanBasketBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}