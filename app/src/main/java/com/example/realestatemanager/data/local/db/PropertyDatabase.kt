package com.example.realestatemanager.data.local.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.realestatemanager.data.Converters
import com.example.realestatemanager.data.local.dao.PropertyDao
import com.example.realestatemanager.data.local.model.Property

@TypeConverters(Converters::class)
@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class PropertyDatabase : RoomDatabase() {
    abstract val propertyDao: PropertyDao

    companion object {
        const val DATABASE_NAME = "property_database"
    }
}