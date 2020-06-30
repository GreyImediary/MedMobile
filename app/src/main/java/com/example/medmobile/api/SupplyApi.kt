package com.example.medmobile.api

import com.example.medmobile.mvvm.model.*
import retrofit2.http.*

interface SupplyApi {
    @GET("/supply")
    suspend fun supplies(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<Supply>

    @GET("/supply/{id}")
    suspend fun supplyById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Supply

    @POST("/supply")
    suspend fun createSupply(
        @Header("Authorization") token: String,
        @Body postSupply: PostSupply
    ): Supply

    @POST("/supply/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}