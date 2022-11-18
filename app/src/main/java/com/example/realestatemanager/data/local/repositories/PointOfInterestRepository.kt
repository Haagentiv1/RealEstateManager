package com.example.realestatemanager.data.local.repositories

import com.example.realestatemanager.data.local.model.PointOfInterest
import kotlinx.coroutines.flow.Flow

interface PointOfInterestRepository {

    fun getPointOfInterest() : Flow<List<PointOfInterest>>

    suspend fun insertPointOfInterest(pointOfInterest: PointOfInterest)
}