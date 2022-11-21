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

    ) : ViewModel() {

    private val currentPropertyId = MutableLiveData<Long?>().apply { value = null }

    val pictureListLiveData = MutableLiveData<List<Pair<String, String>>>()

    val property = MutableLiveData<Property?>()

    fun setPicturesList(list: List<Pair<String, String>>) {
        pictureListLiveData.value = list
    }


    fun getPropertyById(id: Long): LiveData<Property?> {

        val property : MutableLiveData<Property?> = propertyRepository.getPropertyById(id).asLiveData() as MutableLiveData<Property?>
        currentPropertyId.value = id
        return property

    }


    fun insertProperty(property: Property) = viewModelScope.launch {
        propertyRepository.insertProperty(
            Property(
                currentPropertyId.value,
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


    fun insertPointOfInterest(poi: String) = viewModelScope.launch {
        pointOfInterestRepository.insertPointOfInterest(PointOfInterest(null, poi))
    }


    fun setPicture(pictureDes: Pair<String, String>) {
        pictureListLiveData.value =
            pictureListLiveData.value?.plus(pictureDes) ?: listOf(pictureDes)
    }
}