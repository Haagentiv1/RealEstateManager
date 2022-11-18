package com.example.realestatemanager.ui.propertyCreation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.databinding.AddPropertyActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPropertyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AddPropertyActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.topAppBar)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(binding.addPropertyFlContainer.id,AddPropertyFragment()).commitNow()
        }
    }
}