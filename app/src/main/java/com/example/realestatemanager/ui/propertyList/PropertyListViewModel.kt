package com.example.realestatemanager.ui.propertyList

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val currentPropertyIdRepository: CurrentPropertyIdRepository
) :
    ViewModel() {


    val propertyLiveData: LiveData<List<PropertyListItemViewState>> =
        propertyRepository.getProperties()
            .map { properties ->
            properties.map {
                PropertyListItemViewState(
                    it.id!!,
                    it.pictures!![0],
                    it.price,
                    it.type,
                    it.location[2]
                )
            }
        }.asLiveData()




    fun onPropertyClicked(id: Long) {
        currentPropertyIdRepository.setCurrentId(id)
    }

     fun addProperty(property: Property) = viewModelScope.launch {
        propertyRepository.insertProperty(property)
    }




}