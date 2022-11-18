package com.example.realestatemanager.ui.propertyCreation

import android.widget.Toast
import androidx.lifecycle.*
import com.example.realestatemanager.data.local.model.PointOfInterest
import com.example.realestatemanager.data.local.repositories.PointOfInterestRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val pointOfInterestRepository: PointOfInterestRepository
) : ViewModel() {

    val pointOfInterestLiveData: LiveData<List<String>> =
        pointOfInterestRepository.getPointOfInterest().map { list ->
            list.map { poi ->
                poi.name
            }
        }.asLiveData()


    val pictureListLiveData: MutableLiveData<List<Pair<String, String>>> by lazy {
        MutableLiveData<List<Pair<String, String>>>()
    }


     fun insertPointOfInterest(poi: String) = viewModelScope.launch {
            pointOfInterestRepository.insertPointOfInterest(PointOfInterest(null,poi))
        }


    fun setPicture(pictureDes: Pair<String, String>) {
        pictureListLiveData.value =
            pictureListLiveData.value?.plus(pictureDes) ?: listOf(pictureDes)
    }
}