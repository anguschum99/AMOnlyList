package com.example.mal.model.seasons

data class SeasonList(
    val `data`: List<Data>,
    val pagination: Pagination
)

data class SeasonSummary(
    val seasons: List<String>,
    val year: Int
)
