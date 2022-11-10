package com.example.realestatemanager.ui.realEstateLoan

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import com.example.realestatemanager.databinding.LoanActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanActivity : AppCompatActivity() {

    private var _binding: LoanActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LoanActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.topAppBar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.loanFlContainerLoan.id, LoanFragment()).commitNow()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar_menu,menu)
        val editMenuItem = menu?.findItem(R.id.menu_item_edit)
        val addMenuItem = menu?.findItem(R.id.menu_item_add)
        val filterMenuItem = menu?.findItem(R.id.menu_item_filter)
        editMenuItem?.isVisible = false
        addMenuItem?.isVisible = false
        filterMenuItem?.isVisible = false
        invalidateMenu()
        return true
    }
}