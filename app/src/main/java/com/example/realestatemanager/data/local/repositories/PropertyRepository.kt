package com.example.realestatemanager.data.local.repositories

import com.example.realestatemanager.data.local.model.Property
import kotlinx.coroutines.flow.Flow


interface PropertyRepository {

    fun getProperties(): Flow<List<Property>>

    fun getPropertyById(id: Long): Flow<Property?>

    suspend fun insertProperty(property: Property)


}