package com.example.realestatemanager.data.repositories

import androidx.lifecycle.LiveData
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.flow.Flow


interface EstateRepository {

    fun getEstates(): Flow<List<Estate>>

    suspend fun getEstateById(id: Long): LiveData<Estate?>

    suspend fun insertEstate(estate: Estate)


}