package com.example.realestatemanager.ui.propertyList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val currentEstateIdRepository: CurrentPropertyIdRepository
) :
    ViewModel() {


    val estatesLiveData: LiveData<List<PropertyListItemViewState>> =
        propertyRepository.getProperties().map { estates ->
            Log.e("listsize",estates.size.toString())

            estates.map {
                PropertyListItemViewState(
                    it.id!!,
                    it.pictures!![0],
                    it.price,
                    it.type,
                    it.location[2]
                )
            }
        }.asLiveData()

    fun onEstateClicked(id: Long) {
        currentEstateIdRepository.setCurrentId(id)
    }

     fun addEstate(property: Property) = viewModelScope.launch {
        propertyRepository.insertProperty(property)
    }


}