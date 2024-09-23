package com.example.mal.network

import com.example.mal.model.AnimeList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MalApiService {

    @GET("anime")
    suspend fun getAnimeList(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("genre") genre: String? = null
    ): Response<AnimeList>

    @GET("top/anime")
    suspend fun getTopAnimeList(@Query("page") page: Int): Response<AnimeList>


}