package com.app.garant.presenter.screens.main

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.ScreenMainBinding
import com.app.garant.presenter.adapters.main.BannerSalesAdapter
import com.app.garant.presenter.adapters.main.ProductPagerAdapter
import com.app.garant.presenter.viewModel.main.MainScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.MainScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.scope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val bind by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    var nameCategory = ""
    var idCategory = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.salesPager.adapter = BannerSalesAdapter(childFragmentManager, lifecycle)

        view.setOnClickListener {
            it.hideKeyboard()
        }

        viewModel.getProducts()

        viewModel.successFlow.onEach {
            viewModel.getNames()
            bind.all.setOnClickListener {
                val action =
                    MainScreenDirections.actionMainPageToProductsScreen(nameCategory, idCategory)
                findNavController().navigate(action)
            }

            bind.container.isClickable = true
            bind.progress.visibility = View.GONE
        }.launchIn(lifecycleScope)


        viewModel.progressFlow.onEach {
            bind.progress.visibility = View.VISIBLE
            bind.container.isEnabled = false
        }.launchIn(lifecycleScope)

        viewModel.tabСontentLoad.onEach {
            bind.productsPager.adapter =
                ProductPagerAdapter(it.size, childFragmentManager, lifecycle)
            val names = it.reversed()
            TabLayoutMediator(bind.tabLayout, bind.productsPager) { tab, position ->
                tab.text = names[position]
            }.attach()
        }.launchIn(lifecycleScope)


        bind.bell.setOnClickListener {
            findNavController().navigate(R.id.action_mainPage_to_notificationScreen)
        }


        updateUI()
        toolbar()
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

        bind.productsPager.isUserInputEnabled = false

        bind.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                bind.newProducts.text = tab?.text
                StaticValue.nameCategory = tab?.text.toString()
                nameCategory = tab?.text.toString()
                idCategory = (1 + tab?.position!!.toInt())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun toolbar() {
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

        bind.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val queryX = query.trim()
                    val action =
                        MainScreenDirections.actionMainPageToSearchProductsPage(queryX)
                    findNavController().navigate(action)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

}