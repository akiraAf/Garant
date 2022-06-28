package com.app.garant.presenter.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemRecyclerBinding
import com.app.garant.models.ProductData
import com.app.garant.presenter.adapters.ProductsAdapter

class PopularPage : Fragment(R.layout.item_recycler) {

    private val bind by viewBinding(ItemRecyclerBinding::bind)
    private var data = ArrayList<ProductData>()
    private val adapterProduct = ProductsAdapter(data)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        for (i in 1..20){
            data.add( ProductData("2 300 000 сум", "x 12 мес", "10 700 000 сум",
                "Apple iPhone 12 \n128GB", R.drawable.watch)
            )
        }

        bind.recycler.layoutManager = GridLayoutManager(activity, 2)
        bind.recycler.adapter =adapterProduct

        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }
    }

}
