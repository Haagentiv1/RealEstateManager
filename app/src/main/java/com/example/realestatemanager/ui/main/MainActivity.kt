package com.example.realestatemanager.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.databinding.MainActivityBinding
import com.example.realestatemanager.ui.propertyDetail.PropertyDetailFragment
import com.example.realestatemanager.ui.propertyList.PropertyListFragment
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding!!
    lateinit var materialToolbar: MaterialToolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.topAppBar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFlContainerPropertyList.id, PropertyListFragment())
                .commitNow()
        }

        if (binding.mainFlContainerPropertyDetail != null && supportFragmentManager.findFragmentById(
                binding.mainFlContainerPropertyDetail!!.id
            ) == null
        ) {
            supportFragmentManager.beginTransaction()
                .add(
                    binding.mainFlContainerPropertyDetail!!.id,
                    PropertyDetailFragment()
                )
                .commitNow()
        }

        viewModel.navigateSingleLiveEvent.observe(this) {
            when (it) {
                MainViewAction.NavigateToCreatePropertyActivity -> Toast.makeText(this,"",Toast.LENGTH_LONG).show()
                MainViewAction.NavigateToPropertyDetailActivity -> TODO()
                MainViewAction.NavigateToPropertyMapExplorerActivity -> TODO()
                MainViewAction.NavigateToRealEstateLoanActivity -> TODO()
            }
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar_menu,menu)
        if (binding.mainFlContainerPropertyDetail == null){
           val menuItemToHide = menu?.findItem(R.id.menu_item_edit)
            menuItemToHide?.isVisible = false
            invalidateMenu()
            return true
        }
        return true
    }



    override fun onResume() {
        super.onResume()
        viewModel.onConfigurationChanged(resources.getBoolean(R.bool.isTablet))
    }
}