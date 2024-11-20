package com.example.mal.model.seasons

data class SeasonList(
    val `data`: List<SeasonData>,
    val pagination: Pagination
)

data class SeasonData(
    val seasons: List<String>,
    val year: Int
)

//data class Pagination(
//    val has_next_page: Boolean,
//    val last_visible_page: Int
//)