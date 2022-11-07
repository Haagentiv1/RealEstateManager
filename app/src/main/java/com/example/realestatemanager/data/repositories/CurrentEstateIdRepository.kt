package com.example.realestatemanager.data.repositories

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentEstateIdRepository @Inject constructor() {
    private val currentIdMutableLiveData = MutableLiveData<Int>()
    val currentIdLiveData: LiveData<Int> = currentIdMutableLiveData

    @MainThread
    fun setCurrentId(currentId: Int) {
        currentIdMutableLiveData.value = currentId
    }
}