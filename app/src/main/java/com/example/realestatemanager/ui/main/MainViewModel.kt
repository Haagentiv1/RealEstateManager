package com.example.realestatemanager.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.realestatemanager.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var isTablet: Boolean = false
    val state = MediatorLiveData<String>().apply {
        value = ""
    }
    val navigateSingleLiveEvent = SingleLiveEvent<MainViewAction>()


    init {
        navigateSingleLiveEvent.addSource(state) {
            if (!isTablet) {
                navigateSingleLiveEvent.setValue(MainViewAction.NavigateToCreatePropertyActivity)
            }
        }
    }

    fun onConfigurationChanged(isTablet: Boolean) {
        this.isTablet = isTablet
    }

}