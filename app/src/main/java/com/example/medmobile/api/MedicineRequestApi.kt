package com.example.medmobile.api

import com.example.medmobile.mvvm.model.*
import retrofit2.http.*

interface MedicineRequestApi {
    @GET("/medicine_request")
    suspend fun medicineRequests(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<MedicineRequest>

    @GET("/medicine_request/{id}")
    suspend fun medicineRequestById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): MedicineRequest

    @POST("/medicine_request")
    suspend fun createMedicineRequest(
        @Header("Authorization") token: String,
        @Body postMedicine: PostMedicineRequest
    ): MedicineRequest

    @POST("/medicine_request/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}