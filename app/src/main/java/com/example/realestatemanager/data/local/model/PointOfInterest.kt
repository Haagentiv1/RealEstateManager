package com.example.realestatemanager.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "point_of_interest")
data class PointOfInterest (
        @PrimaryKey(autoGenerate = true) val id : Long?,
        @ColumnInfo(name = "poi_name") val name : String
        )
