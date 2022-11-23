package com.example.realestatemanager.ui.propertyList

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.databinding.PropertyListFragmentBinding
import com.example.realestatemanager.ui.utils.Type
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class PropertyListFragment : Fragment() {
    private var _binding: PropertyListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var poiDialogBuilder: AlertDialog.Builder
    private lateinit var typeDialogBuilder: AlertDialog.Builder


    private val viewModel by viewModels<PropertyListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PropertyListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = binding.propertyListFragmentRv
        val adapter = PropertyListAdapter {
            viewModel.onPropertyClicked(it)
        }

        poiDialogBuilder = AlertDialog.Builder(context)
        typeDialogBuilder = AlertDialog.Builder(context)
        setTypeDialog()

        recyclerView.adapter = adapter
        viewModel.propertyLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.setFilterBoolean(false)

        viewModel.propertiesTown.observe(viewLifecycleOwner) {
            binding.listFilterEtDropdownTownList.setSimpleItems(it.toTypedArray())
        }

        binding.propertyListBtnFilter.setOnClickListener {
            Log.e("test", "onClick")
            if (binding.fragmentListRlContainer.visibility == View.GONE) {
                binding.fragmentListRlContainer.visibility = View.VISIBLE
            } else {
                binding.fragmentListRlContainer.visibility = View.GONE
            }
        }

        binding.filterRsPrice.setLabelFormatter {
            return@setLabelFormatter "$${it.roundToInt()}"
        }

        binding.filterRsMonth.setLabelFormatter {
            return@setLabelFormatter if (it.roundToInt() == 0) "No sell filter" else "Sell since ${it.roundToInt()} month"

        }

        binding.filterRsSurface.setLabelFormatter {
            return@setLabelFormatter "${it.roundToInt()}"
        }

        viewModel.poiLiveData.observe(viewLifecycleOwner) {
            createPoiDialog(it)
        }

        binding.listFilterTvPoiSelectionDialog.setOnClickListener {
            poiDialogBuilder.show()
        }

        viewModel.priceMinMax.observe(viewLifecycleOwner){
            Log.e("test","${it.first} and ${it.second}")
            binding.filterRsPrice.valueFrom = it.first.toFloat()
            binding.filterRsPrice.valueTo= it.second.toFloat()
            binding.filterRsPrice.setValues(it.first.toFloat(),it.second.toFloat())
        }
        viewModel.surface.observe(viewLifecycleOwner){
            binding.filterRsSurface.valueFrom = it.first
            binding.filterRsSurface.valueTo= it.second
            binding.filterRsSurface.setValues(it.first,it.second)
        }



        binding.listFilterTvTypeSelectionDialog.setOnClickListener {
            typeDialogBuilder.show()
        }

        binding.filterBtnFilterList.setOnClickListener {
            val type: List<String> =
                if (binding.listFilterTvTypeSelectionDialog.text.isNullOrBlank()) Type.values()
                    .toList()
                    .map { it.name } else binding.listFilterTvTypeSelectionDialog.text!!.split(",")

            viewModel.filtered(type).observe(viewLifecycleOwner) {
                Log.e("test", "onchanged")
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                } else {
                    Toast.makeText(
                        context,
                        "There is no property corresponding to selected filets",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            viewModel.setFilterBoolean(true)
        }


    }

    private fun createPoiDialog(list: List<String>) {
        val poiCheck = mutableListOf<String>()

        poiDialogBuilder.setTitle("Choose Poi").setCancelable(true).setMultiChoiceItems(
                list.toTypedArray(), BooleanArray(list.size)
            ) { _, index, check ->
                if (check) {
                    poiCheck.add(list[index])
                }
                Log.e("Poi", poiCheck.toString())
                binding.listFilterTvPoiSelectionDialog.setText(poiCheck.joinToString(","))
            }
        poiDialogBuilder.create()
    }


    private fun setTypeDialog() {
        val types = Type.values().toList().map { it.name }.toTypedArray()
        val typesChecked = mutableListOf<String>()
        typeDialogBuilder.setTitle("Choose Type").setCancelable(false).setMultiChoiceItems(
                types, BooleanArray(types.size)
            ) { _, index, check ->
                if (check) {
                    typesChecked.add(types[index])
                }

                binding.listFilterTvTypeSelectionDialog.setText(typesChecked.joinToString(","))
            }

        typeDialogBuilder.setPositiveButton("Ok") { dialog, which ->
            dialog.dismiss()
        }
        typeDialogBuilder.setNegativeButton("Reset") { dialog, which ->
            dialog.cancel()
        }
        typeDialogBuilder.create()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}