package com.example.realestatemanager.ui.propertyMap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.databinding.MapActivityBinding

class MapActivity : AppCompatActivity() {

    private var _binding: MapActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MapActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.topAppBar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mapFlContainerGoogleMap.id, MapFragment()).commitNow()
        }
    }

}