package com.example.medmobile.mvvm.model

data class Pharmacy(
    val id: Int,
    val title: String,
    val supervisor: String,
    val address: String,
    val phone: String
)

data class PostPharmacy(
    val title: String,
    val supervisor: String,
    val address: String,
    val phone: Long
)