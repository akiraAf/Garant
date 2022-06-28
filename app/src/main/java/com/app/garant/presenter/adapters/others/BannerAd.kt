package com.app.garant.presenter.adapters.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.FragmentBannerAdBinding


class BannerAd : Fragment(R.layout.fragment_banner_ad) {

    private val bind by viewBinding(FragmentBannerAdBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(requireArguments().getInt("mda", 0)){
            1 ->{
                bind.img.setImageResource(R.drawable.ad)
            }

            else->{
                bind.img.setImageResource(R.drawable.ad)
            }
        }
    }

}