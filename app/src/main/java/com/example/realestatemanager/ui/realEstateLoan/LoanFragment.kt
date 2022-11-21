package com.example.realestatemanager.ui.realEstateLoan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.realestatemanager.databinding.FragmentLoanBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class LoanFragment : Fragment() {

    private var _binding : FragmentLoanBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loanBtnSum.setOnClickListener {
            if (checkEtState()){
                binding.loanTvLoanResult.text = sumLoan()
            }
            else {
                Toast.makeText(context,"Some fields are empty",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sumLoan(): String = (((binding.loanEtPrice.text.toString().toInt() *
            binding.loanEtTaxe.text.toString().toFloat()) -
            binding.loanEtApport.text.toString().toInt())
            / (binding.loanEtTime.text.toString().toInt() * 12)).roundToInt().toString()

    private fun checkEtState(): Boolean {
        val listOfEt = listOf(binding.loanEtPrice.text,binding.loanEtTaxe.text,binding.loanEtApport.text,binding.loanEtTime.text)
        var result = true
        listOfEt.forEach {
            if (it.isNullOrBlank()){
                result = false
            }
        }
        return result
    }


}