package com.example.medmobile.mvvm.repositories

interface LogInRepository {
    suspend fun login(login: String, password: String): String
}