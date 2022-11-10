package com.example.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.ConnectivityObserver
import com.example.realestatemanager.R
import com.example.realestatemanager.databinding.MainActivityBinding
import com.example.realestatemanager.ui.propertyDetail.PropertyDetailActivity
import com.example.realestatemanager.ui.propertyDetail.PropertyDetailFragment
import com.example.realestatemanager.ui.propertyList.PropertyListFragment
import com.example.realestatemanager.ui.propertyMap.MapActivity
import com.example.realestatemanager.ui.realEstateLoan.LoanActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.topAppBar)
        bottomNavigationView = binding.bottomAppBar.bottomNavView
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFlContainerPropertyList.id, PropertyListFragment()).commitNow()
        }

        if (binding.mainFlContainerPropertyDetail != null && supportFragmentManager.findFragmentById(
                binding.mainFlContainerPropertyDetail!!.id
            ) == null
        ) {
            supportFragmentManager.beginTransaction().add(
                binding.mainFlContainerPropertyDetail!!.id, PropertyDetailFragment()
            ).commitNow()
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_nav_map -> {
                    viewModel.networkStatus.observe(this) { result ->
                        when (result) {
                            ConnectivityObserver.Status.Available ->
                                startActivity(
                                    Intent(this@MainActivity, MapActivity::class.java)
                                )
                            ConnectivityObserver.Status.Unavailable -> Toast.makeText(
                                this, "Network Unavailable", Toast.LENGTH_LONG
                            ).show()
                            else -> {
                                Toast.makeText(this, "No signal", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    true
                }
                R.id.menu_nav_loan -> {
                    startActivity(
                        Intent(this@MainActivity, LoanActivity::class.java)
                    )
                    true
                }
                else -> true
            }
        }


        viewModel.navigateSingleLiveEvent.observe(this) {
            when (it) {
                MainViewAction.NavigateToCreatePropertyActivity -> TODO()
                MainViewAction.NavigateToPropertyDetailActivity -> startActivity(
                    Intent(
                        this@MainActivity, PropertyDetailActivity::class.java
                    )
                )
                MainViewAction.NavigateToPropertyMapExplorerActivity -> startActivity(
                    Intent(
                        this@MainActivity, PropertyDetailActivity::class.java
                    )
                )
                MainViewAction.NavigateToRealEstateLoanActivity -> TODO()
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.menu_item_add -> {
                viewModel.networkStatus.observe(this) {
                    when (it) {
                        ConnectivityObserver.Status.Available -> Toast.makeText(
                            this, "Network Available", Toast.LENGTH_LONG
                        ).show()
                        ConnectivityObserver.Status.Unavailable -> Toast.makeText(
                            this, "Network Unavailable", Toast.LENGTH_LONG
                        ).show()
                        else -> {
                            Toast.makeText(this, "No signal", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                return true
            }
        }
        return false

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        if (binding.mainFlContainerPropertyDetail == null) {
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