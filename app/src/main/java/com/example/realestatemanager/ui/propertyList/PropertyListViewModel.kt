package com.example.realestatemanager.ui.propertyList

import android.util.Log
import androidx.lifecycle.*
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.data.local.repositories.PointOfInterestRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val currentPropertyIdRepository: CurrentPropertyIdRepository,
    private val pointOfInterestRepository: PointOfInterestRepository
) :
    ViewModel() {


    private var listOfType = listOf("Manor")
    var listOfPrice = listOf(1000000, 100000000)
    var listOfSurface = listOf<Float>()
    var listOfPoi = listOf<String>()


    private val filterLiveData = MutableLiveData<Boolean>()

    val propertyList: Flow<List<Property>> =
        propertyRepository.getProperties()

    val poiLiveData : LiveData<List<String>> = pointOfInterestRepository.getPointOfInterest().map { listOfPoi ->
        listOfPoi.map {
            it.name
        }
    }.asLiveData()

    val surface : LiveData<Pair<Float,Float>> =
        propertyList.map { properties ->
            properties.map {
                it.squareMeter
            }
        }.map {
            Pair(first = it.min(),second = it.max())
        }.asLiveData()

    val priceMinMax : LiveData<Pair<Long,Long>> =
        propertyList.map { properties ->
            properties.map {
                it.price
            }
        }.map {
            Pair(first = it.min(), second = it.max())
        }.asLiveData()


    fun setFilterBoolean(filter: Boolean) {
        filterLiveData.value = filter
    }

    val propertiesTown : LiveData<List<String>>  = propertyList.map { properties ->

        properties.map {
            it.location[1].trim().lowercase()
        }.distinct()
    }.asLiveData()



    val propertyLiveData: LiveData<List<PropertyListItemViewState>> =
        propertyList
            .map { properties ->
                properties.map {
                    PropertyListItemViewState(
                        it.id!!,
                        it.pictures[0].first,
                        it.price,
                        it.type,
                        it.location[1]
                    )
                }
            }.asLiveData()
    var pricePredicate = { value: Property ,minMax : Pair<Long,Long> -> value.price >= minMax.first && value.price <= minMax.second }
    private val typePredicate = { value : Property, list : List<String> -> list.contains(value.type)}
    private val poiPredicate = {value : Property,list : List<String> -> value.poi!!.containsAll(list)}
    private val townPredicate = {value : Property, list : List<String> -> list.contains(value.location[1].lowercase()) }
    var surfacePredicate = { value: Property, minMax : Pair<Float,Float> -> value.squareMeter >= minMax.first && value.squareMeter <= minMax.second }
    private val numberOfPicturesMin = {value : Property, numberOfPictureMin : Int -> value.pictures.size >= numberOfPictureMin }

    fun filtered(
        list: List<String>,
        poi: List<String>,
        town: List<String>,
        minMax: Pair<Long, Long>,
        surfaceFilter: Pair<Float, Float>,
        picturesMin: Int
    ): LiveData<List<PropertyListItemViewState>> = liveData (context = viewModelScope.coroutineContext + Dispatchers.IO) {
        listOfPoi = poi.ifEmpty { poiLiveData.value!! }
        var listOfTown = town.ifEmpty { propertiesTown.value!! }
        listOfTown = listOfTown.map { it.trim() }
        Log.e("testPrice",minMax.first.toString())
        val data = propertyList.map { properties ->
            properties.filter { property ->
                pricePredicate.invoke(property,minMax).and(typePredicate.invoke(property,list)).and(poiPredicate.invoke(property,poi)).and(surfacePredicate.invoke(property,surfaceFilter)).and(numberOfPicturesMin.invoke(property,picturesMin)).and(townPredicate.invoke(property,listOfTown))
            }.map {
                PropertyListItemViewState(
                    it.id!!,
                    it.pictures[0].first,
                    it.price,
                    it.type,
                    it.location[1]
                )
            }
        }.asLiveData()
        emitSource(data)
    }



    lateinit var lisT: List<String>

    fun setTypeFilter(list: List<String>) {
        lisT = list
    }







    fun onPropertyClicked(id: Long) {
        currentPropertyIdRepository.setCurrentId(id)
    }

    fun addProperty(property: Property) = viewModelScope.launch {
        propertyRepository.insertProperty(property)
    }


}