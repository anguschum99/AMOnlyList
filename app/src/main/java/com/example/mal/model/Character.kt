package com.example.mal.model


data class CharacterList(
    val data: List<Chara>
)

data class Chara(
    val about: String,
    val anime: List<Anime>,
    val favorites: Int,
    val images: Images,
    val mal_id: Int,
    val manga: List<Manga>,
    val name: String,
    val name_kanji: String,
    val nicknames: List<String>,
    val url: String,
    val voices: List<Voice>
)

data class AnimeC(
    val anime: Anime,
    val role: String
)

data class MangaC(
    val manga: Manga,
    val role: String
)

data class Voice(
    val language: String,
    val person: Person
)

data class AnimeCC(
    val images: Images,
    val mal_id: Int,
    val title: String,
    val url: String
)

data class JpgX(
    val image_url: String,
    val small_image_url: String
)

data class WebpX(
    val image_url: String,
    val small_image_url: String
)

data class MangaX(
    val images: Images,
    val mal_id: Int,
    val title: String,
    val url: String
)

data class Person(
    val images: Images,
    val mal_id: Int,
    val name: String,
    val url: String
)
