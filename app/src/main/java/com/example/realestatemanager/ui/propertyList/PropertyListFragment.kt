package com.example.realestatemanager.ui.propertyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.data.model.Estate
import com.example.realestatemanager.databinding.PropertyListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PropertyListFragment : Fragment() {
    private var _binding: PropertyListFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<PropertyListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PropertyListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = binding.root
        val adapter = EstateListAdapter {
            viewModel.onEstateClicked(it)
        }

        recyclerView.adapter = adapter
        viewModel.estatesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}