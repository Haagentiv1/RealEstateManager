package com.example.realestatemanager.ui.propertyCreation

import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.realestatemanager.databinding.AddPropertyFragmentBinding
import java.io.IOException


class AddPropertyFragment : Fragment() {


    private var _binding: AddPropertyFragmentBinding? = null
    private val binding get() = _binding!!


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


        val takePicture =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                Glide.with(this).load(it).into(binding.addPropertyIvPropertyPicture)
            }

        binding.addPropertyBtnAddImage.setOnClickListener {
            takePicture.launch()
        }
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


