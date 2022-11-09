package com.example.realestatemanager.data.local.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.local.model.Property
import kotlinx.coroutines.flow.Flow


interface PropertyRepository {

    fun getEstates(): Flow<List<Property>>

    suspend fun getEstateById(id: Long): LiveData<Property?>

    suspend fun insertEstate(property: Property)


}