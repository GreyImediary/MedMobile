package com.example.medmobile.api

import com.example.medmobile.mvvm.model.*
import retrofit2.http.*

interface PharmSellsApi {
    @GET("/pharm_sells")
    suspend fun pharmSells(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<PharmSell>

    @GET("/pharm_sells/{id}")
    suspend fun pharmSellById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): PharmSell

    @POST("/pharm_sells")
    suspend fun createPharmSell(
        @Header("Authorization") token: String,
        @Body postSell: PostPharmSell
    ): PharmSell

    @POST("/pharm_sells/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}