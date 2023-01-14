package com.ahmedmatem.android.workmeter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ahmedmatem.android.workmeter.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment)
                as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{ _, dest, _ ->
            // hide navigation icon in Site_list Fragment
            if(dest.id == R.id.siteListFragment){
                toolbar.navigationIcon = null
            }
        }
    }
}