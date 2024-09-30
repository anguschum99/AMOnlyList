package com.example.mal.model

data class MangaList(
    val data: List<Manga>,
    val pagination: Pagination
)

data class Manga(
    val approved: Boolean,
    val authors: List<Author>,
    val background: String,
    val chapters: Int,
    val demographics: List<Demographic>,
    val explicit_genres: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val published: Published,
    val publishing: Boolean,
    val rank: Int,
    val score: Double,
    val scored: Double,
    val scored_by: Int,
    val serializations: List<Serialization>,
    val status: String,
    val synopsis: String,
    val themes: List<Theme>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val titles: List<Title>,
    val type: String,
    val url: String,
    val volumes: Int
)


data class Author(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)



data class Published(
    val from: String,
    val prop: Prop,
    val string: String,
    val to: String
)

data class Serialization(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

