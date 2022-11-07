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

    private fun addData() {
        val estate =
            Estate(
                id = null,
                type = "House",
                description = "Centre Ville - 4 place du Sanitat appartement T1 meublé se composant d'une entrée, pièce de vie avec cuisine a/e, une salle d'eau et wc séparé. Forfait d'électricité de 60 euros en sus Disponible immédiatement\n",
                price = "1250000",
                surface = 250,
                location = listOf("24 rue de fleurimont", "Redon", "24", "35600", "France"),
                numberOfRooms = 7,
                numberOfBedRooms = 2,
                numberOfBathRooms = 1,
                pictures = listOf("https://images.pexels.com/photos/323780/pexels-photo-323780.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","https://images.pexels.com/photos/101808/pexels-photo-101808.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","https://images.pexels.com/photos/2121121/pexels-photo-2121121.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","https://images.pexels.com/photos/2635038/pexels-photo-2635038.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","https://images.pexels.com/photos/1974596/pexels-photo-1974596.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
                poi = listOf("Piscine","Commerce","Boulangerie","Riviere"),
                status = true,
                entryDate = "20/10/2022",
                saleDate = null,
                estateManagerName = "tristan"
                )
        viewModel.addEstate(estate)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}