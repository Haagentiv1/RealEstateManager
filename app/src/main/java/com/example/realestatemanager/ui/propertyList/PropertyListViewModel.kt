package com.example.realestatemanager.ui.propertyList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.model.Estate
import com.example.realestatemanager.data.repositories.CurrentEstateIdRepository
import com.example.realestatemanager.data.repositories.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val estateRepository: EstateRepository,
    private val currentEstateIdRepository: CurrentEstateIdRepository
) :
    ViewModel() {


    val estatesLiveData: LiveData<List<EstateListItemViewState>> =
        estateRepository.estates.map { estates ->
            Log.e("listsize",estates.size.toString())

            estates.map {
                EstateListItemViewState(
                    it.id,
                    it.pictures!![0],
                    it.price,
                    it.type,
                    it.location[2]
                )
            }
        }.asLiveData()

    fun onEstateClicked(id: Int) {
        currentEstateIdRepository.setCurrentId(id)
    }

     fun addEstate(estate: Estate) = viewModelScope.launch {
        estateRepository.insertEstate(estate)
    }


}