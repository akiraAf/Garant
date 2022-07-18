package com.app.garant

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.app.App
import com.app.garant.data.other.StaticValue
import com.app.garant.data.pref.MyPref
import com.app.garant.databinding.ActivityMainBinding
import com.app.garant.presenter.viewModel.activity.MainActivityViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.activity.MainActivityViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val bind by viewBinding(ActivityMainBinding::bind)

    private val viewModel: MainActivityViewModel by viewModels<MainActivityViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (MyPref(App.instance).startScreen) {
            val navHost =
                supportFragmentManager.findFragmentById(R.id.containerActivity) as NavHostFragment
            val navController = navHost.navController.navInflater.inflate(R.navigation.nav_start)
            navHost.navController.graph = navController
            navController.setStartDestination(R.id.navigationScreen)
            navHost.navController.navigate(R.id.navigationScreen)
        }

        StaticValue.screenLogoutLiveData.observe(this, observer)

    }

    override fun onBackPressed() {}

    private val observer = Observer<Unit> {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.containerActivity) as NavHostFragment
        val navController = navHost.navController.navInflater.inflate(R.navigation.nav_start)
        navHost.navController.graph = navController
        navController.setStartDestination(R.id.languageScreen)
        navHost.navController.navigate(R.id.languageScreen)
    }

    private val observerMain = Observer<Unit> {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.containerActivity) as NavHostFragment
        val navController = navHost.navController.navInflater.inflate(R.navigation.nav_start)
        navHost.navController.graph = navController
        navController.setStartDestination(R.id.navigationScreen)
        navHost.navController.navigate(R.id.navigationScreen)
    }

    private val observerPopBack = Observer<Unit> {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.containerNav) as NavHostFragment
        val navController = navHost.navController.navInflater.inflate(R.navigation.nav_start)
        navHost.navController.graph = navController
        navHost.navController.popBackStack()
    }


}