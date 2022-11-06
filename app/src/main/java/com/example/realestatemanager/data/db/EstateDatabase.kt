package com.example.realestatemanager.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.realestatemanager.data.Converters
import com.example.realestatemanager.data.Dao.EstateDao
import com.example.realestatemanager.data.model.Estate

@TypeConverters(Converters::class)
@Database(entities = [Estate::class], version = 1, exportSchema = false)
abstract class EstateDatabase : RoomDatabase() {
    abstract val estateDao: EstateDao

    companion object {
        const val DATABASE_NAME = "estate_database"
        @Volatile
        private var INSTANCE: EstateDatabase? = null
        fun getDatabase(context: Context): EstateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        EstateDatabase::class.java,
                        "estate_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}