package com.example.medmobile.mvvm.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token")
    val token: String
)