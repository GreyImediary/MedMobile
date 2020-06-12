package com.example.medmobile.mvvm.viewModels

interface ViewModelCrud<out T, in C> {
    fun create(token: String,postModel: C)
    fun read(token: String)
    fun readById(token: String,id: Int)
    fun delete(token: String,id: Int)
}