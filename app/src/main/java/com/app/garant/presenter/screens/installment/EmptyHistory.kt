package com.app.garant.presenter.screens.installment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.garant.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class EmptyHistory : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_empty_history, container, false)
    }


}