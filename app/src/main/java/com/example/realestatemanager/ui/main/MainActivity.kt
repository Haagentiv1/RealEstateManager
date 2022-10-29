package com.example.realestatemanager.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.databinding.MainActivityBinding
import com.example.realestatemanager.ui.propertyDetail.PropertyDetailFragment
import com.example.realestatemanager.ui.propertyList.PropertyListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFlContainerPropertyList.id, PropertyListFragment())
                .commitNow()
        }

        if (binding.mainFlContainerPropertyDetail != null && supportFragmentManager.findFragmentById(
                binding.mainFlContainerPropertyDetail.id
            ) == null
        ) {
            supportFragmentManager.beginTransaction()
                .add(
                    binding.mainFlContainerPropertyDetail.id,
                    PropertyDetailFragment()
                )
                .commitNow()
        }
        viewModel.navigateSingleLiveEvent.observe(this){
            when(it){
                MainViewAction.NavigateToCreatePropertyActivity -> TODO()
                MainViewAction.NavigateToPropertyDetailActivity -> TODO()
                MainViewAction.NavigateToPropertyMapExplorerActivity -> TODO()
                MainViewAction.NavigateToRealEstateLoanActivity -> TODO()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.onConfigurationChanged(resources.getBoolean(R.bool.isTablet))
    }
}