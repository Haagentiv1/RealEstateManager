package com.example.realestatemanager.data


import androidx.room.TypeConverter
import com.example.realestatemanager.ui.utils.Utils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate


object Converters {


    @TypeConverter
    fun fromString(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromList(list: List<String?>?) =  Json.encodeToString(list)

    @TypeConverter
    fun fromListOfPair(list : List<Pair<String,String>>) = Json.encodeToString(list)

    @TypeConverter
    fun fromStringToListOfPair(value : String) = Json.decodeFromString<List<Pair<String,String>>>(value)

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(value)  }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}

