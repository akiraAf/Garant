package com.app.garant.presenter.screens.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.installment.InstallmentAdapter
import com.app.garant.databinding.PagePopularBinding
import com.app.garant.models.InstallmentHistoryData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstallmentRecyclerView(val data:ArrayList<InstallmentHistoryData>)
    : Fragment(R.layout.page_popular) {

    private val bind by viewBinding(PagePopularBinding::bind)
    private val installmentAdapter by lazy {  InstallmentAdapter(data) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bind.recycler.layoutManager = LinearLayoutManager(activity)

        bind.recycler.adapter =  installmentAdapter

        installmentAdapter.setListenerClick {
            findNavController().navigate(R.id.action_installmentHistoryScreen2_to_installmentPage)
        }


    }

}