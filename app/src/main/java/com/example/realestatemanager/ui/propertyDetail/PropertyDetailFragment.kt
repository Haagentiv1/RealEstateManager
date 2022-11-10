package com.example.realestatemanager.ui.propertyDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.realestatemanager.databinding.PropertyDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyDetailFragment : Fragment() {

    private var _binding : PropertyDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PropertyDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PropertyDetailFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailPropertyLiveData.observe(viewLifecycleOwner){
            Log.e("testpicturesize",it.picturesList.size.toString())
            binding.propertyDetailTvDescription.text = it.description
            binding.propertyDetailTvSquareMeterData.text = it.surfaceInSquareMeter.toString()
            binding.propertyDetailNumberOfRoomsData.text = it.numberOfRooms.toString()
            binding.propertyDetailNumberOfBedRoomsData.text = it.numberOfBedRooms.toString()
            binding.propertyDetailNumberOfBathRoomsData.text = it.numberOfBathRooms.toString()
            binding.propertyDetailAddress.text = it.address
            binding.propertyDetailFlatNumber.text = it.state
            binding.propertyDetailTown.text = it.town
            binding.propertyDetailZipcode.text = it.zipCode
            binding.propertyDetailCountry.text = it.country
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}