package com.app.garant.presenter.containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ContainerOrderingBinding


class ContainerOrdering : Fragment(R.layout.container_ordering) {

    private val bind by viewBinding(ContainerOrderingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}