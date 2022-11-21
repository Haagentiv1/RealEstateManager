package com.example.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.util.Log
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
        val intent = intent.getLongExtra("propertyId",-1)

        if (savedInstanceState == null){
            Log.e("test", intent.toString())
            val bundle = Bundle()
            bundle.putLong("propertyId",intent)
            val fragment =AddPropertyFragment()
            fragment.arguments  =bundle
            supportFragmentManager.beginTransaction().replace(binding.addPropertyFlContainer.id,fragment).commitNow()
        }
    }
}