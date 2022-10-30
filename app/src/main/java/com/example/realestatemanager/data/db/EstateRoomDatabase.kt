package com.example.realestatemanager.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.realestatemanager.data.DAO.EstateDao
import com.example.realestatemanager.data.model.Estate

@Database(entities = [Estate::class], version = 1, exportSchema = false)
abstract class EstateRoomDatabase : RoomDatabase() {
    abstract fun estateDao(): EstateDao

    companion object {
        @Volatile
        private var INSTANCE: EstateRoomDatabase? = null
        fun getDatabase(context: Context): EstateRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        EstateRoomDatabase::class.java,
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