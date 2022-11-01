package com.example.realestatemanager.ui.propertyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.data.Repositories.EstateRepository
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.launch
import javax.inject.Inject

class PropertyListViewModel @Inject constructor(private val estateRepository: EstateRepository) :
    ViewModel() {

        val allEstate: LiveData<List<Estate>> = estateRepository.allEstate.asLiveData()

        fun insertEstate(estate: Estate) = viewModelScope.launch {
            estateRepository.insert(estate)
        }

}