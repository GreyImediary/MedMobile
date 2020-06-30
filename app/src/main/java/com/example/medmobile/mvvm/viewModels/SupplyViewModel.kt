package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.MedicineRequest
import com.example.medmobile.mvvm.model.PostSupply
import com.example.medmobile.mvvm.model.Supply
import com.example.medmobile.mvvm.repositories.SupplyRepository
import kotlinx.coroutines.launch

class SupplyViewModel(
    private val repository: SupplyRepository
) : ViewModel(), ViewModelCrud<Supply, PostSupply> {

    val supplies: MutableLiveData<List<Supply>> by lazy {
        MutableLiveData<List<Supply>>()
    }

    val supplyById: MutableLiveData<Supply> by lazy {
        MutableLiveData<Supply>()
    }

    val createdSupply: MutableLiveData<Supply> by lazy {
        MutableLiveData<Supply>()
    }

    val deletedSupply: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostSupply) {
        viewModelScope.launch {
            try {
                createdSupply.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                supplies.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                supplyById.value = repository.readById(token, id)
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
                    deletedSupply.value = "Лекарство удалено"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

}