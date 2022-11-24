package com.example.realestatemanager.ui.propertyMap

import android.accessibilityservice.GestureDescription.StrokeDescription

data class MapPropertyViewState(
    val id : Long?,
    val picture : String,
    val price : Long,
    val location : List<String>
)