package com.example.medmobile.mvvm.model

data class Sell(
    val id: Int,
    val quantity: Int,
    val date: String,
    val shelfLife: Int,
    val producedAt: String,
    val invoiceId: Int,
    val pharmacy: Pharmacy,
    val medicine: Medicine
)

data class PostSell(
    val quantity: Int,
    val date: String,
    val shelfLife: Int,
    val producedAt: String,
    val invoiceId: Int,
    val pharmacy: Int,
    val medicine: Int
)