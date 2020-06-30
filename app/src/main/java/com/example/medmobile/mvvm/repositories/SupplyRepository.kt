package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.SupplyApi
import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.PostSupply
import com.example.medmobile.mvvm.model.Supply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupplyRepository(
    private val api: SupplyApi,
    private val pageHelper: PageHelper
) : BaseCrudRepository<Supply, PostSupply, DeleteResult> {
    override suspend fun create(token: String, postModel: PostSupply): Supply = withContext(Dispatchers.IO) {
        try {
            api.createSupply(token, postModel)
        } catch (t: Throwable) {
            Log.e("MedicineRequest", t.message ?: "no message")
            throw IllegalArgumentException("Не удалось создать заявку. Проверьте введённые данные")
        }
    }

    override suspend fun read(token: String): List<Supply> = withContext(Dispatchers.IO) {
        try {
            when {
                pageHelper.total == 0 -> {
                    val pageData = api.supplies(token, offset = pageHelper.offset)
                    pageHelper.total += pageData.total
                    pageHelper.currentSize += pageData.data.size
                    pageHelper.offset += 10
                    pageData.data
                }
                pageHelper.currentSize < pageHelper.total -> {
                    val pageData = api.supplies(token, offset = pageHelper.offset)
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
    override suspend fun readById(token: String, id: Int): Supply = withContext(Dispatchers.IO) {
        try {
            api.supplyById(token, id)
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