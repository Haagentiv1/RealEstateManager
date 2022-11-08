package com.example.realestatemanager.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.data.model.Property
import kotlinx.coroutines.flow.Flow


@Dao
interface EstateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEstate(property: Property)

    @Update
    suspend fun updateEstate(property: Property)


    @Query("SELECT * FROM property WHERE id = :id")
    fun getEstateById(id:Long) : LiveData<Property?>

    @Query("SELECT * FROM property")
    fun getEstates() : Flow<List<Property>>

}