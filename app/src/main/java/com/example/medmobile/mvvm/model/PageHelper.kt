package com.example.medmobile.mvvm.model

data class PageHelper(
    var offset: Int = 0,
    var currentSize: Int = 0,
    var total: Int = 0
)