package com.example.realestatemanager.data.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.Dao.EstateDao
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EstateRepository @Inject constructor(private val estateDao: EstateDao) {

    val estates: Flow<List<Estate>> = estateDao.getEstates()

    suspend fun getEstateById(id: Int) : LiveData<Estate?>{
        return estateDao.getEstateById(id)
    }

    suspend fun insertEstate(estate: Estate) {
        estateDao.insertEstate(estate)
    }


}