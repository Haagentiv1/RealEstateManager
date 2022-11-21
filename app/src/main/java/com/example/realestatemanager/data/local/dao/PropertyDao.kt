package com.example.realestatemanager.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.realestatemanager.data.local.model.Property
import kotlinx.coroutines.flow.Flow


@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: Property)

    @Query("SELECT * FROM property WHERE id = :id")
    fun getPropertyById(id: Long): Flow<Property?>

    @Query("SELECT * FROM property")
    fun getProperties(): Flow<List<Property>>

}