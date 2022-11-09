package com.example.realestatemanager.data.local.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.local.dao.PropertyDao
import com.example.realestatemanager.data.local.model.Property
import kotlinx.coroutines.flow.Flow

class PropertyRepositoryImpl (private val dao: PropertyDao) : PropertyRepository {
    override fun getProperties(): Flow<List<Property>> {
        return dao.getProperties()
    }

    override suspend fun getPropertyById(id: Long): LiveData<Property?> {
        return  dao.getPropertyById(id)
    }

    override suspend fun insertProperty(property: Property) {
        return dao.insertProperty(property)
    }
}