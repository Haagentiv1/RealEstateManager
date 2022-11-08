package com.example.realestatemanager.data.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.model.Property
import kotlinx.coroutines.flow.Flow


interface PropertyRepository {

    fun getEstates(): Flow<List<Property>>

    suspend fun getEstateById(id: Long): LiveData<Property?>

    suspend fun insertEstate(property: Property)


}