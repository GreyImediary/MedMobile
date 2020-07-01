package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.PostSell
import com.example.medmobile.mvvm.model.Sell
import com.example.medmobile.mvvm.repositories.SellsRepository
import kotlinx.coroutines.launch

class SellsViewModel(private val repository: SellsRepository) : ViewModel(),
    ViewModelCrud<Sell, PostSell> {

    val sells: MutableLiveData<List<Sell>> by lazy {
        MutableLiveData<List<Sell>>()
    }

    val sellById: MutableLiveData<Sell> by lazy {
        MutableLiveData<Sell>()
    }

    val createdSell: MutableLiveData<Sell> by lazy {
        MutableLiveData<Sell>()
    }

    val deletedSell: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostSell) {
        viewModelScope.launch {
            try {
                createdSell.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                sells.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                sellById.value = repository.readById(token, id)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun delete(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.delete(token, id)
                if (result.affected == 1) {
                    deletedSell.value = "Лекарство удалено"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}