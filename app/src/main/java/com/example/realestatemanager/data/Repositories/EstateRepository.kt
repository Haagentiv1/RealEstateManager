package com.example.realestatemanager.data.Repositories

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.DAO.EstateDao
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstateRepository @Inject constructor(private val estateDao : EstateDao){

    val allEstate: Flow<List<Estate>> = estateDao.getItems()

    @WorkerThread
    suspend fun insert(estate: Estate) = estateDao.insert(estate)
}