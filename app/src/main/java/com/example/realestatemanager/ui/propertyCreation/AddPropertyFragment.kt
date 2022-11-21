package com.example.realestatemanager.ui.propertyCreation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.databinding.AddPropertyFragmentBinding
import com.example.realestatemanager.ui.propertyDetail.PictureAdapter
import com.example.realestatemanager.ui.utils.Type
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class AddPropertyFragment : Fragment() {


    private var pictureList: List<Pair<String, String>>? = null
    private var _binding: AddPropertyFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddPropertyViewModel>()
    private lateinit var actualPictureFilePath: String
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

        viewModel.pictureListLiveData.observe(viewLifecycleOwner) {
            pictureList = it
            adapter.submitList(it)
        }

        if (arguments?.getLong("propertyId") != (-1).toLong()) {
            viewModel.getPropertyById(requireArguments().getLong("propertyId"))
                .observe(viewLifecycleOwner) {
                    lifecycleScope.launch {
                        it?.let {
                            binding.addPropertyTvType.setText(it.type)
                            binding.addPropertyEtPrice.setText(it.price.toString())
                            binding.addPropertyEtSurface.setText(it.squareMeter.toString())
                            binding.addPropertyEtNumberOfRoom.setText(it.numberOfRooms.toString())
                            binding.addPropertyEtNumberOfBedRoom.setText(it.numberOfBedRooms.toString())
                            binding.addPropertyEtNumberOfBathRoom.setText(it.numberOfBathRooms.toString())
                            binding.addPropertyEtDesc.setText(it.description)
                            binding.addPropertyEtAddress.setText(it.location[0])
                            binding.addPropertyEtTown.setText(it.location[1])
                            binding.addPropertyEtState.setText(it.location[2])
                            binding.addPropertyEtZipcode.setText(it.location[3])
                            binding.addPropertyEtCountry.setText(it.location[4])
                            binding.addPropertyEtPoi.setText(it.poi.toString())
                            binding.addPropertyEtEntryDate.setText(it.entryDate)
                            binding.addPropertyEtSaleDate.setText(it.saleDate)
                            binding.addPropertyEtManager.setText(it.estateManagerName)
                            viewModel.setPicturesList(it.pictures)
                        }
                    }
                }

        }



        poiDialogBuilder = AlertDialog.Builder(requireContext())

        binding.addPropertyTvType.text

        viewModel.pointOfInterestLiveData.observe(viewLifecycleOwner) {
            createPoiDialog(it)
        }


        viewModel.pictureListLiveData.observe(viewLifecycleOwner) {
            pictureList = it
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
                val name = Calendar.getInstance().timeInMillis.toString()
                val inputStream = context?.contentResolver?.openInputStream(it!!)
                val bmp = BitmapFactory.decodeStream(inputStream)
                val isSaved = savePhotoToInternalStorage(name, bmp)
                if (isSaved) {
                    retrievePictureFromFile(name)
                } else {
                    Log.e("tete", "ewgwewewge")
                }

            }
        }

        binding.addPropertyBtnGalleryPicture.setOnClickListener {
            galleryPicture.launch("image/gallery")
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
                        Log.e("tete", "ewgwewewge")
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
                if (emptyInputTextCheck()) {
                    if (!pictureList.isNullOrEmpty()) {
                        viewModel.insertProperty(
                            Property(
                                null,
                                type = binding.addPropertyTvType.text.toString(),
                                description = binding.addPropertyEtDesc.text.toString(),
                                price = binding.addPropertyEtPrice.text.toString().toLong(),
                                squareMeter = binding.addPropertyEtSurface.text.toString()
                                    .toFloat(),
                                location = getLocation(),
                                numberOfRooms = binding.addPropertyEtNumberOfRoom.text.toString()
                                    .toInt(),
                                numberOfBedRooms = binding.addPropertyEtNumberOfBedRoom.text.toString()
                                    .toInt(),
                                numberOfBathRooms = binding.addPropertyEtNumberOfBathRoom.text.toString()
                                    .toInt(),
                                poi = getPoi(),
                                status = binding.addPropertyEtSaleDate.text.isNullOrBlank(),
                                pictures = listOf(),
                                entryDate = binding.addPropertyEtEntryDate.text.toString(),
                                saleDate = binding.addPropertyEtSaleDate.text.toString(),
                                estateManagerName = binding.addPropertyEtManager.text.toString()
                            )
                        )
                        Snackbar.make(binding.root, "Property well inserted", Snackbar.LENGTH_LONG)
                            .show()
                        activity?.finish()
                    } else {
                        Toast.makeText(
                            context,
                            "You should at least add one picture",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Some field are empty", Toast.LENGTH_LONG).show()
                }

            }


        }


    }


    private fun getPoi(): List<String> {
        val poi = binding.addPropertyEtPoi.text.toString()
        return poi.split(",")
    }


    private fun getLocation(): List<String> {
        val address = binding.addPropertyEtAddress.text.toString()
        val town = binding.addPropertyEtTown.text.toString()
        val state = binding.addPropertyEtState.text.toString()
        val zipcode = binding.addPropertyEtZipcode.text.toString()
        val country = binding.addPropertyEtCountry.text.toString()

        return listOf(address, town, state, zipcode, country)
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
            if (it.text.isNullOrBlank()) {
                it.error = "You cannot let this field empty"
                result = false
            } else {
                it.error = null
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


