package com.example.realestatemanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.realestatemanager.data.local.model.PointOfInterest
import kotlinx.coroutines.flow.Flow

@Dao
interface PointOfInterestDao {

    @Insert
    suspend fun insertPointOfInterest(pointOfInterest: PointOfInterest)

    @Query("SELECT * FROM point_of_interest")
    fun getPointOfInterest() : Flow<List<PointOfInterest>>
}