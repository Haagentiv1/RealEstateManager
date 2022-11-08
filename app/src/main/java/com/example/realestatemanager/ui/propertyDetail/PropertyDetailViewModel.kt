package com.example.realestatemanager.ui.propertyDetail

import androidx.lifecycle.*
import com.example.realestatemanager.data.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.data.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    currentEstateIdRepository: CurrentPropertyIdRepository,
    private val propertyRepository: PropertyRepository
) : ViewModel() {


        val detailPropertyLiveData: LiveData<PropertyDetailViewState> =
            currentEstateIdRepository.currentIdLiveData.switchMap { id ->
                liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                    val result = propertyRepository.getEstateById(id).map { estate ->
                        PropertyDetailViewState(
                            id = estate!!.id!!,
                            picturesList = estate.pictures!!,
                            description = estate.description,
                            surfaceInSquareMeter = estate.squareMeter,
                            numberOfRooms = estate.numberOfRooms,
                            numberOfBedRooms = estate.numberOfBedRooms,
                            numberOfBathRooms = estate.numberOfBathRooms,
                            address = estate.location[0],
                            town = estate.location[1],
                            state = estate.location[2],
                            zipCode = estate.location[3],
                            country = estate.location[4]
                        )
                    }
                    emitSource(result)
                }
            }


}