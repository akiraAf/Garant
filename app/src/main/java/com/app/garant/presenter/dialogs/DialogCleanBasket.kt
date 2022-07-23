package com.app.garant.presenter.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.DialogCleanBasketBinding
import com.app.garant.databinding.DialogLogoutBinding

class DialogCleanBasket : DialogFragment(R.layout.dialog_clean_basket) {

    private val bind by viewBinding(DialogCleanBasketBinding::bind)
    private var yes: ((Unit) -> Unit)? = null
    private var no: ((Unit) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bind.yesBtn
            .setOnClickListener {
                yes?.invoke(Unit)
            }

        bind.noBtn
            .setOnClickListener {
                no?.invoke(Unit)
            }
    }

    fun setYes(f: (Unit) -> Unit) {
        yes = f
    }

    fun setNo(f: (Unit) -> Unit) {
        no = f
    }


}