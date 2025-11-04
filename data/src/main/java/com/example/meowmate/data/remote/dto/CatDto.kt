package com.example.meowmate.data.remote.dto


data class CatDto(
    val id: String,
    val url: String,
    val breeds: List<BreedDto>?
)