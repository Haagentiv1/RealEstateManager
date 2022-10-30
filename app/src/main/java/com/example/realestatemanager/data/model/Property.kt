package com.example.realestatemanager.data.model

import androidx.room.Entity

@Entity(tableName = "property")
data class Property(
    val id: Int,
    val type: String,
    val surface: Int,
    val numberOfRooms: Int,
    val numberOfBathRooms: Int,
    val pictures: List<String>,
    val location: PropertyLocation,
    val poi: List<String>,
    val status: Boolean,
    val entryDate: String,
    val saleDate: String,
    val estateManagerName: String
    )
