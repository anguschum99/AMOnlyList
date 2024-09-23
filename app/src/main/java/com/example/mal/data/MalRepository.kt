package com.example.mal.data

import android.accounts.NetworkErrorException
import com.example.mal.model.Anime
import com.example.mal.model.AnimeList
import com.example.mal.network.MalApiService

interface MalRepository {
    suspend fun getAnimeList(query: String, page: Int, genre: String?): List<Anime>?
    suspend fun getTopAnimeList(page: Int): List<Anime>?
}

class NetworkMalRepository(
    private val malApiService: MalApiService
) : MalRepository {
    override suspend fun getAnimeList(query: String, page: Int, genre: String?): List<Anime>? {
        return try {
            val response = malApiService.getAnimeList(query, page)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getTopAnimeList(page: Int): List<Anime>? {
        return try {
            val response = malApiService.getTopAnimeList(page)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}