package com.example.realestatemanager.ui.propertyDetail

import androidx.lifecycle.*
import com.example.realestatemanager.BuildConfig
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    currentEstateIdRepository: CurrentPropertyIdRepository,
    private val propertyRepository: PropertyRepository,
) : ViewModel() {


    val BASE_URl = "https://maps.googleapis.com/maps/api/staticmap?"
    val size = "200x200"



        val detailPropertyLiveData: LiveData<PropertyDetailViewState> =
            currentEstateIdRepository.currentIdLiveData.switchMap { id ->
                liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                    val result = propertyRepository.getPropertyById(id).map { estate ->
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
                            country = estate.location[4],
                            mapStaticString = "${BASE_URl}center=${estate.location.joinToString().trim()}&zoom=15&size=${size}&markers=${estate.location.joinToString().trim()}&key=${BuildConfig.STATIC_MAP_API_KEY}"
                        )
                    }
                    emitSource(result)
                }
            }


}