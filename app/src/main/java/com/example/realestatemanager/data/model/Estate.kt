package com.example.realestatemanager.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "estate")
data class Estate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val type: String,
    @ColumnInfo(name = "surface") val surface: Int,
    @ColumnInfo(name = "number_of_rooms") val numberOfRooms: Int,
    @ColumnInfo(name = "number_of_bathrooms")  val numberOfBathrooms: Int,
    @ColumnInfo(name = "pictures") val pictures: List<String> = listOf(),
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "poi") val poi: List<String> = listOf(),
    @ColumnInfo(name = "status") val status: Boolean,
    @ColumnInfo(name ="entry_date") val entryDate: String,
    @ColumnInfo(name = "saleDate") val saleDate: String,
    @ColumnInfo(name = "estateManagerName") val estateManagerName: String

)
