package com.example.realestatemanager.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.data.model.Estate
import kotlinx.coroutines.flow.Flow


@Dao
interface EstateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEstate(estate: Estate)

    @Update
    suspend fun updateEstate(estate: Estate)


    @Query("SELECT * FROM estate WHERE id = :id")
    fun getEstateById(id:Int) : LiveData<Estate?>

    @Query("SELECT * FROM estate")
    fun getEstates() : Flow<List<Estate>>

}