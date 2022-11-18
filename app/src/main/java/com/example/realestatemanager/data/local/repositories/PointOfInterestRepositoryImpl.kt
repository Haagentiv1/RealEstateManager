package com.example.realestatemanager.data.local.repositories

import com.example.realestatemanager.data.local.dao.PointOfInterestDao
import com.example.realestatemanager.data.local.model.PointOfInterest
import kotlinx.coroutines.flow.Flow

class PointOfInterestRepositoryImpl(private val dao: PointOfInterestDao) : PointOfInterestRepository {
    override fun getPointOfInterest(): Flow<List<PointOfInterest>> {
        return dao.getPointOfInterest()
    }

    override suspend fun insertPointOfInterest(pointOfInterest: PointOfInterest) {
        return dao.insertPointOfInterest(pointOfInterest)
    }
}