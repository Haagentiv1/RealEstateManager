package com.example.realestatemanager.data

import android.location.Location
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object Converters {


    @TypeConverter
    fun fromString(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromList(list: List<String?>?) = Json.encodeToString(list)

    @TypeConverter
    fun fromListOfPair(list : List<Pair<String,String>>) = Json.encodeToString(list)

    @TypeConverter
    fun fromStringToListOfPair(value : String) = Json.decodeFromString<List<Pair<String,String>>>(value)

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

