package com.app.garant.presenter.adapters.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemBannerBinding


class BannerProductItem : Fragment(R.layout.item_banner) {
    private val bind by viewBinding(ItemBannerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(requireArguments().getInt("position", 0)){
            1 ->{
                bind.imageBanner.setImageResource(R.drawable.cart_img)
            }

            else->{
                bind.imageBanner.setImageResource(R.drawable.headphones)
            }
        }
    }

}