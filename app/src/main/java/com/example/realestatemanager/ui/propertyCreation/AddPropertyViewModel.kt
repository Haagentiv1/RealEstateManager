package com.example.realestatemanager.ui.propertyCreation

import androidx.lifecycle.*
import com.example.realestatemanager.data.local.model.PointOfInterest
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.data.local.repositories.PointOfInterestRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentPropertyId: Long? = null


    init {
        savedStateHandle.get<Long>("propertyId")?.let { propertyId ->
            if (propertyId != (-1).toLong()) {
                viewModelScope.launch {
                    propertyRepository.getPropertyById(propertyId).also {
                        currentPropertyId = it.value?.id
                    }
                }
            }
        }
    }

    fun insertProperty(property: Property) = viewModelScope.launch {
        propertyRepository.insertProperty(
            Property(
                currentPropertyId,
                property.type,
                property.description,
                property.price,
                property.squareMeter,
                property.location,
                property.numberOfRooms,
                property.numberOfBedRooms,
                property.numberOfBedRooms,
                pictureListLiveData.value!!,
                property.poi,
                property.status,
                property.entryDate,
                property.saleDate,
                property.estateManagerName
            )
        )
    }

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
        pointOfInterestRepository.insertPointOfInterest(PointOfInterest(null, poi))
    }


    fun setPicture(pictureDes: Pair<String, String>) {
        pictureListLiveData.value =
            pictureListLiveData.value?.plus(pictureDes) ?: listOf(pictureDes)
    }
}