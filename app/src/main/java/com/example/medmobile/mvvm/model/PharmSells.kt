package com.example.medmobile.mvvm.model

data class PharmSell(
    val id: Int,
    val quantity: Int,
    val date: String,
    val checkId: Int,
    val pharmacy: Pharmacy,
    val medicine: Medicine
)

data class PostPharmSell(
    val quantity: Int,
    val date: String,
    val checkId: Int,
    val pharmacy: Int,
    val medicine: Int
)