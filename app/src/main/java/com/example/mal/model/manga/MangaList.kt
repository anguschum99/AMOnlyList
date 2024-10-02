package com.example.mal.model.manga

data class MangaList(
    val data: List<MangaSummary>,
    val pagination: Pagination
)

data class MangaSummary(
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

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)

data class Author(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class Demographic(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class Genre(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class Images(
    val jpg: Jpg,
    val webp: Webp
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

data class Theme(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)

data class Title(
    val title: String,
    val type: String
)

data class Jpg(
    val image_url: String,
    val large_image_url: String,
    val small_image_url: String
)

data class Webp(
    val image_url: String,
    val large_image_url: String,
    val small_image_url: String
)

data class Prop(
    val from: From,
    val to: To
)

data class From(
    val day: Int,
    val month: Int,
    val year: Int
)

data class To(
    val day: Int,
    val month: Int,
    val year: Int
)

data class Items(
    val count: Int,
    val per_page: Int,
    val total: Int
)