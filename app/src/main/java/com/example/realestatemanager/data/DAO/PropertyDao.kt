package com.example.realestatemanager.data.DAO

import android.content.ClipData.Item
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.data.model.Property
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow


@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property)

    @Update
    suspend fun update(property: Property)


    @Query("SELECT * FROM property WHERE id = :id")
    fun getItem(id:Int) : Flow<Property>

    @Query("SELECT * FROM property")
    fun getItems() : Flow<List<Property>>

}