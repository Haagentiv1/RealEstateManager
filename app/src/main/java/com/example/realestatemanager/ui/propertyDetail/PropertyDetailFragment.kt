package com.example.realestatemanager.ui.propertyDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.realestatemanager.databinding.PropertyDetailFragmentBinding

class PropertyDetailFragment : Fragment() {

    private var _binding : PropertyDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PropertyDetailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
}