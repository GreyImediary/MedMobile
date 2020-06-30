package com.example.medmobile.mvvm.viewModels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medmobile.mvvm.model.Pharmacy
import com.example.medmobile.mvvm.model.PostPharmacy
import com.example.medmobile.mvvm.repositories.PharmacyRepository
import kotlinx.coroutines.launch

class PharmacyViewModel(private val repository: PharmacyRepository) : ViewModel(), ViewModelCrud<Pharmacy, PostPharmacy> {
    val pharmacies: MutableLiveData<List<Pharmacy>> by lazy {
        MutableLiveData<List<Pharmacy>>()
    }

    val pharmacyById: MutableLiveData<Pharmacy> by lazy {
        MutableLiveData<Pharmacy>()
    }

    val createdPharmacy: MutableLiveData<Pharmacy> by lazy {
        MutableLiveData<Pharmacy>()
    }

    val deletedPharmacy: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun resetHelper() = repository.resetPageHelper()

    override fun create(token: String, postModel: PostPharmacy) {
        viewModelScope.launch {
            try {
                createdPharmacy.value = repository.create(token, postModel)
            } catch (e: IllegalArgumentException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun read(token: String) {
        viewModelScope.launch {
            try {
                pharmacies.value = repository.read(token)
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }

    override fun readById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                pharmacyById.value = repository.readById(token, id)
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
                    deletedPharmacy.value = "Апетка удалена"
                }
            } catch (e: NetworkErrorException) {
                errorMessage.value = e.message
            }
        }
    }
}