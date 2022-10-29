package com.example.realestatemanager.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.databinding.MainActivityBinding
import com.example.realestatemanager.ui.propertyDetail.PropertyDetailFragment
import com.example.realestatemanager.ui.propertyList.PropertyListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFlContainerPropertyList.id,PropertyListFragment())
                .commitNow()
        }

        if (binding.mainFlContainerPropertyDetail != null && supportFragmentManager.findFragmentById(binding.mainFlContainerPropertyDetail.id) == null){
            supportFragmentManager.beginTransaction()
                .add(
                    binding.mainFlContainerPropertyDetail.id,
                    PropertyDetailFragment()
                )
                .commitNow()
        }



    }
}