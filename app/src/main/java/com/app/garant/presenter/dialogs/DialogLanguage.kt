package com.app.garant.presenter.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.DialogLanguageBinding

class DialogLanguage : DialogFragment(R.layout.dialog_language) {


    private var ru: ((Unit) -> Unit)? = null
    private var uz: ((Unit) -> Unit)? = null
    val bind by viewBinding(DialogLanguageBinding::bind)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (MyPref(App.instance).language) {
            bind.uzbekRadio.isChecked = false
            bind.russianRadio.isChecked = true
        } else {
            bind.uzbekRadio.isChecked = true
            bind.russianRadio.isChecked = false
        }

        bind.uzbekRadio
            .setOnClickListener {
                bind.uzbekRadio.isChecked = true
                bind.russianRadio.isChecked = false
                MyPref(App.instance).language = false
                uz?.invoke(Unit)
            }

        bind.russianRadio
            .setOnClickListener {
                bind.uzbekRadio.isChecked = false
                bind.russianRadio.isChecked = true
                MyPref(App.instance).language = true
                ru?.invoke(Unit)
            }
    }


    fun setRu(f: (Unit) -> Unit) {
        ru = f
    }

    fun setUz(f: (Unit) -> Unit) {
        uz = f
    }

}