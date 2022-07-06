package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.main.BannerProductAdapter
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.databinding.ScreenProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ProductDetailsScreen : Fragment(R.layout.screen_product_details) {

    private val bind by viewBinding(ScreenProductDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerProductAdapter = BannerProductAdapter(childFragmentManager, lifecycle)

        bind.bannerProduct.adapter = bannerProductAdapter

        bind.bannerProduct.registerOnPageChangeCallback(pagerChangePos)
        month_btn()



        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.fullDetails.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsPage3_to_fullDetails3)
        }

        bind.buyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsPage3_to_nav_ordering)
        }

        bind.arrangeInstallment.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsPage3_to_nav_installment)
        }
    }


    private val pagerChangePos = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
//            bind.indicators.selection = position
        }
    }

    private fun month_btn() {
        val param = LinearLayout.LayoutParams(
            170,
            170,
            0F
        )

        val paramDef = LinearLayout.LayoutParams(
            210,
            170,
            0F
        )
        param.setMargins(15, 0, 15, 0)
        paramDef.setMargins(10, 0, 10, 0)


        bind.apply {

            button1Month.layoutParams = param
            button3Month.layoutParams = param
            button6Month.layoutParams = param
            button9Month.layoutParams = param
            button12Month.layoutParams = param

            button1Month.setOnClickListener {
                button1Month.layoutParams = paramDef
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }

            button3Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = paramDef
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }
            button6Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = paramDef
                button9Month.layoutParams = param
                button12Month.layoutParams = param
            }
            button9Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = paramDef
                button12Month.layoutParams = param
            }
            button12Month.setOnClickListener {
                button1Month.layoutParams = param
                button3Month.layoutParams = param
                button6Month.layoutParams = param
                button9Month.layoutParams = param
                button12Month.layoutParams = paramDef
            }
        }
    }
}