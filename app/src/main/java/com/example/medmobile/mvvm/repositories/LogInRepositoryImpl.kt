package com.example.medmobile.mvvm.repositories

import android.util.Log
import com.example.medmobile.api.LogInApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogInRepositoryImpl(private val logInApi: LogInApi)  :
    LogInRepository {
    override suspend fun login(login: String, password: String) = withContext(Dispatchers.IO) {
        try {
            "Bearer ${logInApi.login(login, password).token}"
        } catch (t: Throwable) {
            Log.e("LoginRepository", t.message ?: "no message")
            throw IllegalArgumentException("Комбинация логина и пароля неверна")
        }
    }
}