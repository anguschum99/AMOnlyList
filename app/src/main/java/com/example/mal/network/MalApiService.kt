package com.example.mal.network

import com.example.mal.model.AnimeCharacterList
import com.example.mal.model.AnimeList
import com.example.mal.model.manga.Manga
import com.example.mal.model.manga.MangaList
import com.example.mal.model.seasons.Season
import com.example.mal.model.seasons.SeasonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(
        @Path("id") id: Int
    ): Response<AnimeCharacterList>

    @GET("manga")
    suspend fun getMangaList(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("genre") genre: String? = null
    ): Response<MangaList>

    @GET("manga/{id}/full")
    suspend fun getMangaFull(
        @Path("id") id: Int
    ): Response<Manga>

    @GET("seasons/now")
    suspend fun getSeasonNow(
        @Query("page") page: Int
    ): Response<Season>



//    @GET("top/manga")
//    suspend fun getTopMangaList(
//        @Query("page") page: Int,
//        @Query("filter") filter: String? = null
//    ): Response<MangaList>


}