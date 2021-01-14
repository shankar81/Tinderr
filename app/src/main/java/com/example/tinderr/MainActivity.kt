package com.example.tinderr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.tinderr.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val showToolbarFrags = arrayListOf("Select Source")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment)

        // set up toolbar
        setUpToolbar()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val fragment = supportFragmentManager.findFragmentById(R.id.loaderContainer)

        if (fragment == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.loaderContainer, LoaderFragment())
                .commit()
        }

        navController.addOnDestinationChangedListener(this)
    }

    private fun setUpToolbar() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        Log.d(TAG, "onDestinationChanged: ${destination.label} ${destination.navigatorName} ${destination.id}")
        when (destination.label) {
            in showToolbarFrags -> {
                binding.toolbar.visibility = View.VISIBLE
            }
            else -> {
                binding.toolbar.visibility = View.GONE
            }
        }
    }
}