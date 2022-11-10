package com.example.realestatemanager.ui.realEstateLoan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.realestatemanager.databinding.FragmentLoanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanFragment : Fragment() {

    private var _binding : FragmentLoanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanBinding.inflate(inflater)
        return  binding.root
    }
}