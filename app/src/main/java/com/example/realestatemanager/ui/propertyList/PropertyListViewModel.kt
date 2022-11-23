package com.example.realestatemanager.ui.propertyList

import android.util.Log
import androidx.lifecycle.*
import com.example.realestatemanager.data.local.model.PointOfInterest
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.data.local.repositories.PointOfInterestRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import com.example.realestatemanager.ui.utils.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
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
                        it.location[2]
                    )
                }
            }.asLiveData()

    fun filtered(list: List<String>): LiveData<List<PropertyListItemViewState>> = liveData (context = viewModelScope.coroutineContext + Dispatchers.IO) {
        Log.e("test", list.toString())
        Log.e("testType",Type.values().toList().toString())
        val data = propertyList.map { properties ->
            properties.filter { property ->
                list.contains(property.type)
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
        emitSource(data)
    }

    val propertyLiveData1: LiveData<List<PropertyListItemViewState>> =
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


    lateinit var lisT: List<String>

    fun setTypeFilter(list: List<String>) {
        lisT = list
    }


    private var typePredicate = { value: String -> lisT.contains(value) }

    var pricePredicate = { value: Long -> listOfPrice[0] <= value && listOfPrice[1] >= value }

    var surfacePredicate =
        { value: Float -> listOfSurface[0] <= value && listOfSurface[1] >= value }

    var poiPredicate = { value: List<String> -> value.any(listOfPoi::contains) }


    fun getFilteredList(): LiveData<List<PropertyListItemViewState>> {
        return propertyRepository.getProperties().map { properties ->
            properties.filter { property ->
                typePredicate.invoke(property.type).and(pricePredicate.invoke(property.price))
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
    }


    val propertyLiveDataFiltered: LiveData<List<PropertyListItemViewState>> =
        propertyRepository.getProperties().map { properties ->
            properties.filter { property ->
                typePredicate.invoke(property.type).and(pricePredicate.invoke(property.price))
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


    fun onPropertyClicked(id: Long) {
        currentPropertyIdRepository.setCurrentId(id)
    }

    fun addProperty(property: Property) = viewModelScope.launch {
        propertyRepository.insertProperty(property)
    }


}