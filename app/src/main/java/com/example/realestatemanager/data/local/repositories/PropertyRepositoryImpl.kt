package com.example.realestatemanager.data.local.repositories

import com.example.realestatemanager.data.local.dao.PropertyDao
import com.example.realestatemanager.data.local.model.Property
import kotlinx.coroutines.flow.Flow

class PropertyRepositoryImpl (private val dao: PropertyDao) : PropertyRepository {
    override fun getProperties(): Flow<List<Property>> {
        return dao.getProperties()
    }

    override fun getPropertyById(id: Long): Flow<Property?> {
        return  dao.getPropertyById(id)
    }

    override suspend fun insertProperty(property: Property) {
        return dao.insertProperty(property)
    }
}