package com.example.medmobile.di

import com.example.medmobile.api.*
import com.example.medmobile.di.RetrofitProperties.BASE_URL
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProperties {
    const val BASE_URL = "BASE_URL"
}

fun createBaseUrl() = "http://med.std-247.ist.mospolytech.ru/api/"

fun createGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

fun createClient(): OkHttpClient = OkHttpClient()

inline fun <reified T> createApi(
    baseUrl: String,
    gsonConverterFactory: GsonConverterFactory,
    client: OkHttpClient
): T =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build().create(T::class.java)

val baseApiModule = module {
    single(named(BASE_URL)) { createBaseUrl() }
    single { createGsonConverter() }
    single { createClient() }
}

val apiModule = module {
    single {
        createApi<LogInApi>(
            get(named(BASE_URL)),
            get(),
            get()
        )
    }

    single {
        createApi<UserApi>(
            get(named(BASE_URL)),
            get(),
            get()
        )
    }

    single {
        createApi<ManufacturerApi>(
            get(named(BASE_URL)),
            get(),
            get()
        )
    }

    single {
        createApi<MedicineApi>(
            get(named(BASE_URL)),
            get(),
            get()
        )
    }

    single {
        createApi<PharmacyApi>(
            get(named(BASE_URL)),
            get(),
            get()
        )
    }

    single {
        createApi<MedicineRequestApi>(
            get(named(BASE_URL)),
            get(),
            get()
        )
    }
}