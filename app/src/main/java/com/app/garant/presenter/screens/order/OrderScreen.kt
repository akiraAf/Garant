package com.app.garant.presenter.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class OrderScreen : Fragment(R.layout.page_order) {


    private val bind by viewBinding(PageOrderBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}