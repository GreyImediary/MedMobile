package com.example.medmobile.api

import com.example.medmobile.mvvm.model.DeleteResult
import com.example.medmobile.mvvm.model.Manufacturer
import com.example.medmobile.mvvm.model.PageData
import com.example.medmobile.mvvm.model.PostManufacturer
import retrofit2.http.*

interface ManufacturerApi {

    @GET("/manufacturer")
    suspend fun manufacturers(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<Manufacturer>

    @GET("/manufacturer/{id}")
    suspend fun manufacturerById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Manufacturer

    @POST("/manufacturer")
    suspend fun createManufacturer(
        @Header("Authorization") token: String,
        @Body postManufacturer: PostManufacturer
    ): Manufacturer

    @POST("/user/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}