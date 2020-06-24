package com.example.medmobile.mvvm.model

data class MedicineRequest(
    val id: Int,
    val quantity: Int,
    val status: String,
    val medicine: Medicine
)

data class PostMedicineRequest(
    val quantity: Int,
    val medicine: Int
)