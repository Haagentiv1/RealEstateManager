package com.example.realestatemanager.ui.propertyList

import android.util.Log
import androidx.lifecycle.*
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
    private val currentPropertyIdRepository: CurrentPropertyIdRepository
) :
    ViewModel() {


    private var listOfType = listOf("Manor")
    var listOfPrice = listOf<Float>()
    var listOfSurface = listOf<Float>()
    var listOfPoi = listOf<String>()



    private val filterLiveData = MutableLiveData<Boolean>()




    fun setFilterBoolean(filter : Boolean){
        filterLiveData.value = filter
    }


    val propertyLiveData: LiveData<List<PropertyListItemViewState>> =
        propertyRepository.getProperties()
            .map { properties ->
                properties.map {
                    PropertyListItemViewState(
                        it.id!!,
                        it.pictures[0].first,
                        it.price,
                        it.type,
                        it.location[2]
                    )
                }
            }.asLiveData()

    fun selectFilter(
        listOfTypeSelected: CharSequence, listOfPriceSelected: List<Float>,
        listOfSurfaceSelected: List<Float>,
        listOfPoiSelected: CharSequence
    ) {
        if (listOfTypeSelected.isEmpty()) {
            this.listOfType = listOf("Manor", "Penthouse", "Duplex", "Loft")
        } else {
            this.listOfType = listOfTypeSelected.split(",").map { it.trim() }
        }
        this.listOfPrice = listOfPriceSelected
        this.listOfSurface = listOfSurfaceSelected
        this.listOfPoi
    }


    var typePredicate = { value: String -> listOfType.contains(value) }

    var pricePredicate = { value: Long -> listOfPrice[0] <= value && listOfPrice[1] >= value }

    var surfacePredicate =
        { value: Float -> listOfSurface[0] <= value && listOfSurface[1] >= value }

    var poiPredicate = { value: List<String> -> value.any(listOfPoi::contains) }


    val propertyLiveDataFiltered: LiveData<List<PropertyListItemViewState>> =
        propertyRepository.getProperties().map { properties ->
            properties.filter { property ->
                Log.e("testlistoftype", listOfType.toString())
                typePredicate.invoke(property.type)

            }.map {
                PropertyListItemViewState(
                    it.id!!,
                    it.pictures[0].first,
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