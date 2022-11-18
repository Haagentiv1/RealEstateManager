package com.example.realestatemanager.ui.propertyCreation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realestatemanager.data.local.model.PointOfInterest
import com.example.realestatemanager.databinding.AddPropertyFragmentBinding
import com.example.realestatemanager.ui.propertyDetail.PictureAdapter
import com.example.realestatemanager.ui.propertyList.PropertyListAdapter
import com.example.realestatemanager.ui.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class AddPropertyFragment : Fragment() {


    private val FILENAME_FORMAT = "dd-MM-yyyy-hh-mm-ss"
    private var _binding: AddPropertyFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddPropertyViewModel>()
    lateinit var actualPictureFilePath : String



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

        val recyclerView : RecyclerView = binding.addPropertyRvPictures
        val adapter = PictureAdapter()
        recyclerView.adapter = adapter

        viewModel.pictureListLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    

        viewModel.pointOfInterestLiveData.observe(viewLifecycleOwner){
            val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,it)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.addPropertyTypeSpinner.adapter = arrayAdapter
        }


        binding.addPropertyIvPropertyPicture
        val takePicture =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                val name = Calendar.getInstance().timeInMillis.toString()
                if (it != null) {
                   val isSaved = savePhotoToInternalStorage(name,it)
                    if  (isSaved){
                        retrievePictureFromFile(name)
                    }else{
                    }
                }


            }



        binding.addPropertyBtnTakePicture.setOnClickListener {
            takePicture.launch()
        }

        binding.addPropertyBtnAddPicture.setOnClickListener {
            val desc = binding.addPropertyEtPictureDesc.text.toString()
            viewModel.setPicture(Pair(first = actualPictureFilePath, second = desc))
        }
    }



    fun retrievePictureFromFile(name : String){
        Log.e("testRestrie",name)
        val files = context?.filesDir?.listFiles()
        val image =  files?.filter { it.name.equals("$name.jpg") } ?: listOf()
        val file = image[0]?.absoluteFile
        actualPictureFilePath = file.toString()
        Log.e("fileString",file.toString())
        Glide.with(this).load(file).into(binding.addPropertyIvPropertyPicture)
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


