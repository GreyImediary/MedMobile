package com.example.medmobile.mvvm.model

data class PageData<out T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val data: List<T>
)