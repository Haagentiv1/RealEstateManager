package com.example.realestatemanager.ui.propertyDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.databinding.PropertyDetailActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PropertyDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.topAppBar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.propertyDetailFlContainer.id, PropertyDetailFragment())
                .commitNow()
        }
    }
}