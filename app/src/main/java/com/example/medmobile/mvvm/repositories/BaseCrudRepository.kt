package com.example.medmobile.mvvm.repositories

interface BaseCrudRepository<out T, in C> {
    suspend fun create(token: String, postModel: C): T
    suspend fun read(token: String): List<T>
    suspend fun readById(token: String, id: Int): T
    suspend fun delete(token: String, id: Int): T
    fun resetPageHelper()
}