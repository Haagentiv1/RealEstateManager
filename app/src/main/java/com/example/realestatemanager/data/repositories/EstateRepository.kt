package com.example.realestatemanager.data.repositories

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.Dao.EstateDao
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface EstateRepository {

    fun getEstates(): Flow<List<Estate>>

    suspend fun getEstateById(id : Int) : Estate?

    suspend fun insertEstate(estate: Estate)


}