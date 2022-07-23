package com.app.garant.presenter.screens.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.ScreenBasketEmptyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BasketEmptyScreen : Fragment(R.layout.screen_basket_empty) {

    private val bind by viewBinding(ScreenBasketEmptyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.goToCatalog.setOnClickListener {
            findNavController().popBackStack()
        }
        if (!StaticValue.cartCheck)
            findNavController().navigate(R.id.action_cartEmptyPage_to_basketPage)
    }
}