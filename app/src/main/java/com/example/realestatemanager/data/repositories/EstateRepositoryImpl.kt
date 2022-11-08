package com.example.realestatemanager.data.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.Dao.EstateDao
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.flow.Flow

class EstateRepositoryImpl (private val dao: EstateDao) : EstateRepository {
    override fun getEstates(): Flow<List<Estate>> {
        return dao.getEstates()
    }

    override suspend fun getEstateById(id: Long): LiveData<Estate?> {
        return  dao.getEstateById(id)
    }

    override suspend fun insertEstate(estate: Estate) {
        return dao.insertEstate(estate)
    }
}