package com.example.meowmate.data.remote

data class CatImageDto(
    val id: String,
    val url: String,
    val breeds: List<BreedDto>?
)
