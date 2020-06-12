package com.example.medmobile.api

import com.example.medmobile.mvvm.model.PageData
import com.example.medmobile.mvvm.model.PostUser
import com.example.medmobile.mvvm.model.User
import retrofit2.http.*

interface UserApi {
    @GET("/user")
    suspend fun users(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<User>

    @GET("/user/{id}")
    suspend fun userById(@Header("Authorization") token: String, @Path("id") id: Int): User

    @POST("/user")
    suspend fun createUser(@Header("Authorization") token: String, @Body postUser: PostUser): User

    @POST("/user/{id}")
    suspend fun delete(@Header("Authorization") token: String, @Path("id") id: Int): User
}