package com.app.garant.presenter.screens.main

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.app.App
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.ScreenMainBinding
import com.app.garant.presenter.adapters.main.BannerSalesAdapter
import com.app.garant.presenter.adapters.main.ProductPagerAdapter
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.scope
import com.app.garant.utils.showToast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainScreen : Fragment(R.layout.screen_main) {

    private val bind by viewBinding(ScreenMainBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.salesPager.adapter = BannerSalesAdapter(childFragmentManager, lifecycle)
        bind.productsPager.adapter = ProductPagerAdapter(childFragmentManager, lifecycle)

        view.setOnClickListener {
            it.hideKeyboard()
        }

        updateUI()


        TabLayoutMediator(bind.tabLayout, bind.productsPager) { tab, position ->
            tab.text = tabProductTitles[position]

        }.attach()



        bind.bell.setOnClickListener {
            findNavController().navigate(R.id.action_mainPage_to_notificationScreen)
        }


        bind.telegram.setOnClickListener {
            bind.apply {
                logoImage.visibility = View.INVISIBLE
                bell.visibility = View.GONE
                telegram.visibility = View.GONE
                search.visibility = View.GONE
                tgLink.visibility = View.VISIBLE
                countNotification.visibility = View.GONE
            }
        }

        bind.link.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://t.me/android_jobs_for_future_tashkent")
            )
            startActivity(intent)
        }

        bind.close.setOnClickListener {
            bind.apply {
                logoImage.visibility = View.VISIBLE
                bell.visibility = View.VISIBLE
                telegram.visibility = View.VISIBLE
                search.visibility = View.VISIBLE
                tgLink.visibility = View.GONE
                countNotification.visibility = View.VISIBLE
            }
        }
    }

    private fun updateUI() {

        val param = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

        val paramDef = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

        bind.search.setOnSearchClickListener {
            bind.scope {
                searchLayout.layoutParams = param
                logoImage.visibility = View.GONE
                telegram.visibility = View.GONE
                bell.visibility = View.GONE
                countNotification.visibility = View.GONE
            }
        }

        bind.search.setOnCloseListener {
            bind.scope {
                searchLayout.layoutParams = paramDef
                logoImage.visibility = View.VISIBLE
                telegram.visibility = View.VISIBLE
                countNotification.visibility = View.VISIBLE
                bell.visibility = View.VISIBLE
            }
            false
        }

        bind.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                bind.newProducts.text = tab?.text
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    companion object {
        val tabProductTitles = listOf("Топ продаж", "Акции", "Популярное")
    }

}