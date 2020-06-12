package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.PostUser
import com.example.medmobile.mvvm.model.User
import com.example.medmobile.mvvm.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel(),
    ViewModelCrud<User, PostUser> {
    val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }

    val userById: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    val currentUser: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    val createdUser: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    val userDeleted: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetPageHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostUser) {
        viewModelScope.launch {
            try {
                createdUser.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                users.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                userById.value = repository.readById(token, id)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun delete(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val deletedUser = repository.delete(token, id)
                userDeleted.value = "${deletedUser.name} удалён"
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    fun getCurrentUser(token: String, id: Int) {
        viewModelScope.launch {
            try {
                currentUser.value = repository.readById(token, id)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}