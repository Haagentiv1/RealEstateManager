package com.example.realestatemanager.data.repositories

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentEstateIdRepository @Inject constructor() {
    private val currentIdMutableLiveData = MutableLiveData<Long>()
    val currentIdLiveData: LiveData<Long> = currentIdMutableLiveData

    @MainThread
    fun setCurrentId(currentId: Long) {
        currentIdMutableLiveData.value = currentId
    }
}