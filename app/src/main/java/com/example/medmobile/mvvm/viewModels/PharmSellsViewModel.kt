package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.PharmSell
import com.example.medmobile.mvvm.model.PostPharmSell
import com.example.medmobile.mvvm.repositories.PharmSellsRepository
import kotlinx.coroutines.launch

class PharmSellsViewModel(private val repository: PharmSellsRepository) : ViewModel(), ViewModelCrud<PharmSell, PostPharmSell> {
    fun resetHelper() = repository.resetPageHelper()

    val pharmSells: MutableLiveData<List<PharmSell>> by lazy {
        MutableLiveData<List<PharmSell>>()
    }

    val pharmSellById: MutableLiveData<PharmSell> by lazy {
        MutableLiveData<PharmSell>()
    }

    val createdPharmSell: MutableLiveData<PharmSell> by lazy {
        MutableLiveData<PharmSell>()
    }

    val deletedPharmSell: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override fun create(token: String, postModel: PostPharmSell) {
        viewModelScope.launch {
            try {
                createdPharmSell.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                pharmSells.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                pharmSellById.value = repository.readById(token, id)
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
                    deletedPharmSell.value = "Лекарство удалено"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}