package com.example.realestatemanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.realestatemanager.ConnectivityObserver
import com.example.realestatemanager.NetworkConnectivityObserver
import com.example.realestatemanager.data.local.repositories.CurrentPropertyIdRepository
import com.example.realestatemanager.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    currentEstateIdRepository: CurrentPropertyIdRepository,
    networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    var networkStatus : LiveData<ConnectivityObserver.Status> = networkConnectivityObserver.observe().asLiveData()

    private var isTablet: Boolean = false
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