package com.example.meowmate.data.remote.dto

import com.squareup.moshi.Json

data class BreedDto(
    val id: String? = null,
    val name: String,
    val origin: String?,
    val temperament: String?,
    @Json(name = "life_span") val lifeSpan: String?,
    val intelligence: Int?,
    @Json(name = "affection_level") val affectionLevel: Int?,
    @Json(name = "child_friendly") val childFriendly: Int?,
    @Json(name = "social_needs") val socialNeeds: Int?,
    @Json(name = "wikipedia_url") val wikipediaUrl: String?,
    @Json(name = "vetstreet_url") val vetstreetUrl: String?,
    val description: String?
)