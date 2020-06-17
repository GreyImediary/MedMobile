package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.Manufacturer
import com.example.medmobile.mvvm.model.Medicine
import com.example.medmobile.mvvm.model.PostMedicine
import com.example.medmobile.mvvm.repositories.MedicineRepository
import kotlinx.coroutines.launch

class MedicineViewModel(private val repository: MedicineRepository) : ViewModel(), ViewModelCrud<Medicine, PostMedicine> {

    val medicines: MutableLiveData<List<Medicine>> by lazy {
        MutableLiveData<List<Medicine>>()
    }

    val medicineById: MutableLiveData<Medicine> by lazy {
        MutableLiveData<Medicine>()
    }

    val createdMedicine: MutableLiveData<Medicine> by lazy {
        MutableLiveData<Medicine>()
    }

    val deletedMedicine: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostMedicine) {
        viewModelScope.launch {
            try {
                createdMedicine.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                medicines.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                medicineById.value = repository.readById(token, id)
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
                    deletedMedicine.value = "Лекарство удалено"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}