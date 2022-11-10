package com.example.realestatemanager.ui.realEstateLoan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

}