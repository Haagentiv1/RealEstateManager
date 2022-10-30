package com.example.realestatemanager.data.DAO

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
    suspend fun insert(estate: Estate)

    @Update
    suspend fun update(estate: Estate)


    @Query("SELECT * FROM estate WHERE id = :id")
    fun getItem(id:Int) : Flow<Estate>

    @Query("SELECT * FROM estate")
    fun getItems() : Flow<List<Estate>>

}