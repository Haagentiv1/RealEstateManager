package com.example.realestatemanager.data.local.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.local.model.Property
import kotlinx.coroutines.flow.Flow


interface PropertyRepository {

    fun getProperties(): Flow<List<Property>>

    suspend fun getPropertyById(id: Long): LiveData<Property?>

    suspend fun insertProperty(property: Property)


}