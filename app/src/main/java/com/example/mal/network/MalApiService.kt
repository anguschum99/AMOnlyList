package com.example.mal.network

import com.example.mal.model.AnimeList
import com.example.mal.model.CharacterList
import com.example.mal.model.MangaList
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
    suspend fun getTopAnimeList(
        @Query("page") page: Int,
        @Query("filter") filter: String? = null
    ): Response<AnimeList>

    @GET("manga")
    suspend fun getMangaList(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("genre") genre: String? = null
    ): Response<MangaList>

//    @GET("top/manga")
//    suspend fun getTopMangaList(
//        @Query("page") page: Int,
//        @Query("filter") filter: String? = null
//    ): Response<MangaList>

    @GET("characters")
    suspend fun getCharacterList(
        @Query("mal_id") mal_id: String,
    ): Response<CharacterList>

}