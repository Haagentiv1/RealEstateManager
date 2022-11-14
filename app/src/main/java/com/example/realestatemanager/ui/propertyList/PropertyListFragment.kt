package com.example.realestatemanager.ui.propertyList

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
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
        val recyclerView: RecyclerView = binding.propertyListFragmentRv
        val adapter = PropertyListAdapter {
            viewModel.onPropertyClicked(it)
        }


        recyclerView.adapter = adapter
        viewModel.propertyLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.setFilterBoolean(false)


        binding.propertyListBtnFilter.setOnClickListener {
            Log.e("test","onClick")
            if (binding.fragmentListRlContainer.visibility == View.GONE) {
                binding.fragmentListRlContainer.visibility = View.VISIBLE
            } else {
                binding.fragmentListRlContainer.visibility = View.GONE
            }
        }

        binding.filterRsPrice.setValues(1f, 1000000f)
        binding.filterRsSurface.setValues(0f,1000f)
        binding.listFilterTvTypeSelectionDialog.setOnClickListener { setTypeDialog() }

        binding.filterBtnFilterList.setOnClickListener {
            val type = binding.listFilterTvTypeSelectionDialog.text
            val price = binding.filterRsPrice.values
            val surface = binding.filterRsSurface.values
            val poi = binding.listFilterTvPoiSelectionDialog.text
            val test = listOf(type,price,surface,poi)
            Log.e("test",test.toString())

        viewModel.selectFilter(type,price,surface,poi)


        viewModel.setFilterBoolean(true)
        }

    }

    fun setTypeDialog() {
        val types = arrayOf("Loft", "Penthouse", "Manor", "Duplex")
        val typesSelected = booleanArrayOf(false,false,false,false)
        val typesChecked = mutableListOf<String>()
        val builder = AlertDialog.Builder(context)
            .setTitle("Choose Type")
            .setCancelable(false)


        builder.setMultiChoiceItems(
            types,
            typesSelected
        ) { dialogInterface, index, check ->
            if (check) {
                typesChecked.add(types[index])
            }

            binding.listFilterTvTypeSelectionDialog.text = typesChecked.toString()
        }

        builder.setPositiveButton("Ok"){dialog, which ->
            dialog.dismiss()
        }
        builder.setNegativeButton("Reset"){dialog, which ->
            dialog.cancel()
        }



        builder.create()
        builder.show()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}