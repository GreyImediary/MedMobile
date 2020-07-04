package com.example.medmobile.api

import com.example.medmobile.mvvm.model.*
import retrofit2.http.*

interface SellsApi {
    @GET("/sells")
    suspend fun sells(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<Sell>

    @GET("/sells/{id}")
    suspend fun sellById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Sell

    @POST("/sells")
    suspend fun createSell(
        @Header("Authorization") token: String,
        @Body postSell: PostSell
    ): Sell

    @POST("/sells/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}