package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.Manufacturer
import com.example.medmobile.mvvm.model.PostManufacturer
import com.example.medmobile.mvvm.repositories.ManufacturerRepository
import kotlinx.coroutines.launch

class ManufacturerViewModel(private val repository: ManufacturerRepository) : ViewModel(),
    ViewModelCrud<Manufacturer, PostManufacturer> {

    val manufacturers: MutableLiveData<List<Manufacturer>> by lazy {
        MutableLiveData<List<Manufacturer>>()
    }

    val manufacturerById: MutableLiveData<Manufacturer> by lazy {
        MutableLiveData<Manufacturer>()
    }

    val createdManufacturer: MutableLiveData<Manufacturer> by lazy {
        MutableLiveData<Manufacturer>()
    }

    val deletedManufacturer: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostManufacturer) {
        viewModelScope.launch {
            try {
                createdManufacturer.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                manufacturers.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                manufacturerById.value = repository.readById(token, id)
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
                    deletedManufacturer.value = "Производитель удалён"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}