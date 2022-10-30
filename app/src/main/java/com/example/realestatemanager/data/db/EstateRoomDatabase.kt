package com.example.realestatemanager.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.realestatemanager.data.model.Estate

@Database(entities = [Estate::class], version = 1, exportSchema = false)
abstract class EstateRoomDatabase : RoomDatabase() {

}