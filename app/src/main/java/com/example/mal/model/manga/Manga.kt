package com.example.mal.model.manga

data class Manga(
    val `data`: Data
)

data class Data(
    val approved: Boolean,
    val authors: List<Author>,
    val background: Any,
    val chapters: Int,
    val demographics: List<Any>,
    val explicit_genres: List<Any>,
    val `external`: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val published: Published,
    val publishing: Boolean,
    val rank: Any,
    val relations: List<Any>,
    val score: Any,
    val scored: Any,
    val scored_by: Any,
    val serializations: List<Serialization>,
    val status: String,
    val synopsis: Any,
    val themes: List<Any>,
    val title: String,
    val title_english: Any,
    val title_japanese: String,
    val title_synonyms: List<Any>,
    val titles: List<Title>,
    val type: String,
    val url: String,
    val volumes: Any
)

