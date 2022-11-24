package com.example.realestatemanager.ui.propertyMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    propertyRepository: PropertyRepository
) : ViewModel() {


    val properties = propertyRepository.getProperties().map { properties ->
        properties.map {
            MapPropertyViewState(
                it.id,
                it.pictures[0].first,
                it.price,
                it.location
            )
        }
    }.asLiveData()
}