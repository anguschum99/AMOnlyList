package com.example.mal.data

import com.example.mal.network.MalApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val malRepository: MalRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.jikan.moe/v4/"
    
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitService: MalApiService by lazy {
        retrofit.create(MalApiService::class.java)
    }

    override val malRepository: MalRepository by lazy {
        NetworkMalRepository(retrofitService)
    }
    
    
}