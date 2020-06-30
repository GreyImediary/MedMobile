package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.Medicine
import com.example.medmobile.mvvm.model.MedicineRequest
import com.example.medmobile.mvvm.model.PostMedicineRequest
import com.example.medmobile.mvvm.repositories.MedicineRequestRepository
import kotlinx.coroutines.launch

class MedicineRequestViewModel(private val repository: MedicineRequestRepository) : ViewModel(),
    ViewModelCrud<MedicineRequest, PostMedicineRequest> {

    val medicineRequests: MutableLiveData<List<MedicineRequest>> by lazy {
        MutableLiveData<List<MedicineRequest>>()
    }

    val medicineRequestById: MutableLiveData<MedicineRequest> by lazy {
        MutableLiveData<MedicineRequest>()
    }

    val createdMedicineRequest: MutableLiveData<MedicineRequest> by lazy {
        MutableLiveData<MedicineRequest>()
    }

    val deletedMedicineRequest: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostMedicineRequest) {
        viewModelScope.launch {
            try {
                createdMedicineRequest.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                medicineRequests.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                medicineRequestById.value = repository.readById(token, id)
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
                    deletedMedicineRequest.value = "Лекарство удалено"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}