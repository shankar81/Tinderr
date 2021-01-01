package com.example.tinderr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val fragment = supportFragmentManager.findFragmentById(R.id.loaderContainer)

        if (fragment == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.loaderContainer, LoaderFragment())
                .commit()
        }

    }
}