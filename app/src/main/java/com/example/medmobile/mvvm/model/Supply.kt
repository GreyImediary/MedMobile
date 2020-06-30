package com.example.medmobile.mvvm.model

data class Supply(
    val id: Int,
    val positions: String,
    val status: String
)

data class PostSupply(
    val positions: String
)