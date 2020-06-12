package com.example.medmobile.mvvm.model

data class User(
    val id: Int,
    val login: String,
    val name: String,
    val phone: Long,
    val role: String,
    val status: String
)

data class PostUser(
    val login: String,
    val password: String,
    val name: String,
    val phone: Long,
    val role: String
)
