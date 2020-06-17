package com.example.medmobile.api

import com.example.medmobile.mvvm.model.*
import retrofit2.http.*

interface MedicineApi {
    @GET("/medicine")
    suspend fun medicines(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int
    ): PageData<Medicine>

    @GET("/medicine/{id}")
    suspend fun medicineById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Medicine

    @POST("/medicine")
    suspend fun createMedicine(
        @Header("Authorization") token: String,
        @Body postMedicine: PostMedicine
    ): Medicine

    @POST("/medicine/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}