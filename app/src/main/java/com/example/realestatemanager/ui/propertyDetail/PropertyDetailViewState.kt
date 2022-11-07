package com.example.realestatemanager.ui.propertyDetail 

data class PropertyDetailViewState(
    val id : Int,
    val picturesList: List<String>,
    val description : String,
    val surface : Int,
    val numberOfRooms : Int,
    val numberOfBedRooms : Int,
    val numberOfBathRooms : Int,
    val address : String,
    val town : String,
    val addressNumber : String,
    val zipCode :String,
    val country : String
)