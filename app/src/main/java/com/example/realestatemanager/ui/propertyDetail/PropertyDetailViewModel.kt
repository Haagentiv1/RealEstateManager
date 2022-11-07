package com.example.realestatemanager.ui.propertyDetail

import androidx.lifecycle.*
import com.example.realestatemanager.data.repositories.CurrentEstateIdRepository
import com.example.realestatemanager.data.repositories.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    currentEstateIdRepository: CurrentEstateIdRepository,
    private val estateRepository: EstateRepository
) : ViewModel() {


        val detailPropertyLiveData: LiveData<PropertyDetailViewState> =
            currentEstateIdRepository.currentIdLiveData.switchMap { id ->
                liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                    val result = estateRepository.getEstateById(id).map { estate ->
                        PropertyDetailViewState(
                            id = estate!!.id,
                            picturesList = estate.pictures!!,
                            description = estate.description,
                            surface = estate.surface,
                            numberOfRooms = estate.numberOfRooms,
                            numberOfBedRooms = estate.numberOfBedRooms,
                            numberOfBathRooms = estate.numberOfBathRooms,
                            address = estate.location[0],
                            town = estate.location[1],
                            addressNumber = estate.location[2],
                            zipCode = estate.location[3],
                            country = estate.location[4]
                        )
                    }
                    emitSource(result)
                }
            }


}