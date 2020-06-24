package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.MedicineRequestApi
import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.MedicineRequest
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.PostMedicineRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineRequestRepository(
    private val api: MedicineRequestApi,
    private val pageHelper: PageHelper
) : BaseCrudRepository<MedicineRequest, PostMedicineRequest, DeleteResult> {


    override suspend fun create(token: String, postModel: PostMedicineRequest): MedicineRequest =
        withContext(Dispatchers.IO) {
            try {
                api.createMedicineRequest(token, postModel)
            } catch (t: Throwable) {
                Log.e("MedicineRequest", t.message ?: "no message")
                throw IllegalArgumentException("Не удалось создать заявку. Проверьте введённые данные")
            }
        }

    override suspend fun read(token: String): List<MedicineRequest> = withContext(Dispatchers.IO) {
        try {
            when {
                pageHelper.total == 0 -> {
                    val pageData = api.medicineRequests(token, offset = pageHelper.offset)
                    pageHelper.total += pageData.total
                    pageHelper.currentSize += pageData.data.size
                    pageHelper.offset += 10
                    pageData.data
                }
                pageHelper.currentSize < pageHelper.total -> {
                    val pageData = api.medicineRequests(token, offset = pageHelper.offset)
                    pageHelper.offset += 10
                    pageHelper.currentSize += pageData.data.size
                    pageData.data
                }
                else -> {
                    emptyList()
                }
            }

        } catch (t: Throwable) {
            Log.e("MedicineRequest", t.message ?: "no message")
            throw NetworkErrorException("Не удалось получить список заявок")
        }
    }

    override suspend fun readById(token: String, id: Int): MedicineRequest = withContext(Dispatchers.IO) {
        try {
            api.medicineRequestById(token, id)
        } catch (t: Throwable) {
            Log.e("MedicineRequest", t.message ?: "no message")
            throw NetworkErrorException("Не удалось получить заявку")
        }
    }

    override suspend fun delete(token: String, id: Int): DeleteResult = withContext(Dispatchers.IO) {
        try {
            api.delete(token, id)
        } catch (t: Throwable) {
            Log.e("MedicineRequest", t.message ?: "no message")
            throw NetworkErrorException("Не удалось удалить заявку")
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