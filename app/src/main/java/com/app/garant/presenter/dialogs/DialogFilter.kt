package com.app.garant.presenter.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.DialogFilterBinding

class DialogFilter : DialogFragment(R.layout.dialog_filter) {
    private val bind by viewBinding(DialogFilterBinding::bind)

    private var use: ((Unit) -> Unit)? = null
    private var reset: ((Unit) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bind.yesBtn
            .setOnClickListener {
                use?.invoke(Unit)
            }

        bind.noBtn
            .setOnClickListener {
                reset?.invoke(Unit)
            }
    }

    fun setUse(f: (Unit) -> Unit) {
        use = f
    }

    fun setReset(f: (Unit) -> Unit) {
        reset = f
    }
}