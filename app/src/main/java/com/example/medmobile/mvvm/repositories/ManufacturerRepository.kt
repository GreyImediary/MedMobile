package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.ManufacturerApi
import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.Manufacturer
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.PostManufacturer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManufacturerRepository(private val api: ManufacturerApi, private val pageHelper: PageHelper) :
    BaseCrudRepository<Manufacturer, PostManufacturer, DeleteResult> {

    override suspend fun create(token: String, postModel: PostManufacturer): Manufacturer =
        withContext(Dispatchers.IO) {
            try {
                api.createManufacturer(token, postModel)
            } catch (t: Throwable) {
                Log.e("ManufacturerRepository", t.message ?: "no message")
                throw IllegalArgumentException("Не удалось создать производителя. Проверьте введённые данные")
            }
        }
    override suspend fun read(token: String): List<Manufacturer> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    pageHelper.total == 0 -> {
                        val pageData = api.manufacturers(token, offset = pageHelper.offset)
                        pageHelper.total += pageData.total
                        pageHelper.currentSize += pageData.data.size
                        pageHelper.offset += 10
                        pageData.data
                    }
                    pageHelper.currentSize < pageHelper.total -> {
                        val pageData = api.manufacturers(token, offset = pageHelper.offset)
                        pageHelper.offset += 10
                        pageHelper.currentSize += pageData.data.size
                        pageData.data
                    }
                    else -> {
                        emptyList()
                    }
                }

            } catch (t: Throwable) {
                Log.e("ManufacturerRepository", t.message ?: "no message")
                throw NetworkErrorException("Не удалось получить список производителей")
            }
        }

    override suspend fun readById(token: String, id: Int): Manufacturer = withContext(Dispatchers.IO) {
        try {
            api.manufacturerById(token, id)
        } catch (t: Throwable) {
            Log.e("ManufacturerRepository", t.message ?: "no message")
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