package com.example.mal.model

import com.example.mal.model.anime.Images

data class AnimeCharacterList(
    val data: List<AniChara>
)

data class AniChara(
    val character: Character,
    val favorites: Int,
    val role: String,
    val voice_actors: List<VoiceActor>
)

data class Character(
    val images: Images,
    val mal_id: Int,
    val name: String,
    val url: String
)

data class VoiceActor(
    val language: String,
    val person: Person
)

data class Person(
    val images: Images,
    val mal_id: Int,
    val name: String,
    val url: String
)


