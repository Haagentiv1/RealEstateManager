package com.example.realestatemanager.data.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "property")
data class Property(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Long,
    @ColumnInfo(name = "square_meter") val squareMeter: Float,
    @ColumnInfo(name = "location") val location: List<String>,
    @ColumnInfo(name = "number_of_rooms") val numberOfRooms: Int,
    @ColumnInfo(name = "number_of_bedrooms") val numberOfBedRooms: Int,
    @ColumnInfo(name = "number_of_bathrooms") val numberOfBathRooms: Int,
    @ColumnInfo(name = "pictures") val pictures: List<String>,
    @ColumnInfo(name = "poi") val poi: List<String>?,
    @ColumnInfo(name = "status") val status: Boolean,
    @ColumnInfo(name ="entry_date") val entryDate: String,
    @ColumnInfo(name = "saleDate") var saleDate: String?,
    @ColumnInfo(name = "estateManagerName") val estateManagerName: String

)
