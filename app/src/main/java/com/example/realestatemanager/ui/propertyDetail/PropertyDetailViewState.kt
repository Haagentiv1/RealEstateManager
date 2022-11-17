package com.example.realestatemanager.ui.propertyDetail 

data class PropertyDetailViewState(
    val id : Long,
    val picturesList: List<Pair<String,String>>,
    val description : String,
    val surfaceInSquareMeter : Float,
    val numberOfRooms : Int,
    val numberOfBedRooms : Int,
    val numberOfBathRooms : Int,
    val address : String,
    val town : String,
    val state : String,
    val zipCode :String,
    val country : String,
    val mapStaticString : String
)