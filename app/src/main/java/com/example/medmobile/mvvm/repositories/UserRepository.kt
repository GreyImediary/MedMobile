package com.example.medmobile.mvvm.repositories

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.medmobile.api.UserApi
import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.model.PostUser
import com.example.medmobile.mvvm.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val api: UserApi, private val pageHelper: PageHelper) :
    BaseCrudRepository<User, PostUser, User> {

    override suspend fun create(token: String, postModel: PostUser): User =
        withContext(Dispatchers.IO) {
            try {
                api.createUser(token, postModel)
            } catch (t: Throwable) {
                Log.e("UserRepository", t.message ?: "no message")
                throw IllegalArgumentException("Не удалось создать пользователя. Проверьте введённые данные")
            }
        }

    override suspend fun read(token: String): List<User> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    pageHelper.total == 0 -> {
                        val pageData = api.users(token, offset = pageHelper.offset)
                        pageHelper.total += pageData.total
                        pageHelper.currentSize += pageData.data.size
                        pageHelper.offset += 10
                        pageData.data
                    }
                    pageHelper.currentSize < pageHelper.total -> {
                        val pageData = api.users(token, offset = pageHelper.offset)
                        pageHelper.offset += 10
                        pageHelper.currentSize += pageData.data.size
                        pageData.data
                    }
                    else -> {
                        emptyList()
                    }
                }

            } catch (t: Throwable) {
                Log.e("UserRepository", t.message ?: "no message")
                throw NetworkErrorException("Не удалось получить список пользователей")
            }
        }

    override suspend fun readById(token: String, id: Int): User = withContext(Dispatchers.IO) {
        try {
            api.userById(token, id)
        } catch (t: Throwable) {
            Log.e("UserRepository", t.message ?: "no message")
            throw NetworkErrorException("Не удалось получить пользователя")
        }
    }

    override fun resetPageHelper() {
        pageHelper.run {
            currentSize = 0
            offset = 0
            total = 0
        }
    }

    override suspend fun delete(token: String, id: Int): User = withContext(Dispatchers.IO) {
        try {
            api.delete(token, id)
        } catch (t: Throwable) {
            Log.e("UserRepository", t.message ?: "no message")
            throw NetworkErrorException("Не удалось удалить пользователя")
        }
    }
}