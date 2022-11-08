package com.example.realestatemanager.data.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.Dao.EstateDao
import com.example.realestatemanager.data.model.Property
import kotlinx.coroutines.flow.Flow

class PropertyRepositoryImpl (private val dao: EstateDao) : PropertyRepository {
    override fun getEstates(): Flow<List<Property>> {
        return dao.getEstates()
    }

    override suspend fun getEstateById(id: Long): LiveData<Property?> {
        return  dao.getEstateById(id)
    }

    override suspend fun insertEstate(property: Property) {
        return dao.insertEstate(property)
    }
}