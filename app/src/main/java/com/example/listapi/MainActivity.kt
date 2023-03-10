package com.example.listapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.listapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        val bottomNavigationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.dashboardFragment,R.id.filterList, R.id.favouritesFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener{ _, nd: NavDestination, _->
            if (nd.id == R.id.dashboardFragment || nd.id == R.id.favouritesFragment || nd.id == R.id.filterList){
                binding.bottomNavigation.visibility = View.VISIBLE
            }
            else binding.bottomNavigation.visibility = View.GONE
        }
    }
}