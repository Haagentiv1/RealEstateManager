package com.example.realestatemanager.ui.propertyCreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    val pictureListLiveData : MutableLiveData<List<Pair<String,String>>> by lazy {
        MutableLiveData<List<Pair<String, String>>>()
    }




    fun setPicture(pictureDes : Pair<String,String>){
        pictureListLiveData.value = pictureListLiveData.value?.plus(pictureDes) ?: listOf(pictureDes)
    }
}