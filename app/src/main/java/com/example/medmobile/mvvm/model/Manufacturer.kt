package com.example.medmobile.mvvm.model


data class Manufacturer(
    val id: Int,
    val title: String,
    val inn: String,
    val address: String,
    val phone: String
)

data class PostManufacturer(
    val title: String,
    val inn: Long,
    val address: String,
    val phone: Long
)