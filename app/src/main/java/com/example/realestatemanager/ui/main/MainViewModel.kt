package com.example.realestatemanager.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currentEstateIdRepository: CurrentPropertyIdRepository
) : ViewModel() {

    private var isTablet: Boolean = false
    var state = MediatorLiveData<String>()
    val navigateSingleLiveEvent = SingleLiveEvent<MainViewAction>()


    init {
        navigateSingleLiveEvent.addSource(currentEstateIdRepository.currentIdLiveData) {
            if (!isTablet) {
                navigateSingleLiveEvent.setValue(MainViewAction.NavigateToPropertyDetailActivity)
            }
        }
    }

    fun onConfigurationChanged(isTablet: Boolean) {
        this.isTablet = isTablet
    }

}