package com.example.mal.data

import android.accounts.NetworkErrorException
import com.example.mal.model.AniChara
import com.example.mal.model.Anime
import com.example.mal.model.AnimeCharacterList
import com.example.mal.model.AnimeList
import com.example.mal.model.manga.Manga
import com.example.mal.model.manga.MangaList
import com.example.mal.model.manga.MangaSummary
import com.example.mal.network.MalApiService

interface MalRepository {
    suspend fun getAnimeList(query: String, page: Int, genre: String?): List<Anime>?
    suspend fun getTopAnimeList(page: Int, filter: String? = null): List<Anime>?
    suspend fun getAnimeCharacters(id: Int): List<AniChara>?
    suspend fun getManga(query: String, page: Int, genre: String? = null): List<MangaSummary>?
    suspend fun getMangaFull(id: Int): Manga?
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

    override suspend fun getAnimeCharacters(id: Int): List<AniChara>? {
        return try {
            val response = malApiService.getAnimeCharacters(id)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getManga(query: String, page: Int, genre: String?): List<MangaSummary>? {
        return try {
            val response = malApiService.getMangaList(query, page)
            if (response.isSuccessful) response.body()?.data ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getMangaFull(id: Int): Manga? {
        return try {
            val response = malApiService.getMangaFull(id)
            if (response.isSuccessful) response.body()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }




}