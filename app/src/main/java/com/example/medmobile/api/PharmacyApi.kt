package com.example.medmobile.api

import com.example.medmobile.mvvm.model.*
import retrofit2.http.*

interface PharmacyApi {
    @GET("/pharmacy")
    suspend fun pharmacies(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
        @Query("sort") sort: String = "{\"id\":\"ASC\"}"
    ): PageData<Pharmacy>

    @GET("/pharmacy/{id}")
    suspend fun pharmacyById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Pharmacy

    @POST("/pharmacy")
    suspend fun createPharmacy(
        @Header("Authorization") token: String,
        @Body postMedicine: PostPharmacy
    ): Pharmacy

    @POST("/pharmacy/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResult
}