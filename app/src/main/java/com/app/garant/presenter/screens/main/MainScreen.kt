package com.app.garant.presenter.screens.main

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.ScreenMainBinding
import com.app.garant.presenter.adapters.main.BannerSalesAdapter
import com.app.garant.presenter.adapters.main.ProductPagerAdapter
import com.app.garant.presenter.adapters.search.SearchAdapter
import com.app.garant.presenter.viewModel.main.MainScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.MainScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.scope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val bind by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val adapterSearch by lazy { SearchAdapter() }
    private var tabArray: ArrayList<String>? = null
    private var tabMediator: TabLayoutMediator? = null
    private var nameCategory = ""
    private var idCategory = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        initAdapters()
        initObservables()
        initClicks()
        updateSearchView()
        updateToolbar()
        searchQuery()
        bind.salesPager.isSaveEnabled = false
    }

    private fun initClicks() {
        bind.bell.setOnClickListener {
            findNavController().navigate(R.id.action_mainPage_to_notificationScreen)
        }

        adapterSearch.setListenerClick { text ->
            viewModel.cancelProcess()
            val action =
                MainScreenDirections.actionMainPageToSearchProductsPage(text)
            findNavController().navigate(action)
        }

        view?.setOnClickListener {
            it.hideKeyboard()
        }

    }

    private fun initObservables() {

        viewModel.successSearch.onEach {
            val arrayList: ArrayList<String> = it
            adapterSearch.submitList(arrayList)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.successFlow.collect {
                viewModel.getNames()

                StaticValue.mainScreenProduct = it

                bind.all.setOnClickListener {
                    val action =
                        MainScreenDirections.actionMainPageToProductsScreen(
                            nameCategory,
                            idCategory
                        )
                    findNavController().navigate(action)
                }
                bind.progress.visibility = View.GONE
            }

        }

        StaticValue.mainRequest.observe(viewLifecycleOwner, observer)

    }

    private fun initAdapters() {
        // TabLayout adapter
        if (tabArray == null)
            viewModel.tabСontentLoad.onEach {
                tabMediator?.detach()
                tabArray = it
                bind.productsPager.adapter =
                    ProductPagerAdapter(tabArray!!.size, childFragmentManager, lifecycle)
                val names = tabArray!!.reversed()
                tabMediator =
                    TabLayoutMediator(bind.tabLayout, bind.productsPager) { tab, position ->
                        tab.text = names[position]
                    }
                tabMediator?.attach()
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        else {
            bind.productsPager.adapter =
                ProductPagerAdapter(tabArray!!.size, childFragmentManager, lifecycle)
            val names = tabArray!!.reversed()

            tabMediator?.detach()
            tabMediator = TabLayoutMediator(bind.tabLayout, bind.productsPager) { tab, position ->
                tab.text = names[position]
            }
            tabMediator?.attach()
        }
        // SearchView adapter
        bind.listSearch.adapter = adapterSearch
        bind.listSearch.layoutManager = LinearLayoutManager(requireContext())

        // Banner adapter
        bind.salesPager.adapter = BannerSalesAdapter(childFragmentManager, lifecycle)

    }

    private fun updateSearchView() {

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
                listSearch.visibility = View.GONE
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

    private fun updateToolbar() {
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
                Uri.parse("https://t.me/GARANT_ADMI_N")
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

    private fun searchQuery() {
        bind.listSearch.bringToFront()
        bind.listSearch.visibility = View.GONE


        bind.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val action: NavDirections = MainScreenDirections.actionMainPageToSearchProductsPage(
                    query.toString()
                )
                findNavController().navigate(action)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSearch.submitList(emptyList())
                if (newText!!.isNotBlank())
                    viewModel.getSearch(newText)
                bind.listSearch.isVisible = newText!!.isNotBlank()
                return false
            }
        })
    }

    private fun closeSearch() {
        bind.search.setQuery("", false)
    }

    override fun onResume() {
        super.onResume()
        bind.search.clearAnimation()
        bind.search.onActionViewCollapsed();
        bind.search.onCancelPendingInputEvents()
        closeSearch()
        adapterSearch.notifyDataSetChanged()
    }

    private val observer = Observer<Unit> {
        viewModel.getProducts()
    }

}