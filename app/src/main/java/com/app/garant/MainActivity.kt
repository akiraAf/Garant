package com.app.garant

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.app.App
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.ActivityMainBinding
import com.app.garant.presenter.viewModel.activity.MainActivityViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.activity.MainActivityViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val bind by viewBinding(ActivityMainBinding::bind)

    private val viewModel: MainActivityViewModel by viewModels<MainActivityViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHost.navController.navInflater.inflate(R.navigation.nav_start)
        navHost.navController.graph = navController
        navController.setStartDestination(R.id.languageScreen)

        viewModel.errorFlow.onEach {
            val pref = MyPref(App.instance)
            if (pref.authControll) {
                navController.setStartDestination(R.id.navigationScreen)
                navHost.navController.graph = navController
            }
        }.launchIn(lifecycleScope)


//        if (MyPref(App.instance).startScreen) {
//            findNavController(navHost).navigate(R.id.languageScreen)
//        }
        StaticValue.screenNavigateLiveData.observe(this,observer)
    }
    private val observer=Observer<Unit>
    {
        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHost.navController.navInflater.inflate(R.navigation.nav_start)
        navHost.navController.graph = navController
        navController.setStartDestination(R.id.languageScreen)
        navHost.navController.navigate(R.id.languageScreen)
    }
}