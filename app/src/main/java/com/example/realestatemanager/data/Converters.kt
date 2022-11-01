package com.example.realestatemanager.data

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {


    @TypeConverter
    fun fromString(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromList(list: List<String?>?) = Json.encodeToString(list)
}

