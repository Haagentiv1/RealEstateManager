package com.example.realestatemanager.ui.propertyCreation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realestatemanager.databinding.AddPropertyFragmentBinding
import com.example.realestatemanager.ui.propertyDetail.PictureAdapter
import com.example.realestatemanager.ui.utils.Type
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class AddPropertyFragment : Fragment() {


    private var pictureList : List<Pair<String,String>>? = null
    private var _binding: AddPropertyFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddPropertyViewModel>()
    lateinit var actualPictureFilePath: String
    private lateinit var poiDialogBuilder: AlertDialog.Builder



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddPropertyFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.addPropertyRvPictures
        val adapter = PictureAdapter()
        recyclerView.adapter = adapter
        poiDialogBuilder = AlertDialog.Builder(requireContext())

        binding.addPropertyTvType.text

        viewModel.pointOfInterestLiveData.observe(viewLifecycleOwner) {
            createPoiDialog(it)
        }


        viewModel.pictureListLiveData.observe(viewLifecycleOwner) {
            pictureList= it
            adapter.submitList(it)
        }




        binding.addPropertyEtPoi.setOnClickListener {
            poiDialogBuilder.show()
        }


        val listOfType = Type.values().map { it.name }.toTypedArray()

        binding.addPropertyTvType.setSimpleItems(listOfType)



        binding.addPropertyEtEntryDate.setOnClickListener {
            createEntryDatePicker()
        }

        binding.addPropertyEtSaleDate.setOnClickListener {
            createSaleDatePicker()
        }

        val galleryPicture = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it.let {
                Glide.with(requireContext()).load(it).centerCrop()
                    .into(binding.addPropertyIvPropertyPicture)
                actualPictureFilePath = it.toString()
            }
        }

        binding.addPropertyBtnGalleryPicture.setOnClickListener {
            galleryPicture.launch("image/*")
        }

        binding.addPropertyIvPropertyPicture
        val takePicture =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                val name = Calendar.getInstance().timeInMillis.toString()
                if (it != null) {
                    val isSaved = savePhotoToInternalStorage(name, it)
                    if (isSaved) {
                        retrievePictureFromFile(name)
                    } else {
                    }
                }


            }

        binding.addPropertyBtnTakePicture.setOnClickListener {
            takePicture.launch()
        }

        binding.addPropertyBtnAddPicture.setOnClickListener {
            val desc = binding.addPropertyEtPictureDesc.text.toString()
            viewModel.setPicture(Pair(first = actualPictureFilePath, second = desc))
            binding.addPropertyEtPictureDesc.text?.clear()
            Glide.with(binding.addPropertyIvPropertyPicture)
                .clear(binding.addPropertyIvPropertyPicture)
        }

        binding.addPropertyBtnSaveProperty.setOnClickListener {
            lifecycleScope.launch {
                if (emptyInputTextCheck()){
                    if (!pictureList.isNullOrEmpty()){
                        Toast.makeText(context,"All is okay",Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(context,"You should at least add one picture",Toast.LENGTH_LONG).show()
                    }
                }

            }


        }


    }


    @SuppressLint("SetTextI18n")
    var entryDateSetListener =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            binding.addPropertyEtEntryDate.setText(
                "$dayOfMonth/${monthOfYear + 1}/$year"
            )
        }

    @SuppressLint("SetTextI18n")
    var saleDateSetListener =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            binding.addPropertyEtSaleDate.setText(
                "$dayOfMonth/${monthOfYear + 1}/$year"
            )
        }

    private fun emptyInputTextCheck(): Boolean {
        var result = true
        lifecycleScope.launch {

            val listOfInputEditText = listOf(
                binding.addPropertyTvType,
                binding.addPropertyEtPrice,
                binding.addPropertyEtSurface,
                binding.addPropertyEtNumberOfRoom,
                binding.addPropertyEtNumberOfBedRoom,
                binding.addPropertyEtNumberOfBathRoom,
                binding.addPropertyEtDesc,
                binding.addPropertyEtAddress,
                binding.addPropertyEtTown,
                binding.addPropertyEtState,
                binding.addPropertyEtZipcode,
                binding.addPropertyEtCountry,
                binding.addPropertyEtEntryDate,
                binding.addPropertyEtManager
            )
            listOfInputEditText.forEach {
                if (it.text.isNullOrBlank()){
                    it.error = "You cannot let this field empty"
                    result =false
                }else {
                    it.error = null
                }
            }
        }
        return result



    }


    private fun createEntryDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            entryDateSetListener,
            2022, 11 - 1, 19
        )
        datePicker.show()
    }

    private fun createSaleDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            saleDateSetListener,
            2022, 11 - 1, 19
        )
        datePicker.show()
    }


    private fun createPoiDialog(list: List<String>) {
        val poiCheck = mutableListOf<String>()
        poiDialogBuilder
            .setTitle("Choose Poi")
            .setCancelable(true)
            .setMultiChoiceItems(
                list.toTypedArray(),
                BooleanArray(list.size)
            ) { dialogInterface, index, check ->
                if (check) {
                    poiCheck.add(list[index])
                }
                binding.addPropertyEtPoi.setText(poiCheck.toString())
            }
        poiDialogBuilder.create()
    }


    private fun retrievePictureFromFile(name: String) {
        Log.e("testRestrie", name)
        val files = context?.filesDir?.listFiles()
        val image = files?.filter { it.name.equals("$name.jpg") } ?: listOf()
        val file = image[0]?.absoluteFile
        actualPictureFilePath = file.toString()
        Log.e("fileString", file.toString())
        Glide.with(requireContext()).load(file).into(binding.addPropertyIvPropertyPicture)
    }


    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            context?.openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap")

                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }


}


