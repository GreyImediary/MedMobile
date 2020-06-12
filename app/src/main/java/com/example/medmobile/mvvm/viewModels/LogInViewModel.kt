package com.example.medmobile.mvvm.viewModels

import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.repositories.LogInRepository
import kotlinx.coroutines.launch

class LogInViewModel(private val repository: LogInRepository) : ViewModel() {
    val tokenLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val tokenError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            try {
                tokenLiveData.value = repository.login(login, password)
            } catch (e: IllegalArgumentException) {
                tokenError.value = e.message
            }
        }
    }

    fun decodeUserIdFromToken(token: String): Int {
        val splitString = token.split('.')
        val body = String(Base64.decode(splitString[1], Base64.DEFAULT))
        return body.substringAfter("sub\":")[0].toString().toInt()
    }
}