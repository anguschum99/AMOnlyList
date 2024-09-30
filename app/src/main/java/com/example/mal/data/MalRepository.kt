package com.example.mal.data

import android.accounts.NetworkErrorException
import com.example.mal.model.Anime
import com.example.mal.model.AnimeList
import com.example.mal.model.Chara
import com.example.mal.model.Manga
import com.example.mal.network.MalApiService

interface MalRepository {
    suspend fun getAnimeList(query: String, page: Int, genre: String?): List<Anime>?
    suspend fun getTopAnimeList(page: Int, filter: String? = null): List<Anime>?
    suspend fun getManga(query: String, page: Int, genre: String? = null): List<Manga>?
    suspend fun getCharacterList(query: String): List<Chara>?
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

    override suspend fun getTopAnimeList(page: Int, filter: String?): List<Anime>? {
        return try {
            val response = malApiService.getTopAnimeList(page, filter)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getManga(query: String, page: Int, genre: String?): List<Manga>? {
        return try {
            val response = malApiService.getMangaList(query, page)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCharacterList(query: String): List<Chara>? {
        return try {
            val response = malApiService.getCharacterList(query)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}