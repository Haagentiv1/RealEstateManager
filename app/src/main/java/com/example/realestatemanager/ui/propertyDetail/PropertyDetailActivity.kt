package com.example.realestatemanager.ui.propertyDetail

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.databinding.PropertyDetailActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyDetailActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val binding = PropertyDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.propertyDetailFlContainer.id,PropertyDetailFragment())
                .commitNow()
        }
    }
}