package com.example.medmobile.mvvm.model

import com.google.gson.annotations.SerializedName

data class Medicine(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val type: String,
    val manufacturer: Manufacturer
)

data class PostMedicine(
    val title: String,
    val price: Double,
    val description: String,
    val type: String,
    @SerializedName("manufacturer")
    val manufacturerId: Int
)