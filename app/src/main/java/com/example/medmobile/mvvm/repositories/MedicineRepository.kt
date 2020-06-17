package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.MedicineApi
import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.Medicine
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.PostMedicine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineRepository(private val api: MedicineApi, private val pageHelper: PageHelper) :
    BaseCrudRepository<Medicine, PostMedicine, DeleteResult> {
    override suspend fun create(token: String, postModel: PostMedicine): Medicine =
        withContext(Dispatchers.IO) {
            try {
                api.createMedicine(token, postModel)
            } catch (t: Throwable) {
                Log.e("MedicineRepository", t.message ?: "no message")
                throw IllegalArgumentException("Не удалось создать лекарство. Проверьте введённые данные")
            }
        }

    override suspend fun read(token: String): List<Medicine> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    pageHelper.total == 0 -> {
                        val pageData = api.medicines(token, offset = pageHelper.offset)
                        pageHelper.total += pageData.total
                        pageHelper.currentSize += pageData.data.size
                        pageHelper.offset += 10
                        pageData.data
                    }
                    pageHelper.currentSize < pageHelper.total -> {
                        val pageData = api.medicines(token, offset = pageHelper.offset)
                        pageHelper.offset += 10
                        pageHelper.currentSize += pageData.data.size
                        pageData.data
                    }
                    else -> {
                        emptyList()
                    }
                }

            } catch (t: Throwable) {
                Log.e("MedicineRepository", t.message ?: "no message")
                throw NetworkErrorException("Не удалось получить список производителей")
            }
        }

    override suspend fun readById(token: String, id: Int): Medicine = withContext(Dispatchers.IO) {
        try {
            api.medicineById(token, id)
        } catch (t: Throwable) {
            Log.e("MedicineRepository", t.message ?: "no message")
            throw NetworkErrorException("Не удалось получить производителя")
        }
    }

    override suspend fun delete(token: String, id: Int): DeleteResult = withContext(Dispatchers.IO) {
        try {
            api.delete(token, id)
        } catch (t: Throwable) {
            Log.e("ManufacturerRepository", t.message ?: "no message")
            throw NetworkErrorException("Не удалось удалить производителя")
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