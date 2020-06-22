package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.PharmacyApi
import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.Pharmacy
import com.example.medmobile.mvvm.model.PostPharmacy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PharmacyRepository(private val api: PharmacyApi, private val pageHelper: PageHelper) :
    BaseCrudRepository<Pharmacy, PostPharmacy, DeleteResult> {

    override suspend fun create(token: String, postModel: PostPharmacy): Pharmacy =
        withContext(Dispatchers.IO) {
            try {
                api.createPharmacy(token, postModel)
            } catch (t: Throwable) {
                Log.e("PharmacyRepository", t.message ?: "no message")
                throw IllegalArgumentException("Не удалось создать аптеку. Проверьте введённые данные")
            }
        }

    override suspend fun read(token: String): List<Pharmacy> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    pageHelper.total == 0 -> {
                        val pageData = api.pharmacies(token, offset = pageHelper.offset)
                        pageHelper.total += pageData.total
                        pageHelper.currentSize += pageData.data.size
                        pageHelper.offset += 10
                        pageData.data
                    }
                    pageHelper.currentSize < pageHelper.total -> {
                        val pageData = api.pharmacies(token, offset = pageHelper.offset)
                        pageHelper.offset += 10
                        pageHelper.currentSize += pageData.data.size
                        pageData.data
                    }
                    else -> {
                        emptyList()
                    }
                }

            } catch (t: Throwable) {
                Log.e("PharmacyRepository", t.message ?: "no message")
                throw NetworkErrorException("Не удалось получить список аптек")
            }
        }

    override suspend fun readById(token: String, id: Int): Pharmacy = withContext(Dispatchers.IO) {
        try {
            api.pharmacyById(token, id)
        } catch (t: Throwable) {
            Log.e("PharmacyRepository", t.message ?: "no message")
            throw NetworkErrorException("Не удалось получить аптеку")
        }
    }

    override suspend fun delete(token: String, id: Int): DeleteResult = withContext(Dispatchers.IO) {
        try {
            api.delete(token, id)
        } catch (t: Throwable) {
            Log.e("PharmacyRepository", t.message ?: "no message")
            throw NetworkErrorException("Не удалось удалить аптеку")
        }
    }

    override fun resetPageHelper() {
        pageHelper.run {
            currentSize = 0
            offset = 0
            total = 0
        }
    }
}