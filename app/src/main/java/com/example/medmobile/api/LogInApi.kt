package com.example.medmobile.api

import com.example.medmobile.mvvm.model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LogInApi {
    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun login(
        @Field("login") login: String,
        @Field("password") password: String
    ): Token
}