package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.PharmSellsApi
import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.PharmSell
import com.example.medmobile.mvvm.model.PostPharmSell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PharmSellsRepository(
    private val api: PharmSellsApi,
    private val pageHelper: PageHelper
) : BaseCrudRepository<PharmSell, PostPharmSell, DeleteResult> {
    override suspend fun create(token: String, postModel: PostPharmSell): PharmSell  = withContext(
        Dispatchers.IO) {
        try {
            api.createPharmSell(token, postModel)
        } catch (t: Throwable) {
            Log.e("MedicineRequest", t.message ?: "no message")
            throw IllegalArgumentException("Не удалось создать заявку. Проверьте введённые данные")
        }
    }

    override suspend fun read(token: String): List<PharmSell> = withContext(Dispatchers.IO) {
        try {
            when {
                pageHelper.total == 0 -> {
                    val pageData = api.pharmSells(token, offset = pageHelper.offset)
                    pageHelper.total += pageData.total
                    pageHelper.currentSize += pageData.data.size
                    pageHelper.offset += 10
                    pageData.data
                }
                pageHelper.currentSize < pageHelper.total -> {
                    val pageData = api.pharmSells(token, offset = pageHelper.offset)
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

    override suspend fun readById(token: String, id: Int): PharmSell = withContext(Dispatchers.IO) {
        try {
            api.pharmSellById(token, id)
        } catch (t: Throwable) {
            Log.e("MedicineRequest", t.message ?: "no message")
            throw NetworkErrorException("Не удалось получить заявку")
        }
    }


    override suspend fun delete(token: String, id: Int): DeleteResult = withContext(Dispatchers.IO) {
        try {
            api.delete(token, id)
        } catch (t: Throwable) {
            Log.e("SupplyRepository", t.message ?: "no message")
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